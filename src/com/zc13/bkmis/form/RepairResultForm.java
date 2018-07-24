package com.zc13.bkmis.form;

/**
 * 客户报修项目Form 
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：3:18:29 PM
 */
public class RepairResultForm extends BasicForm{

	private Integer id;
	private String result;
	private String createDate;
	private String flag;//控制页面跳转
	
	/*分页信息*/
	public String currentpage;	//当前页数
	public String pagesize;	//每页显示的条数


	//登录用户名
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
