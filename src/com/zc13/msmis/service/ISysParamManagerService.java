package com.zc13.msmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;

/**
 * 系统参数相关
 * @author fengsen
 * Date：Nove,18, 2010
 * Time：15:38:15
 */
public interface ISysParamManagerService {
	
	/** 获得系统参数列 */
	public List getSysParamList() throws BkmisServiceException;
	
	/**
	 * 根据类型代码获得系统参数列
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SysCode> getCodeList() throws BkmisServiceException;
	
	/**
	 * 获得系统参数列表
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<SysCode> getSysCode(SysCode sysCode) throws BkmisServiceException;
	
	/**
	 * 检查数据名称
	 * @param codeId
	 * @param codeName
	 * @return
	 * @throws Exception
	 */
	public boolean checkCodeName(String codeName) throws BkmisServiceException;
	
	
	public boolean checkCodeTypeValue(String codeValue) throws BkmisServiceException;
    
	/**
	 * 通过Id和Name保存数据名称
	 * 
	 * @param codeId
	 * @param codeName
	 * @return
	 * @throws Exception
	 */
	public SysCode saveCodeById(int codeId,String codeName) throws BkmisServiceException;
	
	/**
	 * 转换数据类型
	 * 
	 * @param typeValue
	 * @return
	 * @throws Exception
	 */
	public Map chooseType(String typeValue) throws BkmisServiceException;
	
	/**
	 * 删除数据名称
	 * 
	 * @param codeId
	 * @throws Exception
	 */
	public void  delCode(String codeId,String userName) throws BkmisServiceException;
	
	/**
	 * 转向添加列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map goAdd(int id,String table) throws BkmisServiceException;
	
	/**
	 * 插入数据名称
	 * 
	 * @param codeType
	 * @param codeName
	 * @param userid
	 * @throws Exception
	 */
	public SysCode insertCode(String codeType,String codeName,String userName,String codeValue) throws BkmisServiceException;
	
	/**
	 * 检查数据类型名称
	 * 
	 * @param codeType
	 * @param codeName
	 * @return
	 * @throws Exception
	 */
	public boolean checkCodeNameTwo(String codeName) throws BkmisServiceException;
	
	public boolean checkValue(String value) throws BkmisServiceException;
	
	public void updateCode(int codeId, String codeName,String codeValue,String userName) throws BkmisServiceException;
	
	public void updateCodeType(int codeId,String codeName,String codeValue,int userId,String userName) throws BkmisServiceException;
	
	public List getTest();
	
	public boolean getCodeByName(String code) throws BkmisServiceException;
	
	/** 
	 *  @author houxj
	 * 获得系统参数表里id最小的 */
	public Integer getSysCodeTypeByMinId() throws BkmisServiceException;

	/**
	 * 根据视图中的parentId得到id
	 * ISysParamManagerService.getParentIdByViewId
	 */
	public Integer getParentIdByViewId(Integer parentId);
}
