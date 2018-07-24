package com.zc13.bkmis.form;

import java.util.List;

import com.zc13.bkmis.mapping.CompactTask;

/**
 * 工作任务单form
 * @author daokui
 * @Date Mar 22, 2011
 * @Time 10:20:29 AM
 */
public class WorkTaskForm extends BasicForm {

	/**
	 * Mar 22, 2011
	 */
	private static final long serialVersionUID = -2963576676781647879L;
	
	//查询条件
	public String  cxClientName;
	public String  cxRooms;
	public String  cxNoteDate;
	public String  cxNoteDateEnd;
	private Integer lpId;

	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	
	public List<CompactTask> workTaskList;
	//查询条件
	public String clientName;	//客户名称
	public String rooms;	//房间号
	public String noteDate;	//任务单日期
	public String noteDateEnd;	//任务单结束日期
	
	//实体类的部分
	private Integer id;
	private String code;
	private Integer compactId;
	private String compactCode;
	private String linkMan;
	private String linkPhone;
	private String inDate;
	private String goDate;
	private Integer phones;
	private String internets;
	private Integer keys;
	private String zhaos;
	private String caiwu;
	private String wuye;
	private String gongc;
	
	public String getGongc() {
		return gongc;
	}
	public void setGongc(String gongc) {
		this.gongc = gongc;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	public List<CompactTask> getWorkTaskList() {
		return workTaskList;
	}
	public void setWorkTaskList(List<CompactTask> workTaskList) {
		this.workTaskList = workTaskList;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRooms() {
		return rooms;
	}
	public void setRooms(String rooms) {
		this.rooms = rooms;
	}
	public String getNoteDate() {
		return noteDate;
	}
	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}
	public String getNoteDateEnd() {
		return noteDateEnd;
	}
	public void setNoteDateEnd(String noteDateEnd) {
		this.noteDateEnd = noteDateEnd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getCompactId() {
		return compactId;
	}
	public void setCompactId(Integer compactId) {
		this.compactId = compactId;
	}
	public String getCompactCode() {
		return compactCode;
	}
	public void setCompactCode(String compactCode) {
		this.compactCode = compactCode;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getGoDate() {
		return goDate;
	}
	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}
	public Integer getPhones() {
		return phones;
	}
	public void setPhones(Integer phones) {
		this.phones = phones;
	}
	public String getInternets() {
		return internets;
	}
	public void setInternets(String internets) {
		this.internets = internets;
	}
	public Integer getKeys() {
		return keys;
	}
	public void setKeys(Integer keys) {
		this.keys = keys;
	}
	public String getZhaos() {
		return zhaos;
	}
	public void setZhaos(String zhaos) {
		this.zhaos = zhaos;
	}
	public String getCaiwu() {
		return caiwu;
	}
	public void setCaiwu(String caiwu) {
		this.caiwu = caiwu;
	}
	public String getWuye() {
		return wuye;
	}
	public void setWuye(String wuye) {
		this.wuye = wuye;
	}
	public String getCxClientName() {
		return cxClientName;
	}
	public void setCxClientName(String cxClientName) {
		this.cxClientName = cxClientName;
	}
	public String getCxRooms() {
		return cxRooms;
	}
	public void setCxRooms(String cxRooms) {
		this.cxRooms = cxRooms;
	}
	public String getCxNoteDate() {
		return cxNoteDate;
	}
	public void setCxNoteDate(String cxNoteDate) {
		this.cxNoteDate = cxNoteDate;
	}
	public String getCxNoteDateEnd() {
		return cxNoteDateEnd;
	}
	public void setCxNoteDateEnd(String cxNoteDateEnd) {
		this.cxNoteDateEnd = cxNoteDateEnd;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	
}
