/**
 * Administrator
 */
package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICChoiceDAO;
import com.zc13.bkmis.form.CChoiceForm;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.ExtendUtil;

/**
 * 收费选择
 * 
 * @author zhaosg Date：Dec 7, 2010 Time：2:27:27 PM
 */
public class CChoiceDAOImpl extends BasicDAOImpl implements ICChoiceDAO {
	/**
	 * 房间--收费标准
	 */
	public List getList(Integer standardId, Integer lpId, Integer buildId,
			String floor,String clientName) throws DataAccessException {
		ELp elp = new ELp();
		elp.setLpId(lpId);
		EBuilds ebuilds = new EBuilds();
		ebuilds.setBuildId(buildId);
		CCoststandard standard = new CCoststandard();
		standard.setId(standardId);
		StringBuffer hql = new StringBuffer();
		String today = DateUtil.getNowDate("yyyy-MM-dd");
		//该处查询的是所有通过审批，而且合同状态不为过期的那些合同下的收费标准
		hql.append("select cc,").append(
				"(select crc from CompactRoomCoststandard crc").append(
				" where r.roomId=crc.ERooms.roomId and crc.compact.id=c.id ").append(
				" and crc.CCoststandard = :standard),r,rc,c ").append(
				"from ERooms r,ERoomClient rc,CompactClient cc,Compact c ").append(
				"where r.roomId=rc.roomId and rc.clientId = cc.id and rc.pactId = c.id and c.status='").append(Contants.THROUGHAPPROVAL).append("' and c.isDestine<>'").append(Contants.HASOVER).append("' ")
				.append("and rc.endDate>='"+today+"' ")
				.append(" and r.EBuilds.ELp = :elp ");
		if (buildId != null && buildId.intValue() != 0) {
			hql.append("and r.EBuilds= :ebuilds ");
		}
		if (floor != null && !"".equals(floor)) {
			hql.append("and r.floor = :floor ");
		}
		if (clientName != null && !"".equals(clientName)) {
			hql.append("and cc.name like :clientName ");
		}
		hql.append(" order by cc.code,c.code,r.roomCode");
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("standard", standard);
		query.setParameter("elp", elp);
		if (buildId != null && buildId.intValue() != 0) {
			query.setParameter("ebuilds", ebuilds);
		}
		if (floor != null && !"".equals(floor)) {
			query.setParameter("floor", floor);
		}
		if (clientName != null && !"".equals(clientName)) {
			query.setParameter("clientName", "%"+clientName+"%");
		}
		List list = query.list();
		return list;
	}

	/**
	 * 合同--收费标准
	 */
	public List getListPact(Integer standardId, Integer lpId, Integer buildId,
			String floor,String clientName) throws DataAccessException {
		ELp elp = new ELp();
		elp.setLpId(lpId);
		EBuilds ebuilds = new EBuilds();
		ebuilds.setBuildId(buildId);
		CCoststandard standard = new CCoststandard();
		standard.setId(standardId);
		StringBuffer hql = new StringBuffer();
		//该处查询的是所有通过审批，而且合同状态不为过期的那些合同下的收费标准
		String today = DateUtil.getNowDate("yyyy-MM-dd");
		hql
				.append("select distinct cc,")
				.append("(select crc from CompactRoomCoststandard crc")
				.append(
						" where cc.id=crc.compactClient.id and crc.ERooms.id is null and crc.compact.id=c.id ")
				.append(" and crc.CCoststandard = :standard ),c ")
				.append("from ERooms r,ERoomClient rc,CompactClient cc,Compact c ")
				.append("where r.roomId=rc.roomId and rc.clientId = cc.id and rc.pactId = c.id and c.status ='").append(Contants.THROUGHAPPROVAL).append("' and c.isDestine<>'").append(Contants.HASOVER).append("' ")
				.append("and rc.endDate>='"+today+"' ")
				.append(" and r.EBuilds.ELp = :elp ");
		if (buildId != null && buildId.intValue() != 0) {
			hql.append(" and r.EBuilds= :ebuilds");
		}
		if (floor != null && !"".equals(floor)) {
			hql.append(" and r.floor = :floor");
		}
		if (clientName != null && !"".equals(clientName)) {
			hql.append("and cc.name like :clientName ");
		}
		hql.append(" order by cc.code,c.code");
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("standard", standard);
		query.setParameter("elp", elp);
		if (buildId != null && buildId.intValue() != 0) {
			query.setParameter("ebuilds", ebuilds);
		}
		if (floor != null && !"".equals(floor)) {
			query.setParameter("floor", floor);
		}
		if (clientName != null && !"".equals(clientName)) {
			query.setParameter("clientName", "%"+clientName+"%");
		}
		List list = query.list();
		return list;
	}

