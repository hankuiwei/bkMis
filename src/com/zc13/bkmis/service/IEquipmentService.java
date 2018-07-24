package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.exception.BkmisServiceException;

public interface IEquipmentService {
	
	/**
	 * 获取本楼盘下的所有设备类型
	 * @return
	 * Date:2011-6-14 
	 * Time:下午05:23:01
	 */
	public List getEqpTypeList(Integer LpId) throws BkmisServiceException;
	
	/**
	 * 根据id或许当前类型的名称、编号
	 * @return
	 * @throws BkmisServiceException
	 * Date:2011-6-14 
	 * Time:下午06:21:35
	 */
	public List getTypeById(Integer typeID) throws BkmisServiceException;
	

}
