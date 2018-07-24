package com.zc13.bkmis.mapping;

/**
 * IntentionRoomCoststandard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IntentionRoomCoststandard implements java.io.Serializable {

	// Fields

	private Integer id;
	private CCoststandard CCoststandard;
	private CompactClient compactClient;
	private CompactIntention compactIntention;
	private ERooms ERooms;
	private CCosttype CCosttype;
	private String beginDate;
	private String endDate;
	private String rebate;
	private Integer amount;
	private String display;
	private String status;
	private Integer lpId;
	private Integer rootUser;

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public IntentionRoomCoststandard(Integer id, CCoststandard coststandard, CompactClient compactClient, CompactIntention compactIntention, ERooms rooms, CCosttype costtype, String beginDate, String endDate, String rebate, Integer amount, String display, String status, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		CCoststandard = coststandard;
		this.compactClient = compactClient;
		this.compactIntention = compactIntention;
		ERooms = rooms;
		CCosttype = costtype;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.rebate = rebate;
		this.amount = amount;
		this.display = display;
		this.status = status;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	/** default constructor */
	public IntentionRoomCoststandard() {
	}

	// Property accessors

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CCoststandard getCCoststandard() {
		return this.CCoststandard;
	}

	public void setCCoststandard(CCoststandard CCoststandard) {
		this.CCoststandard = CCoststandard;
	}

	public CompactClient getCompactClient() {
		return this.compactClient;
	}

	public void setCompactClient(CompactClient compactClient) {
		this.compactClient = compactClient;
	}

	public CompactIntention getCompactIntention() {
		return this.compactIntention;
	}

	public void setCompactIntention(CompactIntention compactIntention) {
		this.compactIntention = compactIntention;
	}

	public ERooms getERooms() {
		return this.ERooms;
	}

	public void setERooms(ERooms ERooms) {
		this.ERooms = ERooms;
	}

	public CCosttype getCCosttype() {
		return this.CCosttype;
	}

	public void setCCosttype(CCosttype CCosttype) {
		this.CCosttype = CCosttype;
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

	public String getRebate() {
		return this.rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}