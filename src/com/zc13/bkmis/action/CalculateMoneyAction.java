package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

import com.zc13.bkmis.form.CalculateMoneyForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CalculateMoney;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.service.ICalculateMoneyService;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 合同预算action
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午10:50:51
 */
public class CalculateMoneyAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private ICalculateMoneyService calculateService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		calculateService = (ICalculateMoneyService)getBean("calculateService");
	}
	
	/**
	 * (不包含变更合同只是汇总变更前以及其他的合同)
	 * 
	 * Date:2013-3-23 
	 * Time:上午11:34:24
	 */
	@SuppressWarnings("rawtypes")
	public ActionForward calculateCompact(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
		Integer compactId = calculateForm.getCompactId();
		Map mapSession = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((mapSession.get("userlp").toString()), "0"));
		CAccounttemplate accountTemplate = calculateService.getAccounttemlateByLpId(lpId);
		String data = "1";
		if(compactId != null){
			List<CompactRoomCoststandard> compactRoomCoststandard = calculateService.getCompactRoomCoststandardById(compactId);
			try {
				calculateService.CalculateMoney(compactRoomCoststandard, accountTemplate,compactId);
			} catch (Exception e) {
				data = "0";
				e.printStackTrace();
			}
		}else{
			List<CompactRoomCoststandard> compactRoomCoststandard = calculateService.getCompactRoomCoststandardById(null);
			try {
				calculateService.CalculateMoney(compactRoomCoststandard, accountTemplate,null);
			} catch (Exception e) {
				data = "0";
				e.printStackTrace();
			}
		}
		PrintWriter writer = response.getWriter();
		writer.print(data);
		if(writer != null){
			writer.close();
		}
		return null;
	}
	
	/**
	 * 合同预算列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2013-3-30 
	 * Time:上午11:04:12
	 */
	public ActionForward yearCalculateList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
		
		List yearList = calculateService.getYearCompactMoney(calculateForm);
		//取总条数
		int totalcount = calculateService.getYearCompactMoneyCount(calculateForm);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == yearList || yearList.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/calculate.do?method=yearCalculateList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/calculate.do?method=yearCalculateList" , Integer.parseInt(GlobalMethod.NullToParam(GlobalMethod.ObjToStr(calculateForm.getPagesize()), "10")), 
					Integer.parseInt(GlobalMethod.NullToParam(calculateForm.getCurrentpage(), "1")), totalcount);
		}
		request.setAttribute("pagination", htmlPagination);
		request.setAttribute("yearList", yearList);
		request.setAttribute("calculateForm", calculateForm);
		return mapping.findForward("yearList");
	}
	
	/***
	 * 年度的月统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2013-3-30 
	 * Time:下午12:46:39
	 */
	public ActionForward monthCalculateList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
		
		List monthList = calculateService.getYearDetail(calculateForm);
		request.setAttribute("monthList", monthList);
		request.setAttribute("calculateForm", calculateForm);
		return mapping.findForward("monthList");
	}
	
	/***
	 * 月度的统计详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2013-3-30 
	 * Time:下午12:46:39
	 */
	public ActionForward monthDetailList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
		
		List monthDetailList = calculateService.getMonthDetail(calculateForm);
		//取总条数
		int totalcount = calculateService.getMonthDetailCount(calculateForm);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == monthDetailList || monthDetailList.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/calculate.do?method=monthDetailList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/calculate.do?method=monthDetailList" , Integer.parseInt(GlobalMethod.NullToParam(GlobalMethod.ObjToStr(calculateForm.getPagesize()), "10")), 
					Integer.parseInt(GlobalMethod.NullToParam(calculateForm.getCurrentpage(), "1")), totalcount);
		}
		request.setAttribute("pagination", htmlPagination);
		request.setAttribute("monthDetailList", monthDetailList);
		request.setAttribute("calculateForm", calculateForm);
		return mapping.findForward("monthDetialList");
	}
	
	/***
	 * 合同账单查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2013-3-30 
	 * Time:下午2:27:17
	 */
	public ActionForward compactBillList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
		List list = calculateService.getCompactBill(calculateForm);
		request.setAttribute("compactBillList", list);
		request.setAttribute("calculateForm", calculateForm);
		return mapping.findForward("compactBillList");
	}
	
	/**
	 * 导出年度统计的excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:2013-4-18 
	 * Time:下午9:52:18
	 */
	public ActionForward exportYearExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
			calculateForm.setPage(false);
			//list中存放的就是当前页面上显示的所有数据
			List<CalculateMoney> list = calculateService.getYearCompactMoney(calculateForm);
			
			//表头
			String[] cellHeader = Constant.EXCEL_YEAR_CALCULATE_DETAIL;
			String[] cellValue = Constant.EXCEL_YEAR_CALCULATE_VALUE;
			//定义文件名
			String sheetName = "年度合同预算信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list,sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "年度合同预算信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出各月统计的excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:2013-4-18 
	 * Time:下午9:52:18
	 */
	public ActionForward exportMonthExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
			//list中存放的就是当前页面上显示的所有数据
			List<CalculateMoney> list = calculateService.getYearDetail(calculateForm);
			
			//表头
			String[] cellHeader = Constant.EXCEL_YEAR_DETAIL_CALCULATE_DETAIL;
			String[] cellValue = Constant.EXCEL_YEAR_DETAIL_CALCULATE_VALUE;
			//定义文件名
			String sheetName = GlobalMethod.ObjToStr(calculateForm.getYear())+"各月合同预算信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list,sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, GlobalMethod.ObjToStr(calculateForm.getYear())+"各月合同预算信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出各月详细统计的excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:2013-4-18 
	 * Time:下午9:52:18
	 */
	public ActionForward exportMonthDetailExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
			calculateForm.setPage(false);
			//list中存放的就是当前页面上显示的所有数据
			List list = calculateService.getMonthDetail(calculateForm);
			
			//表头
			String[] cellHeader = Constant.EXCEL_MONTH_DETAIL_CALCULATE_DETAIL;
			String[] cellValue = Constant.EXCEL_MONTH_DETAIL_CALCULATE_VALUE;
			//定义文件名
			String sheetName = GlobalMethod.ObjToStr(calculateForm.getYear())+"年"+GlobalMethod.ObjToStr(calculateForm.getMonth())+"月合同预算信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list,sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, GlobalMethod.ObjToStr(calculateForm.getYear())+"年"+GlobalMethod.ObjToStr(calculateForm.getMonth())+"月合同预算信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出各月详细统计的excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:2013-4-18 
	 * Time:下午9:52:18
	 */
	public ActionForward exportCompactDetailExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			CalculateMoneyForm calculateForm = (CalculateMoneyForm)form;
			calculateForm.setPage(false);
			//list中存放的就是当前页面上显示的所有数据
			List list = calculateService.getCompactBill(calculateForm);
			
			//表头
			String[] cellHeader = Constant.EXCEL_COMPACT_CALCULATE_DETAIL;
			String[] cellValue = Constant.EXCEL_COMPACT_CALCULATE_VALUE;
			//定义文件名
			String sheetName = "合同预算信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list,sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "合同预算信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
