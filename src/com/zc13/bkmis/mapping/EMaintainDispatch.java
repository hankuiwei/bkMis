package com.zc13.bkmis.mapping;

/**
 * EMaintainDispatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EMaintainDispatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private HrPersonnel hrPersonnel;
	private SerClientMaintain serClientMaintain;
	private String appearanceTime;
	private String leaveTime;
	private String status;
	private double workHours;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public EMaintainDispatch() {
	}

	/** full constructor */
	public EMaintainDispatch(HrPersonnel hrPersonnel, SerClientMaintain serClientMaintain, String appearanceTime, String leaveTime, String status, double workHours, Integer lpId, Integer rootUser) {
		this.hrPersonnel = hrPersonnel;
		this.serClientMaintain = serClientMaintain;
		this.appearanceTime = appearanceTime;
		this.leaveTime = leaveTime;
		this.status = status;
		this.workHours = workHours;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HrPersonnel getHrPersonnel() {
		return this.hrPersonnel;
	}

	public void setHrPersonnel(HrPersonnel hrPersonnel) {
		this.hrPersonnel = hrPersonnel;
	}

	public SerClientMaintain getSerClientMaintain() {
		return this.serClientMaintain;
	}

	public void setSerClientMaintain(SerClientMaintain serClientMaintain) {
		this.serClientMaintain = serClientMaintain;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getWorkHours() {
		return this.workHours;
	}

	public void setWorkHours(double workHours) {
		this.workHours = workHours;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

}