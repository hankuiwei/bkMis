package com.zc13.bkmis.action;

import java.io.PrintWriter;
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

import com.zc13.bkmis.form.IntentionForm;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.bkmis.service.IIntentionService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 意向书
 * @author wangzw
 * @Date May 11, 2011
 * @Time 10:41:16 AM
 */
public class IntentionAction extends BasicAction{

	Logger logger = Logger.getLogger(this.getClass());
	private IIntentionService intentionService;
	private ICustomerRoomService iCustomerRoomService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		intentionService = (IIntentionService)getBean("intentionService");
		iCustomerRoomService = (ICustomerRoomService)getBean("ICustomerRoomService");
	}
	
	/**
	 * 获取所有的意向书信息
	 */
	public ActionForward getIntentionList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String forward = "list";
		try {
			IntentionForm intentionForm = (IntentionForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			intentionForm.setLpId(Integer.valueOf(lpid));
			//得到所有预定客户和其数量
			int totalcount = intentionService.getTotalCount(intentionForm);
			intentionService.getList(intentionForm);
			System.out.println("用户的当前工作目录:\n"+System.getProperty("user.dir"));
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == intentionForm.getIntentionList() || intentionForm.getIntentionList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/intention.do?method=getIntentionList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/intention.do?method=getIntentionList", Integer.parseInt(GlobalMethod.NullToParam(intentionForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(intentionForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取意向书信息失败!IntentionAction.getIntentionList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取意向书信息失败，IntentionAction.getIntentionList()!",e);
		}
		this.my_saveToken(request);//自定义token机制，防止重复刷新
		return mapping.findForward(forward);
		
	}
	
	/** 检验意向书状态 */
	public ActionForward checkIntentionStatus(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String id = GlobalMethod.NullToSpace(request.getParameter("id"));//意向书id字符串
		String status = "0";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(intentionService.checkIntentionStatus(id)){//如果意向书的状态都为未提交审批或审批未通过
				status = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("检验意向书状态!IntentionAction.checkIntentionStatus().详细信息：" + e.getMessage());
			throw new BkmisWebException("检验意向书状态，IntentionAction.checkIntentionStatus()!",e);
		}
		out.print(status);
		return null;
	}
	
	/** 检验意向书是否已缴定金 */
	public ActionForward checkIsIsEarnest(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String id = GlobalMethod.NullToSpace(request.getParameter("id"));//意向书id
		String isConvertCompact = "0";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(intentionService.checkIsIsEarnest(id)){
				isConvertCompact = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("检验意向书是否已缴定金!IntentionAction.checkIsConvertCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("检验意向书是否已缴定金，IntentionAction.checkIsConvertCompact()!",e);
		}
		out.print(isConvertCompact);
		return null;
	}
	
	/**
	 * 跳转到添加意向书页面
	 */
	public ActionForward goAdd(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String intentionCode = null;//意向书编号
		String clientCode = null;//客户编号
		List<CompactClient> list = null;//客户列表
		Map<String,List<SysCode>> map = null;//系统参数
		try {
			IntentionForm intentionForm = (IntentionForm)form;
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map2.get("userlp").toString()), "0");
			intentionForm.setLpId(Integer.valueOf(lpid));
			
			list = iCustomerRoomService.getAllClientList(intentionForm.getLpId());
			intentionCode = intentionService.getIntentionCode(intentionForm);
			clientCode = iCustomerRoomService.getCustomerCode(intentionForm.getLpId());
			map = iCustomerRoomService.getSysCode(intentionForm.getLpId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("意向书代码的获得失败!IntentionAction.goAdd().详细信息：" + e.getMessage());
			throw new BkmisWebException("合同代码的获得失败，IntentionAction.goAdd()!",e);
		}
		request.setAttribute("intentionCode",intentionCode);
		request.setAttribute("clientCode",clientCode);
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		return mapping.findForward("toAdd");
	}
	
	/**
	 *  跳到编辑页面
	 */
	public ActionForward gotoEditIntention(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		IntentionForm intentionForm = (IntentionForm)form;
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));
		if(forward.equals("")){
			forward = "toEdit";
		}
		List<CompactClient> list = null;//客户列表
		String clientCode = null;//客户编号
		Map<String,List<SysCode>> map = null;
		try {
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map2.get("userlp").toString()), "0");
			intentionForm.setLpId(Integer.valueOf(lpid));
			
			list = iCustomerRoomService.getAllClientList(intentionForm.getLpId());
			intentionService.getIntentionDetails(intentionForm);
			map = iCustomerRoomService.getSysCode(intentionForm.getLpId());
			clientCode = iCustomerRoomService.getCustomerCode(intentionForm.getLpId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取意向书信息失败!IntentionAction.gotoEditIntention().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取意向书信息失败，IntentionAction.gotoEditIntention()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		request.setAttribute("clientCode",clientCode);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		return mapping.findForward(forward);
	}
	
	/**
	 * 保存或修改意向书信息
	 */
	public ActionForward saveIntention(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		//防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getIntentionList(mapping, form, request, response);
		}
		IntentionForm intentionForm = (IntentionForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String lpId = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
		intentionForm.setLpId(Integer.valueOf(lpId));
		intentionForm.getCompactIntention().setLpId(Integer.valueOf(lpId));
		intentionForm.getCompactClient().setLpId(Integer.valueOf(lpId));
		//加入日志的管理中
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		intentionForm.setUserName(userName);
		String message = "";
		String intentionCode = intentionService.getIntentionCode(intentionForm);//意向书编号
		String clientCode = iCustomerRoomService.getCustomerCode(intentionForm.getLpId());//客户编号
		if(intentionForm.getCompactIntention()!=null){
			if(intentionForm.getCompactIntention().getClientId()==null||intentionForm.getCompactIntention().getClientId()==0){
				intentionForm.getCompactIntention().setCode(clientCode);
			}
			if(intentionForm.getCompactIntention().getId()==null||intentionForm.getCompactIntention().getId()==0){
				intentionForm.getCompactIntention().setIntentionCode(intentionCode);
			}else{
				intentionCode = intentionForm.getCompactIntention().getIntentionCode();
			}
			try {
				intentionService.saveIntention(intentionForm);
				message = "保存成功！意向书编号为："+intentionCode;
			} catch (Exception e) {
				message = "保存失败！";
				e.printStackTrace();
				logger.error("保存意向书失败!IntentionAction.saveIntention().详细信息：" + e.getMessage());
				throw new BkmisWebException("保存意向书失败，IntentionAction.saveIntention()!",e);
			}
		}
		request.setAttribute("message", message);
		return this.getIntentionList(mapping, form, request, response);
	}
	
	
	/** 删除意向书 */
	public ActionForward delIntention(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		//防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getIntentionList(mapping, form, request, response);
		}
		IntentionForm intentionForm = (IntentionForm)form;
		//加入日志的管理中
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		intentionForm.setUserName(userName);
		String message = null;
		try {
			intentionService.delCompactIntention(intentionForm,userName);
			message = "删除成功！";
		} catch (Exception e) {
			message = "删除失败！";
			e.printStackTrace();
			logger.error("删除意向书!IntentionAction.delIntention().详细信息：" + e.getMessage());
			//throw new BkmisWebException("删除意向书，IntentionAction.delIntention()!",e);
		}
		intentionForm.setId(null);
		request.setAttribute("message", message);
		return this.getIntentionList(mapping, form, request, response);
	}
	
	/**
	 *  意向书转合同
	 */
	public ActionForward convertCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		IntentionForm intentionForm = (IntentionForm)form;
		List<CompactClient> list = null;//客户列表
		String clientCode = null;//客户编号
		Map<String,List<SysCode>> map = null;
		String compactCode = null;
		try {
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map2.get("userlp").toString()), "0");
			intentionForm.setLpId(Integer.valueOf(lpid));
			
			list = iCustomerRoomService.getAllClientList(intentionForm.getLpId());
			intentionService.getIntentionDetails(intentionForm);
			map = iCustomerRoomService.getSysCode(intentionForm.getLpId());
			clientCode = iCustomerRoomService.getCustomerCode(intentionForm.getLpId());
			compactCode = iCustomerRoomService.getCompactCode(intentionForm.getLpId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取意向书信息失败!IntentionAction.gotoEditIntention().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取意向书信息失败，IntentionAction.gotoEditIntention()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		request.setAttribute("clientCode",clientCode);
		request.setAttribute("compactCode",compactCode);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		return mapping.findForward("toConvertCompact");
	}
	
	/**
	 * 审批意向书
	 */
	public ActionForward approvalIntention(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		IntentionForm intentionForm = (IntentionForm)form;
		//加入日志的管理中
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		intentionForm.setUserName(userName);
		intentionForm.getCompactIntention().setStatus(request.getParameter("status"));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			intentionService.approvalIntention(intentionForm);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 打印
	 */
	public ActionForward printIntention(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String forward = "printList";
		try {
			IntentionForm intentionForm = (IntentionForm)form;
			//得到所有预定客户和其数量
			intentionForm.setPagesize("99999");
			intentionService.getList(intentionForm);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取意向书信息失败!IntentionAction.getIntentionList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取意向书信息失败，IntentionAction.getIntentionList()!",e);
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 导出报表
	 */
	public ActionForward explorReport(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		IntentionForm intentionForm = (IntentionForm)form;
		try {
			//得到所有预定客户
			intentionForm.setPagesize("99999");
			intentionService.getList(intentionForm);
			//表头
			String[] cellHeader = Constant.EXCEL_INTENTION_HEADER;
			String[] cellValue = Constant.EXCEL_INTENTION_VALUE;
			//定义文件名
			String sheetName = "意向书列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(intentionForm.getIntentionList(), sheetName, cellHeader, cellValue, new CompactIntention());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "意向书列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
