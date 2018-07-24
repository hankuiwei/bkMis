package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CostTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.msmis.mapping.SysCode;

/**
 * 费用类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：1:57:11 PM
 */
public interface ICostTypeDAO  extends BasicDAO{
	/**
	 * 获得费用类型列表
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public List<CCosttype> getCostTypeList(CostTypeForm obj) throws DataAccessException;
	
	/**
	 * 保存费用类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveCostType(CCosttype obj) throws DataAccessException ;
	
	/**
	 * 更新费用类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void updateCostType(CCosttype obj) throws DataAccessException ;
	
	/**
	 * 删除费用类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void deleteCostType(CCosttype obj) throws DataAccessException ;

	/**
	 * 获得收费类型参数列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<CCostparatype> getCostparatypeList()throws DataAccessException ;

	/**
	 * 获得表具列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<SysCode> getGaugeList()throws DataAccessException ;

}
