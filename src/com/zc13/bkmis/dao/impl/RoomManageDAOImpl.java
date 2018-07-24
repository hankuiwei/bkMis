package com.zc13.bkmis.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IRoomManageDAO;
import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * @author 侯哓娟
 * Date：Nov 23, 2010
 * Time：5:16:13 PM
 */
public class RoomManageDAOImpl extends BasicDAOImpl implements IRoomManageDAO {

	@Override
	public List<SysCode> queryRoomType(Integer lpId) throws DataAccessException {
		
		String hql = "from SysCode where codeType = 'roomType'"; 
		if(lpId!= null && lpId!=0){
			hql += " and lpId="+lpId;
		}
		Query query = this.getSession().createQuery(hql);
		List<SysCode> list = query.list();
		return list;
	}
	public List<SysCode> queryToward(Integer lpId) throws DataAccessException {
		
		String hql = "from SysCode where codeType = 'toward'"; 
		if(lpId!= null && lpId!=0){
			hql += " and lpId="+lpId;
		}
		Query query = this.getSession().createQuery(hql);
		List<SysCode> list = query.list();
		return list;
	}
	public List<SysCode> queryStatus(Integer lpId) throws DataAccessException {
		
		String hql = "from SysCode where codeType = 'status'"; 
		if(lpId!= null && lpId!=0){
			hql += " and lpId="+lpId;
		}
		Query query = this.getSession().createQuery(hql);
		List<SysCode> list = query.list();
		return list;
	}
	@Override
	public List<ERooms> queryRoom(RoomForm roomForm) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("select r from ERooms r where 1=1 ");
		if(roomForm!=null){
			Integer lpId = roomForm.getLpId();
			if(lpId!= null && lpId!=0){
				hql.append(" and lpId="+lpId);
			}
			if(roomForm.getLpId()!=null&&roomForm.getLpId()!=0){
				hql.append(" and r.EBuilds.ELp.lpId=").append(roomForm.getLpId());
			}
			if(roomForm.getBuildId()!=null&&roomForm.getBuildId()!=0){
				hql.append(" and r.EBuilds.buildId=").append(roomForm.getBuildId());
			}
			if(!GlobalMethod.NullToSpace(roomForm.getRoomCode()).equals("")){
				hql.append(" and r.roomCode like '%").append(roomForm.getRoomCode()).append("%' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getFloor()).equals("")){
				hql.append(" and r.floor='").append(roomForm.getFloor()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getRoomType()).equals("")){
				hql.append(" and r.roomType='").append(roomForm.getRoomType()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getToward()).equals("")){
				hql.append(" and r.toward='").append(roomForm.getToward()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getStatus()).equals("")){
				hql.append(" and r.status='").append(roomForm.getStatus()).append("' ");
			}
			if(roomForm.getSuseArea()!=0.0){
				hql.append(" and r.useArea >= ").append(roomForm.getSuseArea());
			}
			if(roomForm.getGuseArea()!=0.0){
				hql.append(" and r.useArea <= ").append(roomForm.getGuseArea());
			}
		}
		hql.append(" order by r.EBuilds.buildId,r.roomCode");
		
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")) - 1);
		Query query = this.getSession().createQuery(hql.toString());
		List<ERooms> list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return list;
	}
	
