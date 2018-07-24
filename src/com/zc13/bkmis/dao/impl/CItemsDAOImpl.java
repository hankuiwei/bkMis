package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICItemsDAO;
import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.util.PageBean;
/**
 * 赵书广
 * @author Administrator
 * Date：Nov 26, 2010
 * Time：10:53:31 AM
 */
public class CItemsDAOImpl extends BasicDAOImpl implements ICItemsDAO {
	
	//收费项列表查询
	@SuppressWarnings("unchecked")
	public List getItemsList(CItemsForm form,boolean isShowSystemItems) throws DataAccessException {
		String hql = "select c from CItems c where 1=1 ";
		if(!isShowSystemItems){
			hql += " and c.value is null ";
		}
		if(form.getItemName()!=null&&!"".equals(form.getItemName())){
			hql += " and c.itemName like :itemName ";
		}
		if(form.getLpId()!=null&&form.getLpId()!=0){
			hql += " and c.lpId = "+form.getLpId();
		}
		Query query = this.getSession().createQuery(hql);
		if(form.getItemName()!=null&&!"".equals(form.getItemName())){
			query.setParameter("itemName", "%"+form.getItemName()+"%");
		}
		List list = query.list();
		return list;
	}
	/**gd 2018年3月4日17:38:35   原本功能失效!!! 已修改,- 效果待测试 */
	public PageBean getCItems(CItemsForm form)throws DataAccessException {
		String hql = "select c from CItems c  where c.value is null";
		if(form.getTypeName()!=null&&!"".equals(form.getTypeName())){
			hql += " and c.itemName like :typeName ";
		}
		if(form.getLpId()!=null&&form.getLpId()!=0){
			hql += " and c.lpId = "+form.getLpId();
		}
		hql += " order by c.indexs ";
		Query query = this.getSession().createQuery(hql);
		if(form.getTypeName()!=null&&!"".equals(form.getTypeName())){
			query.setParameter("typeName", "%"+form.getTypeName()+"%");
		}
		PageBean page = this.queryFy(query, form.getPagination(), form.getPagesize());
		return page;
	}
	/**
	 * 收费项保存
	 */
	public void saveItems(CItems items) throws DataAccessException {
		super.saveObject(items);
	}
	/**
	 * 收费项更新
	 */
	public void updateItems(CItems items) throws DataAccessException {
		super.updateObject(items);
	}
	/**
	 * 收费项删除
	 */
	public void deleteItems(CItems items) throws DataAccessException {
		super.deleteObject(items);
	}
}
