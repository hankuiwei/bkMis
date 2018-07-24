/**
 * Administrator
 */
package com.zc13.bkmis.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CBillForm;
import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CTemporal;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ICBillService;
import com.zc13.bkmis.service.ICNoticeService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.service.IUserManagerService;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.ExplortExcel;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

import common.Logger;

/**
 * @author Administrator Date：Dec 20, 2010 Time：2:42:10 PM
 */
public class CBillAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ICBillService icBillService;
	private ICostTransactService iCostTransactService;
	private IUserManagerService iUserManagerService;
	private ICNoticeService icNoticeService;

	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		icBillService = (ICBillService) super.getBean("icBillService");
		iCostTransactService = (ICostTransactService) super.getBean("costTransactService");
		iUserManagerService = (IUserManagerService)super.getBean("IUserManagerService");
		icNoticeService = (ICNoticeService) getBean("icNoticeService");
	}
	/**
	 * 导出报表（账单统计）
	 * CBillAction.exportExcel
	 */
	public ActionForward exportExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			//list中存放的就是当前页面上显示的所有数据
			CBillForm formbean = (CBillForm)form;
			List<CBill> billList = icBillService.getExcelList(formbean);
			List<CBill> list = new ArrayList<CBill>();// 账单
			List<CItems> itemList = icBillService.getItemList();
			//滞纳金
			for (CBill bill : billList) {
				if (bill.getItemId() != null) {
					for (CItems items : itemList) {
						if (bill.getItemId().intValue()==items.getId().intValue()) {
							bill.setStandardName(items.getItemName());
							break;
						}
					}
				}
				if ("0".equals(bill.getStatus())&&!ExtendUtil.checkNull(bill.getCloseDate())) {
					list.add(iCostTransactService.getDelaying(bill));
				}else {
					list.add(bill);
				}
			}
			//计算未来时间段房租
			Integer rent = icBillService.getItems("rent").getId();
			if (formbean.getItemId()==null || rent.intValue()==formbean.getItemId().intValue()) {
				if (!ExtendUtil.checkNull(formbean.getBefore())&&DateUtil.parseDate(formbean.getBefore()+"-01").after(new Date())) {
					String after = formbean.getAfter();
					if (!ExtendUtil.checkNull(after)) {
						String[] time = after.split("-");
						Calendar calendar = Calendar.getInstance();
						calendar.set(Integer.parseInt(time[0]), Integer.parseInt(time[1])-1, 1);
						list = new ArrayList<CBill>();
						List<CompactClient> clients = icBillService.getClients(formbean);
						for (CompactClient compactClient : clients) {
							CBill bill = new CBill();
							bill.setCompactClient(compactClient);
							bill.setBillsExpenses(iCostTransactService
									.countRentCharge(compactClient.getId(),
											null, formbean.getBefore() + "-01",
											DateUtil.getLastDay(calendar)));
							bill.setItemId(rent);
							bill.setStatus("0");
							list.add(bill);
						}
					}
				}
			}
			//表头
			String[] cellHeader = Constant.EXCEL_BILL_HEADER;
			String[] cellValue = Constant.EXCEL_BILL_VALUE;
			//定义文件名
			String sheetName = "账单统计信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new CBill());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "账单统计信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出应收账款excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 应收账款-费用树
	 */
	/**public ActionForward getCostTreeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<DTree> treeList = icBillService.getCostTreeList();
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",
				"bill.do?method=getList&standardId=");
		return mapping.findForward("tree");
	}*/
	
	/**
	 * 账单统计 CBillAction.getList
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		formbean.setLpId(lpId);
		/** 到此为止*/
		
		String flag = GlobalMethod.NullToSpace(request.getParameter("flag"));
		String forward = "account";
		if(flag.equals("print")){//如果是转向打印页面
			forward = "accountPrint";
			formbean.setPagesize(99999);//
		}
		List<CBill> list = new ArrayList<CBill>();// 账单
		String pageHTML = "";
		List<CItems> itemList = null;// 收费项
		List<ELp> ELps = null;
		Integer fz = null;
		Integer zc = null;
		Integer fzyj = null;//房租押金
		Integer zxyj = null;//装修押金
		Integer dj = null;
		Integer rentItemId = null;
		Integer WuYF = null ;//物业费
		Integer WuYFJF = null ;//物业费季付
		
		ELps = icBillService.queryELp();
		itemList = icBillService.getItemList();//获取所有收费项?
		String ysfzANDfz = "";//预收房租和房租
		//合并预收房租和房租收费项
		if(itemList!=null&&itemList.size()>0){
			for(CItems items:itemList){
				if(!ExtendUtil.checkNull(items.getItemName())&&(items.getItemName().equals("房租")||items.getItemName().equals("预收房租"))){
					ysfzANDfz += items.getId()+",";
					if ("destineRent".equals(items.getValue())) {//预定租金
						fz = items.getId();
					}else if ("rent".equals(items.getValue())) {//租金
						rentItemId = items.getId();
					}
				} else if ("tempporal".equals(items.getValue())) {//暂存款 
					zc = items.getId();
				} else if ("rent_deposit".equals(items.getValue())) {//租金押金
					fzyj = items.getId();
				} else if ("decoration_deposit".equals(items.getValue())) {//装修押金
					zxyj = items.getId();
				} else if ("earnest".equals(items.getValue())) {//定金
					dj = items.getId();
				} else if (items.getItemName().equals("物业费")) {//物业费
					WuYF = items.getId();
				} else if (items.getItemName().equals("预收物业费")) {//物业费季付
					WuYFJF = items.getId();
				} 
			}
			if(!ysfzANDfz.equals("")){
				ysfzANDfz = ysfzANDfz.substring(0, ysfzANDfz.length()-1);
			}
		}
		
		//账单分页
		PageBean pageBean = icBillService.getList(formbean);// 统计所有 预付款余额.摄入formbean中
		pageHTML = pageBean.toString("cx()");
		//部分账单
		List<CBill> billList = pageBean.getList();
		//账单全部
		List<CBill> allBill = icBillService.getExcelList(formbean);
		
		//操作员列表0, "999", "1", ""                          
		UserForm uf = new UserForm();
		uf.getUsername();
		uf.getRange();
		uf.getPagesize();
		uf.getCurrentpage();
		/**以上四行 什么作用*/
		
		List usersList = iUserManagerService.findUser(uf,false);//得到一堆 操作员对象?
		
		//计算未来某个时间的 房租 明细
		if (formbean.getItemId()==null || rentItemId.intValue()==formbean.getItemId().intValue()) {// 空查询,或 查询租金 时 进入代码块
			if (!ExtendUtil.checkNull(formbean.getBefore()) && DateUtil.parseDate(formbean.getBefore()+"-01").after(new Date())) {// Before账期-开始 不为空,进入代码块 , 现在 < 账期开始 --> 
				String after = formbean.getAfter();
				if (!ExtendUtil.checkNull(after)) {//账期-结束
					String[] time = after.split("-");//2011,1,1
					Calendar calendar = Calendar.getInstance();//账单结束日
					calendar.set(Integer.parseInt(time[0]), Integer.parseInt(time[1])-1, 1);
					
					billList = new ArrayList<CBill>();
					
					PageBean clientBean = icBillService.queryClient(formbean);// pagebean.getlist  客户 对象集合
					List<CompactClient> clients = clientBean.getList();
					for (CompactClient compactClient : clients) {
						CBill bill = new CBill();
						bill.setCompactClient(compactClient);
						//设置 合同金额
						bill.setBillsExpenses(iCostTransactService.countRentCharge(compactClient.getId(),null, formbean.getBefore() + "-01",DateUtil.getLastDay(calendar)));
						bill.setItemId(rentItemId);
						bill.setStatus("0");
						billList.add(bill);
					}
					/**跟上面的代码 有什么差别? */
					allBill = new ArrayList<CBill>();
					List<CompactClient> allClient = icBillService.getClients(formbean);
					for (CompactClient compactClient : allClient) {
						CBill bill = new CBill();
						bill.setCompactClient(compactClient);
						bill.setBillsExpenses(iCostTransactService.countRentCharge(compactClient.getId(),null, formbean.getBefore() + "-01",DateUtil.getLastDay(calendar)));
						bill.setItemId(rentItemId);
						bill.setStatus("0");
						allBill.add(bill);
					}
					pageHTML = clientBean.toString("cx()");
				}
			}
		}
		//滞纳金
		for (CBill bill : billList) {
			if ("0".equals(bill.getStatus())&&!ExtendUtil.checkNull(bill.getCloseDate())) {
				list.add(iCostTransactService.getDelaying(bill));
			}else {
				list.add(bill);
			}
		}
		//总计
		double total = 0;//实收总金额
		double adv = 0;//预收房租
		double advWuYF = 0 ;//预收物业费
		double tem = 0;//暂存款
		double fzdes = 0;//房租押金
		double zxdes = 0;//装修押金
		double ear = 0;//定金
		double billAmount = 0;//定金
		
		double xj = 0;//现金
		double zp = 0;//支票
		double zz = 0;//转账
		double sk = 0;//刷卡
		double toyj = 0;//订金转房租押金
		double tofz = 0;//订金转预收房租
		
		for (CBill bill2 : allBill) {
			total += bill2.getActuallyPaid();//已收金额
			billAmount += bill2.getBillsExpenses();//合同金额
			
			if (bill2.getItemId() != null) {
				if (fz.intValue() == bill2.getItemId().intValue()) {//destineRent
					adv += bill2.getActuallyPaid();
				} else if (zc.intValue() == bill2.getItemId().intValue()) {//tempporal 暂存款
					tem += bill2.getActuallyPaid();
				} else if (fzyj.intValue() == bill2.getItemId().intValue()) {//rent_deposit 房租押金
					fzdes += bill2.getActuallyPaid();
				} else if (zxyj.intValue() == bill2.getItemId().intValue()) {//decoration_deposit装修押金
					zxdes += bill2.getActuallyPaid();
				} else if (dj.intValue() == bill2.getItemId().intValue()) {//earnest 定金
					ear += bill2.getActuallyPaid();
				} else if (WuYF.intValue() == bill2.getItemId().intValue() || WuYFJF.intValue() == bill2.getItemId().intValue()) {//物业费 和 物业费季付 
					advWuYF += bill2.getActuallyPaid();//WuYF
				} 
			}
			
			
			if ("0".equals(bill2.getPaymentWay())) {//付款方式
				xj += bill2.getActuallyPaid(); 
			} else if ("1".equals(bill2.getPaymentWay())) {
				zp += bill2.getActuallyPaid();
			} else if ("2".equals(bill2.getPaymentWay())) {
				zz += bill2.getActuallyPaid();
			} else if ("3".equals(bill2.getPaymentWay())) {
				sk += bill2.getActuallyPaid();
			} else if (fzyj.intValue() == bill2.getItemId().intValue() && "4".equals(bill2.getPaymentWay())) {
				toyj += bill2.getActuallyPaid();
			} else if (fz.intValue() == bill2.getItemId().intValue() && "5".equals(bill2.getPaymentWay())) {
				tofz += bill2.getActuallyPaid();
			}
		}
		request.setAttribute("size", list.size());
		request.setAttribute("adv", adv);
		
		/**2018年2月27日09:54:23 添加预收物业费 */
		request.setAttribute("advWuYF", advWuYF);
		
		request.setAttribute("tem", tem);
		request.setAttribute("fzdes", fzdes);//房租押金
		request.setAttribute("zxdes", zxdes);//装修押金
		request.setAttribute("ear", ear);
		request.setAttribute("xj", xj);
		request.setAttribute("zp", zp);
		request.setAttribute("zz", zz);
		request.setAttribute("sk", sk);
		request.setAttribute("toyj", toyj);
		request.setAttribute("tofz", tofz);
		request.setAttribute("billAmount", billAmount);
		request.setAttribute("list", list);
		request.setAttribute("pageHTML",pageHTML);
		request.setAttribute("itemList", itemList);
		request.setAttribute("usersList", usersList);
		request.setAttribute("ELps", ELps);
		request.setAttribute("itemIds", formbean.getItemIds());
		request.setAttribute("payType", formbean.getPayType());
		request.setAttribute("totalAmount", total);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("ysfzANDfz", ysfzANDfz);
		return mapping.findForward(forward);
	}

	/**
	 * 添加账单 CBillAction.save
	 */
	/**public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		try {
			icBillService.saveBill(formbean);
			iCostTransactService.updateAwokeTask4PressMoney();
		} catch (Exception e) {
			logger.error("账单保存失败：CBillAction.save" + e.getMessage());
			throw new BkmisWebException("账单保存失败：CBillAction.save", e);
		}
		return this.getList(mapping, form, request, response);
	}*/

	/**
	 * 账单删除 CBillAction.delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		try {
			icBillService.delete(formbean);
			iCostTransactService.updateAwokeTask4PressMoney();
		} catch (Exception e) {
			logger.error("删除失败：CBillAction.delete" + e.getMessage());
		}
		return this.getList(mapping, form, request, response);
	}

	/**
	 * 收款销账-客户树 CBillAction.getCilentTreeList
	 *//**
	public ActionForward getClientTreeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<DTree> treeList = null;
		try {
			treeList = icBillService.getCilentTreeList();
		} catch (Exception e) {
			logger.error("客户查询失败：CBillAction.getCilentTreeList"
					+ e.getMessage());
			throw new BkmisWebException("客户查询失败：CBillAction.getCilentTreeList",
					e);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",
				"zc13/bkmis/costManage/c_collection.jsp");
//		return queryCollectClient(mapping, form, request, response);
		return mapping.findForward("tree");
	}
	/**
	 * 收款销账客户列表(查询所有的客户)
	 * CBillAction.queryBillClient
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryCollectClient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		billForm.setLpId(lpId);
		/** 到此为止*/
		PageBean pageBean = icBillService.queryCollect(billForm);
		List<CompactClient> clients = pageBean.getList();
		List<Compact> compacts = icBillService.queryCompact(clients);
		String pageHTML = pageBean.toString("cx()");
		
		request.setAttribute("list", clients);
		request.setAttribute("compacts", compacts);
		request.setAttribute("pageHTML", pageHTML);
		request.setAttribute("clientName", billForm.getClientName());
		request.setAttribute("roomCode", billForm.getRoomCode());
		return mapping.findForward("collectClient");
	}
	/**
	 * 收款销账收费页面 (指定客户)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getBillList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		List<CBill> billList = new ArrayList<CBill>();// 账单
		List<CItems> itemList = null;// 收费项目
		CompactClient client = new CompactClient();// 客户
		CAdvance advance = new CAdvance();// 预收款
		CAdvanceWuYF advanceWuYF = new CAdvanceWuYF();// 预收款
		CTemporal temporal = new CTemporal();// 暂存款
		CDeposit rentDeposit = new CDeposit();//房租押金
		CDeposit decorationDeposit = new CDeposit();//装修押金
		CEarnest earnest = new CEarnest();//订金
		List<Compact> compacts = null;
		double rentDepositcost = 0;//需要缴纳的房租押金金额
		double decorationDepositcost = 0;//需要缴纳的装修押金金额
		double earnestcost = 0;
		double advancecost = 0;
		double advanceWuYFCost = 0;
		double currentAdvanceCost = 0;		
		double currentWuYFAdvanceCost = 0;		
		double nextAdvanceCost = 0;
/**2018年1月17日14:46:45 gd */
		/*double nextWuYFAdvanceCost = 0;*/
		//获得收据号
		String billNum = null;
		try {
			List<CBill> list = icBillService.getCBilList(formbean);//获得指定客户的 未缴费的  账单
			for (CBill bill : list) {
				if (!ExtendUtil.checkNull(bill.getCloseDate())) {
					billList.add(iCostTransactService.getDelaying(bill));
				}else {
					billList.add(bill);
				}
			}
			billNum = icBillService.getBillNum();
			itemList = icBillService.getItemList();
			client = icBillService.getClient(formbean);
			advance = icBillService.getAdvance(formbean);
			advanceWuYF =  icBillService.getAdvanceWuYF(formbean);
			temporal = icBillService.geTemporal(formbean);
			rentDeposit = icBillService.getCDeposit(formbean,Contants.RENT_DEPOSIT);
			decorationDeposit = icBillService.getCDeposit(formbean,Contants.DECORATION_DEPOSIT);
			earnest = icBillService.getEarnest(formbean);
			compacts = icBillService.queryCompact(formbean);
			
			double[] cost = icBillService.getCost(formbean);
			if (cost != null) {
				rentDepositcost = cost[0];//房屋押金 
				decorationDepositcost = cost[1];//装修押金
				earnestcost = cost[2];//定金
			}
			
			CostTransactForm costTransactForm = new CostTransactForm();
			costTransactForm.setClientId(formbean.getClientId());
			/**!!获得各种缴费信息*/
			List rents = iCostTransactService.getPressAdvanceClient(costTransactForm);//集合中 存储的是map。！！！（内容包括  下周期房租，物业费季付金 ）
			if (rents != null && rents.size()>0) {
				Map map = (Map) rents.get(0);
				String mustPay = map.get("mustPay").toString();//需要缴纳 的 最低金额 预付租金 
				String curCircleRent = map.get("curCircleRent").toString();//当前周期还需要缴纳的房租
				String nextRent = map.get("nextRent").toString();//下一周期房租
				
				String curCircleWuYFJF = map.get("curCircleWuYF").toString();
				/*String nextWuyjf = map.get("nextWuyf").toString();*/
				String leastPay = map.get("leastPay").toString();//需要缴纳 的 最低预付物业费 
				
				if (!ExtendUtil.checkNull(mustPay)) {
					advancecost = Double.parseDouble(mustPay );
					currentAdvanceCost = Double.parseDouble(curCircleRent);
					nextAdvanceCost = Double.parseDouble(nextRent);
					
					advanceWuYFCost = Double.parseDouble(leastPay);
					currentWuYFAdvanceCost = Double.parseDouble(curCircleWuYFJF);
					/*nextWuYFAdvanceCost = Double.parseDouble(nextWuyjf);*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("收款销账查询失败：" + e.getMessage());
		}
		request.setAttribute("billNum", billNum);
		request.setAttribute("billList", billList);
		request.setAttribute("itemList", itemList);
		request.setAttribute("compacts", compacts);
		request.setAttribute("clientId", formbean.getClientId());
		request.setAttribute("itemId", formbean.getItemId());
		request.setAttribute("roomCode", formbean.getRoomCode());
		request.setAttribute("client", client);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("advance", advance);
		request.setAttribute("advanceWuYF", advanceWuYF);// 预存物业费 
		request.setAttribute("temporal", temporal);
		request.setAttribute("earnest", earnest);
		request.setAttribute("rentDeposit", rentDeposit);
		request.setAttribute("decorationDeposit", decorationDeposit);
		request.setAttribute("advancecost", advancecost <= 0 ? 0 : advancecost);
		request.setAttribute("currentAdvanceCost", currentAdvanceCost);
		request.setAttribute("nextAdvanceCost", nextAdvanceCost);
		
/**2018年1月17日14:47:21 gd start */
		request.setAttribute("advanceWuYFCost", advanceWuYFCost);
//		request.setAttribute("nextWuYFJFAdvanceCost", nextWuYFJFAdvanceCost);
		request.setAttribute("currentWuYFAdvanceCost", currentWuYFAdvanceCost);
		// Description 添加了 当前周期 物业费季付金, 下周期的 物业费季付金 . 
/** 2018年3月26日15:54:21 update end */		
		
		request.setAttribute("earnestcost", earnestcost);
		request.setAttribute("rentDepositcost", rentDepositcost);
		request.setAttribute("decorationDepositcost", decorationDepositcost);
		String success = request.getParameter("success");
		if ("1".equals(success)) {
			request.setAttribute("success", true);
		}else {
			request.setAttribute("success", false);
		}
		return mapping.findForward("collection");
	}
	
	
	//收款销账页面-打印账单
	public ActionForward printBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		List<CBill> billList = new ArrayList<CBill>();// 账单
		List<CItems> itemList = null;// 收费项目
		CompactClient client = new CompactClient();// 客户
		double amount = 0;
		String roomCode = "";
		try {
			List<CBill> list = icBillService.getCBilList(formbean);
			for (CBill bill : list) {
				if (!ExtendUtil.checkNull(bill.getCloseDate())) {
					billList.add(iCostTransactService.getDelaying(bill));
				}else {
					billList.add(bill);
				}
				amount += bill.getBillsExpenses()+bill.getDelayingExpenses()-bill.getActuallyPaid();
			}
			itemList = icBillService.getItemList();
			client = icBillService.getClient(formbean);
			roomCode = icNoticeService.getRoomCodeByClientId(client.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("billList", billList);
		request.setAttribute("itemList", itemList);
		request.setAttribute("clientId", formbean.getClientId());
		request.setAttribute("client", client);
		request.setAttribute("roomCode", roomCode);
		request.setAttribute("amount", amount);
		return mapping.findForward("print");
	}
	
	/**
	 * 收取费用 CBillAction.editBill
	 */
	public ActionForward editBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		formbean.getCinvoice().setOperatorId(userId);
		//2012-3-13改
		String billType = GlobalMethod.NullToSpace(formbean.getBill().getBillType());//单据类型
		String isPrint = GlobalMethod.NullToSpace(request.getParameter("isPrint"));//是否打印
		//加入日志的管理中
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		formbean.setLpId(lpId);
		formbean.setUserName(userName);
		String[] id = request.getParameterValues("checkbox");
		Integer chargeId = null;//收款记录id
		if (id==null) {
			formbean.setCheckbox(null);
		}
		//收款操作
		try {
			icBillService.updateBill(formbean);//账单
			String id1 = icBillService.updateTemporal(formbean);// 暂存款
			String id2_1 = icBillService.updateAdvance(formbean);// 预收款
			icBillService.saveAdvanceDetail(formbean);//预收款记录
			/**gd 2018年3月3日14:40:26  添加 预收物业费 记录*/
			String id2_2 = icBillService.updateAdvanceWuYF(formbean);// 预收物业费
			icBillService.saveAdvanceWuYFDetail(formbean);//预收物业费记录
			
			String id3_1 = icBillService.saveDeposit(formbean,Contants.RENT_DEPOSIT);//房租押金
			String id3_2 = icBillService.saveDeposit(formbean,Contants.DECORATION_DEPOSIT);//装修押金
			String id4 = icBillService.saveEarnest(formbean);//定金
			
			chargeId = icBillService.saveCharge(formbean, id1, id2_1,id2_2, id3_1,id3_2, id4);//收取记录
			/**2012-3-9根据需求变动 
			 * -> 在选择单据类型为发票时,需要向发票表和发票账单表中插入数据，
			 * 为了在发票管理中查询时,能够查询出费用金额具体是那种费用
			 */
			if(Contants.BILLTYPE_INVOICE.equals(billType)){
				icBillService.saveInvoice(formbean, id1, id2_1,id2_2,id3_1,id3_2, id4);
			}
			 /**
			 * 2018年3月4日15:06:45 下面方法执行时间过长 !!!
			 * 尝试 添加多线程 操作! -效果待测试  --> 
			 * 2018年2月8日09:40:08 gd ,出现页面多次刷新,暂时取消多线程
			 * */
//			synchronized(CBillAction.class){
//				new Thread(){
//					@Override
//					public void run() {
						iCostTransactService.updateAwokeTask4PressMoney();
						iCostTransactService.updateAwokeTask4PressAdvance();
						iCostTransactService.updateAwokeTask4PressRentDeposit();
						iCostTransactService.updateAwokeTask4PressDecorationDeposit();
						iCostTransactService.updateAwokeTask4PressEarnest();
						iCostTransactService.updateAwokeTask4PressENotIn();
//					}
//				}.start();
//			}
		} catch (Exception e) {
			logger.error("收款销账失败：CBillAction.editBill" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("success", true);
		request.setAttribute("isPrint", isPrint);
		request.setAttribute("chargeId", chargeId);
		response.sendRedirect("bill.do?method=getBillList&clientId="+formbean.getClientId()+"&success=1&isPrint="+isPrint+"&chargeId="+chargeId);
		return null;
	}
	
	/**
	 * 打印收据单
	 */
	public ActionForward printSj(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String clientName = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("clientName"),""),"utf-8");
		String dx = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("dx"),""),"utf-8");
		String billNum = request.getParameter("billNum");
		String amount = request.getParameter("amount");
		String billAmount = GlobalMethod.NullToParam(request.getParameter("billAmount"),"");//账单金额
		String advanceAmount = GlobalMethod.NullToParam(request.getParameter("advanceAmount"),"");//预收房租
		/**gd 2018年3月3日16:04:32  添加预收物业费  
		 * 
		 * 预留位*/
		
		String rentDepositAmount = GlobalMethod.NullToParam(request.getParameter("rentDepositAmount"),"");//房租押金
		String decorationDepositAmount = GlobalMethod.NullToParam(request.getParameter("decorationDepositAmount"),"");//装修押金
		String earnestAmount = GlobalMethod.NullToParam(request.getParameter("earnestAmount"),"");//定金
		String paymentWay = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("paymentWay"),""),"utf-8");//支付方式
		String date = request.getParameter("date");
		String billIds = GlobalMethod.NullToSpace(request.getParameter("billIds"));//账单id
		List<CBill> billList = icBillService.getBillByIds(billIds);
		List<CItems> itemList = icBillService.getItemList();
		request.setAttribute("clientName", clientName);
		request.setAttribute("dx", dx);
		request.setAttribute("billNum", billNum);
		request.setAttribute("amount", amount);
		request.setAttribute("billAmount", billAmount);
		request.setAttribute("advanceAmount", advanceAmount);
		request.setAttribute("rentDepositAmount", rentDepositAmount);
		request.setAttribute("decorationDepositAmount", decorationDepositAmount);
		request.setAttribute("earnestAmount", earnestAmount);
		request.setAttribute("paymentWay", paymentWay);
		request.setAttribute("date", date);
		request.setAttribute("billList", billList);
		request.setAttribute("itemList", itemList);
		return mapping.findForward("printSj");
	}

	
	/**
	 * 打印收据单(用在收费统计中点击详情的页面添加的打印功能)
	 */
	public ActionForward printSj2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());//用户名
		String realname = GlobalMethod.ObjToStr(map.get("name"));//真实姓名
		CBillForm billForm = new CBillForm();
		if (ExtendUtil.checkNull(billForm.getChargeId())) {
			billForm.setChargeId(Integer.decode(request.getParameter("chargeId")));
		}
		List<CItems> itemList = icBillService.getItemList();
		CCharge charge = icBillService.getCharge(billForm);
		billForm.setBillId(charge.getBillId());
		List list = icBillService.getRefundBillList(billForm);
		CompactClient compactClient = new CompactClient();
		if(list!=null&&list.size()>0){
			Object[] objs = (Object[])list.get(0);
			compactClient = ((CBill)objs[0]).getCompactClient();
		}
		String roomCode = icNoticeService.getRoomCodeByClientId(compactClient.getId());
		request.setAttribute("userName", userName);
		request.setAttribute("realname", realname);
		request.setAttribute("list", list);
		request.setAttribute("itemList", itemList);
		request.setAttribute("charge", charge);
		request.setAttribute("compactClient", compactClient);
		request.setAttribute("roomCode", roomCode);
		return mapping.findForward("printSj2");
	}
	/**
	 * 客户退款tree CBillAction.getRefundTreeList
	 *//**
	public ActionForward getRefundTreeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest reequest,
			HttpServletResponse response) throws Exception {
		CBillForm billForm = (CBillForm) form;
		billForm.setRole("0");
		List<DTree> treeList = null;
		try {
			treeList = icBillService.getRefundTreeList(billForm);
		} catch (Exception e) {
			logger.error("客户查询失败：CBillAction.getRefundTreeList"
					+ e.getMessage());
			throw new BkmisWebException("客户查询失败：CBillAction.getRefundTreeList",
					e);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",
				"bill.do?method=getRefundList&role=0&clientId=");
		return mapping.findForward("tree");
	}
	public ActionForward getRefundTreeList1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CBillForm billForm = (CBillForm) form;
		billForm.setRole("1");
		List<DTree> treeList = null;
		try {
			treeList = icBillService.getRefundTreeList(billForm);
		} catch (Exception e) {
			logger.error("客户查询失败：CBillAction.getRefundTreeList"
					+ e.getMessage());
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp",
		"bill.do?method=getRefundList&role=1&clientId=");
		return mapping.findForward("tree");
	}

	/**
	 * 客户退款查询 CBillAction.getRefundList
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getRefundList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		formbean.setLpId(lpId);
		/** 到此为止*/
		Map userMap = (Map) request.getSession().getAttribute("userInfo");
		if ("1".equals(formbean.getRole())) {
			formbean.setUserId(userMap.get("userid").toString());
		}
		List<CCharge> billList = null;
		CompactClient client = new CompactClient();// 客户
		String pageHTML = "";
		List<MUser> userList = null;
		try {
			PageBean page = icBillService.getRefundList(formbean);
			billList = page.getList();
			pageHTML = page.toString("cx()");
			client = icBillService.getClient(formbean);
			userList = icBillService.getUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("billList", billList);
		request.setAttribute("pageHTML", pageHTML);
		request.setAttribute("clientId", formbean.getClientId());
		request.setAttribute("client", client);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("userList", userList);
		return mapping.findForward("refund");
	}
	
	/**
	 * 退款 CBillAction.refundBill
	 */
	public ActionForward refundBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		String[] checkbox = request.getParameterValues("checkbox");
		if (checkbox == null || checkbox.length == 0) {
			billForm.setCheckbox(null);
		}
		try {
			icBillService.refund(billForm);//退款.
			
			iCostTransactService.updateAwokeTask4PressMoney();
			iCostTransactService.updateAwokeTask4PressAdvance();
			iCostTransactService.updateAwokeTask4PressRentDeposit();
			iCostTransactService.updateAwokeTask4PressDecorationDeposit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("save", "成功！");
		return mapping.findForward("refundBill");
	}
	/** 根据billId查询
	 * 退款账单
	 */
	public ActionForward getRefundBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = new CBillForm();
		if (ExtendUtil.checkNull(billForm.getChargeId())) {
			billForm.setChargeId(Integer.decode(request
					.getParameter("chargeId")));
		}
		CCharge charge = icBillService.getCharge(billForm);
		billForm.setBillId(charge.getBillId());
		List list = icBillService.getRefundBillList(billForm);
		request.setAttribute("list", list);
		request.setAttribute("charge", charge);
		return mapping.findForward("refundBill");
	}
	/** 查询当日收费记录
	 * CBillAction.getNowCharge
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getNowCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		List list = icBillService.getNowCharge(billForm);
		request.setAttribute("list", list);
		return mapping.findForward("nowCharge");
	}
	 
	/** 还原收费操作
	 * CBillAction.returnCharge
	 */
	public ActionForward returnCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		icBillService.returnCharge(billForm);
		iCostTransactService.updateAwokeTask4PressMoney();
		iCostTransactService.updateAwokeTask4PressAdvance();
		iCostTransactService.updateAwokeTask4PressRentDeposit();
		iCostTransactService.updateAwokeTask4PressDecorationDeposit();
		request.setAttribute("save", "成功！");
		return getNowCharge(mapping, form, request, response);
	}
	/**
	 * 电费查询 CBillAction.getElectricity
	 */
	/**public ActionForward getElectricity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		PageBean page = icBillService.getElectricity(formbean);
		List<CBill> billList = page.getList();
		String pageHTML = page.toString("cx()");
		List billDateList = icBillService.getBillDates();
		String today = DateUtil.getNowDate("yyyy-MM-dd");
		request.setAttribute("billList", billList);
		request.setAttribute("size", billList.size());
		request.getSession().setAttribute("list", billList);
		request.setAttribute("pageHTML", pageHTML);
		request.setAttribute("billDateList", billDateList);
		request.setAttribute("today", today);
		return mapping.findForward("electricity");
	}

	*//**
	 * 电费收取 CBillAction.saveElectricity
	 *//**
	public ActionForward saveElectricity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CBillForm formbean = (CBillForm) form;
		formbean.setStandard(null);
		// icBillService.saveBill(formbean);
		iCostTransactService.updateAwokeTask4PressMoney();
		return getElectricity(mapping, form, request, response);
	}
	public ActionForward exportExcel1(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			//list中存放的就是当前页面上显示的所有数据
			//List list = (List)request.getSession().getAttribute("list");
			List list = icBillService.getExcelList1((CBillForm)form);
			//表头
			String[] cellHeader = Constant.EXCEL_ELEC_HEADER;
			String[] cellValue = Constant.EXCEL_ELEC_VALUE;
			//定义文件名
			String sheetName = "电费信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new CBill());
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "电费账款信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出应收账款excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	*//**
	 * 电费删除
	 * CBillAction.deleteElectricity
	 *//**
	public ActionForward deleteElectricity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CBillForm formbean = (CBillForm) form;
		icBillService.delete(formbean);
		iCostTransactService.updateAwokeTask4PressMoney();
		return getElectricity(mapping, form, request, response);
	}*/

	/** 押金
	 * CBillAction.getDeposit
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getDeposit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		PageBean pageBean = icBillService.getDeposit(formbean);
		List list = pageBean.getList();
		String pageHTML = pageBean.toString("cx()");
		request.setAttribute("list", list);
		request.setAttribute("pageHTML", pageHTML);
		return mapping.findForward("deposit");
	}

	/** 押金返还
	 * CBillAction.returnDeposit
	 */
	public ActionForward returnDeposit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		icBillService.returnDeposit(formbean);
		iCostTransactService.updateAwokeTask4PressRentDeposit();
		iCostTransactService.updateAwokeTask4PressDecorationDeposit();
		request.setAttribute("flag", "success");
		return getDeposit(mapping, form, request, response);
	}
	
	/**
	 * 账单统计
	 * 
	 */
