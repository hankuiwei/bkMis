package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;

@SuppressWarnings("serial")
public class CompactManagerForm extends BasicForm {
	
	private Integer id;
	private String pagesize;
	private String currentpage;
	private String clientName;
	private String roomCode;
	private String beginDate;
	private String endDate;
	private String quitCode;
	private String type;
	private String applayDate;
	private String quitDate;
	private String quitSeason;
	private String validate;
	private String bz;
	private String doMan;
	private String zsbAttitude;
	private String cwbAttitude;
	private String wybAttitude;
	private String zjlAttitude;
	private String status;
	private String isNotice;
	private String isDestine;
	private String compactId;
	//120928新增加
	private String kfbAttitude;
	private String gcbAttitude;
	
	public String getKfbAttitude() {
		return kfbAttitude;
	}
	public void setKfbAttitude(String kfbAttitude) {
		this.kfbAttitude = kfbAttitude;
	}
	public String getGcbAttitude() {
		return gcbAttitude;
	}
	public void setGcbAttitude(String gcbAttitude) {
		this.gcbAttitude = gcbAttitude;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	private String forword;
	private String flag;
	private Integer lpId;
	private List<CompactIntention> compactIntentions;//意向书列表
	private List<Compact> list;
	private List<CompactRelet> compactRelets;
	private List<CompactQuit> compactQuits;
	private List<CompactChange> compactChanges;
	
	private String ids;

	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<CompactRelet> getCompactRelets() {
		return compactRelets;
	}
	public void setCompactRelets(List<CompactRelet> compactRelets) {
		this.compactRelets = compactRelets;
	}
	public List<Compact> getList() {
		return list;
	}
	public void setList(List<Compact> list) {
		this.list = list;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
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
	public String getQuitCode() {
		return quitCode;
	}
	public void setQuitCode(String quitCode) {
		this.quitCode = quitCode;
	}
	public String getApplayDate() {
		return applayDate;
	}
	public void setApplayDate(String applayDate) {
		this.applayDate = applayDate;
	}
	public String getQuitDate() {
		return quitDate;
	}
	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}
	public String getQuitSeason() {
		return quitSeason;
	}
	public void setQuitSeason(String quitSeason) {
		this.quitSeason = quitSeason;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getDoMan() {
		return doMan;
	}
	public void setDoMan(String doMan) {
		this.doMan = doMan;
	}
	public String getZsbAttitude() {
		return zsbAttitude;
	}
	public void setZsbAttitude(String zsbAttitude) {
		this.zsbAttitude = zsbAttitude;
	}
	public String getCwbAttitude() {
		return cwbAttitude;
	}
	public void setCwbAttitude(String cwbAttitude) {
		this.cwbAttitude = cwbAttitude;
	}
	public String getWybAttitude() {
		return wybAttitude;
	}
	public void setWybAttitude(String wybAttitude) {
		this.wybAttitude = wybAttitude;
	}
	public String getZjlAttitude() {
		return zjlAttitude;
	}
	public void setZjlAttitude(String zjlAttitude) {
		this.zjlAttitude = zjlAttitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompactId() {
		return compactId;
	}
	public void setCompactId(String compactId) {
		this.compactId = compactId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getForword() {
		return forword;
	}
	public void setForword(String forword) {
		this.forword = forword;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<CompactQuit> getCompactQuits() {
		return compactQuits;
	}
	public void setCompactQuits(List<CompactQuit> compactQuits) {
		this.compactQuits = compactQuits;
	}
	public List<CompactChange> getCompactChanges() {
		return compactChanges;
	}
	public void setCompactChanges(List<CompactChange> compactChanges) {
		this.compactChanges = compactChanges;
	}
	public String getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<CompactIntention> getCompactIntentions() {
		return compactIntentions;
	}
	public void setCompactIntentions(List<CompactIntention> compactIntentions) {
		this.compactIntentions = compactIntentions;
	}
	public String getIsDestine() {
		return isDestine;
	}
	public void setIsDestine(String isDestine) {
		this.isDestine = isDestine;
	}
}
