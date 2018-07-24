package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IInvoiceDAO;
import com.zc13.bkmis.form.InvoiceForm;
import com.zc13.util.GlobalMethod;

/**
 * 发票管理DAO
 * @author wangzw
 * @Date Dec 20, 2011
 * @Time 11:22:43 AM
 */
public class InvoiceDAOImpl extends BasicDAOImpl implements IInvoiceDAO {

	//获取收据列表
	@Override
	public List getReceiptList(InvoiceForm form1, boolean b) {
		StringBuffer hql = new StringBuffer();
		hql.append("select ");
		hql.append("	t2.name as clientName,");
		hql.append("	t1.date,");
		hql.append("	t1.bill_num,");
		hql.append("	t3.real_name as reciveUserName,");
		hql.append("	t1.amount,");
		hql.append("	isnull((select SUM(amount) from c_invoice_receipt where receipt_id=t1.id),0) as openBill,");
		hql.append("	dbo.f_connectInvoiceNum(t1.id) as invoiceNum,");
		hql.append("	t1.amount-isnull((select SUM(amount) from c_invoice_receipt where receipt_id=t1.id),0) as notOpenBill,");
		hql.append("	t1.id,");
		hql.append("	t1.client_id,");
		hql.append("	t1.bill_id,");
		hql.append("	t1.pay_type,");
		hql.append("	t1.user_id,");
		hql.append("	t1.cheque_no ");
		hql.append(" from c_charge t1,compact_client t2,m_user t3 where t1.client_id=t2.id and t1.user_id=t3.USERID and bill_type='0' ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getReceiptIds()).equals("")){
				hql.append(" and t1.id in(").append(form1.getReceiptIds()).append(")");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxClientName()).equals("")){
				hql.append(" and t2.name like '%").append(form1.getCxClientName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxReceiptNo()).equals("")){
				hql.append(" and t1.bill_num like '%").append(form1.getCxReceiptNo()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxInvoiceNo()).equals("")){
				hql.append(" and dbo.f_connectInvoiceNum(t1.id) like '%").append(form1.getCxInvoiceNo()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxBeginDate()).equals("")){
				hql.append(" and t1.date >='").append(form1.getCxBeginDate()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndDate()).equals("")){
				hql.append(" and t1.date <='").append(form1.getCxEndDate()).append("'");
			}
			if(form1.getCxReciveUserId()!=null&&form1.getCxReciveUserId()!=0){
				hql.append(" and t1.user_id =").append(form1.getCxReciveUserId());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxStartAmount()).equals("")){
				hql.append(" and t1.amount-isnull((select SUM(amount) from c_invoice_receipt where receipt_id=t1.id),0)>").append(form1.getCxStartAmount());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndAmount()).equals("")){
				hql.append(" and t1.amount-isnull((select SUM(amount) from c_invoice_receipt where receipt_id=t1.id),0)<").append(form1.getCxEndAmount());
			}
		}
		hql.append(" order by t1.client_id,t1.date desc ");
		Query query=this.getSession().createSQLQuery(hql.toString());
		List list = null;
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		List receiptList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("clientName", obj[0]);
				map.put("reciveCostDate", obj[1]);
				map.put("billNum", obj[2]);
				map.put("reciveUserName", obj[3]);
				map.put("amount", obj[4]);
				map.put("openBill", obj[5]);
				map.put("invoiceNum", obj[6]);
				map.put("notOpenBill", obj[7]);
				map.put("id", obj[8]);
				map.put("clientId", obj[9]);
				map.put("billId", obj[10]);
				map.put("payType", obj[11]);
				map.put("userId", obj[12]);
				map.put("chequeNo", obj[13]);
				
				//查询具体的费用项
