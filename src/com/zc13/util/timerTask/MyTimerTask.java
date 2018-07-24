package com.zc13.util.timerTask;

import java.util.TimerTask;

import com.zc13.bkmis.service.IPhoneCostService;

public class MyTimerTask extends TimerTask{
	private IPhoneCostService phoneCostService;
	public MyTimerTask(IPhoneCostService phoneCostService){
		this.phoneCostService = phoneCostService;
	}
	@Override
    public void run() {
		phoneCostService.storeCallInfo(null,null);
    }
}
