package com.zc13.bkmis.service;

import java.io.Serializable;
import java.util.List;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;

public interface IBasicService {

	public List findObject(String queryString)throws Exception;
    
   
    public void saveObject(Object obj) throws Exception;
    
   
    public void saveOrUpdateObject(Object obj) throws Exception;
    
   
    public void updateObject(Object obj) throws Exception;
 
    public void deleteObject(Object obj)throws Exception ;

   
	public Object getObject(Class clazz, Serializable id)throws Exception;
    
   
	public Object loadObject(Class clazz, Serializable id)throws Exception;
    
    
	public List getObjects(Class clazz) throws Exception;
    
    
	public void removeObject(Class clazz, Serializable id)throws Exception;


	public BasicDAOImpl getBasicDAOImpl();

	
	public void setBasicDAOImpl(BasicDAOImpl basicDAOImpl);

}
