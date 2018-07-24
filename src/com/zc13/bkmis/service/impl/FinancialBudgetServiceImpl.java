package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IFinancialBudgetDAO;
import com.zc13.bkmis.form.FinancialBudgetForm;
import com.zc13.bkmis.mapping.FinancialBudget;
import com.zc13.bkmis.service.IFinancialBudgetService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
/***
 * @author 李影
 */
public class FinancialBudgetServiceImpl extends BasicServiceImpl implements
IFinancialBudgetService {
	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialBudgetDAO ifinancialBudgetDao;
	public IFinancialBudgetDAO getIfinancialBudgetDao() {
		return ifinancialBudgetDao;
	}
	public void setIfinancialBudgetDao(IFinancialBudgetDAO ifinancialBudgetDao) {
		this.ifinancialBudgetDao = ifinancialBudgetDao;
	}

	@Override
	public List<FinancialBudget> getList(FinancialBudgetForm financialBudgetForm,boolean isPage) throws BkmisServiceException {
		
		List<FinancialBudget> list = null;
		try{
			list = ifinancialBudgetDao.queryFinancialBudget(financialBudgetForm, isPage);
		}catch(Exception e){
			logger.error("财务预算信息查询失败!FinancialBudgetServiceImpl.getList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("财务预算信息查询失败!FinancialBudgetServiceImpl.getList()。");
		}
		return list;
	}
	
	 /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(FinancialBudgetForm financialBudgetForm){
    	return ifinancialBudgetDao.queryCounttotal(financialBudgetForm);
    }
    
    
  //保存财务预算信息
  	@Override
  	public void addfinan(FinancialBudgetForm form)
  			throws BkmisServiceException {
  		
  		FinancialBudget obj = new FinancialBudget();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e1) {
  			e1.printStackTrace();
  		} catch (InvocationTargetException e1) {
  			e1.printStackTrace();
  		}
  		try {
  			ifinancialBudgetDao.saveObject(obj);
  			
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
  	
  //根据id得到财务预算信息
  	@Override
  	public FinancialBudget getfin(String id) throws BkmisServiceException {
  		return (FinancialBudget)ifinancialBudgetDao.getObject(FinancialBudget.class,Integer.parseInt(id));
  	}
  	
  	
  //根据id编辑财务预算信息
  	@Override
  	public void editfin(FinancialBudgetForm form) throws BkmisServiceException {
  		
  		FinancialBudget obj = new FinancialBudget();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e) {
  			e.printStackTrace();
  		} catch (InvocationTargetException e) {
  			e.printStackTrace();
  		}
  		ifinancialBudgetDao.updateObject(obj);
  	}
  	
  	
  //根据id删除财务预算信息
  	@Override
  	public void delfin(String id) throws BkmisServiceException {
  		FinancialBudget bean = (FinancialBudget)ifinancialBudgetDao.getObject(FinancialBudget.class,Integer.parseInt(id));
  		ifinancialBudgetDao.deleteObject(bean);
  	}
  	
  	
  	public List<MUser> getUserList() throws Exception {
		return ifinancialBudgetDao.getObjects(MUser.class);
	}
  	
}
