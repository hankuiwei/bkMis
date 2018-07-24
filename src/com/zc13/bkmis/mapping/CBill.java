package com.zc13.bkmis.mapping;

/**
 * CBill entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CBill implements java.io.Serializable {

	// Fields

	private Integer id;
	private CCoststandard CCoststandard;
	private CompactClient compactClient;
	private ERooms ERooms;
	private Compact compact;
	private String standardName;
	private String startDate;
	private String endDate;
	private String billDate;
	private String createDate;
	private String closeDate;
	private double billsExpenses;
	private double delayingExpenses;
	private String paymentWay;
	private String billType;
	private String billNum;
	private String bankNum;
	private String chequeNo;//支票号
	private String collectionDate;
	private double actuallyPaid;
	private String status;
	private String expendsId;
	private String userId;
	private String notes;
	private Integer itemId;
	private String billCode;
	private Integer entryUserId;
	private Integer rootUser;
	private Integer lpId;

	// Constructors

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public Integer getEntryUserId() {
		return entryUserId;
	}

	public void setEntryUserId(Integer entryUserId) {
		this.entryUserId = entryUserId;
	}

	/** default constructor */
	public CBill() {
	}

	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
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

	public ERooms getERooms() {
		return this.ERooms;
	}

	public void setERooms(ERooms ERooms) {
		this.ERooms = ERooms;
	}

	public Compact getCompact() {
		return this.compact;
	}

	public void setCompact(Compact compact) {
		this.compact = compact;
	}

	public String getStandardName() {
		return this.standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBillDate() {
		return this.billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public double getBillsExpenses() {
		return this.billsExpenses;
	}

	public void setBillsExpenses(double billsExpenses) {
		this.billsExpenses = billsExpenses;
	}

	public double getDelayingExpenses() {
		return this.delayingExpenses;
	}

	public void setDelayingExpenses(double delayingExpenses) {
		this.delayingExpenses = delayingExpenses;
	}

	public String getPaymentWay() {
		return this.paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getBankNum() {
		return this.bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getCollectionDate() {
		return this.collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public double getActuallyPaid() {
		return this.actuallyPaid;
	}

	public void setActuallyPaid(double actuallyPaid) {
		this.actuallyPaid = actuallyPaid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpendsId() {
		return this.expendsId;
	}

	public void setExpendsId(String expendsId) {
		this.expendsId = expendsId;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public CBill(Integer id, CCoststandard coststandard, CompactClient compactClient, ERooms rooms, Compact compact, String standardName, String startDate, String endDate, String billDate, String createDate, String closeDate, double billsExpenses, double delayingExpenses, String paymentWay, String billType, String billNum, String bankNum, String chequeNo, String collectionDate, double actuallyPaid, String status, String expendsId, String userId, String notes, Integer itemId, String billCode,
			Integer entryUserId, Integer rootUser, Integer lpId) {
		super();
		this.id = id;
		CCoststandard = coststandard;
		this.compactClient = compactClient;
		ERooms = rooms;
		this.compact = compact;
		this.standardName = standardName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.billDate = billDate;
		this.createDate = createDate;
		this.closeDate = closeDate;
		this.billsExpenses = billsExpenses;
		this.delayingExpenses = delayingExpenses;
		this.paymentWay = paymentWay;
		this.billType = billType;
		this.billNum = billNum;
		this.bankNum = bankNum;
		this.chequeNo = chequeNo;
		this.collectionDate = collectionDate;
		this.actuallyPaid = actuallyPaid;
		this.status = status;
		this.expendsId = expendsId;
		this.userId = userId;
		this.notes = notes;
		this.itemId = itemId;
		this.billCode = billCode;
		this.entryUserId = entryUserId;
		this.rootUser = rootUser;
		this.lpId = lpId;
	}

}