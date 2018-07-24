package com.zc13.bkmis.form;

import org.apache.struts.action.ActionForm;

import com.zc13.util.LogParam;

public class BasicForm extends ActionForm{

	public Integer LpId;
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数
	
	public LogParam logParam;//日志参数对象
	public String forward;
	
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public LogParam getLogParam() {
		return logParam;
	}
	public void setLogParam(LogParam logParam) {
		this.logParam = logParam;
	}
	public Integer getLpId() {
		return LpId;
	}
	public void setLpId(Integer lpId) {
		LpId = lpId;
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
	
}
