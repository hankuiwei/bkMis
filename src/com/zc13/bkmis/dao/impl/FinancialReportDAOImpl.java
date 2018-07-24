package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IFinancialReportDAO;
import com.zc13.bkmis.form.FinancialReportForm;
import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/***
 * 财务报表 dao 实现
 * @author Administrator
 * @Date 2013-5-13
 * @Time 下午9:20:54
 */
public class FinancialReportDAOImpl extends BasicDAOImpl implements IFinancialReportDAO {

	@SuppressWarnings("unchecked")
	public List<OperateCost> queryOperateCostList (FinancialReportForm financialReportForm,boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from OperateCost where 1=1");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateCost().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getOperateCost().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateCost().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getOperateCost().getMonth()+"'");
			}
		}
		hql.append(" order by id");
		List<OperateCost> list = new ArrayList<OperateCost>();
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(),"1")) - 1);
			list = (List<OperateCost>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<OperateCost>)query.list();
		}
		return list;
	}
	
	//获取招商计划信息
	public int queryCounttotal(FinancialReportForm financialReportForm){
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m) from OperateCost as m where 1=1 ");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateCost().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getOperateCost().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateCost().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getOperateCost().getMonth()+"'");
			}
		}
		try{
			Query query = this.getSession().createQuery(hql.toString());
			List list = query.list();
			if(list != null && list.size()>0){
				count = (Integer)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 查询经营收入
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OperateIncome> queryOperateIncomeList(FinancialReportForm financialReportForm, boolean isPage) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from OperateIncome where 1=1 ");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateIncome().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getOperateIncome().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateIncome().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getOperateIncome().getMonth()+"'");
			}
		}
		hql.append(" order by id");
		List<OperateIncome> list = new ArrayList<OperateIncome>();
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(),"1")) - 1);
			list = ((List<OperateIncome>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list());
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<OperateIncome>)query.list();
		}
		return list;
	}

	/**
	 * 经营收入数量
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int queryIncomeCounttotal(FinancialReportForm financialReportForm) {
		
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m) from OperateIncome as m where 1=1 ");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateIncome().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getOperateIncome().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getOperateIncome().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getOperateIncome().getMonth()+"'");
			}
		}
		try{
			Query query = this.getSession().createQuery(hql.toString());
			List list = query.list();
			if(list != null && list.size()>0){
				count = (Integer)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCosts> queryProjectCosts(FinancialReportForm financialReportForm, boolean isPage) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from ProjectCosts where 1=1");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getProjectCosts().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getProjectCosts().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getProjectCosts().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getProjectCosts().getMonth()+"'");
			}
		}
		hql.append(" order by id");
		List<ProjectCosts> list = new ArrayList<ProjectCosts>();
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(financialReportForm.getCurrentpage(),"1")) - 1);
			list = ((List<ProjectCosts>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list());
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<ProjectCosts>)query.list();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int queryProjectsCounttotal(FinancialReportForm financialReportForm) {
		
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m) from ProjectCosts as m where 1=1 ");
		if(financialReportForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(financialReportForm.getProjectCosts().getYear()).equals("")){
				hql.append(" and year = '"+financialReportForm.getProjectCosts().getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(financialReportForm.getProjectCosts().getMonth()).equals("")){
				hql.append(" and month = '"+financialReportForm.getProjectCosts().getMonth()+"'");
			}
		}
		try{
			Query query = this.getSession().createQuery(hql.toString());
			List list = query.list();
			if(list != null && list.size()>0){
				count = (Integer)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 查询房租
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public double queryRentByTime(String date, String item) {
		
		double money = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(billsExpenses) from CBill where itemId = (select id from CItems where value='");
		sql.append(item);
		sql.append("') and billDate='");
		sql.append(date);
		sql.append("'");
		
		try{
			Query query = this.getSession().createQuery(sql.toString());
			List list = query.list();
			if(list != null && list.size()>0){
				money = (Double)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return money;
	}

	/**
	 * 查询项目成本
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OperateCost queryCostByTime(String year, String month, String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("from OperateCost where year='");
		sql.append(year);
		sql.append("'");
		if(!"".equals(GlobalMethod.ObjToStr(month)) && !Contants.YS.equals(type)){
			sql.append(" and month='");
			sql.append(month);
			sql.append("'");
		}
		sql.append(" and moneyType='");
		sql.append(type);
		sql.append("'");
		
		OperateCost cost = new OperateCost();
		try{
			Query query = this.getSession().createQuery(sql.toString());
			List<OperateCost> list = query.list();
			if(list != null && list.size()>0){
				cost = (OperateCost)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cost;
	}

	/**
	 * 查询成本年累计
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public OperateCost queryCostLj(String year, String month,String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(cost.clean_fee) clean_fee,sum(cost.security_fee) security_fee,sum(cost.communication_fee) communication_fee,");
		sql.append("sum(cost.water_fee) water_fee,sum(cost.electricity_fee) electricity_fee,sum(cost.materials) materials,");
		//sql.append("sum(cost.eqm_check_fee) eqm_check_fee,sum(cost.other_fee) other_fee from operate_cost cost ");
		//13-06-07加入了燃气费
		sql.append("sum(cost.eqm_check_fee) eqm_check_fee,sum(cost.other_fee) other_fee,sum(gas_fee) gas_fee from operate_cost cost ");
		sql.append(" where cost.year = '");
		sql.append(year);
		sql.append("' and cost.month<='");
		sql.append(month);
		sql.append("'");
		sql.append(" and cost.money_type='");
		sql.append(type);
		sql.append("'");
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		List list = query.list();
		
		OperateCost cost = new OperateCost();
		if(list != null && list.size()>0){
			Object[] obj = (Object[])list.get(0);
			cost.setCleanFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[0],"0")));
			cost.setSecurityFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[1],"0")));
			cost.setCommunicationFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[2],"0")));
			cost.setWaterFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[3],"0")));
			cost.setElectricityFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[4],"0")));
			cost.setMaterials(Double.parseDouble(GlobalMethod.ObjToParam(obj[5],"0")));
			cost.setEqmCheckFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[6],"0")));
			cost.setOtherFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[7],"0")));
			cost.setGasFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[8],"0")));
		}
		return cost;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperateIncome queryIncomeByTime(String year, String month,String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("from OperateIncome where year='");
		sql.append(year);
		sql.append("'");
		if(!"".equals(GlobalMethod.ObjToStr(month)) && !Contants.YS.equals(type)){
			sql.append(" and month='");
			sql.append(month);
			sql.append("'");
		}
		sql.append(" and moneyType='");
		sql.append(type);
		sql.append("'");
		
		OperateIncome income = new OperateIncome();
		try{
			Query query = this.getSession().createQuery(sql.toString());
			List<OperateIncome> list = query.list();
			if(list != null && list.size()>0){
				income = (OperateIncome)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return income;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public OperateIncome queryIncomeLj(String year, String month,String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(income.rent_fee) rent_fee,sum(income.decoration_fee) decoration_fee,sum(income.communication_fee) communication_fee,SUM(income.electricity_fee) electricity_fee,");
		sql.append("sum(income.stop_car_fee) stop_car_fee,SUM(income.other_fee) other_fee,sum(income.heat_fee) heat_fee,sum(income.property_fee) property_fee ");
		sql.append("from operate_income income ");
		sql.append(" where income.year = '");
		sql.append(year);
		sql.append("' and income.month<='");
		sql.append(month);
		sql.append("'");
		sql.append(" and money_type='");
		sql.append(type);
		sql.append("'");
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		List list = query.list();
		OperateIncome income = new OperateIncome();
		if(list != null && list.size()>0){
			Object[] obj = (Object[])list.get(0);
			income.setRentFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[0],"0")));
			income.setDecorationFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[1],"0")));
			income.setCommunicationFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[2],"0")));
			income.setElectricityFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[3],"0")));
			income.setStopCarFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[4],"0")));
			income.setOtherFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[5],"0")));
			income.setHeatFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[6],"0")));
			income.setPropertyFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[7],"0")));
		}
		return income;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProjectCosts queryProjectByTime(String dept, String year,String month, String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("from ProjectCosts where department='");
		sql.append(dept);
		sql.append("' and year = '");
		sql.append(year);
		sql.append("'");
		//项目预算时不用输入年 13-06-17修改
		if(!"".equals(GlobalMethod.ObjToStr(month)) && !Contants.YS.equals(type)){
			sql.append(" and month='");
			sql.append(month);
			sql.append("'");
		}
		sql.append(" and moneyType='");
		sql.append(type);
		sql.append("'");
		
		ProjectCosts project = new ProjectCosts();
		try{
			Query query = this.getSession().createQuery(sql.toString());
			List<ProjectCosts> list = query.list();
			if(list != null && list.size()>0){
				project = (ProjectCosts)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}

	/**
	 * 查询年累计
	 */
	@Override
	public ProjectCosts queryProjectLj(String year, String month,String type) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(pc.cope_wage) cope_wage,sum(pc.cope_welfare) cope_welfare,sum(pc.union_funds) union_funds,sum(pc.education_funds) education_funds,");
		sql.append("sum(pc.supplementary_medical) supplementary_medical,sum(pc.enterprise_annuity) enterprise_annuity,");
		sql.append("sum(pc.pension_insurance) pension_insurance,sum(pc.health_insurance) health_insurance,");
		sql.append("sum(pc.housing_fund) housing_fund,sum(pc.unemploy_insurance) unemploy_insurance,sum(pc.injury_insurance) injury_insurance,");
		sql.append("sum(pc.maternity_insurance) maternity_insurance,sum(pc.traffic_fee) traffic_fee,sum(pc.phone_fee) phone_fee,sum(pc.depreciation_fee) depreciation_fee,");
		sql.append("sum(pc.hospitality_business) hospitality_business,sum(pc.travel_fee) travel_fee,sum(pc.office_fee) office_fee,");
		sql.append("sum(pc.audit_fee) audit_fee,sum(pc.board_fee) board_fee,sum(pc.stamp_Duty) stamp_Duty,sum(pc.conference_fee) conference_fee,sum(pc.other_fee) other_fee,");
		sql.append("sum(pc.glf_hj) glf_hj,sum(pc.estate_fee) estate_fee,sum(pc.land_tax) land_tax,sum(pc.property_tax) property_tax ");
		sql.append("from project_costs pc where pc.year = '");
		sql.append(year);
		sql.append("'");
		//项目预算时不用输入年 13-06-17修改
		if(!"".equals(GlobalMethod.ObjToStr(month)) && !Contants.YS.equals(type)){
			sql.append(" and pc.month<='");
			sql.append(month);
			sql.append("'");
		}
		sql.append(" and pc.money_type='");
		sql.append(type);
		sql.append("'");
		
		ProjectCosts project = new ProjectCosts();
		try{
			Query query = this.getSession().createSQLQuery(sql.toString());
			List list = query.list();
			if(list != null && list.size()>0){
				Object[] obj = (Object[])list.get(0);
				project.setCopeWage(Double.parseDouble(GlobalMethod.ObjToParam(obj[0], "0")));
				project.setCopeWelfare(Double.parseDouble(GlobalMethod.ObjToParam(obj[1], "0")));
				project.setUnionFunds(Double.parseDouble(GlobalMethod.ObjToParam(obj[2], "0")));
				project.setEducationFunds(Double.parseDouble(GlobalMethod.ObjToParam(obj[3], "0")));
				project.setSupplementaryMedical(Double.parseDouble(GlobalMethod.ObjToParam(obj[4], "0")));
				project.setEnterpriseAnnuity(Double.parseDouble(GlobalMethod.ObjToParam(obj[5], "0")));
				project.setPensionInsurance(Double.parseDouble(GlobalMethod.ObjToParam(obj[6], "0")));
				project.setHealthInsurance(Double.parseDouble(GlobalMethod.ObjToParam(obj[7], "0")));
				project.setHousingFund(Double.parseDouble(GlobalMethod.ObjToParam(obj[8], "0")));
				project.setUnemployInsurance(Double.parseDouble(GlobalMethod.ObjToParam(obj[9], "0")));
				project.setInjuryInsurance(Double.parseDouble(GlobalMethod.ObjToParam(obj[10], "0")));
				project.setMaternityInsurance(Double.parseDouble(GlobalMethod.ObjToParam(obj[11], "0")));
				project.setTrafficFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[12], "0")));
				project.setPhoneFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[13], "0")));
				project.setDepreciationFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[14], "0")));
				project.setHospitalityFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[15], "0")));
				project.setTravelFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[16], "0")));
				project.setOfficeFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[17], "0")));
				project.setAuditFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[18], "0")));
				project.setBoardFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[19], "0")));
				project.setStampDuty(Double.parseDouble(GlobalMethod.ObjToParam(obj[20], "0")));
				project.setConferenceFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[21], "0")));
				project.setOtherFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[22], "0")));
				project.setGlfHj(Double.parseDouble(GlobalMethod.ObjToParam(obj[23], "0")));
				project.setEstateFee(Double.parseDouble(GlobalMethod.ObjToParam(obj[24], "0")));
				project.setLandTax(Double.parseDouble(GlobalMethod.ObjToParam(obj[25], "0")));
				project.setPropertyTax(Double.parseDouble(GlobalMethod.ObjToParam(obj[26], "0")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}

	
	
}
