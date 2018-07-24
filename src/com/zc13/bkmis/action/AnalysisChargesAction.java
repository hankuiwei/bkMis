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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.AnalysisChargesForm;
import com.zc13.bkmis.mapping.AnalysisCost;
import com.zc13.bkmis.service.IAnalysisChargesService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：3:17:38 PM
 */
public class AnalysisChargesAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IAnalysisChargesService ichargesService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		ichargesService = (IAnalysisChargesService)getBean("ichargesService");
	}
	
	//显示统计信息列表
	public ActionForward showCharge(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AnalysisChargesForm achForm = (AnalysisChargesForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				achForm.setLpId(lpId);
				/** 到此为止*/
				ichargesService.showChaAnalysis(achForm);
				//获取总条数
				int totalcount = ichargesService.countQuery(achForm);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == achForm.getChaAnalysisList()|| achForm.getChaAnalysisList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCha.do?method=showCharge", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisCha.do?method=showCharge", Integer.parseInt(GlobalMethod.NullToParam(achForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(achForm.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("查询显示收费分析记录信息加载失败，AnalysisChargesAction.showCharge()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询显示收费分析记录信息加载失败，AnalysisChargesAction.showCharge()!",e);
			}
			return mapping.findForward("showCharge");
	}
	//删除统计记录列表信息
	public ActionForward doDelCharge(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//获取登录用户信息
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			//获取id
			String strdelId = request.getParameter("delIds");
			String[] idArray = strdelId.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<AnalysisCost> list = new ArrayList<AnalysisCost>();
			try{
				for(int i = 0;i<idArray.length;i++){
					ids[i] = Integer.parseInt(idArray[i]);
					list = ichargesService.selectDetailAnalys(ids);
				}
				
				for(int i = 0;i<idArray.length;i++){
					idInt[i] = Integer.parseInt(idArray[i]);
					ichargesService.delChaAnalysis(idInt[i],userName);
				}
			}catch(Exception e){
				logger.error("删除收费分析记录信息加载失败，AnalysisChargesAction.doDelCharge()。详细信息："+e.getMessage());
				throw new BkmisWebException("删除收费分析记录信息加载失败，AnalysisChargesAction.doDelCharge()!",e);
			}
			
			response.sendRedirect("analysisCha.do?method=showCharge");
			return null;
	}
	//添加统计记录页面
	public ActionForward addCharge(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//获取系统当前时间
			String currentTime = GlobalMethod.getTime();
			/*List epList = new ArrayList();
			try{
				epList = ichargesService.selectLp();
			}catch(Exception e){
				logger.error("查询楼盘信息加载失败，AnalysisChargesAction.addCharge()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询楼盘信息加载失败，AnalysisChargesAction.addCharge()!",e);
			}
			request.setAttribute("epList",epList);*/
			request.setAttribute("current", currentTime);
			return mapping.findForward("addCharge");
	}
	//执行添加的操作
	public ActionForward doAddCharge(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AnalysisChargesForm achForm = (AnalysisChargesForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				achForm.setLpId(lpId);
				/** 到此为止*/
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				achForm.setUserName(userName);
				ichargesService.addChaAnalysis(achForm);
				
				
			}catch(Exception e){
				logger.error("添加统计信息加载失败，AnalysisChargesAction.doAddCharge()。详细信息："+e.getMessage());
				throw new BkmisWebException("添加统计信息加载失败，AnalysisChargesAction.doAddCharge()!",e);
			}
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("addCharge");
	}
	//查询统计记录信息生成图片
	public ActionForward selectAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//获取要查看的统计记录的id
			String strId = request.getParameter("analysisId");
			int id = Integer.parseInt(strId);
			Map map = new HashMap();
			//图片存放的路径
			String p = request.getSession().getServletContext().getRealPath("")+"\\temporary";
			//存储统计记录的信息
			List analysisList = new ArrayList();
			//获取要查询生成图的条件
			String type = request.getParameter("type");
			String imagePath = "";
			try{
				map = ichargesService.selectAnalys(id,p,type);
			}catch(Exception e){
				logger.error("查询查看生成图统计信息加载失败，AnalysisChargesAction.selectAnalysis()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询查看生成图统计信息加载失败，AnalysisChargesAction.selectAnalysis()!",e);
			}
			analysisList = (List)map.get("analysisList");
			imagePath = request.getContextPath()+(String)map.get("imagePath");
			request.setAttribute("analysisList", analysisList);
			//存取统计记录的信息的id
			request.setAttribute("accountId", strId);
			//路径
			request.setAttribute("imagePath", imagePath);
			request.setAttribute("selectType", type);
			return mapping.findForward("selectAnalysis");
	}
}
