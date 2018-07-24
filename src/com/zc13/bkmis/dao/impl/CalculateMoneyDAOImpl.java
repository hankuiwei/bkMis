package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICalculateMoneyDAO;
import com.zc13.bkmis.form.CalculateMoneyForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CalculateMoney;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/**
 * 合同预算 daoimpl
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午10:56:09
 */
public class CalculateMoneyDAOImpl extends BasicDAOImpl implements ICalculateMoneyDAO {

	/**
	 * 获取需要汇总的合同
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getNeedCalculateCompact(Integer compactId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("from Compact where type !='");
		sql.append(Contants.COMPACTCHANGE);
		sql.append("'");
		if(compactId != null){
			sql.append(" and id=");
			sql.append(compactId);
		}
		
		Query query = this.getSession().createQuery(sql.toString());
		List<Compact> compactList = query.list();
		return compactList;
	}

	/**
	 * 获取该楼盘下的帐套
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CAccounttemplate getAccounttemplateById(Integer lpId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("from CAccounttemplate where lpid=");
		sql.append(lpId);
		
		Query query = this.getSession().createQuery(sql.toString());
		List<CAccounttemplate> list = query.list();
		CAccounttemplate template = (CAccounttemplate)list.get(0);
		return template;
	}

	/**
	 * 查询标准
	 * @return
	 * Date:2013-3-23 
	 * Time:下午3:24:27
	 */
	@SuppressWarnings("unchecked")
	public List<CompactRoomCoststandard> getCompactRoomCoststandardByCompactId(Integer compactId){
		
		StringBuffer sql = new StringBuffer();
		sql.append("from CompactRoomCoststandard where compact.id in(");
		if(null == compactId){
			//不汇总变更合同
			sql.append("select id from Compact where type !='");
			sql.append(Contants.COMPACTCHANGE);
			sql.append("'");
			//合同状态必须是通过审批的
			sql.append(" and status='");
			sql.append(Contants.THROUGHAPPROVAL);
			sql.append("'");
		}else{
			sql.append(compactId);
		}
		sql.append(") and CCoststandard.itemId=");
		sql.append("(select id from CItems where itemName='");
		sql.append(Contants.RENT);
		sql.append("')");
		
		Query query = this.getSession().createQuery(sql.toString());
		List<CompactRoomCoststandard> list = query.list();
		return list;
	}
	
	/**
	 * 获取计算单价等参数
	 * @param map
	 * @return
	 * @throws Exception
	 * Date:2013-3-23 
	 * Time:下午4:27:58
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CompactRent> getRent(Map map) {
		StringBuffer sb = new StringBuffer();
		/*sb.append("from CompactRent cr where cr.compact.id in(select c.id from Compact c where c.clientId=");
		sb.append(map.get("clientId"));
		sb.append(" and c.status='");
		sb.append(Contants.THROUGHAPPROVAL);
		sb.append("') ");*/
		sb.append("from CompactRent cr where cr.compact.id=");
		sb.append(map.get(Contants.COMPACTID));
		sb.append(" and cr.beginDate<='");
		sb.append(map.get(Contants.BILLENDDATE));
		sb.append("' and cr.endDate>='");
		sb.append(map.get(Contants.BILLSTARTDATE));
		sb.append("' ");
		//sb.append("' and cr.compact.id=");
		//sb.append(map.get("compactId"));
		sb.append(" order by cr.beginDate");
		List<CompactRent> list = this.getSession().createQuery(sb.toString()).list();
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CCostparatype> getCostParaTypeByNames(List typeNames){
		List<CCostparatype> list = null;
		String sql = "from CCostparatype c where c.typeName in(:typeNames)";
		list = this.getSession().createQuery(sql.toString()).setParameterList("typeNames", typeNames).list();
		return list;
	}
	
	/**
	 * 执行sql语句，返回执行结果 该方法只针对计费参数类型中的sql语句的执行，执行的结果只能是一个值
	 * 
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public String query(String sql) throws Exception {
		String result = "";
		if (!GlobalMethod.NullToSpace(sql).equals("")) {
			List list = this.getSession().createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				Object obj = (Object)list.get(0);
				result = GlobalMethod.ObjToStr(obj);
			}
		}
		return result;
	}

	/**
	 * 删除汇总合同的钱
	 */
	@Override
	public void deleteCalculateMoney(Integer compactId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from CalculateMoney where 1=1 ");
		if(null != compactId){
			sql.append(" and compactId=");
			sql.append(compactId);
		}
		Query query = this.getSession().createQuery(sql.toString());
		query.executeUpdate();
	}

