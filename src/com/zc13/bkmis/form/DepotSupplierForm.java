package com.zc13.bkmis.form;

import java.util.List;

public class DepotSupplierForm extends BasicForm {

	//供应商信息
	private Integer id;
	private String name;
	private String fullName;
	private String linkMan;
	private String phone;
	//存储供应商信息
	private List supplierList;
	//存储要修改员工信息
	private List editSuplierList;
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List supplierList) {
		this.supplierList = supplierList;
	}
	public List getEditSuplierList() {
		return editSuplierList;
	}
	public void setEditSuplierList(List editSuplierList) {
		this.editSuplierList = editSuplierList;
	}
	public void setLpId(Integer lpId) {
		// TODO Auto-generated method stub
		
	}

}
