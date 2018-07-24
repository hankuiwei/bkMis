package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.ClientBill;

public interface ICostTransactService {
	
	/**
	 * 执行公式中参数对应的sql，返回公式中所有参数对应的sql所执行的结果
	 * @param params 如：{costId=11,costType=elec,......}
	 * @param list 如：{计费面积,单价}
	 * @return map(参数类型名称-对应sql执行得到的结果)如：{计费面积=85,单价=20}
	 */
	public Map getValueMapBySqls(Map params,List paramNames) throws Exception;
	
	/**
	 * 根据参数map和原始公式得到处理后的表达式
	 * @param params{costId=11,costType=elec,......}
	 * @param expressions 公式 eg:{计费面积}*{单价}-12
	 * @return e.g:80.0*56.6-12
	 * @throws Exception 
	 */
	public String getExpression(Map params,String expressions) throws Exception;
	
	/**
	 * 结账日自动生成账单
	 * @throws Exception
	 */
	public void autoBuildBills();
	
	/**通知入住后,自动生成预收物业费 订单 2018年1月25日15:55:32 gd*/
	public void autoBuildWuYFBill(boolean isAuto);
	
	/**
	 * 获得滞纳金
	 * @param bill
	 * @return 保存有滞纳金额的bill对象
	 * @throws Exception 
	 */
	public CBill getDelaying(CBill bill) throws Exception;
	
	/**
	 * 生成特定客户的账单
	 * @param clientId
	 */
	public List buildBillsByClientId(Integer clientId,Integer compactId,String dateStr);
	
	/**
	 * 更新awoke_task表中的未交款客户数量
	 */
	public void updateAwokeTask4PressMoney();
	
	/**
	 * 获得需要缴费客户列表
	 * @return
	 */
	public List<ClientBill> getPressMoneyClient(CostTransactForm form) throws Exception;
	
	/**
	 * 更新awoke_task表中的需缴纳预收款客户的数量
	 */
	public void updateAwokeTask4PressAdvance();
	
	/**
	 * 获得需要缴纳预收款的客户列表
	 * @return
	 */
	public List getPressAdvanceClient(CostTransactForm form) throws Exception;
	
	/**
	 * 更新awoke_task表中的未交房租押金客户数量
	 */
	public void updateAwokeTask4PressRentDeposit();
	
	/**
	 * 更新awoke_task表中的未交装修押金客户数量
	 */
	public void updateAwokeTask4PressDecorationDeposit();
	
	/**
	 * 获得需要缴押金客户列表
	 * @return
	 */
	public List getPressDepositClient(CostTransactForm form) throws Exception;
	
	/**
	 * 获取需要缴纳订金的客户
	 * @param form
	 * @return
	 * @throws Exception
	 * Date:May 24, 2011 
	 * Time:11:38:19 PM
	 */
	public List getPressEarnestClient(CostTransactForm form) throws Exception;
	
	/**
	 * 更新awoke_task表中的未交定金客户数量
	 * Date:May 18, 2011 
	 * Time:12:08:43 PM
	 */
	public void updateAwokeTask4PressEarnest();

	/**
	 * 更新awoke_task表中已缴定金但未入住客户数量
	 * Date:May 18, 2011 
	 * Time:12:09:09 PM
	 */
	public void updateAwokeTask4PressENotIn();
	
	/**
	 * 获得指定时间段内指定客户或帐套下的房租
	 * ICostTransactService.countRentCharge
	 */
	public double countRentCharge(Integer clientId,Integer accountTemplateId1,String startDate,String endDate);

	/**
	 * 清除账单
	 * ICostTransactService.clearDB
	 */
	public void clearBill();
	
	/**
	 * 清除账单日志，用于可以再次生成账单
	 * ICostTransactService.clearBillLog
	 */
	public void clearBillLog();
}
