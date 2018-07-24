package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.FinancialBudgetForm;
import com.zc13.bkmis.mapping.FinancialBudget;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IFinancialBudgetService extends IBasicService {
	
	public List<FinancialBudget> getList(FinancialBudgetForm financialBudgetForm,boolean isPage) throws BkmisServiceException;

	public int queryCounttotal(FinancialBudgetForm financialBudgetForm);
	public void addfinan(FinancialBudgetForm form) throws BkmisServiceException;
	public void delfin(String id) throws BkmisServiceException;
	public FinancialBudget getfin(String id) throws BkmisServiceException;
	public void editfin(FinancialBudgetForm form) throws BkmisServiceException;
	public List<MUser> getUserList() throws Exception;

}
