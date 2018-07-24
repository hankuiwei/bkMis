package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.SMSForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 短信服务service
 * @author wangzw
 * @Date Jul 6, 2011
 * @Time 10:37:55 AM
 */
public interface ISMSService  extends IBasicService{

	/**
	 * 查询短信发送队列
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 */
	List getSMSList(SMSForm form1, boolean isPage) throws BkmisServiceException;
	
	/**
	 * 群发短讯
	 * @param form
	 * @throws BkmisServiceException
	 * Date:Aug 26, 2012 
	 * Time:10:45:37 AM
	 */
	public void sendMessage(SMSForm form) throws BkmisServiceException;
}
