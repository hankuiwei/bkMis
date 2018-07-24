package com.zc13.bkmis.mapping;

/**
 * AnalysisEnergy generated by MyEclipse Persistence Tools
 */

public class AnalysisEnergy implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lpId;
	private String createDate;
	private String beginDate;
	private String endDate;
	private float totalElectricity;
	private float totalWater;
	private float totalGas;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public AnalysisEnergy() {
	}

	/** full constructor */
	public AnalysisEnergy(Integer lpId, String createDate, String beginDate,
			String endDate, float totalElectricity, float totalWater,
			float totalGas, Integer rootUser) {
		this.lpId = lpId;
		this.createDate = createDate;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.totalElectricity = totalElectricity;
		this.totalWater = totalWater;
		this.totalGas = totalGas;
		this.rootUser = rootUser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public float getTotalElectricity() {
		return this.totalElectricity;
	}

	public void setTotalElectricity(float totalElectricity) {
		this.totalElectricity = totalElectricity;
	}

	public float getTotalWater() {
		return this.totalWater;
	}

	public void setTotalWater(float totalWater) {
		this.totalWater = totalWater;
	}

	public float getTotalGas() {
		return this.totalGas;
	}

	public void setTotalGas(float totalGas) {
		this.totalGas = totalGas;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}