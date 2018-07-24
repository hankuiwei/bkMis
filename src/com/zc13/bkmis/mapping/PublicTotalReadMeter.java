package com.zc13.bkmis.mapping;
/**
 * 统计公摊表具读数封装
 * @author 王正伟
 * Date：Dec 17, 2010
 * Time：2:24:05 PM
 */
public class PublicTotalReadMeter {
	private String meterId;
	private String code;
	private String type;
	private String totals;
	private String years;
	private String lpName;
	private String buildName;
	
	public String getLpName() {
		return lpName;
	}
	public void setLpName(String lpName) {
		this.lpName = lpName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotals() {
		return totals;
	}
	public void setTotals(String totals) {
		this.totals = totals;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	
}
