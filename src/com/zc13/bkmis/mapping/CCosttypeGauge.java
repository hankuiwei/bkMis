package com.zc13.bkmis.mapping;

import com.zc13.msmis.mapping.SysCode;

/**
 * CCosttypeGauge entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CCosttypeGauge implements java.io.Serializable {

	// Fields

	private Integer id;
	private CCosttype CCosttype;
	private SysCode sysCode;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	public CCosttypeGauge(Integer id, CCosttype costtype, SysCode sysCode, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		CCosttype = costtype;
		this.sysCode = sysCode;
		this.lpId = lpId;
		this.rootUser = rootUser;
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

	/** default constructor */
	public CCosttypeGauge() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CCosttype getCCosttype() {
		return this.CCosttype;
	}

	public void setCCosttype(CCosttype CCosttype) {
		this.CCosttype = CCosttype;
	}

	public SysCode getSysCode() {
		return this.sysCode;
	}

	public void setSysCode(SysCode sysCode) {
		this.sysCode = sysCode;
	}

}