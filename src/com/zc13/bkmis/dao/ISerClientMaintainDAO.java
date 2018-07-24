package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.mapping.SerMaterialConsume;
import com.zc13.exception.BkmisServiceException;

public interface ISerClientMaintainDAO  extends BasicDAO {
	/**
	 * 查询客户报修列表
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<SerClientMaintain> getClientList(SerClientMaintainForm form,boolean isPage) throws DataAccessException;
	
	public List<SerClientMaintain> getClientByName(String name) throws DataAccessException;
	
	public List<SerClientMaintain> getClientByCode(String code) throws DataAccessException;
	
	public List<SerMaterialConsume> getConsume(int id)throws DataAccessException;
	
	/**
	 * 查询材料出处列表
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<SerMaterialConsume> queryConsume(SerClientMaintainForm form,boolean isPage) throws DataAccessException;
	
	//查询材料出处,用于导出报表
	public List<SerMaterialConsume> explorMaterialList(String department,String materialName,String begin,String end) throws DataAccessException;

	/**
	 * 处理执行减少领料操作
	 * */
	public void updateDepotMaterial(String bh,String sl);
	
	/**
	 * 根据code获取材料数据
	 * */
	public String getDostack(String code) throws BkmisServiceException;
}
