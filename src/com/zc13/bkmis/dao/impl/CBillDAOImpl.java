/**
 * Administrator
 */
package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICBillDAO;
import com.zc13.bkmis.form.CBillForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CTemporal;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

/**
 * @author Administrator Date：Dec 20, 2010 Time：3:48:28 PM
 */
public class CBillDAOImpl extends BasicDAOImpl implements ICBillDAO {
	/**
	 * 应收账款 CBillDAOImpl.getList
	 */
	@SuppressWarnings("unchecked")
	public PageBean getList(CBillForm formbean,List<Integer> clients) throws DataAccessException {
		PageBean pageBean = new PageBean(formbean.getPagesize());
		pageBean.setPagination(formbean.getPagination());
		
		String hql = " from CBill b where 1=1 ";
//		if (!ExtendUtil.checkNull(formbean.getStandardId())) {
//			hql += " and b.CCoststandard.id = :standard";
//		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and b.compactClient.name like :name";
		}
		if (!ExtendUtil.checkNull(formbean.getChequeNo())) {
			hql += " and b.chequeNo = :chequeNo";
		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			hql += " and b.itemId = :itemId";
		}
		if (!ExtendUtil.checkNull(formbean.getItemIds())) {
			hql += " and b.itemId in ("+formbean.getItemIds()+")";
		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			hql += " and b.paymentWay = :paymentWay";
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			hql += " and b.collectionDate >= :begin";
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			hql += " and b.collectionDate <= :end";
		}
		if (!ExtendUtil.checkNull(formbean.getStatus())) {
			hql += " and b.status = :status";
		}
		if (!ExtendUtil.checkNull(formbean.getBefore())) {
			hql += " and b.billDate >= :before";
			// formbean.setBefore(DateUtil.getNowDate("yyyy-MM"));
		}
		if (!ExtendUtil.checkNull(formbean.getAfter())) {
			hql += " and b.billDate <= :after";
//			formbean.setAfter(DateUtil.getNowDate("yyyy-MM"));
		}
//		if (!ExtendUtil.checkNull(formbean.getLpId())) {
//			hql += " and b.compactClient.id in (:clients)";
//		}
		String countHql = "select count(b) "+hql;
		hql += " order by b.compactClient.id,b.id desc";
		Query query = this.getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
		if (!ExtendUtil.checkNull(formbean.getBefore())) {
			query.setParameter("before", formbean.getBefore());
			countQuery.setParameter("before", formbean.getBefore());
		}
		if (!ExtendUtil.checkNull(formbean.getAfter())) {
			query.setParameter("after", formbean.getAfter());
			countQuery.setParameter("after", formbean.getAfter());
		}
//		if (!ExtendUtil.checkNull(formbean.getStandardId())) {
//			query.setParameter("standard", formbean.getStandardId());
//			countQuery.setParameter("standard", formbean.getStandardId());
//		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", "%" + formbean.getClientName() + "%");
			countQuery.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		if (!ExtendUtil.checkNull(formbean.getChequeNo())) {
			query.setParameter("chequeNo", formbean.getChequeNo());
			countQuery.setParameter("chequeNo", formbean.getChequeNo());
		}
		if (!ExtendUtil.checkNull(formbean.getStatus())) {
			query.setParameter("status", formbean.getStatus());
			countQuery.setParameter("status", formbean.getStatus());
		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			query.setParameter("itemId", formbean.getItemId());
			countQuery.setParameter("itemId", formbean.getItemId());
		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			query.setParameter("paymentWay", formbean.getPayType());
			countQuery.setParameter("paymentWay", formbean.getPayType());
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			query.setParameter("begin", formbean.getBegin());
			countQuery.setParameter("begin", formbean.getBegin());
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			query.setParameter("end", formbean.getEnd());
			countQuery.setParameter("end", formbean.getEnd());
		}
//		if (!ExtendUtil.checkNull(formbean.getLpId())) {
//			query.setParameterList("clients", clients);
//			countQuery.setParameterList("clients", clients);
//		}
		Integer tatol = (Integer) countQuery.uniqueResult();
		pageBean.setTatol(tatol);
		List list = query.setFirstResult(pageBean.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 应收账款-导出报表
	 * CBillDAOImpl.getExcelList
	 */
	@SuppressWarnings("unchecked")
	public List<CBill> getExcelList(CBillForm formbean, List<Integer> clients)throws DataAccessException{
		String hql = " from CBill b where 1=1 ";
//		if (!ExtendUtil.checkNull(formbean.getStandardId())) {
//			hql += " and b.CCoststandard.id = :standard";
//		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and b.compactClient.name like :name";
		}
//		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
//			hql += " and b.ERooms.roomCode like :roomCode";
//		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			hql += " and b.itemId = :itemId";
		}
		if (!ExtendUtil.checkNull(formbean.getItemIds())) {
			hql += " and b.itemId in ("+formbean.getItemIds()+")";
		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			hql += " and b.paymentWay = :paymentWay";
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			hql += " and b.collectionDate >= :begin";
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			hql += " and b.collectionDate <= :end";
		}
		if (!ExtendUtil.checkNull(formbean.getStatus())) {
			hql += " and b.status = :status";
		}
		if (!ExtendUtil.checkNull(formbean.getBefore())) {
			hql += " and b.billDate >= :before";
		}
		if (!ExtendUtil.checkNull(formbean.getAfter())) {
			hql += " and b.billDate <= :after";
		}
		
