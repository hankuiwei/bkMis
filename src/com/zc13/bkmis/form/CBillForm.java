/**
 * Administrator
 */
package com.zc13.bkmis.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CCharge;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.CInvoice;
import com.zc13.bkmis.mapping.CInvoiceBill;
import com.zc13.bkmis.mapping.CRefund;
import com.zc13.bkmis.mapping.CTemporal;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.util.PojoArrayList;

/**
 * @author Administrator
 * Date：Dec 20, 2010
 * Time：3:53:44 PM
 */
public class CBillForm extends ActionForm {
	
	private CInvoiceBill cInvouceBill = new CInvoiceBill();
	private CInvoice cinvoice = new CInvoice();
	private CBill bill = new CBill();
	private Integer lpId;//楼盘id
	private Integer standardId;//收费标准id
	private String clientName;//客户名称
	private String chequeNo;//支票号
	private String billCode;//申请单号
	private String roomCode;//房间号
	private String status;//单据状态
	private CompactClient client = new CompactClient();//客户
	private CCoststandard standard = new CCoststandard();
	private String ids;//客户id串
	private Integer[] checkbox;//复选框
	private String before;//账期-开始
	private String after;//账期-结束
	private Integer clientId;//客户id
	private Integer itemId;//收费项目id
	private String itemIds;//收费项id字符串，用于一次选择多个收费项
	private double[] actuallyPaid;//已缴金额
	private double amount;//本期余额
	private String amountType;//余额转入
	private Integer[] id;
	private CRefund refund = new CRefund();//退款信息
	private double[] amountCharged;//账单已收金额
	private double[] returnValue;//退还金额
	private String[] billType;//账单单据类型
	private String[] billNum;//账单单据号
	private Integer depositId;//押金Id
	private String begin;//缴纳日期-开始
	private String end;//缴纳日期-结束
	private CAdvance advance = new CAdvance();//预付款(租金)
	private CAdvanceWuYF advanceWuYF = new CAdvanceWuYF();//预付款(物业费)
	private CTemporal temporal = new CTemporal();//暂付款
	private CDeposit rentDeposit = new CDeposit();//房租押金
	private CDeposit decorationDeposit = new CDeposit();//装修押金
	private String depositType;//押金类型
	private double[] delayingExpenses;//滞纳金
	private CCharge charge = new CCharge();//收费表
	private String billId;//收费-账单id
	private Integer chargeId;//收费id
	private String sfqx;//是否全选
	private List<CCharge> chargeList = new PojoArrayList<CCharge>(CCharge.class);
	private String userId;//用户id
	private String role;
	private String payType;//收费类型
	private CEarnest earnest = new CEarnest();//订金
	private String earnestFlag;
	private double earnestNum;
	private String date;//收费日期
	public String pagination;	//当前页数
	public Integer pagesize;	//每页显示的条数
	/***********用于财务对账对客户房租信息的查询***********/
	private String startTime;
	private String endTime;
	
