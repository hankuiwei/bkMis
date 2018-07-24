package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IExistDepotManageDAO;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:59:12 PM
 */
public class ExistDepotManageDAOImpl extends BasicDAOImpl implements IExistDepotManageDAO {

	//获取树形图
	public List getMaterialsList() {
		String hql = "select dst from DepotMaterialType as dst";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;	
	}

}