//				itemhql.append("select a.id,a.standard_name,a.moneydetail,a.notalreadyopen,b.invoice_id,b.invoice_amount from");
//				itemhql.append(" (select bill.id,bill.standard_name,(bill.bills_expenses+bill.delaying_expenses) moneydetail,");
//				itemhql.append("(bill.bills_expenses+bill.delaying_expenses) notalreadyopen");
//				itemhql.append(" from c_bill bill where bill.id in(");
//				itemhql.append(obj[10]);
//				itemhql.append(")) a left join c_invoice_bill b");
//				itemhql.append(" on a.id = b.bill_id");
				List itemList = new ArrayList();
				String[] billIds = ((String)obj[10]).split(",");
				for(String billid : billIds){
					StringBuffer itemhql = new StringBuffer();
					itemhql.append("select a.id,a.standard_name,a.moneydetail,(a.moneydetail-isnull((select sum(c.invoice_amount) from c_invoice_bill c where c.bill_id=a.id),0)) notalreadyopen,");
					itemhql.append("dbo.f_connectBillId(a.id) invoice_id,isnull((select sum(c.invoice_amount) from c_invoice_bill c where c.bill_id=a.id),0) invoice_amount from");
					itemhql.append(" (select bill.id id,item.item_name standard_name,actually_paid moneydetail");
					itemhql.append(" from c_bill bill,c_items item where bill.id in(");
					itemhql.append(billid);
					itemhql.append(") and bill.item_id = item.id) a");
					//itemhql.append(" left join c_invoice_bill b on a.id = b.bill_id");
					Query itemQuery=this.getSession().createSQLQuery(itemhql.toString());
					List everyitem = itemQuery.list();
					itemList.addAll(everyitem);
				}
				List formatlist = new ArrayList();
				if(itemList != null && itemList.size()>0){
					for(int m =0;m<itemList.size();m++){
						Object[] mobj = (Object[])itemList.get(m);
						Map itemmap = new HashMap();
						itemmap.put("id", mobj[0]);
						itemmap.put("standardName", mobj[1]);
						itemmap.put("moneydetail", mobj[2]);
						itemmap.put("notalreadyopen", mobj[3]);
						itemmap.put("invoiceId", mobj[4]);
						itemmap.put("invoicAmount", mobj[5]);
						formatlist.add(itemmap);
					}
					map.put("rowspan", itemList.size());
				}else{
					map.put("rowspan", 1);
				}
				map.put("itemList", formatlist);
				receiptList.add(map);
			}
		}
		return receiptList;
	}

	@Override
	public List getInvoiceByReceiptId(InvoiceForm form1) {
		StringBuffer hql = new StringBuffer("select new Map(t1.amount as receiptInvoiceAmount,t2.amount as invoiceAmount,t2.invoiceNum as invoiceNum,t2.date as date,t3.username as operatorName,t3.realName as realName) from CInvoiceReceipt t1,CInvoice t2,MUser t3 where t1.invoiceId=t2.id and t3.userid=t2.operatorId ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getReceiptIds()).equals("")){
				hql.append(" and t1.receiptId in(").append(form1.getReceiptIds()).append(") ");
			}
		}
		hql.append(" order by t2.date ");
		Query query=this.getSession().createQuery(hql.toString());
		List list = query.list();
		return list;
	}
	
	@Override
	public List getInvoiceByReceiptId(Integer chargeid) {
		StringBuffer hql = new StringBuffer("select new Map(");
		hql.append(" t1.invoiceAmount as invoiceAmount, ");//发票对应项目金额
		hql.append(" t3.itemName as itemName, ");//项目明细
		hql.append(" t5.invoiceNum as invoiceNum, ");//发票编号
		hql.append(" t5.date as date, ");//操作日期
		hql.append(" t5.amount as amount, ");//发票总金额
		hql.append(" t4.username as userName, ");//操作人用户名
		hql.append(" t4.realName as operatorNameName, ");//操作人姓名
		hql.append(" t2.billsExpenses as billsExpenses, ");//合同金额
		hql.append(" t2.actuallyPaid as actuallyPaid ");//账单实际支付金额
		hql.append(" ) ");//账单实际支付金额
		hql.append(" from CInvoiceBill t1, CBill t2, CItems t3, MUser t4, CInvoice t5 ");
		hql.append(" where t1.invoiceId in (select invoiceId from CInvoiceReceipt where receiptId=?)");
		hql.append(" and t1.billId=t2.id and t2.itemId=t3.id and t5.operatorId=t4.userid and t5.id=t1.invoiceId ");
								 
		Query query=this.getSession().createQuery(hql.toString());
		query.setParameter(0, chargeid);
		List list = query.list();
		this.releaseSession(this.getSession());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getInvoiceList(InvoiceForm form1, boolean b) {
		//原来的hql语句
		//StringBuffer hql = new StringBuffer("select new Map(t1.id as id,t1.amount as amount,t1.invoiceNum as invoiceNum,t1.date as date,t2.name as clientName,t3.username as operatorName,t3.realName as realName) from CInvoice t1,CompactClient t2,MUser t3 where t1.clientId=t2.id and t1.operatorId=t3.userid ");
		StringBuffer sql = new StringBuffer();
//		sql.append("select t1.id as id,");
//		sql.append("(select isnull(sum(cil.invoice_amount),0) from c_invoice_bill cil where cil.invoice_id = t1.invoice_num) totalInvoiceAmount,");
//		sql.append("t1.invoice_num as invoiceNum,t1.date as date,");
//		sql.append("t2.name as clientName,t3.username as operatorName,");
//		sql.append("t3.real_name as realName ");
//		sql.append("from c_invoice t1,compact_client t2,m_user t3");
//		sql.append(" where t1.client_id=t2.id and t1.operator_id=t3.userid");
		sql.append("select t1.id as id,");
		sql.append("t1.amount totalInvoiceAmount,");
		sql.append("t1.invoice_num as invoiceNum,t1.date as date,");
		sql.append("t2.name as clientName,t3.username as operatorName,");
		sql.append("t3.real_name as realName ");
		sql.append("from c_invoice t1,compact_client t2,m_user t3");
		sql.append(" where t1.client_id=t2.id and t1.operator_id=t3.userid");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getInvoiceIds()).equals("")){
				sql.append(" and t1.id in(").append(form1.getInvoiceIds()).append(")");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxClientName()).equals("")){
				sql.append(" and t2.name like '%").append(form1.getCxClientName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxInvoiceNo()).equals("")){
				sql.append(" and t1.invoice_num like '%").append(form1.getCxInvoiceNo()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxBeginDate()).equals("")){
				sql.append(" and t1.date >='").append(form1.getCxBeginDate()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndDate()).equals("")){
				sql.append(" and t1.date <='").append(form1.getCxEndDate()).append("'");
			}
			if(form1.getCxInvoiceUserId()!=null&&form1.getCxInvoiceUserId()!=0){
				sql.append(" and t1.operator_id =").append(form1.getCxInvoiceUserId());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxStartAmount()).equals("")){
				sql.append(" and isnull(t1.amount,0)>").append(form1.getCxStartAmount());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndAmount()).equals("")){
				sql.append(" and isnull(t1.amount,0)<").append(form1.getCxEndAmount());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxInvoiceContent()).equals("")){
				sql.append(" and t1.id in (select invoice_id from c_invoice_bill where invoice_content='").append(form1.getCxInvoiceContent()).append("') ");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxInvoiceItem()).equals("")){
				sql.append(" and t1.id in (select i.invoice_id from c_invoice_bill i,c_bill b where i.bill_id=b.id and b.item_id in (").append(form1.getCxInvoiceItem()).append(")) ");
			}
		}
		sql.append(" order by t1.id desc ");
		List list = null;
		//Query query = this.getSession().createQuery(hql.toString());
		Query query = this.getSession().createSQLQuery(sql.toString());
		if(b){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")) - 1);
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			list = query.list();
		}
		
		List returList = new ArrayList();
		if(list != null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object obj[] = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("id", obj[0]);
				map.put("totalInvoiceAmount", obj[1]);
				map.put("invoiceNum", obj[2]);
				map.put("date", obj[3]);
				map.put("clientName", obj[4]);
				map.put("operatorName", obj[5]);
				map.put("realName", obj[6]);
				
				//根据发票号查询详细的收费项
				StringBuffer itemSql = new StringBuffer();
//				itemSql.append("select (select item.item_name from c_items item,c_bill bill where bill.item_id = item.id and bill.id=a.billId) itemname,");
//				itemSql.append("(select invoice_amount from c_invoice_bill cil where cil.bill_id=a.billId) invoice_amount from ");
//				itemSql.append("(select distinct cil.bill_id billId from c_invoice_bill cil where cil.invoice_id =");
//				itemSql.append(obj[2]);
//				itemSql.append(") a");
				itemSql.append("select item.item_name,cil.invoice_amount, cil.invoice_content from c_bill bill,c_items item,c_invoice_bill cil");
				itemSql.append(" where bill.item_id = item.id and cil.bill_id = bill.id and cil.invoice_id=");
				itemSql.append(obj[0]);
				Query itemQuery = this.getSession().createSQLQuery(itemSql.toString());
				List itemList = itemQuery.list();
				
				List formatelist = new ArrayList();
				if(itemList != null && itemList.size()>0){
					for(int j = 0;j<itemList.size();j++){
						Object itemObj[] = (Object[])itemList.get(j);
						Map itemMap = new HashMap();
						itemMap.put("itemName", itemObj[0]);
						itemMap.put("invoiceAmount", itemObj[1]);
						itemMap.put("invoiceContent", itemObj[2]);
						formatelist.add(itemMap);
					}
					map.put("rowspan", itemList.size());
				}else{
					map.put("rowspan", 1);
				}
				map.put("itemList", formatelist);
				returList.add(map);
			}
		}
		return returList;
	}
	
	/**
	 * 按条件汇总发票内容
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceContent(InvoiceForm form1) {
		StringBuffer sql = new StringBuffer("select sum(invoice_amount) as amount, ");
										sql.append("invoice_content ");
								sql.append("from c_invoice_bill ");
								sql.append("where EXISTS ( ");
								        sql.append(" select t1.id ");
								        sql.append(" from c_invoice t1, compact_client t2, m_user t3 ");
								        sql.append(" where t1.client_id=t2.id ");
								        	sql.append(" and t1.operator_id=t3.userid ");
								        	sql.append(" and invoice_id=t1.id ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getCxClientName()).equals("")){
				sql.append(" and t2.name like '%").append(form1.getCxClientName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxInvoiceNo()).equals("")){
				sql.append(" and t1.invoice_num like '%").append(form1.getCxInvoiceNo()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxBeginDate()).equals("")){
				sql.append(" and t1.date >='").append(form1.getCxBeginDate()).append("'");
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndDate()).equals("")){
				sql.append(" and t1.date <='").append(form1.getCxEndDate()).append("'");
			}
			if(form1.getCxInvoiceUserId()!=null&&form1.getCxInvoiceUserId()!=0){
				sql.append(" and t1.operator_id =").append(form1.getCxInvoiceUserId());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxStartAmount()).equals("")){
				sql.append(" and isnull(t1.amount,0)>").append(form1.getCxStartAmount());
			}
			if(!GlobalMethod.NullToSpace(form1.getCxEndAmount()).equals("")){
				sql.append(" and isnull(t1.amount,0)<").append(form1.getCxEndAmount());
			}
		}
		
		sql.append(") group by invoice_content ");
		List list = null;
		Query query = this.getSession().createSQLQuery(sql.toString());
		return query.list();
	}
	
	/**
	 * 按条件汇总发票项目明细
	 * @param form1
	 * @return
	 * Date:Apr 24, 2012 
	 * Time:5:37:13 PM
	 */
	public List summaryInvoiceItem(InvoiceForm form1) {
		StringBuffer sql = new StringBuffer("select sum(invoice_amount) as amount, ");
		sql.append("i.item_name ");
		sql.append("from c_invoice_bill, c_bill b, c_items i ");
		sql.append("where EXISTS ( ");
		        sql.append(" select t1.id ");
		        sql.append(" from c_invoice t1, compact_client t2, m_user t3 ");
		        sql.append(" where t1.client_id=t2.id ");
		        	sql.append(" and t1.operator_id=t3.userid ");
		        	sql.append(" and invoice_id=t1.id ");
		if(form1!=null){
		if(!GlobalMethod.NullToSpace(form1.getCxClientName()).equals("")){
		sql.append(" and t2.name like '%").append(form1.getCxClientName()).append("%'");
		}
		if(!GlobalMethod.NullToSpace(form1.getCxInvoiceNo()).equals("")){
		sql.append(" and t1.invoice_num like '%").append(form1.getCxInvoiceNo()).append("%'");
		}
		if(!GlobalMethod.NullToSpace(form1.getCxBeginDate()).equals("")){
		sql.append(" and t1.date >='").append(form1.getCxBeginDate()).append("'");
		}
		if(!GlobalMethod.NullToSpace(form1.getCxEndDate()).equals("")){
		sql.append(" and t1.date <='").append(form1.getCxEndDate()).append("'");
		}
		if(form1.getCxInvoiceUserId()!=null&&form1.getCxInvoiceUserId()!=0){
		sql.append(" and t1.operator_id =").append(form1.getCxInvoiceUserId());
		}
		if(!GlobalMethod.NullToSpace(form1.getCxStartAmount()).equals("")){
		sql.append(" and isnull(t1.amount,0)>").append(form1.getCxStartAmount());
		}
		if(!GlobalMethod.NullToSpace(form1.getCxEndAmount()).equals("")){
		sql.append(" and isnull(t1.amount,0)<").append(form1.getCxEndAmount());
		}
		}
		
		sql.append(") and bill_id=b.id and b.item_id=i.id "); 
		sql.append(" group by item_name ");
		List list = null;
		Query query = this.getSession().createSQLQuery(sql.toString());
		return query.list();
	}
	
	@Override
	public List getReceiptListByInvoiceId(InvoiceForm form1) {
		StringBuffer hql = new StringBuffer("select new Map(t1.amount as receiptInvoiceAmount,t2.amount as receiptAmount,t2.billNum as receiptNum,t2.date as date,t3.username as operatorName,t3.realName as realName) from CInvoiceReceipt t1,CCharge t2,MUser t3 where t1.receiptId=t2.id and t3.userid=t2.userId ");
		if(form1!=null){
			if(!GlobalMethod.NullToSpace(form1.getInvoiceIds()).equals("")){
				hql.append(" and t1.invoiceId in(").append(form1.getInvoiceIds()).append(") ");
			}
		}
		hql.append(" order by t2.date ");
		Query query=this.getSession().createQuery(hql.toString());
		List list = query.list();
		return list;
	}
	
	@Override
	public List getInvoiceByInvoiceId(Integer invoiceid) {
		StringBuffer hql = new StringBuffer("select new Map(");
		hql.append(" t1.invoiceAmount as invoiceAmount, ");//发票对应项目金额
		hql.append(" t3.itemName as itemName, ");//项目明细
		hql.append(" t1.invoiceContent as invoiceContent, ");//发票内容
		hql.append(" t5.invoiceNum as invoiceNum, ");//发票编号
		hql.append(" t5.date as date, ");//操作日期
		hql.append(" t5.amount as amount, ");//发票总金额
		hql.append(" t4.username as userName, ");//操作人用户名
		hql.append(" t4.realName as operatorNameName, ");//操作人姓名
		hql.append(" t2.billsExpenses as billsExpenses, ");//合同金额
		hql.append(" t2.billNum as billNum, ");//账单编号
		hql.append(" t2.actuallyPaid as actuallyPaid ");//账单实际支付金额
		hql.append(" ) ");//账单实际支付金额
		hql.append(" from CInvoiceBill t1, CBill t2, CItems t3, MUser t4, CInvoice t5 ");
		hql.append(" where t1.invoiceId=? ");
		hql.append(" and t1.billId=t2.id and t2.itemId=t3.id and t5.operatorId=t4.userid and t5.id=t1.invoiceId ");
								 
		Query query=this.getSession().createQuery(hql.toString());
		query.setParameter(0, invoiceid);
		List list = query.list();
		this.releaseSession(this.getSession());
		return list;
	}

}
