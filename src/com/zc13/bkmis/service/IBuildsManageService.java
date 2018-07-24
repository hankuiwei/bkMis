package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.BuildForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EFloorMap;
import com.zc13.bkmis.mapping.EFloorMapCoordinate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：2:17:07 PM
 */
public interface IBuildsManageService {

	/** 获得楼栋 */
	public List<EBuilds> getBuilds(BuildForm buildForm) throws Exception;
	
	/** 查询数据库里楼栋最小的楼栋对象 */
	public List getBuild(BuildForm buildForm) throws Exception;
	
	/** 检查楼栋编号是否已经存在 */
	public boolean checkBuildCode(BuildForm buildForm) throws Exception;
	
	/** 检查楼栋名称是否已经存在 */
	public boolean checkBuildName(BuildForm buildForm) throws Exception;
	
	/** 检查楼栋名称是否已经存在 */
	public boolean checkBuildNameAndCode(BuildForm buildForm) throws Exception;
	
	/** 添加楼栋 */
	public void addBuild(BuildForm buildForm) throws Exception;
	
	/** 根据id获得指定的楼栋对象 */
	public EBuilds getEBuildsById(Integer buildId) throws Exception;
	
	/** 修改楼栋信息 */
	public void modifyBuild(BuildForm buildForm) throws Exception;
	
	/** 删除楼栋信息 */
	public void deletebuild(List<String> buildList,String userName) throws Exception;
	
	/** 根据楼盘名称,楼栋查寻楼栋信息 */
	public List<EBuilds> getBuildsByELp(ELp ELp,String buildName) throws Exception;
	
	/** 取记录总条数 */
	public int queryCounttotal(BuildForm buildForm) throws Exception;
	
	/** 查询图片大小 */
	public Map getPSize(String psize) throws Exception;
	
	/** 用来获得楼栋的平面图信息 */
	public EFloorMap getEFloorMap(String floor,Integer buildId1,String tablename) throws Exception;
	
	/** 添加平面展示图片 */
	public void addPlanMap(BuildForm buildForm) throws Exception;
	
	 /** 检查数据库里是否已经存在用户想要添加的表具 
	  *  同一种类型的表中不能有重复的表编号
     * @param lpForm
     * @return
     * @throws Exception
     */
	public boolean checkMeter(BuildForm buildForm) throws Exception;
	
	/** 根据lpId和表具名称查询表具信息，用于楼栋表具的所属总表（楼盘的表）之用 */
	public List<EMeter> getMeterByLpIdAndName(Integer lpId,String meterName) throws BkmisServiceException;
	
	/** 添加表具 */
	public void addMeter(BuildForm buildForm) throws Exception;
	
	/** 根据表具编号,和表具名称查询表具信息 */
	public List<EMeter> getMeterByCodeAndName(String meterCode,String meterName) throws Exception; 
	
	 /** 从系统参数表里得到表具类型 */
    public List<SysCode> getMeterType() throws Exception;
    
    /** 从系统参数表里得到表具类型对应的codeValue值 */
    public String getMeterTypeByCodeName(String meterName) throws BkmisServiceException;
    
    /** 从系统参数里获取表具的名称 */
	public String getMeterName(BuildForm buildForm)throws BkmisServiceException;
    
    /** 删除表具信息 */
	public void deleteMeter(String meterCode,String meterType) throws Exception;
	
	/** 根据楼栋id删除表具信息 */
	public void deleteMeterByBuildId(List<String>  buildList) throws Exception;
	
	/** 根据楼栋id得到表具信息 */
	public List getEMeterByBuildId(Integer buildId) throws Exception;
	
	/** 平面图上传和保存 */
	public String upload(BuildForm uploadForm,String filePath)throws Exception; 
	
	/** 保存楼层平面图上的房间图的信息 */
	public Integer addRoomClient(String x,String y,String roomWidth,String chartId,String sroomId,String planSize) throws Exception; 
	
	/**
	 * 删除地图上的房间标记
	 * @param chartId
	 * @throws Exception
	 * Date:Apr 28, 2011 
	 * Time:10:02:06 AM
	 */
	public void deleteRoomMark(Integer chartId) throws Exception;
	
	/** 获取房间图的信息 */
	public List<EFloorMapCoordinate> getRoomMap(String schartId) throws Exception;
	
	/** 获取楼层图大，小图显示的大小 */
	public Map getChartSize() throws Exception;
	
	/** 获取楼层图片显示的大小与默认的图的长宽比例 */
	public Map getProportionOfChart(Map map) throws Exception;
	
	/** 重新上传一个楼层平面图的时候把原来房间图的信息给删除掉 */
	public void deleteRoomMap(String floor,Integer buildId) throws BkmisServiceException; 
	
	/** 根据房间号检查房间图，看是否已经存在房间图，避免重复 */
	public boolean checkRoomMap(String sroomId) throws BkmisServiceException;
	/**
	 * 删除那些表具表中的垃圾信息
	 * IBuildsManageService.deleteRMeter
	 */
	public void deleteRMeter() throws BkmisServiceException;

	/**
	 * 获得房间和对应的已预租或正在租住的客户的信息
	 * @param roomId
	 * @return
	 * Date:Apr 27, 2011 
	 * Time:10:23:05 AM
	 */
	public List<Map> getClientAndRoomById(Integer roomId);
}
