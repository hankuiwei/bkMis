package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IBuildBillsLogDAO;
import com.zc13.bkmis.mapping.CBuildBillsLog;
import com.zc13.util.GlobalMethod;

/**
 * 生成账单日志记录表DAO
 * @author 王正伟
 * Date：Dec 29, 2010
 * Time：2:46:45 PM
 */
public class BuildBillsLogDAOImpl extends BasicDAOImpl implements IBuildBillsLogDAO {

	@Override
	public void delete(CBuildBillsLog obj) throws DataAccessException {
		super.deleteObject(obj);
	}

	@Override
	public List<CBuildBillsLog> query(CBuildBillsLog obj)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from CBuildBillsLog where 1=1 ");
		if(obj!=null){
			if(obj.getId()!=null){
				hql.append(" and id=");
				hql.append(obj.getId());
			}
			if(!GlobalMethod.NullToSpace(obj.getBuildDate()).equals("")){
				hql.append(" and buildDate='");
				hql.append(obj.getBuildDate());
				hql.append("'");
			}
			if(!GlobalMethod.NullToSpace(obj.getBuildFlag()).equals("")){
				hql.append(" and buildFlag='");
				hql.append(obj.getBuildFlag());
				hql.append("'");
			}
		}
		List list = this.getSession().createQuery(hql.toString()).list();
		return list;
	}

	@Override
	public Integer save(CBuildBillsLog obj) throws DataAccessException {
		return (Integer)getHibernateTemplate().save(obj);
	}

	@Override
	public void update(CBuildBillsLog obj) throws DataAccessException {
		super.updateObject(obj);
	}

}
