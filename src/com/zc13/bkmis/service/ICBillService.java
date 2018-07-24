/**
 * Administrator
 */
package com.zc13.bkmis.service;

import java.util.List;

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
import com.zc13.bkmis.mapping.ELp;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.util.DTree;
import com.zc13.util.PageBean;

/**
 * @author zhaosg
 * Date：Dec 20, 2010
 * Time：3:43:01 PM
 */
public interface ICBillService extends IBasicService {
	
	public PageBean getList(CBillForm formbean) throws Exception ;
	
	public List<CBill> getExcelList(CBillForm formbean)throws Exception;
	
//	public List getStandard(CBillForm formbean) throws Exception;
	
//	public void saveBill(CBillForm formbean)throws Exception;
	
//	public List<DTree> getClientList(CBillForm formbean)throws Exception;
	
	public void delete(CBillForm formbean) throws Exception;
	
	public List getBillDates()throws Exception;
	
	public List<DTree> getCilentTreeList() throws Exception;
	
	public List<CBill> getCBilList(CBillForm formbean)throws Exception;
	
	public List<CItems> getItemList()throws Exception;
	
	public CompactClient getClient(CBillForm formbean) throws Exception;
	
	public void updateBill(CBillForm formbean)throws Exception;
	
	public Integer saveCharge(CBillForm billForm, String id1, String id2, String id22, String id3_1,String id3_2, String id4)throws Exception;
	
	public CTemporal geTemporal(CBillForm formbean)throws Exception;
	
	public String updateTemporal(CBillForm formbean)throws Exception;
	
	public String updateAdvance(CBillForm formbean)throws Exception;
	public String updateAdvanceWuYF(CBillForm formbean)throws Exception;
	
	public void saveAdvanceDetail(CBillForm formbean)throws Exception;
	
	public CAdvance getAdvance(CBillForm formbean)throws Exception;
	/**2018年2月27日16:15:27 gd 
	 * 得到物业费 预付金 
	 * */
	public CAdvanceWuYF getAdvanceWuYF(CBillForm formbean)throws Exception;
	
	public CDeposit getCDeposit(CBillForm formbean,String depositType) throws Exception ;
	
	public String saveDeposit(CBillForm formbean,String depositType)throws Exception;
	
	public List<DTree> getRefundTreeList(CBillForm billForm) throws Exception;
	
	public PageBean getRefundList(CBillForm formbean)throws Exception;
	
	public List getRefundBillList(CBillForm billForm )throws Exception;
	
	public CCharge getCharge(CBillForm billForm)throws Exception;
	
	public List getNowCharge(CBillForm billForm)throws Exception;
	
	public void refund(CBillForm formbean) throws Exception;
	
	public void returnCharge(CBillForm billForm)throws Exception;
	
//	public void saveRefund(CBillForm form)throws Exception;
	
//	public PageBean getElectricity(CBillForm formbean)throws Exception;
//	
//	public List<CBill> getExcelList1(CBillForm formbean) throws Exception;
	
	public PageBean getDeposit(CBillForm formbean)throws Exception;
	
	public void returnDeposit(CBillForm formbean)throws Exception;

	public List<DTree> getClientTree4charge(String flag)throws Exception;

	public List getChargeList(CBillForm formbean)throws Exception;
	
	public List<MUser> getUserList()throws Exception;
	
	public PageBean queryCollect(CBillForm billForm)throws Exception;
	
	public CEarnest getEarnest(CBillForm formbean) throws Exception;
	
	public List<Compact> queryCompact(CBillForm billForm)throws Exception;
	
	public List<Compact> queryCompact(List<CompactClient> clients)throws Exception;
	
	public List<ELp> queryELp()throws Exception;
	
	public PageBean queryClient(CBillForm formbean) throws Exception ;
	
	public CItems getItems(String value);
	
	public List<CompactClient> getClients(CBillForm billForm);
	
	public double[] getCost(CBillForm billForm);
	
	public String saveEarnest(CBillForm formbean)throws Exception;
	
	/**
	 * 根据账单id获得账单对象
	 * @param billIds
	 * @return
	 */
	public List<CBill> getBillByIds(String billIds) throws Exception;
	
	/**
	 * 获得收据号
	 * @return
	 * @throws Exception
	 * Date:May 25, 2011 
	 * Time:4:35:45 AM
	 */
	public String getBillNum() throws Exception;

	/**
	 * 查询当前正在入住的客户
	 * @param billForm
	 * @return
	 * Date:Jun 21, 2011 
	 * Time:4:07:10 PM
	 */
	public PageBean queryCurrentInClient(CBillForm billForm) throws BkmisServiceException;
	
	/**
	 * @author zhaoyulong
	 * @param billForm
	 * @param id4 
	 * @return
	 * @throws BkmisServiceException
	 * Date:Mar 12, 2012 
	 * Time:7:37:32 PM
	 */
	public void saveInvoice(
			CBillForm billForm, 
			String tempId, 
			String advanceId, 
			String advanceWuYFId, 
			String rentDepositId,
			String decorationDeposit,
			String earnId) throws BkmisServiceException; 

	/**
	 * 查询财务使用的房租信息
	 * @param billForm
	 * @return
	 * Date:Nov 30, 2012 
	 * Time:1:22:17 AM
	 */
	public PageBean queryRentInfo(CBillForm billForm);
	
	/**
	 * 查询财务使用的房租信息用于导出excel
	 * @param billForm
	 * @return
	 * Date:Jul 2, 2012 
	 * Time:9:05:04 AM
	 */
	public List queryRentInfoExportExcel(CBillForm billForm);
	/**
	 * 保存收取的物业费详情记录
	 * @param formbean
	 * Date:2018-3-3 
	 * Time:下午02:57:54
	 */
	public void saveAdvanceWuYFDetail(CBillForm formbean) throws Exception;

	
}
