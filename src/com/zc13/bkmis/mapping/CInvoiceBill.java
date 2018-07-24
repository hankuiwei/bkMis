package com.zc13.bkmis.mapping;

/**
 * CInvoiceBill entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CInvoiceBill implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer billId;
	private Integer invoiceId;
	private double invoiceAmount;
	private String invoiceContent;

	// Constructors

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	/** default constructor */
	public CInvoiceBill() {
	}

	/** full constructor */
	public CInvoiceBill(Integer billId, Integer invoiceId, double invoiceAmount) {
		this.billId = billId;
		this.invoiceId = invoiceId;
		this.invoiceAmount = invoiceAmount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBillId() {
		return this.billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

}