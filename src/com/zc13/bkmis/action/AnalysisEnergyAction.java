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

import com.zc13.bkmis.form.AnalysisEnergyForm;
import com.zc13.bkmis.mapping.AnalysisEnergy;
import com.zc13.bkmis.service.IAnalysisEnergyService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 31, 2010
 * Time：5:09:38 PM
 */
public class AnalysisEnergyAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IAnalysisEnergyService ienergyService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		ienergyService = (IAnalysisEnergyService)getBean("ienergyService");
	}
	//显示查询的统计记录信息
	public ActionForward showEnergyAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			AnalysisEnergyForm aeForm = (AnalysisEnergyForm)form;
			try{
				ienergyService.showEngAnalysis(aeForm);
				//获取总条数
				int totalcount = ienergyService.queryCountEngAnalysis(aeForm);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == aeForm.getEnergyList() || aeForm.getEnergyList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisEng.do?method=showEnergyAnalysis", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/analysisEng.do?method=showEnergyAnalysis", Integer.parseInt(GlobalMethod.NullToParam(aeForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(aeForm.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("查询显示能源统计信息失败！AnalysisEnergyServiceImpl.showEngAnalysis()。详细信息："+e.getMessage());
				throw new BkmisServiceException("查询显示能源统计信息失败！AnalysisEnergyServiceImpl.showEngAnalysis()");
			}
			return mapping.findForward("showEnergy");
	}
	//添加统计记录
	public ActionForward addEnergyAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			//得到系统当前时间
			String currentTime = GlobalMethod.getTime();
			//查询楼盘
			List epList = new ArrayList();
			epList = ienergyService.selectLp();
			
			request.setAttribute("current", currentTime);
			request.setAttribute("epList", epList);
			return mapping.findForward("addEnergy");
	}
	//执行添加统计记录
	public ActionForward doAddEnergyAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			AnalysisEnergyForm aeForm = (AnalysisEnergyForm)form;
			try{
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				Integer userid = (Integer)map.get("userid");
				aeForm.setRootUser(userid);
				aeForm.setUserName(userName);
				ienergyService.addEngAnalysis(aeForm);
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("添加能源统计信息失败！AnalysisEnergyServiceImpl.doAddEnergyAnalysis()。详细信息："+e.getMessage());
				throw new BkmisServiceException("添加能源统计信息失败！AnalysisEnergyServiceImpl.doAddEnergyAnalysis()");
			}
			
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("addEnergy");
	}
	//查看分析图
	public ActionForward selectGraphic(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			List analysisList = new ArrayList();
			Map map = new HashMap();
			//获取要查看的id
			String strId = request.getParameter("itemId");
			int id = Integer.parseInt(strId);
			//路径
			String path = request.getSession().getServletContext().getRealPath("")+"\\temporary";;
			try{
				map = ienergyService.graphicMessage(id,path);
			}catch (Exception e) {
				logger.error("查看能源统计分析图信息失败！AnalysisEnergyServiceImpl.selectGraphic()。详细信息："+e.getMessage());
				throw new BkmisServiceException("查看能源统计分析图信息失败！AnalysisEnergyServiceImpl.selectGraphic()");
			}
			analysisList = (List)map.get("list");
			String elecRealpath = request.getContextPath()+(String)map.get("electricPath");
			String waterRealPath = request.getContextPath()+(String)map.get("waterPath");
			String gasRealPath = request.getContextPath()+(String)map.get("gasPath");
			
			request.setAttribute("analysisList", analysisList);
			request.setAttribute("elecRealpath", elecRealpath);
			request.setAttribute("waterRealPath", waterRealPath);
			request.setAttribute("gasRealPath", gasRealPath);
			return mapping.findForward("graphic");
	}
	//删除统计记录
	public ActionForward doDelAnalysis(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		
			//获取id
			String strId = request.getParameter("delIds");
			String[] idArray = strId.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<AnalysisEnergy> energyList = new ArrayList<AnalysisEnergy>();
			try{
				for(int i = 0;i<idArray.length;i++){
					ids[i] = Integer.parseInt(idArray[i]);
					energyList = ienergyService.selectDetailAnalysis(ids);
				}
				
				for(int i= 0;i<idArray.length;i++){
					idInt[i] = Integer.parseInt(idArray[i]);
					ienergyService.doDelAnalysis(idInt[i],userName);
				}
			}catch(Exception e){
				logger.error("删除显示能源统计信息失败！AnalysisEnergyServiceImpl.doDelAnalysis()。详细信息："+e.getMessage());
				throw new BkmisServiceException("删除显示能源统计信息失败！AnalysisEnergyServiceImpl.doDelAnalysis()");
			}
			
			response.sendRedirect("analysisEng.do?method=showEnergyAnalysis");
			return null;
	}
}
