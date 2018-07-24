package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IDepotDAO;
import com.zc13.bkmis.form.TypeForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
/**
 * 
 * @author Administrator
 * Date：Dec 3, 2010
 * Time：9:05:25 AM
 */
public class DepotDAOImpl extends BasicDAOImpl implements IDepotDAO {

	//获取树形参数
	public List getMaterialsList() {
		String hql = "select dst from DepotMaterialType as dst";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}

	//通过id获取子节点（这个“子节点”不是指当前节点的子节点，而是当前节点就已经子节点，获取的就是当前节点的内容）内容
	public List getChildList(int id) {
		String hql = "from DepotMaterialType where id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List list = query.list();
		return list;
	}

	//修改类别信息
	public void editType(DepotMaterialType dsf) {
		super.updateObject(dsf);
	}

	//删除材料类别
	public void delType(DepotMaterialType dsf) {
		super.deleteObject(dsf);
	}

	//添加材料类别
	public void addType(DepotMaterialType dsf) {
		// TODO Auto-generated method stub
		super.saveObject(dsf);
	}

	//检验要添加及编辑的类别名称
	public DepotMaterialType checkAddName(String name) {
		String hql = "from DepotMaterialType where name=:name";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("name", name);
		return (DepotMaterialType)query.uniqueResult();
	}

	//检查要添加的类别代码
	public DepotMaterialType checkAddCode(String code) {
		String hql = "from DepotMaterialType where code=:code";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return (DepotMaterialType)query.uniqueResult();
	}

	//更新材料信息中材料类别
	public void updateMaterialCode(String oldCode, DepotMaterialType dsf) {
		
		String typeCode = dsf.getCode();
		String hql = "update from DepotMaterial set type = :type where type = :oldtype";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("oldtype", oldCode);
		query.setParameter("type", typeCode);
		query.executeUpdate();
	}

	//根据id查询该类别下是不是有子类
	public List selectTypeCode(int id) {
		
		String hql = "select code from DepotMaterialType where parentid = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List typeCodeList = query.list();
		return typeCodeList;
	}

	//删除相关的材料信息
	public void deleteMaterial(List list) {

		Iterator it = list.iterator();
		String code = "";
		while(it.hasNext()){
			code = it.next().toString();
			String hql = "delete from DepotMaterial where type = :code";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("code", code);
			query.executeUpdate();
		}
	}

	//删除子类
	public void deleteChildTpye(List list) {
		
		Iterator it = list.iterator();
		String code = "";
		while(it.hasNext()){
			code = it.next().toString();
			String hql = "delete from DepotMaterialType where code = :code";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("code", code);
			query.executeUpdate();
		}
	}

	//判断是否可以添加子节点查询添加节点下是否有数据
	public List selectByCode(String code) {
		
		String hql = "from DepotMaterial where type = :code";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("code", code);
		List materialList = new ArrayList();
		materialList = query.list();
		return materialList;
	}

	//查询要删除类别code以便删除相关的材料
	public List selectCode(TypeForm typeForm) {
		
		String hql = "select code from DepotMaterialType where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", typeForm.getId());
		List typeCodeList = query.list();
		return typeCodeList;
	}
}
