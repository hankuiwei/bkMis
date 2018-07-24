package com.zc13.msmis.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.msmis.dao.IFunctionListDao;
import com.zc13.msmis.mapping.MFunction;

public class FunctionListDaoImpl extends BasicDAOImpl implements IFunctionListDao {

	@Override
	public List<MFunction> getFunctions() throws DataAccessException {
		String hql = "from MFunction as f order by f.sequence ";
		Query query = this.getSession().createQuery(hql);
		
		List<MFunction> list = query.list();
		return list;
	}

	@Override
	public MFunction getFunction(int id) throws DataAccessException {
		return (MFunction)super.getObject(MFunction.class, id);
	}

	@Override
	public List<MFunction> checkFunction(String functionname,int parentid) throws DataAccessException {
		String hql = "from MFunction as f where f.functionname = :function and f.parentid = :parentid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("function", functionname);
		query.setParameter("parentid", parentid);
		
		List<MFunction> list = query.list();
		return list;
	}

	@Override
	public List<MFunction> getSonFunctionList(int parentid)
			throws DataAccessException {
		String hql = "from MFunction as f where f.parentid = :parentid";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("parentid", parentid);
		
		List<MFunction> list = query.list();
		return list;
	}

	@Override
	public void saveFunction(String functionname,String urlname, int sequence, int parentid)
			throws DataAccessException {
		MFunction function = new MFunction();
		Date updatetime = new Date();
		String enabled = "1";
		function.setUrlname(urlname);
		function.setUpdatetime(updatetime);
		function.setParentid(parentid);
		function.setSequence(sequence);
		function.setEnabled(enabled);
		function.setFunctionname(functionname);
		
		super.saveObject(function);
	}

	@Override
	public void updateFunction(String functionname,int functionid, String urlname,int parentid,
			int sequence) throws DataAccessException {
		MFunction function = new MFunction();
		Date updatetime = new Date();
		
		function.setFunctionid(functionid);
		function.setUrlname(urlname);
		function.setUpdatetime(updatetime);
		function.setSequence(sequence);
		function.setFunctionname(functionname);
		function.setParentid(parentid);
		
		super.updateObject(function);
	}

	@Override
	public void delFunction(int functionid)
			throws DataAccessException {
		String hql = "delete from MFunction f where f.functionid = " + functionid;
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
		
		String hql1 = "delete from MFunction f where f.parentid = " + functionid;
		Query query1 = this.getSession().createQuery(hql1);
		query1.executeUpdate();
	}

	@Override
	public int getSequence(int parentid) throws DataAccessException {
		String hql = "select max(sequence) from MFunction as f where f.parentid = " + parentid;
		Query query = this.getSession().createQuery(hql);
		int maxSequence = 0;
		if(query.uniqueResult()!=null){
			maxSequence = Integer.parseInt(query.uniqueResult().toString());
		}
		
		return maxSequence;
	}

}
