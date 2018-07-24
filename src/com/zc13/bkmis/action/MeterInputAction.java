package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.MeterInputForm;
import com.zc13.bkmis.mapping.AllMeterType;
import com.zc13.bkmis.mapping.AllMeterTypeId;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.TotalReadMeter;
import com.zc13.bkmis.mapping.UserMeterReadingId;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.ILpManageService;
import com.zc13.bkmis.service.IWriteMeterService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.DTree;
import com.zc13.util.DateUtil;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
/**
 * 抄表录入
 * @author 王正伟
 * Date：Dec 1, 2010
 * Time：5:49:45 PM
 */
public class MeterInputAction extends BasicAction {
	private IWriteMeterService writeMeterService;
	private ILpManageService lpManageService;
	private ICostTransactService costTransactService;
	Logger logger = Logger.getLogger(this.getClass());

	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		writeMeterService = (IWriteMeterService)getBean("writeMeterService");
		lpManageService = (ILpManageService)getBean("ilpManageService");
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	/**
	 * 得到抄表录入所需的树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ActionForward getCTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		meterInputForm.setLpId(String.valueOf(lpId));
		/** 到此为止*/
		List<DTree> treeList = new ArrayList<DTree>();
		
		List<AllMeterType> allMeterTypeList = new ArrayList<AllMeterType>();
		try {
			allMeterTypeList = writeMeterService.getInfoForTree(meterInputForm);
		} catch (Exception e) {
			logger.error("tree的获得失败!MeterInputAction.getCTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，MeterInputAction.getCTree()!",e);
		}
		AllMeterType a = null;
		treeList.add(new DTree("1","费用抄录","0",""));
		//遍历list，包装成tree，从而在页面上生成树状图
		for(int i = 0;i<allMeterTypeList.size();i++){
			a = allMeterTypeList.get(i);
			DTree tree =  new DTree();
			AllMeterTypeId meterId = a.getId();
			tree.setId(meterId.getId().toString());
			tree.setName(meterId.getName());
			tree.setParentID(meterId.getParentId().toString());
			String tableName = GlobalMethod.NullToSpace(meterId.getTableName());
			//标示是用户表具还是公摊表具
			String meter_flag = GlobalMethod.NullToSpace(meterId.getMeterFlag());
			//楼盘id
			String LP_id = GlobalMethod.NullToSpace(String.valueOf(meterId.getLpId()));
			//楼盘名称
			String LP_name = java.net.URLEncoder.encode(java.net.URLEncoder.encode(GlobalMethod.NullToSpace(meterId.getLpName()),"utf-8"),"utf-8");
			//表具类型
			String meterTypeCode = GlobalMethod.NullToSpace(String.valueOf(meterId.getMeterTypeCode()));
			if(tableName.equals("sys_code")){
				//表具名称
				String meter_name = java.net.URLEncoder.encode(java.net.URLEncoder.encode(GlobalMethod.NullToSpace(meterId.getName()),"utf-8"),"utf-8");
				if(meter_flag.equals("用户表具")){
					tree.setUrl("meterInput.do?method=showTotalWriteMeterInfo&lpId=" + LP_id + "&meterTypeCode=" + meterTypeCode+"&lpName="+LP_name+"&meterName="+meter_name);
				}
				if(meter_flag.equals("公摊表具")){
					tree.setUrl("meterInput.do?method=showPublicWriteMeterInfo&lpId=" + LP_id + "&meterTypeCode=" + meterTypeCode+"&lpName="+LP_name+"&meterName="+meter_name);
				}
			}
			treeList.add(tree);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "meterInput.do?method=showTotalWriteMeterInfo");
		return mapping.findForward("tree");
	}
	

	/**
	 * 得到用户表具的抄表读数的统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward showTotalWriteMeterInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		//得到楼盘id
		String lpId = GlobalMethod.NullToParam(request.getParameter("lpId"), "0");
		//得到表具类型Code
		String meterTypeCode = GlobalMethod.NullToParam(request.getParameter("meterTypeCode"), "");
		//得到表具名称
		String meterName = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("meterName"),"默认"),"utf-8");
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(new Date());
		String years = GlobalMethod.NullToParam(request.getParameter("years"), dateStr.substring(0,4));
		System.out.println(years);
		ELp lp = new ELp();
		try {
			//根据楼盘id得到楼盘对象
			lp = lpManageService.getELpById(Integer.parseInt(lpId));
		} catch (Exception e) {
			lp.setLpName("默认");
			//e.printStackTrace();
		}
		meterInputForm.setLpId(lpId);
		meterInputForm.setMeterName(meterName);
		meterInputForm.setMeterTypeCode(meterTypeCode);
		meterInputForm.setYears(years);
		List<TotalReadMeter> list = null;
		try {
			list = writeMeterService.getTotalWriteMeterInfo(meterInputForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		request.setAttribute("lp", lp);
		request.setAttribute("years", years);
		request.setAttribute("meterTypeCode", meterTypeCode);
		request.setAttribute("meterName", meterName);
		return mapping.findForward("totalUserReadMeter");
	}
	
	/**
	 * 获得用户表具的详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getUserReadMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		MeterInputForm meterInputForm = (MeterInputForm)form;
		List list = null;
		try {
			list = writeMeterService.getUserReadMeter(meterInputForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("userMeterList", list);
		Calendar c = Calendar.getInstance();//获得当前时间对象
		String years = GlobalMethod.NullToParam(meterInputForm.getYears(),String.valueOf(c.get(Calendar.YEAR)));
		String months = GlobalMethod.NullToParam(meterInputForm.getMonths(),String.valueOf(c.get(Calendar.MONTH)+1));
		String roomCode = GlobalMethod.NullToParam(meterInputForm.getRoomCode(),"");
		String meterCode = GlobalMethod.NullToParam(meterInputForm.getMeterCode(),"");
		
		c.set(Integer.parseInt(years), Integer.parseInt(months)-1, 1);
		//获得指定月份的第一天的日期
		String startDate = DateUtil.getFirstDay(c);
		//获得指定月份的最后一天的日期
		String lastDate = DateUtil.getLastDay(c);
		String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
		request.setAttribute("years", years);//年期间
		request.setAttribute("months", months);//月期间
		request.setAttribute("roomCode", roomCode);//房间编号
		request.setAttribute("meterCode", meterCode);//表具编号
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("startDate", startDate);
		request.setAttribute("meterName", meterName);
		request.setAttribute("lookUpMan", GlobalMethod.NullToSpace(meterInputForm.getLookUpMan()));//抄表人
		request.setAttribute("lookUpTime", GlobalMethod.NullToSpace(meterInputForm.getLookUpTime()));//抄表时间
		request.setAttribute("enterMan", GlobalMethod.NullToSpace(meterInputForm.getEnterMan()));//录入人
		request.setAttribute("list", list);
		return mapping.findForward("toEditUserReadMeter");
	}
	
	/**
	 * 保存读表信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveUserReadMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		List list = null;
		try {
			PrintWriter out = response.getWriter();
			try {
				//list中保存的是本次更新记录的id值
				list = writeMeterService.saveUserReadMeter(request);
				StringBuffer ids = new StringBuffer();
				if(list!=null){
					for(int i = 0;i<list.size();i++){
						ids.append(list.get(i));
						if(i<list.size()-1)
							ids.append(",");
					}
				}
				out.print(ids.toString());
			} catch (Exception e) {
				out.print("0");
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到公摊表具的抄表读数的统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward showPublicWriteMeterInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		//得到楼盘id
		String lpId = GlobalMethod.NullToParam(request.getParameter("lpId"), "0");
		//得到表具类型Code
		String meterTypeCode = GlobalMethod.NullToParam(request.getParameter("meterTypeCode"), "");
		//得到表具名称
		String meterName = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("meterName"),"默认"),"utf-8");
		//获得当前日期
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(new Date());
		//如果查询条件的年度的参数为空，则默认为当年
		String years = GlobalMethod.NullToParam(request.getParameter("years"), dateStr.substring(0,4));
		ELp lp = new ELp();
		try {
			//根据楼盘id得到楼盘对象
			lp = lpManageService.getELpById(Integer.parseInt(lpId));
		} catch (Exception e) {
			lp.setLpName("默认");
			e.printStackTrace();
		}
		meterInputForm.setLpId(lpId);
		meterInputForm.setMeterName(meterName);
		meterInputForm.setMeterTypeCode(meterTypeCode);
		meterInputForm.setYears(years);
		List list = null;
		try {
			list = writeMeterService.getTotalPublicWriteMeterInfo(meterInputForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		request.setAttribute("lp", lp);
		request.setAttribute("years", years);
		request.setAttribute("meterTypeCode", meterTypeCode);
		request.setAttribute("meterName", meterName);
		return mapping.findForward("totalPublicReadMeter");
	}
	
	
	/**
	 * 获得指定年度和公摊表具的详细读数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ActionForward getPublicReadMeterByYearAndId(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		List list = null;
		try {
			list = writeMeterService.getPublicReadMeterByYearAndId(meterInputForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
		String meterCode = GlobalMethod.NullToParam(meterInputForm.getMeterCode(),"");
		request.setAttribute("meterCode", meterCode);//表具编号
		request.setAttribute("list", list);
		request.setAttribute("years", meterInputForm.getYears());
		request.setAttribute("meterName", meterInputForm.getMeterName());
		return mapping.findForward("toShowPublicReadMeterForYearAndId");

	}
	
	/**
	 * 获得公摊表具的详细读数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ActionForward getPublicReadMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		List list = null;
		try {
			list = writeMeterService.getPublicReadMeter(meterInputForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("publicMeterList", list);
		Calendar c = Calendar.getInstance();//获得当前时间对象
		String years = GlobalMethod.NullToParam(meterInputForm.getYears(),String.valueOf(c.get(Calendar.YEAR)));
		String months = GlobalMethod.NullToParam(meterInputForm.getMonths(),String.valueOf(c.get(Calendar.MONTH)+1));
		String meterCode = GlobalMethod.NullToParam(meterInputForm.getMeterCode(),"");
		c.set(Integer.parseInt(years), Integer.parseInt(months)-1, 1);
		//获得指定月份的第一天的日期
		String startDate = DateUtil.getFirstDay(c);
		//获得指定月份的最后一天的日期
		String lastDate = DateUtil.getLastDay(c);
		String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
		request.setAttribute("years", years);//年期间
		request.setAttribute("months", months);//月期间
		request.setAttribute("meterCode", meterCode);//表具编号
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("startDate", startDate);
		request.setAttribute("meterName", meterName);
		request.setAttribute("lookUpMan", GlobalMethod.NullToSpace(meterInputForm.getLookUpMan()));//抄表人
		request.setAttribute("lookUpTime", GlobalMethod.NullToSpace(meterInputForm.getLookUpTime()));//抄表时间
		request.setAttribute("enterMan", GlobalMethod.NullToSpace(meterInputForm.getEnterMan()));//录入人
		request.setAttribute("list", list);
		return mapping.findForward("toShowPublicReadMeter");

	}
	
	/**
	 * 用户表具报表导出
	 * 
	 */
	public ActionForward explortUserExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		try {
			List list = null;
			try {
				list = writeMeterService.getUserReadMeter(meterInputForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
			//表头
			String[] cellHeader = Constant.EXCEL_USERMETER_DETAIL;
			String[] cellValue = Constant.EXCEL_USERMETER_VALUE;
			//定义文件名
			String sheetName = "用户"+meterName+"读表信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new UserMeterReadingId());
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "用户"+meterName+"读表信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出用户读表excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 公摊表具报表导出
	 * 
	 */
	public ActionForward explortPublicExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		try {
			List list = null;
			try {
				list = writeMeterService.getPublicReadMeter(meterInputForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
			//表头
			String[] cellHeader = Constant.EXCEL_PUBLICMETER_DETAIL;
			String[] cellValue = Constant.EXCEL_PUBLICMETER_VALUE;
			//定义文件名
			String sheetName = "公摊"+meterName+"读表信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new UserMeterReadingId());
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "公摊"+meterName+"读表信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出公摊读表excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公摊表具报表导出2
	 * 
	 */
	public ActionForward explortPublicExcel2(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MeterInputForm meterInputForm = (MeterInputForm)form;
		try {
			List list = null;
			try {
				list = writeMeterService.getPublicReadMeterByYearAndId(meterInputForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String meterName = GlobalMethod.NullToSpace(meterInputForm.getMeterName());
			//表头
			String[] cellHeader = Constant.EXCEL_PUBLICMETER_DETAIL2;
			String[] cellValue = Constant.EXCEL_PUBLICMETER_VALUE2;
			//定义文件名
			String sheetName = "公摊"+meterName+"读表信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new UserMeterReadingId());
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "公摊"+meterName+"读表信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出公摊读表excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除session中的list
	 * 
	 */
	public ActionForward deleteSessionInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.getSession().removeAttribute("userMeterList");
		request.getSession().removeAttribute("publicMeterList");
		//request.getSession().removeAttribute("");
		return null;
	}
}

