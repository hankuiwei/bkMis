package com.zc13.bkmis.mapping;

/**
 * EqType entity. @author MyEclipse Persistence Tools
 */

public class EqType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String name;
	private String description;
	private Integer parentid;
	private Integer rootUser;
	private Integer lpId;

	// Constructors

	/** default constructor */
	public EqType() {
	}

	/** full constructor */
	public EqType(String code, String name, String description,
			Integer parentid, Integer rootUser, Integer lpId) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.parentid = parentid;
		this.rootUser = rootUser;
		this.lpId = lpId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

}