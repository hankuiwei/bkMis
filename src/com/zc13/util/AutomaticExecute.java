package com.zc13.util;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zc13.bkmis.service.ICompactManagerService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.IPhoneCostService;
import com.zc13.util.tiling.DailyIterator;
import com.zc13.util.tiling.MyScheduleTask;
import com.zc13.util.tiling.MyScheduleTask2;
import com.zc13.util.tiling.Scheduler;
import com.zc13.util.timerTask.MyTimerTask;


/**
 * 服务启动时自动开始运行
 * @author daokui
 * Date：Dec 21, 2010
 * Time：11:58:40 AM
 */
public class AutomaticExecute extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID = 1L;
	//服务器停止时执行该事件
    public void contextDestroyed(ServletContextEvent sce) {
         
     }
    //服务器启动时执行该事件
    public void contextInitialized(ServletContextEvent sce) {
    	//String path = sce.getServletContext().getRealPath("/") + sce.getServletContext().getInitParameter("springConfig");
    	String path = sce.getServletContext().getRealPath("/") + "WEB-INF/applicationContext*.xml";
    	ApplicationContext ac = new FileSystemXmlApplicationContext(path);
    	ICostTransactService costTransactService = (ICostTransactService)ac.getBean("costTransactService");
    	ICompactManagerService compactManagerService = (ICompactManagerService)ac.getBean("ICompactManagerService");
    	IPhoneCostService phoneCostService = (IPhoneCostService)ac.getBean("phoneCostService");
        try {
        	//第一次开机时执行
        	firstTimeRun(costTransactService);
        	
        	/**每天执行 start*/
        	Scheduler scheduler1 = new Scheduler();
        	Scheduler scheduler2 = new Scheduler();
        	scheduler1.schedule(new MyScheduleTask(costTransactService), new DailyIterator(4, 35, 10));
        	scheduler2.schedule(new MyScheduleTask2(compactManagerService), new DailyIterator(5, 1, 10));
        	/**每天执行 end*/
        	
        	/**电话费 计费定时执行*/
        	PropertiesReader properties = null;
        	//读取配置文件
			properties = new PropertiesReader("/propertyInfo.properties");
        	//循环执行的代码
        	Timer timer1 = new Timer();
        	//第二个参数为延时1000*60*5毫秒执行，周期是第三个参数的1000*60*5
        	int getPhoneInfoCircle = Integer.parseInt(properties.getValue("getPhoneInfoCircle"));
        	timer1.schedule(new MyTimerTask(phoneCostService), 1000*60,getPhoneInfoCircle);
        	
         } catch (Exception e) {
        	 e.printStackTrace();
         }
     }
    public void firstTimeRun(ICostTransactService costTransactService){
    	//更新提醒表
		costTransactService.updateAwokeTask4PressMoney();
		costTransactService.updateAwokeTask4PressRentDeposit();
		costTransactService.updateAwokeTask4PressDecorationDeposit();
		costTransactService.updateAwokeTask4PressAdvance();
	 	//if(!dateStr.equals(nowDate)||!isRun){
		/**2018年3月2日15:22:48 gd : 系统启动时执行 自动创建账单*/
 		costTransactService.autoBuildBills();
 		
	 	//}
    }
}
