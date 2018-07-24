package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICustomerRoomDAO;
import com.zc13.bkmis.form.CompactRoomForm;
import com.zc13.bkmis.mapping.AnalysisClientComeGo;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactQuitBill;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;

/**
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：17:16:13 PM
 */
public class CustomerRoomDAOImpl extends BasicDAOImpl implements ICustomerRoomDAO {

	//查询合同列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getCompactList(String key,String currentpage,String code,String pagesize,String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws DataAccessException {
		
		//StringBuffer hql = new StringBuffer("from Compact where 1=1 and status != '过期合同' and  isDestine != '"+Contants.COMPACTDESTINE+"' ");
		//2011-03-06董道奎改
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and status = '"+Contants.THROUGHAPPROVAL+"'  and  isDestine != '"+Contants.HASOVER+"' and  isDestine != '"+Contants.BEENDELETE+"' ");
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and endDate >= '"+beginDate+"'");
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and endDate <= '"+endDate+"'");	
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");	
		}
		if(code!=null&&!"".equals(code)){
			hql.append(" and code like '%"+code+"%'") ;
		}
		if(lpId!=null&&!"0".equals(lpId)){
			hql.append(" and lpId = "+lpId);
		}
		if(isNotice!=null&&!"".equals(isNotice)){
			if(isNotice.equals(Contants.NOTNOTICE)){//如果是未通知入住
				hql.append(" and (isNotice = '"+isNotice+"' or isNotice is null or isNotice='') ");
			}else{
				hql.append(" and isNotice = '"+isNotice+"'");
			}
		}
		hql.append(" order by code");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}
	
	//查询客户列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactClient> getClientList(String currentpage,String pagesize,CompactRoomForm customForm ) throws DataAccessException {
		
		String clientName = customForm.getClientName();
		String clientType = customForm.getClientType();
		String ishString = customForm.getIsHighTech();
		String logindateStart = customForm.getLoginDate();
		String logindateEnd = customForm.getLoginDateEnd();
		String loginFundString = customForm.getLoginFund();
		String loginFundEnd = customForm.getLoginFundEnd();
		Integer lpId= customForm.getLpId();
		StringBuffer hql = new StringBuffer("from CompactClient where 1=1");
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(clientType!=null&&!"".equals(clientType)){
			hql.append(" and clientType like '%"+clientType+"%'");	
		}
		if(ishString!=null&&!"".equals(ishString)){
			hql.append(" and isHighTech like '%"+ishString+"%'");	
		}
		if(logindateStart!=null&&!"".equals(logindateStart) && logindateEnd!=null&&!"".equals(logindateEnd) ){
			hql.append(" and loginDate <=  '"+logindateEnd+"' and loginDate >= '"+logindateStart+"'");	
		}
		if(loginFundString!=null&&!"".equals(loginFundString) && loginFundEnd!=null&&!"".equals(loginFundEnd) ){
			hql.append(" and loginFund <=  "+loginFundEnd+" and loginFund >= "+loginFundString);	
		}
		
		if(lpId!=null && lpId != 0){
			hql.append(" and lpId = "+lpId);
		}
		hql.append(" order by code desc");
		//每页显示的条数，空的情况下默认是10
		int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
		//当前是从第几条开始
		int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
		List<CompactClient> list = (List<CompactClient>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		return list;
	}

	//查询合同条数
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getCount(String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and status = '"+Contants.THROUGHAPPROVAL+"'  and  isDestine != '"+Contants.HASOVER+"' ");
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and beginDate >= '"+beginDate+"'");	
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and beginDate <= '"+endDate+"'");	
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");	
		}
		if(lpId!=null&&!"0".equals(lpId)){
			hql.append(" and lpId = "+lpId);	
		}