	//登陆人信息
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the advanceWuYF
	 */
	public CAdvanceWuYF getAdvanceWuYF() {
		return advanceWuYF;
	}
	/**
	 * @param advanceWuYF ->the advanceWuYF to set
	 */
	public void setAdvanceWuYF(CAdvanceWuYF advanceWuYF) {
		this.advanceWuYF = advanceWuYF;
	}
	/**
	 * @return the bill
	 */
	public CBill getBill() {
		return bill;
	}
	/**
	 * @param bill the bill to set
	 */
	public void setBill(CBill bill) {
		this.bill = bill;
	}
	/**
	 * @return the pagination
	 */
	public String getPagination() {
		return pagination;
	}
	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	/**
	 * @return the pagesize
	 */
	public Integer getPagesize() {
		return pagesize;
	}
	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	/**
	 * @return the lpId
	 */
	public Integer getLpId() {
		return lpId;
	}
	/**
	 * @param lpId the lpId to set
	 */
	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}
	/**
	 * @return the standardId
	 */
	public Integer getStandardId() {
		return standardId;
	}
	/**
	 * @param standardId the standardId to set
	 */
	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the roomCode
	 */
	public String getRoomCode() {
		return roomCode;
	}
	/**
	 * @param roomCode the roomCode to set
	 */
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the client
	 */
	public CompactClient getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(CompactClient client) {
		this.client = client;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * @return the standard
	 */
	public CCoststandard getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(CCoststandard standard) {
		this.standard = standard;
	}
	/**
	 * @return the checkbox
	 */
	public Integer[] getCheckbox() {
		return checkbox;
	}
	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	/**
	 * @return the before
	 */
	public String getBefore() {
		return before;
	}
	/**
	 * @param before the before to set
	 */
	public void setBefore(String before) {
		this.before = before;
	}
	/**
	 * @return the after
	 */
	public String getAfter() {
		return after;
	}
	/**
	 * @param after the after to set
	 */
	public void setAfter(String after) {
		this.after = after;
	}
	/**
	 * @return the clientId
	 */
	public Integer getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the actuallyPaid
	 */
	public double[] getActuallyPaid() {
		return actuallyPaid;
	}
	/**
	 * @param actuallyPaid the actuallyPaid to set
	 */
	public void setActuallyPaid(double[] actuallyPaid) {
		this.actuallyPaid = actuallyPaid;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the amountType
	 */
	public String getAmountType() {
		return amountType;
	}
	/**
	 * @param amountType the amountType to set
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	/**
	 * @return the id
	 */
	public Integer[] getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer[] id) {
		this.id = id;
	}
	/**
	 * @return the refund
	 */
	public CRefund getRefund() {
		return refund;
	}
	/**
	 * @param refund the refund to set
	 */
	public void setRefund(CRefund refund) {
		this.refund = refund;
	}
	/**
	 * @return the amountCharged
	 */
	public double[] getAmountCharged() {
		return amountCharged;
	}
	/**
	 * @param amountCharged the amountCharged to set
	 */
	public void setAmountCharged(double[] amountCharged) {
		this.amountCharged = amountCharged;
	}
	/**
	 * @return the returnValue
	 */
	public double[] getReturnValue() {
		return returnValue;
	}
	/**
	 * @param returnValue the returnValue to set
	 */
	public void setReturnValue(double[] returnValue) {
		this.returnValue = returnValue;
	}
	/**
	 * @return the billType
	 */
	public String[] getBillType() {
		return billType;
	}
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String[] billType) {
		this.billType = billType;
	}
	/**
	 * @return the billNum
	 */
	public String[] getBillNum() {
		return billNum;
	}
	/**
	 * @param billNum the billNum to set
	 */
	public void setBillNum(String[] billNum) {
		this.billNum = billNum;
	}
	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	/**
	 * @return the begin
	 */
	public String getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(String begin) {
		this.begin = begin;
	}
	/**
	 * @return the advance
	 */
	public CAdvance getAdvance() {
		return advance;
	}
	/**
	 * @param advance the advance to set
	 */
	public void setAdvance(CAdvance advance) {
		this.advance = advance;
	}
	/**
	 * @return the temporal
	 */
	public CTemporal getTemporal() {
		return temporal;
	}
	/**
	 * @param temporal the temporal to set
	 */
	public void setTemporal(CTemporal temporal) {
		this.temporal = temporal;
	}
	
	public CDeposit getRentDeposit() {
		return rentDeposit;
	}
	public void setRentDeposit(CDeposit rentDeposit) {
		this.rentDeposit = rentDeposit;
	}
	public CDeposit getDecorationDeposit() {
		return decorationDeposit;
	}
	public void setDecorationDeposit(CDeposit decorationDeposit) {
		this.decorationDeposit = decorationDeposit;
	}
	/**
	 * @return the depositId
	 */
	public Integer getDepositId() {
		return depositId;
	}
	/**
	 * @param depositId the depositId to set
	 */
	public void setDepositId(Integer depositId) {
		this.depositId = depositId;
	}
	/**
	 * @return the depositType
	 */
	public String getDepositType() {
		return depositType;
	}
	/**
	 * @param depositType the depositType to set
	 */
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public double[] getDelayingExpenses() {
		return delayingExpenses;
	}
	public void setDelayingExpenses(double[] delayingExpenses) {
		this.delayingExpenses = delayingExpenses;
	}
	/**
	 * @return the charge
	 */
	public CCharge getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(CCharge charge) {
		this.charge = charge;
	}
	/**
	 * @return the billId
	 */
	public String getBillId() {
		return billId;
	}
	/**
	 * @param billId the billId to set
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}
	/**
	 * @return the chargeId
	 */
	public Integer getChargeId() {
		return chargeId;
	}
	/**
	 * @param chargeId the chargeId to set
	 */
	public void setChargeId(Integer chargeId) {
		this.chargeId = chargeId;
	}
	/**
	 * @return the sfqx
	 */
	public String getSfqx() {
		return sfqx;
	}
	/**
	 * @param sfqx the sfqx to set
	 */
	public void setSfqx(String sfqx) {
		this.sfqx = sfqx;
	}
	/**
	 * @return the chargeList
	 */
	public List<CCharge> getChargeList() {
		return chargeList;
	}
	/**
	 * @param chargeList the chargeList to set
	 */
	public void setChargeList(List<CCharge> chargeList) {
		this.chargeList = chargeList;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public CEarnest getEarnest() {
		return earnest;
	}
	public void setEarnest(CEarnest earnest) {
		this.earnest = earnest;
	}
	public String getEarnestFlag() {
		return earnestFlag;
	}
	public void setEarnestFlag(String earnestFlag) {
		this.earnestFlag = earnestFlag;
	}
	public double getEarnestNum() {
		return earnestNum;
	}
	public void setEarnestNum(double earnestNum) {
		this.earnestNum = earnestNum;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public CInvoiceBill getCInvouceBill() {
		return cInvouceBill;
	}
	public void setCInvouceBill(CInvoiceBill invouceBill) {
		cInvouceBill = invouceBill;
	}
	public CInvoice getCinvoice() {
		return cinvoice;
	}
	public void setCinvoice(CInvoice cinvoice) {
		this.cinvoice = cinvoice;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