		if (clients!=null&&clients.size()>0) {
			hql += " and b.compactClient.id in (:clients)";
		}
		if (!ExtendUtil.checkNull(formbean.getBillCode())) {
			hql += " and b.billCode = '"+formbean.getBillCode()+"' ";
		}
		hql += " order by b.compactClient.id,b.id desc";
		Query query = this.getSession().createQuery(hql);
		if (!ExtendUtil.checkNull(formbean.getBefore())) {
			query.setParameter("before", formbean.getBefore());
		}
		if (!ExtendUtil.checkNull(formbean.getAfter())) {
			query.setParameter("after", formbean.getAfter());
		}
//		if (!ExtendUtil.checkNull(formbean.getStandardId())) {
//			query.setParameter("standard", formbean.getStandardId());
//			countQuery.setParameter("standard", formbean.getStandardId());
//		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", "%" + formbean.getClientName() + "%");
		}
//		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
//			query.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
//			countQuery.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
//		}
		if (!ExtendUtil.checkNull(formbean.getStatus())) {
			query.setParameter("status", formbean.getStatus());
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			query.setParameter("begin", formbean.getBegin());
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			query.setParameter("end", formbean.getEnd());
		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			query.setParameter("itemId", formbean.getItemId());
		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			query.setParameter("paymentWay", formbean.getPayType());
		}
		if (clients!=null&&clients.size()>0) {
			query.setParameterList("clients", clients);
		}
		List<CBill> list = query.list();
		return list;
	}
	/**
	 * 收费标准，帐套 CBillDAOImpl.getStandard
	 */
/*	public List getStandard(Integer lpId, Integer id)
			throws DataAccessException {
		String hql = "select s,a,e from CCoststandard s,CAccounttemplate a,ELp e "
				+ " where s.accountTemplateId = a.id "
				+ " and a.lpid=e.lpId"
				+ " and s.id = :id and a.lpid =:lpId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		query.setParameter("lpId", lpId);
		List list = query.list();
		return list;
	}*/

	/**
	 * 客户 CBillDAOImpl.getClientList
	 */
	public List getClientList() throws DataAccessException {
		String sql = "select distinct c.id,c.name,b.lp_id parent,'client' lb from compact_client c,e_room_client rc,e_rooms r,e_builds b,compact com ";
		sql += " where c.id=rc.client_id and rc.room_id=r.room_id and r.build_id=b.build_id and com.id = rc.pact_id and com.status='通过审批' ";
		sql += " union all select l.lp_id id,l.lp_name name,'1' parent,'lp' lb from e_lp l";
		Query query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	/**
	 * 账期 CBillDAOImpl.getBillDates
	 */
	public List getBillDates() throws DataAccessException {
		String hql = "select distinct b.billDate from CBill b where b.billDate is not null";
		Query query = getSession().createQuery(hql);
		List list = query.list();
		return list;
	}
	/**
	 * 收款销账查询
	 * CBillDAOImpl.getBillList
	 */
	public List<CBill> getBillList(CBillForm formbean)throws DataAccessException{
		String hql = "select b from CBill b where b.status = '0' ";
		hql += " and b.compactClient.id = :clientId";
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			hql += " and b.ERooms.roomCode like :roomCode";
		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			hql += " and b.itemId = :itemId";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", formbean.getClientId());
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			query.setParameter("roomCode", "%"+formbean.getRoomCode()+"%");
		}
		if (!ExtendUtil.checkNull(formbean.getItemId())) {
			query.setParameter("itemId", formbean.getItemId());
		}
		List<CBill> list = query.list();
		return list;
	}
	/**
	 * 收费/退款
	 * CBillDAOImpl.updateBill
	 */
	public void updateBill(CBill bill)throws DataAccessException {
		String hql = "update CBill b set b.paymentWay = :paymentWay,b.billType = :billType," +
				"b.billNum = :billNum,b.collectionDate = :collectionDate,b.chequeNo = :chequeNo," +
				"b.actuallyPaid = :actuallyPaid,b.status = :status,b.delayingExpenses = :delayingExpenses " +
				"where b.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("paymentWay", bill.getPaymentWay());
		query.setParameter("billType", bill.getBillType());
		query.setParameter("billNum", bill.getBillNum());
		query.setParameter("collectionDate", bill.getCollectionDate());
		query.setParameter("chequeNo", bill.getChequeNo());
		query.setParameter("actuallyPaid", bill.getActuallyPaid());
		query.setParameter("delayingExpenses", bill.getDelayingExpenses());
		query.setParameter("status", bill.getStatus());
		query.setParameter("id", bill.getId());
		query.executeUpdate();
	}
	/**
	 * 修改单据号
	 * CBillDAOImpl.updateBillNum
	 */
	public void updateBillNum(CBill bill)throws DataAccessException {
		String hql = "update CBill b set b.billNum = :billNum where b.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("billNum", bill.getBillNum());
		query.setParameter("id", bill.getId());
		query.executeUpdate();
	}
	/**
	 * 暂存款
	 * CBillDAOImpl.geTemporal
	 */
	public CTemporal geTemporal(Integer clientId)throws DataAccessException{
		String hql = "select t from CTemporal t where t.clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", clientId);
		CTemporal item = (CTemporal) query.uniqueResult();
		return item;
	}
	/**
	 * 缴纳暂存款
	 * CBillDAOImpl.updateTemporal
	 */
	public int updateTemporal(CTemporal temporal)throws DataAccessException {
		String hql = "update CTemporal t set t.amount = (:amount+t.amount) where t.clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", temporal.getAmount());
		query.setParameter("clientId", temporal.getClientId());
		int i = query.executeUpdate();
		return i;
	}
	/**
	 * 缴纳预收款
	 * CBillDAOImpl.updateAdvance
	 */
	public int updateAdvance(CAdvance advance)throws DataAccessException {
		String hql = "update CAdvance a set a.amount = (:amount+a.amount) where clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", advance.getAmount());
		query.setParameter("clientId", advance.getClientId());
		int i = query.executeUpdate();
		return i;
	}
	/**
	 * 缴纳预收物业费
	 * CBillDAOImpl.updateAdvanceWuYF
	 */
	public int updateAdvanceWuYF(CAdvanceWuYF advance)throws DataAccessException {
		String hql = "update CAdvanceWuYF a set a.amount = (:amount+a.amount) where clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", advance.getAmount());
		query.setParameter("clientId", advance.getClientId());
		int i = query.executeUpdate();
		return i;
	}
	
	/**
	 * 预收款查询
	 * CBillDAOImpl.getAdvance
	 */
	public CAdvance getAdvance(Integer clientId)throws DataAccessException{
		String hqlString = "select a from CAdvance a where a.clientId = :clientId";
		Query query = getSession().createQuery(hqlString);
		query.setParameter("clientId", clientId);
		CAdvance advance = (CAdvance) query.uniqueResult();
		return advance;
	}
	/**
	 * 预收物业费 查询
	 * CBillDAOImpl.getAdvanceWuYF
	 */
	public CAdvanceWuYF getAdvanceWuYF(Integer clientId)throws DataAccessException{
		String hqlString = "select aw from CAdvanceWuYF aw where aw.clientId = :clientId";
		Query query = getSession().createQuery(hqlString);
		query.setParameter("clientId", clientId);
		CAdvanceWuYF advance = (CAdvanceWuYF) query.uniqueResult();
		return advance;
	}
	/**
	 * 押金
	 * CBillDAOImpl.getDeposit
	 */
	public CDeposit getDeposit(Integer clientId,String depositType)throws DataAccessException{
		String hqlString = "select a from CDeposit a where a.clientId = :clientId and a.depositType=:depositType";
		Query query = getSession().createQuery(hqlString);
		query.setParameter("clientId", clientId);
		query.setParameter("depositType", depositType);
		CDeposit deposit = (CDeposit) query.uniqueResult();
		return deposit;
	}
	public CEarnest getEarnest(Integer clientId)throws DataAccessException{
		String hqlString = "select a from CEarnest a where a.clientId = :clientId";
		Query query = getSession().createQuery(hqlString);
		query.setParameter("clientId", clientId);
		CEarnest earnest = (CEarnest) query.uniqueResult();
		return earnest;
	}
	/**
	 * 客户退款查询
	 * CBillDAOImpl.getRefundList
	 */
	public PageBean getRefundList(CBillForm formbean)throws DataAccessException{
		PageBean page = new PageBean(formbean.getPagesize());
		page.setPagination(formbean.getPagination());
		String hql = " from CCharge b,CompactClient cc where b.clientId=cc.id ";
		if (!ExtendUtil.checkNull(formbean.getCharge().getBillNum())) {
			hql += " and b.billNum like :billNum";
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			hql += " and b.date = :begin";
		}
		if(formbean.getLpId()!=null&&formbean.getLpId()!=0){
			hql += " and cc.lpId="+formbean.getLpId();
		}
//		if (!ExtendUtil.checkNull(formbean.getEnd())) {
//			hql += " and b.date <= :end";
//		}
		if ("1".equals(formbean.getRole())) {
			hql += " and b.userId = :userId";
		}else if ("0".equals(formbean.getRole()) && !ExtendUtil.checkNull(formbean.getUserId())) {
			hql += " and b.userId = :userId";
		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and cc.name = :name";
		}
		String countHql = "select count(b)"+hql;
		hql ="select b,cc"+hql+ " order by b.id desc";
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
//		query.setParameter("clientId", formbean.getClientId());
//		countQuery.setParameter("clientId", formbean.getClientId());
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			query.setParameter("begin", formbean.getBegin());
			countQuery.setParameter("begin", formbean.getBegin());
		}
