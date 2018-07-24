package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * CCosttype generated by MyEclipse Persistence Tools
 */

public class CCosttype implements java.io.Serializable {

	// Fields

	private Integer id;
	private String costTypeCode;
	private String costTypeName;
	private String explanation;
	private Set compactRoomCoststandards = new HashSet(0);
	private Set<CCostparatype> costparaTypes = new HashSet<CCostparatype>();
	private int totalcount;
	private Integer lpId;
	private Integer rootUser;
	// Constructors

	/** default constructor */
	public CCosttype() {
	}

	// Property accessors

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

	public CCosttype(Integer id, String costTypeCode, String costTypeName, String explanation, Set compactRoomCoststandards, Set<CCostparatype> costparaTypes, int totalcount, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.costTypeCode = costTypeCode;
		this.costTypeName = costTypeName;
		this.explanation = explanation;
		this.compactRoomCoststandards = compactRoomCoststandards;
		this.costparaTypes = costparaTypes;
		this.totalcount = totalcount;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCostTypeCode() {
		return this.costTypeCode;
	}

	public void setCostTypeCode(String costTypeCode) {
		this.costTypeCode = costTypeCode;
	}

	public String getCostTypeName() {
		return this.costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public Set getCompactRoomCoststandards() {
		return this.compactRoomCoststandards;
	}

	public void setCompactRoomCoststandards(Set compactRoomCoststandards) {
		this.compactRoomCoststandards = compactRoomCoststandards;
	}

	public Set<CCostparatype> getCostparaTypes() {
		return costparaTypes;
	}

	public void setCostparaTypes(Set<CCostparatype> costparaTypes) {
		this.costparaTypes = costparaTypes;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}


}