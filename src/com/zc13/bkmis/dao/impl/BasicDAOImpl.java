package com.zc13.bkmis.dao.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.msmis.mapping.SysLog;
import com.zc13.util.CloneObject;
import com.zc13.util.GlobalMethod;
import com.zc13.util.LogParam;
import com.zc13.util.PageBean;

/**
 * 所有基本的数据库操作方法
 * @author dongdaokui
 * Date：Oct 27, 2010
 * Time：2:31:00 PM
 */

public class BasicDAOImpl extends HibernateDaoSupport implements BasicDAO{

	
	
	/**
	 * @param String queryString
	 * @基本的查找方法
	 * @return List
	 */
    @SuppressWarnings("unchecked")
	public List findObject(String queryString)throws DataAccessException{ 
        return getHibernateTemplate().find(queryString);
    } 
    
    /**
     * 根据hql语句查询返回唯一的对象
	 * @param String queryString
	 * @基本的查询方法
	 */
	public Object getUniqueObject(String queryString) throws DataAccessException {
		return this.getSession().createQuery(queryString).uniqueResult();
	}
    
    /**
	 * @param Object obj
	 * @基本的保存方法
	 */
    public Serializable saveObject(Object obj) throws DataAccessException{ 
        return getHibernateTemplate().save(obj);
    } 
    
    
    /**
	 * @param Object obj
	 * @基本的保存或者更新
	 */
    public void saveOrUpdateObject(Object obj) throws DataAccessException{ 
        getHibernateTemplate().saveOrUpdate(obj); 
    } 
    
    /**
	 * @param Object obj
	 * @基本的更新方法
	 */
    public void updateObject(Object obj) throws DataAccessException{ 
        getHibernateTemplate().update(obj); 
    } 
    
	@Override
	public int update(String sql) throws DataAccessException {
		int count = 0;
		if (!GlobalMethod.NullToSpace(sql).equals("")) {
			count = this.getSession().createQuery(sql).executeUpdate();
		}
		return count;
	}
    
    /**
	 * @param Object obj
	 * @基本的删除方法
	 */
    public void deleteObject(Object obj)throws DataAccessException { 
        getHibernateTemplate().delete(obj); 
    } 

