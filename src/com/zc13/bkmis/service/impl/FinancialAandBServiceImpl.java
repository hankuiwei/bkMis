package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IFinancialAandBDAO;
import com.zc13.bkmis.form.FinForm;
import com.zc13.bkmis.service.IFinancialAandBService;
/***
 * @author 李影
 */
public class FinancialAandBServiceImpl extends BasicServiceImpl implements IFinancialAandBService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialAandBDAO ifinancialAandBDao;

	public IFinancialAandBDAO getIfinancialAandBDao() {
		return ifinancialAandBDao;
	}

	public void setIfinancialAandBDao(IFinancialAandBDAO ifinancialAandBDao) {
		this.ifinancialAandBDao = ifinancialAandBDao;
	}
	
	/**
	 * 查询列表总数
	 * @param basicForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:53:00
	 */
	public int queryCounttotal(FinForm form){
    	return ifinancialAandBDao.queryCounttotal(form);
    }


    /**
     * 查询列表
     */
    public List getAandBList(FinForm form) {
    	List list = ifinancialAandBDao.queryAandB(form);
    	return list;
    }
 
}
