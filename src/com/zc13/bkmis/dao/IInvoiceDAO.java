package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.InvoiceForm;

/**
 * 发票管理DAO
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:19:24 AM
 */
public interface IInvoiceDAO extends BasicDAO {

	/**
	 * 获取收据列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Dec 20, 2011 
	 * Time:5:43:16 PM
	 */
	public List getReceiptList(InvoiceForm form1, boolean b);

	/**
	 * 根据收据id获取发票信息
	 * @param form1
	 * @return
	 * Date:Dec 22, 2011 
	 * Time:6:04:15 PM
	 */
	public List getInvoiceByReceiptId(InvoiceForm form1);

	/**
	 * 获取发票列表
	 * @param form1
	 * @param b
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:9:53:10 AM
	 */
	public List getInvoiceList(InvoiceForm form1, boolean b);

	/**
	 * 根据发票id获取收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:51 AM
	 */
	public List getReceiptListByInvoiceId(InvoiceForm form1);
	
	/**
	 * 根据发票id获取收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:51 AM
	 */
	public List getInvoiceByReceiptId(Integer chargeId);
	
	/**
	 * 根据发票id获取收据信息
	 * @param form1
	 * @return
	 * Date:Dec 23, 2011 
	 * Time:10:20:51 AM
	 */
	public List getInvoiceByInvoiceId(Integer invoiceid);
	
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
