package com.zc13.bkmis.form;

import org.apache.struts.action.ActionForm;

public class CItemsForm extends ActionForm {
	private Integer id;
	private String itemCode;
	private String itemName;
	private String countPaymentrate;
	private String explanation;
	private Integer indexs;
	private String typeName;
	private Integer[] checkbox;
	private Integer lpId;
	
	/*分页信息*/
	public String pagination;	//当前页数
	public Integer pagesize;	//每页显示的条数
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
	public Integer[] getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCountPaymentrate() {
		return countPaymentrate;
	}
	public void setCountPaymentrate(String countPaymentrate) {
		this.countPaymentrate = countPaymentrate;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public Integer getIndexs() {
		return indexs;
	}
	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	
}
