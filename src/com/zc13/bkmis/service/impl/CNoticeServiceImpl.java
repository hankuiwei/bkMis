/**
 * ZHAOSG
 */
package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICBillDAO;
import com.zc13.bkmis.dao.ICItemsDAO;
import com.zc13.bkmis.dao.ICNoticeDAO;
import com.zc13.bkmis.dao.ICostTransactDAO;
import com.zc13.bkmis.dao.ICustomerRoomDAO;
import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.form.CNoticeForm;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CNotice;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ICNoticeService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

/**
 * @author ZHAOSG Date：Jan 3, 2011 Time：2:39:55 PM
 */
public class CNoticeServiceImpl extends BasicServiceImpl implements
		ICNoticeService {
	private ICNoticeDAO icNoticeDAO;
	private ICItemsDAO icItemsDao;
	private ICBillDAO icBillDao;
	private ICustomerRoomDAO icustomerRoomDao;
	private ICostTransactDAO icostTransactDAO;
	

	Logger logger = Logger.getLogger(this.getClass());

	public ICostTransactDAO getIcostTransactDAO() {
		return icostTransactDAO;
	}

	public void setIcostTransactDAO(ICostTransactDAO icostTransactDAO) {
		this.icostTransactDAO = icostTransactDAO;
	}

	public ICItemsDAO getIcItemsDao() {
		return icItemsDao;
	}

	public void setIcItemsDao(ICItemsDAO icItemsDao) {
		this.icItemsDao = icItemsDao;
	}

	public ICBillDAO getIcBillDao() {
		return icBillDao;
	}

	public void setIcBillDao(ICBillDAO icBillDao) {
		this.icBillDao = icBillDao;
	}
	

	public ICustomerRoomDAO getIcustomerRoomDao() {
		return icustomerRoomDao;
	}

	public void setIcustomerRoomDao(ICustomerRoomDAO icustomerRoomDao) {
		this.icustomerRoomDao = icustomerRoomDao;
	}

	/* (non-Javadoc)
	 * @see com.zc13.bkmis.service.ICNoticeService#save(com.zc13.bkmis.form.CNoticeForm, java.util.Map)
	 */
	public int save(CNoticeForm noticeForm,Map<Integer,Map<String,List>> map) throws Exception {
		int count = 0;
		if (noticeForm.getClientId() != null&& noticeForm.getItemId() != null) {
			icNoticeDAO.deleteNotice(noticeForm);//每次删除 只删除选中的,
			count = icNoticeDAO.save(noticeForm);//添加 未缴费状态的收费项 通知.
			if(map!=null&&!map.isEmpty()){
				Set<Integer> set = map.keySet();
				Iterator<Integer> it = set.iterator();
				/**2 遍历 每个客户的需要缴纳的预收房租，物业费 ,押金，定金等信息*/
				while(it.hasNext()){
					Integer clientID = it.next();//客户的id
					Map<String,List> clientMap = map.get(clientID);
					/**2.2 预收房租信息*/
						List list1 = clientMap.get("pressAdvance");
						if(list1!=null&&list1.size()>0){
							count++;
							Map advanceMap = (Map)list1.get(0);
							
							CNotice notice1 = new CNotice();
							notice1.setClientId(Integer.parseInt(advanceMap.get("clientId").toString()));
							notice1.setClientName(GlobalMethod.ObjToStr(advanceMap.get("clientName")));
							notice1.setAmount(Double.parseDouble(GlobalMethod.ObjToStr(advanceMap.get("mustPay"))));//预收租金
							notice1.setNoticeDate(noticeForm.getNotice().getNoticeDate());
							notice1.setNoticeType(noticeForm.getNotice().getNoticeType());
							CItems items1 = icBillDao.getItems(Contants.ITEM_DESTINERENT);//获取预收房租的收费项
							notice1.setItemId(items1.getId());
							notice1.setItemName(items1.getItemName());
							notice1.setLpId(noticeForm.getLpId());
							icNoticeDAO.saveObject(notice1);
							
							CNotice notice2 = new CNotice();
							notice2.setClientId(Integer.parseInt(advanceMap.get("clientId").toString()));
							notice2.setClientName(GlobalMethod.ObjToStr(advanceMap.get("clientName")));
							notice2.setAmount(Double.parseDouble(GlobalMethod.ObjToStr(advanceMap.get("leastPay"))));//预收物业费
							notice2.setNoticeDate(noticeForm.getNotice().getNoticeDate());
							notice2.setNoticeType(noticeForm.getNotice().getNoticeType());
							notice2.setLpId(noticeForm.getLpId());
							
							List<CompactRoomCoststandard> validRentCoststandardList = icostTransactDAO.getValidRentCoststandard(clientID, null, null);
							for (CompactRoomCoststandard crc : validRentCoststandardList) {
								if(Contants.WuYFJF.equals(crc.getCCoststandard().getStandardName())){
									notice2.setItemId(crc.getCCoststandard().getItemId());
									notice2.setItemName(crc.getCCoststandard().getStandardName());
									icNoticeDAO.saveObject(notice2);
									break ;
								}
								else if(Contants.WuYFBNF.equals(crc.getCCoststandard().getStandardName())){
									notice2.setItemId(crc.getCCoststandard().getItemId());
									notice2.setItemName(crc.getCCoststandard().getStandardName());
									icNoticeDAO.saveObject(notice2);
									break ;
								}
								else if(Contants.WuYFNF.equals(crc.getCCoststandard().getStandardName())){
									notice2.setItemId(crc.getCCoststandard().getItemId());
									notice2.setItemName(crc.getCCoststandard().getStandardName());
									icNoticeDAO.saveObject(notice2);
									break ;
								}
							}
						}//预收款通知结束.
					
						/**2.2房租押金信息*/
						List list2 = clientMap.get("rentPressDeposit");
						if(list2!=null&&list2.size()>0){
							count++;
							Map tempMap = (Map)list2.get(0);
							CNotice notice1 = new CNotice();
							notice1.setClientId(Integer.parseInt(tempMap.get("clientId").toString()));
							notice1.setClientName(GlobalMethod.ObjToStr(tempMap.get("clientName")));
							notice1.setAmount(Double.parseDouble(GlobalMethod.ObjToStr(tempMap.get("payExpenses"))));
							notice1.setNoticeDate(noticeForm.getNotice().getNoticeDate());
							notice1.setNoticeType(noticeForm.getNotice().getNoticeType());
							CItems items1 = icBillDao.getItems(Contants.RENT_DEPOSIT);//获取房租押金的收费项
							notice1.setItemId(items1.getId());
							notice1.setItemName(items1.getItemName());
							notice1.setLpId(noticeForm.getLpId());
							icNoticeDAO.saveObject(notice1);
						}
						/**3.装修押金信息*/
						List list3 = clientMap.get("decorationPressDeposit");
						if(list3!=null&&list3.size()>0){
							count++;
							Map tempMap = (Map)list3.get(0);
							CNotice notice1 = new CNotice();
							notice1.setClientId(Integer.parseInt(tempMap.get("clientId").toString()));
							notice1.setClientName(GlobalMethod.ObjToStr(tempMap.get("clientName")));
							notice1.setAmount(Double.parseDouble(GlobalMethod.ObjToStr(tempMap.get("payExpenses"))));
							notice1.setNoticeDate(noticeForm.getNotice().getNoticeDate());
							notice1.setNoticeType(noticeForm.getNotice().getNoticeType());
							CItems items1 = icBillDao.getItems(Contants.DECORATION_DEPOSIT);//获取房租押金的收费项
							notice1.setItemId(items1.getId());
							notice1.setItemName(items1.getItemName());
							notice1.setLpId(noticeForm.getLpId());
							icNoticeDAO.saveObject(notice1);
						}
						/**4.定金信息*/
						List list4 = clientMap.get("pressEarnest");
						if(list4!=null&&list4.size()>0){
							count++;
							Map tempMap = (Map)list4.get(0);
							CNotice notice1 = new CNotice();
							notice1.setClientId(Integer.parseInt(tempMap.get("clientId").toString()));
							notice1.setClientName(GlobalMethod.ObjToStr(tempMap.get("clientName")));
							notice1.setAmount(Double.parseDouble(GlobalMethod.ObjToStr(tempMap.get("payExpenses"))));
							notice1.setNoticeDate(noticeForm.getNotice().getNoticeDate());
							notice1.setNoticeType(noticeForm.getNotice().getNoticeType());
							CItems items1 = icBillDao.getItems(Contants.ITEM_EARNEST);//获取房租押金的收费项
							notice1.setItemId(items1.getId());
							notice1.setItemName(items1.getItemName());
							notice1.setLpId(noticeForm.getLpId());
							icNoticeDAO.saveObject(notice1);
						}
					/**5. 电话通话费*/	
					
				}
			}
		}
		return count;
	}

	public PageBean getNoticeList(CNoticeForm noticeForm) throws Exception {
		if (ExtendUtil.checkNull(noticeForm.getPagesize())) {
			noticeForm.setPagesize(10);
		}
		PageBean pageBean = icNoticeDAO.getNotiveList(noticeForm);
		return pageBean;
	}
	/**
	 * 删除
	 * CNoticeServiceImpl.delete
	 */
	public void delete(CNoticeForm noticeForm) throws Exception {
		String[] checkbox = noticeForm.getCheckbox();
		if (checkbox != null) {
			for (int i = 0; i < checkbox.length; i++) {
				CNotice notice = new CNotice();
				String[] str = checkbox[i].split(",");
				notice.setClientId(Integer.decode(str[0]));
				notice.setNoticeDate(str[1]);
				icNoticeDAO.delete(notice);
			}
		}
	}
	/**
	 * 客户
	 * CNoticeServiceImpl.getClientList
	 */
	public List getClientList(CNoticeForm noticeForm) throws Exception {
		return icNoticeDAO.getClientList(noticeForm);
	}
	/**
	 * 收费标准
	 * CNoticeServiceImpl.getStandardList
	 */
	public List getStandardList(CNoticeForm noticeForm) throws Exception {
		return icNoticeDAO.getStandardList(noticeForm);
	}
	/**
	 * 查询
	 * CNoticeServiceImpl.getNotice
	 */
	public List<CNotice> getNotice(CNoticeForm noticeForm) throws Exception {
		return icNoticeDAO.getNotice(noticeForm);
	}
	/**
	 * 楼盘
	 * CNoticeServiceImpl.getLp
	 */
	public List<ELp> getLp() throws Exception {
		return icNoticeDAO.getObjects(ELp.class);
	}
	/**
	 * 楼幢
	 * 	 * CNoticeServiceImpl.getBuild
	 */
	public List<EBuilds> getBuild() throws Exception {
		return icNoticeDAO.getObjects(EBuilds.class);
	}
	/**
	 * 全部打印
	 * CNoticeServiceImpl.getAllNoticePrint
	 */
	public List getAllNoticePrint(CNoticeForm noticeForm)throws Exception {
		List<CNotice> list = icNoticeDAO.getAllNoticePrint(noticeForm);
		String roomCode ="";
		Integer clientId = null;
		String noticeDate = "";
		String clientName = "";
		String noticeType = "";
		double amount = 0;
		List printList = new ArrayList();
		List<CNotice> noticeList = new ArrayList();
		for (CNotice notice : list) {
			if (clientId == null) {
				clientId = notice.getClientId();
				noticeDate = notice.getNoticeDate();
				clientName = notice.getClientName();
				noticeType = notice.getNoticeType();
				roomCode = icNoticeDAO.getRoomCodeByClientId(clientId);
				// amount = notice.getAmount();
			}
			roomCode = icNoticeDAO.getRoomCodeByClientId(clientId);
			if (clientId.intValue() == notice.getClientId().intValue() && noticeDate.equals(notice.getNoticeDate()) && noticeType.equals(notice.getNoticeType())) {
				noticeList.add(notice);
				amount += notice.getAmount();
			} else {
				Map map = new HashMap();
				map.put("noticeList", noticeList);
				map.put("clientName", clientName);
				map.put("noticeDate", noticeDate);
				map.put("amount", amount);
				map.put("roomCode", roomCode);
				printList.add(map);
				noticeList = new ArrayList();
				clientId = notice.getClientId();
				noticeDate = notice.getNoticeDate();
				clientName = notice.getClientName();
				amount = notice.getAmount();
				roomCode = icNoticeDAO.getRoomCodeByClientId(clientId);
				noticeList.add(notice);
			}
		}
		Map map = new HashMap();
		map.put("noticeList", noticeList);
		map.put("clientName", clientName);
		map.put("noticeDate", noticeDate);
		map.put("amount", amount);
		map.put("roomCode", roomCode);
		printList.add(map);
		return printList;
	}
	/**
	 * 打印
	 * CNoticeServiceImpl.getNoticePrint
	 */
	public List getNoticePrint(CNoticeForm noticeForm)throws Exception {
		List<CNotice> list = icNoticeDAO.getNoticePrint(noticeForm);
		Integer clientId = null;
		String noticeDate = "";
		String clientName = "";
		String roomCode = "";
		double amount = 0;
		List printList = new ArrayList();
		List<CNotice> noticeList = new ArrayList();
		for (CNotice notice : list) {
			if (clientId == null) {
				clientId = notice.getClientId();
				noticeDate = notice.getNoticeDate();
				clientName = notice.getClientName();
				roomCode = icNoticeDAO.getRoomCodeByClientId(clientId);
			}
			if (clientId.intValue() == notice.getClientId().intValue() && noticeDate.equals(notice.getNoticeDate())) {
				noticeList.add(notice);
				amount += notice.getAmount();
			}else {
				
				Map map = new HashMap();
				map.put("noticeList", noticeList);
				map.put("clientName", clientName);
				map.put("noticeDate", noticeDate);
				map.put("roomCode", roomCode);
				map.put("amount", amount);
				map.put("roomCode", roomCode);
				printList.add(map);
				noticeList = new ArrayList();
				clientId = notice.getClientId();
				noticeDate = notice.getNoticeDate();
				clientName = notice.getClientName();
				amount = notice.getAmount();
				roomCode = icNoticeDAO.getRoomCodeByClientId(clientId);
				noticeList.add(notice);
			}
		}
		Map map = new HashMap();
		map.put("noticeList", noticeList);
		map.put("clientName", clientName);
		map.put("noticeDate", noticeDate);
		map.put("amount", amount);
		map.put("roomCode", roomCode);
		printList.add(map);
		return printList;
	}
	/**
	 * @return the icNoticeDAO
	 */
	public ICNoticeDAO getIcNoticeDAO() {
		return icNoticeDAO;
	}

	/**
	 * @param icNoticeDAO
	 *            the icNoticeDAO to set
	 */
	public void setIcNoticeDAO(ICNoticeDAO icNoticeDAO) {
		this.icNoticeDAO = icNoticeDAO;
	}

	@Override
	public List getItemList(CNoticeForm noticeForm) {
		List list = null;
		CItemsForm form = new CItemsForm();
		form.setLpId(noticeForm.getLpId());
		try{
			list = icItemsDao.getItemsList(form,true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("收费项信息查询失败!CItemsServiceImpl.getItemsList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费项信息查询失败!CItemsServiceImpl.getItemsList()。");
		}
		return list;
	}
	
	@Override
	public String getRoomCodeByClientId(int id) throws DataAccessException {
		return icNoticeDAO.getRoomCodeByClientId(id);
	}
}
