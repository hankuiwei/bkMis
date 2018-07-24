package com.zc13.bkmis.mapping;

/**
 * EMeterExcerption entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EMeterExcerption implements java.io.Serializable {

	// Fields

	private Integer id;
	private EMeter EMeter;
	private String code;
	private String type;
	private String beginDate;
	private String endDate;
	private double lastRecord;
	private double thisRecord;
	private String yearMonth;
	private String lookUpMan;
	private String lookUpTime;
	private String enterMan;
	private String enterTime;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public EMeterExcerption() {
	}

	// Property accessors

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public EMeterExcerption(Integer id, EMeter meter, String code, String type, String beginDate, String endDate, double lastRecord, double thisRecord, String yearMonth, String lookUpMan, String lookUpTime, String enterMan, String enterTime, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		EMeter = meter;
		this.code = code;
		this.type = type;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.lastRecord = lastRecord;
		this.thisRecord = thisRecord;
		this.yearMonth = yearMonth;
		this.lookUpMan = lookUpMan;
		this.lookUpTime = lookUpTime;
		this.enterMan = enterMan;
		this.enterTime = enterTime;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EMeter getEMeter() {
		return this.EMeter;
	}

	public void setEMeter(EMeter EMeter) {
		this.EMeter = EMeter;
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

	public double getLastRecord() {
		return this.lastRecord;
	}

	public void setLastRecord(double lastRecord) {
		this.lastRecord = lastRecord;
	}

	public double getThisRecord() {
		return this.thisRecord;
	}

	public void setThisRecord(double thisRecord) {
		this.thisRecord = thisRecord;
	}

	public String getYearMonth() {
		return this.yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getLookUpMan() {
		return this.lookUpMan;
	}

	public void setLookUpMan(String lookUpMan) {
		this.lookUpMan = lookUpMan;
	}

	public String getLookUpTime() {
		return this.lookUpTime;
	}

	public void setLookUpTime(String lookUpTime) {
		this.lookUpTime = lookUpTime;
	}

	public String getEnterMan() {
		return this.enterMan;
	}

	public void setEnterMan(String enterMan) {
		this.enterMan = enterMan;
	}

	public String getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}
	
	public String getUseRecord2(){
		return "";
	}
	
	public String getPublicRecord2(){
		return "";
	}
}