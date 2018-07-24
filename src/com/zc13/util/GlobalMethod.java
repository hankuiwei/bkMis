package com.zc13.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用方法
 * @author daokui
 * Date：Oct 27, 2010
 * Time：1:22:47 PM
 */
public class GlobalMethod {
	  /**
	   * Timestamp转为date String "yyyy-MM-dd"
	   * @param timestamp Timestamp
	   * @return String
	   */
	  public static String timestampToString(Timestamp timestamp){
	    String str = new String();
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	    if(timestamp!=null){
	    	str = formater.format(timestamp);
	    }
	    return str;
	  }
	  
	  /**
	   * Date转为String "yyyy-MM-dd"
	   * @param timestamp Timestamp
	   * @return String
	   */
	  public static String dateToString(Date date){
	    String str = new String();
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	    if(date!=null){
	    	str = formater.format(date);
	    }
	    return str;
	  }
	  /**
	   * 得到当前日期的String形式yyyy-MM-dd
	   * @return
	   */
	  public static String getTime(){
		  String str = new String();
		  SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		  str = formater.format(new Date());
		  return str;
	  }
	  /**
	   * 得到当前日期的String形式yyyyMMdd
	   * @return
	   */
	  public static String getTime2(){
		  String str = new String();
		  SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		  str = formater.format(new Date());
		  return str;
	  }
	  /**
	   * 得到当前日期时间的String形式yyyy-MM-dd HH:mm:ss
	   * @return
	   */
	  public static String getTime3(){
		  String str = new String();
		  SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  str = formater.format(new Date());
		  return str;
	  }
	  /**
	   * 得到当前日期的String形式yyyyMMddHHmmss。生成单号用
	   * @return
	   */
	  public static String getTime4(){
		  String str = new String();
		  SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		  str = formater.format(new Date());
		  return str;
	  }
	  /**
	   * 取得结束日期
	   * @param startDate : String 开始日期 (YYYY-MM-DD)
	   * @param monthNum : int 增加的月数或者天数
	   * @return : String 结束日期 (YYYY-MM-DD)
	   */
	  public static String getEndDate(String startDate,int dateNum) {
	    String resultDate;
	    resultDate = "";
	    try {
	      Calendar calendar = Calendar.getInstance();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      calendar.setTime(sdf.parse(startDate));
	      calendar.add(Calendar.DATE, dateNum);
	      Date date = calendar.getTime();
	      resultDate = sdf.format(date);
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    return resultDate;
	  }
	  /**
	   * 判断字符串对像是否为null，如是则置为空字符串
	   * @param StringObject 处理的对像
	   * @return Strin 字符串
	   */
	  public static String NullToSpace(String StringObject){
	     
		 String return_str = "";
		 if (StringObject==null || "".equals(StringObject) || "null".equals(StringObject)){
	    	 return_str = "";
	     }else{
	         return_str = StringObject;
	     }
	     return return_str;
	  }
	  /**
	   * 判断对像是否为null，如是则置为空字符串,否则调用对象的toString方法并返回
	   * @param StringObject 处理的对像
	   * @return Strin 字符串
	   */
	  public static String ObjToStr(Object obj){
	     String return_str = "";
		 if(obj==null){
	    	 return_str = "";
	     }else{
	    	 return_str = obj.toString();
	     }
	     return return_str;
	  }
	  
	  /**
	   * 判断对像是否为null，如是则置为参数指定的字符串,否则调用对象的toString方法并返回
	   * @param StringObject 处理的对像,param指定的字符串对象
	   * @return Strin 字符串
	   */
	  public static String ObjToParam(Object obj,String param){
	     String return_str = NullToSpace(param);
		 if(obj==null){
	    	 return_str = param;
	     }else{
	    	 return_str = obj.toString();
	     }
	     return return_str;
	  }
	  
	  /**
	   * yyyy-MM-dd类型String 转为Date
	   * @param str
	   * @return
	   */
	  public static Date StringToDate(String str){
		  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date();
		  try{
			  date = dateFormat.parse(str);
		  }catch(Exception e){
			  e.printStackTrace();
		  }

		  return date;
	  }
	  
	  
  public static Date StringToDate2(String str){
		  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date = new Date();
		  try{
			  date = dateFormat.parse(str);
		  }catch(Exception e){
			  e.printStackTrace();
		  }

		  return date;
	  }
	  
	  //2010-08-21新增
	  /**
	   * hh:mm:ss类型转化为Date
	   */
	  public static Date StringToTime(String str){
		  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		  Date date = new Date();
		  try{
			  date = dateFormat.parse(str);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return date;
	  }
	  
	  /**
	   * 判断字符串对像是否为null，如是则置为用户想要的值
	   * @param StringObject 处理的对像，return_str 处理后的值
	   * @return return_str 字符串
	   */
	  public static String NullToParam(String StringObject,String return_str){
		  
		     if (StringObject==null || "".equals(StringObject) || "null".equals(StringObject)){
		    	 
		     }else{
		         return_str = StringObject;
		     }
		     return return_str;
	  }
	  /**
	   * MD5加密
	   * @param user
	   * @param pass
	   * @return
	   */
	  public static String encryptPassword(String user, String pass) {
			String password = user + pass;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				password = new BigInteger(1, md.digest()).toString(16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return password;
		}
	  /**
	   * 两个String类型的时间相减，返回相差的小时数
	   * @param begin end
	   * @param end
	   * @return
	   */
	public static String minus(String begin,String end)  {
		  
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DecimalFormat df = new DecimalFormat("#0.0");
			float manHours = 0.0f;
			try {
				Date beginDate = formater.parse(begin);
				Date endDate = formater.parse(end);
				long begin1 = beginDate.getTime();
				long end1 = endDate.getTime();
				long manHour = end1 - begin1;
				manHours = ((float)manHour)/(1000*3600);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return df.format(manHours);
		
	}
	/**
	 * 获取当前日期是星期几
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 
	 * 让具体的某一天加1
	 */  
	public static String addDate(String date){
		
		String s = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try{
			calendar.setTime(dateFormat.parse(date));
		}catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		s = dateFormat.format(calendar.getTime());
		return s;
	}
	/**
	 * 
	 * 让具体的某一天减1
	 */
	public static String minDate(String date){
		
		String s = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try{
			calendar.setTime(dateFormat.parse(date));
		}catch(Exception e){
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		s = dateFormat.format(calendar.getTime());
		return s;
	}
	
	/**
	 * 获取当前月
	 * @return
	 * Date:2011-5-13 
	 * Time:下午04:45:11
	 */
	public static String getMonth(){
		
		String str = "";
		DateFormat formater = new SimpleDateFormat("MM");
		str = formater.format(new Date());
		return str;
	}
	
	/**
	 * 查询编号不连号，第二天从1开始
	 * 
	 * @author luq
	 * @param front 前缀
	 * @param oldCode 新号码
	 * @param count 流水号个数 1000
	 * @throws
	 */
	public static String getNewCode4Day(String front, String oldCode, int count) {
		long No = 0;
		Integer newNo = oldCode == null || "".equals(oldCode) ? 1 : Integer
				.parseInt(oldCode.substring(8))+1;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		String olddate = oldCode == null || "".equals(oldCode)?"":oldCode.substring(0,8);
		if(!olddate.equals(nowdate)){
			newNo = 1;
		}
		
		No = Long.parseLong(nowdate) * count;
		No += newNo;
		return front + No;
	}
	
	/**
	 * 将double类型的数进行一下四舍五入
	 * @param d
	 * @return
	 * Date:Aug 15, 2012 
	 * Time:1:39:32 PM
	 */
	public static double round(Double d){
		Long l = Math.round(d * 100);
		Double number = l / 100.0;
		return number;
	}
	
	/**
	 * 获取当前年
	 * @return
	 * Date:2011-5-13 
	 * Time:下午04:45:11
	 */
	public static String getYear(){
		
		String str = "";
		DateFormat formater = new SimpleDateFormat("yyyy");
		str = formater.format(new Date());
		return str;
	}
	
	/**
	 * 四舍五入
	 * @param d
	 * @param n
	 * @return
	 * Date:2013-8-13 
	 * Time:下午10:07:58
	 */
	public static double round(double d, int n) {
		//d*10^n
		double t = d * Math.pow(10, n);
		//四舍五入取整
		long l;
		if (t > 0)
			l = (long) (t + 0.5);
		else
			l = (long) (t - 0.5);
		//d/10^n
		t = (double) l / Math.pow(10, n);

		//为有效数字补充0

		return t;
	}
}
