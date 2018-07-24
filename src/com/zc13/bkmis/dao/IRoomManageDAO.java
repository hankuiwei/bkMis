package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 侯哓娟
 * Date：Nov 23, 2010
 * Time：5:16:01 PM
 */
public interface IRoomManageDAO extends BasicDAO {
    
	/** 查询房间类型 */
	public List<SysCode> queryRoomType(Integer lpId) throws DataAccessException;
	
	/** 查询房间朝向 */
	public List<SysCode> queryToward(Integer lpId) throws DataAccessException;
	
	/** 查询房间的使用状态 */
	public List<SysCode> queryStatus(Integer lpId) throws DataAccessException;
	
	/** 查询房间信息 */
	public List<ERooms> queryRoom(RoomForm roomForm) throws DataAccessException;
	
	/** 查询房间信息,无分页 */
	public List<ERooms> queryRoomE(RoomForm roomForm) throws DataAccessException;
	
	/** 查询建树所需的条件*/
	public List<ViewBuild> queryInfoForTree(RoomForm roomForm) throws DataAccessException;
	
	/** 查出一个楼盘下的所有的房间 */
	public List<ERooms> queryRoomforLp(ELp elp,RoomForm roomForm) throws DataAccessException;
	
	/** 根据楼栋去查询一房间 */
    public List<ERooms> queryRoomByBuild(Integer id,RoomForm roomForm) throws DataAccessException;
    
    /** 根据房间号查询房间 */
    public List<ERooms> queryRoomByRoomCode(String roomCode,EBuilds ebuild) throws DataAccessException;
    
    /** 获取房间住户信息 */
    public List getRoomClientInfo(Integer roomId) throws DataAccessException;
    
    /** 删除房间 */
    public void deleteRoom(List<String> idList) throws DataAccessException;
    
    /** 删除房间 */
    public void deleteEquip(List<String> idList) throws DataAccessException;
    
    /** 删除指定房间设备信息 */
    public void deleteEquip(Integer roomId) throws DataAccessException;
    
    /**  取房间的记录总条数,在有查询条件的情况下 */
    public int queryCounttotal(RoomForm roomForm)throws DataAccessException;
    
    /** 获取楼盘下的房间总数 */ 
    public List<ERooms> queryRoomNumforLp(ELp elp)throws DataAccessException;
    
    /** 获取楼栋下的房间总数 */ 
    public List<ERooms> queryRoomNumByBuild(EBuilds ebuild)throws DataAccessException;
    
    /** 获取房间客户信息的总记录条数 */
    public int queryCounttotal(Integer roomId)throws DataAccessException;
    
    /**
     * 统计房间的出租情况
     * @param lpId 楼盘id
     * @param buildId 楼栋id
     * @param floor 楼层号
     * @param roomStatus 房间状态
     * @return
     * @throws DataAccessException
     * Date:Apr 26, 2011 
     * Time:11:11:40 AM
     */
    public int getRoomNum(Integer lpId,Integer buildId, String floor,String roomStatus)throws DataAccessException;
    
    /**
     * 房间客户入驻情况
     * @param lpId 楼盘id
     * @param buildId 楼栋id
     * @param floor 楼层号
     * @return
     * @throws DataAccessException
     * Date:Apr 26, 2011 
     * Time:11:24:23 AM
     */
    public List getRoomInfo(Integer lpId,Integer buildId, String floor)throws DataAccessException;
    
    /** 查询某房间的历史入驻客户 */
    public List<ERoomClient> getHistoryClientInfo(Integer roomId)throws DataAccessException;
    
    /** 根据房间id查寻房间设备信息 */
    public List queryEquipByRoomId(Integer roomId) throws DataAccessException;
    
    /** 根据使用状态获取房间 */
    public List<ERooms> getRoomByStatus(String status) throws DataAccessException;
    
    /** 查询某楼栋，某楼层的房间 */
    public List<ERooms> getRoomsByEBuildsAndFloor(String floor, EBuilds build)throws DataAccessException;
   
}
