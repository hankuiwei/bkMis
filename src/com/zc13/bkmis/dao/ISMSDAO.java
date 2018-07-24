package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.SMSForm;

/**
 * 短信服务DAO
 * @author wangzw
 * @Date Jul 6, 2011
 * @Time 10:41:13 AM
 */
public interface ISMSDAO  extends BasicDAO{

	/**
	 * 查询短信队列
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	List querySMSList(SMSForm form1, boolean isPage) throws DataAccessException;

}
