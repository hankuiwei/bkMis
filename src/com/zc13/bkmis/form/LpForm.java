package com.zc13.bkmis.form;
import org.apache.struts.upload.FormFile;
/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:54:09 PM
 */
public class LpForm extends BasicForm {

	private Integer lpId;//楼盘id
	private String lpCode;
	private String lpName;//楼盘名称
	private String ppType;//物业类型
	private String regional;//所属区域
	private String developCompany;//开发单位
	private String designCompany;//设计单位
	private String buildCompany;//建设单位
	private float constructionArea;//占地面积
	private float useArea;//使用面积
	private float virescenceArea;//绿化面积
	private String beginDate;//开工日期
	private String endDate;//竣工日期
	private String useDate;//进驻日期
	private String phone;//联系电话
	private String fax;//传真
	private String address;//地址
	private String mapUrl;//效果图地址
	private Integer rootUser;//根用户
	//文件上传
	private FormFile file;
    /* 用于楼盘添加表具 */
	private String code;
	private String type;

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getLpCode() {
		return lpCode;
	}
	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}
	public String getLpName() {
		return lpName;
	}
	public void setLpName(String lpName) {
		this.lpName = lpName;
	}
	public String getPpType() {
		return ppType;
	}
	public void setPpType(String ppType) {
		this.ppType = ppType;
	}
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getDevelopCompany() {
		return developCompany;
	}
	public void setDevelopCompany(String developCompany) {
		this.developCompany = developCompany;
	}
	public String getDesignCompany() {
		return designCompany;
	}
	public void setDesignCompany(String designCompany) {
		this.designCompany = designCompany;
	}
	public String getBuildCompany() {
		return buildCompany;
	}
	public void setBuildCompany(String buildCompany) {
		this.buildCompany = buildCompany;
	}
	public float getConstructionArea() {
		return constructionArea;
	}
	public void setConstructionArea(float constructionArea) {
		this.constructionArea = constructionArea;
	}
	public float getUseArea() {
		return useArea;
	}
	public void setUseArea(float useArea) {
		this.useArea = useArea;
	}
	public float getVirescenceArea() {
		return virescenceArea;
	}
	public void setVirescenceArea(float virescenceArea) {
		this.virescenceArea = virescenceArea;
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
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public Integer getRootUser() {
		return rootUser;
	}
	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
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
   
}
