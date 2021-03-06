package com.zc13.msmis.mapping;

/**
 * SysCodeType generated by MyEclipse Persistence Tools
 */

public class SysCodeType implements java.io.Serializable {

	// Fields

	private Integer codeTypeId;
	private String codeTypeName;
	private String codeTypeValue;
	private String description;
	private String updateTime;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public SysCodeType() {
	}

	// Property accessors

	public Integer getCodeTypeId() {
		return this.codeTypeId;
	}

	public void setCodeTypeId(Integer codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	public String getCodeTypeName() {
		return this.codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	public String getCodeTypeValue() {
		return this.codeTypeValue;
	}

	public void setCodeTypeValue(String codeTypeValue) {
		this.codeTypeValue = codeTypeValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public SysCodeType(Integer codeTypeId, String codeTypeName, String codeTypeValue, String description, String updateTime, Integer lpId, Integer rootUser) {
		super();
		this.codeTypeId = codeTypeId;
		this.codeTypeName = codeTypeName;
		this.codeTypeValue = codeTypeValue;
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