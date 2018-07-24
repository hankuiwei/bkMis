package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zc13.bkmis.dao.IWorkTaskDAO;
import com.zc13.bkmis.form.LimitCarForm;
import com.zc13.bkmis.service.LimitCarService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.LimitCar;

public class LimitCarServiceImpl extends BasicServiceImpl implements LimitCarService {

	IWorkTaskDAO workTaskDAOImpl;
	
	public IWorkTaskDAO getWorkTaskDAOImpl() {
		return workTaskDAOImpl;
	}

	public void setWorkTaskDAOImpl(IWorkTaskDAO workTaskDAOImpl) {
		this.workTaskDAOImpl = workTaskDAOImpl;
	}
	@Override
	public void add(LimitCarForm limitCarForm)  throws BkmisServiceException{
		// TODO Auto-generated method stub
		LimitCar limitCar = new LimitCar();
		try {
			BeanUtils.copyProperties(limitCar, limitCarForm);
			workTaskDAOImpl.saveObject(limitCar);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(LimitCarForm limitCarForm) throws BkmisServiceException{
		// TODO Auto-generated method stub

		try {
			LimitCar limitCar = (LimitCar) workTaskDAOImpl.getObject(LimitCar.class, limitCarForm.getId());
			if (limitCar!=null) {
				workTaskDAOImpl.deleteObject(limitCar);
			}
		} catch (Exception e) {
			throw new BkmisServiceException("删除车辆限行号错误！");
		}
	}

	@Override
	public LimitCar getById(LimitCarForm limitCarForm) {
		// TODO Auto-generated method stub
		
		LimitCar limitCar  ;
		try {
			limitCar = (LimitCar) workTaskDAOImpl.getObject(LimitCar.class, limitCarForm.getId());
		} catch (Exception e) {
			throw new BkmisServiceException("删除车辆限行号错误！");
		}
		return limitCar;
	}

	@Override
	public List getList() throws BkmisServiceException{
		// TODO Auto-generated method stub
		List<LimitCar> list;
		try {
			list = workTaskDAOImpl.getObjects(LimitCar.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BkmisServiceException("获取车辆限行号失败！");
		}
		return list;
	}

	@Override
	public void edit(LimitCarForm limitCarForm) throws BkmisServiceException {
		// TODO Auto-generated method stub
		LimitCar limitCar = new LimitCar();
		try {
			BeanUtils.copyProperties(limitCar, limitCarForm);
			workTaskDAOImpl.saveOrUpdateObject(limitCar);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
