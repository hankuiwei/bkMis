package com.zc13.bkmis.mapping;

/**
 * ECallInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ECallInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String callerPhone;
	private String calledPhone;
	private String callType;
	private String startTime;
	private String endTime;
	private Integer callTime;
	private String areaName;
	private String type;
	private double cost;
	private double serviceCost;
	private String operators;
	private String enterTime;
	private String isPaid;
	private String regionName;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	public ECallInfo(Integer id, String callerPhone, String calledPhone,
			String callType, String startTime, String endTime,
			Integer callTime, String areaName, String type, double cost,
			double serviceCost, String operators, String enterTime,
			String isPaid, String regionName, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.callerPhone = callerPhone;
		this.calledPhone = calledPhone;
		this.callType = callType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.callTime = callTime;
		this.areaName = areaName;
		this.type = type;
		this.cost = cost;
		this.serviceCost = serviceCost;
		this.operators = operators;
		this.enterTime = enterTime;
		this.isPaid = isPaid;
		this.regionName = regionName;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	/** default constructor */
	public ECallInfo() {
	}

	/** full constructor */
	

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCallType() {
		return this.callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Integer getCallTime() {
		return callTime;
	}

	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getServiceCost() {
		return this.serviceCost;
	}

	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}

	public String getOperators() {
		return this.operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public String getCallerPhone() {
		return callerPhone;
	}

	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}

	public String getCalledPhone() {
		return calledPhone;
	}

	public void setCalledPhone(String calledPhone) {
		this.calledPhone = calledPhone;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}