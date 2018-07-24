package com.zc13.bkmis.mapping;

import java.io.Serializable;

/**
 * 客户跟踪记录
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:30:44
 */
public class TrackRecord implements Serializable {

	private Integer id;
	private String code;
	private String customerName;
	private String linkWay;
	private String workField;
	private String status;
	private String bz;
	private String createDate;
	private Integer userId;
	
	public TrackRecord(){
		
	}

	public TrackRecord(Integer id,String code,String customerName,String linkWay,String workField,String status,String bz,String createDate,Integer userId){
		
		this.id = id;
		this.code = code;
		this.customerName = customerName;
		this.linkWay = linkWay;
		this.workField = workField;
		this.status = status;
		this.workField = workField;
		this.userId = userId;
		this.bz = bz;
		this.createDate = createDate;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLinkWay() {
		return linkWay;
	}

	public void setLinkWay(String linkWay) {
		this.linkWay = linkWay;
	}

	public String getWorkField() {
		return workField;
	}

	public void setWorkField(String workField) {
		this.workField = workField;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
