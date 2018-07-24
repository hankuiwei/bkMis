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

import com.zc13.bkmis.form.OutputDepotManageForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.mapping.DepotOutputManager;
import com.zc13.bkmis.service.IOutputDepotManageService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

public class OutputDepotManage2Action extends BasicAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private IOutputDepotManageService outputdepotService;
	private ISetMaterialManageService setmaterialsService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		outputdepotService = (IOutputDepotManageService)getBean("outputdepotService");
	}
	//执行添加操作
	public ActionForward doAddOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			OutputDepotManageForm outputForm = (OutputDepotManageForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				//outputForm.setLpId(lpId);
				outputForm.setLpId(lpId);
				//outputForm.setPagesize("122");
				/** 到此为止*/
				outputdepotService.doAddOutput(outputForm);
				
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				writeLog(userName, Contants.ADD, "添加编号为【"+outputForm.getCode()+"】的出库单",Contants.L_Depot,"出库管理");
				
			}catch(Exception e){
				logger.error("添加出库单信息失败!OutputDepotManage2Action.doAddOutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("添加出库单信息失败，OutputDepotManage2Action.doAddOutput()!",e);
			}
			response.sendRedirect("outputdepot.do?method=showOutputMaterialsList");
			return null;
	}
	
	//删除出库单信息
	public ActionForward doDelOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		OutputDepotManageForm outputForm = (OutputDepotManageForm)form;
		setLogParam(request);
		outputForm.setLogParam(logParam);//为日志操作初始化一些基本的信息
		try{
			outputdepotService.deleteOutput(outputForm);
		}catch(Exception e){
			e.printStackTrace();
		}
		response.sendRedirect("outputdepot.do?method=showOutputMaterialsList");
		return null;
	}
	
	//跳转到编辑界面
	public ActionForward editOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//查询部门信息
			List departList = new ArrayList();
			List outputList = new ArrayList();
			//获取要编辑出库单信息的id
			String strid = request.getParameter("editId");
			int id = Integer.parseInt(strid);
			//出库单编号
			String inoutputCode = "";
			//存储出库单的相信信息
			List inoutputList = new ArrayList();
			String totalMoney = "";
			try{
				//查询部门信息
				departList = outputdepotService.selectDepartment();
				//查询出库单信息
				outputList = outputdepotService.selectOutput(id);
				//获取出库单编号
				if(outputList != null || outputList.size()>0){
					DepotOutputManager output = (DepotOutputManager)outputList.get(0);
					inoutputCode = output.getCode();
					inoutputList = outputdepotService.selectinoutput(inoutputCode);
					
				}
				if(inoutputList != null && inoutputList.size()>0){
					Object obj[] = (Object[])inoutputList.get(0);
					totalMoney = obj[5].toString();
				}
			}catch(Exception e){
				logger.error("查询部门信息失败!OutputDepotManageAction.editOutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询部门信息失败，OutputDepotManageAction.editOutput()!",e);
			}
			request.setAttribute("departList", departList);
			request.setAttribute("outputList", outputList);
			request.setAttribute("inoutputList", inoutputList);
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("inoutputCode", inoutputCode);
			return mapping.findForward("editOutput");
	}
	//执行修改
	public ActionForward doEditOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//String code = request.getParameter("code");
			OutputDepotManageForm outputForm = (OutputDepotManageForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				outputForm.setLpId(lpId);
				/** 到此为止*/

				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				outputForm.setUserName(userName);
				//outputForm.setCode(code);
				//删除出库单详细信息
				//outputdepotService.deleteInOutput(code);
				//更新出库单信息,在这之前删除出库单详细信息，完事后再添加详细信息。这些应该扔到一个事务中，所以应该放到一个service层的方法中
				outputdepotService.updateOutput(outputForm);
				//添加详细信息
				//outputdepotService.doAddDetailOutput(outputForm);
				
				
				
			}catch(Exception e){
				logger.error("执行出库修改信息失败!OutputDepotManageAction.doEditOutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("执行出库修改信息失败，OutputDepotManageAction.doEditOutput()!",e);
			}
			//response.sendRedirect("outputdepot.do?method=showOutputMaterialsList");
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("editOutput");
	}
	//执行结算
	public ActionForward doAccountOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//获取要结算的记录的id
			String accountId = request.getParameter("accountIds");
			String[] idArray = accountId.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			List codeList = new ArrayList();
			
			for(int i = 0;i<idArray.length;i++){
				idInt[i] =Integer.parseInt(idArray[i]);
				outputdepotService.doAccountOutput(idInt[i]);//只是更改状态
			}
			for(int i= 0;i<idArray.length;i++){
				ids[i] = Integer.parseInt(idArray[i]);
				codeList = outputdepotService.selectOutputCode(ids);
			}
			if(null != codeList && codeList.size()>0){
				outputdepotService.selectDetailInoutput(codeList);
			}
			response.sendRedirect("outputdepot.do?method=showOutputMaterialsList");
			return null;
	}
	
	public ActionForward showTreeAndOutputDetail(ActionMapping mapping, ActionForm form,
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
			tree.setUrl("outputmanage.do?method=showOutputDetail&dmtId="+dst.getId().toString());
			treeList.add(tree);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "outputmanage.do?method=showOutputDetail");
		return mapping.findForward("showTreeAndOutputDetail");
	}
	
	/**
	 * 下面是出库明细表的管理
	 */
	public ActionForward showOutputDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			OutputDepotManageForm odmf = (OutputDepotManageForm)form;
			//查询部门信息
			List departList = new ArrayList();
			//获取选择的部门
			String department = request.getParameter("department");
			//汇总结果
			List summaryList = new ArrayList();
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				odmf.setLpId(lpId);
				/** 到此为止 */
				//查询部门信息
				departList = outputdepotService.selectDepartment();
				//显示查询出的出库的详细信息
				outputdepotService.selectOutputDetail(odmf);
				//计算出库明细表中的总数量和总金额
				summaryList = outputdepotService.summaryDetail(odmf);
				//查询记录的总条数
				int totalcount = outputdepotService.queryCountDetail(odmf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == odmf.getOutputDatilList() || odmf.getOutputDatilList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/outputmanage.do?method=showOutputDetail", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/outputmanage.do?method=showOutputDetail", Integer.parseInt(GlobalMethod.NullToParam(odmf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(odmf.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("department", department);
				request.setAttribute("summaryList", summaryList);
				//request.getSession().setAttribute("outputDetailList", odmf.getOutputDatilList());
			}catch(Exception e){
				logger.error("出库明细表查询信息加载失败!OutputDepotManageAction.showOutputDetail().详细信息：" + e.getMessage());
				throw new BkmisWebException("出库明细表查询信息加载失败，OutputDepotManageAction.showOutputDetail()!",e);
			}
			request.setAttribute("departList", departList);
			return mapping.findForward("outDetail");
	}
	//导出报表
	public ActionForward exportOutDetailExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取存放页面显示的内容的list
				//List list = (List)request.getSession().getAttribute("outputDetailList");
				OutputDepotManageForm odmf = (OutputDepotManageForm)form;
				List list = outputdepotService.selectAllDetail(odmf);
				//表头
				String[] cellHeader = Constant.EXCEL_OUTPUT_DETAIL;
				String[] cellValue = Constant.EXCEL_OUTPUT_VALUE;
				
				//定义文件名
				String sheetName = "出库明细列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(10000*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "出库明细列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出出库明细excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
	//执行查看出库单信息
	public ActionForward doViewOutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//查询部门信息
			List departList = new ArrayList();
			List outputList = new ArrayList();
			//获取要编辑出库单信息的id
			String strid = request.getParameter("editId");
			int id = Integer.parseInt(strid);
			//出库单编号
			String inoutputCode = "";
			//存储出库单的相信信息
			List inoutputList = new ArrayList();
			String totalMoney = "";
			try{
				//查询部门信息
				departList = outputdepotService.selectDepartment();
				//查询出库单信息
				outputList = outputdepotService.selectOutput(id);
				//获取出库单编号
				if(outputList != null || outputList.size()>0){
					DepotOutputManager output = (DepotOutputManager)outputList.get(0);
					inoutputCode = output.getCode();
					inoutputList = outputdepotService.selectinoutput(inoutputCode);
				}
				if(inoutputList != null && inoutputList.size()>0){
					Object obj[] = (Object[])inoutputList.get(0);
					totalMoney = obj[5].toString();
				}
			}catch(Exception e){
				logger.error("查询部门信息失败!OutputDepotManageAction.editOutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询部门信息失败，OutputDepotManageAction.editOutput()!",e);
			}
			request.setAttribute("departList", departList);
			request.setAttribute("outputList", outputList);
			request.setAttribute("inoutputList", inoutputList);
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("inoutputCode", inoutputCode);
		    return mapping.findForward("viewOutput");
	}
	/** 打印出库单 */
	public ActionForward outputListPrint(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<OutputDepotManageForm > list = null;
		String name = request.getParameter("name");
		String spec = request.getParameter("spec");
		String unitPrice = request.getParameter("unitPrice");
		String amount = request.getParameter("amount");
		String money = request.getParameter("money");
		try {
			
			OutputDepotManageForm idmf= (OutputDepotManageForm )form;
			list = outputdepotService.selectAllDetail(idmf);
			
		} catch (Exception e) {
			logger.error("打印出库单失败!OutputDepotManage2Action.print().详细信息：" + e.getMessage());
			throw new BkmisWebException("打印出库单失败，OutputDepotManage2Action.print()!",e);
		}
		
		request.setAttribute("outputDatilList",list);
		request.setAttribute("name",name);
		request.setAttribute("spec",spec);
		request.setAttribute("unitPrice",unitPrice);
		request.setAttribute("amount",amount);
		request.setAttribute("money",money);
		
		return mapping.findForward("outputListPrint");
	}
}
