package com.zc13.bkmis.mapping;

/**
 * CInvoiceReceipt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CInvoiceReceipt implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer receiptId;
	private Integer invoiceId;
	private double amount;
	// Constructors

	/** default constructor */
	public CInvoiceReceipt() {
	}

	// Property accessors

	public CInvoiceReceipt(Integer id, Integer receiptId, Integer invoiceId,
			double amount) {
		super();
		this.id = id;
		this.receiptId = receiptId;
		this.invoiceId = invoiceId;
		this.amount = amount;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReceiptId() {
		return this.receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}

	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}