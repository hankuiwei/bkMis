package com.zc13.bkmis.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.zc13.bkmis.form.ExteriorPerForm;
import com.zc13.bkmis.mapping.HrExteriorPersonnel;
import com.zc13.bkmis.service.IExteriorPersonnelService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 1, 2010
 * Time：3:19:04 PM
 */
public class ExteriorPersonnel extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IExteriorPersonnelService iexteriorpersonnelserivce;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		iexteriorpersonnelserivce = (IExteriorPersonnelService)getBean("iexteriorpersonnelserivce");
	}
	//显示员工信息
	public ActionForward showExteriorPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//对工作单位下拉列表的获取
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			exteriorperForm.setLpId(lpId);
			/** 到此为止*/
			iexteriorpersonnelserivce.selectWorkPlace(exteriorperForm);
			//获取下拉列表的选择的内容
			String select = request.getParameter("company"); 
			//显示留学人员信息
			try{
				iexteriorpersonnelserivce.selectExteriorPer(exteriorperForm);
				int totalcount = iexteriorpersonnelserivce.queryCounttotal(exteriorperForm);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == exteriorperForm.getExteriorperList() || exteriorperForm.getExteriorperList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/exterior.do?method=showExteriorPer", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/exterior.do?method=showExteriorPer", Integer.parseInt(GlobalMethod.NullToParam(exteriorperForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(exteriorperForm.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				//request.getSession().setAttribute("explortForeignPerList", exteriorperForm.getExteriorperList());
			}catch(Exception e){
				logger.error("加载功能列信息失败，ExteriorPersonnel.showExteriorPer()。详细信息："+e.getMessage());
				throw new BkmisWebException("加载功能列信息失败，ExteriorPersonnel.showExteriorPer()!",e);
			}
			request.setAttribute("select", select);
			return mapping.findForward("showExteriorPer");
	}
	//添加员工信息页面
	public ActionForward addExteriorPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//对工作单位下拉列表的获取
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			exteriorperForm.setLpId(lpId);
			/** 到此为止*/
			iexteriorpersonnelserivce.selectWorkPlace(exteriorperForm);
			//获取下拉列表的选择的内容
			String select = request.getParameter("company");
			request.setAttribute("select", select);
			saveToken(request);
			return mapping.findForward("addExteriorPer");
	}
	//执行添加操作
	public ActionForward addExterior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			exteriorperForm.setLpId(lpId);
			/** 到此为止*/
			
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			exteriorperForm.setUserName(userName);
			exteriorperForm.setLpId(lpId);
			//writeLog(userName, "添加留学人员信息", "添加留学人员信息为"+exteriorperForm.getName()+"信息",Contants.HR);
			
			if (isTokenValid(request, true)) {
				try{
					iexteriorpersonnelserivce.addExteriorPer(exteriorperForm);
				}catch(Exception e){
					logger.error("留学人员信息添加失败!ExteriorPersonnel.addExterior()。详细信息：" + e.getMessage());
					throw new BkmisWebException("留学人员信息添加失败!ExteriorPersonnel.addExterior()!",e);
				}
			}
			try{
				response.sendRedirect("exterior.do?method=showExteriorPer");
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
	//上传员工图片
	public ActionForward uploadPic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			FormFile myFile = exteriorperForm.getMyfile();
			String fileName = myFile.getFileName();//获取文件名
			if(myFile.toString() != ""){
				String p = request.getSession().getServletContext().getRealPath("");
				System.out.println("p = " + p);
				File uploadPath = new File(p+"\\upExteriorPer");//上传文件目录
			    if (!uploadPath.exists()) {
			       uploadPath.mkdirs();
			    }
			    File realFile = new File(uploadPath + "/" + myFile.getFileName());
			    fileName = myFile.getFileName();
			    StringBuffer realPath = new StringBuffer(p);
			    realPath.append("\\");
			    realPath.append("upExteriorPer");
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
	//删除图片
	public ActionForward delPic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
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
	//跳转到编辑页面
	public ActionForward editPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//对工作单位下拉列表的获取
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			iexteriorpersonnelserivce.selectWorkPlace(exteriorperForm);
			//获取要修改员工的id
			String strid = request.getParameter("Id");
			int id = Integer.parseInt(strid);
			//获取下拉列表的选择的内容
			String select = request.getParameter("company");
			//查询员工信息在页面上显示
			exteriorperForm.setId(id);
			try{
				iexteriorpersonnelserivce.selectExteriorById(exteriorperForm);
			}catch(Exception e){
				logger.error("查看人员信息添加失败!ExteriorPersonnel.editPer()。详细信息：" + e.getMessage());
				throw new BkmisWebException("查看人员信息添加失败!ExteriorPersonnel.editPer()!",e);
			}
			request.setAttribute("select", select);
			return mapping.findForward("editPer");
	}
	//执行修改
	public ActionForward doeditPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			exteriorperForm.setLpId(lpId);
			/** 到此为止*/
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			exteriorperForm.setUserName(userName);
			try{
				iexteriorpersonnelserivce.updateExterior(exteriorperForm);
			}catch(Exception e){
				logger.error("修改人员信息添加失败!ExteriorPersonnel.doeditPer()。详细信息：" + e.getMessage());
				throw new BkmisWebException("修改人员信息添加失败!ExteriorPersonnel.doeditPer()!",e);
			}
			try{
				response.sendRedirect("exterior.do?method=showExteriorPer");
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
	//删除留学人员信息
	public ActionForward delPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
	
			//获取要删除的id
			String deleteIds = request.getParameter("Id");
			String[] idArray = deleteIds.split(",");
			int idInt[] = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<HrExteriorPersonnel> exterNameList = new ArrayList<HrExteriorPersonnel>();
			for(int i = 0;i<idArray.length;i++){
				ids[i] = Integer.parseInt(idArray[i]);
				exterNameList = iexteriorpersonnelserivce.selectNameById(ids);
			}
			for(int i = 0;i<idArray.length;i++){
				idInt[i] = Integer.parseInt(idArray[i]);
				iexteriorpersonnelserivce.delExterior(idInt[i]);
			}
			
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			for(HrExteriorPersonnel per : exterNameList){
				writeLog(userName, Contants.DELETE, "删除了姓名为【"+per.getName()+"】的留学人员信息",Contants.L_HR,"留学人员");
			}
			try{
				response.sendRedirect("exterior.do?method=showExteriorPer");
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
	//查看留学人员的信息
	public ActionForward doViewPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			editPer(mapping,form,request,response);
			return mapping.findForward("viewDetail");
	}
	//导出报表
	public ActionForward exportForeignPerExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取留学人员信息
				//List list = (List)request.getSession().getAttribute("explortForeignPerList");
				ExteriorPerForm exteriorperForm = (ExteriorPerForm)form;
				List list = iexteriorpersonnelserivce.selectAllPersonal(exteriorperForm);
				//表头
				String[] cellHeader = Constant.EXCEL_FORIGN_PERSON;
				String[] cellValue = Constant.EXCEL_FORIGN_VALUE;
				//定义文件名
				String sheetName = "留学人员信息列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new HrExteriorPersonnel());
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "留学人员信息列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出留学人员excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
	//检查图片名称
	public ActionForward checkPicName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			String picName = request.getParameter("picName");
			
			String p = request.getSession().getServletContext().getRealPath("");
			System.out.println("p = " + p);
		    
			List picList = new ArrayList();
			try{
				picList = iexteriorpersonnelserivce.checkPicName(picName,p);
				if(picList != null && picList.size()>0){
						response.getWriter().println("1");
				}
			}catch(Exception e){
				logger.error("检查图片名信息失败!ExteriorPersonnel.doeditPer()。详细信息：" + e.getMessage());
				throw new BkmisWebException("检查图片名信息失败!ExteriorPersonnel.doeditPer()!",e);
			}
			return null;
	}
}