    /**
	 * @param Class clazz, Serializable id
	 * @根据主键取某一个对象
	 * @return Object
	 */
    @SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id)throws DataAccessException { 
        Object obj = getHibernateTemplate().get(clazz, id);
        if (obj == null) { 
            throw new ObjectRetrievalFailureException(clazz, id); 
        } 
        return obj; 
    } 
    
    /**
	 * @param Class clazz, Serializable id
	 * @取某一个对象
	 * @return Object
	 */
    @SuppressWarnings("unchecked")
	public Object loadObject(Class clazz, Serializable id)throws DataAccessException { 
        Object obj = getHibernateTemplate().load(clazz, id); 
        if (obj == null) { 
            throw new ObjectRetrievalFailureException(clazz, id); 
        } 
        return obj; 
    } 
    
    /**
	 * @param Class clazz, Serializable id
	 * @取出所有对象
	 * @return Object
	 */
    @SuppressWarnings("unchecked")
	public List getObjects(Class clazz) throws DataAccessException{ 
        return getHibernateTemplate().loadAll(clazz); 
    } 
    
    /**
	 * @param Class clazz, Serializable id
	 * @删除一个对象
	 * @return Object
	 */
    @SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id)throws DataAccessException { 
        getHibernateTemplate().delete(getObject(clazz, id)); 
    } 
    
    
    /**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的具体内容
     * @param module 操作的系统模块
     * @param obj 操作的实例对象
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
	public void log(String userName,String operateType,String operateContent,String module,String obj)throws DataAccessException { 
    	
    	SysLog log = new SysLog();
    	log.setOperateUserName(userName);
    	log.setOperateContent(operateContent);
    	log.setOperateType(operateType);
    	log.setOperateModule(module);
    	log.setOperateObj(obj);
    	log.setOperateTime(new Date());
    	getHibernateTemplate().save(log);
    }
    /**
     * 更新任务提醒信息，当用户做了某些操作时，需要更新相应任务的数量
     * @param code任务代码
     * @param amount 要更新的数量
     */
    public void updateTask(String code,Integer amount)throws DataAccessException{
    	
    	String hql = "update AwokeTask set amount = "+amount+" where code = '"+code+"'";
    	this.getSession().createQuery(hql).executeUpdate();
    	String hql2 = "update AwokeTask set amount = 0 where amount<0";
    	this.getSession().createQuery(hql2).executeUpdate();
    }
    
    public PageBean queryFy(Query query,String pagination,int rows)throws DataAccessException{
    	
		PageBean page = new PageBean(rows);
		page.setPagination(pagination);
		int tatol = query.list().size();
		page.setTatol(tatol);
		int s = page.getStartPage();
		page.setList(query.setFirstResult(s).setMaxResults(rows).list());
		return page;
	}
    /**
     * 更新某张表的某个字段用
     * BasicDAOImpl.updateAColumn
     */
    public void updateAColumn(String obj,String column,String value,String column2,String value2) throws DataAccessException{
    	
    	String hql = "update "+obj+" set "+column+" = '"+value+"' where "+column2+" = '"+value2+"'";
    	this.getSession().createQuery(hql).executeUpdate();
    }
    /**
     * 生成编号用，存放各种单据编号的当前最大值
     * BasicDAOImpl.getBiggestCode
     */
    public String getBiggestCode(String type){
    	
    	String hql = "select value from SysSequence where code='"+type+"'";
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
    	
    }

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
	@Override
	public void logDetail(String userName, String operateType,
			String operateObj, String module,String flag,Object obj) throws Exception{
		
		StringBuffer tempOperateContent = new StringBuffer("");
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("1")){//判断是更新操作
			
			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得修改之前的对象
			Object beforeObj = this.getObject(classType, id);
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("该信息的修改情况如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				//根据属性的名称得到数据库中该字段的名称
				String dbFieldName = this.toDBField(fieldName);
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object beforeValue = getMethod.invoke(beforeObj, new Object[] {});
				Object afterValue = getMethod.invoke(obj, new Object[] {});
				if((beforeValue!=null&&!beforeValue.equals(afterValue))&&CloneObject.testBaseType(beforeValue)||(beforeValue==null&&afterValue!=null&&CloneObject.testBaseType(afterValue))){
					//tempOperateContent.append(" ");
					//tempOperateContent.append(i+1);
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("\"由'");
					tempOperateContent.append(beforeValue);
					tempOperateContent.append("'变为'");
					tempOperateContent.append(afterValue);
					tempOperateContent.append("'】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("2")){//判断为新增

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("增加的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("3")){//判断为删除

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得删除之前的对象
			obj = this.getObject(classType, id);
			
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("删除的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		getHibernateTemplate().clear();//此处必须要有这句话
		SysLog log = new SysLog();
		log.setOperateUserName(userName);
    	log.setOperateContent(tempOperateContent.toString());
    	log.setOperateType(operateType);
    	log.setOperateObj(operateObj);
    	log.setOperateModule(module);
    	log.setOperateTime(new Date());
    	getHibernateTemplate().save(log);
	}
	
	 /**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档(暂时只为房间的修改服务)
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的具体内容
     * @param module 操作的系统模块
     * @param flag 类型：1,更新；2,新增；3,删除
     * @param afterModifyObj 修改之后的对象
     * @throws Exception
     */
	@Override
	public void logDetail2(String userName, String operateType,
			String operateObj, String module,String flag,Object obj) throws Exception{
		
		StringBuffer tempOperateContent = new StringBuffer("");
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("1")){//判断是更新操作
			
			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得修改之前的对象
			Object beforeObj = this.getObject(classType, id);
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("该信息的修改情况如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				//根据属性的名称得到数据库中该字段的名称
				String dbFieldName = this.toDBField(fieldName);
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object beforeValue = getMethod.invoke(beforeObj, new Object[] {});
				Object afterValue = getMethod.invoke(obj, new Object[] {});
				if((beforeValue!=null&&!beforeValue.equals(afterValue))&&CloneObject.testBaseType(beforeValue)||(beforeValue==null&&afterValue!=null&&CloneObject.testBaseType(afterValue))){
					//tempOperateContent.append(" ");
					//tempOperateContent.append(i+1);
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("\"由'");
					tempOperateContent.append(beforeValue);
					tempOperateContent.append("'变为'");
					tempOperateContent.append(afterValue);
					tempOperateContent.append("'】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("2")){//判断为新增

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("增加的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("3")){//判断为删除

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得删除之前的对象
			obj = this.getObject(classType, id);
			
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("删除的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
//		getHibernateTemplate().clear();//此处必须要有这句话
		SysLog log = new SysLog();
		log.setOperateUserName(userName);
    	log.setOperateContent(tempOperateContent.toString());
    	log.setOperateType(operateType);
    	log.setOperateObj(operateObj);
    	log.setOperateModule(module);
    	log.setOperateTime(new Date());
    	getHibernateTemplate().save(log);
	}
	
	/**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateObj 操作的物理对象（比如系统模块是房产模块的情况下，下面还有楼盘，房间等）
     * @param module 操作的系统模块
     * @param flag 类型：1,更新；2,新增；3,删除
     * @param afterModifyObj 修改之后的对象
     * @throws Exception
     */
	@Override
	public void logDetail(LogParam logParam) throws Exception{
		
		Object obj = logParam.getObject();
		String flag = logParam.getFlag();
		
		StringBuffer tempOperateContent = new StringBuffer("");
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("1")){//判断是更新操作
			
			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得修改之前的对象
			Object beforeObj = this.getObject(classType, id);
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("该信息的修改情况如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				//根据属性的名称得到数据库中该字段的名称
				String dbFieldName = this.toDBField(fieldName);
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object beforeValue = getMethod.invoke(beforeObj, new Object[] {});
				Object afterValue = getMethod.invoke(obj, new Object[] {});
				if((beforeValue!=null&&!beforeValue.equals(afterValue))&&CloneObject.testBaseType(beforeValue)||(beforeValue==null&&afterValue!=null&&CloneObject.testBaseType(afterValue))){
					//tempOperateContent.append(" ");
					//tempOperateContent.append(i+1);
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("\"由'");
					tempOperateContent.append(beforeValue);
					tempOperateContent.append("'变为'");
					tempOperateContent.append(afterValue);
					tempOperateContent.append("'】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("2")){//判断为新增

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("增加的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("3")){//判断为删除

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得删除之前的对象
			obj = this.getObject(classType, id);
			
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("删除的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		getHibernateTemplate().clear();//此处必须要有这句话
		SysLog log = new SysLog();
		log.setOperateUserName(logParam.getUserName());
    	log.setOperateContent(tempOperateContent.toString());
    	log.setOperateType(logParam.getOperateType());
    	log.setOperateObj(logParam.getOperateObj());
    	log.setOperateModule(logParam.getOperateModule());
    	log.setOperateTime(new Date());
    	log.setLpId(logParam.getLpId());
    	getHibernateTemplate().save(log);
	}
	
	 /**
     * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档(暂时只为房间的修改服务)
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的具体内容
     * @param module 操作的系统模块
     * @param flag 类型：1,更新；2,新增；3,删除
     * @param afterModifyObj 修改之后的对象
     * @throws Exception
     */
	@Override
	public void logDetail2(LogParam logParam) throws Exception{
		
		Object obj = logParam.getObject();
		String flag = logParam.getFlag();
		
		StringBuffer tempOperateContent = new StringBuffer("");
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("1")){//判断是更新操作
			
			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得修改之前的对象
			Object beforeObj = this.getObject(classType, id);
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("该信息的修改情况如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				//根据属性的名称得到数据库中该字段的名称
				String dbFieldName = this.toDBField(fieldName);
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object beforeValue = getMethod.invoke(beforeObj, new Object[] {});
				Object afterValue = getMethod.invoke(obj, new Object[] {});
				if((beforeValue!=null&&!beforeValue.equals(afterValue))&&CloneObject.testBaseType(beforeValue)||(beforeValue==null&&afterValue!=null&&CloneObject.testBaseType(afterValue))){
					//tempOperateContent.append(" ");
					//tempOperateContent.append(i+1);
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("\"由'");
					tempOperateContent.append(beforeValue);
					tempOperateContent.append("'变为'");
					tempOperateContent.append(afterValue);
					tempOperateContent.append("'】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("2")){//判断为新增

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("增加的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
		if(obj!=null&&GlobalMethod.NullToSpace(flag).equals("3")){//判断为删除

			// 获得对象的类型  
			Class classType = obj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			Method getIdMethod = null;
			try {
				getIdMethod = classType.getMethod("getId",new Class[] {});
			} catch (NoSuchMethodException e) {//如果没有getId方法，则使用第一个参数的get方法
				String firstLetter = fields[0].getName().substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fields[0].getName().substring(1);
				getIdMethod = classType.getMethod(getMethodName,new Class[] {});
			}
			//获得id字段
			Integer id = (Integer)getIdMethod.invoke(obj, new Object[] {});
			//根据id获得删除之前的对象
			obj = this.getObject(classType, id);
			
			//根据类型获得数据库中该类对应的表名
			String dbTableName = this.toDBField(classType.getSimpleName());
			//通过表名获得该表下的字段及对应的描述
			List<Map<String,String>> list = this.getFieldDesByTableName(dbTableName);
			//根据表名获得该表的主键列表
			List<String> pList = this.getPrimaryKeyByTableName(dbTableName);
			//得到列表中的第一个主键
			String pk1 = "";
			if(pList!=null&&pList.size()>0){
				pk1 = pList.get(0);
			}
//			//根据主键列表中的第一个主键，获得该主键的描述
//			String pkDes = this.getDesByFieldName(list, pk1);
//			tempOperateContent.append("\"");
//			tempOperateContent.append(pkDes);
			tempOperateContent.append("删除的记录详情如下：");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);

				// 获得和属性对应的getXXX()方法
				Method getMethod = classType.getMethod(getMethodName,new Class[] {});
				// 获得和属性对应的setXXX()方法
				Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });

				// 调用原对象的getXXX()方法
				Object value = getMethod.invoke(obj, new Object[] {});
				if(CloneObject.testBaseType(value)){
					tempOperateContent.append("\n【\"");
					tempOperateContent.append(this.getDesByFieldName(list, this.toDBField(fieldName)));
					tempOperateContent.append("=");
					tempOperateContent.append(value);
					tempOperateContent.append("\"】");
				}
			}
		}
//		getHibernateTemplate().clear();//此处必须要有这句话
		SysLog log = new SysLog();
		log.setOperateUserName(logParam.getUserName());
    	log.setOperateContent(tempOperateContent.toString());
    	log.setOperateType(logParam.getOperateType());
    	log.setOperateObj(logParam.getOperateObj());
    	log.setOperateModule(logParam.getOperateModule());
    	log.setOperateTime(new Date());
    	getHibernateTemplate().save(log);
	}
	
	
	/**
	 * 将字符串参数转换成数据库字段的形式，及将诸如userName转换成user_name
	 * @param str
	 * @return
	 * Date:Apr 18, 2011 
	 * Time:11:18:54 PM
	 */
	public String toDBField(String str){
		
		StringBuffer sb = new StringBuffer();
		if(!GlobalMethod.NullToSpace(str).equals("")){
			for(int i = 0;i<str.length();i++){
				if(str.charAt(i)>=65&&str.charAt(i)<=90&&i!=0){
					sb.append('_');
					sb.append((char)(str.charAt(i)+32));
				}else{
					sb.append(str.charAt(i));
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 通过表名获得该表下的字段及对应的描述
	 * @param tableName
	 * @return
	 * Date:Apr 18, 2011 
	 * Time:11:39:42 PM
	 */
	public List<Map<String,String>> getFieldDesByTableName(String tableName){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                              ");   
		sql.append("    CONVERT(char,A.name) AS table_name,           ");  
		sql.append("    CONVERT(char,B.name) AS column_name,          "); 
		sql.append("    CONVERT(char,C.value) AS column_description   ");            
		sql.append("FROM sys.tables A                   ");               
		sql.append("INNER JOIN sys.columns B            ");                 
		sql.append("    ON B.object_id = A.object_id    ");              
		sql.append("LEFT JOIN sys.extended_properties C ");             
		sql.append("    ON C.major_id = B.object_id     ");            
		sql.append("        AND C.minor_id = B.column_id");             
		sql.append(" WHERE A.name = '").append(tableName).append("'");
		List list = null;
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map<String,String> map = new HashMap<String,String>();
				map.put("table_name", GlobalMethod.ObjToStr(obj[0]));
				map.put("column_name", GlobalMethod.ObjToStr(obj[1]));
				map.put("column_description", GlobalMethod.ObjToStr(obj[2]));
				list1.add(map);
			}
		}
		return list1;
	}
	
	/**
	 * 根据表名获得该表的主键列表
	 * @param tableName
	 * @return
	 * Date:Apr 19, 2011 
	 * Time:12:08:53 AM
	 */
	public List<String> getPrimaryKeyByTableName(String tableName){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select COLUMN_NAME = convert(sysname,c.name)     ");          
		sql.append("from                                             ");
		sql.append("sysindexes i, syscolumns c, sysobjects o         ");          
		sql.append("where o.id = object_id('").append(tableName).append("')");                  
		sql.append("and o.id = c.id                                  ");          
		sql.append("and o.id = i.id                                  ");          
		sql.append("and (i.status & 0x800) = 0x800                   ");
		sql.append("and (c.name = index_col ('").append(tableName).append("', i.indid,  1) or ");
		int i = 2;
		for(;i<20;i++){
			sql.append(" c.name = index_col ('").append(tableName).append("', i.indid,  ").append(i).append(") or ");
		}
		sql.append("     c.name = index_col ('").append(tableName).append("', i.indid,  ").append(i).append(")) ");
		List list = null;
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List list1 = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int j = 0;j<list.size();j++){
				list1.add(GlobalMethod.ObjToStr(list.get(j)));
			}
		}
		return list1;
	}
	
	/**
	 * 根据字段名称到list中查找对应的字段描述
	 * @param list
	 * @param fieldName
	 * @return
	 * Date:Apr 19, 2011 
	 * Time:12:28:02 AM
	 */
	public String getDesByFieldName(List<Map<String,String>> list,String fieldName){
		
		fieldName = GlobalMethod.NullToSpace(fieldName);
		if(list==null||list.size()<=0){
		}else{
			for(int i = 0;i<list.size();i++){
				if(fieldName.equals(list.get(i).get("column_name").trim())){
					fieldName = list.get(i).get("column_description").trim();
					break;
				}
			}
		}
		return fieldName;
	}

}
