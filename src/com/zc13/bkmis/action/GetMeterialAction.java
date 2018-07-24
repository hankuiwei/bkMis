package com.zc13.bkmis.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.ISerClientMaintainService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

import net.sf.json.JSONObject;

/***
 * @author qinyantao
 * Date：Dec 13, 2010
 * Time：13:35:50 PM
 */
public class GetMeterialAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ISetMaterialManageService setmaterialsService;
	private ISerClientMaintainService  iSerClientMaintainService= null;
	/** 从spring容器里得到iCustomerRepairService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		iSerClientMaintainService = (ISerClientMaintainService)getBean("ISerClientMaintainService");
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
				tree.setUrl("getMeterial.do?method=selectExistMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "getMeterial.do?method=selectExistMaterials");
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
							+ "/getMeterial.do?method=selectExistMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/getMeterial.do?method=selectExistMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("点击tree获得信息失败!InputDepotManageAction.selectExistMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，InputDepotManageAction.selectExistMaterials()!",e);
			}
			return mapping.findForward("mainFramJsp");
	}
	
	//根据不同的id得到材料类别
	public ActionForward getMaterialById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, SQLException {
		
		try{
			String id = request.getParameter("id");
			DepotMaterial obj = iSerClientMaintainService.selectMaterialById(id);
			JSONObject json = JSONObject.fromObject(obj);
			this.render(response, json);
			//string = obj.getCode() + "," + obj.getName() + "," + obj.getSpec() + "," + obj.getUnitPrice() + "," + obj.getId(); 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("点击tree获得信息失败!GetMeterialAction.selectMaterials().详细信息：" + e.getMessage());
			throw new BkmisWebException("点击tree获得信息失败，GetMeterialAction.selectMaterials()!",e);
		}
		return null;
	}
	//根据不同的id得到材料数量-李荣刚
	public void getMaterialCountById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {
		try{
			String ids = request.getParameter("itemIds");//获得所选材料的id
			String[] idArr=ids.split(",");
			for(int i=0;i<idArr.length;i++){
				DepotMaterial obj = iSerClientMaintainService.selectMaterialById(idArr[i]);
				JSONObject json = JSONObject.fromObject(obj);
				this.render(response, json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getKyslAnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {
		
		Map<String, String> map=new HashMap<String, String>();
		String flag = "0";
		try{
			String codes = request.getParameter("itemIds");//获得所选材料的id
			String sls = request.getParameter("sl");//获得所选材料的id
			if(codes==null && sls==null){
				map.put("flag", flag);//不选材料
				response.getWriter().write(JSONObject.fromObject(map).toString());
				response.getWriter().close();
			}else{
				String[] codesArr=codes.split(",");
				String[] slArr=sls.split(",");
				for(int i=0;i<codesArr.length;i++){
					String dostack = iSerClientMaintainService.getDostack(codesArr[i]);
					int dostackNumber = (int)Double.parseDouble(dostack);
					int slNumber = (int)Double.parseDouble(slArr[i]);
					if(slNumber<=dostackNumber){
						flag = "1";//选材料没料
						map.put("flag", flag);
					}
					else{
						flag = "2";//选材料有料
						map.put("flag", flag);
					}
					response.getWriter().write(JSONObject.fromObject(map).toString());
					response.getWriter().close();
					 
				}
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

