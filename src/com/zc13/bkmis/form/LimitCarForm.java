package com.zc13.bkmis.form;


/**
 * 车辆限行操作的属性
 * @author daokui
 * @Date Apr 28, 2011
 * @Time 6:24:42 PM
 */
public class LimitCarForm extends BasicForm{
	
	private Integer id;
	private String beginDate;
	private String endDate;
	private String mon;
	private String tues;
	private String wed;
	private String thrus;
	private String friday;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getTues() {
		return tues;
	}
	public void setTues(String tues) {
		this.tues = tues;
	}
	public String getWed() {
		return wed;
	}
	public void setWed(String wed) {
		this.wed = wed;
	}
	public String getThrus() {
		return thrus;
	}
	public void setThrus(String thrus) {
		this.thrus = thrus;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}

}
