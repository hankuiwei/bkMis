package com.zc13.bkmis.form;


public class RoomForm extends BasicForm {

	private String roomCode;
	private String floor;
	private String roomType;
	
	/** 用于房间添加,修改时显示列表信息 */
	private float useArea;
	private Integer buildId;
	private Integer lpId;
	private String tablename;
	
	private float suseArea;
	private float guseArea;
	private String toward;
	private String status;
	private Integer roomId;
	private String roomFullName;
	private String unitId;
	private float constructionArea;
	private String regional;
	private String bz;
	private String phoneNumber;
	private Integer rootUser;
	
	/** 批量增加 */
	private String croomCode; //批量增加的房间非数字列编号
	private Integer beginRoomCode;
	private Integer endRoomCode;
	
	/** 分页信息 **/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	
	/** 用于房间添加表具 */
	private String code;
	private String type;
	private Integer parentId;
	
	/** 用于房间添加设备 */
	private Integer fixtureId;
	private Integer amount;
	private String ebz;
	

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public float getUseArea() {
		return useArea;
	}
	public void setUseArea(float useArea) {
		this.useArea = useArea;
	}
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
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public float getSuseArea() {
		return suseArea;
	}
	public void setSuseArea(float suseArea) {
		this.suseArea = suseArea;
	}
	public float getGuseArea() {
		return guseArea;
	}
	public void setGuseArea(float guseArea) {
		this.guseArea = guseArea;
	}
	public String getToward() {
		return toward;
	}
	public void setToward(String toward) {
		this.toward = toward;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomFullName() {
		return roomFullName;
	}
	public void setRoomFullName(String roomFullName) {
		this.roomFullName = roomFullName;
	}
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public float getConstructionArea() {
		return constructionArea;
	}
	public void setConstructionArea(float constructionArea) {
		this.constructionArea = constructionArea;
	}
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getRootUser() {
		return rootUser;
	}
	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}
	
	/** 批量增加 */
	public String getCroomCode() {
		return croomCode;
	}
	public void setCroomCode(String croomCode) {
		this.croomCode = croomCode;
	}
	public Integer getBeginRoomCode() {
		return beginRoomCode;
	}
	public void setBeginRoomCode(Integer beginRoomCode) {
		this.beginRoomCode = beginRoomCode;
	}
	public Integer getEndRoomCode() {
		return endRoomCode;
	}
	public void setEndRoomCode(Integer endRoomCode) {
		this.endRoomCode = endRoomCode;
	}
	
	/** 分页信息 **/
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
	public Integer getFixtureId() {
		return fixtureId;
	}
	public void setFixtureId(Integer fixtureId) {
		this.fixtureId = fixtureId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getEbz() {
		return ebz;
	}
	public void setEbz(String ebz) {
		this.ebz = ebz;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
