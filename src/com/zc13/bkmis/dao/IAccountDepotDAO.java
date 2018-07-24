package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AccountDepotForm;
import com.zc13.bkmis.mapping.DepotAdjustAccounts;

public interface IAccountDepotDAO extends  BasicDAO {
	
	//显示查询库存核算信息
	public List showAccount(AccountDepotForm adf) throws DataAccessException;
	//查看库存核算的详细信息
	public List showDetailAccount(AccountDepotForm adf) throws DataAccessException;
	//查看核算信息的记录条数
	public int queryCountTotal(AccountDepotForm adf) throws DataAccessException;
	//按id查询结算信息
	public List selectAccountById(int id) throws DataAccessException;
	//添加月结算
	public List doAddAccount(AccountDepotForm adf) throws DataAccessException;
	//按id查询要删除的详细信息内容
	public List<DepotAdjustAccounts> selectDepotAccount(Integer[] idArray) throws DataAccessException;
	//更改出入库的状态
	public void updateStatus(AccountDepotForm adf) throws DataAccessException;
	/**
	 * 查询库存核算
	 * @param accounts
	 * @return
	 * @throws DataAccessException
	 * Date:Apr 29, 2011 
	 * Time:2:38:32 PM
	 */
	public List<DepotAdjustAccounts> getAccount(DepotAdjustAccounts accounts) throws DataAccessException;
}
