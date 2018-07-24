package com.zc13.bkmis.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:56:29 PM
 */
public class ExistDepotManageAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	/*private IExistDepotManageService existdepotService;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		existdepotService = (IExistDepotManageService)getBean("existdepotService");
	}*/
	private ISetMaterialManageService setmaterialsService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
	}
	//材料库存
	public ActionForward showExistMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			List materialsList = null;
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
				tree.setUrl("existdepot.do?method=selectExistMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "existdepot.do?method=selectExistMaterials");
			return mapping.findForward("existMaterials");
	}
	//点击不同的类别显示不同信息
	public ActionForward selectExistMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			SetMaterialsForm smf = (SetMaterialsForm)form;
			double sum = 0;
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
				sum = setmaterialsService.sumMoney(smf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == smf.getMaterialList() || smf.getMaterialList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/existdepot.do?method=selectExistMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/existdepot.do?method=selectExistMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
				//request.getSession().setAttribute("materialList", smf.getMaterialList());
				request.setAttribute("dmtId", strdmtId);
				request.setAttribute("typeCode", code);
			}catch(Exception e){
				logger.error("点击tree获得信息失败!SetMaterialsManageAction.selectExistMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，SetMaterialsManageAction.selectExistMaterials()!",e);
			}
			request.setAttribute("sum", sum);
			return mapping.findForward("mainFramJsp");
	}
	//导出现有库存报表
	@SuppressWarnings("unchecked")
	public ActionForward exportExistDepotExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			try{
				SetMaterialsForm smf = (SetMaterialsForm)form;
				//获取库存信息的内容
				//List list = (List)request.getSession().getAttribute("materialList");
				List list = setmaterialsService.selectAllMaterial(smf);
				//表头
				String[] cellHeader = Constant.EXCEL_EXIST_DEPOT;
				String[] cellValue = Constant.EXCEL_EXIST_VALUE;
				
				//定义文件名
				String sheetName = "现有库存信息列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(10000*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "现有库存信息列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出现有库存excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
}
