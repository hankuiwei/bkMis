package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
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

import com.zc13.bkmis.form.FinancialReportForm;
import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;
import com.zc13.bkmis.service.IFinancialReportService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 财务报表维护
 * @author Administrator
 * @Date 2013-5-13
 * @Time 下午9:11:32
 */
public class FinancialReportAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialReportService financialReportService;

	public IFinancialReportService getFinancialReportService() {
		return financialReportService;
	}

	public void setFinancialReportService(IFinancialReportService financialReportService) {
		this.financialReportService = financialReportService;
	}
	
	/** 从spring容器里得到ifinancialBudgetService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		financialReportService = (IFinancialReportService)getBean("financialReportService");
	}
	
	/**
	 * 查询经营成本
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:41:09
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		
		List<OperateCost> operateCostList = new ArrayList<OperateCost>();
		try{
			operateCostList = financialReportService.queryOperateCostList(financialReportForm, true);
			//取总条数
			int totalcount = financialReportService.queryCounttotal(financialReportForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == operateCostList || operateCostList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(), "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(), "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("财务报表经营成本查询失败!FinancialReportAction.getList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("operateCostList", operateCostList);
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("list");
	}
	
	/**
	 * 添加或编辑页面(经营成本)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:42:34
	 */
	public ActionForward addOrEditPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportForm.getOperateCost().setYear(GlobalMethod.getYear());
		financialReportForm.getOperateCost().setMonth(GlobalMethod.getMonth());
		if(financialReportForm.getOperateCost().getId() != null){
			financialReportForm.setOperateCost(financialReportService.queryOperateCostById(financialReportForm));
		}
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("addOrEdit");
	}
	
	/**
	 * 保存或是更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:48:32
	 */
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportService.SaveOrUpdate(financialReportForm);
		return null;
	}
	
	public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		String ids = GlobalMethod.ObjToStr(financialReportForm.getIds());
		try {
			String[] strings = ids.split(",");
			for (int i = 0; i < strings.length; i++) {
				financialReportService.delById(strings[i]);
			}
			response.sendRedirect("financialReport.do?method=getList");
		} catch (Exception e) {
			logger.error("删除实际财务失败!iFinancialReportService.delById(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("删除实际财务失败!iFinancialReportService.delById(form2)!", e);
		}
		return null;
	}
	
	/**
	 * 查询经营收入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:41:09
	 */
	public ActionForward getIncomeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		
		List<OperateIncome> operateIncomeList = new ArrayList<OperateIncome>();
		try{
			operateIncomeList = financialReportService.queryOperateIncomeList(financialReportForm, true);
			//取总条数
			int totalcount = financialReportService.queryIncomeCounttotal(financialReportForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == operateIncomeList || operateIncomeList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getIncomeList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getIncomeList" , Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(), "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(), "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("财务报表经营收入查询失败!FinancialReportAction.getIncomeList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("operateIncomeList", operateIncomeList);
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("incomeList");
	}
	
	/**
	 * 添加或编辑页面(经营收入)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:42:34
	 */
	public ActionForward addOrEditIncomePage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportForm.getOperateIncome().setYear(GlobalMethod.getYear());
		financialReportForm.getOperateIncome().setMonth(GlobalMethod.getMonth());
		if(financialReportForm.getOperateIncome().getId() != null){
			financialReportForm.setOperateIncome(financialReportService.queryOperateIncomeById(financialReportForm));
		}
		String date = financialReportForm.getOperateIncome().getYear()+"-"+financialReportForm.getOperateIncome().getMonth();
		financialReportForm.getOperateIncome().setRentFee(financialReportService.queryRentByTime(date, Contants.ITEM_RENT));
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("addOrEditIncome");
	}
	
	/**
	 * 获取房租
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-19 
	 * Time:下午8:47:14
	 */
	public ActionForward queryRent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		double money = financialReportService.queryRentByTime(year+"-"+month, Contants.ITEM_RENT);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(money);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
	
	/**
	 * 保存或是更新 经营收入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:48:32
	 */
	public ActionForward saveOrUpdateIncome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportService.SaveOrUpdateIncome(financialReportForm);
		return null;
	}
	
	/**
	 * 删除经营成本 收入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-19 
	 * Time:下午7:24:47
	 */
	public ActionForward delIncomeById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		String ids = GlobalMethod.ObjToStr(financialReportForm.getIds());
		try {
			String[] strings = ids.split(",");
			for (int i = 0; i < strings.length; i++) {
				financialReportService.delIncomeById(strings[i]);
			}
			response.sendRedirect("financialReport.do?method=getList");
		} catch (Exception e) {
			logger.error("删除经营收入失败!iFinancialReportService.delIncomeById(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("删除经营收入失败!iFinancialReportService.delIncomeById(form2)!", e);
		}
		return null;
	}
	
	/**
	 * 查询项目费用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:41:09
	 */
	public ActionForward getProjectCostList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		
		List<ProjectCosts> projectCostList = new ArrayList<ProjectCosts>();
		try{
			projectCostList = financialReportService.queryProjectCosts(financialReportForm, true);
			//取总条数
			int totalcount = financialReportService.queryProjectsCounttotal(financialReportForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == projectCostList || projectCostList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getProjectCostList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialReport.do?method=getProjectCostList" , Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(), "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(), "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("财务报表项目查询失败!FinancialReportAction.getIncomeList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("projectCostList", projectCostList);
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("projectList");
	}
	
	/**
	 * 添加或编辑页面(项目费用)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:42:34
	 */
	public ActionForward addOrEditProjectPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportForm.getProjectCosts().setYear(GlobalMethod.getYear());
		financialReportForm.getProjectCosts().setMonth(GlobalMethod.getMonth());
		if(financialReportForm.getProjectCosts().getId() != null){
			financialReportForm.setProjectCosts(financialReportService.queryProjectCostsById(financialReportForm));
		}
		request.setAttribute("form", financialReportForm);
		return mapping.findForward("addOrEditProject");
	}
	
	/**
	 * 保存或是更新 项目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:48:32
	 */
	public ActionForward saveOrUpdateProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		financialReportService.SaveOrUpdateProjectCosts(financialReportForm);
		return null;
	}
	
	/**
	 * 删除项目维护
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-19 
	 * Time:下午7:24:20
	 */
	public ActionForward delProjectById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		String ids = GlobalMethod.ObjToStr(financialReportForm.getIds());
		try {
			String[] strings = ids.split(",");
			for (int i = 0; i < strings.length; i++) {
				financialReportService.delProjectCostsById(strings[i]);
			}
			response.sendRedirect("financialReport.do?method=getList");
		} catch (Exception e) {
			logger.error("删除实际财务失败!iFinancialReportService.delIncomeById(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("删除实际财务失败!iFinancialReportService.delIncomeById(form2)!", e);
		}
		return null;
	}
	
	/**
	 * 查询界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:42:34
	 */
	public ActionForward queryShow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		FinancialReportForm financialReportForm = (FinancialReportForm)form;
		if("".equals(GlobalMethod.NullToSpace(financialReportForm.getYear()))){
			financialReportForm.setYear(GlobalMethod.getYear());
		}
		if("".equals(GlobalMethod.NullToSpace(financialReportForm.getMonth()))){
			financialReportForm.setMonth(GlobalMethod.getMonth());
		}
		
		OperateCost byCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		OperateCost nljCost = financialReportService.queryCostLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.SJ);
		OperateCost ysjeCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
		
		OperateIncome income = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		OperateIncome nljIncome = financialReportService.queryIncomeLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.SJ);
		OperateIncome ysjeIncome = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
		
		/*OperateCost byCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.BY);
		OperateCost nljCost = financialReportService.queryCostLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.BY);
		OperateCost ysjeCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YSJE);
		
		OperateIncome income = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.BY);
		OperateIncome nljIncome = financialReportService.queryIncomeLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.BY);
		OperateIncome ysjeIncome = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YSJE);*/
		
		ProjectCosts glysPeoject = financialReportService.queryProjectByTime(Contants.GL_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
		ProjectCosts gcysPeoject = financialReportService.queryProjectByTime(Contants.GC_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
		ProjectCosts zsysPeoject = financialReportService.queryProjectByTime(Contants.ZS_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
		
		ProjectCosts glsjPeoject = financialReportService.queryProjectByTime(Contants.GL_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		ProjectCosts gcsjPeoject = financialReportService.queryProjectByTime(Contants.GC_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		ProjectCosts zssjPeoject = financialReportService.queryProjectByTime(Contants.ZS_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		
		ProjectCosts nljProject = financialReportService.queryProjectLj(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
		
		request.setAttribute("form", financialReportForm);
		request.setAttribute("byCost", byCost);
		request.setAttribute("nljCost", nljCost);
		request.setAttribute("ysjeCost", ysjeCost);
		request.setAttribute("income", income);
		request.setAttribute("nljIncome", nljIncome);
		request.setAttribute("ysjeIncome", ysjeIncome);
		request.setAttribute("glysPeoject", glysPeoject);
		request.setAttribute("gcysPeoject", gcysPeoject);
		request.setAttribute("zsysPeoject", zsysPeoject);
		request.setAttribute("glsjPeoject", glsjPeoject);
		request.setAttribute("gcsjPeoject", gcsjPeoject);
		request.setAttribute("zssjPeoject", zssjPeoject);
		request.setAttribute("nljProject", nljProject);
		
		return mapping.findForward("queryShow");
	}
	
	/***
	 * 导出excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:2013-5-30 
	 * Time:下午9:36:17
	 */
	public ActionForward exportExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			//获取导出数据
			FinancialReportForm financialReportForm = (FinancialReportForm)form;
			if("".equals(GlobalMethod.NullToSpace(financialReportForm.getYear()))){
				financialReportForm.setYear(GlobalMethod.getYear());
			}
			if("".equals(GlobalMethod.NullToSpace(financialReportForm.getMonth()))){
				financialReportForm.setMonth(GlobalMethod.getMonth());
			}
			
			/*OperateCost byCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.BY);
			OperateCost nljCost = financialReportService.queryCostLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.BY);
			OperateCost ysjeCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YSJE);
			
			OperateIncome income = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.BY);
			OperateIncome nljIncome = financialReportService.queryIncomeLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.BY);
			OperateIncome ysjeIncome = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YSJE);*/
			
			OperateCost byCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			OperateCost nljCost = financialReportService.queryCostLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.SJ);
			OperateCost ysjeCost = financialReportService.queryCostByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
			
			OperateIncome income = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			OperateIncome nljIncome = financialReportService.queryIncomeLj(financialReportForm.getYear(), financialReportForm.getMonth(),Contants.SJ);
			OperateIncome ysjeIncome = financialReportService.queryIncomeByTime(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
			
			ProjectCosts glysPeoject = financialReportService.queryProjectByTime(Contants.GL_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
			ProjectCosts gcysPeoject = financialReportService.queryProjectByTime(Contants.GC_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
			ProjectCosts zsysPeoject = financialReportService.queryProjectByTime(Contants.ZS_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.YS);
			
			ProjectCosts glsjPeoject = financialReportService.queryProjectByTime(Contants.GL_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			ProjectCosts gcsjPeoject = financialReportService.queryProjectByTime(Contants.GC_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			ProjectCosts zssjPeoject = financialReportService.queryProjectByTime(Contants.ZS_DEPT, financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			
			ProjectCosts nljProject = financialReportService.queryProjectLj(financialReportForm.getYear(), financialReportForm.getMonth(), Contants.SJ);
			//表头
			String[] cellValue1 = Constant.EXCEL_FINANCIAL_REPORT_PROMONEY;
			String[] cellValue2 = Constant.EXCEL_FINANCIAL_REPORT_OPERATEMONEY;
			String[] proTitle = Constant.EXCEL_FINANCIAL_REPORT_PROJECT;
			List<Map<String,String>> proRs = this.getProjectMoneys(glysPeoject, gcysPeoject, zsysPeoject, glsjPeoject, gcsjPeoject, zssjPeoject, nljProject);
			List<Map<String,String>> operateRs = this.getOperateMoneys(byCost, nljCost, ysjeCost, income, nljIncome, ysjeIncome);
			//定义文件名
			String sheetName = financialReportForm.getYear()+"年"+financialReportForm.getMonth()+"月实际费用与预算费用比较表";
			HSSFWorkbook workbook = ExplortExcel.createFincncialReport(proRs,operateRs,sheetName,cellValue1,cellValue2,proTitle);
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, financialReportForm.getYear()+"年"+financialReportForm.getMonth()+"月实际费用与预算费用比较表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Map<String,String>> getProjectMoneys(ProjectCosts glysPeoject,ProjectCosts gcysPeoject,ProjectCosts zsysPeoject,ProjectCosts glsjPeoject,ProjectCosts gcsjPeoject,ProjectCosts zssjPeoject,ProjectCosts nljProject){
		
		List<Map<String,String>> rs = new ArrayList<Map<String,String>>();
		Map<String,String> copeWageMap = new HashMap<String,String>();
		copeWageMap.put("proName", "应付工资");
		copeWageMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getCopeWage(),2)));
		copeWageMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getCopeWage(),2)));
		copeWageMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getCopeWage(),2)));
		copeWageMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getCopeWage()+gcysPeoject.getCopeWage()+zsysPeoject.getCopeWage(),2)));
		copeWageMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getCopeWage(),2)));
		copeWageMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getCopeWage(),2)));
		copeWageMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getCopeWage(),2)));
		copeWageMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getCopeWage()+gcsjPeoject.getCopeWage()+zssjPeoject.getCopeWage(),2)));
		copeWageMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getCopeWage(),2)));
		if((glysPeoject.getCopeWage()+gcysPeoject.getCopeWage()+zsysPeoject.getCopeWage()) != 0){
			copeWageMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getCopeWage()/(glysPeoject.getCopeWage()+gcysPeoject.getCopeWage()+zsysPeoject.getCopeWage())*100,2))+"%");
		}else{
			copeWageMap.put("zqn", "");
		}
		rs.add(copeWageMap);
		
		Map<String,String> copeWelfareMap = new HashMap<String,String>();
		copeWelfareMap.put("proName", "应付福利费(14%)");
		copeWelfareMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getCopeWelfare()+gcysPeoject.getCopeWelfare()+zsysPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getCopeWelfare()+gcsjPeoject.getCopeWelfare()+zssjPeoject.getCopeWelfare(),2)));
		copeWelfareMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getCopeWage(),2)));
		if((glysPeoject.getCopeWelfare()+gcysPeoject.getCopeWelfare()+zsysPeoject.getCopeWelfare()) != 0){
			copeWelfareMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getCopeWelfare()/(glysPeoject.getCopeWelfare()+gcysPeoject.getCopeWelfare()+zsysPeoject.getCopeWelfare())*100,2))+"%");
		}else{
			copeWelfareMap.put("zqn", "");
		}
		rs.add(copeWelfareMap);
		
		Map<String,String> unionFundsMap = new HashMap<String,String>();
		unionFundsMap.put("proName", "工会经费(2%)");
		unionFundsMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getUnionFunds(),2)));
		unionFundsMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getUnionFunds(),2)));
		unionFundsMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getUnionFunds(),2)));
		unionFundsMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getUnionFunds()+gcysPeoject.getUnionFunds()+zsysPeoject.getUnionFunds(),2)));
		unionFundsMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getUnionFunds(),2)));
		unionFundsMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getUnionFunds(),2)));
		unionFundsMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getUnionFunds(),2)));
		unionFundsMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getUnionFunds()+gcsjPeoject.getUnionFunds()+zssjPeoject.getUnionFunds(),2)));
		unionFundsMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getUnionFunds(),2)));
		if((glysPeoject.getUnionFunds()+gcysPeoject.getUnionFunds()+zsysPeoject.getUnionFunds()) != 0){
			unionFundsMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getUnionFunds()/(glysPeoject.getUnionFunds()+gcysPeoject.getUnionFunds()+zsysPeoject.getUnionFunds())*100,2))+"%");
		}else{
			unionFundsMap.put("zqn", "");
		}
		rs.add(unionFundsMap);
		
		Map<String,String> educationFundsMap = new HashMap<String,String>();
		educationFundsMap.put("proName", "教育经费(2.5%)");
		educationFundsMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getEducationFunds(),2)));
		educationFundsMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getEducationFunds(),2)));
		educationFundsMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getEducationFunds(),2)));
		educationFundsMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getEducationFunds()+gcysPeoject.getEducationFunds()+zsysPeoject.getEducationFunds(),2)));
		educationFundsMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getEducationFunds(),2)));
		educationFundsMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getEducationFunds(),2)));
		educationFundsMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getEducationFunds(),2)));
		educationFundsMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getEducationFunds()+gcsjPeoject.getEducationFunds()+zssjPeoject.getEducationFunds(),2)));
		educationFundsMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getEducationFunds(),2)));
		if((glysPeoject.getEducationFunds()+gcysPeoject.getEducationFunds()+zsysPeoject.getEducationFunds()) != 0){
			educationFundsMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getEducationFunds()/(glysPeoject.getEducationFunds()+gcysPeoject.getEducationFunds()+zsysPeoject.getEducationFunds())*100,2))+"%");
		}else{
			educationFundsMap.put("zqn", "");
		}
		rs.add(educationFundsMap);
		
		Map<String,String> supplementaryMedicalMap = new HashMap<String,String>();
		supplementaryMedicalMap.put("proName", "补充医疗(4%)");
		supplementaryMedicalMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getSupplementaryMedical()+gcysPeoject.getSupplementaryMedical()+zsysPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getSupplementaryMedical()+gcsjPeoject.getSupplementaryMedical()+zssjPeoject.getSupplementaryMedical(),2)));
		supplementaryMedicalMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getSupplementaryMedical(),2)));
		if((glysPeoject.getSupplementaryMedical()+gcysPeoject.getSupplementaryMedical()+zsysPeoject.getSupplementaryMedical()) != 0){
			supplementaryMedicalMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getSupplementaryMedical()/(glysPeoject.getSupplementaryMedical()+gcysPeoject.getSupplementaryMedical()+zsysPeoject.getSupplementaryMedical())*100,2))+"%");
		}else{
			supplementaryMedicalMap.put("zqn", "");
		}
		rs.add(supplementaryMedicalMap);
		
		Map<String,String> enterpriseAnnuityMap = new HashMap<String,String>();
		enterpriseAnnuityMap.put("proName", "企业年金(4%)");
		enterpriseAnnuityMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getEnterpriseAnnuity()+gcysPeoject.getEnterpriseAnnuity()+zsysPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getEnterpriseAnnuity()+gcsjPeoject.getEnterpriseAnnuity()+zssjPeoject.getEnterpriseAnnuity(),2)));
		enterpriseAnnuityMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getEnterpriseAnnuity(),2)));
		if((glysPeoject.getEnterpriseAnnuity()+gcysPeoject.getEnterpriseAnnuity()+zsysPeoject.getEnterpriseAnnuity()) != 0){
			enterpriseAnnuityMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getEnterpriseAnnuity()/(glysPeoject.getEnterpriseAnnuity()+gcysPeoject.getEnterpriseAnnuity()+zsysPeoject.getEnterpriseAnnuity())*100,2))+"%");
		}else{
			enterpriseAnnuityMap.put("zqn", "");
		}
		rs.add(enterpriseAnnuityMap);
		
		Map<String,String> pensionInsuranceMap = new HashMap<String,String>();
		pensionInsuranceMap.put("proName", "基本养老保险");
		pensionInsuranceMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getPensionInsurance()+gcysPeoject.getPensionInsurance()+zsysPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getPensionInsurance()+gcsjPeoject.getPensionInsurance()+zssjPeoject.getPensionInsurance(),2)));
		pensionInsuranceMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getPensionInsurance(),2)));
		if((glysPeoject.getPensionInsurance()+gcysPeoject.getPensionInsurance()+zsysPeoject.getPensionInsurance()) != 0){
			pensionInsuranceMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getPensionInsurance()/(glysPeoject.getPensionInsurance()+gcysPeoject.getPensionInsurance()+zsysPeoject.getPensionInsurance())*100,2))+"%");
		}else{
			pensionInsuranceMap.put("zqn", "");
		}
		rs.add(pensionInsuranceMap);
		
		Map<String,String> healthInsuranceMap = new HashMap<String,String>();
		healthInsuranceMap.put("proName", "基本医疗保险");
		healthInsuranceMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getHealthInsurance()+gcysPeoject.getHealthInsurance()+zsysPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getHealthInsurance()+gcsjPeoject.getHealthInsurance()+zssjPeoject.getHealthInsurance(),2)));
		healthInsuranceMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getHealthInsurance(),2)));
		if((glysPeoject.getHealthInsurance()+gcysPeoject.getHealthInsurance()+zsysPeoject.getHealthInsurance()) != 0){
			healthInsuranceMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getHealthInsurance()/(glysPeoject.getHealthInsurance()+gcysPeoject.getHealthInsurance()+zsysPeoject.getHealthInsurance())*100,2))+"%");
		}else{
			healthInsuranceMap.put("zqn", "");
		}
		rs.add(healthInsuranceMap);
		
		Map<String,String> housingFundMap = new HashMap<String,String>();
		housingFundMap.put("proName", "住房公积金");
		housingFundMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getHousingFund(),2)));
		housingFundMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getHousingFund(),2)));
		housingFundMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getHousingFund(),2)));
		housingFundMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getHousingFund()+gcysPeoject.getHousingFund()+zsysPeoject.getHousingFund(),2)));
		housingFundMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getHousingFund(),2)));
		housingFundMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getHousingFund(),2)));
		housingFundMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getHousingFund(),2)));
		housingFundMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getHousingFund()+gcsjPeoject.getHousingFund()+zssjPeoject.getHousingFund(),2)));
		housingFundMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getHousingFund(),2)));
		if((glysPeoject.getHousingFund()+gcysPeoject.getHousingFund()+zsysPeoject.getHousingFund()) != 0){
			housingFundMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getHousingFund()/(glysPeoject.getHousingFund()+gcysPeoject.getHousingFund()+zsysPeoject.getHousingFund())*100,2))+"%");
		}else{
			housingFundMap.put("zqn", "");
		}
		rs.add(housingFundMap);
		
		Map<String,String> unemployInsuranceMap = new HashMap<String,String>();
		unemployInsuranceMap.put("proName", "失业保险");
		unemployInsuranceMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getUnemployInsurance()+gcysPeoject.getUnemployInsurance()+zsysPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getUnemployInsurance()+gcsjPeoject.getUnemployInsurance()+zssjPeoject.getUnemployInsurance(),2)));
		unemployInsuranceMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getUnemployInsurance(),2)));
		if((glysPeoject.getUnemployInsurance()+gcysPeoject.getUnemployInsurance()+zsysPeoject.getUnemployInsurance()) != 0){
			unemployInsuranceMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getUnemployInsurance()/(glysPeoject.getUnemployInsurance()+gcysPeoject.getUnemployInsurance()+zsysPeoject.getUnemployInsurance())*100,2))+"%");
		}else{
			unemployInsuranceMap.put("zqn", "");
		}
		rs.add(unemployInsuranceMap);
		
		Map<String,String> injuryInsuranceMap = new HashMap<String,String>();
		injuryInsuranceMap.put("proName", "工伤保险");
		injuryInsuranceMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getInjuryInsurance()+gcysPeoject.getInjuryInsurance()+zsysPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getInjuryInsurance()+gcsjPeoject.getInjuryInsurance()+zssjPeoject.getInjuryInsurance(),2)));
		injuryInsuranceMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getInjuryInsurance(),2)));
		if((glysPeoject.getInjuryInsurance()+gcysPeoject.getInjuryInsurance()+zsysPeoject.getInjuryInsurance()) != 0){
			injuryInsuranceMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getInjuryInsurance()/(glysPeoject.getInjuryInsurance()+gcysPeoject.getInjuryInsurance()+zsysPeoject.getInjuryInsurance())*100,2))+"%");
		}else{
			injuryInsuranceMap.put("zqn", "");
		}
		rs.add(injuryInsuranceMap);
		
		Map<String,String> maternityInsuranceMap = new HashMap<String,String>();
		maternityInsuranceMap.put("proName", "生育保险");
		maternityInsuranceMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getMaternityInsurance()+gcysPeoject.getMaternityInsurance()+zsysPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getMaternityInsurance()+gcsjPeoject.getMaternityInsurance()+zssjPeoject.getMaternityInsurance(),2)));
		maternityInsuranceMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getMaternityInsurance(),2)));
		if((glysPeoject.getMaternityInsurance()+gcysPeoject.getMaternityInsurance()+zsysPeoject.getMaternityInsurance()) != 0){
			maternityInsuranceMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getMaternityInsurance()/(glysPeoject.getMaternityInsurance()+gcysPeoject.getMaternityInsurance()+zsysPeoject.getMaternityInsurance())*100,2))+"%");
		}else{
			maternityInsuranceMap.put("zqn", "");
		}
		rs.add(maternityInsuranceMap);
		
		Map<String,String> trafficFeeMap = new HashMap<String,String>();
		trafficFeeMap.put("proName", "交通费");
		trafficFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getTrafficFee()+gcysPeoject.getTrafficFee()+zsysPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getTrafficFee()+gcsjPeoject.getTrafficFee()+zssjPeoject.getTrafficFee(),2)));
		trafficFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getTrafficFee(),2)));
		if((glysPeoject.getTrafficFee()+gcysPeoject.getTrafficFee()+zsysPeoject.getTrafficFee()) != 0){
			trafficFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getTrafficFee()/(glysPeoject.getTrafficFee()+gcysPeoject.getTrafficFee()+zsysPeoject.getTrafficFee())*100,2))+"%");
		}else{
			trafficFeeMap.put("zqn", "");
		}
		rs.add(trafficFeeMap);
		
		Map<String,String> phoneFeeMap = new HashMap<String,String>();
		phoneFeeMap.put("proName", "电话费");
		phoneFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getPhoneFee()+gcysPeoject.getPhoneFee()+zsysPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getPhoneFee()+gcsjPeoject.getPhoneFee()+zssjPeoject.getPhoneFee(),2)));
		phoneFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getPhoneFee(),2)));
		if((glysPeoject.getPhoneFee()+gcysPeoject.getPhoneFee()+zsysPeoject.getPhoneFee()) != 0){
			phoneFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getPhoneFee()/(glysPeoject.getPhoneFee()+gcysPeoject.getPhoneFee()+zsysPeoject.getPhoneFee())*100,2))+"%");
		}else{
			phoneFeeMap.put("zqn", "");
		}
		rs.add(phoneFeeMap);
		
		Map<String,String> depreciationFeeMap = new HashMap<String,String>();
		depreciationFeeMap.put("proName", "折旧费");
		depreciationFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getDepreciationFee()+gcysPeoject.getDepreciationFee()+zsysPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getDepreciationFee()+gcsjPeoject.getDepreciationFee()+zssjPeoject.getDepreciationFee(),2)));
		depreciationFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getDepreciationFee(),2)));
		if((glysPeoject.getDepreciationFee()+gcysPeoject.getDepreciationFee()+zsysPeoject.getDepreciationFee()) != 0){
			depreciationFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getDepreciationFee()/(glysPeoject.getDepreciationFee()+gcysPeoject.getDepreciationFee()+zsysPeoject.getDepreciationFee())*100,2))+"%");
		}else{
			depreciationFeeMap.put("zqn", "");
		}
		rs.add(depreciationFeeMap);
		
		Map<String,String> hospitalityFeeMap = new HashMap<String,String>();
		hospitalityFeeMap.put("proName", "业务招待费");
		hospitalityFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getHospitalityFee()+gcysPeoject.getHospitalityFee()+zsysPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getHospitalityFee()+gcsjPeoject.getHospitalityFee()+zssjPeoject.getHospitalityFee(),2)));
		hospitalityFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getHospitalityFee(),2)));
		if((glysPeoject.getHospitalityFee()+gcysPeoject.getHospitalityFee()+zsysPeoject.getHospitalityFee()) != 0){
			hospitalityFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getHospitalityFee()/(glysPeoject.getHospitalityFee()+gcysPeoject.getHospitalityFee()+zsysPeoject.getHospitalityFee())*100,2))+"%");
		}else{
			hospitalityFeeMap.put("zqn", "");
		}
		rs.add(hospitalityFeeMap);
		
		Map<String,String> travelFeeMap = new HashMap<String,String>();
		travelFeeMap.put("proName", "差旅费");
		travelFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getTravelFee(),2)));
		travelFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getTravelFee(),2)));
		travelFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getTravelFee(),2)));
		travelFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getTravelFee()+gcysPeoject.getTravelFee()+zsysPeoject.getTravelFee(),2)));
		travelFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getTravelFee(),2)));
		travelFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getTravelFee(),2)));
		travelFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getTravelFee(),2)));
		travelFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getTravelFee()+gcsjPeoject.getTravelFee()+zssjPeoject.getTravelFee(),2)));
		travelFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getTravelFee(),2)));
		if((glysPeoject.getTravelFee()+gcysPeoject.getTravelFee()+zsysPeoject.getTravelFee()) != 0){
			travelFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getTravelFee()/(glysPeoject.getTravelFee()+gcysPeoject.getTravelFee()+zsysPeoject.getTravelFee())*100,2))+"%");
		}else{
			travelFeeMap.put("zqn", "");
		}
		rs.add(travelFeeMap);
		
		Map<String,String> officeFeeMap = new HashMap<String,String>();
		officeFeeMap.put("proName", "办公费");
		officeFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getOfficeFee(),2)));
		officeFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getOfficeFee(),2)));
		officeFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getOfficeFee(),2)));
		officeFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getOfficeFee()+gcysPeoject.getOfficeFee()+zsysPeoject.getOfficeFee(),2)));
		officeFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getOfficeFee(),2)));
		officeFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getOfficeFee(),2)));
		officeFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getOfficeFee(),2)));
		officeFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getOfficeFee()+gcsjPeoject.getOfficeFee()+zssjPeoject.getOfficeFee(),2)));
		officeFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getOfficeFee(),2)));
		if((glysPeoject.getOfficeFee()+gcysPeoject.getOfficeFee()+zsysPeoject.getOfficeFee()) != 0){
			officeFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getOfficeFee()/(glysPeoject.getOfficeFee()+gcysPeoject.getOfficeFee()+zsysPeoject.getOfficeFee())*100,2))+"%");
		}else{
			officeFeeMap.put("zqn", "");
		}
		rs.add(officeFeeMap);
		
		Map<String,String> auditFeeMap = new HashMap<String,String>();
		auditFeeMap.put("proName", "审计费、律师费");
		auditFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getAuditFee(),2)));
		auditFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getAuditFee(),2)));
		auditFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getAuditFee(),2)));
		auditFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getAuditFee()+gcysPeoject.getAuditFee()+zsysPeoject.getAuditFee(),2)));
		auditFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getAuditFee(),2)));
		auditFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getAuditFee(),2)));
		auditFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getAuditFee(),2)));
		auditFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getAuditFee()+gcsjPeoject.getAuditFee()+zssjPeoject.getAuditFee(),2)));
		auditFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getAuditFee(),2)));
		if((glysPeoject.getAuditFee()+gcysPeoject.getAuditFee()+zsysPeoject.getAuditFee()) != 0){
			auditFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getAuditFee()/(glysPeoject.getAuditFee()+gcysPeoject.getAuditFee()+zsysPeoject.getAuditFee())*100,2))+"%");
		}else{
			auditFeeMap.put("zqn", "");
		}
		rs.add(auditFeeMap);
		
		Map<String,String> boardFeeMap = new HashMap<String,String>();
		boardFeeMap.put("proName", "董事会费");
		boardFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getBoardFee(),2)));
		boardFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getBoardFee(),2)));
		boardFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getBoardFee(),2)));
		boardFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getBoardFee()+gcysPeoject.getBoardFee()+zsysPeoject.getBoardFee(),2)));
		boardFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getBoardFee(),2)));
		boardFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getBoardFee(),2)));
		boardFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getBoardFee(),2)));
		boardFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getBoardFee()+gcsjPeoject.getBoardFee()+zssjPeoject.getBoardFee(),2)));
		boardFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getBoardFee(),2)));
		if((glysPeoject.getBoardFee()+gcysPeoject.getBoardFee()+zsysPeoject.getBoardFee()) != 0){
			boardFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getBoardFee()/(glysPeoject.getBoardFee()+gcysPeoject.getBoardFee()+zsysPeoject.getBoardFee())*100,2))+"%");
		}else{
			boardFeeMap.put("zqn", "");
		}
		rs.add(boardFeeMap);
		
		Map<String,String> stampDutyMap = new HashMap<String,String>();
		stampDutyMap.put("proName", "印花税");
		stampDutyMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getStampDuty(),2)));
		stampDutyMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getStampDuty(),2)));
		stampDutyMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getStampDuty(),2)));
		stampDutyMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getStampDuty()+gcysPeoject.getStampDuty()+zsysPeoject.getStampDuty(),2)));
		stampDutyMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getStampDuty(),2)));
		stampDutyMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getStampDuty(),2)));
		stampDutyMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getStampDuty(),2)));
		stampDutyMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getStampDuty()+gcsjPeoject.getStampDuty()+zssjPeoject.getStampDuty(),2)));
		stampDutyMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getStampDuty(),2)));
		if((glysPeoject.getStampDuty()+gcysPeoject.getStampDuty()+zsysPeoject.getStampDuty()) != 0){
			stampDutyMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getStampDuty()/(glysPeoject.getStampDuty()+gcysPeoject.getStampDuty()+zsysPeoject.getStampDuty())*100,2))+"%");
		}else{
			stampDutyMap.put("zqn", "");
		}
		rs.add(stampDutyMap);
		
		Map<String,String> conferenceFeeMap = new HashMap<String,String>();
		conferenceFeeMap.put("proName", "会议费");
		conferenceFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getConferenceFee()+gcysPeoject.getConferenceFee()+zsysPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getConferenceFee()+gcsjPeoject.getConferenceFee()+zssjPeoject.getConferenceFee(),2)));
		conferenceFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getConferenceFee(),2)));
		if((glysPeoject.getConferenceFee()+gcysPeoject.getConferenceFee()+zsysPeoject.getConferenceFee()) != 0){
			conferenceFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getConferenceFee()/(glysPeoject.getConferenceFee()+gcysPeoject.getConferenceFee()+zsysPeoject.getConferenceFee())*100,2))+"%");
		}else{
			conferenceFeeMap.put("zqn", "");
		}
		rs.add(conferenceFeeMap);
		
		Map<String,String> otherFeeMap = new HashMap<String,String>();
		otherFeeMap.put("proName", "其他");
		otherFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getOtherFee(),2)));
		otherFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getOtherFee(),2)));
		otherFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getOtherFee(),2)));
		otherFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getOtherFee()+gcysPeoject.getOtherFee()+zsysPeoject.getOtherFee(),2)));
		otherFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getOtherFee(),2)));
		otherFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getOtherFee(),2)));
		otherFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getOtherFee(),2)));
		otherFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getOtherFee()+gcsjPeoject.getOtherFee()+zssjPeoject.getOtherFee(),2)));
		otherFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getOtherFee(),2)));
		if((glysPeoject.getOtherFee()+gcysPeoject.getOtherFee()+zsysPeoject.getOtherFee()) != 0){
			otherFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getOtherFee()/(glysPeoject.getOtherFee()+gcysPeoject.getOtherFee()+zsysPeoject.getOtherFee())*100,2))+"%");
		}else{
			otherFeeMap.put("zqn", "");
		}
		rs.add(otherFeeMap);
		
		Map<String,String> glfHjMap = new HashMap<String,String>();
		glfHjMap.put("proName", "管理费用合计(不含房产折旧、土地税、房产税)");
		glfHjMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getGlfHj(),2)));
		glfHjMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getGlfHj(),2)));
		glfHjMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getGlfHj(),2)));
		glfHjMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getGlfHj()+gcysPeoject.getGlfHj()+zsysPeoject.getGlfHj(),2)));
		glfHjMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getGlfHj(),2)));
		glfHjMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getGlfHj(),2)));
		glfHjMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getGlfHj(),2)));
		glfHjMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getGlfHj()+gcsjPeoject.getGlfHj()+zssjPeoject.getGlfHj(),2)));
		glfHjMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getGlfHj(),2)));
		if((glysPeoject.getGlfHj()+gcysPeoject.getGlfHj()+zsysPeoject.getGlfHj()) != 0){
			glfHjMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getGlfHj()/(glysPeoject.getGlfHj()+gcysPeoject.getGlfHj()+zsysPeoject.getGlfHj())*100,2))+"%");
		}else{
			glfHjMap.put("zqn", "");
		}
		rs.add(glfHjMap);
		
		Map<String,String> estateFeeMap = new HashMap<String,String>();
		estateFeeMap.put("proName", "房产、土地摊销");
		estateFeeMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getEstateFee(),2)));
		estateFeeMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getEstateFee(),2)));
		estateFeeMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getEstateFee(),2)));
		estateFeeMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getEstateFee()+gcysPeoject.getEstateFee()+zsysPeoject.getEstateFee(),2)));
		estateFeeMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getEstateFee(),2)));
		estateFeeMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getEstateFee(),2)));
		estateFeeMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getEstateFee(),2)));
		estateFeeMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getEstateFee()+gcsjPeoject.getEstateFee()+zssjPeoject.getEstateFee(),2)));
		estateFeeMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getEstateFee(),2)));
		if((glysPeoject.getEstateFee()+gcysPeoject.getEstateFee()+zsysPeoject.getEstateFee()) != 0){
			estateFeeMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getEstateFee()/(glysPeoject.getEstateFee()+gcysPeoject.getEstateFee()+zsysPeoject.getEstateFee())*100,2))+"%");
		}else{
			estateFeeMap.put("zqn", "");
		}
		rs.add(estateFeeMap);
		
		Map<String,String> landTaxMap = new HashMap<String,String>();
		landTaxMap.put("proName", "土地税");
		landTaxMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getLandTax(),2)));
		landTaxMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getLandTax(),2)));
		landTaxMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getLandTax(),2)));
		landTaxMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getLandTax()+gcysPeoject.getLandTax()+zsysPeoject.getLandTax(),2)));
		landTaxMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getLandTax(),2)));
		landTaxMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getLandTax(),2)));
		landTaxMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getLandTax(),2)));
		landTaxMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getLandTax()+gcsjPeoject.getLandTax()+zssjPeoject.getLandTax(),2)));
		landTaxMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getLandTax(),2)));
		if((glysPeoject.getLandTax()+gcysPeoject.getLandTax()+zsysPeoject.getLandTax()) != 0){
			landTaxMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getLandTax()/(glysPeoject.getLandTax()+gcysPeoject.getLandTax()+zsysPeoject.getLandTax())*100,2))+"%");
		}else{
			landTaxMap.put("zqn", "");
		}
		rs.add(landTaxMap);
		
		Map<String,String> propertyTaxMap = new HashMap<String,String>();
		propertyTaxMap.put("proName", "房产税");
		propertyTaxMap.put("glys", String.valueOf(GlobalMethod.round(glysPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("gcys", String.valueOf(GlobalMethod.round(gcysPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("zsys", String.valueOf(GlobalMethod.round(zsysPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("yshj", String.valueOf(GlobalMethod.round(glysPeoject.getPropertyTax()+gcysPeoject.getPropertyTax()+zsysPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("glsj", String.valueOf(GlobalMethod.round(glsjPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("gcsj", String.valueOf(GlobalMethod.round(gcsjPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("zssj", String.valueOf(GlobalMethod.round(zssjPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("sjhj", String.valueOf(GlobalMethod.round(glsjPeoject.getPropertyTax()+gcsjPeoject.getPropertyTax()+zssjPeoject.getPropertyTax(),2)));
		propertyTaxMap.put("nlj",String.valueOf(GlobalMethod.round(nljProject.getPropertyTax(),2)));
		if((glysPeoject.getPropertyTax()+gcysPeoject.getPropertyTax()+zsysPeoject.getPropertyTax()) != 0){
			propertyTaxMap.put("zqn", String.valueOf(GlobalMethod.round(nljProject.getPropertyTax()/(glysPeoject.getPropertyTax()+gcysPeoject.getPropertyTax()+zsysPeoject.getPropertyTax())*100,2))+"%");
		}else{
			propertyTaxMap.put("zqn", "");
		}
		rs.add(propertyTaxMap);
		
		NumberFormat nf = NumberFormat.getInstance();
		Map<String,String> hjMap = new HashMap<String,String>();
		double glys = glysPeoject.getGlfHj()+glysPeoject.getEstateFee()+glysPeoject.getLandTax()+glysPeoject.getPropertyTax();
		double gcys = gcysPeoject.getGlfHj()+gcysPeoject.getEstateFee()+gcysPeoject.getLandTax()+gcysPeoject.getPropertyTax();
		double zsys = glysPeoject.getGlfHj()+glysPeoject.getEstateFee()+glysPeoject.getLandTax()+glysPeoject.getPropertyTax();
		double glsj = glsjPeoject.getGlfHj()+glsjPeoject.getEstateFee()+glsjPeoject.getLandTax()+glsjPeoject.getPropertyTax();
		double gcsj = gcsjPeoject.getGlfHj()+gcsjPeoject.getEstateFee()+gcsjPeoject.getLandTax()+gcsjPeoject.getPropertyTax();
		double zssj = zssjPeoject.getGlfHj()+zssjPeoject.getEstateFee()+zssjPeoject.getLandTax()+zssjPeoject.getPropertyTax();
		double nlj = nljProject.getGlfHj()+nljProject.getEstateFee()+nljProject.getLandTax()+nljProject.getPropertyTax();
		hjMap.put("proName", "费用合计");
		hjMap.put("glys", String.valueOf(nf.format(GlobalMethod.round(glys,2))));
		hjMap.put("gcys", String.valueOf(nf.format(GlobalMethod.round(gcys,2))));
		hjMap.put("zsys", String.valueOf(nf.format(GlobalMethod.round(zsys,2))));
		hjMap.put("yshj", String.valueOf(nf.format(GlobalMethod.round(glys+gcys+zsys,2))));
		hjMap.put("glsj", String.valueOf(nf.format(GlobalMethod.round(glsj,2))));
		hjMap.put("gcsj", String.valueOf(nf.format(GlobalMethod.round(gcsj,2))));
		hjMap.put("zssj", String.valueOf(nf.format(GlobalMethod.round(zssj,2))));
		hjMap.put("sjhj", String.valueOf(nf.format(GlobalMethod.round(glsj+gcsj+zssj,2))));
		hjMap.put("nlj",String.valueOf(GlobalMethod.round(nlj,2)));
		if((glys+gcys+zsys) != 0){
			hjMap.put("zqn", String.valueOf(GlobalMethod.round(nlj/(glys+gcys+zsys)*100,2))+"%");
		}else{
			hjMap.put("zqn", "");
		}
		rs.add(hjMap);
		return rs;
	}
	
	public List<Map<String,String>> getOperateMoneys(OperateCost byCost,OperateCost nljCost,OperateCost ysjeCost,OperateIncome income,OperateIncome nljIncome,OperateIncome ysjeIncome){
		
		NumberFormat nf = NumberFormat.getInstance();
		List<Map<String,String>> rs = new ArrayList<Map<String,String>>();
		
		Map<String,String> titleMap = new HashMap<String,String>();
		titleMap.put("costName", "经营成本");
		titleMap.put("byCost", "本月");
		titleMap.put("nljCost", "年累计");
		titleMap.put("ysjeCost", "预算金额");
		titleMap.put("zqnCost", "占全年%");
		titleMap.put("operateBlank", "");
		titleMap.put("incomeName", "经营收入");
		titleMap.put("byIncome", "本月");
		titleMap.put("nljIncome", "年累计");
		titleMap.put("ysjeIncome", "预算金额");
		titleMap.put("zqnIncome", "占全年%");
		rs.add(titleMap);
		
		Map<String,String> firstMap = new HashMap<String,String>();
		firstMap.put("costName", "保洁费");
		firstMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getCleanFee(),2)));
		firstMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getCleanFee(),2)));
		firstMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getCleanFee(),2)));
		if(ysjeCost.getCleanFee() != 0){
			firstMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getCleanFee()/ysjeCost.getCleanFee()*100,2))+"%");
		}else{
			firstMap.put("zqnCost", "");
		}
		
		firstMap.put("operateBlank", "");
		firstMap.put("incomeName", "房租收入");
		firstMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getRentFee(),2)));
		firstMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getRentFee(),2)));
		firstMap.put("ysjeIncome", String.valueOf(nf.format(GlobalMethod.round(ysjeIncome.getRentFee(),2))));
		if(ysjeIncome.getRentFee() != 0){
			firstMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getRentFee()/ysjeIncome.getRentFee()*100,2))+"%");
		}else{
			firstMap.put("zqnIncome", "");
		}
		rs.add(firstMap);
		
		Map<String,String> secondMap = new HashMap<String,String>();
		secondMap.put("costName", "保安费");
		secondMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getSecurityFee(),2)));
		secondMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getSecurityFee(),2)));
		secondMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getSecurityFee(),2)));
		if(ysjeCost.getSecurityFee() != 0){
			secondMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getSecurityFee()/ysjeCost.getSecurityFee()*100,2))+"%");
		}else{
			secondMap.put("zqnCost", "");
		}
		
		secondMap.put("operateBlank", "");
		secondMap.put("incomeName", "装修管理费");
		secondMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getDecorationFee(),2)));
		secondMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getDecorationFee(),2)));
		secondMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getDecorationFee(),2)));
		if(ysjeIncome.getDecorationFee() != 0){
			secondMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getDecorationFee()/ysjeIncome.getDecorationFee()*100,2))+"%");
		}else{
			secondMap.put("zqnIncome", "");
		}
		rs.add(secondMap);
		
		Map<String,String> threeMap = new HashMap<String,String>();
		threeMap.put("costName", "通讯费");
		threeMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getCommunicationFee(),2)));
		threeMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getCommunicationFee(),2)));
		threeMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getCommunicationFee(),2)));
		if(ysjeCost.getCommunicationFee() != 0){
			threeMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getCommunicationFee()/ysjeCost.getCommunicationFee()*100,2))+"%");
		}else{
			threeMap.put("zqnCost", "");
		}
		
		threeMap.put("operateBlank", "");
		threeMap.put("incomeName", "通讯费");
		threeMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getCommunicationFee(),2)));
		threeMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getCommunicationFee(),2)));
		threeMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getCommunicationFee(),2)));
		if(ysjeIncome.getCommunicationFee() != 0){
			threeMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getCommunicationFee()/ysjeIncome.getCommunicationFee()*100,2))+"%");
		}else{
			threeMap.put("zqnIncome", "");
		}
		rs.add(threeMap);
		
		Map<String,String> fourMap = new HashMap<String,String>();
		fourMap.put("costName", "水费");
		fourMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getWaterFee(),2)));
		fourMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getWaterFee(),2)));
		fourMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getWaterFee(),2)));
		if(ysjeCost.getWaterFee() != 0){
			fourMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getWaterFee()/ysjeCost.getWaterFee()*100,2))+"%");
		}else{
			fourMap.put("zqnCost", "");
		}
		
		fourMap.put("operateBlank", "");
		fourMap.put("incomeName", "电费");
		fourMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getElectricityFee(),2)));
		fourMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getElectricityFee(),2)));
		fourMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getElectricityFee(),2)));
		if(ysjeIncome.getElectricityFee() != 0){
			fourMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getElectricityFee()/ysjeIncome.getElectricityFee()*100,2))+"%");
		}else{
			fourMap.put("zqnIncome", "");
		}
		rs.add(fourMap);
		
		Map<String,String> fiveMap = new HashMap<String,String>();
		fiveMap.put("costName", "电费");
		fiveMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getElectricityFee(),2)));
		fiveMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getElectricityFee(),2)));
		fiveMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getElectricityFee(),2)));
		if(ysjeCost.getElectricityFee() != 0){
			fiveMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getElectricityFee()/ysjeCost.getElectricityFee()*100,2))+"%");
		}else{
			fiveMap.put("zqnCost", "");
		}
		
		fiveMap.put("operateBlank", "");
		fiveMap.put("incomeName", "停车管理费");
		fiveMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getStopCarFee(),2)));
		fiveMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getStopCarFee(),2)));
		fiveMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getStopCarFee(),2)));
		if(ysjeIncome.getStopCarFee() != 0){
			fiveMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getStopCarFee()/ysjeIncome.getStopCarFee()*100,2))+"%");
		}else{
			fiveMap.put("zqnIncome", "");
		}
		rs.add(fiveMap);
		
		Map<String,String> newMap = new HashMap<String, String>();
		newMap.put("costName", "燃气费");
		newMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getGasFee(),2)));
		newMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getGasFee(),2)));
		newMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getGasFee(),2)));
		if(ysjeCost.getGasFee() != 0){
			newMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getGasFee()/ysjeCost.getGasFee()*100,2))+"%");
		}else{
			newMap.put("zqnCost", "");
		}
		
		newMap.put("operateBlank", "");
		newMap.put("incomeName", "");
		newMap.put("byIncome", "");
		newMap.put("nljIncome", "");
		newMap.put("ysjeIncome", "");
		if(ysjeIncome.getRentFee() != 0){
			newMap.put("zqnIncome", "");
		}else{
			newMap.put("zqnIncome", "");
		}
		rs.add(newMap);
		
		Map<String,String> sixMap = new HashMap<String,String>();
		sixMap.put("costName", "物料");
		sixMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getMaterials(),2)));
		sixMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getMaterials(),2)));
		sixMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getMaterials(),2)));
		if(ysjeCost.getMaterials() != 0){
			sixMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getMaterials()/ysjeCost.getMaterials()*100,2))+"%");
		}else{
			sixMap.put("zqnCost", "");
		}
		
		sixMap.put("operateBlank", "");
		sixMap.put("incomeName", "其他收入");
		sixMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getOtherFee(),2)));
		sixMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getOtherFee(),2)));
		sixMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getOtherFee(),2)));
		if(ysjeIncome.getOtherFee() != 0){
			sixMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getOtherFee()/ysjeIncome.getOtherFee()*100,2))+"%");
		}else{
			sixMap.put("zqnIncome", "");
		}
		rs.add(sixMap);
		
		Map<String,String> sevenMap = new HashMap<String,String>();
		sevenMap.put("costName", "设备维修、保养、检测费");
		sevenMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getEqmCheckFee(),2)));
		sevenMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getEqmCheckFee(),2)));
		sevenMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getEqmCheckFee(),2)));
		if(ysjeCost.getEqmCheckFee() != 0){
			sevenMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getEqmCheckFee()/ysjeCost.getEqmCheckFee()*100,2))+"%");
		}else{
			sevenMap.put("zqnCost", "");
		}
		
		sevenMap.put("operateBlank", "");
		sevenMap.put("incomeName", "供暖费");
		sevenMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getHeatFee(),2)));
		sevenMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getHeatFee(),2)));
		sevenMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getHeatFee(),2)));
		if(ysjeIncome.getHeatFee() != 0){
			sevenMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getHeatFee()/ysjeIncome.getHeatFee()*100,2))+"%");
		}else{
			sevenMap.put("zqnIncome", "");
		}
		rs.add(sevenMap);
		
		Map<String,String> eightMap = new HashMap<String,String>();
		eightMap.put("costName", "其他(保险及绿化)");
		eightMap.put("byCost", String.valueOf(GlobalMethod.round(byCost.getOtherFee(),2)));
		eightMap.put("nljCost", String.valueOf(GlobalMethod.round(nljCost.getOtherFee(),2)));
		eightMap.put("ysjeCost", String.valueOf(GlobalMethod.round(ysjeCost.getOtherFee(),2)));
		if(ysjeCost.getOtherFee() != 0){
			eightMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCost.getOtherFee()/ysjeCost.getOtherFee()*100,2))+"%");
		}else{
			eightMap.put("zqnCost", "");
		}
		
		eightMap.put("operateBlank", "");
		eightMap.put("incomeName", "物业费");
		eightMap.put("byIncome", String.valueOf(GlobalMethod.round(income.getPropertyFee(),2)));
		eightMap.put("nljIncome", String.valueOf(GlobalMethod.round(nljIncome.getPropertyFee(),2)));
		eightMap.put("ysjeIncome", String.valueOf(GlobalMethod.round(ysjeIncome.getPropertyFee(),2)));
		if(ysjeIncome.getPropertyFee() != 0){
			eightMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljIncome.getPropertyFee()/ysjeIncome.getPropertyFee()*100,2))+"%");
		}else{
			eightMap.put("zqnIncome", "");
		}
		rs.add(eightMap);
		
		Map<String,String> hjMap = new HashMap<String,String>();
		double byCst = byCost.getCleanFee()+byCost.getSecurityFee()+byCost.getCommunicationFee()+byCost.getWaterFee()+byCost.getElectricityFee()+byCost.getMaterials()+byCost.getEqmCheckFee()+byCost.getOtherFee()+byCost.getGasFee();
		double nljCst = nljCost.getCleanFee()+nljCost.getSecurityFee()+nljCost.getCommunicationFee()+nljCost.getWaterFee()+nljCost.getElectricityFee()+nljCost.getMaterials()+nljCost.getEqmCheckFee()+nljCost.getOtherFee()+nljCost.getGasFee();
		double ysjeCst = ysjeCost.getCleanFee()+ysjeCost.getSecurityFee()+ysjeCost.getCommunicationFee()+ysjeCost.getWaterFee()+ysjeCost.getElectricityFee()+ysjeCost.getMaterials()+ysjeCost.getEqmCheckFee()+ysjeCost.getOtherFee()+ysjeCost.getGasFee();
		hjMap.put("costName", "合计");
		hjMap.put("byCost", String.valueOf(nf.format(GlobalMethod.round(byCst,2))));
		hjMap.put("nljCost", String.valueOf(nf.format(GlobalMethod.round(nljCst,2))));
		hjMap.put("ysjeCost", String.valueOf(nf.format(GlobalMethod.round(ysjeCst,2))));
		if(ysjeCst != 0){
			hjMap.put("zqnCost", String.valueOf(GlobalMethod.round(nljCst/ysjeCst*100,2))+"%");
		}else{
			hjMap.put("zqnCost", "");
		}
		
		hjMap.put("operateBlank", "");
		hjMap.put("incomeName", "合计");
		double inc = income.getRentFee()+income.getDecorationFee()+income.getCommunicationFee()+income.getElectricityFee()+income.getStopCarFee()+income.getOtherFee()+income.getHeatFee()+income.getPropertyFee();
		double nljInc = nljIncome.getRentFee()+nljIncome.getDecorationFee()+nljIncome.getCommunicationFee()+nljIncome.getElectricityFee()+nljIncome.getStopCarFee()+nljIncome.getOtherFee()+nljIncome.getHeatFee()+nljIncome.getPropertyFee();
		double ysjeInc = ysjeIncome.getRentFee()+ysjeIncome.getDecorationFee()+ysjeIncome.getCommunicationFee()+ysjeIncome.getElectricityFee()+ysjeIncome.getStopCarFee()+ysjeIncome.getOtherFee()+ysjeIncome.getHeatFee()+ysjeIncome.getPropertyFee();
		hjMap.put("byIncome", String.valueOf(nf.format(GlobalMethod.round(inc,2))));
		hjMap.put("nljIncome", String.valueOf(nf.format(GlobalMethod.round(nljInc,2))));
		hjMap.put("ysjeIncome", String.valueOf(nf.format(GlobalMethod.round(ysjeInc,2))));
		if(ysjeInc != 0){
			hjMap.put("zqnIncome", String.valueOf(GlobalMethod.round(nljInc/ysjeInc*100,2))+"%");
		}else{
			hjMap.put("zqnIncome", "");
		}
		rs.add(hjMap);
		return rs;
	}
}
