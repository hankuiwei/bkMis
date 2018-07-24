package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.CalculateMoneyForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CalculateMoney;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;

public interface ICalculateMoneyService extends IBasicService {

	/**
	 * 获取需要计算的合同
	 * @param compactId
	 * @return
	 * Date:2013-3-23 
	 * Time:下午2:01:40
	 */
	public List<Compact> getNeedCompact(Integer compactId);
	
	/**
	 * 获取需要的帐套信息
	 * @param lpId
	 * @return
	 * Date:2013-3-23 
	 * Time:下午2:02:24
	 */
	public CAccounttemplate getAccounttemlateByLpId(Integer lpId);
	
	/**
	 * 计算
	 * @param compactList
	 * @param accountTemplate
	 * @param compactId
	 * Date:2013-3-23 
	 * Time:下午2:05:02
	 */
	public void CalculateMoney(List<CompactRoomCoststandard> compactRoomCoststandard,CAccounttemplate accountTemplate,Integer compactId) throws Exception;
	
	/**
	 * 查询标准
	 * @return
	 * Date:2013-3-23 
	 * Time:下午3:22:01
	 */
	public List<CompactRoomCoststandard> getCompactRoomCoststandardById(Integer compactId);
	
	/**
	 * 统计合同一年的收费
	 * @return
	 * Date:2013-3-27 
	 * Time:下午10:27:30
	 */
	public List<CalculateMoney> getYearCompactMoney(CalculateMoneyForm form);
	
	/**
	 * 统计数量
	 * @param form
	 * @return
	 * Date:2013-3-27 
	 * Time:下午10:45:45
	 */
	public int getYearCompactMoneyCount(CalculateMoneyForm form);
	
	/**
	 * 年度的详细费用
	 * @param form
	 * @return
	 * Date:2013-3-27 
	 * Time:下午10:48:58
	 */
	public List<CalculateMoney> getYearDetail(CalculateMoneyForm form);
	
	/**
	 * 获取月的详细信息
	 * @param form
	 * @return
	 * Date:2013-3-27 
	 * Time:下午10:51:50
	 */
	public List getMonthDetail(CalculateMoneyForm form);
	
	/**
	 * 获取月的详细信息数量
	 * @param form
	 * @return
	 * Date:2013-3-27 
	 * Time:下午10:51:50
	 */
	public int getMonthDetailCount(CalculateMoneyForm form);
	
	/**
	 * 获取合同账单费用
	 * @param form
	 * @return
	 * Date:2013-3-30 
	 * Time:下午2:30:30
	 */
	public List getCompactBill(CalculateMoneyForm form);
	
	/**
	 * 获取需要汇总的合同的id
	 * @return
	 * Date:2013-3-31 
	 * Time:下午1:12:28
	 */
	public List<Integer> getNeedCalculateCompactIds();
	
	/**
	 * 获取需要的帐套信息
	 * @param lpId
	 * @return
	 * Date:2013-3-23 
	 * Time:下午2:02:24
	 */
	public CAccounttemplate getAccounttemlateByCompactId(Integer compactId);
}
