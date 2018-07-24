package com.zc13.bkmis.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CompactManagerForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.exception.BkmisServiceException;

/**
 * @author qinyantao
 * Date：Dec 24, 2010
 * Time：15:16:33 AM
 */
public interface ICompactManagerService {

	// 查询合同列表 
	public List<Compact> getCompactList(CompactManagerForm form) throws BkmisServiceException;
	
	// 查询合同退租列表 
	public List<CompactQuit> getDelContractList(CompactManagerForm form) throws BkmisServiceException;
	
	// 查询合同续租列表 
	public List<CompactRelet> getContractList(CompactManagerForm form) throws BkmisServiceException;
	
	// 查询合同变更列表 
	public List<CompactChange> getCompactChangeList(CompactManagerForm form) throws BkmisServiceException;
	
	//得到合同总数
	public int getCount(CompactManagerForm form) throws BkmisServiceException;
	
	//得到合同续租总数
	public int getContractCount(CompactManagerForm form) throws BkmisServiceException;
	
	//得到合同变更总数
	public int getCompactChangeCount(CompactManagerForm form) throws BkmisServiceException;
	
	//得到合同退租总数
	public int getDelContractCount(CompactManagerForm form) throws BkmisServiceException;
	
	//得到合同退租总数
	public Compact getCompactById(String compactId) throws BkmisServiceException;
	
	//保存合同退租
	public void saveQuit(CompactManagerForm form2) throws BkmisServiceException;

	//编辑合同退租
	public Object[] goEditQuit(int id) throws BkmisServiceException;
	
	//编辑合同退租
	public void editQuit(CompactManagerForm form2) throws BkmisServiceException;

	//得到需要审批的合同列表
	public String getPassList(CompactManagerForm form1) throws BkmisServiceException;
	
	//得到需要校验的合同列表
	public String getCheckList(CompactManagerForm form1) throws BkmisServiceException;
	
	//审批合同
	public void applyQuit(CompactManagerForm form1) throws BkmisServiceException;
	
	//审批租赁合同
	public String applyCompact(CompactManagerForm form1) throws BkmisServiceException;
	
	//审批租赁合同
	public String checkQuit(int id) throws BkmisServiceException;
	
	//审批变更合同和续租合同
	public void applyCompact(String compactId,String id,String type,String flag,String userName) throws BkmisServiceException;
	
	/**
	 * 自动执行的方法，每天执行一次。有两个功能：1、判断合同是否快过期，是的话为任务提示信息amount加1；2、超出日期的合同，置为已过期。
	 * @throws BkmisServiceException
	 */
	public void setDead() throws BkmisServiceException;
		
	/**
	 * 为导出报表提供的查询
	 * CompactManagerDAOImpl.getCompactListForE
	 */
	public void getCompactListForE(CompactManagerForm form) throws DataAccessException;

	/**
	 * 删除合同
	 * @param form2
	 * @throws DataAccessException
	 * Date:May 5, 2011 
	 * Time:5:41:24 PM
	 */
	public void deleteCompact(CompactManagerForm form2) throws DataAccessException;
	
	/***
	 * 得到所有合同总数
	 * @param form
	 * @return
	 * @throws BkmisServiceException
	 * Date:2013-3-26 
	 * Time:下午10:04:31
	 */
	public int getAllCompactCount(CompactManagerForm form) throws BkmisServiceException;
	
	/**
	 * 查询合同列表 
	 * @param form
	 * @return
	 * @throws BkmisServiceException
	 * Date:2013-3-26 
	 * Time:下午10:04:38
	 */
	public List<Compact> getAllCompactList(CompactManagerForm form) throws BkmisServiceException;
}
