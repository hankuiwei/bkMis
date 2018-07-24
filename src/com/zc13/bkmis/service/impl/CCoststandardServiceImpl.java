package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.ICAccounttemplateDAO;
import com.zc13.bkmis.dao.ICCoststandardDAO;
import com.zc13.bkmis.dao.ICItemsDAO;
import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.form.CCoststandardForm;
import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.service.ICCoststandardService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.ExtendUtil;
import com.zc13.util.PageBean;

public class CCoststandardServiceImpl extends BasicServiceImpl implements
		ICCoststandardService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private ICCoststandardDAO icCoststandardDao;
	private ICItemsDAO icItemsDao;
	private ICAccounttemplateDAO icAccounttemplateDao;

	public ICCoststandardDAO getIcCoststandardDao() {
		return icCoststandardDao;
	}

	public void setIcCoststandardDao(ICCoststandardDAO icCoststandardDao) {
		this.icCoststandardDao = icCoststandardDao;
	}
	/**
	 * 收费标准查询
	 * CCoststandardServiceImpl.getCCoststandardList
	 */
	public PageBean getCCoststandardList(CCoststandardForm form) throws Exception {
		if (ExtendUtil.checkNull(form.getPagesize())) {
			form.setPagesize(5);
		}
		int pageSize = form.getPagesize();
		PageBean page = new PageBean(pageSize);
		try {
			page = icCoststandardDao.getCCoststandards(form);
			CAccounttemplateForm account = new CAccounttemplateForm();
			account.setLpId(form.getLpId());
			List ztList = icAccounttemplateDao.getCAccounttemplateList(account);//帐套
			CItemsForm itemsform = new CItemsForm();
			itemsform.setLpId(form.getLpId());
			List<CItems> itemsList = icItemsDao.getItemsList(itemsform,false);//收费项目
			List<CCosttype> fylxList = icCoststandardDao.getCostTypeList(form);//费用类型
			List<CCostparatype> ctypeParas = icCoststandardDao.getCostParaType();//计费参数
			form.setZtList(ztList);
			form.setItemsList(itemsList);
			form.setFylxList(fylxList);
			form.setCtypeParas(ctypeParas);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("收费标准查询失败!CCoststandardServiceImpl.getCCoststandardList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费标准查询失败!CCoststandardServiceImpl.getCCoststandardList()。");
		}
		return page;
	}
	/**
	 * 收费标准保存
	 * CCoststandardServiceImpl.saveCCoststandard
	 */
	public void saveCCoststandard(CCoststandardForm form) throws Exception{
		CCoststandard standard = form.getStandard();
		try {
			if (standard.getId() == null || standard.getId().intValue() == 0) {
				icCoststandardDao.saveObject(standard);
			} else {
				icCoststandardDao.updateObject(standard);
			}
		} catch (Exception e) {
			logger.error("收费标准保存失败!CCoststandardServiceImpl.saveCCoststandard()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费标准保存失败!CCoststandardServiceImpl.saveCCoststandard()。");
		}
	}
	/**
	 * 收费标准删除
	 * CCoststandardServiceImpl.deleteCCoststandard
	 */
	public void deleteCCoststandard(CCoststandardForm form) throws Exception{
		Integer[] id = form.getCheckbox();
		try {
			if (id!=null) {
				for (int i = 0; i < id.length; i++) {
					CCoststandard item = new CCoststandard();
					item.setId(id[i]);
					icCoststandardDao.deleteCCoststandard(item);
				}
			}
		} catch (Exception e) {
			logger.error("收费标准删除失败!CCoststandardServiceImpl.deleteCCoststandard()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费标准删除失败!CCoststandardServiceImpl.deleteCCoststandard()。");
		}
	}

	public ICItemsDAO getIcItemsDao() {
		return icItemsDao;
	}

	public void setIcItemsDao(ICItemsDAO icItemsDao) {
		this.icItemsDao = icItemsDao;
	}

	public ICAccounttemplateDAO getIcAccounttemplateDao() {
		return icAccounttemplateDao;
	}

	public void setIcAccounttemplateDao(ICAccounttemplateDAO icAccounttemplateDao) {
		this.icAccounttemplateDao = icAccounttemplateDao;
	}
	
}
