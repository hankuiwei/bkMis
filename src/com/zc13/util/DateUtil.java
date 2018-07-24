package com.zc13.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Do date format.
 * 
 */
public final class DateUtil {

    private static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static String DATETIME_FORMAT_S = "yyyy-MM-dd HH:mm:ss";
    private static String DATE_FORMAT = "yyyy-MM-dd";
    private static String TIME_FORMAT = "HH:mm";
    
    public static final long TIME_ONE_HOUR = 3600L * 1000L;

    public static final long TIME_ONE_DAY = 24L * TIME_ONE_HOUR;
    /**
     * Get the previous time, from how many days to now.
     * 
     * @param days How many days.
     * @return The new previous time.
     */
    public static long previous(int days) {
        return System.currentTimeMillis() - 3600000L * 24L * days;
    }

    /**
     * Get the next time, from how many days to now.
     * 
     * @param days How many days.
     * @return The new next time.
     */
    public static long next(int days) {
        return System.currentTimeMillis() + 3600000L * 24L * days;
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
    public static String formatDateTime(Date d) {
        return new SimpleDateFormat(DATETIME_FORMAT).format(d);
    }
    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
     */
    public static String formatDateTimes(Date d) {
    	return new SimpleDateFormat(DATETIME_FORMAT_S).format(d);
    }

    /**
     * Convert time to string like "HH:mm".
     */
    public static String formatTime(Date d) {
        return new SimpleDateFormat(TIME_FORMAT).format(d);
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
//    public static String formatDateTime(long d) {
//        return new SimpleDateFormat(DATETIME_FORMAT).format(d);
//    }

    /**
     * Convert date to String like "yyyy-MM-dd".
     */
    public static String formatDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT).format(d);
    }

