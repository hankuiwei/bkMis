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

import com.zc13.bkmis.form.EquipmentForm;
import com.zc13.bkmis.mapping.EqType;
import com.zc13.bkmis.service.IEquipmentService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;

public class EquipmentAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IEquipmentService equipmentService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		equipmentService = (IEquipmentService)getBean("equipmentService");
	}

	/**
	 * 获取所有的材料，调用此方法。此方法会先提取设备类型树，然后才会在子界面上展示具体的设备。与上面那个“showEquipmentType”方法的唯一区别就是子页面的链接不同
	 * 说不定哪一天我会将这两个方法合二为一，但是现在没有时间，因为现在我在给做原型
	 * 好吧，下周距离写上面一段话过去了有两个小时了，哥还在加班干活。现在又出现需要这个树的东西了，所有我现在就合并。穿参数key，如果是0，那么类型；如果1，那么设备；2那么报废的设备。我自然就把本来获取类型的那个方法删掉了
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2011-6-14 
	 * Time:下午07:15:28
	 */
	public ActionForward getTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		/** 到此为止*/
		String key = GlobalMethod.NullToParam(request.getParameter("key"),"0");
		List materialsList = null;
		List<DTree> treeList = new ArrayList<DTree>();
		try {
			materialsList = equipmentService.getEqpTypeList(lpId);
		}catch(Exception e){
			logger.error("tree的获得失败!EquipmentAction.getTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，EquipmentAction.getTree()!",e);
		}
		treeList.add(new DTree("1","设备总类","",""));
		String urlString = "equp.do?method=searchTypeById";
		if("1".equals(key)){
			urlString = "zc13/bkmis/equipment/equipmentList.jsp";
		}else if("2".equals(key)){
			urlString = "zc13/bkmis/equipment/equipmentBFList.jsp";
		}
		
		for(int i = 0;i<materialsList.size();i++){
			DTree tree =  new DTree();
			EqType dst = (EqType)materialsList.get(i);
			tree.setId(dst.getId().toString());
			tree.setName(dst.getName());
			tree.setParentID(dst.getParentid().toString());
			tree.setUrl(urlString);//标注1：这里因为是原型，所以这个地方和标注2的地方使用了统一个url，将来做真实的话，需要有应该的区别
			treeList.add(tree);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", urlString);//标注2:
		return mapping.findForward("typeList");
	}
	/**
	 * 根据树的id来显示点击不同节点显示该节点的内容（用于设备类型的操作）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * Date:2011-6-14 
	 * Time:下午05:31:29
	 */
	
	public ActionForward searchTypeById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
		EquipmentForm equipmentForm = (EquipmentForm)form;	
		String strid = GlobalMethod.NullToSpace(request.getParameter("id"));
		if(strid != "0"){
			if(!"".equals(strid) && null != strid){
				int typeID = Integer.parseInt(strid);
				List list = null;
				equipmentForm.setId(typeID);
				try{
					list = equipmentService.getTypeById(typeID);
					request.setAttribute("list", list);
				}catch(Exception e){
					logger.error("点击tree的获得信息失败!DepotAction.searchById().详细信息：" + e.getMessage());
					throw new BkmisWebException("点击tree的获得信息失败，DepotAction.searchById()!",e);
				}
			}
			else{
				request.setAttribute("codeList", "0");
			}
		}
		return mapping.findForward("MaterightJsp");
	}
}
