package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CompactRoomForm;
import com.zc13.bkmis.mapping.AnalysisClientComeGo;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactQuitBill;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.msmis.mapping.SysCode;

/**
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：17:16:01 PM
 */
public interface ICustomerRoomDAO extends BasicDAO {
   
	// 查询合同列表 
	public List<Compact> getCompactList(String key,String currentpage,String code,String pagesize,String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws DataAccessException;
	
	//查询合同条数
	public List<Compact> getCount(String clientName,String roomCode,String beginDate,String endDate,String lpId,String isNotice) throws DataAccessException;
	
	// 查询客户列表 
	public List<CompactClient> getClientList(String currentpage,String pagesize,CompactRoomForm customForm ) throws DataAccessException;
	
	//查询客户条数
	public List<CompactClient> getClientCount(CompactRoomForm compactRoomForm) throws DataAccessException;
	
	//根据lpId得到收费账套id
	public CAccounttemplate getAccounttemplateId(int lpId) throws DataAccessException;
	
	//根据楼盘id和收费类型id得到账套
	public List<CCoststandard> getAccounttemplate(int id,int costTypeId,String type) throws DataAccessException;
	
	//得到最后保存的客户id
	public Integer getObjectId(String obj) throws DataAccessException;
	
	//得到最大的客户code
	public String getCompactCode(Integer lpId) throws DataAccessException;
	
	//得到最大的客户code
	public String getCustomerCode(Integer lpId) throws DataAccessException;

	//根据合同id找到对应的客户
	public CompactClient getClientByCompId(int id) throws DataAccessException;

	/**
	 * 根据合同id得到租金列表
	 */
	public List<CompactRent> getRentList(int id) throws DataAccessException;
	
	/**
	 * 根据客户id和合同id查询由该合同对应的房间id
	 * @param id 客户id
	 * @param compactId  合同id
	 */
	public List<ERoomClient> getRoomListByClientId(int id,int compactId) throws DataAccessException;
	
	//根据合同id得到收费标准列表
	public List<CompactRoomCoststandard> getStandardListByCompactId(int id) throws DataAccessException;
	
	//根据合同id得到房租信息列表
	public List<CompactRent> getCompactRent(int id) throws DataAccessException;
	
	//根据合同id得到收费标准列表
	public List<CompactRoomCoststandard> getCompactRoomCoststandard(int id) throws DataAccessException;
	
	//根据客户id得到房间客户列表
	public List<ERoomClient> getERoomClient(int id) throws DataAccessException;
	
	public List<ERoomClient> getERoomClientByPactId(int id) throws DataAccessException;
	
	/**根据客户id得到合同 */
	public List<Compact> getCompactByClientId(int id) throws DataAccessException;
	
	/**根据客户id得到房间客户列表*/
	public List<AnalysisClientComeGo> getComeGo(int clientId,int lpId) throws DataAccessException;
	
	/**得到当前楼盘的所有所需参数客户列表*/
	public List<SysCode> getSysCode(String obj,Integer lpId) throws DataAccessException;
	
	/**根据客户编号得到客户信息*/
	public List<CompactClient> getClient(String code) throws DataAccessException;

	public String getQuitCode() throws DataAccessException;
	
	/* 根据楼盘查询房间 */
	public List<ERooms> queryRoomforLp(Integer id,String tablename) throws DataAccessException;
	
	/* 根据楼栋查询房间 */
	public List<ERooms> queryRoomByBuild(Integer id,String tablename) throws DataAccessException ;
		
	//得到id最大的合同
	public int getLastCompact() throws DataAccessException;
	
	//根据合同id得到变更合同
	public CompactChange getChangeCompactByCId(int id) throws DataAccessException;
	
	//根据合同id得到续租合同合同
	public CompactRelet getReletCompactByCId(int id) throws DataAccessException;
	
	//查询当日迁出合同
	public List<Compact> getOutCompactList() throws DataAccessException;
	
	//删除迁出时保存的账单id记录
	public void delCompactBill(String pactId) throws DataAccessException;
	
	//查询迁出时生成的账单id
	public List<CompactQuitBill> getBillIds(String pactId) throws DataAccessException;
	
	//修改账单最后生成日期
	public void updateLastBuildDate(Integer id,String lastBuildDate)throws DataAccessException;
	
	//查询客户名称，导出报表 
	public List<CompactClient> explorCustomerRoomList(CompactRoomForm compactRoomForm) throws DataAccessException;
	
	//导出入驻管理信息报表 
	public List<Compact> explorCustomerRoomList1(String lpId,String clientName,String roomCode,String status,String beginDate,String endDate) throws DataAccessException;

	/**
	 * 设置指定合同的状态为未提交审批
	 * @param id
	 * @throws DataAccessException
	 * Date:Apr 18, 2011 
	 * Time:4:09:04 PM
	 */
	public void cancelChecked4Compact(String id)throws DataAccessException;

	/**
	 * 设置合同续租表中的状态为未提交审批
	 * @param id
	 * @throws DataAccessException
	 * Date:Apr 18, 2011 
	 * Time:4:23:07 PM
	 */
	public void cancelChecked4Relet(String id)throws DataAccessException;

	/**
	 * 设置合同变更表中的状态为未提交审批
	 * @param id
	 * @throws DataAccessException
	 * Date:Apr 18, 2011 
	 * Time:4:23:46 PM
	 */
	public void cancelChecked4Change(String id)throws DataAccessException;

	/**
	 * 设置意向表的状态为未提交审批
	 * @param id
	 * @throws DataAccessException
	 * Date:May 13, 2011 
	 * Time:2:59:18 PM
	 */
	public void cancelChecked4CompactIntention(String id)throws DataAccessException;
	
	/**
	 * 得到最大的迁出code
	 * @param lpId
	 * @return
	 * @throws DataAccessException
	 * Date:2012-12-13 
	 * Time:下午9:01:24
	 */
	public String getQuitCode(Integer lpId) throws DataAccessException;
}
