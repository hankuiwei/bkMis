package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IInputDepotManageDAO;
import com.zc13.bkmis.form.InputDepotManageForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:06:33 PM
 */
public class InputDepotManageDAOImpl extends BasicDAOImpl implements IInputDepotManageDAO {

	//根据id显示选择要添加的信息
	public List selectMaterialsById(String[] idArray) {
		
		//修改前
//		String hql = "from DepotMaterial depot where 1=1";
		//修改后
		String hql = "select new Map( depot.id as id,depot.code as code,depot.name as name,depot.type as type,depot.spec as spec,depot.unit as unit,depot.lowerLimit as lowerLimit,depot.upperLimit as upperLimit,depot.nowStock as nowStock,depot.money as money,depot.doStock as doStock,depot.unitPrice as unitPrice,depot.bz as bz,depot.lpId as lpId,depot.rootUser as rootUser,sc.codeName as codeName )from DepotMaterial depot,SysCode as sc where 1=1 and depot.unit=sc.codeValue "; 
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " depot.code=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List materialsList = this.getHibernateTemplate().find(hql,idArray);
		return materialsList;
	}

	//查询显示的入库单信息
	public List selectInputList(InputDepotManageForm idmf) {
		
		String inputCode = GlobalMethod.NullToSpace(idmf.getInputCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(idmf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(dim.id as id,dim.code as inputCode,dim.date as inputDate,dim.man as man,dim.money as money,dim.invoiceCode as invoiceCode,dim.status as status,dim.supplierId as supplierName) from DepotInputManager as dim where 1=1 ";
		//String hql = "select dim from DepotInputManager dim left join DepotSupplier ds on dim.supplierId = ds.id";
		if(!"".equals(inputCode) && inputCode != null){
			hql += " and dim.code like '%"+inputCode+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxInvoiceCode()).equals("")){
			hql += " and dim.invoiceCode like '%"+idmf.getCxInvoiceCode()+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxStatus()).equals("")){
			hql += " and dim.status = '"+idmf.getCxStatus()+"'";
		}
		if(!"".equals(startDate) && startDate != null){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(lpId) && lpId != null){
			hql += " and dim.lpId ="+lpId;
		}
		
		if(!"".equals(endDate) && endDate != null){
			hql += " and dim.date <= :endDate";
		}
		hql += " order by dim.id desc";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && startDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate != null){
			query.setParameter("endDate", se);
		}
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(idmf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(idmf.getCurrentpage(),"1"))-1);
		List inputList =  query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return inputList;
	}

	//入库单信息记录数
	public int queryCounttotal(InputDepotManageForm idmf) {
		
		int count = 0;
		String inputCode = GlobalMethod.NullToSpace(idmf.getInputCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(idmf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select count(dim) from DepotInputManager as dim where 1=1 ";
		if(!"".equals(inputCode) && inputCode != null){
			hql += " and dim.code like '%"+inputCode+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxInvoiceCode()).equals("")){
			hql += " and dim.invoiceCode like '%"+idmf.getCxInvoiceCode()+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxStatus()).equals("")){
			hql += " and dim.status = '"+idmf.getCxStatus()+"'";
		}
		if(!"".equals(startDate) && startDate != null){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(lpId) && lpId != null){
			hql += " and dim.lpId = " + lpId ;
		}
		
		if(!"".equals(endDate) && endDate != null){
			hql += " and dim.date <= :endDate";
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

	//查询供应商
	public List selectSupplier() {
		
		String hql = "select d.id,d.name from DepotSupplier d";
		Query query = this.getSession().createQuery(hql);
		List supplierList = query.list();
		return supplierList;
	}

	//生成入库单编号
	public String GenerationNum() {
		
		String num = "";
		String hql = "select max(code) from DepotInputManager where updateDate = :date";
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

	//按id查询出要修改的信息
	public List selectEditInput(int id) {
		
		String hql = "from DepotInputManager where id = :id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List inputList = new ArrayList();
		inputList = query.list();
		return inputList;
	}

	//按入库单编号查询入库详细信息
	public List selectDetailInput(String inputCode) {
		
		String hql = "select dio,dm.code,dm.name,dm.spec,dim.money,sc.codeName as unit from DepotInOutputList as dio,DepotMaterial as dm,DepotInputManager as dim,SysCode as sc where dm.unit = sc.codeValue and  dm.id = dio.materialId and dim.code = dio.inOutputCode and dio.type2 = '1' and dio.inOutputCode = :inputCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("inputCode", inputCode);
		List detailInput = new ArrayList();
		detailInput = query.list();
		return detailInput;
	}

	//按入库库单编号删除详细信息
	public void deleteInOutput(String inputCode) {
		
		String hql = "delete from DepotInOutputList where type2 = '1' and inOutputCode = :inOutputCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("inOutputCode", inputCode);
		query.executeUpdate();
	}

	//查询要删除的入库单编号
	public List selectIutputCode(Integer[] ids) {

		String hql = "select dom.code from DepotInputManager as dom where 1=1";
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

	//删除入库单时删除其详细信息
	public void deleteInOutList(List list) {
		
		Iterator it = list.iterator();
		String code = "";
		while(it.hasNext()){
			code = it.next().toString();
			String hql = "delete from DepotInOutputList where type2 = '1' and inOutputCode = :inOutputCode";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("inOutputCode", code);
			query.executeUpdate();
		}
	}

	/**入库详细表的查询**/ 
	//查询显示入库详细表
	public List selectInputDetail(InputDepotManageForm idmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(idmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(idmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(idmf.getMaterCode());
		int materialId = idmf.getMaterialId();
//		String lpId = GlobalMethod.NullToSpace(String.valueOf(idmf.getLpId()));
		//String supplierId = "";
		//int supplierid = 0;
		String supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId());
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(idmf.getDmtId());
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
		
		/*if(null != supplierId && !"".equals(supplierId)){
			supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId().toString());
			supplierid = idmf.getSupplierId();
		}*/
		
		//String hql = "select new Map(diol.inOutputCode as inoutCode,diol.amountMoney as amountMoney,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,dim.man as man,dim.date as date,dim.supplierId as suplyName) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and dim.status = '已结算'";
		String hql = "select new Map(diol.inOutputCode as inoutCode,diol.amountMoney as amountMoney,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,dim.man as man,dim.date as date,dim.supplierId as suplyName,sc.codeName as unit) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm, SysCode as sc where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and sc.codeValue = dm.unit";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dim.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(supplierId) && null != supplierId){
			hql += " and dim.supplierId =:supplierId";
		}
//		if(!"".equals(lpId) && null != lpId){
//			hql += " and diol.lpId="+lpId;
//		}
//		
		hql += " order by dim.id desc";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(supplierId) && null != supplierId){
			query.setParameter("supplierId", supplierId);
		}
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(idmf.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(idmf.getCurrentpage(),"1"))-1);
		List inputDetailList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return inputDetailList;
	}

	//查询入库详细记录的条数
	public int queryCountInDetail(InputDepotManageForm idmf) {
		
		int count = 0;
		String inoutCode = GlobalMethod.NullToSpace(idmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(idmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(idmf.getMaterCode());
		int materialId = idmf.getMaterialId();
		//String supplierId = "";
		//int supplierid = 0;
		String supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId());
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(idmf.getDmtId());
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
		/*if(idmf.getSupplierId() != null && idmf.getSupplierId() != 0){
			supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId().toString());
			supplierid = idmf.getSupplierId();
		}*/
		
		//String hql = "select count(diol) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and dim.status = '已结算'";
		String hql = "select count(diol) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dim.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(supplierId) && null != supplierId){
			hql += " and dim.supplierId =:supplierId";
		}
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(supplierId) && null != supplierId){
			query.setParameter("supplierId", supplierId);
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

	//查询入库的入库总数量和入库总金额
	public List summaryInDetail(InputDepotManageForm idmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(idmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(idmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(idmf.getMaterCode());
		int materialId = idmf.getMaterialId();
		//String supplierId = "";
		//int supplierid = 0;
		String supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId());
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(idmf.getDmtId());
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
		
		/*if(idmf.getSupplierId() != null && idmf.getSupplierId() != 0){
			supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId().toString());
			supplierid = idmf.getSupplierId();
		}*/
		
		//String hql = "select new Map(sum(diol.amount) as amount,sum(diol.amountMoney) as totalMoney) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm,DepotSupplier as ds where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and ds.id = dim.supplierId and dim.status = '已结算'";
		//String hql = "select new Map(sum(diol.amount) as amount,sum(diol.amountMoney) as totalMoney) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and dim.status = '已结算'";
		String hql = "select new Map(sum(diol.amount) as amount,sum(diol.amountMoney) as totalMoney) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dim.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(supplierId) && null != supplierId){
			hql += " and dim.supplierId =:supplierId";
		}
		//hql += " order by diol.inOutputCode";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(supplierId) && null != supplierId){
			query.setParameter("supplierId", supplierId);
		}
		List summaryList = new ArrayList();
		summaryList = query.list();
		return summaryList;
	}

	//按入库单编号查询入库单详细信息
	public List selectDetailList(List list) {
		
		String inoutCode = "";
		Iterator it = list.iterator();
		List detailList = new ArrayList();
		while(it.hasNext()){
			inoutCode = (String)it.next();
			String hql = "select materialId,amount,amountMoney,unitPrice from DepotInOutputList where type2 = '1' and inOutputCode = :inOutputCode";
			Query query = this.getSession().createQuery(hql);
			query.setParameter("inOutputCode", inoutCode);
			detailList = query.list();
		}
		return detailList;
	}

	//按材料编号查询数量和金额
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

	//查询所有的入库明细信息
	public List selectAllDetail(InputDepotManageForm idmf) {
		
		String inoutCode = GlobalMethod.NullToSpace(idmf.getInoutCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String materName = GlobalMethod.NullToSpace(idmf.getMaterName());
		String materCode = GlobalMethod.NullToSpace(idmf.getMaterCode());
		//String supplierId = "";
		//int supplierid = 0;
		String supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId());
		//因为有具体的时间要先把开始日期减1结束日期加1
		String se = "";
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		//获取类型下所有的子类型的代码
		String dmtId = GlobalMethod.NullToSpace(idmf.getDmtId());
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
		
		/*if(idmf.getSupplierId() != null && idmf.getSupplierId() != 0){
			supplierId = GlobalMethod.NullToSpace(idmf.getSupplierId().toString());
			supplierid = idmf.getSupplierId();
		}*/
		//String hql = "select new Map(diol.inOutputCode as inoutCode,diol.amountMoney as amountMoney,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,dim.man as man,dim.date as date,dim.supplierId as suplyName) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and dim.status = '已结算'";
		String hql = "select new Map(diol.inOutputCode as inoutCode,diol.amountMoney as amountMoney,dm.name as name,dm.code as code,dm.spec as spec,diol.amount as amount,diol.unitPrice as unitPrice,dim.man as man,dim.date as date,dim.supplierId as suplyName,sc.codeName as unit) from DepotInOutputList as diol,DepotInputManager as dim,DepotMaterialCopy as dm, SysCode as sc where diol.type2 = '1' and diol.inOutputCode = dim.code and diol.materialId = dm.id and sc.codeValue = dm.unit";
		if(!types.equals("")){
			hql += " and dm.type in("+types+")";
		}
		if(!"".equals(inoutCode) && null != inoutCode){
			hql += " and diol.inOutputCode like '%"+inoutCode+"%'";
		}
		if(!"".equals(startDate) && null != startDate){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(endDate) && null != endDate){
			hql += " and dim.date <= :endDate";
		}
		if(!"".equals(materName) && null != materName){
			hql += " and dm.name like '%"+materName+"%'";
		}
		if(!"".equals(materCode) && null != materCode){
			hql += " and dm.code like '%"+materCode+"%'";
		}
		if(!"".equals(supplierId) && null != supplierId){
			hql += " and dim.supplierId =:supplierId";
		}
		hql += " order by dim.id desc";
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && null != startDate){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			query.setParameter("endDate", se);
		}
		if(!"".equals(supplierId) && null != supplierId){
			query.setParameter("supplierId", supplierId);
		}
		List allDetailList = query.list();
		return allDetailList;
	}

	//查询所有的入库信息
	public List selectAllInput(InputDepotManageForm idmf) {
		
		String inputCode = GlobalMethod.NullToSpace(idmf.getInputCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		//因为有具体的时间要先把开始日期减1结束日期加1
		String se = "";
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(dim.id as id,dim.code as inputCode,dim.date as inputDate,dim.man as man,dim.money as money,dim.invoiceCode as invoiceCode,dim.status as status,dim.supplierId as supplierName) from DepotInputManager as dim where 1=1 ";
		
		if(!"".equals(inputCode) && inputCode != null){
			hql += " and dim.code like '%"+inputCode+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxInvoiceCode()).equals("")){
			hql += " and dim.invoiceCode like '%"+idmf.getCxInvoiceCode()+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxStatus()).equals("")){
			hql += " and dim.status = '"+idmf.getCxStatus()+"'";
		}
		if(!"".equals(startDate) && startDate != null){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(endDate) && endDate != null){
			hql += " and dim.date <= :endDate";
		}
		
		hql += " order by dim.id desc";
		
		Query query = this.getSession().createQuery(hql);
		
		if(!"".equals(startDate) && startDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate != null){
			query.setParameter("endDate", se);
		}
		
		List allInputList = query.list();
		
		return allInputList;
	}

	@Override
	public List summary(InputDepotManageForm idmf) {

		String inputCode = GlobalMethod.NullToSpace(idmf.getInputCode());
		String startDate = GlobalMethod.NullToSpace(idmf.getStartDate());
		String endDate = GlobalMethod.NullToSpace(idmf.getEndDate());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(idmf.getLpId()));
		//因为有具体的时间要先把开始日期减1结束日期加1
		String ss = "";
		String se = "";
		if(!"".equals(startDate) && null != endDate){
			ss = GlobalMethod.minDate(startDate);
		}
		if(!"".equals(endDate) && null != endDate){
			se = GlobalMethod.addDate(endDate);
		}
		
		String hql = "select new Map(sum(dim.money) as totalMoney) from DepotInputManager as dim where 1=1 ";
		//String hql = "select dim from DepotInputManager dim left join DepotSupplier ds on dim.supplierId = ds.id";
		if(!"".equals(inputCode) && inputCode != null){
			hql += " and dim.code like '%"+inputCode+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxInvoiceCode()).equals("")){
			hql += " and dim.invoiceCode like '%"+idmf.getCxInvoiceCode()+"%'";
		}
		if(!GlobalMethod.NullToSpace(idmf.getCxStatus()).equals("")){
			hql += " and dim.status = '"+idmf.getCxStatus()+"'";
		}
		if(!"".equals(startDate) && startDate != null){
			hql += " and dim.date >= :startDate";
		}
		if(!"".equals(lpId) && lpId != null){
			hql += " and dim.lpId ="+lpId;
		}
		
		if(!"".equals(endDate) && endDate != null){
			hql += " and dim.date <= :endDate";
		}
		Query query = this.getSession().createQuery(hql);
		if(!"".equals(startDate) && startDate != null){
			query.setParameter("startDate", startDate);
		}
		if(!"".equals(endDate) && endDate != null){
			query.setParameter("endDate", se);
		}
		List inputList =  query.list();
		return inputList;
	}

}
