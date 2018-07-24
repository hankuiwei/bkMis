package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.dao.IDestineDAO;
import com.zc13.bkmis.dao.IIntentionDAO;
import com.zc13.bkmis.form.IntentionForm;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.ERoomIntention;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.IntentionRent;
import com.zc13.bkmis.mapping.IntentionRoomCoststandard;
import com.zc13.bkmis.service.IIntentionService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/**
 * 意向书service
 * 
 * @author wangzw
 * @Date May 11, 2011
 * @Time 10:48:55 AM
 */
public class IntentionServiceImpl implements IIntentionService {

	private IIntentionDAO intentionDAO;
	private IDestineDAO destineDAO;
	private ICompactManagerDAO compactManagerDAO;

	public ICompactManagerDAO getCompactManagerDAO() {
		return compactManagerDAO;
	}

	public void setCompactManagerDAO(ICompactManagerDAO compactManagerDAO) {
		this.compactManagerDAO = compactManagerDAO;
	}

	public IDestineDAO getDestineDAO() {
		return destineDAO;
	}

	public void setDestineDAO(IDestineDAO destineDAO) {
		this.destineDAO = destineDAO;
	}

	public IIntentionDAO getIntentionDAO() {
		return intentionDAO;
	}

	public void setIntentionDAO(IIntentionDAO intentionDAO) {
		this.intentionDAO = intentionDAO;
	}

