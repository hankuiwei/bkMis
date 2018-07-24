package com.zc13.bkmis.form;

import com.zc13.bkmis.mapping.CAccounttemplate;

public class CAccounttemplateForm extends BasicForm {
	
	private CAccounttemplate accounttemplate = new CAccounttemplate();
	
	private Integer[] checkbox;
	private Integer lpId;

	/**
	 * @return the accounttemplate
	 */
	public CAccounttemplate getAccounttemplate() {
		return accounttemplate;
	}

	/**
	 * @param accounttemplate the accounttemplate to set
	 */
	public void setAccounttemplate(CAccounttemplate accounttemplate) {
		this.accounttemplate = accounttemplate;
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

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	
}
