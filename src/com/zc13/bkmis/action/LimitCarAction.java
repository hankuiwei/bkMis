package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.LimitCarForm;
import com.zc13.bkmis.service.LimitCarService;
import com.zc13.msmis.mapping.LimitCar;
import com.zc13.util.GlobalMethod;


/**
 * 车辆限行操作
 * @author daokui
 * @Date Apr 28, 2011
 * @Time 6:21:42 PM
 */
public class LimitCarAction extends BasicAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private LimitCarService limitCarService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		limitCarService = (LimitCarService)getBean("limitCarService");
	}
	/**
	 * 获取一个所有记录的list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:9:56:48 AM
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			//System.out.println("======================");
			List list = limitCarService.getList();
			request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取车辆限行号列表信息失败！");
			e.printStackTrace();
		}
		
		return mapping.findForward("list");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:9:56:44 AM
	 */
	public ActionForward deletByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		LimitCarForm limitCarForm = (LimitCarForm)form;
	    Integer id = Integer.parseInt(GlobalMethod.NullToSpace(request.getParameter("id")));
	    try {
		    limitCarForm.setId(id);
			out = response.getWriter();
			limitCarService.deleteById(limitCarForm);
			out.print(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除车辆限行号信息失败！");
			out.print(false);
		}
		return null;
	}
	/**
	 * 跳转到编辑页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:10:20:11 AM
	 */
	public ActionForward goEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			LimitCarForm limitCarForm = (LimitCarForm)form;
			LimitCar limitCar = limitCarService.getById(limitCarForm);
			request.setAttribute("limitCar", limitCar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("跳转到车辆限行号编辑页面失败！");
		}
		return mapping.findForward("goEdit");
	}
	/**
	 * 编辑一条记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:10:26:43 AM
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			LimitCarForm limitCarForm = (LimitCarForm)form;
			limitCarService.edit(limitCarForm);
			request.setAttribute("isEdit", "true");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("修改失败！");
		}
		//跳转回编辑页面的目的，是判断是否修改成功，成功的话就直接关闭当前的编辑页面，并刷新它的父页面
		return mapping.findForward("goEdit");
	}
	/**
	 * 增加一条新的记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:10:24:44 AM
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			LimitCarForm limitCarForm = (LimitCarForm)form;
			limitCarService.add(limitCarForm);
			request.setAttribute("isAdd", "true");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("新增失败！");
		}
		//跳转回新增页面的目的，是判断是否新增成功，成功的话就直接关闭当前的新增页面，并刷新它的父页面
		return mapping.findForward("goAdd");
	}
	/**
	 * 跳转到新增页面，这个方法没有什么意义。目前是因为过滤器将jsp全部过滤掉了，所以必须除此下策
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 4, 2011 
	 * Time:9:24:26 AM
	 */
	public ActionForward goAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		return mapping.findForward("goAdd");
	}
		
	
}
