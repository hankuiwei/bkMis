package com.zc13.msmis.mapping;

import java.util.Date;

/**
 * SysCode generated by MyEclipse Persistence Tools
 */

public class SysCode implements java.io.Serializable {

	// Fields

	private Integer codeId;
	private String codeType;
	private String codeName;
	private String codeValue;
	private String description;
	private Date updateTime;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public SysCode() {
	}

	// Property accessors

	public Integer getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public SysCode(Integer codeId, String codeType, String codeName, String codeValue, String description, Date updateTime, Integer lpId, Integer rootUser) {
		super();
		this.codeId = codeId;
		this.codeType = codeType;
		this.codeName = codeName;
		this.codeValue = codeValue;
		this.description = description;
		this.updateTime = updateTime;
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

}