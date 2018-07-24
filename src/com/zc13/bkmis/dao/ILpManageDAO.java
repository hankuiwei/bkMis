package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.LpForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:26 PM
 */
public interface ILpManageDAO extends  BasicDAO{
    
	/** 查询是否已存在当前的楼盘名称 */
	public List<ELp> queryLpByName(String lpName) throws DataAccessException;
	
	/** 查询楼盘楼盘信息 
	 * （有查询条件的情况下：根据名称模糊查询等）
	 * @return
	 */
	public List<ELp> queryLp(LpForm lpForm) throws DataAccessException;
	
	/** 查询楼盘楼盘信息 
	 * @return
	 */
	public List<ELp> queryLp() throws DataAccessException;
	
	
	/** 添加楼盘 */
    public void saveLp(ELp elp) throws DataAccessException;
    
    /** 根据id得到指定楼盘的信息 */
    public List<ELp> queryLpById(Integer lpId) throws DataAccessException;
    
    /** 删除楼盘信息 */
    public void deleteLp(List<String> idList) throws DataAccessException;
    /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(LpForm lpForm);
    
    /** 查询当前是否已存在指定名称和表具编号的表具*/
	public List<EMeter> queryMeterByCodeAndName(String meterCode,String meterName) throws DataAccessException;
    
	/** 从系统参数表里得到表具类型 */
	public List<SysCode> queryMeterType() throws DataAccessException;
	
	/** 根据楼盘id查询表具信息 */
	public List queryMeterByLpId(Integer lpId)throws DataAccessException;
	
	/** 根据编剧编号删除表具信息 */
	public void deleteMeter(String meterCode,String meterName) throws DataAccessException;
	
	/** 根据楼盘编号删除表具信息 */
	public void deleteMeterBylpId(List<String> idList) throws DataAccessException;
}

