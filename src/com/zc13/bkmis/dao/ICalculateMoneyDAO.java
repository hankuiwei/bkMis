package com.zc13.bkmis.dao;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.CalculateMoneyForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CalculateMoney;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;

/**
 * 合同预算 dao
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午10:56:30
 */
public interface ICalculateMoneyDAO extends BasicDAO {

	/**
	 * 获取需要计算的合同
	 * @param compactId
	 * @return
	 * Date:2013-3-23 
	 * Time:下午1:53:05
	 */
	public List<Compact> getNeedCalculateCompact(Integer compactId);
	
	/**
	 * 获取该楼盘下的帐套
	 * @param lpId
	 * @return
	 * Date:2013-3-23 
	 * Time:下午1:54:55
	 */
	public CAccounttemplate getAccounttemplateById(Integer lpId);
	
	/**
	 * 查询标准
	 * @return
	 * Date:2013-3-23 
	 * Time:下午3:24:27
	 */
	public List<CompactRoomCoststandard> getCompactRoomCoststandardByCompactId(Integer compactId);
	
	/**
	 * 获取计算参数单价
	 * @param map
	 * @return
	 * Date:2013-3-23 
	 * Time:下午4:28:59
	 */
	public List<CompactRent> getRent(Map map);
	
	public String query(String sql) throws Exception;
	public List<CCostparatype> getCostParaTypeByNames(List typeNames);
	
	/**
	 * 删除汇总的合同的钱
	 * @param compactId
	 * Date:2013-3-27 
	 * Time:下午10:06:57
	 */
	public void deleteCalculateMoney(Integer compactId);
	
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
	 * 根据楼盘id查询帐套
	 */
	public CAccounttemplate getAccounttemlateByCompactId(Integer compactId);
}
