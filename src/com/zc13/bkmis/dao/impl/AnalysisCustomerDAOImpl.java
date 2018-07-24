package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAnalysisCustomerDAO;
import com.zc13.bkmis.form.AnalysisCustomerForm;
import com.zc13.bkmis.mapping.AnalysisCustomer;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 28, 2010
 * Time：3:14:11 PM
 */
public class AnalysisCustomerDAOImpl extends BasicDAOImpl implements IAnalysisCustomerDAO {

	//显示查询客户信息分析列表
	public List showAnalysis(AnalysisCustomerForm acForm) throws DataAccessException {
		
		//查询条件
		String createDate = GlobalMethod.NullToSpace(acForm.getCreateDate());
		String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
		
		String hql = "from AnalysisCustomer where 1=1";
		if(!"".equals(createDate) && null != createDate){
			hql += " and createDate = :createDate";
		}
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and beginDate >= :beginDate ";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and endDate <= :endDate"; 
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

	//查询记录的条数
	public int queryCountAnalysisCus(AnalysisCustomerForm acForm)
			throws DataAccessException {
		
			int count = 0;
			//查询条件
			String createDate = GlobalMethod.NullToSpace(acForm.getCreateDate());
			String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
			String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
			
			String hql = "select count(ac) from AnalysisCustomer as ac where 1=1";
			if(!"".equals(createDate) && null != createDate){
				hql += " and createDate = :createDate";
			}
			if(!"".equals(beginDate) && null != beginDate){
				hql += " and beginDate >= :beginDate ";
			}
			if(!"".equals(endDate) && null != endDate){
				hql += " and endDate <= :endDate"; 
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

	//执行添加
	public List addAnalysis(AnalysisCustomerForm acForm) throws DataAccessException {
		
			//计算条件
			int comeCount = 0;
			int goCount = 0;
			List countList = new ArrayList();
			int lpId = acForm.getLpId();
			String strlpId = GlobalMethod.NullToSpace(String.valueOf(acForm.getLpId()));
			String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
			String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
			
			//查询入住数量
			String inHql = "select count(comeGo) from AnalysisClientComeGo as comeGo where lp_id = :lpId";
			if(!"".equals(beginDate) && null != beginDate){
				inHql += " and comeGo.comeDate >= :beginDate";
			}
			if(!"".equals(endDate) && null != endDate){
				inHql += " and comeGo.comeDate <= :endDate";
			}
			Query inQuery = this.getSession().createQuery(inHql);
			inQuery.setParameter("lpId", lpId);
			if(!"".equals(beginDate) && null != beginDate){
				inQuery.setParameter("beginDate", beginDate);
			}
			if(!"".equals(endDate) && null != endDate){
				inQuery.setParameter("endDate", endDate);
			}
			List inList = inQuery.list();
			if(null != inList && inList.size()>0){
				comeCount = (Integer)inList.get(0);
			}
			countList.add(comeCount);
			//查询迁出数量
			String goHql = "select count(comeGo) from AnalysisClientComeGo as comeGo where lp_id = :lpId";
			if(!"".equals(beginDate) && null != beginDate){
				goHql += " and comeGo.goDate >= :startDate";
			}
			if(!"".equals(endDate) && null != endDate){
				goHql += " and comeGo.goDate <= :edDate";
			}
			Query goQuery = this.getSession().createQuery(goHql);
			goQuery.setParameter("lpId", lpId);
			if(!"".equals(beginDate) && null != beginDate){
				goQuery.setParameter("startDate", beginDate);
			}
			if(!"".equals(endDate) && null != endDate){
				goQuery.setParameter("edDate", endDate);
			}
			List outList = goQuery.list();
			if(null != outList && outList.size()>0){
				goCount = (Integer)outList.get(0);
			}
			countList.add(goCount);
			return countList;
	}

	//查询楼盘
	public List selectLp() throws DataAccessException {
		
		String hql = "from ELp";
		Query query = this.getSession().createQuery(hql);
		List lpList = new ArrayList();
		lpList = query.list();
		return lpList;
	}

	//按id查询统计分析信息
	public List selectAnalysisById(int id) throws DataAccessException {
		
		String hql = "from AnalysisCustomer where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List analysisList = new ArrayList();
		analysisList = query.list();
		return analysisList;
	}

	//按条件查询详细的数据
	public Map selectDetailAmount(AnalysisCustomerForm acForm)
			throws DataAccessException {
		
		String beginDate = acForm.getBeginDate();
		String endDate = acForm.getEndDate();
		int id = acForm.getLpId();
		String Type = acForm.getType();
		
		List inList = new ArrayList();
		List outList = new ArrayList();
//		String inHql = "select "+Type+",count(cc) from CompactClient as cc inner join \n" +
//				"(select acc from AnalysisClientComeGo as acc where acc.lp_id = :lpId and acc.comeDate >= :beginDate and acc.comeDate <= :endDate) as a \n" +
//				"on a.client_id = cc .id group by "+Type;
//		Query query = this.getSession().createQuery(inHql);
//		query.setParameter("lpId", id);
//		query.setParameter("beginDate", beginDate);
//		query.setParameter("endDate", endDate);
		String inSql = "select cc."+Type+",count(*) from compact_client as cc inner join"
						+"("
						+"select * from analysis_client_come_go where lp_id = '"+id+"' and come_date >= '"+beginDate+"' and come_date <= '"+endDate+"'"
						+")as a"
						+" on a.client_id = cc .id group by cc."+Type+"";
		Query inquery = this.getSession().createSQLQuery(inSql);
		inList = inquery.list();
		
		String outSql = "select cc."+Type+",count(*) from compact_client as cc inner join"
						+"("
						+"select * from analysis_client_come_go where lp_id = '"+id+"' and go_date >= '"+beginDate+"' and go_date <= '"+endDate+"'"
						+")as a"
						+" on a.client_id = cc .id group by cc."+Type+"";
		Query outQuery = this.getSession().createSQLQuery(outSql);
		outList = outQuery.list();
		Map map = new HashMap();
		map.put("inList", inList);
		map.put("outList", outList);
		return map;
	}

	//查看详细信息
	public List selectDetail(AnalysisCustomerForm acForm)
			throws DataAccessException {
		
		int lpId = acForm.getLpId();
		String beginDate = GlobalMethod.NullToSpace(acForm.getBeginDate());
		String endDate = GlobalMethod.NullToSpace(acForm.getEndDate());
		//按不同条件查询
		String paramValue = acForm.getParamType();
		
		String hql = "select new Map(cc.name as customerName,er.roomFullName as roomFullName,er.useArea as userArea,cc.clientType as clientType,erc.beginDate as beginDate,erc.endDate as endDate) from ERoomClient as erc,CompactClient as cc,EBuilds as eb,ERooms as er where eb.ELp.lpId = :lpId and eb.buildId = er.EBuilds.buildId and er.roomId = erc.roomId and erc.clientId = cc.id";
		
		if(!"".equals(beginDate) && null != beginDate){
			hql += " and ((erc.beginDate >= :beginDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and erc.beginDate <= :endDate)";
		}
		if("all".equals(paramValue) || "null".equals(paramValue) || null == paramValue){
			if(!"".equals(beginDate) && null != beginDate){
				hql += " or (erc.beginDate <= :beginDate";
			}
			if(!"".equals(endDate) && null != endDate){
				hql += " and erc.endDate >= :beginDate))";
			}
		}else{
			hql += ")";
		}
			hql += " order by erc.beginDate desc";
		Query query  = this.getSession().createQuery(hql);
		query.setParameter("lpId", lpId);
		
		if(!"".equals(beginDate) && null != beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", endDate);
		}
		List detailList = new ArrayList();
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(acForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(acForm.getCurrentpage(),"1"))-1);
		detailList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return detailList;
	}

	//按id查询要删除统计记录的详细信息
	public List<AnalysisCustomer> selectDetailAnalysis(Integer[] idArray) throws DataAccessException {
		
		String hql = "from AnalysisCustomer where 1=1 ";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List cusList = this.getHibernateTemplate().find(hql, idArray);
		return cusList;
	}
}
