package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.FinancialReportForm;
import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;

/**
 * 财务报表接口 dao
 * @author Administrator
 * @Date 2013-5-13
 * @Time 下午9:18:25
 */
public interface IFinancialReportDAO extends BasicDAO {

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
	 *  查询列表
	 * @param financialReportForm
	 * @param isPage
	 * @return
	 * @throws DataAccessException
	 * Date:2013-5-16 
	 * Time:下午9:54:55
	 */
	public List<ProjectCosts> queryProjectCosts(FinancialReportForm financialReportForm, boolean isPage)throws DataAccessException;
	
	/**
	 * 查询列表数量
	 * @param financialReportForm
	 * @return
	 * Date:2013-5-16 
	 * Time:下午9:55:59
	 */
	public int queryProjectsCounttotal(FinancialReportForm financialReportForm);
	
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
