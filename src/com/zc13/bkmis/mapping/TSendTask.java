package com.zc13.bkmis.mapping;

import java.util.Date;

/**
 * TSendTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSendTask implements java.io.Serializable {

	// Fields

	private long taskId;
	private String destNumber;
	private String content;
	private String signName;
	private short sendPriority;
	private Date sendTime;
	private short statusReport;
	private short englishFlag;
	private short msgType;
	private String pushUrl;
	private short recAction;
	private Integer validMinute;
	private short sendFlag;
	private short commPort;
	private short splitCount;
	private String batchId;
	private String personnelName;
	private int lpId;
	private int rootUser;
	// Constructors

	/** default constructor */
	public TSendTask() {
	}

	/** minimal constructor */
	public TSendTask(String destNumber) {
		this.destNumber = destNumber;
	}

	// Property accessors

	public TSendTask(long taskId, String destNumber, String content, String signName, short sendPriority, Date sendTime, short statusReport, short englishFlag, short msgType, String pushUrl, short recAction, Integer validMinute, short sendFlag, short commPort, short splitCount, String batchId, String personnelName, int lpId, int rootUser) {
		super();
		this.taskId = taskId;
		this.destNumber = destNumber;
		this.content = content;
		this.signName = signName;
		this.sendPriority = sendPriority;
		this.sendTime = sendTime;
		this.statusReport = statusReport;
		this.englishFlag = englishFlag;
		this.msgType = msgType;
		this.pushUrl = pushUrl;
		this.recAction = recAction;
		this.validMinute = validMinute;
		this.sendFlag = sendFlag;
		this.commPort = commPort;
		this.splitCount = splitCount;
		this.batchId = batchId;
		this.personnelName = personnelName;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getDestNumber() {
		return this.destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public short getSendPriority() {
		return this.sendPriority;
	}

	public void setSendPriority(short sendPriority) {
		this.sendPriority = sendPriority;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public short getStatusReport() {
		return this.statusReport;
	}

	public void setStatusReport(short statusReport) {
		this.statusReport = statusReport;
	}

	public short getEnglishFlag() {
		return this.englishFlag;
	}

	public void setEnglishFlag(short englishFlag) {
		this.englishFlag = englishFlag;
	}

	public short getMsgType() {
		return this.msgType;
	}

	public void setMsgType(short msgType) {
		this.msgType = msgType;
	}

	public String getPushUrl() {
		return this.pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public short getRecAction() {
		return this.recAction;
	}

	public void setRecAction(short recAction) {
		this.recAction = recAction;
	}

	public Integer getValidMinute() {
		return this.validMinute;
	}

	public void setValidMinute(Integer validMinute) {
		this.validMinute = validMinute;
	}

	public short getSendFlag() {
		return this.sendFlag;
	}

	public void setSendFlag(short sendFlag) {
		this.sendFlag = sendFlag;
	}

	public short getCommPort() {
		return this.commPort;
	}

	public void setCommPort(short commPort) {
		this.commPort = commPort;
	}

	public short getSplitCount() {
		return this.splitCount;
	}

	public void setSplitCount(short splitCount) {
		this.splitCount = splitCount;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public int getLpId() {
		return lpId;
	}

	public void setLpId(int lpId) {
		this.lpId = lpId;
	}

	public int getRootUser() {
		return rootUser;
	}

	public void setRootUser(int rootUser) {
		this.rootUser = rootUser;
	}

}