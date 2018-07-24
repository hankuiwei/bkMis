package com.zc13.bkmis.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ILpManageDAO;
import com.zc13.bkmis.form.LpForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:11 PM
 */
public class LpManageDAOImpl extends BasicDAOImpl implements ILpManageDAO {

	public List<ELp> queryLpByName(String lpName) throws DataAccessException {
		
		String hql = "from ELp where lpName = :lpName";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("lpName", lpName);
		List<ELp> list = query.list();
		return list;
	}

	public int queryCounttotal(LpForm lpForm){
		int count = 0;
		String hql = "select count(e) from ELp as e ";
		String lpName = lpForm.getLpName();
		if(!"".equals(lpName) && lpName != null){
			hql += " where lpName like '%"+lpName+"%'";
		}
		try{
			Query query = this.getSession().createQuery(hql);
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	public List<ELp> queryLp(LpForm lpForm) throws DataAccessException {
		
		String hql = "from ELp ";
		String lpName = lpForm.getLpName();
		if(!"".equals(lpName) && lpName != null){
			hql += " where lpName like '%"+lpName+"%'";
		}
		hql += " order by lpCode";
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(lpForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(lpForm.getCurrentpage(),"1")) - 1);
		List<ELp> list = this.getSession().createQuery(hql).setFirstResult(currentindex).setMaxResults(pagesize).list();
		
		return list;
	}

	public void saveLp(ELp elp) throws DataAccessException {
		super.saveObject(elp); 
		
	}

	public List<ELp> queryLpById(Integer lpId) {
		String hql = "from ELp where lpId = :lpId";
		Query  query = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpId);
		List<ELp> list = query.list();
		return list;
	}

	public void deleteLp(List<String> idList) throws DataAccessException {
		Integer lpId = 0;
		Iterator<String> it = idList.iterator();
		while(it.hasNext()){
			lpId = Integer.parseInt(it.next());
			String hql = "delete from ELp where lpId =" + lpId;
			Query query = this.getSession().createQuery(hql);
			query.executeUpdate();
		}
	}
	
	public List<ELp> queryLp() throws DataAccessException {
		
		String hql = "from ELp ";
		Query query = this.getSession().createQuery(hql);
		List<ELp> list = query.list();
		return list;
	}

	@Override
	/* 同一种类型的表中不能有重复的表编号 */
	public List queryMeterByCodeAndName(String meterCode,String meterName) throws DataAccessException {
		String hql = "from EMeter where type = :meterName and code = :meterCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("meterCode", meterCode);
		query.setParameter("meterName", meterName);
		List<EMeter> list = query.list();
		return list;
	}

	@Override
	public List<SysCode> queryMeterType() throws DataAccessException {
		String hql = "from SysCode where codeType = 'meterType'"; 
		Query query = this.getSession().createQuery(hql);
		List<SysCode> list = query.list();
		return list;
	}

	@Override
	public void deleteMeter(String meterCode,String meterName) throws DataAccessException {
		String hql = "delete from EMeter where code = :code and type = :type";
		Query query =this.getSession().createQuery(hql);
		query.setParameter("code", meterCode);
		query.setParameter("type", meterName);
		query.executeUpdate();
	}

	@Override
	public void deleteMeterBylpId(List<String> idList)throws DataAccessException {
		
		Integer lpId = 0;
		Iterator<String> it = idList.iterator();
		while(it.hasNext()){
			lpId = Integer.parseInt(it.next()) ;
			String hql = "delete from EMeter where lpId = :lpId";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("lpId", lpId);
			query.executeUpdate();
		}
	}

	@Override
	public List queryMeterByLpId(Integer lpId)throws DataAccessException {
		String hql = "select m,s from EMeter m,SysCode s where s.codeValue =  m.type and m.lpId = :lpId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpId);
		List<EMeter> list = query.list();
		return list;
	}
}

