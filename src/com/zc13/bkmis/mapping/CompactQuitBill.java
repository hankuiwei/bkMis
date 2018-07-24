package com.zc13.bkmis.mapping;

/**
 * CompactQuitBill entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CompactQuitBill implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer pactId;
	private Integer billId;
	private Integer standardId;
	private String lastBuildDate;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CompactQuitBill() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

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

	public CompactQuitBill(Integer id, Integer pactId, Integer billId, Integer standardId, String lastBuildDate, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.pactId = pactId;
		this.billId = billId;
		this.standardId = standardId;
		this.lastBuildDate = lastBuildDate;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPactId() {
		return this.pactId;
	}

	public void setPactId(Integer pactId) {
		this.pactId = pactId;
	}

	public Integer getBillId() {
		return this.billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getStandardId() {
		return this.standardId;
	}

	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	public String getLastBuildDate() {
		return this.lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

}