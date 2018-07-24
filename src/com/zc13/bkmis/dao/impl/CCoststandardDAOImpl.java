package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICCoststandardDAO;
import com.zc13.bkmis.form.CCoststandardForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.PageBean;

public class CCoststandardDAOImpl extends BasicDAOImpl implements
		ICCoststandardDAO {

	public List getCCoststandardList(String typeName, String accountid)
			throws DataAccessException {
		String hql = "select bz,zt,xm,t from CCoststandard bz ,CAccounttemplate zt,CItems xm,CCosttype t where bz.display is null and bz.accountTemplateId=zt.id and bz.itemId=xm.id and bz.costTypeId=t.id";
		if (typeName != null && !"".equals(typeName)) {
			hql += " and t.costTypeName like :typeName";
		}
		if (accountid != null && !"".equals(accountid)) {
			hql += " and zt.id = :accountid";
		}
		Query query = this.getSession().createQuery(hql);
		if (typeName != null && !"".equals(typeName)) {
			query.setParameter("typeName", "%" + typeName + "%");
		}
		if (accountid != null && !"".equals(accountid)) {
			query.setParameter("id", accountid);
		}
		List list = query.list();
		return list;
	}

	/**
	 * 分页
	 */
	public PageBean getCCoststandards(CCoststandardForm form) throws DataAccessException {
		String hql = "select bz,zt,xm,t from CCoststandard bz ,CAccounttemplate zt,CItems xm,CCosttype t where bz.display is null and bz.accountTemplateId=zt.id and bz.itemId=xm.id and bz.costTypeId=t.id";
		if (form.getTypeName() != null && !"".equals(form.getTypeName())) {
			hql += " and bz.standardName like :typeName";
		}
		if (form.getAccountid() != null && !"".equals(form.getAccountid())) {
			hql += " and bz.accountTemplateId = :accountid";
		}
		if(form.getLpId()!=null&&form.getLpId()!=0){
			hql += " and bz.lpId="+form.getLpId();
		}
		hql += " order by bz.id ";
		Query query = this.getSession().createQuery(hql);
		if (form.getTypeName() != null && !"".equals(form.getTypeName())) {
			query.setParameter("typeName", "%" + form.getTypeName() + "%");
		}
		if (form.getAccountid() != null && !"".equals(form.getAccountid())) {
			query.setParameter("accountid", Integer.decode(form.getAccountid()));
		}
		PageBean page = this.queryFy(query, form.getPagination(), form.getPagesize());
		return page;
	}

	public void saveCCoststandard(CCoststandard item)
			throws DataAccessException {
		super.saveObject(item);
	}

	public void updateCCoststandard(CCoststandard item)
			throws DataAccessException {
		super.updateObject(item);
	}

	public void deleteCCoststandard(CCoststandard item)
			throws DataAccessException {
		super.deleteObject(item);
	}

	public List<SysCode> getSysCodeList(String codeType)
			throws DataAccessException {
		String hql = "from SysCode where codeType = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, codeType);
		List<SysCode> list = query.list();
		return list;
	}
	/**
	 * 帐套
	 * CCoststandardDAOImpl.getCAccounttemplateList
	 */
	public List<CAccounttemplate> getCAccounttemplateList()
			throws DataAccessException {
		String hql = " from CAccounttemplate ";
		Query query = this.getSession().createQuery(hql);
		List<CAccounttemplate> list = query.list();
		return list;
	}
	/**
	 * 
	 * CCoststandardDAOImpl.getAllGauge
	 */
	public List getAllGauge() throws DataAccessException {
		String hql = "select b,sc from CCosttypeGauge b,SysCode sc where b.gaugeId = sc.codeValue and sc.codeType='gaugeType'";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}
	/**
	 * 计费参数--费用类型
	 * CCoststandardDAOImpl.getAllCostParaType
	 */
	public List getAllCostParaType() throws DataAccessException {
		String hql = "select p,t from CCosttypeCostparatype p,CCostparatype t where p.costParaTypeId=t.id ";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}
	/**
	 * 计费参数
	 * CCoststandardDAOImpl.getCostParaType
	 */
	public List<CCostparatype> getCostParaType() throws DataAccessException {
		String hql = "from CCostparatype ";
		Query query = this.getSession().createQuery(hql);
		List<CCostparatype> list = query.list();
		return list;
	}
	/**
	 * 费用类型
	 * CCoststandardDAOImpl.getCostTypeList
	 */
	public List<CCosttype> getCostTypeList(CCoststandardForm form) throws DataAccessException {
		String hql = "from CCosttype where 1=1 ";
		if(form.getLpId()!=null&&form.getLpId()!=0){
			hql += " and lpId="+form.getLpId();
		}
		Query query = this.getSession().createQuery(hql);
		List<CCosttype> list = query.list();
		return list;
	}

}
