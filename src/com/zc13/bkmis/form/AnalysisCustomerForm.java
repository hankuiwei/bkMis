package com.zc13.bkmis.form;

import java.util.List;

public class AnalysisCustomerForm extends BasicForm {

	//客户信息分析字段信息
	private Integer id;
	private String createDate;
	private String beginDate;
	private String endDate;
	private Integer inamount;
	private Integer outamount;
	private String type;
	private String selectValue;
	private Integer rootUser;
	//存储查询出的客户信息列表
	private List analysisCusList;
	//客户详细信息查询条件
	private String paramType;

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public List getAnalysisCusList() {
		return analysisCusList;
	}

	public void setAnalysisCusList(List analysisCusList) {
		this.analysisCusList = analysisCusList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getInamount() {
		return inamount;
	}

	public void setInamount(Integer inamount) {
		this.inamount = inamount;
	}

	public Integer getOutamount() {
		return outamount;
	}

	public void setOutamount(Integer outamount) {
		this.outamount = outamount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}
}
