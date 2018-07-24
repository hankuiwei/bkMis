package com.zc13.msmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.MFunction;
import com.zc13.msmis.service.IFunctionListService;
import com.zc13.util.GlobalMethod;

	/**
	 * 功能列维护相关
	 * @author fengsen
	 * Date：Nove,08, 2010
	 * Time：15:38:15
	 */

public class FunctionListAction extends BasicAction{
	//注入ServiceBean
	private IFunctionListService functionListService = null;
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		functionListService = (IFunctionListService) getBean("functionListService");
	}
	
	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 展示功能列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getFunctionList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		List<MFunction> functionList =  new ArrayList();
		
		try {
			functionList = functionListService.getFunctionListAll();
		} catch (Exception e) {
			logger.error("加载功能列信息失败，FunctionListAction.getFunctionList()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载功能列信息失败，FunctionListAction.getFunctionList()!",e);
		}
		
		request.setAttribute("functionList", functionList);
		
		return mapping.findForward("urlList");
	}
	
	/**
	 * 转换到添加页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addUrlBefore(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		int functionId = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("parentId"), "0"));
		MFunction function = null;
		IFunctionListService functionListService = (IFunctionListService)getBean("functionListService");
		
			try {
				function =(MFunction)functionListService.getFunction(functionId);
			} catch (Exception e) {
				logger.error("加载单独功能信息失败，FunctionListAction.addUrlBefore()。详细信息："+e.getMessage());
				throw new BkmisWebException("加载单独功能信息失败，FunctionListAction.addUrlBefore()!",e);
			}
		
		request.setAttribute("function", function);
		saveToken(request);
		return mapping.findForward("addUrl");
	}

	/**
	 * 查询功能名是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward checkFuncName(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String functionName = request.getParameter("functionName").trim();
		int parentid = Integer.parseInt(request.getParameter("parentId"));
		
		boolean isExist = false;
			try {
				isExist = functionListService.checkFunction(functionName, parentid);
			} catch (Exception e) {
				logger.error("查询功能名信息失败，FunctionListAction.checkFuncName()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询功能名信息失败，FunctionListAction.checkFuncName()!",e);
			}
		
		out.print(isExist);
		return null;
	}
	
	/**
	 * 添加功能信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addFunction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		IFunctionListService functionListService = (IFunctionListService)getBean("functionListService");
		int functionId = Integer.parseInt(request.getParameter("functionId"));
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String direction = request.getParameter("direction") == null ? "" :request.getParameter("direction");
		String urlName = request.getParameter("urlName");
		int sequence = Integer.parseInt(request.getParameter("sequence"));
		String functionName = request.getParameter("functionName");  
		if (isTokenValid(request, true)) {
			try {
				functionListService.addFunction(functionName,functionId,direction, parentId, sequence, urlName);
			} catch (Exception e) {
				logger.error("添加功能信息失败，FunctionListAction.addFunction()。详细信息："+e.getMessage());
				throw new BkmisWebException("添加功能信息失败，FunctionListAction.addFunction()!",e);
			}
		
		}
		return getFunctionList(mapping, form, request, response);
	}

	/**
	 * 转换到修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateBefore(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		int functionId = Integer.parseInt(request.getParameter("functionId"));
		MFunction function = null;
		IFunctionListService functionListService = (IFunctionListService)getBean("functionListService");
		
			try {
				function =(MFunction)functionListService.getFunction(functionId);
			} catch (Exception e) {
				logger.error("加载单独功能信息失败，FunctionListAction.updateBefore()。详细信息："+e.getMessage());
				throw new BkmisWebException("加载单独功能信息失败，FunctionListAction.updateBefore()!",e);
			}
		
		request.setAttribute("function", function);
		
		return mapping.findForward("updateBefore");
	}
	
	/**
	 * 修改功能信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateFunction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		System.out.println("ssss");
		IFunctionListService functionListService = (IFunctionListService)getBean("functionListService");
		int functionId = Integer.parseInt(request.getParameter("functionId"));
		String urlName = request.getParameter("urlName");
		int sequence = Integer.parseInt(request.getParameter("sequence"));
		String functionName = request.getParameter("functionName");
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		System.out.println("functionId"+functionId);
		System.out.println("urlName"+urlName);
		System.out.println("sequence"+sequence);
		System.out.println("functionname"+functionName);
		
			try {
				functionListService.updateFunction(functionName,functionId, urlName,parentId, sequence);
			} catch (Exception e) {
				logger.error("修改功能信息失败，FunctionListAction.updateFunction()。详细信息："+e.getMessage());
				throw new BkmisWebException("修改功能信息失败，FunctionListAction.updateFunction()!",e);
			}
		
		
		return getFunctionList(mapping, form, request, response);
	}
	
	/**
	 * 删除功能信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward delFunction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		IFunctionListService functionListService = (IFunctionListService)getBean("functionListService");
		int functionId = Integer.parseInt(request.getParameter("functionId"));
			try {
				functionListService.delFunction(functionId);
			} catch (Exception e) {
				logger.error("删除功能信息失败，FunctionListAction.delFunction()。详细信息："+e.getMessage());
				throw new BkmisWebException("删除功能信息失败，FunctionListAction.delFunction()!",e);
			}
		
		
		return getFunctionList(mapping, form, request, response);
	}
}
