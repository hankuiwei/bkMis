package com.zc13.bkmis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.BuildForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EFloorMap;
import com.zc13.bkmis.mapping.EFloorMapCoordinate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.exception.BkmisServiceException;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：2:17:07 PM
 */
public interface IBuildsManageDAO extends BasicDAO{

	/** 根据名称查询数据库 */
	public List<EBuilds> queryBuildByName(String buildName) throws DataAccessException;
	
	/** 根据elp对象查询楼盘,暂时未用到,处于保留状态 */
	public List<EBuilds> queryBuildByCLN(String buildCode,ELp ELp,String buildName) throws DataAccessException;
	
	/** 根据elp对象查询楼盘 */
	public List<EBuilds> queryBuildsByELpAndCode(ELp ELp,String buildCode) throws DataAccessException;
	
	/** 根据elp对象查询楼盘 */
	public List<EBuilds> queryBuildByELpAndName(ELp ELp,String buildName) throws DataAccessException;
	
	/** 根据elp对象,和楼盘名称的模糊查询查询楼盘 */
	public List<EBuilds> queryBuildsByELp(ELp ELp,String buildName) throws DataAccessException;
	
	/** 查询楼栋列表，分页 */
	public List<EBuilds> queryBuild(BuildForm buildForm) throws DataAccessException;
	
	/**
     * 取记录总条数
     * @param lpForm
     * @return
     */
	public int queryCounttotal(BuildForm buildForm);
	
	/**
	 * 用来获得楼栋的平面图信息
	 * @param floorMap
	 * @return
	 * @throws DataAccessException
	 * Date:Apr 26, 2011 
	 * Time:10:21:40 AM
	 */
	public List<EFloorMap> getEfloorMap(EFloorMap floorMap) throws DataAccessException;
	
	/** 查询图片大小 */
	public Map queryPSize(String psize) throws DataAccessException;
	
	/** 根据楼层平面图获取房间图 */
	public List getRoomMap(EFloorMap chart) throws DataAccessException;
	
    /** 根据房间号删除房间图信息 */
	public void deleteRoomMap(Integer roomId) throws DataAccessException;
	
	/** 根据房间id查寻房间图信息 */
	public List<EFloorMapCoordinate> getRoomMap(Integer roomId) throws DataAccessException;
	
	/**
	 * 删除那些表具表中的垃圾信息
	 * IBuildsManageService.deleteRMeter
	 */
	public void deleteRMeter() throws BkmisServiceException;

	/**
	 * 获得房间和对应客户的信息
	 * @param roomId
	 * @return
	 * Date:Apr 27, 2011 
	 * Time:10:24:37 AM
	 */
	public List<Map> getClientAndRoomById(Integer roomId);
	
}
