package com.zc13.bkmis.form;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.zc13.bkmis.mapping.HrPact;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.SerClientMaintain;
/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:37:22 AM
 */
public class PersonnelForm extends BasicForm {
	
	/*查询条件start*/
	/**查询条件:员工姓名*/
	private String cx_personnelName;
	/**查询条件:员工编号*/
	private String cx_personnelCode;
	/**查询条件:员工工号*/
	private String cx_personnelNum;
	/**查询条件:所属部门*/
	private String cx_departmentcode;
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
	private String ids;
	/*查询条件end*/
	
	//查询用的id
	private Integer personnelId;
	
	private HrPersonnel personnel = new HrPersonnel();
	private HrPact pact = new HrPact();
	
	private List personnelList;
	private List department;
	List<SerClientMaintain> serClientMainList;//客户报修信息列表
	private FormFile myfile;
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	private Integer totalcount;//总记录数
	
	private Integer lpId;
	//登录用户名
	private String userName;
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
	public String getCx_departmentcode() {
		return cx_departmentcode;
	}
	public void setCx_departmentcode(String cx_departmentcode) {
		this.cx_departmentcode = cx_departmentcode;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(Integer personnelId) {
		this.personnelId = personnelId;
	}
	public HrPersonnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(HrPersonnel personnel) {
		this.personnel = personnel;
	}
	public HrPact getPact() {
		return pact;
	}
	public void setPact(HrPact pact) {
		this.pact = pact;
	}
	public List getPersonnelList() {
		return personnelList;
	}
	public void setPersonnelList(List personnelList) {
		this.personnelList = personnelList;
	}
	public List getDepartment() {
		return department;
	}
	public void setDepartment(List department) {
		this.department = department;
	}
	public FormFile getMyfile() {
		return myfile;
	}
	public void setMyfile(FormFile myfile) {
		this.myfile = myfile;
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
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<SerClientMaintain> getSerClientMainList() {
		return serClientMainList;
	}
	public void setSerClientMainList(List<SerClientMaintain> serClientMainList) {
		this.serClientMainList = serClientMainList;
	}
	
}
