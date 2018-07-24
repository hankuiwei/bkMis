package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 侯哓娟
 * Date：Nov 23, 2010
 * Time：5:16:33 PM
 */
public interface IRoomManageService {

	/** 得到高级查询所需的房间类型信息和朝向信息 */
	public Map<String, List<SysCode>> getAdvancedInfo(Integer lpId) throws BkmisServiceException;
	
	/** 查询房间 
	 * 添加分页 */
	public List<ERooms> getRoom(RoomForm roomForm) throws BkmisServiceException;
	
	/** 按照条件查询房间 */
	public List<ERooms> getRoomE(RoomForm roomForm) throws BkmisServiceException;
	
	/** 建树 */
	public List<ViewBuild> getInfoForTree(RoomForm roomForm)throws BkmisServiceException;
	
	/** 根据导航查询房间 */
	public List<ERooms> getRoomByLpOrBuild(Integer id,String tablename,RoomForm roomForm) throws BkmisServiceException;
	
	/** 根据房间号检查房间是否已经存在 */
	public boolean checkRoomCode(String roomCode,EBuilds ebuild) throws BkmisServiceException;
	
	/** 添加房间 */
	public void addRoom(HttpServletRequest request,RoomForm roomForm) throws BkmisServiceException;
	
	/**根据房间id得到指定房间信息做修改之用 */
	public ERooms getERoomsById(Integer roomId) throws BkmisServiceException;
	
	/**
	 * 得到房间客户入住信息
	 * @param floor 楼层号
	 * @param id 楼盘或楼栋id
	 * @param tablename 表名(是楼盘还是楼栋关系着上面id指代是楼盘还是楼栋)
	 * @return 
	 * @throws BkmisServiceException
	 * Date:Apr 26, 2011 
	 * Time:11:20:07 AM
	 */
	public List getRoomInfo(String floor,Integer id,String tablename)throws BkmisServiceException;
	
	/** 根据房间查询入住客户 */ 
	public List getClient(String sroomId)throws BkmisServiceException;
	
   /**
    * 根据条件获得房间数量
    * @param floor 楼层号
    * @param id 楼盘或楼栋id
    * @param tablename 表名(是楼盘还是楼栋关系着上面id指代是楼盘还是楼栋)
    * @param roomStatus 房间状态
    * @return
    * @throws BkmisServiceException
    * Date:Apr 26, 2011 
    * Time:10:48:00 AM
    */
	public int getRoomNumber(String floor,Integer id,String tablename,String roomStatus) throws BkmisServiceException;
	
	/** 获取房间历史住户信息 */
	public List<ERoomClient> getHistoryClientInfo(Integer roomId)throws BkmisServiceException;
	
	/** 修改房间信息 */
	public void midifyRoom(HttpServletRequest request,RoomForm roomForm) throws BkmisServiceException;
   
	/** 获取房间客户信息，用于判断所选中需删除的房间是否还有客户入驻 */
	public String deleteRoom(String sroomId,String userName)throws BkmisServiceException;
	
   /** 删除房间 */
//   public void deleteRoom(List<String> idList) throws BkmisServiceException;
//   public void deleteRoom(String sroomId) throws BkmisServiceException;
   
   /** 房间的批量增加 */
   public void batchincreaseRoom(RoomForm roomForm) throws BkmisServiceException;
   
   /** 取记录总条数,在有查询条件的情况下 */
   public int queryCounttotal(RoomForm roomForm);
   
   /** 根据树的不同导航点击得到房间总的记录数 */
   public int getRoomNumByTablename(Integer id,String tablename)throws BkmisServiceException;
   
   /** 获取房间客户信息的总条数 */
   public int queryCounttotal(Integer roomId)throws BkmisServiceException;
   
   /** 从系统参数表里得到表具类型 */
   public List<SysCode> getMeterType() throws BkmisServiceException;
   
   /** 根据buildId和表具名称查询表具信息，用于房间表具的所属总表之用 */
   public List<EMeter> getMeterByBuildIdAndName(Integer buildId,String meterName) throws BkmisServiceException;
   
   /** 根据buildId和表具名称查询表具信息，用于房间表具的所属总表之用 */
   public boolean getMeterByBuildId(Integer buildId) throws BkmisServiceException;
   
   /** 检查数据库里是否已经存在用户想要添加的表具 
	  *  同一种类型的表中不能有重复的表编号
   * @param lpForm
   * @return
   * @throws Exception
   */
	public boolean checkMeter(RoomForm roomForm) throws BkmisServiceException;
	
	/** 添加表具 */
	public void addMeter(RoomForm roomForm) throws BkmisServiceException;
	
	 /** 从系统参数里获取表具的名称 */
	public String getMeterName(RoomForm roomForm)throws BkmisServiceException;
	
	/** 根据表具编号,和表具名称查询表具信息 */
	public List<EMeter> getMeterByCTP(String meterCode,String meterName,Integer parentId) throws BkmisServiceException; 
	
	 /** 从系统参数表里得到表具类型对应的codeValue值 */
    public String getMeterTypeByCodeName(String meterName) throws BkmisServiceException;
	
	 /** 删除表具信息 */
	public void deleteMeter(String meterCode,String meterType) throws BkmisServiceException;
	
	/** 根据房间id删除表具信息 */
	public void deleteMeterByRoomId(List<String> idList) throws BkmisServiceException;
	
	/** 根据表具编号,和表具名称查询表具信息 */
	public List<EMeter> getMeterByCodeAndName(String meterCode,String meterName) throws BkmisServiceException; 
	
	/** 根据房间id得到表具信息 */
	public List getEMeterByRoomId(Integer roomId) throws BkmisServiceException;
	
	/** 根据房间id得到房间设备信息 */
	public List getEquipByRoomId(Integer roomId) throws BkmisServiceException;
	
	 /** 删除房间设备信息 */
	public void deleteEquip(Integer equipId,String userName) throws BkmisServiceException;
}
