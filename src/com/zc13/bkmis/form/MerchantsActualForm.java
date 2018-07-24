package com.zc13.bkmis.form;

/**
 * 招商实际项目Form 
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：3:18:29 PM
 */
public class MerchantsActualForm extends BasicForm{
	
	private Integer id;
	private String year;
	private String month;
	private double actualArea;
	private String quarter;
	private Integer createUser;
	private String responsePerson;
	private String personType;
	
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数


	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
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
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
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
	
}
