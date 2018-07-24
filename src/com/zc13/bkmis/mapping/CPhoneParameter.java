package com.zc13.bkmis.mapping;

import java.io.Serializable;

public class CPhoneParameter implements Serializable {
	private Integer id;
	private Integer providerId;
	private String prefix;
	private Integer timeDelay;
	private Integer frontSeconds;
	private Double frontCost;
	private Integer nextEachSeconds;
	private Double eachCost;
	private String costType;
	private Integer lpId;
	private Integer rootUser;
	
	
	public CPhoneParameter() {
		super();
	}
	public CPhoneParameter(Integer id, Integer providerId, String prefix,
			Integer timeDelay, Integer frontSeconds, Double frontCost,
			Integer nextEachSeconds, Double eachCost, String costType,
			Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.providerId = providerId;
		this.prefix = prefix;
		this.timeDelay = timeDelay;
		this.frontSeconds = frontSeconds;
		this.frontCost = frontCost;
		this.nextEachSeconds = nextEachSeconds;
		this.eachCost = eachCost;
		this.costType = costType;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Integer getTimeDelay() {
		return timeDelay;
	}
	public void setTimeDelay(Integer timeDelay) {
		this.timeDelay = timeDelay;
	}
	public Integer getFrontSeconds() {
		return frontSeconds;
	}
	public void setFrontSeconds(Integer frontSeconds) {
		this.frontSeconds = frontSeconds;
	}
	public Double getFrontCost() {
		return frontCost;
	}
	public void setFrontCost(Double frontCost) {
		this.frontCost = frontCost;
	}
	public Integer getNextEachSeconds() {
		return nextEachSeconds;
	}
	public void setNextEachSeconds(Integer nextEachSeconds) {
		this.nextEachSeconds = nextEachSeconds;
	}
	public Double getEachCost() {
		return eachCost;
	}
	public void setEachCost(Double eachCost) {
		this.eachCost = eachCost;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
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
	
}
