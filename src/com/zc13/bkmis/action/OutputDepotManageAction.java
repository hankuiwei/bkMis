package com.zc13.bkmis.action;

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

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IOutputDepotManageService;
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
 * Time：1:37:36 PM
 */
public class OutputDepotManageAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	/*private IOutputDepotManageService outputdepotService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		outputdepotService = (IOutputDepotManageService)getBean("outputdepotService");
	}*/
	private IOutputDepotManageService outputdepotService;
	private ISetMaterialManageService setmaterialsService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		outputdepotService = (IOutputDepotManageService)getBean("outputdepotService");
	}
	//材料出库中选择材料添加信息
	public ActionForward chooseOutMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			List materialsList = new ArrayList();
			List<DTree> treeList = new ArrayList<DTree>();
			try {
				materialsList = setmaterialsService.getMaterialsList();
			}catch(Exception e){
				logger.error("tree的获得失败!OutputDepotManageAction.chooseOutMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("tree的获得失败，OutputDepotManageAction.chooseOutMaterials()!",e);
			}
			treeList.add(new DTree("1","材料设置信息","",""));
			for(int i = 0;i<materialsList.size();i++){
				DTree tree =  new DTree();
				DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
				tree.setId(dst.getId().toString());
				tree.setName(dst.getName());
				tree.setParentID(dst.getParentid().toString());
				tree.setUrl("outputdepot.do?method=selectExistMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "outputdepot.do?method=selectExistMaterials");
			return mapping.findForward("chooseOutMaterails");
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
							+ "/outputdepot.do?method=selectExistMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/outputdepot.do?method=selectExistMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
			}catch(Exception e){
				logger.error("点击tree获得信息失败!OutputDepotManageAction.selectExistMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，OutputDepotManageAction.selectExistMaterials()!",e);
			}
			return mapping.findForward("mainFramJsp");
	}
	//跳转到添加页面的方法
	public ActionForward addOutputMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//查询部门信息
			List departList = new ArrayList();
			//GlobalMethod.getTime2()
			String num = "";//存储出库单号
			try{
				//查询部门信息
				departList = outputdepotService.selectDepartment();
				//查询出库单编号
				num = outputdepotService.GenerationNum();
				
			}catch(Exception e){
				logger.error("查询部门信息失败!OutputDepotManageAction.addOutputMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询部门信息失败，OutputDepotManageAction.addOutputMaterials()!",e);
			}
			request.setAttribute("departList", departList);
			request.setAttribute("num", num);
			return mapping.findForward("addOutput");
	}
	//根据id查询选择的添加的材料
	public ActionForward selectChooseMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//获取选择的id
		 	String chooseIds = request.getParameter("chooseIds");
		 //	System.out.println("======="+chooseIds);
		 	List materialList = new ArrayList();
		 	if(!"".equals(chooseIds) && chooseIds != null){
		 		String[] idArray = chooseIds.split(",");
		 		//Integer idInt[] = new Integer[idArray.length];
		 		for(int i = 0;i<idArray.length;i++){
		 			//idInt[i] = Integer.parseInt(idArray[i]);
		 			materialList = outputdepotService.selectMaterialsById(idArray);
		 		}
		 	}
		 	request.setAttribute("materialList", materialList);
			return mapping.findForward("showChoose");
	}
	//查询显示出库单信息
	public ActionForward showOutputMaterialsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//获取查询条件
			SetMaterialsForm smf = (SetMaterialsForm)form;
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			smf.setLpId(lpId);
			/** 到此为止 */
			//汇总结果
			List summaryList = new ArrayList();
			try{
				//查询出库管理
				outputdepotService.showOutputMaterial(smf);
				//查询记录的总条数
				int totalcount = outputdepotService.queryCounttotal(smf);
				summaryList = outputdepotService.summary(smf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == smf.getOutputList() || smf.getOutputList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/outputdepot.do?method=showOutputMaterialsList", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/outputdepot.do?method=showOutputMaterialsList", Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("summaryList", summaryList);
				//request.getSession().setAttribute("exportOutputList", smf.getOutputList());
			}catch(Exception e){
				e.printStackTrace();
				logger.error("显示出库单信息失败!OutputDepotManageAction.showOutputMaterialsList().详细信息：" + e.getMessage());
				throw new BkmisWebException("显示出库单信息失败，OutputDepotManageAction.showOutputMaterialsList()!",e);
			}
			return mapping.findForward("outputManage");
	}
	//导出出库单报表
	public ActionForward exportOutputExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取要显示内容的信息
				//List list = (List)request.getSession().getAttribute("exportOutputList");
				SetMaterialsForm smf = (SetMaterialsForm)form;
				List list = outputdepotService.selectAllOutput(smf);
				//表头
				String[] cellHeader = Constant.EXCEL_OUT_DETAIL;
				String[] cellValue = Constant.EXCEL_OUT_VALUE;
				//定义文件名
				String sheetName = "出库单列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "出库单列表.xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出出库单excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
}
