package com.zc13.bkmis.service.impl;

import java.util.List;

import com.zc13.bkmis.dao.IAwokeTaskDAO;
import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.dao.ICostTransactDAO;
import com.zc13.bkmis.form.AwokeTaskForm;
import com.zc13.bkmis.service.IAwokeTaskService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;

public class AwokeTaskServiceImpl extends BasicServiceImpl implements IAwokeTaskService{
	private ICompactManagerDAO compactManagerDAO;
	private IAwokeTaskDAO awokeTaskDAO;
	private ICostTransactDAO costTransactDAO;
	
	public ICostTransactDAO getCostTransactDAO() {
		return costTransactDAO;
	}

	public void setCostTransactDAO(ICostTransactDAO costTransactDAO) {
		this.costTransactDAO = costTransactDAO;
	}

	public IAwokeTaskDAO getAwokeTaskDAO() {
		return awokeTaskDAO;
	}

	public void setAwokeTaskDAO(IAwokeTaskDAO awokeTaskDAO) {
		this.awokeTaskDAO = awokeTaskDAO;
	}

	public ICompactManagerDAO getCompactManagerDAO() {
		return compactManagerDAO;
	}

	public void setCompactManagerDAO(ICompactManagerDAO compactManagerDAO) {
		this.compactManagerDAO = compactManagerDAO;
	}

	//更新代办任务数量
	@Override
	public void updateAwokeTaskAmount(AwokeTaskForm form) throws BkmisServiceException {
		//更新即将到期的合同数量
		String todayDate = GlobalMethod.getTime();
		String endDate = GlobalMethod.getEndDate(todayDate, 30);
		int count1 = 0;
		List list1 = compactManagerDAO.getAtTermList(endDate);//即将过期的未被续租的合同列表
		if(list1!=null){count1 = list1.size();}
		awokeTaskDAO.updateTask(Contants.COMPACTRIGHT, count1);
		
		//更新未处理的报修数量
		List list2 = awokeTaskDAO.findObject("from SerClientMaintain t where t.status='"+Contants.DISPATCHING_WAIT+"' and t.isEnabled='1' ");
		int count2 = 0;
		if(list2!=null){count2 = list2.size();}
		awokeTaskDAO.updateTask(Contants.MAINTAIN, count2);
		
		//更新未处理的投诉的数量
		List list3 = awokeTaskDAO.findObject("from SerClientComplaint t where t.status='"+Contants.UNPROCESSED+"'");
		int count3 = 0;
		if(list3!=null){count3 = list3.size();}
		awokeTaskDAO.updateTask(Contants.COMPLAINT, count3);
		
		//更新需要校验的新合同的数量
		List list4 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.NOTSUBMIT+"' and t.type='"+Contants.COMPACT+"'");
		int count4 = 0;
		if(list4!=null){count4 = list4.size();}
		awokeTaskDAO.updateTask(Contants.COLLATENEW, count4);
		
