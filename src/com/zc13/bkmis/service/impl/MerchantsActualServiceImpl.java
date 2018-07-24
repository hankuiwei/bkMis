package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IMerchantsActualDAO;
import com.zc13.bkmis.form.MerchantsActualForm;
import com.zc13.bkmis.mapping.MerchantsActual;
import com.zc13.bkmis.service.IMerchantsActualService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;

/*******************************************************************************
 * @author 李影
 */
public class MerchantsActualServiceImpl extends BasicServiceImpl implements
		IMerchantsActualService {
	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsActualDAO imerchantsActualDao;

	public IMerchantsActualDAO getImerchantsActualDao() {
		return imerchantsActualDao;
	}

	public void setImerchantsActualDao(IMerchantsActualDAO imerchantsActualDao) {
		this.imerchantsActualDao = imerchantsActualDao;
	}

	@Override
	public List<MerchantsActual> getList(
			MerchantsActualForm merchantsActualForm, boolean isPage)
			throws BkmisServiceException {

		List<MerchantsActual> list = null;
		try {
			list = imerchantsActualDao.queryActualResult(merchantsActualForm,
					isPage);
		} catch (Exception e) {
			logger
					.error("招商计划信息查询失败!MerchantsActualServiceImpl.getList()。详细信息："
							+ e.getMessage());
			throw new BkmisServiceException(
					"招商计划信息查询失败!MerchantsActualServiceImpl.getList()。");
		}
		return list;
	}

	public int queryCounttotal(MerchantsActualForm merchantsActualForm) {
		return imerchantsActualDao.queryCounttotal(merchantsActualForm);
	}

	// 根据id得到招商计划
	@Override
	public MerchantsActual getMerchantsActual(String id)
			throws BkmisServiceException {
		return (MerchantsActual) imerchantsActualDao.getObject(
				MerchantsActual.class, Integer.parseInt(id));
	}

	// 保存客户报修
	@Override
	public void addClient(MerchantsActualForm form)
			throws BkmisServiceException {
		MerchantsActual obj = new MerchantsActual();
		try {
			BeanUtils.copyProperties(obj, form);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		try {
			imerchantsActualDao.saveObject(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据id编辑招商计划信息
	@Override
	public void editMerchantsActual(MerchantsActualForm form)
			throws BkmisServiceException {

		MerchantsActual obj = new MerchantsActual();
		try {
			BeanUtils.copyProperties(obj, form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		imerchantsActualDao.updateObject(obj);
	}

	// 根据id删除招商计划信息
	@Override
	public void delMerchantsActual(String id) throws BkmisServiceException {
		MerchantsActual bean = (MerchantsActual) imerchantsActualDao.getObject(
				MerchantsActual.class, Integer.parseInt(id));
		imerchantsActualDao.deleteObject(bean);
	}

	public List<MUser> getUserList() throws Exception {
		return imerchantsActualDao.getObjects(MUser.class);
	}

	/**
	 * 查询计划分配人
	 */
	@Override
	public List<SysCode> queryCodeByType() {
		
		return imerchantsActualDao.queryCodeByType();
	}

}
