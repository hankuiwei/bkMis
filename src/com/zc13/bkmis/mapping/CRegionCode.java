package com.zc13.bkmis.mapping;

/**
 * CRegionCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CRegionCode implements java.io.Serializable {

	// Fields

	private Integer id;
	private String areaCode;
	private String areaName;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CRegionCode() {
	}

	/** full constructor */
	public CRegionCode(String areaCode, String areaName, Integer lpId,
			Integer rootUser) {
		this.areaCode = areaCode;
		this.areaName = areaName;
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

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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