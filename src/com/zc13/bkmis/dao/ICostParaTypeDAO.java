package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CostParaTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;

/**
 * 计费参数类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：1:57:11 PM
 */
public interface ICostParaTypeDAO  extends BasicDAO{
	/**
	 * 获得计费参数类型列表
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public List<CCostparatype> getCostParaTypeList(CostParaTypeForm obj) throws DataAccessException;
	
	/**
	 * 保存计费参数类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveCostParaType(CCostparatype obj) throws DataAccessException ;
	
	/**
	 * 更新计费参数类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void updateCostParaType(CCostparatype obj) throws DataAccessException ;
	
	/**
	 * 删除计费参数类型
	 * @param obj
	 * @throws DataAccessException
	 */
	public void deleteCostParaType(CCostparatype obj) throws DataAccessException ;
	
	/**
	 * 根据类型名称列表查询
	 * @param typeNames
	 * @return
	 * @throws DataAccessException
	 */
	public List<CCostparatype> getCostParaTypeByNames(List typeNames) throws DataAccessException;

}
