/**
 * daokui
 */
package com.zc13.msmis.form;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.BasicForm;
import com.zc13.bkmis.mapping.AwokeTask;

/**
 * 管理用户登录及权限设置
 * @author daokui
 * Date：Oct 28, 2010
 * Time：4:24:59 PM
 */
public class AdminLoginForm extends BasicForm{

	private String username;
	private String password;
	private List functionList;//存储功能菜单列表
	private Map userinfoMap;
	private String forward;
	private List<AwokeTask> tipsList;//houxj加
	private Map roomInfoMap;//houxj
	//查询前几条记录用于首页的显示
	private List frontEngList;//zyl
	
	private String week;
	private String week1;
	private int today;
	private int tomorrow;
	public List getFrontEngList() {
		return frontEngList;
	}
	public void setFrontEngList(List frontEngList) {
		this.frontEngList = frontEngList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List functionList) {
		this.functionList = functionList;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public Map getUserinfoMap() {
		return userinfoMap;
	}
	public void setUserinfoMap(Map userinfoMap) {
		this.userinfoMap = userinfoMap;
	}
	public List<AwokeTask> getTipsList() {
		return tipsList;
	}
	public void setTipsList(List<AwokeTask> tipsList) {
		this.tipsList = tipsList;
	}
	public Map getRoomInfoMap() {
		return roomInfoMap;
	}
	public void setRoomInfoMap(Map roomInfoMap) {
		this.roomInfoMap = roomInfoMap;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getToday() {
		return today;
	}
	public void setToday(int today) {
		this.today = today;
	}
	public String getWeek1() {
		return week1;
	}
	public void setWeek1(String week1) {
		this.week1 = week1;
	}
	public int getTomorrow() {
		return tomorrow;
	}
	public void setTomorrow(int tomorrow) {
		this.tomorrow = tomorrow;
	}
	
}
