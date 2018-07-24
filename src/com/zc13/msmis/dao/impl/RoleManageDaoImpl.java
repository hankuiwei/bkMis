package com.zc13.msmis.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.msmis.dao.IRoleManageDAO;
import com.zc13.msmis.mapping.MFunction;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MRolePerm;
import com.zc13.msmis.mapping.MRolreUserPrem;
/**
 * 
 * @author 侯哓娟
 * Date：Nov 8, 2010
 * Time：2:45:48 PM
 */
public class RoleManageDaoImpl extends BasicDAOImpl implements IRoleManageDAO {

	/** 添加角色 */
	public void saveRole(MRole mrole) {
		super.saveObject(mrole);
	}
	/** 删除角色 */
	public void deleteRole(MRole mrole) {
		super.deleteObject(mrole);
		
	}
	 /** 更新角色 */
	public void updateRole(MRole mrole) {
		super.updateObject(mrole);
	}
	 /** 根据id查询指定的角色为数据更新传参 */
	public List<MRole> queryRoleById(Integer roleid) {
		
		String hql = "from MRole where roleid = :roleid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roleid",roleid);
		List<MRole> list = query.list();
		return list;
	}
	/** 查询得到角色列表 */
	public List<MRole> queryRole(int range,int lpId) {
		
		String hql="from MRole where rolename != 'Administrator' and range >="+range;
		hql+=" and lpId = " + lpId;
		List<MRole> list=super.findObject(hql);
		return list;
	}
    
	/** 检查角色名 */
	public boolean checkRoleName(String rolename) {
		
		String hql = "from MRole where rolename = :rolename";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("rolename", rolename);
		List<MRole> list = query.list();
		if (list != null && list.size() >= 1) {
			return false;
		}
		return true;
	}
	
	/** 根据角色id查询角色的功能 */
	public List<MRolePerm> getRolePerm(Integer roleid) {
		String hql = "from MRolePerm where roleid = :roleid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roleid", roleid);
		List<MRolePerm> list = query.list();
		return list;
	}
	
	/** 实现角色表,功能表 ,和用户表及其关系表之间的多表查询 */
	public List<MRolreUserPrem> getRole_User_Perm(){
		List<MRolreUserPrem> resultList = new ArrayList();
		String sql = "select r.rolename ,u.username ,"
				+ "r.enabled ,f.functionname "
				+ "from m_role r left join m_role_perm rp on r.roleid = rp.roleid"
				+ " left join m_function f on rp.functionid = f.functionid ,"
				+ "m_role r1 left join m_user_role ur on r1.roleid = ur.roleid "
				+ " left join m_user u on u.userid = ur.userid";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		for (int i = 0; i < list.size(); i++) {
			MRolreUserPrem mru = new MRolreUserPrem();
			String rolename;
			String username;
			String functionname = null;
			String senabled;
			rolename = (((Object[]) list.get(i))[0]).toString();
			if (((Object[]) list.get(i))[1] == null) {
				username = null;
			} else {
				username = ((Object[]) list.get(i))[1].toString();
			}
			if(((Object[]) list.get(i))[2] == null){
				senabled = null ;
			}else{
				senabled = ((Object[])list.get(i))[2].toString();
			}
			if (((Object[]) list.get(i))[3] == null) {
				functionname = null;
			} else {
				String fsql = "select t.functionname from (select r.rolename ,u.username ,"
				+ "r.enabled ,f.functionname "
				+ "from m_role r left join m_role_perm rp on r.roleid = rp.roleid"
				+ " left join m_function f on rp.functionid = f.functionid ,"
				+ "m_role r1 left join m_user_role ur on r1.roleid = ur.roleid "
				+ " left join m_user u on u.userid = ur.userid ) t where t.rolename = " + "'" + rolename + "'";
				SQLQuery query2 = this.getSession().createSQLQuery(fsql);
				List flist = query2.list();
//				List<Object[]> flist =query2.list();
//				for(int j = 0;j<flist.size();j++){
//					functionname += (String)flist.get(i)[0].toString();
//				}
				Iterator it = flist.iterator();
				while(it.hasNext()){
					functionname += it.next().toString() + ",";
				}
				functionname = ((Object[]) list.get(i))[3].toString();
			}
System.out.println(functionname);
		    mru.setRolename(rolename);
		    mru.setUsername(username);
		    mru.setEnabled(Integer.parseInt(senabled));
		    mru.setFunctionname(functionname);
		    resultList.add(mru);
		}
		return resultList;
	}
   
	/** 更新角色权限表 */
	public void updateRolePerm(List permList,Integer roleid){
		//首先根据指定角色id删除角色权限表中对应的角色权限关系记录

		String sql = "delete from MRolePerm where roleid = " + roleid;
		Query query = this.getSession().createQuery(sql);
		query.executeUpdate();
		for(int i=0;i<permList.size();i++){
			MRolePerm mrolePerm = new MRolePerm();
			MFunction mfunction = new MFunction();
			mfunction.setFunctionid((Integer) permList.get(i));
			mrolePerm.setMFunction(mfunction);
			MRole mrole = new MRole();
			mrole.setRoleid(roleid);
			mrolePerm.setMRole(mrole);
			super.saveOrUpdateObject(mrolePerm);
		}
		return;
	}
	
	@Override
	public List getTip(Integer roleid) throws DataAccessException {
		
		String hql = "select t,tr from AwokeTask t,AwokeTaskRole tr where t.id = tr.taskId and tr.roleId = :roleid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roleid", roleid);
		List list = query.list();
		return list;
	}
	
	@Override
	public void deleteRoleTip(Integer roleid) throws DataAccessException {
		
		String hql = "delete from AwokeTaskRole where roleId = :roleId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roleId", roleid);
		query.executeUpdate();
	}
}
