package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.IntentionForm;
import com.zc13.bkmis.mapping.ERoomIntention;
import com.zc13.bkmis.mapping.IntentionRent;
import com.zc13.bkmis.mapping.IntentionRoomCoststandard;
/**
 * 意向书dao
 * @author wangzw
 * @Date May 11, 2011
 * @Time 10:49:13 AM
 */
public interface IIntentionDAO extends BasicDAO{

	/**
	 * 查询意向书列表
	 * @param intentionForm
	 * @param isPage 是否分页
	 * @throws DataAccessException
	 * Date:May 11, 2011 
	 * Time:3:52:49 PM
	 */
	public void queryList(IntentionForm intentionForm,boolean isPage)throws DataAccessException;

	/**
	 * 得到意向书的最大编号
	 * @return
	 * @throws DataAccessException
	 * Date:May 11, 2011 
	 * Time:5:05:08 PM
	 */
	public String getIntentionCode()throws DataAccessException;

	/**
	 * 根据意向书id获得对应的房租信息
	 * @param id 意向书id
	 * @return
	 * @throws DataAccessException
	 * Date:May 13, 2011 
	 * Time:6:38:56 AM
	 */
	public List<IntentionRent> getRentList(Integer id)throws DataAccessException;

	/**
	 * 根据客户id和意向书id获得房间信息
	 * @param clientId  客户id
	 * @param intentionId 意向书id
	 * @return
	 * Date:May 13, 2011 
	 * Time:6:44:26 AM
	 */
	public List<ERoomIntention> getRoomListByClientId(Integer clientId, Integer intentionId) throws DataAccessException;

	/**
	 * 根据意向书id获取意向书对应的收费标准信息
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * Date:May 13, 2011 
	 * Time:6:55:57 AM
	 */
	public List<IntentionRoomCoststandard> getStandardListByIntentionId(Integer id) throws DataAccessException;
	
}
