package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.zc13.bkmis.form.AccountDepotForm;
import com.zc13.bkmis.service.IAccountDepotService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 22, 2010
 * Time：9:25:12 AM
 */
public class AccountDepotAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IAccountDepotService iaccountService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		iaccountService = (IAccountDepotService)getBean("iaccountService");
	}
	//显示查询的结算信息
	public ActionForward showAccount(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AccountDepotForm adform = (AccountDepotForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				adform.setLpId(lpId);
				/** 到此为止 */
				iaccountService.showAccount(adform);
				//获取总条数
				int totalcount = iaccountService.queryCountTotal(adform);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == adform.getAccountList() || adform.getAccountList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/accountDepot.do?method=showAccount", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/accountDepot.do?method=showAccount", Integer.parseInt(GlobalMethod.NullToParam(adform.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(adform.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("year",adform.getYear());
				String monthString  = GlobalMethod.getMonth();
				request.setAttribute("month",monthString);
			}catch(Exception e){
				logger.error("查询库存核算信息加载失败，AccountDepotAction.showAccount()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询库存核算信息加载失败，AccountDepotAction.showAccount()!",e);
			}
			return mapping.findForward("showAccount");
	}
	//打开添加结算页面
	public ActionForward goAdd(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			String strDate=GlobalMethod.getTime2();
			String year = strDate.substring(0,4);
			String month = strDate.substring(4,6);
			String lastDate = GlobalMethod.NullToSpace(iaccountService.getBiggestCode("yjs"));
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			//渠道的最后一天加一再扔到jsp
			if(!"".equals(lastDate)){
				request.setAttribute("lastDate", GlobalMethod.addDate(lastDate));
			}else{
				request.setAttribute("lastDate", lastDate);
			}
			return mapping.findForward("addAccount");
	}
	//执行添加结算
	public ActionForward doAddAccount(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			AccountDepotForm adform = (AccountDepotForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				adform.setLpId(lpId);
				/** 到此为止 */
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				adform.setUserName(userName);
				iaccountService.doAddAccount(adform);
			}catch(Exception e){
				logger.error("添加库存核算信息加载失败，AccountDepotAction.doAddAccount()。详细信息："+e.getMessage());
				throw new BkmisWebException("添加库存核算信息加载失败，AccountDepotAction.doAddAccount()!",e);
			}
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("addAccount");
	}
	//打开查看结算详细信息界面
	public ActionForward detailAccount(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			AccountDepotForm adf = (AccountDepotForm)form;
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			adf.setLpId(lpId);
			/** 到此为止 */
			String strId = request.getParameter("editId");
			int id = Integer.parseInt(strId);
			List list = new ArrayList();
			
			try{
				list = iaccountService.selectAccountById(id,adf);
				request.setAttribute("list", list);
				//iaccountService.showDetailAccount(adf);
			}catch(Exception e){
				logger.error("查询库存详细核算信息加载失败，AccountDepotAction.detailAccount()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询库存详细核算信息加载失败，AccountDepotAction.detailAccount()!",e);
			}
			request.setAttribute("editId", strId);
			return mapping.findForward("detailAccount");
	}
	//删除库存核算信息
	public ActionForward delDepotAccount(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			AccountDepotForm adForm = (AccountDepotForm)form;
			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()), "0"));
			String strdelId = request.getParameter("delIds");
			adForm.setUserName(userName);
			adForm.setLpId(lpId);
			adForm.setIds(strdelId);
			iaccountService.delDepotAccount(adForm);
			response.sendRedirect("accountDepot.do?method=showAccount");
			return null;
	}
	/**
	 * 检查当前月份是否已经结算过
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * Date:May 10, 2011 
	 * Time:3:45:59 PM
	 */
	public ActionForward checkLast(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		boolean b = false;
		try {
			out = response.getWriter();
			AccountDepotForm form1 = (AccountDepotForm)form;
			b = iaccountService.ifAcount(form1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(b);
		
		return null;
		
	}
	
	/**
	 * luq 导出库存核算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Apr 10, 2012 
	 * Time:1:12:26 PM
	 */
	public ActionForward exportExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			//list中存放的就是当前页面上显示的所有数据
			AccountDepotForm adf = (AccountDepotForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			adf.setLpId(lpId);
			/** 到此为止*/
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			
			String strId = request.getParameter("editId");
			int id = Integer.parseInt(strId);
			List list = new ArrayList();
			list = iaccountService.selectAccountById(id,adf);
			//表头
			String[] cellHeader = Constant.EXCEL_ACCOUNT_DETAIL;
			String[] cellValue = Constant.EXCEL_ACCOUNT_VALUE;
			//定义文件名
			String sheetName = "材料出入库核算表明细列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(adf.getDetailAccountList(),sheetName,cellHeader,cellValue);
			
			response.setBufferSize(10000 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, "材料出入库核算表明细列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出材料出入库核算表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出报表
	 */
	public ActionForward printAccountDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		AccountDepotForm adf = (AccountDepotForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		adf.setLpId(lpId);
		/** 到此为止*/
		String strId = request.getParameter("editId");
		int id = Integer.parseInt(strId);
		List list = new ArrayList();
		
		try{
			list = iaccountService.selectAccountById(id,adf);
			request.setAttribute("list", list);
			//iaccountService.showDetailAccount(adf);
		}catch(Exception e){
			logger.error("查询库存详细核算信息加载失败，AccountDepotAction.detailAccount()。详细信息："+e.getMessage());
			throw new BkmisWebException("查询库存详细核算信息加载失败，AccountDepotAction.detailAccount()!",e);
		}
		request.setAttribute("editId", strId);
		return mapping.findForward("printAccountDetail");
	}
}
