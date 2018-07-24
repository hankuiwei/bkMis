package com.zc13.demo.action;

import java.util.TimerTask;

import com.zc13.util.GlobalMethod;

public class MyTimerTask extends TimerTask {
 
     @Override
     public void run() {
    	 
    	String nowDate = GlobalMethod.getTime();
 	 	System.out.println(nowDate);
 	 	if("2010-01-21".equals(nowDate)){
 	 		 System.out.println( " 备份程序运行…… " );
 	 	}
           
    }

} 

