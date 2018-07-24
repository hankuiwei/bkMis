package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CCoststandardForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.PageBean;

public interface ICCoststandardDAO  extends BasicDAO {
	
	public List getCCoststandardList(String typeName,String accountid) throws DataAccessException;
	
	public PageBean getCCoststandards(CCoststandardForm form) throws DataAccessException;
	
	public void saveCCoststandard(CCoststandard item) throws DataAccessException;
	
	public void updateCCoststandard(CCoststandard item) throws DataAccessException;
	
	public void deleteCCoststandard(CCoststandard item) throws DataAccessException;
	
	public List<SysCode> getSysCodeList(String codeType) throws DataAccessException ;
	
	public List<CAccounttemplate> getCAccounttemplateList() throws DataAccessException;
	
	public List getAllGauge() throws DataAccessException;
	
	public List getAllCostParaType() throws DataAccessException;
	
	public List<CCostparatype> getCostParaType() throws DataAccessException;
	
	public List<CCosttype> getCostTypeList(CCoststandardForm form) throws DataAccessException;
	
}
