package com.zc13.bkmis.mapping;

/**
 * HrRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class HrRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String personnelNum;
	private String personnelName;
	private String workcode;
	private String status;
	private String cardSrc;
	private String type;
	private String time;
	private Integer creationId;
	private String creationDate;
	private Integer lasteditId;
	private String lasteditDate;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public HrRecord() {
	}

	/** full constructor */
	public HrRecord(String personnelNum, String personnelName, String workcode, String status, String cardSrc, String type, String time, Integer creationId, String creationDate, Integer lasteditId, String lasteditDate, Integer lpId, Integer rootUser) {
		this.personnelNum = personnelNum;
		this.personnelName = personnelName;
		this.workcode = workcode;
		this.status = status;
		this.cardSrc = cardSrc;
		this.type = type;
		this.time = time;
		this.creationId = creationId;
		this.creationDate = creationDate;
		this.lasteditId = lasteditId;
		this.lasteditDate = lasteditDate;
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

	public String getPersonnelNum() {
		return this.personnelNum;
	}

	public void setPersonnelNum(String personnelNum) {
		this.personnelNum = personnelNum;
	}

	public String getPersonnelName() {
		return this.personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getWorkcode() {
		return this.workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCardSrc() {
		return this.cardSrc;
	}

	public void setCardSrc(String cardSrc) {
		this.cardSrc = cardSrc;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getCreationId() {
		return this.creationId;
	}

	public void setCreationId(Integer creationId) {
		this.creationId = creationId;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getLasteditId() {
		return this.lasteditId;
	}

	public void setLasteditId(Integer lasteditId) {
		this.lasteditId = lasteditId;
	}

	public String getLasteditDate() {
		return this.lasteditDate;
	}

	public void setLasteditDate(String lasteditDate) {
		this.lasteditDate = lasteditDate;
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