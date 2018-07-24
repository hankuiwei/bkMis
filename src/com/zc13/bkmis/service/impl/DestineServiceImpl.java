package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.dao.ICustomerRoomDAO;
import com.zc13.bkmis.dao.IDestineDAO;
import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.AnalysisClientComeGo;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.IDestineService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class DestineServiceImpl implements IDestineService {

	private  IDestineDAO destineDAOImpl;
	
	private ICompactManagerDAO iCompactManagerDAO;
	
	private ICustomerRoomDAO iCustomerRoomDAO;

	public ICompactManagerDAO getICompactManagerDAO() {
		return iCompactManagerDAO;
	}

	public void setICompactManagerDAO(ICompactManagerDAO compactManagerDAO) {
		iCompactManagerDAO = compactManagerDAO;
	}

	public ICustomerRoomDAO getICustomerRoomDAO() {
		return iCustomerRoomDAO;
	}

	public void setICustomerRoomDAO(ICustomerRoomDAO customerRoomDAO) {
		iCustomerRoomDAO = customerRoomDAO;
	}
	public IDestineDAO getDestineDAOImpl() {
		return destineDAOImpl;
	}

	public void setDestineDAOImpl(IDestineDAO destineDAOImpl) {
		this.destineDAOImpl = destineDAOImpl;
	}

	@Override
	public void getList(DestineForm destineForm) {
		// TODO Auto-generated method stub
		destineDAOImpl.getList(destineForm);
		
	}

	@Override
	public int getTotalCount(DestineForm destineForm) {
		// TODO Auto-generated method stub
		return destineDAOImpl.getCount(destineForm);
	}

	@Override
	/**
	 * 保存客户预入住信息
	 */
	public void saveRoomRent(DestineForm form)
			throws BkmisServiceException {
		
		destineDAOImpl.updateTask("collateNew", 1);
		//destineDAOImpl.updateTask(Contants.collateEarnest, 1);
		
		//定义客户id
		int compactClientId = 0;
		int compactId = 0;
		Integer lpIdInteger = form.getLpId();
		try {
			List<CompactClient> cList = destineDAOImpl.getClient(form.getCode());
			//检查form中带过来的客户是用户新增的还是自数据库中选取的
			if(cList==null||cList.size()<1){
				//保存客户
				compactClientId = saveCustomer(form);
				//保存合同
				form.setClientId(compactClientId);
				compactId = saveCompact(form);
			}else{
				//更新客户
				int id = cList.get(0).getId();
				compactClientId = id;
				CompactClient client = (CompactClient)destineDAOImpl.getObject(CompactClient.class, id);
				form.setId(id);
				try {
					try {
						BeanUtils.copyProperties(client, form);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					destineDAOImpl.updateObject(client);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
				
				//保存合同
				Compact compact = new Compact();
				try {
					try {
						BeanUtils.copyProperties(compact, form);
						compact.setClientId(id);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					compact.setCode(form.getCompactCode());
					compact.setStatus(Contants.NOTSUBMIT);
					compact.setIsDestine(Contants.DESTINES);
					destineDAOImpl.saveObject(compact);
					compactId = compact.getId();
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
			}
			//保存合同房租表
			if(form.getBeginDateCost()!=null&&form.getBeginDateCost().length>0){//
				for(int i=0;i<form.getBeginDateCost().length;i++){
					CompactRent rent = new CompactRent();
					rent.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					rent.setRent(form.getRent()[i]);
					rent.setBeginDate(form.getBeginDateCost()[i]);
					rent.setEndDate(form.getEndDateCost()[i]);
					rent.setLpId(lpIdInteger);
					destineDAOImpl.saveObject(rent);
				}
			}
			
			//将房间状态置为已预租。并且保存房租的收费标准
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){//
					ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class,form.getClientRoomId()[i]);
					eRooms.setStatus(Contants.DESTINE);
					destineDAOImpl.updateObject(eRooms);
					//添加房租收费标准，默认值
					CompactRoomCoststandard stCoststandard1 = new CompactRoomCoststandard();
					stCoststandard1.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					//获取默认的收费标准id，display值为none的那个
					List list = destineDAOImpl.findObject("from CCoststandard where display is not Null ");
					if(list.size()>0){
						CCoststandard coststandard = (CCoststandard)list.get(0);
						stCoststandard1.setCCoststandard(coststandard);
					}
					stCoststandard1.setERooms(eRooms);
					stCoststandard1.setCompactClient((CompactClient)destineDAOImpl.getObject(CompactClient.class, compactClientId));
					stCoststandard1.setBeginDate(form.getBeginDate());
					stCoststandard1.setDisplay("none");
					stCoststandard1.setAmount(1);
					stCoststandard1.setStatus("1");//设为有效
					stCoststandard1.setEndDate(form.getEndDate());
					stCoststandard1.setLpId(lpIdInteger);
					destineDAOImpl.saveObject(stCoststandard1);
					
				}
			}
			//保存合同对应收费标准表
			if(form.getCostStandartId()!=null&&form.getCostStandartId().length>0){
				for(int i=0;i<form.getCostStandartId().length;i++){
					
					CompactRoomCoststandard stCoststandard = new CompactRoomCoststandard();
					stCoststandard.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					stCoststandard.setCCoststandard((CCoststandard)destineDAOImpl.getObject(CCoststandard.class,form.getCostStandartId()[i]));
					if(form.getRoomId()[i]!=0){
						ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class,form.getRoomId()[i]);
						stCoststandard.setERooms(eRooms);
					}
					stCoststandard.setCompactClient((CompactClient)destineDAOImpl.getObject(CompactClient.class, compactClientId));
					stCoststandard.setCCosttype((CCosttype)destineDAOImpl.getObject(CCosttype.class,form.getCostTypeId()[i]));
					stCoststandard.setBeginDate(form.getBeginDateStand()[i]);
					stCoststandard.setEndDate(form.getEndDateStand()[i]);
					stCoststandard.setAmount(form.getAmount()[i]);
					stCoststandard.setStatus("1");
					stCoststandard.setLpId(lpIdInteger);
					//stCoststandard.setRebate(form.getRebate()[i]);
					destineDAOImpl.saveObject(stCoststandard);
				}
			}

			Compact compact = (Compact)destineDAOImpl.getObject(Compact.class,compactId);
			//保存房间客户表多条记录
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){
					ERoomClient roomClient = new ERoomClient();
					roomClient.setClientId(compactClientId);
					roomClient.setPactId(compactId);
					roomClient.setRoomId(form.getClientRoomId()[i]);
					roomClient.setBeginDate(compact.getBeginDate());
					roomClient.setEndDate(compact.getEndDate());
					roomClient.setClientName(form.getName());
					roomClient.setClientType(form.getClientType());
					roomClient.setLinkName(form.getLinkMan());
					roomClient.setPhone(form.getPhone());
					roomClient.setUnitName(form.getUnitName());
					roomClient.setLpId(lpIdInteger);
					destineDAOImpl.saveObject(roomClient);
				}
			}
			//更新合同对应的意向书的状态为已转为合同
			Integer intentionId = form.getIntentionId();
			if(intentionId!=null&&intentionId!=0){
				destineDAOImpl.update("update CompactIntention set isConvertCompact='"+Contants.CONVERT+"' where id="+intentionId);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 保存客户信息
	 * DestineServiceImpl.saveCustomer
	 */
	
	public Integer saveCustomer(DestineForm form) throws BkmisServiceException {
		
		CompactClient client = new CompactClient();
		try {
			try {
				BeanUtils.copyProperties(client, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			destineDAOImpl.saveObject(client);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return client.getId();
	}
	//保存合同
	@Override
	public Integer saveCompact(DestineForm form) throws BkmisServiceException {
		

		Compact compact = new Compact();
		try {
			try {
				BeanUtils.copyProperties(compact, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			compact.setCode(form.getCompactCode());
			compact.setClientId(form.getClientId());
			//保存审核状态
			compact.setStatus(Contants.NOTSUBMIT);
			//保存合同状态
			compact.setIsDestine(Contants.DESTINES);
			//保存入驻状态
			compact.setIsNotice(Contants.NOTNOTICE);
			destineDAOImpl.saveObject(compact);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return compact.getId();
	}

	@Override
	public void getCompactMassage(DestineForm form, String id)throws BkmisServiceException {

		int id1 = 0;
		
		id1 = Integer.parseInt(id);
		
		try {
			Compact compact = (Compact)destineDAOImpl.getObject(Compact.class,id1);
			CompactClient client = (CompactClient)destineDAOImpl.getObject(CompactClient.class,compact.getClientId());
			List<CompactRent> rents = destineDAOImpl.getRentList(id1);
			List<ERoomClient> roomClient = (List<ERoomClient>)destineDAOImpl.getRoomListByClientId(client.getId(), id1);
			List<ERooms> roomsList = new ArrayList<ERooms>();
			for(int i=0;i<roomClient.size();i++){
				ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class,roomClient.get(i).getRoomId());
				roomsList.add(eRooms);
			}
			List<CompactRoomCoststandard> standardList = destineDAOImpl.getStandardListByCompactId(compact.getId());
			form.setCompact(compact);
			form.setClient(client);
			form.setEroomList(roomsList);
			form.setRents(rents);
			form.setStandardList(standardList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 修改
	 * DestineServiceImpl.editCompact
	 */
	public void editCompact(DestineForm form,String compactId1) throws BkmisServiceException {

		int lpId = 0;
		try {
			lpId = form.getLpId();
			int compactId = Integer.parseInt(compactId1);
			//定义客户id
			int clientId = 0;
			List<CompactClient> clients = destineDAOImpl.getClient(form.getCode());
			/*if(clients==null||clients.size()<1){ //用户选择新生成了客户代码。注：目前已经不允许客户在编辑时选择生成新的客户代码
				saveCustomer(form);
				clientId = destineDAOImpl.getObjectId("CompactClient");
			}else{//更新客户*/
				clientId = clients.get(0).getId();
				CompactClient client = (CompactClient)destineDAOImpl.getObject(CompactClient.class, clientId);
				form.setId(clientId);
				try {
					try {
						BeanUtils.copyProperties(client, form);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					destineDAOImpl.updateObject(client);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
			//}
			
			//删除旧的房租信息
			List<CompactRent> rents = destineDAOImpl.getCompactRent(compactId);
			for(int i=0;i<rents.size();i++){
				destineDAOImpl.deleteObject(rents.get(i));
			}
			
			//删除旧的收费标准信息
			List<CompactRoomCoststandard> list = destineDAOImpl.getCompactRoomCoststandard(compactId);
			for(int i=0;i<list.size();i++){
				destineDAOImpl.deleteObject(list.get(i));
			}
			
			//删除旧的客户房间信息并改变房间的状态
			List<ERoomClient> list1 = destineDAOImpl.getERoomClient(clientId);
			for(int i=0;i<list1.size();i++){
				ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class, list1.get(i).getRoomId());
				eRooms.setStatus(Contants.OUTUSE);
				destineDAOImpl.updateObject(eRooms);
				destineDAOImpl.deleteObject(list1.get(i));
			}
			
			//保存合同
			Compact compact = (Compact)destineDAOImpl.getObject(Compact.class, compactId);
			form.setId(compactId);
			form.setClientId(clientId);
			form.setStatus(compact.getStatus());
			try {
				try {
					BeanUtils.copyProperties(compact, form);
					compact.setStatus(Contants.NOTSUBMIT);
					compact.setCode(form.getCompactCode());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				destineDAOImpl.updateObject(compact);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			
			//保存房租
			if(form.getBeginDateCost()!=null&&form.getBeginDateCost().length>0){
				for(int i=0;i<form.getBeginDateCost().length;i++){
					CompactRent rent = new CompactRent();
					rent.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					rent.setRent(form.getRent()[i]);
					rent.setBeginDate(form.getBeginDateCost()[i]);
					rent.setEndDate(form.getEndDateCost()[i]);
					rent.setLpId(lpId);
					destineDAOImpl.saveObject(rent);
				}
			}
			
			//将房间的状态设为已预租
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){
					ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class,form.getClientRoomId()[i]);
					eRooms.setStatus(Contants.DESTINE);
					destineDAOImpl.updateObject(eRooms);
					
					//添加房租收费标准，默认值
					CompactRoomCoststandard stCoststandard1 = new CompactRoomCoststandard();
					stCoststandard1.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					//获取默认的收费标准id，display值为none的那个
					List list11 = destineDAOImpl.findObject("from CCoststandard where display is not Null ");
					if(list11.size()>0){
						CCoststandard coststandard = (CCoststandard)list11.get(0);
						stCoststandard1.setCCoststandard(coststandard);
					}
					stCoststandard1.setERooms(eRooms);
					stCoststandard1.setCompactClient((CompactClient)destineDAOImpl.getObject(CompactClient.class, clientId));
					stCoststandard1.setBeginDate(form.getBeginDate());
					stCoststandard1.setDisplay("none");
					stCoststandard1.setAmount(1);
					stCoststandard1.setStatus("1");//设为有效
					stCoststandard1.setEndDate(form.getEndDate());
					stCoststandard1.setLpId(lpId);
					destineDAOImpl.saveObject(stCoststandard1);
				}
			}
			
			//保存收费标准信息
			if(form.getCostStandartId()!=null&&form.getCostStandartId().length>0){
				for(int i=0;i<form.getCostStandartId().length;i++){
					CompactRoomCoststandard stCoststandard = new CompactRoomCoststandard();
					stCoststandard.setCompact((Compact)destineDAOImpl.getObject(Compact.class, compactId));
					stCoststandard.setCCoststandard((CCoststandard)destineDAOImpl.getObject(CCoststandard.class,form.getCostStandartId()[i]));
					if(form.getRoomId()[i]!=0){
						ERooms eRooms = (ERooms)destineDAOImpl.getObject(ERooms.class,form.getRoomId()[i]);
						stCoststandard.setERooms(eRooms);
					}
					stCoststandard.setCompactClient((CompactClient)destineDAOImpl.getObject(CompactClient.class, clientId));
					stCoststandard.setCCosttype((CCosttype)destineDAOImpl.getObject(CCosttype.class,form.getCostTypeId()[i]));
					stCoststandard.setBeginDate(form.getBeginDateStand()[i]);
					stCoststandard.setEndDate(form.getEndDateStand()[i]);
					stCoststandard.setAmount(form.getAmount()[i]);
					stCoststandard.setStatus("1");//设为有效
					stCoststandard.setLpId(lpId);
					//stCoststandard.setRebate(form.getRebate()[i]);
					destineDAOImpl.saveObject(stCoststandard);
				}
			}
			Compact compact1 = (Compact)destineDAOImpl.getObject(Compact.class, compactId);
			//保存客户房间信息
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){
					ERoomClient roomClient = new ERoomClient();
					roomClient.setClientId(clientId);
					roomClient.setPactId(compactId);
					roomClient.setRoomId(form.getClientRoomId()[i]);
					roomClient.setBeginDate(compact1.getBeginDate());
					roomClient.setEndDate(compact1.getEndDate());
					roomClient.setClientName(form.getName());
					roomClient.setClientType(form.getClientType());
					roomClient.setLinkName(form.getLinkMan());
					roomClient.setPhone(form.getPhone());
					roomClient.setLpId(lpId);
					roomClient.setUnitName(form.getUnitName());
					destineDAOImpl.saveObject(roomClient);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 正式入住
	 * DestineServiceImpl.inCome
	 */
	@Override
	public void inCome(String compactId) {
		
		try{
			
			Compact compact = (Compact)destineDAOImpl.getObject(Compact.class,Integer.parseInt(compactId));
			
			compact.setIsDestine("常规");
			//保存入驻时间
			compact.setInDate(GlobalMethod.getTime());
			compact.setIsNotice(Contants.HAVEIN);
			destineDAOImpl.updateObject(compact);
			//更新房租修改status
			List<CompactRent> rents = iCustomerRoomDAO.getCompactRent(compact.getId());
			for(int i=0;i<rents.size();i++){
				rents.get(i).setStatus(Contants.VALID);
				iCustomerRoomDAO.updateObject(rents.get(i));
			}
			//更新房间客户表状态改status为1，更新房间表房间状态从已预租改为已出租
			List<ERoomClient> roomsClient = iCustomerRoomDAO.getERoomClientByPactId(compact.getId());
			for(int i=0;i<roomsClient.size();i++){
				roomsClient.get(i).setStatus(Contants.VALID);
				iCustomerRoomDAO.updateObject(roomsClient.get(i));
				//更新房间表房间状态从已预租改为已出租
				ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class, roomsClient.get(i).getRoomId());
				eRooms.setStatus(Contants.BELEASED);
				iCustomerRoomDAO.updateObject(eRooms);
			}
			//更新房间对应收费标准修改status
			List<CompactRoomCoststandard> standards = destineDAOImpl.getCompactRoomCoststandard(compact.getId());
			for(int i=0;i<standards.size();i++){
				standards.get(i).setStatus(Contants.VALID);
				iCustomerRoomDAO.updateObject(standards.get(i));
			}
			//保存客户在住时间表
			AnalysisClientComeGo comeGo = new AnalysisClientComeGo();
			CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,compact.getClientId());
			List<AnalysisClientComeGo> comeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(),compact.getLpId());
			if(comeGos!=null&&comeGos.size()>0){//客户有入住信息
				AnalysisClientComeGo comeGo2 = comeGos.get(0);
				String enddate = comeGo2.getGoDate();
				int flag = compact.getBeginDate().compareTo(enddate);
				if(flag<0){
					comeGo2.setGoDate(compact.getEndDate());
					iCustomerRoomDAO.updateObject(comeGo2);
				}else if(flag>0){
					comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
					comeGo.setCompactClient(client);
					comeGo.setComeDate(compact.getBeginDate());
					comeGo.setGoDate(compact.getEndDate());
					iCustomerRoomDAO.saveObject(comeGo);
				}
			}else{//没有在住时间信息的客户
				comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
				comeGo.setCompactClient(client);
				comeGo.setComeDate(compact.getBeginDate());
				comeGo.setGoDate(compact.getEndDate());
				iCustomerRoomDAO.saveObject(comeGo);
			}
		
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}


	//导出报表用
	public List<Compact> explorDestineList(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate,String isEarnest)
			throws BkmisServiceException {
		
		List<Compact> list = destineDAOImpl.explorDestineList(lpId,clientName,roomCode,status,beginDate,endDate,isEarnest); 
		return list;
	}
	
}
