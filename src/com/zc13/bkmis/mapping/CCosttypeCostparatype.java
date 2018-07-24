package com.zc13.bkmis.mapping;

/**
 * CCosttypeCostparatype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CCosttypeCostparatype implements java.io.Serializable {

	// Fields

	private Integer id;
	private CCostparatype CCostparatype;
	private CCosttype CCosttype;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	public CCosttypeCostparatype(Integer id, CCostparatype costparatype, CCosttype costtype, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		CCostparatype = costparatype;
		CCosttype = costtype;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	/** default constructor */
	public CCosttypeCostparatype() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CCostparatype getCCostparatype() {
		return this.CCostparatype;
	}

	public void setCCostparatype(CCostparatype CCostparatype) {
		this.CCostparatype = CCostparatype;
	}

	public CCosttype getCCosttype() {
		return this.CCosttype;
	}

	public void setCCosttype(CCosttype CCosttype) {
		this.CCosttype = CCosttype;
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

}