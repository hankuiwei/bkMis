package com.zc13.bkmis.mapping;

import java.io.Serializable;

/**
 * 实体类
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午11:14:12
 */
public class CalculateMoney implements Serializable {

	private Integer id;
	private String year;
	private String month;
	private Integer roomId;
	private Integer compactId;
	private Integer clientId;
	private String circleStart;
	private String circleEnd;
	private String billDate;
	private double money;
	private String startTime;
	private String endTime;
	private Integer lpId;
	
	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public CalculateMoney(){}
	
	public CalculateMoney(Integer id,String year,String month,Integer roomId,Integer compactId,Integer clientId,String circleStart,String circleEnd,String billDate,double money,String startTime,String endTime){
		
		this.id = id;
		this.year = year;
		this.month = year;
		this.roomId = roomId;
		this.compactId = compactId;
		this.clientId = clientId;
		this.circleStart = circleStart;
		this.circleEnd = circleEnd;
		this.billDate = billDate;
		this.money = money;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public Integer getCompactId() {
		return compactId;
	}
	public void setCompactId(Integer compactId) {
		this.compactId = compactId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getCircleStart() {
		return circleStart;
	}
	public void setCircleStart(String circleStart) {
		this.circleStart = circleStart;
	}
	public String getCircleEnd() {
		return circleEnd;
	}
	public void setCircleEnd(String circleEnd) {
		this.circleEnd = circleEnd;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
}
