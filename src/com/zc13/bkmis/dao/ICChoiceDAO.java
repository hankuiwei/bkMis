/**
 * Administrator
 */
package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CChoiceForm;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.EBuilds;

/**
 * @author zhaosg Date：Dec 7, 2010 Time：2:26:39 PM
 */
public interface ICChoiceDAO extends BasicDAO {

	public List getTreeList(CChoiceForm choiceForm) throws DataAccessException;

	public List getList(Integer standardId, Integer lpId, Integer buildId,
			String floor,String clientName) throws DataAccessException;

	public List getListPact(Integer standardId, Integer lpId, Integer buildId,
			String floor,String clientName) throws DataAccessException;

	public CCoststandard getCStandard(Integer standardId)
			throws DataAccessException;

	public List<EBuilds> getBuilds(Integer lpId) throws DataAccessException;
	
	public List getClientList(CChoiceForm choiceForm)throws DataAccessException;
}
