/**
 * Administrator
 */
package com.zc13.bkmis.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CChoiceForm;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.service.ICBillService;
import com.zc13.bkmis.service.ICChoiceService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.util.DTree;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;

import common.Logger;

/**
 * 收费选择
 * 
 * @author zhaosg Date：Dec 7, 2010 Time：2:21:33 PM
 */
public class CChoiceAction extends BasicAction {

	Logger logger = Logger.getLogger(getClass());
	private ICChoiceService icChoiceService;
	private ICostTransactService iCostTransactService;
	private ICBillService icBillService;

	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		icChoiceService = (ICChoiceService) super.getBean("icChoiceService");
		iCostTransactService = (ICostTransactService) super.getBean("costTransactService");
		icBillService = (ICBillService) super.getBean("icBillService");
	}

	/**
	 * 收费选择查询 CChoiceAction.getList
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CChoiceForm formbean = (CChoiceForm) form;
		List list = null;
		CCoststandard standard = null;
		List<EBuilds> EBuilds = null;
		try {
			list = icChoiceService.getList(formbean);
			standard = icChoiceService.getCStandard(formbean);
			EBuilds = icChoiceService.getEBuilds(formbean);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("收费选择查询失败：CChoiceAction.getList" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		request.setAttribute("standard", standard);
		request.setAttribute("EBuilds", EBuilds);
		request.setAttribute("standardId", formbean.getStandardId());
		request.setAttribute("lpId", formbean.getLpId());
		request.setAttribute("buildId", formbean.getBuildId());
		request.setAttribute("floor", formbean.getFloor());
		request.setAttribute("clientName", formbean.getClientName());
		request.setAttribute("billType", formbean.getBillType());
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		return mapping.findForward("list");
	}

	/**
	 * 树：帐套--费用类型--收费标准 CChoiceAction.getTreeList
	 */
	public ActionForward getTreeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CChoiceForm choiceForm = (CChoiceForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		choiceForm.setLpId(lpId);
		/** 到此为止*/
		// TODO Auto-generated method stub
		List<DTree> treeList = null;
		try {
			treeList = icChoiceService.getTreeList(choiceForm);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("收费标准查询失败：CChoiceAction.getTreeList" + e.getMessage());
			e.printStackTrace();
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",
				"zc13/bkmis/costManage/c_choice.jsp");
		return mapping.findForward("tree");
	}

	/**
	 * 保存 CChoiceAction.save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] id = request.getParameterValues("checkbox");
		CChoiceForm choiceForm = (CChoiceForm) form;
		if (id==null) {
			choiceForm.setCheckbox(null);
		}
		try {
			/**1.保存收费选择*/
			icChoiceService.save(choiceForm);
			
			
			/**2.判断是否需要 生成物业费账单 --取消*/
			/*List csList = choiceForm.getCsList();// 收费标准
			boolean flag = false ;
			if(csList!=null && csList.size() > 0){
				Integer ID = ((CCoststandard) csList.get(0)).getId();
				CCoststandard cStandard = icChoiceService.getCCoststandardById(ID);
				String standardName = cStandard.getStandardName();
				if(standardName!=null && 
						(standardName.equals(Contants.WuYFJF)
								||standardName.equals(Contants.WuYFBNF)
								||standardName.equals(Contants.WuYFNF))){
					flag = true ;
				}
			}
			List<Integer> checkboxs = new ArrayList<Integer>();
			if (choiceForm.getCheckbox() != null) {
				checkboxs = Arrays.asList(choiceForm.getCheckbox());
			}
			if(flag && checkboxs!=null){
				iCostTransactService.autoBuildWuYFBill(true);
			}*/
			
		} catch (Exception e) {
			logger.error("收费标准保存失败：CChoiceAction.save" + e.getMessage());
			e.printStackTrace();
		}
		return this.getList(mapping, form, request, response);
	}
	/**
	 * 费用录入(高级)
	 * CChoiceAction.saveAccount
	 */
	public ActionForward saveAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CChoiceForm formbean = (CChoiceForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		formbean.setLpId(lpId);
		/** 到此为止*/
		Map map = (Map)request.getSession().getAttribute("userInfo");
		Integer userId = (Integer)map.get("userid");
		formbean.setUserId(userId);
		try {
			icChoiceService.saveBill(formbean,request);
			iCostTransactService.updateAwokeTask4PressMoney();
		} catch (Exception e) {
			logger.error("账单保存失败：" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("success",true);
		request.setAttribute("ids", GlobalMethod.NullToSpace(request.getParameter("ids")));
		request.setAttribute("Person", GlobalMethod.NullToSpace(request.getParameter("Person")));
		return queryInput(mapping, form, request, response);
	}
	/**
	 * 费用录入
	 * CChoiceAction.saveAccount
	 */
	public ActionForward saveAccount2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CChoiceForm formbean = (CChoiceForm) form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		Integer userId = (Integer)map.get("userid");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		formbean.setUserId(userId);
		formbean.setLpId(lpId);
		try {
			icChoiceService.saveBill(formbean,request);
			iCostTransactService.updateAwokeTask4PressMoney();
		} catch (Exception e) {
			logger.error("账单保存失败：" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("success",true);
		request.setAttribute("ids", GlobalMethod.NullToSpace(request.getParameter("ids")));
		request.setAttribute("Person", GlobalMethod.NullToSpace(request.getParameter("Person")));
		return queryInput2(mapping, form, request, response);
	}
	/**
	 * 费用录入-选择客户 CBillAction.getClientList
	 */
	public ActionForward getClientList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CChoiceForm choiceForm = (CChoiceForm) form;
		List<DTree> treeList = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			choiceForm.setLpId(lpId);
			/** 到此为止 */
			treeList = icChoiceService.getClientList(choiceForm);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("客户查询失败：CBillAction.getClientList" + e.getMessage());
			e.printStackTrace();
		}
		request.getSession().setAttribute("treeList", treeList);
		return mapping.findForward("person");
	}
	
	/**
	 * to费用录入高级
	 * CChoiceAction.queryInput
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<CItems> itemList = icBillService.getItemList();// 收费项目
		String applayCode = GlobalMethod.getTime4();//申请单号2013-03-31添加
		request.setAttribute("itemList", itemList);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("applayCode", applayCode);
		return mapping.findForward("input");
	}
	/**
	 * to费用录入
	 * CChoiceAction.queryInput
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryInput2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<CItems> itemList = null;// 收费项目
		itemList = icBillService.getItemList();
		String applyCode = GlobalMethod.getTime4();//申请单号2013-03-31添加为自动生成
		request.setAttribute("itemList", itemList);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("applyCode", applyCode);
		return mapping.findForward("input2");
	}
}
