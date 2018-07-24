package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.AccountDepotForm;
import com.zc13.bkmis.mapping.DepotAdjustAccounts;
import com.zc13.exception.BkmisServiceException;

public interface IAccountDepotService extends IBasicService{
	
	//显示查询核算信息列表
	public void showAccount(AccountDepotForm adf) throws BkmisServiceException;
	//查看核算的详细信息
	public void showDetailAccount(AccountDepotForm adf) throws BkmisServiceException;
	//查询核算信息的记录条数
	public int queryCountTotal(AccountDepotForm adf) throws BkmisServiceException;
	//按id查询结算信息
	public List selectAccountById(int id,AccountDepotForm adf) throws BkmisServiceException;
	//添加月结算
	public void doAddAccount(AccountDepotForm adf) throws BkmisServiceException;
	//删除库存核算信息
	public void delDepotAccount(AccountDepotForm form) throws BkmisServiceException;
	//按id查询要删除的详细信息内容
	public List<DepotAdjustAccounts> selectDepotAccount(Integer[] idArray) throws BkmisServiceException;
	//获取上次结算的结束日期
	public String getBiggestCode(String string) throws BkmisServiceException;
	/**
	 * 判断是否当前年月的结算记录已经存在
	 * @param form
	 * @return
	 * @throws BkmisServiceException
	 * Date:2011-5-13 
	 * Time:上午11:48:39
	 */
	public boolean ifAcount(AccountDepotForm form) throws BkmisServiceException;
}
