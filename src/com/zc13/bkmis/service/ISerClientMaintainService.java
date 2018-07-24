package com.zc13.bkmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.EMaintainDispatch;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.mapping.SerMaterialConsume;
import com.zc13.exception.BkmisServiceException;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface ISerClientMaintainService extends IBasicService {
	
	/**
	 * 查询报修列表
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<SerClientMaintain> getClientList(SerClientMaintainForm form,boolean isPage) throws BkmisServiceException;
	
	public int getCountList(SerClientMaintainForm form) throws BkmisServiceException;
	
	public void addClient(SerClientMaintainForm form) throws BkmisServiceException;
	
	public void dealClient(HttpServletRequest request,SerClientMaintainForm form) throws BkmisServiceException;
	
	public DepotMaterial selectMaterialById(String id) throws BkmisServiceException;
	
	public String getDostack(String code);
	
	public void editClient(SerClientMaintainForm form) throws BkmisServiceException;
	
	public void saveConsume(HttpServletRequest request, SerClientMaintainForm form) throws BkmisServiceException;
	
	public void delete(SerClientMaintain bean) throws BkmisServiceException;
	
	public List<SerMaterialConsume> getConsume(String id) throws BkmisServiceException;
	
	public SerClientMaintain getClientMaintainById(String id,String forward) throws BkmisServiceException;
	
	/**
	 * 查询材料出处列表
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<SerMaterialConsume> queryConsume(SerClientMaintainForm form,boolean isPage) throws BkmisServiceException;

	//查询材料出处,用于导出报表
	public List<SerMaterialConsume> explorMaterialList(String department,String materialName,String begin,String end) throws BkmisServiceException;

	/**
	 * 设置到场时间
	 * @param form2
	 */
	public void setReachTime(SerClientMaintainForm form2)throws BkmisServiceException;

	/**
	 * 设置离场时间
	 * @param form2
	 */
	public void setLeaveTime(SerClientMaintainForm form2)throws BkmisServiceException;

	/**
	 * 发送短信
	 * @param phones 手机号码
	 * @param contents 短信内容
	 * @throws BkmisServiceException
	 */
	public void sendMessage(SerClientMaintainForm form)throws BkmisServiceException;

	/**
	 * 根据报修id获得客户报修的状态，有效状态和派工状态
	 * @param maintainId
	 * @return
	 * @throws BkmisServiceException
	 */
	public String getRepairAndDispatchStatusByMaintainId(String maintainId)throws BkmisServiceException;

	/**
	 * 将客户报修置为无效
	 * @param maintainId
	 * @throws BkmisServiceException
	 */
	public void setInvalid(String maintainId,String userName)throws BkmisServiceException;

	/**
	 * 根据报修id获得派工人员列表
	 * @param id
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<EMaintainDispatch> getDispatchListByMaintainId(String id)throws BkmisServiceException;
	
	/**
	 * 程序生成新编号
	 * @return
	 * Date:Mar 5, 2012 
	 * Time:10:18:58 AM
	 */
	public String getNewCode();
	
	/**
	 * 处理执行减少领料操作
	 * */
	public void updateDepotMaterial(String bh,String sl);
	
	
}
