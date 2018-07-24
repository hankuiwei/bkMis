package com.zc13.bkmis.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ISMSDAO;
import com.zc13.bkmis.form.SMSForm;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.TSendTask;
import com.zc13.bkmis.service.ISMSService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;

import common.Logger;

public class SMSServiceImpl  extends BasicServiceImpl implements ISMSService{
	Logger logger = Logger.getLogger(this.getClass());
	private ISMSDAO SMSDAO;
	public ISMSDAO getSMSDAO() {
		return SMSDAO;
	}
	public void setSMSDAO(ISMSDAO smsdao) {
		SMSDAO = smsdao;
	}
	
	//查询短信发送队列
	@Override
	public List getSMSList(SMSForm form1, boolean isPage) throws BkmisServiceException {
		List list1 = null;
		try {
			list1 = SMSDAO.querySMSList(form1,isPage);
			if(form1!=null){
				form1.setList(list1);
			}
			if(isPage){
				int count = 0;
				List list2 = SMSDAO.querySMSList(form1,false);
				if(list2!=null&&list2.size()>0){
					count = list2.size();
				}
				form1.setTotalcount(count);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list1;
	}
	
	//发送短信
	@Override
	public void sendMessage(SMSForm form) throws BkmisServiceException {
		String names = GlobalMethod.NullToSpace(form.getCusName());
		String phones = GlobalMethod.NullToSpace(form.getCusPhone());
		String[] nameArr = names.split(";");
		String[] phoneArr = phones.split(";");
		if(!"".equals(names)){
			for(int i = 0;i<nameArr.length;i++){
				if(phoneArr[i].length()==11&&(phoneArr[i].startsWith("13")||phoneArr[i].startsWith("18")||phoneArr[i].startsWith("15"))) {
					TSendTask sendTask = new TSendTask();
					sendTask.setDestNumber(phoneArr[i]);//手机号码
					sendTask.setContent(form.getMessage());//发送内容
					sendTask.setSendTime(new Date());//发送时间
					sendTask.setSendPriority((short)16);
					sendTask.setValidMinute(0);
					sendTask.setPushUrl("");
					sendTask.setCommPort((short)0);//指定发送此任务的端口,缺省为0表示自动选择端口发送
					sendTask.setSplitCount((short)0);//拆分发送的总条数
					sendTask.setPersonnelName(nameArr[i]);
					sendTask.setLpId(form.getLpId());
					SMSDAO.saveObject(sendTask);
				}
			}
		} else {
			List<CompactClient> list = SMSDAO.findObject("from CompactClient");
			Iterator<CompactClient> it = list.iterator();
			while(it.hasNext()) {
				CompactClient client = it.next();
				if(client.getPhone().length()==11&&(client.getPhone().startsWith("13")||client.getPhone().startsWith("18")||client.getPhone().startsWith("15"))) {
					TSendTask sendTask = new TSendTask();
					sendTask.setDestNumber(client.getPhone());//手机号码
					sendTask.setContent(form.getMessage());//发送内容
					sendTask.setSendTime(new Date());//发送时间
					sendTask.setSendPriority((short)16);
					sendTask.setValidMinute(0);
					sendTask.setPushUrl("");
					sendTask.setCommPort((short)0);//指定发送此任务的端口,缺省为0表示自动选择端口发送
					sendTask.setSplitCount((short)0);//拆分发送的总条数
					sendTask.setPersonnelName(client.getName());
					sendTask.setLpId(form.getLpId());
					SMSDAO.saveObject(sendTask);
				}
			}
		}
		
	}
}
