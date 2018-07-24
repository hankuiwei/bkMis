package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.exception.BkmisServiceException;

public interface IDestineService {

	/**
	 * 得到所有预定用户列表
	 * IDestineService.getList
	 */
	public void getList(DestineForm destineForm);
	/**
	 * 获取总条数
	 * IDestineService.getTotalCount
	 */
	public int getTotalCount(DestineForm destineForm);
	/**
	 * 保存房间和租金
	 * IDestineService.saveRoomRent
	 */
	public void saveRoomRent(DestineForm destineForm ) throws BkmisServiceException;
	
	/**
	 *  保存合同,保存各信息，包括房租、合同、客户、以及他们之间的关系
	 * IDestineService.saveCompact
	 */
	public Integer saveCompact(DestineForm form) throws BkmisServiceException;
	/**
	 * 得到合同详情
	 * IDestineService.getCompactMassage
	 */
	public void getCompactMassage(DestineForm form2,String id) throws BkmisServiceException;
	/**
	 * 编辑合同信息
	 * @param form
	 * @param compactId1
	 * @throws BkmisServiceException
	 * Date:May 6, 2011 
	 * Time:10:11:02 AM
	 */
	public void editCompact(DestineForm form,String compactId1) throws BkmisServiceException ;
	
	/**
	 * 正式入住
	 * IDestineService.inCome
	 */
	public void inCome(String compactId);
	
	/** 导出报表 **/
	public List<Compact> explorDestineList(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate,String isEarnest ) throws BkmisServiceException;
		
}
