/**
 * Administrator
 */
package com.zc13.bkmis.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zc13.bkmis.dao.ICBillDAO;
import com.zc13.bkmis.form.CBillForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceDetail;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CAdvanceWuYFDetail;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CInvoice;
import com.zc13.bkmis.mapping.CInvoiceBill;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CTemporal;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ICBillService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.DateUtil;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

import common.Logger;

/**
 * @author ZHAOSG Date：Dec 24, 2010 Time：10:47:13 AM
 */
public class CBillServiceImpl extends BasicServiceImpl implements ICBillService {

	Logger logger = Logger.getLogger(this.getClass());
	private ICBillDAO icBillDao;

	/**
	 * 菜单树-费用 CBillServiceImpl.getCostTreeList
	 */
	/*
	 * public List<DTree> getCostTreeList() throws Exception { List<DTree>
	 * treeList = new ArrayList<DTree>(); DTree tr = new DTree("1", "收费标准",
	 * "0", ""); treeList.add(tr); List list = null; try { list =
	 * icChoiceDao.getTreeList(); } catch (Exception e) { // TODO: handle
	 * exception logger.error("费用查询失败:CBillServiceImpl.getCostTreeList()详细信息:" +
	 * e.getMessage()); throw new BkmisServiceException(
	 * "费用查询失败:CBillServiceImpl.getCostTreeList()"); } for (int i = 0; i <
	 * list.size() && list != null; i++) { Object[] obj = (Object[])
	 * list.get(i); String id = String.valueOf(obj[0]); String name =
	 * String.valueOf(obj[2]); String parentId = String.valueOf(obj[1]); String
	 * type = String.valueOf(obj[3]); String lpid = String.valueOf(obj[4]);
	 * String Url = ""; if ("费用类型".equals(type)) { id += "_2_" + lpid; } else if
	 * ("收费标准".equals(type)) { id += "_3_" + lpid; parentId += "_2_" + lpid; Url =
	 * "bill.do?method=getList&standardId=" + String.valueOf(obj[0]) + "&lpId=" +
	 * lpid + "&billType=" + String.valueOf(obj[5]); } DTree tree = new
	 * DTree(id, name, parentId, Url); treeList.add(tree); } return treeList;
	 *  }
	 */
	/**
	 * 账单查询--应收账款
	 */
	public PageBean getList(CBillForm formbean) throws Exception {
		PageBean list = null;
		if (ExtendUtil.checkNull(formbean.getPagesize())) {
			formbean.setPagesize(10);
		}
//		List<Integer> clients = icBillDao.queryClientId(formbean.getLpId());//参数 没用处, 此处注释掉.2018年2月26日16:24:23 gd
		double advanceRentBalance = 0.0;// 预收房租 余额
		try {
//			list = icBillDao.getList(formbean, clients);
			list = icBillDao.getList(formbean, null);//第二个参数 完全没用.
			/*获得查询出来的客户的预收房租余额的合计start*/
			Set<Integer> clientIds = new HashSet<Integer>();
			if(list!=null&&list.getList()!=null&&list.getList().size()>0){
				List tempList = list.getList();
				for(int i = 0;i<tempList.size();i++){
					CBill bill = (CBill)tempList.get(i);
					clientIds.add(bill.getCompactClient().getId());
				}
			}
			if(clientIds!=null&&clientIds.size()>0){
				Iterator<Integer> it = clientIds.iterator();
				while(it.hasNext()){
					CAdvance advance = icBillDao.getAdvance(it.next());
					if(advance!=null){
						advanceRentBalance += advance.getAmount();
					}
				}
			}
			formbean.getAdvance().setAmount(advanceRentBalance);
			/*获得查询出来的客户的预售房租余额的合计end*/
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("账单查询失败：CBillServiceImpl.getList()详细信息：" + e.getMessage());
			throw new BkmisServiceException("账单查询失败：CBillServiceImpl.getList");
		}
		return list;
	}

	/**
	 * 应收账款-导出报表 CBillServiceImpl.getExcelList
	 */
	public List<CBill> getExcelList(CBillForm formbean) throws Exception {
		List<Integer> clients = icBillDao.queryClientId(formbean.getLpId());
		return icBillDao.getExcelList(formbean, clients);
	}

	/**
	 * 收费标准，帐套 CBillServiceImpl.getStandard
	 */
	/*
	 * public List getStandard(CBillForm formbean) throws Exception { List list =
	 * null; try { list = icBillDao.getStandard(formbean.getLpId(), formbean
	 * .getStandardId()); } catch (Exception e) { // TODO: handle exception }
	 * return list; }
	 */

	/**
	 * 添加账单 CBillServiceImpl.saveBill
	 */
	/*
	 * public void saveBill(CBillForm formbean) throws Exception { String ids =
	 * formbean.getIds(); CBill cBill = formbean.getBill(); if
	 * (!ExtendUtil.checkNull(ids)) { String[] id = ids.split(","); for (int i =
	 * 0; i < id.length; i++) { CBill bill = new CBill(); CompactClient client =
	 * new CompactClient(); bill.setCCoststandard(formbean.getStandard());//
	 * 收费标准 client.setId(Integer.decode(id[i]));
	 * bill.setCompactClient(client);// 客户 bill.setStatus(cBill.getStatus());//
	 * 单据状态 bill.setBillDate(cBill.getBillDate());// 账单月份
	 * bill.setBillsExpenses(cBill.getBillsExpenses());// 单据金额
	 * bill.setCreateDate(cBill.getCreateDate());// 账单日期
	 * bill.setStartDate(cBill.getStartDate());// 开始日期
	 * bill.setEndDate(cBill.getEndDate());// 结束日期
	 * bill.setActuallyPaid(cBill.getActuallyPaid());// 实付金额
	 * bill.setStandardName(cBill.getStandardName());//费用名称 try {
	 * icBillDao.saveObject(bill); } catch (Exception e) { // TODO: handle
	 * exception logger.error("添加账单失败：CBillServiceImpl.saveBill。" +
	 * e.getMessage()); throw new BkmisServiceException(
	 * "添加账单失败：CBillServiceImpl.saveBill。", e); } } } }
	 */

	/**
	 * 删除账单 CBillServiceImpl.delete
	 */
	public void delete(CBillForm formbean) throws Exception {
		Integer[] id = formbean.getCheckbox();
		if (id != null) {
			for (int i = 0; i < id.length; i++) {
				CBill bill = new CBill();
				bill.setId(id[i]);
				try {
					icBillDao.deleteObject(bill);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("账单删除失败：CBillServiceImpl.delete" + e.getMessage());
					throw new BkmisServiceException("账单删除失败：CBillServiceImpl.delete", e);
				}
			}
		}
	}

