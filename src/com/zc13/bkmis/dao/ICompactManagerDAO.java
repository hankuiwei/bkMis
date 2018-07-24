package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CompactManagerForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;

/**
 * @author 秦彦韬
 * Date：Dec 24, 2010
 * Time：15:16:01 PM
 */
public interface ICompactManagerDAO extends BasicDAO {
   
	// 查询合同列表 
	public List<Compact> getCompactList(CompactManagerForm form) throws DataAccessException;
	
	//查询合同总数
	public List<Compact> getCount(CompactManagerForm form) throws DataAccessException;
	
	// 查询合同续租列表 
	public List<CompactRelet> getContractList(CompactManagerForm form) throws DataAccessException;
	
	// 查询合同变更列表 
	public List<CompactChange> getCompactChangeList(CompactManagerForm form) throws DataAccessException;
	
	//查询合同续租总数
	public List<CompactChange> getCompactChangeCount(CompactManagerForm form) throws DataAccessException;
	
	//查询合同变更总数
	public List<CompactRelet> getContractCount(CompactManagerForm form) throws DataAccessException;
	
	//查询合同退租列表
	public List<CompactQuit> getDelContractList(CompactManagerForm form) throws DataAccessException;
	
	//查询合同退租总数
	public List<CompactQuit> getDelContractCount(CompactManagerForm form) throws DataAccessException;
	
	//根据客户id和押金类型查询押金
	public CDeposit getDeposit(int id,String depositType) throws DataAccessException;
	
	//根据客户id查询预收款
	public CAdvance getCAdvance(int id) throws DataAccessException;
	/**gd 2018年3月3日11:39:05 */
	//根据客户id查询预收物业费
	public CAdvanceWuYF getCAdvanceWuYF(int clientId);
	
	//查定金
	public CEarnest getCEarnest(int id) throws DataAccessException; 
	
	//查询合同退租总数
	public List<CompactQuit> getQuitByCompactId(int id) throws DataAccessException;
	/**
	 * 返回已入住的结束日期=date的那些合同
	 * @param date 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Compact> getAtTermList(String date) throws DataAccessException;
	/**
	 * 更新任务提示表的合同即将到期提示任务数量
	 * @param count 正数或负数
	 * @throws DataAccessException
	 */
	public void updateTask(int count) throws DataAccessException;
	/**
	 * 为导出报表提供的查询
	 * CompactManagerDAOImpl.getCompactListForE
	 */
	public List<Compact> getCompactListForE(CompactManagerForm form) throws DataAccessException;

	/**
	 * 返回所有结束日期=date的那些合同
	 * @param todayDate
	 * @return
	 * Date:May 6, 2011 
	 * Time:4:49:33 AM
	 */
	public List<Compact> getAtTermList2(String todayDate) throws DataAccessException;

	/**
	 * 获得意向书列表
	 * @param form
	 * @return
	 * @throws DataAccessException
	 * Date:May 13, 2011 
	 * Time:2:44:02 PM
	 */
	public List<CompactIntention> getIntentionList(CompactManagerForm form) throws DataAccessException;
	
	/**
	 * 获取所有合同
	 * @param form
	 * @return
	 * @throws DataAccessException
	 * Date:2013-3-26 
	 * Time:下午10:14:12
	 */
	public List<Compact> getAllCompactList(CompactManagerForm form) throws DataAccessException;
	
	/**
	 * 获取所有合同总数
	 * @param form
	 * @return
	 * @throws DataAccessException
	 * Date:2013-3-26 
	 * Time:下午10:14:24
	 */
	public List<Compact> getAllCompactCount(CompactManagerForm form) throws DataAccessException;

	
}
