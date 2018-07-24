package com.zc13.bkmis.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IFinancialReportDAO;
import com.zc13.bkmis.form.FinancialReportForm;
import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;
import com.zc13.bkmis.service.IFinancialReportService;

/**
 * 接口实现
 * @author Administrator
 * @Date 2013-5-13
 * @Time 下午9:14:28
 */
public class FinancialReportServiceImpl extends BasicServiceImpl implements IFinancialReportService {

	private IFinancialReportDAO financialReportDao;

	public IFinancialReportDAO getFinancialReportDao() {
		return financialReportDao;
	}

	public void setFinancialReportDao(IFinancialReportDAO financialReportDao) {
		this.financialReportDao = financialReportDao;
	}

	@Override
	public List<OperateCost> queryOperateCostList(FinancialReportForm financialReportForm, boolean isPage)
			throws DataAccessException {
		
		return financialReportDao.queryOperateCostList(financialReportForm, isPage);
	}

	@Override
	public int queryCounttotal(FinancialReportForm financialReportForm) {
		
		return financialReportDao.queryCounttotal(financialReportForm);
	}

	@Override
	public OperateCost queryOperateCostById(FinancialReportForm financialReportForm) {
		
		return (OperateCost)financialReportDao.getObject(OperateCost.class, financialReportForm.getOperateCost().getId());
	}

	/**
	 * 保存或是更新
	 */
	@Override
	public void SaveOrUpdate(FinancialReportForm financialReportForm) {
		
		if(financialReportForm.getOperateCost().getId() == null || financialReportForm.getOperateCost().getId() == 0){
			financialReportDao.saveObject(financialReportForm.getOperateCost());
		}else if(financialReportForm.getOperateCost().getId().intValue()>0){
			financialReportDao.updateObject(financialReportForm.getOperateCost());
		}
	}

	@Override
	public void delById(String id) {
		
		OperateCost cost = (OperateCost)financialReportDao.getObject(OperateCost.class, Integer.parseInt(id));
		financialReportDao.deleteObject(cost);
	}

	/**
	 * 查询经营收入
	 */
	@Override
	public List<OperateIncome> queryOperateIncomeList(FinancialReportForm financialReportForm, boolean isPage) throws DataAccessException {
		
		return financialReportDao.queryOperateIncomeList(financialReportForm, isPage);
	}

	/**
	 * 查询经营收入 count
	 */
	@Override
	public int queryIncomeCounttotal(FinancialReportForm financialReportForm) {
		
		return financialReportDao.queryIncomeCounttotal(financialReportForm);
	}

	@Override
	public OperateIncome queryOperateIncomeById(FinancialReportForm financialReportForm) {
		
		return (OperateIncome)financialReportDao.getObject(OperateIncome.class, financialReportForm.getOperateIncome().getId());
	}

	@Override
	public void SaveOrUpdateIncome(FinancialReportForm financialReportForm) {
		
		if(financialReportForm.getOperateIncome().getId() == null || financialReportForm.getOperateIncome().getId() == 0){
			financialReportDao.saveObject(financialReportForm.getOperateIncome());
		}else if(financialReportForm.getOperateIncome().getId().intValue()>0){
			financialReportDao.updateObject(financialReportForm.getOperateIncome());
		}
	}
	
	@Override
	public void delIncomeById(String id) {
		
		OperateIncome income = (OperateIncome)financialReportDao.getObject(OperateIncome.class, Integer.parseInt(id));
		financialReportDao.deleteObject(income);
	}

	@Override
	public List<ProjectCosts> queryProjectCosts(FinancialReportForm financialReportForm, boolean isPage)throws DataAccessException {
		
		return financialReportDao.queryProjectCosts(financialReportForm, isPage);
	}

	/**
	 * 获取数量
	 */
	@Override
	public int queryProjectsCounttotal(FinancialReportForm financialReportForm) {
		
		return financialReportDao.queryProjectsCounttotal(financialReportForm);
	}

	/**
	 * 查询对象
	 */
	@Override
	public ProjectCosts queryProjectCostsById(FinancialReportForm financialReportForm) {
		
		return (ProjectCosts)financialReportDao.getObject(ProjectCosts.class, financialReportForm.getProjectCosts().getId());
	}

	/**
	 * 保存或更新项目费用
	 */
	@Override
	public void SaveOrUpdateProjectCosts(FinancialReportForm financialReportForm) {
		
		if(financialReportForm.getProjectCosts().getId() == null || financialReportForm.getProjectCosts().getId() == 0){
			financialReportDao.saveObject(financialReportForm.getProjectCosts());
		}else if(financialReportForm.getProjectCosts().getId().intValue()>0){
			financialReportDao.updateObject(financialReportForm.getProjectCosts());
		}
	}

	/**
	 * 删除项目费用
	 */
	@Override
	public void delProjectCostsById(String id) {
		
		ProjectCosts project = (ProjectCosts)financialReportDao.getObject(ProjectCosts.class, Integer.parseInt(id));
		financialReportDao.deleteObject(project);
	}

	/**
	 * 查询房租
	 */
	@Override
	public double queryRentByTime(String date, String item) {
		
		return financialReportDao.queryRentByTime(date, item);
	}

	@Override
	public OperateCost queryCostByTime(String year, String month, String type) {
		
		return financialReportDao.queryCostByTime(year, month, type);
	}

	@Override
	public OperateCost queryCostLj(String year, String month,String type) {
		
		return financialReportDao.queryCostLj(year,month,type);
	}

	@Override
	public OperateIncome queryIncomeByTime(String year, String month,String type) {
		
		return financialReportDao.queryIncomeByTime(year, month, type);
	}

	@Override
	public OperateIncome queryIncomeLj(String year, String month,String type) {
		
		return financialReportDao.queryIncomeLj(year, month,type);
	}

	@Override
	public ProjectCosts queryProjectByTime(String dept, String year,String month, String type) {
		
		return financialReportDao.queryProjectByTime(dept, year, month, type);
	}

	@Override
	public ProjectCosts queryProjectLj(String year, String month, String type) {
		
		return financialReportDao.queryProjectLj(year, month, type);
	}
}
