package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.PhoneCostForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author wangzw
 * @Date Oct 10, 2011
 * @Time 4:24:42 PM
 */
public interface IPhoneCostDAO  extends  BasicDAO{
	
	/**
	 * 获取电话号码对应的通话信息
	 * @param form
	 * @return
	 * Date:Nov 13, 2011 
	 * Time:11:52:44 AM
	 */
	public List getPhoneCostInfo(PhoneCostForm form);

	/**
	 * 获取房间电话信息列表
	 * @param form
	 * @return
	 * Date:Nov 13, 2011 
	 * Time:3:56:35 PM
	 */
	public List getRoomPhoneInfo(PhoneCostForm form);
	
	/**
	 * 获取通话记录信息
	 * @param form
	 * @return
	 * Date:Nov 13, 2011 
	 * Time:7:58:19 PM
	 */
	public List getCallInfo(PhoneCostForm form,boolean isPage);

	/**
	 * 获取运营商信息
	 * @param form1
	 * @param b
	 * @return
	 * Date:Nov 13, 2011 
	 * Time:10:57:52 PM
	 */
	public List getServiceProviderInfo(PhoneCostForm form1, boolean b);

	/**
	 * 获取电话资费标准列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Nov 14, 2011 
	 * Time:12:05:12 AM
	 */
	public List getPhoneParameterInfo(PhoneCostForm form1, boolean b);
	
	/**
	 * 获取区号列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Nov 14, 2011 
	 * Time:12:05:12 AM
	 */
	public List getRegionCodeInfo(PhoneCostForm form1, boolean b);

	/**
	 * 获取通话记录对象列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Nov 26, 2011 
	 * Time:5:12:35 PM
	 */
	public List getCallInfoObj(PhoneCostForm form1, boolean b);
	
	/**
	 * 每个账单对应的通话记录和费用列表
	 * @param form
	 * @param isPage
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:11:47:21 AM
	 */
	public List getCallInfoList(PhoneCostForm form,boolean isPage);
}
