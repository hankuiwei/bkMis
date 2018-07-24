package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IRepairDAO;
import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.util.GlobalMethod;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class RepairDAOImpl extends BasicDAOImpl implements IRepairDAO {

	//获取客户报修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<SerMaintainProject> getRepairList(RepairForm repairForm,boolean isPage)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from SerMaintainProject where 1 = 1");
		String pagesize = null;
		String currentpage = null;
		if(repairForm!=null){
			//name的模糊查询
			if(!GlobalMethod.NullToSpace(repairForm.getProjectname()).equals("")){
				hql.append(" and name like '%"+repairForm.getProjectname()+"%'");
			}
			//name的精确查询
			if(!GlobalMethod.NullToSpace(repairForm.getName()).equals("")){
				hql.append(" and name = '"+repairForm.getName()+"'");
			}
			if(!GlobalMethod.NullToSpace(repairForm.getCode()).equals("")){
				hql.append(" and code = '"+repairForm.getCode()+"'");
			}
			if(repairForm.getLpId()!=null&&repairForm.getLpId()!=0){
				hql.append(" and lpId = "+repairForm.getLpId());
			}
			pagesize = repairForm.getPagesize();
			currentpage = repairForm.getCurrentpage();
		}
		
		hql.append(" order by id");
		List<SerMaintainProject> list = null;
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
			list = (List<SerMaintainProject>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<SerMaintainProject>)query.list();
		}
		return list;
	}

	@Override
	public List<EBuilds> buildList(int id) throws DataAccessException {
		String hql = "from EBuilds where LP_id = "+id;
		Query query = this.getSession().createQuery(hql);
		List<EBuilds> list = (List<EBuilds>)query.list();
		return list;
	}
	
	@Override
	public List<ERooms> roomList(int id) throws DataAccessException {
		String hql = "from ERooms where build_id = "+id;
		Query query = this.getSession().createQuery(hql);
		List<ERooms> list = (List<ERooms>)query.list();
		return list;
	}

}
