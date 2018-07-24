package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICostTransactDAO;
import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.ClientBill;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ECallInfo;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class CostTransactDAOImpl extends BasicDAOImpl implements
		ICostTransactDAO {
	/**
	 * 执行sql语句，返回执行结果 该方法只针对计费参数类型中的sql语句的执行，执行的结果只能是一个值
	 * 
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
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
	 * 获得当前有效合同客户下的客户对应收费标准
	 */
	public List<CompactRoomCoststandard> queryUsedCoststandard(
			String todayDate,Integer clientId,Integer compactId) throws Exception {
		/**2018年2月26日11:04:13 gd 修改过 sql的 顺序, 优化性能*/
		String hql = "from CompactRoomCoststandard crc where crc.status='"+Contants.VALID+"' and crc.beginDate<=:todayDate" ;
		if(compactId!=null){//pact_id
			hql = hql+" and crc.compact.id="+compactId;
		}
		hql+= " and crc.compactClient.id in" +
				"(select distinct clientId from Compact c where ";
		if(clientId!=null){
			hql = hql+" c.compactClient.id="+clientId;
			hql += " and c.isDestine='"+Contants.NORMARL+"' and c.endDate >=  :todayDate )";
		}else{
			hql += " c.isDestine='"+Contants.NORMARL+"' and c.endDate >=  :todayDate )";
		}
		Query query = this.getSession().createQuery(hql);
		query.setParameter("todayDate", todayDate);
		List<CompactRoomCoststandard> list  = query.list();
		return list;
	}

	
	/**
	 * 根据客户或帐套id获得客户对应房租收费标准
	 */
	public List<CompactRoomCoststandard> queryUsedRentCoststandard(Integer clientId,Integer accountTemplateId) throws Exception {
		String hql = "from CompactRoomCoststandard crc where crc.status='"+Contants.VALID+"' and crc.compactClient.id in(select distinct clientId from Compact c where  c.isDestine='"+Contants.NORMARL+"' )";
		if(clientId!=null){
			hql = hql+" and crc.compactClient.id="+clientId;
		}
		if(accountTemplateId!=null){
			hql = hql+" and crc.CCoststandard.accountTemplateId="+accountTemplateId;
		}
		hql = hql+" and crc.CCoststandard.itemId in (select ci.id from CItems ci where ci.itemName='"+Contants.RENT+"')";
		Query query = this.getSession().createQuery(hql);
		List<CompactRoomCoststandard> list = query.list();
		return list;
	}
	
	
	@Override
	public CItems getItemsById(Integer itemId) throws Exception {
		CItems c = null;
		String hql = "from CItems where id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", itemId);
		List<CItems> list = query.list();
		if(list!=null&&list.size()>0){
			c = list.get(0);
		}
		return c;
	}

	@Override
	public List<ClientBill> getPressMoneyClient(CostTransactForm form) throws Exception {
		StringBuffer sql = new StringBuffer("select b.client_id as clientId,(select name from compact_client where id=b.client_id) as clientName,sum(b.bills_expenses) as billExpenses,sum(b.delaying_expenses) as delayingExpenses,sum(b.bills_expenses)+sum(b.delaying_expenses) as payExpenses,sum(b.actually_paid) as actuallyPaid,sum(b.bills_expenses)+sum(b.delaying_expenses)-sum(b.actually_paid) as requireExpenses from c_bill b where b.status='0' ");
		if(form!=null){
			if(form.getClientId()!=null&&form.getClientId()!=0){
				sql.append(" and client_id=").append(form.getClientId());
			}
		}
		sql.append(" group by client_id");
		List list = null;
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List<ClientBill> list2 = new ArrayList<ClientBill>();
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				ClientBill cBill = new ClientBill();
				cBill.setClientId(GlobalMethod.ObjToStr(obj[0]));
				cBill.setClientName(GlobalMethod.ObjToStr(obj[1]));
				cBill.setBillExpenses(GlobalMethod.ObjToStr(obj[2]));
				cBill.setDelayingExpenses(GlobalMethod.ObjToStr(obj[3]));
				cBill.setPayExpenses(GlobalMethod.ObjToStr(obj[4]));
				cBill.setActuallyPaid(GlobalMethod.ObjToStr(obj[5]));
				cBill.setRequireExpenses(GlobalMethod.ObjToStr(obj[6]));
				list2.add(cBill);
			}
		}
		return list2;
	}

	//由于预收房租是在通知入住后缴纳，所有合同的查询条件为isDestine='常规' or isNotice='已通知入住'
	@Override
	public List getCurAvailClient(CostTransactForm form) throws Exception {
/**2018年2月27日15:35:44 gd 
 * sql 已优化 ,待测试
 * */
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select " +
				"cc.id as clientId," +
				"cc.name as clientName," +
				"a.amount as amount," +
				"c.isNotice as isNotice ");
		sqlBuf.append(" from compact_client cc,c_advance a,compact c " +
				"where a.client_id = cc.id and (");
		if("".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isDestine='");
			sqlBuf.append(Contants.NORMARL);
			sqlBuf.append("' or c.isNotice='");
			sqlBuf.append(Contants.HAVENOTICE);
		}else if("1".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isDestine='");
			sqlBuf.append(Contants.NORMARL);
		}else if("0".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isNotice='");
			sqlBuf.append(Contants.HAVENOTICE);
		}
		
		if(form.getClientId()!=null&&form.getClientId()!=0){
			sqlBuf.append("') and cc.id="+form.getClientId());
		}else{
			sqlBuf.append("') and cc.id =c.client_id");
		}
		List list = null;
		list = this.getSession().createSQLQuery(sqlBuf.toString()).list();
