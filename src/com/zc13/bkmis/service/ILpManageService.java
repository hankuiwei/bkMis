package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.LpForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:39 PM
 */
public interface ILpManageService {
   
	/** 根据楼盘名称查询楼盘信息 */
	public List<ELp> queryLpByName(String lpName) throws BkmisServiceException;
	
	/** 获得楼盘信息列表 */
	public List<ELp> getLp(LpForm lpForm)throws BkmisServiceException;
	
	/** 获得楼盘信息列表 */
	public List<ELp> getLp()throws BkmisServiceException;
	
	/** 添加楼盘 */
	public void addLp(LpForm lpForm) throws BkmisServiceException;
	
	/** 检查楼盘名称是否以经存在 */
	public boolean checkLpName(String lpName) throws BkmisServiceException;
	
	/** 根据id得到指定的ELp对象 */
	public ELp getELpById(Integer lpId) throws BkmisServiceException;
	
	/** 修改楼盘信息 */
	public void modifyLp(LpForm lpForm) throws BkmisServiceException;
	
	/** 删除楼盘信息 */
	public void deleteLp(List<String> idList,String userName) throws BkmisServiceException;
	 /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(LpForm lpForm);
    
    /** 从系统参数表里得到表具类型 */
    public List<SysCode> getMeterType() throws BkmisServiceException;
    
    /** 检查数据库里是否已经存在用户想要添加的表具 
     * @param lpForm
     * @return
     * @throws Exception
     */
	public boolean checkMeter(LpForm lpForm) throws BkmisServiceException;
	
	/** 添加表具 */
	public void addMeter(LpForm lpForm) throws BkmisServiceException;
	
	 /** 从系统参数表里得到表具类型对应的codeValue值 */
    public String getMeterTypeByCodeName(String meterName) throws BkmisServiceException;
	
	/** 从系统参数里获取表具的名称 */
	public String getMeterName(LpForm lpForm)throws BkmisServiceException;
	
	/** 根据表具编号,和表具名称查询表具信息 */
	public List<EMeter> getMeterByCodeAndName(String meterCode,String meterName) throws BkmisServiceException; 
	
	/** 根据楼盘id得到表具信息 */
	public List getEMeterByLpId(Integer lpId) throws BkmisServiceException;
	
	/** 删除表具信息 */
	public void deleteMeter(String meterCode,String meterType) throws BkmisServiceException;
	
	/** 根据楼盘id删除表具信息 */
	public void deleteMeterBylpId(List<String> idList) throws BkmisServiceException;
			
}
