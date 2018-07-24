package com.zc13.bkmis.form;

import java.util.List;

public class SetMaterialsForm extends BasicForm {
	//材料信息
	private int id;
	private String code;
	private String name;
	private String type;
	private String spec;
	private String unit;
	private double lowerLimit;
	private double upperLimit;
	private double unitPrice;
	private String bz;
	private double nowStock;
	private double doStock;
	private double money;
	//存储查出来要显示的内容
	private List materialList;
	//存储查询出来的单位
	private List unitList;
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	private int totalcount;//总记录数
	//存储类别信息中的id
	private int dmtId;
	//存储显示名称
	private String showName;
	
	//出库单信息
	private String outputCode;
	private String startDate;
	private String endDate;
	private List outputList;
	
	/***********luq  增加库存查询条件************/
	//查询条件
	private String nowStockStart;//库存查询下限
	private String nowStockEnd;//库存查询上限

	//登录用户名
	private String userName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public double getNowStock() {
		return nowStock;
	}

	public void setNowStock(double nowStock) {
		this.nowStock = nowStock;
	}

	public double getDoStock() {
		return doStock;
	}

	public void setDoStock(double doStock) {
		this.doStock = doStock;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public List getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List materialList) {
		this.materialList = materialList;
	}

	public List getUnitList() {
		return unitList;
	}

	public void setUnitList(List unitList) {
		this.unitList = unitList;
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

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getDmtId() {
		return dmtId;
	}

	public void setDmtId(int dmtId) {
		this.dmtId = dmtId;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List getOutputList() {
		return outputList;
	}

	public void setOutputList(List outputList) {
		this.outputList = outputList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNowStockStart() {
		return nowStockStart;
	}

	public void setNowStockStart(String nowStockStart) {
		this.nowStockStart = nowStockStart;
	}

	public String getNowStockEnd() {
		return nowStockEnd;
	}

	public void setNowStockEnd(String nowStockEnd) {
		this.nowStockEnd = nowStockEnd;
	}

	
}
