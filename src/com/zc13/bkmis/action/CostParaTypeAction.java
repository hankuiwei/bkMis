package com.zc13.bkmis.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CostParaTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.service.ICostParaTypeService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 计费参数类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:12:21 PM
 */
public class CostParaTypeAction extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private ICostParaTypeService costParaTypeService= null;
	
	@Override
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		costParaTypeService = (ICostParaTypeService)getBean("costParaTypeService");
	}
	/**
	 * 获得计费参数类型列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCostParaTypeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostParaTypeForm costParaTypeForm = (CostParaTypeForm)form;
		String forward = "toShowCostParaTypeList";
		List<CCostparatype> costParaTypeList = new ArrayList<CCostparatype>();//费用类型列表
		try {
			costParaTypeList = costParaTypeService.getCostParaTypeList(costParaTypeForm);//获得费用类型列表
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == costParaTypeList || costParaTypeList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/costParaType.do?method=getCostParaTypeList", 5, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/costParaType.do?method=getCostParaTypeList", Integer.parseInt(GlobalMethod.NullToParam(costParaTypeForm.getPagesize(),"5")),
						Integer.parseInt(GlobalMethod.NullToParam(costParaTypeForm.getCurrentpage(),"1")), costParaTypeList.get(0).getTotalcount());
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (RuntimeException e) {
			logger.error("计费参数类型信息查询失败!CostParaTypeAction.getCostParaTypeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("计费参数类型信息查询失败!CostParaTypeAction.getCostParaTypeList()。");
		}
		request.setAttribute("startNum", costParaTypeForm.getStartIndex());
		request.setAttribute("costParaTypeList", costParaTypeList);
		request.setAttribute("typeName", costParaTypeForm.getTypeName());
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward(forward);
	}
	
	/**
	 * 保存或更新计费参数类型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editCostParaType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostParaTypeForm costParaTypeForm = (CostParaTypeForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getCostParaTypeList(mapping, form, request, response);
		}
		try {
			costParaTypeService.saveOrUpdateCostParaType(costParaTypeForm);
			request.setAttribute("alertMessage", "保存成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "保存失败！");
			logger.error("保存或更新计费参数类型失败!CostParaTypeAction.editCostParaType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("保存或更新计费参数类型失败!CostParaTypeAction.editCostParaType()。");
		}
		return this.getCostParaTypeList(mapping, form, request, response);
	}
	
	/**
	 * 删除计费参数类型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCostParaType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostParaTypeForm costParaTypeForm = (CostParaTypeForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getCostParaTypeList(mapping, form, request, response);
		}
		try {
			costParaTypeService.deleteCostParaType(costParaTypeForm);
			request.setAttribute("alertMessage", "删除成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "删除失败！");
			logger.error("删除计费参数类型失败!CostParaTypeAction.deleteCostParaType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("删除计费参数类型失败!CostParaTypeAction.deleteCostParaType()。");
		}
		return this.getCostParaTypeList(mapping, form, request, response);
	}
	
}
