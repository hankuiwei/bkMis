package com.zc13.msmis.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.bkmis.mapping.ViewTreeCode;
import com.zc13.bkmis.mapping.ViewTreeCodeId;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.form.SysParamForm;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.mapping.SysCodeType;
import com.zc13.util.GlobalMethod;

public class SysParamManagerDAOImpl extends BasicDAOImpl implements ISysParamManagerDAO {

	@Override
	public List getSysParam() throws DataAccessException {
		String sql = "from ViewTreeCode";
		Query query = getSession().createQuery(sql);
		List<ViewTreeCodeId> list = query.list();
		return list;
	}

	@Override
	public List<SysParamForm> getSysParam(int codeTypeId) throws DataAccessException {
		return null;
	}

	public SysCodeType getSysCodeType(String codeName)
			throws DataAccessException {
		
		SysCodeType code = new SysCodeType();
		String hql = "from SysCodeType c where c.codeTypeName = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, codeName);
		
		code = query.uniqueResult() == null ? null :(SysCodeType)query.uniqueResult();
		
		return code;
	}
	
	public SysCodeType getSysCodeType1(String codeValue)
	throws DataAccessException {
		
		SysCodeType code = new SysCodeType();
		String hql = "from SysCodeType c where c.codeTypeValue = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, codeValue);
		
		code = query.uniqueResult() == null ? null :(SysCodeType)query.uniqueResult();
		
		return code;
	}

	@Override
	public List<SysParamForm> getChoose(String typeValue) throws DataAccessException {
		List<SysParamForm> list = new ArrayList<SysParamForm>();
		String sql = "select ct.code_type_id,c.code_id,ct.code_type_name,c.code_value,c.code_name from sys_code as c,sys_code_type as ct where c.code_type = ct.code_type_value and ct.code_type_value = ?";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, typeValue);
		List<Object[]> result = query.list();       
		Iterator it = result.iterator();       
		while (it.hasNext()) { 
			Object[] obj = (Object[]) it.next();       
		    SysParamForm sysParamForm = new SysParamForm();
		    sysParamForm.setCodeTypeId(Integer.parseInt(obj[0].toString()));
		    sysParamForm.setCodeId(Integer.parseInt(obj[1].toString()));
		    sysParamForm.setCodeName(obj[4].toString());
		    sysParamForm.setCodeValue(obj[3] == null ? "":obj[3].toString());
		    sysParamForm.setTypeName(obj[2].toString());
			list.add(sysParamForm);  
		}
		return list;
	}
	@Override
	public List getTest() throws DataAccessException {
		String sql = "from ViewTreeCode";
		Query query = getSession().createQuery(sql);
		List<ViewTreeCodeId> list = query.list();
		return list;
	}

	@Override
	public SysCode getCodeByValue(String codeValue) throws DataAccessException {
		String hql = "from SysCode s where s.codeValue = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, codeValue);
		return (SysCode)query.uniqueResult();
	}

	@Override
	public SysCodeType getCodeTypeByValue(String codeTypeValue)
			throws DataAccessException {
		String hql = "from SysCodeType s where s.codeTypeValue = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, codeTypeValue);
		return (SysCodeType)query.uniqueResult();
	}

	@Override
	public SysCode getCodeByName(String code) throws DataAccessException {
		String hql = "from SysCode s where s.codeName = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, code);
		return (SysCode)query.uniqueResult();
	}

	@Override
	public List getSysCodeTypeByMinId() throws DataAccessException {
		
		String hql = "select min(sysCodeType.codeTypeId) from SysCodeType sysCodeType";
		Query query = this.getSession().createQuery(hql);
		List list = query.list();
		return list;
	}

	@Override
	/* houxj改*/
	public SysCode getPrama(String type, String name) throws DataAccessException {
		
		String hql = "from SysCode where codeType = 'size' and codeName = :name";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("name", name);
		SysCode sysCode = (SysCode)query.uniqueResult();
		return sysCode;
	}

	@Override
	public Integer getParentIdByViewId(Integer parentId) {
		StringBuffer hql = new StringBuffer("from ViewTreeCode where id.id=");
		hql.append(parentId);
		Query query = getSession().createQuery(hql.toString());
		List<ViewTreeCode> list = query.list();
		int id = 0;
		if(list!=null&&list.size()>0){
			id = list.get(0).getId().getPk();
		}
		return id;
	}

	@Override
	public List<SysCode> getSysCode(SysCode sysCode) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from SysCode where 1=1 ");
		if(sysCode!=null){
			if(sysCode.getCodeId()!=null&&sysCode.getCodeId()!=0){
				hql.append(" and codeId=").append(sysCode.getCodeId());
			}
			if(!GlobalMethod.NullToSpace(sysCode.getCodeType()).equals("")){
				hql.append(" and codeType='").append(sysCode.getCodeType()).append("'");
			}
			if(!GlobalMethod.NullToSpace(sysCode.getCodeName()).equals("")){
				hql.append(" and codeName='").append(sysCode.getCodeName()).append("'");
			}
			if(!GlobalMethod.NullToSpace(sysCode.getCodeValue()).equals("")){
				hql.append(" and codeValue='").append(sysCode.getCodeValue()).append("'");
			}
			if(sysCode.getRootUser()!=null&&sysCode.getRootUser()!=0){
				hql.append(" and rootUser=").append(sysCode.getRootUser());
			}
			if(sysCode.getLpId()!=null&&sysCode.getLpId()!=0){
				hql.append(" and lpId=").append(sysCode.getLpId());
			}
		}
		List<SysCode> list = this.getSession().createQuery(hql.toString()).list();
		return list;
	}

}
