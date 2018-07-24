package com.zc13.bkmis.mapping;

/**
 * ERoomIntention entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ERoomIntention implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer roomId;
	private Integer clientId;
	private Integer intentionId;
	private String beginDate;
	private String endDate;
	private String clientName;
	private String unitName;
	private String status;
	private Integer lpId;
	private Integer rootUser;
	// Constructors

	/** default constructor */
	public ERoomIntention() {
	}

	// Property accessors

	public ERoomIntention(Integer id, Integer roomId, Integer clientId, Integer intentionId, String beginDate, String endDate, String clientName, String unitName, String status, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.roomId = roomId;
		this.clientId = clientId;
		this.intentionId = intentionId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.clientName = clientName;
		this.unitName = unitName;
		this.status = status;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getIntentionId() {
		return this.intentionId;
	}

	public void setIntentionId(Integer intentionId) {
		this.intentionId = intentionId;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}