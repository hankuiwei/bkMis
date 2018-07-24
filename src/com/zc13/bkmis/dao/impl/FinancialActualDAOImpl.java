package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IFinancialActualDAO;
import com.zc13.bkmis.form.FinancialActualForm;
import com.zc13.bkmis.mapping.FinancialActual;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class FinancialActualDAOImpl extends BasicDAOImpl implements IFinancialActualDAO{
	

	//获取实际财务列表
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialActual> queryFinancialBudget(FinancialActualForm financialActualForm,boolean isPage) throws DataAccessException {
			StringBuffer hql = new StringBuffer("from FinancialActual where 1=1");
			if(financialActualForm!=null){
				if(!GlobalMethod.NullToSpace(financialActualForm.getYear()).equals("")){
					hql.append(" and year = '"+financialActualForm.getYear()+"'");
				}
			}
			hql.append(" order by year desc");
			List<FinancialActual> list=null; 
			if(isPage){
				//每页显示的条数，空的情况下默认是10
				int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(financialActualForm.getPagesize(),"10"));
				//当前是从第几条开始
				int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(financialActualForm.getCurrentpage(),"1")) - 1);
				list = (List<FinancialActual>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
			}else{
				Query query = this.getSession().createQuery(hql.toString());
				list = (List<FinancialActual>)query.list();
			}
			return list;
		}
	
	
	
	//获取财务信息列表 
	public int queryCounttotal(FinancialActualForm financialActualForm){
		int count = 0;
		String hql = "select count(f) from FinancialActual as f ";
		
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
