package com.zc13.bkmis.mapping;

/**
 * CAdvance entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CAdvance implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String bz;
	private Integer rootUser;
	private Integer lpId; 

	// Constructors

	public CAdvance(Integer id, Integer clientId, double amount, String bz, Integer rootUser, Integer lpId) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.amount = amount;
		this.bz = bz;
		this.rootUser = rootUser;
		this.lpId = lpId;
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

	/** default constructor */
	public CAdvance() {
	}

	/** full constructor */
	public CAdvance(Integer clientId, double amount, String bz) {
		this.clientId = clientId;
		this.amount = amount;
		this.bz = bz;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
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