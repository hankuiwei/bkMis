package com.zc13.quartz;

import java.util.List;

import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.service.ICalculateMoneyService;

/**
 * 定时汇总没有汇总过的通过校验后的合同
 * @author Administrator
 * @Date 2013-3-31
 * @Time 下午1:06:19
 */
public class CalculateJob {

	private ICalculateMoneyService calculateService;

	public ICalculateMoneyService getCalculateService() {
		return calculateService;
	}

	public void setCalculateService(ICalculateMoneyService calculateService) {
		this.calculateService = calculateService;
	}


	/**
	 * 计算
	 */
	public void doCalculate(){
		
		List<Integer> compactIds = calculateService.getNeedCalculateCompactIds();
		for(int id : compactIds){
			List<CompactRoomCoststandard> compactRoomCoststandard = calculateService.getCompactRoomCoststandardById(id);
			CAccounttemplate accountTemplate = calculateService.getAccounttemlateByCompactId(id);
			try {
				calculateService.CalculateMoney(compactRoomCoststandard, accountTemplate, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("test--------------"+id);
		}
	}
}
