package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IDepotSupplierDAO;
import com.zc13.bkmis.form.DepotSupplierForm;
import com.zc13.bkmis.mapping.DepotSupplier;
import com.zc13.util.GlobalMethod;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 21, 2010
 * Time：3:23:19 PM
 */
public class DepotSupplierDAOImpl extends BasicDAOImpl implements IDepotSupplierDAO {

	//查询供应商信息
	public List selectSupplierList(DepotSupplierForm dsf) throws DataAccessException{
		
		String name = GlobalMethod.NullToSpace(dsf.getName());
		String fullName = GlobalMethod.NullToSpace(dsf.getFullName());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(dsf.getLpId()));
		
		String hql = "from DepotSupplier where 1=1 ";
		if(!"".equals(name) && null != name){
			hql += " and name like '%"+name+"%'";
		}
		if(!"".equals(fullName) && null != fullName){
			hql += " and fullName like '%"+fullName+"%'";
		}
		hql += " order by id desc ";
		hql += " and lpId = "+ lpId ;
		Query query = this.getSession().createQuery(hql);
		List supplierList = new ArrayList();
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(dsf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(dsf.getCurrentpage(),"1")) - 1);
		supplierList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return supplierList;
	}

	//查询供应商记录总条数用于分页
	public int queryCount(DepotSupplierForm dsf) {
		
		int count = 0;
		String name = GlobalMethod.NullToSpace(dsf.getName());
		String fullName = GlobalMethod.NullToSpace(dsf.getFullName());
		
		String hql = "select count(ds) from DepotSupplier as ds where 1=1 ";
		if(!"".equals(name) && null != name){
			hql += " and name like '%"+name+"%'";
		}
		if(!"".equals(fullName) && null != fullName){
			hql += " and fullName like '%"+fullName+"%'";
		}
		Query query = this.getSession().createQuery(hql);
		List countList = new ArrayList();
		try{
			countList = query.list();
			count = (Integer)countList.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	//查询要修改的信息
	public List editSupplier(int id) {
		
		String hql = "from DepotSupplier where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List supplierList = new ArrayList();
		supplierList = query.list();
		return supplierList;
	}

	//按id查询要删除的供应商的信息
	public List<DepotSupplier> selectSupplier(Integer[] idArray) {
		
		String hql = "from DepotSupplier where 1=1";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List supplierNameList = this.getHibernateTemplate().find(hql, idArray);
		return supplierNameList;
	}

}
