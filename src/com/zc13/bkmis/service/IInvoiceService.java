package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.InvoiceForm;

/**
 * 发票管理service
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:22:37 AM
 */
public interface IInvoiceService extends IBasicService {

	/**
	 * 获取收据列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Dec 20, 2011 
	 * Time:5:37:43 PM
	 */
	public List getReceiptList(InvoiceForm form1, boolean b);

	/**
	 * 开发票
	 * @param form1
	 * Date:Dec 21, 2011 
	 * Time:11:33:49 PM
	 */
	public void openInvoice(InvoiceForm form1);

	/**
	 * 根据收据id获取相关联的发票信息
	 * @param form1
	 * Date:Dec 22, 2011 
	 * Time:6:00:30 PM
	 */
	public void getInvoiceListByReceiptId(InvoiceForm form1);

	/**
	 * 获取发票列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:9:48:37 AM
	 */
	public List getInvoiceList(InvoiceForm form1, boolean b);

	/**
	 * 根据发票id获取相关联的收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:06 AM
	 */
	public List getReceiptListByInvoiceId(InvoiceForm form1);
	
	/**
	 * 根据收据id获取相关联的收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:06 AM
	 */
	public void getInvoiceListByChargeId(InvoiceForm form1);
	
	/**
	 * 根据发票id获取相关联的收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:06 AM
	 */
	public void getInvoiceListByInvoiceId(InvoiceForm form1);
	
	/**
	 * 按条件汇总发票内容
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceContent(InvoiceForm form1);
	
	/**
	 * 按条件汇总发票项目明细
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceItem(InvoiceForm form1);

}
