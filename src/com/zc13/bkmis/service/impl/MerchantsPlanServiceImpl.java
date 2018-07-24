package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IMerchantsPlanDAO;
import com.zc13.bkmis.form.MerchantsPlanForm;
import com.zc13.bkmis.mapping.MerchantsPlan;
import com.zc13.bkmis.service.IMerchantsPlanService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;
/***
 * @author 李影
 */
public class MerchantsPlanServiceImpl extends BasicServiceImpl implements
		IMerchantsPlanService {
	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsPlanDAO imerchantsPlanDao;
	public IMerchantsPlanDAO getImerchantsPlanDao() {
		return imerchantsPlanDao;
	}
	public void setImerchantsPlanDao(IMerchantsPlanDAO imerchantsPlanDao) {
		this.imerchantsPlanDao = imerchantsPlanDao;
	}
	

	@Override
	public List<MerchantsPlan> getList(MerchantsPlanForm merchantsPlanForm,boolean isPage) throws BkmisServiceException {
		
		List<MerchantsPlan> list = null;
		try{
			list = imerchantsPlanDao.queryPlanResult(merchantsPlanForm, isPage);
		}catch(Exception e){
			logger.error("招商计划信息查询失败!MerchantsPlanServiceImpl.getList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("招商计划信息查询失败!MerchantsPlanServiceImpl.getList()。");
		}
		return list;
	}
	 public int queryCounttotal(MerchantsPlanForm merchantsPlanForm){
	    	return imerchantsPlanDao.queryCounttotal(merchantsPlanForm);
	    }
	 
	 
	 //根据id得到招商计划
	  	@Override
	  	public MerchantsPlan getMerchantsPlan(String id) throws BkmisServiceException {
	  		return (MerchantsPlan)imerchantsPlanDao.getObject(MerchantsPlan.class,Integer.parseInt(id));
	  	}
	 
	//保存客户报修
	  	@Override
	  	public void addClient(MerchantsPlanForm form)
	  			throws BkmisServiceException {
	  		MerchantsPlan obj = new MerchantsPlan();
	  		try {
	  			BeanUtils.copyProperties(obj, form);
	  		} catch (IllegalAccessException e1) {
	  			e1.printStackTrace();
	  		} catch (InvocationTargetException e1) {
	  			e1.printStackTrace();
	  		}
	  		try {
	  			imerchantsPlanDao.saveObject(obj);
	  			
	  		} catch (Exception e) {
	  			e.printStackTrace();
	  		}
	  	}
	  	
	  //根据id编辑招商计划信息
	  	@Override
	  	public void editMerchantsPlan(MerchantsPlanForm form) throws BkmisServiceException {
	  		
	  		MerchantsPlan obj = new MerchantsPlan();
	  		try {
	  			BeanUtils.copyProperties(obj, form);
	  		} catch (IllegalAccessException e) {
	  			e.printStackTrace();
	  		} catch (InvocationTargetException e) {
	  			e.printStackTrace();
	  		}
	  		imerchantsPlanDao.updateObject(obj);
	  	}
	  	
	  	
	  //根据id删除招商计划信息
	  	@Override
	  	public void delMerchantsPlan(String id) throws BkmisServiceException {
	  		MerchantsPlan bean = (MerchantsPlan)imerchantsPlanDao.getObject(MerchantsPlan.class,Integer.parseInt(id));
	  		imerchantsPlanDao.deleteObject(bean);
	  	}
	  	
	  	
		public List<MUser> getUserList() throws Exception {
			return imerchantsPlanDao.getObjects(MUser.class);
		}
		
		/**
		 * 查询计划分配人
		 */
		@Override
		public List<SysCode> queryCodeByType() {
			
			return imerchantsPlanDao.queryCodeByType();
		}

}
