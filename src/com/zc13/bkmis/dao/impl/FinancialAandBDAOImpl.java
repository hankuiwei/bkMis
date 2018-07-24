package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.zc13.bkmis.dao.IFinancialAandBDAO;
import com.zc13.bkmis.form.FinForm;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class FinancialAandBDAOImpl extends BasicDAOImpl implements IFinancialAandBDAO{
	
	
	/**
	 * 列表数据
	 * @param year
	 * @param isPage
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:56:25
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryAandB(FinForm finForm) {
		String sql = "select a.year, a.water a_awter, a.electricity a_electricity, a.gas a_gas, c.water c_water, c.electricity c_electricity, c.gas c_gas from "
				+"financial_budget a left join (select b.year,sum(b.water) water, sum(b.electricity) electricity, sum(b.gas) gas "
				+"from financial_actual b group by b.year) c on a.year=c.year";
		
		if(!"".equals(GlobalMethod.ObjToStr(finForm.getYear()))){
			sql=sql+" where a.year='"+GlobalMethod.ObjToStr(finForm.getYear())+"'";
		}
		
		sql+=" order by a.year desc";
		List formateList = new ArrayList();
		try{
			SQLQuery query = this.getSession().createSQLQuery(sql);
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(finForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(finForm.getCurrentpage(),"1")) - 1);
			List list = query.setFirstResult(currentindex).setMaxResults(pagesize1).list();
			if(list != null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					Object[] obj = (Object[])list.get(i);
					Map map = new HashMap();
					map.put("year", GlobalMethod.ObjToStr(obj[0]));
					map.put("advaceWater", GlobalMethod.ObjToStr(obj[1]));
					map.put("advaceElectricity", GlobalMethod.ObjToStr(obj[2]));
					map.put("advaceGas", GlobalMethod.ObjToStr(obj[3]));
					map.put("actualWater", GlobalMethod.ObjToStr(obj[4]));
					map.put("actualElectricity", GlobalMethod.ObjToStr(obj[5]));
					map.put("actualGas", GlobalMethod.ObjToStr(obj[6]));
					formateList.add(map);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return formateList;
		
	}
	
	
	
	/**
	 * 列表总数
	 */
	public int queryCounttotal(FinForm finForm){
		
		int count = 0;
		String sql = "select count(1) from "
				+"financial_budget a left join (select b.year,sum(b.water) water, sum(b.electricity) electricity, sum(b.gas) gas "
				+"from financial_actual b group by b.year) c on a.year=c.year";
		
		if(!"".equals(GlobalMethod.ObjToStr(finForm.getYear()))){
			sql=sql+" where a.year='"+GlobalMethod.ObjToStr(finForm.getYear())+"'";
		}
		
		try{
			Query query = this.getSession().createSQLQuery(sql);
			List list = query.list();
			if(list != null && list.size()>0){
				count = (Integer)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
