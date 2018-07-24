package com.zc13.bkmis.form;

import com.zc13.bkmis.mapping.OperateCost;
import com.zc13.bkmis.mapping.OperateIncome;
import com.zc13.bkmis.mapping.ProjectCosts;


public class FinancialReportForm extends BasicForm {

	//经营成本
	private OperateCost operateCost = new OperateCost();
	
	//经营收入
	private OperateIncome operateIncome = new OperateIncome();
	
	//项目
	private ProjectCosts projectCosts = new ProjectCosts();
	
	//年度
	private String year;
	
	//月度
	private String month;
	
	//删除的ids
	private String ids;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public ProjectCosts getProjectCosts() {
		return projectCosts;
	}

	public void setProjectCosts(ProjectCosts projectCosts) {
		this.projectCosts = projectCosts;
	}

	public OperateIncome getOperateIncome() {
		return operateIncome;
	}

	public void setOperateIncome(OperateIncome operateIncome) {
		this.operateIncome = operateIncome;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public OperateCost getOperateCost() {
		return operateCost;
	}

	public void setOperateCost(OperateCost operateCost) {
		this.operateCost = operateCost;
	}
}
