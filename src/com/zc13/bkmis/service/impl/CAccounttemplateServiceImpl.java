package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.ICAccounttemplateDAO;
import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ICAccounttemplateService;
import com.zc13.exception.BkmisServiceException;

public class CAccounttemplateServiceImpl extends BasicServiceImpl implements
		ICAccounttemplateService {
	Logger logger = Logger.getLogger(this.getClass());

	private ICAccounttemplateDAO icAccounttemplateDao;

	public ICAccounttemplateDAO getIcAccounttemplateDao() {
		return icAccounttemplateDao;
	}

	public void setIcAccounttemplateDao(
			ICAccounttemplateDAO icAccounttemplateDao) {
		this.icAccounttemplateDao = icAccounttemplateDao;
	}

	/**
	 * 帐套查询 CAccounttemplateServiceImpl.getCAccounttemplateList
	 */
	public List getCAccounttemplateList(CAccounttemplateForm account) throws Exception {
		// TODO Auto-generated method stub
		List list = null;
		try {
			list = icAccounttemplateDao.getCAccounttemplateList(account);
		} catch (Exception e) {
			logger.error("收费帐套查询失败!getCAccounttemplateList()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException(
					"收费帐套查询失败!getCAccounttemplateList()。");
		}
		return list;
	}

	/**
	 * 帐套保存 CAccounttemplateServiceImpl.saveCAccounttemplate
	 */
	public void saveCAccounttemplate(CAccounttemplateForm form)
			throws Exception {
		CAccounttemplate items = form.getAccounttemplate();
		try {
			if (items.getId() == null || items.getId().intValue() == 0) {
				icAccounttemplateDao.saveObject(items);
			} else {
				icAccounttemplateDao.updateObject(items);
			}
		} catch (Exception e) {
			logger.error("收费帐套保存失败!.saveCAccounttemplate()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException("收费帐套保存失败!.saveCAccounttemplate()。");
		}
	}

	/**
	 * 帐套删除 CAccounttemplateServiceImpl.deleteCAccounttemplate
	 */
	public void deleteCAccounttemplate(CAccounttemplateForm form)
			throws Exception {
		Integer[] id = form.getCheckbox();
		try {
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					CAccounttemplate items = new CAccounttemplate();
					items.setId(id[i]);
					icAccounttemplateDao.deleteObject(items);
				}
			}
		} catch (Exception e) {
			logger.error("收费帐套删除失败!.deleteCAccounttemplate()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException(
					"收费帐套删除失败!.deleteCAccounttemplate()。");
		}
	}

	/**
	 * 楼盘查询 CAccounttemplateServiceImpl.getLpList
	 */
	public List<ELp> getLpList(CAccounttemplateForm account) throws Exception {
		List<ELp> list = icAccounttemplateDao.getLp(account);
		return list;
	}
}
