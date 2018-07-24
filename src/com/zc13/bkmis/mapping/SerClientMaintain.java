package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * SerClientMaintain generated by MyEclipse Persistence Tools
 */

public class SerClientMaintain implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String type;
	private String phone;
	private Integer buildId;
	private Integer roomId;
	private String area;
	private Integer projectId;
	private String project;
	private String contentExplain;
	private String date;
	private String clerk;
	private String status;
	private String sendcardCode;
	private String sendDutyMan;
	private String sendedMan;
	private String sendTime;
	private String doMethod;
	private String maintainContent;
	private float amountMoney;
	private double amountRate;
	private String appearanceTime;
	private String leaveTime;
	private String manHour;
	private String checkRecord;
	private String clientNotion;
	private String isEnabled;
	private Integer lpId;
	private Integer rootUser;
	private Set serMaterialConsumes = new HashSet(0);

	// Constructors

	/** default constructor */
	public SerClientMaintain() {
	}

	public SerClientMaintain(Integer id, String name, String type, String phone, Integer buildId, Integer roomId, String area, Integer projectId, String project, String contentExplain, String date, String clerk, String status, String sendcardCode, String sendDutyMan, String sendedMan, String sendTime, String doMethod, String maintainContent, float amountMoney, double amountRate, String appearanceTime, String leaveTime, String manHour, String checkRecord, String clientNotion, Integer lpId,
			Integer rootUser, Set serMaterialConsumes) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.phone = phone;
		this.buildId = buildId;
		this.roomId = roomId;
		this.area = area;
		this.projectId = projectId;
		this.project = project;
		this.contentExplain = contentExplain;
		this.date = date;
		this.clerk = clerk;
		this.status = status;
		this.sendcardCode = sendcardCode;
		this.sendDutyMan = sendDutyMan;
		this.sendedMan = sendedMan;
		this.sendTime = sendTime;
		this.doMethod = doMethod;
		this.maintainContent = maintainContent;
		this.amountMoney = amountMoney;
		this.amountRate = amountRate;
		this.appearanceTime = appearanceTime;
		this.leaveTime = leaveTime;
		this.manHour = manHour;
		this.checkRecord = checkRecord;
		this.clientNotion = clientNotion;
		this.lpId = lpId;
		this.rootUser = rootUser;
		this.serMaterialConsumes = serMaterialConsumes;
	}

	/** full constructor */

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getBuildId() {
		return this.buildId;
	}

	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getContentExplain() {
		return this.contentExplain;
	}

	public void setContentExplain(String contentExplain) {
		this.contentExplain = contentExplain;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClerk() {
		return this.clerk;
	}

	public void setClerk(String clerk) {
		this.clerk = clerk;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSendcardCode() {
		return this.sendcardCode;
	}

	public void setSendcardCode(String sendcardCode) {
		this.sendcardCode = sendcardCode;
	}

	public String getSendDutyMan() {
		return this.sendDutyMan;
	}

	public void setSendDutyMan(String sendDutyMan) {
		this.sendDutyMan = sendDutyMan;
	}

	public String getSendedMan() {
		return this.sendedMan;
	}

	public void setSendedMan(String sendedMan) {
		this.sendedMan = sendedMan;
	}

	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getDoMethod() {
		return this.doMethod;
	}

	public void setDoMethod(String doMethod) {
		this.doMethod = doMethod;
	}

	public String getMaintainContent() {
		return this.maintainContent;
	}

	public void setMaintainContent(String maintainContent) {
		this.maintainContent = maintainContent;
	}

	public float getAmountMoney() {
		return this.amountMoney;
	}

	public void setAmountMoney(float amountMoney) {
		this.amountMoney = amountMoney;
	}

	public String getAppearanceTime() {
		return this.appearanceTime;
	}

	public void setAppearanceTime(String appearanceTime) {
		this.appearanceTime = appearanceTime;
	}

	public String getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getManHour() {
		return this.manHour;
	}

	public void setManHour(String manHour) {
		this.manHour = manHour;
	}

	public String getCheckRecord() {
		return this.checkRecord;
	}

	public void setCheckRecord(String checkRecord) {
		this.checkRecord = checkRecord;
	}

	public String getClientNotion() {
		return this.clientNotion;
	}

	public void setClientNotion(String clientNotion) {
		this.clientNotion = clientNotion;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Set getSerMaterialConsumes() {
		return this.serMaterialConsumes;
	}

	public void setSerMaterialConsumes(Set serMaterialConsumes) {
		this.serMaterialConsumes = serMaterialConsumes;
	}
	
	public double getAmountRate() {
		return amountRate;
	}

	public void setAmountRate(double amountRate) {
		this.amountRate = amountRate;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

}