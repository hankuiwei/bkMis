package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.MerchantsPlanForm;
import com.zc13.bkmis.mapping.MerchantsPlan;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IMerchantsPlanService extends IBasicService {
	

	/** 获得楼盘信息列表 */
	public List<MerchantsPlan> getList(MerchantsPlanForm merchantsPlanForm,boolean isPage)throws BkmisServiceException;
	public int queryCounttotal(MerchantsPlanForm merchantsPlanForm);
	public void addClient(MerchantsPlanForm form)throws BkmisServiceException; 
	public void delMerchantsPlan(String id) throws BkmisServiceException;
	public MerchantsPlan getMerchantsPlan(String id) throws BkmisServiceException;
	public void editMerchantsPlan(MerchantsPlanForm form) throws BkmisServiceException;
	public List<MUser> getUserList() throws Exception;
	
	/**
	 * 查询计划分配人
	 * @return
	 * Date:2013-5-8 
	 * Time:下午10:26:04
	 */
	public List<SysCode> queryCodeByType();
}
