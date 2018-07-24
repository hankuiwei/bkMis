package com.zc13.bkmis.mapping;

/**
 * CCharge entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CCharge implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String billType;
	private String billNum;
	private String chequeNo;//支票号
	private double billAmount;
	private double temporalAmount;
	private double advanceAmount;
	private double advanceWuYFAmount;/**gd 2018年3月3日15:51:21 添加属性 预收物业费 金额 */
	private double depositAmount;
	private double earnestAmount;//定金
	private String date;
	private String billId;
	private String userId;
	private String payType;
	private String realName;
	private Integer rootUser;
	private Integer lpId;


	public CCharge(Integer id, Integer clientId, double amount, String billType, String billNum, String chequeNo, double billAmount, double temporalAmount, double advanceAmount, double depositAmount, double earnestAmount, String date, String billId, String userId, String payType, String realName, Integer rootUser, Integer lpId,double advanceWuYFAmount) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.amount = amount;
		this.billType = billType;
		this.billNum = billNum;
		this.chequeNo = chequeNo;
		this.billAmount = billAmount;
		this.temporalAmount = temporalAmount;
		this.advanceAmount = advanceAmount;
		this.depositAmount = depositAmount;
		this.earnestAmount = earnestAmount;
		this.date = date;
		this.billId = billId;
		this.userId = userId;
		this.payType = payType;
		this.realName = realName;
		this.rootUser = rootUser;
		this.lpId = lpId;
		this.advanceWuYFAmount = advanceWuYFAmount;
	}

	
	public double getAdvanceWuYFAmount() {
		return advanceWuYFAmount;
	}


	public void setAdvanceWuYFAmount(double advanceWuYFAmount) {
		this.advanceWuYFAmount = advanceWuYFAmount;
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
	
	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public double getEarnestAmount() {
		return earnestAmount;
	}

	public void setEarnestAmount(double earnestAmount) {
		this.earnestAmount = earnestAmount;
	}

	/** default constructor */
	public CCharge() {
	}

	/** full constructor */

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

	public double getBillAmount() {
		return this.billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getTemporalAmount() {
		return this.temporalAmount;
	}

	public void setTemporalAmount(double temporalAmount) {
		this.temporalAmount = temporalAmount;
	}

	public double getAdvanceAmount() {
		return this.advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public double getDepositAmount() {
		return this.depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}