package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAnalysisChargesDAO;
import com.zc13.bkmis.form.AnalysisChargesForm;
import com.zc13.bkmis.mapping.AnalysisCost;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：3:17:06 PM
 */
public class AnalysisChargesDAOImpl extends BasicDAOImpl implements IAnalysisChargesDAO {

	//显示统计记录列表
	public List showChaAnalysis(AnalysisChargesForm acForm) throws DataAccessException {
		
		//查询条件
		String createDate = GlobalMethod.NullToSpace(acForm.getCreateDate());
		String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(acForm.getLpId()));
		String hql = "from AnalysisCost where 1=1";
		
		if(!"".equals(createDate) && null != createDate){
			hql += " and createDate = :createDate";
		}
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and beginDate = :beginDate";
		}
		if(!"".equals(lpId) && null != lpId){
			hql += " and lpId = " + lpId;
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and endDate = :endDate";
		}
			hql += " order by id desc";
		Query query = this.getSession().createQuery(hql);
		
		if(!"".equals(createDate) && null != createDate){
			query.setParameter("createDate", createDate);
		}
		if(!"".equals(beginDate) && null != beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", endDate);
		}
		
		List analysisList = new ArrayList();
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(acForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(acForm.getCurrentpage(),"1"))-1);
		analysisList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return analysisList;
	}

	//统计记录条数
	public int countQuery(AnalysisChargesForm acForm) throws DataAccessException {
		
		int count = 0;
		//查询条件
		String createDate = GlobalMethod.NullToSpace(acForm.getCreateDate());
		String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
		
		String hql = "select count(ac) from AnalysisCost as ac where 1=1";
		
		if(!"".equals(createDate) && null != createDate){
			hql += " and ac.createDate = :createDate";
		}
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and ac.beginDate = :beginDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and ac.endDate = :endDate";
		}
		
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(createDate) && null != createDate){
			query.setParameter("createDate", createDate);
		}
		if(!"".equals(beginDate) && null != beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", endDate);
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

	//执行添加统计记录
	public List addChaAnalysis(AnalysisChargesForm acForm) throws DataAccessException {
		
		//查询的限制条件
		//int lpId = acForm.getLpId();
		String beginDate = acForm.getBeginDate();
		String endDate = acForm.getEndDate();
		
		//String hql = "select sum(bill.actuallyPaid) from CBill as bill,CCoststandard as cs,CAccounttemplate as cac where bill.CCoststandard.id = cs.id and cs.accountTemplateId = cac.id and cac.lpid = :lpId";
		String hql = "select sum(bill.actuallyPaid) from CBill as bill where 1=1 ";
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and bill.collectionDate >= :beginDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and bill.collectionDate <= :endDate";
		}
		Query query = this.getSession().createQuery(hql);
		//query.setParameter("lpId", lpId);
		if(!"".equals(beginDate) && null != beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", endDate);
		}
		List countList = query.list();
		return countList;
	}

	//查询楼盘信息
	public List selectLp() throws DataAccessException {
		
		String hql = "from ELp";
		Query query = this.getSession().createQuery(hql);
		List epList = query.list();
		return epList;
	}

	//查询统计记录信息
	public List selectAnalysis(int id) throws DataAccessException {
		
		String hql = "from AnalysisCost where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List list = new ArrayList();
		list = query.list();
		return list;
	}

	//查询生成图所需条件
	public List createGraphicData(AnalysisChargesForm acForm, String type) throws DataAccessException {
		
		//获取所需的条件
		int lpid = acForm.getLpId();
		String beginDate = acForm.getBeginDate();
		String endDate = acForm.getEndDate();
		List list = new ArrayList();
		//以收费项来分析
		if(type.equals("item_type")){
			String hql = "select ci.itemName,sum(bill.actuallyPaid) from CBill as bill,CItems as ci" +
					" where bill.itemId = ci.id" +
					" and bill.collectionDate >= :beginDate and bill.collectionDate <= :endDate group by ci.itemName";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("beginDate", beginDate);
			query.setParameter("endDate", endDate);
			list = query.list();
		}
		//以客户来分析
		if(type.equals("customer")){
			String hql = "select bill.compactClient.name,sum(bill.actuallyPaid) from CBill as bill " +
					" where bill.collectionDate >= :beginDate and bill.collectionDate <= :endDate group by bill.compactClient.name order by sum(bill.actuallyPaid) desc";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("beginDate", beginDate);
			query.setParameter("endDate", endDate);
			
			list = query.setFirstResult(0).setMaxResults(10).list();
		}
		return list;
	}

	//按id查询要删除的统计记录信息
	public List<AnalysisCost> selectDetailAnalys(Integer[] idArray) throws DataAccessException {
		
		String hql = "from AnalysisCost where 1=1 ";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List costList = this.getHibernateTemplate().find(hql, idArray);
		return costList;
	}

}
