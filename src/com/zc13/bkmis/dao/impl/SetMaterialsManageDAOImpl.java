package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.ISetMaterialsManageDAO;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：2:22:45 PM
 */
public class SetMaterialsManageDAOImpl extends BasicDAOImpl implements ISetMaterialsManageDAO {

	//获取树形图
	public List getMaterialsList() {
		String hql = "select dst from DepotMaterialType as dst";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}

	//查询要显示的信息
	/**
	 * luq
	 * 增加库存条件查询
	 */
	public List selectMaterials(SetMaterialsForm smf,boolean isPage) {
		
		String name = GlobalMethod.NullToSpace(smf.getName());
		String codeType = GlobalMethod.NullToSpace(smf.getType());
		String code = GlobalMethod.NullToSpace(smf.getCode());
		String noStockStart = GlobalMethod.NullToSpace(smf.getNowStockStart()).trim();
		String noStockEnd = GlobalMethod.NullToSpace(smf.getNowStockEnd()).trim();
		
		//判断是否为根节点
		int dmtId = smf.getDmtId();
		String Idhql = "from DepotMaterialType where parentid=:dmtId";
		Query idQuery = this.getSession().createQuery(Idhql);
		idQuery.setParameter("dmtId", dmtId);
		List idList = null;
		idList = idQuery.list();
		//查询材料
		String hql = "select new Map(dmt.name as typeName,dm.id as id,dm.code as materCode,dm.name as materName,dm.spec as spec,sc.codeName as codeName,dm.lowerLimit as lowerLimit,dm.upperLimit as upperLimit,dm.nowStock as nowStock,dm.money as money,dm.doStock as doStock,dm.unitPrice as unitPrice) from DepotMaterialType as dmt,DepotMaterial as dm,SysCode as sc where dmt.code = dm.type and dm.unit=sc.codeValue";
		if(null == idList || idList.size()<=0){
			if(!"".equals(codeType) && codeType != null && !"null".equals(codeType)){
				hql += " and dm.type=:type";
			}
		}else{
			hql += " and dmt.parentid=:dmtId";
		}
		if(!"".equals(name) && name != null ){
			hql += " and dm.name like '%"+name+"%'"; 
		}
		if(!"".equals(code) && code != null){
			hql += " and dm.code like '%"+code+"%'";
		}
		
		if(!"".equals(noStockStart) && noStockStart != null){
			hql += " and dm.nowStock >= " + noStockStart;
		}
		if(!"".equals(noStockEnd) && noStockEnd != null){
			hql += " and dm.nowStock <= " + noStockEnd;
		}
		
		hql += " order by dm.name,dm.code desc ";
		Query query = this.getSession().createQuery(hql);
		if(null == idList || idList.size()<=0){
			if(!"".equals(codeType) && codeType != null && !"null".equals(codeType)){
				query.setParameter("type", codeType);
			}
		}else{
			query.setParameter("dmtId", dmtId);
		}
		List materialList = null;
		if(!isPage){
			materialList = query.list();
		}else{
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1"))-1);
			materialList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}
		return materialList;
	}

	

	//查询存储单位
	public List selectUnit() {

		String hql = "from SysCode where codeType = :codeType";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("codeType", "unit");
		List unitList = null;
		unitList = query.list();
		return unitList;
	}

	//添加材料信息
	public void addMaterials(DepotMaterial dm) {
		// TODO Auto-generated method stub
		super.saveObject(dm);
	}

	//验证添加材料编号
	public DepotMaterial checkAddCode(String code) {

		String hql = "from DepotMaterial where code = :code";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return (DepotMaterial)query.uniqueResult();
	}

	//检验添加材料的验证
	public DepotMaterial checkAddName(String name) {

		String hql = "from DepotMaterial where name = :name";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("name", name);
		return (DepotMaterial)query.uniqueResult();
	}

	//按id查询出要修改的信息
	public List selectMaterialById(int id) {
		
		String hql = "from DepotMaterial where id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List materialList = query.list();
		return materialList;
	}