//		try {
//			list = jdbcTemplate.queryForList(sql);
//		} catch (Exception e) {}
		return list;
	}
	@Override
	public List getCurAvailClientWithAdvanceWuYF(CostTransactForm form) throws Exception {
/**2018年2月27日15:35:44 gd 
 * sql 已优化 ,待测试- 通过
 * */
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select " +
				"cc.id as clientId," +
				"cc.name as clientName," +
				"a.amount as amount," +
				"aw.amount as amountWuYF," +
				"c.isNotice as isNotice ");
//		sqlBuf.append(" from compact_client cc,c_advance a ,c_advance_wuyf aw,compact c " +
//				"where a.client_id = cc.id and aw.client_id = cc.id and (");
		sqlBuf.append(" from compact_client cc " +
				"left join c_advance a on  a.client_id = cc.id " +
				" left join c_advance_wuyf aw on aw.client_id = cc.id" +
				" left join compact c on cc.id = c.client_id " +
				"where  (");
		
		if("".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isDestine='");
			sqlBuf.append(Contants.NORMARL);
			sqlBuf.append("' or c.isNotice='");
			sqlBuf.append(Contants.HAVENOTICE);
		}else if("1".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isDestine='");
			sqlBuf.append(Contants.NORMARL);
		}else if("0".equals(GlobalMethod.NullToSpace(form.getStatyStatus()))){
			sqlBuf.append("c.isNotice='");
			sqlBuf.append(Contants.HAVENOTICE);
		}
		
		if(form.getClientId()!=null&&form.getClientId()!=0){
			sqlBuf.append("') and cc.id="+form.getClientId());
		}else{
			sqlBuf.append("') and cc.id =c.client_id");
		}
		List list = null;
		list = this.getSession().createSQLQuery(sqlBuf.toString()).list();
		return list;
	}
	/**
	 * 根据合同id找到客户
	 */
	public CompactClient getClientByCompactId(Integer compactId) {
		CompactClient compactClient = null;
		String hql = "from CompactClient cc where cc.id in(select clientId from Compact c where c.id="+compactId+")";
		List<CompactClient> list = this.getSession().createQuery(hql).list();
		if(list!=null&&list.size()>0){
			compactClient = list.get(0);
		}
		return compactClient;
	}

	/**
	 * 根据客户id和合同id找到其他的有效合同
	 */
	public Compact getCompactByClientIdAndCompactId(Integer id,
			Integer compactId) {
		Compact compact = null;
		String hql = "from Compact c where c.id<>"+compactId+" and c.clientId="+id+" and c.status='"+Contants.THROUGHAPPROVAL+"'";
		List<Compact> list = this.getSession().createQuery(hql).list();
		if(list!=null&&list.size()>0){
			compact = list.get(0);
		}
		return compact;
	}

	/**
	 * 获取客户下给的时间段的有效房租收费标准列表
	 */
	@Override
	public List<CompactRoomCoststandard> getUsedRentCoststandard(Integer clientId, String startDate,
			String endDate) throws Exception {
		//String hql = "from CompactRoomCoststandard crc where crc.status='"+Contants.VALID+"' and crc.CCoststandard.itemId in (select id from CItems where itemName='"+Contants.RENT+"') ";
		String hql = "from CompactRoomCoststandard crc where (crc.compact.isDestine='"+Contants.NORMARL+
		"' or crc.compact.isNotice='"+Contants.HAVENOTICE+"') and crc.CCoststandard.itemId in (select id from CItems where itemName='"+Contants.RENT+"') ";//RENT 房租 收费项
		if(!GlobalMethod.NullToSpace(endDate).equals("")){
			hql = hql+" and crc.beginDate<'"+endDate+"'";
		}
		if(!GlobalMethod.NullToSpace(startDate).equals("")){
			hql = hql+" and crc.endDate>='"+startDate+"'";
		}
		if(clientId!=null){
			hql = hql+" and crc.compactClient.id="+clientId;
		}
		Query query = this.getSession().createQuery(hql);
		List<CompactRoomCoststandard> list = query.list();
		return list;
	}
	
