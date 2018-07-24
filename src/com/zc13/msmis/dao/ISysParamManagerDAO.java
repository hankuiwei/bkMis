package com.zc13.msmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.msmis.form.SysParamForm;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.mapping.SysCodeType;

/**
 * 系统参数维护相关
 * @author fengsen
 * Date：Nove,18, 2010
 * Time：15:38:15
 */

public interface ISysParamManagerDAO extends BasicDAO{
	
	/**
	 * 得到对应得参数及名称
	 * 
	 * @return
	 */
	public List getSysParam() throws DataAccessException;
	
	/**
	 * 获得确定的参数信息
	 * 
	 * @param codeTypeId
	 * @return
	 */
	public List<SysParamForm> getSysParam(int codeTypeId) throws DataAccessException;
	
	/**
	 * 通过数据名称和数据类型查询一类数据名称
	 * 
	 * @param codeName
	 * @param codeType
	 * @return
	 * @throws DataAccessException
	 */
	public SysCodeType getSysCodeType(String codeName) throws DataAccessException;
	
	public SysCodeType getSysCodeType1(String codeValue) throws DataAccessException;
	
	/**
	 * 改变数据类型查看
	 * 
	 * @param typeValue
	 * @return
	 * @throws DataAccessException
	 */
	public List<SysParamForm> getChoose(String typeValue) throws DataAccessException;

	/**houxj 
	 * 根据codeValue获得数据库里系统参数信息 */
	public SysCode getCodeByValue(String codeValue) throws DataAccessException;
	
	public SysCodeType getCodeTypeByValue(String codeTypeValue) throws DataAccessException;
	
	public List getTest() throws DataAccessException;
	
	/** houxj
	 * 根据codename的获取SysCode */
	public SysCode getCodeByName(String code) throws DataAccessException;
	
	/**houxj
	 *  查询系统参数类型里面id最小的 */
	public List getSysCodeTypeByMinId() throws DataAccessException;

	/** houxj
	 * 根据参数类型和，名称获取参数信息 */
	public SysCode getPrama(String type,String name) throws DataAccessException;

	/**
	 * 根据视图中的parentId获得Id的值
	 * ISysParamManagerDAO.getParentIdByViewId
	 */
	public Integer getParentIdByViewId(Integer parentId);

	/**
	 * 获得参数列表
	 * @param sysCode
	 * @return
	 */
	public List<SysCode> getSysCode(SysCode sysCode) throws DataAccessException;
}
