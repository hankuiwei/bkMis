package com.zc13.bkmis.mapping;

/**
 * AnalysisClientComeGo generated by MyEclipse Persistence Tools
 */

public class FinancialBudget implements java.io.Serializable {

	// Fields

	private Integer id;
	private String year;
	private double water;
	private double electricity;
	private double gas;
	private String createTime;
    private Integer createUser;	

	// Constructors

	/** default constructor */
	public FinancialBudget() {
	}

	/** full constructor */
	public FinancialBudget(String year,double water,double electricity,double gas,String createTime,Integer createUser) {
		this.year=year;
		this.water=water;
		this.electricity=electricity;
		this.gas=gas;
		this.createTime=createTime;
		this.createUser=createUser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getElectricity() {
		return electricity;
	}

	public void setElectricity(double electricity) {
		this.electricity = electricity;
	}

	public double getGas() {
		return gas;
	}

	public void setGas(double gas) {
		this.gas = gas;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	

	

}