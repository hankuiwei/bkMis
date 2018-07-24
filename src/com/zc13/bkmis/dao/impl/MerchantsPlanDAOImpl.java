package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IMerchantsPlanDAO;
import com.zc13.bkmis.form.MerchantsPlanForm;
import com.zc13.bkmis.mapping.MerchantsPlan;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class MerchantsPlanDAOImpl extends BasicDAOImpl implements IMerchantsPlanDAO {

	//获取客户报修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantsPlan> queryPlanResult(MerchantsPlanForm merchantsPlanForm,boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from MerchantsPlan where 1=1");
		if(merchantsPlanForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getYear()).equals("")){
				hql.append(" and year = '"+merchantsPlanForm.getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getMonth()).equals("")){
				hql.append(" and month = '"+merchantsPlanForm.getMonth()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getResponsePerson()).equals("")){
				hql.append(" and responsePerson like '%"+merchantsPlanForm.getResponsePerson()+"%'");
			}
			
		}
		hql.append(" order by id");
		List<MerchantsPlan> list = new ArrayList();
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(merchantsPlanForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(merchantsPlanForm.getCurrentpage(),"1")) - 1);
			list = (List<MerchantsPlan>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<MerchantsPlan>)query.list();
		}
		return list;
	}
			
	
	//获取招商计划信息
	public int queryCounttotal(MerchantsPlanForm merchantsPlanForm){
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m) from MerchantsPlan as m where 1=1 ");
		if(merchantsPlanForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getYear()).equals("")){
				hql.append(" and year = '"+merchantsPlanForm.getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getMonth()).equals("")){
				hql.append(" and month = '"+merchantsPlanForm.getMonth()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsPlanForm.getResponsePerson()).equals("")){
				hql.append(" and responsePerson like '%"+merchantsPlanForm.getResponsePerson()+"%'");
			}
			
		}
		try{
			Query query = this.getSession().createQuery(hql.toString());
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	/**
	 * 查询类型代码
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysCode> queryCodeByType() {
		
		String hql = "from SysCode where codeType='"+Contants.PLAN_BLOCK+"'";
		Query query = this.getSession().createQuery(hql.toString());
		
		List<SysCode> list = new ArrayList<SysCode>();
		list = query.list();
		return list;
	}
}
