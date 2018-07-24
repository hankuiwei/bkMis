package com.zc13.demo.action;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

public class T1 extends HttpServlet implements ServletContextListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//服务器停止时执行该事件
    public void contextDestroyed(ServletContextEvent sce) {
         DeleteFile();//删除启动服务器时建立的文件
     }
    //服务器启动时执行该事件
    public void contextInitialized(ServletContextEvent sce) {
         WriteFile();//添加一个新的文本文件
     }

    public void WriteFile() {
        try {
             Timer timer = new Timer();
             //schedule()方法的第二个参数是指第一次执行的时间，第三个参数是指每隔多长时间执行一次
             timer.schedule( new MyTimerTask(),0,2000);
         } catch (Exception e) {
         }
     }

    public void DeleteFile() {
         
     }

}