	// 得到所有意向书列表
	@Override
	public void getList(IntentionForm intentionForm) throws BkmisServiceException {
		try {
			intentionDAO.queryList(intentionForm, true);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}

	// 获得意向书数量
	@Override
	public int getTotalCount(IntentionForm intentionForm) throws BkmisServiceException {
		int count = 0;
		try {
			intentionDAO.queryList(intentionForm, false);
			if (intentionForm != null && intentionForm.getIntentionList() != null) {
				count = intentionForm.getIntentionList().size();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return count;
	}

	// 根据id查询出来的意向书的状态是否都为未提交审批或审批不通过的（用来判断是否可以删除或编辑）
	@Override
	public boolean checkIntentionStatus(String id) throws BkmisServiceException {
		boolean check = true;
		try {
			IntentionForm intentionForm = new IntentionForm();
			intentionForm.setId(id);
			intentionDAO.queryList(intentionForm, false);
			List<CompactIntention> list = intentionForm.getIntentionList();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// 如果状态为待审批或审批通过，则返回false;
					if (Contants.ONAPPROVAL.equals(list.get(i).getStatus()) || Contants.THROUGHAPPROVAL.equals(list.get(i).getStatus())) {
						check = false;
					}
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return check;
	}

	// 获得意向书编号
	@Override
	public String getIntentionCode(IntentionForm intentionForm) throws BkmisServiceException {
		
		List<CompactIntention> list = (List<CompactIntention>) intentionDAO.findObject("from CompactIntention where lpId="+intentionForm.getLpId());
		
		String year = GlobalMethod.getTime().substring(0, 4);
		String code = intentionDAO.getIntentionCode();
		if (list != null && list.size() > 0) {
			String[] strings = code.split("-");
			if (Integer.parseInt(year) > Integer.parseInt(strings[0])) {// 如果当前年份大于已存在的最大年份，那么重新从1开始编号
				return year + "-" + "001";
			} else {
				code = String.valueOf(Integer.parseInt(strings[1]) + 1);
				if (code.length() == 1) {
					code = "00" + code;
				} else if (code.length() == 2) {
					code = "0" + code;
				}
				return year + "-" + code;
			}
		} else {
			return year + "-" + "001";
		}
	}

	/* 保存或更新意向书 */
	public void saveIntention(IntentionForm intentionform) throws BkmisServiceException {
		
		try {
			Integer clientId = null;// 客户id
			Integer compactIntentionId = null;// 意向书id
			clientId = intentionform.getCompactIntention().getClientId();
			compactIntentionId = intentionform.getCompactIntention().getId();
			if (clientId != null && clientId.intValue() == 0) {// 如果值为0，将之设为null
				clientId = null;
			}
			if (compactIntentionId != null && compactIntentionId.equals(new Integer(0))) {// 如果值为0，将之设为null
				compactIntentionId = null;
			}

			/* 保存或更新客户信息start */
			clientId = saveOrUpdateCustomer(intentionform);

			/* 保存或更新意向书信息start */
			if (compactIntentionId == null) {// 保存意向书
				intentionform.getCompactIntention().setClientId(clientId);
				CompactIntention compactintention = intentionform.getCompactIntention();
				// 保存审核状态
				compactintention.setStatus(Contants.NOTSUBMIT);
				// 保存有效标志
				compactintention.setIsEnabled(Contants.ENABLE);
				compactintention.setIsEarnest("0");
				compactintention.setLpId(intentionform.getLpId());//楼盘号
				compactIntentionId = (Integer) intentionDAO.saveObject(compactintention);
				//记录日志
				intentionDAO.log(intentionform.getUserName(), "添加", "添加的意向书的编号为："+compactintention.getIntentionCode(), Contants.L_CLIENT, "意向书");
			} else {// 更新意向书
				// 更新意向书信息
				CompactIntention intention = new CompactIntention();//
				try {
					BeanUtils.copyProperties(intention, intentionform.getCompactIntention());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				// 保存审核状态
				intention.setStatus(Contants.NOTSUBMIT);
				// 保存有效标志
				intention.setIsEnabled(Contants.ENABLE);
				intention.setIsEarnest("0");
				//记录日志
				intentionDAO.logDetail(intentionform.getUserName(), "修改", "意向书", Contants.L_CLIENT, "1", intention);
				//更新
				intentionDAO.updateObject(intention);
				
				// 删除旧的房租信息
				List<IntentionRent> rents = intentionDAO.getRentList(compactIntentionId);
				for (int i = 0; i < rents.size(); i++) {
					intentionDAO.deleteObject(rents.get(i));
				}

				// 删除旧的收费标准信息
				List<IntentionRoomCoststandard> list = intentionDAO.getStandardListByIntentionId(compactIntentionId);
				for (int i = 0; i < list.size(); i++) {
					intentionDAO.deleteObject(list.get(i));
				}

				// 删除旧的客户房间信息并改变房间的状态
				List<ERoomIntention> list1 = intentionDAO.getRoomListByClientId(clientId, compactIntentionId);
				for (int i = 0; i < list1.size(); i++) {
					ERooms eRooms = (ERooms) intentionDAO.getObject(ERooms.class, list1.get(i).getRoomId());
					eRooms.setStatus(Contants.OUTUSE);
					intentionDAO.updateObject(eRooms);
					intentionDAO.deleteObject(list1.get(i));
				}
			}
			/* 保存或更新意向书信息end */

			// 保存房租信息表
			if (intentionform.getBeginDateCost() != null && intentionform.getBeginDateCost().length > 0) {//
				for (int i = 0; i < intentionform.getBeginDateCost().length; i++) {
					IntentionRent rent = new IntentionRent();
					rent.setCompactIntention((CompactIntention) intentionDAO.getObject(CompactIntention.class, compactIntentionId));
					rent.setRent(intentionform.getRent()[i]);
					rent.setBeginDate(intentionform.getBeginDateCost()[i]);
					rent.setEndDate(intentionform.getEndDateCost()[i]);
					rent.setLpId(intentionform.getLpId());
					intentionDAO.saveObject(rent);
				}
			}

			// 将房间状态置为已预租。并且保存房租的收费标准
			if (intentionform.getClientRoomId() != null && intentionform.getClientRoomId().length > 0) {
				for (int i = 0; i < intentionform.getClientRoomId().length; i++) {//
					ERooms eRooms = (ERooms) intentionDAO.getObject(ERooms.class, intentionform.getClientRoomId()[i]);
					eRooms.setStatus(Contants.DESTINE);
					intentionDAO.updateObject(eRooms);
					// 添加房租收费标准，默认值
					IntentionRoomCoststandard intentionStantard = new IntentionRoomCoststandard();
					intentionStantard.setCompactIntention((CompactIntention) intentionDAO.getObject(CompactIntention.class, compactIntentionId));
					// 获取默认的收费标准id，display值为none的那个
					List list = intentionDAO.findObject("from CCoststandard where display is not Null ");
					if (list.size() > 0) {
						CCoststandard coststandard = (CCoststandard) list.get(0);
						intentionStantard.setCCoststandard(coststandard);
					}
					intentionStantard.setERooms(eRooms);
					intentionStantard.setCompactClient((CompactClient) intentionDAO.getObject(CompactClient.class, clientId));
					intentionStantard.setBeginDate(intentionform.getCompactIntention().getBeginDate());
					intentionStantard.setEndDate(intentionform.getCompactIntention().getEndDate());
					intentionStantard.setDisplay("none");
					intentionStantard.setStatus("1");
					intentionStantard.setAmount(1);
					intentionStantard.setLpId(intentionform.getLpId());
					intentionDAO.saveObject(intentionStantard);
				}
			}

			// 保存合同对应收费标准表
			if (intentionform.getCostStandartId() != null && intentionform.getCostStandartId().length > 0) {
				for (int i = 0; i < intentionform.getCostStandartId().length; i++) {
					IntentionRoomCoststandard intentionStantard = new IntentionRoomCoststandard();
					intentionStantard.setCompactIntention((CompactIntention) intentionDAO.getObject(CompactIntention.class, compactIntentionId));
					intentionStantard.setCCoststandard((CCoststandard) intentionDAO.getObject(CCoststandard.class, intentionform.getCostStandartId()[i]));
					if (intentionform.getRoomId()[i] != 0) {
						ERooms eRooms = (ERooms) intentionDAO.getObject(ERooms.class, intentionform.getRoomId()[i]);
						intentionStantard.setERooms(eRooms);
					}
					intentionStantard.setCompactClient((CompactClient) intentionDAO.getObject(CompactClient.class, clientId));
					intentionStantard.setCCosttype((CCosttype) intentionDAO.getObject(CCosttype.class, intentionform.getCostTypeId()[i]));
					intentionStantard.setBeginDate(intentionform.getBeginDateStand()[i]);
					intentionStantard.setEndDate(intentionform.getEndDateStand()[i]);
					intentionStantard.setAmount(intentionform.getAmount()[i]);
					intentionStantard.setStatus("1");//设为有效
					intentionStantard.setLpId(intentionform.getLpId());
					intentionDAO.saveObject(intentionStantard);
				}
			}

			CompactIntention intention = (CompactIntention) destineDAO.getObject(CompactIntention.class, compactIntentionId);
			// 保存房间客户表多条记录
			if (intentionform.getClientRoomId() != null && intentionform.getClientRoomId().length > 0) {
				for (int i = 0; i < intentionform.getClientRoomId().length; i++) {
					ERoomIntention roomIntention = new ERoomIntention();
					roomIntention.setClientId(clientId);
					roomIntention.setIntentionId(compactIntentionId);
					roomIntention.setRoomId(intentionform.getClientRoomId()[i]);
					roomIntention.setBeginDate(intention.getBeginDate());
					roomIntention.setEndDate(intention.getEndDate());
					roomIntention.setClientName(intention.getName());
					roomIntention.setUnitName(intention.getUnitName());
					roomIntention.setLpId(intentionform.getLpId());
					intentionDAO.saveObject(roomIntention);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}

	// 保存或更新客户信息
	public Integer saveOrUpdateCustomer(IntentionForm intentionform) throws BkmisServiceException {

		CompactClient client = new CompactClient();
		Integer clientId = intentionform.getCompactIntention().getClientId();
		if (clientId != null && clientId.intValue() == 0) {// 如果值为0，将之设为null
			clientId = null;
		}
		try {
			try {
				BeanUtils.copyProperties(client, intentionform.getCompactIntention());
				client.setId(clientId);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (clientId == null) {
				clientId = (Integer) destineDAO.saveObject(client);
			} else {
				destineDAO.updateObject(client);
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return clientId;
	}

	// 获得意向书详情
	@Override
	public void getIntentionDetails(IntentionForm form) throws BkmisServiceException {
		Integer id = null;
		id = Integer.parseInt(form.getId());
		try {
			CompactIntention compactIntention = (CompactIntention) intentionDAO.getObject(CompactIntention.class, id);
			CompactClient client = (CompactClient) intentionDAO.getObject(CompactClient.class, compactIntention.getClientId());
			// 获取房租信息
			List<IntentionRent> rents = intentionDAO.getRentList(id);
			// 获取意向书预租的房间
			List<ERoomIntention> roomIntention = (List<ERoomIntention>) intentionDAO.getRoomListByClientId(client.getId(), id);
			// 获得意向书房间信息
			List<ERooms> roomsList = new ArrayList<ERooms>();
			for (int i = 0; i < roomIntention.size(); i++) {
				ERooms eRooms = (ERooms) intentionDAO.getObject(ERooms.class, roomIntention.get(i).getRoomId());
				roomsList.add(eRooms);
			}
			// 获取收费标准
			List<IntentionRoomCoststandard> standardList = intentionDAO.getStandardListByIntentionId(compactIntention.getId());
			form.setCompactIntention(compactIntention);
			form.setCompactClient(client);
			form.setRoomList(roomsList);
			form.setRentList(rents);
			form.setStandardList(standardList);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}

	// 删除意向书
	@Override
	public void delCompactIntention(IntentionForm intentionForm, String userName) throws BkmisServiceException {
		try {
			String[] ids = intentionForm.getId().split(",");
			for (int count = 0; count < ids.length; count++) {
				int id = Integer.parseInt(ids[count]);
				//记录日志
				intentionDAO.logDetail(intentionForm.getUserName(), "删除", "意向书", Contants.L_CLIENT, "3", new CompactIntention(id));
				// 删除房租信息
				List<IntentionRent> rents = intentionDAO.getRentList(id);
				for (int i = 0; i < rents.size(); i++) {
					intentionDAO.deleteObject(rents.get(i));
				}

				// 删除收费标准信息
				List<IntentionRoomCoststandard> list = intentionDAO.getStandardListByIntentionId(id);
				for (int i = 0; i < list.size(); i++) {
					intentionDAO.deleteObject(list.get(i));
				}

				// 删除客户房间信息并改变房间的状态
				List<ERoomIntention> list1 = intentionDAO.getRoomListByClientId(null, id);
				for (int i = 0; i < list1.size(); i++) {
					ERooms eRooms = (ERooms) intentionDAO.getObject(ERooms.class, list1.get(i).getRoomId());
					eRooms.setStatus(Contants.OUTUSE);
					intentionDAO.updateObject(eRooms);
					intentionDAO.deleteObject(list1.get(i));
				}
				// 删除意向书
				intentionDAO.deleteObject(intentionDAO.getObject(CompactIntention.class, id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}

	// 审批意向书,（根据id取得意向书）
	@Override
	public void approvalIntention(IntentionForm intentionForm) throws BkmisServiceException {
		try {
			CompactIntention ci = (CompactIntention) intentionDAO.getObject(CompactIntention.class, Integer.parseInt(intentionForm.getId()));
			ci.setStatus(intentionForm.getCompactIntention().getStatus());
			//添加日志
			intentionDAO.log(intentionForm.getUserName(), "审批", "编号为："+ci.getIntentionCode()+"的意向书"+ci.getStatus(), Contants.L_CLIENT, "意向书");
			intentionDAO.updateObject(ci);
			if (Contants.THROUGHAPPROVAL.equals(intentionForm.getCompactIntention().getStatus())) {// 如果通过审批
				// 保存定金
				CEarnest cEarnest = new CEarnest();
				cEarnest.setClientId(ci.getClientId());
				CEarnest cEarnest2 = compactManagerDAO.getCEarnest(ci.getClientId());
				if (cEarnest2 == null) {
					intentionDAO.saveObject(cEarnest);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}

	// 根据意向书id判断该意向书是否已缴定金(如果缴纳的定金为0，则作为已缴来处理)
	@Override
	public boolean checkIsIsEarnest(String id) throws BkmisServiceException {
		CompactIntention ci = (CompactIntention) intentionDAO.getObject(CompactIntention.class, Integer.parseInt(id));
		boolean isIsEarnest = true;
		if ("0".equals(ci.getIsEarnest()) && ci.getEarnest() > 0) {
			isIsEarnest = false;
		}
		return isIsEarnest;
	}
}
