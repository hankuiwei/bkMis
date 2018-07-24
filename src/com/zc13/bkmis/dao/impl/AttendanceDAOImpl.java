package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAttendanceDAO;
import com.zc13.bkmis.form.AttendanceForm;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.util.GlobalMethod;

public class AttendanceDAOImpl extends BasicDAOImpl implements IAttendanceDAO {

	@Override
	public List getAttendanceList(AttendanceForm form1, boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("select new Map(p.personnelId as personnelId,p.personnelCode as personnelCode,p.personnelNum as personnelNum,p.personnelName as personnelName,p.sex as sex,p.phone as phone,(case when p.isDispatch='1' then '是' else '否' end) as isDispatch,(case when r.status='1' then '上班' else '下班' end) as status,r.time as time) from HrPersonnel as p,HrRecord as r where p.personnelNum=r.personnelNum ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getCx_personnelName()).equals("")){
				hql.append(" and p.personnelName like '%").append(form1.getCx_personnelName()).append("%' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_starttime()).equals("")){
				hql.append(" and SUBSTRING((case r.time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else r.time end),1,10)>='").append(form1.getCx_starttime()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_endtime()).equals("")){
				hql.append(" and SUBSTRING((case r.time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else r.time end),1,10)<='").append(form1.getCx_endtime()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxStatus()).equals("")){
				hql.append(" and p.status='").append(form1.getCxStatus()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isDispatch()).equals("")){
				hql.append(" and p.isDispatch='").append(form1.getCx_isDispatch()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isInPost()).equals("")){
				hql.append(" and r.status='").append(form1.getCx_isInPost()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isOut()).equals("")){
				hql.append(" and p.isOut='").append(form1.getCx_isOut()).append("' ");
			}
			
			if(form1.getLpId()!=null&&form1.getLpId()!=0){
				hql.append(" and p.lpId=").append(form1.getLpId());
				hql.append(" and r.lpId=").append(form1.getLpId());
			}
			
		}
		hql.append(" order by r.time desc");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(isPage){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}

	@Override
	public List getCurrentAttendance(AttendanceForm form1, boolean isPage) throws DataAccessException {
		//StringBuffer hql = new StringBuffer("select new Map(p.personnelId as personnelId,p.personnelCode as personnelCode,p.personnelNum as personnelNum,p.personnelName as personnelName,p.sex as sex,p.phone as phone,(case when p.isDispatch='1' then '是' else '否' end) as isDispatch,p.isInPost as isInPost,(select max(r.time) from HrRecord r where r.personnelNum=p.personnelNum) as time) from HrPersonnel p where p.personnelNum is not null and p.personnelNum<>'' ");
		StringBuffer hql = new StringBuffer("select new Map(p.personnelId as personnelId,p.personnelCode as personnelCode,p.personnelNum as personnelNum,p.personnelName as personnelName,p.sex as sex,p.phone as phone,(case when p.isDispatch='1' then '是' else '否' end) as isDispatch,p.isInPost as isInPost,(select max(r.time) from HrRecord r where r.personnelNum=p.personnelNum) as time) from HrPersonnel p where 1=1 ");
		if(form1!=null){
			if(form1.getLpId()!=null&&form1.getLpId()!=0){
				hql.append(" and p.lpId=").append(form1.getLpId());
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_personnelName()).equals("")){
				hql.append(" and p.personnelName like '%").append(form1.getCx_personnelName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_personnelCode()).equals("")){
				hql.append(" and p.personnelCode like '%").append(form1.getCx_personnelCode()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isDispatch()).equals("")){
				hql.append(" and p.isDispatch = '").append(form1.getCx_isDispatch()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isInPost()).equals("")){
				hql.append(" and p.isInPost = '").append(form1.getCx_isInPost()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_isOut()).equals("")){
				hql.append(" and p.isOut = '").append(form1.getCx_isOut()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCx_personnelNum()).equals("")){
				hql.append(" and p.personnelNum = '").append(form1.getCx_personnelNum()).append("'");
			}
		}
		hql.append(" order by p.personnelId");
		List<HrPersonnel> list = null;
		if(!isPage){
			list = this.getSession().createQuery(hql.toString()).list();
		}else{
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			Query query=this.getSession().createQuery(hql.toString());
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}
		return list;
	}

}
