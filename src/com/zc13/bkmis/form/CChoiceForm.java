/**
 * Administrator
 */
package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.util.PojoArrayList;

/**
 * @author zhaosg
 * Date：Dec 7, 2010
 * Time：2:49:32 PM
 */
public class CChoiceForm extends BasicForm {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 6827320974528136534L;
	private Integer standardId;//收费标准
	private Integer buildId;//楼幢
	private String floor;//楼层
	private String clientName;//客户名称
	private String billType;//账单类型
	private Integer itemId;
	
	private Integer[] checkbox;
	private List<CompactRoomCoststandard> crcList = new PojoArrayList<CompactRoomCoststandard>(CompactRoomCoststandard.class);
	private List<CCoststandard> csList = new PojoArrayList<CCoststandard>(CCoststandard.class);
	private List<CompactClient> clientList = new PojoArrayList<CompactClient>(CompactClient.class);
	private List<ERooms> roomList = new PojoArrayList<ERooms>(ERooms.class);
	private List<Compact> compactList = new PojoArrayList<Compact>(Compact.class);
	private List<CCosttype> costtypeList = new PojoArrayList<CCosttype>(CCosttype.class);
	
	private String ids;//客户id串
	private CBill bill = new CBill();//账单
	private CCoststandard standard = new CCoststandard();//收费标准
	
	//登陆者信息
	private Integer userId;
	private String userName;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * @return the bill
	 */
	public CBill getBill() {
		return bill;
	}
	/**
	 * @param bill the bill to set
	 */
	public void setBill(CBill bill) {
		this.bill = bill;
	}
	/**
	 * @return the standard
	 */
	public CCoststandard getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(CCoststandard standard) {
		this.standard = standard;
	}
	
	/**
	 * @return the standardId
	 */
	public Integer getStandardId() {
		return standardId;
	}
	/**
	 * @param standardId the standardId to set
	 */
	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}
	/**
	 * @return the buildId
	 */
	public Integer getBuildId() {
		return buildId;
	}
	/**
	 * @param buildId the buildId to set
	 */
	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}
	/**
	 * @return the floor
	 */
	public String getFloor() {
		return floor;
	}
	/**
	 * @param floor the floor to set
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}
	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * @return the checkbox
	 */
	public Integer[] getCheckbox() {
		return checkbox;
	}
	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	/**
	 * @return the crcList
	 */
	public List<CompactRoomCoststandard> getCrcList() {
		return crcList;
	}
	/**
	 * @param crcList the crcList to set
	 */
	public void setCrcList(List<CompactRoomCoststandard> crcList) {
		this.crcList = crcList;
	}
	/**
	 * @return the csList
	 */
	public List<CCoststandard> getCsList() {
		return csList;
	}
	/**
	 * @param csList the csList to set
	 */
	public void setCsList(List<CCoststandard> csList) {
		this.csList = csList;
	}
	/**
	 * @return the clientList
	 */
	public List<CompactClient> getClientList() {
		return clientList;
	}
	/**
	 * @param clientList the clientList to set
	 */
	public void setClientList(List<CompactClient> clientList) {
		this.clientList = clientList;
	}
	/**
	 * @return the roomList
	 */
	public List<ERooms> getRoomList() {
		return roomList;
	}
	/**
	 * @param roomList the roomList to set
	 */
	public void setRoomList(List<ERooms> roomList) {
		this.roomList = roomList;
	}
	/**
	 * @return the compactList
	 */
	public List<Compact> getCompactList() {
		return compactList;
	}
	/**
	 * @param compactList the compactList to set
	 */
	public void setCompactList(List<Compact> compactList) {
		this.compactList = compactList;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public List<CCosttype> getCosttypeList() {
		return costtypeList;
	}
	public void setCosttypeList(List<CCosttype> costtypeList) {
		this.costtypeList = costtypeList;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