//		if (!ExtendUtil.checkNull(formbean.getEnd())) {
//			query.setParameter("end", formbean.getEnd());
//			countQuery.setParameter("end", formbean.getEnd());
//		}
		if (!ExtendUtil.checkNull(formbean.getCharge().getBillNum())) {
			query.setParameter("billNum", "%"+formbean.getCharge().getBillNum()+"%");
			countQuery.setParameter("billNum", "%"+formbean.getCharge().getBillNum()+"%");
		}
		if ("1".equals(formbean.getRole())) {
			query.setParameter("userId", formbean.getUserId());
			countQuery.setParameter("userId", formbean.getUserId());
		}else if ("0".equals(formbean.getRole()) && !ExtendUtil.checkNull(formbean.getUserId())) {
			query.setParameter("userId", formbean.getUserId());
			countQuery.setParameter("userId", formbean.getUserId());
		}
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", formbean.getClientName());
			countQuery.setParameter("name", formbean.getClientName());
		}
		Integer tatol = (Integer) countQuery.uniqueResult();
		page.setTatol(tatol.intValue());
		List<CBill> list = query.setFirstResult(page.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		page.setList(list);
		return page;
	}
	/**
	 * 根据收费表中账单id查询账单
	 * CBillDAOImpl.getRefundBillList
	 */
	public List getRefundBillList(CBillForm billForm)throws DataAccessException {
		String ids = billForm.getBillId();
		List list = new ArrayList();
		if (!ExtendUtil.checkNull(ids)) {
			ids = "'" + ids.replaceAll(",", "','") + "'";
			String hql = "from CBill bill,CItems item where bill.id in (" +ids+ ") and bill.itemId=item.id";
			Query query = getSession().createQuery(hql);
			list = query.list();
		}
		return list;
	}
	/**
	 * 查询当日收费操作
	 * CBillDAOImpl.getChargeList
	 */
	public List getChargeList(CBillForm billForm)throws DataAccessException{
		String today = DateUtil.getNowDate("yyyy-MM-dd");
		String hql = "select b,cc from CCharge b,CompactClient cc where b.clientId=cc.id and date = '"+today+"' and b.clientId = :clientId and b.userId = :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", billForm.getClientId());
		query.setParameter("userId", billForm.getUserId());
		List list = query.list();
		return list;
	}
	/**
	 * 电费查询
	 * CBillDAOImpl.getElectricity
	 */
	/*public PageBean getElectricity(CBillForm formbean) throws DataAccessException{
		PageBean page = new PageBean(formbean.getPagesize());
		page.setPagination(formbean.getPagination());
		String hql = " from CBill b where b.standardName='电费' and b.CCoststandard is null ";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and b.compactClient.name = :name";
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			hql += " and b.ERooms.roomCode = :roomCode";
		}
		hql += " and b.billDate >= :before";
		hql += " and b.billDate <= :after";
		String countHql = "select count(b)"+hql;
		hql += " order by b.id desc";
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
		query.setParameter("before", formbean.getBefore());
		query.setParameter("after", formbean.getAfter());
		countQuery.setParameter("before", formbean.getBefore());
		countQuery.setParameter("after", formbean.getAfter());
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", "%" + formbean.getClientName() + "%");
			countQuery.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			query.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
			countQuery.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
		}
		Integer tatol = (Integer) countQuery.uniqueResult();
		page.setTatol(tatol.intValue());
		List<CBill> list = query.setFirstResult(page.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		page.setList(list);
		return page;
	}
	public List<CBill> getExcelList1(CBillForm formbean) throws DataAccessException{
		String hql = " from CBill b where b.standardName='电费' and b.CCoststandard is null ";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and b.compactClient.name = :name";
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			hql += " and b.ERooms.roomCode = :roomCode";
		}
		hql += " and b.billDate >= :before";
		hql += " and b.billDate <= :after";
		hql += " order by b.id desc";
		Query query = getSession().createQuery(hql);
		query.setParameter("before", formbean.getBefore());
		query.setParameter("after", formbean.getAfter());
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			query.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
		}
		List<CBill> list = query.list();
		return list;
	}*/
	/**
	 * 押金
	 * CBillDAOImpl.getDeposit
	 */
	public PageBean getDeposit(CBillForm formbean)throws DataAccessException{
		PageBean page = new PageBean(formbean.getPagesize());
		page.setPagination(formbean.getPagination());
		String hql = " from CDeposit c,CompactClient cc where c.clientId = cc.id";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql += " and cc.name = :name";
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			hql += " and c.date >= :before";
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			hql += " and c.date <= :after";
		}
		if ("0".equals(formbean.getDepositType())) {
			hql += " and c.amount = 0";
		}else if ("1".equals(formbean.getDepositType())) {
			hql += " and c.amount > 0";
		}
		String countHql = "select count(c)"+hql;
		hql = "select c,cc"+hql +" order by c.id ";
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createQuery(countHql);
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query.setParameter("name", formbean.getClientName());
			countQuery.setParameter("name", formbean.getClientName());
		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			query.setParameter("before", formbean.getBegin());
			countQuery.setParameter("before", formbean.getBegin());
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			query.setParameter("after", formbean.getEnd());
			countQuery.setParameter("after", formbean.getEnd());
		}
		Integer tatol = (Integer) countQuery.uniqueResult();
		page.setTatol(tatol.intValue());
		List<CBill> list = query.setFirstResult(page.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		page.setList(list);
		return page;
	}
	/**
	 * 返还押金
	 * CBillDAOImpl.returnDeposit
	 */
	public void returnDeposit(CBillForm formbean)throws DataAccessException{
		String hql = "update CDeposit c set c.amount = (c.amount-:amount),c.amountReturn = (:amount+c.amountReturn),c.returnDate = :returnDate where c.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", formbean.getAmount());
		query.setParameter("id", formbean.getDepositId());
		query.setParameter("returnDate", DateUtil.getNowDate("yyyy-MM-dd"));
		query.executeUpdate();
	}
	public void updateDeposit(CDeposit deposit)throws DataAccessException{
		String hql = "update CDeposit t set t.amount = (:amount+t.amount)," +
				"t.date = :date,t.billType= :billType,t.billNum= :billNum where t.clientId = :clientId and t.depositType=:depositType ";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", deposit.getAmount());
		query.setParameter("date", deposit.getDate());
		query.setParameter("billType", deposit.getBillType());
		query.setParameter("billNum", deposit.getBillNum());
		query.setParameter("clientId", deposit.getClientId());
		query.setParameter("depositType", deposit.getDepositType());
		query.executeUpdate();
	}
	@Override
	public List getAllChargeList(CBillForm formbean)
			throws DataAccessException {
		String hql = " from CCharge b,CompactClient cc,MUser u where b.clientId=cc.id and b.userId=u.userid ";
//		if (!ExtendUtil.checkNull(formbean.getCharge().getBillNum())) {
//			hql += " and b.billNum like :billNum";
//		}
		if (!ExtendUtil.checkNull(formbean.getUserId())) {
			hql += " and b.userId = :userId";
		}
		
		if (!ExtendUtil.checkNull(formbean.getChequeNo())) {
			hql += " and b.chequeNo = :chequeNo";
		}
		
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			hql += " and b.date >= :begin";
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			hql += " and b.date <= :end";
		}
