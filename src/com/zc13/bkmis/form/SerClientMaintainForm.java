package com.zc13.bkmis.form;


public class SerClientMaintainForm extends BasicForm {
	/**查询条件start*/
	private String begindate;
	private String enddate;
	private String workBegindate;//工作时间查询条件：开始时间
	private String workEnddate;//工作时间查询条件：结束时间
	private Integer cx_buildId;//楼栋
	private String cx_sendedMan;//派工人员
	private String cx_isEnabled;//是否有效
	private String cx_consumeDetails;//材料消耗详情，包括：房间或区域
	private String selstatus;//报修状态
	
	private String cx_sendcardCode;//派工单编号
	private String cx_department;//出料部门
	private String cx_materialName;//材料名称
	/**查询条件end*/
	private String flag;//标识在页面上具有的权限 如：flag="add"表示具有添加权限，flag=",add,edit,"表示具有添加和编辑的权限，等等
	
	/**保存原来的派遣人员id字符串信息*/
	private String oldSendedMan;
	
	private String oldSendingMan;//修改前保存的状态为已派遣的员工id信息
	private String newSendingMan;//修改后的状态为已派遣的员工id信息
	
	/*添加发送短信记录所需要的字段start*/
	private String phones;//电话号码
	private String names;//姓名
	private String contents;//发送内容
	/*添加发送短信记录所需要的字段end*/
	
	private Integer id;
	private Integer lpId;
	private Integer buildId;
	private String name;
	private String type;
	private String phone;
	private Integer roomId;
	private String area;
	private String projectId;
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
	private String appearanceTime;
	private String leaveTime;
	private String manHour;
	private String checkRecord;
	private String clientNotion;
	private String amountMoney;
	private double amountRate;
	private Integer rootUser;
	//登录用户名
	private String userName;
	private Integer userId;
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	private Integer totalcount;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCx_sendcardCode() {
		return cx_sendcardCode;
	}
	public void setCx_sendcardCode(String cx_sendcardCode) {
		this.cx_sendcardCode = cx_sendcardCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
		return doMethod;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getBuildId() {
		return buildId;
	}

	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getSelstatus() {
		return selstatus;
	}

	public void setSelstatus(String selstatus) {
		this.selstatus = selstatus;
	}

	public String getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(String amountMoney) {
		this.amountMoney = amountMoney;
	}
	public Integer getCx_buildId() {
		return cx_buildId;
	}
	public void setCx_buildId(Integer cx_buildId) {
		this.cx_buildId = cx_buildId;
	}
	public String getCx_sendedMan() {
		return cx_sendedMan;
	}
	public void setCx_sendedMan(String cx_sendedMan) {
		this.cx_sendedMan = cx_sendedMan;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getOldSendedMan() {
		return oldSendedMan;
	}
	public void setOldSendedMan(String oldSendedMan) {
		this.oldSendedMan = oldSendedMan;
	}
	public String getWorkBegindate() {
		return workBegindate;
	}
	public void setWorkBegindate(String workBegindate) {
		this.workBegindate = workBegindate;
	}
	public String getWorkEnddate() {
		return workEnddate;
	}
	public void setWorkEnddate(String workEnddate) {
		this.workEnddate = workEnddate;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public double getAmountRate() {
		return amountRate;
	}
	public void setAmountRate(double amountRate) {
		this.amountRate = amountRate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCx_department() {
		return cx_department;
	}
	public void setCx_department(String cx_department) {
		this.cx_department = cx_department;
	}
	public String getCx_materialName() {
		return cx_materialName;
	}
	public void setCx_materialName(String cx_materialName) {
		this.cx_materialName = cx_materialName;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCx_isEnabled() {
		return cx_isEnabled;
	}
	public String getOldSendingMan() {
		return oldSendingMan;
	}
	public void setOldSendingMan(String oldSendingMan) {
		this.oldSendingMan = oldSendingMan;
	}
	public String getNewSendingMan() {
		return newSendingMan;
	}
	public void setNewSendingMan(String newSendingMan) {
		this.newSendingMan = newSendingMan;
	}
	public void setCx_isEnabled(String cx_isEnabled) {
		this.cx_isEnabled = cx_isEnabled;
	}
	public String getCx_consumeDetails() {
		return cx_consumeDetails;
	}
	public void setCx_consumeDetails(String cx_consumeDetails) {
		this.cx_consumeDetails = cx_consumeDetails;
	}

}
