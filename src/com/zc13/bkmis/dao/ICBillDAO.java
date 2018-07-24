/**
 * Administrator
 */
package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CBillForm;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CTemporal;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.util.PageBean;

/**
 * @author Administrator
 * Date：Dec 20, 2010
 * Time：3:47:29 PM
 */
public interface ICBillDAO extends BasicDAO {
	public PageBean getList(CBillForm formbean,List<Integer> clients)throws DataAccessException;
	
	public List<CBill> getExcelList(CBillForm formbean, List<Integer> clients)throws DataAccessException;
	
//	public List getStandard(Integer lpId, Integer id)throws DataAccessException;
	
	public List getClientList()throws DataAccessException;
	
	public List getBillDates()throws DataAccessException;
	
	public List<CBill> getBillList(CBillForm formbean)throws DataAccessException;
	
	public void updateBill(CBill bill)throws DataAccessException;
	
	public void updateBillNum(CBill bill)throws DataAccessException;
	
	public CTemporal geTemporal(Integer clientId)throws DataAccessException;
	
	public int updateTemporal(CTemporal temporal)throws DataAccessException;
	
	public int updateAdvance(CAdvance advance)throws DataAccessException;
	/**2018年2月27日16:19:27 gd */
	public int updateAdvanceWuYF(CAdvanceWuYF advance)throws DataAccessException;
	
	public CAdvance getAdvance(Integer clientId)throws DataAccessException;
	/**2018年2月27日16:19:27 gd */
	public CAdvanceWuYF getAdvanceWuYF(Integer clientId)throws DataAccessException;
	public CItems getItemsByName(String value) throws DataAccessException;
	
	
	public CDeposit getDeposit(Integer clientId,String depositType)throws DataAccessException;
	
	public PageBean getRefundList(CBillForm formbean)throws DataAccessException;
	
	public List getRefundBillList(CBillForm billForm)throws DataAccessException;
	
	public List getChargeList(CBillForm billForm)throws DataAccessException;
	
//	public PageBean getElectricity(CBillForm formbean) throws DataAccessException;
//	
//	public List<CBill> getExcelList1(CBillForm formbean) throws DataAccessException;
	
	public PageBean getDeposit(CBillForm formbean)throws DataAccessException;
	
	public void returnDeposit(CBillForm formbean)throws DataAccessException;
	
	public void updateDeposit(CDeposit deposit)throws DataAccessException;

	public List getAllChargeList(CBillForm formbean)throws DataAccessException;
	
	public PageBean queryCollect(CBillForm formbean) throws DataAccessException;
	
	public void updateEarnest(CEarnest earnest)throws DataAccessException;
	
	public CEarnest getEarnest(Integer clientId)throws DataAccessException;
	
	public void updateCompact(Integer clientId)throws DataAccessException;
	
	public List<Compact> queryCompact(Integer clientId)throws DataAccessException;
	
	public List<Compact> queryCompact(List<CompactClient> clients)throws DataAccessException;
	
	public CItems getItems(String value) throws DataAccessException;
	
	public Integer maxBillId()throws DataAccessException;
	
	public List<CBill> queryBill(Integer[] id)throws DataAccessException;
	
	public List<CCharge> queryCharge(Integer[] id)throws DataAccessException;
	
	public List<Integer> queryClientId(Integer lpid)throws DataAccessException;
	
	public PageBean queryClient(CBillForm billForm);
	
	public List<CompactClient> getClients(CBillForm billForm);
	
	public double[] getCost(Integer clientId);

	/**
	 * 更新意向书为已缴定金
	 * @param clientId
	 * @throws DataAccessException
	 * Date:May 16, 2011 
	 * Time:2:09:53 PM
	 */
	public void updateCompactIntention(Integer clientId)throws DataAccessException;

	/**
	 * 获得现有的最大的收据号
	 * @return
	 * @throws DataAccessException
	 * Date:May 25, 2011 
	 * Time:4:37:43 AM
	 */
	public String getMaxBillNum()throws DataAccessException;

	/**
	 * 查询当前正在入住的客户
	 * @param billForm
	 * @return
	 * @throws DataAccessException
	 * Date:Jun 21, 2011 
	 * Time:4:09:39 PM
	 */
	public PageBean queryCurrentInClient(CBillForm billForm) throws DataAccessException;
	
	/**
	 * 查询财务使用的房租信息
	 * @param billForm
	 * @return
	 * Date:Jun 30, 2012 
	 * Time:1:25:27 AM
	 */
	public PageBean queryRentInfo(CBillForm billForm);
	
	/**
	 * 查询财务使用的房租信息用于导出excel
	 * @param billForm
	 * @return
	 * Date:Jul 2, 2012 
	 * Time:9:04:01 AM
	 */
	public List queryRentInfoExportExcel(CBillForm billForm);
}
