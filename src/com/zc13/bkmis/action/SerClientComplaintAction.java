package com.zc13.bkmis.action;

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

import com.zc13.bkmis.form.SerClientComplaintForm;
import com.zc13.bkmis.mapping.SerClientComplaint;
import com.zc13.bkmis.service.ISerClientComplaintService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author qinyantao
 * Date：Dec 15, 2010
 * Time：11:05:50 AM
 */
public class SerClientComplaintAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ISerClientComplaintService  iSerClientComplaintService= null;
	/** 从spring容器里得到iSerClientComplaintService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iSerClientComplaintService = (ISerClientComplaintService)getBean("ISerClientComplaintService");
	}
	/** 查询客户投诉列表 */
	public ActionForward getList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			List<SerClientComplaint> list = iSerClientComplaintService.getClientList(form2,true);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/complaint.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/complaint.do?method=getList", Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), form2.getTotalcount());
			}
			request.setAttribute("pagination", htmlPagination);
		}catch(Exception e){
			logger.error("高级查询失败!SerClientComplaintAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，SerClientComplaintAction.getList()!",e);
		}
		this.my_saveToken(request);
		String forward = "list";
		return mapping.findForward(forward);
	}
	
	//增加客户投诉
	@SuppressWarnings("unchecked")
	public ActionForward addComplaint(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form2.setUserName(userName);
			iSerClientComplaintService.save(form2);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
			logger.error("添加客户报修失败!SerClientComplaintAction.addComplaint(form2).详细信息：" + e.getMessage());
			//throw new BkmisWebException("添加客户报修失败!SerClientComplaintAction.addComplaint(form2)!",e);
		}
		return mapping.findForward("add");
	}
	
	//处理客户投诉
	@SuppressWarnings("unchecked")
	public ActionForward dealComplaint(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		if(!this.my_isTokenValid(request)){
			return this.getList(mapping, form, request, response);
		}
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form2.setUserName(userName);
			iSerClientComplaintService.dealComplaint(form2);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
			logger.error("处理客户报修失败!SerClientComplaintAction.dealComplaint(form2).详细信息：" + e.getMessage());
			//throw new BkmisWebException("处理客户报修失败!SerClientComplaintAction.dealComplaint(form2)!",e);
		}
		return this.getList(mapping, form, request, response);
	}
	
	//编辑客户投诉
	@SuppressWarnings("unchecked")
	public ActionForward editComplaint(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form2.setUserName(userName);
			iSerClientComplaintService.editComplaint(form2);
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
			logger.error("编辑客户报修失败!SerClientComplaintAction.editComplaint(form2).详细信息：" + e.getMessage());
			//throw new BkmisWebException("编辑客户报修失败!SerClientComplaintAction.editComplaint(form2)!",e);
		}
		return mapping.findForward("edit");
	}
	
	//删除客户投诉
	@SuppressWarnings("unchecked")
	public ActionForward delComplaint(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		if(!this.my_isTokenValid(request)){
			return this.getList(mapping, form, request, response);
		}
		String str = request.getParameter("ids");
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		try{
			String[] ids = str.split(",");
			for(int i=0;i<ids.length;i++){
				iSerClientComplaintService.delComplaint(ids[i],userName);
			}
			request.setAttribute("message", "操作成功！");
		}catch(Exception e){
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
			logger.error("删除客户报修失败!SerClientComplaintAction.delComplaint(form2).详细信息：" + e.getMessage());
			//throw new BkmisWebException("删除客户报修失败!SerClientComplaintAction.delComplaint(form2)!",e);
		}
		return this.getList(mapping, form, request, response);
	}
	
	//获得客户投诉信息
	public ActionForward getComplaint(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		String id = request.getParameter("id");
		SerClientComplaint bean = null;
		String forward = request.getParameter("forward");
		try{
			bean = iSerClientComplaintService.getComplaint(id);
		}catch(Exception e){
			logger.error("查询客户报修失败!SerClientComplaintAction.getComplaint(id).详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户报修失败!SerClientComplaintAction.getComplaint(id)!",e);
		}
		this.my_saveToken(request);
		request.setAttribute("bean", bean);
		return mapping.findForward(forward);
	}
	//导出报表
	public ActionForward explorClient(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		SerClientComplaintForm form2 = (SerClientComplaintForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			List list = iSerClientComplaintService.getClientList(form2, false);
			//表头
			String[] cellHeader = Constant.EXCEL_COMPLAIN_DETAIL;
			String[] cellValue = Constant.EXCEL_COMPLAIN_VALUE;
			//定义文件名 
			String sheetName = "客户投诉";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new SerClientComplaint());
			
			response.setBufferSize(100*1024);
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "客户投诉"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			log.error("导出客户投诉excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}

