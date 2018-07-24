package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.PhoneCostForm;
import com.zc13.bkmis.mapping.CServiceProvider;
import com.zc13.bkmis.mapping.ECallInfo;
import com.zc13.bkmis.service.IPhoneCostService;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 通话记录action
 * @author wangzw
 * @Date Oct 12, 2011
 * @Time 9:24:07 AM
 */
public class PhoneCostAction extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private IPhoneCostService phoneCostService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		phoneCostService = (IPhoneCostService)getBean("phoneCostService");
	}
	
	/**
	 * 获取当前在住客户电话费用信息列表
	 */
	public ActionForward getPhoneCostInfoList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		phoneCostService.getPhoneCostInfoList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getPhoneCostList() || form1.getPhoneCostList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneCostInfoList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneCostInfoList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		return mapping.findForward("showPhoneCostInfoList");
	}
	
	/**
	 * 获取指定的电话号码的明细
	 */
	public ActionForward getPhoneCostInfoDetails(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		phoneCostService.getPhoneCostInfoDetails(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getPhoneCallInfoList() || form1.getPhoneCallInfoList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneCostInfoDetails", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneCostInfoDetails", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward("showPhoneCostInfoDetails");
	}
	
	/**
	 * 获取运营商信息service_ provider
	 */
	public ActionForward getServiceProviderInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		List list = phoneCostService.getServiceProviderInfo(form1,true);
		CServiceProvider provider = new CServiceProvider();
		if(list!=null&&list.size()>0){
			provider = (CServiceProvider)list.get(0);
		}
		request.setAttribute("provider", provider);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		return mapping.findForward("serviceProviderInfo");
	}
	
	/**
	 * 保存供应商
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 13, 2011 
	 * Time:11:17:47 PM
	 */
	public ActionForward saveProvider(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getPhoneParameterList(mapping, form, request, response);
		}try{
			phoneCostService.saveProvider(form1);
			request.setAttribute("alertMessage", "保存成功！");
		}catch(Exception e){
			request.setAttribute("alertMessage", "保存失败！");
		}
		return this.getServiceProviderInfo(mapping, form, request, response);
	}
	
	/**
	 * 获取电话资费参数列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 14, 2011 
	 * Time:12:00:02 AM
	 */
	public ActionForward getPhoneParameterList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		phoneCostService.getServiceProviderInfo(form1, false);
		phoneCostService.getPhoneParameterList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getPhoneParameterList() || form1.getPhoneParameterList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneParameterList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getPhoneParameterList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward("showPhoneParameterList");
	}
	
	/**
	 * 保存资费信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 14, 2011 
	 * Time:12:42:12 AM
	 */
	public ActionForward savePhoneParameter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getPhoneParameterList(mapping, form, request, response);
		}
		try{
			phoneCostService.savePhoneParameter(form1);
			request.setAttribute("alertMessage", "保存成功！");
		}catch(Exception e){
			request.setAttribute("alertMessage", "保存失败！");
			e.printStackTrace();
		}
		return this.getPhoneParameterList(mapping, form, request, response);
	}
	
	/**
	 * 删除资费标准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePhoneParameter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getPhoneParameterList(mapping, form, request, response);
		}
		try {
			phoneCostService.deletePhoneParameter(form1);
			request.setAttribute("alertMessage", "删除成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "删除失败！");
		}
		return this.getPhoneParameterList(mapping, form, request, response);
	}
	
	/**
	 * 获取区号列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 14, 2011 
	 * Time:12:00:02 AM
	 */
	public ActionForward getRegionCodeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		phoneCostService.getRegionCodeList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getRegionCodeList() || form1.getRegionCodeList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getRegionCodeList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=getRegionCodeList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward("showRegionCodeList");
	}
	
	/**
	 * 保存区号信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 14, 2011 
	 * Time:12:42:12 AM
	 */
	public ActionForward saveRegionCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getRegionCodeList(mapping, form, request, response);
		}
		try{
			phoneCostService.saveRegionCode(form1);
			request.setAttribute("alertMessage", "保存成功！");
		}catch(Exception e){
			request.setAttribute("alertMessage", "保存失败！");
			e.printStackTrace();
		}
		return this.getRegionCodeList(mapping, form, request, response);
	}
	
	/**
	 * 删除区号信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRegionCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getRegionCodeList(mapping, form, request, response);
		}
		try {
			phoneCostService.deleteRegionCode(form1);
			request.setAttribute("alertMessage", "删除成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "删除失败！");
		}
		return this.getRegionCodeList(mapping, form, request, response);
	}
	
	/**
	 * 获取区域名称
	 */
	public ActionForward getAreaName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String phone = GlobalMethod.NullToSpace(request.getParameter("phone"));//电话号码
		PrintWriter out = null;
		String areaName = "";
		try {
			out = response.getWriter();
			areaName = phoneCostService.getAreaName(phone,null);
			out.print(areaName);
		} catch (Exception e) {
			out.print(areaName);
			e.printStackTrace();
		}finally{
			if(out!=null){out.close();}
		}
		return null;
	}
	
	/**
	 * 获取通话费用
	 */
	public ActionForward getCost(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String phone = GlobalMethod.NullToSpace(request.getParameter("phone"));//电话号码
		int callTime = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("callTime"), "0"));//通话时长
		double cost = 0.0;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			cost = phoneCostService.getCost(phone, callTime,null,null);
			out.print(cost);
		} catch (Exception e) {
			out.print(cost);
			e.printStackTrace();
		}finally{
			if(out!=null){out.close();}
		}
		return null;
	}
	
	/**
	 * 转向编辑通话费用页面
	 */
	public ActionForward toEditCallInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		String id = GlobalMethod.NullToSpace(request.getParameter("id"));
		try {
			phoneCostService.getCallInfo(form1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward("editCallInfo");
	}
	
	/**
	 * 保存通话费用页面
	 */
	public ActionForward editCallInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getPhoneCostInfoDetails(mapping, form, request, response);
		}
		String message = "";
		try {
			phoneCostService.saveCallInfo(form1);
			message = "保存成功！";
		} catch (Exception e) {
			message = "保存失败！";
			e.printStackTrace();
			log.error("保存通话费用失败！", e);
		}
		request.setAttribute("message", message);
		return mapping.findForward("editCallInfo");
	}
	
	/**
	 * 删除通话费用信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCallInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getPhoneCostInfoDetails(mapping, form, request, response);
		}
		try {
			phoneCostService.deleteCallInfo(form1);
			request.setAttribute("message", "删除成功！");
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.error("删除通话费用信息！", e);
			request.setAttribute("message", "删除失败！");
		}
		return this.getPhoneCostInfoDetails(mapping, form, request, response);
	}
	
	/**
	 * 在住客户电话费用信息列表导出报表
	 */
	public ActionForward exportPhoneCostInfoList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try {
			phoneCostService.getPhoneCostInfoList(form1,false);
			// 表头
			String[] cellHeader = Constant.EXCEL_PHONECOSTINFO_DETAIL;
			String[] cellValue = Constant.EXCEL_PHONECOSTINFO_VALUE;
			// 定义文件名
			String sheetName = "电话费用信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(form1.getPhoneCostList(), sheetName, cellHeader, cellValue);
			//HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new CompactClient());
			response.setBufferSize(100 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, "电话费用信息列表" + GlobalMethod.getTime2() + ".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出在住客户电话费用信息列表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 在住客户电话费用信息详单列表导出报表
	 */
	public ActionForward exportPhoneCostInfoDetails(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try {
			phoneCostService.getPhoneCostInfoDetails(form1,false);
			// 表头
			String[] cellHeader = Constant.EXCEL_PHONECOSTINFODETAIL_DETAIL;
			String[] cellValue = Constant.EXCEL_PHONECOSTINFODETAIL_VALUE;
			// 定义文件名
			String sheetName = form1.getCxPhoneNumber()+"电话费用详细列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(form1.getPhoneCallInfoList(), sheetName, cellHeader, cellValue);
			//HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new CompactClient());
			response.setBufferSize(10000 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, form1.getCxPhoneNumber()+"电话费用详细列表" + GlobalMethod.getTime2() + ".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出在住客户电话费用详细信息列表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 在住客户电话费用信息列表打印
	 */
	public ActionForward printPhoneCostInfoList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try {
			phoneCostService.getPhoneCostInfoList(form1,false);
		} catch (Exception e) {
			log.error("打印在住客户电话费用信息列表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return mapping.findForward("printPhoneCostInfoList");
	}
	
	/**
	 * 在住客户电话费用信息详单列表打印
	 */
	public ActionForward printPhoneCostInfoDetails(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try {
			phoneCostService.getPhoneCostInfoDetails(form1,false);
		} catch (Exception e) {
			log.error("打印在住客户电话费用详细信息列表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return mapping.findForward("printPhoneCostInfoDetails");
	}
	
	/**
	 * 手动将指定时间段内没有同步到数据库的话单同步进去
	 */
	public ActionForward buildCallInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try{
			phoneCostService.buildCallInfo(form1);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			log.error("手动将指定时间段内没有同步到数据库的话单同步进去操作失败",e);
		}
		return mapping.findForward("toBatchUpdateCallInfo");
	}
	
	/**
	 * 根据最新的资费标准更新指定时间段内的话费金额，如果未指定时间段，则默认更新所有
	 */
	public ActionForward updatePhoneCost(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try{
			phoneCostService.updatePhoneCost(form1);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			log.error("根据最新的资费标准更新指定时间段内的话费金额操作失败",e);
		}
		return mapping.findForward("toBatchUpdateCallInfo");
	}
	
	/**
	 * 根据最新的区号设置更新指定时间段内的地区名称，如果未指定时间段，则默认更新所有
	 */
	public ActionForward updateAreaName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try{
			phoneCostService.updateAreaName(form1);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			log.error("根据最新的区号设置更新指定时间段内的地区名称操作失败",e);
		}
		return mapping.findForward("toBatchUpdateCallInfo");
	}
	
	/**
	 * luq 查看指定账单的通话信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Apr 10, 2012 
	 * Time:1:12:26 PM
	 */
	public ActionForward showCallInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		String billid = request.getParameter("billid");
		form1.setId(Integer.valueOf(billid));
		List calllist = phoneCostService.getCallInfoList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getPagesize() || "".equals(form1.getPagesize())) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=showCallInfo", 10, 1, form1.getTotalcount());
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/phoneCost.do?method=showCallInfo", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		request.setAttribute("calllist", calllist);
		return mapping.findForward("showCallInfoDetail");
	}
	
	/**
	 * luq 查看指定账单的通话信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Apr 10, 2012 
	 * Time:1:12:26 PM
	 */
	public ActionForward exportPhoneInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PhoneCostForm form1 = (PhoneCostForm)form;
		String billid = request.getParameter("billid");
		form1.setId(Integer.valueOf(billid));
		try {
			List calllist = phoneCostService.getCallInfoList(form1,false);
			// 表头
			String[] cellHeader = Constant.EXCEL_PHONECOSTINFO_BILL_DETAIL;
			String[] cellValue = Constant.EXCEL_PHONECOSTINFO_BILL_VALUE;
			// 定义文件名
			String sheetName = "电话费用信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookObject(calllist, sheetName, cellHeader, cellValue, ECallInfo.class);
			//HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new CompactClient());
			response.setBufferSize(10000 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, "电话费用信息列表" + GlobalMethod.getTime2() + ".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出在住客户电话费用信息列表excel出错，详细信息：" + e);
			e.printStackTrace();
		}
		return null;
	}
}
