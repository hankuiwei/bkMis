package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IMerchantsAandPDAO;
import com.zc13.bkmis.form.MerForm;
import com.zc13.bkmis.service.IMerchantsAandPService;
/***
 * @author 李影
 */
public class MerchantsAandPServiceImpl extends BasicServiceImpl implements IMerchantsAandPService {

	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsAandPDAO imerchantsAandPDao;
	
	public IMerchantsAandPDAO getImerchantsAandPDao() {
		return imerchantsAandPDao;
	}

	public void setImerchantsAandPDao(IMerchantsAandPDAO imerchantsAandPDao) {
		this.imerchantsAandPDao = imerchantsAandPDao;
	}


	/**
	 * 查询列表总数
	 * @param basicForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:53:00
	 */
	public int queryCounttotal(MerForm form){
    	return imerchantsAandPDao.queryCounttotal(form);
    }


    /**
     * 查询列表
     */
    public List getAandBList(MerForm form) {
    	List list = imerchantsAandPDao.queryAandB(form);
    	return list;
    }
}
