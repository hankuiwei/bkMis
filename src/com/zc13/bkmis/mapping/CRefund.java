package com.zc13.bkmis.mapping;

/**
 * CRefund entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CRefund implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private Integer billId;
	private double amount;
	private double amountCharged;
	private String userName;
	private String refundDate;
	private String billNum;
	private String billType;
	private String refundNum;
	private String bz;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CRefund() {
	}

	// Property accessors

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

	public CRefund(Integer id, Integer clientId, Integer billId, double amount, double amountCharged, String userName, String refundDate, String billNum, String billType, String refundNum, String bz, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.billId = billId;
		this.amount = amount;
		this.amountCharged = amountCharged;
		this.userName = userName;
		this.refundDate = refundDate;
		this.billNum = billNum;
		this.billType = billType;
		this.refundNum = refundNum;
		this.bz = bz;
		this.lpId = lpId;
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

	public Integer getBillId() {
		return this.billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountCharged() {
		return this.amountCharged;
	}

	public void setAmountCharged(double amountCharged) {
		this.amountCharged = amountCharged;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRefundDate() {
		return this.refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
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

	public String getRefundNum() {
		return this.refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}