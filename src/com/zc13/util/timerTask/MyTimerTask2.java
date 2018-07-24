package com.zc13.util.timerTask;

import java.util.TimerTask;

import com.zc13.bkmis.service.ICompactManagerService;
public class MyTimerTask2 extends TimerTask{
	private ICompactManagerService compactManagerService;
	public MyTimerTask2(ICompactManagerService compactManagerService){
		this.compactManagerService = compactManagerService;
	}
	@Override
    public void run() {
		System.out.println("判断合同状态并执行更改");
		compactManagerService.setDead();
          
    }
}
