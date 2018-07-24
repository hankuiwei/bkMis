package com.zc13.bkmis.service.impl;

import java.util.List;

import com.zc13.bkmis.dao.IEquipmentDAO;
import com.zc13.bkmis.service.IEquipmentService;
import com.zc13.exception.BkmisServiceException;

public class EquipmentServiceImpl implements IEquipmentService{

	IEquipmentDAO equipmentDAO;

	public IEquipmentDAO getEquipmentDAO() {
		return equipmentDAO;
	}

	public void setEquipmentDAO(IEquipmentDAO equipmentDAO) {
		this.equipmentDAO = equipmentDAO;
	}

	@Override
	public List getEqpTypeList(Integer lpId) throws BkmisServiceException{
		// TODO Auto-generated method stub
		String hql = "from EqType ";
		if(lpId!=null && lpId !=0){
			hql += " where lpId = "+lpId;
		}
		return equipmentDAO.findObject(hql);
	}

	@Override
	public List getTypeById(Integer typeID) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		return equipmentDAO.findObject("from EqType where id ="+typeID);
	}
}
