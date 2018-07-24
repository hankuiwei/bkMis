package com.zc13.bkmis.mapping;

/**
 * CInvoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CInvoice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String invoiceNum;
	private String date;
	private Integer operatorId;
	private String payType;
	private String chequeNo;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CInvoice() {
	}

	/** full constructor */
	public CInvoice(Integer clientId, double amount, String invoiceNum,
			String date, Integer operatorId, String payType, String chequeNo,
			Integer lpId, Integer rootUser) {
		this.clientId = clientId;
		this.amount = amount;
		this.invoiceNum = invoiceNum;
		this.date = date;
		this.operatorId = operatorId;
		this.payType = payType;
		this.chequeNo = chequeNo;
		this.lpId = lpId;
		this.rootUser = rootUser;
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

	public String getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getChequeNo() {
		return this.chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}