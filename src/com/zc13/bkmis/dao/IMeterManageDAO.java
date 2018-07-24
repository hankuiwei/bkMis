package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.mapping.EMeter;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:26 PM
 */
public interface IMeterManageDAO extends  BasicDAO{
    
    /** 查询当前是否已存在指定名称和表具编号的表具*/
	public List<EMeter> queryMeterByCodeAndType(String meterCode,String meterType) throws DataAccessException;
    
	/** 从系统参数表里得到表具类型 */
	public List<SysCode> queryMeterType() throws DataAccessException;
	
	/** 根据楼盘id查询表具信息 */
	public List queryMeterByLpId(Integer lpId)throws DataAccessException;
	
	/** 根据编剧编号和名称删除表具信息 */
	public void deleteMeter(String meterCode,String meterType) throws DataAccessException;
	
	/** 根据楼盘id删除表具信息 */
	public void deleteMeterBylpId(List<String> idList) throws DataAccessException;
	
	/** 根据楼栋id删除表具信息 */
	public void deleteMeterByBuildId(List<String>  buildList) throws DataAccessException;
	
	/** 根据房间id删除表具信息 */
	public void deleteMeterByRoomId(List<String>  idList) throws DataAccessException;
	
	/** 根据指定的房间id删除表具信息 */
	public void deleteMeterByRoomId(Integer roomId) throws DataAccessException;
	
	/** 根据楼栋id查询表具信息 */
	public List queryMeterByBuildId(Integer buildId)throws DataAccessException;
	
	/** 根据楼盘id和表具名称查询表具信息 */
	public List<EMeter> MeterByLpIdAndName(Integer lpId,String meterName)throws DataAccessException;
	
	/** 根据楼栋id和表具名称查询表具信息 */
	public List<EMeter> MeterByBuildIdAndName(Integer buildId,String meterName)throws DataAccessException;
	
	/** 利用表具的类型，名称，所属总表查询表具 */
	public List<EMeter> queryMeterByCTP(String meterCode,String meterName,Integer parentId) throws DataAccessException;
	
	/** 根据房间id查询表具信息 */
	public List queryMeterByRoomId(Integer roomId)throws DataAccessException;
}

