package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;

public class DestineForm extends BasicForm{
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	public String clientName;//客户名称
	public String roomCode;//房间编号
	public String beginDate;//开始日期
	public String endDate;//结束日期
	public Integer lpId;//楼盘id
	public String status;//状态
	public Float earnest;//定金
	public String isEarnest;//是否缴纳定金
	
	public List destineList;//所有的预定合同列表
	public List<ELp> lpList;//所有楼盘列表
	
	private Integer id;
	private String compactCode;
	private String compactCodeOld;
	private String type;
	private String roomCodes;
	private Integer clientId;
	private String totalArea;
	private String signDate;
	private Double rentDeposit;//房租押金
	private Double decorationDeposit;//装修押金
	private Integer intentionId;//意向书id
	private String man;
	private String date;
	private String instruction;
    private String applyDate;
    private String bz;
    private Integer compactId;
    private String circle;
    private String isHighTech;
    private String loginDate;
    private String loginFund;
	private String loginDateEnd;
	private String loginFundEnd;
	
	//房间id
	private Integer[] clientRoomId;
	
	//客户属性
	private String code;
	private String name;
	private String unitName;
	private String linkMan;
	private String phone;
	private String clientType;
	private String country;
	private String nation;
	private String residence;
	private String identityCard;
	private String fax;
	private String trade;
	private String fundType;
	private String companyType;
	private String corporation;
	private String operation;
	private String taxNo;
	private String rentNo;
	
	//租金自定义
	private Integer pactId;
	private float[] rent;
	private String[] beginDateCost;
	private String[] endDateCost;
	
	//收费信息
	private Integer[] costStandartId;
	private Integer[] roomId;
	private Integer[] costTypeId;
	private String[] beginDateStand;
	private String[] endDateStand;
	private Integer[] amount;
	//private String[] rebate;
	
	//编辑信息
	private Compact compact;
	private CompactClient client;
	private List<CompactRent> rents;
	private List<ERooms> eroomList;
	private List<CompactRoomCoststandard> standardList;
	
	
	public String getRentNo() {
		return rentNo;
	}
	public void setRentNo(String rentNo) {
		this.rentNo = rentNo;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
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
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public List getDestineList() {
		return destineList;
	}
	public void setDestineList(List destineList) {
		this.destineList = destineList;
	}
	public List<ELp> getLpList() {
		return lpList;
	}
	public void setLpList(List<ELp> lpList) {
		this.lpList = lpList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompactCode() {
		return compactCode;
	}
	public void setCompactCode(String compactCode) {
		this.compactCode = compactCode;
	}
	public String getCompactCodeOld() {
		return compactCodeOld;
	}
	public void setCompactCodeOld(String compactCodeOld) {
		this.compactCodeOld = compactCodeOld;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRoomCodes() {
		return roomCodes;
	}
	public void setRoomCodes(String roomCodes) {
		this.roomCodes = roomCodes;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getCompactId() {
		return compactId;
	}
	public void setCompactId(Integer compactId) {
		this.compactId = compactId;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getIsHighTech() {
		return isHighTech;
	}
	public void setIsHighTech(String isHighTech) {
		this.isHighTech = isHighTech;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLoginFund() {
		return loginFund;
	}
	public void setLoginFund(String loginFund) {
		this.loginFund = loginFund;
	}
	public String getLoginDateEnd() {
		return loginDateEnd;
	}
	public void setLoginDateEnd(String loginDateEnd) {
		this.loginDateEnd = loginDateEnd;
	}
	public String getLoginFundEnd() {
		return loginFundEnd;
	}
	public void setLoginFundEnd(String loginFundEnd) {
		this.loginFundEnd = loginFundEnd;
	}
	public Integer[] getClientRoomId() {
		return clientRoomId;
	}
	public void setClientRoomId(Integer[] clientRoomId) {
		this.clientRoomId = clientRoomId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public Integer getPactId() {
		return pactId;
	}
	public void setPactId(Integer pactId) {
		this.pactId = pactId;
	}
	public float[] getRent() {
		return rent;
	}
	public void setRent(float[] rent) {
		this.rent = rent;
	}
	public String[] getBeginDateCost() {
		return beginDateCost;
	}
	public void setBeginDateCost(String[] beginDateCost) {
		this.beginDateCost = beginDateCost;
	}
	public String[] getEndDateCost() {
		return endDateCost;
	}
	public void setEndDateCost(String[] endDateCost) {
		this.endDateCost = endDateCost;
	}
	public Integer[] getCostStandartId() {
		return costStandartId;
	}
	public void setCostStandartId(Integer[] costStandartId) {
		this.costStandartId = costStandartId;
	}
	public Integer[] getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer[] roomId) {
		this.roomId = roomId;
	}
	public Integer[] getCostTypeId() {
		return costTypeId;
	}
	public void setCostTypeId(Integer[] costTypeId) {
		this.costTypeId = costTypeId;
	}
	public String[] getBeginDateStand() {
		return beginDateStand;
	}
	public void setBeginDateStand(String[] beginDateStand) {
		this.beginDateStand = beginDateStand;
	}
	public String[] getEndDateStand() {
		return endDateStand;
	}
	public void setEndDateStand(String[] endDateStand) {
		this.endDateStand = endDateStand;
	}
//	public String[] getRebate() {
//		return rebate;
//	}
//	public void setRebate(String[] rebate) {
//		this.rebate = rebate;
//	}
	public Compact getCompact() {
		return compact;
	}
	public void setCompact(Compact compact) {
		this.compact = compact;
	}
	public CompactClient getClient() {
		return client;
	}
	public void setClient(CompactClient client) {
		this.client = client;
	}
	public List<CompactRent> getRents() {
		return rents;
	}
	public void setRents(List<CompactRent> rents) {
		this.rents = rents;
	}
	public List<ERooms> getEroomList() {
		return eroomList;
	}
	public void setEroomList(List<ERooms> eroomList) {
		this.eroomList = eroomList;
	}
	public List<CompactRoomCoststandard> getStandardList() {
		return standardList;
	}
	public void setStandardList(List<CompactRoomCoststandard> standardList) {
		this.standardList = standardList;
	}
	public Float getEarnest() {
		return earnest;
	}
	public void setEarnest(Float earnest) {
		this.earnest = earnest;
	}
	public String getIsEarnest() {
		return isEarnest;
	}
	public void setIsEarnest(String isEarnest) {
		this.isEarnest = isEarnest;
	}
	public Double getRentDeposit() {
		return rentDeposit;
	}
	public void setRentDeposit(Double rentDeposit) {
		this.rentDeposit = rentDeposit;
	}
	public Double getDecorationDeposit() {
		return decorationDeposit;
	}
	public void setDecorationDeposit(Double decorationDeposit) {
		this.decorationDeposit = decorationDeposit;
	}
	public Integer getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(Integer intentionId) {
		this.intentionId = intentionId;
	}
	public Integer[] getAmount() {
		return amount;
	}
	public void setAmount(Integer[] amount) {
		this.amount = amount;
	}
	
	
}
