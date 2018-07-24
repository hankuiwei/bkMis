package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.dao.ICustomerRoomDAO;
import com.zc13.bkmis.form.CompactRoomForm;
import com.zc13.bkmis.mapping.AnalysisClientComeGo;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactQuitBill;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.CompactTask;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.ICalculateMoneyService;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.SpringContextHolder;
/**
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：5:15:20 PM
 */
public class CustomerRoomServiceImpl extends BasicServiceImpl implements ICustomerRoomService {

	private ICustomerRoomDAO iCustomerRoomDAO;
	private ICompactManagerDAO iCompactManagerDAO;
	
	public ICustomerRoomDAO getICustomerRoomDAO() {
		return iCustomerRoomDAO;
	}
	
	public ICompactManagerDAO getICompactManagerDAO() {
		return iCompactManagerDAO;
	}

	public void setICompactManagerDAO(ICompactManagerDAO compactManagerDAO) {
		iCompactManagerDAO = compactManagerDAO;
	}

	public void setICustomerRoomDAO(ICustomerRoomDAO customerRoomDAO) {
		iCustomerRoomDAO = customerRoomDAO;
	}
	
	//查询合同列表
	@Override
	public List<Object[]> getCompactList(String key,String currentpage,String code,String pagesize,String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws BkmisServiceException {
		
		List<Object[]> compactRentList = new ArrayList<Object[]>();
		List<Compact> list = iCustomerRoomDAO.getCompactList(key,currentpage,code,pagesize,clientName,roomCode,beginDate,endDate,lpId,isNotice);
		boolean cRent = false;
		for(int i=0;i<list.size();i++){
			List<CompactRent> rents =  iCustomerRoomDAO.getRentList(list.get(i).getId());
			double s = 0;
			//取当前的房租情况
			for(int j=0;j<rents.size();j++){
				String date = GlobalMethod.getTime();
				cRent = date.compareTo(rents.get(j).getBeginDate())>0&&date.compareTo(rents.get(j).getEndDate())<0;
				if(cRent){
					s = rents.get(j).getRent();
					break;
				}
			}
			Object[] objs = new Object[]{list.get(i),s};
			compactRentList.add(objs);
		}
		return compactRentList;
	}

	//根据合同id得到合同
	@Override
	public Compact getCompactById(String id) throws BkmisServiceException {
		
		return (Compact)iCustomerRoomDAO.getObject(Compact.class, Integer.parseInt(id));
	}

	//根据合同找到客户
	@Override
	public CompactClient getCompactClientById(Compact compact) throws BkmisServiceException {
		
		return (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,compact.getClientId());
	}

	//得到合同总数
	@Override
	public int getCount(String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws BkmisServiceException {
		
		List<Compact> list = iCustomerRoomDAO.getCount(clientName,roomCode,beginDate,endDate,lpId,isNotice);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}

	//生成合同编号
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String getCompactCode(Integer lpId) throws BkmisServiceException {
		
		List<Compact> list = (List<Compact>)iCustomerRoomDAO.findObject("from Compact where lpId = "+lpId);;
		String year = GlobalMethod.getTime().substring(0,4);
		String code = iCustomerRoomDAO.getCompactCode(lpId);
		if(list!=null&&list.size()>0){
			String[] strings = code.split("-");
			if(Integer.parseInt(year)>Integer.parseInt(strings[0])){//如果当前年份大于已存在的最大年份，那么重新从1开始编号
				return  year + "-" + "001";
			}else{
				code = String.valueOf(Integer.parseInt(strings[1]) + 1);
				if(code.length()==1){
					code = "00" + code;
				}else if(code.length()==2){
					code = "0" + code;
				}
				return year + "-"+ code;
			}
		}else{
			return year + "-" + "001";
		}
		
	}
	
	
	//得到客户编号
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String getCustomerCode(Integer lpId) throws BkmisServiceException {
		
		List<CompactClient> list = (List<CompactClient>)iCustomerRoomDAO.findObject("from CompactClient where lpId = "+lpId);
		String year = GlobalMethod.getTime().substring(0,4);
		String code = iCustomerRoomDAO.getCustomerCode(lpId);
		if(list!=null&&list.size()>0){
			String s = code.substring(code.length()-4,code.length());
			String oyear = code.substring(0,4);
			if(Integer.parseInt(year)>Integer.parseInt(oyear)){//如果当前年份大于已存在的最大年份，那么重新从1开始编号
				return  year + "0001";
			}else{
				code = String.valueOf(Integer.parseInt(s) + 1);
				if(code.length()==1){
					code = "000" + code;
				}else if(code.length()==2){
					code = "00" + code;
				}else if(code.length()==3){
					code = "0" + code;
				}
				return year + code;
			}
		}else{
			return year + "0001";
		}
	}
	
	//得到退租编号
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String getQuitCode() throws BkmisServiceException {

		List<CompactQuit> list = (List<CompactQuit>)iCustomerRoomDAO.getObjects(CompactQuit.class);
		String year = GlobalMethod.getTime().substring(0,4);
		String code = iCustomerRoomDAO.getQuitCode();
		if(list!=null&&list.size()>0){
			String[] strings = code.split("-");
			if(Integer.parseInt(year)>Integer.parseInt(strings[0])){//如果当前年份大于已存在的最大年份，那么重新从1开始编号
				return  year + "-" + "001";
			}else{
				code = String.valueOf(Integer.parseInt(strings[1]) + 1);
				if(code.length()==1){
					code = "00" + code;
				}else if(code.length()==2){
					code = "0" + code;
				}
				return year + "-"+ code;
			}
		}else{
			return year + "-" + "001";
		}
	}

	

	//得到费用类型列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CCosttype> getCostType() throws BkmisServiceException {
		
		return (List<CCosttype>)iCustomerRoomDAO.getObjects(CCosttype.class);
	}

	//根据楼盘id，费用类型得到收费标准列表
	@Override
	public String costStandList(String lpId, String costType,String str,String lpIds,String type) throws BkmisServiceException {
		
		String string1 = ""; 
		if(!"".equals(lpId)){
			CAccounttemplate bean = iCustomerRoomDAO.getAccounttemplateId(Integer.parseInt(lpId));
			List<CCoststandard> coststandards = null;
			if(bean!=null){
				coststandards = iCustomerRoomDAO.getAccounttemplate(bean.getId(),Integer.parseInt(costType),type);
			}
			if(coststandards!=null&&coststandards.size()>0){
				for(int i=0;i<coststandards.size();i++){
					string1 += coststandards.get(i).getStandardName() + "," + coststandards.get(i).getId() + ";";
				}
				return string1.substring(0,string1.length()-1);
			}else{
				return string1;
			}
		}else{
			String[] ids = lpIds.split(",");
			List<CCoststandard> list = new ArrayList<CCoststandard>();
			for(int i=0;i<ids.length;i++){
				CAccounttemplate template = iCustomerRoomDAO.getAccounttemplateId(Integer.parseInt(ids[i]));
				if(template==null)
					break;
				List<CCoststandard> coststandards = iCustomerRoomDAO.getAccounttemplate(template.getId(),Integer.parseInt(costType),type);
				for(int j=0;j<coststandards.size();j++){
					list.add(coststandards.get(j));
				}
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					CAccounttemplate cAccounttemplate = (CAccounttemplate)iCustomerRoomDAO.getObject(CAccounttemplate.class,list.get(i).getAccountTemplateId());
					ELp eLp = (ELp)iCustomerRoomDAO.getObject(ELp.class,cAccounttemplate.getLpid());
					if(Boolean.parseBoolean(str)){
						string1 += list.get(i).getStandardName() + "," + list.get(i).getId() + ";";
					}else{
						string1 += eLp.getLpName()+list.get(i).getStandardName() + "," + list.get(i).getId() + ";";
					}
				}
				return string1.substring(0,string1.length()-1);
			}else{
				return string1;
			}
		}
	}

