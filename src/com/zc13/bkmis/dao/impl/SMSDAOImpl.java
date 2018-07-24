package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ISMSDAO;
import com.zc13.bkmis.form.SMSForm;
import com.zc13.util.GlobalMethod;

public class SMSDAOImpl extends BasicDAOImpl implements ISMSDAO {

	@Override
	public List querySMSList(SMSForm form1, boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("select r.MsgID as msgId, r.DestTel as destTel,r.Content as content,r.SentStatus as sentStatus,r.SentTime as sentTime,t.personnel_name as personnelName from T_SentRecord as r,T_SendTask as t where r.MsgID=t.TaskID ");
		if(form1!=null){
			if(form1.getLpId()!=null&&form1.getLpId()!=0){
				hql.append(" and t.lp_id=").append(form1.getLpId());
			}
			if(!GlobalMethod.NullToSpace(form1.getName()).equals("")){
				hql.append(" and t.personnel_name like '%").append(form1.getName()).append("%' ");
			}
			if(!GlobalMethod.NullToSpace(form1.getPhone()).equals("")){
				hql.append(" and r.DestTel ='").append(form1.getPhone()).append("' ");
			}
		}
		hql.append(" order by r.SentTime desc");
		Query query=this.getSession().createSQLQuery(hql.toString());
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
	
}
