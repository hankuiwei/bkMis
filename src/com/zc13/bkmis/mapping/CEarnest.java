package com.zc13.bkmis.mapping;

/**
 * CEarnest generated by MyEclipse Persistence Tools
 */

public class CEarnest implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private double amountReturn;
	private String userName;
	private String date;
	private String returnDate;
	private String billNum;
	private String billType;
	private String bz;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CEarnest() {
	}

	// Property accessors

	public CEarnest(Integer id, Integer clientId, double amount, double amountReturn, String userName, String date, String returnDate, String billNum, String billType, String bz, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.amount = amount;
		this.amountReturn = amountReturn;
		this.userName = userName;
		this.date = date;
		this.returnDate = returnDate;
		this.billNum = billNum;
		this.billType = billType;
		this.bz = bz;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountReturn() {
		return this.amountReturn;
	}

	public void setAmountReturn(double amountReturn) {
		this.amountReturn = amountReturn;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}