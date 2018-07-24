package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.FinancialBudgetForm;
import com.zc13.bkmis.mapping.FinancialBudget;

public interface IFinancialBudgetDAO  extends BasicDAO {
	
	public List<FinancialBudget> queryFinancialBudget(FinancialBudgetForm financialBudgetForm,boolean isPage) throws DataAccessException;
    
	public int queryCounttotal(FinancialBudgetForm financialBudgetForm);
	
	
}
