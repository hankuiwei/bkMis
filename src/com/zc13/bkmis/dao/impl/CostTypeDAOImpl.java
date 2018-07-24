package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICostTypeDAO;
import com.zc13.bkmis.form.CostTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
/**
 * 费用类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:01:18 PM
 */
public class CostTypeDAOImpl extends BasicDAOImpl implements ICostTypeDAO{

	/**
	 * 获得费用类型列表
	 */
	public List<CCosttype> getCostTypeList(CostTypeForm obj)throws DataAccessException {
		StringBuffer hql = new StringBuffer("from CCosttype where 1=1 ");
		//统计总数的hql语句
		StringBuffer countHql = new StringBuffer("select count(*) from CCosttype where 1=1 ");
		StringBuffer tempHql = new StringBuffer();
		if(obj!=null){
			if(!ExtendUtil.null2str(obj.getTypeName()).equals("")){
				tempHql.append(" and costTypeName like '%");
				tempHql.append(obj.getTypeName());
				tempHql.append("%' ");
			}
			if(obj.getLpId()!=null&&obj.getLpId()!=0){
				tempHql.append(" and lpId=").append(obj.getLpId());
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
		
		List<CCosttype> list = this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize).list();
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
	 * 删除费用类型
	 */
	public void deleteCostType(CCosttype obj) throws DataAccessException {
		super.deleteObject(obj);
	}

	/**
	 * 保存费用类型
	 */
	public void saveCostType(CCosttype obj) throws DataAccessException {
		super.saveObject(obj);
	}

	/**
	 * 更新费用类型
	 */
	public void updateCostType(CCosttype obj) throws DataAccessException {
		super.updateObject(obj);
	}

	/**
	 * 获得计费参数类型列表
	 */
	public List<CCostparatype> getCostparatypeList() throws DataAccessException {
		StringBuffer hql = new StringBuffer("from CCostparatype ");
		Query query = this.getSession().createQuery(hql.toString());
		List<CCostparatype> list = query.list();
		return list;
	}

	/**
	 * 获得表具列表
	 */
	public List<SysCode> getGaugeList() throws DataAccessException {
		StringBuffer hql = new StringBuffer("from SysCode where codeType='gaugeType' ");
		Query query = this.getSession().createQuery(hql.toString());
		List<SysCode> list = query.list();
		return list;
	}

}
