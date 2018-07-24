package com.zc13.bkmis.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.util.LogParam;

public interface BasicDAO {
	/**
	 * @param String queryString
	 * @基本的查找方法
	 * @return List
	 */
	public List findObject(String queryString)throws DataAccessException;
	/**
	 * 根据hql语句查询返回唯一的对象
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 */
	public Object getUniqueObject(String queryString) throws DataAccessException;
    public Serializable saveObject(Object obj) throws DataAccessException;
    
    public void saveOrUpdateObject(Object obj) throws DataAccessException;
    
    public void updateObject(Object obj) throws DataAccessException;
    
    public void deleteObject(Object obj)throws DataAccessException ;
    
	public Object getObject(Class clazz, Serializable id)throws DataAccessException;
	
	public Object loadObject(Class clazz, Serializable id)throws DataAccessException ;
	
	public List getObjects(Class clazz) throws DataAccessException;
    
	public void removeObject(Class clazz, Serializable id)throws DataAccessException ;
	/**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的具体内容
     * @param module 操作的系统模块
     * @param module 操作的实例对象
     * @throws DataAccessException
     */
	public void log(String userName,String operateType,String operateContent,String module,String obj)throws DataAccessException ;

	public void updateTask(String code,Integer amount)throws DataAccessException;
	/**
     * 更新某张表的某个字段用
     * BasicDAOImpl.updateAColumn
     */
	public void updateAColumn(String obj,String column,String value,String column2,String value2)  throws DataAccessException;
	
	public String getBiggestCode(String type) throws DataAccessException;
	/**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的物理对象（比如系统模块是房产模块的情况下，下面还有楼盘，房间等）
     * @param module 操作的系统模块
     * @param flag 类型：1,更新；2,新增；3,删除
     * @param afterModifyObj 修改之后的对象
     * @throws Exception
     */
	public void logDetail(String userName,String operateType,String operateContent,String module,String flag,Object obj)throws Exception ;
	public void logDetail2(String userName,String operateType,String operateContent,String module,String flag,Object obj)throws Exception ;
	public void logDetail(LogParam logParam)throws Exception ;
	public void logDetail2(LogParam logParam)throws Exception ;
	
	/**
	 * 根据hql语句来更新数据库(可以执行更新和删除的操作)
	 * @param hql hql语句
	 * @return
	 * @throws DataAccessException
	 * Date:May 5, 2011 
	 * Time:5:46:25 PM
	 */
	public int update(String hql) throws DataAccessException;
}
