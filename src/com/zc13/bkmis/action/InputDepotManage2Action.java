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

import com.zc13.bkmis.form.InputDepotManageForm;
import com.zc13.bkmis.mapping.DepotInputManager;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IInputDepotManageService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

public class InputDepotManage2Action extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private IInputDepotManageService inputdepotService;
	private ISetMaterialManageService setmaterialsService;
	
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		inputdepotService = (IInputDepotManageService)getBean("inputdepotService");
	}
	//查询显示入库单信息
	public ActionForward showInputMaterialsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			InputDepotManageForm idmf = (InputDepotManageForm)form;
			//汇总结果
			List summaryList = new ArrayList();
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				idmf.setLpId(lpId);
				/** 到此为止 */
				inputdepotService.selectInputList(idmf);
				summaryList = inputdepotService.summary(idmf);
				//查询记录的总条数
				int totalcount = inputdepotService.queryCounttotal(idmf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == idmf.getInputList() || idmf.getInputList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputmanage.do?method=showInputMaterialsList", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputmanage.do?method=showInputMaterialsList", Integer.parseInt(GlobalMethod.NullToParam(idmf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(idmf.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("summaryList", summaryList);
				//request.getSession().setAttribute("explortInputList", idmf.getInputList());
			}catch(Exception e){
				logger.error("显示入库单信息失败!InputDepotManage2Action.showInputMaterialsList().详细信息：" + e.getMessage());
				throw new BkmisWebException("显示入库单信息失败，InputDepotManage2Action.showInputMaterialsList()!",e);
			}
			return mapping.findForward("showInput");
	}
	//跳转到添加页面
	public ActionForward addIutputMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//查询公司
			List supplierList = new ArrayList(); 
			String num = ""; //存储入库单号
			
			try{
				supplierList = inputdepotService.selectSupplier();
				num = inputdepotService.GenerationNum();
				
			}catch(Exception e){
				logger.error("查询供应商信息失败!InputDepotManage2Action.addIutputMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询供应商信息失败，InputDepotManage2Action.addIutputMaterials()!",e);
			}
			request.setAttribute("supplierList", supplierList);
			request.setAttribute("num", num);
			return mapping.findForward("addInput");
	}
	//执行添加入库单信息
	public ActionForward doAddIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			InputDepotManageForm idmf = (InputDepotManageForm)form;
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				
				idmf.setLpId(lpId);
				/** 到此为止*/
				inputdepotService.doAddInput(idmf);
				
				//加入日志管理
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				writeLog(userName, Contants.ADD, "添加了编号为【"+idmf.getCode()+"】的入库单信息",Contants.L_Depot,"入库管理");
				
			}catch(Exception e){
				logger.error("添加入库单信息失败!InputDepotManage2Action.doAddIutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("添加入库单信息失败，InputDepotManage2Action.doAddIutput()!",e);
			}
			response.sendRedirect("inputmanage.do?method=showInputMaterialsList");
			return null;
	}
	//跳转到修改界面
	@SuppressWarnings("unchecked")
	public ActionForward editIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//查询供应商
			List supplierList = new ArrayList();
			List inputList = new ArrayList();
			List inoutputList = new ArrayList();
			//入库单编号
			String inputCode = "";
			//总金额
			String totalMoney = "";
			//获取要修改的id
			String strId = request.getParameter("editId");
			int id = Integer.parseInt(strId);
			try{
				//查询供应商
				supplierList = inputdepotService.selectSupplier();
				//查询显示要修改的信息
				inputList = inputdepotService.selectEditInput(id);
				//获取出库单编号查询出库的详细信息
				if(null != inputList || inputList.size()>0){
					DepotInputManager dim = (DepotInputManager)inputList.get(0);
					inputCode = dim.getCode();
					inoutputList = inputdepotService.selectDetailInput(inputCode);
				}
				if(inoutputList != null && inoutputList.size()>0){
					Object obj[] = (Object[])inoutputList.get(0);
					totalMoney = obj[4].toString();
				}
			}catch(Exception e){
				logger.error("查询入库单信息失败!InputDepotManage2Action.editIutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询入库单信息失败，InputDepotManage2Action.editIutput()!",e);
			}
			request.setAttribute("supplierList", supplierList);
			request.setAttribute("inputList", inputList);
			request.setAttribute("inoutputList", inoutputList);
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("inputCode", inputCode);
			return mapping.findForward("editInput");
	}
	//执行修改
	public ActionForward doEditIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//String code = request.getParameter("code");
			InputDepotManageForm idmf = (InputDepotManageForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			idmf.setUserName(userName);
			try{
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				idmf.setLpId(lpId);
				/** 到此为止*/
				//删除入库单详细信息
				//inputdepotService.deleteInOutput(code);
				//更新出库单信息
				//更新出库单信息,在这之前删除出库单详细信息，完事后再添加详细信息。这些应该扔到一个事务中，所以应该放到一个service层的方法中
				inputdepotService.updateInput(idmf);
				//添加入库的详细信息
				//inputdepotService.doAddDetailInput(idmf);
				
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("修改入库单信息失败!InputDepotManage2Action.doEditIutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("修改入库单信息失败，InputDepotManage2Action.doEditIutput()!",e);
			}
			boolean flag = true;
			request.setAttribute("flag", flag);
			return mapping.findForward("editInput");
	}
	//执行删除
	public ActionForward doDelIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		InputDepotManageForm idmf = (InputDepotManageForm)form;
		setLogParam(request);
		idmf.setLogParam(logParam);//为日志操作初始化一些基本的信息
		try{
			inputdepotService.deleteInput(idmf);
		}catch(Exception e){
			e.printStackTrace();
		}
		response.sendRedirect("inputmanage.do?method=showInputMaterialsList");
		return null;
	}
	
	//执行结算
	public ActionForward doAccountIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			//获取要结算的id
			String accountId = request.getParameter("accountIds");
			String idArray[] = accountId.split(",");
			int[] idInt = new int[idArray.length];
			Integer[] ids = new Integer[idArray.length];
			//存储要修改的编号
			List codeList = new ArrayList();
			for(int i = 0;i<idArray.length;i++){
				idInt[i] = Integer.parseInt(idArray[i]);
				inputdepotService.doAccountInput(idInt[i]);//这个方法只更新结算状态
			}
			for(int i = 0;i<idArray.length;i++){
				//查询要修改的编号
				ids[i] = Integer.parseInt(idArray[i]);
				codeList = inputdepotService.selectIutputCode(ids);
			}
			if(null != codeList && codeList.size()>0){
				inputdepotService.selectDetailList(codeList);//根据编号查出材料编号用于更新材料库信息
			}
			response.sendRedirect("inputmanage.do?method=showInputMaterialsList");
			return null;
	}
	//查看入库单信息
	public ActionForward doViewIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			//查询供应商
			List supplierList = new ArrayList();
			List inputList = new ArrayList();
			List inoutputList = new ArrayList();
			//入库单编号
			String inputCode = "";
			//总金额
			String totalMoney = "";
			//获取要修改的id
			String strId = request.getParameter("editId");
			int id = Integer.parseInt(strId);
			try{
				//查询供应商
				supplierList = inputdepotService.selectSupplier();
				//查询显示要修改的信息
				inputList = inputdepotService.selectEditInput(id);
				//获取出库单编号查询出库的详细信息
				if(null != inputList || inputList.size()>0){
					DepotInputManager dim = (DepotInputManager)inputList.get(0);
					inputCode = dim.getCode();
					inoutputList = inputdepotService.selectDetailInput(inputCode);
				}
				if(inoutputList != null && inoutputList.size()>0){
					Object obj[] = (Object[])inoutputList.get(0);
					totalMoney = obj[4].toString();
				}
			}catch(Exception e){
				logger.error("查询入库单信息失败!InputDepotManage2Action.editIutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询入库单信息失败，InputDepotManage2Action.editIutput()!",e);
			}
			request.setAttribute("supplierList", supplierList);
			request.setAttribute("inputList", inputList);
			request.setAttribute("inoutputList", inoutputList);
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("inputCode", inputCode);
			return mapping.findForward("viewInput");
	}
	//导出入库单报表
	public ActionForward exportInputExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				//获取要显示内容
				//List list = (List)request.getSession().getAttribute("explortInputList");
				InputDepotManageForm idmf = (InputDepotManageForm)form;
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				idmf.setLpId(lpId);
				/** 到此为止*/
				List list = inputdepotService.selectAllInput(idmf);
				//表头
				String[] cellHeader = Constant.EXCEL_IN_DETAIL;
				String[] cellValue = Constant.EXCEL_IN_VALUE;
				//定义文件名
				String sheetName = "入库单列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "入库单列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出入库单excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
	
	
	/*入库明细表*/
	public ActionForward showTreeAndDetailIutput(ActionMapping mapping, ActionForm form,
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
		/*
		 * 把得到的树的菜单对象放在List<DTree> treeList对象中.
		 */
		for(int i = 0;i<materialsList.size();i++){
			DTree tree =  new DTree();
			DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
			tree.setId(dst.getId().toString());
			tree.setName(dst.getName());
			tree.setParentID(dst.getParentid().toString());
			tree.setUrl("inputmanage.do?method=showDetailIutput&dmtId="+dst.getId().toString());
			treeList.add(tree);
		}
		//用setAttribute()把得到的内容返回给JSP页面读取;
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "inputmanage.do?method=showDetailIutput");
		return mapping.findForward("showTreeAndDetailIutput");
	}
	
	//查询显示入库明细表的详细信息
	public ActionForward showDetailIutput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			InputDepotManageForm idmf = (InputDepotManageForm)form;
			
			//查询公司
			List supplierList = new ArrayList(); 
			//获取下拉列表
			String strSelect = GlobalMethod.NullToSpace(request.getParameter("supplierId"));
			
			//汇总信息
			List summaryList = new ArrayList();
			try{
				/** 下面夹着的代码是为了实现多楼盘的 */
				Map map1 = (Map) request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
				idmf.setLpId(lpId);
				/** 到此为止 */
				//查询供应商
				supplierList = inputdepotService.selectSupplier();
				//查询显示入库单的详细信息
				inputdepotService.selectInputDetail(idmf);
				//汇总入库信息的总数
				summaryList = inputdepotService.summaryInDetail(idmf);
				//查询记录的总条数
				int totalcount = inputdepotService.queryCountInDetail(idmf);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == idmf.getInputDatilList() || idmf.getInputDatilList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputmanage.do?method=showDetailIutput", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/inputmanage.do?method=showDetailIutput", Integer.parseInt(GlobalMethod.NullToParam(idmf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(idmf.getCurrentpage(),"1")), totalcount);
				}
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("select", strSelect);
				request.setAttribute("summaryList", summaryList);
