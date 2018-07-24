package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICostParaTypeDAO;
import com.zc13.bkmis.form.CostParaTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
/**
 * 计费参数类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:01:18 PM
 */
public class CostParaTypeDAOImpl extends BasicDAOImpl implements ICostParaTypeDAO{

	/**
	 * 获得计费参数类型列表
	 */
	public List<CCostparatype> getCostParaTypeList(CostParaTypeForm obj)throws DataAccessException {
		StringBuffer hql = new StringBuffer("from CCostparatype where 1=1 ");
		//统计总数的hql语句
		StringBuffer countHql = new StringBuffer("select count(*) from CCostparatype where 1=1 ");
		StringBuffer tempHql = new StringBuffer();
		if(obj!=null){
			if(!ExtendUtil.null2str(obj.getType_Name()).equals("")){
				tempHql.append(" and typeName like '%");
				tempHql.append(obj.getType_Name());
				tempHql.append("%' ");
			}
		}
		hql.append(tempHql);
		//此处必须加上order by 子句，否则无法实现分页功能
		hql.append(" order by id");
		countHql.append(tempHql);
		//每页显示的条数，空的情况下默认是5
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(obj.getPagesize(),"5"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(obj.getCurrentpage(),"1")) - 1);
		
		List<CCostparatype> list = this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize).list();
		int count = 0;
		try{
			Query query = this.getSession().createQuery(countHql.toString());
			List countList = query.list();
			//获得记录总数
			count = (Integer)countList.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			//将记录总数保存到list的第一个对象中
			list.get(0).setTotalcount(count);
		}
		return list;
	}

	/**
	 * 删除计费参数类型
	 */
	public void deleteCostParaType(CCostparatype obj) throws DataAccessException {
		super.deleteObject(obj);
	}

	/**
	 * 保存计费参数类型
	 */
	public void saveCostParaType(CCostparatype obj) throws DataAccessException {
		super.saveObject(obj);
	}

	/**
	 * 更新计费参数类型
	 */
	public void updateCostParaType(CCostparatype obj) throws DataAccessException {
		super.updateObject(obj);
	}

	@Override
	public List<CCostparatype> getCostParaTypeByNames(List typeNames)
			throws DataAccessException {
		List<CCostparatype> list = null;
		String sql = "from CCostparatype c where c.typeName in(:typeNames)";
		list = this.getSession().createQuery(sql.toString()).setParameterList("typeNames", typeNames).list();
		return list;
	}

}
