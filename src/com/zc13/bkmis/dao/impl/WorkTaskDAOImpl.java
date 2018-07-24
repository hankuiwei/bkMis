package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IWorkTaskDAO;
import com.zc13.bkmis.form.WorkTaskForm;
import com.zc13.bkmis.mapping.CompactTask;
import com.zc13.util.GlobalMethod;

public class WorkTaskDAOImpl extends BasicDAOImpl  implements IWorkTaskDAO {

	@Override
	public List<CompactTask> getWorkTaskList(WorkTaskForm workForm) throws DataAccessException {
		// TODO Auto-generated method stub
		String hql = "select c from CompactTask c ,Compact c2 where c2.id = c.compactId ";
		String roomString = workForm.getCxRooms();
		String clientString = workForm.getCxClientName();
		String noteDateString = workForm.getCxNoteDate();
		String endDateString = workForm.getCxNoteDateEnd();
		if(!"".equals(roomString) && null != roomString){
			hql += " and c.rooms like '%"+roomString+"%'";
		}
		if(!"".equals(clientString) && null != clientString){
			hql += " and c.clientName like '%"+clientString+"%'";
		}
		if(!"".equals(noteDateString) && null != noteDateString){
			hql += " and c.noteDate >= '"+noteDateString+"'";
		}
		if(!"".equals(endDateString) && null != endDateString){
			hql += " and c.noteDate <= '"+endDateString+"'";
		}
		if(workForm.getLpId()!=null&&workForm.getLpId()!=0){
			hql += " and c.lpId = "+workForm.getLpId();
		}
		hql += " order by c.id desc";
		Query query = this.getSession().createQuery(hql);
		int count = 0;
		try{
			List countList = query.list();
			//获得记录总数
			if(countList!=null){
				count = countList.size();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//每页显示的条数，空的情况下默认是10
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(workForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(workForm.getCurrentpage(),"1")) - 1);
		List<CompactTask> list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		
	
		if(list!=null&&list.size()>0){
			//将记录总数保存到list的第一个对象中
			list.get(0).setTotalcount(count);
		}
		
		return list;
	}

	
}