	/**
	 * 合同年度的收费
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CalculateMoney> getYearCompactMoney(CalculateMoneyForm form) {
		
		List<CalculateMoney> list = new ArrayList<CalculateMoney>();
		StringBuffer hql = new StringBuffer("select new Map(year as year,sum(money) as money) from CalculateMoney where 1=1 ");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getYear()))){
			hql.append(" and year = '"+form.getYear()+"'");	
		}
		hql.append(" group by year order by year ");
		Query query = this.getSession().createQuery(hql.toString());
		if(form.isPage()){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			list = query.list();
		}
		return list;
	}
	
	/**
	 * 合同年度的收费
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getYearCompactMoneyCount(CalculateMoneyForm form) {
		
		int count = 0;
		StringBuffer hql = new StringBuffer("select new Map(year as year,sum(money) as money) from CalculateMoney where 1=1 ");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getYear()))){
			hql.append(" and year = '"+form.getYear()+"'");	
		}
		hql.append(" group by year");
		
		Query query = this.getSession().createQuery(hql.toString());
		List<CalculateMoney> list = query.list();
		if(list != null && list.size()>0){
			count = list.size();
		}
		return count;
	}

	/**
	 * 年的详细费用
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CalculateMoney> getYearDetail(CalculateMoneyForm form) {
		
		StringBuffer hql = new StringBuffer("select new Map(year as year,month as month,sum(money) as money) from CalculateMoney where 1=1 ");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getYear()))){
			hql.append(" and year = '"+form.getYear()+"'");	
		}
		hql.append(" group by year,month order by CONVERT(int,month) ");
		
		Query query = this.getSession().createQuery(hql.toString());
		List<CalculateMoney> list = query.list();
		return list;
	}

	/**
	 * 获取月的详细信息
	 */
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List getMonthDetail(CalculateMoneyForm form) {
		
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select new Map(c.id as id,c.code as code,cc.name as name,cm.year as year,cm.month as month,cm.money as money) from CalculateMoney cm,Compact c,CompactClient cc where cm.compactId = c.id and cm.clientId = cc.id ");
		if(!"".equals(GlobalMethod.ObjToStr(form.getYear()))){
			hql.append(" and cm.year = '");
			hql.append(form.getYear());
			hql.append("'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getMonth()))){
			hql.append(" and cm.month = '");
			hql.append(form.getMonth());
			hql.append("'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getClientName()))){
			hql.append(" and cc.name = '%");
			hql.append(form.getClientName());
			hql.append("%'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getCompactCode()))){
			hql.append(" and c.code = '%");
			hql.append(form.getCompactCode());
			hql.append("%'");
		}
		
		hql.append(" order by c.code");
		Query query = this.getSession().createQuery(hql.toString());
		
		if(form.isPage()){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			list = query.list();
		}
		
		return list;
	}

	/**
	 * 获取月详细信息数量
	 */
	@Override
	public int getMonthDetailCount(CalculateMoneyForm form) {
		
		int count = 0;
		StringBuffer hql = new StringBuffer();
		hql.append("select new Map(c.id,c.code as code,cc.name as name,cm.year as year,cm.month as month,cm.money as money) from CalculateMoney cm,Compact c,CompactClient cc where cm.compactId = c.id and cm.clientId = cc.id ");
		if(!"".equals(GlobalMethod.ObjToStr(form.getYear()))){
			hql.append(" and cm.year = '");
			hql.append(form.getYear());
			hql.append("'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getMonth()))){
			hql.append(" and cm.month = '");
			hql.append(form.getMonth());
			hql.append("'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getClientName()))){
			hql.append(" and cc.name = '%");
			hql.append(form.getClientName());
			hql.append("%'");
		}
		if(!"".equals(GlobalMethod.ObjToStr(form.getCompactCode()))){
			hql.append(" and c.code = '%");
			hql.append(form.getCompactCode());
			hql.append("%'");
		}
		
		Query query = this.getSession().createQuery(hql.toString());
		List list = query.list();
		if(list != null && list.size()>0){
			count = list.size();
		}
		return count;
	}

	/***
	 * 获取合同账单费用
	 */
	@Override
	public List getCompactBill(CalculateMoneyForm form) {
		
		StringBuffer hql = new StringBuffer();
		hql.append("select new Map(c.code as code,cm.circleStart as circleStart,cm.circleEnd as circleEnd,cm.startTime as startTime,cm.endTime as endTime,cm.billDate as billDate,cm.money as money,c.circle as circle) from CalculateMoney cm,Compact c where cm.compactId = c.id and cm.compactId=");
		hql.append(form.getCompactId());
		Query query = this.getSession().createQuery(hql.toString());
		List list = query.list();
		return list;
	}

	/**
	 * 获取需要计算的合同id
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Integer> getNeedCalculateCompactIds() {
		
		List<Integer> formatList = new ArrayList<Integer>();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.id from compact c LEFT JOIN calculate_money cm on c.id = cm.compact_id where cm.compact_id is null");
		Query query = this.getSession().createSQLQuery(sql.toString());
		List list = query.list();
		if(list != null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				formatList.add((Integer)list.get(i));
			}
		}
		return formatList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CAccounttemplate getAccounttemlateByCompactId(Integer compactId) {
		
		CAccounttemplate template = new CAccounttemplate();
		StringBuffer sql = new StringBuffer();
		sql.append("select ca from CAccounttemplate ca where lpid=");
		sql.append("(select lpId from Compact c where c.id=");
		sql.append(compactId);
		sql.append(")");
		
		Query query = this.getSession().createQuery(sql.toString());
		List<CAccounttemplate> list = query.list();
		if(list != null && list.size()>0){
			template = (CAccounttemplate)list.get(0);
		}
		 
		return template;
	}
}
