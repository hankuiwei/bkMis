package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.mapping.CBuildBillsLog;

/**
 * 生成账单日志记录表DAO
 * @author 王正伟
 * Date：Dec 29, 2010
 * Time：2:21:28 PM
 */
public interface IBuildBillsLogDAO extends BasicDAO{
	/**
	 * 保存
	 * @param obj
	 * @throws DataAccessException
	 */
	public Integer save(CBuildBillsLog obj) throws DataAccessException;
	
	/**
	 * 更新
	 * @param obj
	 * @throws DataAccessException
	 */
	public void update(CBuildBillsLog obj) throws DataAccessException;
	
	/**
	 * 查询
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public List<CBuildBillsLog> query(CBuildBillsLog obj) throws DataAccessException;
	
	/**
	 * 删除
	 * @param obj
	 * @throws DataAccessException
	 */
	public void delete(CBuildBillsLog obj) throws DataAccessException;
}
