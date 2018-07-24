package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.FinancialActualForm;
import com.zc13.bkmis.mapping.FinancialActual;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;

/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IFinancialActualService extends IBasicService {
	
	public List<FinancialActual> getList(FinancialActualForm financialActualForm,boolean isPage) throws BkmisServiceException;
	

	public int queryCounttotal(FinancialActualForm financialActualForm);
	
	public void addfinan(FinancialActualForm form) throws BkmisServiceException;
	public void delfin(String id) throws BkmisServiceException;
	public FinancialActual getfin(String id) throws BkmisServiceException;
	public void editfin(FinancialActualForm form) throws BkmisServiceException;
	public List<MUser> getUserList() throws Exception;

}