		//更新需要校验的变更合同的数量
		List list5 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.NOTSUBMIT+"' and t.type='"+Contants.COMPACTCHANGE+"'");
		int count5 = 0;
		if(list5!=null){count5 = list5.size();}
		awokeTaskDAO.updateTask(Contants.COLLATECHAGE, count5);
		
		//更新需要校验的续租合同的数量
		List list6 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.NOTSUBMIT+"' and t.type='"+Contants.COMPACTRELET+"'");
		int count6 = 0;
		if(list6!=null){count6 = list6.size();}
		awokeTaskDAO.updateTask(Contants.COLLATERELET, count6);
		
		//更新需要审批的新合同数量
		List list7 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.ONAPPROVAL+"' and t.type='"+Contants.COMPACT+"'");
		int count7 = 0;
		if(list7!=null){count7 = list7.size();}
		awokeTaskDAO.updateTask(Contants.APPROVENEW, count7);
		
		//更新需要审批的变更合同的数量
		List list8 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.ONAPPROVAL+"' and t.type='"+Contants.COMPACTCHANGE+"'");
		int count8 = 0;
		if(list8!=null){count8 = list8.size();}
		awokeTaskDAO.updateTask(Contants.APPROVECHANGE, count8);
		
		//更新需要审批的续租合同的数量
		List list9 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.ONAPPROVAL+"' and t.type='"+Contants.COMPACTRELET+"'");
		int count9 = 0;
		if(list9!=null){count9 = list9.size();}
		awokeTaskDAO.updateTask(Contants.APPROVERELET, count9);
		
		//更新未缴纳定金的客户的数量
		List list10 = null;
		try {
			list10 = costTransactDAO.getPressEarnestClient(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count10 = 0;
		if(list10!=null){count10 = list10.size();}
		awokeTaskDAO.updateTask(Contants.PRESSEARNEST, count10);
		
		//更新可以转为合同的意向书的数量
		List list11 = null;
		list11 = awokeTaskDAO.findObject("from CompactIntention t where t.id not in (select c.intentionId from Compact c) and (t.isEarnest='1' or t.earnest=0.000) and t.isConvertCompact<>'1' and t.status='"+Contants.THROUGHAPPROVAL+"'");
		int count11 = 0;
		if(list11!=null){count11 = list11.size();}
		awokeTaskDAO.updateTask(Contants.ENOTIN, count11);
		
		//更新需要缴费的客户的数量
		List list12 = null;
		try {
			list12 = costTransactDAO.getPressMoneyClient(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count12 = 0;
		if(list12!=null){count12 = list12.size();}
		awokeTaskDAO.updateTask(Contants.PRESSMONEY, count12);
		
		//更新预收房租余额不足的客户的数量
		//在ICostTransactService中更新，调用costTransactService.updateAwokeTask4PressAdvance()方法
		
		//更新需要缴纳房租押金的客户的数量
		//在ICostTransactService中更新，调用costTransactService.updateAwokeTask4PressRentDeposit()方法
		
		//更新需要缴纳装修押金的客户的数量
		//在ICostTransactService中更新，调用costTransactService.updateAwokeTask4PressDecorationDeposit()方法
		
		//更新需要校验的意向书的数量
		List list13 = awokeTaskDAO.findObject("from CompactIntention t where t.status='"+Contants.NOTSUBMIT+"'");
		int count13 = 0;
		if(list13!=null){count13 = list13.size();}
		awokeTaskDAO.updateTask(Contants.COLLATEINTENTION, count13);
		
		//更新需要审批的意向书的数量
		List list14 = awokeTaskDAO.findObject("from CompactIntention t where t.status='"+Contants.ONAPPROVAL+"'");
		int count14 = 0;
		if(list14!=null){count14 = list14.size();}
		awokeTaskDAO.updateTask(Contants.APPROVEINTENTION, count14);
		
		//更新需要通知入住的客户的数量
		List list15 = awokeTaskDAO.findObject("from Compact t where t.status='"+Contants.THROUGHAPPROVAL+"' and (t.isNotice is null or t.isNotice='' or t.isNotice='"+Contants.NOTNOTICE+"') and t.type='"+Contants.COMPACT+"'");
		int count15 = 0;
		if(list15!=null){count15 = list15.size();}
		awokeTaskDAO.updateTask(Contants.NOTICEIN, count15);
		
		//更新需要正式入住的客户的数量
		List list16 = awokeTaskDAO.findObject("from Compact t where t.isNotice='"+Contants.HAVENOTICE+"' and status='"+Contants.THROUGHAPPROVAL+"' and isDestine!='"+Contants.HASOVER+"'");
		int count16 = 0;
		if(list16!=null&list16.size()>0){
			count16 = list16.size();
		}
		
		awokeTaskDAO.updateTask(Contants.IN, count16);
	}

}
