package com.zc13.bkmis.mapping;

/**
 * 客户账单
 * @author 王正伟
 * Date：Jan 6, 2011
 * Time：11:19:08 AM
 */
public class ClientBill {
	private String clientId;//客户id
	private String closeDate;//关帐日期
	private String clientName;//客户名称
	private String billExpenses;//合同金额
	private String delayingExpenses;//滞纳金额
	private String payExpenses;//应付金额
	private String actuallyPaid;//实付金额
	private String requireExpenses;//需付金额
	private Integer lpId;
	private Integer rootUser;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBillExpenses() {
		return billExpenses;
	}
	public void setBillExpenses(String billExpenses) {
		this.billExpenses = billExpenses;
	}
	public String getDelayingExpenses() {
		return delayingExpenses;
	}
	public void setDelayingExpenses(String delayingExpenses) {
		this.delayingExpenses = delayingExpenses;
	}
	public String getPayExpenses() {
		return payExpenses;
	}
	public void setPayExpenses(String payExpenses) {
		this.payExpenses = payExpenses;
	}
	public String getActuallyPaid() {
		return actuallyPaid;
	}
	public void setActuallyPaid(String actuallyPaid) {
		this.actuallyPaid = actuallyPaid;
	}
	public String getRequireExpenses() {
		return requireExpenses;
	}
	public void setRequireExpenses(String requireExpenses) {
		this.requireExpenses = requireExpenses;
	}
	
}