//		if (!ExtendUtil.checkNull(formbean.getClientId())) {
//			hql += " and b.clientId = :clientId";
//		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			hql += " and b.payType = :payType";
		}
		if(formbean.getLpId()!=null&&formbean.getLpId()!=0){
			hql += " and b.lpId="+formbean.getLpId();
		}
		hql ="select b,cc,u "+hql+ " order by b.id desc";
		Query query = getSession().createQuery(hql);
//		if (!ExtendUtil.checkNull(formbean.getClientId())) {
//			query.setParameter("clientId", formbean.getClientId());
//		}
		if (!ExtendUtil.checkNull(formbean.getBegin())) {
			query.setParameter("begin", formbean.getBegin());
		}
		if (!ExtendUtil.checkNull(formbean.getEnd())) {
			query.setParameter("end", formbean.getEnd());
		}
//		if (!ExtendUtil.checkNull(formbean.getCharge().getBillNum())) {
//			query.setParameter("billNum", "%"+formbean.getCharge().getBillNum()+"%");
//		}
		if (!ExtendUtil.checkNull(formbean.getUserId())) {
			query.setParameter("userId", formbean.getUserId());
		}
		if (!ExtendUtil.checkNull(formbean.getChequeNo())) {
			query.setParameter("chequeNo", formbean.getChequeNo());
		}
		if (!ExtendUtil.checkNull(formbean.getPayType())) {
			query.setParameter("payType", formbean.getPayType());
		}
		List list = query.list();
		return list;
	}
	/**
	 * 查询所有的客户
	 * CBillDAOImpl.queryCollect
	 */
	@SuppressWarnings("unchecked")
	public PageBean queryCollect(CBillForm formbean) throws DataAccessException {
		PageBean pageBean = new PageBean(formbean.getPagesize());
		pageBean.setPagination(formbean.getPagination());
	//	String hql = "from CompactClient c,CBill b where c.id=b.compactClient.id and b.status = '0'";
		//合同
		String hql1 = " from CompactClient c,Compact p,ERoomClient rc,ERooms r " +
				"where c.id = p.clientId and c.id = rc.clientId and rc.roomId=r.roomId " +
				"and p.status='"+Contants.THROUGHAPPROVAL+"' and p.isDestine !='"+Contants.HASOVER+"'";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql1 += " and c.name like :name";
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			hql1 += " and r.roomCode like :roomCode";
		}
		if(formbean.getLpId()!=null&&formbean.getLpId()!=0){
			hql1 += " and c.lpId="+formbean.getLpId();
		}
		String countHql1 = "select count(distinct c) "+hql1;
		hql1 ="from CompactClient cc where cc in(select distinct c "+hql1+ ") order by cc.code";
		Query query1 = this.getSession().createQuery(hql1);
		Query countQuery1 = getSession().createQuery(countHql1);
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query1.setParameter("name", "%" + formbean.getClientName() + "%");
			countQuery1.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			query1.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
			countQuery1.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
		}
		Integer tatol1 = (Integer) countQuery1.uniqueResult();
		//pageBean.setTatol(tatol);
		//List list1 = query1.setFirstResult(pageBean.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		List list1 = query1.list();
		
		//意向书
		String hql2 = "from CompactClient c,CompactIntention p where c.id=p.clientId  " +
				"and p.status='"+Contants.THROUGHAPPROVAL+"' and (p.isConvertCompact='"+Contants.NOCONVERT+"' or p.isConvertCompact is null )";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql2 += " and c.name like :name";
		}
		if(formbean.getLpId()!=null&&formbean.getLpId()!=0){
			hql1 += " and c.lpId="+formbean.getLpId();
		}
		String countHql2 = "select count(distinct c) "+hql2;
		hql2 ="from CompactClient cc where cc in(select distinct c "+hql2+ ") order by cc.code";
		Query query2 = this.getSession().createQuery(hql2);
		Query countQuery2 = getSession().createQuery(countHql2);
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query2.setParameter("name", "%" + formbean.getClientName() + "%");
			countQuery2.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		
		Integer tatol2 = (Integer) countQuery2.uniqueResult();
		//List list2 = query2.setFirstResult(pageBean.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		List list2 = query2.list();
		//pageBean.setList(list);
		
		List list = new ArrayList();
		list.addAll(list2);
		list.addAll(list1);
		pageBean.setTatol(tatol1+tatol2);
		int fromIndex = pageBean.getStartPage();
		int toIndex = list.size()>(pageBean.getStartPage()+formbean.getPagesize())?(pageBean.getStartPage()+formbean.getPagesize()):list.size();
		pageBean.setList(list.subList(fromIndex, toIndex));
		return pageBean;
	}	
	/**
	 * 定金
	 * CBillDAOImpl.updateEarnest
	 */
	public void updateEarnest(CEarnest earnest)throws DataAccessException {
		String hql="update CEarnest t set t.amount = (:amount+t.amount),t.date = :date,t.billType = :billType,t.billNum = :billNum where t.clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", earnest.getAmount());
		query.setParameter("clientId", earnest.getClientId());
		query.setParameter("date", earnest.getDate());
		query.setParameter("billType", earnest.getBillType());
		query.setParameter("billNum", earnest.getBillNum());
		query.executeUpdate();
	}
	public void updateCompact(Integer clientId)throws DataAccessException {
		String hql = "update Compact t set t.isEarnest = '1' where t.clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", clientId);
		query.executeUpdate();
	}
	/**
	 * 查询客户合同
	 * CBillDAOImpl.queryCompact
	 */
	@SuppressWarnings("unchecked")
	public List<Compact> queryCompact(Integer clientId)throws DataAccessException {
		String hql = "from Compact t where t.clientId = :clientId and t.isDestine in('"+Contants.NORMARL+"','"+Contants.DESTINES+"')";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", clientId);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<Compact> queryCompact(List<CompactClient> clients)throws DataAccessException {
		String hql = "from Compact t where t.clientId in (:clients) and t.isDestine in('"+Contants.NORMARL+"','"+Contants.DESTINES+"')";
		Query query = getSession().createQuery(hql);
		List<Integer> integers = new ArrayList<Integer>();
		for (CompactClient client : clients) {
			integers.add(client.getId());
		}
		if (integers==null || integers.size()==0) {
			return null;
		} else {
			query.setParameterList("clients", integers);
			return query.list();
		}
	}
	@SuppressWarnings("unchecked")
	public CItems getItems(String value) throws DataAccessException{
		String hql = "from CItems t where t.value='"+value.trim()+"'";
		Query query = getSession().createQuery(hql);
		CItems items = null;
		List<CItems> list = query.list();
		if (list!=null && list.size()>0) {
			items = list.get(0);
		}
		return items;
	}
	public CItems getItemsByName(String value) throws DataAccessException{
		String hql = "from CItems t where t.itemName='"+value.trim()+"'";
		Query query = getSession().createQuery(hql);
		CItems items = null;
		List<CItems> list = query.list();
		if (list!=null && list.size()>0) {
			items = list.get(0);
		}
		return items;
	}
	
	
	public Integer maxBillId()throws DataAccessException{
		String hql = "select max(t.id) as id from CBill t";
		Query query = getSession().createQuery(hql);
		return (Integer) query.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<CBill> queryBill(Integer[] id)throws DataAccessException {
		String hql = "from CBill t where t.id in (:id)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("id", id);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<CCharge> queryCharge(Integer[] id)throws DataAccessException {
		String hql = "from CCharge t where t.id in (:id)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("id", id);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<Integer> queryClientId(Integer lpid)throws DataAccessException {
		String hql = "select distinct rc.clientId from ERoomClient rc,ERooms r,Compact c where rc.roomId=r.roomId" +
				" and rc.pactId = c.id and c.status = '"+Contants.THROUGHAPPROVAL+"'";
		if (!ExtendUtil.checkNull(lpid)) {
			hql += " and r.EBuilds.ELp.lpId = :lpId";
		}
		Query query = getSession().createQuery(hql);
		if (!ExtendUtil.checkNull(lpid)) {
			query.setParameter("lpId", lpid);
		}
		return query.list();
	}
	public PageBean queryClient(CBillForm billForm) {
		PageBean pageBean = new PageBean(billForm.getPagesize());
		pageBean.setPagination(billForm.getPagination());
		String hql = " from CompactClient cc,Compact c " +
				"where cc.id=c.clientId and c.status = '"+Contants.THROUGHAPPROVAL+"'";
		if (!ExtendUtil.checkNull(billForm.getClientName())) {
			hql += " and cc.name like :name";
		}
		String countHql = "select count(distinct cc)" +hql;
		
		Query query = getSession().createQuery("select distinct cc"+hql);
		Query countQuery = getSession().createQuery(countHql);
		
		if (!ExtendUtil.checkNull(billForm.getClientName())) {
			query.setParameter("name", "%" + billForm.getClientName() + "%");
			countQuery.setParameter("name", "%" + billForm.getClientName() + "%");
		}
		Integer tatol = (Integer) countQuery.uniqueResult();
		pageBean.setTatol(tatol);
		List list = query.setFirstResult(pageBean.getStartPage()).setMaxResults(billForm.getPagesize()).list();
		pageBean.setList(list);
		return pageBean;
	}
	@SuppressWarnings("unchecked")
	public List<CompactClient> getClients(CBillForm billForm) {
		String hql = " from CompactClient cc,Compact c where cc.id=c.clientId and c.status = '"+Contants.THROUGHAPPROVAL+"'";
		if (!ExtendUtil.checkNull(billForm.getClientName())) {
			hql += " and cc.name like :name";
		}
		Query query = getSession().createQuery("select distinct cc"+hql);
		if (!ExtendUtil.checkNull(billForm.getClientName())) {
			query.setParameter("name", "%" + billForm.getClientName() + "%");
		}
		return query.list();
	}
	
	public double[] getCost(Integer clientId) {
		String hql = "select isnull(t.rentDeposit,0) as rentDeposit,isnull(t.decorationDeposit,0) as decorationDeposit,isnull(t.earnest,0) as earnest from( select "+
			"((select sum(isnull(c.rent_deposit,0.000)) from compact c where c.client_id=cc.id and (c.is_rent_deposit='0' or c.is_rent_deposit is null)  and c.status='通过审批')-(select isnull(d.amount,0.000) from c_deposit d where d.client_id=cc.id and d.deposit_type='"+Contants.RENT_DEPOSIT+"')) as rentDeposit,"+
			"((select sum(isnull(c.decoration_deposit,0.000)) from compact c where c.client_id=cc.id and (c.is_decoration_deposit='0' or c.is_decoration_deposit is null)  and c.status='通过审批')-(select isnull(d.amount,0.000) from c_deposit d where d.client_id=cc.id and d.deposit_type='"+Contants.DECORATION_DEPOSIT+"')) as decorationDeposit,"+
			"((select sum(isnull(c.earnest,0.000)) from compact_intention c where c.client_id=cc.id and (c.isEarnest ='0' or c.isEarnest is null)  and c.status='通过审批')-(select isnull(d.amount,0.000) from c_earnest d where d.client_id=cc.id)) as earnest"+
			" from compact_client cc where cc.id = :id) t";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("id", clientId);
		Object[] objects = (Object[]) query.uniqueResult();
		return new double[]{Double.parseDouble(objects[0].toString()),Double.parseDouble(objects[1].toString()),Double.parseDouble(objects[2].toString())} ;
	}
	
	//更新意向书为已缴定金
	@Override
	public void updateCompactIntention(Integer clientId) throws DataAccessException {
		String hql = "update CompactIntention t set t.isEarnest = '1' where t.clientId = :clientId";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", clientId);
		query.executeUpdate();
	}
	@Override
	public String getMaxBillNum() throws DataAccessException {
		String hql = "select max(billNum) from CBill where billType='0'";
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}
	@Override
	public PageBean queryCurrentInClient(CBillForm formbean) throws DataAccessException {
		PageBean pageBean = new PageBean(formbean.getPagesize());
		pageBean.setPagination(formbean.getPagination());
	//	String hql = "from CompactClient c,CBill b where c.id=b.compactClient.id and b.status = '0'";
		//合同
		String hql1 = " from CompactClient c,Compact p,ERoomClient rc,ERooms r where c.id = p.clientId and c.id = rc.clientId and rc.roomId=r.roomId and p.isDestine='"+Contants.NORMARL+"' ";
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			hql1 += " and c.name like :name";
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			hql1 += " and r.roomCode like :roomCode";
		}
		if(formbean.getLpId()!=null&&formbean.getLpId()!=0){
			hql1 += " and c.lpId="+formbean.getLpId();
		}
		String countHql1 = "select count(distinct c) "+hql1;
		hql1 ="from CompactClient cc where cc in(select distinct c "+hql1+ ") order by cc.code";
		Query query1 = this.getSession().createQuery(hql1);
		Query countQuery1 = getSession().createQuery(countHql1);
		if (!ExtendUtil.checkNull(formbean.getClientName())) {
			query1.setParameter("name", "%" + formbean.getClientName() + "%");
			countQuery1.setParameter("name", "%" + formbean.getClientName() + "%");
		}
		if (!ExtendUtil.checkNull(formbean.getRoomCode())) {
			query1.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
			countQuery1.setParameter("roomCode", "%" + formbean.getRoomCode() + "%");
		}
		Integer tatol1 = (Integer) countQuery1.uniqueResult();
		pageBean.setTatol(tatol1);
		List list1 = query1.setFirstResult(pageBean.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		pageBean.setList(list1);
		return pageBean;
	}
	/**
	 * 财务使用的房租统计信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageBean queryRentInfo(CBillForm formbean) {
		PageBean pageBean = new PageBean(formbean.getPagesize());
		pageBean.setPagination(formbean.getPagination());
		
		StringBuffer sqlBuf = new StringBuffer();
		//2012-12-13修改了sql语句这样查询除迁出的合同客户不查询其他全部查询
		/*sqlBuf.append("select bill.client_id,bill.name,bill.rent,bill.room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit");
		sqlBuf.append(" from ");
		sqlBuf.append(" (select sum(bill.bills_expenses) rent,c.name,bill.client_id,c.room_codes from compact c,c_bill bill where c.id = bill.compact_id ");
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getStartTime()))){
			sqlBuf.append(" and bill.bill_date='");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getStartTime()));
			sqlBuf.append("'");
		}
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getClientName()))){
			sqlBuf.append(" and c.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='");
		sqlBuf.append(Contants.RENT);
		sqlBuf.append("') GROUP BY bill.client_id,c.name,c.room_codes,bill.bill_date) bill");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN (select * from c_deposit cd where cd.deposit_type='");
		sqlBuf.append(Contants.RENT_DEPOSIT);
		sqlBuf.append("') deposit on deposit.client_id= bill.client_id ");
		sqlBuf.append(" LEFT JOIN (select * from c_deposit cd where cd.deposit_type='");
		sqlBuf.append(Contants.DECORATION_DEPOSIT);
		sqlBuf.append("') decodeposit on decodeposit.client_id= bill.client_id");*/
		/*sqlBuf.append("select distinct c.client_id,cc.name,bill.rent,bill.room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit from ");
		sqlBuf.append(" (select c.client_id from compact c where c.isNotice!='"+Contants.HASGO+"') c LEFT JOIN compact_client cc on c.client_id = cc.id LEFT JOIN ");
		sqlBuf.append(" (select sum(bill.bills_expenses) rent,c.name,bill.client_id,c.room_codes,bill.bill_date from compact c,c_bill bill where c.id = bill.compact_id");
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='"+Contants.RENT+"') GROUP BY bill.client_id,c.name,c.room_codes,bill.bill_date) bill ");
		sqlBuf.append(" on c.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = bill.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='rent_deposit') deposit on ");
		sqlBuf.append(" deposit.client_id= bill.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='decoration_deposit') decodeposit on decodeposit.client_id= bill.client_id");
		sqlBuf.append(" where 1=1 ");
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getStartTime()))){
			sqlBuf.append(" and bill.bill_date='");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getStartTime()));
			sqlBuf.append("'");
		}
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getClientName()))){
			sqlBuf.append(" and cc.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append("order by cc.name");*///2013-03-01 将代码注释sql进行改变
		sqlBuf.append("select distinct c.client_id,cc.name,bill.rent,REPLACE(c.room_codes,' ','') room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit from ");
		sqlBuf.append(" (select c.client_id,c.room_codes from compact c where c.isDestine!='"+Contants.HASOVER+"') c LEFT JOIN compact_client cc on c.client_id = cc.id LEFT JOIN ");
		sqlBuf.append(" (select sum(bill.bills_expenses) rent,bill.client_id,c.room_codes,bill.bill_date from compact c,c_bill bill where c.id = bill.compact_id");
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='"+Contants.RENT+"') ");
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getStartTime()))){
			sqlBuf.append(" and SUBSTRING(bill.bill_date,0,8)='");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getStartTime()));
			sqlBuf.append("'");
		}
		sqlBuf.append(" GROUP BY bill.client_id,c.room_codes,bill.bill_date) bill");
		sqlBuf.append(" on c.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = c.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='rent_deposit') deposit on ");
		sqlBuf.append(" deposit.client_id= c.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='decoration_deposit') decodeposit on decodeposit.client_id= c.client_id");
		sqlBuf.append(" where 1=1 ");
		if(!"".equals(GlobalMethod.NullToSpace(formbean.getClientName()))){
			sqlBuf.append(" and cc.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(formbean.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append("order by c.client_id");
		Query query = this.getSession().createSQLQuery(sqlBuf.toString());
		Integer tatol1 = query.list().size();
		pageBean.setTatol(tatol1);
		List list = query.setFirstResult(pageBean.getStartPage()).setMaxResults(formbean.getPagesize()).list();
		List formatList = new ArrayList();
		if(list != null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientId", obj[0]);
				map.put("clientName", obj[1]);
				map.put("roomCodes", obj[3]);
				map.put("rent", obj[2]);
				map.put("advanceRent", obj[4]);
				map.put("rentDeposit", obj[5]);
				map.put("decorationDeposit", obj[6]);
				formatList.add(map);
			}
		}
		pageBean.setList(formatList);
		return pageBean;
	}
	/**
	 * 查询财务使用的房租信息用于导出excel
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List queryRentInfoExportExcel(CBillForm billForm) {
		
		StringBuffer sqlBuf = new StringBuffer();
		/*sqlBuf.append("select bill.client_id,bill.name,bill.rent,bill.room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit");
		sqlBuf.append(" from (select sum(bill.bills_expenses) rent,c.name,bill.client_id,c.room_codes from compact c,c_bill bill where c.id = bill.compact_id ");
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getStartTime()))){
			sqlBuf.append(" and bill.bill_date='");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getStartTime()));
			sqlBuf.append("'");
		}
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getClientName()))){
			sqlBuf.append(" and c.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='");
		sqlBuf.append(Contants.RENT);
		sqlBuf.append("') GROUP BY bill.client_id,c.name,c.room_codes,bill.bill_date) bill");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN (select * from c_deposit cd where cd.deposit_type='");
		sqlBuf.append(Contants.RENT_DEPOSIT);
		sqlBuf.append("') deposit on deposit.client_id= bill.client_id ");
		sqlBuf.append(" LEFT JOIN (select * from c_deposit cd where cd.deposit_type='");
		sqlBuf.append(Contants.DECORATION_DEPOSIT);
		sqlBuf.append("') decodeposit on decodeposit.client_id= bill.client_id");*/
		/*sqlBuf.append("select distinct c.client_id,cc.name,bill.rent,bill.room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit from ");
		sqlBuf.append(" (select c.client_id from compact c where c.isNotice!='"+Contants.HASGO+"') c LEFT JOIN compact_client cc on c.client_id = cc.id LEFT JOIN ");
		sqlBuf.append(" (select sum(bill.bills_expenses) rent,c.name,bill.client_id,c.room_codes,bill.bill_date from compact c,c_bill bill where c.id = bill.compact_id");
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='"+Contants.RENT+"') GROUP BY bill.client_id,c.name,c.room_codes,bill.bill_date) bill ");
		sqlBuf.append(" on c.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = bill.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='rent_deposit') deposit on ");
		sqlBuf.append(" deposit.client_id= bill.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='decoration_deposit') decodeposit on decodeposit.client_id= bill.client_id");
		sqlBuf.append(" where 1=1 ");
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getStartTime()))){
			sqlBuf.append(" and bill.bill_date='");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getStartTime()));
			sqlBuf.append("'");
		}
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getClientName()))){
			sqlBuf.append(" and cc.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append("order by cc.name");*///2013-03-01进行注释进行了修改
		sqlBuf.append("select distinct c.client_id,cc.name,bill.rent,REPLACE(c.room_codes,' ','') room_codes,ca.amount advance,deposit.amount deposit,decodeposit.amount decodeposit from ");
		sqlBuf.append(" (select c.client_id,c.room_codes from compact c where c.isDestine!='"+Contants.HASOVER+"') c LEFT JOIN compact_client cc on c.client_id = cc.id LEFT JOIN ");
		sqlBuf.append(" (select sum(bill.bills_expenses) rent,bill.client_id,c.room_codes,bill.bill_date from compact c,c_bill bill where c.id = bill.compact_id");
		sqlBuf.append(" and bill.item_id = (select ci.id from c_items ci where ci.item_name='"+Contants.RENT+"') ");
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getStartTime()))){
			sqlBuf.append(" and SUBSTRING(bill.bill_date,0,8)='");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getStartTime()));
			sqlBuf.append("'");
		}
		sqlBuf.append(" GROUP BY bill.client_id,c.room_codes,bill.bill_date) bill");
		sqlBuf.append(" on c.client_id = bill.client_id ");
		sqlBuf.append(" LEFT JOIN c_advance ca on ca.client_id = c.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='rent_deposit') deposit on ");
		sqlBuf.append(" deposit.client_id= c.client_id  LEFT JOIN (select * from c_deposit cd where cd.deposit_type='decoration_deposit') decodeposit on decodeposit.client_id= c.client_id");
		sqlBuf.append(" where 1=1 ");
		if(!"".equals(GlobalMethod.NullToSpace(billForm.getClientName()))){
			sqlBuf.append(" and cc.name like '%");
			sqlBuf.append(GlobalMethod.NullToSpace(billForm.getClientName()));
			sqlBuf.append("%'");
		}
		sqlBuf.append("order by c.client_id");
		Query query = this.getSession().createSQLQuery(sqlBuf.toString());
		List list = query.list();
		List formatList = new ArrayList();
		if(list != null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientId", obj[0]);
				map.put("clientName", obj[1]);
				map.put("roomCodes", obj[3]);
				map.put("rent", obj[2]);
				map.put("advanceRent", obj[4]);
				map.put("rentDeposit", obj[5]);
				map.put("decorationDeposit", obj[6]);
				formatList.add(map);
			}
		}
		return formatList;
	}
}
