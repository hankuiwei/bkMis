package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAccountDepotDAO;
import com.zc13.bkmis.form.AccountDepotForm;
import com.zc13.bkmis.mapping.DepotAdjustAccounts;
import com.zc13.bkmis.service.IAccountDepotService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.CloneObject;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 22, 2010
 * Time：9:26:34 AM
 */
public class AccountDepotServiceImpl extends BasicServiceImpl implements IAccountDepotService{
	Logger logger = Logger.getLogger(this.getClass());
	/** 对iaccountDao 注入*/
	private IAccountDepotDAO iaccountDAO;
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public IAccountDepotDAO getIaccountDAO() {
		return iaccountDAO;
	}
	public void setIaccountDAO(IAccountDepotDAO iaccountDAO) {
		this.iaccountDAO = iaccountDAO;
	}
	//显示查询核算信息
	public void showAccount(AccountDepotForm adf) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = iaccountDAO.showAccount(adf);
		}catch(DataAccessException e){
			logger.error("查询显示库存核算信息失败！AccountDepotServiceImpl.showAccount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示库存核算信息失败！AccountDepotServiceImpl.showAccount()");
		}
		adf.setAccountList(list);
	}
	//查看详细的核算信息
	public void showDetailAccount(AccountDepotForm adf)
			throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			//list = iaccountDAO.showDetailAccount(adf);
		}catch(DataAccessException e){
			logger.error("查询库存详细核算信息失败！AccountDepotServiceImpl.showDetailAccount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询库存详细核算信息失败！AccountDepotServiceImpl.showAccount()");
		}
		adf.setDetailAccountList(list);
	}
	//查询核算信息的记录条数
	public int queryCountTotal(AccountDepotForm adf) throws BkmisServiceException {
		
		int count = 0;
		try{
			count = iaccountDAO.queryCountTotal(adf);
		}catch(DataAccessException e){
			logger.error("查询显示库存核算记录条数信息失败！AccountDepotServiceImpl.queryCountTotal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示库存核算记录条数信息失败！AccountDepotServiceImpl.queryCountTotal()");
		}
		return count;
	}
	//按id查询结算信息 
	public List selectAccountById(int id,AccountDepotForm adf) throws BkmisServiceException {
		
		List list = new ArrayList();
		DepotAdjustAccounts das = new DepotAdjustAccounts();
		List detailList = new ArrayList();
	
		List totalList = new ArrayList();
		double begin = 0;
		try{
			//查询出要查看详细信息的库存信息
			list = iaccountDAO.selectAccountById(id);
			//得到查看详细信息的查询条件
			if(null != list && list.size()>0){
				das = (DepotAdjustAccounts)list.get(0);
			}
			//查询出详细信息
			adf.setBeginDate(das.getBeginDate());
			adf.setEndDate(das.getEndDate());
			detailList = iaccountDAO.showDetailAccount(adf);
			for(int i = 0;i<detailList.size();i++){
				Object obj[] = (Object[])detailList.get(i);
				Map map = new HashMap();
				if(null != obj[0]){
					map.put("name", obj[0].toString());
				}
				if(null != obj[1]){
					map.put("material_id", obj[1].toString());
				}
				if(null != obj[2]){
					map.put("unit", obj[2].toString());
				}
				if(null != obj[3]){
					map.put("spec", obj[3].toString());
				}
				if(null != obj[4]){
					map.put("unitPrice", obj[4].toString());
				}
				if(null != obj[5]){
					map.put("qiAmount", (Double)obj[5]);
				}
				if(null != obj[6]){
					map.put("qiMoney", ((Double)obj[6]).doubleValue());
					begin = ((Double)obj[6]).doubleValue();
				}
				if(null != obj[7]){
					map.put("benInAmount", (Double)obj[7]);
				}
				if(null != obj[8]){
					map.put("benInMoney", ((Double)obj[8]).doubleValue());
				}
				if(null != obj[9]){
					map.put("benOutAmount", (Double)obj[9]);
				}
				if(null != obj[10]){
					map.put("benOutMoney", ((Double)obj[10]).doubleValue());
				}
				if(null != obj[11]){
					map.put("code", obj[11].toString());
				}
				double residue = 0;
				if(null != obj[9] && null != obj[5] && null != obj[7]){
				    residue = (Double)obj[5] + (Double)obj[7] - (Double)obj[9];
				}
				double balance  = 0;
				if(null != obj[8] && null != obj[10]){
					balance = begin + ((Double)obj[8]).doubleValue() - ((Double)obj[10]).doubleValue();
				}
				map.put("residue", residue);
				map.put("balance", balance);
				totalList.add(map);
				adf.setDetailAccountList(totalList);
			}
 		}catch(DataAccessException e){
			logger.error("查询显示详细库存核算信息失败！AccountDepotServiceImpl.selectAccountById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示详细库存核算信息失败！AccountDepotServiceImpl.selectAccountById()");
		} 
		return list;
	}
	//执行添加操作
	public void doAddAccount(AccountDepotForm form) throws BkmisServiceException {
		
		List list = new ArrayList();
		double beginAmount = 0;
		double beginMoney = 0;
		double inputAccount = 0;
		double inputMoney = 0;
		double outAccount = 0;
		double outMoney = 0;
		double residue = 0;
		double balance = 0;
		double qiInMoney = 0;
		double qiOutMoney = 0;
		try{
			list = iaccountDAO.doAddAccount(form);
			if(list != null && list.size()>0){
				Object obj[] = (Object[])list.get(0);
				if( null != obj[0]){
					beginAmount = (Double)obj[0];
				}
				if(null != obj[1]){
					qiInMoney = ((Double)obj[1]).doubleValue();
				}
				if(null != obj[2]){
					qiOutMoney = ((Double)obj[2]).doubleValue();
				}
				if(null != obj[3]){
					inputAccount = (Double)obj[3];
				}
				if(null != obj[4]){
					inputMoney = ((Double)obj[4]).doubleValue();
				}
				if(null != obj[5]){
					outAccount = (Double)obj[5];
				}
				if(null != obj[6]){ 
					outMoney = ((Double)obj[6]).doubleValue();
				}
				beginMoney = qiInMoney-qiOutMoney;
				residue = beginAmount + inputAccount - outAccount;
				balance = beginMoney + inputMoney - outMoney;
			}
			//将数值存入对象
			DepotAdjustAccounts das = new DepotAdjustAccounts();
			//根据月份和年份查询该月的库存结算
			das.setYear(form.getYear());
			das.setMonth(form.getMonth());
			List<DepotAdjustAccounts> templist = iaccountDAO.getAccount(das);
			//如果是更新
			if(templist!=null&&templist.size()>0){
				das = templist.get(0);
				DepotAdjustAccounts temp = (DepotAdjustAccounts)CloneObject.copy(das);
				temp.setBeginAmount(beginAmount);
				temp.setBeginMoney(beginMoney);
				temp.setInputAccount(inputAccount);
				temp.setInputMomey(inputMoney);
				temp.setOutputAccount(outAccount);
				temp.setOutputMoney(outMoney);
				temp.setResidue(residue);
				temp.setBalance(balance);
				temp.setMakeDate(GlobalMethod.getTime());
				temp.setBeginDate(form.getBeginDate());
				temp.setEndDate(form.getEndDate());
				iaccountDAO.logDetail(form.getUserName(),Contants.ADD,"库存结算",Contants.L_Depot,"1",temp);
				das = (DepotAdjustAccounts)CloneObject.copy(temp);
				iaccountDAO.updateObject(das);
			}else{//如果是新增
				das.setBeginAmount(beginAmount);
				das.setBeginMoney(beginMoney);
				das.setInputAccount(inputAccount);
				das.setInputMomey(inputMoney);
				das.setOutputAccount(outAccount);
				das.setOutputMoney(outMoney);
				das.setResidue(residue);
				das.setBalance(balance);
				das.setMakeDate(GlobalMethod.getTime());
				das.setBeginDate(form.getBeginDate());
				das.setEndDate(form.getEndDate());
				das.setLpId(form.getLpId());
				//进行保存操作
				iaccountDAO.saveObject(das);
				//添加系统日志
				try {
					iaccountDAO.logDetail(form.getUserName(),Contants.ADD,"","库存结算","2",das);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//更改出入库状态
			iaccountDAO.updateStatus(form);
			//更新序列表中存放的最后一次结算结束日期
			iaccountDAO.updateAColumn("SysSequence", "value", form.getEndDate(), "code", "yjs");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("添加库存核算信息失败！AccountDepotServiceImpl.doAddAccount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加库存核算信息失败！AccountDepotServiceImpl.doAddAccount()");
		}
	}
	//删除库存核算信息
	public void delDepotAccount(AccountDepotForm form) throws BkmisServiceException {
		//登陆用户名
		String userName = GlobalMethod.NullToSpace(form.getUserName());
		String idsStr = GlobalMethod.NullToSpace(form.getIds());
		String[] ids = idsStr.split(",");
		
		try{
			for(int i = 0;i<ids.length;i++){
				DepotAdjustAccounts das = (DepotAdjustAccounts)iaccountDAO.getObject(DepotAdjustAccounts.class, Integer.parseInt(ids[i]));
				iaccountDAO.logDetail(userName, Contants.DELETE, "库存核算",Contants.L_Depot, "3", das);
				iaccountDAO.update("update DepotInputManager as dim set dim.status = '"+Contants.WAITACCOUNT+"' where 1=1 and dim.date>'" + GlobalMethod.minDate(das.getBeginDate()) + "'");
				iaccountDAO.update("update DepotOutputManager as dom set dom.status = '"+Contants.WAITACCOUNT+"' where 1=1 and dom.date>'" + GlobalMethod.minDate(das.getBeginDate()) +"'");
				iaccountDAO.deleteObject(das);
			}
			//获得剩余的结算信息的最后一天的日期
			String endDate = GlobalMethod.NullToSpace((String)iaccountDAO.getUniqueObject("select max(endDate) from DepotAdjustAccounts"));
			iaccountDAO.updateAColumn("SysSequence", "value", endDate, "code", "yjs");
			
		}catch(Exception e){
			logger.error("删除库存核算信息失败！AccountDepotServiceImpl.delDepotAccount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除库存核算信息失败！AccountDepotServiceImpl.delDepotAccount()");
		}
	}
	//按id查询要删除的详细信息内容
	public List<DepotAdjustAccounts> selectDepotAccount(Integer[] idArray) throws BkmisServiceException {
		
		List<DepotAdjustAccounts> list = new ArrayList<DepotAdjustAccounts>();
		try{
			list = iaccountDAO.selectDepotAccount(idArray);
		}catch (DataAccessException e) {
			logger.error("查询库存核算信息失败！AccountDepotServiceImpl.selectDepotAccount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询库存核算信息失败！AccountDepotServiceImpl.selectDepotAccount()");
		}
		return list;
	}
	
	@Override
	public String getBiggestCode(String string) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		String lastDate;
		lastDate = iaccountDAO.getBiggestCode(string);
		if(lastDate.isEmpty() || lastDate==null || "null".equals(lastDate)){
			lastDate="";
		}
		
		return lastDate; 
		
	}
	public boolean ifAcount(AccountDepotForm form) throws BkmisServiceException{
		
		boolean b = false;
		DepotAdjustAccounts das = new DepotAdjustAccounts();
		//根据月份和年份查询该月的库存结算
		das.setYear(form.getYear());
		das.setMonth(form.getMonth());
		List<DepotAdjustAccounts> templist = iaccountDAO.getAccount(das);
		if(templist!=null&&templist.size()>0){//存在了当前年月的核算记录
			b = true;
		}
		return b;
	}
}
