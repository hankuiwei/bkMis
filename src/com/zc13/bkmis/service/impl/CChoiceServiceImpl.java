/**
 * Administrator
 */
package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zc13.bkmis.dao.ICChoiceDAO;
import com.zc13.bkmis.form.CChoiceForm;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.ICChoiceService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;

import common.Logger;

/**
 * @author zhaosg Date：Dec 7, 2010 Time：2:47:24 PM
 */
public class CChoiceServiceImpl extends BasicServiceImpl implements
		ICChoiceService {
	Logger logger = Logger.getLogger(getClass());
	private ICChoiceDAO icChoiceDao;
	private ICostTransactService costTransactService;

	public ICChoiceDAO getIcChoiceDao() {
		return icChoiceDao;
	}

	public void setIcChoiceDao(ICChoiceDAO icChoiceDao) {
		this.icChoiceDao = icChoiceDao;
	}
	
	public ICostTransactService getCostTransactService() {
		return costTransactService;
	}

	public void setCostTransactService(ICostTransactService costTransactService) {
		this.costTransactService = costTransactService;
	}

	/**
	 * 树：帐套--费用类型--收费标准
	 */
	public List<DTree> getTreeList(CChoiceForm choiceForm) throws Exception {
		List<DTree> treeList = new ArrayList<DTree>();
		DTree tr = new DTree("1", "收费标准", "0", "");
		treeList.add(tr);
		List list = null;
		try {
			list = icChoiceDao.getTreeList(choiceForm);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("收费标准查询失败：getTreeList。", e);
			throw new BkmisServiceException("收费标准查询失败：getTreeList。", e);
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String id = String.valueOf(obj[0]);
			String name = String.valueOf(obj[2]);
			String parentId = String.valueOf(obj[1]);
			String type = String.valueOf(obj[3]);
			String lpid = String.valueOf(obj[4]);
			String Url = "";
			if ("费用类型".equals(type)) {
				id += "_2_" + lpid;
			} else if ("收费标准".equals(type)) {
				id += "_3_" + lpid;
				parentId += "_2_" + lpid;
				Url = "choice.do?method=getList&standardId="
						+ String.valueOf(obj[0]) + "&lpId=" + lpid
						+ "&billType=" + String.valueOf(obj[5]);
			}
			DTree tree = new DTree(id, name, parentId, Url);
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 收费选择查询 
	 */
	public List getList(CChoiceForm form) throws Exception {
		Integer standardId = form.getStandardId();
		String billType = form.getBillType() == null ? "" : form.getBillType();
		List list = null;
		try {
			if (standardId != null && standardId.intValue() != 0) {
				if (billType.equals("room")) {
					list = icChoiceDao.getList(standardId, form.getLpId(), form
							.getBuildId(), form.getFloor(),form.getClientName());
				} else if (billType.equals("person")) {
					list = icChoiceDao.getListPact(standardId, form.getLpId(),
							form.getBuildId(), form.getFloor(),form.getClientName());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("收费选择查询失败：CChoiceServiceImpl.getList。", e);
			throw new BkmisServiceException(
					"收费选择查询失败：CChoiceServiceImpl.getList。", e);
		}
		return list;
	}

	/**
	 * 收费标准
	 */
	public CCoststandard getCStandard(CChoiceForm form) throws Exception {
		Integer standardId = form.getStandardId();
		CCoststandard item = new CCoststandard();
		try {
			if (standardId != null && standardId.intValue() != 0) {
				item = icChoiceDao.getCStandard(standardId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("收费标准查询失败：getCStandard。", e);
			throw new BkmisServiceException("收费标准查询失败：getCStandard。", e);
		}
		return item;
	}

	/**
	 * 楼幢
	 */
	public List<EBuilds> getEBuilds(CChoiceForm form) throws Exception {
		Integer lpId = form.getLpId();
		List<EBuilds> list = null;
		try {
			if (lpId.intValue() != 0) {
				list = icChoiceDao.getBuilds(lpId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("楼幢查询失败：getEBuilds。", e);
			throw new BkmisServiceException("楼幢查询失败：getEBuilds。", e);
		}
		return list;
	}

	/**
	 * 保存收费选择
	 */
	public void save(CChoiceForm form) throws Exception {
		
		List crcList = form.getCrcList();// 收费标准-房间-客户
		List csList = form.getCsList();// 收费标准
		List clientList = form.getClientList();// 客户
		List roomList = form.getRoomList();// 房间
		List compactList = form.getCompactList();//合同
		List costtypeList = form.getCosttypeList();//费用类型
		CCoststandard standard = form.getStandard();
		List<Integer> checkboxs = new ArrayList<Integer>();
		if (form.getCheckbox() != null) {
			checkboxs = Arrays.asList(form.getCheckbox());
		}
		for (int i = 0; i < crcList.size(); i++) {
			CompactRoomCoststandard csbean = (CompactRoomCoststandard) crcList.get(i);
			csbean.setCCoststandard((CCoststandard) csList.get(i));
			csbean.setCompactClient((CompactClient) clientList.get(i));
			csbean.setCompact((Compact) compactList.get(i));
			csbean.setCCosttype((CCosttype) costtypeList.get(i));
			csbean.setStatus("1");
			csbean.setLpId(form.getLpId());
			//bean.setCCoststandard(standard);
			Integer id = null;
			if (form.getBillType().equals("room")) {
				ERooms rooms = (ERooms) roomList.get(i);
				id = rooms.getRoomId();
				csbean.setERooms(rooms);
			} else if (form.getBillType().equals("person")) {
				id = csbean.getCompactClient().getId();
				ERooms rooms = null;
				csbean.setERooms(rooms);
			}
			if (csbean.getId().intValue() > 0) {
				if (checkboxs.contains(id)) {
					
					icChoiceDao.updateObject(csbean);
					// System.out.println("update=====");
				} else {
					icChoiceDao.deleteObject(csbean);
					// System.out.println("delete=====");
				}
			} else if (checkboxs.contains(id)) {
				icChoiceDao.saveObject(csbean);
				// System.out.println("save=====");
			}
		}
		
		/**2018年2月28日21:10:48 
		 * &1如果收费标准 为 预收物业费,预收物业费半年付,预收物业费年付 其中之一,
		 * &2并且checkboxs != null 
		 * 进行物业费 账单 计费.
		 * */
		/*
		boolean flag = false ;
		for(int cs=0;cs < csList.size();cs++){
			Integer csId = ((CCoststandard) csList.get(cs)).getId();
			CCoststandard cStandard = icChoiceDao.getCStandard(csId);
			String standardName = cStandard.getStandardName();
			if(standardName!=null && 
					(standardName.equals(Contants.WuYFJF)
							||standardName.equals(Contants.WuYFBNF)
							||standardName.equals(Contants.WuYFNF))){
				flag = true ;
				break;
			}
		}
		if(flag && checkboxs!=null){
			costTransactService.autoBuildWuYFBill(false);
		}*/
	}
	public CCoststandard getCCoststandardById(Integer id ) throws Exception{
		return icChoiceDao.getCStandard(id);
	}
	
	
	/**
	 * 费用录入
	 * CChoiceServiceImpl.saveBill
	 */
	public void saveBill(CChoiceForm choiceForm,HttpServletRequest request) throws Exception {
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		
		String ids = choiceForm.getIds();
		CBill cBill = choiceForm.getBill();
		if (!ExtendUtil.checkNull(ids)) {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				CBill bill = new CBill();
				CompactClient client = new CompactClient();
				// bill.setCCoststandard(choiceForm.getStandard());// 收费标准
				client.setId(Integer.decode(id[i]));
				bill.setCompactClient(client);// 客户
				bill.setBillCode(cBill.getBillCode());//申请单号
				bill.setEntryUserId(choiceForm.getUserId());
				bill.setStatus("0");// 单据状态
				bill.setBillDate(cBill.getBillDate());// 账单月份
				bill.setBillsExpenses(cBill.getBillsExpenses());// 合同金额
				bill.setCreateDate(cBill.getCreateDate());// 账单日期
				bill.setStartDate(cBill.getStartDate());// 开始日期
				bill.setEndDate(cBill.getEndDate());// 结束日期
				bill.setActuallyPaid(0);// 实付金额
				// bill.setStandardName(cBill.getStandardName());//费用名称
				bill.setNotes(cBill.getNotes());//原因
				bill.setItemId(choiceForm.getItemId());//收费项
				bill.setLpId(choiceForm.getLpId());
				bill.setCloseDate(cBill.getCloseDate());//关帐日期(最晚缴款日期)
				try {
					icChoiceDao.saveObject(bill);
					icChoiceDao.logDetail(userName, Contants.ADD, "费用录入", Contants.L_COST, "2", bill);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("添加账单失败：CBillServiceImpl.saveBill。"
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 选择客户
	 * CChoiceServiceImpl.getClientList
	 */
	public List<DTree> getClientList(CChoiceForm formbean) throws Exception {
		List<DTree> treeList = new ArrayList<DTree>();
		DTree tr = new DTree("1", "客户", "0", "");
		treeList.add(tr);
		List list = null;
		try {
			list = icChoiceDao.getClientList(formbean);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("客户查询失败：CChoiceServiceImpl.getClientList"
					+ e.getMessage());
			e.printStackTrace();
		}
		for (int i = 0; i < list.size() && list != null; i++) {
			Object[] objects = (Object[]) list.get(i);
			String id = String.valueOf(objects[0]);
			String name = String.valueOf(objects[1]);
			String parent = String.valueOf(objects[2]);
			String lb = String.valueOf(objects[3]);
			String url = "";
			if ("client".equals(lb)) {
				String ids = formbean.getIds();
				url = "<input type=checkbox name=\"client\" value=\"" + id
						+ "," + name + "," + parent + "\" ";
				if (ids != null && Arrays.asList(ids.split(",")).contains(id)) {
					url += " checked ";
				}
				url += "/>";
				id = "client" + id;
			} else if ("lp".equals(lb)) {
				url = "<input type=checkbox onclick=setChecked(this," + id
						+ ")>";
			}
			DTree tree = new DTree(id, name, parent, url);
			treeList.add(tree);
		}
		return treeList;
	}
}
