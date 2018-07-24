package com.zc13.bkmis.form;

import java.util.List;

public class OutputDepotManageForm extends BasicForm {
	
	//获取添加的页面元素添加的详细出入库表
	private String[] inOutputCode;
	private double[] unitPrice;
	private double[] amount;
	private double[] amountMoney;
	private String[] materialCode;
	private String[] oldAmount;
	private String oldMoney;
	private int[] ids;
	private String dmtId;
	//添加到出库管理表
	private int id;
	private String code;
	private String date;
	private String man;
	private String department;
	private String money;
	private String status;
	private String type2;
	private String updateDate;
	
	//出库明细表的查询条件
	private String inoutCode;
	private String startDate;
	private String endDate;
	private String materName;
	private String materCode;
	private List outputDatilList;
	private Integer materialId;

	//登录用户名
	private String userName;

	//存储出入库单id的字符串
	private String inOutputIds;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List getOutputDatilList() {
		return outputDatilList;
	}
	public void setOutputDatilList(List outputDatilList) {
		this.outputDatilList = outputDatilList;
	}
	public String getInoutCode() {
		return inoutCode;
	}
	public void setInoutCode(String inoutCode) {
		this.inoutCode = inoutCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMaterName() {
		return materName;
	}
	public void setMaterName(String materName) {
		this.materName = materName;
	}
	public String getMaterCode() {
		return materCode;
	}
	public void setMaterCode(String materCode) {
		this.materCode = materCode;
	}
	
	public double[] getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double[] unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double[] getAmount() {
		return amount;
	}
	public void setAmount(double[] amount) {
		this.amount = amount;
	}
	public double[] getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(double[] amountMoney) {
		this.amountMoney = amountMoney;
	}
	public String[] getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String[] materialCode) {
		this.materialCode = materialCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String[] getInOutputCode() {
		return inOutputCode;
	}
	public void setInOutputCode(String[] inOutputCode) {
		this.inOutputCode = inOutputCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[] getOldAmount() {
		return oldAmount;
	}
	public void setOldAmount(String[] oldAmount) {
		this.oldAmount = oldAmount;
	}
	public String getOldMoney() {
		return oldMoney;
	}
	public void setOldMoney(String oldMoney) {
		this.oldMoney = oldMoney;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getInOutputIds() {
		return inOutputIds;
	}
	public void setInOutputIds(String inOutputIds) {
		this.inOutputIds = inOutputIds;
	}
	public String getDmtId() {
		return dmtId;
	}
	public void setDmtId(String dmtId) {
		this.dmtId = dmtId;
	}

}