//				request.getSession().setAttribute("inputDetailList", idmf.getInputDatilList());
			}catch(Exception e){
				logger.error("查询显示入库单信息失败!InputDepotManage2Action.showDetailIutput().详细信息：" + e.getMessage());
				throw new BkmisWebException("查询显示入库单信息失败，InputDepotManage2Action.showDetailIutput()!",e);
			}
			//返回供应商的
			request.setAttribute("supplierList", supplierList);
			return mapping.findForward("inDetail");
	}	
	//导出报表
	public ActionForward exportInputDetailExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			try{
				//获取入库明细详细信息
				//List list = (List)request.getSession().getAttribute("inputDetailList");
				InputDepotManageForm idmf = (InputDepotManageForm)form;
				List list = inputdepotService.selectAllDetail(idmf);
				//表头
				String[] cellHeader = Constant.EXCEL_INPUT_DETAIL;
				String[] cellValue = Constant.EXCEL_INPUT_VALUE;
				//定义文件名
				String sheetName = "入库信息列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(10000 * 1024);
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "入库明细列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出入库明细excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}

/** 打印入库单 */
public ActionForward inputListPrint(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response){
	
	List<InputDepotManageForm> list = null;
	String name = request.getParameter("name");
	String spec = request.getParameter("spec");
	String unitPrice = request.getParameter("unitPrice");
	String amount = request.getParameter("amount");
	String amountMoney = request.getParameter("amountMoney");	
	try {
		
		InputDepotManageForm idmf= (InputDepotManageForm)form;
		list = inputdepotService.selectAllDetail(idmf);
		
	} catch (Exception e) {
		logger.error("打印入库单失败!InputDepotManage2Action.print().详细信息：" + e.getMessage());
		throw new BkmisWebException("打印入库单失败，InputDepotManage2Action.print()!",e);
	}
	
	request.setAttribute("inputDatilList",list);
	request.setAttribute("name",name);
	request.setAttribute("spec",spec);
	request.setAttribute("unitPrice",unitPrice);
	request.setAttribute("amount",amount);
	request.setAttribute("amountMoney",amountMoney);
	
	return mapping.findForward("inputListPrint");
}
}
