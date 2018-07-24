package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.CompactRoomForm;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactTask;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author qinyantao
 * Date：Dec 16, 2010
 * Time：17:16:33 AM
 */
public interface ICustomerRoomService {

	/** 查询合同列表 **/
	public List<Object[]> getCompactList(String key,String currentpage,String code,String pagesize,String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws BkmisServiceException;
	
	/** 查询客户列表 **/
	public List<CompactClient> getClientList(String currentpage,String pagesize,CompactRoomForm customForm) throws BkmisServiceException;
	
	/**根据合同id找到合同**/
	public Compact getCompactById(String id) throws BkmisServiceException;
	
	/**根据合同找到客户**/
	public CompactClient getCompactClientById(Compact compact) throws BkmisServiceException;
	
	/**查询合同条数**/
	public int getCount(String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws BkmisServiceException;
	
	public int getClientCount(CompactRoomForm compactRoomForm) throws BkmisServiceException;
	
	/**得到合同编号**/
	public String getCompactCode(Integer lpId) throws BkmisServiceException;
	
	/**得到客户编号**/
	public String getCustomerCode(Integer lpId) throws BkmisServiceException;
	
	/**得到费用类型列表**/
	public List<CCosttype> getCostType() throws BkmisServiceException;
	
	/**得到收费标准列表**/
	public String costStandList(String lpId,String costType,String str,String lpIds,String type) throws BkmisServiceException;
	
	/**得到收费标准列表**/
	public ERooms getRoomById(String roomId) throws BkmisServiceException;
	
	/** 根据房间名称和费用类型名称和收费标准名称 **/
	public String getNames(String roomId,String costtype,String costStand) throws BkmisServiceException;
	
	/** 保存合同 **/
	public void saveCompact(CompactRoomForm form2,String flag) throws BkmisServiceException;
	
	/** 保存客户 **/
	public Integer saveCustomer(CompactRoomForm form2) throws BkmisServiceException;
	
	/**保存房间和租金**/
	public void saveRoomRent(CompactRoomForm form,String flag) throws BkmisServiceException;
	
	/**根据客户id删除客户**/
	public void delClientById(String ids) throws BkmisServiceException;
	
	/**编辑房间和租金**/
	public void editCompact(CompactRoomForm form,String compactId,String clientId) throws BkmisServiceException;
	
	/**编辑客户**/
	public void editClientById(CompactRoomForm form) throws BkmisServiceException;
	
	/**编辑客户**/
	public CompactClient getClientById(String id) throws BkmisServiceException;
	
	/**根据合同号得到合同信息*/
	public void getCompactMassage(CompactRoomForm form2,String id,String flag) throws BkmisServiceException;
	
	/**根据合同号得到合同信息**/
	public String checkClientById(String ids) throws BkmisServiceException;
	
	/**根据客户id得到客户信息**/
	public String getClientString(String id) throws BkmisServiceException;
	
	/**查询所有客户**/
	public List<CompactClient> getAllClientList(Integer lpId) throws BkmisServiceException;
	
	/**得到楼盘列表**/
	public List<ELp> getElp() throws BkmisServiceException;
	
	/**得到房间列表**/
	public List<ERooms> getRoomByLp(String id,String tablename) throws BkmisServiceException;
	
	/**得到续租合同**/
	public void saveCompactRelet(CompactRoomForm form) throws BkmisServiceException;
	
	/**保存变更合同**/
	public void saveCompactChange(CompactRoomForm form) throws BkmisServiceException;

	public Map<String, List<SysCode>> getSysCode(Integer lpId) throws BkmisServiceException;

	public String getQuitCode() throws BkmisServiceException;
	
	/**提交审批**/
	public String applyCompact(String id,String id1,String flag,String userName) throws BkmisServiceException;
	
	/**检验合同状态**/
	public String checkCompactStatus(String id) throws BkmisServiceException;
	
	/**删除合同**/
	public void delCompact(String id,String userName) throws BkmisServiceException;
	
	/**客户迁出**/
	public void outRoom(String id,String endDate,String userName) throws BkmisServiceException;
	
	/**反悔迁出**/
	public void returnOutRoom(String id) throws BkmisServiceException;

	/**查询当日迁出合同**/
	public List<Compact> getOutCompactList()throws BkmisServiceException;
	
	/**保存客户迁出的生成的账单id**/
	public void saveCompactBill(List list,String pactId)throws BkmisServiceException;
	/**
	 * 通知入驻
	 * ICustomerRoomService.notice
	 */
	public void notice(Integer compactId)throws BkmisServiceException;
	/**
	 * 确定正式迁出
	 * ICustomerRoomService.notice
	 */
	public void enterToGo(String compactId)throws BkmisServiceException;
	/**
	 * 得到工作任务单详情
	 * ICustomerRoomService.getTask
	 */
	public CompactTask getTask(Integer compactId)throws BkmisServiceException;
	/**
	 * 保存工作任务单
	 * ICustomerRoomService.saveTask
	 */
	public void saveTask(CompactRoomForm form1 )throws BkmisServiceException;
	/** 查询客户名称，导出报表 **/
	public List<CompactClient> explorCustomerRoomList(CompactRoomForm compactRoomForm) throws BkmisServiceException;
	/**导出入驻管理信息报表**/
	public List<Compact> explorCustomerRoomList1(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate) throws BkmisServiceException;
	/**
	 * 得到相应编号
	 * CustomerRoomServiceImpl.getCompactCode
	 */
	public String getTheCode(String beginCode,String code) throws BkmisServiceException;
	/**
	 * 得到某客户的历史住房记录
	 * @param clientId
	 * @return
	 * @throws BkmisServiceException
	 * Date:Mar 22, 2011 
	 * Time:7:10:10 PM
	 */
	public List getClientsHistoryRooms(String clientId)throws BkmisServiceException;

	/**
	 * 取消提交（取消校验）
	 * @param id
	 * @param type
	 * Date:Apr 18, 2011 
	 * Time:11:23:02 AM
	 */
	public void cancelChecked(String id, String type)throws BkmisServiceException;
	
	/**得到迁出编号**/
	public String getQuitCode(Integer lpId) throws BkmisServiceException;

}
