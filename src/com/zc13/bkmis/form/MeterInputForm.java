package com.zc13.bkmis.form;

import org.apache.struts.action.ActionForm;
/**
 * 
 * @author 王正伟
 * Date：Dec 9, 2010
 * Time：5:49:19 PM
 */
public class MeterInputForm  extends ActionForm{
	//统计页面的查询条件
	private String lpId;//楼盘id
	private String meterName;//表具名称
	private String meterTypeCode;//表具类型代码
	private String years;//年份
	//表具抄录向导页面的查询条件
	private String months;//月份
	private String lookUpMan;//抄表人
	private String lookUpTime;//抄表时间
	private String enterMan;//录入人
	
	private String meterId;//表具id
	private String roomCode;//房间号
	private String meterCode;//表具编号
	private String meterCode4Public;//公摊表具编号
	
	public String getMeterCode4Public() {
		return meterCode4Public;
	}
	public void setMeterCode4Public(String meterCode4Public) {
		this.meterCode4Public = meterCode4Public;
	}
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	public String getMeterCode() {
		return meterCode;
	}
	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}
	public String getLpId() {
		return lpId;
	}
	public void setLpId(String lpId) {
		this.lpId = lpId;
	}
	public String getMeterName() {
		return meterName;
	}
	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getMeterTypeCode() {
		return meterTypeCode;
	}
	public void setMeterTypeCode(String meterTypeCode) {
		this.meterTypeCode = meterTypeCode;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getLookUpMan() {
		return lookUpMan;
	}
	public void setLookUpMan(String lookUpMan) {
		this.lookUpMan = lookUpMan;
	}
	public String getLookUpTime() {
		return lookUpTime;
	}
	public void setLookUpTime(String lookUpTime) {
		this.lookUpTime = lookUpTime;
	}
	public String getEnterMan() {
		return enterMan;
	}
	public void setEnterMan(String enterMan) {
		this.enterMan = enterMan;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	
	
}
