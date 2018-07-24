package com.zc13.bkmis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IBuildsManageDAO;
import com.zc13.bkmis.form.BuildForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EFloorMap;
import com.zc13.bkmis.mapping.EFloorMapCoordinate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：2:17:07 PM
 */
public class BuildsManageDAOImpl extends BasicDAOImpl implements IBuildsManageDAO {

	public List<EBuilds> queryBuildByName(String buildName)
			throws DataAccessException {
		String hql = "from EBuilds where buildName = :buildName";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("buildName", buildName);
		List<EBuilds> list = query.list();
		return list; 
	}


	@Override
	public List<EBuilds> queryBuildsByELp(ELp ELp,String buildName) throws DataAccessException {
		
		String hql = "from EBuilds where 1= 1 ";
		if(ELp != null){
			hql += " and ELp = :ELp  ";
		}
		if(!"".equals(buildName) && buildName != null){
			hql += " and buildName like '%" + buildName + "%'" ;
		}
		hql += "order by buildCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("ELp", ELp);
		List<EBuilds> list = query.list();
		return list;
	}

	@Override
	public List<EBuilds> queryBuildsByELpAndCode(ELp ELp,String buildCode) throws DataAccessException {
		
		String hql = "from EBuilds where ELp = :ELp and buildCode = :buildCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("ELp", ELp);
		query.setParameter("buildCode", buildCode);
		List<EBuilds> list = query.list();
		return list;
	}
	
	@Override
	public List<EBuilds> queryBuildByELpAndName(ELp ELp,String buildName) throws DataAccessException {
		
		String hql = "from EBuilds where ELp = :ELp and buildName = :buildName";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("ELp", ELp);
		query.setParameter("buildName", buildName);
		List<EBuilds> list = query.list();
		return list;
	}
	
	@Override
	public List<EBuilds> queryBuildByCLN(String buildCode,ELp ELp,String buildName) throws DataAccessException {
		
		String hql = "from EBuilds where ELp = :ELp and buildCode = :buildCode and buildName = :buildName";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("ELp", ELp);
		query.setParameter("buildCode", buildCode);
		query.setParameter("buildName", buildName);
		List<EBuilds> list = query.list();
		return list;
	}
	@Override
	public List<EBuilds> queryBuild(BuildForm buildForm) throws DataAccessException {
			
		Integer lpId = buildForm.getLpId();
		String buildName = buildForm.getBuildName();
		String hql = "from EBuilds where 1=1 ";
		/* 用于有查询条件的情况 */
		if(lpId != 0){
			hql += " and ELp.lpId = :lpId ";
		}
		if(!"".equals(buildName) && buildName != null){
			hql += " and buildName like '%" + buildName + "%'" ;
		}
		hql += " order by buildCode";
		Query query = this.getSession().createQuery(hql);
		if(lpId != 0){
			query.setParameter("lpId", lpId);
		}
		
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(buildForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(buildForm.getCurrentpage(),"1")) - 1);
		List<EBuilds> list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		
		return list;
	}

	@Override
	public int queryCounttotal(BuildForm buildForm) {
		Integer lpId = buildForm.getLpId();
		String buildName = buildForm.getBuildName();
		int count = 0;
		String hql = "select count(b) from EBuilds as b where 1=1 ";
		/* 用于有查询条件的情况 */
		if(lpId != null && lpId != 0){
			hql += " and b.ELp.lpId = :lpId ";
		}
		if(!"".equals(buildName) && buildName != null){
			hql += " and b.buildName like '%" + buildName + "%'" ;
		}
		try{
			Query query = this.getSession().createQuery(hql);
			if(lpId != null && lpId != 0){
				query.setParameter("lpId", lpId);
			}
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<EFloorMap> getEfloorMap(EFloorMap floorMap)
			throws DataAccessException {
		List<EFloorMap> list = null;
//		String hql = "from EFloorMap where floor = :floor and build_id = "+buildId1;
		StringBuffer hql = new StringBuffer("from EFloorMap e where 1=1");
		if(floorMap!=null){
			if(floorMap.getId()!=null){
				hql.append(" and e.id=").append(floorMap.getId());
			}
			if(floorMap.getELp()!=null&&floorMap.getELp().getLpId()!=null){
				hql.append(" and e.ELp.lpId=").append(floorMap.getELp().getLpId());
			}
			if(floorMap.getEBuilds()!=null&&floorMap.getEBuilds().getBuildId()!=null){
				hql.append(" and e.EBuilds.buildId=").append(floorMap.getEBuilds().getBuildId());
			}
			if(!GlobalMethod.NullToSpace(floorMap.getFloor()).equals("")){
				hql.append(" and e.floor='").append(floorMap.getFloor()).append("'");
			}
			if(!GlobalMethod.NullToSpace(floorMap.getType()).equals("")){
				hql.append(" and e.type='").append(floorMap.getType()).append("'");
			}
		}
		Query query = this.getSession().createQuery(hql.toString());
		list = query.list();
		return list;
	}

	@Override
	public Map queryPSize(String psize) throws DataAccessException {
		
		String size = null;
		Map map = new HashMap();
		String hql = "from SysCode where codeType = 'size'"; 
		if(!"".equals(psize) ){
			hql += "and codeName = '" + psize + "'"; 
		}
		Query query = this.getSession().createQuery(hql);
		List<SysCode> list = query.list();
		for(SysCode s:list){
			//如果查出图片大小为空，则给size一默认值
			if(s.getCodeValue() == null){
				size = "700-500";
			}else{
				size = s.getCodeValue();
			}
		}
		String[] sArray = size.split("-");
		map.put("width", sArray[0]);
		map.put("height",sArray[1]);
		
		return map;
	}


	@Override
	public List getRoomMap(EFloorMap chart) throws DataAccessException {
		
		String hql = "from EFloorMapCoordinate where EFloorMap = :chart";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("chart", chart);
		List<EFloorMapCoordinate> list = query.list();
		return list;
	}

	@Override
	public void deleteRoomMap(Integer roomId) throws DataAccessException {

		String hql = "delete from EFloorMapCoordinate where roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		query.executeUpdate();
	}

	@Override
	public List<EFloorMapCoordinate> getRoomMap(Integer roomId) throws DataAccessException {

		String hql = "from EFloorMapCoordinate where roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		List<EFloorMapCoordinate> list = query.list();
		return list;
	}

	@Override
	public void deleteRMeter() throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		String hqlString = "delete from EMeter where lpId = 0 and buildId = 0 and roomId = 0 ";
		this.getSession().createQuery(hqlString).executeUpdate();
	}


	@Override
	public List<Map> getClientAndRoomById(Integer roomId) {
		StringBuffer hql = new StringBuffer("select new Map(room.status as status,client.name as client_name,client.id as client_id,client.unitName as unit_name) from ERooms room,CompactClient client,ERoomClient erc ");
		hql.append(" where room.roomId=erc.roomId and client.id=erc.clientId ");
		hql.append(" and client.id in(select c.clientId from Compact c where c.isDestine in('").append(Contants.DESTINES).append("','").append(Contants.NORMARL).append("'))");
		hql.append(" and room.roomId=").append(roomId);
		return this.getSession().createQuery(hql.toString()).list();
	}

}
