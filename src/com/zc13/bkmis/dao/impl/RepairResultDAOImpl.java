package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IRepairResultDAO;
import com.zc13.bkmis.form.RepairResultForm;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class RepairResultDAOImpl extends BasicDAOImpl implements IRepairResultDAO {
	
//获取客户报修列表
@SuppressWarnings("unchecked")
@Override
public List<RepairResult> queryRepairResult(RepairResultForm repairResultForm,boolean isPage) throws DataAccessException {
		
		String hql = "from RepairResult order by id"; 
		   //每页显示的条数，空的情况下默认是10
		List<RepairResult> list=null; 
	
		
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(repairResultForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(repairResultForm.getCurrentpage(),"1")) - 1);
			list = (List<RepairResult>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<RepairResult>)query.list();
		}
		return list;
		
	}

//获取客户报修列表 
public int queryCounttotal(RepairResultForm repairResultForm){
	int count = 0;
	String hql = "select count(r) from RepairResult as r ";
	
	try{
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		count = (Integer)list.get(0);
	}catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}

}
