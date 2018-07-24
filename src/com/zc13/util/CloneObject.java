package com.zc13.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import com.zc13.msmis.mapping.SysLog;

public class CloneObject {

	public static Object copy(Object object) throws Exception {
		// 获得对象的类型
		Class<?> classType = object.getClass();
		System.out.println("Class:" + classType.getName());

		// 通过默认构造方法创建一个新的对象
		Object objectCopy = classType.getConstructor(new Class[] {})
				.newInstance(new Object[] {});

		// 获得对象的所有属性
		Field fields[] = classType.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			// 获得和属性对应的getXXX()方法的名字
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			// 获得和属性对应的setXXX()方法的名字
			String setMethodName = "set" + firstLetter + fieldName.substring(1);

			// 获得和属性对应的getXXX()方法
			Method getMethod = classType.getMethod(getMethodName,
					new Class[] {});
			// 获得和属性对应的setXXX()方法
			Method setMethod = classType.getMethod(setMethodName,
					new Class[] { field.getType() });

			// 调用原对象的getXXX()方法
			Object value = getMethod.invoke(object, new Object[] {});
			if(!CloneObject.testBaseType(value)){
				value = null;
			}
			System.out.println(fieldName + ":" + value);
			// 调用拷贝对象的setXXX()方法
			setMethod.invoke(objectCopy, new Object[] { value });
		}
		return objectCopy;
	}

	/**
	 * 判断对象是否为基本类型和字符串类型
	 * CloneObject.testBaseType
	 */
	public static boolean testBaseType(Object obj){
		boolean b = false;
		if(obj!=null){
			String classType = obj.getClass().getName();
			String baseTypes[] = new String[]{"java.lang.Integer","java.lang.Double","java.lang.Float","java.lang.Short","java.lang.Character","java.lang.Long","java.lang.Boolean","java.lang.String"};
			for(int i = 0;i<baseTypes.length;i++){
				if(baseTypes[i].equals(classType)){
					b = true;
					break;
				}
			}
		}
		return b;
	}
	
	
	public void logDetail(String userName, String operateType,
			String operateContent, String module, Object beforeModifyObj,
			Object afterModifyObj) throws Exception {
		StringBuffer tempOperateContent = new StringBuffer(operateContent);
		if(beforeModifyObj!=null&&afterModifyObj!=null){//如果2个对象都不为空，则判断是更新操作
			
			// 获得对象的类型  
			Class classType = beforeModifyObj.getClass();
			// 获得对象的所有属性       
			Field fields[] = classType.getDeclaredFields(); 
			
			tempOperateContent.append("表\"");
			tempOperateContent.append(classType.getSimpleName());
			tempOperateContent.append("\"中的字段的修改情况：");
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
				Object beforeValue = getMethod.invoke(beforeModifyObj, new Object[] {});
				Object afterValue = getMethod.invoke(afterModifyObj, new Object[] {});
				if((beforeValue!=null&&!beforeValue.equals(afterValue))||(beforeValue==null&&afterValue!=null)){
					//tempOperateContent.append(" ");
					//tempOperateContent.append(i+1);
					tempOperateContent.append("[字段:\"");
					tempOperateContent.append(fieldName);
					tempOperateContent.append("\"由'");
					tempOperateContent.append(beforeValue);
					tempOperateContent.append("'变为'");
					tempOperateContent.append(afterValue);
					tempOperateContent.append("']");
				}
			}
		}
		System.out.println(tempOperateContent.toString());
		SysLog log = new SysLog();
    	log.setOperateUserName(userName);
    	log.setOperateContent(tempOperateContent.toString());
    	log.setOperateType(operateType);
    	log.setOperateModule(module);
    	log.setOperateTime(new Date());
	}
	
	
	
	public static void main(String[] args) throws Exception {
//		Customer customer = new Customer("Tom", 21);
//		customer.setId(new Long(1));
//
//		Customer customerCopy = (Customer) new CloneObject().copy(customer);
//		System.out.println("Copy information:" + customerCopy.getId() + " "
//				+ customerCopy.getName() + " " + customerCopy.getAge());
		
		
		Customer c1 = new Customer("ww",11);
		Customer c2 = new Customer("ss",12);
		new CloneObject().logDetail("", "", "", "", c1, c2);
	}
}

class Customer {
	private Long id;

	private String customerName;

	private int age;

	public Customer() {
	}

	public Customer(String customerName, int age) {
		this.customerName = customerName;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
