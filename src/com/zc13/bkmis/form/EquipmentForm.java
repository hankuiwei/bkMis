package com.zc13.bkmis.form;


/**
 * 设备维护模块form
 * @author daokui
 * @Date 2011-6-14
 * @Time 下午05:32:49
 */
public class EquipmentForm extends BasicForm{
	
	//这一部分是负责设备类型的
	private int id;
	private String name;
	private String code;
	private String description;
	private String parentid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
