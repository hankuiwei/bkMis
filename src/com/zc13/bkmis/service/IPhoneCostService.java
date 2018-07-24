package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.PhoneCostForm;
import com.zc13.bkmis.mapping.CPhoneParameter;
import com.zc13.bkmis.mapping.CRegionCode;
import com.zc13.bkmis.mapping.CServiceProvider;
import com.zc13.bkmis.mapping.ECallInfo;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author wangzw
 * @Date Oct 10, 2011
 * @Time 4:23:12 PM
 */
public interface IPhoneCostService  extends IBasicService{
	
	/**
	 * 获取电话交换机的通话信息并保存到数据库中
	 * @param sDate 开始时间  格式为：yyyy-MM-dd
	 * @param eDate 结束时间  格式为：yyyy-MM-dd
	 * @throws BkmisServiceException
	 * Date:Nov 26, 2011 
	 * Time:4:41:52 PM
	 */
	public void storeCallInfo(String sDate,String eDate) throws BkmisServiceException;
	
	/**
	 * 每部电话在指定时间段内产生的总通话时长和总费用列表
	 * @param form
	 * @param isPage
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:11:47:21 AM
	 */
	public List getPhoneCostInfoList(PhoneCostForm form,boolean isPage) throws BkmisServiceException;
	
	public String test();

	/**
	 * 获取指定的电话号码的明细
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:7:55:44 PM
	 */
	public List getPhoneCostInfoDetails(PhoneCostForm form1, boolean isPage) throws BkmisServiceException;

	/**
	 * 获取运营商信息
	 * @param form1
	 * @param b
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:10:13:55 PM
	 */
	public List getServiceProviderInfo(PhoneCostForm form1, boolean b) throws BkmisServiceException;

	/**
	 * 保存供应商信息
	 * @param form1
	 * Date:Nov 13, 2011 
	 * Time:11:19:10 PM
	 */
	public void saveProvider(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 获取电话资费参数列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Nov 14, 2011 
	 * Time:12:03:41 AM
	 */
	public List getPhoneParameterList(PhoneCostForm form1, boolean b) throws BkmisServiceException;

	/**
	 * 保存资费信息
	 * @param form1
	 * Date:Nov 14, 2011 
	 * Time:12:50:08 AM
	 */
	public void savePhoneParameter(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 删除资费信息
	 * @param form1
	 * Date:Nov 14, 2011 
	 * Time:12:53:28 AM
	 */
	public void deletePhoneParameter(PhoneCostForm form1) throws BkmisServiceException;
	
	/**
	 * 获取区号列表
	 * @param form1
	 * @param b
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 23, 2011 
	 * Time:3:16:51 PM
	 */
	public List getRegionCodeList(PhoneCostForm form1, boolean b) throws BkmisServiceException;
	
	/**
	 * 保存区号信息
	 * @param form1
	 * Date:Nov 14, 2011 
	 * Time:12:50:08 AM
	 */
	public void saveRegionCode(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 删除区号信息
	 * @param form1
	 * Date:Nov 14, 2011 
	 * Time:12:53:28 AM
	 */
	public void deleteRegionCode(PhoneCostForm form1) throws BkmisServiceException;
	
	/**
	 * 获取区域名称
	 * @param phone
	 * @param regionCodeList 区域名称列表
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 26, 2011 
	 * Time:5:05:03 PM
	 */
	public String getAreaName(String phone,List<CRegionCode> regionCodeList) throws BkmisServiceException;
	
	/**
	 * 获取通话费用
	 * @param phone
	 * @param callTime
	 * @param phoneParameterList 资费参数列表
	 * @param serviceProviderList 运营商信息
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 26, 2011 
	 * Time:5:02:38 PM
	 */
	public double getCost(String phone,int callTime,List<CPhoneParameter> phoneParameterList,List<CServiceProvider> serviceProviderList) throws BkmisServiceException;

	/**
	 * 获取通话信息实例
	 * @param form1
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 23, 2011 
	 * Time:6:43:52 PM
	 */
	public ECallInfo getCallInfo(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 保存通话信息
	 * @param form1
	 * @throws BkmisServiceException
	 * Date:Nov 23, 2011 
	 * Time:9:23:56 PM
	 */
	public void saveCallInfo(PhoneCostForm form1) throws BkmisServiceException;
	
	/**
	 * 删除通话信息
	 * @param form1
	 * Date:Nov 14, 2011 
	 * Time:12:53:28 AM
	 */
	public void deleteCallInfo(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 手动将指定时间段内没有同步到数据库的话单同步进去
	 * @param form1
	 * @throws BkmisServiceException
	 * Date:Nov 26, 2011 
	 * Time:4:34:51 PM
	 */
	public void buildCallInfo(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 根据最新的资费标准更新指定时间段内的话费金额，如果未指定时间段，则默认更新所有
	 * @param form1
	 * Date:Nov 26, 2011 
	 * Time:4:36:22 PM
	 */
	public void updatePhoneCost(PhoneCostForm form1) throws BkmisServiceException;

	/**
	 * 根据最新的区号设置更新指定时间段内的地区名称，如果未指定时间段，则默认更新所有
	 * @param form1
	 * Date:Nov 26, 2011 
	 * Time:4:36:38 PM
	 */
	public void updateAreaName(PhoneCostForm form1) throws BkmisServiceException;
	
	/**
	 * 每个账单对应的通话记录和费用列表
	 * @param form
	 * @param isPage
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:11:47:21 AM
	 */
	public List getCallInfoList(PhoneCostForm form,boolean isPage) throws BkmisServiceException;
}
