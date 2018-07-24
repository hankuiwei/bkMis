package com.zc13.msmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.msmis.dao.IUserManagerDao;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class UserManagerDaoImpl extends BasicDAOImpl implements IUserManagerDao {

	public List<MUser> findUser(UserForm userform,boolean isPage) {

		List<MUser> list = null;
		String lpId = GlobalMethod.NullToSpace(String.valueOf(userform.getLpId()));
		String hql = "select u from MUser as u left join u.MUserRoles as ur left join ur.MRole as r where (r.range > isnull(" + userform.getRange() + ",0) or r.range is null)";
		/* houxj
		 * 模糊查询 */
		if(!"".equals(GlobalMethod.NullToSpace(userform.getUsername()))){
			hql += " and username like '%" + userform.getUsername() + "%'";
		}
		if(!"".equals(userform.getLpId())&&userform.getLpId()!=null){
			hql += " and u.lpId = " + lpId;
		}
		
		hql += " order by u.userid";
		
		if(isPage){
			int pagesize ;
			//每页显示的条数，空的情况下默认是10
			pagesize = Integer.parseInt(GlobalMethod.NullToParam(userform.getPagesize(),"10"));
			int currentindex;
			currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(userform.getCurrentpage(),"1")) - 1);
			list = (List<MUser>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = (List<MUser>)this.getSession().createQuery(hql.toString()).list();
		}
		return list;
	}
	public int findUser(int range) {
		
		List<MUser> list = null;
		String hql = "select u from MUser as u left join u.MUserRoles as ur left join ur.MRole as r where r.range > " + range + " or r.range is null order by u.userid";
		//每页显示的条数，空的情况下默认是10
		list = (List<MUser>)this.getSession().createQuery(hql.toString()).list();
		return list.size();
	}

	public List findRole() {

		List list = null;
		String hql = "from MRole";
		Query query = this.getSession().createQuery(hql);
		list = query.list();
		return list;
	}

	public void saveRole(MUserRole mUserRole) {

		mUserRole.setDescription("默认添加");
		this.getSession().saveOrUpdate(mUserRole);

	}

	public List findUserById(MUser mUser) {

		String hql = "from MUser where userid = '" + mUser.getUserid() + "'";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}

	/**
	 * 添加用户
	 */
	public void addUser(MUser mUser) {

		this.getSession().save(mUser);
	}

	

	@Override
	public MRole getRoleOfUser(int userid) {
		MRole role = new MRole();
		String sql = "select r.roleid,r.rolename from m_role as r inner JOIN m_user_role as ur on r.roleid = ur.roleid where ur.userid = " + userid +"";
		SQLQuery query = getSession().createSQLQuery(sql);
		List<Object[]> result = query.list();
		  for(Object[] datas :result){
             
               role.setRoleid(Integer.parseInt(datas[0].toString()));
               role.setRolename(datas[1].toString());
         }
		return role;
	}

	@Override
	public List findRole(int range) {
		List list = null;
		String hql = "from MRole as r where r.range > " + range +" and r.enabled='"+Contants.ENABLE+"'";
		Query query = this.getSession().createQuery(hql);
		list = query.list();
		return list;
	}

	@Override
	public List<MUser> getuser(String username) {
		
		String hql = "from MUser as u where u.username = :username";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("username", username);
		
		List<MUser> list = query.list();
		return list;
	}
	@Override
	public List<HrPersonnel> getEmployeeName(String department)
			throws DataAccessException {
		
		String hql = "from HrPersonnel where departmentCode = :department";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("department", department);
		List<HrPersonnel> list = query.list();
		return list;
	}
	@Override
	public void deleteUserRole(String userId) throws DataAccessException {
		// TODO Auto-generated method stub
		
		String hql = "delete from MUserRole where USERID='"+userId+"'";
		this.getSession().createQuery(hql).executeUpdate();
		
		
	}
}
