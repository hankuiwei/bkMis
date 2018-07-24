package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IDestineDAO;
import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class DestineDAOImpl extends BasicDAOImpl implements IDestineDAO{

	@Override
	public void getList(DestineForm destineForm) {
		
		// TODO Auto-generated method stub
		String currentpage = destineForm.getCurrentpage();
		String pagesize = destineForm.getPagesize();
		String clientName = destineForm.getClientName();
		String roomCode = destineForm.getRoomCode();
		String beginDate = destineForm.getBeginDate();
		String endDate = destineForm.getEndDate();
		String code = destineForm.getCode();
		Integer lpId = destineForm.getLpId();
		String status = destineForm.getStatus();
		String isE = destineForm.getIsEarnest();
		//对于预入驻界面合同列表的过滤条件，默认只有一个，那就是isDestine是“预租”。
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and isDestine = '"+Contants.DESTINES+"' ");
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and beginDate >= '"+beginDate+"'");	
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and beginDate <= '"+endDate+"'");	
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");	
		}
		if(code!=null&&!"".equals(code)){
			hql.append(" and code like '%"+code+"%'");	
		}
		if(lpId!=null&& lpId!=0){
			hql.append(" and lpId = "+lpId);	
		}
		if(status!=null&&!"".equals(status)){
			hql.append(" and status = '"+status+"'");	
		}
		if(isE!=null&&!"".equals(isE)){
			hql.append(" and isEarnest = '"+isE+"'");	
		}
		hql.append(" order by code");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		destineForm.setDestineList(list);
	}


	@Override
	public List<CompactClient> getClient(String code)
			throws DataAccessException {

		String hql = "from CompactClient where code='"+code+"'";
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public int getCount(DestineForm destineForm) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		int i = 0;
		String clientName = destineForm.getClientName();
		String roomCode = destineForm.getRoomCode();
		String beginDate = destineForm.getBeginDate();
		String endDate = destineForm.getEndDate();
		Integer lpId = destineForm.getLpId();
		String status = destineForm.getStatus();
		String isE = destineForm.getIsEarnest();
		
		
		StringBuffer hql = new StringBuffer("from Compact where 1=1  and isDestine = '"+Contants.DESTINES+"' ");
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and beginDate >= '"+beginDate+"'");	
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and beginDate <= '"+endDate+"'");	
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");	
		}
		if(lpId!=null && lpId!=0){
			hql.append(" and lpId = "+lpId);	
		}
		if(status!=null&&!"".equals(status)){
			hql.append(" and status = '"+status+"'");	
		}
		if(isE!=null&&!"".equals(isE)){
			hql.append(" and isEarnest = '"+isE+"'");	
		}
		hql.append(" order by code desc");
		
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).list();
		if(list!=null){
			i = list.size();
		}
		return i;
	}

	//根据合同id得到租金列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRent> getRentList(int id) throws DataAccessException {
	
		String hql = "from CompactRent where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRent>)query.list();
	}


	//根据客户id查询所租的房间列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ERoomClient> getRoomListByClientId(int id,int compactId)
			throws DataAccessException {
		
		String hql = "select ec from ERoomClient ec,ERooms er where ec.roomId=er.roomId and ec.clientId="+id+"and ec.pactId="+compactId +"order by er.roomCode ";
		Query query = this.getSession().createQuery(hql);
		return (List<ERoomClient>)query.list();
	}


	//根据合同id查询房间对应的收费标准列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRoomCoststandard> getStandardListByCompactId(int id)
			throws DataAccessException {
		
		String hql = "select t from CompactRoomCoststandard t left outer join t.ERooms e where t.compact.id="+id+" and t.display is Null order by e.roomCode";
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRoomCoststandard>)query.list();
	}
	//根据合同id得到收费标准列表
	@Override
	public List<CompactRent> getCompactRent(int id)
			throws DataAccessException {
		
		String hql = "from CompactRent where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRent>)query.list();
	}
	//根据合同id得到房租信息列表，与getStandardListByCompactId方法的区别：取所有的，用于更新合同时删除所有收费标准前的获取，和正式入住时更新所有的status
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRoomCoststandard> getCompactRoomCoststandard(int id)
			throws DataAccessException {
		
		String hql = "from CompactRoomCoststandard where pact_id="+id+" ";
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRoomCoststandard>)query.list();
	}
	//根据客户id得到房间客户列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ERoomClient> getERoomClient(int id)
			throws DataAccessException {
		
		String hql = "from ERoomClient where clientId="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<ERoomClient>)query.list();
	}
	//查询客户列表，导出报表
	public List<Compact> explorDestineList(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate,String isEarnest)
			throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from Compact where 1 = 1 and isDestine = '"+Contants.DESTINES+"' ");
		if(lpId!=null && Integer.parseInt(lpId)!=0){
			hql.append(" and lpId ="+lpId);
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and clientName like '%"+clientName+"%'");
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");
		}
		if(status!=null&&!"".equals(status)){
			hql.append(" and status like '%"+status+"%'");
		}
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and inDate like '%"+beginDate+"%'");
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and inDate like '%"+endDate+"%'");
		}
		if(isEarnest!=null&&!"".equals(isEarnest)){
			hql.append(" and isEarnest = '"+isEarnest+"'");
		}
		Query query = this.getSession().createQuery(hql.toString());
		List<Compact> destineList = query.list();
		return destineList;
	}
}
