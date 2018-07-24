package com.zc13.bkmis.mapping;

/**
 * AllMeterTypeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AllMeterTypeId implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer lpId;
	private String lpCode;
	private String lpName;
	private String meterFlag;
	private Integer meterTypeId;
	private String meterTypeCode;
	private String tableName;

	// Constructors

	/** default constructor */
	public AllMeterTypeId() {
	}

	/** minimal constructor */
	public AllMeterTypeId(Integer lpId, Integer meterTypeId, String tableName) {
		this.lpId = lpId;
		this.meterTypeId = meterTypeId;
		this.tableName = tableName;
	}

	/** full constructor */
	public AllMeterTypeId(Integer pk, Integer id, String name,
			Integer parentId, Integer lpId, String lpCode, String lpName,
			String meterFlag, Integer meterTypeId, String meterTypeCode,
			String tableName) {
		this.pk = pk;
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.lpId = lpId;
		this.lpCode = lpCode;
		this.lpName = lpName;
		this.meterFlag = meterFlag;
		this.meterTypeId = meterTypeId;
		this.meterTypeCode = meterTypeCode;
		this.tableName = tableName;
	}

	// Property accessors

	public Integer getPk() {
		return this.pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public String getLpCode() {
		return this.lpCode;
	}

	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}

	public String getLpName() {
		return this.lpName;
	}

	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	public String getMeterFlag() {
		return this.meterFlag;
	}

	public void setMeterFlag(String meterFlag) {
		this.meterFlag = meterFlag;
	}

	public Integer getMeterTypeId() {
		return this.meterTypeId;
	}

	public void setMeterTypeId(Integer meterTypeId) {
		this.meterTypeId = meterTypeId;
	}

	public String getMeterTypeCode() {
		return this.meterTypeCode;
	}

	public void setMeterTypeCode(String meterTypeCode) {
		this.meterTypeCode = meterTypeCode;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AllMeterTypeId))
			return false;
		AllMeterTypeId castOther = (AllMeterTypeId) other;

		return ((this.getPk() == castOther.getPk()) || (this.getPk() != null
				&& castOther.getPk() != null && this.getPk().equals(
				castOther.getPk())))
				&& ((this.getId() == castOther.getId()) || (this.getId() != null
						&& castOther.getId() != null && this.getId().equals(
						castOther.getId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getParentId() == castOther.getParentId()) || (this
						.getParentId() != null
						&& castOther.getParentId() != null && this
						.getParentId().equals(castOther.getParentId())))
				&& ((this.getLpId() == castOther.getLpId()) || (this.getLpId() != null
						&& castOther.getLpId() != null && this.getLpId()
						.equals(castOther.getLpId())))
				&& ((this.getLpCode() == castOther.getLpCode()) || (this
						.getLpCode() != null
						&& castOther.getLpCode() != null && this.getLpCode()
						.equals(castOther.getLpCode())))
				&& ((this.getLpName() == castOther.getLpName()) || (this
						.getLpName() != null
						&& castOther.getLpName() != null && this.getLpName()
						.equals(castOther.getLpName())))
				&& ((this.getMeterFlag() == castOther.getMeterFlag()) || (this
						.getMeterFlag() != null
						&& castOther.getMeterFlag() != null && this
						.getMeterFlag().equals(castOther.getMeterFlag())))
				&& ((this.getMeterTypeId() == castOther.getMeterTypeId()) || (this
						.getMeterTypeId() != null
						&& castOther.getMeterTypeId() != null && this
						.getMeterTypeId().equals(castOther.getMeterTypeId())))
				&& ((this.getMeterTypeCode() == castOther.getMeterTypeCode()) || (this
						.getMeterTypeCode() != null
						&& castOther.getMeterTypeCode() != null && this
						.getMeterTypeCode()
						.equals(castOther.getMeterTypeCode())))
				&& ((this.getTableName() == castOther.getTableName()) || (this
						.getTableName() != null
						&& castOther.getTableName() != null && this
						.getTableName().equals(castOther.getTableName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPk() == null ? 0 : this.getPk().hashCode());
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getParentId() == null ? 0 : this.getParentId().hashCode());
		result = 37 * result
				+ (getLpId() == null ? 0 : this.getLpId().hashCode());
		result = 37 * result
				+ (getLpCode() == null ? 0 : this.getLpCode().hashCode());
		result = 37 * result
				+ (getLpName() == null ? 0 : this.getLpName().hashCode());
		result = 37 * result
				+ (getMeterFlag() == null ? 0 : this.getMeterFlag().hashCode());
		result = 37
				* result
				+ (getMeterTypeId() == null ? 0 : this.getMeterTypeId()
						.hashCode());
		result = 37
				* result
				+ (getMeterTypeCode() == null ? 0 : this.getMeterTypeCode()
						.hashCode());
		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		return result;
	}

}