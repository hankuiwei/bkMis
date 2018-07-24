package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IInputDepotManageService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:06:09 PM
 */
public class InputDepotManageAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IInputDepotManageService inputdepotService;
	private ISetMaterialManageService setmaterialsService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		inputdepotService = (IInputDepotManageService)getBean("inputdepotService");
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
	}
	
	public ActionForward showInputDepot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
			return mapping.findForward("");
	}
	//材料入库中选择材料添加信息
	public ActionForward chooseMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			List materialsList = new ArrayList();
			List<DTree> treeList = new ArrayList<DTree>();
			try {
				materialsList = setmaterialsService.getMaterialsList();
			}catch(Exception e){
				logger.error("tree的获得失败!InputDepotManageAction.chooseMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("tree的获得失败，InputDepotManageAction.chooseMaterials()!",e);
			}
			treeList.add(new DTree("1","材料设置信息","",""));
			for(int i = 0;i<materialsList.size();i++){
				DTree tree =  new DTree();
				DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
				tree.setId(dst.getId().toString());
				tree.setName(dst.getName());
				tree.setParentID(dst.getParentid().toString());
				tree.setUrl("inputdepot.do?method=selectExistMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "inputdepot.do?method=selectExistMaterials");
			return mapping.findForward("chooseMaterials");
	}
	//点击不同的类别显示不同信息
	public ActionForward selectExistMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			SetMaterialsForm smf = (SetMaterialsForm)form;
			//获取点击类别代号及id
			String strdmtId = request.getParameter("dmtId");
			if(strdmtId != null && strdmtId != ""){
				int dmtId = Integer.parseInt(strdmtId);
				smf.setDmtId(dmtId);
			}
			String code = request.getParameter("Code");
			if(code != null && code !=""){
				smf.setType(code);
			}
			try{
				//获取要显示的内容
				setmaterialsService.selectMaterials(smf,true);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == smf.getMaterialList() || smf.getMaterialList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputdepot.do?method=selectExistMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputdepot.do?method=selectExistMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("点击tree获得信息失败!InputDepotManageAction.selectExistMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，InputDepotManageAction.selectExistMaterials()!",e);
			}
			return mapping.findForward("mainFramJsp");
	}
	//根据id查询选择添加的材料
	public ActionForward selectChooseMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
			//获取选择的id
		 	String chooseIds = request.getParameter("chooseIds");
		 	//System.out.println("======="+chooseIds);
		 	List materialList = new ArrayList();
		 	if(!"".equals(chooseIds) && chooseIds != null){
		 		String[] idArray = chooseIds.split(",");
		 		//Integer idInt[] = new Integer[idArray.length];
		 		for(int i = 0;i<idArray.length;i++){
		 			//idInt[i] = Integer.parseInt(idArray[i]);
		 			materialList = inputdepotService.selectMaterialsById(idArray);
		 		}
		 	}
		 	request.setAttribute("materialList", materialList);
			return mapping.findForward("showChoose");
	}
	/**
	 * 检查当年是否是结算日
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-5-13 
	 * Time:下午06:57:18
	 */
	public ActionForward checkIfDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		boolean b = false;
		try {
			out = response.getWriter();
			b = inputdepotService.ifDate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(b);
		return null;
	}
	
}
