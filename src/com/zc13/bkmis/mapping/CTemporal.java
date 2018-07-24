package com.zc13.bkmis.mapping;

/**
 * CTemporal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CTemporal implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String bz;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public CTemporal() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

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

	public CTemporal(Integer id, Integer clientId, double amount, String bz, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.amount = amount;
		this.bz = bz;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}