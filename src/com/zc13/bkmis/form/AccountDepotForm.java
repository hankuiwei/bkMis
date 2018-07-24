package com.zc13.bkmis.form;

import java.util.List;

public class AccountDepotForm extends BasicForm {
	
	//库存核算信息
	private Integer id;
	private String makeDate;
	private String year;
	private String month;
	private String beginDate;
	private String endDate;
	private double beginAmount;
	private double beginMoney;
	private double inputAccount;
	private double inputMomey;
	private double outputAccount;
	private double outputMoney;
	private double residue;
	private double balance;
	
	//明细的查询条件
	private String cx_name;//材料名称
	private String cx_code;//材料编号
	
	//存储核算信息
	private List accountList;
	//存储核算的详细信息
	private List detailAccountList;

	//库存核算的id字符串
	private String ids;
	
	//登录用户名
	private String userName;
	
	//楼盘ID
	private Integer lpId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
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

	public double getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(double beginAmount) {
		this.beginAmount = beginAmount;
	}

	public double getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(double beginMoney) {
		this.beginMoney = beginMoney;
	}

	public double getInputAccount() {
		return inputAccount;
	}

	public void setInputAccount(double inputAccount) {
		this.inputAccount = inputAccount;
	}

	public double getInputMomey() {
		return inputMomey;
	}

	public void setInputMomey(double inputMomey) {
		this.inputMomey = inputMomey;
	}

	public double getOutputAccount() {
		return outputAccount;
	}

	public void setOutputAccount(double outputAccount) {
		this.outputAccount = outputAccount;
	}

	public double getOutputMoney() {
		return outputMoney;
	}

	public void setOutputMoney(double outputMoney) {
		this.outputMoney = outputMoney;
	}

	public double getResidue() {
		return residue;
	}

	public void setResidue(double residue) {
		this.residue = residue;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCx_name() {
		return cx_name;
	}

	public void setCx_name(String cx_name) {
		this.cx_name = cx_name;
	}

	public String getCx_code() {
		return cx_code;
	}

	public void setCx_code(String cx_code) {
		this.cx_code = cx_code;
	}

	public List getAccountList() {
		return accountList;
	}

	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}

	public List getDetailAccountList() {
		return detailAccountList;
	}

	public void setDetailAccountList(List detailAccountList) {
		this.detailAccountList = detailAccountList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
}
