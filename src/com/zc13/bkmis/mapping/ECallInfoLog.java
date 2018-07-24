package com.zc13.bkmis.mapping;

/**
 * ECallInfoLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ECallInfoLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private String endDate;
	private Integer reachRow;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public ECallInfoLog() {
	}

	/** full constructor */
	public ECallInfoLog(String endDate, Integer reachRow, Integer lpId,
			Integer rootUser) {
		this.endDate = endDate;
		this.reachRow = reachRow;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getReachRow() {
		return this.reachRow;
	}

	public void setReachRow(Integer reachRow) {
		this.reachRow = reachRow;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}