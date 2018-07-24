package com.zc13.util.tiling;

import com.zc13.bkmis.service.ICompactManagerService;

public class MyScheduleTask2 extends SchedulerTask{

	private ICompactManagerService compactManagerService;
	public MyScheduleTask2(ICompactManagerService compactManagerService){
		this.compactManagerService = compactManagerService;
	}
	@Override
    public void run() {
		System.out.println("判断合同状态并执行更改");
		compactManagerService.setDead();
          
    }

	
}
