package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ERoomClient;

public interface IDestineDAO extends BasicDAO{

	/**
	 * 得到所有列表
	 * IDestineDAO.getList
	 */
	public void getList(DestineForm destineForm);
	
	public int getCount(DestineForm destineForm);
	
	
	public List<CompactClient> getClient(String code) throws DataAccessException;
	
	public List<CompactRent> getRentList(int id) throws DataAccessException;
	
	//根据客户id得到所租的房间列表
	public List<ERoomClient> getRoomListByClientId(int id,int compactId) throws DataAccessException;
	//根据合同id得到收费标准列表
	public List<CompactRoomCoststandard> getStandardListByCompactId(int id) throws DataAccessException;
	
	public List<CompactRent> getCompactRent(int id)throws DataAccessException; 
	
	public List<CompactRoomCoststandard> getCompactRoomCoststandard(int id)throws DataAccessException ;
	
	public List<ERoomClient> getERoomClient(int id)throws DataAccessException;
	/** 导出报表 **/
	public List<Compact> explorDestineList(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate,String isEarnest) throws DataAccessException;
}