	/**
	 * 账期 CBillServiceImpl.getBillDates
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillDates() throws Exception {
		List<String> list = new ArrayList<String>();
		try {
			list = icBillDao.getBillDates();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// String today = DateUtil.getNowDate("yyyy-MM");
		// if (!list.contains(today)) {
		// list.add(today);
		// }
		return list;
	}

	/**
	 * 客户 CBillServiceImpl.getCilentTreeList
	 */
	public List<DTree> getCilentTreeList() throws Exception {
		List<DTree> treeList = new ArrayList<DTree>();
		DTree tr = new DTree("1", "客户", "0", "");
		treeList.add(tr);
		List list = null;
		try {
			list = icBillDao.getClientList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("客户查询失败：CBillServiceImpl.getCilentTreeList" + e.getMessage());
			throw new BkmisServiceException("客户查询失败：CBillServiceImpl.getCilentTreeList", e);
		}
		for (int i = 0; i < list.size() && list != null; i++) {
			Object[] objects = (Object[]) list.get(i);
			String id = String.valueOf(objects[0]);
			String name = String.valueOf(objects[1]);
			String parent = String.valueOf(objects[2]);
			String lb = String.valueOf(objects[3]);
			String url = "";
			if ("client".equals(lb)) {
				url = "bill.do?method=getBillList&clientId=" + id;
				id = "client" + id;
			}
			DTree tree = new DTree(id, name, parent, url);
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 收款销账 CBillServiceImpl.getCBilList
	 */
	public List<CBill> getCBilList(CBillForm formbean) throws Exception {
		List<CBill> billList = null;
		try {
			billList = icBillDao.getBillList(formbean);
		} catch (Exception e) {
			logger.error("收款销账查询失败：CBillServiceImpl.getCBilList。" + e.getMessage());
			throw new BkmisServiceException("收款销账查询失败：CBillServiceImpl.getCBilList。", e);
		}
		return billList;
	}

	/**
	 * 收费项目 CBillServiceImpl.getItemList
	 */
	@SuppressWarnings("unchecked")
	public List<CItems> getItemList() throws Exception {
		List<CItems> itemList = null;
		try {
			itemList = icBillDao.getObjects(CItems.class);
		} catch (Exception e) {
			logger.error("收费项目查询失败:" + e.getMessage());
			e.printStackTrace();
		}
		return itemList;
	}

	/**
	 * 某一客户 CBillServiceImpl.getClient
	 */
	public CompactClient getClient(CBillForm formbean) throws Exception {
		CompactClient client = new CompactClient();
		if (!ExtendUtil.checkNull(formbean.getClientId())) {
			try {
				client = (CompactClient) icBillDao.getObject(CompactClient.class, formbean.getClientId());
			} catch (Exception e) {
				logger.error("客户查询失败：CBillServiceImpl.getClient。", e);
				e.printStackTrace();
			}
		}
		return client;
	}

	/**
	 * 收取费用 CBillServiceImpl.updateBill
	 */
	@SuppressWarnings("unchecked")
	public void updateBill(CBillForm formbean) throws Exception {
		Integer[] id = formbean.getId();//所有的账单
		Integer[] ids = formbean.getCheckbox();//选中的账单（需要结账的账单）
		if (ids != null) {
			List checkbox = Arrays.asList(ids);
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					if (checkbox.contains(id[i])) {
						CBill bill = formbean.getBill();
						bill.setId(id[i]);
						bill.setStatus("1");
						bill.setActuallyPaid(formbean.getActuallyPaid()[i]);
						bill.setDelayingExpenses(formbean.getDelayingExpenses()[i]);
						try {
							icBillDao.updateBill(bill);
						} catch (Exception e) {
							logger.error("收取费用失败：CBillServiceImpl.updateBill。", e);
							throw new BkmisServiceException("收取费用失败：CBillServiceImpl.updateBill。", e);
						}
						//记录日志
						bill = (CBill)icBillDao.getObject(CBill.class, bill.getId());
						CItems item = (CItems) icBillDao.getObject(CItems.class, bill.getItemId());
						if(null!=item && null!=item.getValue()&&"phoneCost".equals(item.getValue())) {
							String sql = "update ECallInfo set isPaid='"+Contants.ISPAID+"' where rootUser="+bill.getId();
							icBillDao.update(sql);
						}
						
						StringBuffer logStr = new StringBuffer("收取客户：").append(bill.getCompactClient().getName());
						logStr.append(bill.getBillDate()).append("月");
						if(bill.getERooms()!=null){
							logStr.append(bill.getERooms().getRoomCode()).append("房间");
						}
						CItems items = (CItems)icBillDao.getObject(CItems.class, bill.getItemId());
						logStr.append(items.getItemName());
						logStr.append(bill.getActuallyPaid()).append("元，单据号为：").append(bill.getBillNum());
						icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
					}
				}
			}
		}
	}

