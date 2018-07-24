/**
 * ZHAOSG
 */
package com.zc13.bkmis.form;

import org.apache.struts.action.ActionForm;

import com.zc13.bkmis.mapping.CNotice;

/**
 * @author ZHAOSG
 * Date：Jan 3, 2011
 * Time：2:40:26 PM
 */
public class CNoticeForm extends ActionForm {
	private CNotice notice = new CNotice();
	private Integer[] clientId;//客户id
	private Integer[] itemId;//收费项id
	private Integer[] standardId;//收费标准id
	private String[] checkbox;//复选框-通知单id
	private Integer id;//客户id
	private String noticeDate;//发出日期
	private String clientName;
	private String noticeType;
	private String start;
	private String end;
	private String name;
	private Integer buildId;
	private Integer lpId;
	/*分页信息*/
	public String pagination;	//当前页数
	public Integer pagesize;	//每页显示的条数
	/**
	 * @return the notice
	 */
	public CNotice getNotice() {
		return notice;
	}
	/**
	 * @param notice the notice to set
	 */
	public void setNotice(CNotice notice) {
		this.notice = notice;
	}
	/**
	 * @return the clientId
	 */
	public Integer[] getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Integer[] clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the standardId
	 */
	public Integer[] getStandardId() {
		return standardId;
	}
	/**
	 * @param standardId the standardId to set
	 */
	public void setStandardId(Integer[] standardId) {
		this.standardId = standardId;
	}
	/**
	 * @return the pagination
	 */
	public String getPagination() {
		return pagination;
	}
	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	/**
	 * @return the pagesize
	 */
	public Integer getPagesize() {
		return pagesize;
	}
	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	/**
	 * @return the checkbox
	 */
	public String[] getCheckbox() {
		return checkbox;
	}
	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the noticeDate
	 */
	public String getNoticeDate() {
		return noticeDate;
	}
	/**
	 * @param noticeDate the noticeDate to set
	 */
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}
	/**
	 * @param noticeType the noticeType to set
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the lpId
	 */
	public Integer getLpId() {
		return lpId;
	}
	/**
	 * @param lpId the lpId to set
	 */
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public Integer[] getItemId() {
		return itemId;
	}
	public void setItemId(Integer[] itemId) {
		this.itemId = itemId;
	}
}
