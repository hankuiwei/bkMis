package com.zc13.bkmis.action;

import java.util.HashMap;
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

import com.zc13.bkmis.form.InvoiceForm;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.service.ICBillService;
import com.zc13.bkmis.service.IInvoiceService;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.service.ISysParamManagerService;
import com.zc13.msmis.service.IUserManagerService;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 发票管理Action
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:19:39 AM
 */
public class InvoiceAction  extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private IInvoiceService invoiceService;
	private IUserManagerService iUserManagerService;
	ISysParamManagerService iSysParamManagerService = null;
	private ICBillService icBillService;
	
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		invoiceService = (IInvoiceService) super.getBean("invoiceService");
		iUserManagerService = (IUserManagerService)super.getBean("IUserManagerService");
		iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		icBillService = (ICBillService) super.getBean("icBillService");
	}

	/**
	 * 获取收据列表信息
	 */
	public ActionForward getReceiptList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		//收款员列表
		List usersList = null;
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer range = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userRoleRange").toString()),"0"));
		UserForm uf = new UserForm();
		uf.setRange(0);
		invoiceService.getReceiptList(form1,false);
		usersList = iUserManagerService.findUser(uf,false);
		//添加分页信息
		/*String htmlPagination = "&nbsp;";
		if (null == form1.getReceiptList() || form1.getReceiptList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/invoice.do?method=getReceiptList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/invoice.do?method=getReceiptList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);*/
		request.setAttribute("usersList", usersList);
		return mapping.findForward("receiptList");
	}
	
	/**
	 * 转向开发票页面
	 */
	public ActionForward toOpenInvoice(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		
		invoiceService.getReceiptList(form1,false);
		
		SysCode sysCode = new SysCode();
		sysCode.setCodeType(Contants.INVOICE_CONTENT);
		List<SysCode> syscodes =  iSysParamManagerService.getSysCode(sysCode);
		
		request.setAttribute("invoiceContents", syscodes);
		return mapping.findForward("openInvoice");
	}
	
	/**
	 * 开发票
	 * @throws Exception 
	 */
	public ActionForward openInvoice(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		form1.setUserId(userId);
		form1.setUserName(userName);
		form1.setLpId(lpId);
		try{
			invoiceService.openInvoice(form1);
			response.getWriter().print("操作成功！");
		}catch(Exception e){
			response.getWriter().print("操作失败！");
			e.printStackTrace();
			logger.error("开发票操作失败invoiceAction.openInvoice()详细信息：", e);
		}
		return null;
	}
	
	/**
	 * 根据id获取收据明细
	 */
	public ActionForward getReceiptById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		InvoiceForm form1 = (InvoiceForm)form;
		try{
			invoiceService.getReceiptList(form1,false);
			Map map = new HashMap();
			if(form1.getReceiptList()!=null&&form1.getReceiptList().size()>0){
				map = (Map)form1.getReceiptList().get(0);
			}
			request.setAttribute("receiptDetail", map);
			//根据收据id获取相关联的发票信息
			invoiceService.getInvoiceListByChargeId(form1);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据id获取收据明细失败invoiceAction.getReceiptById()详细信息：", e);
		}
		return mapping.findForward("showReceiptDetail");
	}
	
	/**
	 * 获取发票列表信息
	 * @throws Exception 
	 */
	public ActionForward getInvoiceList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		//收款员列表
		List usersList = null;
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer range = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userRoleRange").toString()),"0"));
		UserForm uf = new UserForm();
		uf.setRange(0);
		invoiceService.getInvoiceList(form1,true);
		usersList = iUserManagerService.findUser(uf,false);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getInvoiceList() || form1.getInvoiceList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/invoice.do?method=getInvoiceList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/invoice.do?method=getInvoiceList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		
		List<CItems> itemList = icBillService.getItemList();// 收费项目
		
		//发票内容
		SysCode sysCode = new SysCode();
		sysCode.setCodeType(Contants.INVOICE_CONTENT);
		List<SysCode> syscodes =  iSysParamManagerService.getSysCode(sysCode);
		
		//项目明细、发票内容汇总
		List summaryContent = invoiceService.summaryInvoiceContent(form1);
		List summaryItem = invoiceService.summaryInvoiceItem(form1);
		
		request.setAttribute("summaryContent", summaryContent);
		request.setAttribute("summaryItem", summaryItem);
		request.setAttribute("itemList", itemList);
		request.setAttribute("invoiceContents", syscodes);
		request.setAttribute("pagination", htmlPagination);
		request.setAttribute("usersList", usersList);
		return mapping.findForward("invoiceList");
	}
	
	/**
	 * 根据id获取发票明细
	 */
	public ActionForward getInvoiceById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		InvoiceForm form1 = (InvoiceForm)form;
		try{
			invoiceService.getInvoiceList(form1,false);
			Map map = new HashMap();
			if(form1.getInvoiceList()!=null&&form1.getInvoiceList().size()>0){
				map = (Map)form1.getInvoiceList().get(0);
			}
			request.setAttribute("invoiceDetail", map);
			//根据收据id获取相关联的发票信息
			invoiceService.getInvoiceListByInvoiceId(form1);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据id获取收据明细失败invoiceAction.getInvoiceById()详细信息：", e);
		}
		return mapping.findForward("showInvoiceDetail");
	}
	
	/**
	 * 打印收据列表信息
	 */
	public ActionForward printReceiptList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		invoiceService.getReceiptList(form1,false);
		return mapping.findForward("receiptList_print");
	}
	
	/**
	 * 打印发票列表信息
	 */
	public ActionForward printInvoiceList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		invoiceService.getInvoiceList(form1,false);
		return mapping.findForward("invoiceList_print");
	}
	
	/**
	 * 导出收据列表信息
	 */
	public ActionForward exportReceiptList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		try {
			invoiceService.getReceiptList(form1,false);
			//表头
			String[] cellHeader = Constant.EXCEL_RECEIPT_DETAIL;
			String[] cellValue = Constant.EXCEL_RECEIPT_VALUE;
			//定义文件名
			String sheetName = "收据信息列表";
			//HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(form1.getReceiptList(),sheetName,cellHeader,cellValue);
			//修改导出excel
			HSSFWorkbook workbook = ExplortExcel.creatReceiptWorkbookMap(form1.getReceiptList(),sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "收据信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出收据excel出错，详细信息："+e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出发票列表信息
	 */
	public ActionForward exportInvoiceList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InvoiceForm form1 = (InvoiceForm)form;
		this.setLogParam(request);
		form1.setLogParam(logParam);
		
		try {
			invoiceService.getInvoiceList(form1,false);
			//表头
			String[] cellHeader = Constant.EXCEL_INVOICE_DETAIL;
			String[] cellValue = Constant.EXCEL_INVOICE_VALUE;
			//定义文件名
			String sheetName = "发票信息列表";
			HSSFWorkbook workbook = ExplortExcel.createInvoiceWorkbookMap(form1.getInvoiceList(),sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "发票信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出发票excel出错，详细信息："+e);
			e.printStackTrace();
		}
		return null;
	}
	
}
