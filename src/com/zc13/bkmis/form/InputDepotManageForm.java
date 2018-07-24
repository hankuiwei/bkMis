package com.zc13.bkmis.form;

import java.util.List;

public class InputDepotManageForm extends BasicForm {
	
	//出库单信息
	private Integer id;
	private String code;
	private String date;
	private String type2;
	private String man;
	private double money;
	private String invoiceCode;
	private String supplierId;
	private String updateDate;
	private String status;
	private String dmtId;
	//显示入库单的信息查询条件
	private String inputCode;
	private String startDate;
	private String endDate;
	private String cxInvoiceCode;
	private String cxStatus;
	
	//入库单信息
	private List inputList;
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	
	//获取添加的页面元素添加的详细出入库表
	private String[] inOutputCode;
	private double[] unitPrice;
	private double[] amount;
	private double[] amountMoney;
	private String[] materialCode;
	private String[] oldAmount;
	private String oldMoney;
	private int[] ids;
	//入库明细表的查询条件
	private String inoutCode;
	private String materName;
	private String materCode;
	private List inputDatilList;
	private int materialId;

	//存储出入库单id的字符串
	private String inOutputIds;
	
	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	public String[] getInOutputCode() {
		return inOutputCode;
	}
	public void setInOutputCode(String[] inOutputCode) {
		this.inOutputCode = inOutputCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDmtId() {
		return dmtId;
	}
	public void setDmtId(String dmtId) {
		this.dmtId = dmtId;
	}
	public String getInputCode() {
		return inputCode;
	}
	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
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
	public String getCxInvoiceCode() {
		return cxInvoiceCode;
	}
	public void setCxInvoiceCode(String cxInvoiceCode) {
		this.cxInvoiceCode = cxInvoiceCode;
	}
	public String getCxStatus() {
		return cxStatus;
	}
	public void setCxStatus(String cxStatus) {
		this.cxStatus = cxStatus;
	}
	public List getInputList() {
		return inputList;
	}
	public void setInputList(List inputList) {
		this.inputList = inputList;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
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
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public String getInoutCode() {
		return inoutCode;
	}
	public void setInoutCode(String inoutCode) {
		this.inoutCode = inoutCode;
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
	public List getInputDatilList() {
		return inputDatilList;
	}
	public void setInputDatilList(List inputDatilList) {
		this.inputDatilList = inputDatilList;
	}
	public String getInOutputIds() {
		return inOutputIds;
	}
	public void setInOutputIds(String inOutputIds) {
		this.inOutputIds = inOutputIds;
	}
	
}
