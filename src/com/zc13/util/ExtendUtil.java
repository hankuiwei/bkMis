package com.zc13.util;
/**
 * 
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：4:53:06 PM
 */
public class ExtendUtil {
	/**
	 * 将对象转换成字符串格式
	 * 如果传进去的是null则返回"";
	 * 如果传进去的是字符串，则会去掉字符串前后空格;
	 * 如果传进去的是对象，则会调用该对象的toString方法
	 * @param str
	 * @return
	 */
	public static String null2str(Object obj){
		if(obj == null){
			return "";
		}else if(obj.getClass().getName().equals("java.lang.String")){
			return obj.toString().trim();
		}else{
			return obj.toString();
		}
	}
	/**
	 * 判断对象是非为空，空返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkNull(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}
	public static boolean checkNull(String[] str){
		if (str == null || str.length == 0) 
			return true;
		return false;
	}
	public static boolean checkNull(Integer str){
		if (str == null || str.intValue() == 0) 
			return true;
		return false;
	}
	public static boolean isNull(String str) {
		if (str == null)
			return true;
		return false;
	}
}
