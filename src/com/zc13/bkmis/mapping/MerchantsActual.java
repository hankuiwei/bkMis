package com.zc13.bkmis.mapping;

/**
 * 招商實際
 * AnalysisClientComeGo generated by MyEclipse Persistence Tools
 */

public class MerchantsActual implements java.io.Serializable {

	// Fields

	private Integer id;
	private String year;
	private String month;
	private double actualArea;
	private String quarter;
	private Integer createUser;
	private String responsePerson;
	private String personType;

	// Constructors

	/** default constructor */
	public MerchantsActual() {
	}

	/** full constructor */
	public MerchantsActual(String year,String month,float actualArea,String quarter,Integer createUser,String responsePerson,String personType) {
		this.year=year;
		this.month=month;
		this.actualArea=actualArea;
		this.quarter=quarter;
		this.createUser=createUser;
		this.responsePerson=responsePerson;
		this.personType=personType;
	}



	public String getResponsePerson() {
		return responsePerson;
	}

	public void setResponsePerson(String responsePerson) {
		this.responsePerson = responsePerson;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public Integer getId() {
		return id;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	

	public double getActualArea() {
		return actualArea;
	}

	public void setActualArea(double actualArea) {
		this.actualArea = actualArea;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	

}