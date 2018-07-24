package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.exception.BkmisServiceException;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IRepairService extends IBasicService {
	
	/**
	 * 查询报修项目列表 
	 * @param repairForm
	 * @param isPage 是否分页
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<SerMaintainProject> getRepairList(RepairForm repairForm,boolean isPage) throws BkmisServiceException;
	
	public List<ELp> getElpList() throws BkmisServiceException;
	
	public List<EBuilds> buildList(int id) throws BkmisServiceException;
	
	public List<ERooms> roomList(int id) throws BkmisServiceException;
	
	public int getCountList(RepairForm repairForm) throws BkmisServiceException;
	
	public void addRepair(RepairForm form) throws BkmisServiceException;
	
	public SerMaintainProject getRepair(String id) throws BkmisServiceException;
	
	public void delRepair(String id,String userName) throws BkmisServiceException;
	
	public void editRepair(RepairForm form) throws BkmisServiceException;
}
