package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * EMeter entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EMeter implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String type;
	private Integer lpId;
	private Integer buildId;
	private Integer roomId;
	private Integer parentId;
	private Integer rootUser;
	private Set EMeterExcerptions = new HashSet(0);

	// Constructors

	/** default constructor */
	public EMeter() {
	}

	/** full constructor */
	public EMeter(String code, String type, Integer lpId, Integer buildId,
			Integer roomId, Integer parentId, Integer rootUser,
			Set EMeterExcerptions) {
		this.code = code;
		this.type = type;
		this.lpId = lpId;
		this.buildId = buildId;
		this.roomId = roomId;
		this.parentId = parentId;
		this.rootUser = rootUser;
		this.EMeterExcerptions = EMeterExcerptions;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getBuildId() {
		return this.buildId;
	}

	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Set getEMeterExcerptions() {
		return this.EMeterExcerptions;
	}

	public void setEMeterExcerptions(Set EMeterExcerptions) {
		this.EMeterExcerptions = EMeterExcerptions;
	}

}