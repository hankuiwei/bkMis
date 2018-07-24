package com.zc13.bkmis.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.apache.struts.upload.FormFile;

import com.zc13.bkmis.form.AttendanceForm;
import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.service.IAttendanceService;
import com.zc13.bkmis.service.IPersonnelService;
import com.zc13.bkmis.service.ISerClientMaintainService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:38:39 AM
 */
public class PersonnelAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IPersonnelService ipersonnelServicce;
	private IAttendanceService attendanceService;
	private ISerClientMaintainService  serClientMaintainService= null;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		ipersonnelServicce = (IPersonnelService)getBean("ipersonnelService");
		attendanceService = (IAttendanceService)getBean("attendanceService");
		serClientMaintainService = (ISerClientMaintainService)getBean("ISerClientMaintainService");
	}
	
	//显示员工信息方法
	public ActionForward showPersonnel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

		PersonnelForm personnelForm = (PersonnelForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		personnelForm.setLpId(lpId);
		/** 到此为止*/
		//对部门下拉列表内容的查询
		ipersonnelServicce.selectDepartment(personnelForm);
		
		//对员工信息的查询用于在页面的显示
		try{
			ipersonnelServicce.showPersonnel(personnelForm,true);
			//获取总条数
			int totalcount = ipersonnelServicce.queryCounttotal(personnelForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == personnelForm.getPersonnelList() || personnelForm.getPersonnelList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/personnel.do?method=showPersonnel", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/personnel.do?method=showPersonnel", Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			//request.getSession().setAttribute("explortPersonList", personnelForm.getPersonnelList());
		}catch(Exception e){
			logger.error("加载功能列信息失败，FunctionListAction.showPersonnel()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载功能列信息失败，FunctionListAction.showPersonnel()!",e);
		}
		this.my_saveToken(request);
		return mapping.findForward("showPersonnel");
	}
	
	/**
	 * 获取工人工作情况列表
	 */
	public ActionForward getWorkingConditions4Personnel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

		PersonnelForm personnelForm = (PersonnelForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		personnelForm.setLpId(lpId);
		/** 到此为止*/
		personnelForm.setCx_isDispatch("1");//查询是派遣员工的工人信息
		//根据考勤机的信息更新数据库
		AttendanceForm attendanceForm = new AttendanceForm();
		attendanceForm.setLpId(lpId);
		attendanceService.updatePersonnelAndRecord(attendanceForm);
		//对部门下拉列表内容的查询
		ipersonnelServicce.selectDepartment(personnelForm);
		
		//对员工信息的查询用于在页面的显示
		try{
			ipersonnelServicce.getWorkingConditions4Personnel(personnelForm,true);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == personnelForm.getPersonnelList() || personnelForm.getPersonnelList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/personnel.do?method=getWorkingConditions4Personnel", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/personnel.do?method=getWorkingConditions4Personnel", Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")), personnelForm.getTotalcount());
			}
			request.setAttribute("pagination", htmlPagination);
			//request.getSession().setAttribute("explortPersonList", personnelForm.getPersonnelList());
		}catch(Exception e){
			logger.error("加载功能列信息失败，FunctionListAction.showPersonnel()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载功能列信息失败，FunctionListAction.showPersonnel()!",e);
		}
		this.my_saveToken(request);
		return mapping.findForward("showDispatchPersonnel");
	}
	
	/**
	 * 获得可以被派遣的人员列表
	 */
	public ActionForward getDispatchPersonnel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		PersonnelForm personnelForm = (PersonnelForm)form;
		String ids = GlobalMethod.NullToSpace(request.getParameter("ids"));
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		//根据考勤机的信息更新数据库
		AttendanceForm attendanceForm = new AttendanceForm();
		attendanceForm.setLpId(lpId);
		attendanceService.updatePersonnelAndRecord(attendanceForm);
		
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));
		personnelForm.setLpId(lpId);
		personnelForm.setCx_isDispatch(Contants.ISDISPATCH);
		String cx_isOut = personnelForm.getCx_isOut();
		try {
			//转码
			personnelForm.setCx_isOut(java.net.URLDecoder.decode(cx_isOut, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//personnelForm.setCx_isInPost(Contants.ISINPOST);
		//personnelForm.setCx_isOut(Contants.ISNOTOUT);
		ipersonnelServicce.getDispatchPersonnel(personnelForm,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		String params = "";//"&cx_personnelName="+GlobalMethod.NullToSpace(personnelForm.getCx_personnelName())+"&cx_personnelCode="+GlobalMethod.NullToSpace(personnelForm.getCx_personnelCode())+"&ids="+","+ids+",";
		if (null == personnelForm.getPersonnelList() || personnelForm.getPersonnelList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/personnel.do?method=getDispatchPersonnel"+params, 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/personnel.do?method=getDispatchPersonnel"+params, Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")), personnelForm.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		
		request.setAttribute("ids", ","+ids+",");
		request.setAttribute("forward", forward);
		return mapping.findForward(forward);
	}
	
	
	//删除员工信息
	public ActionForward delPersonnel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		PersonnelForm personnelForm = (PersonnelForm)form;
		if(!this.my_isTokenValid(request)){
			return this.showPersonnel(mapping, form, request, response);
		}
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		personnelForm.setUserName(userName);
		personnelForm.setLpId(lpId);
		try {
			ipersonnelServicce.delPersonnel(personnelForm);
			request.setAttribute("message", "删除成功！");
		} catch (BkmisServiceException e) {
			request.setAttribute("message", "删除失败！");
			e.printStackTrace();
		}
		return this.showPersonnel(mapping, form, request, response);
	}
	//跳转到添加页面
	public ActionForward addPersonnelFace(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			//对部门下拉列表内容的查询
			PersonnelForm personnelForm = (PersonnelForm)form;		
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			personnelForm.setLpId(lpId);
			/** 到此为止*/
			ipersonnelServicce.selectDepartment(personnelForm);
			this.my_saveToken(request);
			return mapping.findForward("addPersonnel");
	}
	//添加员工信息
	public ActionForward addPersonnel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			PersonnelForm personnelForm = (PersonnelForm)form;
			if(!this.my_isTokenValid(request)){
				return this.showPersonnel(mapping, form, request, response);
			}
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			personnelForm.setLpId(lpId);
			/** 到此为止*/
			String userName = GlobalMethod.NullToSpace(map1.get("username").toString());
			personnelForm.setUserName(userName);
			//对员工信息及合同的添加
			try{
				ipersonnelServicce.addPersonnel(personnelForm);
				request.setAttribute("message", "保存成功！");
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("message", "保存失败！");
				logger.error("员工信息添加失败!PersonnelAction.addPersonnel()。详细信息：" + e.getMessage());
				//throw new BkmisWebException("员工信息添加失败!PersonnelAction.addPersonnel()!",e);
			}
			return this.showPersonnel(mapping, form, request, response);
	}
	//上传员工图片
	public ActionForward upLoad(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			PersonnelForm personnelForm = (PersonnelForm)form;
			FormFile myFile = personnelForm.getMyfile();
			String fileName = myFile.getFileName();//获取文件名
//			//截取后缀名
//			int index = fileName.lastIndexOf(".");
//			String type = fileName.substring(index + 1);
//			fileName = type.toLowerCase();
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+myFile.toString());
			if(myFile.toString() != ""){
				String p = request.getSession().getServletContext().getRealPath("");
				System.out.println("p = " + p);
				File uploadPath = new File(p+"\\uploadPerFile");//上传文件目录
			    if (!uploadPath.exists()) {
			       uploadPath.mkdirs();
			    }
			    File realFile = new File(uploadPath + "/" + myFile.getFileName());
			    fileName = myFile.getFileName();
			    StringBuffer realPath = new StringBuffer(p);
			    realPath.append("\\");
			    realPath.append("uploadPerFile");
			    realPath.append("\\");
			    realPath.append(fileName);
			    FileOutputStream fos = new FileOutputStream(realFile);
			    System.out.println(realFile);
				fos.write(myFile.getFileData());
				fos.flush();
				fos.close();
				String path = realPath.toString();
				response.getWriter().println(path);
			}
			return null;
	}
	//跳到修改员工界面
	public ActionForward editPersonnelFace(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			//对部门下拉列表内容的查询
			PersonnelForm personnelForm = (PersonnelForm)form;		
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			personnelForm.setLpId(lpId);
			/** 到此为止*/
			ipersonnelServicce.selectDepartment(personnelForm);
			
			//对员工信息的查询用于在页面的显示
			try{
				ipersonnelServicce.selectPersonnelById(personnelForm);
			}catch(Exception e){
				logger.error("员工编辑信息查询失败!PersonnelAction.editPersonnelFace()。详细信息：" + e.getMessage());
				throw new BkmisWebException("员工编辑信息查询失败!editPersonnelFace()!",e);
			}
			this.my_saveToken(request);
			return mapping.findForward("edit");
	}
	//执行员工修改
	public ActionForward editPersonnel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			if(!this.my_isTokenValid(request)){
				return this.showPersonnel(mapping, form, request, response);
			}
			PersonnelForm personnelForm = (PersonnelForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			personnelForm.setLpId(lpId);
			/** 到此为止*/
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			personnelForm.setUserName(userName);

			//执行修改
			try{
				ipersonnelServicce.updatePersonnel(personnelForm);
				request.setAttribute("message", "修改成功！");
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("message", "修改失败！");
				logger.error("员工编辑信息失败!PersonnelAction.editPersonnel()。详细信息：" + e.getMessage());
				//throw new BkmisWebException("员工编辑信息失败!editPersonnel()!",e);
			}
			return this.showPersonnel(mapping, form, request, response);
	}
	
	/**
	 * 根据工人id查询工人派工明细
	 */
	public ActionForward doViewWorkingConditionsDetail(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		PersonnelForm personnelForm = (PersonnelForm)form;
		this.editPersonnelFace(mapping, form, request, response);
		SerClientMaintainForm form2 = new SerClientMaintainForm();
		form2.setWorkBegindate(personnelForm.getCx_starttime());
		form2.setWorkEnddate(personnelForm.getCx_endtime());
		form2.setCx_sendedMan(request.getParameter("cx_sendedMan"));
		form2.setSelstatus(Contants.REPAIR_COMPLETED);
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		/** 到此为止*/
		form2.setLpId(lpId);
		//报修列表
		List<SerClientMaintain> list = null;
		list = serClientMaintainService.getClientList(form2,false);
		//将sendedMan的id替换为name
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				SerClientMaintain s = list.get(i);
				String sendedMan = s.getSendedMan();
				String sendedManName = ipersonnelServicce.getNamesByPersonnelIds(sendedMan);
				s.setSendedMan(sendedManName);
			}
		}
		personnelForm.setSerClientMainList(list);
		return mapping.findForward("viewWorkingConditionsDetail");
	}
	
	//上传文件
	public ActionForward upLoadFile(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			PersonnelForm personnelForm = (PersonnelForm)form;
			FormFile myFile = personnelForm.getMyfile();
			String fileName = myFile.getFileName();
			//System.out.println("========"+fileName);
//			int index = fileName.lastIndexOf(".");
//			String type = fileName.substring(index + 1);
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+myFile.toString());
			if(myFile.toString() != ""){
				String p = request.getSession().getServletContext().getRealPath("");
				System.out.println("p = " + p);
				File uploadPath = new File(p+"\\uploadFile");//上传文件目录
			    if (!uploadPath.exists()) {
			       uploadPath.mkdirs();
			    }
			    File realFile = new File(uploadPath + "/" + myFile.getFileName());
			    StringBuffer realPath = new StringBuffer(p);
			    realPath.append("\\");
			    realPath.append("uploadFile");
			    realPath.append("\\");
			    realPath.append(fileName);
			    FileOutputStream fos = new FileOutputStream(realFile);
			    System.out.println(realFile);
				fos.write(myFile.getFileData());
				fos.flush();
				fos.close();
				String path = realPath.toString();
				//response.getWriter().println(path+"-"+fileName);
				response.getWriter().println(path);
			}
			return null;
	}
	public ActionForward delPic(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			String path = request.getParameter("path");
			File file = new File(path);
			boolean test = file.exists();
			System.out.println(file.exists());
			if(!file.exists()){
				System.out.println(test);
				response.getWriter().println("1");
				System.out.println("图片路径不存在!");
			}else{
				file.delete();
				response.getWriter().println("0");
			}
			
			return null;
	}
	//对现有图片的删除
	public ActionForward delExitsPic(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			//对编辑中现有图片的修改
			String exitsPath = request.getParameter("imgurl");
			if(exitsPath != null || exitsPath !=""){
				File exitsFile = new File(exitsPath);
				exitsFile.delete();
				response.getWriter().println("0");
			}
			return null;
	}
	public ActionForward delFile(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			
			String path = request.getParameter("path");
			request.setAttribute("my_Rtoken", request.getParameter("my_Rtoken"));
			File file = new File(path);
			file.delete();
			return null;
	}
	//查看员工的详细信息
	public ActionForward doViewDetail(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
			editPersonnelFace(mapping,form,request,response);
			return mapping.findForward("viewDetail");
	}
	//导出报表
	public ActionForward exportPersonExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			try{
				PersonnelForm personnelForm = (PersonnelForm)form;		
				List list = ipersonnelServicce.showPersonnel(personnelForm,false);
				//表头
				String[] cellHeader = Constant.EXCEL_PERSONAL_DETAIL;
				String[] cellValue = Constant.EXCEL_PERSONAL_VALUE;
				//定义文件名
				String sheetName = "员工信息列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "员工信息列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出员工excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * 工人工作情况信息导出
	 */
	public ActionForward exportWorkingConditions4PersonExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取员工信息
				//List list = (List)request.getSession().getAttribute("explortPersonList");
				PersonnelForm personnelForm = (PersonnelForm)form;		
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				personnelForm.setLpId(lpId);
				/** 到此为止*/
				personnelForm.setCx_isDispatch("1");//查询是派遣员工的工人信息
				List list = ipersonnelServicce.getWorkingConditions4Personnel(personnelForm,false);
				//表头
				String[] cellHeader = Constant.EXCEL_WCPERSONAL_DETAIL;
				String[] cellValue = Constant.EXCEL_WCPERSONAL_VALUE;
				//定义文件名
				String sheetName = "工人工作情况列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "工人工作情况列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出员工excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * 个人维修记录的导出报表
	 */
	public ActionForward exportExcel4PersonalWorkingConditions(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PersonnelForm personnelForm = (PersonnelForm)form;
		this.editPersonnelFace(mapping, form, request, response);
		SerClientMaintainForm form2 = new SerClientMaintainForm();
		form2.setWorkBegindate(personnelForm.getCx_starttime());
		form2.setWorkEnddate(personnelForm.getCx_endtime());
		form2.setCx_sendedMan(request.getParameter("cx_sendedMan"));
		form2.setSelstatus(Contants.REPAIR_COMPLETED);
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		/** 到此为止*/
		form2.setLpId(lpId);
		//报修列表
		List<SerClientMaintain> list = null;
		try {
			list = serClientMaintainService.getClientList(form2,false);
			//表头
			String[] cellHeader = Constant.EXCEL_CLIENT_DETAIL;
			String[] cellValue = Constant.EXCEL_CLIENT_VALUE;
			//定义文件名
			String sheetName = personnelForm.getPersonnel().getPersonnelName()+"的派工明细";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new SerClientMaintain());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, sheetName+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//检查图片名
	public ActionForward checkPicName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			String picName = request.getParameter("picName");
			String p = request.getSession().getServletContext().getRealPath("");
			System.out.println("p = " + p);
			
		    List picList = new ArrayList();
		    try{
		    	picList = ipersonnelServicce.selectPicName(picName,p);
		    	if(picList.size()>0 && picList != null){
		    		response.getWriter().println("1");
		    	}
		    }catch(Exception e){
		    	logger.error("查询图片名信息失败，FunctionListAction.checkPicName()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询图片名信息失败，FunctionListAction.checkPicName()!",e);
		    }
			return null;
	}
	//检查上传文件名称
	public ActionForward checkFileName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			String fileName = request.getParameter("fileName");
			String p = request.getSession().getServletContext().getRealPath("");
			System.out.println("p = " + p);
		    
		    List fileList = new ArrayList();
		    try{
		    	fileList = ipersonnelServicce.selectFileName(fileName,p);
		    	if(null != fileList && fileList.size()>0){
		    		response.getWriter().println("1");
		    	}
		    }catch(Exception e){
		    	logger.error("查询附件名信息失败，FunctionListAction.checkFileName()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询附件名信息失败，FunctionListAction.checkFileName()!",e);
		    }
			return null;
	}
}
