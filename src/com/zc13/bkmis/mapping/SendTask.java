package com.zc13.bkmis.mapping;
/**
 * 指定等待发送队列表信息，将待发短信保存到此表中供组件定时查询来发送
 * @author wangzw
 * @Date Jun 30, 2011
 * @Time 3:31:41 PM
 */
public class SendTask {
	private int taskID;//
	private String destNumber;//短信发送目标，群发以分号“;”间隔
	private String content;//短信内容，中文长度超过70的自动拆分发送
	private String signName;//短信署名，内容可缺省为空
	private int sendPriority;//发送优先级(0～32)，字段数据类型：数字，数值可缺省为16，数值越大优先级越高
	private String datetime;//请求的发送时间，缺省为当前系统时间表示立即发送，可以指定时间发送，对于指定时间发送则比当前时间多QueryTimer的才有效，字段数据类型：日期/时间
	private int statusReport;//是否需要状态报告，字段数据类型：真/假，缺省为假
	private int englishFlag;//是否为英文短信标志，字段数据类型：真/假，缺省为假
	private int msgType;//短信类型，字段数据类型：数字，缺省为0(普通短信),10表示发送WAP Push
	private String pushUrl;//WAP PUSH URL地址，字段数据类型：文本，发送wap push才有用
	private int recAction;//接收动作，字段数据类型：数字，发送wap push才有用，缺省0即可
	private int validMinute;//有效期，字段数据类型：数字，缺省为0
	private int sendFlag;//发送标记，字段数据类型：数字，0表示待发(缺省值)，1为正在发送，2为发送完成，插入记录时自动设为0
	private int commPort;//指定发送此任务的端口，字段数据类型：数字，缺省为0表示自动选择端口发送
	private int splitCount;//拆分发送的总条数，字段数据类型：数字，缺省为1
	private String batchId;//客户自定义的字段，字段数据类型：文本，此字段值自动添加到已发送表记录中
	
	
	public SendTask() {
		super();
	}


	public SendTask(int taskID, String destNumber, String content, String signName, int sendPriority, String datetime, int statusReport, int englishFlag, int msgType, String pushUrl, int recAction, int validMinute, int sendFlag, int commPort, int splitCount, String batchId) {
		super();
		this.taskID = taskID;
		this.destNumber = destNumber;
		this.content = content;
		this.signName = signName;
		this.sendPriority = sendPriority;
		this.datetime = datetime;
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
	}


	public int getTaskID() {
		return taskID;
	}


	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}


	public String getDestNumber() {
		return destNumber;
	}


	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSignName() {
		return signName;
	}


	public void setSignName(String signName) {
		this.signName = signName;
	}


	public int getSendPriority() {
		return sendPriority;
	}


	public void setSendPriority(int sendPriority) {
		this.sendPriority = sendPriority;
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public int getStatusReport() {
		return statusReport;
	}


	public void setStatusReport(int statusReport) {
		this.statusReport = statusReport;
	}


	public int getEnglishFlag() {
		return englishFlag;
	}


	public void setEnglishFlag(int englishFlag) {
		this.englishFlag = englishFlag;
	}


	public int getMsgType() {
		return msgType;
	}


	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	public String getPushUrl() {
		return pushUrl;
	}


	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}


	public int getRecAction() {
		return recAction;
	}


	public void setRecAction(int recAction) {
		this.recAction = recAction;
	}


	public int getValidMinute() {
		return validMinute;
	}


	public void setValidMinute(int validMinute) {
		this.validMinute = validMinute;
	}


	public int getSendFlag() {
		return sendFlag;
	}


	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}


	public int getCommPort() {
		return commPort;
	}


	public void setCommPort(int commPort) {
		this.commPort = commPort;
	}


	public int getSplitCount() {
		return splitCount;
	}


	public void setSplitCount(int splitCount) {
		this.splitCount = splitCount;
	}


	public String getBatchId() {
		return batchId;
	}


	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	
}
