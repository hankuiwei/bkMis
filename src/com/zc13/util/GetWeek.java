package com.zc13.util;

import java.util.Calendar;
import java.util.Date;
/**
 * 获取当前日期是星期几
 * @author daokui
 * Date：Jan 4, 2011
 * Time：1:23:48 PM
 */
public class GetWeek {
	
	public static void main(String args[]){
		
		Date date = new Date();
		
		System.out.println(getDay(date));
	}
	
	public static int getDay(Date date) {
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal.get(Calendar.DAY_OF_WEEK);
		 }
}

