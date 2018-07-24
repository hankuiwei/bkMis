package com.zc13.bkmis.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zc13.bkmis.dao.IPhoneCostDAO;
import com.zc13.bkmis.form.PhoneCostForm;
import com.zc13.bkmis.mapping.CPhoneParameter;
import com.zc13.bkmis.mapping.CRegionCode;
import com.zc13.bkmis.mapping.CServiceProvider;
import com.zc13.bkmis.mapping.ECallInfo;
import com.zc13.bkmis.mapping.ECallInfoLog;
import com.zc13.bkmis.service.IPhoneCostService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PropertiesReader;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * 电话计费service
 * @author wangzw
 * @Date Oct 10, 2011
 * @Time 4:22:51 PM
 */
public class PhoneCostServiceImpl  extends BasicServiceImpl implements IPhoneCostService{
	Logger logger = Logger.getLogger(this.getClass());
	private IPhoneCostDAO phoneCostDAO;
	
	public IPhoneCostDAO getPhoneCostDAO() {
		return phoneCostDAO;
	}

	public void setPhoneCostDAO(IPhoneCostDAO phoneCostDAO) {
		this.phoneCostDAO = phoneCostDAO;
	}

	//获取电话交换机的通话信息并保存到数据库中
	@Override
	public void storeCallInfo(String sDate,String eDate) throws BkmisServiceException {
		PropertiesReader properties = null;
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//获取当前日期
			String nowDateStr = format.format(new Date());
			
			//读取配置文件
			properties = new PropertiesReader("/propertyInfo.properties");
			String path = properties.getValue("phoneInfoPath");
			path = new String(path.getBytes("ISO-8859-1"),"utf-8");
			//path = "smb:"+path;
			//获取通话记录日志表
			ECallInfoLog log = (ECallInfoLog)phoneCostDAO.getUniqueObject("from ECallInfoLog");
			if(log == null){
				log = new ECallInfoLog();
				log.setEndDate("2011-09-01");
				log.setReachRow(0);
			}
			//获取运营商信息
			List serviceProviderList = phoneCostDAO.getServiceProviderInfo(null, false);
			CServiceProvider provider = null;
			if(serviceProviderList!=null&&serviceProviderList.size()>0){
				provider = (CServiceProvider)serviceProviderList.get(0);
			}
			//获取资费参数列表
			List phoneParameterList = phoneCostDAO.getPhoneParameterInfo(null, false);
			//获取区号列表
			List regionCodeList = phoneCostDAO.getRegionCodeInfo(null, false);
			//获取开始日期
			String startDateStr = GlobalMethod.NullToSpace(log.getEndDate()).equals("")?nowDateStr:log.getEndDate();
			Calendar startDate = Calendar.getInstance();
			startDate.set(Integer.parseInt(startDateStr.substring(0, 4)), Integer.parseInt(startDateStr.substring(5, 7))-1, Integer.parseInt(startDateStr.substring(8, 10)));
			
			Calendar paramStartDate = Calendar.getInstance();
			try{
				paramStartDate.setTime(format.parse(sDate));
				startDate = paramStartDate.after(startDate)?paramStartDate:startDate;
			}catch(Exception e){}
			//获取当前时间
			Calendar nowDate = Calendar.getInstance();
			nowDate.set(Integer.parseInt(nowDateStr.substring(0, 4)), Integer.parseInt(nowDateStr.substring(5, 7))-1, Integer.parseInt(nowDateStr.substring(8, 10)));
			Calendar paramEndDate = Calendar.getInstance();
			try{
				paramEndDate.setTime(format.parse(eDate));
				nowDate = paramEndDate.after(nowDate)?nowDate:paramEndDate;
			}catch(Exception e){}
			//获取上次到达的行数
			int reachRow = log.getReachRow();
			//获取上次最后的日期
			String endDate = log.getEndDate();
			//获取开始的行数
			int startRow = log.getReachRow()+1;
			while(!startDate.after(nowDate)){
				SmbFile file = new SmbFile(path+format.format(startDate.getTime())+"-2533.TXT");
				//如果文件存在
				if(file.exists()){
					SmbFileInputStream in = null;
					try{
						in = new SmbFileInputStream (file);
//						in = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\share\\"+format.format(startDate.getTime())+"-2533.TXT")));
						String str = "";
						char c;
						int a;
						int count = 0;
						while((a=in.read())!=-1){
							c = (char)a;
							if(c!=13){//如果不是回车
								str += c;
							}else{
								if(!GlobalMethod.NullToSpace(str).trim().equals("")&&count+1>=startRow){
									str = str.trim();
									ECallInfo obj = resolveCallInfoStr(str);
									if(obj!=null){
										String calledPhone = GlobalMethod.NullToSpace(obj.getCalledPhone());//被叫
										/**匹配被叫字头，计算费用 start*/
										//是否已被匹配
										boolean isPP = false;
										if(phoneParameterList!=null&&phoneParameterList.size()>0){
											for(int i = 0;i<phoneParameterList.size();i++){
												CPhoneParameter parameter = (CPhoneParameter)phoneParameterList.get(i);
												String prefix = GlobalMethod.NullToSpace(parameter.getPrefix()).trim();//字头
												if(calledPhone.length()>=prefix.length()&&prefix.equals(calledPhone.substring(0, prefix.length()))){
													obj.setCost(calculationCost(obj.getCallTime(),parameter.getFrontSeconds(),parameter.getFrontCost(),parameter.getNextEachSeconds(),parameter.getEachCost()));
													isPP = true;
													break;
												}
											}
										}
										if(!isPP){
											obj.setCost(calculationCost(obj.getCallTime(),provider.getFrontSeconds(),provider.getFrontCost(),provider.getNextEachSeconds(),provider.getEachCost()));
										}
										obj.setIsPaid(Contants.ISNOTPAID);
										/**匹配被叫字头,计算费用 end*/
										
										/**匹配区号 start*/
										//是否已被匹配
										boolean isPPQH = false;
										if(regionCodeList!=null&&regionCodeList.size()>0){
											for(int i = 0;i<regionCodeList.size();i++){
												CRegionCode regionCode = (CRegionCode)regionCodeList.get(i);
												String areaCode = GlobalMethod.NullToSpace(regionCode.getAreaCode()).trim();//区号
												if(calledPhone.length()>=areaCode.length()&&areaCode.equals(calledPhone.substring(0, areaCode.length()))){
													obj.setAreaName(regionCode.getAreaName());
													isPPQH = true;
													break;
												}
											}
										}
										if(!isPPQH){
											obj.setAreaName("市话");
										}
										/**匹配区号 end*/
										phoneCostDAO.saveObject(obj);
									}
								}
								count+=2;
								str = "";
							}
						}
						reachRow = count;
						endDate = format.format(startDate.getTime());
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try{in.close();}catch(Exception e){}
					}
				}
				startDate.add(Calendar.DAY_OF_MONTH, 1);
				startRow = 1;
			}
			//将本次执行后的日期和到达的行数存储到数据库中
			log.setEndDate(endDate);
			log.setReachRow(reachRow);
			phoneCostDAO.saveObject(log);
		}catch(Exception e){
			e.printStackTrace();
//			throw new BkmisServiceException();
		}
	}
	
	//获取区域名称
	public String getAreaName(String phone,List<CRegionCode> regionCodeList){
		String areaName = "";
		if(!GlobalMethod.NullToSpace(phone).equals("")){
			//获取区号列表
			if(regionCodeList==null||regionCodeList.size()<=0){
				regionCodeList = phoneCostDAO.getRegionCodeInfo(null, false);
			}
			boolean isPPQH = false;
			if(regionCodeList!=null&&regionCodeList.size()>0){
				for(int i = 0;i<regionCodeList.size();i++){
					CRegionCode regionCode = (CRegionCode)regionCodeList.get(i);
					String areaCode = GlobalMethod.NullToSpace(regionCode.getAreaCode()).trim();//区号
					if(phone.length()>=areaCode.length()&&areaCode.equals(phone.substring(0, areaCode.length()))){
						areaName = regionCode.getAreaName();
						isPPQH = true;
						break;
					}
				}
			}
			if(!isPPQH){
				areaName = "市话";
			}
		}
		return areaName;
	}
	
	//获取通话费用
	@Override
	public double getCost(String phone, int callTime,List<CPhoneParameter> phoneParameterList,List<CServiceProvider> serviceProviderList)
			throws BkmisServiceException {
		double returnValue = 0.0;
		if(!GlobalMethod.NullToSpace(phone).equals("")&&callTime>0){
			//获取资费参数列表
			if(phoneParameterList==null||phoneParameterList.size()<=0){
				phoneParameterList = phoneCostDAO.getPhoneParameterInfo(null, false);
			}
			//获取运营商信息
			if(serviceProviderList==null||serviceProviderList.size()<=0){
				serviceProviderList = phoneCostDAO.getServiceProviderInfo(null, false);
			}
			CServiceProvider provider = null;
			if(serviceProviderList!=null&&serviceProviderList.size()>0){
				provider = (CServiceProvider)serviceProviderList.get(0);
			}
			
			/**匹配被叫字头，计算费用 start*/
			//是否已被匹配
			boolean isPP = false;
			String finalPrefix = "";
			int frontSeconds = 0;
			double frontCost = 0;
			int nextEachSeconds = 0;
			double eachCost = 0;
			if(phoneParameterList!=null&&phoneParameterList.size()>0){
				for(int i = 0;i<phoneParameterList.size();i++){
					CPhoneParameter parameter = (CPhoneParameter)phoneParameterList.get(i);
					String prefix = GlobalMethod.NullToSpace(parameter.getPrefix()).trim();//字头
					if(phone.length()>=prefix.length()&&prefix.equals(phone.substring(0, prefix.length()))){
						if(prefix.length()>finalPrefix.length()){
							finalPrefix = prefix;
							frontSeconds = parameter.getFrontSeconds();
							frontCost = parameter.getFrontCost();
							nextEachSeconds = parameter.getNextEachSeconds();
							eachCost = parameter.getEachCost();
						}
						//returnValue = calculationCost(callTime,parameter.getFrontSeconds(),parameter.getFrontCost(),parameter.getNextEachSeconds(),parameter.getEachCost());
						isPP = true;
						//break;
					}
				}
			}
			if(!isPP){
				returnValue = calculationCost(callTime,provider.getFrontSeconds(),provider.getFrontCost(),provider.getNextEachSeconds(),provider.getEachCost());
			}else{
				returnValue = calculationCost(callTime,frontSeconds,frontCost,nextEachSeconds,eachCost);
			}
			/**匹配被叫字头,计算费用 end*/
		}
		return returnValue;
	}
	
	
	
	//解析通话信息的字符串，并封装
	public ECallInfo resolveCallInfoStr(String str) throws ParseException{
		ECallInfo obj = null;
		if(!GlobalMethod.NullToSpace(str).equals("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat format2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			obj = new ECallInfo();
			obj.setCallerPhone(GlobalMethod.NullToSpace(str.substring(35, 65)).trim());
			obj.setCalledPhone(GlobalMethod.NullToSpace(str.substring(5, 35)).trim());
			obj.setStartTime(GlobalMethod.NullToSpace(str.substring(441, 458)).trim());
			obj.setStartTime(dateFormat.format(format2.parse(obj.getStartTime())));
			obj.setEndTime(GlobalMethod.NullToSpace(str.substring(169, 186)).trim());
			obj.setEndTime(dateFormat.format(format2.parse(obj.getEndTime())));
			obj.setCallTime(Integer.parseInt(GlobalMethod.NullToSpace(str.substring(205, 211)).trim()));
			obj.setEnterTime(dateFormat.format(new Date()));
		}
		return obj;
	}
	
	/**
	 * 计算费用
	 * @param seconds 通话秒数
	 * @param frontSeconds 前x秒
	 * @param frontCost 前x秒费用
	 * @param nextEachSeconds 之后每x秒
	 * @param eachCost 之后每x秒费用
	 * @return
	 * Date:Nov 14, 2011 
	 * Time:1:56:50 AM
	 */
	public double calculationCost(int seconds,int frontSeconds,double frontCost,int nextEachSeconds,double eachCost){
		double returnVal = 0;
		if(seconds==0){
			returnVal = 0;
		}else if(seconds<=frontSeconds){
			returnVal = frontCost;
		}else{
			returnVal += frontCost;
			//剩余秒数
			int sySeconds = seconds-frontSeconds;
			int a = sySeconds/nextEachSeconds;
			int b = sySeconds%nextEachSeconds;
			if(b>0){
				returnVal += (a+1)*eachCost;
			}else{
				returnVal += a*eachCost;
			}
		}
		return returnVal;
	}
	

	//手动将指定时间段内没有同步到数据库的话单同步进去
	@Override
	public void buildCallInfo(PhoneCostForm form1) throws BkmisServiceException {
		try{
			storeCallInfo(null,form1.getCxEndDate());
		}catch(Exception e){
			throw new BkmisServiceException("手动将指定时间段内没有同步到数据库的话单同步进去失败",e);
		}
	}

	//根据最新的区号设置更新指定时间段内的地区名称，如果未指定时间段，则默认更新所有
	@Override
	public void updateAreaName(PhoneCostForm form1)
			throws BkmisServiceException {
		try{
			List<ECallInfo> list = phoneCostDAO.getCallInfoObj(form1, false);
			List<CRegionCode> regionCodeList = phoneCostDAO.getRegionCodeInfo(null, false);
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					ECallInfo e = list.get(i);
					String areaName = this.getAreaName(e.getCalledPhone(), regionCodeList);
					e.setAreaName(areaName);
					phoneCostDAO.saveObject(e);
				}
			}
		}catch(Exception e){
			throw new BkmisServiceException("根据最新的区号设置更新指定时间段内的地区名称失败",e);
		}
	}

	//根据最新的资费标准更新指定时间段内的话费金额，如果未指定时间段，则默认更新所有
	@Override
	public void updatePhoneCost(PhoneCostForm form1)
			throws BkmisServiceException {
		try{
			List<ECallInfo> list = phoneCostDAO.getCallInfoObj(form1, false);
			//获取运营商信息
			List serviceProviderList = phoneCostDAO.getServiceProviderInfo(null, false);
			//获取资费参数列表
			List phoneParameterList = phoneCostDAO.getPhoneParameterInfo(null, false);
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					ECallInfo e = list.get(i);
					double d = this.getCost(e.getCalledPhone(), e.getCallTime(), phoneParameterList, serviceProviderList);
					e.setCost(d);
					phoneCostDAO.saveObject(e);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException("根据最新的资费标准更新指定时间段内的话费金额",e);
		}
	}
	
	//获取通话信息实例
	@Override
	public ECallInfo getCallInfo(PhoneCostForm form1)
			throws BkmisServiceException {
		ECallInfo callInfo = new ECallInfo();
		if(form1!=null&&form1.getId()!=null&&form1.getId()!=0){
			callInfo = (ECallInfo)phoneCostDAO.getObject(ECallInfo.class, form1.getId());
		}
		form1.setCallInfo(callInfo);
		return callInfo;
	}

	//保存通话信息
	@Override
	public void saveCallInfo(PhoneCostForm form1) throws BkmisServiceException {
		try {
			if(new Integer(0).equals(form1.getCallInfo().getId())){
				form1.getCallInfo().setId(null);
			}
			if(GlobalMethod.NullToSpace(form1.getCallInfo().getEnterTime()).equals("")){
				form1.getCallInfo().setEnterTime(GlobalMethod.getTime3());
			}
			phoneCostDAO.saveOrUpdateObject(form1.getCallInfo());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException("保存通话信息失败！",e);
		}	
	}
	
	//删除资费信息
	@Override
	public void deleteCallInfo(PhoneCostForm form1)
			throws BkmisServiceException {
		if(form1!=null&&form1.getCheckbox()!=null){
			Integer[] checkbox = form1.getCheckbox();
			for(int i = 0;i<checkbox.length;i++){
				ECallInfo cct = new ECallInfo();
				cct.setId(checkbox[i]);
				try {
					phoneCostDAO.deleteObject(cct);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BkmisServiceException("删除通话信息失败！",e);
				}
			}
		}
	}
	
	//每部电话在指定时间段内产生的总通话时长和总费用列表
	@Override
	public List getPhoneCostInfoList(PhoneCostForm form, boolean isPage)
			throws BkmisServiceException {
		List roomPhoneList = null;
		roomPhoneList = phoneCostDAO.getRoomPhoneInfo(form);
		List phoneCostList = new ArrayList();
		double totalCost = 0;
		if(roomPhoneList!=null&&roomPhoneList.size()>0){
			//循环房间的电话信息
			for(int i = 0;i<roomPhoneList.size();i++){
				Map map1 = (Map)roomPhoneList.get(i);
				String phoneNumber = GlobalMethod.ObjToStr(map1.get("phoneNumber"));
				if(!phoneNumber.equals("")){
					String[] phones = phoneNumber.split(";");
					for(int a = 0;a<phones.length;a++){
						Map tempMap = new HashMap();
						tempMap = cloneMap(map1);
						tempMap.put("callTime", 0.0);
						tempMap.put("cost", 0.0);
						tempMap.put("phoneNumber", phones[a]);
						form.setCxPhoneNumber(phones[a]);
						List tempList = phoneCostDAO.getPhoneCostInfo(form);
						if(tempList!=null&&tempList.size()>0){
							Object[] obj = (Object[])tempList.get(0);
							tempMap.put("callTime", GlobalMethod.ObjToStr(obj[0]).equals("")?0:obj[0]);
							tempMap.put("cost", GlobalMethod.ObjToStr(obj[1]).equals("")?0:obj[1]);
							totalCost += Double.parseDouble(GlobalMethod.ObjToParam(obj[1], "0"));
						}
						phoneCostList.add(tempMap);
					}
				}
			}
		}
		form.setTotalCost(totalCost);
		form.setTotalcount(phoneCostList.size());
		List returnList = new ArrayList();
		returnList.addAll(phoneCostList);
		if(isPage){
			int formIndex = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"))*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1"))-1);
			int toIndex = formIndex+Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			toIndex = toIndex>returnList.size()?returnList.size():toIndex;
			returnList = returnList.subList(formIndex, toIndex);
		}
		form.setPhoneCostList(returnList);
		return returnList;
	}

	//获取指定的电话号码的明细
	@Override
	public List getPhoneCostInfoDetails(PhoneCostForm form1, boolean isPage)
			throws BkmisServiceException {
		List list = null;
		list = phoneCostDAO.getCallInfo(form1,isPage);
		form1.setPhoneCallInfoList(list);
		List list2 = phoneCostDAO.getCallInfo(form1, false);
		int totalcount = 0;
		double totalCost = 0;
		if(list2!=null){
			totalcount = list2.size();
			for(int  i =0;i<list2.size();i++){
				Map map = (Map)list2.get(i);
				totalCost += Double.parseDouble(GlobalMethod.ObjToParam(map.get("cost"), "0"));
			}
		}
		form1.setTotalCost(totalCost);
		form1.setTotalcount(totalcount);
		return list;
	}
	
	//获取运营商信息 
	@Override
	public List getServiceProviderInfo(PhoneCostForm form1, boolean b)
			throws BkmisServiceException {
		List list = null;
		list = phoneCostDAO.getServiceProviderInfo(form1,b);
		form1.setServiceProviderList(list);
		List list2 = phoneCostDAO.getServiceProviderInfo(form1, false);
		int totalcount = 0;
		if(list2!=null){
			totalcount = list2.size();
		}
		form1.setTotalcount(totalcount);
		return list;
	}

	//保存供应商
	@Override
	public void saveProvider(PhoneCostForm form1) throws BkmisServiceException {
		if(new Integer(0).equals(form1.getProvider().getId())){
			form1.getProvider().setId(null);
		}
		phoneCostDAO.saveOrUpdateObject(form1.getProvider());
	}
	
	//获取电话资费参数列表
	@Override
	public List getPhoneParameterList(PhoneCostForm form1, boolean b)
			throws BkmisServiceException {
		List list = null;
		list = phoneCostDAO.getPhoneParameterInfo(form1,b);
		form1.setPhoneParameterList(list);
		List list2 = phoneCostDAO.getPhoneParameterInfo(form1, false);
		int totalcount = 0;
		if(list2!=null){
			totalcount = list2.size();
		}
		form1.setTotalcount(totalcount);
		return list;
	}
	
	//保存资费信息
	@Override
	public void savePhoneParameter(PhoneCostForm form1)
			throws BkmisServiceException {
		try{
			if(form1.getPhoneParameter().getId()!=null&&form1.getPhoneParameter().getId().equals(0)){
				form1.getPhoneParameter().setId(null);
			}
			phoneCostDAO.saveOrUpdateObject(form1.getPhoneParameter());
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		
	}
	
	//删除资费信息
	@Override
	public void deletePhoneParameter(PhoneCostForm form1)
			throws BkmisServiceException {
		if(form1!=null&&form1.getCheckbox()!=null){
			Integer[] checkbox = form1.getCheckbox();
			for(int i = 0;i<checkbox.length;i++){
				CPhoneParameter cct = new CPhoneParameter();
				cct.setId(checkbox[i]);
				try {
					phoneCostDAO.deleteObject(cct);
				} catch (RuntimeException e) {
					throw new BkmisServiceException();
				}
			}
		}
	}
	
	//获取区号列表
	@Override
	public List getRegionCodeList(PhoneCostForm form1, boolean b)
			throws BkmisServiceException {
		List list = null;
		list = phoneCostDAO.getRegionCodeInfo(form1,b);
		form1.setRegionCodeList(list);
		List list2 = phoneCostDAO.getRegionCodeInfo(form1, false);
		int totalcount = 0;
		if(list2!=null){
			totalcount = list2.size();
		}
		form1.setTotalcount(totalcount);
		return list;
	}
	
	//保存区号信息
	@Override
	public void saveRegionCode(PhoneCostForm form1)
			throws BkmisServiceException {
		try{
			if(form1.getRegionCode().getId()!=null&&form1.getRegionCode().getId().equals(0)){
				form1.getRegionCode().setId(null);
			}
			phoneCostDAO.saveOrUpdateObject(form1.getRegionCode());
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		
	}
	
	//删除区号信息
	@Override
	public void deleteRegionCode(PhoneCostForm form1)
			throws BkmisServiceException {
		if(form1!=null&&form1.getCheckbox()!=null){
			Integer[] checkbox = form1.getCheckbox();
			for(int i = 0;i<checkbox.length;i++){
				CRegionCode cct = new CRegionCode();
				cct.setId(checkbox[i]);
				try {
					phoneCostDAO.deleteObject(cct);
				} catch (RuntimeException e) {
					throw new BkmisServiceException();
				}
			}
		}
	}
	
	
	//复制hashMap
	public Map cloneMap(Map map){
		Map returnMap = new HashMap();
		if(map!=null){
			for(Iterator<Object> it = map.keySet().iterator(); it.hasNext();){
				Object key = it.next();
				returnMap.put(key, map.get(key));
			}
		}
		return returnMap;
	}
	
	@Override
	public String test() {
		
		String result = "0";
		try {
		     ApplicationContext ac = new ClassPathXmlApplicationContext("com/zc13/bkmis/client/applicationContext.xml");
			 //super.setSessionFactory((SessionFactory)ac.getBean("sessionFactory"));
			 JdbcTemplate jdbcTemplate = (JdbcTemplate)ac.getBean("jdbcTemplate");
			 java.util.List list = jdbcTemplate.queryForList("select t1.phone_number from e_rooms t1,compact t2,e_room_client t3 where t3.pact_id=t2.id and t2.isDestine='常规' and t3.room_id=t1.room_id and ISNULL(t1.phone_number,'') <> '' and t3.client_id=211");
			 if(list!=null&&list.size()>0){
				 String phoneNumbers = "";
				 for(int i = 0;i<list.size();i++){
					 phoneNumbers+=((Map)(list.get(i))).get("phone_number")+";";
				 }
				 if(!phoneNumbers.equals("")){
					 phoneNumbers = phoneNumbers.substring(0, phoneNumbers.length()-1);
					 phoneNumbers = phoneNumbers.replace(";", "','");
					 phoneNumbers = "'"+phoneNumbers+"'";
					 java.util.List list2 = jdbcTemplate.queryForList("select t1.id,t1.cost from e_call_info t1 where t1.caller_phone in("+phoneNumbers+") and t1.is_paid='未缴' and SUBSTRING(t1.end_time,0,11)<CONVERT(varchar(10),GETDATE(),120)");
					 double costs = 0;
					 for(int i = 0;i<list2.size();i++){
						 costs+=Double.parseDouble(((Map)list2.get(i)).get("cost").toString());
						 jdbcTemplate.execute("update e_call_info set is_paid='已缴' where id="+((Map)list2.get(i)).get("id"));
					 }
					 result = String.valueOf(costs);
				 }
			  }
		} catch (Exception e) {
		  e.printStackTrace();
		  //throw new Exception();
		}
		/**
		File f = new File("//192.168.0.123/share/2011-09-07-2533.TXT");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String str;
			while((str = br.readLine())!=null){
				if(!str.equals("")){
					System.out.print("主叫："+GlobalMethod.NullToSpace(str.substring(35, 65)));
					System.out.print("被叫："+GlobalMethod.NullToSpace(str.substring(5, 35)));
					System.out.print("开始时间："+GlobalMethod.NullToSpace(str.substring(441, 458)));
					System.out.print("挂机时间："+GlobalMethod.NullToSpace(str.substring(169, 186)));
					System.out.print("时长(秒)："+GlobalMethod.NullToSpace(str.substring(205, 211)));
					System.out.println();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{br.close();}catch(Exception e){}
		}
		*/
		return null;
	}
	
	/**
	 * 每个账单对应的通话记录和费用列表
	 * @param form
	 * @param isPage
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 13, 2011 
	 * Time:11:47:21 AM
	 */
	public List getCallInfoList(PhoneCostForm form,boolean isPage) throws BkmisServiceException {
		List list = null;
		List list2 = phoneCostDAO.getCallInfoList(form, false);
		int totalcount = 0;
		double totalCost = 0;
		if(list2!=null){
			totalcount = list2.size();
			for(int  i =0;i<list2.size();i++){
				ECallInfo call = (ECallInfo)list2.get(i);
				totalCost += call.getCost();
			}
		}
		form.setTotalCost(totalCost);
		form.setTotalcount(totalcount);
		list = phoneCostDAO.getCallInfoList(form,isPage);
		return list;
	}

}
