package com.zc13.bkmis.mapping;

import java.util.Date;

/**
 * TSentRecordId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSentRecordId implements java.io.Serializable {

	// Fields

	private long msgId;
	private short splitIndex;
	private String destTel;
	private String content;
	private short sentStatus;
	private Date sentTime;
	private short commPort;
	private String batchId;

	// Constructors

	/** default constructor */
	public TSentRecordId() {
	}

	/** minimal constructor */
	public TSentRecordId(long msgId, String destTel, Date sentTime, short commPort) {
		this.msgId = msgId;
		this.destTel = destTel;
		this.sentTime = sentTime;
		this.commPort = commPort;
	}

	/** full constructor */
	public TSentRecordId(long msgId, short splitIndex, String destTel, String content, short sentStatus, Date sentTime, short commPort, String batchId) {
		this.msgId = msgId;
		this.splitIndex = splitIndex;
		this.destTel = destTel;
		this.content = content;
		this.sentStatus = sentStatus;
		this.sentTime = sentTime;
		this.commPort = commPort;
		this.batchId = batchId;
	}

	// Property accessors

	public long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public short getSplitIndex() {
		return this.splitIndex;
	}

	public void setSplitIndex(short splitIndex) {
		this.splitIndex = splitIndex;
	}

	public String getDestTel() {
		return this.destTel;
	}

	public void setDestTel(String destTel) {
		this.destTel = destTel;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public short getSentStatus() {
		return this.sentStatus;
	}

	public void setSentStatus(short sentStatus) {
		this.sentStatus = sentStatus;
	}

	public Date getSentTime() {
		return this.sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public short getCommPort() {
		return this.commPort;
	}

	public void setCommPort(short commPort) {
		this.commPort = commPort;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TSentRecordId))
			return false;
		TSentRecordId castOther = (TSentRecordId) other;

		return (this.getMsgId() == castOther.getMsgId()) && (this.getSplitIndex() == castOther.getSplitIndex()) && ((this.getDestTel() == castOther.getDestTel()) || (this.getDestTel() != null && castOther.getDestTel() != null && this.getDestTel().equals(castOther.getDestTel()))) && ((this.getContent() == castOther.getContent()) || (this.getContent() != null && castOther.getContent() != null && this.getContent().equals(castOther.getContent())))
				&& (this.getSentStatus() == castOther.getSentStatus()) && ((this.getSentTime() == castOther.getSentTime()) || (this.getSentTime() != null && castOther.getSentTime() != null && this.getSentTime().equals(castOther.getSentTime()))) && (this.getCommPort() == castOther.getCommPort()) && ((this.getBatchId() == castOther.getBatchId()) || (this.getBatchId() != null && castOther.getBatchId() != null && this.getBatchId().equals(castOther.getBatchId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getMsgId();
		result = 37 * result + this.getSplitIndex();
		result = 37 * result + (getDestTel() == null ? 0 : this.getDestTel().hashCode());
		result = 37 * result + (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37 * result + this.getSentStatus();
		result = 37 * result + (getSentTime() == null ? 0 : this.getSentTime().hashCode());
		result = 37 * result + this.getCommPort();
		result = 37 * result + (getBatchId() == null ? 0 : this.getBatchId().hashCode());
		return result;
	}

}