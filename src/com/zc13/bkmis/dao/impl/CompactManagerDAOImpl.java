package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.form.CompactManagerForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

/**
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：17:16:13 PM
 */
public class CompactManagerDAOImpl extends BasicDAOImpl implements ICompactManagerDAO {

	//查询合同列表..获取需要校验的新合同列表也是调用的这个方法，当审核状态不为空时，就是说需要过滤审核状态的合同时，就说明是需要校验或者需要审批的合同
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getCompactList(CompactManagerForm form) throws DataAccessException {
		
		//像变更和续租的情况下，能得到的符合条件的合同列表肯定是正在正常入驻，没有被续租或者变更的合同（因为一旦续租或变更，就会产生一条新的合同记录），所以要在这里过滤
		StringBuffer hql = new StringBuffer("from Compact where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			if(Contants.NOTSUBMIT.equals(form.getStatus())){//当前查询的是需要校验的，或者校验完但是还没有被审批的合同（即待审批）
				hql.append(" and( status = '"+Contants.NOTSUBMIT+"' or  status = '"+Contants.ONAPPROVAL+"')");	
			}else{//否则当前查询的就是待审批的，这里就只有待审批的合同了
				hql.append(" and status = '"+Contants.ONAPPROVAL+"'");
			}
		}
		if(form.getType()!=null&&!"".equals(form.getType())){
			hql.append(" and type = '"+form.getType()+"'");	
		}
		if(form.getFlag()!=null&&!"".equals(form.getFlag())){
			hql.append(" and status = '"+Contants.THROUGHAPPROVAL+"'");	
		}
		if(form.getIsNotice()!=null&&!"".equals(form.getIsNotice())){
			hql.append(" and isNotice = '"+form.getIsNotice()+"'");	
		}
		if(form.getIsDestine()!=null&&!"".equals(form.getIsDestine())){
			hql.append(" and isDestine = '"+form.getIsDestine()+"'");	
		}
		if("relet".equals(form.getFlag()) || "change".equals(form.getFlag())){//当前查询符合变更、续租条件的合同，则查询条件是所有入驻状态isNotice为已入驻的合同
			hql.append(" and isNotice = '"+Contants.HAVEIN+"'");
		}
		if("quit".equals(form.getFlag())){//查询符合退租条件的合同，则查询条件是所有合同状态isDestine为常规的合同
			hql.append(" and isDestine = '"+Contants.NORMARL+"'");
			hql.append(" and isNotice <> '").append(Contants.HASAPPLYGO).append("'");
		}
		
		hql.append(" order by code");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	//得到合同总条数
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getCount(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from Compact where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		if(form.getType()!=null&&!"".equals(form.getType())){
			hql.append(" and type = '"+form.getType()+"'");	
		}
		if(form.getIsNotice()!=null&&!"".equals(form.getIsNotice())){
			hql.append(" and isNotice = '"+form.getIsNotice()+"'");	
		}
		if(form.getIsDestine()!=null&&!"".equals(form.getIsDestine())){
			hql.append(" and isDestine = '"+form.getIsDestine()+"'");	
		}
		if(form.getFlag()!=null&&!"".equals(form.getFlag())){
			hql.append(" and status = '"+Contants.THROUGHAPPROVAL+"'");	
		}
		if("relet".equals(form.getFlag()) || "change".equals(form.getFlag())){//当前查询符合变更、续租条件的合同，则查询条件是所有入驻状态isNotice为已入驻的合同
			hql.append(" and isNotice = '"+Contants.HAVEIN+"'");
		}
		if("quit".equals(form.getFlag())){//查询符合退租条件的合同，则查询条件是所有合同状态isDestine为常规的合同
			hql.append(" and isDestine = '"+Contants.NORMARL+"'");
			hql.append(" and isNotice <> '").append(Contants.HASAPPLYGO).append("'");
		}
		hql.append("order by code desc");
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}