	@Override
	public List<ERooms> queryRoomE(RoomForm roomForm) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("select r from ERooms r where 1=1 ");
		if(roomForm!=null){
			if(roomForm.getLpId()!=null&&roomForm.getLpId()!=0){
				hql.append(" and r.EBuilds.ELp.lpId=").append(roomForm.getLpId());
			}
			if(roomForm.getBuildId()!=null&&roomForm.getBuildId()!=0){
				hql.append(" and r.EBuilds.buildId=").append(roomForm.getBuildId());
			}
			if(!GlobalMethod.NullToSpace(roomForm.getRoomCode()).equals("")){
				hql.append(" and r.roomCode like '%").append(roomForm.getRoomCode()).append("%' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getFloor()).equals("")){
				hql.append(" and r.floor='").append(roomForm.getFloor()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getRoomType()).equals("")){
				hql.append(" and r.roomType='").append(roomForm.getRoomType()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getToward()).equals("")){
				hql.append(" and r.toward='").append(roomForm.getToward()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(roomForm.getStatus()).equals("")){
				hql.append(" and r.status='").append(roomForm.getStatus()).append("' ");
			}
			if(roomForm.getSuseArea()!=0.0){
				hql.append(" and r.useArea >= ").append(roomForm.getSuseArea());
			}
			if(roomForm.getGuseArea()!=0.0){
				hql.append(" and r.useArea <= ").append(roomForm.getGuseArea());
			}
		}
		hql.append(" order by r.id");
		Query query = this.getSession().createQuery(hql.toString());
		List<ERooms> list = query.list();
		return list;
	}
	
	public List<ViewBuild> queryInfoForTree(RoomForm roomForm){
		
		Integer lpId = roomForm.getLpId();
		String hql = "from ViewBuild b ";
		if(lpId!=null && lpId != 0){
			hql += " where b.id.lpId = "+lpId;
		}
		List<ViewBuild> list = findObject(hql);
		return list;
	}
	@Override
	/* 根据楼盘查询房间 */
	public List<ERooms> queryRoomforLp(ELp elp,RoomForm roomForm) throws DataAccessException {
		
		//查询出一个楼盘下所有的房间
		String hql = "select r from ERooms r,EBuilds b where r.EBuilds = b.buildId and b.ELp = :elp order by r.roomCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("elp", elp);
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")) - 1);
		List<ERooms> list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return list;
	}
	
	@Override
	 public List<ERooms> queryRoomNumforLp(ELp elp)throws DataAccessException{
		
		//根据楼盘查询出楼栋
		String hql = "from EBuilds where ELp = :elp";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("elp", elp);
		List<EBuilds> blist = query.list();
		//
		List<ERooms> list = new ArrayList();
		for(EBuilds b:blist){
			//得到楼栋id
			Integer buildId = b.getBuildId();
			EBuilds ebuild = (EBuilds)super.getObject(EBuilds.class, buildId);
			List<ERooms> alist = queryRoomNumByBuild(ebuild);
			list.addAll(alist);//将根据每个楼栋id查询出来的list添加到一个里面
			//count得到的是一个一个值。所以把各个list添加到一个里面之后是会是每个list的值，而不是给合并为一个count值.
		}
		return list;
	 }
	
	@Override
	/* 根据楼栋查询房间 */
	public List<ERooms> queryRoomByBuild(Integer id,RoomForm roomForm) throws DataAccessException {
		
		EBuilds ebuild = new EBuilds();
		ebuild.setBuildId(id);
		ERooms eroom = new ERooms();
		//保存一个ebuild对象到eroom中
		eroom.setEBuilds(ebuild);
		String hql = "from ERooms where EBuilds = :ebuild order by roomId" ;
	    Query query = this.getSession().createQuery(hql);
	    query.setParameter("ebuild", ebuild);
	    
	  //每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")) - 1);
		List<ERooms> list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return list;
	}
	
	/** 获取楼栋下的房间总数 */ 
    public List<ERooms> queryRoomNumByBuild(EBuilds ebuild)throws DataAccessException{
    	
		String hql = " select count(r) from ERooms as r where EBuilds = :ebuild" ;
	    Query query = this.getSession().createQuery(hql);
	    query.setParameter("ebuild", ebuild);
	    List<ERooms> list = query.list();
		return list;
    }
	
	@Override
	public List<ERooms> queryRoomByRoomCode(String roomCode,EBuilds ebuild) throws DataAccessException {
		
		String hql = "from ERooms where roomCode = :roomCode and EBuilds = :ebuild order by roomId desc";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomCode", roomCode);
		query.setParameter("ebuild", ebuild);
		List<ERooms> list = query.list();
		return list;
	}
	
	@Override
	public List getRoomClientInfo(Integer roomId) throws DataAccessException{
		
		/* 得到系统的当前时间 */
		String currentTime =  GlobalMethod.getTime();
		/* 查询一个房间对象和一个客户房间关系对象 */
		String hql = "select r,ec from ERooms r,ERoomClient ec where ec.endDate >= '" + currentTime + "' and ec.roomId = r.roomId and r.roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		List list = query.list();
		return list;
	}
	
	@Override
	public void deleteRoom(List<String> idList) throws DataAccessException {
		
		Integer roomId = 0;
		Iterator<String> it = idList.iterator();
		while(it.hasNext()){
			roomId = Integer.parseInt(it.next());
			String hql = "delete from ERooms where roomId = :roomId";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("roomId", roomId);
			query.executeUpdate();
		}
	}
	
	@Override
	public void deleteEquip(List<String> idList) throws DataAccessException {
		Integer roomId = 0;
		Iterator<String> it = idList.iterator();
		while(it.hasNext()){
			roomId = Integer.parseInt(it.next());
			String hql = "delete from ERoomFixture where roomId = :roomId";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("roomId", roomId);
			query.executeUpdate();
		}
	}
	
	@Override
	public void deleteEquip(Integer roomId) throws DataAccessException {
			
		String hql = "delete from ERoomFixture where roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		query.executeUpdate();
	}

	
	 public int queryCounttotal(RoomForm roomForm)throws DataAccessException{
		 
		 int count = 0;
		 String hql;
		 ERooms eroom = new ERooms();
			try {
				BeanUtils.copyProperties(eroom,roomForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			/* 按照楼盘，楼栋的查询 */
			Integer lpId = roomForm.getLpId();
			Integer buildId = roomForm.getBuildId();
			//面积的处理
			float suseArea = roomForm.getSuseArea();
			float guseArea = roomForm.getGuseArea();
			
			String roomCode = eroom.getRoomCode();
			String floor = eroom.getFloor();
			String roomType = eroom.getRoomType();
			String toward = eroom.getToward();
			String status = eroom.getStatus();
			
			if(lpId != null && lpId != 0){
			    hql = "select count(r) from ERooms r,EBuilds b where 1=1"; 
				hql += " and r.EBuilds.buildId = b.buildId and b.ELp.lpId = :lpId";
			}else{
				hql = "select count(r) from ERooms r where 1=1"; 
			}
			if(buildId != null && buildId != 0){
				hql += " and r.EBuilds.buildId = :buildId"; 
			}
			if(roomCode != "" && roomCode != null){
				hql += " and r.roomCode like :roomCode";
			}
			if(floor != null && floor != ""){
				hql += " and r.floor = :floor"; 
			}
			if(roomType != "" && roomType != null){
				hql += " and r.roomType = :roomType";
			}
			
			
			//面积的处理
			if(suseArea != 0.0){
				hql += " and r.useArea >= :suseArea";
			}
			if(guseArea != 0.0){
				hql += " and r.useArea <= :guseArea";
			}
			
			
			if(toward != "" && toward != null){
				hql += " and r.toward = :toward";
			}
			if(status != "" && status != null){
				hql += " and r.status = :status";;
			}
			
			Query query = this.getSession().createQuery(hql);
			
			if(lpId != null && lpId != 0){
				query.setParameter("lpId", lpId);
			}
			if(buildId != null && buildId != 0){
				query.setParameter("buildId", buildId);
			}
			if(roomCode != "" && roomCode != null){
				query.setParameter("roomCode", "%"+roomCode+"%");
			}
			if(floor != null && floor != ""){
				query.setParameter("floor", floor);
			}
			if(roomType != "" && roomType != null){
				query.setParameter("roomType", roomType);
			}
			
			if(suseArea != 0.0){
				query.setParameter("suseArea", suseArea);
			}
			if(guseArea != 0.0){
				query.setParameter("guseArea", guseArea);
			}
			
			
			if(toward != "" && toward != null){
				query.setParameter("toward", toward);
			}
			if(status != "" && status != null){
				query.setParameter("status", status);
			}
		 
		  List list = query.list();
		  count = (Integer)list.get(0);
		  return count;
	 }
	
	@Override
	public int queryCounttotal(Integer roomId) throws DataAccessException{
		
		int count = 0;
		String hql = "select count(r) from ERoomClient as r where roomId = :roomId ";
		try{
			Query query = this.getSession().createQuery(hql);
			query.setParameter("roomId", roomId);
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List queryEquipByRoomId(Integer roomId) throws DataAccessException {
		
		/* hql实现多个对象的拼接查询 
		 * 根据房间id得到设备的相关信息 */
		String hql = "select er, de ,dt from ERoomFixture er ,DepotMaterial de ,DepotMaterialType dt where dt.code = de.type and de.id = er.fixtureId and er.roomId = :roomId ";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		List list = query.list();
		return list;
	}
	
	@Override
	public int getRoomNum(Integer lpId,Integer buildId, String floor,String roomStatus) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("select count(r) from ERooms as r where 1=1 ");
		if(lpId!=null){
			hql.append(" and r.EBuilds.ELp.lpId=").append(lpId);
		}
		if(buildId!=null){
			hql.append(" and r.EBuilds.buildId=").append(buildId);
		}
		if(!GlobalMethod.NullToSpace(floor).equals("")){
			hql.append(" and r.floor='").append(floor).append("'");
		}
		if(!GlobalMethod.NullToSpace(roomStatus).equals("")){
			hql.append(" and r.status='").append(roomStatus).append("'");
		}
		
		Query query = this.getSession().createQuery(hql.toString());
		List list = query.list();
		int roomnum = (Integer)list.get(0);
		return roomnum;
	}
	@Override
	public List getRoomInfo(Integer lpId,Integer buildId, String floor)throws DataAccessException {
		
		/* 查询预租和正在租住的房间及客户 */
		StringBuffer hql = new StringBuffer("select r,ec from ERooms r,ERoomClient ec,Compact c ");
		hql.append(" where r.roomId=ec.roomId and c.id=ec.pactId and c.isDestine in('").append(Contants.DESTINES).append("','").append(Contants.NORMARL).append("') ");
		if(lpId!=null){
			hql.append(" and r.EBuilds.ELp.lpId=").append(lpId);
		}
		if(buildId!=null){
			hql.append(" and r.EBuilds.buildId=").append(buildId);
		}
		if(!GlobalMethod.NullToSpace(floor).equals("")){
			hql.append(" and r.floor='").append(floor).append("'");
		}
		hql.append(" order by ec.clientId,r.status,r.roomId");
		Query query = this.getSession().createQuery(hql.toString());
		List list = query.list();
		
		return list;
	}
	@Override
	public List<ERoomClient> getHistoryClientInfo(Integer roomId)
			throws DataAccessException {
		
		String hql = "from ERoomClient where roomId = :roomId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("roomId", roomId);
		List<ERoomClient> list = query.list();
		return list;
	}
	@Override
	public List<ERooms> getRoomByStatus(String status)
			throws DataAccessException {
		
		String hql = "from ERooms where status = :status";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("status", status);
		List<ERooms> list = query.list();
		return list;
	}
	@Override
	public List<ERooms> getRoomsByEBuildsAndFloor(String floor, EBuilds build)throws DataAccessException {
			
		String hql = "from ERooms where EBuilds = :build and floor = :floor";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("build", build);
		query.setParameter("floor", floor);
		List<ERooms> list = query.list();
		return list;
	}
}
