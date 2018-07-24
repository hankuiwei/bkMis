package com.zc13.bkmis.action;

import java.io.IOException;
import java.sql.SQLException;
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

import com.zc13.bkmis.form.DepotSupplierForm;
import com.zc13.bkmis.mapping.DepotSupplier;
import com.zc13.bkmis.service.IDepotSupplierService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 21, 2010
 * Time：3:21:45 PM
 */
public class DepotSupplierAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IDepotSupplierService isupplierService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		isupplierService = (IDepotSupplierService)getBean("isupplierService");
	}
	//显示供应商管理的方法
	public ActionForward showSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			DepotSupplierForm dsf = (DepotSupplierForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				dsf.setLpId(lpId);
				/** 到此为止 */
				//查询显示供应商信息
				isupplierService.selectSupplierList(dsf);
				//获取总条数
				int totalcount = isupplierService.queryCount(dsf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == dsf.getSupplierList() || dsf.getSupplierList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/supplier.do?method=showSupplier", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/supplier.do?method=showSupplier", Integer.parseInt(GlobalMethod.NullToParam(dsf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(dsf.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("查询供应商信息加载失败，DepotSupplierAction.showSupplier()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询供应商信息加载失败，DepotSupplierAction.showSupplier()!",e);
			}
			return mapping.findForward("showSupplier");
	}
	//跳转到添加页面
	public ActionForward addSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			saveToken(request);
			return mapping.findForward("addSupplier");
	}
	//执行添加操作
	public ActionForward doAddSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			DepotSupplierForm dsf = (DepotSupplierForm)form;
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			dsf.setLpId(lpId);
			/** 到此为止 */
			if (isTokenValid(request, true)) {
				try{
					isupplierService.addSupplier(dsf);
					
					//加入日志管理
					Map map = (Map)request.getSession().getAttribute("userInfo");
					String userName = GlobalMethod.NullToSpace(map.get("username").toString());
					writeLog(userName, Contants.ADD, "添加了名称为【"+dsf.getName()+"】的供应商信息", Contants.L_Depot,"供应商");
					
				}catch(Exception e){
					logger.error("添加供应商信息加载失败，DepotSupplierAction.doAddSupplier()。详细信息："+e.getMessage());
					throw new BkmisWebException("添加供应商信息加载失败，DepotSupplierAction.doAddSupplier()!",e);
				}
			}
			response.sendRedirect("supplier.do?method=showSupplier");
		    return null;
	}
	//跳转到供应商信息的编辑界面
	public ActionForward editSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			DepotSupplierForm dsf = (DepotSupplierForm)form;
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			dsf.setLpId(lpId);
			/** 到此为止 */
			String strId = request.getParameter("editId");
			int id = Integer.parseInt(strId);
			dsf.setId(id);
			try{
				isupplierService.editSupplier(dsf);
			}catch(Exception e){
				logger.error("查询要编辑供应商信息加载失败，DepotSupplierAction.editSupplier()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询要编辑供应商信息加载失败，DepotSupplierAction.editSupplier()!",e);
			}
			return mapping.findForward("edidSupplier");
	}
	//执行修改供应商信息操作
	public ActionForward doEditSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	
			DepotSupplierForm dsf = (DepotSupplierForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				dsf.setLpId(lpId);
				/** 到此为止 */
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				dsf.setUserName(userName);
				isupplierService.doEditSupplier(dsf);
			}catch(Exception e){
				logger.error("编辑供应商信息加载失败，DepotSupplierAction.doEditSupplier()。详细信息："+e.getMessage());
				throw new BkmisWebException("编辑供应商信息加载失败，DepotSupplierAction.doEditSupplier()!",e);
			}
			response.sendRedirect("supplier.do?method=showSupplier");
			return null;
	}
	//执行删除操作
	public ActionForward delSupplier(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			String deleteIds = request.getParameter("delIds");
			String[] idArray = deleteIds.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<DepotSupplier> supplierList = new ArrayList<DepotSupplier>();
			for(int i = 0;i<idArray.length;i++){
				ids[i] = Integer.parseInt(idArray[i]);
				supplierList = isupplierService.selectSupplier(ids);
			}
			for(int i = 0;i<idInt.length;i++){
				idInt[i] = Integer.parseInt(idArray[i]);
				isupplierService.delSupplier(idInt[i]);
			}
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			for(DepotSupplier ds : supplierList){
				writeLog(userName, Contants.DELETE, "删除了名称为【"+ds.getName()+"】的供应商信息", Contants.L_Depot,"供应商");
			}
			response.sendRedirect("supplier.do?method=showSupplier");
			return null;
	}
}
