package com.zc13.bkmis.dao.impl;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IRecordDAO;
import com.zc13.bkmis.form.AttendanceForm;

public class RecordDAOImpl extends BasicDAOImpl implements IRecordDAO{

	@Override
	public String getLatestTime(AttendanceForm form) throws DataAccessException {
		StringBuffer hql = new StringBuffer("select max(time) from HrRecord where 1=1 ");
		if(form!=null){
			if(form.getLpId()!=null&&form.getLpId()!=0){
				hql.append(" and lpId=").append(form.getLpId());
			}
		}
		String latestTime = (String)this.getSession().createQuery(hql.toString()).uniqueResult();
		return latestTime;
	}
	
}
