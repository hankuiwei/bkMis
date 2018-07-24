package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.CPhoneParameter;
import com.zc13.bkmis.mapping.CRegionCode;
import com.zc13.bkmis.mapping.CServiceProvider;
import com.zc13.bkmis.mapping.ECallInfo;

public class PhoneCostForm extends BasicForm{
	//查询有关
	private Integer id;
	private String cxRoomCode;
	private String cxClientName;
	private String cxIsPaid;
	private String cxStartDate;
	private String cxEndDate;
	private String cxPhoneNumber;
	private String cxPrefix;//被叫字头
	private String cxAreaCode;//区号
	private String cxAreaName;//地区名称
	private Integer[] checkbox;// 保存需要删除的记录id
	//每部电话在指定时间段内产生的总通话时长和总费用列表
	private List phoneCostList;
	//电话明细列表
	private List phoneCallInfoList;
	//运营商信息列表
	private List serviceProviderList;
	//电话资费参数列表
	private List phoneParameterList;
	//区号列表
	private List regionCodeList;
	
	private ECallInfo callInfo = new ECallInfo();
	private CServiceProvider provider = new CServiceProvider();
	private CPhoneParameter phoneParameter = new CPhoneParameter();
	private CRegionCode regionCode = new CRegionCode();
	private int totalcount;
	private double totalCost;//话费总计

	private String clientId;
	private String itemId;
	public String getCxRoomCode() {
		return cxRoomCode;
	}

	public void setCxRoomCode(String cxRoomCode) {
		this.cxRoomCode = cxRoomCode;
	}

	public String getCxClientName() {
		return cxClientName;
	}

	public void setCxClientName(String cxClientName) {
		this.cxClientName = cxClientName;
	}

	public String getCxStartDate() {
		return cxStartDate;
	}

	public void setCxStartDate(String cxStartDate) {
		this.cxStartDate = cxStartDate;
	}

	public String getCxEndDate() {
		return cxEndDate;
	}

	public void setCxEndDate(String cxEndDate) {
		this.cxEndDate = cxEndDate;
	}

	public List getPhoneCostList() {
		return phoneCostList;
	}

	public void setPhoneCostList(List phoneCostList) {
		this.phoneCostList = phoneCostList;
	}

	public ECallInfo getCallInfo() {
		return callInfo;
	}

	public void setCallInfo(ECallInfo callInfo) {
		this.callInfo = callInfo;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String getCxPhoneNumber() {
		return cxPhoneNumber;
	}

	public void setCxPhoneNumber(String cxPhoneNumber) {
		this.cxPhoneNumber = cxPhoneNumber;
	}

	public List getPhoneCallInfoList() {
		return phoneCallInfoList;
	}

	public void setPhoneCallInfoList(List phoneCallInfoList) {
		this.phoneCallInfoList = phoneCallInfoList;
	}

	public List getServiceProviderList() {
		return serviceProviderList;
	}

	public void setServiceProviderList(List serviceProviderList) {
		this.serviceProviderList = serviceProviderList;
	}

	public CServiceProvider getProvider() {
		return provider;
	}

	public void setProvider(CServiceProvider provider) {
		this.provider = provider;
	}

	public List getPhoneParameterList() {
		return phoneParameterList;
	}

	public void setPhoneParameterList(List phoneParameterList) {
		this.phoneParameterList = phoneParameterList;
	}

	public String getCxPrefix() {
		return cxPrefix;
	}

	public void setCxPrefix(String cxPrefix) {
		this.cxPrefix = cxPrefix;
	}

	public CPhoneParameter getPhoneParameter() {
		return phoneParameter;
	}

	public void setPhoneParameter(CPhoneParameter phoneParameter) {
		this.phoneParameter = phoneParameter;
	}

	public Integer[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}

	public String getCxIsPaid() {
		return cxIsPaid;
	}

	public void setCxIsPaid(String cxIsPaid) {
		this.cxIsPaid = cxIsPaid;
	}

	public CRegionCode getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(CRegionCode regionCode) {
		this.regionCode = regionCode;
	}

	public List getRegionCodeList() {
		return regionCodeList;
	}

	public void setRegionCodeList(List regionCodeList) {
		this.regionCodeList = regionCodeList;
	}

	public String getCxAreaCode() {
		return cxAreaCode;
	}

	public void setCxAreaCode(String cxAreaCode) {
		this.cxAreaCode = cxAreaCode;
	}

	public String getCxAreaName() {
		return cxAreaName;
	}

	public void setCxAreaName(String cxAreaName) {
		this.cxAreaName = cxAreaName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
