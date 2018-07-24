package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IRepairResultDAO;
import com.zc13.bkmis.form.RepairResultForm;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.bkmis.service.IRepairResultService;
import com.zc13.exception.BkmisServiceException;
/***
 * @author 李影
 */
public class RepairResultServiceImpl extends BasicServiceImpl implements
		IRepairResultService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IRepairResultDAO irepairResultDao;

	public IRepairResultDAO getIrepairResultDao() {
		return irepairResultDao;
	}

	public void setIrepairResultDao(IRepairResultDAO irepairResultDao) {
		this.irepairResultDao = irepairResultDao;
	}
	@Override
	public List<RepairResult> getList(RepairResultForm repairResultForm,boolean isPage) throws BkmisServiceException {
		
		List<RepairResult> list = null;
		try{
			list = irepairResultDao.queryRepairResult(repairResultForm, isPage);
		}catch(Exception e){
			logger.error("客户保修内容查询失败!RepairResultServiceImpl.getList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("客户保修内容查询失败!RepairResultServiceImpl.getList()。");
		}
		return list;
	}
	
	 /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(RepairResultForm repairResultForm){
    	return irepairResultDao.queryCounttotal(repairResultForm);
    }
    
    
  //保存客户报修内容
  	@Override
  	public void addClient(RepairResultForm form)
  			throws BkmisServiceException {
  		
  		RepairResult obj = new RepairResult();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e1) {
  			e1.printStackTrace();
  		} catch (InvocationTargetException e1) {
  			e1.printStackTrace();
  		}
  		try {
  			irepairResultDao.saveObject(obj);
  			
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
  	
  //根据id得到客户报修内容
  	@Override
  	public RepairResult getRepair(String id) throws BkmisServiceException {
  		return (RepairResult)irepairResultDao.getObject(RepairResult.class,Integer.parseInt(id));
  	}
  	
  	
  //根据id编辑客户报修内容 
  	@Override
  	public void editRepair(RepairResultForm form) throws BkmisServiceException {
  		
  		RepairResult obj = new RepairResult();
  		try {
  			BeanUtils.copyProperties(obj, form);
  		} catch (IllegalAccessException e) {
  			e.printStackTrace();
  		} catch (InvocationTargetException e) {
  			e.printStackTrace();
  		}
  		irepairResultDao.updateObject(obj);
  	}
  	
  	
  	
  //根据id删除客户报修内容 
  	@Override
  	public void delRepair(String id) throws BkmisServiceException {
  		RepairResult bean = (RepairResult)irepairResultDao.getObject(RepairResult.class,Integer.parseInt(id));
  		irepairResultDao.deleteObject(bean);
  	}
}
