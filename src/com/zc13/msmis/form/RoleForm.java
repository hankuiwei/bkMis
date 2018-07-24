package com.zc13.msmis.form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zc13.bkmis.form.BasicForm;
/**
 * 
 * @author 侯哓娟
 * Date：Nov 10, 2010
 * Time：4:06:03 PM
 */
public class RoleForm extends BasicForm {

	private Integer roleid;
	private String rolename;
	private String roledesc;
	private Integer enabled;
	private Date updatetime;
	private Integer range;
	private Set MRolePerms = new HashSet(0);
	private Set MUserRoles = new HashSet(0);
	

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRoledesc() {
		return roledesc;
	}
	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}
	public Set getMRolePerms() {
		return MRolePerms;
	}
	public void setMRolePerms(Set rolePerms) {
		MRolePerms = rolePerms;
	}
	public Set getMUserRoles() {
		return MUserRoles;
	}
	public void setMUserRoles(Set userRoles) {
		MUserRoles = userRoles;
	}
	
}
