package com.zc13.bkmis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IMeterManageDAO;
import com.zc13.bkmis.dao.IRoomManageDAO;
import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERoomFixture;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.CloneObject;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * @author 侯哓娟
 * Date：Nov 23, 2010
 * Time：5:15:20 PM
 */
public class RoomManageServiceImpl implements IRoomManageService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IRoomManageDAO iroomManageDao;
	/* 注入imeterManageDao */
	private IMeterManageDAO imeterManageDao;
	//注入iSysParamManagerDAO
    private ISysParamManagerDAO iSysParamManagerDAO;
    
	public ISysParamManagerDAO getISysParamManagerDAO() {
		return iSysParamManagerDAO;
	}
	public void setISysParamManagerDAO(ISysParamManagerDAO sysParamManagerDAOImpl){
		this.iSysParamManagerDAO = sysParamManagerDAOImpl;
	}
	public IRoomManageDAO getIroomManageDao() {
		return iroomManageDao;
	}
	public void setIroomManageDao(IRoomManageDAO iroomManageDao) {
		this.iroomManageDao = iroomManageDao;
	}
	public IMeterManageDAO getImeterManageDao() {
		return imeterManageDao;
	}
	public void setImeterManageDao(IMeterManageDAO imeterManageDao) {
		this.imeterManageDao = imeterManageDao;
	}
	@Override
	public Map<String, List<SysCode>> getAdvancedInfo(Integer lpId) throws BkmisServiceException {
		List<SysCode> roomTypelist = null;
		List<SysCode> towardlist = null;
		List<SysCode> statuslist = null;
		try{
			roomTypelist = iroomManageDao.queryRoomType(lpId);
			towardlist = iroomManageDao.queryToward(lpId);
			statuslist = iroomManageDao.queryStatus(lpId);
		}catch(Exception e){
			logger.error("高级查询所需的房间类型信息和朝向信息失败!RoomManageServiceImpl.getAdvancedInfo()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("高级查询所需的房间类型信息和朝向信息获取失败!RoomManageServiceImpl.getAdvancedInfo()。");
		}
		Map map = new HashMap();
		map.put("roomTypelist", roomTypelist);
		map.put("towardlist", towardlist);
		map.put("statuslist",statuslist);
		return map;
	}

	public List<ERooms>  getRoom(RoomForm roomForm) throws BkmisServiceException {
		
		List<ERooms> list = null;
		try{
			list = iroomManageDao.queryRoom(roomForm);
		}catch(Exception e){
			logger.error("查询房间失败!RoomManageServiceImpl.getRoom()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("查询房间失败!RoomManageServiceImpl.getRoom()。");
		}
		return list;
	}
	

	public List<ERooms>  getRoomE(RoomForm roomForm) throws BkmisServiceException {
		
		List<ERooms> list = null;
		try{
			list = iroomManageDao.queryRoomE(roomForm);
		}catch(Exception e){
			logger.error("查询房间失败!RoomManageServiceImpl.getRoom()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("查询房间失败!RoomManageServiceImpl.getRoom()。");
		}
		return list;
	}
	
	public List<ERooms> getRoom() throws BkmisServiceException{
		
		List<ERooms> list = iroomManageDao.getObjects(ERooms.class);
		return list;
		
	}
	
   public List<ViewBuild> getInfoForTree(RoomForm roomForm){
	   List<ViewBuild> treelist = null;
	   treelist = iroomManageDao.queryInfoForTree(roomForm);
	   return treelist;
   }

  /**根据楼栋和楼盘去查询房间*/
   public List<ERooms> getRoomByLpOrBuild(Integer id,String tablename,RoomForm roomForm){
	   List<ERooms> list = null;
	   if("e_lp".equals(tablename.trim())){
		   ELp elp = (ELp)iroomManageDao.getObject(ELp.class, id);
		  list = iroomManageDao.queryRoomforLp(elp,roomForm);
	   }else{
		  list =  iroomManageDao.queryRoomByBuild(id,roomForm);
	   }
	return list;
   }

   @Override
   public boolean checkRoomCode(String roomCode,EBuilds ebuild) throws BkmisServiceException {
	boolean var = false;
	try{
		List<ERooms> list = iroomManageDao.queryRoomByRoomCode(roomCode,ebuild);
		if(list != null && list.size() >= 1){
			var = false;
		}else{
			var = true;
		}
	}catch(Exception e){
		logger.error("根据房间号检查房间失败!RoomManageServiceImpl.checkRoomCode()。详细信息：" + e.getMessage());
		throw new BkmisServiceException("根据房间号检查房间失败!RoomManageServiceImpl.checkRoomCode()。");
	}
	return var;
}

   public void addRoom(HttpServletRequest request,RoomForm roomForm) throws BkmisServiceException {
	   
	try{
		ERooms eroom = new ERooms();
		BeanUtils.copyProperties(eroom,roomForm);
		/* 设置一个楼栋对象到eroom */
		Integer buildId = roomForm.getBuildId();
		EBuilds ebuild = (EBuilds)iroomManageDao.getObject(EBuilds.class, buildId);
		ELp elp = ebuild.getELp();
		Integer lpId = elp.getLpId();
		eroom.setEBuilds(ebuild);
		
		/* 设置房间室号全称 
		 * 从form中得到楼栋对象，再得到楼栋的编号
		 * 室号全称为楼栋号加上房间编号
		 * */
		//String roomFullName = ebuild.getBuildCode() + "—" + roomForm.getRoomCode() ;
		String roomFullName = roomForm.getRoomCode();
		/* 保存室号全称到房间对象 */
		eroom.setRoomFullName(roomFullName);
		eroom.setStatus("未出租");
		iroomManageDao.saveObject(eroom);

		//添加系统日志
		iroomManageDao.logDetail(roomForm.getUserName(),Contants.ADD,"房间",Contants.L_BUILD,"2",eroom);
		
		
		/** 保存楼盘的表具信息 */
	    /* 从表单里面得到楼盘名称 */
		    String roomCode = roomForm.getRoomCode();
		    List<ERooms> roomlist = iroomManageDao.queryRoomByRoomCode(roomCode, ebuild);
		    Integer roomId = 0;
		    for(ERooms r:roomlist){
		    	roomId = r.getRoomId();
		    }
	  /* 添加表具 
	   * 从Form中得到表具的信息拼接的字符串 */
	   String str1 = roomForm.getCode();
	   String str2 = roomForm.getType();
	   /* 从form中得到的code的字符串和type的字符串为空的情况下，不执行房间表具的添加
	    * 避免meterId为空 */
	   if(!"".equals(str1) && !"".equals(str2)){
		   /* 把到的字符串从＂；＂分号处开始拆分　*/
		   String[] arr1 = str1.split(";");
		   String[] arr2 = str2.split(";");
		   String code = null;
		   String type = null;
		   Integer meterId = 0;
		   for(int i=0,j=0;i<arr1.length && j<arr2.length;i++,j++){
			   /* 循环出表具信息，得到表具编号 */
			   code = arr1[i];
			   type = arr2[j];
			   /* 根据表具编号得到一个表具list */
			   List<EMeter> list = imeterManageDao.queryMeterByCodeAndType(code, type);
			   for(EMeter e:list){
				   meterId = e.getId();
			   }
			   System.out.println(meterId);
			   /* 再根据表具id去得到一个表具对象 */
			   EMeter emeter = (EMeter) imeterManageDao.getObject(EMeter.class, meterId);
			   /* 把楼盘号保存到表具对象里 */
			   emeter.setRoomId(roomId);
			   emeter.setBuildId(buildId);
			   emeter.setLpId(lpId);
			   /* 更新表具对象 */
			   imeterManageDao.updateObject(emeter);
	   }
	 }
		   /** 添加房间设备 */
		   String[] sfixtureId = request.getParameterValues("fixtureId");
		   String[] snumber = request.getParameterValues("number");
		   String[] sebz = request.getParameterValues("ebz");
		   Integer fixtureId = 0;
		   Integer amount = 0;
		   /* 如果从页面得到的名为fixtureId的数组为空的情况下不执行房间设备的添加 
		    * 避免 null */
		   if(sfixtureId != null){
			   for(int k=0;k<sfixtureId.length;k++){
				   String ssfixtureId = GlobalMethod.NullToSpace(sfixtureId[k]);
				   String ssnumber = GlobalMethod.NullToSpace(snumber[k]);
				   String bz = sebz[k];
				   if(!"".equals(ssfixtureId)){
					   fixtureId = Integer.parseInt(ssfixtureId);
				   }
				   if(!"".equals(ssnumber)){
					   amount = Integer.parseInt(ssnumber);
				   }
				   ERoomFixture equip = new ERoomFixture();
				   equip.setFixtureId(fixtureId);
				   equip.setRoomId(roomId);
				   equip.setAmount(amount);
				   equip.setBz(bz);
				   iroomManageDao.saveObject(equip);
			   }
		   }
	}catch(Exception e){
		logger.error("房间新增失败!RoomManageServiceImpl.addRoom()。详细信息：" + e.getMessage());
		throw new BkmisServiceException("房间新增失败!RoomManageServiceImpl.addRoom()。");
	}
	
}

   @Override
   public ERooms getERoomsById(Integer roomId) throws BkmisServiceException {
	ERooms eroom = new ERooms();
	try{
		if(roomId == null){
			return null;
		}else{
			eroom = (ERooms) iroomManageDao.getObject(ERooms.class, roomId);
		}
	}catch(Exception e){
		logger.error("根据房间id得到指定房间信息失败!RoomManageServiceImpl.getERoomsById()。详细信息：" + e.getMessage());
		throw new BkmisServiceException("根据房间id得到指定房间信息失败!RoomManageServiceImpl.getERoomsById()。");
	}
	return eroom;
}

   @Override
	public void midifyRoom(HttpServletRequest request,RoomForm roomForm) throws BkmisServiceException {
	
	   try{
			/* 更新房间信息 */
			int roomId = roomForm.getRoomId();
			ERooms room = (ERooms)iroomManageDao.getObject(ERooms.class,roomId);
			ERooms room2 = new ERooms();
			room2=(ERooms)CloneObject.copy(room);
			BeanUtils.copyProperties(room2,roomForm);
			//写入系统日志
			iroomManageDao.logDetail2(roomForm.getUserName(), Contants.MODIFY,"房间",Contants.L_BUILD, "1", room2);
			BeanUtils.copyProperties(room,roomForm);
			String roomFullName = roomForm.getRoomCode();
			room.setRoomFullName(roomFullName);
			iroomManageDao.updateObject(room);
			/* 更新对应房间的表具信息 
			 * 从Form中得到新增的表具的信息拼接的字符串 */
			/** 获取buildId和lpId用于保存到表具信息里 */
				EBuilds ebuild = room.getEBuilds();
				Integer buildId = ebuild.getBuildId();
				ELp elp = ebuild.getELp();
				Integer lpId = elp.getLpId();
			  
				String str1 = roomForm.getCode();
				String str2 = roomForm.getType();
				if (!"".equals(str1)  && !"".equals(str2)) {
			/* 把到的字符串从＂；＂分号处开始拆分 */
			String[] arr1 = str1.split(";");
			String[] arr2 = str2.split(";");
			String code = null;
			String type = null;
			Integer meterId = 0;
			for (int i = 0, j = 0; i < arr1.length && j < arr2.length; i++, j++) {
				/* 循环出表具信息，得到表具编号 */
				code = arr1[i];
				type = arr2[j];
				/* 根据表具编号得到一个表具list */
				List<EMeter> list = imeterManageDao.queryMeterByCodeAndType(code, type);
				for (EMeter e : list) {
					meterId = e.getId();
				}
				/* 再根据表具id去得到一个表具对象 */
				EMeter emeter = (EMeter) imeterManageDao.getObject(EMeter.class, meterId);
				/* 把楼盘号保存到表具对象里 */
				emeter.setRoomId(roomId);
				emeter.setBuildId(buildId);
				emeter.setLpId(lpId);
				/* 更新表具对象 */
						imeterManageDao.updateObject(emeter);
					}
				}
			
			   /** 添加房间设备 */
			   String[] sfixtureId = request.getParameterValues("fixtureId");
			   String[] snumber = request.getParameterValues("number");
			   String[] sebz = request.getParameterValues("ebz");
			   Integer fixtureId = 0;
			   Integer amount = 0;
			   /* 如果从页面得到的名为fixtureId的数组为空的情况下不执行房间设备的添加 
			* 避免 null */
			   if(sfixtureId != null){
				   for(int k=0;k<sfixtureId.length;k++){
					   String ssfixtureId = GlobalMethod.NullToSpace(sfixtureId[k]);
					   String ssnumber = GlobalMethod.NullToSpace(snumber[k]);
					   String bz = sebz[k];
					   if(!"".equals(ssfixtureId)){
				   fixtureId = Integer.parseInt(ssfixtureId);
			   }
			   if(!"".equals(ssnumber)){
						   amount = Integer.parseInt(ssnumber);
					   }
					   ERoomFixture equip = new ERoomFixture();
					   equip.setFixtureId(fixtureId);
					   equip.setRoomId(roomId);
					   equip.setAmount(amount);
					   equip.setBz(bz);
					   iroomManageDao.saveObject(equip);
				   }
			   }
			   //更新在用合同中的房间总面积room
			   //iroomManageDao.update("");
	}catch(Exception e){
		e.printStackTrace();
		logger.error("房间信息修改失败!RoomManageServiceImpl.midifyRoom()。详细信息：" + e.getMessage());
		throw new BkmisServiceException("房间信息修改失败!RoomManageServiceImpl.midifyRoom()。");
	}
}

   @Override
   public String deleteRoom(String sroomId,String userName)throws BkmisServiceException{
	   
	   List list = null;
	   String stemp = null;
	   String roomCode = "";
	   String strroomId = "";
	   try{
		   Integer roomId = 0;
		   String[] temp = sroomId.split(";");
		   for(int i=0;i<temp.length;i++){
			   roomId = Integer.parseInt(temp[i]);
			   list = iroomManageDao.getRoomClientInfo(roomId);
			   if(list.size() != 0){
				   Object[] s = (Object[])list.get(0);
				   ERooms e = (ERooms)s[0];
				   roomCode += e.getRoomCode() + ","; 
			   }else{
				   /* 删除房间 */
				   ERooms room = (ERooms)iroomManageDao.getObject(ERooms.class, roomId);
				   iroomManageDao.logDetail2(userName, Contants.DELETE, "房间", Contants.L_BUILD, "3", room);
				   iroomManageDao.deleteObject(room);
				   /* 删除对应房间下的表具信息 */
				   imeterManageDao.deleteMeterByRoomId(roomId);
				   /* 删除对应房间下的设备信息 */
				   iroomManageDao.deleteEquip(roomId);
			   }
		   }
		   stemp = roomCode ;
	   }catch(Exception e){
			logger.error("获取房间客户信息失败!RoomManageServiceImpl.deleteRoom()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获取房间客户信息失败!RoomManageServiceImpl.deleteRoom()。");
		}
	   return stemp;
   }
   
//   @Override
//   public void deleteRoom(List<String> idList) throws BkmisServiceException {
//	
//	   try{
//		iroomManageDao.deleteRoom(idList);
//		/* 删除对应房间下的表具信息 */
//		imeterManageDao.deleteMeterByRoomId(idList);
//		/* 删除对应房间下的设备信息 */
//		iroomManageDao.deleteEquip(idList);
//	   }catch(Exception e){
//		logger.error("房间信息删除失败!iroomManageDao.deleteRoom()。详细信息：" + e.getMessage());
//		throw new BkmisServiceException("房间信息删除失败!RoomManageServiceImpl.deleteRoom()。");
//	}
//	
//}

//   @Override
//   public void deleteRoom(String sroomId) throws BkmisServiceException{
//	   
//	   try{
//		   String[] temp = sroomId.split(";");
//		   for(int i=0;i<temp.length;i++){
//			   Integer roomId = Integer.parseInt(temp[i]);
//			   ERooms room = (ERooms)iroomManageDao.getObject(ERooms.class, roomId);
//			   iroomManageDao.deleteObject(room);
//			   /* 删除对应房间下的表具信息 */
//			   imeterManageDao.deleteMeterByRoomId(roomId);
//			   /* 删除对应房间下的设备信息 */
//			   iroomManageDao.deleteEquip(roomId);
//		   }
//		   }catch(Exception e){
//			logger.error("房间信息删除失败!iroomManageDao.deleteRoom()。详细信息：" + e.getMessage());
//			throw new BkmisServiceException("房间信息删除失败!RoomManageServiceImpl.deleteRoom()。");
//		}
//   }
  
   @Override
   public void batchincreaseRoom(RoomForm roomForm) throws BkmisServiceException {
		
	   try {
		    /* 房间号非数字列 */
			String croomCode = roomForm.getCroomCode();
			/* 房间增加开始数 */
			Integer beginRoomCode = roomForm.getBeginRoomCode();
			/* 房间增加截至数 */
			Integer endRoomCode = roomForm.getEndRoomCode();
			/* 房间号 */
			String roomCode = null;
			Integer buildId = roomForm.getBuildId();
			EBuilds ebuild = (EBuilds)iroomManageDao.getObject(EBuilds.class, buildId);
			
			for (int i = beginRoomCode; i <= endRoomCode; i++) {
				ERooms eroom = new ERooms();
				BeanUtils.copyProperties(eroom, roomForm);
				/* 设置一个楼栋对象到eroom */
				eroom.setEBuilds(ebuild);
				
				String roomFullName = null; 
				
				/** 这里是设置房间号的，判断数字列的开始数字和结束数字是否为0,
				 *  如果为零就给房间号设置只是非数字类型的，否则房间号为非数字列家数字列 */
//				if (beginRoomCode == 0 && endRoomCode == 0) {
//					eroom.setRoomCode(croomCode);
//					/* 设置房间室号全称 */
//					roomFullName = ebuild.getBuildCode() + "—" + croomCode ;
//				} else {
//					eroom.setRoomCode(croomCode + i);
//					roomFullName = ebuild.getBuildCode() + "—" + (croomCode + i) ;
//				}
//				if(i>0 && i<=10){
					if ("".equals(croomCode)) {
						Integer numcode = beginRoomCode++;
						roomCode = numcode.toString();
						eroom.setRoomCode(roomCode);
						/* 设置房间室号全称 */
						roomFullName = ebuild.getBuildCode() + "—" + roomCode ;
					} else {
						roomCode = croomCode + beginRoomCode++;
						eroom.setRoomCode(roomCode);
						roomFullName = ebuild.getBuildCode() + "—" + roomCode ;
					}
//				}else{
//					if(i>10 && i<=100){
//						if ("".equals(croomCode)) {
//							Integer numcode = beginRoomCode++;
//							roomCode = numcode.toString();
//							eroom.setRoomCode(roomCode);
//							/* 设置房间室号全称 */
//							roomFullName = ebuild.getBuildCode() + "—" + roomCode ;
//						} else {
//							roomCode = croomCode + beginRoomCode++;
//							eroom.setRoomCode(roomCode);
//							roomFullName = ebuild.getBuildCode() + "—" + roomCode ;
//						}
//					}
//				}
				eroom.setRoomFullName(roomFullName);
				eroom.setStatus("未出租");
				iroomManageDao.saveObject(eroom);
				//添加系统日志
				iroomManageDao.logDetail(roomForm.getUserName(),Contants.ADD,"房间",Contants.L_BUILD,"2",eroom);
			}
		} catch (Exception e) {
			logger.error("房间的批量增加失败!RoomManageServiceImpl.batchincreaseRoom()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("房间的批量增加失败!RoomManageServiceImpl.batchincreaseRoom()。");
		}
	}


   @Override
   public int queryCounttotal(RoomForm roomForm){
	   
	   return iroomManageDao.queryCounttotal(roomForm);
   }
   
   @Override
   public int getRoomNumByTablename(Integer id,String tablename)throws BkmisServiceException{
	  
	   Integer roomnum = 0;
	   List list = null;
	   if("e_lp".equals(tablename.trim())){
		   ELp elp = (ELp)iroomManageDao.getObject(ELp.class, id);
		   list = iroomManageDao.queryRoomNumforLp(elp);
		   for(int i=0;i<list.size();i++){
			   roomnum += (Integer)list.get(i); 
		   }
	   }else{
		   EBuilds ebuild = (EBuilds)iroomManageDao.getObject(EBuilds.class, id);
		   list =  iroomManageDao.queryRoomNumByBuild(ebuild);
		   roomnum = (Integer) list.get(0);
	   }
	return roomnum;
   }
  
   @Override
   public int queryCounttotal(Integer roomId) {
	   
	   return iroomManageDao.queryCounttotal(roomId);
	}
   
   @Override
   public List<SysCode> getMeterType() throws BkmisServiceException {
		List<SysCode> list = null;
		try{
			list = imeterManageDao.queryMeterType();
		}catch(Exception e){
			logger.error("表具类型获得失败!RoomManageServiceImpl.getMeterType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具类型获得失败!RoomManageServiceImpl.getMeterType()。");
		}
		return list;
	}

   @Override
   public List<EMeter> getMeterByBuildIdAndName(Integer buildId,String meterName) throws BkmisServiceException {
	   boolean var = false;
	   List<EMeter> list = null;
		try{
			 list = imeterManageDao.MeterByBuildIdAndName(buildId,meterName);
		}catch(Exception e){
			logger.error("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。");
		}
		return list;
  }
   
   @Override
   public boolean getMeterByBuildId(Integer buildId) throws BkmisServiceException {
	   List<EMeter> list = null;
	   boolean var = false;
	   try{
			 list = imeterManageDao.queryMeterByBuildId(buildId);
			 if(list != null && list.size()>= 1){
					var = true;
				}else{
					var = false;
				}
		}catch(Exception e){
			logger.error("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。");
		}
		return var;
}

   @Override
   public boolean checkMeter(RoomForm roomForm) throws BkmisServiceException {
	   boolean var = false;
		try{
			EMeter emeter = new EMeter();
			BeanUtils.copyProperties(emeter,roomForm);
			String meterType = emeter.getType();
			String meterCode = emeter.getCode();
			/* 同一种类型的表中不能有重复的表编号 */
			List<EMeter> list = (List<EMeter>) imeterManageDao.queryMeterByCodeAndType(meterCode, meterType);
			if(list != null && list.size()>= 1){
				var = false;
			}else{
				var = true;
			}
		}catch(Exception e){
			logger.error("表具名称检测失败!RoomManageServiceImpl.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具名称检测失败!RoomManageServiceImpl.checkMeter()。");
		}
		return var;}

   @Override
   public void addMeter(RoomForm roomForm) throws BkmisServiceException {
		try {
			EMeter emeter = new EMeter();
			BeanUtils.copyProperties(emeter, roomForm);
			imeterManageDao.saveObject(emeter);
			imeterManageDao.logDetail(roomForm.getUserName(),Contants.ADD,"房间表具",Contants.L_BUILD,"2",emeter);
		} catch (Exception e) {logger.error("表具添加失败!RoomManageServiceImpl.addMeter()。详细信息："+ e.getMessage());
			throw new BkmisServiceException("表具添加失败!RoomManageServiceImpl.addMeter()。");
		}
	}
  
   @Override
	public String getMeterName(RoomForm roomForm)throws BkmisServiceException{
		
		String meterName = null;
		try{
			String codeValue = roomForm.getType();
			SysCode syscode =  iSysParamManagerDAO.getCodeByValue(codeValue);
			meterName = syscode.getCodeName();
		}catch(DataAccessException e){
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterName;
	}
   
   @Override
	public List<EMeter> getMeterByCTP(String meterCode, String meterName,Integer parentId)throws BkmisServiceException {
			
		List<EMeter> list = null;
		try{
			 list = imeterManageDao.queryMeterByCTP(meterCode,meterName,parentId);
		}catch(Exception e){
			logger.error("根据表具编号查询表具失败!RoomManageServiceImpl.getMeterByCodeAndName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据表具编号查询表具失败!RoomManageServiceImpl.getMeterByCodeAndName()。");
		}
		return list;
	}
  
   @Override
	public List<EMeter> getMeterByCodeAndName(String meterCode, String meterName)throws BkmisServiceException {
			
		List<EMeter> list = null;
		try{
			 list = imeterManageDao.queryMeterByCodeAndType(meterCode, meterName);
		}catch(Exception e){
			logger.error("根据表具编号查询表具失败!RoomManageServiceImpl.getMeterByCodeAndName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据表具编号查询表具失败!RoomManageServiceImpl.getMeterByCodeAndName()。");
		}
		return list;
	}
   
   @Override
	 public String getMeterTypeByCodeName(String meterName)throws BkmisServiceException{
		
		String meterType = null;
		try{
			SysCode syscode =  iSysParamManagerDAO.getCodeByName(meterName);
			meterType = syscode.getCodeValue();
		}catch(DataAccessException e){
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterType;
	}
   
   @Override
	public void deleteMeter(String meterCode, String meterType) throws BkmisServiceException {
		try{
			imeterManageDao.deleteMeter(meterCode,meterType);
		}catch(Exception e){
			logger.error("表具信息删除失败!RoomManageServiceImpl.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具信息删除失败!RoomManageServiceImpl.deleteMeter()。");
		}
	}
   
   @Override
	public void deleteMeterByRoomId(List<String> idList) throws BkmisServiceException {
		try {
			imeterManageDao.deleteMeterByRoomId(idList);
		} catch (Exception e) {
			logger.error("根据房间id删除表具信息失败!RoomManageServiceImpl.deleteMeterByRoomId()。详细信息："+ e.getMessage());
			throw new BkmisServiceException("根据房间id删除表具信息失败!RoomManageServiceImpl.deleteMeterByRoomId()。");
		}
	}

   @Override
   public List getEMeterByRoomId(Integer roomId) throws BkmisServiceException {
	   
	   List list = null;
		try{
			 list = imeterManageDao.queryMeterByRoomId(roomId);
		}catch(Exception e){
			logger.error("根据房间id查询表具信息失败!RoomManageServiceImpl.getEMeterByRoomId()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据房间id查询表具信息失败!RoomManageServiceImpl.getEMeterByRoomId()。");
		}
		return list;
}

   @Override
   public List getEquipByRoomId(Integer roomId) throws BkmisServiceException {
	   
	    List equiplist = null;
		try{
			equiplist = iroomManageDao.queryEquipByRoomId(roomId);
		}catch(Exception e){
			logger.error("根据房间id查询表具信息失败!RoomManageServiceImpl.getEMeterByRoomId()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据房间id查询表具信息失败!RoomManageServiceImpl.getEMeterByRoomId()。");
		}
		return equiplist;
  }

   @Override
   public void deleteEquip(Integer equipId,String userName) throws BkmisServiceException {

	   try{
		   ERoomFixture equip =  (ERoomFixture) iroomManageDao.getObject(ERoomFixture.class, equipId);

			//添加系统日志
		   iroomManageDao.logDetail(userName,Contants.DELETE,"房间设备",Contants.L_BUILD,"3",equip);
		   iroomManageDao.deleteObject(equip);
		}catch(Exception e){
			logger.error("房间设备信息删除失败!RoomManageServiceImpl.deleteEquip()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("房间设备信息删除失败!RoomManageServiceImpl.deleteEquip()。");
		}
  }

   @Override
   public int getRoomNumber(String floor,Integer id,String tablename,String roomStatus) throws BkmisServiceException {

	   int roomnum = 0;
	   Integer lpId = null;
	   Integer buildId = null;
	   if(Contants.E_LP.equals(tablename)){//如果是楼盘
		   lpId = id;
	   }
	   if(Contants.E_BUILD.equals(tablename)){//如果是楼栋
		   buildId = id;
	   }
	   try{
		   roomnum = iroomManageDao.getRoomNum(lpId,buildId, floor,roomStatus);
	   }catch(DataAccessException e){
			logger.error("获得房间数失败!IRoomManageDAO.getRoomNum()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获得房间数失败!IRoomManageDAO.getRoomNum()。");
		}
	   return roomnum;
   }

   @Override
   public List getRoomInfo(String floor,Integer id,String tablename)
		throws BkmisServiceException {
	
	   List list = null;
	   Integer lpId = null;
	   Integer buildId = null;
	   if(Contants.E_LP.equals(tablename)){//如果是楼盘
		   lpId = id;
	   }
	   if(Contants.E_BUILD.equals(tablename)){//如果是楼栋
		   buildId = id;
	   }
	   try{
		  list = iroomManageDao.getRoomInfo(lpId,buildId, floor);
	   }catch(DataAccessException e){
			logger.error("获得房间信息失败!IRoomManageDAO.getRoomInfo()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获得房间信息失败!IRoomManageDAO.getRoomInfo()。");
	   }
	   return list;
   }

   @Override
   public List getClient(String sroomId)throws BkmisServiceException{
	   
	   List list = null;
	   Integer roomId = null;
	   if(!"".equals(sroomId)){
		   roomId = Integer.parseInt(sroomId);
	   }
	   list = iroomManageDao.getRoomClientInfo(roomId);
	   return list;
   }
  
   @Override
   public List<ERoomClient> getHistoryClientInfo(Integer roomId)
		throws BkmisServiceException {
	
	   List<ERoomClient> list = null;
	   try{
		   list = iroomManageDao.getHistoryClientInfo(roomId);
	   }catch(DataAccessException e){
			logger.error("获得房间信息失败!IRoomManageDAO.getHistoryClientInfo()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获得房间信息失败!IRoomManageDAO.getHistoryClientInfo()。");
		}
	   return list;
  }
}