//		if(code!=null&&"".equals(code)){
//			hql.append(" and code like '%"+code+"%'") ;
//		}
		if(isNotice!=null&&!"".equals(isNotice)){
			if(isNotice.equals(Contants.NOTNOTICE)){//如果是未通知入住
				hql.append(" and (isNotice = '"+isNotice+"' or isNotice is null or isNotice='') ");
			}else{
				hql.append(" and isNotice = '"+isNotice+"'");	
			}
		}
		Query query = this.getSession().createQuery(hql.toString());
		List<Compact> list = (List<Compact>)query.list();
		return list;
	}
	
	//查询客户条数
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactClient> getClientCount(CompactRoomForm customForm) throws DataAccessException {

		String clientName = customForm.getClientName();
		String clientType = customForm.getClientType();
		String ishString = customForm.getIsHighTech();
		String logindateStart = customForm.getLoginDate();
		String logindateEnd = customForm.getLoginDateEnd();
		String loginFundString = customForm.getLoginFund();
		String loginFundEnd = customForm.getLoginFundEnd();
		StringBuffer hql = new StringBuffer("from CompactClient where 1=1");
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(clientType!=null&&!"".equals(clientType)){
			hql.append(" and clientType like '%"+clientType+"%'");	
		}
		if(ishString!=null&&!"".equals(ishString)){
			hql.append(" and isHighTech like '%"+ishString+"%'");	
		}
		if(logindateStart!=null&&!"".equals(logindateStart) && logindateEnd!=null&&!"".equals(logindateEnd) ){
			hql.append(" and loginDate <=  '"+logindateEnd+"' and loginDate >= '"+logindateStart+"'");	
		}
		if(loginFundString!=null&&!"".equals(loginFundString) && loginFundEnd!=null&&!"".equals(loginFundEnd) ){
			hql.append(" and loginFund <=  "+loginFundEnd+" and loginFund >= "+loginFundString);	
		}
		Query query = this.getSession().createQuery(hql.toString());
		List<CompactClient> list = (List<CompactClient>)query.list();
		return list;
	}

	//根据楼盘id得到收费账套id
	@Override
	public CAccounttemplate getAccounttemplateId(int lpId) throws DataAccessException {

		String hql = "from CAccounttemplate where lpid ="+lpId;
		Query query = this.getSession().createQuery(hql);
		return (CAccounttemplate)query.uniqueResult();
	}

	//根据根据账套id和费用类型id得到收费标准
	@Override
	public List<CCoststandard> getAccounttemplate(int id, int costTypeId,String type)
			throws DataAccessException {

		String hql = "from CCoststandard where accountTemplateId ="+id + " and costTypeId="+costTypeId+" and billType='"+type+"'  and display is Null ";
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	//得到表中id的最大值
	@Override
	public Integer getObjectId(String obj) throws DataAccessException {
		
		String hql = "select max(id) from "+ obj;
		Query query = this.getSession().createQuery(hql);
		return (Integer)query.uniqueResult();
	}

	//得到合同编码的最大值
	@Override
	public String getCompactCode(Integer lpId) throws DataAccessException {
		
		String hql = "select max(code) from Compact where lpId="+lpId;
		
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}
	
	//得到客户编码的最大值
	@Override
	public String getCustomerCode(Integer lpId) throws DataAccessException {
		
		String hql = "select max(code) from CompactClient where lpId="+lpId;
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}
	
	//得到续租编码的最大值
	@Override
	public String getQuitCode() throws DataAccessException {
		
		String hql = "select max(quitCode) from CompactQuit";
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}

	//根据合同id得到对应的客户
	@Override
	public CompactClient getClientByCompId(int id) throws DataAccessException {
		
		String hql = "from CompactClient where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (CompactClient)query.uniqueResult();
	}

	//根据合同id得到租金列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRent> getRentList(int id) throws DataAccessException {
	
		String hql = "from CompactRent where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRent>)query.list();
	}

	//根据客户id查询所租的房间列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ERoomClient> getRoomListByClientId(int id,int compactId)
			throws DataAccessException {
		
		String hql = "select ec from ERoomClient ec,ERooms er where ec.roomId=er.roomId and ec.clientId="+id+"and ec.pactId="+compactId +"order by er.roomCode ";
		Query query = this.getSession().createQuery(hql);
		return (List<ERoomClient>)query.list();
	}

	//根据合同id查询房间对应的收费标准列表
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRoomCoststandard> getStandardListByCompactId(int id)
			throws DataAccessException {
		
		String hql = "select t from CompactRoomCoststandard t left outer join t.ERooms e where t.compact.id="+id+" and t.display is Null order by e.roomCode";
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRoomCoststandard>)query.list();
	}

	//根据合同id得到收费标准列表
	@Override
	public List<CompactRent> getCompactRent(int id)
			throws DataAccessException {
		
		String hql = "from CompactRent where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRent>)query.list();
	}

	//根据合同id得到房租信息列表。所有的，与getStandardListByCompactId方法的区别：取所有的，用于更新合同时删除所有收费标准前的获取，和正式入住时更新所有的status
	@SuppressWarnings("unchecked")
	@Override
	public List<CompactRoomCoststandard> getCompactRoomCoststandard(int id)
			throws DataAccessException {
		
		String hql = "from CompactRoomCoststandard where pact_id="+id+" ";
		Query query = this.getSession().createQuery(hql);
		return (List<CompactRoomCoststandard>)query.list();
	}

	//根据客户id得到房间客户列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ERoomClient> getERoomClient(int id)
			throws DataAccessException {
		
		String hql = "from ERoomClient where clientId="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<ERoomClient>)query.list();
	}
	
	//根据合同id得到房间客户列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ERoomClient> getERoomClientByPactId(int id)
	throws DataAccessException {
		
		String hql = "from ERoomClient where pact_id="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<ERoomClient>)query.list();
	}

	//根据客户id查找所对应的合同列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Compact> getCompactByClientId(int id)
			throws DataAccessException {
		
		String hql = "from Compact where clientId="+id;
		Query query = this.getSession().createQuery(hql);
		return (List<Compact>)query.list();
	}

	//根据客户id和楼盘id得到客户的入住迁出记录
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisClientComeGo> getComeGo(int clientId, int lpId)
			throws DataAccessException {

		String hql = "from AnalysisClientComeGo where client_Id="+clientId+" and lp_Id="+lpId + " order by goDate desc";
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	//根据参数类型得到系统参数列表
	@Override
	public List<SysCode> getSysCode(String codeType,Integer lpId) throws DataAccessException {

		String hql = "from SysCode where codeType='"+codeType+"'";
		if(lpId!=null && lpId != 0){
			hql+=" and lpId = "+lpId;
		}
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<CompactClient> getClient(String code)
			throws DataAccessException {

		String hql = "from CompactClient where code='"+code+"'";
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}
	
	/* 根据楼盘查询房间 */
	public List<ERooms> queryRoomforLp(Integer id,String table) throws DataAccessException {
		//根据楼盘查询出楼栋
		String hql = "from EBuilds where ELp = :elp";
		ELp elp = new ELp();
		elp.setLpId(id);
		Query query = this.getSession().createQuery(hql);
		query.setParameter("elp", elp);
		List<EBuilds> blist = query.list();
		//
		List<ERooms> list = new ArrayList();
		for(EBuilds b:blist){
			//得到楼栋id
			Integer buildId = b.getBuildId();
			List<ERooms> alist = queryRoomByBuild(buildId,table);
			list.addAll(alist);//将根据每个楼栋id查询出来的list添加到一个里面
		}
		return list;
	}

	/* 根据楼栋查询房间 */
	public List<ERooms> queryRoomByBuild(Integer id,String table) throws DataAccessException {
		
		EBuilds ebuild = new EBuilds();
		ebuild.setBuildId(id);
		ERooms eroom = new ERooms();
		//保存一个ebuild对象到eroom中
		eroom.setEBuilds(ebuild);
		String hql = "from ERooms where EBuilds = :ebuild order by roomCode" ;
	    Query query = this.getSession().createQuery(hql);
	    query.setParameter("ebuild", ebuild);
	    
		List<ERooms> list = query.list();
		return list;
	}
	
	//得到id最大的合同
	@Override
	public int getLastCompact() throws DataAccessException {

		String sql = "select max(id) from compact";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		return (Integer)query.uniqueResult();
	}

	//根据合同id得到变更合同
	@Override
	public CompactChange getChangeCompactByCId(int id) throws DataAccessException {
	
		String sql = "from CompactChange where new_id="+id;
		Query query = this.getSession().createQuery(sql);
		return (CompactChange)query.uniqueResult();
	}

	//根据合同id得到续租合同
	@Override
	public CompactRelet getReletCompactByCId(int id) throws DataAccessException {
		
		String sql = "from CompactRelet where new_id="+id;
		Query query = this.getSession().createQuery(sql);
		return (CompactRelet)query.uniqueResult();
	}
	
	//查询迁出合同列表
	public List<Compact> getOutCompactList() throws DataAccessException {
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and isNotice in('"+Contants.HASNOTICE+"','"+Contants.HASGO+"')");
			hql.append(" and goDate >= '"+DateUtil.getNowDate("yyyy-MM-dd")+"'");	
			hql.append(" order by id");
		List<Compact> list = (List<Compact>)this.getSession().createQuery(hql.toString()).list();
		return list;
	}
	//删除迁出保存的账单id记录
	public void delCompactBill(String pactId) throws DataAccessException {
		String hql = "delete from CompactQuitBill where pactId = :pactId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pactId", Integer.decode(pactId));
		query.executeUpdate();
	}
	//查询迁出生成的账单id
	public List<CompactQuitBill> getBillIds(String pactId) throws DataAccessException{
		String hql = " from CompactQuitBill where pactId = :pactId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pactId", Integer.decode(pactId));
		List<CompactQuitBill> list = query.list();
		return list;
	}
	
	public void updateLastBuildDate(Integer id,String lastBuildDate)throws DataAccessException {
		String hql = "update CompactRoomCoststandard set lastBuildDate = :lastBuildDate where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		query.setParameter("lastBuildDate", lastBuildDate);
		query.executeUpdate();
	}
	
	//查询客户列表，导出报表
	public List<CompactClient> explorCustomerRoomList(CompactRoomForm compactRoomForm)
			throws DataAccessException {
		
		String clientName = compactRoomForm.getClientName();
		String clientType = compactRoomForm.getClientType();
		String isHighTech = compactRoomForm.getIsHighTech();
		String loginDate = compactRoomForm.getLoginDate();
		String loginDateEnd = compactRoomForm.getLoginDateEnd();
		String loginFund = compactRoomForm.getLoginFund();
		String loginFundEnd = compactRoomForm.getLoginFundEnd();
		Integer lpIdInteger = compactRoomForm.getLpId();
		
		StringBuffer hql = new StringBuffer("from CompactClient where 1 = 1");
		
		if(lpIdInteger!=null && lpIdInteger!=0){
			hql.append(" and lpId = "+lpIdInteger);
		}
		
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and name like '%"+clientName+"%'");	
		}
		if(clientType!=null&&!"".equals(clientType)){
			hql.append(" and clientType like '%"+clientType+"%'");
		}
		if(isHighTech!=null&&!"".equals(isHighTech)){
			hql.append(" and isHighTech like '%"+isHighTech+"%'");
		}
		if(loginDate!=null&&!"".equals(loginDate)){
			hql.append(" and loginDate >='"+loginDate+"'");
		}
		if(loginDateEnd!=null&&!"".equals(loginDateEnd)){
			hql.append(" and loginDate <= '"+loginDateEnd+"'");
		}
		if(loginFund!=null&&!"".equals(loginFund)){
			hql.append(" and loginFund >='"+loginFund+"'");
		}
		if(loginFundEnd!=null&&!"".equals(loginFundEnd)){
			hql.append(" and loginFund <='"+loginFundEnd+"'");
		}
		Query query = this.getSession().createQuery(hql.toString());
		List<CompactClient> customerList = query.list();
		return customerList;
	}
	
	//查询客户列表，导出报表
	public List<Compact> explorCustomerRoomList1(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate)
			throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from Compact where 1=1 and status = '"+Contants.THROUGHAPPROVAL+"'  and  isDestine != '"+Contants.HASOVER+"' ");
		if(lpId!=null&&!"0".equals(lpId)){
		    hql.append(" and lpId ="+lpId );
		}
		if(clientName!=null&&!"".equals(clientName)){
			hql.append(" and clientName like '%"+clientName+"%'");
		}
		if(roomCode!=null&&!"".equals(roomCode)){
			hql.append(" and roomCodes like '%"+roomCode+"%'");
		}
		if(status!=null&&!"".equals(status)){
			hql.append(" and status ='"+status+"'");
		}
		if(beginDate!=null&&!"".equals(beginDate)){
			hql.append(" and inDate >= '"+beginDate+"'");
		}
		if(endDate!=null&&!"".equals(endDate)){
			hql.append(" and inDate <= '"+endDate+"'");
		}
		Query query = this.getSession().createQuery(hql.toString());
		List<Compact> customerList = query.list();
		return customerList;
	}

	@Override
	public void cancelChecked4Compact(String id) throws DataAccessException {
		String hql = "update Compact t set t.status=:status where t.id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("status", Contants.NOTSUBMIT);//设置为未提交审批
		query.setParameter("id", Integer.parseInt(id));
		query.executeUpdate();
	}

	@Override
	public void cancelChecked4Change(String id) throws DataAccessException {
		String hql = "update CompactChange t set t.status=:status where t.compactByNewId.id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("status", Contants.NOTSUBMIT);//设置为未提交审批
		query.setParameter("id", Integer.parseInt(id));
		query.executeUpdate();
	}

	@Override
	public void cancelChecked4Relet(String id) throws DataAccessException {
		String hql = "update CompactRelet t set t.status=:status where t.compactByNewId.id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("status", Contants.NOTSUBMIT);//设置为未提交审批
		query.setParameter("id", Integer.parseInt(id));
		query.executeUpdate();
	}

	@Override
	public void cancelChecked4CompactIntention(String id) throws DataAccessException {
		String hql = "update CompactIntention t set t.status=:status where t.id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("status", Contants.NOTSUBMIT);//设置为未提交审批
		query.setParameter("id", Integer.parseInt(id));
		query.executeUpdate();
		
	}

	/**
	 * 获取最大迁出编号
	 */
	@Override
	public String getQuitCode(Integer lpId) throws DataAccessException {
		
		String hql = "select max(quitCode) from CompactQuit where lpId="+lpId;
		
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}
}
