package com.zc13.bkmis.mapping;

/**
 * HrPersonnel generated by MyEclipse Persistence Tools
 */

public class HrPersonnel implements java.io.Serializable {

	// Fields

	private Integer personnelId;
	private String personnelCode;
	private String personnelNum;
	private String personnelName;
	private String departmentCode;
	private String sex;
	private String nation;
	private Integer age;
	private String birthday;
	private String identityCard;
	private String phone;
	private String groups;
	private String health;
	private String marriage;
	private String bloodType;
	private String interest;
	private String imageUrl;
	private String academicCertificate;
	private String speciality;
	private String residence;
	private String address;
	private String bz;
	private String foreignLanguage;
	private String status;
	private String isDispatch;
	private String isInPost;
	private String isOut;
	private String post;
	private String labourInsurance;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	public HrPersonnel(Integer personnelId, String personnelCode, String personnelNum, String personnelName, String departmentCode, String sex, String nation, Integer age, String birthday, String identityCard, String phone, String groups, String health, String marriage, String bloodType, String interest, String imageUrl, String academicCertificate, String speciality, String residence, String address, String bz, String foreignLanguage, String status, String isDispatch, String isInPost,
			String isOut, String post, String labourInsurance, Integer lpId, Integer rootUser) {
		super();
		this.personnelId = personnelId;
		this.personnelCode = personnelCode;
		this.personnelNum = personnelNum;
		this.personnelName = personnelName;
		this.departmentCode = departmentCode;
		this.sex = sex;
		this.nation = nation;
		this.age = age;
		this.birthday = birthday;
		this.identityCard = identityCard;
		this.phone = phone;
		this.groups = groups;
		this.health = health;
		this.marriage = marriage;
		this.bloodType = bloodType;
		this.interest = interest;
		this.imageUrl = imageUrl;
		this.academicCertificate = academicCertificate;
		this.speciality = speciality;
		this.residence = residence;
		this.address = address;
		this.bz = bz;
		this.foreignLanguage = foreignLanguage;
		this.status = status;
		this.isDispatch = isDispatch;
		this.isInPost = isInPost;
		this.isOut = isOut;
		this.post = post;
		this.labourInsurance = labourInsurance;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	/** default constructor */
	public HrPersonnel() {
	}

	// Property accessors

	public HrPersonnel(Integer personnelId) {
		super();
		this.personnelId = personnelId;
	}

	public String getPersonnelNum() {
		return personnelNum;
	}

	public void setPersonnelNum(String personnelNum) {
		this.personnelNum = personnelNum;
	}

	public String getIsDispatch() {
		return isDispatch;
	}

	public void setIsDispatch(String isDispatch) {
		this.isDispatch = isDispatch;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getPersonnelId() {
		return this.personnelId;
	}

	public void setPersonnelId(Integer personnelId) {
		this.personnelId = personnelId;
	}

	public String getPersonnelCode() {
		return this.personnelCode;
	}

	public void setPersonnelCode(String personnelCode) {
		this.personnelCode = personnelCode;
	}

	public String getPersonnelName() {
		return this.personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getDepartmentCode() {
		return this.departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGroups() {
		return this.groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getBloodType() {
		return this.bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAcademicCertificate() {
		return this.academicCertificate;
	}

	public void setAcademicCertificate(String academicCertificate) {
		this.academicCertificate = academicCertificate;
	}

	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public String getForeignLanguage() {
		return foreignLanguage;
	}

	public void setForeignLanguage(String foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getLabourInsurance() {
		return labourInsurance;
	}

	public void setLabourInsurance(String labourInsurance) {
		this.labourInsurance = labourInsurance;
	}

	public String getIsInPost() {
		return isInPost;
	}

	public void setIsInPost(String isInPost) {
		this.isInPost = isInPost;
	}

	public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

}