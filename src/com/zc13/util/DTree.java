package com.zc13.util;

import java.io.Serializable;

/**
 * 使用树状结构的时候需要用到，统一存放树需要的几个属性
 * @author daokui
 * Date：Nov 23, 2010
 * Time：6:58:42 PM
 */
public class DTree implements Serializable {

	private String id;
	private String name;
	private String parentID;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public DTree(){};
	public DTree(String id, String name, String parentID, String url) {
		
		this.id = id;
		this.name = name;
		this.parentID = parentID;
		this.url = url;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