	//根据id得到room
	@Override
	public ERooms getRoomById(String roomId) throws BkmisServiceException {
		
		return (ERooms)iCustomerRoomDAO.getObject(ERooms.class,Integer.parseInt(roomId));
	}

	// 根据房间名称和费用类型名称和收费标准名称
	@Override
	public String getNames(String roomId, String costtype, String costStand)
			throws BkmisServiceException {
		
		ERooms rooms = null;
		if(!"".equals(roomId)){
			rooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,Integer.parseInt(roomId));
		}
		String roomCode = "";
		if(rooms!=null){
			roomCode = rooms.getRoomCode();
		}
		CCosttype costtype2 = (CCosttype)iCustomerRoomDAO.getObject(CCosttype.class,Integer.parseInt(costtype));
		CCoststandard coststandard = (CCoststandard)iCustomerRoomDAO.getObject(CCoststandard.class,Integer.parseInt(costStand));
		return roomCode+","+costtype2.getCostTypeName()+","+coststandard.getStandardName();
	}

	//保存合同
	@Override
	public void saveCompact(CompactRoomForm form,String flag) throws BkmisServiceException {
		
		String code = iCustomerRoomDAO.getCustomerCode(form.getLpId());
		int id = iCustomerRoomDAO.getClient(code).get(0).getId();
		Compact compact = new Compact();
		compact.setIsDestine(Contants.DESTINES);
		try {
			try {
				BeanUtils.copyProperties(compact, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			compact.setCode(form.getCompactCode());
			compact.setClientId(id);
			if(!flag.isEmpty()){//flag不为空，说明这个是保存的续租或者变更合同，下面两项就要放空
				compact.setIsDestine("");
				compact.setIsNotice("");
			}else{
				//保存合同状态和入驻状态
				compact.setIsDestine(Contants.DESTINES);
				compact.setIsNotice(Contants.NOTNOTICE);
			}
			//保存合同审核状态
			compact.setStatus(Contants.NOTSUBMIT);
			iCustomerRoomDAO.saveObject(compact);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	//保存客户
	@Override
	public Integer saveCustomer(CompactRoomForm form) throws BkmisServiceException {
		
		CompactClient client = new CompactClient();
		try {
			try {
				BeanUtils.copyProperties(client, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			iCustomerRoomDAO.saveObject(client);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return client.getId();
		
	}
	
	//保存房间与租金
	@Override
	public void saveRoomRent(CompactRoomForm form,String flag) throws BkmisServiceException {
		
		//也不知道他怎么写的，变更和续租都用这个方法，这怎么可以！没时间全改了，判断当新增的时候才调用执行增加任务
		if(!"relet".equals(flag) && !"change".equals(flag)){
			iCustomerRoomDAO.updateTask("collateNew", 1);
		}
		//定义客户id
		int compactClientId = 0;
		try {
			List<CompactClient> cList = iCustomerRoomDAO.getClient(form.getCode());
			if(cList==null||cList.size()<1){
				//保存客户
				saveCustomer(form);
				//保存合同
				saveCompact(form,flag);
				compactClientId = iCustomerRoomDAO.getObjectId("CompactClient");
			}else{
				//更新客户
				int id = cList.get(0).getId();
				compactClientId = id;
				CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, id);
				form.setId(id);
				try {
					try {
						BeanUtils.copyProperties(client, form);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					iCustomerRoomDAO.updateObject(client);
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
					iCustomerRoomDAO.saveObject(compact);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
			}
			int compactId = iCustomerRoomDAO.getObjectId("Compact");
			//保存合同房租表
			if(form.getBeginDateCost()!=null&&form.getBeginDateCost().length>0){//
				for(int i=0;i<form.getBeginDateCost().length;i++){
					CompactRent rent = new CompactRent();
					rent.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					rent.setRent(form.getRent()[i]);
					rent.setBeginDate(form.getBeginDateCost()[i]);
					rent.setEndDate(form.getEndDateCost()[i]);
					iCustomerRoomDAO.saveObject(rent);
				}
			}
			
			//将房间状态置为已出租
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){//
					ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,form.getClientRoomId()[i]);
					eRooms.setStatus(Contants.BELEASED);
					iCustomerRoomDAO.updateObject(eRooms);
					//添加房租收费标准，默认值
					CompactRoomCoststandard stCoststandard1 = new CompactRoomCoststandard();
					stCoststandard1.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					//获取默认的收费标准id，display值为none的那个
					List list = iCustomerRoomDAO.findObject("from CCoststandard where display is not Null ");
					if(list.size()>0){
						CCoststandard coststandard = (CCoststandard)list.get(0);
						stCoststandard1.setCCoststandard(coststandard);
					}
					stCoststandard1.setERooms(eRooms);
					stCoststandard1.setCompactClient((CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, compactClientId));
					stCoststandard1.setBeginDate(form.getBeginDate());
					stCoststandard1.setDisplay("none");
					stCoststandard1.setAmount(1);
					stCoststandard1.setStatus("1");//设为有效
					stCoststandard1.setEndDate(form.getEndDate());
					iCustomerRoomDAO.saveObject(stCoststandard1);
					
				}
			}
			//保存合同对应收费标准表
			if(form.getCostStandartId()!=null&&form.getCostStandartId().length>0){
				for(int i=0;i<form.getCostStandartId().length;i++){
					
					CompactRoomCoststandard stCoststandard = new CompactRoomCoststandard();
					stCoststandard.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					stCoststandard.setCCoststandard((CCoststandard)iCustomerRoomDAO.getObject(CCoststandard.class,form.getCostStandartId()[i]));
					if(form.getRoomId()[i]!=0){
						ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,form.getRoomId()[i]);
						stCoststandard.setERooms(eRooms);
					}
					stCoststandard.setCompactClient((CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, compactClientId));
					stCoststandard.setCCosttype((CCosttype)iCustomerRoomDAO.getObject(CCosttype.class,form.getCostTypeId()[i]));
					stCoststandard.setBeginDate(form.getBeginDateStand()[i]);
					stCoststandard.setEndDate(form.getEndDateStand()[i]);
					stCoststandard.setAmount(form.getAmount()[i]);
					stCoststandard.setStatus("1");//设为有效
					//stCoststandard.setRebate(form.getRebate()[i]);
					iCustomerRoomDAO.saveObject(stCoststandard);
				}
			}
			//保存客户在住时间表
//			AnalysisClientComeGo comeGo = new AnalysisClientComeGo();
			Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,compactId);
//			CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(Compact.class,compact.getClientId());
//			List<AnalysisClientComeGo> comeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(),form.getLpId());
//			AnalysisClientComeGo comeGo2 = comeGos.get(0);
//			String enddate = comeGo2.getGoDate();
//			int flag = form.getBeginDate().compareTo(enddate);
//			if(flag>0){
//				comeGo2.setGoDate(form.getEndDate());
//				iCustomerRoomDAO.updateObject(comeGo2);
//			}else if(flag<0){
//				comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,form.getLpId()));
//				comeGo.setCompactClient(client);
//				comeGo.setComeDate(form.getBeginDate());
//				comeGo.setGoDate(form.getEndDate());
//				iCustomerRoomDAO.saveObject(comeGo);
//			}
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
					iCustomerRoomDAO.saveObject(roomClient);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}

	//根据合同号得到合同信息
	@Override
	public void getCompactMassage(CompactRoomForm form, String id,String flag)
			throws BkmisServiceException {
		
		int id1 = 0;
		if("lookChange".equals(flag)){//得到变更合同的信息
			CompactChange change = (CompactChange)iCustomerRoomDAO.getObject(CompactChange.class, Integer.parseInt(id));
			id1 = change.getCompactByNewId().getId();
		}else if("lookRelet".equals(flag)){//得到续租合同的信息
			CompactRelet relet = (CompactRelet)iCustomerRoomDAO.getObject(CompactRelet.class, Integer.parseInt(id));
			id1 = relet.getCompactByNewId().getId();
		}else if("lookCheck".equals(flag)){//得到迁出合同的信息
			CompactQuit quit = (CompactQuit)iCustomerRoomDAO.getObject(CompactQuit.class, Integer.parseInt(id));
			id1 = quit.getCompact().getId();
		}
		else{
			id1 = Integer.parseInt(id);
		}
		try {
			//得到合同信息
			Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,id1);
			//得到客户信息
			CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,compact.getClientId());
			//根据合同id得到租金列表
			List<CompactRent> rents = iCustomerRoomDAO.getRentList(id1);
			//根据客户id和合同id查询由该合同出租的房间
			List<ERoomClient> roomClient = (List<ERoomClient>)iCustomerRoomDAO.getRoomListByClientId(client.getId(), id1);
			List<ERooms> roomsList = new ArrayList<ERooms>();
			for(int i=0;i<roomClient.size();i++){
				ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,roomClient.get(i).getRoomId());
				roomsList.add(eRooms);
			}
			//获得该合同下签订的收费标准
			List<CompactRoomCoststandard> standardList = iCustomerRoomDAO.getStandardListByCompactId(compact.getId());
			
			form.setCode2(getTheCode("00001",Contants.RWD));//生成任务单编号
			form.setCompact(compact);
			form.setClient(client);
			form.setEroomList(roomsList);
			form.setRents(rents);
			form.setStandardList(standardList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	//编辑房间与租金
	@Override
	public void editCompact(CompactRoomForm form,String compactId1,String clientId1) throws BkmisServiceException {
		
		try {
			
			int compactId = Integer.parseInt(compactId1);
			int lpId = 0;
			lpId = form.getLpId();
			//定义客户id
			int clientId = 0;
			List<CompactClient> clients = iCustomerRoomDAO.getClient(form.getCode());
			if(clients==null||clients.size()<1){ //用户选择新生成了客户代码
				clientId = saveCustomer(form);
			}else{//更新客户
				clientId = clients.get(0).getId();
				CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, clientId);
				form.setId(clientId);
				try {
					try {
						BeanUtils.copyProperties(client, form);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					iCustomerRoomDAO.updateObject(client);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
			}
			
			//删除旧的房租信息
			List<CompactRent> rents = iCustomerRoomDAO.getCompactRent(compactId);
			for(int i=0;i<rents.size();i++){
				iCustomerRoomDAO.deleteObject(rents.get(i));
			}
			
			//删除旧的收费标准信息
			List<CompactRoomCoststandard> list = iCustomerRoomDAO.getCompactRoomCoststandard(compactId);
			for(int i=0;i<list.size();i++){
				iCustomerRoomDAO.deleteObject(list.get(i));
			}
			
			//删除旧的客户房间信息并改变房间的额状态
			List<ERoomClient> list1 = iCustomerRoomDAO.getERoomClient(clientId);
			for(int i=0;i<list1.size();i++){
				ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class, list1.get(i).getRoomId());
				eRooms.setStatus(Contants.OUTUSE);
				iCustomerRoomDAO.updateObject(eRooms);
				iCustomerRoomDAO.deleteObject(list1.get(i));
			}
			
			//保存合同
			Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, compactId);
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
				iCustomerRoomDAO.updateObject(compact);
				//更新续租表中的对应的记录的审批状态(status)为未提交审批
				iCustomerRoomDAO.update("update CompactRelet t set t.status='"+Contants.NOTSUBMIT+"' where t.compactByNewId.id="+compact.getId());
				//更新变更表中的对应的记录的审批状态(status)为未提交审批
				iCustomerRoomDAO.update("update CompactChange t set t.status='"+Contants.NOTSUBMIT+"' where t.compactByNewId.id="+compact.getId());
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			
			//保存收费标准
			if(form.getBeginDateCost()!=null&&form.getBeginDateCost().length>0){
				for(int i=0;i<form.getBeginDateCost().length;i++){
					CompactRent rent = new CompactRent();
					rent.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					rent.setRent(form.getRent()[i]);
					rent.setBeginDate(form.getBeginDateCost()[i]);
					rent.setEndDate(form.getEndDateCost()[i]);
					rent.setLpId(lpId);
					iCustomerRoomDAO.saveObject(rent);
				}
			}
			//将房间状态置为已预租。并且保存房租的收费标准
			if(form.getClientRoomId()!=null&&form.getClientRoomId().length>0){
				for(int i=0;i<form.getClientRoomId().length;i++){//
					ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,form.getClientRoomId()[i]);
					eRooms.setStatus(Contants.DESTINE);
					iCustomerRoomDAO.updateObject(eRooms);
					//添加房租收费标准，默认值
					CompactRoomCoststandard stCoststandard1 = new CompactRoomCoststandard();
					stCoststandard1.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					//获取默认的收费标准id，display值为none的那个
					List list2 = iCustomerRoomDAO.findObject("from CCoststandard where display is not Null ");
					if(list2.size()>0){
						CCoststandard coststandard = (CCoststandard)list2.get(0);
						stCoststandard1.setCCoststandard(coststandard);
					}
					stCoststandard1.setERooms(eRooms);
					stCoststandard1.setCompactClient((CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, clientId));
					stCoststandard1.setBeginDate(form.getBeginDate());
					stCoststandard1.setDisplay("none");
					stCoststandard1.setAmount(1);
					stCoststandard1.setStatus("1");//设为有效
					stCoststandard1.setEndDate(form.getEndDate());
					stCoststandard1.setLpId(lpId);
					iCustomerRoomDAO.saveObject(stCoststandard1);
					
				}
			}
			
			//保存收费标准信息
			if(form.getCostStandartId()!=null&&form.getCostStandartId().length>0){
				for(int i=0;i<form.getCostStandartId().length;i++){
					CompactRoomCoststandard stCoststandard = new CompactRoomCoststandard();
					stCoststandard.setCompact((Compact)iCustomerRoomDAO.getObject(Compact.class, compactId));
					stCoststandard.setCCoststandard((CCoststandard)iCustomerRoomDAO.getObject(CCoststandard.class,form.getCostStandartId()[i]));
					if(form.getRoomId()[i]!=0){
						ERooms eRooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class,form.getRoomId()[i]);
						stCoststandard.setERooms(eRooms);
					}
					stCoststandard.setCompactClient((CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, clientId));
					stCoststandard.setCCosttype((CCosttype)iCustomerRoomDAO.getObject(CCosttype.class,form.getCostTypeId()[i]));
					stCoststandard.setBeginDate(form.getBeginDateStand()[i]);
					stCoststandard.setEndDate(form.getEndDateStand()[i]);
					stCoststandard.setAmount(form.getAmount()[i]);
					stCoststandard.setStatus("1");//设为有效
					stCoststandard.setLpId(lpId);
					//stCoststandard.setRebate(form.getRebate()[i]);
					iCustomerRoomDAO.saveObject(stCoststandard);
				}
			}
			Compact compact1 = (Compact)iCustomerRoomDAO.getObject(Compact.class, compactId);
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
					iCustomerRoomDAO.saveObject(roomClient);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	//得到客户列表条数
	@Override
	public int getClientCount(CompactRoomForm compactRoomForm)
			throws BkmisServiceException {

		List<CompactClient> list = iCustomerRoomDAO.getClientCount(compactRoomForm);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}

	//得到客户列表
	@Override
	public List<CompactClient> getClientList(String currentpage,
			String pagesize,CompactRoomForm customForm )
			throws BkmisServiceException {
		
		List<CompactClient> list = iCustomerRoomDAO.getClientList(currentpage, pagesize, customForm);
		return list;
	}

	//根据客户id删除客户
	@Override
	public void delClientById(String ids) throws BkmisServiceException {
		
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, Integer.parseInt(id[i]));
			iCustomerRoomDAO.deleteObject(client);
		}
	}

	//检查客户是否有对应的合同
	@Override
	public String checkClientById(String ids) throws BkmisServiceException {
	
		String[] id = ids.split(",");
		String string = "";
		for(int i=0;i<id.length;i++){
			List<Compact> list = iCustomerRoomDAO.getCompactByClientId(Integer.parseInt(id[i]));
			if(list!=null&&list.size()>0){
				//判断合同的有效性
				for(int c = 0;c<list.size();c++){
					if(!Contants.HASOVER.equals(list.get(c).getIsDestine())){//如果合同没有过期
						string += id[i] + ",";
						break;
					}
				}
				
			}
		}
		if(!"".equals(string)){
			return string.substring(0,string.length()-1);
		}else{
			return "";
		}
	}

	//根据客户id得到客户
	@Override
	public CompactClient getClientById(String id) throws BkmisServiceException {
	
		return (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class, Integer.parseInt(id));
	}

	//编辑客户
	@Override
	public void editClientById(CompactRoomForm form) throws BkmisServiceException {
		
		CompactClient client = new CompactClient();
		try {
			try {
				BeanUtils.copyProperties(client, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//写入系统日志
			try {
				iCustomerRoomDAO.logDetail(form.getUserName(), Contants.MODIFY,"客户档案信息",Contants.L_CLIENT, "1", client);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iCustomerRoomDAO.updateObject(client);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	//得到本楼盘全部客户列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactClient> getAllClientList(Integer lpId) throws BkmisServiceException {
		
		String hql="from CompactClient";
		if(lpId!=null && lpId!=0){
			hql += " where lpId = "+lpId;
		}
		
		return (List<CompactClient>)iCustomerRoomDAO.findObject(hql);
	}

	//根据客户id得到客户的信息
	@Override
	public String getClientString(String id) throws BkmisServiceException {
		
		CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,Integer.parseInt(id));
		String clientString = client.getId()+ "," + client.getName()+ "," + client.getUnitName()+ "," + client.getLinkMan()+ "," + client.getPhone()+ "," + 
							client.getClientType()+ "," + client.getCountry()+ "," + client.getNation()+ "," + client.getResidence()+ "," + client.getIdentityCard()+ "," +
							client.getCompanyType()+ "," + client.getFax()+ "," + client.getTrade()+ "," + client.getFundType()+ "," + client.getOperation()+ "," + 
							client.getCorporation()+ "," + client.getTaxNo() + "," + client.getCode() + "," + client.getLoginFund() + "," + client.getLoginDate() + "," + client.getIsHighTech();
		
		return clientString;
	}

	//得到楼盘列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ELp> getElp() throws BkmisServiceException {

		return (List<ELp>)iCustomerRoomDAO.getObjects(ELp.class);
	}

	//得到全部参数列表
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,List<SysCode>> getSysCode(Integer lpId) throws BkmisServiceException {
		
		Map<String,List<SysCode>> map = new HashMap<String, List<SysCode>>();
		List<SysCode> list1 = iCustomerRoomDAO.getSysCode("enterpriseType",lpId);//企业类型
		List<SysCode> list2 = iCustomerRoomDAO.getSysCode("tradeType",lpId);//行业类型
		List<SysCode> list3 = iCustomerRoomDAO.getSysCode("fundType",lpId);//资金类型
		map.put("enterpriseType", list1);
		map.put("tradeType", list2);
		map.put("fundType", list3);
		return map;
	}

	//保存续租合同
	@Override
	public void saveCompactRelet(CompactRoomForm form) throws BkmisServiceException {

		CompactRelet relet = new CompactRelet();
		relet.setApplyDate(form.getApplyDate());
		relet.setTheMan(form.getMan());
		relet.setBz(form.getBz());
		int id = iCustomerRoomDAO.getLastCompact();
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,id);
		relet.setCompactByNewId(compact);
		Compact compact1 = (Compact)iCustomerRoomDAO.getObject(Compact.class, form.getCompactId());
		//将老合同的入驻状态改为已申请续租
		compact1.setIsNotice(Contants.HASAPPLYRELET);
		relet.setNewCode(form.getCompactCode());
		relet.setCompactByOldId(compact1);
		relet.setOldCode(form.getCompactCodeOld());
		relet.setClientName(form.getName());
		relet.setStatus(Contants.NOTSUBMIT);
		relet.setLpId(form.getLpId());
		iCustomerRoomDAO.updateTask("collateRelet", 1);
		iCustomerRoomDAO.saveObject(relet);
	}
	
	//保存变更合同
	@Override
	public void saveCompactChange(CompactRoomForm form) throws BkmisServiceException {
		
		CompactChange change = new CompactChange();
		change.setApplyDate(form.getDate());
		change.setTheMan(form.getMan());
		change.setBz(form.getBz());
		Compact compact1 = (Compact)iCustomerRoomDAO.getObject(Compact.class,form.getCompactId());
		//将老合同的入驻状态改为已申请变更
		compact1.setIsNotice(Contants.HASAPPLYCHANGE);
		
		change.setCompactByOldId(compact1);
		int id = iCustomerRoomDAO.getLastCompact();
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,id);
		change.setCompactByNewId(compact);
		change.setNewCode(form.getCompactCode());
		change.setOldCode(form.getCompactCodeOld());
		change.setClientName(form.getName());
		change.setStatus(Contants.NOTSUBMIT);
		change.setLpId(form.getLpId());
		iCustomerRoomDAO.updateTask("collateChage", 1);
		iCustomerRoomDAO.saveObject(compact1);
		iCustomerRoomDAO.saveObject(change);
		
	}

	@Override
	public List<ERooms> getRoomByLp(String id, String tablename)
			throws BkmisServiceException {

		 List<ERooms> list = null;
		   if("e_lp".equals(tablename.trim())){
			  list = iCustomerRoomDAO.queryRoomforLp(Integer.parseInt(id),tablename);
		   }else{
			  list = iCustomerRoomDAO.queryRoomByBuild(Integer.parseInt(id),tablename);
		   }
		return list;
	}

	//提交审批
	@Override
	public String applyCompact(String compactId,String id,String flag,String userName) throws BkmisServiceException {

		int compactId1 = Integer.parseInt(compactId);
		Compact compact = null;//合同
		CompactIntention intention = null;//意向书
		if("intention".equals(flag)){
			intention = (CompactIntention)iCustomerRoomDAO.getObject(CompactIntention.class, compactId1);
		}else{
			compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, compactId1);
		}
		
		String codeString = "";
		if(compact!=null){
			codeString = compact.getCode();
		}
		if("intention".equals(flag)){//意向书提交审批
			intention.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateObject(intention);
			return "";
		}else if("compact".equals(flag)||flag==null){//租赁合同提交审批
			compact.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateTask("collateNew", -1);
			iCustomerRoomDAO.updateTask("approveNew", 1);
			iCustomerRoomDAO.updateObject(compact);
			iCustomerRoomDAO.log(userName, Contants.RELADAT, "将编号为"+codeString+"的新合同提交审批", Contants.COMPACTCHANGE,"合同校验");
			return "customer.do?method=getList";
		}else if("change".equals(flag)){//变更合同提交审批
			compact.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateObject(compact);
			int id1 = Integer.parseInt(id);
			CompactChange change = (CompactChange)iCustomerRoomDAO.getObject(CompactChange.class, id1);
			change.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateObject(change);
			iCustomerRoomDAO.updateTask("collateChage", -1);
			iCustomerRoomDAO.updateTask("approveChange", 1);
			iCustomerRoomDAO.log(userName, Contants.RELADAT, "将编号为"+codeString+"的变更合同提交审批", Contants.COMPACTCHANGE,"合同校验");
			return "compact.do?method=getCompactChangeList";
		}else if("relet".equals(flag)){//续租合同提交审批
			compact.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateObject(compact);
			int id1 = Integer.parseInt(id);
			CompactRelet relet = (CompactRelet)iCustomerRoomDAO.getObject(CompactRelet.class, id1);
			relet.setStatus(Contants.ONAPPROVAL);
			iCustomerRoomDAO.updateObject(relet);
			iCustomerRoomDAO.updateTask("collateRelet", -1);
			iCustomerRoomDAO.updateTask("approveRelet", 1);
			iCustomerRoomDAO.log(userName, Contants.RELADAT, "将编号为"+codeString+"的续租合同提交审批", Contants.COMPACTCHANGE,"合同校验");
			return "compact.do?method=getContractList";
		}else{
			return "";
		}
	}

	@Override
	public String checkCompactStatus(String id) throws BkmisServiceException {

		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, Integer.parseInt(id));
		return compact.getStatus();
	}

	//删除合同
	@Override
	public void delCompact(String id,String userName) throws BkmisServiceException {

		List<ERoomClient> rooms = iCustomerRoomDAO.getERoomClientByPactId(Integer.parseInt(id));
		for(int i=0;i<rooms.size();i++){
			int id1 = rooms.get(i).getRoomId();
			ERooms room = (ERooms)iCustomerRoomDAO.getObject(ERooms.class, id1);
			room.setStatus(Contants.OUTUSE);
		}
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, Integer.parseInt(id));
		//compact.setType(Contants.BEENDELETE);
		compact.setIsDestine(Contants.HASOVER);
		compact.setStatus("");
		try {//添加系统日志
			iCompactManagerDAO.logDetail2(userName, Contants.DELETE, "合同",Contants.L_COMPACTMANAGE, "3", compact);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Contants.COMPACT.equals(compact.getType())){
			//不能将合同直接删除，只能是置为作废
			//iCustomerRoomDAO.deleteObject(compact);
			iCustomerRoomDAO.updateObject(compact);
		}else if(Contants.COMPACTCHANGE.equals(compact.getType())) {
			CompactChange change = iCustomerRoomDAO.getChangeCompactByCId(compact.getId());
			iCustomerRoomDAO.deleteObject(change);
			//iCustomerRoomDAO.deleteObject(compact);
		}else if(Contants.COMPACTRELET.equals(compact.getType())){
			CompactRelet relet = iCustomerRoomDAO.getReletCompactByCId(compact.getId());
			iCustomerRoomDAO.deleteObject(relet);
			//iCustomerRoomDAO.deleteObject(compact);
		}
	}

	//客户迁出
	@Override
	public void outRoom(String id, String endDate,String userName) throws BkmisServiceException {
		
		//更改合同
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,Integer.parseInt(id));
		iCustomerRoomDAO.log(userName, Contants.LEAVE, "对编号为"+compact.getCode()+"的合同执行了迁出",Contants.L_COMPACTMANAGE, "合同迁出");
		compact.setGoDate(endDate);
		//10.11在确定迁出时才将合同的状态设置为过期
		//compact.setIsDestine(Contants.HASOVER);
		compact.setIsNotice(Contants.HASNOTICE);
		iCustomerRoomDAO.updateObject(compact);
		//更新房间对应收费标准
		List<CompactRoomCoststandard> standards = iCustomerRoomDAO.getCompactRoomCoststandard(Integer.parseInt(id));
		for(int i=0;i<standards.size();i++){
			standards.get(i).setStatus(Contants.INVALID);
		}
		//更新客户房间关系表
		List<ERoomClient> rList = iCustomerRoomDAO.getERoomClientByPactId((Integer.parseInt(id)));
		for(int i=0;i<rList.size();i++){
			rList.get(i).setStatus(Contants.INVALID);
			int roomId = rList.get(i).getRoomId();
			ERooms rooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class, roomId);
			rooms.setStatus(Contants.OUTUSE);
			//iCustomerRoomDAO.updateObject(rooms);
		}
		//更新统计入住迁出表
		//取当前客户的所有有效合同，如果数量大于1，就是说，除了当前迁出的合同外，客户还有另外的合同正在生效，所以可以不必修改客户的迁出日期，因为客户并没有离开，只是退了部分合同而已
		//所以只有在合同数量为1的情况下才执行更新
		int clientId = compact.getClientId();
		List<Compact> compacts = iCustomerRoomDAO.getCompactByClientId(clientId);
		int k = 0;
		if(compacts!=null&&compacts.size()>0){
			for(int i=0;i<compacts.size();i++){
				if(Contants.THROUGHAPPROVAL.equals(compacts.get(i).getStatus())){
					k++;
				}
			}
		}
		if(k==1){
			List<AnalysisClientComeGo> ComeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(), compact.getLpId());
			if(ComeGos!=null&&ComeGos.size()>0){
				ComeGos.get(0).setGoDate(endDate);
				iCustomerRoomDAO.updateObject(ComeGos.get(0));
			}
		}
	}
	//反悔迁出
	public void returnOutRoom(String id) throws BkmisServiceException {
		
		//更改合同--迁出日期置空，状态置“通过审批”
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class,Integer.parseInt(id));
		compact.setGoDate("");
		compact.setStatus(Contants.THROUGHAPPROVAL);
		compact.setIsDestine(Contants.NORMARL);
		compact.setIsNotice(Contants.HAVEIN);
		iCustomerRoomDAO.updateObject(compact);
		//删除合同迁出表中的相应的记录
		iCustomerRoomDAO.update("delete from CompactQuit t where t.compact.id="+compact.getId());
		//更新房间对应收费标准--状态置1（有效）
		List<CompactRoomCoststandard> standards = iCustomerRoomDAO.getCompactRoomCoststandard(Integer.parseInt(id));
		for(int i=0;i<standards.size();i++){
			standards.get(i).setStatus(Contants.VALID);
			iCustomerRoomDAO.updateObject(standards.get(i));
		}
		//更新客户房间关系表--状态置1（有效）；房间状态置已出租
		List<ERoomClient> rList = iCustomerRoomDAO.getERoomClientByPactId((Integer.parseInt(id)));
		for(int i=0;i<rList.size();i++){
			rList.get(i).setStatus(Contants.VALID);
			iCustomerRoomDAO.updateObject(rList.get(i));
			//房间状态置已出租
			int roomId = rList.get(i).getRoomId();
			ERooms rooms = (ERooms)iCustomerRoomDAO.getObject(ERooms.class, roomId);
			rooms.setStatus(Contants.BELEASED);
			iCustomerRoomDAO.updateObject(rooms);
		}
		//更新统计入住迁出表
		//取当前客户的所有有效合同，如果数量大于1，就是说，除了当前迁出的合同外，客户还有另外的合同正在生效，所以可以不必修改客户的迁出日期，因为客户并没有离开，只是退了部分合同而已
		//所以只有在合同数量为1的情况下才执行更新
		//统计-迁出日期置空
		List<AnalysisClientComeGo> ComeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(), compact.getLpId());
		if(ComeGos!=null&&ComeGos.size()>0){
			ComeGos.get(0).setGoDate("");
			iCustomerRoomDAO.updateObject(ComeGos.get(0));
		}
		//删除生成的账单
		List<CompactQuitBill> billIds = iCustomerRoomDAO.getBillIds(id);
		for (CompactQuitBill compactQuitBill : billIds) {
			CBill bill = new CBill();
			bill.setId(compactQuitBill.getBillId());
			iCustomerRoomDAO.deleteObject(bill);
			iCustomerRoomDAO.updateLastBuildDate(compactQuitBill.getStandardId(), compactQuitBill.getLastBuildDate());
		}
		iCustomerRoomDAO.delCompactBill(id);
	}
	//查询当日迁出合同
	public List<Compact> getOutCompactList()throws BkmisServiceException{
		List<Compact> list = iCustomerRoomDAO.getOutCompactList();
		return list;
	}
	//保存客户迁出时生成的账单id
	public void saveCompactBill(List list,String pactId)throws BkmisServiceException{
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				Integer billId = (Integer) map.get("billId");
				Integer standardId = (Integer) map.get("id");
				String lastBuildDate = (String) map.get("lastBuildDate");
				if (!ExtendUtil.checkNull(billId)) {
					CompactQuitBill compactQuitBill = new CompactQuitBill();
					compactQuitBill.setBillId(billId);
					compactQuitBill.setPactId(Integer.decode(pactId));
					compactQuitBill.setStandardId(standardId);
					compactQuitBill.setLastBuildDate(lastBuildDate);
					iCustomerRoomDAO.saveObject(compactQuitBill);
				}
			}
		}
	}
	/**
	 * 通知入驻
	 * CustomerRoomServiceImpl.notice
	 */
	public void notice(Integer compactId)throws BkmisServiceException{
		/**1.生成合同数据*/
		//Integer id = Integer.parseInt(compactId);
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, compactId);
		compact.setIsNotice(Contants.HAVENOTICE);
		iCustomerRoomDAO.updateObject(compact);
		/**2.生成押金数据*/
		CDeposit rentDeposit = new CDeposit();//房租押金
		rentDeposit.setClientId(compact.getClientId());
		rentDeposit.setDepositType(Contants.RENT_DEPOSIT);
		CDeposit decorationDeposit = new CDeposit();//装修押金
		decorationDeposit.setClientId(compact.getClientId());
		decorationDeposit.setDepositType(Contants.DECORATION_DEPOSIT);
		if(iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.RENT_DEPOSIT)==null){
			iCompactManagerDAO.saveObject(rentDeposit);
		}
		if(iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.DECORATION_DEPOSIT)==null){
			iCompactManagerDAO.saveObject(decorationDeposit);
		}
		/**3.生成预收款数据*/
		CAdvance cAdvance = new CAdvance();//保存预收房租
		cAdvance.setClientId(compact.getClientId());
		if(iCompactManagerDAO.getCAdvance(compact.getClientId())==null){
			iCompactManagerDAO.saveObject(cAdvance);
		}
		CAdvanceWuYF cAWYf = new CAdvanceWuYF();//保存预收物业费 
		cAWYf.setClientId(compact.getClientId());
		if(iCompactManagerDAO.getCAdvanceWuYF(compact.getClientId())==null){
			iCompactManagerDAO.saveObject(cAWYf);
		}
		/**4.生成定金数据 ?*/
		CEarnest earnest = new CEarnest();
		earnest.setClientId(compact.getClientId());
		if(iCompactManagerDAO.getCEarnest(compact.getClientId())==null){
			iCompactManagerDAO.saveObject(earnest);
		}
	}
	/**
	 * 确定正式迁出
	 * ICustomerRoomService.notice
	 */
	public void enterToGo(String compactId)throws BkmisServiceException{
		
		Integer id = Integer.parseInt(compactId);
		Compact compact = (Compact)iCustomerRoomDAO.getObject(Compact.class, id);
		compact.setIsNotice(Contants.HASGO);
		compact.setIsDestine(Contants.HASOVER);
		//10.11更新合同的内容
		iCustomerRoomDAO.updateObject(compact);
		//2013-03-31加入了汇总要迁出的合同
		ICalculateMoneyService calculateService = (ICalculateMoneyService)SpringContextHolder.getBean("calculateService");
		CAccounttemplate accountTemplate = calculateService.getAccounttemlateByCompactId(Integer.parseInt(compactId));
		List<CompactRoomCoststandard> compactRoomCoststandard = calculateService.getCompactRoomCoststandardById(Integer.parseInt(compactId));
		try {
			calculateService.CalculateMoney(compactRoomCoststandard, accountTemplate, Integer.parseInt(compactId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//查询客户名称，导出报表用
	public List<CompactClient> explorCustomerRoomList(CompactRoomForm compactRoomForm)
			throws BkmisServiceException {
		
		List<CompactClient> list = iCustomerRoomDAO.explorCustomerRoomList(compactRoomForm); 
		return list;
	}
	//导出入驻信息报表用
	public List<Compact> explorCustomerRoomList1(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate)
			throws BkmisServiceException {
		
		List<Compact> list = iCustomerRoomDAO.explorCustomerRoomList1(lpId,clientName,roomCode,status,beginDate,endDate); 
		return list;
	}

	@Override
	/**
	 * 得到一个任务通知单详细
	 */
	public CompactTask getTask(Integer id) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		String hql = "from CompactTask where id = "+id +"  ";
		List list = iCustomerRoomDAO.findObject(hql);
		CompactTask compactTask = new CompactTask();
		if(!list.isEmpty()){
			compactTask = (CompactTask)list.get(0);
		}
		return compactTask;
		
	}

	/**
	 * 保存工作任务单
	 * CustomerRoomServiceImpl.saveTask
	 */
	@Override
	public void saveTask(CompactRoomForm form1) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		Compact compact = new Compact();
		//compact.setIsDestine(Contants.DESTINES);
		try {
			
			CompactTask compactTask = new CompactTask();
			compactTask.setNoteDate(GlobalMethod.getTime3());
			try {
				BeanUtils.copyProperties(compactTask, form1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			iCustomerRoomDAO.saveObject(compactTask);
			//更新编号表中的值
			iCustomerRoomDAO.updateAColumn("SysSequence", "value", form1.getCode(), "code", "rwd");
			//将刚保存后的工作任务单取出来，以打印使用
			String hql = "from CompactTask where compactId = "+form1.getCompactId() +" order by id desc ";
			List list = iCustomerRoomDAO.findObject(hql);
			CompactTask compactTask1 = new CompactTask();
			if(!list.isEmpty()){
				compactTask1 = (CompactTask)list.get(0);
			}
			form1.setCompactTask(compactTask1);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 得到相应编号
	 * @param beginCode 传入开始的默认第一个代码
	 * @param code 要获取的代码种类
	 * CustomerRoomServiceImpl.getTheCode
	 */
	public String getTheCode(String beginCode,String code) throws BkmisServiceException {

		String year = GlobalMethod.getTime().substring(0,4);
		String bigCode = iCustomerRoomDAO.getBiggestCode(code);
		String newCode = bigCode;
		if(!bigCode.isEmpty() && !"null".equals(bigCode)){
			String[] strings = bigCode.split("-");//合同编号的格式为2011-00001，所以去横岗后面的排序值加一
			if(Integer.parseInt(year)>Integer.parseInt(strings[0])){//如果当前年份大于已存在的最大年份，那么重新从1开始编号
				newCode = year + "-" + beginCode;
			}else{//这个地方可能还有问题，明天来了接着测试一下下
				newCode = String.valueOf(Integer.parseInt(strings[1]) + 1);
				String lingString  = "0";
				int k = 1;//为什么k的初值是1，下面的for循环中i的起始也是1而都不是0 呢？
						//因为：假设beginCode的长度是5，那么下面的这个for循环应该就是执行五次来判断有效数字的位数（最大是5），所以i是小于等于而不是小于。
						//而为什么i取的最小是1呢？因为newCode的最小长度是1不可能是0，而i要顺应它也必须是1到5，而不是0到4
						//再说说k为什么是1，因为code的长度剪掉了有效值后，0的位数最多是4，所以因为第二个for循环是按照总长度减掉k后来作为0的个数，所以k至少是1（当然也可以不是1，如果k初始为0，那么只需j那里变成初始2就可以了 ）
						//为什么j也是1呢？因为lingString本身已经是一个位数的0了，所以要少循环一次才行。
				for(int i = 1;i<=beginCode.length();i++){
					if(newCode.length()==i){
						break;
					}
					k++;
				}
				for(int j =1;j<beginCode.length()-k;j++){
					lingString = "0"+lingString;
				}
				
				newCode = year + "-"+ lingString+newCode;
			}
		}else{//如果当前是第一份合同，那么直接给予001的编号
			newCode = year + "-" + beginCode;
		}
		return newCode;
	}

	@Override
	//得到某客户的历史住房记录
	public List getClientsHistoryRooms(String clientId)
			throws BkmisServiceException {
		// TODO Auto-generated method stub
		String hql = "select r,c from ERoomClient c,ERooms r where c.roomId = r.roomId and c.clientId='"+clientId+"' and c.status='"+Contants.VALID+"'";
		
		return iCustomerRoomDAO.findObject(hql);
	}

	/** 取消提交（取消校验） */
	@Override
	public void cancelChecked(String id, String type)
			throws BkmisServiceException {
		type = GlobalMethod.NullToSpace(type);
		if(type.equals(Contants.INTENTION)){//如果是意向书
			iCustomerRoomDAO.cancelChecked4CompactIntention(id);
		}
		if(type.equals(Contants.COMPACT)){//如果是租赁合同
			iCustomerRoomDAO.updateTask("approveNew", -1);
			iCustomerRoomDAO.updateTask("collateNew", 1);
			iCustomerRoomDAO.cancelChecked4Compact(id);
		}
		if(type.equals(Contants.COMPACTRELET)){//如果是续租合同
			iCustomerRoomDAO.updateTask("approveRelet", -1);
			iCustomerRoomDAO.updateTask("collateRelet", 1);
			iCustomerRoomDAO.cancelChecked4Compact(id);
			iCustomerRoomDAO.cancelChecked4Relet(id);
		}
		if(type.equals(Contants.COMPACTCHANGE)){//如果是变更合同
			iCustomerRoomDAO.updateTask("approveChange", -1);
			iCustomerRoomDAO.updateTask("collateChage", 1);
			iCustomerRoomDAO.cancelChecked4Compact(id);
			iCustomerRoomDAO.cancelChecked4Change(id);
		}
		
	}

	/**
	 * 获取迁出的编号
	 */
	@Override
	public String getQuitCode(Integer lpId) throws BkmisServiceException {
		
		List<Compact> list = (List<Compact>)iCustomerRoomDAO.findObject("from CompactQuit where lpId = "+lpId);;
		String year = GlobalMethod.getTime().substring(0,4);
		String code = iCustomerRoomDAO.getQuitCode(lpId);
		if(list!=null&&list.size()>0){
			String[] strings = code.split("-");
			if(Integer.parseInt(year)>Integer.parseInt(strings[0])){//如果当前年份大于已存在的最大年份，那么重新从1开始编号
				return  year + "-" + "001";
			}else{
				code = String.valueOf(Integer.parseInt(strings[1]) + 1);
				if(code.length()==1){
					code = "00" + code;
				}else if(code.length()==2){
					code = "0" + code;
				}
				return year + "-"+ code;
			}
		}else{
			return year + "-" + "001";
		}
	}
}
