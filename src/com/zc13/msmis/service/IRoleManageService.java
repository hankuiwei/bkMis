package com.zc13.msmis.service;

import java.util.List;

import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.form.RoleForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MRolePerm;
import com.zc13.msmis.mapping.MRolreUserPrem;

/**
 * 
 * @author 侯哓娟
 * Date：Nov 8, 2010
 * Time：11:02:48 AM
 */
public interface IRoleManageService {

	/** 添加角色 */
	public void addRole(RoleForm roleForm) throws BkmisServiceException;
	
	/** 删除角色 */
	public String deleteRole(RoleForm roleForm) throws BkmisServiceException;
	
	/** 设置更新角色所需的信息 */
    public MRole setModifyInfo(RoleForm roleForm) throws BkmisServiceException;
	
    /** 更新角色 */
	public void updateRole(RoleForm roleForm) throws BkmisServiceException; 
	
	/** 检查角色名 */
	public boolean checkRoleName(String rolename) throws BkmisServiceException;
	
	/** 查询得到角色列表 */
	public List<MRole> getRole(int range,int lpId) throws BkmisServiceException;
	
	/**根据id查询指定的角色得到相应的权限功能*/
	public List<MRolePerm> getRolePerm(Integer roleid) throws BkmisServiceException;
	
	/** 得到角色对应的用户和权限名称 */
	public List<MRolreUserPrem> getRole_User_Perm()throws BkmisServiceException;
	
	/** 更新角色权限表 */
	public void updateRolePerm(List permList,Integer roleid)throws BkmisServiceException;
	
	/** 获得任务信息 */
	public List<AwokeTask> getTips () throws BkmisServiceException ;
	
	/** 获取指定角色对应的任务 */
	public List getTipsByRoleId(Integer roleid) throws BkmisServiceException;
	
	/** 保存角色的任务提示信息 */
	public void addRoleTips(List<String> idList,Integer roleid,List rtipList) throws BkmisServiceException;
}
