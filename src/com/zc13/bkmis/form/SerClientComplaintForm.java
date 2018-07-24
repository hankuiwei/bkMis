package com.zc13.bkmis.form;

import java.util.List;

public class SerClientComplaintForm extends BasicForm {
	
	/*查询条件start*/
	private String cx_status;
	private String begindate;
	private String enddate;
	/*查询条件end*/
	
	private List list;
	
	private Integer id;
	private String type;
	private String name;
	private String phone;
	private String roomCode;
	private String complaintContent;
	private String complaintDate;
	private String clerk;
	private String code;
	private String status;
	private String verifyContent;
	private String verifyResult;
	private String verifyMan;
	private String verifyDate;
	private String disposalWay;
	private String disposalDate;
	private String disposalResult;
	private String dutyMan;
	private String ownerOpinion;
	private String leadOpinion;
	private String generalOpinion;
	private Integer lpId;
	private Integer rootUser;
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	public Integer totalcount;//总条数
	
	/**查询条件end*/
	private String flag;//标识在页面上具有的权限 如：flag="add"表示具有添加权限，flag=",add,edit,"表示具有添加和编辑的权限，等等
	
	//登录用户名
	private String userName;

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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoomCode() {
		return this.roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getComplaintContent() {
		return this.complaintContent;
	}

	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}

	public String getComplaintDate() {
		return this.complaintDate;
	}

	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getClerk() {
		return this.clerk;
	}

	public void setClerk(String clerk) {
		this.clerk = clerk;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerifyContent() {
		return this.verifyContent;
	}

	public void setVerifyContent(String verifyContent) {
		this.verifyContent = verifyContent;
	}

	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getVerifyMan() {
		return this.verifyMan;
	}

	public void setVerifyMan(String verifyMan) {
		this.verifyMan = verifyMan;
	}

	public String getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getDisposalWay() {
		return this.disposalWay;
	}

	public void setDisposalWay(String disposalWay) {
		this.disposalWay = disposalWay;
	}

	public String getDisposalDate() {
		return this.disposalDate;
	}

	public void setDisposalDate(String disposalDate) {
		this.disposalDate = disposalDate;
	}

	public String getDisposalResult() {
		return this.disposalResult;
	}

	public void setDisposalResult(String disposalResult) {
		this.disposalResult = disposalResult;
	}

	public String getDutyMan() {
		return this.dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}

	public String getOwnerOpinion() {
		return this.ownerOpinion;
	}

	public void setOwnerOpinion(String ownerOpinion) {
		this.ownerOpinion = ownerOpinion;
	}

	public String getLeadOpinion() {
		return this.leadOpinion;
	}

	public void setLeadOpinion(String leadOpinion) {
		this.leadOpinion = leadOpinion;
	}

	public String getGeneralOpinion() {
		return this.generalOpinion;
	}

	public void setGeneralOpinion(String generalOpinion) {
		this.generalOpinion = generalOpinion;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}
	public String getCx_status() {
		return cx_status;
	}
	public void setCx_status(String cx_status) {
		this.cx_status = cx_status;
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
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

}
