package com.zc13.util.tiling;

import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.util.GlobalMethod;

public class MyScheduleTask extends SchedulerTask{
	
	private static String dateStr = GlobalMethod.getTime();
	private static boolean isRun = false;
	private ICostTransactService costTransactService;
	
	public MyScheduleTask(ICostTransactService costTransactService){
		this.costTransactService = costTransactService;
	}
	@Override
    public void run() {
		//更新提醒表
		costTransactService.updateAwokeTask4PressMoney();
		costTransactService.updateAwokeTask4PressRentDeposit();
		costTransactService.updateAwokeTask4PressDecorationDeposit();
		costTransactService.updateAwokeTask4PressAdvance();
		
	 	String nowDate = GlobalMethod.getTime();
	 		isRun = true;
	 		dateStr = nowDate;
	 		
	 		costTransactService.autoBuildBills();
	 		
    }
	
}
