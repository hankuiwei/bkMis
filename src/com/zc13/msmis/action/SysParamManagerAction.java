package com.zc13.msmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.bkmis.mapping.ViewBuildId;
import com.zc13.bkmis.mapping.ViewTreeCode;
import com.zc13.bkmis.mapping.ViewTreeCodeId;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.form.SysParamForm;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.mapping.SysCodeType;
import com.zc13.msmis.service.ISysParamManagerService;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;


/**
 * 系统参数相关
 * @author 秦彦韬
 * Date：Nov 3, 2010
 * Time：2:47:23 PM
 */

public class SysParamManagerAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	private IRoomManageService iroomManageService = null;
	/** houxj改 */
	ISysParamManagerService iSysParamManagerService = null;
	/** 从spring容器里得到iroomManageService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
		/** houxj改 */
	    iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
	}
	/**
	 * 显示系统参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getSysParam(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		/**
		 * houxj改
		 * 获得一个树状列表中id最小的 */
		String id = GlobalMethod.NullToSpace(request.getParameter("id"));
		Integer codeTypeId = iSysParamManagerService.getSysCodeTypeByMinId();
		if(!id.equals("")){
			codeTypeId = Integer.parseInt(id);
		}
		request.setAttribute("mainFramJsp", "sysParamManager.do?method=goAdd&id=" + codeTypeId + "&table=sys_code_type");
		request.setAttribute("tree", "sysParamManager.do?method=toGetTreeList");
		return mapping.findForward("sysParamList");
	}
	
	/**
	 * 获得参数树
	 * SysParamManagerAction.getMTree
	 */
	public ActionForward toGetTreeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		List<ViewTreeCode> list = iSysParamManagerService.getSysParamList();
		List<DTree> treeList = new ArrayList<DTree>();
		treeList.add(new DTree("1","系统参数维护","",""));
		for(ViewTreeCode code: list){
			ViewTreeCodeId codeId = code.getId();
			DTree tree =  new DTree();
			tree.setId(codeId.getId().toString());
			tree.setName(codeId.getName());
			tree.setParentID(codeId.getParentId().toString());
			int parentId = iSysParamManagerService.getParentIdByViewId(codeId.getParentId());
			tree.setUrl("sysParamManager.do?method=goAdd&id=" + codeId.getPk() + "&table=" +codeId.getTableName()+"&parentId="+parentId);
			treeList.add(tree);
		}
		request.setAttribute("treeList", treeList);
		return mapping.findForward("toShowTree");
	}
	
	/**得到房间管理所需的树 */
	public ActionForward getMTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String room = request.getParameter("room")==null?"customer.do?method=getList":request.getParameter("room");
		List<ViewBuild> treeforlist = null; 
		RoomForm roomForm = (RoomForm)form;
		List<DTree> treeList = new ArrayList<DTree>();
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			treeforlist = iroomManageService.getInfoForTree(roomForm);
		} catch (Exception e) {
			logger.error("tree的获得失败!RoomManageAction.getTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，RoomManageAction.getTree()!",e);
		}
		
		treeList.add(new DTree("1","房产信息","0","000"));
		for(ViewBuild v:treeforlist){
			DTree tree =  new DTree();
			ViewBuildId build = v.getId();
			tree.setId(build.getId().toString());
			tree.setName(build.getName());
			tree.setParentID(build.getParentId().toString());
			treeList.add(tree);	
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",room);
		return mapping.findForward("tree");
	}
	
	//检查代码类型名称是否存在
	@SuppressWarnings("unchecked")
	public ActionForward checkCodeTypeName(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		String codeName = request.getParameter("codeName");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isExist = false;
		try{
			isExist = iSysParamManagerService.checkCodeName(codeName);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.getSysParam()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.getSysParam()!",e);
			}
		out.print(isExist);
		return null;
	}
	
	//保存参数代码
	@SuppressWarnings("unchecked")
	public ActionForward saveCodeById(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String[] id = request.getParameterValues("codeId");
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		int codeId = Integer.parseInt(request.getParameter("codeId"));
		String codeName = request.getParameter("codeName");
		try{
			iSysParamManagerService.saveCodeById(codeId,codeName);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.getSysParam()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.getSysParam()!",e);
			}
		return null;
	}
	
	//选择参数类型
	@SuppressWarnings("unchecked")
	public ActionForward chooseType(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		String typeValue = request.getParameter("value");
		Map map = new HashedMap();
		List<SysParamForm> list = null;
		try{
			map = iSysParamManagerService.chooseType(typeValue);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.chooseType()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.chooseType()!",e);
			}
		request.setAttribute("value", typeValue);	
		request.setAttribute("list", map.get("list"));
		request.setAttribute("list2", map.get("list2"));
		return mapping.findForward("sysParamList");
	}
	
	//删除参数代码
	public ActionForward delCode(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String codeId = request.getParameter("codeId")==null?"":request.getParameter("codeId");
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		try{
			iSysParamManagerService.delCode(codeId,userName);
		} catch (Exception e) {
			logger.error("查询系统参数信息失败，SysParamManagerAction.chooseType()。详细信息："+e.getMessage());
			throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.chooseType()!",e);
		}
		
		//return getSysParam(mapping, form, request, response);
		return goAdd(mapping, form, request, response);
	}
	
	//得到要编辑的参数代码和参数类型
	public ActionForward goAdd(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		int id = Integer.parseInt(request.getParameter("id")==null?"1":request.getParameter("id"));
		String table = request.getParameter("table")==null?"sys_code_type":request.getParameter("table");
		String parentId = GlobalMethod.NullToSpace(request.getParameter("parentId"));
		Map map = null;
		try{
			map = iSysParamManagerService.goAdd(id,table);
			} catch (Exception e) {
			logger.error("查询系统参数信息失败，SysParamManagerAction.goAdd()。详细信息："+e.getMessage());
			throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.goAdd()!",e);
		}
		request.setAttribute("map", map);
		request.setAttribute("id", id);
		request.setAttribute("parentId", parentId);
		request.setAttribute("table", table);
		return mapping.findForward(map.get("forWard").toString());
	}
	
	//插入参数代码
	public ActionForward insertCode(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		String codeType = request.getParameter("codeValue");
		String codeName = request.getParameter("codeName1");
		String codeValue = request.getParameter("codeValue1");
		int userId = Integer.parseInt(((Map)request.getSession().getAttribute("userInfo")).get("userid").toString());
		SysCode code = null;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		try{
			code= iSysParamManagerService.insertCode(codeType,codeName,userName,codeValue);
			} catch (Exception e) {
			logger.error("插入系统参数信息失败，SysParamManagerAction.insertCode()。详细信息："+e.getMessage());
			throw new BkmisWebException("插入系统参数信息失败，SysParamManagerAction.insertCode()!",e);
		}
		
		return getSysParam(mapping, form, request, response);
	}
	
	//检查代码名称是否存在
	@SuppressWarnings("unchecked")
	public ActionForward checkCodeNameTwo(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		
		String codeName = request.getParameter("codeName");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean isExist = false;
		
		try{
			isExist = iSysParamManagerService.checkCodeNameTwo(codeName);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.checkCodeNameTwo()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.checkCodeNameTwo()!",e);
			}
		out.print(isExist);
		return null;
	}
	
	public ActionForward checkValue(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		
		String value = request.getParameter("value");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean isExist = false;
		
		try{
			isExist = iSysParamManagerService.checkValue(value);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.checkValue()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.checkValue()!",e);
			}
		out.print(isExist);
		return null;
	}
	
	public ActionForward updateCode(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		String codeValue = request.getParameter("codeValue");
		String codeName = request.getParameter("codeName");
		int codeId = Integer.parseInt(request.getParameter("codeId"));
		int userId = Integer.parseInt(((Map)request.getSession().getAttribute("userInfo")).get("userid").toString());
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		try{
			iSysParamManagerService.updateCode(codeId, codeName,codeValue,userName);
			} catch (Exception e) {
			logger.error("插入系统参数信息失败，SysParamManagerAction.insertCode()。详细信息："+e.getMessage());
			throw new BkmisWebException("插入系统参数信息失败，SysParamManagerAction.insertCode()!",e);
		}
		
		//return getSysParam(mapping, form, request, response);
		return goAdd(mapping, form, request, response);
	}
	
	public ActionForward updateCodeType(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		String codeValue = request.getParameter("codeValue");
		String codeName = request.getParameter("codeName");
		int codeId = Integer.parseInt(request.getParameter("codeId"));
		SysCodeType code = new SysCodeType();
		code.setCodeTypeId(codeId);
		code.setCodeTypeName(codeName);
		code.setCodeTypeValue(codeValue);
		int userId = Integer.parseInt(((Map)request.getSession().getAttribute("userInfo")).get("userid").toString());
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map1.get("username").toString());
		try{
			iSysParamManagerService.updateCodeType(codeId,codeName,codeValue,userId,userName);
		} catch (Exception e) {
			logger.error("修改系统参数信息失败，SysParamManagerAction.insertCode()。详细信息："+e.getMessage());
			throw new BkmisWebException("修改系统参数信息失败，SysParamManagerAction.insertCode()!",e);
		}

		Map<String,SysCodeType> map = new HashMap<String, SysCodeType>();	
		map.put("codeType", code);
		request.setAttribute("map",map);
		return mapping.findForward("codeType");
	}
	
	public ActionForward checkCodeTypeValue(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysParamManagerService iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		
		String codeValue = request.getParameter("value");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean isExist = false;
		
		try{
			isExist = iSysParamManagerService.checkCodeTypeValue(codeValue);
			} catch (Exception e) {
				logger.error("查询系统参数信息失败，SysParamManagerAction.checkCodeNameTwo()。详细信息："+e.getMessage());
				throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.checkCodeNameTwo()!",e);
			}
		out.print(isExist);
		return null;
	}
}
