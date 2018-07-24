package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.ELp;

public interface ICAccounttemplateDAO extends BasicDAO {
	
	public List getCAccounttemplateList(CAccounttemplateForm account) throws DataAccessException;
	
	public List<ELp> getLp(CAccounttemplateForm account) throws DataAccessException;
	
	public CAccounttemplate getById(Integer id) throws DataAccessException;

}
