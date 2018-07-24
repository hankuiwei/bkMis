package com.zc13.bkmis.mapping;

import java.io.Serializable;

/***
 *  物业费 预收款 类  
 * @description (为避免 修改 预收款, 造成动乱, 故重新建类,专门计算 预付物业费)
 * @author GD
 * @Date 2018-1-26
 * @Time 下午01:59:28
 */
public class CAdvanceWuYF implements Serializable{
	// Fields

	private Integer id;
	private Integer clientId;
	private double amount;
	private String bz;
	private Integer rootUser;
	private Integer lpId; 

	// Constructors

	/** default constructor */
	public CAdvanceWuYF() {
	}

	/** full constructor */
	public CAdvanceWuYF(Integer clientId, double amount, String bz) {
		this.clientId = clientId;
		this.amount = amount;
		this.bz = bz;
	}

	public CAdvanceWuYF(Integer id, Integer clientId, double amount, String bz, Integer rootUser, Integer lpId) {
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
