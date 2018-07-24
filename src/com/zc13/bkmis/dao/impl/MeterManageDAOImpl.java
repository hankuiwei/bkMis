package com.zc13.bkmis.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IMeterManageDAO;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.msmis.mapping.SysCode;
/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:11 PM
 */
public class MeterManageDAOImpl extends BasicDAOImpl implements IMeterManageDAO {

	@Override
	/* 同一种类型的表中不能有重复的表编号 */
	public List queryMeterByCodeAndType(String meterCode,String meterType) throws DataAccessException {
		String hql = "from EMeter where type = :meterType and code = :meterCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("meterCode", meterCode);
		query.setParameter("meterType", meterType);
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
	public void deleteMeter(String meterCode,String meterType) throws DataAccessException {
		
		String hql = "delete from EMeter where code = :code and type = :type";
		Query query =this.getSession().createQuery(hql);
		query.setParameter("code", meterCode);
		query.setParameter("type", meterType);
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
		
		String hql = "select m,s from EMeter m,SysCode s where s.codeValue =  m.type and m.lpId = :lpId and m.buildId is null";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpId);
		List<EMeter> list = query.list();
		return list;
	}

	@Override
	public void deleteMeterByBuildId(List<String> buildList) throws DataAccessException {
		
		Integer buildId = 0;
		Iterator<String> it = buildList.iterator();
		while(it.hasNext()){
			buildId = Integer.parseInt(it.next()) ;
			String hql = "delete from EMeter where buildId = :buildId";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("buildId", buildId);
			query.executeUpdate();
		}
	}

	@Override
	public List queryMeterByBuildId(Integer buildId)throws DataAccessException {
			
		String hql = "select m,s from EMeter m,SysCode s where s.codeValue =  m.type and m.buildId = :buildId and m.roomId is null";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("buildId", buildId);
		List<EMeter> list = query.list();
		return list;
	}

	@Override
	public List<EMeter> MeterByLpIdAndName(Integer lpId,String meterName) throws DataAccessException{

		String hql = "from EMeter where lpId = :lpId and type = :meterName and buildId is null and roomId is null";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpId);
		query.setParameter("meterName", meterName);
		List<EMeter> list = query.list();
		return list;
	}
	
	@Override
	public List<EMeter> MeterByBuildIdAndName(Integer buildId, String meterName)
			throws DataAccessException {
		
		String hql = "from EMeter where buildId = :buildId and type = :meterName and roomId is null";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("buildId", buildId);
		query.setParameter("meterName", meterName);
		List<EMeter> list = query.list();
		return list;
	}

	@Override
	public List<EMeter> queryMeterByCTP(String meterCode, String meterName,Integer parentId)
			throws DataAccessException {
		
		String hql = "from EMeter where type = :meterName and code = :meterCode and parentId = :parentId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("meterCode", meterCode);
		query.setParameter("meterName", meterName);
		query.setParameter("parentId", parentId);
		List<EMeter> list = query.list();
		return list;
	}

	@Override
	public void deleteMeterByRoomId(List<String> idList)
			throws DataAccessException {
		Integer roomId = 0;
		Iterator<String> it = idList.iterator();
		while(it.hasNext()){
			roomId = Integer.parseInt(it.next()) ;
			String hql = "delete from EMeter where roomId = :roomId";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("roomId", roomId);
			query.executeUpdate();
		}
		
	}

	@Override
	public void deleteMeterByRoomId(Integer roomId)throws DataAccessException {

		String hql = "delete from EMeter where roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		query.executeUpdate();
		
	}
	
	@Override
	public List queryMeterByRoomId(Integer roomId)
			throws DataAccessException {
		
		String hql = "select m,s from EMeter m,SysCode s where s.codeValue =  m.type and m.roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		List<EMeter> list = query.list();
		return list;
	}
}

