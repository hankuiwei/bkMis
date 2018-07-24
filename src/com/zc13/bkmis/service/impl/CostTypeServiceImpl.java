package com.zc13.bkmis.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICostTypeDAO;
import com.zc13.bkmis.form.CostTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.service.ICostTypeService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;
/**
 * 费用类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:05:57 PM
 */
public class CostTypeServiceImpl implements ICostTypeService{
	private ICostTypeDAO costTypeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public ICostTypeDAO getCostTypeDAO() {
		return costTypeDAO;
	}

	public void setCostTypeDAO(ICostTypeDAO costTypeDAO) {
		this.costTypeDAO = costTypeDAO;
	}

	/**
	 * 获得费用类型列表
	 */
	public List<CCosttype> getCostTypeList(CostTypeForm form) throws Exception {
		List<CCosttype> list = null;
		try {
			list = costTypeDAO.getCostTypeList(form);
		} catch (Exception e) {
			logger.error("费用类型列表信息查询失败!CostTypeServiceImpl.getCostTypeList(obj)。详细信息：" + e.getMessage());
			throw new BkmisServiceException("费用类型列表信息查询失败!CostTypeServiceImpl.getCostTypeList(obj)。");
		}
		return list;
	}

	/**
	 * 删除费用类型
	 */
	public void deleteCostType(CostTypeForm form) throws Exception {
		if(form!=null&&form.getCheckbox()!=null){
			Integer[] checkbox = form.getCheckbox();
			for(int i = 0;i<checkbox.length;i++){
				CCosttype cct = new CCosttype();
				cct.setId(checkbox[i]);
				try {
					costTypeDAO.deleteCostType(cct);
				} catch (RuntimeException e) {
					logger.error("删除费用类型失败!CostTypeServiceImpl.deleteCostType(obj)。详细信息：" + e.getMessage());
					throw new BkmisServiceException("删除费用类型失败!CostTypeServiceImpl.deleteCostType(obj)。");
				}
			}
		}
	}

	/**
	 * 保存或更新费用类型
	 */
	public void saveOrUpdateCostType(CostTypeForm form) throws Exception {
		CCosttype cct = new CCosttype();
		try {
			//将form中字段的值赋给CCosttype对象对应的字段
			BeanUtils.copyProperties(cct, form);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		Set<CCostparatype> costparatypes = new HashSet<CCostparatype>();//保存该费用类型拥有的所有计费参数类型对象
		Integer[] costparatypeIds = form.getCostparatypeIds();//得到页面上被选中的计费参数类型id数组
		if(costparatypeIds!=null){
			for(int i = 0;i<costparatypeIds.length;i++){
				CCostparatype ccp = new CCostparatype();
				ccp.setId(costparatypeIds[i]);
				costparatypes.add(ccp);//使用id创建对象并保存到set中
			}
		}
		cct.setCostparaTypes(costparatypes);//给CCosttype添加收费参数类型对象列表
		if(cct.getId()==null||cct.getId()==0){
			try {
				costTypeDAO.saveCostType(cct);
			} catch (DataAccessException e) {
				logger.error("费用类型信息添加失败!CostTypeServiceImpl.saveOrUpdateCostType()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("费用类型信息添加失败!CostTypeServiceImpl.saveItems()。");
			}
		}else if(cct.getId().intValue()>0){
			try {
				costTypeDAO.updateCostType(cct);
			} catch (DataAccessException e) {
				logger.error("费用类型信息更新失败!CostTypeServiceImpl.saveOrUpdateCostType()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("费用类型信息更新失败!CostTypeServiceImpl.saveItems()。");
			}
		}
		
	}

	/**
	 * 获得计费参数类型列表
	 */
	public List<CCostparatype> getCostparatypeList() throws Exception {
		List<CCostparatype> list = null;
		try {
			list = costTypeDAO.getCostparatypeList();
		} catch (Exception e) {
			logger.error("计费参数类型列表信息查询失败!CostTypeServiceImpl.getCostparatypeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("计费参数类型列表信息查询失败!CostTypeServiceImpl.getCostparatypeList()。");
		}
		return list;
	}

	/**
	 * 获得表具列表
	 */
	public List<SysCode> getGaugeList()  throws Exception{
		List<SysCode> list = null;
		try {
			list = costTypeDAO.getGaugeList();
		} catch (Exception e) {
			logger.error("表具列表信息查询失败!CostTypeServiceImpl.getGaugeList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具列表信息查询失败!CostTypeServiceImpl.getGaugeList()。");
		}
		return list;
	}
	
}
