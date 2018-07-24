package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.HrRecord;
/**
 * 考勤form
 * @author wangzw
 * @Date Jun 20, 2011
 * @Time 3:08:15 PM
 */
public class AttendanceForm extends BasicForm{
	/*查询条件start*/
	/**查询条件:员工姓名*/
	private String cx_personnelName;
	/**查询条件:员工编号*/
	private String cx_personnelCode;
	/**查询条件:员工工号*/
	private String cx_personnelNum;
	private String cx_endtime;
	private String cx_starttime;
	/**查询条件:用工状态*/
	private String cxStatus;
	/**查询条件:是否是派工工人*/
	private String cx_isDispatch;
	/**查询条件:是否在岗*/
	private String cx_isInPost;
	/**查询条件:是否已派出*/
	private String cx_isOut;
	private Integer rootUser;
	/*查询条件end*/
	
	/*考勤记录维护所需的字段start*/
	private Integer personnelId;
	private String personnelNum;
	private String status;
	private String time;
	/*考勤记录维护所需的字段end*/
	
	private List attendanceList;
	private List personnelList;
	
	private HrRecord record = new HrRecord();
	private HrPersonnel person = new HrPersonnel();
	private Integer totalcount;//总条数

	//登录用户名
	private String userName;


	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List attendanceList) {
		this.attendanceList = attendanceList;
	}

	public String getCx_personnelName() {
		return cx_personnelName;
	}

	public void setCx_personnelName(String cx_personnelName) {
		this.cx_personnelName = cx_personnelName;
	}

	public String getCx_personnelCode() {
		return cx_personnelCode;
	}

	public void setCx_personnelCode(String cx_personnelCode) {
		this.cx_personnelCode = cx_personnelCode;
	}

	public String getCx_personnelNum() {
		return cx_personnelNum;
	}

	public void setCx_personnelNum(String cx_personnelNum) {
		this.cx_personnelNum = cx_personnelNum;
	}

	public String getCx_endtime() {
		return cx_endtime;
	}

	public void setCx_endtime(String cx_endtime) {
		this.cx_endtime = cx_endtime;
	}

	public String getCx_starttime() {
		return cx_starttime;
	}

	public void setCx_starttime(String cx_starttime) {
		this.cx_starttime = cx_starttime;
	}

	public String getCxStatus() {
		return cxStatus;
	}

	public void setCxStatus(String cxStatus) {
		this.cxStatus = cxStatus;
	}

	public String getCx_isDispatch() {
		return cx_isDispatch;
	}

	public void setCx_isDispatch(String cx_isDispatch) {
		this.cx_isDispatch = cx_isDispatch;
	}

	public String getCx_isInPost() {
		return cx_isInPost;
	}

	public void setCx_isInPost(String cx_isInPost) {
		this.cx_isInPost = cx_isInPost;
	}

	public String getCx_isOut() {
		return cx_isOut;
	}

	public void setCx_isOut(String cx_isOut) {
		this.cx_isOut = cx_isOut;
	}

	public Integer getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}

	public List getPersonnelList() {
		return personnelList;
	}

	public void setPersonnelList(List personnelList) {
		this.personnelList = personnelList;
	}

	public HrRecord getRecord() {
		return record;
	}

	public void setRecord(HrRecord record) {
		this.record = record;
	}

	public HrPersonnel getPerson() {
		return person;
	}

	public void setPerson(HrPersonnel person) {
		this.person = person;
	}

	public Integer getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(Integer personnelId) {
		this.personnelId = personnelId;
	}

	public String getPersonnelNum() {
		return personnelNum;
	}

	public void setPersonnelNum(String personnelNum) {
		this.personnelNum = personnelNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
