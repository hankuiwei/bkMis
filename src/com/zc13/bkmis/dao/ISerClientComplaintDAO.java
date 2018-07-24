package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.SerClientComplaintForm;
import com.zc13.bkmis.mapping.SerClientComplaint;

public interface ISerClientComplaintDAO  extends BasicDAO {
	
	/**
	 * 查询客户投诉
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<SerClientComplaint> getClientList(SerClientComplaintForm form,boolean isPage) throws DataAccessException;
}
