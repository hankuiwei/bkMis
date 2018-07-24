package com.zc13.bkmis.form;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts.upload.FormFile;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：5:23:46 PM
 */
public class BuildForm extends BasicForm {

	private Integer buildId;
	private Integer lpId;
	private String buildCode;
	private String buildName;
	private float useArea;
	private String floor;
	private String block;
	private String purpose;
	private String structure;
	private String beginDate;
	private String endDate;
	private Integer indexs;
	private Integer rootUser;
	/*文件上传*/
	private FormFile file;
	
	/** 楼层平面展示 */
	private String mapUrl;
	/**楼盘或楼栋id*/
	private Integer id;
	/**表名，是楼盘还是楼栋*/
	private String tablename;
	
	/* 用于楼栋添加表具 */
	private String code;
	private String type;
	private Integer parentId;
	
	//登录用户名
	private String userName;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private Set ERoomses = new HashSet(0);
	public Integer getBuildId() {
		return buildId;
	}
	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public float getUseArea() {
		return useArea;
	}
	public void setUseArea(float useArea) {
		this.useArea = useArea;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
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
	public Integer getIndexs() {
		return indexs;
	}
	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
	public Integer getRootUser() {
		return rootUser;
	}
	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}
	public Set getERoomses() {
		return ERoomses;
	}
	public void setERoomses(Set roomses) {
		ERoomses = roomses;
	}

	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	

}
