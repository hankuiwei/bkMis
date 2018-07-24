package com.zc13.bkmis.mapping;

/**
 * UserMeterReadingId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserMeterReadingId implements java.io.Serializable {

	// Fields
	private Integer id;
	private Integer lpId;
	private String buildName;
	private String unitName;
	private String roomCode;
	private Integer meterId;
	private String code;
	private String meterType;
	private String beginDate;
	private String endDate;
	private String lastRecord;
	private String thisRecord;
	private String years;
	private String months;

	// Constructors

	/** default constructor */
	public UserMeterReadingId() {
	}

	/** minimal constructor */
	public UserMeterReadingId(Integer meterId) {
		this.meterId = meterId;
	}

	/** full constructor */
	public UserMeterReadingId(Integer id,Integer lpId, String buildName, String unitName,
			String roomCode, Integer meterId, String code, String meterType,
			String beginDate, String endDate, String lastRecord,
			String thisRecord, String years, String months) {
		this.id = id;
		this.lpId = lpId;
		this.buildName = buildName;
		this.unitName = unitName;
		this.roomCode = roomCode;
		this.meterId = meterId;
		this.code = code;
		this.meterType = meterType;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.lastRecord = lastRecord;
		this.thisRecord = thisRecord;
		this.years = years;
		this.months = months;
	}

	// Property accessors

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public String getBuildName() {
		return this.buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRoomCode() {
		return this.roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public Integer getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMeterType() {
		return this.meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
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

	public String getLastRecord() {
		return this.lastRecord;
	}

	public void setLastRecord(String lastRecord) {
		this.lastRecord = lastRecord;
	}

	public String getThisRecord() {
		return this.thisRecord;
	}

	public void setThisRecord(String thisRecord) {
		this.thisRecord = thisRecord;
	}

	public String getYears() {
		return this.years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getMonths() {
		return this.months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserMeterReadingId))
			return false;
		UserMeterReadingId castOther = (UserMeterReadingId) other;

		return ((this.getLpId() == castOther.getLpId()) || (this.getLpId() != null
				&& castOther.getLpId() != null && this.getLpId().equals(
				castOther.getLpId())))
				&& ((this.getBuildName() == castOther.getBuildName()) || (this
						.getBuildName() != null
						&& castOther.getBuildName() != null && this
						.getBuildName().equals(castOther.getBuildName())))
				&& ((this.getUnitName() == castOther.getUnitName()) || (this
						.getUnitName() != null
						&& castOther.getUnitName() != null && this
						.getUnitName().equals(castOther.getUnitName())))
				&& ((this.getRoomCode() == castOther.getRoomCode()) || (this
						.getRoomCode() != null
						&& castOther.getRoomCode() != null && this
						.getRoomCode().equals(castOther.getRoomCode())))
				&& ((this.getMeterId() == castOther.getMeterId()) || (this
						.getMeterId() != null
						&& castOther.getMeterId() != null && this.getMeterId()
						.equals(castOther.getMeterId())))
				&& ((this.getCode() == castOther.getCode()) || (this.getCode() != null
						&& castOther.getCode() != null && this.getCode()
						.equals(castOther.getCode())))
				&& ((this.getMeterType() == castOther.getMeterType()) || (this
						.getMeterType() != null
						&& castOther.getMeterType() != null && this
						.getMeterType().equals(castOther.getMeterType())))
				&& ((this.getBeginDate() == castOther.getBeginDate()) || (this
						.getBeginDate() != null
						&& castOther.getBeginDate() != null && this
						.getBeginDate().equals(castOther.getBeginDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getLastRecord() == castOther.getLastRecord()) || (this
						.getLastRecord() != null
						&& castOther.getLastRecord() != null && this
						.getLastRecord().equals(castOther.getLastRecord())))
				&& ((this.getThisRecord() == castOther.getThisRecord()) || (this
						.getThisRecord() != null
						&& castOther.getThisRecord() != null && this
						.getThisRecord().equals(castOther.getThisRecord())))
				&& ((this.getYears() == castOther.getYears()) || (this
						.getYears() != null
						&& castOther.getYears() != null && this.getYears()
						.equals(castOther.getYears())))
				&& ((this.getMonths() == castOther.getMonths()) || (this
						.getMonths() != null
						&& castOther.getMonths() != null && this.getMonths()
						.equals(castOther.getMonths())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLpId() == null ? 0 : this.getLpId().hashCode());
		result = 37 * result
				+ (getBuildName() == null ? 0 : this.getBuildName().hashCode());
		result = 37 * result
				+ (getUnitName() == null ? 0 : this.getUnitName().hashCode());
		result = 37 * result
				+ (getRoomCode() == null ? 0 : this.getRoomCode().hashCode());
		result = 37 * result
				+ (getMeterId() == null ? 0 : this.getMeterId().hashCode());
		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getMeterType() == null ? 0 : this.getMeterType().hashCode());
		result = 37 * result
				+ (getBeginDate() == null ? 0 : this.getBeginDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37
				* result
				+ (getLastRecord() == null ? 0 : this.getLastRecord()
						.hashCode());
		result = 37
				* result
				+ (getThisRecord() == null ? 0 : this.getThisRecord()
						.hashCode());
		result = 37 * result
				+ (getYears() == null ? 0 : this.getYears().hashCode());
		result = 37 * result
				+ (getMonths() == null ? 0 : this.getMonths().hashCode());
		return result;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUseRecord(){
		return "";
	}
	
	public String getPublicRecord(){
		return "";
	}

}