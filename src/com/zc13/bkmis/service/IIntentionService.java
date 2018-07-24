package com.zc13.bkmis.service;

import com.zc13.bkmis.form.IntentionForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 意向书service
 * @author wangzw
 * @Date May 11, 2011
 * @Time 10:48:37 AM
 */
public interface IIntentionService {

	/**
	 * 得到所有意向书列表
	 */
	public void getList(IntentionForm intentionForm) throws BkmisServiceException;

	/**
	 * 获得意向书数量
	 * @param intentionForm
	 */
	public int getTotalCount(IntentionForm intentionForm) throws BkmisServiceException;

	/**
	 * 根据id查询出来的意向书的状态是否都为未提交审批或审批不通过的（用来判断是否可以删除或编辑）
	 * @param id 意向书id字符串
	 */
	public boolean checkIntentionStatus(String id) throws BkmisServiceException;

	/**
	 * 获得意向书编号
	 * @return
	 */
	public String getIntentionCode(IntentionForm intentionForm) throws BkmisServiceException;

	/**
	 * 保存意向书及相关信息
	 * @param intentionForm
	 * Date:May 12, 2011 
	 * Time:3:48:59 PM
	 */
	public void saveIntention(IntentionForm intentionForm) throws BkmisServiceException;
	
	/**
	 * 获得意向书详情
	 * @param form
	 * @throws BkmisServiceException
	 * Date:May 13, 2011 
	 * Time:6:32:26 AM
	 */
	public void getIntentionDetails(IntentionForm form) throws BkmisServiceException;

	/**
	 * 删除意向书
	 * @param intentionForm
	 * @param userName
	 * @throws BkmisServiceException
	 * Date:May 13, 2011 
	 * Time:9:40:49 AM
	 */
	public void delCompactIntention(IntentionForm intentionForm, String userName) throws BkmisServiceException;

	/**
	 * 审批意向书
	 * @param intentionForm
	 * Date:May 15, 2011 
	 * Time:3:27:47 PM
	 */
	public void approvalIntention(IntentionForm intentionForm) throws BkmisServiceException;

	/**
	 * 根据意向书id判断该意向书是否已缴定金(如果缴纳的定金为0，则作为已缴来处理)
	 * @param id
	 * @return
	 */
	public boolean checkIsIsEarnest(String id) throws BkmisServiceException;
	
}
