package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICAccounttemplateDAO;
import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.ELp;

public class CAccounttemplateDAOImpl extends BasicDAOImpl implements
		ICAccounttemplateDAO {

	public List getCAccounttemplateList(CAccounttemplateForm account) throws DataAccessException {
		String hql = " from CAccounttemplate as c,ELp as lp where c.lpid = lp.lpId ";
		if(account.getLpId()!=null&&account.getLpId()!=0){
			hql += " and c.lpid="+account.getLpId();
		}
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}

	public List<ELp> getLp(CAccounttemplateForm account) throws DataAccessException {
		String hql = "from ELp ";
		if(account.getLpId()!=null&&account.getLpId()!=0){
			hql += " where  lpId="+account.getLpId();
		}
		Query query = this.getSession().createQuery(hql);
		List<ELp> list = query.list();
		return list;
	}

	@Override
	public CAccounttemplate getById(Integer id) throws DataAccessException {
		CAccounttemplate c = null;
		String hql = "from CAccounttemplate where id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List<CAccounttemplate> list = query.list();
		if(list!=null&&list.size()>0){
			c = list.get(0);
		}
		return c;
	}
}
