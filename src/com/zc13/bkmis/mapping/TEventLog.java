package com.zc13.bkmis.mapping;

import java.util.Date;

/**
 * TEventLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TEventLog implements java.io.Serializable {

	// Fields

	private long logIndex;
	private short commPort;
	private String description;
	private Date happenTime;

	// Constructors

	/** default constructor */
	public TEventLog() {
	}

	/** minimal constructor */
	public TEventLog(String description, Date happenTime) {
		this.description = description;
		this.happenTime = happenTime;
	}

	/** full constructor */
	public TEventLog(short commPort, String description, Date happenTime) {
		this.commPort = commPort;
		this.description = description;
		this.happenTime = happenTime;
	}

	// Property accessors

	public long getLogIndex() {
		return this.logIndex;
	}

	public void setLogIndex(long logIndex) {
		this.logIndex = logIndex;
	}

	public short getCommPort() {
		return this.commPort;
	}

	public void setCommPort(short commPort) {
		this.commPort = commPort;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getHappenTime() {
		return this.happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

}