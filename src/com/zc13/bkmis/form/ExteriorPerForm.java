package com.zc13.bkmis.form;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class ExteriorPerForm extends BasicForm {
	//留学人员信息
	private int id;
	private String name;
	private String sex;
	private String natioanality;
	private String birtyday;
	private int age;
	private String nativePalace;
	private String foreignDegree;
	private String nativeDegree;
	private String identityCard;
	private String phone;
	private String currentCountry;
	private String researchTopic;
	private String joincompanyType;
	private String company;
	private String imageUrl;
	private String researchResult;
	private String researchField;
	private String getRewart;
	private String mailbox;
	private String resolveProblem;
	private String post;
	private String homecomingDisposition;
	private String address;
	private String enjoyPolicy;
	//对工作单位下拉列表内容获取
	public List workList;
	//留学人员信息
	public List exteriorperList;
	//上传图片
	private FormFile myfile;
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
	public FormFile getMyfile() {
		return myfile;
	}
	public void setMyfile(FormFile myfile) {
		this.myfile = myfile;
	}
	public List getExteriorperList() {
		return exteriorperList;
	}
	public void setExteriorperList(List exteriorperList) {
		this.exteriorperList = exteriorperList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNatioanality() {
		return natioanality;
	}
	public void setNatioanality(String natioanality) {
		this.natioanality = natioanality;
	}
	public String getBirtyday() {
		return birtyday;
	}
	public void setBirtyday(String birtyday) {
		this.birtyday = birtyday;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNativePalace() {
		return nativePalace;
	}
	public void setNativePalace(String nativePalace) {
		this.nativePalace = nativePalace;
	}
	public String getForeignDegree() {
		return foreignDegree;
	}
	public void setForeignDegree(String foreignDegree) {
		this.foreignDegree = foreignDegree;
	}
	public String getNativeDegree() {
		return nativeDegree;
	}
	public void setNativeDegree(String nativeDegree) {
		this.nativeDegree = nativeDegree;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCurrentCountry() {
		return currentCountry;
	}
	public void setCurrentCountry(String currentCountry) {
		this.currentCountry = currentCountry;
	}
	public String getResearchTopic() {
		return researchTopic;
	}
	public void setResearchTopic(String researchTopic) {
		this.researchTopic = researchTopic;
	}
	public String getJoincompanyType() {
		return joincompanyType;
	}
	public void setJoincompanyType(String joincompanyType) {
		this.joincompanyType = joincompanyType;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getResearchResult() {
		return researchResult;
	}
	public void setResearchResult(String researchResult) {
		this.researchResult = researchResult;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	public String getGetRewart() {
		return getRewart;
	}
	public void setGetRewart(String getRewart) {
		this.getRewart = getRewart;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getResolveProblem() {
		return resolveProblem;
	}
	public void setResolveProblem(String resolveProblem) {
		this.resolveProblem = resolveProblem;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getHomecomingDisposition() {
		return homecomingDisposition;
	}
	public void setHomecomingDisposition(String homecomingDisposition) {
		this.homecomingDisposition = homecomingDisposition;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnjoyPolicy() {
		return enjoyPolicy;
	}
	public void setEnjoyPolicy(String enjoyPolicy) {
		this.enjoyPolicy = enjoyPolicy;
	}
	public List getWorkList() {
		return workList;
	}
	public void setWorkList(List workList) {
		this.workList = workList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
