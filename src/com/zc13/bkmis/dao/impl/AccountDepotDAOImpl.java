package com.zc13.bkmis.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAccountDepotDAO;
import com.zc13.bkmis.form.AccountDepotForm;
import com.zc13.bkmis.mapping.DepotAdjustAccounts;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class AccountDepotDAOImpl extends BasicDAOImpl implements IAccountDepotDAO {

	//显示查询库存核算信息
	public List showAccount(AccountDepotForm adf) throws DataAccessException {
		
		String year = GlobalMethod.NullToSpace(adf.getYear());
		String  lpId = GlobalMethod.NullToSpace(String.valueOf(adf.getLpId()));
		String hql = "from DepotAdjustAccounts where 1=1";
		if(!"".equals(year) && null != year){
			hql += " and year = :year";
		}
		if(!"".equals(lpId) && null != lpId){
			hql +=" and lpId =" + lpId;
		}
		
		hql += " order by id desc";
		
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(year) && null != year){
			query.setParameter("year", year);
		}
		List accountList = new ArrayList();
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(adf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(adf.getCurrentpage(),"1"))-1);
		accountList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return accountList;
	}

	//查看库存的核算的详细信息
	public List showDetailAccount(AccountDepotForm adf) throws DataAccessException {
		
		String beginDate = adf.getBeginDate();
		String endDate = adf.getEndDate();
		String s = GlobalMethod.addDate(beginDate);
		String sql = " select b.name,a.material_id,(select code_name from sys_code where code_type='unit' and code_value=b.unit)as unit,b.spec,a.price,a.qiamount,a.qiMoney,a.benInAmount,a.benInMoney,a.benOutAmount,a.benOutMoney,b.code as material_code  from ( \n"
			+" SELECT  case when benIn.id is not null then benIn.id \n"
			+" else( case when benOut.material_id is not null then benOut.material_id  \n"
			+" else(case when qiIn.material_id is not null then qiIn.material_id \n"
			+" else (case when qiOut.material_id is not null then qiOut.material_id end)end) end)end  as material_id, \n"
			+" case when benIn.unit_price is not null then benIn.unit_price \n"
			+" else( case when benOut.unit_price is not null then benOut.unit_price  \n"
			+" else(case when qiIn.unit_price is not null then qiIn.unit_price \n"
			+" else (case when qiOut.unit_price is not null then qiOut.unit_price end)end) end)end as price, \n"
			+" case when benIn.benInAmount is null then 0 else benIn.benInAmount end as benInAmount , \n"
			+" case when benIn.benInMoney is null then 0 else benIn.benInMoney end as benInMoney , \n"
			+" case when benOut.benOutAmount is null then 0 else benOut.benOutAmount end as benOutAmount , \n"
			+" case when benOut.benOutMoney is null then 0 else benOut.benOutMoney end as benOutMoney , \n"
			+" (case when qiIn.qiInAmount is null then 0 else qiIn.qiInAmount end ) -"
			+" (case when qiOut.qiOutAmount is null then 0 else qiOut.qiOutAmount end ) as qiamount, \n"
			+" (case when qiIn.qiInMoney is null then 0 else qiIn.qiInMoney end ) -"
			+" (case when qiOut.qiOutMoney is null then 0 else qiOut.qiOutMoney end) as qiMoney \n"
			+" FROM \n"
			+" (SELECT     diol.material_id as id, diol.unit_price , SUM(diol.amount) AS benInAmount, SUM(diol.amount_money) AS benInMoney \n"
			+" FROM          depot_in_output_list as diol,depot_input_manager as dim,depot_material_copy as dm \n"
			+" WHERE      (diol.type2 = '1') AND (diol.date >= '"+beginDate+"') AND (substring(diol.date,0,10)<='"+endDate+"') AND (dim.status = '已结算') AND (diol.in_output_code = dim.code) AND (diol.material_id = dm.id)  \n"
			+" GROUP BY diol.material_id, diol.unit_price) AS benIn  \n"
			+" FULL JOIN \n"
			+" (SELECT     diol.material_id, diol.unit_price, SUM(diol.amount) AS benOutAmount, SUM(diol.amount_money) AS benOutMoney \n"
			+" FROM          depot_in_output_list as diol,depot_output_manager as dom,depot_material_copy as dm,sys_code as sc \n"
			+" WHERE      (diol.type2 = '0') AND (diol.date >= '"+beginDate+"') AND (substring(diol.date,0,10)<='"+endDate+"') AND (dom.status = '已结算') AND (diol.in_output_code = dom.code) AND (diol.material_id = dm.id) AND (sc.code_value = dom.department) \n"
			+" GROUP BY diol.material_id, diol.unit_price) AS benOut \n"
			+" ON benIn.id = benOut.material_id AND benIn.unit_price = benOut.unit_price \n"
			+" FULL JOIN \n"
			+" (SELECT     diol.material_id, diol.unit_price, SUM(diol.amount) AS qiInAmount, SUM(diol.amount_money) AS qiInMoney \n"
			+" FROM          depot_in_output_list as diol,depot_input_manager as dim,depot_material_copy as dm \n"
			+" WHERE      (diol.type2 = '1') AND (substring(diol.date,0,10)<='"+s+"') AND (dim.status = '已结算') AND (diol.in_output_code = dim.code) AND (diol.material_id = dm.id) \n"
			+" GROUP BY diol.material_id, diol.unit_price) AS qiIn \n"
			+" ON (benIn.id = qiIn.material_id AND benIn.unit_price = qiIn.unit_price) \n"
			+" or(qiIn.material_id = benOut.material_id AND qiIn.unit_price = benOut.unit_price ) \n"
			+" FULL JOIN \n"
			+" (SELECT     diol.material_id, diol.unit_price, SUM(diol.amount) AS qiOutAmount, SUM(diol.amount_money) AS qiOutMoney \n"
			+" FROM          depot_in_output_list as diol,depot_output_manager as dom,depot_material_copy as dm,sys_code as sc \n"
			+" WHERE      (diol.type2 = '0') AND (substring(diol.date,0,10)<='"+s+"') AND (dom.status = '已结算') AND (diol.in_output_code = dom.code) AND (diol.material_id = dm.id) AND (sc.code_value = dom.department) \n"
			+" GROUP BY diol.material_id, diol.unit_price) AS qiOut \n"
			+" ON (benIn.id = qiOut.material_id AND benIn.unit_price = qiOut.unit_price) \n"
			+" or(qiOut.material_id = benOut.material_id AND qiOut.unit_price = benOut.unit_price ) \n"
			+" or(qiIn.material_id = qiOut.material_id AND qiIn.unit_price = qiOut.unit_price ) \n"
			+" ) as a ,"
			+" depot_material_copy as b where a.material_id = b.id" ;
		if(!GlobalMethod.NullToSpace(adf.getCx_name()).equals("")){
			sql += " and b.name like'%"+adf.getCx_name()+"%'";
		}
		if(!GlobalMethod.NullToSpace(adf.getCx_code()).equals("")){
			sql += " and b.code ='"+adf.getCx_code()+"'";
		}
		sql += " order by b.name,b.code";
		//System.out.println("================"+sql);
		Query query = this.getSession().createSQLQuery(sql);
		List list = new ArrayList();
		list = query.list();
		return list;
	}

	//查看核算信息的记录条数
	public int queryCountTotal(AccountDepotForm adf) throws DataAccessException {
		int count = 0;
		String year = GlobalMethod.NullToSpace(adf.getYear());
		String hql = "select count(da) from DepotAdjustAccounts as da where 1=1";
		if(!"".equals(year) && null != year){
			hql += " and year = :year";
		}
		
		
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(year) && null != year){
			query.setParameter("year", year);
		}
		List countList = new ArrayList();
		try{
			countList = query.list();
			count = (Integer)countList.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	//按id查询要结算的信息
	public List selectAccountById(int id) throws DataAccessException {
		
		String hql = "from DepotAdjustAccounts where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List list = query.list();
		return list;
	}
	
	//添加月结算
	public List doAddAccount(AccountDepotForm adf) throws DataAccessException {
		
		String beginDate = adf.getBeginDate();
		String endDate = adf.getEndDate();
		String s = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try{
			calendar.setTime(dateFormat.parse(beginDate));
		}catch (Exception e) {}
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		s = dateFormat.format(calendar.getTime());
		/*String hql = "select new Map(qiInAmount-qiOutAmount as qiBgAmount,qiInMoney-qiOutMoney as qiEdMoney,benInAmount as benInAnount,benInMoney as benInMoney,benOutAmount as benOutAmount,benOutMoney as benOutMoney) from " +
				" (select sum(amount) as qiInAmount,sum(amountMoney) as qiInMoney from DepotInOutputList where typ2 = '1' and date <= :qiInDate) as qiIn, " +
				" (select sum(amount) as qiOutAmount,sum(amountMoney) as qiOutMoney from DepotInOutputList where typ2 = '0' and date <= :qiOutDate) as qiOut, " +
				" (select sum(amount) as benInAmount,sum(amountMoney) as benInMoney from DepotInOutputList where typ2 = '1' and date>= :benInBgDate and date<=:benInEdDate) as benIn, " +
				" (select sum(amount) as benOutAmount,sum(amountMoney) as benOutMoney from DepotInOutputList where typ2 = '0' and date>= :benOutBgDate and date<=:benOutEdDate) as benOut ";
		Query query = this.getSession().createQuery(hql);*/
		//小于等于enddate实际相当于小于enddate在数据库中的date带有具体的时间然而enddate没有具体时间默认是00：00:00
		String sql = "select qiIn.qiInAmount-qiOut.qiOutAmount,qiIn.qiInMoney,qiOut.qiOutMoney,benIn.benInAmount,benIn.benInMoney,benOut.benOutAmount,benOut.benOutMoney from " +
				" (select sum(diol.amount) as qiInAmount,sum(diol.amount_money) as qiInMoney from depot_in_output_list as diol,depot_input_manager as dim,depot_material_copy as dm where diol.type2 = '1' and substring(diol.date,0,10)<='"+s+"' and diol.in_output_code = dim.code and diol.material_id = dm.id) as qiIn, " +
				" (select sum(diol.amount) as qiOutAmount,sum(diol.amount_money) as qiOutMoney from depot_in_output_list as diol,depot_output_manager as dom,depot_material_copy as dm,sys_code as sc where diol.type2 = '0' and substring(diol.date,0,10)<='"+s+"' and diol.in_output_code = dom.code and diol.material_id = dm.id and sc.code_value = dom.department) as qiOut," +
				" (select sum(diol.amount) as benInAmount,sum(diol.amount_money) as benInMoney from depot_in_output_list as diol,depot_input_manager as dim,depot_material_copy as dm where diol.type2 = '1' and diol.date>= '"+beginDate+"' and substring(diol.date,0,10)<='"+endDate+"' and diol.in_output_code = dim.code and diol.material_id = dm.id) as benIn," +
				" (select sum(diol.amount) as benOutAmount,sum(diol.amount_money) as benOutMoney from depot_in_output_list as diol,depot_output_manager as dom,depot_material_copy as dm,sys_code as sc where diol.type2 = '0' and diol.date>= '"+beginDate+"' and substring(diol.date,0,10)<='"+endDate+"' and diol.in_output_code = dom.code and diol.material_id = dm.id and sc.code_value = dom.department) as benOut ";
		Query query = this.getSession().createSQLQuery(sql);
		
		List accountList = new ArrayList();
		accountList = query.list();
		return accountList;
		
	}

	//按id查询要删除的详细信息内容
	public List<DepotAdjustAccounts> selectDepotAccount(Integer[] idArray) throws DataAccessException {
		
		String hql = "from DepotAdjustAccounts where 1=1";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List accountList = this.getHibernateTemplate().find(hql, idArray); 
		return accountList;
	}

	//更改出入库的状态
	public void updateStatus(AccountDepotForm adf) throws DataAccessException {
		
		String beginDate = GlobalMethod.NullToSpace(adf.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(adf.getEndDate());
		
		//hqlInput1的作用是针对更新结算表。首先需要把上次结算的（不是上个月，而是被更新的那条，规则是出入库日期大于上月结算的统计用的结束日期）那些出入库结算状态置为未结算，然后再将本次
		//其实，当我在页面上限制了开会日期不能手动输入之后，这个就没什么效果了，因为不会允许其更新的，至少到今天2011-5-13号为止，是不允许的，先留着，以后如果要改的话再说
		//之所以下面的开始日期要减1,而结束日期要加1,原因在于：数据库中数据是精确到秒的，而查询条件却只是到日，
		String hqlInput1 = "update DepotInputManager as dim set dim.status = '"+Contants.WAITACCOUNT+"' where 1=1 and dim.date>'" +  GlobalMethod.minDate(beginDate) + "'";
		String hqlInput2 = "update DepotInputManager as dim set dim.status = '"+Contants.ALREADYACCOUNT+"' where 1=1 and dim.date>'" + beginDate + "' and dim.date<'" +  GlobalMethod.addDate(endDate) + "'";
		Query queryInput1 = this.getSession().createQuery(hqlInput1);
		Query queryInput2 = this.getSession().createQuery(hqlInput2);
		queryInput1.executeUpdate();
		queryInput2.executeUpdate();
		
		//更新出库状态
		String hqlOutput1 = "update DepotOutputManager as dom set dom.status = '"+Contants.WAITACCOUNT+"' where 1=1 and dom.date>'" +  GlobalMethod.minDate(beginDate) +"'";
		String hqlOutput2 = "update DepotOutputManager as dom set dom.status = '"+Contants.ALREADYACCOUNT+"' where 1=1 and dom.date>'" +  beginDate + "' and dom.date<'" + GlobalMethod.addDate(endDate) + "'";
		Query queryOutput1 = this.getSession().createQuery(hqlOutput1);
		Query queryOutput2 = this.getSession().createQuery(hqlOutput2);
		queryOutput1.executeUpdate();
		queryOutput2.executeUpdate();
	}

	@Override
	public List<DepotAdjustAccounts> getAccount(DepotAdjustAccounts accounts)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from DepotAdjustAccounts t where 1=1 ");
		if(accounts!=null){
			if(!GlobalMethod.NullToSpace(accounts.getYear()).equals("")){
				hql.append(" and t.year='").append(accounts.getYear()).append("'");
			}
			if(!GlobalMethod.NullToSpace(accounts.getMonth()).equals("")){
				hql.append(" and t.month='").append(accounts.getMonth()).append("'");
			}
		}
		List<DepotAdjustAccounts> list = this.getSession().createQuery(hql.toString()).list();
		return list;
	}

}
