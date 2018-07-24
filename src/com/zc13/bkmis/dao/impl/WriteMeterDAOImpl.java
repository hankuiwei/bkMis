package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IWriteMeterDAO;
import com.zc13.bkmis.form.MeterInputForm;
import com.zc13.bkmis.mapping.AllMeterType;
import com.zc13.bkmis.mapping.EMeterExcerption;
import com.zc13.bkmis.mapping.PublicTotalReadMeter;
import com.zc13.bkmis.mapping.TotalReadMeter;
import com.zc13.bkmis.mapping.UserMeterReadingId;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/**
 * 表具抄录
 * 
 * @author 王正伟 Date：Dec 8, 2010 Time：3:05:31 PM
 */
public class WriteMeterDAOImpl extends BasicDAOImpl implements IWriteMeterDAO {

	@Override
	public List<AllMeterType> getInfoForTree(MeterInputForm meterInputForm) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from AllMeterType where id.lpId=").append(meterInputForm.getLpId());
		Query query = this.getSession().createQuery(hql.toString());
		List<AllMeterType> list = query.list();
		return list;
	}

	@Override
	public List<TotalReadMeter> countUserReadMeterInfo(
			MeterInputForm meterInputForm) throws DataAccessException {
		List<TotalReadMeter> list = new ArrayList<TotalReadMeter>();
		if (meterInputForm != null) {
			StringBuffer sql = new StringBuffer(
					"select substring(year_month,1,4) as years,substring(year_month,6,2) as months,sum(this_record-last_record) as total from e_meter_excerption e1 where e1.meter_id in(select e2.id from e_meter e2 where isnull(e2.room_id,0)<>0 ");
			sql.append(" and e2.lp_id=");
			sql.append(meterInputForm.getLpId());
			sql.append(" and e2.type='");
			sql.append(meterInputForm.getMeterTypeCode());
			sql.append("'");
			sql.append(")");
			if (!GlobalMethod.NullToSpace(meterInputForm.getYears()).equals("")) {
				sql.append(" and substring(e1.year_month,1,4)='");
				sql.append(meterInputForm.getYears());
				sql.append("'");
			}
			sql.append(" group by year_month order by year_month asc");
			// query = this.getSession().createSQLQuery(sql.toString());
			List list2 = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql.toString()).list();
			if (list2 != null) {
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = (Object[]) list2.get(i);
					// 将得到的结果集封装成TotalReadMeter对象
					TotalReadMeter po = new TotalReadMeter();
					po.setYears(o[0].toString());
					po.setMonths(o[1].toString());
					po.setTotal(o[2].toString());
					list.add(po);
				}
			}
		}
		return list;
	}

	@Override
	public List getUserReadMeter(MeterInputForm meterInputForm)
			throws DataAccessException {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select eme.id,t2.lp_id,t2.build_name,t2.name,t2.room_code,t2.meter_id,t2.code,t2.meterType,eme.begin_date,eme.end_date,replace(isnull(eme.last_record,-1.0),-1.0,isnull(t2.old_record,0.0)) as last_record,isnull(eme.this_record,0.0) as this_record,substring(eme.year_month,1,4),substring(eme.year_month,6,2) from (");
		sql
				.append("select b.lp_id,b.build_name,cc.name,r.room_code,m.id as meter_id,m.code,m.type as meterType,(select isnull(this_record,0.0) from e_meter_excerption eme22 where eme22.year_month in (select max(eme33.year_month) from e_meter_excerption eme33 where eme33.meter_id=m.id and eme33.year_month<'"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"') and eme22.meter_id=m.id and eme22.year_month<'"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"') as old_record from ");
		sql.append(" e_builds b,compact_client cc,e_rooms r,e_meter m,e_room_client erc,compact c ");
		sql
				.append(" where  erc.pact_id=c.id and c.status='"+Contants.THROUGHAPPROVAL+"' and substring(c.begin_date,1,7)<='"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"' and substring(c.end_date,1,7)>='"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"' and  erc.client_id=cc.id and erc.room_id=r.room_id and b.lp_id="+meterInputForm.getLpId()+" and r.build_id=b.build_id and m.build_id=b.build_id and m.room_id=r.room_id and m.type='"+meterInputForm.getMeterTypeCode()+"' ");
		if(!GlobalMethod.NullToSpace(meterInputForm.getRoomCode()).equals("")){
			sql.append(" and r.room_code like '%");
			sql.append(meterInputForm.getRoomCode());
			sql.append("%' ");
		}
		if(!GlobalMethod.NullToSpace(meterInputForm.getMeterCode()).equals("")){
			sql.append(" and m.code like '%");
			sql.append(meterInputForm.getMeterCode());
			sql.append("%' ");
		}
		sql.append(") t2  ");
		sql.append("left join ");
		sql
				.append(" e_meter_excerption eme on eme.meter_id=t2.meter_id and eme.year_month='"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"'");
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		
		List list2 = query.list();
		if(list2!=null){
			for(int i = 0;i<list2.size();i++){
				Object[] obj = (Object[])list2.get(i);
				//将结果集封装成UserMeterReadingId对象
				UserMeterReadingId umr = new UserMeterReadingId(Integer.parseInt(GlobalMethod.ObjToParam(obj[0], "0")),Integer.parseInt(GlobalMethod.ObjToParam(obj[1], "0")),GlobalMethod.ObjToStr(obj[2]),GlobalMethod.ObjToStr(obj[3]),GlobalMethod.ObjToStr(obj[4]),Integer.parseInt(GlobalMethod.ObjToParam(obj[5], "0")),GlobalMethod.ObjToStr(obj[6]),GlobalMethod.ObjToStr(obj[7]),GlobalMethod.ObjToStr(obj[8]),GlobalMethod.ObjToStr(obj[9]),GlobalMethod.ObjToParam(obj[10],"0"),GlobalMethod.ObjToParam(obj[11],"0"),GlobalMethod.ObjToStr(obj[12]),GlobalMethod.ObjToStr(obj[13]));
				list.add(umr);
			}
		}
		//hql语句的写法，尚未完成
		StringBuffer hql = new StringBuffer("from CompactClient cc,ERooms r,EMeter m ");
		hql.append(" left join m.EMeterExcerptions eme where  m.EMeterExcerptions.months='10' and m.EMeterExcerptions.years='2010'  and  r.EBuilds.ELp.lpId=85 and m.buildId=r.EBuilds.buildId and m.roomId=r.roomId and m.type='electric' and cc.id in (select c.clientId from Compact c where c.id in(select cr.pactId from CompactRoom cr where cr.roomId=r.roomId))");
		//List list3 = this.getSession().createQuery(hql.toString()).list();
		
		return list;
	}

	@Override
	public Integer saveMeterInfo(EMeterExcerption m) throws DataAccessException {
		Integer id = (Integer)getHibernateTemplate().save(m);
		return id;
	}

	@Override
	public void updateMeterInfo(EMeterExcerption m) throws DataAccessException {
		super.updateObject(m);
		
	}

	@Override
	public List countPublicReadMeterInfo(MeterInputForm meterInputForm)  throws DataAccessException {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select temp1.*,lp.LP_name,b.build_name from (");
		sql.append("select eme.meter_id,eme.code,eme.type,sum(eme.this_record-eme.last_record) as totals,substring(eme.year_month,1,4) as years,m.lp_id,m.build_id from e_meter_excerption eme,e_meter m where isnull(m.room_id,0)=0 and eme.meter_id=m.id ");
		sql.append(" and m.lp_id=");
		sql.append(meterInputForm.getLpId());
		sql.append(" and substring(eme.year_month,1,4)='");
		sql.append(meterInputForm.getYears());
		sql.append("' and eme.type='");
		sql.append(meterInputForm.getMeterTypeCode());
		sql.append("'");
		sql.append(" group by eme.meter_id,eme.code,eme.type,substring(eme.year_month,1,4),m.lp_id,m.build_id ");
		sql.append(") temp1 ");
		sql.append(" left join ");
		sql.append(" e_LP lp on lp.LP_id=temp1.lp_id ");
		sql.append(" left join ");
		sql.append(" e_builds b on b.build_id=temp1.build_id ");
		List list2 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).list();
		if (list2 != null) {
			for (int i = 0; i < list2.size(); i++) {
				Object[] o = (Object[]) list2.get(i);
				// 将得到的结果集封装成PublicTotalReadMeter对象
				PublicTotalReadMeter po = new PublicTotalReadMeter();
				po.setMeterId(GlobalMethod.ObjToStr(o[0]).toString());
				po.setCode(GlobalMethod.ObjToStr(o[1]).toString());
				po.setType(GlobalMethod.ObjToStr(o[2]).toString());
				po.setTotals(GlobalMethod.ObjToStr(o[3]).toString());
				po.setYears(GlobalMethod.ObjToStr(o[4]).toString());
				po.setLpName(GlobalMethod.ObjToStr(o[7]).toString());
				po.setBuildName(GlobalMethod.ObjToStr(o[8]).toString());
				list.add(po);
			}
		}
		return list;
	}

	@Override
	public List<EMeterExcerption> getPublicReadMeterByYearAndId(MeterInputForm meterInputForm)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from EMeterExcerption where EMeter.id=");
		hql.append(meterInputForm.getMeterId());
		hql.append(" and substring(year_month,1,4)='");
		hql.append(meterInputForm.getYears());
		hql.append("'");
		if(!GlobalMethod.NullToSpace(meterInputForm.getMeterCode()).equals("")){
			hql.append(" and EMeter.code like'%");
			hql.append(meterInputForm.getMeterCode());
			hql.append("%' ");
		}
		hql.append(" order by substring(year_month,6,2) ");
		Query query = this.getSession().createQuery(hql.toString());
		List<EMeterExcerption> list = query.list();
		return list;
	}

	@Override
	public List getPublicReadMeter(MeterInputForm meterInputForm)
			throws DataAccessException {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select t.id,t.meter_id,t.code,t.meter_type,t.begin_date,t.end_date,replace(isnull(t.last_record,-1.0),-1.0,isnull(t.old_record,0.0)) as last_record,isnull(t.this_record,0.0) from (");
		sql.append("select eme.id,m.id as meter_id,m.code,m.type as meter_type,eme.begin_date,eme.end_date,eme.last_record,isnull(eme.this_record,0.0) as this_record,(select isnull(this_record,0.0) from e_meter_excerption eme22 where eme22.year_month in (select max(eme33.year_month) from e_meter_excerption eme33 where eme33.meter_id=m.id and eme33.year_month<'"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"') and eme22.meter_id=m.id and eme22.year_month<'"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"') as old_record from e_meter m ");
		sql.append(" left join ");
		sql.append(" e_meter_excerption eme ");
		sql.append("	on eme.meter_id=m.id and eme.year_month='"+meterInputForm.getYears()+"-"+meterInputForm.getMonths()+"' ");
		sql.append("	where isnull(m.room_id,0)=0 and m.type='"+meterInputForm.getMeterTypeCode()+"' and m.lp_id='"+meterInputForm.getLpId()+"' ");
		if(!GlobalMethod.NullToSpace(meterInputForm.getMeterCode()).equals("")){
			sql.append(" and m.code like'%");
			sql.append(meterInputForm.getMeterCode());
			sql.append("%' ");
		}
		sql.append(") t ");
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		
		List list2 = query.list();
		if(list2!=null){
			for(int i = 0;i<list2.size();i++){
				Object[] obj = (Object[])list2.get(i);
				//将结果集封装成UserMeterReadingId对象
				UserMeterReadingId umr = new UserMeterReadingId();
				umr.setId(Integer.parseInt(GlobalMethod.ObjToParam(obj[0], "0")));
				umr.setMeterId(Integer.parseInt(GlobalMethod.ObjToParam(obj[1], "0")));
				umr.setCode(GlobalMethod.ObjToStr(obj[2]));
				umr.setMeterType(GlobalMethod.ObjToStr(obj[3]));
				umr.setBeginDate(GlobalMethod.ObjToStr(obj[4]));
				umr.setEndDate(GlobalMethod.ObjToStr(obj[5]));
				umr.setLastRecord(GlobalMethod.ObjToStr(obj[6]));
				umr.setThisRecord(GlobalMethod.ObjToStr(obj[7]));
				list.add(umr);
			}
		}
		return list;
	}

}
