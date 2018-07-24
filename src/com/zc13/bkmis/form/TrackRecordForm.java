package com.zc13.bkmis.form;

import com.zc13.bkmis.mapping.TrackRecord;

/**
 * 客户跟踪
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:43:44
 */
public class TrackRecordForm extends BasicForm {

	private String customerName;
	private String status;
	private TrackRecord trackRecord = new TrackRecord();
	private String ids;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public TrackRecord getTrackRecord() {
		return trackRecord;
	}
	public void setTrackRecord(TrackRecord trackRecord) {
		this.trackRecord = trackRecord;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
