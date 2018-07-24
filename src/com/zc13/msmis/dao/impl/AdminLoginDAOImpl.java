package com.zc13.msmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.msmis.dao.IAdminLoginDAO;
import com.zc13.msmis.mapping.LimitCar;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class AdminLoginDAOImpl extends BasicDAOImpl implements IAdminLoginDAO  {
	
	public MUser getUserInfo(String username, String password) throws DataAccessException {
		// TODO Auto-generated method stub
		
		String hql = " from MUser where username =:username and password = :password and enabled = '"+Contants.ENABLE+"'";
		
		//String hql = " from MUser where username =:username and password = :password";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		List list = query.list();
		MUser userLogin = new MUser();
		if(list.size()>0){
			userLogin = (MUser)list.get(0);
			return userLogin;
		}else{
			return null;
		}
	}

	public MUserRole getRoleByUserID(Integer userid) throws DataAccessException {
		// TODO Auto-generated method stub
		
		//enable 暂时用不到
		//String hql = "from MUserRole where USERID = :userid  and ENABLED = '"+Contants.ENABLE+"'";
		
		String hql = "from MUserRole where USERID = :userid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("userid", userid);
		List list = query.list();
		MUserRole userRole =  new MUserRole();
		if(list.size()>0){
			userRole = (MUserRole)list.get(0);
			return userRole;
		}else{
			return null;
		}
	}
	
	public List getFundesc(Integer roleid){
		
		//根据角色得到所对应的功能id
		List rolePerm = this.findObject("select f from MRolePerm r,MFunction f where  r.MFunction.functionid = f.functionid and r.MRole.roleid = '" + roleid + "' order by f.parentid,f.sequence");
		/*Iterator it = rolePerm.iterator();
		List<MFunction> fundesc = new ArrayList<MFunction>();
		while(it.hasNext()){
			MRolePerm perm = (MRolePerm) it.next();
			Integer funid = perm.getMFunction().getFunctionid();
			
			//根据功能id得到功能详情（包括功能名，url等）
			MFunction fun = (MFunction)this.getObject(MFunction.class,funid);
			fundesc.add(fun);
		}*/
		return rolePerm;
	}
	public List<AwokeTask> getTask(Integer roleid) throws DataAccessException {
		
		List roleTask = this.findObject("select a2 from AwokeTaskRole a1,AwokeTask a2 where a1.roleId = '" + roleid + "' and a1.taskId = a2.id order by a2.type,a2.sequence");
		
		return roleTask;
	}
	
	public List<LimitCar> getCarList() throws DataAccessException{
		
		String hqlString = "from LimitCar where beginDate <='"+GlobalMethod.getTime()+"' and endDate >='"+GlobalMethod.getTime()+"'";
		List<LimitCar> list = this.getSession().createQuery(hqlString).list();
		
		return list;
		
	}
	
	
}
