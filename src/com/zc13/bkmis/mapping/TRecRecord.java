package com.zc13.bkmis.mapping;

import java.util.Date;

/**
 * TRecRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TRecRecord implements java.io.Serializable {

	// Fields

	private long smsIndex;
	private String sourceNumber;
	private String content;
	private Date sentTime;
	private short commPort;
	private short msgType;

	// Constructors

	/** default constructor */
	public TRecRecord() {
	}

	/** minimal constructor */
	public TRecRecord(String sourceNumber, Date sentTime, short commPort) {
		this.sourceNumber = sourceNumber;
		this.sentTime = sentTime;
		this.commPort = commPort;
	}

	/** full constructor */
	public TRecRecord(String sourceNumber, String content, Date sentTime, short commPort, short msgType) {
		this.sourceNumber = sourceNumber;
		this.content = content;
		this.sentTime = sentTime;
		this.commPort = commPort;
		this.msgType = msgType;
	}

	// Property accessors

	public long getSmsIndex() {
		return this.smsIndex;
	}

	public void setSmsIndex(long smsIndex) {
		this.smsIndex = smsIndex;
	}

	public String getSourceNumber() {
		return this.sourceNumber;
	}

	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public short getMsgType() {
		return this.msgType;
	}

	public void setMsgType(short msgType) {
		this.msgType = msgType;
	}

}