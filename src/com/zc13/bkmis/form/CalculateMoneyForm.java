package com.zc13.bkmis.form;

/**
 * 合同预算 form
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午10:57:32
 */
public class CalculateMoneyForm extends BasicForm {

	/**合同id*/
	private Integer compactId;
	private String year;
	private String compactCode;
	private String clientName;
	private String month;
	
	private boolean isPage = true;

	public boolean isPage() {
		return isPage;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

	public String getCompactCode() {
		return compactCode;
	}

	public void setCompactCode(String compactCode) {
		this.compactCode = compactCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getCompactId() {
		return compactId;
	}

	public void setCompactId(Integer compactId) {
		this.compactId = compactId;
	}
}
