package com.zc13.bkmis.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IPhoneCostService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：2:18:53 PM
 */
public class SetMaterialsManageAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private ISetMaterialManageService setmaterialsService;
	private IPhoneCostService phoneCostService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		phoneCostService = (IPhoneCostService)getBean("phoneCostService");
	}
	//显示材料设置
	public ActionForward showMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			List materialsList = new ArrayList();
			List<DTree> treeList = new ArrayList<DTree>();
			try {
				materialsList = setmaterialsService.getMaterialsList();
			}catch(Exception e){
				logger.error("tree的获得失败!SetMaterialsManageAction.showMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("tree的获得失败，SetMaterialsManageAction.showMaterials()!",e);
			}
			treeList.add(new DTree("1","材料设置信息","",""));
			for(int i = 0;i<materialsList.size();i++){
				DTree tree =  new DTree();
				DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
				tree.setId(dst.getId().toString());
				tree.setName(dst.getName());
				tree.setParentID(dst.getParentid().toString());
				tree.setUrl("setmaterials.do?method=selectMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString()+"&typeName="+dst.getName());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "setmaterials.do?method=selectMaterials");
			return mapping.findForward("MaterialsList");
	}
	//点击不同的类别显示不同信息
	public ActionForward selectMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			SetMaterialsForm smf = (SetMaterialsForm)form;
			try{
				//获取点击类别代号及名称,id
				String code = request.getParameter("Code");
				String name = request.getParameter("typeName");
				String strdmtId = request.getParameter("dmtId");
				//查询存储的名称
				DepotMaterialType dmType = new DepotMaterialType();
				String showName = "";
				List nameList = new ArrayList();
				List list = new ArrayList();
				if(strdmtId != null && strdmtId != ""){
					int dmtId = Integer.parseInt(strdmtId);
					smf.setDmtId(dmtId);
					//判断是否为根节点
					list = setmaterialsService.judgeNode(smf);
					//为了防止页面显示出乱码重新查一下名称
					if(dmtId != 0){
						dmType = setmaterialsService.selectName(dmtId);
						showName = dmType.getName();
					}
				}
//				String s = "";
//				if(name != "" && name != null){
//					s = new String(name.getBytes("iso-8859-1"),"utf-8");
//				}
				if(code != null && code !=""){
					smf.setType(code);
				}
				setmaterialsService.selectMaterials(smf,true);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == smf.getMaterialList() || smf.getMaterialList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/setmaterials.do?method=selectMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/setmaterials.do?method=selectMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
				
				request.setAttribute("typeCode", code);
//				if(nameList.size()>0 && nameList != null){
//					DepotMaterialType dmType = (DepotMaterialType)nameList.get(0);
//					request.setAttribute("typeName", dmType.getName());
//				}
				smf.setShowName(showName);
				request.setAttribute("typeName", showName);
				request.setAttribute("dmtId", strdmtId);
				request.setAttribute("judgeList", list.size());
				request.getSession().setAttribute("explortMaterialList", smf.getMaterialList());
			}catch(Exception e){
				logger.error("点击tree获得信息失败!SetMaterialsManageAction.selectMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，SetMaterialsManageAction.selectMaterials()!",e);
			}
			return mapping.findForward("mainFramJsp");
	}
	//删除
	public ActionForward delMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			//获取要删除的id
			String deleteIds = request.getParameter("delId");
			String[] idArray = deleteIds.split(",");
			int intId[] = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List<DepotMaterial> materNameList = new ArrayList<DepotMaterial>();
			//获取要删除类别的代号
			String typeCode = request.getParameter("typeCode");
			//获取点击类别保证删除后仍然在之前选中的类别
			String strdmtId = request.getParameter("dmtId");
			for(int i = 0;i<idArray.length;i++){
				ids[i] = Integer.parseInt(idArray[i]);
				materNameList = setmaterialsService.selectMaterName(ids);
			}
			for(int i = 0;i<idArray.length;i++){
				intId[i] = Integer.parseInt(idArray[i]);
				
				setmaterialsService.delMaterials(intId[i],userName);
			}
			
			response.sendRedirect("setmaterials.do?method=selectMaterials&Code="+typeCode+"&dmtId="+strdmtId);
			//return showMaterials(mapping,form,request,response);
			return null;
	}
	//跳转到添加页面
	public ActionForward addMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			SetMaterialsForm smf = (SetMaterialsForm)form;
			String select = request.getParameter("unit");
			//获取要添加的类别信息
			String typeCode = request.getParameter("typeCode");
			String strdmtId = request.getParameter("dmtId");
			//System.out.println(typeCode);
			try{
				setmaterialsService.selectUnit(smf);
			}catch(Exception e){
				logger.error("查询材料单位信息失败!SetMaterialsManageAction.addMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询材料单位信息失败，SetMaterialsManageAction.addMaterials()!",e);
			}
			request.setAttribute("select", select);
			request.setAttribute("type", typeCode);
			return mapping.findForward("add");
	}
	//执行添加操作
	public ActionForward doAddMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			SetMaterialsForm smf = (SetMaterialsForm)form;
			DepotMaterial dm = new DepotMaterial();
			String typeCode = smf.getType();
			System.out.println("======"+typeCode);
			try{
				BeanUtils.copyProperties(dm, smf);
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			writeLog(userName, Contants.ADD, "添加了名称为【"+smf.getName()+"】的材料信息",Contants.L_Depot,"材料设置");
			
			try{
				setmaterialsService.addMaterials(dm);
				//response.sendRedirect("setmaterials.do?method=selectMaterials&Code="+typeCode);
			}catch(Exception e){
				logger.error("添加材料单位信息失败!SetMaterialsManageAction.doAddMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("添加材料单位信息失败，SetMaterialsManageAction.doAddMaterials()!",e);
			}
			//return showMaterials(mapping,form,request,response);
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("add");
	}
	//验证添加的材料编号
	public ActionForward checkAddCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			String code = request.getParameter("code");
			boolean exist = false;
			try{
				exist = setmaterialsService.checkAddCode(code);
			}catch(Exception e){
				logger.error("验证材料编号失败!SetMaterialsManageAction.checkAddCode().详细信息：" + e.getMessage());
				throw new BkmisWebException("验证材料编号失败，SetMaterialsManageAction.checkAddCode()!",e);
			}
			if(exist){
				response.getWriter().println("1");
			}else{
				response.getWriter().println("0");
			}
			return null;
	}
	//验证添加的材料的名称
	public ActionForward checkAddName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String name = request.getParameter("name");
			boolean exist = false;
			try{
				exist = setmaterialsService.checkAddName(name);
			}catch(Exception e){
				logger.error("验证材料名称失败!SetMaterialsManageAction.checkAddName().详细信息：" + e.getMessage());
				throw new BkmisWebException("验证材料名称失败，SetMaterialsManageAction.checkAddName()!",e);
			}
			if(exist){
				response.getWriter().println("1");
			}else{
				response.getWriter().println("0");
			}
			return null;
	}
	//编辑界面
	public ActionForward editMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			SetMaterialsForm smf = (SetMaterialsForm)form;
			//获取要修改的id
			String strid = request.getParameter("id");
			int id = Integer.parseInt(strid);
			
			//获取要点击树的类别代码
			String typeCode = request.getParameter("typeCode");
			//查询出单位
			setmaterialsService.selectUnit(smf);
			//查询出要要修改的信息
			try{
				setmaterialsService.selectMaterialById(smf);
			}catch(Exception e){
				logger.error("查询要修改材料信息失败!SetMaterialsManageAction.editMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询要修改材料信息失败，SetMaterialsManageAction.editMaterials()!",e);
			}
			String string = smf.getType();
			request.setAttribute("typeCode", typeCode);
			request.setAttribute("tpCode", smf.getType());
			return mapping.findForward("edit");
	}
	//执行修改
	public ActionForward doEditMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			SetMaterialsForm smform = (SetMaterialsForm)form;

			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			smform.setUserName(userName);
			try{
				setmaterialsService.editMaterials(smform);
				//response.sendRedirect("setmaterials.do?method=selectMaterials&Code="+treeTypeCode);
			}catch(Exception e){
				logger.error("修改材料信息失败!SetMaterialsManageAction.doEditMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("修改材料信息失败，SetMaterialsManageAction.doEditMaterials()!",e);
			}
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("edit");
	}
	
	/**
	 * 导出材料信息列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Apr 10, 2012 
	 * Time:1:12:26 PM
	 */
	public ActionForward exportMaterialExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			SetMaterialsForm smf = (SetMaterialsForm)form;
			//获取要显示的材料信息
			//List list = (List)request.getSession().getAttribute("explortMaterialList");
			List list = setmaterialsService.selectAllMaterial(smf);
			//表头
			String[] cellHeader = Constant.EXCEL_SET_MATERIAL;
			String[] cellValue = Constant.EXCEL_SET_VALUE;
			// 定义文件名
			String sheetName = "材料设置信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
			//HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new CompactClient());
			response.setBufferSize(10000 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, "材料设置信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出材料设置excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//查看详细信息
	public ActionForward selectMaterialDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			SetMaterialsForm smf = (SetMaterialsForm)form;
			String strid = request.getParameter("id");
			int id = Integer.parseInt(strid);
			//查询单位
			List detailList = new ArrayList();
			try{
				setmaterialsService.selectUnit(smf);
				detailList = setmaterialsService.materialDetail(id);
			}catch(Exception e){
				logger.error("查询材料详细信息失败!SetMaterialsManageAction.selectMaterialDetail().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询材料详细信息失败，SetMaterialsManageAction.selectMaterialDetail()!",e);
			}
			request.setAttribute("detail", detailList);
			return mapping.findForward("detailMaterial");
	}
}
