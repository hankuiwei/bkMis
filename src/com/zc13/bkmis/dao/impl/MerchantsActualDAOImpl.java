package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IMerchantsActualDAO;
import com.zc13.bkmis.form.MerchantsActualForm;
import com.zc13.bkmis.mapping.MerchantsActual;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class MerchantsActualDAOImpl extends BasicDAOImpl implements IMerchantsActualDAO {

	//获取客户报修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantsActual> queryActualResult(MerchantsActualForm merchantsActualForm,boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from MerchantsActual where 1=1");
		if(merchantsActualForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getYear()).equals("")){
				hql.append(" and year = '"+merchantsActualForm.getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getMonth()).equals("")){
				hql.append(" and month = '"+merchantsActualForm.getMonth()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getResponsePerson()).equals("")){
				hql.append(" and responsePerson like '%"+merchantsActualForm.getResponsePerson()+"%'");
			}
			
		}
		hql.append(" order by id");
		List<MerchantsActual> list = new ArrayList();
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(merchantsActualForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(merchantsActualForm.getCurrentpage(),"1")) - 1);
			list = (List<MerchantsActual>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<MerchantsActual>)query.list();
		}
		return list;
	}
			
	
	//获取招商计划信息
	public int queryCounttotal(MerchantsActualForm merchantsActualForm){
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m) from MerchantsActual as m where 1=1 ");
		if(merchantsActualForm!=null){
			//name的精确查询
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getYear()).equals("")){
				hql.append(" and year = '"+merchantsActualForm.getYear()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getMonth()).equals("")){
				hql.append(" and month = '"+merchantsActualForm.getMonth()+"'");
			}
			if(!GlobalMethod.NullToSpace(merchantsActualForm.getResponsePerson()).equals("")){
				hql.append(" and responsePerson like '%"+merchantsActualForm.getResponsePerson()+"%'");
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


	@Override
	public List<SysCode> queryCodeByType() {
		
		String hql = "from SysCode where codeType='"+Contants.PLAN_BLOCK+"'";
		Query query = this.getSession().createQuery(hql.toString());
		
		List<SysCode> list = new ArrayList<SysCode>();
		list = query.list();
		return list;
	}
}
