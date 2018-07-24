package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * CCoststandard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CCoststandard implements java.io.Serializable {

	// Fields

	private Integer id;
	/**帐套Id*/
	private Integer accountTemplateId;
	/**收费标准代码*/
	private String standardCode;
	/**收费标准名称*/
	private String standardName;
	/**费用类型id*/
	private Integer costTypeId;
	/**收费项id*/
	private Integer itemId;
	/**计算周期*/
	private String computeCycle;
	/**结算周期*/
	private String balanceCycle;
	/**账单类型*/
	private String billType;
	/***/
	private String useGauge;
	/**计算公式*/
	private String formula;
	/**说明*/
	private String explanation;
	/**是否显示*/
	private String display;
	private Set CBills = new HashSet(0);
	private Set compactRoomCoststandards = new HashSet(0);
	private Integer lpId;
	private Integer rootUser;


	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public CCoststandard(Integer id, Integer accountTemplateId, String standardCode, String standardName, Integer costTypeId, Integer itemId, String computeCycle, String balanceCycle, String billType, String useGauge, String formula, String explanation, String display, Set bills, Set compactRoomCoststandards, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.accountTemplateId = accountTemplateId;
		this.standardCode = standardCode;
		this.standardName = standardName;
		this.costTypeId = costTypeId;
		this.itemId = itemId;
		this.computeCycle = computeCycle;
		this.balanceCycle = balanceCycle;
		this.billType = billType;
		this.useGauge = useGauge;
		this.formula = formula;
		this.explanation = explanation;
		this.display = display;
		CBills = bills;
		this.compactRoomCoststandards = compactRoomCoststandards;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public String getBalanceCycle() {
		return balanceCycle;
	}

	public void setBalanceCycle(String balanceCycle) {
		this.balanceCycle = balanceCycle;
	}

	/** default constructor */
	public CCoststandard() {
	}

	/** full constructor */
	

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountTemplateId() {
		return this.accountTemplateId;
	}

	public void setAccountTemplateId(Integer accountTemplateId) {
		this.accountTemplateId = accountTemplateId;
	}

	public String getStandardCode() {
		return this.standardCode;
	}

	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}

	public String getStandardName() {
		return this.standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public Integer getCostTypeId() {
		return this.costTypeId;
	}

	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getComputeCycle() {
		return this.computeCycle;
	}

	public void setComputeCycle(String computeCycle) {
		this.computeCycle = computeCycle;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getUseGauge() {
		return this.useGauge;
	}

	public void setUseGauge(String useGauge) {
		this.useGauge = useGauge;
	}

	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public Set getCBills() {
		return this.CBills;
	}

	public void setCBills(Set CBills) {
		this.CBills = CBills;
	}

	public Set getCompactRoomCoststandards() {
		return this.compactRoomCoststandards;
	}

	public void setCompactRoomCoststandards(Set compactRoomCoststandards) {
		this.compactRoomCoststandards = compactRoomCoststandards;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}