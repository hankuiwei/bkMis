package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.IntentionRent;
import com.zc13.bkmis.mapping.IntentionRoomCoststandard;

public class IntentionForm extends BasicForm{
	/*查询条件*/
	private Integer lpId;//楼盘id
	private String c_clientName;//客户名称
	private String c_roomCode;//房间号
	private String c_status;//审批状态
	private String c_isEarnest;//是否缴纳定金
	private String c_isConvertCompact;//是否已转为合同
	private String awokeTaskFlag;//代办提醒
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	
	
	/*查看详细，删除，编辑*/
	private String id;//意向书id
	
	/*保存意向书的信息*/
	private CompactIntention compactIntention = new CompactIntention();//意向书对象
	private CompactClient compactClient = new CompactClient();//客户对象
	
	/*意向书相关信息*/
	private Integer[] clientRoomId;//房间id
	//租金自定义
	private double[] rent;
	private String[] beginDateCost;
	private String[] endDateCost;
	//收费信息
	private Integer[] costStandartId;
	private Integer[] roomId;
	private Integer[] costTypeId;
	private String[] beginDateStand;
	private String[] endDateStand;
	private Integer[] amount;
	
	/*其他的信息*/
	private List<ELp> lpList;//楼盘列表信息
	private List<CompactIntention> intentionList;//意向书列表信息
	
	private List<IntentionRent> rentList;//意向书对应房租信息
	private List<IntentionRoomCoststandard> standardList;//意向书对应收费标准信息
	private List<ERooms> roomList;//意向书对应房间信息
	
	private String userName;//登陆用户名
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getC_isConvertCompact() {
		return c_isConvertCompact;
	}
	public void setC_isConvertCompact(String convertCompact) {
		c_isConvertCompact = convertCompact;
	}
	public List<IntentionRent> getRentList() {
		return rentList;
	}
	public void setRentList(List<IntentionRent> rentList) {
		this.rentList = rentList;
	}
	public List<IntentionRoomCoststandard> getStandardList() {
		return standardList;
	}
	public void setStandardList(List<IntentionRoomCoststandard> standardList) {
		this.standardList = standardList;
	}
	
	public List<ERooms> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<ERooms> roomList) {
		this.roomList = roomList;
	}
	public CompactClient getCompactClient() {
		return compactClient;
	}
	public void setCompactClient(CompactClient compactClient) {
		this.compactClient = compactClient;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getC_clientName() {
		return c_clientName;
	}
	public void setC_clientName(String name) {
		c_clientName = name;
	}
	public String getC_roomCode() {
		return c_roomCode;
	}
	public void setC_roomCode(String code) {
		c_roomCode = code;
	}
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	public String getC_isEarnest() {
		return c_isEarnest;
	}
	public void setC_isEarnest(String earnest) {
		c_isEarnest = earnest;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CompactIntention getCompactIntention() {
		return compactIntention;
	}
	public void setCompactIntention(CompactIntention compactIntention) {
		this.compactIntention = compactIntention;
	}
	public List<ELp> getLpList() {
		return lpList;
	}
	public void setLpList(List<ELp> lpList) {
		this.lpList = lpList;
	}
	public List<CompactIntention> getIntentionList() {
		return intentionList;
	}
	public void setIntentionList(List<CompactIntention> intentionList) {
		this.intentionList = intentionList;
	}
	public Integer[] getClientRoomId() {
		return clientRoomId;
	}
	public void setClientRoomId(Integer[] clientRoomId) {
		this.clientRoomId = clientRoomId;
	}
	public double[] getRent() {
		return rent;
	}
	public void setRent(double[] rent) {
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
	public Integer[] getAmount() {
		return amount;
	}
	public void setAmount(Integer[] amount) {
		this.amount = amount;
	}
	public String getAwokeTaskFlag() {
		return awokeTaskFlag;
	}
	public void setAwokeTaskFlag(String awokeTaskFlag) {
		this.awokeTaskFlag = awokeTaskFlag;
	}
	
	
}
