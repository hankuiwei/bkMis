package com.zc13.bkmis.form;

import java.util.List;

public class AnalysisChargesForm extends BasicForm {

	//统计信息
	private Integer id;
	private Integer lpId;
	private String createDate;
	private String beginDate;
	private String endDate;
	private float amountMoney;
	private Integer rootUser;
	//存储统计信息记录
	private List chaAnalysisList;

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public float getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(float amountMoney) {
		this.amountMoney = amountMoney;
	}
	public List getChaAnalysisList() {
		return chaAnalysisList;
	}
	public void setChaAnalysisList(List chaAnalysisList) {
		this.chaAnalysisList = chaAnalysisList;
	}
}
