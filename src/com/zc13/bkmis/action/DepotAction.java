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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.TypeForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IDepotService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author Administrator
 * Date：Dec 3, 2010
 * Time：9:06:14 AM
 */
public class DepotAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IDepotService idepotservice;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		idepotservice = (IDepotService)getBean("idepotservice");
	}

	//根据树的id来显示点击不同节点显示该节点的内容
	public ActionForward searchById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

			TypeForm typeForm = (TypeForm)form;	
			//int id = Integer.parseInt(request.getParameter("id")==null?"3":request.getParameter("id"));
			List codeList = new ArrayList();
			String strid = GlobalMethod.NullToSpace(request.getParameter("id"));
			if(strid != "0"){
				if(!"".equals(strid) && null != strid){
					int id = Integer.parseInt(strid);
					List list = null;
					typeForm.setId(id);
					try{
						codeList = idepotservice.selectTypeCode(typeForm);//知道这个list是干嘛的吗？告诉你，是tm的为了判断能不能删除的，谁告诉你不能删除了？
																			//2011-6-13日写，还没有改
						list = idepotservice.getChildList(id);
						request.setAttribute("list", list);
						//request.setAttribute("name", strId);
						request.setAttribute("codeList", codeList.size());
					}catch(Exception e){
						logger.error("点击tree的获得信息失败!DepotAction.searchById().详细信息：" + e.getMessage());
						throw new BkmisWebException("点击tree的获得信息失败，DepotAction.searchById()!",e);
					}
				}
			}
			return mapping.findForward("MaterightJsp");
	}
	//显示材料类别设置
	@SuppressWarnings("unchecked")
	public ActionForward showMaterialsTpey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String strid = GlobalMethod.NullToSpace(request.getParameter("id"));
			int id = 0;
			if(!"".equals(strid) && null != strid){
				id = Integer.parseInt(strid);
			}
			List materialsList = null;
			List<DTree> treeList = new ArrayList<DTree>();
			try {
				materialsList = idepotservice.getMaterialsList();
			}catch(Exception e){
				logger.error("tree的获得失败!DepotAction.getTree().详细信息：" + e.getMessage());
				throw new BkmisWebException("tree的获得失败，DepotAction.getTree()!",e);
			}
			treeList.add(new DTree("1","材料设置信息","","depot.do?method=addSecond"));
			for(int i = 0;i<materialsList.size();i++){
				DTree tree =  new DTree();
				DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
				tree.setId(dst.getId().toString());
				tree.setName(dst.getName());
				tree.setParentID(dst.getParentid().toString());
				tree.setUrl("depot.do?method=searchById&id="+dst.getId()+"&name="+dst.getName()+"&code="+dst.getCode());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "depot.do?method=searchById&id="+id);
			return mapping.findForward("typeList");
	}
	//添加二级目录页面
	public ActionForward addSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			return mapping.findForward("addSecond");
	}
	//执行添加二级目录
	public ActionForward doAddSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			
			DepotMaterialType dsf = new DepotMaterialType();
			dsf.setCode(code);
			dsf.setName(name);
			dsf.setParentid(1);
			try{
				idepotservice.addType(dsf,userName);
			}catch(Exception e){
				logger.error("添加二级材料类别信息失败!DepotAction.doAddSecond()。详细信息：" + e.getMessage());
				throw new BkmisWebException("添加二级材料类别信息失败!DepotAction.doAddSecond()!",e);
			}
			request.setAttribute("parentFrame", "depot.do?method=showMaterialsTpey");
			return mapping.findForward("jumpPage");
	}
	//修改材料类别
	public ActionForward editTpey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			TypeForm typeForm = (TypeForm)form;
			DepotMaterialType dsf = new DepotMaterialType();
			//获取更改前的类别代码
			String typeCode = request.getParameter("oldcode");
			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			
			
			try{
				BeanUtils.copyProperties(dsf,typeForm);
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			try{
				//写入系统日志
				logDetail(userName, Contants.MODIFY, "材料类别",Contants.L_Depot, "1", dsf);
				idepotservice.editType(dsf);
				idepotservice.updateMaterialCode(typeCode, dsf);
			}catch(Exception e){
				logger.error("材料类别信息修改失败!DepotAction.editTpey()。详细信息：" + e.getMessage());
				throw new BkmisWebException("材料类别信息修改失败!DepotAction.editTpey()!",e);
			}
			//response.sendRedirect("depot.do?method=showMaterialsTpey");
			//return showMaterialsTpey(mapping,form,request,response);
			request.setAttribute("parentFrame", "depot.do?method=showMaterialsTpey&id="+dsf.getId());
			return mapping.findForward("jumpPage");
			//return null;
	}
	//删除材料类别
	public ActionForward delTpey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			TypeForm typeForm = (TypeForm)form;
			List typeList = new ArrayList();
			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			writeLog(userName,Contants.DELETE, "删除了名称为【"+typeForm.getName()+"】的仓库类别信息",Contants.L_Depot,"仓库类别");
			
			try{
				typeList = idepotservice.selectCode(typeForm);
				//判断类别是不是有子节点若有子节点不让直接删除根节点要从子节点删除
				if(typeList !=null){
					idepotservice.deleteMaterial(typeList);
					//idepotservice.deleteChildTpye(codeList);
					//request.setAttribute("flag", "1");
				//}else{
				}
					idepotservice.delType(typeForm);
				//}
			}catch(Exception e){
				logger.error("删除材料类别信息失败!DepotAction.delTpey()。详细信息：" + e.getMessage());
				throw new BkmisWebException("删除材料类别信息失败!DepotAction.delTpey()!",e);
			}
			request.setAttribute("parentFrame", "depot.do?method=showMaterialsTpey");
			return mapping.findForward("jumpPage");
	}
	//添加材料类别
	public ActionForward addTpey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			TypeForm typeForm = (TypeForm)form;
			//获取要添加的子类
			String name = request.getParameter("childName");
			String code = request.getParameter("childCode");
			String parent = request.getParameter("id");
			String typeCode = request.getParameter("code");
			int parentId = Integer.parseInt(parent);
			DepotMaterialType dsf = new DepotMaterialType();
			//加入日志管理
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			//typeForm.setUserName(userName);
			//writeLog(userName, "添加了仓库子类类别信息", "添加了仓库子类类别名称为"+name+"的信息",Contants.Depot);
			
			List materialList = new ArrayList();
			try{
				BeanUtils.copyProperties(dsf,typeForm);
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//重新设置子类的名称
			dsf.setName(name);
			dsf.setCode(code);
			dsf.setParentid(parentId);
			try{
				//判断是否可以添加子节点即查询要添加节点的下是否有数据
				materialList = idepotservice.selectByCode(typeCode);
				if(null == materialList || materialList.size()<=0){
					idepotservice.addType(dsf,userName);
					response.getWriter().print("添加成功!");
				}else{
					response.getWriter().print("该类别下已经有材料信息，不能为其添加子类别!");
				}
			}catch(Exception e){
				logger.error("添加材料类别信息失败!DepotAction.addTpey()。详细信息：" + e.getMessage());
				throw new BkmisWebException("添加材料类别信息失败!DepotAction.addTpey()!",e);
			}
			return null;
	}
	//检验类别名称
	public ActionForward checkAddTypeName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String name = request.getParameter("childName");
			
			boolean exist = false;
			try{
				exist = idepotservice.checkAddName(name);
			}catch(Exception e){
				logger.error("材料类别名称验证信息失败!DepotAction.checkAddTypeName()。详细信息：" + e.getMessage());
				throw new BkmisWebException("材料类别名称验证信息失败!DepotAction.checkAddTypeName()!",e);
			}
			if(exist){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
			return null;
	}
	//检验类别代码
	public ActionForward checkAddTypeCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String code = request.getParameter("childCode");
			boolean exist = false;
			try{
				exist = idepotservice.checkAddCode(code);
			}catch(Exception e){
				logger.error("材料类别代码验证信息失败!DepotAction.checkAddTypeCode()。详细信息：" + e.getMessage());
				throw new BkmisWebException("材料类别代码验证信息失败!DepotAction.checkAddTypeCode()!",e);
			}
			if(exist){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
			return null;
	}
	//检验修改类别名称
	public ActionForward checkEditTypeName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String name = request.getParameter("currentName");
			boolean exist =false;
			try{
				exist = idepotservice.checkAddName(name);
			}catch(Exception e){
				logger.error("编辑材料类别名称验证信息失败!DepotAction.checkEditTypeName()。详细信息：" + e.getMessage());
				throw new BkmisWebException("编辑材料类别类别验证信息失败!DepotAction.checkEditTypeName()!",e);
			}
			if(exist){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
			return null;
	}
	//检验修改的类别代码
	public ActionForward checkEditTypeCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			String code = request.getParameter("currentCode");
			boolean exist =false;
			try{
				exist = idepotservice.checkAddCode(code);
			}catch(Exception e){
				logger.error("编辑材料类别代码验证信息失败!DepotAction.checkEditTypeCode()。详细信息：" + e.getMessage());
				throw new BkmisWebException("编辑材料类别代码验证信息失败!DepotAction.checkEditTypeCode()!",e);
			}
			if(exist){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
			return null;
	}
}