/**	@SuppressWarnings("unchecked")
	public ActionForward getList4Count(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		CBillForm formbean = (CBillForm) form;
		List<CBill> list = new ArrayList<CBill>();// 账单
		String pageHTML = "";
		List billDateList = null;// 账期
		try {
			PageBean pageBean = icBillService.getList(formbean);
			List<CBill> billList = pageBean.getList();
			for (CBill bill : billList) {
				if ("0".equals(bill.getStatus())&&!ExtendUtil.checkNull(bill.getCloseDate())) {
					list.add(iCostTransactService.getDelaying(bill));
				}else {
					list.add(bill);
				}
			}
			pageHTML = pageBean.toString("cx()");
			billDateList = icBillService.getBillDates();
		} catch (Exception e) {
			logger.error("账单查询失败:CBillAction.getList()" + e.getMessage());
			throw new BkmisWebException("账单查询失败:CBillAction.getList()", e);
		}
		request.setAttribute("size", list.size());
		request.getSession().setAttribute("list", list);
		request.setAttribute("pageHTML",pageHTML);
		request.setAttribute("billDateList", billDateList);
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		return mapping.findForward("countBills");
	}
	
	/**
	 * 收费统计客户列表tree CBillAction.getRefundTreeList
	 *//**
	public ActionForward getClientTree4charge(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//判断是否只需要显示收款人自己收取的费用
//		String isShowByUser = GlobalMethod.NullToSpace(request.getParameter("isShowByUser"));
//		List<DTree> treeList = null;
//		try {
//			treeList = icBillService.getClientTree4charge(isShowByUser);
//		} catch (Exception e) {
//			logger.error("客户查询失败：CBillAction.getClientTree4charge"
//					+ e.getMessage());
//			throw new BkmisWebException("客户查询失败：CBillAction.getClientTree4charge",
//					e);
//		}
//		request.getSession().setAttribute("treeList", treeList);
//		request.getSession().setAttribute("mainFramJsp",
//				"bill.do?method=getChargeList&isShowByUser="+isShowByUser+"&flag=start&clientId=");
//		return mapping.findForward("tree");
		return getChargeList(mapping, form, request, response);
	}
	/**
	 * 客户收费统计查询
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getChargeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm uf = new UserForm();
//		uf.getUsername();
//		uf.getRange();
//		uf.getPagesize();
//		uf.getCurrentpage();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = dateFormat.format(new Date());
		//标识是否是点击左边的链接树进来的
		String flag = GlobalMethod.NullToSpace(request.getParameter("flag"));
		//判断是否只需要显示收款人自己收取的费用
		String isShowByUser = GlobalMethod.NullToSpace(request.getParameter("isShowByUser"));
		
		CBillForm formbean = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		Integer range = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userRoleRange").toString()),"0"));
		uf.setRange(range);
		formbean.setLpId(lpId);
		/** 到此为止*/
		if(flag.equals("start")){
			formbean.setBegin(todayStr);
			formbean.setEnd(todayStr);
		}
		//操作员列表
		List usersList = null;
		if(isShowByUser.equals("1")){
			Map map = (Map)request.getSession().getAttribute("userInfo");
			
			if(map==null){
				return mapping.findForward("countCharge");
			}
			formbean.setUserId(GlobalMethod.ObjToStr(map.get("userid")));
		}else{
			usersList = iUserManagerService.findUser(uf,false);
		}
		
		List<CCharge> billList = null;
		try {
			billList = icBillService.getChargeList(formbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("billList", billList);
		request.setAttribute("usersList", usersList);
		request.setAttribute("clientId", formbean.getClientId());
		request.setAttribute("payType", formbean.getPayType());
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		request.setAttribute("isShowByUser", isShowByUser);
		return mapping.findForward("countCharge");
	}
	//收费统计打印
	@SuppressWarnings("unchecked")
	public ActionForward printCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = dateFormat.format(new Date());
		//标识是否是点击左边的链接树进来的
		String flag = GlobalMethod.NullToSpace(request.getParameter("flag"));
		//判断是否只需要显示收款人自己收取的费用
		String isShowByUser = GlobalMethod.NullToSpace(request.getParameter("isShowByUser"));
		
		CBillForm formbean = (CBillForm) form;
		if(flag.equals("start")){
			formbean.setBegin(todayStr);
			formbean.setEnd(todayStr);
		}
		if(isShowByUser.equals("1")){
			Map map = (Map)request.getSession().getAttribute("userInfo");
			if(map==null){
				return mapping.findForward("printCharge");
			}
			formbean.setUserId(GlobalMethod.ObjToStr(map.get("userid")));
		}
		
		List billList = null;
		try {
			billList = icBillService.getChargeList(formbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("billList", billList);
		return mapping.findForward("printCharge");
	}
	
	/** 
	 * 收费纪录的详细账单
	 */
	public ActionForward getChargeBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = new CBillForm();
		if (ExtendUtil.checkNull(billForm.getChargeId())) {
			billForm.setChargeId(Integer.decode(request
					.getParameter("chargeId")));
		}
		List<CItems> itemList = icBillService.getItemList();
		CCharge charge = icBillService.getCharge(billForm);
		billForm.setBillId(charge.getBillId());
		List list = icBillService.getRefundBillList(billForm);
		request.setAttribute("list", list);
		request.setAttribute("itemList", itemList);
		request.setAttribute("charge", charge);
		return mapping.findForward("chargeBill");
	}
	
	/**
	 *导出收费报表 
	 *
	 */
	public ActionForward explortExcel4Charge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm formbean = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		formbean.setLpId(lpId);
		/** 到此为止*/
		List list = null;
		List<CCharge> billList = new ArrayList<CCharge>();
		//保存总计的CCharge对象
		CCharge countCharge = new CCharge();
		try {
			//list中存放的就是当前页面上显示的所有数据
			list = icBillService.getChargeList(formbean);
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					CCharge cc = (CCharge)((Object[])list.get(i))[0];
					MUser u = (MUser)((Object[])list.get(i))[2];
					cc.setRealName(u.getRealName());
					billList.add(cc);
					countCharge.setAmount(countCharge.getAmount()+cc.getAmount());
					countCharge.setBillAmount(countCharge.getBillAmount()+cc.getBillAmount());
					countCharge.setTemporalAmount(countCharge.getTemporalAmount()+cc.getTemporalAmount());
					countCharge.setAdvanceAmount(countCharge.getAdvanceAmount()+cc.getAdvanceAmount());
					countCharge.setDepositAmount(countCharge.getDepositAmount()+cc.getDepositAmount());
				}
				billList.add(countCharge);
			}
			//表头
			String[] cellHeader = Constant.EXCEL_CHARGE_HEADER;
			String[] cellValue = Constant.EXCEL_CHARGE_VALUE;
			//定义文件名
			String sheetName = "收款信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(billList,sheetName,cellHeader,cellValue,new CCharge());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "收款信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出收款excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 选择客户
	 * CChoiceAction.queryClient
	 */
	public ActionForward queryClient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		billForm.setLpId(lpId);
		/** 到此为止*/
		//PageBean pageBean = icBillService.queryCollect(billForm);
		PageBean pageBean = icBillService.queryCurrentInClient(billForm);
		String pageHTML = pageBean.toString("cx()");
		List<CompactClient> clients = pageBean.getList();
		List<Compact> compacts = icBillService.queryCompact(clients);
		request.setAttribute("list", pageBean.getList());
		request.setAttribute("compacts", compacts);
		request.setAttribute("pageHTML", pageHTML);
		request.setAttribute("clientName", billForm.getClientName());
		request.setAttribute("roomCode", billForm.getRoomCode());
		return mapping.findForward("client");
	}
	/**
	 * 给财务对账使用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Date:Nov 30, 2012 
	 * Time:1:06:25 AM
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryRentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CBillForm billForm = (CBillForm) form;
		PageBean pageBean = icBillService.queryRentInfo(billForm);
		String pageHTML = pageBean.toString("cx()");
		List rentInfo = pageBean.getList();
		request.setAttribute("list", rentInfo);
		request.setAttribute("pageHTML", pageHTML);
		request.setAttribute("form",billForm);
		request.setAttribute("size", rentInfo.size());
		return mapping.findForward("rentInfo");
	}
	/**
	 * 导出报表（财务统计房租信息）
	 * CBillAction.exportExcel
	 */
	@SuppressWarnings("unchecked")
	public ActionForward exportExcelRentInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			CBillForm billForm = (CBillForm)form;
			List list = icBillService.queryRentInfoExportExcel(billForm);
			//表头
			String[] cellHeader = Constant.EXCEL_RENTINFO_DETALL;
			String[] cellValue = Constant.EXCEL_RENTINFO_VALUE;
			//定义文件名
			String sheetName = "房租账单统计信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list,sheetName,cellHeader,cellValue);
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "房租账单统计信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出应收账款excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
