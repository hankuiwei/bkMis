package com.zc13.bkmis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.ClientBill;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ECallInfo;

/**
 * 
 * @author 王正伟
 * Date：Dec 23, 2010
 * Time：11:32:47 AM
 */
public interface ICostTransactDAO extends BasicDAO{
	
	/**
	 * 执行sql语句，返回执行结果
	 * 该方法只针对计费参数类型中的sql语句的执行，执行的结果只能是一个值
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public String query(String sql) throws Exception;
	
	/**
	 * 查询CompactRoomCoststandard对象列表
	 * 获得当前有效合同客户下的客户对应收费标准
	 * @param String 日期
	 * @param Integer 客户id
	 * @param Integer 合同id
	 * @return
	 * @throws Exception
	 */
	public List<CompactRoomCoststandard> queryUsedCoststandard(String todayDate,Integer clientId,Integer compactId) throws Exception;

	/**
	 * 根据收费项id获得收费项对象
	 * @param itemId
	 * @return
	 */
	public CItems getItemsById(Integer itemId)throws Exception;
	

	/**
	 * 获得需要缴费客户列表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<ClientBill> getPressMoneyClient(CostTransactForm form) throws Exception;

	/**
	 * 获取指定日期期间的有效客户及预存款金额
	 * @param dateStr
	 * @return
	 */
	public List getCurAvailClient(CostTransactForm form) throws Exception;
	/**
	 * 获取指定日期期间的有效客户及预存物业费 金额
	 * @param dateStr
	 * @return
	 */
	public List getCurAvailClientWithAdvanceWuYF(CostTransactForm form) throws Exception ;
	/**
	 * 根据合同id找到客户
	 * @param compactId
	 * @return
	 */
	public CompactClient getClientByCompactId(Integer compactId);

	/**
	 * 根据客户id和合同id找到其他的有效合同
	 * @param id
	 * @param compactId
	 * @return
	 */
	public Compact getCompactByClientIdAndCompactId(Integer id,
			Integer compactId);
	
	/**
	 * 获取客户下给的时间段的有效房租收费标准列表
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<CompactRoomCoststandard> getUsedRentCoststandard(Integer clientId,String startDate,String endDate) throws Exception;
	

	/**
	 * 根据客户id得到该客户下的有效合同
	 * ICostTransactDAO.getAvailCompactByClientId
	 */
	public List<Compact> getAvailCompactByClientId(int clientId) throws Exception;
	
	public Integer save(Object obj) throws Exception;
	
	public List getPressEarnestClient(CostTransactForm form) throws Exception;
	
	public int countCompact();
	
	/**
	 * 根据客户或帐套id获得客户对应房租收费标准
	 * ICostTransactDAO.queryUsedRentCoststandard
	 */
	public List<CompactRoomCoststandard> queryUsedRentCoststandard(Integer clientId,Integer accountTemplateId) throws Exception;

	/**
	 * 根据条件查询得到合同房租对象列表
	 * ICostTransactDAO.getRent
	 */
	public List<CompactRent> getRent(Map map) throws Exception;

	/**
	 * 获得需要缴房租押金客户列表
	 * ICostTransactDAO.getPressDepositClient
	 */
	public List getPressDepositRentClient(CostTransactForm form) throws Exception;
	
	/**
	 * 获得需要缴装修押金客户列表
	 * ICostTransactDAO.getPressDepositClient
	 */
	public List getPressDepositDecorationClient(CostTransactForm form) throws Exception;

	/**
	 * 获取当期已入住客户
	 * @return
	 * @throws Exception
	 * Date:Nov 24, 2011 
	 * Time:1:39:34 AM
	 */
	public List getCurClient(Integer clientId,Integer compactId) throws Exception;

	/**
	 * 根据lpId获取帐套信息
	 * @param lpId
	 * @return
	 * @throws Exception
	 * Date:Nov 24, 2011 
	 * Time:1:55:53 AM
	 */
	public CAccounttemplate getAccounttemplateByLpId(Integer lpId) throws Exception;

	/**
	 * 根据客户id获取客户当前使用的电话号码
	 * @param id
	 * Date:Nov 24, 2011 
	 * Time:2:01:27 AM
	 */
	public List getPhoneNumByClientId(Integer id) throws Exception;

	/**
	 * 统计指定电话号码下的未缴的通话费用总计
	 * @param phoneNum
	 * @return
	 * Date:Nov 24, 2011 
	 * Time:2:11:57 AM
	 */
	public List countMoneyByPhoneNum(String phoneNum) throws Exception;

	/**
	 * 根据value字段获取item
	 * @param string
	 * @return
	 * @throws Exception
	 * Date:Nov 24, 2011 
	 * Time:2:31:07 AM
	 */
	public CItems getItemByValue(String string) throws Exception;
	
	/**
	 * @author luq
	 * 得到指定号码的生成账单的通话记录
	 * @param phone
	 * @return
	 * @throws Exception
	 * Date:Nov 24, 2011 
	 * Time:2:31:07 AM
	 */
	public List<ECallInfo> getCallInfoByNumber(String phone) throws Exception;

	/***
	 * 得到物业季付费
	 * @param form
	 * @return
	 * @throws Exception
	 * Date:2018-1-17 
	 * Time:上午09:27:53
	 */
	List<CompactRoomCoststandard> getValidRentCoststandard(Integer clientId,
			String startDate, String endDate) throws Exception;
	
	/***
	 * 获取定义的全部的收费标准.
	 * @return
	 * Date:2018-3-2 
	 * Time:下午10:21:28
	 * author : gd
	 */
	List<CCoststandard> getAllCoststandard();
}