	/**
	 * 收费标准-树
	 */
	public List getTreeList(CChoiceForm choiceForm) throws DataAccessException {
		String hql = "select s.id ,s.cost_type_id parentid,s.standard_name name,'收费标准' type,a.lp_id,s.bill_type from c_coststandard s,c_items i,c_accounttemplate a where s.item_id=i.id and s.account_template_id = a.id and s.display is null";
		if(choiceForm.getLpId()!=null&&choiceForm.getLpId()!=0){
			hql += " and s.lp_id="+choiceForm.getLpId();
		}
		hql += " union all select distinct(t.id) id,sa.account_template_id parentid,t.cost_type_name name,'费用类型' type,a.lp_id,'' bill_type from c_costtype t,c_coststandard sa,c_accounttemplate a where t.id=sa.cost_type_id and sa.account_template_id = a.id";
		if(choiceForm.getLpId()!=null&&choiceForm.getLpId()!=0){
			hql += " and a.lp_id="+choiceForm.getLpId();
		}
		hql += " union all select a.id,'1' as parentid,e.lp_name as name,'收费帐套' type,a.lp_id,'' bill_type  from c_accounttemplate a,e_lp e where a.lp_id=e.lp_id";
		if(choiceForm.getLpId()!=null&&choiceForm.getLpId()!=0){
			hql += " and a.lp_id="+choiceForm.getLpId();
		}
		Query query = this.getSession().createSQLQuery(hql);
		List list = query.list();
		return list;
	}

	/**
	 * 某一个收费标准
	 */
	public CCoststandard getCStandard(Integer standardId)
			throws DataAccessException {
		String hql = " from CCoststandard where id = :standardId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("standardId", standardId);
		List<CCoststandard> list = query.list();
		CCoststandard item = new CCoststandard();
		if (list != null && list.size() > 0) {
			item = (CCoststandard) list.get(0);
		}
		return item;
	}

	/**
	 * 楼幢
	 * 
	 * @param lpId
	 * @return
	 */
	public List<EBuilds> getBuilds(Integer lpId) throws DataAccessException {
		ELp elp = new ELp();
		elp.setLpId(lpId);
		String hql = "from EBuilds where ELp = :elp";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("elp", elp);
		List<EBuilds> list = query.list();
		return list;
	}
	/**
	 * 客户 CBillDAOImpl.getClientList
	 */
	public List getClientList(CChoiceForm choiceForm) throws DataAccessException {
	 
		String sql = "select distinct c.id,c.name,b.lp_id parent,'client' lb from compact_client c,e_room_client rc,e_rooms r,e_builds b,compact com ";
		sql += " where c.id=rc.client_id and rc.room_id=r.room_id and r.build_id=b.build_id and com.id = rc.pact_id and com.status='通过审批' ";
		if (!ExtendUtil.checkNull(choiceForm.getLpId())) {
			sql += " and b.lp_id = '"+choiceForm.getLpId()+"'";
		}
		
		sql += " union all select l.lp_id id,l.lp_name name,'1' parent,'lp' lb from e_lp l";
		if (!ExtendUtil.checkNull(choiceForm.getLpId())) {
			sql += " where l.lp_id = '"+choiceForm.getLpId()+"'";
		}
		Query query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
}
