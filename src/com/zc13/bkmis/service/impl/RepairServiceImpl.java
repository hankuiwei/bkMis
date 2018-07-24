package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IRepairDAO;
import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.bkmis.service.IRepairService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class RepairServiceImpl extends BasicServiceImpl implements
		IRepairService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IRepairDAO IRepairDAO;


	public IRepairDAO getIRepairDAO() {
		return IRepairDAO;
	}

	public void setIRepairDAO(IRepairDAO repairDAO) {
		IRepairDAO = repairDAO;
	}

	/**
	 * 查询
	 */
	public List<SerMaintainProject> getRepairList(RepairForm repairForm,boolean isPage)
			throws BkmisServiceException {
		List<SerMaintainProject> list = null;
		try{
//			list = IRepairDAO.getRepairList(currentpage,pagesize,name);
			//list = IRepairDAO.getRepairList(repairForm,true);2013-02-21 进行注释，客户反应在报修项目如果有11个时再填写报修信息时不能都出来分析原因是分页影响
			list = IRepairDAO.getRepairList(repairForm, isPage);
		}catch(DataAccessException e){
			logger.error("客户报修查询失败!iCustomerRepairDAO.getCustomerRepairList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("客户报修查询失败!iCustomerRepairDAO.getCustomerRepairList()。");
		}
		return list;
	}

	//查询客户报修列表总条数
	@Override
	public int getCountList(RepairForm repairForm) throws BkmisServiceException {
		List<SerMaintainProject> list= IRepairDAO.getRepairList(repairForm,false);
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}
	}

	//添加客户报修项目
	@Override
	public void addRepair(RepairForm form) throws BkmisServiceException {
		SerMaintainProject obj = new SerMaintainProject();
		try {
			BeanUtils.copyProperties(obj, form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(obj.getBz()==null){
			obj.setBz("");
		}
		IRepairDAO.saveObject(obj);
		//添加系统日志
		try {
			IRepairDAO.logDetail(form.getUserName(),Contants.ADD,"报修项目",Contants.L_SERVICE,"2",obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//根据id得到客户报修
	@Override
	public SerMaintainProject getRepair(String id) throws BkmisServiceException {
		return (SerMaintainProject)IRepairDAO.getObject(SerMaintainProject.class,Integer.parseInt(id));
	}

	//根据id删除客户报修
	@Override
	public void delRepair(String id,String userName) throws BkmisServiceException {
		SerMaintainProject bean = (SerMaintainProject)IRepairDAO.getObject(SerMaintainProject.class,Integer.parseInt(id));

		try {
			IRepairDAO.logDetail(userName, Contants.DELETE, "报修项目",Contants.L_SERVICE, "3", bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IRepairDAO.deleteObject(bean);
	}

	//根据id编辑客户报修
	@Override
	public void editRepair(RepairForm form) throws BkmisServiceException {
		
		SerMaintainProject obj = new SerMaintainProject();
		try {
			BeanUtils.copyProperties(obj, form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//写入系统日志
		try {
			IRepairDAO.logDetail(form.getUserName(), Contants.MODIFY,"报修项目",Contants.L_SERVICE, "1", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IRepairDAO.updateObject(obj);
	}
	
	//得到楼盘列表
	@Override
	public List<ELp> getElpList() throws BkmisServiceException {
		return IRepairDAO.getObjects(ELp.class);
	}
	
	//得到楼盘id得到楼幢列表
	@Override
	public List<EBuilds> buildList(int id1) throws BkmisServiceException {
		List<EBuilds> list = IRepairDAO.buildList(id1);
		return list;
	}
	
	//根据局楼幢id得到房间列表
	@Override
	public List<ERooms> roomList(int id1) throws BkmisServiceException {
		List<ERooms> list = IRepairDAO.roomList(id1);
		return list;
	}

}
