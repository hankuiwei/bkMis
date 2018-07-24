package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAnalysisEnergyDAO;
import com.zc13.bkmis.form.AnalysisEnergyForm;
import com.zc13.bkmis.mapping.AnalysisEnergy;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 31, 2010
 * Time：5:12:43 PM
 */
public class AnalysisEnergyDAOImpl extends BasicDAOImpl implements IAnalysisEnergyDAO {

	//显示查询统计记录
	public List showEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException {
		
		//查询条件
		String createDate = GlobalMethod.NullToSpace(aeForm.getCreateDate());
		String beginDate = GlobalMethod.NullToSpace(aeForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(aeForm.getEndDate());
		
		String hql = "from AnalysisEnergy where 1=1";
		if(!"".equals(createDate) && null != createDate){
			hql += " and createDate = :createDate";
		}
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and beginDate = :beginDate";
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
		
		List energyList = new ArrayList();
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(aeForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(aeForm.getCurrentpage(),"1"))-1);
		energyList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return energyList;
	}

	//查询统计记录的条数 
	public int queryCountEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException {
		
		int count = 0;
		//查询条件
		String createDate = GlobalMethod.NullToSpace(aeForm.getCreateDate());
		String beginDate = GlobalMethod.NullToSpace(aeForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(aeForm.getEndDate());
		
		String hql = "select count(ae) from AnalysisEnergy ae where 1=1";
		if(!"".equals(createDate) && null != createDate){
			hql += " and ae.createDate = :createDate";
		}
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and ae.beginDate = :beginDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and ae.endDate = :endDate";
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

	//添加统计记录信息
	public List addEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException {
		
		String beginDate = aeForm.getBeginDate();
		String endDate = aeForm.getEndDate();
		int lpid = aeForm.getLpId();
		
		String hql = "select eme.type,sum((eme.thisRecord - eme.lastRecord)) from EMeterExcerption as eme,EMeter as em where eme.EMeter.id = em.id and em.lpId = :lpId  and (em.buildId is null or em.buildId=0) and (em.roomId is null or em.roomId=0) ";
		if(!"".equals(beginDate) || null != beginDate){
			hql += " and eme.yearMonth >= :beginDate";
		}
		if(!"".equals(endDate) || null != endDate){
			hql += " and eme.yearMonth <= :endDate";
		}
		hql += " group by eme.type";
		
		Query query = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpid);
		if(!"".equals(beginDate) || null != beginDate){
			query.setParameter("beginDate",beginDate);
		}
		if(!"".equals(endDate) || null != endDate){
			query.setParameter("endDate",endDate);
		}
		List countList = new ArrayList();
		countList = query.list();
		return countList;
	}

	//查询楼盘信息
	public List selectLp() throws DataAccessException {
		
		String hql = "from ELp";
		Query query = this.getSession().createQuery(hql);
		List epList = query.list();
		return epList;
	}

	//查看生成图的统计记录信息
	public List graphicMessage(int id) throws DataAccessException {
		
		String hql = "from AnalysisEnergy where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id",id);
		List countMessageList = new ArrayList();
		countMessageList = query.list();
		return countMessageList;
	}
	//查询生成图所需数据
	public Map graphicData(String beginDate,String endDate,int lpid) throws DataAccessException {
		
		//查询电生成图的数据
		Map map = new HashMap();
		String electricHql = "select eme.yearMonth,sum((eme.thisRecord - eme.lastRecord)) from EMeterExcerption as eme,EMeter as em where eme.EMeter.id = em.id and em.lpId = :lpId and em.buildId is null and em.roomId is null " +
				     " and eme.type = :type and eme.yearMonth >= :beginDate and eme.yearMonth <= :endDate group by eme.yearMonth";
		Query electricQuery = this.getSession().createQuery(electricHql);
		electricQuery.setParameter("lpId", lpid);
		electricQuery.setParameter("beginDate",beginDate);
		electricQuery.setParameter("endDate",endDate);
		electricQuery.setParameter("type", "601");//electric换为601
		List electricList = new ArrayList();
		electricList = electricQuery.list();
		//查询水
		String waterHql = "select eme.yearMonth,sum((eme.thisRecord - eme.lastRecord)) from EMeterExcerption as eme,EMeter as em where eme.EMeter.id = em.id and em.lpId = :lpId and em.buildId is null and em.roomId is null " +
	     			" and eme.type = :type and eme.yearMonth >= :beginDate and eme.yearMonth <= :endDate group by eme.yearMonth";
		Query waterQuery = this.getSession().createQuery(waterHql);
		waterQuery.setParameter("lpId", lpid);
		waterQuery.setParameter("beginDate",beginDate);
		waterQuery.setParameter("endDate",endDate);
		waterQuery.setParameter("type", "602");//water换为602
		List waterList = new ArrayList();
		waterList = waterQuery.list();
		//查询气
		String gasHql = "select eme.yearMonth,sum((eme.thisRecord - eme.lastRecord)) from EMeterExcerption as eme,EMeter as em where eme.EMeter.id = em.id and em.lpId = :lpId and em.buildId is null and em.roomId is null " +
					" and eme.type = :type and eme.yearMonth >= :beginDate and eme.yearMonth <= :endDate group by eme.yearMonth";
		Query gasQuery = this.getSession().createQuery(waterHql);
		gasQuery.setParameter("lpId", lpid);
		gasQuery.setParameter("beginDate",beginDate);
		gasQuery.setParameter("endDate",endDate);
		gasQuery.setParameter("type", "603");//gas换为603
		List gasList = new ArrayList();
		gasList = gasQuery.list();
		map.put("electricList", electricList);
		map.put("waterList", waterList);
		map.put("gasList", gasList);
		return map;
	}

	////查询前三条记录用于首页的显示
	public List selectEngAnalysis() throws DataAccessException {
		
		String hql = "from AnalysisEnergy order by id desc";
		Query query = this.getSession().createQuery(hql);
		List list = query.setFirstResult(0).setMaxResults(3).list();
		return list;
	}

	//按id查询要删除记录的详细信息
	public List<AnalysisEnergy> selectDetailAnalysis(Integer[] idArray) throws DataAccessException {
		
		String hql = "from AnalysisEnergy where 1=1 ";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List<AnalysisEnergy> detailList = this.getHibernateTemplate().find(hql, idArray);
		return detailList;
	}
}
