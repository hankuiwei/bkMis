package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IFinancialActualDAO;
import com.zc13.bkmis.form.FinancialActualForm;
import com.zc13.bkmis.mapping.FinancialActual;
import com.zc13.bkmis.service.IFinancialActualService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
/***
 * @author 李影
 */
public class FinancialActualServiceImpl extends BasicServiceImpl implements
IFinancialActualService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialActualDAO ifinancialActualDao;
	public IFinancialActualDAO getIfinancialActualDao() {
		return ifinancialActualDao;
	}
	public void setIfinancialActualDao(IFinancialActualDAO ifinancialActualDao) {
		this.ifinancialActualDao = ifinancialActualDao;
	}


	

	@Override
	public List<FinancialActual> getList(FinancialActualForm financialActualForm,boolean isPage) throws BkmisServiceException {
		
		List<FinancialActual> list = null;
		try{
			list = ifinancialActualDao.queryFinancialBudget(financialActualForm, isPage);
		}catch(Exception e){
			logger.error("实际财务查询失败!FinancialActualServiceImpl.getList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("实际财务查询失败!FinancialActualServiceImpl.getList()。");
		}
		return list;
	}
	
	
    public int queryCounttotal(FinancialActualForm financialActualForm){
    	return ifinancialActualDao.queryCounttotal(financialActualForm);
    }
    
	 
  //保存实际财务信息
  	@Override
  	public void addfinan(FinancialActualForm form)
  			throws BkmisServiceException {
  		
  		FinancialActual obj = new FinancialActual();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e1) {
  			e1.printStackTrace();
  		} catch (InvocationTargetException e1) {
  			e1.printStackTrace();
  		}
  		try {
  			ifinancialActualDao.saveObject(obj);
  			
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
  	
  	
  //根据id得到实际财务信息
  	@Override
  	public FinancialActual getfin(String id) throws BkmisServiceException {
  		return (FinancialActual)ifinancialActualDao.getObject(FinancialActual.class,Integer.parseInt(id));
  	}
  	
  	
  //根据id编辑实际财务信息
  	@Override
  	public void editfin(FinancialActualForm form) throws BkmisServiceException {
  		
  		FinancialActual obj = new FinancialActual();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e) {
  			e.printStackTrace();
  		} catch (InvocationTargetException e) {
  			e.printStackTrace();
  		}
  		ifinancialActualDao.updateObject(obj);
  	}
  	
  //根据id删除实际账务信息
  	@Override
  	public void delfin(String id) throws BkmisServiceException {
  		FinancialActual bean = (FinancialActual)ifinancialActualDao.getObject(FinancialActual.class,Integer.parseInt(id));
  		ifinancialActualDao.deleteObject(bean);
  	}
  	
  	public List<MUser> getUserList() throws Exception {
		return ifinancialActualDao.getObjects(MUser.class);
	}
}
