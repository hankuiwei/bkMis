package com.zc13.bkmis.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.bkmis.service.IBasicService;


public class BasicServiceImpl implements IBasicService{
	
	BasicDAOImpl basicDAOImpl;

	public BasicDAOImpl getBasicDAOImpl() {
		return basicDAOImpl;
	}

	public void setBasicDAOImpl(BasicDAOImpl basicDAOImpl) {
		this.basicDAOImpl = basicDAOImpl;
	}
	@Override
	public void deleteObject(Object obj) throws Exception {
		basicDAOImpl.deleteObject(obj);
	}
	
	
	@Override
	public List findObject(String queryString) throws Exception {
		return basicDAOImpl.findObject(queryString);
	}

	

	@Override
	public Object getObject(Class clazz, Serializable id) throws Exception {
		return basicDAOImpl.getObject(clazz, id);
	}

	@Override
	public List getObjects(Class clazz) throws Exception {
		return basicDAOImpl.getObjects(clazz);
	}

	@Override
	public Object loadObject(Class clazz, Serializable id) throws Exception {
		return basicDAOImpl.loadObject(clazz, id);
	}

	@Override
	public void removeObject(Class clazz, Serializable id) throws Exception {
		basicDAOImpl.removeObject(clazz, id);
	}

	@Override
	public void saveObject(Object obj) throws Exception {
		basicDAOImpl.saveObject(obj);
	}

	@Override
	public void saveOrUpdateObject(Object obj) throws Exception {
		basicDAOImpl.saveOrUpdateObject(obj);
	}
	

	@Override
	public void updateObject(Object obj) throws Exception {
		basicDAOImpl.saveObject(obj);
		
	}
	


}
