package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.SerMaintainProject;

public interface IRepairDAO  extends BasicDAO {
	
	/**
	 * 查询报修项目列表
	 * @param repairForm
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<SerMaintainProject> getRepairList(RepairForm repairForm,boolean isPage) throws DataAccessException;
	
	public List<EBuilds> buildList(int id) throws DataAccessException;
	
	public List<ERooms> roomList(int id) throws DataAccessException;
}
