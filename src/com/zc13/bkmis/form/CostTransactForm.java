package com.zc13.bkmis.form;

public class CostTransactForm extends BasicForm{
	
	private static final long serialVersionUID = -8623943298441571931L;
	
	private Integer clientId;//客户id
	private String countDate;//计算日期
	private String depositType;//押金类型
	private String statyStatus;//入住状态
	
	public String getStatyStatus() {
		return statyStatus;
	}
	public void setStatyStatus(String statyStatus) {
		this.statyStatus = statyStatus;
	}
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getCountDate() {
		return countDate;
	}
	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}
	
}
