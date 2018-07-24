package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IFinancialBudgetDAO;
import com.zc13.bkmis.form.FinancialBudgetForm;
import com.zc13.bkmis.mapping.FinancialBudget;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class FinancialBudgetDAOImpl extends BasicDAOImpl implements IFinancialBudgetDAO{
	

	//获取财务预算列表
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialBudget> queryFinancialBudget(FinancialBudgetForm financialBudgetForm,boolean isPage) throws DataAccessException {
			StringBuffer hql = new StringBuffer("from FinancialBudget where 1=1");
			if(financialBudgetForm!=null){
				if(!GlobalMethod.NullToSpace(financialBudgetForm.getYear()).equals("")){
					hql.append(" and year = '"+financialBudgetForm.getYear()+"'");
				}
			}
			hql.append(" order by year desc");
			List<FinancialBudget> list=null; 
			if(isPage){
				//每页显示的条数，空的情况下默认是10
				int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(financialBudgetForm.getPagesize(),"10"));
				//当前是从第几条开始
				int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(financialBudgetForm.getCurrentpage(),"1")) - 1);
				list = (List<FinancialBudget>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
			}else{
				Query query = this.getSession().createQuery(hql.toString());
				list = (List<FinancialBudget>)query.list();
			}
			return list;
			
		}
	
	
	
	//获取财务预算列表 
	public int queryCounttotal(FinancialBudgetForm financialBudgetForm){
		int count = 0;
		String hql = "select count(f) from FinancialBudget as f ";
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
