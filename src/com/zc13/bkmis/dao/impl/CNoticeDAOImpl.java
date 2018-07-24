/**
 * ZHAOSG
 */
package com.zc13.bkmis.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICNoticeDAO;
import com.zc13.bkmis.form.CNoticeForm;
import com.zc13.bkmis.mapping.CNotice;
import com.zc13.util.Contants;
import com.zc13.util.ExtendUtil;
import com.zc13.util.PageBean;

/**
 * @author ZHAOSG
 * Date：Jan 3, 2011
 * Time：2:38:30 PM
 */
public class CNoticeDAOImpl extends BasicDAOImpl implements ICNoticeDAO {
	
	/**
	 * 删除通知单
	 */
	@Override
	public void deleteNotice(CNoticeForm noticeForm) throws DataAccessException {
		String noticeDate = noticeForm.getNotice().getNoticeDate();
		String noticeType = noticeForm.getNotice().getNoticeType();
		Integer[] clientId = noticeForm.getClientId();
		Integer[] itemId = noticeForm.getItemId();
		String deleteHql = "delete from CNotice n where n.clientId in ("+ toStr(clientId) + ") "
				+ "and n.itemId in (" + toStr(itemId)+ ")" ;
		/**2018年2月9日11:26:41 gd 
		 * 添加了判断... 效果未知 ...
		 * */
		if(!ExtendUtil.checkNull(noticeDate))
			deleteHql += " and n.noticeDate = '" + noticeDate + "'" ;
		if(!ExtendUtil.checkNull(noticeType))
			deleteHql += " and n.noticeType='" + noticeType + "'";
		Query delQuery = getSession().createQuery(deleteHql);
		delQuery.executeUpdate();
	}
	/**
	 * 添加通知单
	 * CNoticeDAOImpl.save
	 */
	public int save(CNoticeForm noticeForm) throws DataAccessException{
		String noticeDate = noticeForm.getNotice().getNoticeDate();
		String noticeType = noticeForm.getNotice().getNoticeType();
		Integer[] clientId = noticeForm.getClientId();
		Integer[] itemId = noticeForm.getItemId();
		String hql = "insert into CNotice (clientId,clientName,itemId,standardId,itemName,standardName,amount,billDate,closeDate,roomId,roomCode,noticeType,noticeDate,beginDate,endDate,lpId)"
				+ " select b.compactClient.id,b.compactClient.name,i.id,b.CCoststandard.id,i.itemName,i.itemName,b.billsExpenses,b.billDate,b.closeDate,b.ERooms.roomId," +
				"(select r.roomCode from ERooms r where r.roomId = b.ERooms.roomId),'"+ noticeType+ "','"+ noticeDate+ "',b.startDate,b.endDate,b.compactClient.lpId " 
				+ "from CBill b ,CItems i " 
				+"where b.status='0' " 
				+	"and b.itemId=i.id "
				+ " and b.compactClient.id in ("+ toStr(clientId)+ ") "
				+ "and b.itemId in (" + toStr(itemId) + ")";
		Query query = getSession().createQuery(hql);
		return query.executeUpdate();
	}
	/**
	 * 通知单查询
	 * CNoticeDAOImpl.getNotiveList
	 */
	public PageBean getNotiveList(CNoticeForm noticeForm)throws DataAccessException{
		PageBean pageBean = new PageBean(noticeForm.getPagesize());
		pageBean.setPagination(noticeForm.getPagination());
		String hql = "select n.noticeDate,n.clientId,n.clientName,sum(n.amount),n.noticeType from CNotice n where 1=1";
				
		String countHql = "select count(1) from (select n.notice_date,n.client_id from c_notice n where 1=1";
				
		if (!ExtendUtil.checkNull(noticeForm.getClientName())) {
			hql += " and n.clientName like :clientName";
			countHql += " and n.client_name like :clientName";
		}
		if (!ExtendUtil.checkNull(noticeForm.getNoticeType())) {
			hql += " and n.noticeType = :noticeType";
			countHql += " and n.notice_type = :noticeType";
		}
		if (!ExtendUtil.checkNull(noticeForm.getStart())) {
			hql += " and n.noticeDate >= :begin";
			countHql += "  and n.notice_date >= :begin";
		}
		if (!ExtendUtil.checkNull(noticeForm.getEnd())) {
			hql += " and n.noticeDate <= :end";
			countHql += " and n.notice_date <= :end";
		}
		if(noticeForm.getLpId()!=null&&noticeForm.getLpId()!=0){
			hql += " and n.lpId ="+noticeForm.getLpId();
			countHql += " and n.lp_id="+noticeForm.getLpId();
		}
		hql += " group by n.noticeDate,n.clientId,n.clientName,n.noticeType order by n.noticeDate desc";
		countHql += " group by n.notice_date,n.client_id,n.notice_type) c";
		Query query = getSession().createQuery(hql);
		Query countQuery = getSession().createSQLQuery(countHql);
		if (!ExtendUtil.checkNull(noticeForm.getClientName())) {
			query.setParameter("clientName", "%"+noticeForm.getClientName()+"%");
			countQuery.setParameter("clientName", "%"+noticeForm.getClientName()+"%");
		}
		if (!ExtendUtil.checkNull(noticeForm.getNoticeType())) {
			query.setParameter("noticeType", noticeForm.getNoticeType());
			countQuery.setParameter("noticeType", noticeForm.getNoticeType());
		}
		if (!ExtendUtil.checkNull(noticeForm.getStart())) {
			query.setParameter("begin", noticeForm.getStart());
			countQuery.setParameter("begin", noticeForm.getStart());
		}
		if (!ExtendUtil.checkNull(noticeForm.getEnd())) {
			query.setParameter("end", noticeForm.getEnd());
			countQuery.setParameter("end", noticeForm.getEnd());
		}
		Integer tatol = (Integer) countQuery.list().get(0);
		pageBean.setTatol(tatol);
		List list = query.setFirstResult(pageBean.getStartPage()).setMaxResults(noticeForm.getPagesize()).list();
		pageBean.setList(list);
		return pageBean;
	}
	public List getClientList(CNoticeForm noticeForm)throws DataAccessException{
		String hql = "select distinct(cc) from CompactClient cc,ERoomClient rc,ERooms r,Compact c where cc.id = rc.clientId and r.roomId = rc.roomId and rc.pactId = c.id and c.status='通过审批' ";
		if (!ExtendUtil.checkNull(noticeForm.getName())) {
			hql += " and cc.name like :name";
		}
//		if (!ExtendUtil.checkNull(noticeForm.getBuildId())) {
//			hql += " and r.EBuilds.buildId = :buildId";
//		}
		if (!ExtendUtil.checkNull(noticeForm.getLpId())) {
			hql += " and r.EBuilds.ELp.lpId = :lpId";
		}
		Query query = getSession().createQuery(hql);
		if (!ExtendUtil.checkNull(noticeForm.getName())) {
			query.setParameter("name", "%"+noticeForm.getName()+"%");
		}
//		if (!ExtendUtil.checkNull(noticeForm.getBuildId())) {
//			query.setParameter("buildId", noticeForm.getBuildId());
//		}
		if (!ExtendUtil.checkNull(noticeForm.getLpId())) {
			query.setParameter("lpId", noticeForm.getLpId());
		}
		List list = query.list();
		return list;
	}
	public List getStandardList(CNoticeForm noticeForm)throws DataAccessException{
		String hql = "select s,t from CCoststandard s,CCosttype t,CAccounttemplate a where s.costTypeId = t.id and s.accountTemplateId = a.id";
		if (!ExtendUtil.checkNull(noticeForm.getLpId())) {
			hql += " and a.lpid = :lpId";
		}
		Query query = getSession().createQuery(hql);
		if (!ExtendUtil.checkNull(noticeForm.getLpId())) {
			query.setParameter("lpId", noticeForm.getLpId());
		}
		List list = query.list();
		return list;	
	}
	/***添加了 判断... 效果及 影响 待测试.	 */
	public List<CNotice> getNotice(CNoticeForm noticeForm)throws DataAccessException{
		String hql = "from CNotice n where n.clientId = :id ";
		String noticeDate = noticeForm.getNoticeDate();
		String noticeType = noticeForm.getNoticeType();
		if(!ExtendUtil.checkNull(noticeDate))
			hql += "and n.noticeDate = :noticeDate ";
		if(!ExtendUtil.checkNull(noticeType))
			hql += "and n.noticeType=:noticeType ";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("id", noticeForm.getId());
		if(!ExtendUtil.checkNull(noticeDate))
			query.setParameter("noticeDate", noticeDate);
		if(!ExtendUtil.checkNull(noticeType))
			query.setParameter("noticeType", noticeType);
		
		List<CNotice> list = query.list();
		return list;
	}
	public void delete(CNotice notice) throws DataAccessException{
		String hql = "delete from CNotice n where n.clientId = :clientId and n.noticeDate = :noticeDate";
		Query query = getSession().createQuery(hql);
		query.setParameter("clientId", notice.getClientId());
		query.setParameter("noticeDate", notice.getNoticeDate());
		query.executeUpdate();
	}
	private String toStr(Integer[] id){
		String str = "";
		if (id!=null&&id.length>0) {
			for (int i = 0; i < id.length; i++) {
				str += "','"+id[i];
			}
		}
		if (!"".equals(str)) {
			str = str.substring(2)+"'";
		}
		return str;
	}
	/**
	 * 打印全部
	 * CNoticeDAOImpl.getAllNoticePrint
	 */
	public List<CNotice> getAllNoticePrint(CNoticeForm noticeForm)throws DataAccessException {
		String hql = "select n from CNotice n where 1=1";
		if (!ExtendUtil.checkNull(noticeForm.getClientName())) {
			hql += " and n.clientName like :clientName";
		}
		if (!ExtendUtil.checkNull(noticeForm.getNoticeType())) {
			hql += " and n.noticeType = :noticeType";
		}
		if (!ExtendUtil.checkNull(noticeForm.getStart())) {
			hql += " and n.noticeDate >= :begin";
		}
		if (!ExtendUtil.checkNull(noticeForm.getEnd())) {
			hql += " and n.noticeDate <= :end";
		}
		if(noticeForm.getLpId()!=null&&noticeForm.getLpId()!=0){
			hql += " and n.lpId = "+noticeForm.getLpId();
		}
		hql += " order by n.clientId,n.noticeDate desc";
		Query query = getSession().createQuery(hql);
		if (!ExtendUtil.checkNull(noticeForm.getClientName())) {
			query.setParameter("clientName", "%"+noticeForm.getClientName()+"%");
		}
		if (!ExtendUtil.checkNull(noticeForm.getNoticeType())) {
			query.setParameter("noticeType", noticeForm.getNoticeType());
		}
		if (!ExtendUtil.checkNull(noticeForm.getStart())) {
			query.setParameter("begin", noticeForm.getStart());
		}
		if (!ExtendUtil.checkNull(noticeForm.getEnd())) {
			query.setParameter("end", noticeForm.getEnd());
		} 
		List list = query.list();
		return list;
	}
	public List<CNotice> getNoticePrint(CNoticeForm noticeForm)throws DataAccessException {
		String[] checkbox = noticeForm.getCheckbox();
		String where = "";
		if (checkbox!=null) {
			for (int i = 0; i < checkbox.length; i++) {
				String[] str = checkbox[i].split(",");
				String clientId = str[0];
				String noticeDate = str[1];
				String noticeType = str[2];
				where += " or (n.clientId='"+clientId+"' and n.noticeDate = '"+noticeDate+"' and n.noticeType='"+noticeType+"')";
			}
		}
		if (!ExtendUtil.checkNull(where)) {
			where = where.substring(4);
		}
		String hql = "select n from CNotice n where 1=1 and ("+where+")";
		hql += " order by n.clientId,n.noticeDate desc";
		Query query = getSession().createQuery(hql);
		List<CNotice> list = query.list();
		return list;
	}
	public String getRoomCodeByClientId(int id) throws DataAccessException{
		String roomCode = "";
		String hql ="select roomCodes from Compact where clientId=:clientId and isDestine in('"+Contants.NORMARL+"','"+Contants.DESTINES+"')";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("clientId", id);
		List list =query.list();
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			roomCode += it.next();
		}
		return "".equals(roomCode) ? "" : roomCode.substring(0, roomCode.length()-1);
	}
}
