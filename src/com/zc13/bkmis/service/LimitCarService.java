package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.LimitCarForm;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.LimitCar;

public interface LimitCarService {

	/**
	 * 获取全部记录
	 * @return
	 * @throws BkmisServiceException
	 * Date:May 3, 2011 
	 * Time:10:46:18 AM
	 */
	public List getList() throws BkmisServiceException;
	/**
	 * 根据id获取一条记录
	 * @param limitCarForm
	 * @return
	 * @throws BkmisServiceException
	 * Date:May 3, 2011 
	 * Time:10:38:12 AM
	 */
	public LimitCar getById(LimitCarForm limitCarForm) throws BkmisServiceException;
	/**
	 * 修改某条记录
	 * @param limitCarForm
	 * @throws BkmisServiceException
	 * Date:May 3, 2011 
	 * Time:4:11:44 PM
	 */
	public void edit(LimitCarForm limitCarForm) throws BkmisServiceException;
	/**
	 * 删除一条记录
	 * @param limitCarForm
	 * @throws BkmisServiceException
	 * Date:May 3, 2011 
	 * Time:10:38:30 AM
	 */
	public void deleteById(LimitCarForm limitCarForm) throws BkmisServiceException;
	/**
	 * 新增一条记录
	 * @param limitCarForm
	 * @throws BkmisServiceException
	 * Date:May 3, 2011 
	 * Time:10:38:40 AM
	 */
	public void add(LimitCarForm limitCarForm) throws BkmisServiceException;
}
