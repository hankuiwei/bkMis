package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.MerchantsActualForm;
import com.zc13.bkmis.mapping.MerchantsActual;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IMerchantsActualService extends IBasicService {
	

	/** 获得楼盘信息列表 */
	public List<MerchantsActual> getList(MerchantsActualForm MerchantsActualForm,boolean isPage)throws BkmisServiceException;
	public int queryCounttotal(MerchantsActualForm MerchantsActualForm);
	public void addClient(MerchantsActualForm form)throws BkmisServiceException; 
	public void delMerchantsActual(String id) throws BkmisServiceException;
	public MerchantsActual getMerchantsActual(String id) throws BkmisServiceException;
	public void editMerchantsActual(MerchantsActualForm form) throws BkmisServiceException;
	public List<MUser> getUserList() throws Exception;
	
	/**
	 * 查询计划分配人
	 * @return
	 * Date:2013-5-8 
	 * Time:下午10:26:04
	 */
	public List<SysCode> queryCodeByType();
}
