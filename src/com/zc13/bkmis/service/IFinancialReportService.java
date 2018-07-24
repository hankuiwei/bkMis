package com.zc13.bkmis.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.FinancialReportForm;
import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;

/**
 * 财务报表 service
 * @author Administrator
 * @Date 2013-5-13
 * @Time 下午9:13:07
 */
public interface IFinancialReportService {

	/**
	 * 查询经营成本list
	 * @param financialReportForm
	 * @param isPage
	 * @return
	 * @throws DataAccessException
	 * Date:2013-5-13 
	 * Time:下午10:35:58
	 */
	public List<OperateCost> queryOperateCostList (FinancialReportForm financialReportForm,boolean isPage) throws DataAccessException;
	
	/**
	 * 查询经营成本count
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:36:13
	 */
	public int queryCounttotal(FinancialReportForm financialReportForm);
	
	/**
	 * 根据主键查询对象
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:44:56
	 */
	public OperateCost queryOperateCostById(FinancialReportForm financialReportForm);
	
	/**
	 * 保存或是更新
	 * @param financialReportForm
	 * Date:2013-5-13 
	 * Time:下午10:49:44
	 */
	public void SaveOrUpdate(FinancialReportForm financialReportForm);
	
	/**
	 * 删除经营成本
	 * @param id
	 * Date:2013-5-14 
	 * Time:下午10:11:26
	 */
	public void delById(String id);
	
	/**
	 * 查询经营收入
	 * @param financialReportForm
	 * @param isPage
	 * @return
	 * @throws DataAccessException
	 * Date:2013-5-14 
	 * Time:下午10:39:24
	 */
	public List<OperateIncome> queryOperateIncomeList (FinancialReportForm financialReportForm,boolean isPage) throws DataAccessException;
	
	/**
	 * 查询经营收入count
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:36:13
	 */
	public int queryIncomeCounttotal(FinancialReportForm financialReportForm);
	
	/**
	 * 根据主键查询对象
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:44:56
	 */
	public OperateIncome queryOperateIncomeById(FinancialReportForm financialReportForm);
	
	/**
	 * 保存或是更新
	 * @param financialReportForm
	 * Date:2013-5-13 
	 * Time:下午10:49:44
	 */
	public void SaveOrUpdateIncome(FinancialReportForm financialReportForm);
	
	/**
	 * 删除经营收入
	 * @param id
	 * Date:2013-5-14 
	 * Time:下午10:11:26
	 */
	public void delIncomeById(String id);
	
	/**
	 * 查询项目费用
	 * @param financialReportForm
	 * @param isPage
	 * @return
	 * @throws DataAccessException
	 * Date:2013-5-16 
	 * Time:下午9:48:46
	 */
	public List<ProjectCosts> queryProjectCosts(FinancialReportForm financialReportForm,boolean isPage) throws DataAccessException;
	
	/**
	 * 查询项目费用count
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:36:13
	 */
	public int queryProjectsCounttotal(FinancialReportForm financialReportForm);
	
	/**
	 * 根据主键查询对象
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-13 
	 * Time:下午10:44:56
	 */
	public ProjectCosts queryProjectCostsById(FinancialReportForm financialReportForm);
	
	/**
	 * 保存或是更新
	 * @param financialReportForm
	 * Date:2013-5-13 
	 * Time:下午10:49:44
	 */
	public void SaveOrUpdateProjectCosts(FinancialReportForm financialReportForm);
	
	/**
	 * 删除项目费用
	 * @param id
	 * Date:2013-5-14 
	 * Time:下午10:11:26
	 */
	public void delProjectCostsById(String id);
	
	/**
	 *  查询房租
	 * @param date
	 * @param item
	 * @return
	 * Date:2013-5-19 
	 * Time:下午8:38:03
	 */
	public double queryRentByTime(String date,String item);
	
	/**
	 * 查询经营成本
	 * @param date
	 * @param type
	 * @return
	 * Date:2013-5-19 
	 * Time:下午9:24:01
	 */
	public OperateCost queryCostByTime(String year,String month,String type);
	
	/**
	 * 查询经营成本年累计
	 * @param endDate
	 * @param beginDate
	 * @return
	 * Date:2013-5-19 
	 * Time:下午9:25:17
	 */
	public OperateCost queryCostLj(String year,String month,String type);
	
	/**
	 * 查询经营收入
	 * @param date
	 * @param type
	 * @return
	 * Date:2013-5-19 
	 * Time:下午9:24:01
	 */
	public OperateIncome queryIncomeByTime(String year,String month,String type);
	
	/**
	 * 查询经营收入年累计
	 * @param endDate
	 * @param beginDate
	 * @return
	 * Date:2013-5-19 
	 * Time:下午9:25:17
	 */
	public OperateIncome queryIncomeLj(String year,String month,String type);
	
	/**
	 * 查询项目费用
	 * @param dept
	 * @param date
	 * @param type
	 * @return
	 * Date:2013-5-19 
	 * Time:下午9:27:52
	 */
	public ProjectCosts queryProjectByTime(String dept,String year,String month,String type);
	
	/**
	 * 查询项目费用的累计
	 * @param dept
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 * Date:2013-5-21 
	 * Time:下午8:52:38
	 */
	public ProjectCosts queryProjectLj(String year,String month,String type);
}
