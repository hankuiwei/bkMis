package com.zc13.bkmis.mapping;

/**
 * CBuildBillsLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CBuildBillsLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private String buildDate;
	private String buildFlag;
	private Integer rootUser;
	private Integer lpId;

	// Constructors

	/** default constructor */
	public CBuildBillsLog() {
	}

	// Property accessors

	public CBuildBillsLog(Integer id, String buildDate, String buildFlag, Integer rootUser, Integer lpId) {
		super();
		this.id = id;
		this.buildDate = buildDate;
		this.buildFlag = buildFlag;
		this.rootUser = rootUser;
		this.lpId = lpId;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getBuildFlag() {
		return this.buildFlag;
	}

	public void setBuildFlag(String buildFlag) {
		this.buildFlag = buildFlag;
	}

	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

}