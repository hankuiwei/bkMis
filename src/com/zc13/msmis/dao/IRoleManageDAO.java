package com.zc13.msmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MRolePerm;
import com.zc13.msmis.mapping.MRolreUserPrem;
/**
 * 
 * @author 侯哓娟
 * Date：Nov 8, 2010
 * Time：2:41:31 PM
 */
public interface IRoleManageDAO extends BasicDAO{

	/** 添加角色 */
	public void saveRole(MRole mrole);

	/** 检查角色名 */
	public boolean checkRoleName(String rolename);
	
	/** 删除角色 */
	public void deleteRole(MRole mrole);

	/** 更新角色 */
	public void updateRole(MRole mrole);
    
	/** 根据id查询指定的角色 */
	public List<MRole> queryRoleById(Integer roleid);
	
	/** 查询得到角色列表 */
	public List<MRole> queryRole(int range,int lpId);
	
	/**根据id查询指定的角色得到相应的权限功能 */
	public List<MRolePerm> getRolePerm(Integer roleid);

//	/** 根据角色id查询角色的功能 */
//	public String getFunctionNameByRoleId(Integer roleid);
	
	/** 实现角色表,功能表 ,和用户表及其关系表之间的多表查询 */
	public List<MRolreUserPrem> getRole_User_Perm();
    
	/** 更新角色权限表 */
	public void updateRolePerm(List permList,Integer roleid);
	
	/** 根据角色id获取该角色的任务信息 */ 
	public List getTip(Integer roleid) throws DataAccessException;
	
	/** 删除角色任务信息 */
	public void deleteRoleTip(Integer roleid) throws DataAccessException;

}