	//计算库存中现有金额的计算
	public double sumMoney(SetMaterialsForm smf) {
		
		int id = smf.getDmtId();
		String type = GlobalMethod.NullToSpace(smf.getType());
		String code = GlobalMethod.NullToSpace(smf.getCode());
		List nodeList = new ArrayList();
		nodeList = judgeNode(id);
		double sum = 0;
		String hql = "select sum(dm.money) from DepotMaterial as dm,DepotMaterialType as dmt where dmt.code = dm.type";
		if(nodeList.size()>0){
			hql += " and dmt.parentid=:dmtId";
		}else{
			if(type !="" && type != null && !"null".equals(type)){
				hql += " and dm.type like '%"+type+"%'";
			}
		}
		if(!"".equals(code) || null != code){
			hql += " and dm.code like '%"+code+"%'";
		}
		Query query = this.getSession().createQuery(hql);
		if(nodeList.size()>0){
			query.setParameter("dmtId", id);
		}
		List list = new ArrayList();
		list = query.list();
		if(null != list && list.size() > 0){ 
			if(null != list.get(0) && !"null".equals(list.get(0))){
				sum = (Double)list.get(0);
			}
		}
		return sum;
	}

	//根据id判断是不是根节点
	public List judgeNode(int id) {

		String hql = "from DepotMaterialType where parentid=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List infoList = null;
		infoList = query.list();
		return infoList;
	}

	//根据新插入的材料编号查出插入的材料信息
	public List selectMaterialByCode(String code) {
		
		String hql = "from DepotMaterial where code = :code";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("code", code);
		List copyList = new ArrayList();
		copyList = query.list();
		return copyList;
	}

	//按id查找要删除名称
	public List<DepotMaterial> selectMaterName(Integer[] idArray) {
		
		String hql = "from DepotMaterial where 1=1";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List materrNameList = this.getHibernateTemplate().find(hql, idArray);
		return materrNameList;
	}

	//按id查询材料的详细信息
	public List materialDetail(int id) {
		
		String hql = "select new Map(dmt.name as typeName,dm.id as id,dm.code as materCode,dm.name as materName,dm.spec as spec,sc.codeName as codeName,dm.lowerLimit as lowerLimit,dm.upperLimit as upperLimit,dm.nowStock as nowStock,dm.money as money,dm.doStock as doStock,dm.unitPrice as unitPrice,dm.bz as bz,dm.unit as unit) from DepotMaterialType as dmt,DepotMaterial as dm,SysCode as sc where dmt.code = dm.type and dm.unit=sc.codeValue and dm.id = :id ";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id", id);
		List materialdetailList = query.list();
		return materialdetailList;
	}

	//查询所有的材料信息
	public List selectAllMaterial(SetMaterialsForm smf) {
		
		String name = GlobalMethod.NullToSpace(smf.getName());
		String codeType = GlobalMethod.NullToSpace(smf.getType());
		String code = GlobalMethod.NullToSpace(smf.getCode());
		//判断是否为根节点
		int dmtId = smf.getDmtId();
		String Idhql = "from DepotMaterialType where parentid=:dmtId";
		Query idQuery = this.getSession().createQuery(Idhql);
		idQuery.setParameter("dmtId", dmtId);
		List idList = null;
		idList = idQuery.list();
		//查询所有材料
		String hql = "select new Map(dmt.name as typeName,dm.id as id,dm.code as materCode,dm.name as materName,dm.spec as spec,sc.codeName as codeName,dm.lowerLimit as lowerLimit,dm.upperLimit as upperLimit,dm.nowStock as nowStock,dm.money as money,dm.doStock as doStock,dm.unitPrice as unitPrice) from DepotMaterialType as dmt,DepotMaterial as dm,SysCode as sc where dmt.code = dm.type and dm.unit=sc.codeValue and dm.doStock <>'0.0' and dm.nowStock <> '0.0'";
		if(null == idList || idList.size()<=0){
			if(!"".equals(codeType) && codeType != null && !"null".equals(codeType)){
				hql += " and dm.type=:type";
			}
		}else{
			hql += " and dmt.parentid=:dmtId";
		}
		if(!"".equals(name) && name != null ){
			hql += " and dm.name like '%"+name+"%'"; 
		}
		if(!"".equals(code) && code != null){
			hql += " and dm.code like '%"+code+"%'";
		}
		
		hql += " order by dmt.name desc";
		Query query = this.getSession().createQuery(hql);
		if(null == idList || idList.size()<=0){
			if(!"".equals(codeType) && codeType != null && !"null".equals(codeType)){
				query.setParameter("type", codeType);
			}
		}else{
			query.setParameter("dmtId", dmtId);
		}
		List allMaterilList = query.list();
		return allMaterilList;
	}
}
