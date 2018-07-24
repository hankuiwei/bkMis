package com.zc13.bkmis.action;

import java.io.IOException;
import java.sql.SQLException;
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

import com.zc13.bkmis.form.AnalysisCustomerForm;
import com.zc13.bkmis.mapping.AnalysisCustomer;
import com.zc13.bkmis.service.IAnalysisCustomerService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 28, 2010
 * Time：3:13:34 PM
 */
public class AnalysisCustomerAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IAnalysisCustomerService ianalysiscusService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		ianalysiscusService = (IAnalysisCustomerService)getBean("ianalysiscusService");
	}
	
	//显示查询出的客户信息分析列表
	public ActionForward showAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AnalysisCustomerForm acForm = (AnalysisCustomerForm)form;
			try{
				ianalysiscusService.showAnalysis(acForm);
				//获取总条数
				int totalcount = ianalysiscusService.queryCountAnalysisCus(acForm);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == acForm.getAnalysisCusList() || acForm.getAnalysisCusList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCus.do?method=showAnalysis", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCus.do?method=showAnalysis", Integer.parseInt(GlobalMethod.NullToParam(acForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(acForm.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("查询客户分析信息加载失败，AnalysisCustomerAction.showAnalysis()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询客户分析信息加载失败，AnalysisCustomerAction.showAnalysis()!",e);
			}
			return mapping.findForward("showAnalysis");
	}
	//删除统计信息
	public ActionForward doDelAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			//获取id
			String strdelId = request.getParameter("delIds");
			String[] idArray = strdelId.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<AnalysisCustomer> list = new ArrayList<AnalysisCustomer>();
			try{
				for(int i = 0;i<idArray.length;i++){
					ids[i] = Integer.parseInt(idArray[i]);
					list = ianalysiscusService.selectDetailAnalysis(ids);
				}
				
				for(int i = 0;i<idArray.length;i++){
					idInt[i] = Integer.parseInt(idArray[i]);
					ianalysiscusService.delAnalysis(idInt[i],userName);
				}
			}catch (Exception e) {
				logger.error("删除客户分析记录信息加载失败，AnalysisCustomerAction.doDelAnalysis()。详细信息："+e.getMessage());
				throw new BkmisWebException("删除客户分析记录信息加载失败，AnalysisCustomerAction.doDelAnalysis()!",e);
			}
			
			response.sendRedirect("analysisCus.do?method=showAnalysis");
			return null;
	}
	//跳转到添加页面
	public ActionForward addAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//获取当前系统时间
			String currentDate = GlobalMethod.getTime();
			//查询楼盘名称
			List lpList = new ArrayList();
			try{
				lpList = ianalysiscusService.selectLp();
			}catch(Exception e){
				logger.error("查询楼盘信息失败，AnalysisCustomerAction.addAnalysis()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询楼盘信息失败，AnalysisCustomerAction.addAnalysis()!",e);
			}
			request.setAttribute("lpList", lpList);
			request.setAttribute("current", currentDate);
			return mapping.findForward("addAnalysis");
	}
	//执行添加操作 ,客户统计信息分析
	public ActionForward doAddAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AnalysisCustomerForm acForm = (AnalysisCustomerForm)form;
			try{
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				acForm.setUserName(userName);
				ianalysiscusService.addAnalysisCus(acForm);
				
				
			}catch(Exception e){
				logger.error("添加客户分析信息失败，AnalysisCustomerAction.doAddAnalysis()。详细信息："+e.getMessage());
				throw new BkmisWebException("添加客户分析信息失败，AnalysisCustomerAction.doAddAnalysis()!",e);
			}
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("addAnalysis");
	}
	//查看分析图界面
	public ActionForward selectGraphic(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			//获取要查询的id
			String strId = request.getParameter("itemId");
			int id = Integer.parseInt(strId);
			//生成图形的查询条件
			String type = request.getParameter("type");
			AnalysisCustomer ac = new AnalysisCustomer();
			List analysisList = new ArrayList();
			Map map = new HashMap();
			String fileName = "";
			String p = request.getSession().getServletContext().getRealPath("")+"\\temporary";
			String imagePath = "";
			try{
				//查询出统计信息
				map = ianalysiscusService.selectAnalysisById(id,p);
			}catch(Exception e){
				logger.error("查看统计分析数据信息失败，AnalysisCustomerAction.selectGraphic()。详细信息："+e.getMessage());
				throw new BkmisWebException("查看统计分析数据信息失败，AnalysisCustomerAction.selectGraphic()!",e);
			}
			analysisList = (List)map.get("list");
			imagePath = request.getContextPath()+(String)map.get("imagePath");
			
			request.setAttribute("analysisList", analysisList);
			request.setAttribute("imagePath", imagePath);
			//将查看统计信息id放在页面
			request.setAttribute("accountId", strId);
			request.setAttribute("selectType", type);
			return  mapping.findForward("seeGraphic");
	}
	//按条件查询生成图片
	public ActionForward selectGraphicByParam(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//String type = request.getParameter("type");
			//获取id统计记录的id
			String strId = request.getParameter("accountId");
			int id = Integer.parseInt(strId);
			AnalysisCustomerForm acForm = (AnalysisCustomerForm)form;
			String p = request.getSession().getServletContext().getRealPath("")+"\\temporary";
			List analysisList = new ArrayList();
			Map map = new HashMap();
			String inRealPath = "";
			String outRealPath = ""; 
			String type = acForm.getType();
			try{
				//查询统计信息记录信息
				analysisList = ianalysiscusService.selectAnaById(id);
				//按条件查询出统计信息的数据
				map = ianalysiscusService.selectAnalysisByParam(p, acForm);
			}catch(Exception e){
				logger.error("查看条件统计分析数据信息失败，AnalysisCustomerAction.selectGraphicByParam()。详细信息："+e.getMessage());
				throw new BkmisWebException("查看条件统计分析数据信息失败，AnalysisCustomerAction.selectGraphicByParam()!",e);
			}
			inRealPath = (String)map.get("inRealPath");
			outRealPath = (String)map.get("outRealPath");
			request.setAttribute("inRealPath", request.getContextPath()+inRealPath);
			request.setAttribute("outRealPath", request.getContextPath()+outRealPath);
			request.setAttribute("analysisList", analysisList);
			request.setAttribute("accountId", strId);
			request.setAttribute("selectType", type);
			return mapping.findForward("seeGraphic");
	}
	//查看客户的详细信息
	public ActionForward selectCustomerDetail(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//查询客户统计记录的信息
			AnalysisCustomerForm acForm = (AnalysisCustomerForm)form;
			String detailId = request.getParameter("detailId");
			int id = Integer.parseInt(detailId);
			acForm.setId(id);
			Map map = new HashMap();
			//获取下拉列表的查询参数
			String paramerValue = request.getParameter("paramType");
			//存储详细信息
			List detailList = new ArrayList();
			try{
				map = ianalysiscusService.selecetDetail(acForm);
				detailList = (List)map.get("detailList");
				//获取总条数
				/*int totalcount = detailList.size();
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == map.get("detailList") || detailList.size()<= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCus.do?method=selectCustomerDetail", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCus.do?method=selectCustomerDetail", Integer.parseInt(GlobalMethod.NullToParam(acForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(acForm.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);*/
				request.getSession().setAttribute("exportList", detailList);
			}catch(Exception e){
				logger.error("查看客户信息失败，AnalysisCustomerAction.selectCustomerDetail()。详细信息："+e.getMessage());
				throw new BkmisWebException("查看客户信息失败，AnalysisCustomerAction.selectCustomerDetail()!",e);
			}
			request.setAttribute("accountList", map.get("accountList"));
			request.setAttribute("detailList", map.get("detailList"));
			request.setAttribute("detailId", id);
			request.setAttribute("paramerValue", paramerValue);
			return mapping.findForward("customerDetail");
	}
	//导出客户详细信息报表
	public ActionForward exportCusDetailExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取存放页面显示的内容的list
				List list = (List)request.getSession().getAttribute("exportList");
				//表头
				String[] cellHeader = Constant.EXCEL_CUSTOMER_DETAIL;
				String[] cellValue = Constant.EXCEL_CUSTOMER_VALUE;
				//定义文件名
				String sheetName = "统计客户详细列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "统计客户详细列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出统计客户详细报表出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
}
