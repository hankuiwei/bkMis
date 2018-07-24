package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.zc13.bkmis.dao.IMerchantsAandPDAO;
import com.zc13.bkmis.form.MerForm;
import com.zc13.util.GlobalMethod;
/***
 * @author 李影
 */
public class MerchantsAandPDAOImpl extends BasicDAOImpl implements IMerchantsAandPDAO {

	/**
	 * 列表数据
	 * @param year
	 * @param isPage
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:56:25
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryAandB(MerForm merForm) {
		/*String sql = "select a.year, a.area a_aarea, c.area c_area from "
				+"merchants_plan a left join (select b.year,sum(b.actualArea) area "
				+ "from merchants_actual b group by b.year) c on a.year=c.year";*/
		StringBuffer sql = new StringBuffer();
		sql.append("select a.year,a.quarter,a.person_type,a.area,b.actualArea from ");
		sql.append("(select a.year,a.person_type,a.quarter,sum(a.area) area from merchants_plan a group by a.year,a.quarter,a.person_type) a ");
		sql.append("LEFT JOIN (select b.year,b.person_type,b.quarter,sum(b.actualArea) actualArea from merchants_actual b group by b.year,b.quarter,b.person_type) b");
		sql.append(" on a.year = b.year and a.person_type = b.person_type and a.quarter = b.quarter ");
		if(!"".equals(GlobalMethod.ObjToStr(merForm.getYear()))){
			sql.append(" where a.year='"+GlobalMethod.ObjToStr(merForm.getYear())+"'");
		}
		
		sql.append(" order by a.year desc");
		List formateList = new ArrayList();
		try{
			SQLQuery query = this.getSession().createSQLQuery(sql.toString());
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(merForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(merForm.getCurrentpage(),"1")) - 1);
			List list = query.setFirstResult(currentindex).setMaxResults(pagesize1).list();
			if(list != null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					Object[] obj = (Object[])list.get(i);
					Map map = new HashMap();
					map.put("year", GlobalMethod.ObjToStr(obj[0]));
					map.put("quarter", GlobalMethod.ObjToStr(obj[1]));
					map.put("personType", GlobalMethod.ObjToStr(obj[2]));
					map.put("planArea", GlobalMethod.ObjToStr(obj[3]));
					map.put("actualArea", GlobalMethod.ObjToStr(obj[4]));
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
	public int queryCounttotal(MerForm merForm){
		
		int count = 0;
		/*String sql = "select count(1) from "
				+"merchants_plan a left join (select b.year,sum(b.actualArea) area "
				+"from merchants_actual b group by b.year) c on a.year=c.year";*/
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from ");
		sql.append("(select a.year,a.person_type,a.quarter,sum(a.area) area from merchants_plan a group by a.year,a.quarter,a.person_type) a ");
		sql.append("LEFT JOIN (select b.year,b.person_type,b.quarter,sum(b.actualArea) actualArea from merchants_actual b group by b.year,b.quarter,b.person_type) b");
		sql.append(" on a.year = b.year and a.person_type = b.person_type and a.quarter = b.quarter ");
		if(!"".equals(GlobalMethod.ObjToStr(merForm.getYear()))){
			sql.append(" where a.year='"+GlobalMethod.ObjToStr(merForm.getYear())+"'");
		}
		
		if(!"".equals(GlobalMethod.ObjToStr(merForm.getYear()))){
			sql.append(" where a.year='"+GlobalMethod.ObjToStr(merForm.getYear())+"'");
		}
		
		try{
			Query query = this.getSession().createSQLQuery(sql.toString());
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
