package com.zc13.bkmis.mapping;

/**
 * AnalysisClientComeGo generated by MyEclipse Persistence Tools
 */

public class FinancialActual implements java.io.Serializable {

	// Fields

	private Integer id;
	private String year;
	private String month;
	private double water;
	private double electricity;
	private double gas;
	private String createDate;
	private Integer createUser;
	

	// Constructors

	/** default constructor */
	public FinancialActual() {
	}

	/** full constructor */
	public FinancialActual(String year,String month,double water,double electricity,double gas,String createDate,Integer createUser) {
		this.year=year;
		this.month=month;
		this.water=water;
		this.electricity=electricity;
		this.gas=gas;
		this.createDate=createDate;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}



	

	

}