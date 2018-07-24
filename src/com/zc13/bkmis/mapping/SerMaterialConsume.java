package com.zc13.bkmis.mapping;

/**
 * SerMaterialConsume generated by MyEclipse Persistence Tools
 */

public class SerMaterialConsume implements java.io.Serializable {

	// Fields

	private Integer id;
	private SerClientMaintain serClientMaintain;
	private String outDate;
	private String materialCode;
	private String materialName;
	private String spec;
	private double amount;
	private float unitPrice;
	private float amountMoney;
	private String department;
	private String roomName;
	private String areaName;
	private String type;
	private String surchargeWay;
	private double surchargeAmount;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public SerMaterialConsume() {
	}

	// Property accessors

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public SerMaterialConsume(Integer id, SerClientMaintain serClientMaintain, String outDate, String materialCode, String materialName, String spec, double amount, float unitPrice, float amountMoney, String department, String roomName, String areaName, String type, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.serClientMaintain = serClientMaintain;
		this.outDate = outDate;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.spec = spec;
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.amountMoney = amountMoney;
		this.department = department;
		this.roomName = roomName;
		this.areaName = areaName;
		this.type = type;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SerClientMaintain getSerClientMaintain() {
		return this.serClientMaintain;
	}

	public void setSerClientMaintain(SerClientMaintain serClientMaintain) {
		this.serClientMaintain = serClientMaintain;
	}

	public String getOutDate() {
		return this.outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getAmountMoney() {
		return this.amountMoney;
	}

	public void setAmountMoney(float amountMoney) {
		this.amountMoney = amountMoney;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public String getSurchargeWay() {
		return surchargeWay;
	}

	public void setSurchargeWay(String surchargeWay) {
		this.surchargeWay = surchargeWay;
	}

	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

}