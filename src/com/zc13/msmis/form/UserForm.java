package com.zc13.msmis.form;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zc13.bkmis.form.BasicForm;

/**
 * 用户查询
 * @author qinyantao
 * Date：Nov 7, 2010
 * Time：11:49:46 AM
 */
@SuppressWarnings("serial")
public class UserForm extends BasicForm {
	
	private Integer userid;
	private String username;
	private String oldPassword;
	private String password;
	private String department;
	private String enabled;
	private String description;
	private String rootuser;
	private String realName;
	private String phone;
	private Integer range;
	private String address;
	private String identityCard;
	private Set MUserRoles = new HashSet(0);
	private List lpList;
	private List userList;
	private int totalcount;

	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}
	public List getLpList() {
		return lpList;
	}
	public void setLpList(List lpList) {
		this.lpList = lpList;
	}
	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRootuser() {
		return this.rootuser;
	}

	public void setRootuser(String rootuser) {
		this.rootuser = rootuser;
	}

	public Set getMUserRoles() {
		return this.MUserRoles;
	}

	public void setMUserRoles(Set MUserRoles) {
		this.MUserRoles = MUserRoles;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public List getUserList() {
		return userList;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	
}