    /**
     * Parse date like "yyyy-MM-dd".
     */
    public static Date parseDate(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(d);
        }
        catch(ParseException e) {}
        return null;
    }

    /**
     * Parse date and time like "yyyy-MM-dd hh:mm".
     */
    public static Date parseDateTime(String dt) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
        }
        catch(Exception e) {}
        return null;
    }

    private static final int[] DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Return today's year, month(1-12), day(1-31), week(0-6, for SUN, MON, ... SAT).
     */
    public static int[] getToday() {
        return getDate(Calendar.getInstance());
    }

    /**
     * Return today's time with hour offset.
     */
    public static long getToday(int hourOffset) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hourOffset);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTimeInMillis();
    }

    /**
     * Return day's year, month(1-12), day(1-31), week(0-6, for SUN, MON, ... SAT).
     */
    public static int[] getDate(long t) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        return getDate(c);
    }

    /**
     * Return day's year, month(1-12), day(1-31), week(0-6, for SUN, MON, ... SAT).
     */
    public static int[] getDate(Calendar c) {
        int week = c.get(Calendar.DAY_OF_WEEK)-1;
        if(week==0)
            week = 7;
        return new int[] {
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH),
                week
        };
    }

    /**
     * Return day's year, month(1-12), day(1-31), week(0-6, for SUN, MON, ... SAT), hour, minute, second.
     */
    public static int[] getTime(long t) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        return getTime(c);
    }

    /**
     * Return day's year, month(1-12), day(1-31), week(0-6, for SUN, MON, ... SAT), hour, minute, second.
     */
    public static int[] getTime(Calendar c) {
        int week = c.get(Calendar.DAY_OF_WEEK)-1;
        if(week==0)
            week = 7;
        return new int[] {
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH),
                week,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND)
        };
    }

    /**
     * Get Calendar instance by specified year, month and day.
     * 
     * @param year 4-digit year.
     * @param month Month range is 1-12.
     * @param day Day range is 1-?, end depends on year and month.
     * @return A Calendar instance.
     */
    public static Calendar getCalendar(int year, int month, int day) {
        if(year<2000 || year>2100)
            throw new IllegalArgumentException();
        if(month<1 || month>12)
            throw new IllegalArgumentException();
        if(day<1)
            throw new IllegalArgumentException();
        if(month==2 && isLeapYear(year)) {
            if(day>29)
                throw new IllegalArgumentException();
        }
        else {
            if(day>DAYS[month-1])
                throw new IllegalArgumentException();
        }
        month--;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static boolean isLeapYear(int year) {
        if(year % 100==0) {
            return year % 400==0;
        }
        return year % 4==0;
    }

    public static String format(int hour, int minute, String format) {
        if("HH-mm".equals(format)) {
//            StringBuilder sb = new StringBuilder(5);
        	StringBuffer sb = new StringBuffer(5);
            if(hour<10)
                sb.append('0');
            sb.append(hour).append(':');
            if(minute<10)
                sb.append('0');
            return sb.append(minute).toString();
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static String format(int year, int month, int day, String format) {
        if("MM-dd".equals(format)) {
//            StringBuilder sb = new StringBuilder(5);
        	StringBuffer sb = new StringBuffer(5);
            return sb.append(month).append('-').append(day).toString();
        }
        if("yyyy-MM-dd".equals(format)) {
//            StringBuilder sb = new StringBuilder(10);
        	StringBuffer sb = new StringBuffer(10);
            return sb.append(year).append('-').append(month).append('-').append(day).toString();
        }
        Calendar c = getCalendar(year, month, day);
        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static int[] getPreviousDay(int year, int month, int day) {
        day--;
        if (day < 1) {
            month --;
            if (month < 1) {
                year --;
                month = 12;
            }
            int lastDay = DAYS[month-1];
            if(month==2 && isLeapYear(year))
                lastDay++;
            day = lastDay;
        }
        return new int[] { year, month, day };
    }

    public static int[] getNextDay(int year, int month, int day) {
        day++;
        int max = DAYS[month-1];
        if(month==2 && isLeapYear(year))
            max++;
        if (day > max) {
            day = 1;
            month ++;
            if (month > 12) {
                year++;
                month = 1;
            }
        }
        return new int[] { year, month, day };
    }
    
    public static String format(Timestamp time, String format) {
    	return new SimpleDateFormat(format).format(time);
    }
    
    public static long date_difference(Date date1, Date date2, int v) {
		long diff = 0L;
		try {
			switch (v) {
			case 1: 
				diff = (date1.getTime() - date2.getTime()) / 0x5265c00L;
				break;
			case 2: 
				diff = (date1.getTime() - date2.getTime()) / 0x36ee80L;
				break;
			case 3:
				diff = (date1.getTime() - date2.getTime()) / 60000L;
				break;
			case 4: 
				diff = (date1.getTime() - date2.getTime()) / 1000L;
				break;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return diff;
	}

	public static String dateToString(Date date, String fomat) {
		String dateStr = null;
		SimpleDateFormat df = new SimpleDateFormat(fomat);
		dateStr = df.format(date);
		return dateStr;
	}

	public static String timestampToString(java.sql.Timestamp date, String fomat) {
		String dateStr = null;
		// java.util.Date d=stringToDate(date.toString(),fomat);
		dateStr = date.toString();
		try {

			dateStr = dateStr.substring(0, dateStr.length() - 2);
		} catch (Exception e) {
			System.out.println( e);
			return dateStr;
		}
		return dateStr;
	}

	public static Date getSysDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date stringToDate(String str, String format) {
		Date date = null;
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat(format);
			date = myFormatter.parse(str);
			return date;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return date;
	}

	public static String minuteToTime(long minute) {
		int day, hour;
		String restr = "";
		day = (int) Math.ceil(minute / 24 / 60);
		minute %= 24 * 60;
		hour = (int) Math.ceil(minute / 60);
		minute %= 60;
		restr = day + "��" + hour + "Сʱ" + minute + "��";
		return restr;
	}
	public static java.util.Date add(java.util.Date date, int amount, int field) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	} 

	/**
	 * 
	 * �õ����µ�һ��
	 * @param String
	*/
	public static String getFirstDay() {
		Calendar calendar  =   new  GregorianCalendar();
		 calendar.set( Calendar.DATE,  1 );
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		 String firstDay=simpleFormate.format(calendar.getTime());
		return firstDay;
	}
	/**
	 * 
	 * 返回当月最后一天
	 * @param String
	*/
	public static String getLastDay() {
		Calendar calendar  =   new  GregorianCalendar();
		 calendar.set( Calendar.DATE,  1 );
		 calendar.roll(Calendar.DATE, -1);
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		 String firstDay=simpleFormate.format(calendar.getTime());
		return firstDay;
	}
	/**
	 * 
	 * 返回指定月份的第一天
	 * @param String
	*/
	public static String getFirstDay(Calendar calendar) {
		 calendar.set( Calendar.DAY_OF_MONTH,  1 );
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		 String firstDay=simpleFormate.format(calendar.getTime());
		return firstDay;
	}
	/**
	 * 
	 * 返回指定月份的最后一天
	 * @param String
	*/
	public static String getLastDay(Calendar calendar) {
		 calendar.add(Calendar.MONTH, 1);
		 calendar.set(Calendar.DAY_OF_MONTH, 1);
		 calendar.add(Calendar.DAY_OF_MONTH, -1);
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		 String lastDay=simpleFormate.format(calendar.getTime());
		return lastDay;
	}
	/**
	 * 
	 * �õ����µ�һ��
	 * @param String
	*/
	public static String getNowDay() {
		Calendar calendar  =   new  GregorianCalendar();
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyyMMdd" );
		 String nowDay=simpleFormate.format(calendar.getTime());
		return nowDay;
	}
	/**
	 * 返回当前年月日
	 * @return
	 */
	public static String getNowDate(String forStr) {
		Calendar calendar  =   new  GregorianCalendar();
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat(forStr);
		 String nowDay=simpleFormate.format(calendar.getTime());
		return nowDay;
	}
	
	/**
	 * 返回指定日期偏移指定标志的指定量后的值
	 * 如：参数为：dateStr=2010-01-22;field=Calendar.MONTH;offset=-3
	 * 则返回的值为2009-10-22
	 * @param calendar
	 * @param field 偏移标志，可以取得值为Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH
	 * @param offset 偏移的量：正数向后偏移，负数向前偏移
	 * @return 
	 */
	public static String getDateByParams(String dateStr,int field,int offset){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
			if(calendar==null){
				calendar = Calendar.getInstance();
			}
			calendar.add(field, offset);
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 计算指定2个日期之间经过的天数，包括开始日期和结束日期
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static int throughDays(String startdate,String enddate){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int days = 0;
		try {
			Date startDate = dateFormat.parse(startdate);
			Date endDate = dateFormat.parse(enddate);
			days = (int)((endDate.getTime()-startDate.getTime())/(3600*24*1000)+1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days;
	}
	
	/**
	 * 将时间字符串转化成Calendar对象
	 * @param dateStr 格式为"yyyy-MM-dd"
	 */
	public static Calendar strToCalendar(String dateStr){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
			calendar = null;
		}
		return calendar;
	}
	
	/**
	 * 将calendar对象转换成String 格式为"yyyy-MM-dd"
	 */
	public static String calendarToStr(Calendar calendar){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 获取2个时间之间的间隔时间
	 * @param fromTime 格式为yyyy-MM-dd HH:mm:ss
	 * @param toTime 格式为yyyy-MM-dd HH:mm:ss
	 * @return 间隔的小时数
	 * Date:Jul 17, 2011 
	 * Time:11:24:38 PM
	 * @throws ParseException 
	 */
	public static double getInterval(String fromTime,String toTime) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fromDate = dateFormat.parse(fromTime);
		Date toDate = dateFormat.parse(toTime);
		return ((double)(toDate.getTime()-fromDate.getTime()))/(1000*60*60);
	}
	
	/**
	 * 保留r位小数
	 * @param s 要操作的数据
	 * @param r 要保留的小数位数
	 * @return
	 * Date:Jul 17, 2011 
	 * Time:11:35:22 PM
	 */
	public static double round(double s,int r){
		int temp = 1;
		for(int i = 0;i<r;i++){
			temp = temp*10;
		}
		s = s*temp;
		return (double)(Math.round(s))/temp;
	}
}
