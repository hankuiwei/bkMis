package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zc13.bkmis.dao.IInvoiceDAO;
import com.zc13.bkmis.form.InvoiceForm;
import com.zc13.bkmis.mapping.CInvoice;
import com.zc13.bkmis.mapping.CInvoiceBill;
import com.zc13.bkmis.mapping.CInvoiceReceipt;
import com.zc13.bkmis.service.IInvoiceService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;
/**
 * 发票管理service
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:22:22 AM
 */
public class InvoiceServiceImpl extends BasicServiceImpl implements IInvoiceService {
	private IInvoiceDAO invoiceDAO;

	public IInvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(IInvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	//获取收据列表
	@Override
	public List getReceiptList(InvoiceForm form1, boolean b) {
		List list = null;
		try{
			list = invoiceDAO.getReceiptList(form1,b);
			//2012-3-13 根据charge表中bil_id查询具体费用项
			if(b){
				List list2 = invoiceDAO.getReceiptList(form1,false);
				if(list2!=null){
					form1.setTotalcount(list2.size());
				}
			}
			form1.setReceiptList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("查询收据列表失败。",e);
		}
		return list;
	}

	//开发票
	@Override
	public void openInvoice(InvoiceForm form1) {
		//保存发票信息
		Integer[] clientIds = form1.getClientId();
		double[] amounts = form1.getAmount();
		String[] invoiceNums = form1.getInvoiceNum();
		Integer[] invoiceIds = new Integer[clientIds!=null&&clientIds.length>0?clientIds.length:0];
		List<CInvoice> invoiceList = new ArrayList<CInvoice>();
		if(clientIds!=null&&clientIds.length>0){
			for(int i = 0;i<clientIds.length;i++){
				CInvoice i1 = new CInvoice();
				i1.setClientId(clientIds[i]);
				i1.setAmount(amounts[i]);
				i1.setInvoiceNum(invoiceNums[i]);
				i1.setDate(GlobalMethod.getTime());
				i1.setOperatorId(form1.getUserId());
				i1.setLpId(form1.getLpId());
				Integer invoiceId = (Integer) invoiceDAO.saveObject(i1);
				invoiceIds[i] = invoiceId;
				invoiceList.add(i1);
			}
		}
		//保存收据发票关系信息
		Integer[] receiptId = form1.getReceiptId();
		String[] invoice_num = form1.getInvoice_num();
		double[] invoice_amount = form1.getInvoice_amount();
		String[] invoice_content = form1.getInvoice_content();
		if(receiptId!=null&&receiptId.length>0){
			for(int i = 0;i<receiptId.length;i++){
				CInvoiceReceipt cir = new CInvoiceReceipt();
				cir.setReceiptId(receiptId[i]);
				cir.setAmount(invoice_amount[i]);
				Integer invoiceId = null;
				for(int j = 0;j<invoiceList.size();j++){
					if(invoice_num[i].equals(invoiceList.get(j).getInvoiceNum())){
						invoiceId = invoiceList.get(j).getId();
					}
				}
				cir.setInvoiceId(invoiceId);
				invoiceDAO.saveObject(cir);
			}
		}
		//保存发票账单关系
		Integer[] billId = form1.getBillId();
		if(billId != null && billId.length>0){
			for(int i=0; i<billId.length;i++){
				CInvoiceBill cib = new CInvoiceBill();
				cib.setBillId(billId[i]);
				cib.setInvoiceAmount(invoice_amount[i]);
				cib.setInvoiceContent(invoice_content[i]);
				Integer invoiceId = null;
				for(int j = 0;j<invoiceList.size();j++){
					if(invoice_num[i].equals(invoiceList.get(j).getInvoiceNum())){
						invoiceId = invoiceList.get(j).getId();
					}
				}
				cib.setInvoiceId(invoiceId);
				invoiceDAO.saveObject(cib);
			}
		}
	}

	@Override
	public void getInvoiceListByReceiptId(InvoiceForm form1) {
		List list = null;
		try{
			list = invoiceDAO.getInvoiceByReceiptId(form1);
			form1.setInvoiceList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("根据收据id获取发票信息失败！",e);
		}
	}
	
	@Override
	public void getInvoiceListByChargeId(InvoiceForm form1) {
		List list = null;
		try{
			list = invoiceDAO.getInvoiceByReceiptId(Integer.valueOf(form1.getReceiptIds()));
			form1.setInvoiceList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("根据收据id获取发票信息失败！",e);
		}
	}

	@Override
	public List getInvoiceList(InvoiceForm form1, boolean b) {
		List list = null;
		try{
			list = invoiceDAO.getInvoiceList(form1,b);
			if(b){
				List list2 = invoiceDAO.getInvoiceList(form1,false);
				if(list2!=null){
					double total = 0;
					Iterator it = list2.iterator();
					while(it.hasNext()) {
						Map map = (Map) it.next();
						total += (Double) map.get("totalInvoiceAmount");
					}
					form1.setTotalcount(list2.size());
					form1.setTotalAmount(total);
				}
			}
			form1.setInvoiceList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("查询收据列表失败。",e);
		}
		return list;
	}

	@Override
	public List getReceiptListByInvoiceId(InvoiceForm form1) {
		List list = null;
		try{
			list = invoiceDAO.getReceiptListByInvoiceId(form1);
			form1.setReceiptList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("根据发票id获取收据信息失败！",e);
		}
		return list;
	}
	
	@Override
	public void getInvoiceListByInvoiceId(InvoiceForm form1) {
		List list = null;
		try{
			list = invoiceDAO.getInvoiceByInvoiceId(Integer.valueOf(form1.getInvoiceIds()));
			form1.setInvoiceList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("根据收据id获取发票信息失败！",e);
		}
	}
	
	/**
	 * 按条件汇总发票内容
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceContent(InvoiceForm form1) {
		return invoiceDAO.summaryInvoiceContent(form1);
	}
	
	/**
	 * 按条件汇总发票项目明细
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceItem(InvoiceForm form1) {
		return invoiceDAO.summaryInvoiceItem(form1);
	}

}
