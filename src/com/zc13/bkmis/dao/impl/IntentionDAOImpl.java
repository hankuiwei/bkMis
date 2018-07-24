package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IIntentionDAO;
import com.zc13.bkmis.form.IntentionForm;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.ERoomIntention;
import com.zc13.bkmis.mapping.IntentionRent;
import com.zc13.bkmis.mapping.IntentionRoomCoststandard;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * 意向书dao
 * @author wangzw
 * @Date May 11, 2011
 * @Time 10:49:25 AM
 */
public class IntentionDAOImpl extends BasicDAOImpl implements IIntentionDAO {

	//查询意向书列表
	@Override
	public void queryList(IntentionForm intentionForm,boolean isPage)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from CompactIntention where 1=1 ");
		String pagesize = "10";
		String currentpage = "1";
		if(intentionForm!=null){
			pagesize = intentionForm.getPagesize();
			currentpage = intentionForm.getCurrentpage();
			if(intentionForm.getId()!=null){
				hql.append(" and id in(").append(intentionForm.getId()).append(")");
			}
			if(intentionForm.getLpId()!=null && intentionForm.getLpId()!=0){
				hql.append(" and lpId = ").append(intentionForm.getLpId());
			}
			if(!GlobalMethod.NullToSpace(intentionForm.getC_clientName()).equals("")){
				hql.append(" and name like '%").append(intentionForm.getC_clientName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(intentionForm.getC_roomCode()).equals("")){
				hql.append(" and roomCodes like '%").append(intentionForm.getC_roomCode()).append("%'");
			}
			if(!"".equals(intentionForm.getCompactIntention().getIntentionCode())&&intentionForm.getCompactIntention().getIntentionCode()!=null){
				hql.append(" and intentionCode like '%"+intentionForm.getCompactIntention().getIntentionCode()+"%'");
			}
			if(!GlobalMethod.NullToSpace(intentionForm.getC_status()).equals("")){
				hql.append(" and status = '").append(intentionForm.getC_status()).append("'");
			}
			if(!GlobalMethod.NullToSpace(intentionForm.getC_isEarnest()).equals("")){
				hql.append(" and isEarnest = '").append(intentionForm.getC_isEarnest()).append("'");
			}
			if(!GlobalMethod.NullToSpace(intentionForm.getC_isConvertCompact()).equals("")){
				hql.append(" and isConvertCompact = '").append(intentionForm.getC_isConvertCompact()).append("'");
			}
			//如果是通过页面的代办任务“可以转为合同的意向书”跳转过来的，则需要查询出可以转为合同的意向书(条件为审核通过，已缴定金或需缴定金为0，还没有转为合同的)
			if(GlobalMethod.NullToSpace(intentionForm.getAwokeTaskFlag()).equals("eNotIn")){
				//审核通过的
				hql.append(" and status = '").append(Contants.THROUGHAPPROVAL).append("'");
				//已缴定金或需缴定金为0的
				hql.append(" and (isEarnest='1' or earnest=0.000)");
				//还未转为合同
				hql.append(" and isConvertCompact<>'1'");
				intentionForm.setC_status(Contants.THROUGHAPPROVAL);
				intentionForm.setC_isConvertCompact("0");
			}
		}
		hql.append(" order by code");
		List<CompactIntention> list = null;
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
			list = (List<CompactIntention>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			list = (List<CompactIntention>)this.getSession().createQuery(hql.toString()).list();
		}
		intentionForm.setIntentionList(list);
	}

	@Override
	public String getIntentionCode() throws DataAccessException {
		String hql = "select max(intentionCode) from CompactIntention";
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}

	@Override
	public List<IntentionRent> getRentList(Integer id) throws DataAccessException {
		String hql = "from IntentionRent where intention_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<IntentionRent>)query.list();
	}

	@Override
	public List<ERoomIntention> getRoomListByClientId(Integer clientId, Integer intentionId) throws DataAccessException {
		StringBuffer hql = new StringBuffer("select ec from ERoomIntention ec,ERooms er where ec.roomId=er.roomId ");
		if(clientId!=null&&clientId!=0){
			hql.append(" and ec.clientId=").append(clientId);
		}
		if(intentionId!=null&&intentionId!=0){
			hql.append(" and ec.intentionId=").append(intentionId);
		}
		hql.append(" order by er.roomCode ");
		Query query = this.getSession().createQuery(hql.toString());
		return (List<ERoomIntention>)query.list();
	}

	@Override
	public List<IntentionRoomCoststandard> getStandardListByIntentionId(Integer id) throws DataAccessException {
		String hql = "select t from IntentionRoomCoststandard t left outer join t.ERooms e where t.compactIntention.id="+id+" and t.display is Null order by e.roomCode";
		Query query = this.getSession().createQuery(hql);
		return (List<IntentionRoomCoststandard>)query.list();
	}

}