	/**
	 * 收费操作记录 CBillServiceImpl.saveCharge
	 */
	public Integer saveCharge(
			CBillForm billForm, 
			String id1, 
			String id2, 
			String id22, 
			String id3_1, 
			String id3_2, 
			String id4) throws Exception {
		CCharge charge = billForm.getCharge();
		CBill bill = billForm.getBill();// 账单
		// CTemporal temporal = billForm.getTemporal();//暂存款
		CAdvance advance = billForm.getAdvance();// 预收款
		CAdvanceWuYF advanceWuYF = billForm.getAdvanceWuYF(); /**添加 预收 物业费* gd 2018年3月3日15:49:35 * */
		CDeposit rentDeposit = billForm.getRentDeposit();// 房租押金
		CDeposit decorationDeposit = billForm.getDecorationDeposit();// 装修押金 --> 不需要记录?
		CEarnest earnest = billForm.getEarnest();// 定金
		
		charge.setClientId(billForm.getClientId());
		charge.setBillType(bill.getBillType());
		charge.setBillNum(bill.getBillNum());
		charge.setPayType(bill.getPaymentWay());// 付款方式
		
		charge.setTemporalAmount(billForm.getAmount() - charge.getBillAmount());
		charge.setDepositAmount(rentDeposit.getAmount() + decorationDeposit.getAmount());//房租押金 + 装修押金.
		charge.setEarnestAmount(earnest.getAmount());
		charge.setAdvanceAmount(advance.getAmount());//预收款金额
		charge.setAdvanceWuYFAmount(advanceWuYF.getAmount());/**添加 预收 物业费金额 * gd 2018年3月3日15:49:35 * */
		
		charge.setDate(bill.getCollectionDate());
		charge.setChequeNo(bill.getChequeNo());
		charge.setLpId(billForm.getLpId());
		
		if (billForm.getEarnestNum() > 0) {
			if ("1".equals(billForm.getEarnestFlag())) {
				charge.setPayType("4");// 定金转押金
			} else if ("0".equals(billForm.getEarnestFlag())) {
				charge.setPayType("5");// 定金转预收房租
			}
		}
		
		Integer[] ids = billForm.getCheckbox();
		StringBuffer id = new StringBuffer();
		//列表收费项目
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				id.append(String.valueOf(ids[i])).append(",");
			}
		}
		//表单收费项目
		if (!ExtendUtil.checkNull(id1)) {
			id.append(id1).append(",");
		}
		if (!ExtendUtil.checkNull(id2)) {
			id.append(id2).append(",");
		}
		if (!ExtendUtil.checkNull(id22)) {/**添加 预收 物业费金额 * gd 2018年3月3日15:49:35 * */
			id.append(id22).append(",");
		}
		if (!ExtendUtil.checkNull(id3_1)) {
			id.append(id3_1).append(",");
		}
		if (!ExtendUtil.checkNull(id3_2)) {
			id.append(id3_2).append(",");
		}
		if (!ExtendUtil.checkNull(id4)) {
			id.append(id4).append(",");
		}
		
		charge.setBillId(id.toString().substring(0, id.toString().length() - 1));//收款表 记录了各种账单id(billID)
		return (Integer)icBillDao.saveObject(charge);
	}

	/**
	 * 暂存款余额查询 CBillServiceImpl.geTemporal
	 */
	public CTemporal geTemporal(CBillForm formbean) throws Exception {
		return icBillDao.geTemporal(formbean.getClientId());
	}

	/**
	 * 余额转入暂存款 CBillServiceImpl.updateTemporal
	 */
	public String updateTemporal(CBillForm formbean) throws Exception {
		String id = "";
		double billAmount = formbean.getCharge().getBillAmount();// 本次应付
		if (formbean.getAmount() - billAmount != 0) {
			CTemporal temporal = new CTemporal();
			temporal.setClientId(formbean.getClientId());
			temporal.setAmount(formbean.getAmount() - billAmount);
			int i = icBillDao.updateTemporal(temporal);
			if (i == 0) {
				icBillDao.saveObject(temporal);
			}
			CBill bill = formbean.getBill();
			bill.setId(null);
			CompactClient compactClient = new CompactClient();
			compactClient.setId(formbean.getClientId());
			bill.setCompactClient(compactClient);
			bill.setStatus("1");
			CItems items = icBillDao.getItems("tempporal");
			bill.setItemId(items.getId());
			bill.setActuallyPaid(temporal.getAmount());
			icBillDao.saveObject(bill);
			id = icBillDao.maxBillId().toString();
			//记录日志
			CompactClient cc = (CompactClient)icBillDao.getObject(CompactClient.class, bill.getCompactClient().getId());
			StringBuffer logStr = new StringBuffer("客户").append(cc.getName());
			logStr.append("余额转入暂存款的金额为：").append(temporal.getAmount());
			logStr.append("元，单据号为：").append(bill.getBillNum());
			icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
		}
		return id;
	}

	/**
	 * 缴纳预收房租 CBillServiceImpl.updateAdvance
	 */
	public String updateAdvance(CBillForm formbean) throws Exception {
		String id = "";
		CAdvance advance = formbean.getAdvance();
		advance.setClientId(formbean.getClientId());
		CBill bill = formbean.getBill();
		if ("0".equals(formbean.getEarnestFlag())) {// 定金转预收房租
			double amount = advance.getAmount() + formbean.getEarnestNum();
			advance.setAmount(amount);
			bill.setPaymentWay("5");
		}
		if (advance.getAmount() != 0) {
			int i = icBillDao.updateAdvance(advance);//缴纳预收款,返回数据库 受影响行数
			if (i == 0) {//0 为新客户,需要保存 ?
				icBillDao.saveObject(advance);
			}
			bill.setId(null);//?这就算 账单结束?
			
			CompactClient compactClient = new CompactClient();
			compactClient.setId(formbean.getClientId());
			bill.setCompactClient(compactClient);
			bill.setStatus("1");
			
			CItems items = icBillDao.getItems("destineRent");
			bill.setItemId(items.getId());
			bill.setActuallyPaid(advance.getAmount());
			icBillDao.saveObject(bill);
			id = icBillDao.maxBillId().toString();
			//记录日志
			CompactClient cc = (CompactClient)icBillDao.getObject(CompactClient.class, bill.getCompactClient().getId());
			StringBuffer logStr = new StringBuffer("收取客户").append(cc.getName());
			logStr.append("预售房租：").append(advance.getAmount());
			logStr.append("元，单据号为：").append(bill.getBillNum());
			icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
		}
		return id;
	}
	/**
	 * 缴纳预收物业费 CBillServiceImpl.updateAdvance
	 */
	public String updateAdvanceWuYF(CBillForm formbean) throws Exception {
		String id = "";
		CAdvanceWuYF advanceWuYF = formbean.getAdvanceWuYF();
		advanceWuYF.setClientId(formbean.getClientId());
		CBill bill = formbean.getBill();
		if (advanceWuYF.getAmount() != 0) {
			int i = icBillDao.updateAdvanceWuYF(advanceWuYF);//缴纳预收物业费,返回数据库 受影响行数
			if (i == 0) {//0 为新客户,需要保存 ?
				icBillDao.saveObject(advanceWuYF);
			}
			bill.setId(null);//?
			
			CompactClient compactClient = new CompactClient();
			compactClient.setId(formbean.getClientId());
			bill.setCompactClient(compactClient);
			bill.setStatus("1");
			
			CItems items = icBillDao.getItemsByName(Contants.WuYFJF);//获取 收费项目 
			
			bill.setItemId(items.getId());
			bill.setActuallyPaid(advanceWuYF.getAmount());
			icBillDao.saveObject(bill);
			id = icBillDao.maxBillId().toString();
			
			//记录日志
			CompactClient cc = (CompactClient)icBillDao.getObject(CompactClient.class, bill.getCompactClient().getId());
			StringBuffer logStr = new StringBuffer("收取客户").append(cc.getName());
			logStr.append("预收物业费：").append(advanceWuYF.getAmount());
			logStr.append("元，单据号为：").append(bill.getBillNum());
			icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
		}
		return id;
	}

	/**
	 * 预收款详细信息 CBillServiceImpl.saveAdvanceDetail
	 */
	public void saveAdvanceDetail(CBillForm formbean) throws Exception {
		CAdvanceDetail detail = new CAdvanceDetail();
		
		CBill bill = formbean.getBill();
		CAdvance advance = formbean.getAdvance();
		if ("0".equals(formbean.getEarnestFlag())) {//定金转入: 房租0 / 房租押金1
			double amount = advance.getAmount() + formbean.getEarnestNum();
			advance.setAmount(amount);
		}
		if (advance.getAmount() != 0) {
			detail.setClientId(formbean.getClientId());
			detail.setBillType(bill.getBillType());
			detail.setBillNum(bill.getBillNum());
			detail.setAmount(advance.getAmount());
			detail.setPayDate(bill.getCollectionDate());
			icBillDao.saveObject(detail);
		}
	}
	/**
	 * 预收款详细信息 CBillServiceImpl.saveAdvanceDetail
	 */
	public void saveAdvanceWuYFDetail(CBillForm formbean) throws Exception {
		CAdvanceWuYFDetail detail = new CAdvanceWuYFDetail();
		
		CBill bill = formbean.getBill();
		CAdvanceWuYF advanceWuYF = formbean.getAdvanceWuYF();
		if (advanceWuYF.getAmount() != 0) {
			detail.setClientId(formbean.getClientId());
			detail.setBillType(bill.getBillType());
			detail.setBillNum(bill.getBillNum());
			detail.setAmount(advanceWuYF.getAmount());
			detail.setPayDate(bill.getCollectionDate());
			icBillDao.saveObject(detail);
		}
	}

	/**
	 * 预收款查询 CBillServiceImpl.getAdvance
	 */
	public CAdvance getAdvance(CBillForm formbean) throws Exception {
		return icBillDao.getAdvance(formbean.getClientId());
	}
	/**
	 * 预收物业费 查询 CBillServiceImpl.getAdvance
	 */
	public CAdvanceWuYF getAdvanceWuYF(CBillForm formbean) throws Exception {
		return icBillDao.getAdvanceWuYF(formbean.getClientId());
	}

	public CDeposit getCDeposit(CBillForm formbean, String depositType) throws Exception {
		return icBillDao.getDeposit(formbean.getClientId(), depositType);
	}

	public CEarnest getEarnest(CBillForm formbean) throws Exception {
		return icBillDao.getEarnest(formbean.getClientId());
	}

	/**
	 * 缴纳押金 CBillServiceImpl.saveDeposit
	 */
	public String saveDeposit(CBillForm formbean, String depositType) throws Exception {
		// 房租押金
		String depositId = "";
		CDeposit deposit = null;
		CBill bill = formbean.getBill();
		CompactClient compactClient = new CompactClient();
		compactClient.setId(formbean.getClientId());
		bill.setCompactClient(compactClient);
		String depositTypeStr = null;
		if (Contants.RENT_DEPOSIT.equals(depositType)) {// 如果是房租押金
			depositTypeStr = "房租押金";
			deposit = formbean.getRentDeposit();
			if ("1".equals(formbean.getEarnestFlag())) {// 定金转房租押金
				double amount = deposit.getAmount() + formbean.getEarnestNum();
				deposit.setAmount(amount);
				bill.setPaymentWay("4");
			}
		} else {
			depositTypeStr = "装修押金";
			deposit = formbean.getDecorationDeposit();
		}
		if (deposit.getAmount() != 0) {
			deposit.setClientId(formbean.getClientId());
			deposit.setDate(bill.getCollectionDate());
			deposit.setBillType(bill.getBillType());
			deposit.setBillNum(bill.getBillNum());
			deposit.setDepositType(depositType);
			// icBillDao.saveObject(deposit);
			icBillDao.updateDeposit(deposit);
			bill.setId(null);
			bill.setStatus("1");
			CItems items = icBillDao.getItems(depositType);
			bill.setItemId(items.getId());
			bill.setActuallyPaid(deposit.getAmount());
			icBillDao.saveObject(bill);
			Integer integer = icBillDao.maxBillId();
			depositId = integer.toString();
			//得到当前需要缴纳的房租押金和装修押金信息
			double[] cost = icBillDao.getCost(formbean.getClientId());
			if(cost[0]<=0.01){//如果需要缴纳的房租押金小于0，则置合同中的是否已缴房租押金为已缴
				icBillDao.update("update Compact set isRentDeposit='1' where clientId="+formbean.getClientId()+" and status='"+Contants.THROUGHAPPROVAL+"'");
			}
			if(cost[1]<=0.01){//如果需要缴纳的装修押金小于0，则置合同中的是否已缴装修押金为已缴
				icBillDao.update("update Compact set isDecorationDeposit='1' where clientId="+formbean.getClientId()+" and status='"+Contants.THROUGHAPPROVAL+"'");
			}
			//记录日志
			CompactClient cc = (CompactClient)icBillDao.getObject(CompactClient.class, bill.getCompactClient().getId());
			StringBuffer logStr = new StringBuffer("收取客户").append(cc.getName());
			logStr.append(depositTypeStr+"：").append(deposit.getAmount());
			logStr.append("元，单据号为：").append(bill.getBillNum());
			icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
		}
		return depositId;
	}

	public String saveEarnest(CBillForm formbean) throws Exception {
		CItems items1 = icBillDao.getItems("earnest");
		String ids = "";
		CBill bill = formbean.getBill();
		CompactClient compactClient = new CompactClient();
		compactClient.setId(formbean.getClientId());
		bill.setCompactClient(compactClient);
		// 定金
		CEarnest earnest = formbean.getEarnest();
		// earnestFlag=0:定金转预售房租；earnestFlag=1：定金转房租押金
		if ("1".equals(formbean.getEarnestFlag()) || "0".equals(formbean.getEarnestFlag())) {
			double amount2 = earnest.getAmount() - formbean.getEarnestNum();
			earnest.setAmount(amount2);
		}
		if (earnest.getAmount() != 0) {
			earnest.setClientId(formbean.getClientId());
			earnest.setDate(bill.getCollectionDate());
			earnest.setBillType(bill.getBillType());
			earnest.setBillNum(bill.getBillNum());
			CEarnest earnest2 = icBillDao.getEarnest(formbean.getClientId());
			if (earnest2 == null) {
				icBillDao.saveObject(earnest);
			} else {
				icBillDao.updateEarnest(earnest);
			}
			if (earnest.getAmount() > 0) {// 更新合同或意向书的是否已缴定金为已缴
				icBillDao.updateCompact(formbean.getClientId());
				icBillDao.updateCompactIntention(formbean.getClientId());
			}
			// icBillDao.updateDeposit(deposit);
			bill.setId(null);
			bill.setStatus("1");
			bill.setItemId(items1.getId());
			bill.setActuallyPaid(earnest.getAmount());
			icBillDao.saveObject(bill);
			Integer integer = icBillDao.maxBillId();
			ids = integer.toString();
			//记录日志
			CompactClient cc = (CompactClient)icBillDao.getObject(CompactClient.class, bill.getCompactClient().getId());
			StringBuffer logStr = new StringBuffer("收取客户").append(cc.getName());
			logStr.append("定金：").append(earnest.getAmount());
			logStr.append("元，单据号为：").append(bill.getBillNum());
			icBillDao.log(formbean.getUserName(), "收费", logStr.toString(), Contants.L_COST, "账单");
		}
		return ids;
	}

	/**
	 * 客户退款tree CBillServiceImpl.getRefundTreeList
	 */
	public List<DTree> getRefundTreeList(CBillForm billForm) throws Exception {
		List<DTree> treeList = new ArrayList<DTree>();
		DTree tr = new DTree("1", "客户", "0", "");
		treeList.add(tr);
		List list = null;
		try {
			list = icBillDao.getClientList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("客户查询失败：CBillServiceImpl.getRefundTreeList" + e.getMessage());
			throw new BkmisServiceException("客户查询失败：CBillServiceImpl.getRefundTreeList", e);
		}
		for (int i = 0; i < list.size() && list != null; i++) {
			Object[] objects = (Object[]) list.get(i);
			String id = String.valueOf(objects[0]);
			String name = String.valueOf(objects[1]);
			String parent = String.valueOf(objects[2]);
			String lb = String.valueOf(objects[3]);
			String url = "";
			if ("client".equals(lb)) {
				url = "bill.do?method=getRefundList&role=" + billForm.getRole() + "&clientId=" + id;
				id = "client" + id;
			}
			DTree tree = new DTree(id, name, parent, url);
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 退款查询 CBillServiceImpl.getRefundList
	 */
	public PageBean getRefundList(CBillForm formbean) throws Exception {
//		String billDate = formbean.getBill().getBillDate();
		// if (ExtendUtil.checkNull(billDate)) {
		// String today = DateUtil.getNowDate("yyyy-MM");
		// formbean.getBill().setBillDate(today);
		// }
		if (ExtendUtil.checkNull(formbean.getPagesize())) {
			formbean.setPagesize(10);
		}
		if (ExtendUtil.isNull(formbean.getBegin())) {
			formbean.setBegin(DateUtil.getNowDate("yyyy-MM-dd"));
		}
		PageBean page = icBillDao.getRefundList(formbean);
		return page;
	}

	/**
	 * 查询当日收费 CBillServiceImpl.getNowCharge
	 */
	public List getNowCharge(CBillForm billForm) throws Exception {
		return icBillDao.getChargeList(billForm);
	}

	/**
	 * 根据收费表中账单id查询账单 CBillServiceImpl.getRefundBillList
	 */
	public List getRefundBillList(CBillForm billForm) throws Exception {
		return icBillDao.getRefundBillList(billForm);
	}

	/**
	 * 查询收费表 CBillServiceImpl.getCharge
	 */
	public CCharge getCharge(CBillForm billForm) throws Exception {
		Integer id = billForm.getChargeId();
		CCharge charge = null;
		if (!ExtendUtil.checkNull(id)) {
			charge = (CCharge) icBillDao.getObject(CCharge.class, id);
		}
		return charge;
	}

	/**
	 * 客户退款 CBillServiceImpl.refund
	 */
	public void refund(CBillForm billForm) throws Exception {
		CCharge charge = billForm.getCharge();// 收费
		Integer clientId = charge.getClientId();// 客户id
		double amount = charge.getAmount();
		double billAmount = 0;
		String billId = "";
		// 账单
		Integer[] checkbox = billForm.getCheckbox();// 选中的账单id
		Integer[] id = billForm.getId();// 全部账单id
		double[] actuallyPaid = billForm.getActuallyPaid();
		List<CBill> bills = icBillDao.queryBill(checkbox);
		List<CItems> itemList = getItemList();
		int rent = 0;
		int temp = 0;
		int rent_deposi = 0;// 房租押金
		int decoration_deposi = 0;// 装修押金
		int earnest = 0;
		int wuyf = 0 ;
		int wuybnf = 0 ;
		int wuynf = 0 ;
		for (CItems items : itemList) {
			if ("destineRent".equals(items.getValue())) {
				rent = items.getId().intValue();
			} else if ("tempporal".equals(items.getValue())) {
				temp = items.getId().intValue();
			} else if ("rent_deposit".equals(items.getValue())) {
				rent_deposi = items.getId().intValue();
			} else if ("decoration_deposit".equals(items.getValue())) {
				decoration_deposi = items.getId().intValue();
			} else if ("earnest".equals(items.getValue())) {
				earnest = items.getId().intValue();
			} else if(Contants.WuYFJF.equals(items.getItemName())){
				wuyf = items.getId().intValue();
			} else if(Contants.WuYFBNF.equals(items.getItemName())){
				wuybnf = items.getId().intValue();
			} else if(Contants.WuYFNF.equals(items.getItemName())){
				wuynf = items.getId().intValue();
			}
		}
		for (CBill bill : bills) {
			if (bill.getItemId().intValue() == rent) {
				// 预收款
				CAdvance advance = new CAdvance();
				double advanceAmount = bill.getActuallyPaid();
				advance.setAmount(-advanceAmount);
				advance.setClientId(clientId);
				icBillDao.updateAdvance(advance);
				if (advanceAmount == charge.getAdvanceAmount()) {
					charge.setAdvanceAmount(0);
					amount -= advanceAmount;
				}
				icBillDao.deleteObject(bill);
			} else if (bill.getItemId().intValue() == wuyf 
					|| bill.getItemId().intValue() == wuybnf 
					|| bill.getItemId().intValue() == wuynf ) {
				// 预收物业费
				CAdvanceWuYF advance = new CAdvanceWuYF();
				double advanceAmount = bill.getActuallyPaid();
				advance.setAmount(-advanceAmount);
				advance.setClientId(clientId);
				icBillDao.updateAdvanceWuYF(advance);
				
				if (advanceAmount == charge.getAdvanceWuYFAmount()) {
					charge.setAdvanceAmount(0);
					amount -= advanceAmount;
				}
				icBillDao.deleteObject(bill);
			} else if (bill.getItemId().intValue() == temp) {
				// 暂存款
				CTemporal temporal = new CTemporal();
				double temporalAmount = bill.getActuallyPaid();
				temporal.setAmount(-temporalAmount);
				temporal.setClientId(clientId);
				icBillDao.updateTemporal(temporal);
				if (temporalAmount == charge.getTemporalAmount()) {
					charge.setTemporalAmount(0);
					amount -= temporalAmount;
				}
				icBillDao.deleteObject(bill);
			} else if (bill.getItemId().intValue() == rent_deposi) {
				// 房租押金 ?这个每次也都要缴纳?
				CDeposit deposit = new CDeposit();
				double depositAmount = bill.getActuallyPaid();
				deposit.setAmount(-depositAmount);
				deposit.setClientId(clientId);
				deposit.setDepositType("rent_deposit");
				icBillDao.updateDeposit(deposit);
				if (depositAmount == charge.getDepositAmount()) {
					charge.setDepositAmount(0);
					amount -= depositAmount;
				}
				icBillDao.deleteObject(bill);
			} else if (bill.getItemId().intValue() == decoration_deposi) {
				// 装修押金
				CDeposit deposit = new CDeposit();
				double depositAmount = bill.getActuallyPaid();
				deposit.setAmount(-depositAmount);
				deposit.setClientId(clientId);
				deposit.setDepositType("decoration_deposit");
				icBillDao.updateDeposit(deposit);
				if (depositAmount == charge.getDepositAmount()) {
					charge.setDepositAmount(0);
					amount -= depositAmount;
				}
				icBillDao.deleteObject(bill);
			} else if (bill.getItemId().intValue() == earnest) {
				//定金
				CEarnest earnest2 = new CEarnest();
				double depositAmount = bill.getActuallyPaid();
				earnest2.setAmount(-depositAmount);
				earnest2.setClientId(clientId);
				icBillDao.updateEarnest(earnest2);
				amount -= depositAmount;
				icBillDao.deleteObject(bill);
			} else {
				bill.setStatus("0");
				bill.setActuallyPaid(0);
				icBillDao.updateBill(bill);
			}
		}
		if (checkbox != null && checkbox.length > 0) {
			List<Integer> idList = Arrays.asList(checkbox);
			for (int i = 0; i < id.length; i++) {
				CBill bill = new CBill();// 账单
				bill.setId(id[i]);
				if (idList.contains(id[i])) {
					// bill.setStatus("0");
					// bill.setActuallyPaid(0);
					// amount -= actuallyPaid[i];
					// icBillDao.updateBill(bill);
				} else {
					bill.setBillNum(charge.getBillNum());
					billId += "," + String.valueOf(id[i]);
					billAmount += actuallyPaid[i];
					icBillDao.updateBillNum(bill);
				}
			}
			if (!"".equals(billId) && billId.charAt(0) == ',') {
				billId = billId.substring(1);
			}
		}

		if ("1".equals(billForm.getSfqx())) {
			icBillDao.deleteObject(charge);
		} else if ("0".equals(billForm.getSfqx())) {
			charge.setAmount(amount);
			if (checkbox != null && checkbox.length > 0) {
				charge.setBillAmount(billAmount);
				charge.setBillId(billId);
			}
			icBillDao.updateObject(charge);
		}
	}

	/**
	 * 取消收费 CBillServiceImpl.returnCharge
	 */
	public void returnCharge(CBillForm billForm) throws Exception {
		Integer[] checkbox = billForm.getCheckbox();
		// Integer[] id = billForm.getId();
		// List<CCharge> chargeList = billForm.getChargeList();
		List<CCharge> chargeList = icBillDao.queryCharge(checkbox);
		for (CCharge charge : chargeList) {
			// 账单
			Integer clientId = charge.getClientId();
			String billId = charge.getBillId();
			if (!ExtendUtil.checkNull(billId)) {
				String[] ids = billId.split(",");
				Integer[] ids1 = new Integer[ids.length];
				for (int i = 0; i < ids.length; i++) {
					ids1[i] = Integer.parseInt(ids[i]);
				}
				List<CBill> bills = icBillDao.queryBill(ids1);
				List<CItems> itemList = getItemList();
				int rent = 0;
				int temp = 0;
				int rent_deposi = 0;// 房租押金id
				int decoration_deposi = 0;// 装修押金id
				int earnest = 0;
				for (CItems items : itemList) {
					if ("destineRent".equals(items.getValue())) {
						rent = items.getId().intValue();
					} else if ("tempporal".equals(items.getValue())) {
						temp = items.getId().intValue();
					} else if ("rent_deposit".equals(items.getValue())) {
						rent_deposi = items.getId().intValue();
					} else if ("decoration_deposit".equals(items.getValue())) {
						decoration_deposi = items.getId().intValue();
					} else if ("earnest".equals(items.getValue())) {
						earnest = items.getId().intValue();
					}
				}
				for (CBill bill : bills) {
					if (bill.getItemId().intValue() == rent) {
						// 预收款
						CAdvance advance = new CAdvance();
						double advanceAmount = bill.getActuallyPaid();
						advance.setAmount(-advanceAmount);
						advance.setClientId(clientId);
						icBillDao.updateAdvance(advance);
						icBillDao.deleteObject(bill);
					} else if (bill.getItemId().intValue() == temp) {
						// 暂存款
						CTemporal temporal = new CTemporal();
						double temporalAmount = bill.getActuallyPaid();
						temporal.setAmount(-temporalAmount);
						temporal.setClientId(clientId);
						icBillDao.updateTemporal(temporal);
						icBillDao.deleteObject(bill);
					} else if (bill.getItemId().intValue() == rent_deposi) {
						// 房租押金
						CDeposit deposit = new CDeposit();
						double depositAmount = bill.getActuallyPaid();
						deposit.setAmount(-depositAmount);
						deposit.setClientId(clientId);
						deposit.setDepositType("rent_deposit");
						icBillDao.updateDeposit(deposit);
						icBillDao.deleteObject(bill);
						//得到当前需要缴纳的房租押金信息
						double[] cost = icBillDao.getCost(bill.getCompactClient().getId());
						if(cost[0]+depositAmount>0.01){//如果需要缴纳的房租押金大于0，则置合同中的是否已缴房租押金为未缴(由于上面的sql语句没有真正的执行，所有这里要用cost[0]+depositAmount>0.01而不是cost[0]>0.01)
							icBillDao.update("update Compact set isRentDeposit='0' where clientId="+bill.getCompactClient().getId()+" and status='"+Contants.THROUGHAPPROVAL+"'");
						}
						
					} else if (bill.getItemId().intValue() == decoration_deposi) {
						// 装修押金
						CDeposit deposit = new CDeposit();
						double depositAmount = bill.getActuallyPaid();
						deposit.setAmount(-depositAmount);
						deposit.setClientId(clientId);
						deposit.setDepositType("decoration_deposit");
						icBillDao.updateDeposit(deposit);
						icBillDao.deleteObject(bill);
						//得到当前需要缴纳的房租押金和装修押金信息
						double[] cost = icBillDao.getCost(bill.getCompactClient().getId());
						if(cost[1]+depositAmount>0.01){//如果需要缴纳的装修押金大于0，则置合同中的是否已缴装修押金为未缴(由于上面的sql语句没有真正的执行，所有这里要用cost[1]+depositAmount>0.01而不是cost[1]>0.01)
							icBillDao.update("update Compact set isDecorationDeposit='0' where clientId="+bill.getCompactClient().getId()+" and status='"+Contants.THROUGHAPPROVAL+"'");
						}
					} else if (bill.getItemId().intValue() == earnest) {
						CEarnest earnest2 = new CEarnest();
						double depositAmount = bill.getActuallyPaid();
						earnest2.setAmount(-depositAmount);
						earnest2.setClientId(clientId);
						icBillDao.updateEarnest(earnest2);
						icBillDao.deleteObject(bill);
					} else {
						bill.setStatus("0");
						bill.setActuallyPaid(0);
						icBillDao.updateBill(bill);
					}
				}
			}
			icBillDao.deleteObject(charge);
		}
		/***********************************************************************
		 * if (checkbox!=null) { List<Integer> idList =
		 * Arrays.asList(checkbox); for (int j = 0; j < id.length; j++) { if
		 * (idList.contains(id[j])) { CCharge charge = chargeList.get(j); //账单
		 * Integer clientId = charge.getClientId(); String billId =
		 * charge.getBillId(); if (!ExtendUtil.checkNull(billId)) { String[] ids =
		 * billId.split(","); for (int i = 0; i < ids.length; i++) { CBill bill =
		 * new CBill();//账单 bill.setId(Integer.decode(ids[i]));
		 * bill.setStatus("0"); bill.setActuallyPaid(0);
		 * icBillDao.updateBill(bill); } } //暂存款 double temporalAmount =
		 * charge.getTemporalAmount(); if (temporalAmount != 0) { CTemporal
		 * temporal = new CTemporal(); temporal.setAmount(-temporalAmount);
		 * temporal.setClientId(clientId); icBillDao.updateTemporal(temporal); }
		 * //预收款 double advanceAmount = charge.getAdvanceAmount(); if
		 * (advanceAmount != 0) { CAdvance advance = new CAdvance();
		 * advance.setAmount(-advanceAmount); advance.setClientId(clientId);
		 * icBillDao.updateAdvance(advance); } //押金 double depositAmount =
		 * charge.getDepositAmount(); if (depositAmount != 0) { CDeposit deposit =
		 * new CDeposit(); deposit.setAmount(-depositAmount);
		 * deposit.setClientId(clientId); icBillDao.updateDeposit(deposit); } } }
		 * for (int i = 0; i < checkbox.length; i++) { CCharge charge = new
		 * CCharge(); charge.setId(checkbox[i]); icBillDao.deleteObject(charge); } }
		 **********************************************************************/
	}

	/**
	 * 退款信息 CBillServiceImpl.saveRefund
	 */
	/*
	 * public void saveRefund(CBillForm form)throws Exception{ Integer[] id =
	 * form.getId(); List idList = Arrays.asList(form.getCheckbox()); double[]
	 * amount = form.getReturnValue(); double[] amountCharged =
	 * form.getAmountCharged(); String[] billType = form.getBillType(); String[]
	 * billNum = form.getBillNum(); CRefund refund1 = form.getRefund();//退款信息 if
	 * (id != null) { for (int i = 0; i < id.length; i++) { if
	 * (idList.contains(id[i])) { CRefund refund = new CRefund();
	 * refund.setClientId(refund1.getClientId()); refund.setBz(refund1.getBz());
	 * refund.setRefundNum(refund1.getRefundNum());
	 * refund.setRefundDate(refund1.getRefundDate());
	 * refund.setUserName(refund1.getUserName()); refund.setBillId(id[i]);
	 * refund.setAmount(amount[i]); refund.setAmountCharged(amountCharged[i]);
	 * refund.setBillType(billType[i]); refund.setBillNum(billNum[i]);
	 * icBillDao.saveObject(refund); } } } }
	 */
	/**
	 * 电费查询 CBillServiceImpl.getElectricity
	 */
	/*
	 * public PageBean getElectricity(CBillForm formbean)throws Exception{
	 * String today = DateUtil.getNowDate("yyyy-MM"); if
	 * (ExtendUtil.checkNull(formbean.getBefore())) { formbean.setBefore(today); }
	 * if (ExtendUtil.checkNull(formbean.getAfter())) {
	 * formbean.setAfter(today); } if
	 * (ExtendUtil.checkNull(formbean.getPagesize())) {
	 * formbean.setPagesize(10); } PageBean page =
	 * icBillDao.getElectricity(formbean); return page; } public List<CBill>
	 * getExcelList1(CBillForm formbean) throws Exception{ return
	 * icBillDao.getExcelList1(formbean); }
	 */
	/**
	 * 押金查询 CBillServiceImpl.getDeposit
	 */
	public PageBean getDeposit(CBillForm formbean) throws Exception {
		if (ExtendUtil.checkNull(formbean.getPagesize())) {
			formbean.setPagesize(10);
		}
		PageBean pageBean = icBillDao.getDeposit(formbean);
		return pageBean;
	}

	/**
	 * 押金返还 CBillServiceImpl.returnDeposit
	 */
	public void returnDeposit(CBillForm formbean) throws Exception {
		icBillDao.returnDeposit(formbean);
	}

	/**
	 * \ 用户 CBillServiceImpl.getUserList
	 */
	public List<MUser> getUserList() throws Exception {
		return icBillDao.getObjects(MUser.class);
	}

	/**
	 * @return the icBillDao
	 */
	public ICBillDAO getIcBillDao() {
		return icBillDao;
	}

	/**
	 * @param icBillDao
	 *            the icBillDao to set
	 */
	public void setIcBillDao(ICBillDAO icBillDao) {
		this.icBillDao = icBillDao;
	}

	@Override
	public List<DTree> getClientTree4charge(String flag) throws Exception {
		List<DTree> treeList = new ArrayList<DTree>();
		DTree tr = new DTree("1", "客户", "0", "");
		treeList.add(tr);
		List list = null;
		try {
			list = icBillDao.getClientList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("客户查询失败：CBillServiceImpl.getClientTree4charge" + e.getMessage());
			throw new BkmisServiceException("客户查询失败：CBillServiceImpl.getClientTree4charge", e);
		}
		for (int i = 0; i < list.size() && list != null; i++) {
			Object[] objects = (Object[]) list.get(i);
			String id = String.valueOf(objects[0]);
			String name = String.valueOf(objects[1]);
			String parent = String.valueOf(objects[2]);
			String lb = String.valueOf(objects[3]);
			String url = "";
			if ("client".equals(lb)) {
				url = "bill.do?method=getChargeList&flag=start&isShowByUser=" + flag + "&clientId=" + id;
				id = "client" + id;
			}
			DTree tree = new DTree(id, name, parent, url);
			treeList.add(tree);
		}
		return treeList;
	}

	@Override
	public List getChargeList(CBillForm formbean) throws Exception {
		List list = icBillDao.getAllChargeList(formbean);
		return list;
	}

	/**
	 * 查询所有要交费的客户 CBillServiceImpl.queryCollect
	 */
	public PageBean queryCollect(CBillForm billForm) throws Exception {
		if (ExtendUtil.checkNull(billForm.getPagesize())) {
			billForm.setPagesize(10);
		}
		PageBean pageBean = icBillDao.queryCollect(billForm);
		return pageBean;
	}

	/**
	 * 查询客户合同 CBillServiceImpl.queryCompact
	 */
	public List<Compact> queryCompact(CBillForm billForm) throws Exception {
		return icBillDao.queryCompact(billForm.getClientId());
	}

	public List<Compact> queryCompact(List<CompactClient> clients) throws Exception {
		return icBillDao.queryCompact(clients);
	}

	@SuppressWarnings("unchecked")
	public List<ELp> queryELp() throws Exception {
		return icBillDao.findObject("from ELp");
	}

	public PageBean queryClient(CBillForm formbean) throws Exception {
		PageBean list = null;
		if (ExtendUtil.checkNull(formbean.getPagesize())) {
			formbean.setPagesize(10);
		}
		list = icBillDao.queryClient(formbean);
		return list;
	}

	public CItems getItems(String value) {
		return icBillDao.getItems(value);
	}

	public List<CompactClient> getClients(CBillForm billForm) {
		return icBillDao.getClients(billForm);
	}

	public double[] getCost(CBillForm billForm) {
		return icBillDao.getCost(billForm.getClientId());
	}

	@Override
	public List<CBill> getBillByIds(String billIds) throws Exception {
		List<CBill> billList = new ArrayList<CBill>();
		if(!GlobalMethod.NullToSpace(billIds).equals("")){
			String[] ids = billIds.split(",");
			for(int i=0;i<ids.length;i++){
				billList.add((CBill)icBillDao.getObject(CBill.class, Integer.parseInt(ids[i])));
			}
		}
		return billList;
	}

	@Override
	public String getBillNum() throws Exception {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String maxBillNum = icBillDao.getMaxBillNum();
		String date = dateformat.format(new Date());
		String dateStr = date.replaceAll("-", "");
		long dateInt = Long.parseLong(dateStr);
		long maxdate = dateInt;
		String returnStr = null;
		try {
			maxdate = Long.parseLong(maxBillNum.substring(0, 8));
		} catch (Exception e) {
			maxBillNum = dateStr+"001";
			e.printStackTrace();
		}
		if(dateInt>maxdate){
			returnStr =  dateInt+"001";
		}else{
			try {
				returnStr =  String.valueOf(Long.parseLong(maxBillNum)+1);
			} catch (RuntimeException e) {
				e.printStackTrace();
				returnStr =  dateInt+"001";
			}
		}
		return returnStr;
	}

	@Override
	public PageBean queryCurrentInClient(CBillForm billForm) throws BkmisServiceException {
		if (ExtendUtil.checkNull(billForm.getPagesize())) {
			billForm.setPagesize(10);
		}
		PageBean pageBean = icBillDao.queryCurrentInClient(billForm);
		return pageBean;
	}

	@Override
	public void saveInvoice(CBillForm billForm, 
			String tempId, 
			String advanceId, 
			String advanceWuYFId, 
			String rentDepositId,
			String decorationDeposit,
			String earnId) throws BkmisServiceException {
		
		//保存发票信息
		CInvoice cinvoice = new CInvoice();
		cinvoice.setClientId(billForm.getClientId());
		cinvoice.setAmount(billForm.getCharge().getAmount());//收费 表 金额
		cinvoice.setInvoiceNum(billForm.getBill().getBillNum());//发票号
		cinvoice.setLpId(billForm.getLpId());
		cinvoice.setDate(billForm.getBill().getCollectionDate());
		cinvoice.setOperatorId(Integer.valueOf(billForm.getBill().getUserId()));
		cinvoice.setPayType(billForm.getBill().getPaymentWay());
		cinvoice.setChequeNo(billForm.getBill().getChequeNo());//支票号
		
		Integer invoiceid = (Integer) icBillDao.saveObject(cinvoice);
		
		//保存<发票-账单>关系信息
		Integer[] billIds = billForm.getCheckbox();
		double[] actuallyPaid = billForm.getActuallyPaid();
		
		if(null!=billIds && billIds.length>=1){
			for(int i = 0;i<billIds.length;i++){
				CInvoiceBill cInvoiceBill = new CInvoiceBill();
				cInvoiceBill.setBillId(billIds[i]);
				cInvoiceBill.setInvoiceAmount(actuallyPaid[i]);
				cInvoiceBill.setInvoiceId(invoiceid);
				icBillDao.saveObject(cInvoiceBill);
			}
		}
		
		//暂存款
		if(!"".equals(GlobalMethod.NullToSpace(tempId).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(tempId));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
		
		//预收款
		if(!"".equals(GlobalMethod.NullToSpace(advanceId).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(advanceId));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
		/** 预收物业费  
		 * gd 2018年3月3日15:16:07*/
		if(!"".equals(GlobalMethod.NullToSpace(advanceWuYFId).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(advanceWuYFId));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
		
		//房租押金
		if(!"".equals(GlobalMethod.NullToSpace(rentDepositId).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(rentDepositId));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
		
		//装修押金
		if(!"".equals(GlobalMethod.NullToSpace(decorationDeposit).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(decorationDeposit));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
		
		//
		if(!"".equals(GlobalMethod.NullToSpace(earnId).trim())) {
			CInvoiceBill cInvoiceBill = new CInvoiceBill();
			CBill bill = (CBill) icBillDao.getObject(CBill.class, Integer.valueOf(earnId));
			cInvoiceBill.setBillId(bill.getId());
			cInvoiceBill.setInvoiceAmount(bill.getActuallyPaid());
			cInvoiceBill.setInvoiceId(invoiceid);
			icBillDao.saveObject(cInvoiceBill);
		}
	}

	/**
	 * 查询财务使用的房租信息
	 */
	@Override
	public PageBean queryRentInfo(CBillForm billForm) {
		if (ExtendUtil.checkNull(billForm.getPagesize())) {
			billForm.setPagesize(10);
		}
		PageBean pageBean = icBillDao.queryRentInfo(billForm);
		return pageBean;
	}

	/**
	 * 查询财务使用的房租信息用于导出excel
	 */
	@Override
	public List queryRentInfoExportExcel(CBillForm billForm) {
		
		return icBillDao.queryRentInfoExportExcel(billForm);
	}
}
