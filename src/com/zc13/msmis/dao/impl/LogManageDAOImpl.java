package com.zc13.msmis.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.msmis.dao.ILogManagerDAO;
import com.zc13.msmis.mapping.SysLog;
import com.zc13.util.GlobalMethod;
/**
 * @author 秦彦韬
 * Date：Nov 30, 2010
 * Time：5:16:13 PM
 */
public class LogManageDAOImpl extends BasicDAOImpl implements ILogManagerDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SysLog> query(SysLog sysLog,String currentpage,String pagesize1) {
		
		StringBuffer hql = new StringBuffer("from SysLog where 1=1 ");
		if(sysLog.getOperateUserName()!=null&&!"".equals(sysLog.getOperateUserName())){
			hql.append(" and operateUserName like '%"+sysLog.getOperateUserName()+"%'");
		}
		if(sysLog.getOperateType()!=null&&!"".equals(sysLog.getOperateType())){
			hql.append(" and operateType like '%"+sysLog.getOperateType()+"%'");
		}
		if(sysLog.getOperateModule()!=null&&!"".equals(sysLog.getOperateModule())){
			hql.append(" and operateModule like '%"+sysLog.getOperateModule()+"%'");
		}
		if(sysLog.getOperateUserId()!=null&&sysLog.getOperateUserId()!=0){
			hql.append(" and operateUserId like '%"+sysLog.getOperateUserId()+"%'");
		}
		if(sysLog.getLpId()!=null&&sysLog.getLpId()!=0){
			hql.append("and lpId = " +sysLog.getLpId() );
		}
		
		hql.append(" order by operateTime desc");
		Query query = this.getSession().createQuery(hql.toString());
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(pagesize1,"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
		List<SysLog> list = (List<SysLog>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize).list();
		return list;
	}

	@Override
	public List<SysLog> queryCount(SysLog sysLog) {
		StringBuffer hql = new StringBuffer("from SysLog where 1=1 ");
		if(sysLog.getOperateUserName()!=null&&!"".equals(sysLog.getOperateUserName())){
			hql.append(" and operateUserName like '%"+sysLog.getOperateUserName()+"%'");
		}
		if(sysLog.getOperateType()!=null&&!"".equals(sysLog.getOperateType())){
			hql.append(" and operateType like '%"+sysLog.getOperateType()+"%'");
		}
		if(sysLog.getOperateModule()!=null&&!"".equals(sysLog.getOperateModule())){
			hql.append(" and operateModule like '%"+sysLog.getOperateModule()+"%'");
		}
		if(sysLog.getLpId()!=null&&!"".equals(sysLog.getLpId())){
			hql.append(" and lpId like '%"+sysLog.getLpId()+"%'");
		}
		if(sysLog.getOperateUserId()!=null&&sysLog.getOperateUserId()!=0){
			hql.append(" and operateUserId like '%"+sysLog.getOperateUserId()+"%'");
		}
		Query query = this.getSession().createQuery(hql.toString());
		return query.list();
	}

}
