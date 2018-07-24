package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IBuildsManageDAO;
import com.zc13.bkmis.dao.ICBillDAO;
import com.zc13.bkmis.dao.ISerClientMaintainDAO;
import com.zc13.bkmis.form.CBillForm;
import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.EMaintainDispatch;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.bkmis.mapping.SerMaterialConsume;
import com.zc13.bkmis.mapping.TSendTask;
import com.zc13.bkmis.service.ISerClientMaintainService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class SerClientMaintainServiceImpl extends BasicServiceImpl implements
		ISerClientMaintainService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private ISerClientMaintainDAO iSerClientMaintainDAO;
	private ICBillDAO icBillDao;
	private IBuildsManageDAO ibuildsManageDao;

	public ISerClientMaintainDAO getISerClientMaintainDAO() {
		return iSerClientMaintainDAO;
	}

	public void setISerClientMaintainDAO(ISerClientMaintainDAO serClientMaintainDAO) {
		iSerClientMaintainDAO = serClientMaintainDAO;
	}

	public ICBillDAO getIcBillDao() {
		return icBillDao;
	}

	public void setIcBillDao(ICBillDAO icBillDao) {
		this.icBillDao = icBillDao;
	}

	public IBuildsManageDAO getIbuildsManageDao() {
		return ibuildsManageDao;
	}

	public void setIbuildsManageDao(IBuildsManageDAO ibuildsManageDao) {
		this.ibuildsManageDao = ibuildsManageDao;
	}

	//保存客户报修
	@Override
	public void addClient(SerClientMaintainForm form)
			throws BkmisServiceException {
		
		SerClientMaintain obj = new SerClientMaintain();
		try {
			BeanUtils.copyProperties(obj, form);
			obj.setIsEnabled("1");
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		try {
			iSerClientMaintainDAO.saveObject(obj);
			
			//添加系统日志
			iSerClientMaintainDAO.logDetail(form.getUserName(),Contants.ADD,"客户报修",Contants.L_SERVICE,"2",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//编辑客户报修
	@Override
	public void editClient(SerClientMaintainForm form)
			throws BkmisServiceException {
		
		SerClientMaintain obj = new SerClientMaintain();
		try {
			BeanUtils.copyProperties(obj, form);
			obj.setIsEnabled("1");
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		try {
			//写入系统日志
			try {
				iSerClientMaintainDAO.logDetail(form.getUserName(), Contants.MODIFY,"客户报修",Contants.L_SERVICE, "1", obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iSerClientMaintainDAO.updateObject(obj);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	
	}
	
	//保存客户报修(点击派工，处理，客户反馈按钮执行此方法)
	@Override
	public void dealClient(HttpServletRequest request,SerClientMaintainForm form) throws BkmisServiceException {
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat2.format(new Date());
		SerClientMaintain obj = new SerClientMaintain();
		obj.setIsEnabled("1");
		try {
			//将form中的值复制到实体对象中
			try {
				BeanUtils.copyProperties(obj, form);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//添加系统日志
			try {
				iSerClientMaintainDAO.logDetail(form.getUserName(),Contants.DOMAINTAIN,"客户报修",Contants.L_SERVICE,"1",obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iSerClientMaintainDAO.updateObject(obj);
			
			String status = GlobalMethod.NullToSpace(form.getStatus());
			//如果是客户报修的状态为维修中,则需要更新维修派工信息和员工状态信息
			if(status.equals(Contants.BEING_REPAIRED)){
				String oldSendingMan = GlobalMethod.NullToSpace(form.getOldSendingMan());//修改前保存的状态为已派遣的员工id信息
				String newSendingMan = GlobalMethod.NullToSpace(form.getNewSendingMan());//修改后的状态为已派遣的员工id信息
				if(!oldSendingMan.equals("")){
					iSerClientMaintainDAO.update("update HrPersonnel set isOut='"+Contants.ISNOTOUT+"' where personnelId in("+oldSendingMan+")");
					iSerClientMaintainDAO.update("delete from EMaintainDispatch where hrPersonnel.personnelId in("+oldSendingMan+") and status='"+Contants.IS_DISPATCH+"'");
				}
				if(!newSendingMan.equals("")){
					iSerClientMaintainDAO.update("update HrPersonnel set isOut='"+Contants.ISOUT+"' where personnelId in("+newSendingMan+")");
					String[] newSends = newSendingMan.split(",");
					for(int i=0;i<newSends.length;i++){
						EMaintainDispatch e = new EMaintainDispatch();
						e.setHrPersonnel((HrPersonnel)iSerClientMaintainDAO.getObject(HrPersonnel.class, Integer.parseInt(newSends[i])));
						e.setSerClientMaintain(obj);
						e.setStatus(Contants.IS_DISPATCH);
						iSerClientMaintainDAO.saveObject(e);
					}
				}
			}
			
			//如果客户报修的状态为维修等待，则需要将派工维修信息更改为派工结束，更改已派出的员工状态为未派出，更新已派出的员工的离场时间为当前时间，算出员工本次的工作时间
			if(status.equals(Contants.REPAIR_WAIT)){
				List<EMaintainDispatch> list = null;
				list = iSerClientMaintainDAO.findObject("from EMaintainDispatch where serClientMaintain.id="+form.getId()+" and status='"+Contants.PERSONNEL_IS_REACH+"'");
				String appearance_time = "";
				for(int i = 0;i<list.size();i++){
					appearance_time = list.get(i).getAppearanceTime();
					HrPersonnel personnel = (HrPersonnel)iSerClientMaintainDAO.getObject(HrPersonnel.class, list.get(i).getHrPersonnel().getPersonnelId());
					personnel.setIsOut(Contants.ISNOTOUT);
					iSerClientMaintainDAO.updateObject(personnel);
				}
				double workHours = 0;//本次工作时间
				try {
					workHours = DateUtil.getInterval(appearance_time, date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//将工作时间保留2位小数
				workHours = DateUtil.round(workHours, 2);
				iSerClientMaintainDAO.update("update EMaintainDispatch set leaveTime='"+date+"',status='"+Contants.PERSONNEL_IS_LEAVE+"',workHours="+workHours+" where serClientMaintain.id="+form.getId()+" and status='"+Contants.PERSONNEL_IS_REACH+"'");
			}
			
			//保存材料消耗
			saveConsume(request,form);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * 根据报修id获得报修的总的服务小时数
	 * @param maintainId
	 */
	public double getTotalHours(Integer maintainId){
		List<EMaintainDispatch> list = null;
		double totalHours = 0;
		try {
			list = iSerClientMaintainDAO.findObject("from EMaintainDispatch where serClientMaintain.id="+maintainId+" order by serClientMaintain.id,appearanceTime");
			if(list!=null&&list.size()>0){
				String appearanceTime = "";
				for(int i = 0;i<list.size();i++){
					if(!appearanceTime.equals(list.get(i).getAppearanceTime())){
						appearanceTime = list.get(i).getAppearanceTime();
						totalHours += list.get(i).getWorkHours();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return totalHours;
	}

	//查询客户报修列表
	@Override
	public List<SerClientMaintain> getClientList(SerClientMaintainForm form,boolean isPage) throws BkmisServiceException {
		List<SerClientMaintain> list = null;
		try {
			list = iSerClientMaintainDAO.getClientList(form,isPage);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
	}

	//查询客户报修列表总数
	@Override
	public int getCountList(SerClientMaintainForm form) throws BkmisServiceException {
		
		List<SerClientMaintain> list = iSerClientMaintainDAO.getClientList(form,false);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}
	
	//根据客户报修项目id查询材料消耗列表
	@Override
	public List<SerMaterialConsume> getConsume(String id) throws BkmisServiceException {
		int id1 = Integer.parseInt(id);
		return iSerClientMaintainDAO.getConsume(id1);
	}

	//根据id得到材料类别
	@Override
	public DepotMaterial selectMaterialById(String id)
			throws BkmisServiceException {
		
		int id1 = Integer.parseInt(id); 
		return (DepotMaterial)iSerClientMaintainDAO.getObject(DepotMaterial.class, id1);
	}
	
	/**
	 * 保存保修时对材料的消耗
	 * SerClientMaintainServiceImpl.saveConsume
	 */
	@Override
	public void saveConsume(HttpServletRequest request, SerClientMaintainForm form2)
			throws BkmisServiceException {
		
		String id = request.getParameter("id");
		List<SerMaterialConsume> list = iSerClientMaintainDAO.getConsume(Integer.parseInt(id));
		for(int i=0;i<list.size();i++){
			iSerClientMaintainDAO.deleteObject(list.get(i));
		}
		String department = request.getParameter("department"); 
		String[] materialCode = request.getParameterValues("materialCode");
		String[] materialName = request.getParameterValues("materialName");
		String[] spec = request.getParameterValues("spec");
		String[] unitPrice = request.getParameterValues("unitPrice");
		String[] amount = request.getParameterValues("amount");
		String[] surchargeWay = request.getParameterValues("surchargeWay");
		String[] surchargeAmount = request.getParameterValues("surchargeAmount");
		String[] amountMoney = request.getParameterValues("amount_money");
		if(materialName!=null&&materialName.length>0){
			for(int i=0;i<spec.length;i++){
				SerMaterialConsume bean = new SerMaterialConsume();
				bean.setSerClientMaintain((SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class,Integer.parseInt(id)));
				bean.setDepartment(department);
				bean.setAmount(Double.parseDouble(amount[i]));
				bean.setAmountMoney(Float.parseFloat(amountMoney[i]));
				bean.setMaterialCode(materialCode[i]);
				bean.setUnitPrice(Float.parseFloat(unitPrice[i]));
				bean.setSpec(spec[i]);
				bean.setSurchargeWay(surchargeWay[i]);
				bean.setSurchargeAmount(Double.parseDouble(GlobalMethod.NullToParam(surchargeAmount[i], "0")));
				bean.setMaterialName(materialName[i]);
				ERooms eRooms = null;
				if(form2.getRoomId()!=null&&form2.getRoomId()!=0){
					eRooms = (ERooms)iSerClientMaintainDAO.getObject(ERooms.class,form2.getRoomId());
					bean.setRoomName(eRooms.getRoomFullName());
				}
				String area = form2.getArea();
				bean.setAreaName(area);
				bean.setOutDate(form2.getSendTime());
				iSerClientMaintainDAO.saveObject(bean);
			}
		}
	}

	@Override
	public SerClientMaintain getClientMaintainById(String id,String forward)
			throws BkmisServiceException {
		int id1 = Integer.parseInt(id);
		SerClientMaintain obj = null;
		obj = (SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class, id1);
		
		return obj;
	}

	//查询材料出处列表
	@Override
	public List<SerMaterialConsume> queryConsume(SerClientMaintainForm form,boolean isPage) throws BkmisServiceException {
		List<SerMaterialConsume> list = null;
		try {
			list = iSerClientMaintainDAO.queryConsume(form,isPage);
			if(isPage){
				int totalcount = 0;
				List list2 = iSerClientMaintainDAO.queryConsume(form,false);
				if(list2!=null&&list2.size()>0){
					totalcount = list2.size();
				}
				form.setTotalcount(totalcount);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
		
	}

	@Override
	public void delete(SerClientMaintain bean) throws BkmisServiceException {
		
		iSerClientMaintainDAO.deleteObject(bean);
		iSerClientMaintainDAO.updateTask("maintain", -1);
	}

	//查询材料出处,用于导出报表
	public List<SerMaterialConsume> explorMaterialList(String department,
			String materialName, String begin, String end)
			throws BkmisServiceException {
		
		List<SerMaterialConsume> list = iSerClientMaintainDAO.explorMaterialList(department, materialName, begin, end);
		return list;
	}

	@Override
	public void setLeaveTime(SerClientMaintainForm form2) throws BkmisServiceException {
		if(form2!=null&&form2.getId()!=null&&form2.getId()!=0){
			SerClientMaintain obj = (SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class, form2.getId());
			obj.setLeaveTime(form2.getLeaveTime());
			obj.setManHour(form2.getManHour());
			obj.setStatus(Contants.REPAIR_COMPLETED);//将状态设为已离场
			
			//记录日志
			StringBuffer logStr = new StringBuffer("报修人为：").append(obj.getName()).append(" 报修时间为：").append(obj.getDate());
			logStr.append(" 报修项目名称为：").append(obj.getProject()).append(" 的维修人员已离场，离场时间为:").append(form2.getLeaveTime());
			iSerClientMaintainDAO.log(form2.getUserName(), "离场", logStr.toString(), Contants.L_SERVICE, "客户报修");
			
			List<EMaintainDispatch> dispatchlist = iSerClientMaintainDAO.findObject("from EMaintainDispatch where serClientMaintain.id="+form2.getId()+" and status='"+Contants.PERSONNEL_IS_REACH+"'");
			if(dispatchlist!=null&&dispatchlist.size()>0){
				for(int i = 0;i<dispatchlist.size();i++){
					EMaintainDispatch em = dispatchlist.get(i);
					//将员工状态置为未派出
					iSerClientMaintainDAO.update("update HrPersonnel set isOut='"+Contants.ISNOTOUT+"' where personnelId="+em.getHrPersonnel().getPersonnelId());
					//将派遣信息置为已离场
					em.setWorkHours(Double.parseDouble(form2.getManHour()));
					em.setLeaveTime(form2.getLeaveTime());
					em.setStatus(Contants.PERSONNEL_IS_LEAVE);
					iSerClientMaintainDAO.updateObject(em);
				}
			}
			/*生成账单start*/
			//客户报修中的派工单编号(sendcardCode)对应账单表中的申请单号(billCode)
			//根据派工单号来查询账单中是否有对应的账单，如果有，则不再生产，如果没有，则生产账单
			CBillForm formbean = new CBillForm();
			formbean.setBillCode(obj.getSendcardCode());
			List list = icBillDao.getExcelList(formbean, null);
			//如果根据派工单号不能查到账单，则需要生产账单
			if((list==null||list.size()<=0)&&obj.getRoomId()!=null&&obj.getRoomId()!=0){
				//根据房间id找到客户
				List<Map> list1 = ibuildsManageDao.getClientAndRoomById(obj.getRoomId());
				if(list1!=null&&list1.size()>0){
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String today = dateFormat.format(new Date());
					CItems item = null;
					List itemList = iSerClientMaintainDAO.findObject("from CItems where itemName='维修服务费'");
					//查询是否有维修服务费的收费项，如果有，则取第一个，如果没有，则增加一个收费项
					if(itemList!=null&&itemList.size()>0){
						item = (CItems)itemList.get(0);
					}else{
						item = new CItems();
						item.setItemCode("WXF001");
						item.setItemName("维修服务费");
						item.setCountPaymentrate("1");
						item.setIndexs(11);
						item.setLpId(obj.getLpId());
						item.setValue("repair_services");
						iSerClientMaintainDAO.saveObject(item);
					}
					
					
					/*计算服务费总金额start*/
					double charge = 0.0;//服务费总额
					double hours = this.getTotalHours(obj.getId());
					obj.setManHour(String.valueOf(hours));
					//如果是物业维修，则需要缴纳服务费
					if(Contants.DOMETHOD_MAINTENANCE.equals(obj.getDoMethod())){
						SerMaintainProject project = (SerMaintainProject)iSerClientMaintainDAO.getObject(SerMaintainProject.class, obj.getProjectId());
						//如果是有偿的服务
						if(Contants.PROJECT_TYPE_PAID.equals(project.getType())){
							//如果是按小时
							if(Contants.CHARGE_TYPE_HOUR.equals(project.getChargeType())){
								charge = project.getRate()*hours;
							}else{
								charge = project.getRate();
							}
						}
					}
					obj.setAmountRate(charge);
					/*计算服务费总金额end*/
					
					double billsExpenses = 0;
					billsExpenses = obj.getAmountMoney()+obj.getAmountRate();
					if(billsExpenses>0.001){
						CBill bill = new CBill();
						CompactClient client = new CompactClient();
						client.setId(Integer.decode(String.valueOf(list1.get(0).get("client_id"))));
						bill.setCompactClient(client);// 客户
						bill.setBillCode(obj.getSendcardCode());//申请单号
						bill.setEntryUserId(form2.getUserId());
						bill.setStatus("0");// 单据状态
						bill.setBillDate(today.substring(0, 7));// 账单月份
						bill.setBillsExpenses(billsExpenses);// 合同金额
						bill.setCreateDate(today);// 账单日期
						bill.setActuallyPaid(0);// 实付金额
						bill.setItemId(item.getId());//收费项
						bill.setLpId(obj.getLpId());
						iSerClientMaintainDAO.saveObject(bill);
					}
				}
			}
			/*生成账单end*/
			iSerClientMaintainDAO.updateObject(obj);
		}
	}

	@Override
	public void setReachTime(SerClientMaintainForm form2) throws BkmisServiceException {
		if(form2!=null&&form2.getId()!=null&&form2.getId()!=0){
			SerClientMaintain obj = (SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class, form2.getId());
			obj.setAppearanceTime(form2.getAppearanceTime());
			iSerClientMaintainDAO.updateObject(obj);
			iSerClientMaintainDAO.update("update EMaintainDispatch set appearanceTime='"+form2.getAppearanceTime()+"',status='"+Contants.PERSONNEL_IS_REACH+"' where serClientMaintain.id="+form2.getId()+" and status='"+Contants.IS_DISPATCH+"'");
			//记录日志
			StringBuffer logStr = new StringBuffer("报修人为：").append(obj.getName()).append(" 报修时间为：").append(obj.getDate());
			logStr.append(" 报修项目名称为：").append(obj.getProject()).append(" 的维修人员已到场，到场时间为:").append(form2.getAppearanceTime());
			iSerClientMaintainDAO.log(form2.getUserName(), "到场", logStr.toString(), Contants.L_SERVICE, "客户报修");
		}
	}

	//发送短信
	@Override
	public void sendMessage(SerClientMaintainForm form) throws BkmisServiceException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String names = GlobalMethod.NullToSpace(form.getNames());
		String phones = GlobalMethod.NullToSpace(form.getPhones());
		String[] nameArr = names.split(";");
		String[] phoneArr = phones.split(";");
		if(nameArr.length>0){
			for(int i = 0;i<nameArr.length;i++){
				TSendTask sendTask = new TSendTask();
				sendTask.setDestNumber(phoneArr[i]);//手机号码
				sendTask.setContent(form.getContents());//发送内容
				sendTask.setSendTime(new Date());//发送时间
				sendTask.setSendPriority((short)16);
				sendTask.setValidMinute(0);
				sendTask.setPushUrl("");
				sendTask.setCommPort((short)0);//指定发送此任务的端口,缺省为0表示自动选择端口发送
				sendTask.setSplitCount((short)0);//拆分发送的总条数
				sendTask.setPersonnelName(nameArr[i]);
				sendTask.setLpId(form.getLpId());
				iSerClientMaintainDAO.saveObject(sendTask);
			}
		}
		
	}

	//根据报修id获得客户报修的状态，有效状态和派工状态 
	@Override
	public String getRepairAndDispatchStatusByMaintainId(String maintainId) throws BkmisServiceException {
		SerClientMaintain maintain = null;
		String statuss = "";
		try {
			maintain = (SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class, Integer.parseInt(maintainId));
			statuss += maintain.getStatus()+","+maintain.getIsEnabled()+",";
			List<EMaintainDispatch> list = iSerClientMaintainDAO.findObject("from EMaintainDispatch t where t.serClientMaintain.id="+maintain.getId());
			if(list!=null&&list.size()>0){
				boolean flag = true;
				for(int i = 0;i<list.size();i++){
					EMaintainDispatch obj = list.get(i);
					if(Contants.IS_DISPATCH.equals(obj.getStatus())||Contants.PERSONNEL_IS_REACH.equals(obj.getStatus())){
						statuss += obj.getStatus();
						flag = false;
						break;
					}
				}
				if(flag){//如果没有已派出或已到场状态的派工信息，则派工状态为已离场
					statuss += Contants.PERSONNEL_IS_LEAVE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return statuss;
	}

	@Override
	public void setInvalid(String maintainId,String userName) throws BkmisServiceException {
		SerClientMaintain maintain = null;
		try {
			maintain = (SerClientMaintain)iSerClientMaintainDAO.getObject(SerClientMaintain.class, Integer.parseInt(maintainId));
			maintain.setIsEnabled("0");//置为无效
			iSerClientMaintainDAO.updateObject(maintain);
			List<EMaintainDispatch> dispatchlist = iSerClientMaintainDAO.findObject("from EMaintainDispatch where serClientMaintain.id="+Integer.parseInt(maintainId)+" and status<>'"+Contants.PERSONNEL_IS_LEAVE+"'");
			if(dispatchlist!=null&&dispatchlist.size()>0){
				for(int i = 0;i<dispatchlist.size();i++){
					EMaintainDispatch em = dispatchlist.get(i);
					//将员工状态置为未派出
					iSerClientMaintainDAO.update("update HrPersonnel set isOut='"+Contants.ISNOTOUT+"' where personnelId="+em.getHrPersonnel().getPersonnelId());
					iSerClientMaintainDAO.deleteObject(em);
					//将派遣信息置为已离场
//					em.setWorkHours(Double.parseDouble(form2.getManHour()));
//					em.setLeaveTime(form2.getLeaveTime());
//					em.setStatus(Contants.PERSONNEL_IS_LEAVE);
//					iSerClientMaintainDAO.updateObject(em);
				}
			}
			
			//记录日志
			StringBuffer logStr = new StringBuffer("报修人为：").append(maintain.getName()).append(" 报修时间为：").append(maintain.getDate());
			logStr.append(" 报修项目名称为：").append(maintain.getProject()).append(" 的维修项目置为无效");
			iSerClientMaintainDAO.log(userName, "置为无效", logStr.toString(), Contants.L_SERVICE, "客户报修");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		} 
	}
	@Override
	public List<EMaintainDispatch> getDispatchListByMaintainId(String id) throws BkmisServiceException {
		List<EMaintainDispatch> list = null;
		try {
			list = iSerClientMaintainDAO.findObject("from EMaintainDispatch where serClientMaintain.id="+Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
	}
	/**
	 * 程序生成新编号
	 * @return
	 * Date:Mar 5, 2012 
	 * Time:10:18:58 AM
	 */
	public String getNewCode() {
		String queryString = "select max(sendcardCode) from SerClientMaintain where len(sendcardCode)=11 and substring(sendcardCode, 0, 4)='201'";
		String oldCode =  (String) iSerClientMaintainDAO.getUniqueObject(queryString);
		String newCode = GlobalMethod.getNewCode4Day("", oldCode, 1000);
		return newCode;
	}
	@Override
	public void updateDepotMaterial(String bh, String sl) {
		 iSerClientMaintainDAO.updateDepotMaterial(bh, sl);
	}
	@Override
	public String getDostack(String code) {
		return iSerClientMaintainDAO.getDostack(code);
	}
}
