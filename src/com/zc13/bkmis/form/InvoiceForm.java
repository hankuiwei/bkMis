package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CInvoice;

/**
 * 发票管理Form
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:27:17 AM
 */
public class InvoiceForm extends BasicForm {
	//查询条件
	private String cxClientName;
	private String cxReceiptNo;
	private String cxInvoiceNo;
	private String cxBeginDate;
	private String cxEndDate;
	private Integer cxReciveUserId;
	private Integer cxInvoiceUserId;
	private String receiptIds;
	private String invoiceIds;
	private String cxStartAmount;
	private String cxEndAmount;
	private String cxInvoiceContent;
	private String cxInvoiceItem;
	private Double totalAmount;
	
	private List invoiceList;
	private List receiptList;
	private Integer totalcount;
	
	private CInvoice invoice = new CInvoice();
	private CCharge change = new CCharge();
	
	//保存发票信息
	private Integer[] clientId;
	private double[] amount;
	private String[] invoiceNum;
	
	//保存收据发票关系信息
	private Integer[] receiptId;
	private String[] invoice_num;
	private double[] invoice_amount;
	private String[] invoice_content;
	//保存发票账单关系
	private Integer[] billId;

	//用户信息
	private Integer userId;
	private String userRealName;
	private String userName;
	public Integer getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}

	public List getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List receiptList) {
		this.receiptList = receiptList;
	}

	public String getCxClientName() {
		return cxClientName;
	}

	public void setCxClientName(String cxClientName) {
		this.cxClientName = cxClientName;
	}

	public String getCxReceiptNo() {
		return cxReceiptNo;
	}

	public void setCxReceiptNo(String cxReceiptNo) {
		this.cxReceiptNo = cxReceiptNo;
	}

	public String getCxInvoiceNo() {
		return cxInvoiceNo;
	}

	public void setCxInvoiceNo(String cxInvoiceNo) {
		this.cxInvoiceNo = cxInvoiceNo;
	}

	public String getCxBeginDate() {
		return cxBeginDate;
	}

	public void setCxBeginDate(String cxBeginDate) {
		this.cxBeginDate = cxBeginDate;
	}

	public String getCxEndDate() {
		return cxEndDate;
	}

	public void setCxEndDate(String cxEndDate) {
		this.cxEndDate = cxEndDate;
	}

	public Integer getCxReciveUserId() {
		return cxReciveUserId;
	}

	public void setCxReciveUserId(Integer cxReciveUserId) {
		this.cxReciveUserId = cxReciveUserId;
	}

	public Integer getCxInvoiceUserId() {
		return cxInvoiceUserId;
	}

	public void setCxInvoiceUserId(Integer cxInvoiceUserId) {
		this.cxInvoiceUserId = cxInvoiceUserId;
	}

	public String getReceiptIds() {
		return receiptIds;
	}

	public void setReceiptIds(String receiptIds) {
		this.receiptIds = receiptIds;
	}

	public Integer[] getClientId() {
		return clientId;
	}

	public void setClientId(Integer[] clientId) {
		this.clientId = clientId;
	}

	public double[] getAmount() {
		return amount;
	}

	public void setAmount(double[] amount) {
		this.amount = amount;
	}

	public String[] getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String[] invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public Integer[] getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer[] receiptId) {
		this.receiptId = receiptId;
	}

	public String[] getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String[] invoice_num) {
		this.invoice_num = invoice_num;
	}

	public double[] getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(double[] invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCxStartAmount() {
		return cxStartAmount;
	}

	public void setCxStartAmount(String cxStartAmount) {
		this.cxStartAmount = cxStartAmount;
	}

	public String getCxEndAmount() {
		return cxEndAmount;
	}

	public void setCxEndAmount(String cxEndAmount) {
		this.cxEndAmount = cxEndAmount;
	}

	public CInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(CInvoice invoice) {
		this.invoice = invoice;
	}

	public CCharge getChange() {
		return change;
	}

	public void setChange(CCharge change) {
		this.change = change;
	}

	public String getInvoiceIds() {
		return invoiceIds;
	}

	public void setInvoiceIds(String invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

	public Integer[] getBillId() {
		return billId;
	}

	public void setBillId(Integer[] billId) {
		this.billId = billId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCxInvoiceContent() {
		return cxInvoiceContent;
	}

	public void setCxInvoiceContent(String cxInvoiceContent) {
		this.cxInvoiceContent = cxInvoiceContent;
	}

	public String[] getInvoice_content() {
		return invoice_content;
	}

	public void setInvoice_content(String[] invoice_content) {
		this.invoice_content = invoice_content;
	}

	public String getCxInvoiceItem() {
		return cxInvoiceItem;
	}

	public void setCxInvoiceItem(String cxInvoiceItem) {
		this.cxInvoiceItem = cxInvoiceItem;
	}
	
}
