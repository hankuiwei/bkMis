package com.zc13.bkmis.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.CItems;

public class CCoststandardForm extends ActionForm {
	
	private CCoststandard standard = new CCoststandard();
	
	private Integer[] checkbox;
	
	private List bzList;
	private List ztList;
	private List<CCosttype> fylxList;
	private List<CItems> itemsList;
	private List<CCostparatype> ctypeParas;
	
	private String typeName;
	private String accountid;
	private Integer lpId;
	/*分页信息*/
	public String pagination;	//当前页数
	public Integer pagesize;	//每页显示的条数
	
	public Integer[] getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	public List getBzList() {
		return bzList;
	}
	public void setBzList(List bzList) {
		this.bzList = bzList;
	}
	public List getZtList() {
		return ztList;
	}
	public void setZtList(List ztList) {
		this.ztList = ztList;
	}
	public List<CCosttype> getFylxList() {
		return fylxList;
	}
	public void setFylxList(List<CCosttype> fylxList) {
		this.fylxList = fylxList;
	}
	public List<CItems> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<CItems> itemsList) {
		this.itemsList = itemsList;
	}
	public List<CCostparatype> getCtypeParas() {
		return ctypeParas;
	}
	public void setCtypeParas(List<CCostparatype> ctypeParas) {
		this.ctypeParas = ctypeParas;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
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
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	
}
