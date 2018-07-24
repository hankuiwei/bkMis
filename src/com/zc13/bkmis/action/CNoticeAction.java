/**
 * ZHAOSG
 */
package com.zc13.bkmis.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CNoticeForm;
import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CNotice;
import com.zc13.bkmis.service.ICNoticeService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

/**
 * @author ZHAOSG
 * Date：Jan 3, 2011
 * Time：2:36:45 PM
 */
public class CNoticeAction extends BasicAction {
	private ICNoticeService icNoticeService;
	private ICostTransactService iCostTransactService;
	/** 
	 * CNoticeAction.setServlet
	 */
	public void setServlet(ActionServlet servlet) {
		// TODO Auto-generated method stub
		super.setServlet(servlet);
		icNoticeService = (ICNoticeService) getBean("icNoticeService");
		iCostTransactService = (ICostTransactService) super.getBean("costTransactService");
	}

	/** 
	 * CNoticeAction.getList
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		noticeForm.setLpId(lpId);
		/** 到此为止*/
		//List<ELp> lpList = icNoticeService.getLp();
		//List<EBuilds> buildList = icNoticeService.getBuild();
		PageBean pageBean = icNoticeService.getNoticeList(noticeForm);
		request.setAttribute("list", pageBean.getList());
		request.setAttribute("pageHTML", pageBean.toString("cx()"));
		//request.setAttribute("lpList", lpList);
		//request.setAttribute("buildList", buildList);
		return mapping.findForward("list");
	}

	/** 
	 * CNoticeAction.save-->保存添加的催缴通知
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		noticeForm.setLpId(lpId);
		/** 到此为止*/
		int count = 0;
		try {
			CostTransactForm transactForm = new CostTransactForm();
			Integer[] clientIds = noticeForm.getClientId();
			Map<Integer,Map<String,List>> map = new HashMap<Integer,Map<String,List>>();//保存所有客户的 /预收房租/ 房租押金/ 装修押金/ 定金/的信息
			if(clientIds!=null&&clientIds.length>0){
				//循环遍历选取的每个客户，计算出每个客户需要缴纳的 /预收房租/，押金，/定金/等信息
				for(int i = 0;i<clientIds.length;i++){
					Map<String,List> clientMap = new HashMap<String,List>();//保存每个客户的预收房租 房租押金 装修押金 定金的信息
					transactForm.setClientId(clientIds[i]);
					//获得需要缴纳预收款的信息
					clientMap.put("pressAdvance", iCostTransactService.getPressAdvanceClient(transactForm));
					//房租押金
					transactForm.setDepositType(Contants.RENT_DEPOSIT);
					clientMap.put("rentPressDeposit", iCostTransactService.getPressDepositClient(transactForm));
					//装修押金
					transactForm.setDepositType(Contants.DECORATION_DEPOSIT);
					clientMap.put("decorationPressDeposit", iCostTransactService.getPressDepositClient(transactForm));
					//定金
					clientMap.put("pressEarnest", iCostTransactService.getPressEarnestClient(transactForm));
					map.put(clientIds[i], clientMap);
				}
			}
			count = icNoticeService.save(noticeForm,map);
			if(count<=0){
				request.setAttribute("save", "没有需要缴费的客户！");
			}else{
				request.setAttribute("save", "保存成功！");
			}
		} catch (Exception e) {
			request.setAttribute("save", "保存失败！");
			e.printStackTrace();
		}
		return getNoticePop(mapping, form, request, response);
	}

	/** 
	 * CNoticeAction.delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		icNoticeService.delete(noticeForm);
		return getList(mapping, form, request, response);
	}

	/** 
	 * CNoticeAction.execute
	 */
	public ActionForward getNoticePop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		noticeForm.setLpId(lpId);
		/** 到此为止*/
		List clientList = icNoticeService.getClientList(noticeForm);
		//List standardList = icNoticeService.getStandardList(noticeForm);
		List itemList = icNoticeService.getItemList(noticeForm);
		//List<ELp> lpList = icNoticeService.getLp();
		request.setAttribute("clientList", clientList);
//		request.setAttribute("standardList", standardList);
		request.setAttribute("itemList", itemList);
		request.setAttribute("today", DateUtil.getNowDate("yyyy-MM-dd"));
		//request.setAttribute("lpList", lpList);
		return mapping.findForward("add");
	}

	/** 
	 * CNoticeAction.execute
	 */
	public ActionForward getNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		List<CNotice> noticeList = icNoticeService.getNotice(noticeForm);
		CNotice notice = null;
		double amount = 0;
		if (noticeList!=null&&noticeList.size()>0) {
			notice = noticeList.get(0);
			for (CNotice notice1 : noticeList) {
				amount += notice1.getAmount();
			}
		}
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("notice", notice);
		request.setAttribute("amount", amount);
		return mapping.findForward("detail");
	}
	/**
	 * 打印全部
	 * CNoticeAction.printAll
	 */
	public ActionForward printAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		noticeForm.setLpId(lpId);
		/** 到此为止*/
		List list = icNoticeService.getAllNoticePrint(noticeForm);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("print");
	}
	/**
	 *  打印
	 * CNoticeAction.print
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CNoticeForm noticeForm = (CNoticeForm) form;
		List list = icNoticeService.getNoticePrint(noticeForm);
		request.setAttribute("list", list);
		return mapping.findForward("print");
	}
	
	
}
