package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IOutputDepotManageDAO;
import com.zc13.bkmis.form.OutputDepotManageForm;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotInOutputList;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.mapping.DepotOutputManager;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:38:33 PM
 */
public class OutputDepotManageDAOImpl extends BasicDAOImpl implements IOutputDepotManageDAO {

	//查询部门
	public List selectDepartment() {
		
		String hql = "from SysCode where codeType=:codeType";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("codeType", "department");
		List departList = new ArrayList();
		departList = query.list();
		return departList;
	}

	//按id查询选择要添加的材料信息
	public List selectMaterialsById(String idArray[]) {
		
		String hql = "select new Map( depot.id as id,depot.code as code,depot.name as name,depot.type as type,depot.spec as spec,depot.unit as unit,depot.lowerLimit as lowerLimit,depot.upperLimit as upperLimit,depot.nowStock as nowStock,depot.money as money,depot.doStock as doStock,depot.unitPrice as unitPrice,depot.bz as bz,depot.lpId as lpId,depot.rootUser as rootUser,sc.codeName as codeName )from DepotMaterial depot,SysCode as sc where 1=1 and depot.unit=sc.codeValue ";
//		Query query = this.getSession().createQuery(hql);
//		for(int i=0; i<idInt.length; i++){
//			hql += " or id=? ";
//			query.setParameter(i+1, idInt[i]);
//		}
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " depot.code=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		
//		List materialsList = new ArrayList();
		List materialsList = this.getHibernateTemplate().find(hql,idArray);
//		materialsList = query.list();
		return materialsList;
	}

	
	/**
	 * 显示出库单信息
	 */
	public List showOutputMaterial(SetMaterialsForm smf) {
		
		String code = GlobalMethod.NullToSpace(smf.getOutputCode());
		String startDate = GlobalMethod.NullToSpace(smf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(smf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(smf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(dout.id as id,dout.code as outputCode,dout.date as outputDate,dout.man as man,dout.money as money,dout.status as status,sc.codeName as codeName) from DepotOutputManager as dout,SysCode as sc where dout.department=sc.codeValue";
		if(!"".equals(code) && code != null){
			hql +=" and code like '%"+code+"%'";
		}
	
		if(!"".equals(startDate) && endDate != null){
			hql +=" and date >= :startDate";
		}
		if(!"".equals(endDate) && endDate !=null){
			hql +=" and date < :endDate";
		}
		if(!"".equals(lpId) && lpId !=null){
			hql += " and dout.lpId = "+lpId;
		}
		
		hql += " order by dout.id desc";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && endDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate !=null){
			query.setParameter("endDate", se);
		}
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1"))-1);
		List outputMaerialList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return outputMaerialList;
	}
	//查询记录的总条数
	public int queryCounttotal(SetMaterialsForm smf) {
		
		int count = 0;
		String code = GlobalMethod.NullToSpace(smf.getOutputCode());
		String startDate = GlobalMethod.NullToSpace(smf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(smf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(smf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select count(dout) from DepotOutputManager as dout,SysCode as sc where dout.department=sc.codeValue";
		
		if(!"".equals(code) && code != null){
			hql += " and code like '%"+code+"%'";
		}
		if(!"".equals(lpId) && lpId != null){
			hql += " and dout.lpId = " + lpId;
		}
	
		
		if(!"".equals(startDate) && startDate != null){
			hql += " and date >= :startDate";
		}
		
		if(!"".equals(endDate) && endDate != null){
			hql += " and date <= :endDate";
		}
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && startDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate != null){
			query.setParameter("endDate", se);
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

	//生成出库单号
	public String GenerationNum() {
		
		String num = "";
		String hql = "select max(code) from DepotOutputManager where updateDate=:date";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("date", GlobalMethod.dateToString(new Date()));
		List numList = new ArrayList();
		try{
			numList = query.list();
			if(numList.get(0) != null){
				num = numList.get(0).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}

	//添加出入库详细信息
	public void doAddInOutput(DepotInOutputList depotInOutput) {
		// TODO Auto-generated method stub
		super.saveObject(depotInOutput);
	}

	//添加出库信息
	public void doAddOutput(DepotOutputManager depotOutput) {
		
		super.saveObject(depotOutput);
	}

	//按id查询出库单信息
	public List selectOutput(int id) {
		
		String hql = "from DepotOutputManager where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List outputList = new ArrayList();
		outputList = query.list();
		return outputList;
	}

	//按出库单编号查询出库的相信信息
	public List selectinoutput(String inouputCode) {
		
		String hql = "select dio,dm.code,dm.name,dm.spec,dm.doStock,dom.money,sc.codeName as unit from DepotInOutputList as dio,DepotMaterial as dm,DepotOutputManager as dom,SysCode as sc where sc.codeValue=dm.unit and dm.id = dio.materialId and dom.code = dio.inOutputCode and dio.type2 = '0' and  dio.inOutputCode = :inOutputCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("inOutputCode", inouputCode);
		List inoutputList = new ArrayList();
		inoutputList = query.list();
		return inoutputList;
	}

	//按出库单编号删除详细信息
	public void deleteInOutput(String inouputCode) {
		
		String hql = "delete from DepotInOutputList where type2 = '0' and inOutputCode = :inOutputCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("inOutputCode",inouputCode);
		query.executeUpdate();
	}

	//按id查询要删除出库单的编号
	public List selectOutputCode(Integer[] ids) {
		
		String hql = "select dom.code from DepotOutputManager as dom where 1=1";
		if(ids != null && ids.length > 0){
			hql += " and (";
			for(int i=0; i<ids.length; i++){
				hql += " dom.id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List codeList = this.getHibernateTemplate().find(hql,ids);
		return codeList;
	}

	//删除多个出库单的详细信息
	public void deleteInOutList(List list) {
		
		Iterator it = list.iterator();
		String code = "";
		while(it.hasNext()){
			code = it.next().toString();
			String hql = "delete from DepotInOutputList where type2 = '0' and inOutputCode = :inOutputCode";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("inOutputCode", code);
			query.executeUpdate();
		}
	}

	/**出库明细表**/
	//查询显示出库明细表的详细信息
	public List selectOutputDetail(OutputDepotManageForm odmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(odmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(odmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(odmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(odmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(odmf.getMaterCode());
		String department = GlobalMethod.NullToSpace(odmf.getDepartment());
//		String lpId =  GlobalMethod.NullToSpace(String.valueOf(odmf.getLpId()));
		//int materialId = odmf.getMaterialId();
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(odmf.getDmtId());
		String types = "";
		if(!dmtId.equals("")){
			String dmtHql = "from DepotMaterialType where parentid="+dmtId;
			List<DepotMaterialType> idList = null;
			idList = this.getSession().createQuery(dmtHql).list();
			if(idList!=null&&idList.size()>0){
				for(int i = 0;i<idList.size();i++){
					types+=idList.get(i).getCode()+",";
				}
				types = types.substring(0, types.length()-1);
				types = types.replaceAll(",", "','");
				types = "'"+types+"'";
			}else{
				DepotMaterialType objs = (DepotMaterialType)this.getSession().get(DepotMaterialType.class, Integer.parseInt(dmtId));
				types = "'"+objs.getCode()+"'";
			}
			
		}
		
		//String hql = " select new Map(diol.inOutputCode as inoutCode,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,diol.amountMoney as money,dom.date as outDate,dom.type2 as outType,dom.man as man,sc.codeName as codeName) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and dom.status = '已结算'";
		String hql = " select new Map(diol.inOutputCode as inoutCode,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,diol.amountMoney as money,dom.date as outDate,dom.type2 as outType,dom.man as man,sc.codeName as codeName,sc2.codeName as unit) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc,SysCode as sc2 where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and sc2.codeValue=dm.unit ";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dom.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dom.date <= :endDate";
		}
//		if(!"".equals(lpId) && null != lpId){
//			hql += " and dom.lpId =" + lpId;
//		}
		
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(department) && null != department){
			hql += " and dom.department = :department";
		}
		hql += " order by dom.id desc";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(department) && null != department){
			query.setParameter("department", department);
		}
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(odmf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(odmf.getCurrentpage(),"1"))-1);
		List outputDetailList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return outputDetailList;
	}

	//查询出库计算总条数
	public int queryCountDetail(OutputDepotManageForm odmf) {
		
		int count = 0;
		String inoutCode = GlobalMethod.NullToSpace(odmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(odmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(odmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(odmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(odmf.getMaterCode());
		String department = GlobalMethod.NullToSpace(odmf.getDepartment());
		//int materialId = odmf.getMaterialId();
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(odmf.getDmtId());
		String types = "";
		if(!dmtId.equals("")){
			String dmtHql = "from DepotMaterialType where parentid="+dmtId;
			List<DepotMaterialType> idList = null;
			idList = this.getSession().createQuery(dmtHql).list();
			if(idList!=null&&idList.size()>0){
				for(int i = 0;i<idList.size();i++){
					types+=idList.get(i).getCode()+",";
				}
				types = types.substring(0, types.length()-1);
				types = types.replaceAll(",", "','");
				types = "'"+types+"'";
			}else{
				DepotMaterialType objs = (DepotMaterialType)this.getSession().get(DepotMaterialType.class, Integer.parseInt(dmtId));
				types = "'"+objs.getCode()+"'";
			}
			
		}
		
		//String hql = " select count(diol) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and dom.status = '已结算'";
		String hql = " select count(diol) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department ";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dom.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dom.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(department) && null != department){
			hql += " and dom.department = :department";
		}
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(department) && null != department){
			query.setParameter("department", department);
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

	//计算出库明细表中的总数量和总金额
	public List summaryDetail(OutputDepotManageForm odmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(odmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(odmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(odmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(odmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(odmf.getMaterCode());
		String department = GlobalMethod.NullToSpace(odmf.getDepartment());
		//int materialId = odmf.getMaterialId();
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(odmf.getDmtId());
		String types = "";
		if(!dmtId.equals("")){
			String dmtHql = "from DepotMaterialType where parentid="+dmtId;
			List<DepotMaterialType> idList = null;
			idList = this.getSession().createQuery(dmtHql).list();
			if(idList!=null&&idList.size()>0){
				for(int i = 0;i<idList.size();i++){
					types+=idList.get(i).getCode()+",";
				}
				types = types.substring(0, types.length()-1);
				types = types.replaceAll(",", "','");
				types = "'"+types+"'";
			}else{
				DepotMaterialType objs = (DepotMaterialType)this.getSession().get(DepotMaterialType.class, Integer.parseInt(dmtId));
				types = "'"+objs.getCode()+"'";
			}
			
		}
		
		//String hql = "select new Map(sum(diol.amount) as amount,sum(diol.amountMoney) as totalMoney) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterialCopy as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and dom.status = '已结算'";
		String hql = "select new Map(sum(diol.amount) as amount,sum(diol.amountMoney) as totalMoney) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterialCopy as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department ";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dom.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dom.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(department) && null != department){
			hql += " and dom.department = :department";
		}
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(department) && null != department){
			query.setParameter("department", department);
		}
		List countList = new ArrayList();
		countList = query.list();
		return countList;
	}

	//查询详细信息用于更新材料
	public List selectDetailInoutput(List list) {
		
		String inoutCode = "";
		Iterator it = list.iterator();
		List detailList = new ArrayList();
		while(it.hasNext()){
			inoutCode = (String)it.next();
			String hql = "select materialId,amount,amountMoney from DepotInOutputList where type2 = '0' and inOutputCode = :inOutputCode";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("inOutputCode", inoutCode);
			detailList = query.list();
		}
		return detailList;
	}

	//按材料编号查询材料的数量和金额
	public List selectMaterilList(String materId) {
		
		List list = new ArrayList();
		if(!"".equals(materId) && null != materId){
			String hql = "select nowStock,doStock,money from DepotMaterial where id = :id";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("id", Integer.parseInt(materId));
			list = query.list();
		}
		return list;
	}

	//查询所有的出库明细信息
	public List selectAllDetail(OutputDepotManageForm odmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(odmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(odmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(odmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(odmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(odmf.getMaterCode());
		String department = GlobalMethod.NullToSpace(odmf.getDepartment());
		
		String se = "";
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(odmf.getDmtId());
		String types = "";
		if(!dmtId.equals("")){
			String dmtHql = "from DepotMaterialType where parentid="+dmtId;
			List<DepotMaterialType> idList = null;
			idList = this.getSession().createQuery(dmtHql).list();
			if(idList!=null&&idList.size()>0){
				for(int i = 0;i<idList.size();i++){
					types+=idList.get(i).getCode()+",";
				}
				types = types.substring(0, types.length()-1);
				types = types.replaceAll(",", "','");
				types = "'"+types+"'";
			}else{
				DepotMaterialType objs = (DepotMaterialType)this.getSession().get(DepotMaterialType.class, Integer.parseInt(dmtId));
				types = "'"+objs.getCode()+"'";
			}
			
		}
		
		//String hql = "select new Map(diol.inOutputCode as inoutCode,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,diol.amountMoney as money,dom.date as outDate,dom.type2 as outType,dom.man as man,sc.codeName as codeName) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and dom.status = '已结算'";
		String hql = "select new Map(diol.inOutputCode as inoutCode,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,diol.amountMoney as money,dom.date as outDate,dom.type2 as outType,dom.man as man,sc.codeName as codeName,sc2.codeName as unit) from DepotInOutputList as diol,DepotOutputManager as dom,DepotMaterial as dm,SysCode as sc,SysCode as sc2 where diol.type2 = '0' and diol.inOutputCode = dom.code and diol.materialId = dm.id and sc.codeValue = dom.department and sc2.codeValue=dm.unit ";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dom.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dom.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(department) && null != department){
			hql += " and dom.department = :department";
		}
		
		hql += " order by dom.id desc";
		
		Query query = this.getSession().createQuery(hql);
		
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(department) && null != department){
			query.setParameter("department", department);
		}
		
		List allDetailList = query.list();
		return allDetailList;
	}

	//查询所有的出库信息
	public List selectAllOutput(SetMaterialsForm smf) {
		
		String code = GlobalMethod.NullToSpace(smf.getOutputCode());
		String startDate = GlobalMethod.NullToSpace(smf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(smf.getEndDate());
		String se = "";
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(dout.id as id,dout.code as outputCode,dout.date as outputDate,dout.man as man,dout.money as money,dout.status as status,sc.codeName as codeName) from DepotOutputManager as dout,SysCode as sc where dout.department=sc.codeValue";
		
		if(!"".equals(code) && code != null){
			hql +=" and code like '%"+code+"%'";
		}
		if(!"".equals(startDate) && endDate != null){
			hql +=" and date >= :startDate";
		}
		if(!"".equals(endDate) && endDate !=null){
			hql +=" and date < :endDate";
		}
		
		hql += " order by dout.id desc";
		
		Query query = this.getSession().createQuery(hql);
		
		if(!"".equals(startDate) && endDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate !=null){
			query.setParameter("endDate", se);
		}
		List allOutputList = query.list();
		return allOutputList;
	}

	@Override
	public List summary(SetMaterialsForm smf) {

		String code = GlobalMethod.NullToSpace(smf.getOutputCode());
		String startDate = GlobalMethod.NullToSpace(smf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(smf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(smf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != startDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(sum(dout.money) as totalMoney) from DepotOutputManager as dout,SysCode as sc where dout.department=sc.codeValue";
		if(!"".equals(code) && code != null){
			hql +=" and code like '%"+code+"%'";
		}
	
		if(!"".equals(startDate) && endDate != null){
			hql +=" and date >= :startDate";
		}
		if(!"".equals(endDate) && endDate !=null){
			hql +=" and date < :endDate";
		}
		if(!"".equals(lpId) && lpId !=null){
			hql += " and dout.lpId = "+lpId;
		}
		
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && endDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate !=null){
			query.setParameter("endDate", se);
		}
		List outputMaerialList = query.list();
		return outputMaerialList;
	}
}