	//查询合同续租列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRelet> getContractList(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactRelet where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and beginDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and beginDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			if(form.getStatus().equals(Contants.NOTSUBMIT)){//如果是状态未提交审批，则需要查出状态为未提交审批和待审批的记录
				hql.append(" and (status = '"+Contants.NOTSUBMIT+"'");
				hql.append(" or status = '"+Contants.ONAPPROVAL+"')");
			}else{
				hql.append(" and status = '"+form.getStatus()+"'");
			}
		}
		hql.append(" order by  newCode desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<CompactRelet> list = (List<CompactRelet>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	//查询合同续租列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRelet> getContractCount(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactRelet where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and beginDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and beginDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		hql.append(" order by  newCode desc");
		List<CompactRelet> list = (List<CompactRelet>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}
	
	//查询合同退租列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactQuit> getDelContractList(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactQuit where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and applayDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and applayDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		hql.append(" order by  quitCode desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<CompactQuit> list = (List<CompactQuit>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}
	
	//查询合同续租列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactQuit> getDelContractCount(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactQuit where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and applayDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and applayDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		hql.append(" order by  quitCode desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<CompactQuit> list = (List<CompactQuit>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	//查询合同变更总数
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactChange> getCompactChangeCount(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactChange where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and applyDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and applyDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		hql.append(" order by  newCode desc");
		List<CompactChange> list = (List<CompactChange>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}

	//查询合同变更列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactChange> getCompactChangeList(CompactManagerForm form)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactChange where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and applyDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and applyDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and clientName like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			if(form.getStatus().equals(Contants.NOTSUBMIT)){//如果是状态未提交审批，则需要查出状态为未提交审批和待审批的记录
				hql.append(" and (status = '"+Contants.NOTSUBMIT+"'");
				hql.append(" or status = '"+Contants.ONAPPROVAL+"')");
			}else{
				hql.append(" and status = '"+form.getStatus()+"'");
			}
		}
		hql.append(" order by newCode desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<CompactChange> list = (List<CompactChange>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	@Override
	public List<Compact> getAtTermList(String date) throws DataAccessException {
		
		//String hql  = "from Compact where endDate <= '"+date+"' and  isNotice='"+Contants.HAVEIN+"'";//2013-03-02加入了一个查询的条件
		String hql  = "from Compact where endDate <= '"+date+"' and  isNotice='"+Contants.HAVEIN+"' and isDestine !='"+Contants.HASOVER+"'";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public void updateTask(int count) throws DataAccessException {
		
		String hql  = "update AwokeTask set amount = "+count+" where code = '"+Contants.COMPACTRIGHT+"'";
		this.getSession().createQuery(hql).executeUpdate();
	}

	//检查客户退租是否审批通过
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactQuit> getQuitByCompactId(int id) throws DataAccessException {

		String hql  = "from CompactQuit where pact_id="+id;
		return this.getSession().createQuery(hql).list();
	}

	//根据客户id和押金类型查询客户是否交了押金
	@Override
	public CDeposit getDeposit(int id,String depositType) throws DataAccessException {
		
		String hql  = "from CDeposit where clientId="+id+" and deposit_type='"+depositType+"'";
		return (CDeposit)this.getSession().createQuery(hql).uniqueResult();
	}

	//根据客户id查询客户是否交了预收款
	@Override
	public CAdvance getCAdvance(int id) throws DataAccessException {
		
		String hql  = "from CAdvance where clientId="+id;
		return (CAdvance)this.getSession().createQuery(hql).uniqueResult();
	}
	//根据客户id查询客户预收物业费  
	@Override
	public CAdvanceWuYF getCAdvanceWuYF(int id) throws DataAccessException {
		String hql  = "from CAdvanceWuYF where clientId="+id;
		return (CAdvanceWuYF)this.getSession().createQuery(hql).uniqueResult();
	}
	//根据客户id查询客户是否交了定金
	@Override
	public CEarnest getCEarnest(int id) throws DataAccessException {
		
		String hql  = "from CEarnest where clientId="+id;
		return (CEarnest)this.getSession().createQuery(hql).uniqueResult();
	}
	/**
	 * 为导出报表提供的查询
	 * CompactManagerDAOImpl.getCompactListForE
	 */
	public List<Compact> getCompactListForE(CompactManagerForm form) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from Compact where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		if(form.getType()!=null&&!"".equals(form.getType())){
			hql.append(" and type = '"+form.getType()+"'");	
		}
		if(form.getFlag()!=null&&!"".equals(form.getFlag())){
			hql.append(" and status = '"+Contants.THROUGHAPPROVAL+"'");	
		}
		hql.append(" order by code desc");
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}

	@Override
	public List<Compact> getAtTermList2(String todayDate)
			throws DataAccessException {
		String hql  = "from Compact where endDate < '"+todayDate+"' and  isDestine='"+Contants.NORMARL+"'";
		return this.getSession().createQuery(hql).list();
	}

	//获得意向书列表
	@Override
	public List<CompactIntention> getIntentionList(CompactManagerForm form) throws DataAccessException {

		StringBuffer hql = new StringBuffer("from CompactIntention where 1=1");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			if(Contants.NOTSUBMIT.equals(form.getStatus())){//当前查询的是需要校验的，或者校验完但是还没有被审批的合同（即待审批）
				hql.append(" and( status = '"+Contants.NOTSUBMIT+"' or  status = '"+Contants.ONAPPROVAL+"')");	
			}else{//否则当前查询的就是待审批的，这里就只有待审批的合同了
				hql.append(" and status = '"+Contants.ONAPPROVAL+"'");
			}
		}
		
		hql.append("order by intentionCode");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<CompactIntention> list = (List<CompactIntention>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	/**
	 * 查询所有合同
	 */
	@SuppressWarnings("unchecked")
	public List<Compact> getAllCompactList(CompactManagerForm form) throws DataAccessException {
			
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and status='"+Contants.THROUGHAPPROVAL+"'");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");
		}
		if(form.getType()!=null&&!"".equals(form.getType())){
			hql.append(" and type = '"+form.getType()+"'");	
		}
		if(form.getIsNotice()!=null&&!"".equals(form.getIsNotice())){
			hql.append(" and isNotice = '"+form.getIsNotice()+"'");	
		}
		if(form.getIsDestine()!=null&&!"".equals(form.getIsDestine())){
			hql.append(" and isDestine = '"+form.getIsDestine()+"'");	
		}
		
		hql.append(" order by code");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}
		
	/**
	 * 得到合同总条数
	 */
	@SuppressWarnings("unchecked")
	public List<Compact> getAllCompactCount(CompactManagerForm form) throws DataAccessException {

		StringBuffer hql = new StringBuffer("from Compact where 1=1 and status='"+Contants.THROUGHAPPROVAL+"'");
		Integer lpIdInteger = form.getLpId();
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		if(form.getBeginDate()!=null&&!"".equals(form.getBeginDate())){
			hql.append(" and signDate >= '"+form.getBeginDate()+"'");	
		}
		if(form.getEndDate()!=null&&!"".equals(form.getEndDate())){
			hql.append(" and signDate <= '"+form.getEndDate()+"'");	
		}
		if(form.getClientName()!=null&&!"".equals(form.getClientName())){
			hql.append(" and name like '%"+form.getClientName()+"%'");	
		}
		if(form.getStatus()!=null&&!"".equals(form.getStatus())){
			hql.append(" and status = '"+form.getStatus()+"'");	
		}
		if(form.getType()!=null&&!"".equals(form.getType())){
			hql.append(" and type = '"+form.getType()+"'");	
		}
		if(form.getIsNotice()!=null&&!"".equals(form.getIsNotice())){
			hql.append(" and isNotice = '"+form.getIsNotice()+"'");	
		}
		if(form.getIsDestine()!=null&&!"".equals(form.getIsDestine())){
			hql.append(" and isDestine = '"+form.getIsDestine()+"'");	
		}
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}
}
