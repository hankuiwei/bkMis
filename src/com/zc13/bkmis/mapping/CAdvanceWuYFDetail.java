package com.zc13.bkmis.mapping;

/**
 * CAdvanceDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CAdvanceWuYFDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String payDate;
	private String billType;//单据类型
	private String billNum;//单据号
	private String bz;
	private Integer rootUser;
	private Integer lpId;
	// Constructors

	/** default constructor */
	public CAdvanceWuYFDetail() {
	}

	/** full constructor */
	public CAdvanceWuYFDetail(Integer clientId, double amount, String payDate,
			String billType, String billNum, String bz) {
		this.clientId = clientId;
		this.amount = amount;
		this.payDate = payDate;
		this.billType = billType;
		this.billNum = billNum;
		this.bz = bz;
	}

	// Property accessors

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

	public String getPayDate() {
		return this.payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public CAdvanceWuYFDetail(Integer id, Integer clientId, double amount, String payDate, String billType, String billNum, String bz, Integer rootUser, Integer lpId) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.amount = amount;
		this.payDate = payDate;
		this.billType = billType;
		this.billNum = billNum;
		this.bz = bz;
		this.rootUser = rootUser;
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

}