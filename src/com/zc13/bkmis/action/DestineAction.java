package com.zc13.bkmis.action;

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

import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.bkmis.service.IDestineService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 预定入住管理
 * @author dongdk
 * Date：Feb 21, 2011
 * Time：10:40:32 AM
 */
public class DestineAction extends BasicAction{

	Logger logger = Logger.getLogger(this.getClass());
	private IDestineService idestineService;
	private ICustomerRoomService iCustomerRoomService;
	private ICostTransactService costTransactService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		idestineService = (IDestineService)getBean("destineService");
		iCustomerRoomService = (ICustomerRoomService)getBean("ICustomerRoomService");
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	/**
	 * 获取所有的预入驻合同情况
	 * DestineAction.getDestineList
	 */
	public ActionForward getDestineList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String forward = "list";
		try {
			
			DestineForm destineForm = (DestineForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			destineForm.setLpId(Integer.valueOf(lpid));
			//得到所有预定客户和其数量
			int totalcount = idestineService.getTotalCount(destineForm);
			idestineService.getList(destineForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == destineForm.getDestineList() || destineForm.getDestineList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/destine.do?method=getDestineList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/destine.do?method=getDestineList", Integer.parseInt(GlobalMethod.NullToParam(destineForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(destineForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("lpId", GlobalMethod.NullToSpace(String.valueOf(destineForm.getLpId()))); 
			request.setAttribute("clientName", GlobalMethod.NullToSpace(destineForm.getClientName())); 
			request.setAttribute("roomCode", GlobalMethod.NullToSpace(destineForm.getRoomCode())); 
			request.setAttribute("status", GlobalMethod.NullToSpace(destineForm.getStatus())); 
			request.setAttribute("beginDate", GlobalMethod.NullToSpace(destineForm.getBeginDate())); 
			request.setAttribute("endDate", GlobalMethod.NullToSpace(destineForm.getEndDate())); 
			request.setAttribute("isEarnest", GlobalMethod.NullToSpace(destineForm.getIsEarnest()));
			request.setAttribute("code", GlobalMethod.NullToSpace(destineForm.getCode()));
		} catch (Exception e) {
			logger.error("获取预定合同信息失败!CustomerRoomAction.getDestineList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取预定合同信息失败，CustomerRoomAction.getDestineList()!",e);
		}
		return mapping.findForward(forward);
		
	}
	/**
	 * 跳转到入住页面
	 * DestineAction.goAdd
	 */
	public ActionForward goAdd(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactCode = null;
		String code = null;
		List<CompactClient> list = null;
		Map<String,List<SysCode>> map = null;
		String lpId = request.getParameter("lpId");
		try {
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpid = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			list = iCustomerRoomService.getAllClientList(lpid);
			compactCode = iCustomerRoomService.getCompactCode(lpid);
			code = iCustomerRoomService.getCustomerCode(lpid);
			map = iCustomerRoomService.getSysCode(lpid);
		} catch (Exception e) {
			logger.error("合同代码的获得失败!CustomerRoomAction.goAdd().详细信息：" + e.getMessage());
			throw new BkmisWebException("合同代码的获得失败，CustomerRoomAction.goAdd()!",e);
		}
		request.setAttribute("compactCode",compactCode);
		request.setAttribute("code",code);
		request.setAttribute("list",list);
		request.setAttribute("lpId",lpId);
		request.setAttribute("map",map);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		return mapping.findForward("goAdd");
	}
	/**
	 * 保存预租合同和各相关信息
	 * DestineAction.saveDestineCompact
	 */
	public ActionForward saveDestineCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		//防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getDestineList(mapping, form, request, response);
		}
		DestineForm destineForm = (DestineForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
		destineForm.setLpId(Integer.valueOf(lpid));
		
		String message = "";
		String compactCode = iCustomerRoomService.getCompactCode(Integer.valueOf(lpid));
		destineForm.setCompactCode(compactCode);
		try {
			idestineService.saveRoomRent(destineForm);//保存各信息，包括房租、合同、客户、以及他们之间的关系
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			writeLog(userName, Contants.ADD, "添加的预租合同编号为:" + destineForm.getCode(), Contants.L_COMPACTMANAGE,"预入驻");
			message = "保存成功！合同编号为："+compactCode;
		} catch (Exception e) {
			message = "保存失败！";
			logger.error("保存合同失败!CustomerRoomAction.saveCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("保存合同失败，CustomerRoomAction.saveCompact()!",e);
		}
		request.setAttribute("message", message);
//		response.sendRedirect("destine.do?method=getDestineList&message="+message);
		return this.getDestineList(mapping, form, request, response);
	}
	/**
	 *  跳到编辑页面
	 * DestineAction.gotoEditCompact
	 */
	public ActionForward gotoEditCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		DestineForm destineForm = (DestineForm)form;
		String compactChangeId = request.getParameter("id");
		String flag = request.getParameter("flag");
		List<CompactClient> list = null;
		Map<String,List<SysCode>> map = null;
		try {
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpid = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			destineForm.setLpId(lpid);
			list = iCustomerRoomService.getAllClientList(lpid);
			idestineService.getCompactMassage(destineForm,compactChangeId);
			map = iCustomerRoomService.getSysCode(lpid);
		} catch (Exception e) {
			logger.error("获取合同信息失败!CustomerRoomAction.gotoEditCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取合同信息失败，CustomerRoomAction.gotoEditCompact()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		request.setAttribute("id1",compactChangeId);
		if(!"".equals(flag)&&flag!=null){
			return mapping.findForward(flag);
		}else{
			return mapping.findForward("goEdit");
		}
	}
	/**
	 * 编辑修改信息
	 * DestineAction.saveEditCompact
	 */
	public ActionForward saveEditCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		DestineForm destineForm = (DestineForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
		destineForm.setLpId(Integer.valueOf(lpid));
		String compactId = request.getParameter("compactId");
		try {
			idestineService.editCompact(destineForm,compactId);
			
			response.sendRedirect("destine.do?method=getDestineList");
			
		} catch (Exception e) {
			logger.error("编辑合同失败!CustomerRoomAction.editCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑合同失败，CustomerRoomAction.editCompact()!",e);
		}
		return null;
	}
	/**
	 * 正式入住
	 * DestineAction.inCome
	 */
	public ActionForward inCome(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = request.getParameter("compactId");
		try {
			idestineService.inCome(compactId);

			costTransactService.updateAwokeTask4PressAdvance();
			costTransactService.updateAwokeTask4PressRentDeposit();
			costTransactService.updateAwokeTask4PressDecorationDeposit();
			costTransactService.updateAwokeTask4PressENotIn();
			costTransactService.updateAwokeTask4PressEarnest();
			response.sendRedirect("customer.do?method=getList&ruzhu=1&compactId="+compactId);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/** 删除合同 */
	public ActionForward delCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());

			iCustomerRoomService.delCompact(id,userName);
			response.sendRedirect("destine.do?method=getDestineList");
		} catch (Exception e) {
			logger.error("删除租赁合同!CustomerRoomAction.delCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("删除租赁合同，CustomerRoomAction.delCompact()!",e);
		}
		return null;
	}
	/**
	 * 给打印提供的
	 * DestineAction.printBill
	 */
	public ActionForward printBill(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			DestineForm destineForm = (DestineForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			destineForm.setLpId(Integer.valueOf(lpid));
			//得到所有预定客户和其数量
			idestineService.getList(destineForm);
			
		} catch (Exception e) {
			logger.error("获取预定合同信息失败!CustomerRoomAction.getDestineList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取预定合同信息失败，CustomerRoomAction.getDestineList()!",e);
		}
		return mapping.findForward("print");
	}
	//查询预入驻信息，导出报表
	public ActionForward explorReport(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){

		DestineForm destineForm = (DestineForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
		destineForm.setLpId(Integer.valueOf(lpid));
		String clientName = request.getParameter("clientName");
		String roomCode = request.getParameter("roomCode");
		String status = request.getParameter("status");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String isEarnest = request.getParameter("isEarnest");
			try{
				List<Compact> list = idestineService.explorDestineList(lpid,clientName,roomCode,status,beginDate,endDate,isEarnest);
				//表头
				String[] cellHeader = Constant.EXCEL_DESTINE_HEADER;
				String[] cellValue = Constant.EXCEL_DESTINE_VALUE;
				//定义文件名
				String sheetName = "预入驻管理列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new Compact());
				response.setBufferSize(100*1024);
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "预入驻管理列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出预入驻列表excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
}
