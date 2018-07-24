package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * 意向书
 * CompactIntention entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CompactIntention implements java.io.Serializable {

	// Fields

	private Integer id;
	private String intentionCode;
	private String code;
	private Integer clientId;
	private String signDate;
	private String beginDate;
	private String endDate;
	private String roomCodes;
	private double totalArea;
	private String circle;
	private double rentDeposit;
	private double decorationDeposit;
	private String man;
	private String date;
	private String instruction;
	private String status;
	private String name;
	private String clientType;
	private String country;
	private String nation;
	private String residence;
	private String identityCard;
	private String linkMan;
	private String phone;
	private String unitName;
	private String companyType;
	private String trade;
	private String operation;
	private String fundType;
	private String corporation;
	private String fax;
	private String taxNo;
	private String rentNo;
	private String loginFund;
	private String loginDate;
	private String isHighTech;
	private double earnest;
	private String isEarnest;
	private String isConvertCompact;
	private String isEnabled;
	private Set intentionRoomCoststandards = new HashSet(0);
	private Integer lpId;
	private Integer rootUser;

	public CompactIntention(Integer id) {
		super();
		this.id = id;
	}

	public CompactIntention(Integer id, String intentionCode, String code, Integer clientId, Integer lpId, String signDate, String beginDate, String endDate, String roomCodes, double totalArea, String circle, double rentDeposit, double decorationDeposit, String man, String date, String instruction, String status, String name, String clientType, String country, String nation, String residence, String identityCard, String linkMan, String phone, String unitName, String companyType, String trade,
			String operation, String fundType, String corporation, String fax, String taxNo, String rentNo, String loginFund, String loginDate, String isHighTech, double earnest, String isEarnest, String isConvertCompact, String isEnabled, Integer rootUser, Set intentionRoomCoststandards) {
		super();
		this.id = id;
		this.intentionCode = intentionCode;
		this.code = code;
		this.clientId = clientId;
		this.lpId = lpId;
		this.signDate = signDate;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.roomCodes = roomCodes;
		this.totalArea = totalArea;
		this.circle = circle;
		this.rentDeposit = rentDeposit;
		this.decorationDeposit = decorationDeposit;
		this.man = man;
		this.date = date;
		this.instruction = instruction;
		this.status = status;
		this.name = name;
		this.clientType = clientType;
		this.country = country;
		this.nation = nation;
		this.residence = residence;
		this.identityCard = identityCard;
		this.linkMan = linkMan;
		this.phone = phone;
		this.unitName = unitName;
		this.companyType = companyType;
		this.trade = trade;
		this.operation = operation;
		this.fundType = fundType;
		this.corporation = corporation;
		this.fax = fax;
		this.taxNo = taxNo;
		this.rentNo = rentNo;
		this.loginFund = loginFund;
		this.loginDate = loginDate;
		this.isHighTech = isHighTech;
		this.earnest = earnest;
		this.isEarnest = isEarnest;
		this.isConvertCompact = isConvertCompact;
		this.isEnabled = isEnabled;
		this.rootUser = rootUser;
		this.intentionRoomCoststandards = intentionRoomCoststandards;
	}

	/** default constructor */
	public CompactIntention() {
	}

	// Property accessors

	public String getIsConvertCompact() {
		return isConvertCompact;
	}

	public void setIsConvertCompact(String isConvertCompact) {
		this.isConvertCompact = isConvertCompact;
	}

	public String getRentNo() {
		return rentNo;
	}

	public void setRentNo(String rentNo) {
		this.rentNo = rentNo;
	}

	public String getIntentionCode() {
		return intentionCode;
	}

	public void setIntentionCode(String intentionCode) {
		this.intentionCode = intentionCode;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getLpId() {
		return this.lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public String getSignDate() {
		return this.signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRoomCodes() {
		return this.roomCodes;
	}

	public void setRoomCodes(String roomCodes) {
		this.roomCodes = roomCodes;
	}

	public double getTotalArea() {
		return this.totalArea;
	}

	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}

	public String getCircle() {
		return this.circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public double getRentDeposit() {
		return this.rentDeposit;
	}

	public void setRentDeposit(double rentDeposit) {
		this.rentDeposit = rentDeposit;
	}

	public double getDecorationDeposit() {
		return this.decorationDeposit;
	}

	public void setDecorationDeposit(double decorationDeposit) {
		this.decorationDeposit = decorationDeposit;
	}

	public String getMan() {
		return this.man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getTrade() {
		return this.trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getFundType() {
		return this.fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getCorporation() {
		return this.corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getLoginFund() {
		return this.loginFund;
	}

	public void setLoginFund(String loginFund) {
		this.loginFund = loginFund;
	}

	public String getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getIsHighTech() {
		return this.isHighTech;
	}

	public void setIsHighTech(String isHighTech) {
		this.isHighTech = isHighTech;
	}

	public double getEarnest() {
		return this.earnest;
	}

	public void setEarnest(double earnest) {
		this.earnest = earnest;
	}

	public String getIsEarnest() {
		return this.isEarnest;
	}

	public void setIsEarnest(String isEarnest) {
		this.isEarnest = isEarnest;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Set getIntentionRoomCoststandards() {
		return this.intentionRoomCoststandards;
	}

	public void setIntentionRoomCoststandards(Set intentionRoomCoststandards) {
		this.intentionRoomCoststandards = intentionRoomCoststandards;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

}