/**2018年1月17日09:22:11 gd 
 * 获取 客户对应 有效合同 时间段内   物业费季付 收费标准 列表
 * */
	@Override
	public List<CompactRoomCoststandard> getValidRentCoststandard(Integer clientId, String startDate,
			String endDate) throws Exception {
		String hql = "from CompactRoomCoststandard crc where (crc.compact.isDestine='"+Contants.NORMARL+"' or crc.compact.isNotice='"+Contants.HAVENOTICE
		+"') and crc.CCoststandard.itemId in" +
		" (select id from CItems where itemName in('"+Contants.WuYFJF+"','"+Contants.WuYFBNF+"','"+Contants.WuYFNF+"')) ";//获取物业费 收费项 
		if(!GlobalMethod.NullToSpace(endDate).equals("")){
			hql = hql+" and crc.beginDate<'"+endDate+"'";
		}
		if(!GlobalMethod.NullToSpace(startDate).equals("")){
			hql = hql+" and crc.endDate>='"+startDate+"'";
		}
		if(clientId!=null){
			hql = hql+" and crc.compactClient.id="+clientId;
		}
		Query query = this.getSession().createQuery(hql);
		List<CompactRoomCoststandard> list = query.list();
		return list;
	}
	
	
	@Override
	public List getPressDepositRentClient(CostTransactForm form) throws Exception {
		List list = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.id as clientId,t.name as clientName,t.pactExpenses,t.amount,(t.pactExpenses-t.amount) as payExpenses from ");
			sql.append("(select ");
				sql.append("cc.id,");
				sql.append("cc.name,");
				sql.append("(select sum(isnull(c.rent_deposit,0.000)) from compact c where c.client_id=cc.id and (c.is_rent_deposit='0' or c.is_rent_deposit is null)  and c.status='"+Contants.THROUGHAPPROVAL+"' and (c.isDestine='"+Contants.NORMARL+"' or c.isNotice='"+Contants.HAVENOTICE+"')) as pactExpenses,");
				sql.append("(select isnull(d.amount,0.000) from c_deposit d where d.client_id=cc.id and d.deposit_type='").append(Contants.RENT_DEPOSIT).append("') as amount ");
				sql.append("from compact_client cc ");
				sql.append("where cc.id in (select c.client_id from compact c where c.client_id=cc.id and c.status='"+Contants.THROUGHAPPROVAL+"' and (c.isDestine='"+Contants.NORMARL+"' or c.isNotice='"+Contants.HAVENOTICE+"'))");
			if(form!=null){
				if(form.getClientId()!=null){
					sql.append(" and cc.id=");
					sql.append(form.getClientId());
				}
			}
			sql.append(") t where t.amount<t.pactExpenses");
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List list2 = new ArrayList();
		//将得到的list中的object[]封装成map
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientId", GlobalMethod.ObjToStr(obj[0]));
				map.put("clientName", GlobalMethod.ObjToStr(obj[1]));
				map.put("pactExpenses", GlobalMethod.ObjToStr(obj[2]));
				map.put("amount", GlobalMethod.ObjToStr(obj[3]));
				map.put("payExpenses", GlobalMethod.ObjToStr(obj[4]));
				list2.add(map);
			}
		}
		return list2;
	}
	
	//获得需要缴装修押金客户列表
	public List getPressDepositDecorationClient(CostTransactForm form) throws Exception {
		List list = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.id as clientId,t.name as clientName,t.pactExpenses,t.amount,(t.pactExpenses-t.amount) as payExpenses from ");
			sql.append("(select ");
				sql.append("cc.id,");
				sql.append("cc.name,");
				sql.append("(select sum(isnull(c.decoration_deposit,0.000)) from compact c where c.client_id=cc.id and (c.is_decoration_deposit='0' or c.is_decoration_deposit is null)  and c.status='"+Contants.THROUGHAPPROVAL+"') as pactExpenses,");
				sql.append("(select isnull(d.amount,0.000) from c_deposit d where d.client_id=cc.id and d.deposit_type='").append(Contants.DECORATION_DEPOSIT).append("') as amount ");
				sql.append("from compact_client cc ");
				sql.append("where cc.id in (select c.client_id from compact c where c.client_id=cc.id   and c.status='"+Contants.THROUGHAPPROVAL+"')");
			if(form!=null){
				if(form.getClientId()!=null){
					sql.append(" and cc.id=");
					sql.append(form.getClientId());
				}
			}
			sql.append(") t where t.amount<t.pactExpenses");
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List list2 = new ArrayList();
		//将得到的list中的object[]封装成map
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientId", GlobalMethod.ObjToStr(obj[0]));
				map.put("clientName", GlobalMethod.ObjToStr(obj[1]));
				map.put("pactExpenses", GlobalMethod.ObjToStr(obj[2]));
				map.put("amount", GlobalMethod.ObjToStr(obj[3]));
				map.put("payExpenses", GlobalMethod.ObjToStr(obj[4]));
				list2.add(map);
			}
		}
		return list2;
	}
	
	public List getPressEarnestClient(CostTransactForm form) throws Exception {
		List list = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.id as clientId,t.name as clientName,t.pactExpenses,t.amount,(t.pactExpenses-t.amount) as payExpenses from ");
		sql.append("(select ");
		sql.append("cc.id,");
		sql.append("cc.name,");
		sql.append("(select sum(isnull(c.earnest,0.000)) from compact_intention c where c.client_id=cc.id and (c.isEarnest is null or c.isEarnest='0')  and  c.status='"+Contants.THROUGHAPPROVAL+"') as pactExpenses,");
		sql.append("(select isnull(d.amount,0.000) from c_earnest d where d.client_id=cc.id) as amount ");
		sql.append("from compact_client cc ");
		sql.append("where cc.id in (select c.client_id from compact_intention c where c.client_id=cc.id  and c.status='"+Contants.THROUGHAPPROVAL+"')");
		if(form!=null){
			if(form.getClientId()!=null&&form.getClientId()!=0){
				sql.append(" and cc.id=");
				sql.append(form.getClientId());
			}
		}
		sql.append(") t where t.amount<t.pactExpenses");
		list = this.getSession().createSQLQuery(sql.toString()).list();
		List list2 = new ArrayList();
		//将得到的list中的object[]封装成map
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientId", GlobalMethod.ObjToStr(obj[0]));
				map.put("clientName", GlobalMethod.ObjToStr(obj[1]));
				map.put("pactExpenses", GlobalMethod.ObjToStr(obj[2]));
				map.put("amount", GlobalMethod.ObjToStr(obj[3]));
				map.put("payExpenses", GlobalMethod.ObjToStr(obj[4]));
				list2.add(map);
			}
		}
		return list2;
	}

	@Override
	public List<Compact> getAvailCompactByClientId(int clientId)
			throws Exception {
		String hql = "from Compact c where c.clientId="+clientId+" and c.status='"+Contants.THROUGHAPPROVAL+"'";
		List<Compact> list = this.getSession().createQuery(hql).list();
		return list;
	}

	@Override
	public Integer save(Object obj) throws Exception {
		return (Integer)getHibernateTemplate().save(obj);
	}
	
	public int countCompact() {
		String hql = "select count(c) from Compact c where c.status='"+Contants.THROUGHAPPROVAL+"' and  c.isDestine='"+Contants.DESTINES+"' and c.isEarnest = '1'";
		Integer count = (Integer) getSession().createQuery(hql).uniqueResult();
		return count.intValue();
	}

	@Override
	public List<CompactRent> getRent(Map map) throws Exception {
		/**2018年1月26日15:03:23 gd 
		 * sql 已尝试 优化. 效果待测
		 * */
		StringBuffer sb = new StringBuffer("from CompactRent cr where cr.compact.id " );
		if(map.get("compactId")!=null){
			sb.append(" =" + map.get("compactId"));
		}else{
			sb.append("in(select c.id from Compact c where c.clientId=");
			sb.append(map.get("clientId"));
			sb.append(" and c.status='");
			sb.append(Contants.THROUGHAPPROVAL + "')");
		}
		
		sb.append(" and cr.beginDate<='");
		sb.append(map.get("billEndDate"));
		sb.append("' and cr.endDate>='");
		sb.append(map.get("billStartDate") + "'");
		
		sb.append(" order by cr.beginDate");
		List<CompactRent> list = this.getSession().createQuery(sb.toString()).list();
		return list;
	}

	@Override
	public List getCurClient(Integer clientId,Integer compactId) throws Exception {
		StringBuffer hql = new StringBuffer(" from CompactClient t  where ");
/**2018年2月26日09:45:02 gd 修改过sql 顺序 , 优化性能 */		
		hql.append("t.id in(select clientId from Compact where clientId= ");
		if(clientId!=null&&clientId!=0){
			hql.append(clientId);
		}else{
			hql.append("t.id");
		}
		if(compactId!=null&&compactId!=0){
			hql.append(" and id=").append(compactId);
		}
		hql.append(" and isDestine='").append(Contants.NORMARL).append("') ");
		
		List list = null;
		list = this.getSession().createQuery(hql.toString()).list();
		return list;
	}

	@Override
	public CAccounttemplate getAccounttemplateByLpId(Integer lpId)
			throws Exception {
		CAccounttemplate obj = null;
		StringBuffer hql = new StringBuffer(" from CAccounttemplate t ");
		hql.append(" where t.lpid ="+lpId);
		List list = null;
		list = this.getSession().createQuery(hql.toString()).list();
		if(list!=null&&list.size()>0){
			obj = (CAccounttemplate)list.get(0);
		}
		return obj;
	}

	@Override
	public List getPhoneNumByClientId(Integer id) throws Exception {
		StringBuffer hql = new StringBuffer("select new Map(t2.phoneNumber as phoneNumber,t2.roomCode as roomCode,t2.roomFullName as roomFullName,t4.roomId as roomId,t4.clientId as clientId,t4.pactId as pactId,t4.clientName as clientName,t4.clientType as clientType,t4.unitName as unitName,t4.linkName as linkName) from ERooms t2,Compact t3,ERoomClient t4 ");
		hql.append(" where t4.pactId=t3.id and t3.isDestine='").append(Contants.NORMARL).append("'");
		hql.append(" and t4.roomId=t2.roomId  and ISNULL(t2.phoneNumber,'')<> '' ");
		hql.append(" and t4.clientId =").append(id);
		hql.append(" order by t4.clientId ");
		Query query=this.getSession().createQuery(hql.toString());
		List roomPhoneList = null;
		roomPhoneList = query.list();
		return roomPhoneList;
	}

	@Override
	public List countMoneyByPhoneNum(String phoneNum) throws Exception {
		StringBuffer hql = new StringBuffer("select new Map(sum(cost) as totalMoney) from ECallInfo where callerPhone='")
			.append(phoneNum).append("' and isPaid='").append(Contants.ISNOTPAID).append("'").append(" and rootUser is null");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		list = query.list();
		return list;
	}

	@Override
	public CItems getItemByValue(String string) throws Exception {
		CItems item = null;
		StringBuffer hql = new StringBuffer("from CItems where value='").append(string).append("'");
		Query query=this.getSession().createQuery(hql.toString());
		List list = null;
		list = query.list();
		if(list!=null&&list.size()>0){
			item = (CItems)list.get(0);
		}
		return item;
	}
	
	/**
	 * @author luq
	 * 得到指定号码的生成账单的通话记录
	 * @param phone
	 * @return
	 * @throws Exception
	 * Date:Nov 24, 2011 
	 * Time:2:31:07 AM
	 */
	public List<ECallInfo> getCallInfoByNumber(String phone) throws Exception {
		StringBuffer hql = new StringBuffer("from ECallInfo where callerPhone='").append(phone).append("' and isPaid='").append(Contants.ISNOTPAID).append("'").append(" and rootUser is null");
		Query query=this.getSession().createQuery(hql.toString());
		List<ECallInfo> list = query.list();
		this.releaseSession(this.getSession());
		return list;
	}

	/**
	 * @author gd
	 */
	@Override
	public List<CCoststandard> getAllCoststandard() {
		StringBuffer hql = new StringBuffer("from CCoststandard ");
		List<CCoststandard> list = this.getSession().createQuery(hql.toString()).list();
		return list;
		
	}
}
