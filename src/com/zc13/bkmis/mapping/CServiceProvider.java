package com.zc13.bkmis.mapping;

import java.io.Serializable;

public class CServiceProvider implements Serializable {
	private Integer id;
	private String code;
	private String name;
	private Integer timeDelay;
	private Integer frontSeconds;
	private Double frontCost;
	private Integer nextEachSeconds;
	private Double eachCost;
	private String costType;
	private Integer lpId;
	private Integer rootUser;
	
	
	public CServiceProvider() {
		super();
	}
	public CServiceProvider(Integer id, String code, String name,
			Integer timeDelay, Integer frontSeconds, Double frontCost,
			Integer nextEachSeconds, Double eachCost, String costType,
			Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
