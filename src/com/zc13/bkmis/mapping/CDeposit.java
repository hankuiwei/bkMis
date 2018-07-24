package com.zc13.bkmis.mapping;

/**
 * CDeposit entity.押金表
 * 
 * @author MyEclipse Persistence Tools
 */

public class CDeposit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private String depositType;
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

	public CDeposit(Integer id, Integer clientId, String depositType, double amount, double amountReturn, String userName, String date, String returnDate, String billNum, String billType, String bz, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.depositType = depositType;
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

	/** default constructor */
	public CDeposit() {
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

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

}