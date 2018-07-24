package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ITrackRecordDAO;
import com.zc13.bkmis.form.TrackRecordForm;
import com.zc13.bkmis.mapping.TrackRecord;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;

/**
 * 客户跟踪记录
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:29:57
 */
public class TrackRecordDAOImpl extends BasicDAOImpl implements ITrackRecordDAO {

	@Override
	public List<TrackRecord> getList(TrackRecordForm trackRecordForm) {
		
		StringBuffer hql = new StringBuffer("from TrackRecord where 1=1");
		if(trackRecordForm!=null){
			if(!"".equals(GlobalMethod.ObjToStr(trackRecordForm.getCustomerName()))){
				hql.append(" and customerName like '%");
				hql.append(GlobalMethod.ObjToStr(trackRecordForm.getCustomerName()));
				hql.append("%'");
			}
			if(!"".equals(GlobalMethod.ObjToStr(trackRecordForm.getStatus()))){
				hql.append(" and status='");
				hql.append(GlobalMethod.ObjToStr(trackRecordForm.getStatus()));
				hql.append("'");
			}
		}
		hql.append(" order by id desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(trackRecordForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(trackRecordForm.getCurrentpage(),"1")) - 1);
		List<TrackRecord> list = (List<TrackRecord>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	@Override
	public int queryCounttotal(TrackRecordForm trackRecordForm) {
		
		int count = 0;
		String hql = "select count(f) from TrackRecord as f ";
		
		try{
			Query query = this.getSession().createQuery(hql);
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<SysCode> getSysCode(String codeType,Integer lpId) throws DataAccessException {

		String hql = "from SysCode where codeType='"+codeType+"'";
		if(lpId!=null && lpId != 0){
			hql+=" and lpId = "+lpId;
		}
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}
}
