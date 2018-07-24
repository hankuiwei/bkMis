package com.zc13.bkmis.form;

/**
 * 客户报修项目Form 
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：3:18:29 PM
 */
public class FinancialActualForm extends BasicForm{
	
	private Integer id;
	private String year;
	private String month;
	private double water;
	private double electricity;
	private double gas;
	private String createDate;
	private Integer createUser;
	
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
	
}
