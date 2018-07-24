package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IPhoneCostDAO;
import com.zc13.bkmis.form.PhoneCostForm;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/**
 * 电话接口DAO
 * @author wangzw
 * @Date Oct 10, 2011
 * @Time 4:26:10 PM
 */
public class PhoneCostDAOImpl  extends BasicDAOImpl implements IPhoneCostDAO{

	//获取房间电话信息列表
	@Override
	public List getRoomPhoneInfo(PhoneCostForm form) {
		StringBuffer hql = new StringBuffer("select new Map(t2.phoneNumber as phoneNumber,t2.roomCode as roomCode,t2.roomFullName as roomFullName,t4.roomId as roomId,t4.clientId as clientId,t4.pactId as pactId,t4.clientName as clientName,t4.clientType as clientType,t4.unitName as unitName,t4.linkName as linkName) from ERooms t2,Compact t3,ERoomClient t4 ");
		hql.append(" where t4.pactId=t3.id and t3.isDestine='").append(Contants.NORMARL).append("'");
		hql.append(" and t4.roomId=t2.roomId  and ISNULL(t2.phoneNumber,'')<> '' ");
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getCxClientName()).equals("")){
				hql.append(" and t4.clientName like'%").append(form.getCxClientName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form.getCxRoomCode()).equals("")){
				hql.append(" and t2.roomCode like'%").append(form.getCxRoomCode()).append("%'");			
			}
		}
		hql.append(" order by t4.clientId ");
		Query query=this.getSession().createQuery(hql.toString());
		List roomPhoneList = null;
		roomPhoneList = query.list();
		return roomPhoneList;
	}
	
	//获取电话号码对应的通话信息
	@Override
	public List getPhoneCostInfo(PhoneCostForm form) {
		StringBuffer hql = new StringBuffer("select sum(call_time) as call_time,sum(cost) as cost from (");
		hql.append(" select t1.start_time as start_time,t1.call_time as call_time,t1.cost as cost from e_call_info t1 where 1=1 ");
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getCxPhoneNumber()).equals("")){
				hql.append(" and t1.caller_phone ='").append(form.getCxPhoneNumber()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form.getCxStartDate()).equals("")){
				hql.append(" and SUBSTRING(t1.start_time,0,11) >='").append(form.getCxStartDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxEndDate()).equals("")){
				hql.append(" and SUBSTRING(t1.start_time,0,11) <='").append(form.getCxEndDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxIsPaid()).equals("")){
				hql.append(" and t1.is_paid='").append(form.getCxIsPaid()).append("'");			
			}
		}
		hql.append(" union all  ");
		hql.append(" select t2.start_time as start_time,t2.call_time as call_time,0 as cost from e_call_info t2 where 1=1 ");
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getCxPhoneNumber()).equals("")){
				hql.append(" and t2.called_phone ='").append(form.getCxPhoneNumber()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form.getCxStartDate()).equals("")){
				hql.append(" and SUBSTRING(t2.start_time,0,11) >='").append(form.getCxStartDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxEndDate()).equals("")){
				hql.append(" and SUBSTRING(t2.start_time,0,11) <='").append(form.getCxEndDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxIsPaid()).equals("")){
				hql.append(" and t2.is_paid='").append(form.getCxIsPaid()).append("'");			
			}
		}
		hql.append(") t");
		Query query=this.getSession().createSQLQuery(hql.toString());
		List roomPhoneList = null;
		roomPhoneList = query.list();
		return roomPhoneList;
	}

	//获取通话记录信息
	@Override
	public List getCallInfo(PhoneCostForm form,boolean isPage) {
		StringBuffer hql = new StringBuffer("select t.* from (");
		hql.append("select t1.id as id,t1.start_time as start_time,t1.end_time as end_time,t1.call_time as call_time,'主叫' as call_type,t1.called_phone as called_phone,t1.cost as cost,t1.is_paid as is_paid,t1.area_name as area_name from e_call_info t1 where 1=1 ");
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getCxPhoneNumber()).equals("")){
				hql.append(" and t1.caller_phone ='").append(form.getCxPhoneNumber()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form.getCxStartDate()).equals("")){
				hql.append(" and SUBSTRING(t1.start_time,0,11) >='").append(form.getCxStartDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxEndDate()).equals("")){
				hql.append(" and SUBSTRING(t1.start_time,0,11) <='").append(form.getCxEndDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxIsPaid()).equals("")){
				hql.append(" and t1.is_paid='").append(form.getCxIsPaid()).append("'");			
			}
		}
		hql.append(" union all  ");
		hql.append(" select t2.id as id,t2.start_time as start_time,t2.end_time as end_time,t2.call_time as call_time,'被叫' as call_type,t2.caller_phone as called_phone,0 as cost,t2.is_paid as is_paid,t2.area_name as area_name from e_call_info t2 where 1=1  ");
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getCxPhoneNumber()).equals("")){
				hql.append(" and t2.called_phone ='").append(form.getCxPhoneNumber()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form.getCxStartDate()).equals("")){
				hql.append(" and SUBSTRING(t2.start_time,0,11) >='").append(form.getCxStartDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxEndDate()).equals("")){
				hql.append(" and SUBSTRING(t2.start_time,0,11) <='").append(form.getCxEndDate()).append("'");			
			}
			if(!GlobalMethod.NullToSpace(form.getCxIsPaid()).equals("")){
				hql.append(" and t2.is_paid='").append(form.getCxIsPaid()).append("'");			
			}
		}
		hql.append(") t order by t.start_time");
		Query query=this.getSession().createSQLQuery(hql.toString());
		List roomPhoneList = null;
		if(isPage){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			roomPhoneList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			roomPhoneList = query.list();
		}
		//处理list，将object[]类型转换成map
		List list = new ArrayList();
		if(roomPhoneList!=null&&roomPhoneList.size()>0){
			for(int i = 0;i<roomPhoneList.size();i++){
				Object[] objs = (Object[])roomPhoneList.get(i);
				Map map = new HashMap();
				map.put("id", objs[0]);
				map.put("startTime", objs[1]);
				map.put("endTime", objs[2]);
				map.put("callTime", objs[3]);
				map.put("callType", objs[4]);
				map.put("phone", objs[5]);
				map.put("cost", objs[6]);
				map.put("isPaid", objs[7]);
				map.put("areaName", objs[8]);
				list.add(map);
			}
		}
		
		return list;
	}

	//获取运营商信息
	@Override
	public List getServiceProviderInfo(PhoneCostForm form1, boolean b) {
		StringBuffer hql = new StringBuffer("from CServiceProvider");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}

	//获取电话资费标准列表
	@Override
	public List getPhoneParameterInfo(PhoneCostForm form1, boolean b) {
		StringBuffer hql = new StringBuffer("from CPhoneParameter where 1=1 ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getCxPrefix()).equals("")){
				hql.append(" and prefix like'%").append(form1.getCxPrefix()).append("%'");
			}
		}
		hql.append(" order by id");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}
	
	//获取区号列表
	@Override
	public List getRegionCodeInfo(PhoneCostForm form1, boolean b) {
		StringBuffer hql = new StringBuffer("from CRegionCode where 1=1 ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getCxAreaCode()).equals("")){
				hql.append(" and areaCode like'%").append(form1.getCxAreaCode()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxAreaName()).equals("")){
				hql.append(" and areaName like'%").append(form1.getCxAreaName()).append("%'");
			}
		}
		hql.append(" order by areaCode");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}

	@Override
	public List getCallInfoObj(PhoneCostForm form1, boolean b) {
		StringBuffer hql = new StringBuffer("from ECallInfo t2 where 1=1 ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getCxStartDate()).equals("")){
				hql.append(" and SUBSTRING(t2.startTime,0,11) >='").append(form1.getCxStartDate()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndDate()).equals("")){
				hql.append(" and SUBSTRING(t2.startTime,0,11) <='").append(form1.getCxEndDate()).append("'");
			}
		}
		hql.append(" order by t2.id");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}
	
	/**
	 * 每个账单对应的通话记录和费用列表
	 * @param form
	 * @param isPage
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:11:47:21 AM
	 */
	public List getCallInfoList(PhoneCostForm form,boolean isPage) {
		StringBuffer hql = new StringBuffer("from ECallInfo t2 where rootUser="+form.getId());
		hql.append(" order by t2.callerPhone");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		if(isPage){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		return list;
	}
}
