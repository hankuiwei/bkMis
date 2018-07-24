package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICostParaTypeDAO;
import com.zc13.bkmis.form.CostParaTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.service.ICostParaTypeService;
import com.zc13.exception.BkmisServiceException;
/**
 * 计费参数类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:05:57 PM
 */
public class CostParaTypeServiceImpl implements ICostParaTypeService{
	private ICostParaTypeDAO costParaTypeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	

	public ICostParaTypeDAO getCostParaTypeDAO() {
		return costParaTypeDAO;
	}

	public void setCostParaTypeDAO(ICostParaTypeDAO costParaTypeDAO) {
		this.costParaTypeDAO = costParaTypeDAO;
	}

	/**
	 * 获得计费参数类型列表
	 */
	public List<CCostparatype> getCostParaTypeList(CostParaTypeForm form) throws Exception {
		List<CCostparatype> list = null;
		try {
			list = costParaTypeDAO.getCostParaTypeList(form);
		} catch (Exception e) {
			logger.error("计费参数类型列表信息查询失败!CostParaTypeServiceImpl.getCostParaTypeList(obj)。详细信息：" + e.getMessage());
			throw new BkmisServiceException("计费参数类型列表信息查询失败!CostParaTypeServiceImpl.getCostParaTypeList(obj)。");
		}
		return list;
	}

	/**
	 * 删除计费参数类型
	 */
	public void deleteCostParaType(CostParaTypeForm form) throws Exception {
		if(form!=null&&form.getCheckbox()!=null){
			Integer[] checkbox = form.getCheckbox();
			for(int i = 0;i<checkbox.length;i++){
				CCostparatype cct = new CCostparatype();
				cct.setId(checkbox[i]);
				try {
					costParaTypeDAO.deleteCostParaType(cct);
				} catch (RuntimeException e) {
					logger.error("删除计费参数类型失败!CostParaTypeServiceImpl.deleteCostParaType(obj)。详细信息：" + e.getMessage());
					throw new BkmisServiceException("删除计费参数类型失败!CostParaTypeServiceImpl.deleteCostParaType(obj)。");
				}
			}
		}
	}

	/**
	 * 保存或更新计费参数类型
	 */
	public void saveOrUpdateCostParaType(CostParaTypeForm form) throws Exception {
		CCostparatype cct = new CCostparatype();
		try {
			//将form中字段的值赋给CCostparatype对象对应的字段
			BeanUtils.copyProperties(cct, form);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		if(cct.getId()==null||cct.getId()==0){
			try {
				costParaTypeDAO.saveCostParaType(cct);
			} catch (DataAccessException e) {
				logger.error("计费参数类型信息添加失败!CostParaTypeServiceImpl.saveOrUpdateCostParaType()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("计费参数类型信息添加失败!CostParaTypeServiceImpl.saveOrUpdateCostParaType()。");
			}
		}else if(cct.getId().intValue()>0){
			try {
				costParaTypeDAO.updateCostParaType(cct);
			} catch (DataAccessException e) {
				logger.error("计费参数类型信息更新失败!CostParaTypeServiceImpl.saveOrUpdateCostParaType()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("计费参数类型信息更新失败!CostParaTypeServiceImpl.saveOrUpdateCostParaType()。");
			}
		}
		
	}

}
