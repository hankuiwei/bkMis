package com.zc13.bkmis.form;

import java.util.List;

public class AnalysisEnergyForm extends BasicForm {

	//能源统计信息
	private Integer id;
	private String createDate;
	private String beginDate;
	private String endDate;
	private float totalElectricity;
	private float totalWater;
	private float totalGas;
	private Integer rootUser;

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//存储查询出的统计信息
	private List energyList;
	
	public List getEnergyList() {
		return energyList;
	}
	public void setEnergyList(List energyList) {
		this.energyList = energyList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public float getTotalElectricity() {
		return totalElectricity;
	}
	public void setTotalElectricity(float totalElectricity) {
		this.totalElectricity = totalElectricity;
	}
	public float getTotalWater() {
		return totalWater;
	}
	public void setTotalWater(float totalWater) {
		this.totalWater = totalWater;
	}
	public float getTotalGas() {
		return totalGas;
	}
	public void setTotalGas(float totalGas) {
		this.totalGas = totalGas;
	}
	public Integer getRootUser() {
		return rootUser;
	}
	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}
	
}
