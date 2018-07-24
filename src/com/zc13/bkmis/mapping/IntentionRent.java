package com.zc13.bkmis.mapping;

/**
 * IntentionRent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IntentionRent implements java.io.Serializable {

	// Fields

	private Integer id;
	private CompactIntention compactIntention;
	private double rent;
	private String beginDate;
	private String endDate;
	private String status;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public IntentionRent() {
	}

	// Property accessors

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public IntentionRent(Integer id, CompactIntention compactIntention, double rent, String beginDate, String endDate, String status, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.compactIntention = compactIntention;
		this.rent = rent;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.status = status;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CompactIntention getCompactIntention() {
		return this.compactIntention;
	}

	public void setCompactIntention(CompactIntention compactIntention) {
		this.compactIntention = compactIntention;
	}

	public double getRent() {
		return this.rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}