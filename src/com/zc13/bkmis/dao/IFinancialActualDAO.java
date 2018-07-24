package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.FinancialActualForm;
import com.zc13.bkmis.mapping.FinancialActual;


public interface IFinancialActualDAO  extends BasicDAO {
	
	public List<FinancialActual> queryFinancialBudget(FinancialActualForm financialActualForm,boolean isPage) throws DataAccessException;
    
	public int queryCounttotal(FinancialActualForm financialActualForm);
	
	
}
