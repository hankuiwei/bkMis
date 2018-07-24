package com.zc13.util;

public class LogParam {

	public String userName; 		//操作用户名
	public Integer userId;			//操作人ID
	public Integer lpId; 		    //用户所在楼盘
	public String operateModule;	//操作模块  ：系统的十个大模块中的模块名
	public String operateObj;		//操作对象  ：操作的物理对象（比如系统模块是房产模块的情况下，下面还有楼盘，房间等）
	public String operateType; 		//操作类型
	public String flag;				//标识位：1,更新；2,新增；3,删除
	public Object object;			//修改之后的对象
	private String operateContent;  //操作的具体内容
	
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLpId() {
		return lpId;
	}
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	public String getOperateModule() {
		return operateModule;
	}
	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}
	public String getOperateObj() {
		return operateObj;
	}
	public void setOperateObj(String operateObj) {
		this.operateObj = operateObj;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
