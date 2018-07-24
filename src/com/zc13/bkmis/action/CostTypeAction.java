package com.zc13.bkmis.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CostTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.service.ICostTypeService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 费用类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:12:21 PM
 */
public class CostTypeAction extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private ICostTypeService costTypeService= null;
	
	@Override
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		costTypeService = (ICostTypeService)getBean("costTypeService");
	}
	/**
	 * 获得费用类型列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCostTypeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostTypeForm costTypeForm = (CostTypeForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		costTypeForm.setLpId(lpId);
		/** 到此为止*/
		String typeName = java.net.URLDecoder.decode(GlobalMethod.NullToSpace(costTypeForm.getTypeName()),"UTF-8");
		costTypeForm.setTypeName(typeName);
		String forward = "toShowCostTypeList";
		List<CCosttype> costTypeList = new ArrayList<CCosttype>();//费用类型列表
		List<SysCode> gaugeList = new ArrayList<SysCode>();//表具列表
		List<CCostparatype> costparatypeList = new ArrayList<CCostparatype>();//计费参数类型列表
		try {
			costTypeList = costTypeService.getCostTypeList(costTypeForm);//获得费用类型列表
			//添加分页信息
			String htmlPagination = "&nbsp;";
			String params = "&typeName="+typeName;//
			if (null == costTypeList || costTypeList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/costType.do?method=getCostTypeList"+params, 5, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/costType.do?method=getCostTypeList"+params, Integer.parseInt(GlobalMethod.NullToParam(costTypeForm.getPagesize(),"5")),
						Integer.parseInt(GlobalMethod.NullToParam(costTypeForm.getCurrentpage(),"1")), costTypeList.get(0).getTotalcount());
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (RuntimeException e) {
			logger.error("费用类型信息查询失败!CostTypeAction.getCostTypeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("费用类型信息查询失败!CostTypeAction.getCostTypeList()。");
		}
		try {
			gaugeList = costTypeService.getGaugeList();//获得表具列表
		} catch (RuntimeException e) {
			logger.error("表具信息查询失败!CostTypeAction.getGaugeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具信息查询失败!CostTypeAction.getGaugeList()。");
		}
		try {
			costparatypeList = costTypeService.getCostparatypeList();//获得计费参数类型列表
		} catch (RuntimeException e) {
			logger.error("计费参数类型信息查询失败!CostTypeAction.getCostparatypeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("计费参数类型信息查询失败!CostTypeAction.getCostparatypeList()。");
		}
		request.setAttribute("startNum", costTypeForm.getStartIndex());
		request.setAttribute("costTypeList", costTypeList);
		request.setAttribute("gaugeList", gaugeList);
		request.setAttribute("costparatypeList", costparatypeList);
		request.setAttribute("typeName", costTypeForm.getTypeName());
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新 
		return mapping.findForward(forward);
	}
	
	/**
	 * 保存或更新费用类型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editCostType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostTypeForm costTypeForm = (CostTypeForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		costTypeForm.setLpId(lpId);
		/** 到此为止*/
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getCostTypeList(mapping, form, request, response);
		}
		try {
			costTypeService.saveOrUpdateCostType(costTypeForm);
			request.setAttribute("alertMessage", "保存成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "保存失败！");
			logger.error("保存或更新费用类型失败!CostTypeAction.editCostType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("保存或更新费用类型失败!CostTypeAction.editCostType()。");
		}
		return this.getCostTypeList(mapping, form, request, response);
	}
	
	/**
	 * 删除费用类型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCostType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CostTypeForm costTypeForm = (CostTypeForm)form;
		//使用自定义token机制防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getCostTypeList(mapping, form, request, response);
		}
		try {
			costTypeService.deleteCostType(costTypeForm);
			request.setAttribute("alertMessage", "删除成功！");
		} catch (RuntimeException e) {
			request.setAttribute("alertMessage", "删除失败！");
			logger.error("删除费用类型失败!CostTypeAction.deleteCostType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("删除费用类型失败!CostTypeAction.deleteCostType()。");
		}
		return this.getCostTypeList(mapping, form, request, response);
	}
	
}
