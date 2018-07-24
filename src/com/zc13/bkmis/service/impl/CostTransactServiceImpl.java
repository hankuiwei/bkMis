package com.zc13.bkmis.service.impl;

import java.math.BigDecimal;
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

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IBuildBillsLogDAO;
import com.zc13.bkmis.dao.ICAccounttemplateDAO;
import com.zc13.bkmis.dao.ICBillDAO;
import com.zc13.bkmis.dao.ICostParaTypeDAO;
import com.zc13.bkmis.dao.ICostTransactDAO;
import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CAdvanceDetail;
import com.zc13.bkmis.mapping.CAdvanceWuYF;
import com.zc13.bkmis.mapping.CAdvanceWuYFDetail;
import com.zc13.bkmis.mapping.CBill;
import com.zc13.bkmis.mapping.CBuildBillsLog;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.mapping.ClientBill;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ECallInfo;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.util.Contants;
import com.zc13.util.CostTransactUtil;
import com.zc13.util.DateUtil;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.cacu.Cacu;
import com.zc13.util.complier.template.BuildDynamicClass;
import com.zc13.util.complier.template.Function;

/**
 * 费用处理service
 * 
 * @author 王正伟 Date：Dec 22, 2010 Time：10:59:48 AM
 */

public class CostTransactServiceImpl implements ICostTransactService {
	
	private ICostParaTypeDAO costParaTypeDAO;
	private ICostTransactDAO costTransactDAO;
	private ICAccounttemplateDAO accountTemplateDAO;
	private IBuildBillsLogDAO buildBillsLogDAO;
	private ICBillDAO icBillDao;

	public ICBillDAO getIcBillDao() {
		return icBillDao;
	}

	public void setIcBillDao(ICBillDAO icBillDao) {
		this.icBillDao = icBillDao;
	}
	public IBuildBillsLogDAO getBuildBillsLogDAO() {
		return buildBillsLogDAO;
	}

	public void setBuildBillsLogDAO(IBuildBillsLogDAO buildBillsLogDAO) {
		this.buildBillsLogDAO = buildBillsLogDAO;
	}

	public ICostParaTypeDAO getCostParaTypeDAO() {
		return costParaTypeDAO;
	}

	public void setCostParaTypeDAO(ICostParaTypeDAO costParaTypeDAO) {
		this.costParaTypeDAO = costParaTypeDAO;
	}

	public ICostTransactDAO getCostTransactDAO() {
		return costTransactDAO;
	}

	public void setCostTransactDAO(ICostTransactDAO costTransactDAO) {
		this.costTransactDAO = costTransactDAO;
	}

	public ICAccounttemplateDAO getAccountTemplateDAO() {
		return accountTemplateDAO;
	}

	public void setAccountTemplateDAO(ICAccounttemplateDAO accountTemplateDAO) {
		this.accountTemplateDAO = accountTemplateDAO;
	}
	/**
	 * 执行公式中参数对应的sql，返回公式中所有参数对应的sql所执行的结果
	 * 
	 * @param params
	 *            如：{costId=11,costType=elec,......}
	 * @param list
	 *            如：{计费面积,单价}
	 * @return map(参数类型名称-对应sql执行得到的结果)如：{计费面积=85,单价=20}
	 */
	public Map getValueMapBySqls(Map params, List paramNames) throws Exception {
		/* 保存的键值对为(类型名称-执行对应sql语句后的值)(key-value) */
		Map typeName_values = new HashMap();

		/* 得到typeName对应的sql语句 */
		List<CCostparatype> list = null;
		// 根据typename查询得到CCostparatype对象列表,从而获得typeName对应的sql语句
		list = costParaTypeDAO.getCostParaTypeByNames(paramNames);

		for (int i = 0; i < list.size(); i++) {
			// 获得代码
			String code = CostTransactUtil.assemblySql(params, list.get(i)
					.getCode());
			// 获得代码类型
			String codeType = GlobalMethod.NullToSpace(list.get(i)
					.getCodeType());
			// 执行代码后得到的值
			String reading = "";
//			System.out.println("计费参数代码为：" + code);
			if (codeType.equals("1")) {// 如果是sql语句
				reading = costTransactDAO.query(code);
			}
			if (codeType.equals("2")) {// 如果是普通公式
				reading = String.valueOf(Cacu.cacu(code));
			}
			if (codeType.equals("3")) {// 如果是java代码
				// 创建动态代码实例
				Function func = new BuildDynamicClass().newFunction(code);
				// 执行方法
				reading = func.getResult();
			}
			// 将计费参数类型名称和执行sql语句的返回值作为键值对保存到typeName_values中
			typeName_values.put(list.get(i).getTypeName(), reading);
		}
		return typeName_values;
	}

	/**
	 * 根据参数map和原始公式得到处理后的表达式
	 * 
	 * @param params{costId=11,costType=elec,......}
	 * @param expressions 公式 eg:{计费面积}*{单价}-12
	 * @return eg: 80.0*56.6-12
	 * @throws Exception
	 */
	public String getExpression(Map params, String expressions)
			throws Exception {
		expressions = GlobalMethod.NullToSpace(expressions);
		if (expressions.equals("")) {
			return "0";
		}
		/* 得到typeName列表 */
		List paramNames = CostTransactUtil.splitExpressions(expressions);// 保存"计费面积"和"单价"等参数名称
		if (paramNames == null || paramNames.size() <= 0) {
			try{
				expressions = String.valueOf(Double.parseDouble(expressions));
			}catch(Exception e){
				expressions = "0";
			}
			return expressions;
		}

		Map typeName_values = null;
		typeName_values = getValueMapBySqls(params, paramNames);

		/* 根据得到的(类型名称-执行对应sql语句后的值)(key-value)键值对和公式得到该公式的计算结果 */
		String expressionsForValue = CostTransactUtil.assemblySql(typeName_values, expressions);
		return expressionsForValue;
	}

	/**
	 * 自动生成账单方法
	 * 
	 * @throws ParseException
	 */
	public void autoBuildBills(){
//		List billIdList = new ArrayList();//这个list 什么用啊,
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = dateFormat.format(new Date());
		CBuildBillsLog billLog = new CBuildBillsLog();
		billLog.setBuildDate(todayDate);
		//根据当前日期 查询  账单日志表
		List<CBuildBillsLog> list = buildBillsLogDAO.query(billLog);
		//如果得到的结果不为空，则表明当天已经生成过账单了，就不在生成账单了
		if(list!=null&&list.size()>0){
			return;
		}else{//否则，表明当天没有生成过账单，则需要继续往下执行，并且向生成账单日志表中添加一条记录
			billLog.setBuildFlag("0");
			billLog.setId(buildBillsLogDAO.save(billLog));
		}
		//计算电话费用，生成账单
		buildBillForPhone(null,null,true);
		
		List<CompactRoomCoststandard> list1 = null;
		try {
			// 获得当前 有效合同 客户下的客户对应收费标准
			list1 = costTransactDAO.queryUsedCoststandard(todayDate, null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//标志该次执行操作是否生成了账单
		boolean isBuildBills = false;
		// 根据获得的客户对应的收费标准生成该客户对应的账单
		if (list1 != null && list1.size() > 0) {
			for (CompactRoomCoststandard crc : list1) {
				Integer accountTemplateId = 0;// 帐套id
				Integer itemId = 0;//收费项id
				try {
					accountTemplateId = crc.getCCoststandard().getAccountTemplateId();
				} catch (Exception e) {
				}
				try {
					itemId = crc.getCCoststandard().getItemId();
				} catch (Exception e1) {
				}
				// 帐套对象
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				//收费项对象
				CItems item = null;
				try {
					item = costTransactDAO.getItemsById(itemId);
				} catch (Exception e1) {}
				//收费项名称
				String itemName = "";
				if(item!=null){
					itemName = GlobalMethod.ObjToStr(item.getItemName());
				}
				
				String produceDate = accounttemplate.getProduceDate();
				// 生成账单日
				int produceDay = Integer.parseInt(produceDate);
				
				// 如果当前时间是生成账单日，则生成账单 --
				/**常规合同 才会 进行自动 生成账单*/
				if (dateFormat.getCalendar().get(Calendar.DAY_OF_MONTH) == produceDay) {
					try {
						if(!itemName.equals(Contants.RENT)
								&&!itemName.equals(Contants.WuYFJF)
								&&!itemName.equals(Contants.WuYFBNF)
								&&!itemName.equals(Contants.WuYFNF)){//如果不是房租
							buildBill(crc, accounttemplate, todayDate,true);
						}else if(Contants.WuYFJF.equals(itemName) 
								|| Contants.WuYFBNF.equals(itemName)
								|| Contants.WuYFNF.equals(itemName) ){
							buildBillForWuYF(crc, accounttemplate, todayDate,true);//创建账单
						}else if(itemName.equals(Contants.RENT)){//如果是房租
							buildBillForRent(crc, accounttemplate, todayDate,true);
						}
						isBuildBills = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		//如果本次操作生成了账单，则将生成账单日志表中对于记录的标志位置为1，并且更新awoke_task表中的未交款客户数量
		if(isBuildBills){
			billLog.setBuildFlag("1");
			costTransactDAO.updateObject(billLog);
			//更新awoke_task表中的未交款客户数量
			updateAwokeTask4PressMoney();
		}
	}
	/**
	 * 1.产生物业费<各种版本-季,半年,年> 账单
	 * 2018年1月26日09:45:52 gd
	 */
	@Override
	public void autoBuildWuYFBill(boolean isAuto) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = dateFormat.format(new Date());
		List<CompactRoomCoststandard> list1 = null;
		try {
			// 获得当前有效合同客户下的客户对应收费标准
			list1 = costTransactDAO.queryUsedCoststandard(todayDate,null,null); //-->查询 使用了 约17秒 
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0 ; i< list1.size() ;i ++ ) {
			CompactRoomCoststandard crc  = list1.get(i);
			if(Contants.WuYFJF.equals(crc.getCCoststandard().getStandardName())
				|| Contants.WuYFBNF.equals(crc.getCCoststandard().getStandardName())
				|| Contants.WuYFNF.equals(crc.getCCoststandard().getStandardName()) ){
				
				Integer accountTemplateId = 0;// 帐套id
				try {
					accountTemplateId = crc.getCCoststandard().getAccountTemplateId();
				} catch (Exception e) {
				}
				// 帐套对象
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				//创建订单
				try {
					buildBillForWuYF(crc, accounttemplate, todayDate,isAuto);//创建账单(模仿 预收租金的形式 )
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 生成电话通话费账单
	 * @param clientId
	 * @param compactId
	 * @param isAutoBuild是否是自动生成账单
	 * Date:Nov 24, 2011 
	 * Time:2:50:16 AM
	 */
	public void buildBillForPhone(Integer clientId,Integer compactId,boolean isAutoBuild){
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todayDate = dateFormat.format(new Date());
			Calendar pre = Calendar.getInstance();
			pre.setTime(new Date());
			pre.add(Calendar.MONTH, -1);
			//当前日期的前一月
			String preDate = dateFormat.format(pre.getTime());
			//获取当前有效客户
			List<CompactClient> clientList = null;
			clientList = costTransactDAO.getCurClient(clientId,compactId);
			CItems item = costTransactDAO.getItemByValue("phoneCost");
			if(clientList!=null&&clientList.size()>0){
				for(int i = 0;i<clientList.size();i++){
					CompactClient client = clientList.get(i);
					Integer lpId = client.getLpId();
					//根据lpId获取帐套信息
					CAccounttemplate template = costTransactDAO.getAccounttemplateByLpId(lpId);
					// 生成账单日
					String produceDate = template.getProduceDate();
					int produceDay = Integer.parseInt(produceDate);
					// 如果当前时间是生成账单日，或者不是自动生成账单时，则生成账单
					if (!isAutoBuild||dateFormat.getCalendar().get(Calendar.DAY_OF_MONTH) == produceDay) {
						//获取客户下的电话号码
						List phoneList = costTransactDAO.getPhoneNumByClientId(client.getId());
						if(phoneList!=null&&phoneList.size()>0){
							double totalMoney = 0.0;//总费用
							//20120410 luq 账单通话记录
							List<ECallInfo> calllist = new ArrayList(); 
							for(int a = 0;a<phoneList.size();a++){
								Map map = (Map)phoneList.get(a);
								//电话号码  - 可能有很多号码.
								String phoneNum = GlobalMethod.ObjToStr(map.get("phoneNumber"));
								String[] phoneArr = phoneNum.split(";");
								if(phoneArr!=null&&phoneArr.length>0){
									for(int b = 0;b<phoneArr.length;b++){
										List countMoneyList = costTransactDAO.countMoneyByPhoneNum(phoneArr[b]);//单个号码 未缴的话费
										if(countMoneyList!=null&&countMoneyList.size()>0){
											double tempMoney = Double.parseDouble(GlobalMethod.ObjToParam( ((Map)(countMoneyList.get(0))).get("totalMoney"), "0"));
											totalMoney += tempMoney;
											
											//20120410 luq 账单通话记录
											List<ECallInfo> tempCallInfo = costTransactDAO.getCallInfoByNumber(phoneArr[b]);
											calllist.addAll(tempCallInfo);
										}
										//将电话费信息状态置为已缴
										//20120410 luq--交费以后再置为已交
										//costTransactDAO.update("update ECallInfo set isPaid='"+Contants.ISPAID+"' where callerPhone='"+phoneArr[b]+"'");
									}
								}
							}
							
							//创建账单
							if(totalMoney>0.001){
								CBill bill = new CBill();
								bill.setCompactClient(client);
								bill.setItemId(item.getId());
								bill.setCreateDate(todayDate);
								// 账单日期
								String billDate = "";
								// 如果生成账单日期的日份小于15号，则此账单算作上月的账单
								if (produceDay<15) {
									billDate = preDate.substring(0, 7);// 截取年月
								} else {//否则账单日期为本月
									billDate = todayDate.substring(0, 7);// 截取年月
								}
								bill.setBillDate(billDate);
								//关帐日期
								String closeType = GlobalMethod.NullToSpace(template.getCloseType());//关帐类型
								
								if(closeType.equals("0")){//如果没有关帐日
									bill.setCloseDate("0");
								}
								
								//将 账单日 字符串转换成calendar类型
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(dateFormat.parse(todayDate));
								
								/*防止在非生成账单日生成账单时出现问题，需要下面操作start*/
								Calendar tempC = Calendar.getInstance();
								tempC.setTime(calendar.getTime());
								//如果  生成账单日  < 实际生成账单日期的日份
								if(Integer.parseInt(produceDate) < calendar.get(Calendar.DAY_OF_MONTH)){
									tempC.add(Calendar.MONTH, 1);
								}
								/*防止在非生成账单日生成账单，需要下面操作end*/
								
								if(closeType.equals("1")){//如果关帐日是当月最后一天
									bill.setCloseDate(DateUtil.getLastDay(tempC));
								}
								if(closeType.equals("2")){//如果关帐日是指定日
									String closeMonth = GlobalMethod.NullToSpace(template.getCloseMonth());//关帐月
									String closeDay = GlobalMethod.NullToSpace(template.getCloseDay());//关帐日
									int c_month = Integer.parseInt(closeMonth);
									int c_day = Integer.parseInt(closeDay);
									tempC.add(Calendar.MONTH, c_month);
									tempC.set(Calendar.DAY_OF_MONTH, c_day);
									bill.setCloseDate(dateFormat.format(tempC.getTime()));
								}
								bill.setStatus("0");// 将状态置为未缴
								// 根据进位方式和计算精度处理处理账单的值
								totalMoney = dealValueByPrecision(template.getPrecision(),template.getRounding(), totalMoney);
								// 单据金额
								bill.setBillsExpenses(totalMoney);
								// 保存账单
								Integer billId = (Integer) costTransactDAO.saveObject(bill);
								Iterator<ECallInfo> it = calllist.iterator();
								while(it.hasNext()) {
									ECallInfo temp = it.next();
									temp.setRootUser(billId);
									costTransactDAO.updateObject(temp);
								}
							}
						}
					}
					//循环一次结束,继续循环
				}
			}
		} catch (NumberFormatException e) {
		} catch (DataAccessException e) {
		} catch (ParseException e) {
		} catch (Exception e) {
		}
	}
	
	/**
	 * 生成特定客户或合同的账单
	 * @param clientId
	 * @return list 生成账单的id列表
	 */
	public List buildBillsByClientId(Integer clientId,Integer compactId,String dateStr){
		List list = new ArrayList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = dateFormat.format(new Date());
		if(GlobalMethod.NullToSpace(dateStr).equals("")){
			dateStr = todayDate;
		}
		
		//计算电话费用，生成账单
		try {
			buildBillForPhone(clientId,compactId,false);
		} catch (Exception e2) {
		}
		
		/*根据提供的合同id找到对应的客户，判断该客户下是否还有其他的有效合同*/
		//根据合同id找到客户
		CompactClient client = null;
		if(compactId!=null){
			client = costTransactDAO.getClientByCompactId(compactId);
		}
		if(client!=null){
			//根据客户id和合同id找到其他的有效合同
			Compact compact = costTransactDAO.getCompactByClientIdAndCompactId(client.getId(),compactId);
			//如果未找到其他的有效合同
			if(compact==null){
				clientId = client.getId();
				compactId = null;
			}
		}
		List<CompactRoomCoststandard> list1 = null;
		try {
			// 获得当前有效合同客户下的客户对应收费标准
			list1 = costTransactDAO.queryUsedCoststandard(dateStr, clientId,compactId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据获得的客户对应的收费标准生成该客户对应的账单
		if (list1 != null && list1.size() > 0) {
			for (CompactRoomCoststandard crc : list1) {
				Map map = new HashMap();
				Integer accountTemplateId = 0;// 帐套id
				Integer itemId = 0;//收费项id
				try {accountTemplateId = crc.getCCoststandard().getAccountTemplateId();} catch (Exception e) {}
				try {itemId = crc.getCCoststandard().getItemId();} catch (Exception e1) {}
				// 帐套对象
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				//收费项对象
				CItems item = null;
				try {
					item = costTransactDAO.getItemsById(itemId);
				} catch (Exception e1) {}
				//收费项名称
				String itemName = "";
				if(item!=null){
					itemName = GlobalMethod.ObjToStr(item.getItemName());
				}
				try {
					map.put("id", crc.getId());
					map.put("lastBuildDate", crc.getLastBuildDate());
					if(!itemName.equals(Contants.RENT)){//如果不是房租
						//map.put("billId", buildBill(crc, accounttemplate, todayDate,false));//2013-03-02 如果设置的时间不是当天也会按照系统当天时间，根据客户提出修改设置的时间
						map.put("billId", buildBill(crc, accounttemplate, dateStr,false));
					}else{//如果是房租
						//map.put("billId", buildBillForRent(crc, accounttemplate, todayDate,false));//2013-03-02 如果设置的时间不是当天也会按照系统当天时间，根据客户提出修改设置的时间
						map.put("billId", buildBillForRent(crc, accounttemplate, dateStr,false));
					}
					list.add(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//更新awoke_task表中的未交款客户数量
			updateAwokeTask4PressMoney();
		}
		return list;
	}
	
	/**
	 * 获得 <指定时间段>内 <指定客户>或 <帐套>下的房租
	 * @param clientId 客户id
	 * @param accountTemplateId帐套id
	 * @param startDate 账单开始 日 
	 * @param endDate账单结束日
	 * @return double
	 */
	public double countRentCharge(Integer clientId,Integer accountTemplateId,String startDate,String endDate){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		/*根据提供的合同id找到对应的客户，判断该客户下是否还有其他的有效合同*/
		//根据合同id找到客户
		List<CompactRoomCoststandard> list1 = null;
		try {
			// 根据客户或帐套id获得客户对应  房租收费标准
			list1 = costTransactDAO.queryUsedRentCoststandard(clientId,accountTemplateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double countRentCharge = 0.0;
		// 根据获得的客户对应的收费标准生成该客户对应的账单
		if (list1 != null && list1.size() > 0) {
			for (CompactRoomCoststandard crc : list1) {
				Integer tempAccountTemplateId = 0;// 帐套id
				try {
					tempAccountTemplateId = crc.getCCoststandard().getAccountTemplateId();
				} catch (Exception e) {
				}
				// 帐套对象
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(tempAccountTemplateId);
				try {
					countRentCharge += this.countChargeForRent(crc, accounttemplate, startDate, endDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return countRentCharge;
	}
	
	/**
	 * 生成房租账单
	 * @param crc
	 * @param accounttemplate
	 * @param todayStr
	 * @param isAuto 是否系统自动生成账单
	 * @return bill_id 生成账单的id
	 */
	private Integer buildBillForRent(CompactRoomCoststandard crc,
			CAccounttemplate accounttemplate, String todayStr,boolean isAuto) throws Exception{
		Integer billId = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = dateFormat.parse(todayStr);
		CompactClient client = crc.getCompactClient();
		CCoststandard standard = crc.getCCoststandard();
		
		String standardBeginStr = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准生效时间
		String standardEndStr = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
//		Compact compact = crc.getCompact();
//		ERooms rooms = crc.getERooms();
//		Integer atId = standard.getAccountTemplateId();// 帐套id
//		String computeCycle = GlobalMethod.NullToSpace(standard.getComputeCycle());// 计算周期
		String balanceCycle = GlobalMethod.NullToSpace(standard.getBalanceCycle());// 结算周期
		//获得上次结账日期，如果为空，则将之置为收费标准生效日期
		String preBalStr = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?standardBeginStr:crc.getLastBuildDate();
		Date curBalD = null;
		if(isAuto){//如果是系统自动生成账单
			//获得本次结账日期
			String curBalStr = getCurBalDate(balanceCycle,preBalStr,accounttemplate.getProduceDate());
			curBalD = dateFormat.parse(curBalStr);
		}else{
			curBalD = todayDate;
		}
		Date standard_b_date = null;// 收费标准生效时间
		Date standard_e_date = null;// 收费标准失效时间
		Date preBalDate = null;// 上次结账日期
		standard_b_date = dateFormat.parse(standardBeginStr);
		standard_e_date = dateFormat.parse(standardEndStr);
		preBalDate = dateFormat.parse(preBalStr);
		/** 如果 上次结账日期 <= 收费标准失效日期、收费标准生效日期 < 生成账单日期(maybe today)、
		 * 本次结账日 <= 当前时间、 上次结账日期 < 本次结账日期  ,则生成账单 */
		if (!standard_e_date.before(preBalDate) && standard_b_date.before(todayDate)&&!curBalD.after(todayDate)&& preBalDate.before(curBalD)) {
			
			Date s_compute_time = null;// 账单开始日期
			Date e_compute_time = null;// 账单结束日期crc.getLastBuildDate()
			s_compute_time = standard_b_date.after(preBalDate) ? standard_b_date : preBalDate;
			e_compute_time = !standard_e_date.before(todayDate) ? dateFormat.parse(DateUtil.getDateByParams(todayStr, Calendar.DAY_OF_MONTH, -1)) : standard_e_date;
			
			// 创建账单对象
			CBill bill = makeBill(crc, accounttemplate, todayStr);
			// 获得参数map
			Map map = getParamsMap(crc,bill, todayStr);
			// 公式
			String formula = standard.getFormula();
			/** 根据参数map和公式得到账单金额start */
			double values = 0;
			//账单开始时间的Calendar对象
			Calendar tempS = Calendar.getInstance();
			tempS.setTime(s_compute_time);
			//账单结束时间的Calendar对象
			Calendar tempE = Calendar.getInstance();
			tempE.setTime(e_compute_time);
			//根据账单开始时间和结束时间查找所有的涉及到的  合同房租关系记录
			List<CompactRent> rentList = costTransactDAO.getRent(map);
			double rent = 0;
			double previousValue = 0;
			//遍历时间：从开始时间遍历到结束时间
			while(!tempS.after(tempE)){
				double tempRent = 0;
				if(rentList!=null&&rentList.size()>0){
					for(CompactRent cr:rentList){
						if(!tempS.before(DateUtil.strToCalendar(cr.getBeginDate()))&&!tempS.after(DateUtil.strToCalendar(cr.getEndDate()))){
							tempRent = cr.getRent();
							break;
						}
					}
					if(new BigDecimal(rent).compareTo(new BigDecimal(tempRent))!=0){
						rent = tempRent;
						String tempDateStr = dateFormat.format(tempS.getTime());
						map.put(Contants.RENTDAY, tempDateStr);//获取租金单价的日期条件
						String expression = "";
						// 得到算术公式
						expression = getExpression(map, formula);
						// 计算字符串的计算公式得到值
						previousValue = Cacu.cacu(expression);
						values += previousValue;
					}else{
						values += previousValue;
					}
				}
				tempS.add(Calendar.DAY_OF_MONTH, 1);
			}
			// 根据进位方式和计算精度处理处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(),accounttemplate.getRounding(), values);
			//从预收款中扣除房租金额
			updateCAdvance(client,values,todayStr);
			// 单据金额
			bill.setBillsExpenses(values);
			//将账单设为已缴
			bill.setStatus("1");
			//设置已缴金额
			//bill.setActuallyPaid(values);
			bill.setActuallyPaid(0.0);
			//设置收款日期
			bill.setCollectionDate(todayStr);
			// 保存账单
			billId = costTransactDAO.save(bill);
			//更新compact_room_coststandard表中的Last_build_date(最后一次生成账单日期)字段为当前结账日期
			crc.setLastBuildDate(todayStr);
			costTransactDAO.updateObject(crc);
			/** 根据参数map和公式得到账单金额end */
		}
		return billId;
	}
	/**
	 * 生成物业费 账单
	 * @param crc 物业费相关的收费标准
	 * @param accounttemplate 对应的账套对象
	 * @param todayStr 今日
	 * @param isAuto 是否系统自动生成账单
	 * @return bill_id 生成账单的id
	 * @Auther GD
	 * @Time 2018年2月1日16:50:41
	 */ 
	private Integer buildBillForWuYF(CompactRoomCoststandard crc,
			CAccounttemplate accounttemplate, String todayStr,boolean isAuto) throws Exception{
		Integer billId = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = dateFormat.parse(todayStr);
		CompactClient client = crc.getCompactClient();
		CCoststandard standard = crc.getCCoststandard();
		/**1.收费标准生效时间 , 收费标准失效时间  */
		String standardBeginStr = GlobalMethod.NullToSpace(crc.getBeginDate());// 
		String standardEndStr = GlobalMethod.NullToSpace(crc.getEndDate());// 
		/**2.结算周期 -- 获得上次结账日期，如果为空，则将之置为收费标准生效日期*/
		String balanceCycle = GlobalMethod.NullToSpace(standard.getBalanceCycle());//
		String preBalStr = GlobalMethod.NullToSpace(ExtendUtil.checkNull( crc.getLastBuildDate()) ? standardBeginStr:crc.getLastBuildDate() );
		/**3. 计算 当前周期 计费结束日期 */
		Date curBalDate = null;
		String curBalStr = "";
		if(isAuto){//如果是系统自动生成账单
			//获得本周期 计费末尾日期
			curBalStr = getCurBalDate(balanceCycle,preBalStr,accounttemplate.getProduceDate());//
			curBalDate = dateFormat.parse(curBalStr);
		}else{//非系统自动生成 
			curBalDate = todayDate ;
		}
		/**4.判断条件是否符合 创建账单 条件*/
		Date standard_b_date = dateFormat.parse(standardBeginStr);// 收费标准生效时间
		Date standard_e_date = dateFormat.parse(standardEndStr);// 收费标准失效时间
		Date preBalDate = dateFormat.parse(preBalStr);// 上次结账日期
		/* 
		 *如果   上次结账日期 <= 收费标准失效日期 
		 * &&  收费标准生效日期 < 生成账单日期(maybe today) 
		 * &&  ----本次结账日 <= 当前时间 -->不可能!!!!!!
		 * &&  上次结账日期 < 本次结账日期  ,则生成账单*/ 
		if (!standard_e_date.before(preBalDate) 
				&& standard_b_date.before(todayDate)
				&& preBalDate.before(curBalDate) 
				&& !todayDate.before(curBalDate) ) {
			/**5. 计算  周期计费 开始和结束 日期*/
			// 判断逻辑: 如果是系统第一次启动,进入代码,计费从 标准生肖开始,到周期末 结束 , 生成账单. 
			//如果是 日常 操作 , 判断是否到了结账 计费日, 控制账单生成.
			Date s_compute_time = null;// 账单开始日期
			Date e_compute_time = null;// 账单结束日期
			s_compute_time = standard_b_date.after(preBalDate) ? standard_b_date : preBalDate;/**2018年3月2日12:48:27 gd  此处修改过.  e_compute_time  由 todayStr 改为 curBalD * */
			e_compute_time = !standard_e_date.before(curBalDate) ? 
					dateFormat.parse( DateUtil.getDateByParams(DateUtil.dateToString(curBalDate, "yyyy-MM-dd"), Calendar.DAY_OF_MONTH, -1) ) 
					: standard_e_date;
		
			//账单计费开始时间的Calendar对象
			Calendar tempS = Calendar.getInstance();
			tempS.setTime(s_compute_time);
			//账单计费结束时间的Calendar对象
			Calendar tempE = Calendar.getInstance();
			tempE.setTime(e_compute_time);
			
			/**6.其他条件 */
			// 创建账单对象
			CBill bill = makeBill(crc, accounttemplate, dateFormat.format(curBalDate));
			// 获得参数map
			Map map = getParamsMap(crc,bill, dateFormat.format(curBalDate));
			// 公式
			String formula = standard.getFormula();
			/** 7.开始计算费用  -->参数map和公式得到 单日账单金额 start */
			double values = 0;
			double previousValue = 0;
			String tempDateStr = dateFormat.format(tempS.getTime());
			map.put(Contants.RENTDAY, tempDateStr);//获取租金单价的日期条件
			// 得到算术公式
			String expression = "";
			expression = getExpression(map, formula);
			previousValue = Cacu.cacu(expression);
			/**8 .计算整个周期的费用 --> 遍历时间：从开始时间遍历到结束时间*/
			while(!tempS.after(tempE)){
				values += previousValue;
				tempS.add(Calendar.DAY_OF_MONTH, 1);
			}
			/**9.数值处理 -->根据进位方式和计算精度处理处理账单的值 */ 
			values = dealValueByPrecision(accounttemplate.getPrecision(),accounttemplate.getRounding(), values);
			/**10.预收款处理 -->从预收款中扣除房租金额*/
			updateCAdvanceWuYF(client,values,curBalStr);
			/**11.账单处理  - 生成账单*/
			// 单据金额
			bill.setBillsExpenses(values);
			//将账单设为已缴
			bill.setStatus("1");
			//设置已缴金额
			bill.setActuallyPaid(0.0);//???为什么设值为 0 ? 
			//设置收款日期
			bill.setCollectionDate(todayStr);
			// 保存账单
			billId = costTransactDAO.save(bill);
			/**12. 收费标准处理.*/
			//更新compact_room_coststandard表中的Last_build_date(最后一次生成账单日期)字段为当前结账日期 
			crc.setLastBuildDate(dateFormat.format(todayStr));
			costTransactDAO.updateObject(crc);
			/** 根据参数map和公式得到账单金额end */
		}
		return billId;
	}
	/**
	 * 计算<指定时间段>可以收取的房租
	 * @param crc
	 * @param accounttemplate
	 * @param todayDate
	 * @param isAuto 是否系统自动生成账单
	 * @return bill_id 生成账单的id
	 */
	private double countChargeForRent(CompactRoomCoststandard crc, CAccounttemplate accounttemplate, String startDate,String endDate) throws Exception{
		double values = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date s_date = dateFormat.parse(startDate);//时间段的开始时间
		Date e_date = dateFormat.parse(endDate);//时间段的结束时间
		CCoststandard standard = crc.getCCoststandard();
		String beginEffectiveDate = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准生效时间
		String endEffectiveDate = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
		Date beginEffectiveD = dateFormat.parse(beginEffectiveDate);// 收费标准生效时间
		Date endEffectiveD = dateFormat.parse(endEffectiveDate);// 收费标准失效时间
		/** 时间段开始时间  <= 收费标准失效时间， 收费标准生效时间 <= 结束时间  */
		if (!s_date.after(endEffectiveD)&&!e_date.before(beginEffectiveD)) {
			
			Date s_compute_time = null;// 费用开始计算日期
			Date e_compute_time = null;// 费用结束计算日期
			s_compute_time = s_date.after(beginEffectiveD) ? s_date : beginEffectiveD;
			e_compute_time = e_date.after(endEffectiveD)?endEffectiveD:e_date;
			
			/**无效注释 代码 start*/
//			String s_compute_str = "";// 费用开始计算日期
//			String e_compute_str = "";// 费用结束计算日期
//			s_compute_str = dateFormat.format(s_compute_time);
//			e_compute_str = dateFormat.format(e_compute_time);
			
			// 创建账单对象
//			CBill bill = makeBill4CountCharge(crc, accounttemplate, startDate,endDate);
			/**无效注释 代码 end*/
			
			// 获得参数map
			Map map = getParamsMap4CountCharge(crc,startDate,endDate);
			// 公式
			String formula = standard.getFormula();
			/** 根据参数map和公式得到账单金额start */
			
			//账单开始时间的Calendar对象
			Calendar tempS = Calendar.getInstance();
			tempS.setTime(s_compute_time);
			//账单结束时间的Calendar对象
			Calendar tempE = Calendar.getInstance();
			tempE.setTime(e_compute_time);
			
			
			//根据账单开始时间和结束时间查找所有的涉及到的 合同租金 关系记录
			List<CompactRent> rentList = costTransactDAO.getRent(map);
			double rent = 0;
			double previousValue = 0;
			//遍历时间：从开始时间遍历到结束时间
			while(!tempS.after(tempE)){
				double tempRent = 0;
				if(rentList!=null&&rentList.size()>0){
					for(CompactRent cr:rentList){
						if(!tempS.before(DateUtil.strToCalendar(cr.getBeginDate()))&&!tempS.after(DateUtil.strToCalendar(cr.getEndDate()))){
							tempRent = cr.getRent();
							break;
						}
						if(new BigDecimal(rent).compareTo(new BigDecimal(tempRent))!=0){
							rent = tempRent;
							String tempDateStr = dateFormat.format(tempS.getTime());
							map.put(Contants.RENTDAY, tempDateStr);//获取租金单价的日期条件
							String expression = "";
							// 得到算术公式
							expression = getExpression(map, formula);
							// 计算字符串的计算公式得到值
							previousValue = Cacu.cacu(expression);
							values += previousValue;
						}else{
							values += previousValue;
						}
					}
				}
				tempS.add(Calendar.DAY_OF_MONTH, 1);
			}
			// 根据进位方式和计算精度处理处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(),
					accounttemplate.getRounding(), values);
		}
		return values;
	}

	/**
	 * 更新预收款和预收款明细中的数据
	 * @param client
	 * @param values
	 * @param todayDay 账单日期
	 */
	private void updateCAdvance(CompactClient client,double values,String todayDay) {
		CAdvance advance = new CAdvance();
		advance.setAmount(-values);
		advance.setClientId(client.getId());
		//更新预收款表
		icBillDao.updateAdvance(advance);
		
		CAdvanceDetail advanceDetail = new CAdvanceDetail();
		advanceDetail.setClientId(client.getId());
		advanceDetail.setAmount(-values);
		advanceDetail.setPayDate(todayDay);
		//向明细表中插入一条更新记录
		icBillDao.saveObject(advanceDetail);
	}

	/**
	 * 更新预收物业费 和 <预收物业费明细 - 表未添加> 中的数据
	 * @param client
	 * @param values
	 * @param todayDay 账单日期
	 */
	private void updateCAdvanceWuYF(CompactClient client,double values,String todayDay) {
		CAdvanceWuYF advance = new CAdvanceWuYF();
		advance.setAmount(-values);
		advance.setClientId(client.getId());
		//更新预收款表
		icBillDao.updateAdvanceWuYF(advance);
		
		CAdvanceWuYFDetail advanceDetail = new CAdvanceWuYFDetail();
		advanceDetail.setClientId(client.getId());
		advanceDetail.setAmount(-values);
		advanceDetail.setPayDate(todayDay);
		//向明细表中插入一条更新记录
		icBillDao.saveObject(advanceDetail);
	}	
	
	/**
	 * 生成账单
	 * 
	 * @param crc
	 * @param accounttemplate
	 * @param todayDate  账单日期
	 * @param isAuto 是否系统自动生成账单
	 * @return bill_id 生成账单的id
	 * @throws ParseException
	 */
	private Integer buildBill(CompactRoomCoststandard crc,
			CAccounttemplate accounttemplate, String todayDate,boolean isAuto)
			throws Exception {
		Integer billId = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date nowDate = dateFormat.parse(todayDate);
		
//		CompactClient client = crc.getCompactClient();
//		Compact compact = crc.getCompact(); 
//		ERooms rooms = crc.getERooms();
		CCoststandard standard = crc.getCCoststandard();
//		Integer atId = standard.getAccountTemplateId();// 帐套id
		
		String beginDate = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准生效时间
		String endDate = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
		int amount = crc.getAmount()==null?1:crc.getAmount();//收费标准数量
		String computeCycle = GlobalMethod.NullToSpace(standard.getComputeCycle());// 计算周期 (大部分都是天计吧)
		String balanceCycle = GlobalMethod.NullToSpace(standard.getBalanceCycle());// 结算周期  (大部分都是月结吧)
		//获得上次结账日期，如果为空，则将之置为收费标准生效日期
		String preBalDate = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?beginDate:crc.getLastBuildDate();
		//获得本次 结账日期
		Date curBalD = null;
		if(isAuto){//如果是系统自动生成账单
			String curBalDate = getCurBalDate(balanceCycle,preBalDate,accounttemplate.getProduceDate());
			curBalD = dateFormat.parse(curBalDate);
		}else{
			curBalD = nowDate;
		}
		Date standard_b_date = null;// 收费标准生效时间
		Date standard_e_date = null;// 收费标准失效时间
		Date pbdate = null;// 上次结账日期
		standard_b_date = dateFormat.parse(beginDate);
		standard_e_date = dateFormat.parse(endDate);
		pbdate = dateFormat.parse(preBalDate);
		/** 收费标准生效日期 < 生成账单日期  、上次结账日期  <= 收费标准失效日期、 
		 * 上次结账日期 < 本次结账日期、本次结账日 <= 当前时间 , 则生成账单 */
		if (!standard_e_date.before(pbdate) && standard_b_date.before(nowDate)&& !curBalD.after(nowDate) &&pbdate.before(curBalD)) {//此处有问题, 结账时间 有问题. 不能按照以前的结账日期走~~~
			Date s_compute_time = null;// 账单开始日期(计费日期范围?)
			Date e_compute_time = null;// 账单结束日期
			s_compute_time = standard_b_date.after(pbdate) ? standard_b_date : pbdate;
			e_compute_time = !standard_e_date.before(nowDate) ? dateFormat.parse(DateUtil.getDateByParams(todayDate, Calendar.DAY_OF_MONTH, -1)) : standard_e_date; 
			
//			String s_compute_str = "";// 账单开始日期
//			String e_compute_str = "";// 账单结束日期
//			s_compute_str = dateFormat.format(s_compute_time);
//			e_compute_str = dateFormat.format(e_compute_time);
			
			// 创建账单对象
			CBill bill = makeBill(crc, accounttemplate, todayDate);
			// 获得参数map
			Map map = getParamsMap(crc,bill, todayDate);
			// 公式
			String formula = standard.getFormula();
			/** 根据参数map和公式得到账单金额start */
			String expression = ""; 
			// 得到算术公式
			expression = getExpression(map, formula);
			// 根据字符串的计算公式 得到值
			double values = 0.0;
			try {
				values = Cacu.cacu(expression);
			} catch (NullPointerException e) {//可能会出现空指针异常
				values=0.0 ;
				e.printStackTrace();
			}
			// 根据账单开始时间和账单结束时间 处理账单的值
			values = dealValuesByTime(computeCycle, s_compute_time,e_compute_time,curBalD, values);
			//根据收费标准数量来处理账单的值
			values = values*amount;
			// 根据进位方式和计算精度处理处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(),accounttemplate.getRounding(), values);
			// 单据金额
			bill.setBillsExpenses(values);
			// 保存账单
			billId = costTransactDAO.save(bill);
			// 更新compact_room_coststandard表中的Last_build_date(最后一次生成账单日期)字段为当前结账日期
			crc.setLastBuildDate(todayDate);
			costTransactDAO.updateObject(crc);
			/** 根据参数map和公式得到账单金额end */
		}
		return billId;
	}
	
	/**
	 * 获得滞纳金
	 * @param bill
	 * @return 保存有滞纳金额的bill对象
	 * @throws Exception 
	 */
	public CBill getDelaying(CBill bill) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = dateFormat.format(new Date());
		String closeDate = GlobalMethod.NullToSpace(bill.getCloseDate());
		//如果当前日期不在关帐日之后，则没有滞纳金
		if(!closeDate.equals("0")&&!closeDate.equals("")&&!dateFormat.parse(todayDate).after(dateFormat.parse(closeDate))){
			bill.setDelayingExpenses(0.0);
		}else{
			// 获得参数map
			Map map = null;
			try {
				map = getParamsMap(null,bill, todayDate);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获得滞纳金计算公式
			String formula = "";
			Integer accountTemplateId = 0;
			try{accountTemplateId = bill.getCCoststandard().getAccountTemplateId();}catch(Exception e){}
			if(accountTemplateId == 0){
				Integer lpId = ((CItems)accountTemplateDAO.getObject(CItems.class, bill.getItemId())).getLpId();
				if(lpId!=null&&lpId!=0){
					CAccounttemplateForm account = new CAccounttemplateForm();
					account.setLpId(lpId);
					List accountTemplateList = accountTemplateDAO.getCAccounttemplateList(account);
					if(accountTemplateList!=null&&accountTemplateList.size()>0){
						accountTemplateId = ((CAccounttemplate)(((Object[])accountTemplateList.get(0))[0])).getId();
					}
				}
			}
			CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);// 帐套对象
			formula = accounttemplate.getFormula();
			// 得到算术公式
			String expression = getExpression(map, formula);
			// 获得字符串的计算公式得到值
			double values = Cacu.cacu(expression);
			// 根据进位方式和计算精度处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(),accounttemplate.getRounding(), values);
			bill.setDelayingExpenses(values);
		}
		return bill;
	}

	/**
	 * 根据进位方式和计算精度处理得到的账单的值
	 * 
	 * @param precision 计算精度:1，元；2，角；3，分
	 * @param rounding 进位方式:1，四舍五入；2，只入不舍;3，只舍不入
	 * @param values
	 *            默认以元为单位
	 * @return
	 */
	private double dealValueByPrecision(String precision, String rounding,
			double values) {
		int nten = 1;
		if (precision.equals("1")) {// 计算精度为元
			nten = nten * 1;
		}
		if (precision.equals("2")) {// 计算精度为角
			nten = nten * 10;
		}
		if (precision.equals("3")) {// 计算精度为分
			nten = nten * 100;
		}
		values = values * nten;

		if (rounding.equals("1")) {// 四舍五入
			values = Math.round(values);
		}
		if (rounding.equals("2")) {// 只入不舍
			double temp1 = values / nten;
			int temp2 = (int) (temp1 * 1000);
			if (temp2 % (1000 / nten) > 0) {

				values = (int) values + 1;
			} else {
				values = (int) values;
			}
		}
		if (rounding.equals("3")) {// 只舍不入
			values = (int) values;
		}
		return values / nten;
	}

	/**
	 * 根据客户对应收费标准信息组装map参数
	 * @param crc crc不为空则为普通账单计算所需参数
	 * @param bill bill不为空则为滞纳金计算所需参数
	 * @param String
	 *            普通账单：生成账单日期字符串：格式为2010-12-10
	 *            滞纳金计算：滞纳金计算截止日期：格式为2010-12-10
	 * @return 如：{costId=11,roomId=111,......}
	 * @throws ParseException
	 */
	private Map getParamsMap(CompactRoomCoststandard crc,CBill bill, String createBillDateStr)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map map = new HashMap();
		Date createBillDate = dateFormat.parse(createBillDateStr);
		if (crc != null) {
			CCoststandard standard = crc.getCCoststandard();
			CompactClient client = crc.getCompactClient();
			Compact compact = crc.getCompact();
			ERooms rooms = crc.getERooms();
			String beginDate = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准开始生效时间
			String endDate = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
			String balanceCycle = GlobalMethod.NullToSpace(standard.getBalanceCycle());// 结算周期
			//获得上次结账日期，如果为空，则将之置为 收费标准生效日期
			String preBalDate = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?beginDate:crc.getLastBuildDate();
			Date costStandardBegin_date = null;// 收费标准生效时间
			Date costStandardEnd_date = null;// 收费标准失效时间
			Date preBalanceDate = null;// 上次结账日期
			costStandardBegin_date = dateFormat.parse(beginDate);
			costStandardEnd_date = dateFormat.parse(endDate);
			preBalanceDate = dateFormat.parse(preBalDate);
			
			Date s_compute_time = null;// 账单开始日期
			Date e_compute_time = null;// 账单结束日期
			String s_compute_str = "";// 账单开始日期
			String e_compute_str = "";// 账单结束日期
			s_compute_time = costStandardBegin_date.after(preBalanceDate) ? costStandardBegin_date : preBalanceDate;//标准开始 > 上次结算 ? 标准开始 : 上次结算 
			e_compute_time = !costStandardEnd_date.before(createBillDate) ? dateFormat.parse(DateUtil.getDateByParams(createBillDateStr, Calendar.DAY_OF_MONTH, -1)) : costStandardEnd_date;
							//标准结束 >= 指定周期 账单清算日 ? 指定周期清算日 : 标准结束 
			s_compute_str = dateFormat.format(s_compute_time);
			e_compute_str = dateFormat.format(e_compute_time);
			
			try {
				map.put(Contants.CLIENTID, client.getId());//客户id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.COMPACTID, compact.getId());//合同id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.ROOMID, rooms.getRoomId());//房间id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.STANDARDID, standard.getId());//收费标准id
			} catch (Exception e) {
			}
			map.put(Contants.BILLSTARTDATE, s_compute_str);//账单开始日期
			map.put(Contants.BILLENDDATE, e_compute_str);//账单结束日期
			map.put(Contants.BILLDAY, createBillDateStr);//账单生成日
		}
		
		if(bill!=null){
			String closeDate = bill.getCloseDate();//关账日期
			int overDate = 0;
			try {
				Date closeD = dateFormat.parse(closeDate);
				overDate = DateUtil.throughDays(closeDate, createBillDateStr)-1;// 指定日期之间的 天数 !!!
			} catch (Exception e) {}
			map.put(Contants.BILLID,bill.getId());//账单id
			map.put(Contants.OVERDATE, overDate);//需要计算滞纳金的天数
			map.put(Contants.BILLSEXPENSES, bill.getBillsExpenses());//合同金额
			map.put(Contants.YEARMONTH, bill.getBillDate());//账单年月，抄表费用所需的参数
		}
		return map;
	}
	
	/**
	 * 根据客户对应收费标准信息组装map参数
	 * 用于统计指定时间段内的费用
	 * @param crc crc不为空则为普通账单计算所需参数
	 * @param bill bill不为空则为滞纳金计算所需参数
	 * @param String
	 *            普通账单：生成账单日期字符串：格式为2010-12-10
	 *            滞纳金计算：滞纳金计算截止日期：格式为2010-12-10
	 * @return 如：{costId=11,roomId=111,......}
	 * @throws ParseException
	 */
	private Map getParamsMap4CountCharge(CompactRoomCoststandard crc,String startDate,String endDate)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date s_date = dateFormat.parse(startDate);//时间段的开始时间
		Date e_date = dateFormat.parse(endDate);//时间段的结束时间
		Map map = new HashMap();
		if (crc != null) {
			CCoststandard standard = crc.getCCoststandard();
			CompactClient client = crc.getCompactClient();
			Compact compact = crc.getCompact();
			ERooms rooms = crc.getERooms();
			String beginEffectiveDate = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准生效时间
			String endEffectiveDate = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
			Date beginEffectiveD = dateFormat.parse(beginEffectiveDate);// 收费标准生效时间
			Date endEffectiveD = dateFormat.parse(endEffectiveDate);// 收费标准失效时间
			String balanceCycle = GlobalMethod.NullToSpace(standard
					.getBalanceCycle());// 结算周期
			Date s_compute_time = null;// 费用开始计算日期
			Date e_compute_time = null;// 费用结束计算日期
			String s_compute_str = "";// 费用开始计算日期
			String e_compute_str = "";// 费用结束计算日期
			s_compute_time = s_date.after(beginEffectiveD) ? s_date : beginEffectiveD;
			e_compute_time = e_date.after(endEffectiveD)?endEffectiveD:e_date;
			s_compute_str = dateFormat.format(s_compute_time);
			e_compute_str = dateFormat.format(e_compute_time);
			try {
				map.put(Contants.CLIENTID, client.getId());//客户id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.COMPACTID, compact.getId());//合同id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.ROOMID, rooms.getRoomId());//房间id
			} catch (Exception e) {
			}
			try {
				map.put(Contants.STANDARDID, standard.getId());//收费标准id
			} catch (Exception e) {
			}
			map.put(Contants.BILLSTARTDATE, s_compute_str);//账单开始日期
			map.put(Contants.BILLENDDATE, e_compute_str);//账单结束日期
		}
		return map;
	}

	/**
	 * 根据CompactRoomCoststandard创建CBill对象
	 * 
	 * @param crc
	 * @param accounttemplate
	 * @param date
	 *            生成账单日期字符串：格式为2010-12-10
	 * @return
	 * @throws ParseException
	 */
	private CBill makeBill(CompactRoomCoststandard crc,
			CAccounttemplate accounttemplate, String date)
			throws ParseException {
		CBill bill = new CBill();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date nowDate = dateFormat.parse(date);//当前时刻
		String todayDate = dateFormat.format(new Date());//当前时刻 -->为什么两个一样
		String produceDate = accounttemplate.getProduceDate();
		if (crc != null) {
			CompactClient client = crc.getCompactClient();
			Compact compact = crc.getCompact();
			ERooms rooms = crc.getERooms();
			CCoststandard standard = crc.getCCoststandard();
			//最后一次生成账单的日期
			String lastBuildDate = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?crc.getBeginDate():crc.getLastBuildDate();
			//本次生成账单日期 --> 默认今日为生成账单日???
			Calendar thisProductDate = Calendar.getInstance();
			thisProductDate.setTime(dateFormat.parse(date));
			//生成账单日期所在月份的第一天
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(dateFormat.parse(date));
			firstDate.set(Calendar.DAY_OF_MONTH, 1);
			//上次生成账单日期
			Calendar lastProductDate = Calendar.getInstance();
			lastProductDate.setTime(dateFormat.parse(lastBuildDate));
			//创建一个日期对象，月份是上一次生成账单日月份，日份是上次生成账单日期的日份
			Calendar temp_lastBal_Cal = Calendar.getInstance();
			temp_lastBal_Cal.setTime(dateFormat.parse(date));
			if(lastProductDate.get(Calendar.YEAR)==thisProductDate.get(Calendar.YEAR)
					&&lastProductDate.get(Calendar.MONTH)==thisProductDate.get(Calendar.MONTH)){
				temp_lastBal_Cal.add(Calendar.MONTH, 0);//这种骚操作...
			}else{
				temp_lastBal_Cal.add(Calendar.MONTH, -1);
			}
			temp_lastBal_Cal.set(Calendar.DAY_OF_MONTH, lastProductDate.get(Calendar.DAY_OF_MONTH));
			String tempLastBalStr = dateFormat.format(temp_lastBal_Cal.getTime());
			
			/** 计算账单时间start */
			String s_begin_str = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准开始生效时间
			String s_end_str = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
//			String balanceCycle = GlobalMethod.NullToSpace(standard.getBalanceCycle());// 结算周期
			//获得上次结账日期，如果为空，则将之置为收费标准生效日期
			String preBalStr = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?s_begin_str:crc.getLastBuildDate();
			Date standard_b_date = null;// 收费标准开始生效时间
			Date standard_e_date = null;// 收费标准失效时间
			Date pre_bal_date = null;// 上次结账日期
			standard_b_date = dateFormat.parse(s_begin_str);
			standard_e_date = dateFormat.parse(s_end_str);
			pre_bal_date = dateFormat.parse(preBalStr);
			
			Date s_compute_date = null;// 账单开始日期
			Date e_compute_date = null;// 账单结束日期
			String s_compute_str = "";// 账单开始日期
			String e_compute_str = "";// 账单结束日期
			s_compute_date = standard_b_date.after(pre_bal_date) ? standard_b_date : pre_bal_date;
			e_compute_date = !standard_e_date.before(nowDate) ? dateFormat.parse(DateUtil.getDateByParams(date, Calendar.DAY_OF_MONTH, -1)) : standard_e_date;
			s_compute_str = dateFormat.format(s_compute_date);
			e_compute_str = dateFormat.format(e_compute_date);
			/** 计算账单时间end */

			bill.setCCoststandard(standard);
			bill.setCompactClient(client);
			bill.setERooms(rooms);
			bill.setCompact(compact);
			bill.setItemId(standard.getItemId());
			bill.setStandardName(standard.getStandardName());
			bill.setCreateDate(todayDate);//为什么不用 date . 2018年2月9日15:32:34 gd
			bill.setStartDate(s_compute_str);// 账单开始日期
			bill.setEndDate(e_compute_str);// 账单结束日期
			// 账单日期
			String billDate = "";
			// 如果生成账单日期的日份到本月一号的间隔  <= 上月结账日到 本月初 的间隔，则此账单算作上月的账单
			if (Math.abs( (firstDate.getTimeInMillis()-temp_lastBal_Cal.getTimeInMillis()) )>=(thisProductDate.getTimeInMillis()-firstDate.getTimeInMillis()) ) {
				billDate = tempLastBalStr.substring(0, 7);// 截取年月
			} else {//否则账单日期为本月
				billDate = date.substring(0, 7);// 截取年月
			}
			bill.setBillDate(billDate);
			//关帐日期
			String closeType = GlobalMethod.NullToSpace(accounttemplate.getCloseType());//关帐类型
			
			if(closeType.equals("0")){//如果没有关帐日
				bill.setCloseDate("0");
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateFormat.parse(date));
			
			/*防止在非生成账单日生成账单时出现问题，需要下面操作start*/
				Calendar tempC = Calendar.getInstance();
				tempC.setTime(calendar.getTime());
				//如果实际生成账单日期的日份大于生成账单日
				if(calendar.get(Calendar.DAY_OF_MONTH)>Integer.parseInt(produceDate)){
					tempC.add(Calendar.MONTH, 1);
				}
			/*防止在非生成账单日生成账单，需要下面操作end*/
			if(closeType.equals("1")){//如果关帐日是当月最后一天
				bill.setCloseDate(DateUtil.getLastDay(tempC));
			}
			if(closeType.equals("2")){//如果关帐日是指定日
				String closeMonth = GlobalMethod.NullToSpace(accounttemplate.getCloseMonth());//关帐月
				String closeDay = GlobalMethod.NullToSpace(accounttemplate.getCloseDay());//关帐日
				int c_month = Integer.parseInt(closeMonth);
				int c_day = Integer.parseInt(closeDay);
				tempC.add(Calendar.MONTH, c_month);
				tempC.set(Calendar.DAY_OF_MONTH, c_day);
				bill.setCloseDate(dateFormat.format(tempC.getTime()));
			}
			bill.setStatus("0");// 将状态置为未缴
		}
		return bill;
	}
	
	/**
	 * 根据CompactRoomCoststandard创建CBill对象
	 * 用于统计指定时间段内的费用
	 * @param crc
	 * @param accounttemplate
	 * @param date
	 *            生成账单日期字符串：格式为2010-12-10
	 * @return
	 * @throws ParseException
	 */
	private CBill makeBill4CountCharge(CompactRoomCoststandard crc,
			CAccounttemplate accounttemplate, String startDate,String endDate)
			throws ParseException {
		CBill bill = new CBill();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date s_date = dateFormat.parse(startDate);//时间段的开始时间
		Date e_date = dateFormat.parse(endDate);//时间段的结束时间
		String todayDate = dateFormat.format(new Date());
		String produceDate = accounttemplate.getProduceDate();
		if (crc != null) {
			CompactClient client = crc.getCompactClient();
			Compact compact = crc.getCompact();
			ERooms rooms = crc.getERooms();
			CCoststandard standard = crc.getCCoststandard();
			String beginEffectiveDate = GlobalMethod.NullToSpace(crc.getBeginDate());// 收费标准生效时间
			String endEffectiveDate = GlobalMethod.NullToSpace(crc.getEndDate());// 收费标准失效时间
			Date beginEffectiveD = dateFormat.parse(beginEffectiveDate);// 收费标准生效时间
			Date endEffectiveD = dateFormat.parse(endEffectiveDate);// 收费标准失效时间
			String balanceCycle = GlobalMethod.NullToSpace(standard
					.getBalanceCycle());// 结算周期
			Date s_compute_time = null;// 费用开始计算日期
			Date e_compute_time = null;// 费用结束计算日期
			String s_compute_str = "";// 费用开始计算日期
			String e_compute_str = "";// 费用结束计算日期
			s_compute_time = s_date.after(beginEffectiveD) ? s_date : beginEffectiveD;
			e_compute_time = e_date.after(endEffectiveD)?endEffectiveD:e_date;
			s_compute_str = dateFormat.format(s_compute_time);
			e_compute_str = dateFormat.format(e_compute_time);

			bill.setCCoststandard(standard);
			bill.setCompactClient(client);
			bill.setERooms(rooms);
			bill.setCompact(compact);
			bill.setItemId(standard.getItemId());
			try {
				bill.setStandardName(standard.getStandardName());
			} catch (Exception e) {
			}
			bill.setCreateDate(todayDate);
			bill.setStartDate(s_compute_str);// 账单开始日期
			bill.setEndDate(e_compute_str);// 账单结束日期
		}
		return bill;
	}

	/**
	 * 
	 * @param computeCycle计算周期
	 * @param starttime开始时间
	 * @param endtime结束时间
	 * @param curBalDate 本次结账日期
	 * @param values值
	 * @return 处理后的值
	 * @throws ParseException 
	 */
	private double dealValuesByTime(String computeCycle, Date s_compute_time,Date e_compute_time,Date curBaltime,double values) throws ParseException {
		double value = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sComputeTime = dateFormat.format(s_compute_time);
		String eComputeTime = dateFormat.format(e_compute_time);
		//本次账单结束日期(为本次结账日的前一天)
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.setTime(curBaltime);
		Date starttime2 = curCalendar.getTime();
		curCalendar.add(Calendar.DAY_OF_MONTH, -1);
		curBaltime = curCalendar.getTime();
		
		if (!GlobalMethod.NullToSpace(computeCycle).equals("")) {
			if (computeCycle.equals("1")) {// 如果计算周期为年
				//如果本次结账日期不在结束日期之后
				if(!curBaltime.after(e_compute_time)){
					String cTime = dateFormat.format(curBaltime);
					// 获得开始时间的下一年的前一天
					String tempTime = DateUtil.getDateByParams(DateUtil
							.getDateByParams(sComputeTime, Calendar.YEAR, 1),
							Calendar.DAY_OF_MONTH, -1);
					if(!tempTime.equals(cTime)){
						int days = DateUtil.throughDays(sComputeTime, cTime);
						value += values * days / 365;
					}else{
						value += values;
					}
					//本次账单结束日期增加一年
					curCalendar.add(Calendar.YEAR, 1);
					curBaltime = curCalendar.getTime();
					//递增本次账单结束日期
					while(!curBaltime.after(e_compute_time)){
						value += values;
						curCalendar.add(Calendar.DAY_OF_MONTH, 1);
						starttime2 = curCalendar.getTime();
						curCalendar.add(Calendar.DAY_OF_MONTH, -1);
						//本次账单结束日期增加一年
						curCalendar.add(Calendar.YEAR, 1);
						curBaltime = curCalendar.getTime();
						
					}
					int days = DateUtil.throughDays(dateFormat.format(starttime2), dateFormat.format(e_compute_time));
					if(days>0){
						value += values * days / 365;
					}
				}else{
					int days = DateUtil.throughDays(sComputeTime, eComputeTime);
					value += values * days / 365;
				}
//				// 获得开始时间的下一年的前一天
//				String tempTime = DateUtil.getDateByParams(DateUtil
//						.getDateByParams(sTime, Calendar.YEAR, 1),
//						Calendar.DAY_OF_MONTH, -1);
//				if (!tempTime.equals(eTime)) {
//					values = values * days / 365;
//				}
			}
			else if (computeCycle.equals("2")) {// 如果计算周期为半年
				//如果本次结账日期不在结束日期之后
				if(!curBaltime.after(e_compute_time)){
					String cTime = dateFormat.format(curBaltime);
					// 获得开始时间的下一半年的前一天
					String tempTime = DateUtil.getDateByParams(DateUtil
							.getDateByParams(sComputeTime, Calendar.MONTH, 6),
							Calendar.DAY_OF_MONTH, -1);
					if(!tempTime.equals(cTime)){
						int days = DateUtil.throughDays(sComputeTime, cTime);
						value += values * days* 2 / 365;
					}else{
						value += values;
					}
					//本次账单结束日期增加半年
					curCalendar.add(Calendar.MONTH, 6);
					curBaltime = curCalendar.getTime();
					//递增本次账单结束日期
					while(!curBaltime.after(e_compute_time)){
						value += values;
						curCalendar.add(Calendar.DAY_OF_MONTH, 1);
						starttime2 = curCalendar.getTime();
						curCalendar.add(Calendar.DAY_OF_MONTH, -1);
						//本次账单结束日期增加半年
						curCalendar.add(Calendar.MONTH, 6);
						curBaltime = curCalendar.getTime();
						
					}
					int days = DateUtil.throughDays(dateFormat.format(starttime2), dateFormat.format(e_compute_time));
					if(days>0){
						value += values * days* 2 / 365;
					}
				}else{
					int days = DateUtil.throughDays(sComputeTime, eComputeTime);
					value += values * days* 2 / 365;
				}
				
//				// 获得开始时间的下一半年的前一天
//				String tempTime = DateUtil.getDateByParams(DateUtil
//						.getDateByParams(sTime, Calendar.MONTH, 6),
//						Calendar.DAY_OF_MONTH, -1);
//				if (!tempTime.equals(eTime)) {
//					values = values * days * 2 / 365;
//				}
			}
			else if (computeCycle.equals("3")) {// 如果计算周期为季度
				//如果本次结账日期不在结束日期之后
				if(!curBaltime.after(e_compute_time)){
					String curBalTimeStr = dateFormat.format(curBaltime);
					// 获得开始时间的下一季度的前一天
					String tempTime = DateUtil.getDateByParams( DateUtil.getDateByParams(sComputeTime, Calendar.MONTH, 3),Calendar.DAY_OF_MONTH, -1);
					if(!tempTime.equals(curBalTimeStr)){
						int days = DateUtil.throughDays(sComputeTime, curBalTimeStr);
						value += values * days* 4 / 365;//不太理解    , 什么规则 
					}else{
						value += values;
					}
					//本次账单结束日期增加1季度
					curCalendar.add(Calendar.MONTH, 3);
					curBaltime = curCalendar.getTime();
					//递增本次账单结束日期
					while(!curBaltime.after(e_compute_time)){
						value += values;
						curCalendar.add(Calendar.DAY_OF_MONTH, 1);
						starttime2 = curCalendar.getTime();
						curCalendar.add(Calendar.DAY_OF_MONTH, -1);
						//本次账单结束日期增加1季度
						curCalendar.add(Calendar.MONTH, 3);
						curBaltime = curCalendar.getTime();
						
					}
					int days = DateUtil.throughDays(dateFormat.format(starttime2), dateFormat.format(e_compute_time));
					if(days>0){
						value += values * days* 4 / 365;
					}
				}else{
					int days = DateUtil.throughDays(sComputeTime, eComputeTime);
					value += values * days* 4 / 365;
				}
				
//				// 获得开始时间的下一季度的前一天
//				String tempTime = DateUtil.getDateByParams(DateUtil
//						.getDateByParams(sTime, Calendar.MONTH, 3),
//						Calendar.DAY_OF_MONTH, -1);
//				if (!tempTime.equals(eTime)) {
//					values = values * days * 4 / 365;
//				}
			}
			else if (computeCycle.equals("4")) {// 如果计算周期为月
				//如果本次结账日期不在结束日期之后
				if(!curBaltime.after(e_compute_time)){
					String cTime = dateFormat.format(curBaltime);
					// 获得开始时间的下一月的前一天
					String tempTime = DateUtil.getDateByParams(DateUtil
							.getDateByParams(sComputeTime, Calendar.MONTH, 1),
							Calendar.DAY_OF_MONTH, -1);
					if(!tempTime.equals(cTime)){
						int days = DateUtil.throughDays(sComputeTime, cTime);
						value += values * days / 30;
					}else{
						value += values;
					}
					//本次账单结束日期增加1月
					curCalendar.add(Calendar.MONTH, 1);
					curBaltime = curCalendar.getTime();
					//递增本次账单结束日期
					while(!curBaltime.after(e_compute_time)){
						value += values;
						curCalendar.add(Calendar.DAY_OF_MONTH, 1);
						starttime2 = curCalendar.getTime();
						curCalendar.add(Calendar.DAY_OF_MONTH, -1);
						//本次账单结束日期增加1月
						curCalendar.add(Calendar.MONTH, 1);
						curBaltime = curCalendar.getTime();
						
					}
					int days = DateUtil.throughDays(dateFormat.format(starttime2), dateFormat.format(e_compute_time));
					if(days>0){
						value += values * days / 30;
					}
				}else{
					int days = DateUtil.throughDays(sComputeTime, eComputeTime);
					value += values * days / 30;
				}
				
//				//System.out.println("计算周期为月");
//				// 获得开始时间的下一月的前一天
//				String tempTime = DateUtil.getDateByParams(DateUtil
//						.getDateByParams(sTime, Calendar.MONTH, 1),
//						Calendar.DAY_OF_MONTH, -1);
//				if (!tempTime.equals(eTime)) {
//					values = values * days / 30;
//				}
			}
			else if (computeCycle.equals("5")) {// 如果计算周期为天
				int days = DateUtil.throughDays(sComputeTime, eComputeTime);
				value = values * days;
			}
			else if (computeCycle.equals("0")) {// 如果无计算周期
				value = values;
			}
		}else{
			value = values;
		}
		return value;
	}

	/**
	 * 根据收费标准中的结算周期,上次账单日期和帐套中指定的账单日期获得本次的结账日期
	 * @param balanceCycle 收费标准中的结算周期
	 * @param preBalDate 上次账单日期
	 * @param produceDate 帐套中指定的账单日 如:10
	 * @return 本次的结账日期
	 * @throws Exception 
	 */
	private String getCurBalDate(String balanceCycle,String preBalDate, String produceDate) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar preBalCal = Calendar.getInstance();
		preBalCal.setTime(dateFormat.parse(preBalDate));
		//获得上次生成账单日是多少号
		int preBillday = preBalCal.get(Calendar.DAY_OF_MONTH);
		int field = 0;
		int offset = 0;
		// 本次结账日期
		String curBalDate = "";
		if (balanceCycle.equals("1")) {// 如果结算周期为年
			field = Calendar.MONTH;
			offset = 12;
		}
		if (balanceCycle.equals("2")) {// 如果结算周期为半年
			field = Calendar.MONTH;
			offset = 6;
		}
		if (balanceCycle.equals("3")) {// 如果结算周期为季度
			field = Calendar.MONTH;
			offset = 3;
		}
		if (balanceCycle.equals("4")) {// 如果结算周期为月
			field = Calendar.MONTH;
			offset = 1;
		}
		if (balanceCycle.equals("5")) {// 如果结算周期为天
			field = Calendar.DAY_OF_MONTH;
			offset = 1;
		}
		if(preBillday<Integer.parseInt(produceDate)&&!balanceCycle.equals("5")){//上次结账日  < 结算日
			offset = offset-1;
		}
		preBalCal.add(field, offset);
		preBalCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(produceDate));
		curBalDate = dateFormat.format(preBalCal.getTime());
		//curBalDate = DateUtil.getDateByParams(preBalDate, field, offset);
		return curBalDate;
	}
	
	/**
	 * 获取指定周期偏移量  应收 的房租
	 * 条件 ( 以当前日期所在的房租月份为基准 )
	 * @param clientId 客户id
	 * @param appoint 偏移量 如：appoint=1指代获取当前日期所在房租月份的下个周期房租
	 * @param dateStr 指定日期
	 * @param flag 标示是否只能是提前一月获得下一周期的房租，flag=true是，flag=false否
	 * @param useCompact 是否使用合同中指定的周期
	 * @param myCircle 自定义周期(只有useCompact=false时有效)
	 * @return
	 * @throws Exception 
	 */
	public double getAppointDateRentByClientId(int clientId,int appoint,String dateStr,boolean flag,boolean useCompactCircle,int myCircle,boolean currPayFlag) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//当前日期
		Date nowDate = null;
		Calendar nowC = Calendar.getInstance();
		try{nowDate = dateFormat.parse(dateStr);}catch(Exception e){}
		nowC.setTime(nowDate);
		
		List<CompactRoomCoststandard> list = null;
		
		try {
			//查出当前客户下的有效 <房租> 收费标准
			list = costTransactDAO.getUsedRentCoststandard(clientId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//房租总费用
		double countRent = 0;
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				CompactRoomCoststandard crc = list.get(i);
				//得到合同对象
				Compact compact = crc.getCompact();
				//得到预收款缴纳周期
				int circle = 0;
				try{circle = Integer.parseInt(compact.getCircle());}catch(Exception e){circle = 0;}
				//得到租赁开始时间 
				String startDate = compact.getBeginDate();
				// 帐套对象
				int accountTemplateId = crc.getCCoststandard().getAccountTemplateId();//CCoststandard 收费标准
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				// 账单生成日
				String produceDate = accounttemplate.getProduceDate();
				int produceDay = Integer.parseInt(produceDate);
				
				boolean isPay = false;
				if(flag){
					//根据租赁 开始日期，预收款缴纳周期，账单生成日，当前时间，判断当前时间是否需要提醒该缴纳下一周期的预收款了
					isPay = isPayAdvance(startDate,circle,produceDay,dateStr);
					//如果当前时间不需要提示缴纳下一周期的预收款或（指定日期在合同开始日期之前并且需要计算的是下一周期的预收款，则不进行计算）
					if(!isPay||(dateFormat.parse(startDate).after(dateFormat.parse(dateStr))&&appoint==1)){
						continue;
					}
				}else if(currPayFlag){
					isPay = isAdvancePressDate(startDate,circle,produceDay,dateStr);
					//如果当前时间不需要提示缴纳下一周期的预收款或（指定日期在合同开始日期之前并且需要计算的是下一周期的预收款，则不进行计算）
					if(!isPay){
						continue;
					}
				}
				if(!useCompactCircle){
					circle = myCircle;
				}
				
				//获取指定周期偏移量和预收款周期的房租
				double values = 0;
				try {
					values = getAppointDateRent(dateStr, appoint, circle, crc,true);
				} catch (Exception e) {
				}
				countRent = countRent+values;
			}
		}
		return countRent;
	}
/**
 * Date:2018-1-17  上午10:32:50
 * 获取物业费季付 金额
 * @param clientId 客户id
 * @param appoint 偏移量 如：appoint=1指代获取当前日期所在房租月份的下个周期房租
 * @param currDateStr 指定日期
 * @param flag 标示 是否只能是提前一月获得下一周期的房租，flag=true是，flag=false否
 * @param useCompact 是否使用合同中指定的周期
 * @param myCircle 自定义周期(只有useCompact=false时有效)
 * @throws Exception 
 */
	public double getAppointDateWuYFByClientId(int clientId,int appoint,String currDateStr,boolean flag,boolean useCompactCircle,int myCircle,boolean currPayFlag) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<CompactRoomCoststandard> list = null;
		try {
			list = costTransactDAO.getValidRentCoststandard(clientId, null, null);/**获取 当前客户下的有效 物业费等 收费标准 * */ 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//物业费 总费用
		double countWuYF = 0;
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				CompactRoomCoststandard crc = list.get(i);
				//得到收费标准对象
				CCoststandard cs = crc.getCCoststandard();
				//得到  结算周期
				int balCircle = 0;
				try {
					balCircle = Integer.parseInt(cs.getBalanceCycle());
					if(balCircle==1)
						balCircle = 12 ;
					else if(balCircle==2)
						balCircle = 6 ;
					else if(balCircle==3)
						balCircle = 3 ;
					else if(balCircle==4)
						balCircle = 1 ;
					else if(balCircle==5)//怎么可能有按天结算的 收费? 不嫌烦吗?
						balCircle = 1/30 ;//按天结算 , 计算周期,未知 如何设置!2018年2月6日15:21:42 gd 
				} catch (Exception e) {
					balCircle = 0;
				}
				//计费周期
				int computeCircle = 0 ;
				try {
					computeCircle =  Integer.parseInt(cs.getComputeCycle());
				} catch (Exception e1) {
					computeCircle = 0 ;
				}
				//得到收费标准生效 时间 
				String csBeginDate = crc.getBeginDate();
				// 帐套对象
				int accountTemplateId = cs.getAccountTemplateId();//CCoststandard 收费标准
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				// 账单生成日
				String produceDate = accounttemplate.getProduceDate();
				int produceDay = Integer.parseInt(produceDate);
				
				boolean isPay = false;
				if(flag){//如果只能 提前一个月获取 ?
					//根据租赁开始日期，预收款缴纳周期，账单生成日，当前时间，判断当前时间 <<是否需要缴费>> 
					isPay = isPayAdvance(csBeginDate,balCircle,produceDay,currDateStr);
					//如果当前时间不需要提醒缴纳下一周期的预收款  或（指定日期  在合同开始日期之前  并且需要计算的是下一个周期的预收款，则不进行计算）
					if(!isPay||(dateFormat.parse(csBeginDate).after(dateFormat.parse(currDateStr))&&appoint==1)){
						continue;
					}
				}else if(currPayFlag){
					isPay = isAdvancePressDate(csBeginDate,balCircle,produceDay,currDateStr);
					if(!isPay){
						continue;
					}
					/**如果当前日期 不在 催缴日期范围 , 不计算 当前周期 物业费 */
				}
				/**2018年3月5日10:30:03 gd 下面的代码 作用未知?*/
				if(!useCompactCircle){
					balCircle = myCircle;
				}
				
				//获取指定周期偏移量和预收款周期 的 物业费季付
				double values = 0;
				try {
					values = getAppointDateWuYF(currDateStr, appoint, balCircle,computeCircle, crc, true);//获取指定收费标准的  费用
				} catch (Exception e) {
				}
				countWuYF = countWuYF+values;
			}
		}
		return countWuYF;
	}

	
/**2018年1月17日08:47:55 gd ：  获取 相对当前月，指定偏移量 的  物业费季付 金。*/
	/**
	 * @param clientId 客户id
	 * @param appoint 偏移量  ，如：appoint=1 指代获取当前日期所在房租月份的下个周期房租
	 * @param dateStr 指定日期
	 * @param flag 标示是否只能是提前一月获得下一周期的房租，flag=true是，flag=false否
	 * @param useCompact 是否使用合同中指定的周期
	 * @param myCircle 自定义周期(只有useCompact=false时有效)
	 * @return
	 * @throws Exception 
	 */
	public double getAppointDateWuYJFRentByClientId(int clientId,int appoint,String dateStr,boolean flag,boolean useCompactCircle,int myCircle) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = null;
		//当前日期
		Calendar nowC = Calendar.getInstance();
		try {
			nowDate = dateFormat.parse(dateStr);
		} catch (Exception e) {
		}
		nowC.setTime(nowDate);
		
		List<CompactRoomCoststandard> list = null;
		try {
			//查出当前客户下的有效房租收费标准
			list = costTransactDAO.getUsedRentCoststandard(clientId, null, null);
			//
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//房租总费用
		double countRent = 0;
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				CompactRoomCoststandard crc = list.get(i);
				//得到合同对象
				Compact compact = crc.getCompact();
				//得到预收款缴纳周期
				int circle = 0;
				try{circle = Integer.parseInt(compact.getCircle());}catch(Exception e){circle = 0;}
				//得到租赁开始时间 
				String compactStartDate = compact.getBeginDate();
				// 帐套对象
				int accountTemplateId = crc.getCCoststandard().getAccountTemplateId();//CCoststandard 收费标准
				CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
				// 账单生成日
				String produceDate = accounttemplate.getProduceDate();
				int produceDay = Integer.parseInt(produceDate);
				
				if(flag){
					boolean isPay = false;
					//根据租赁开始日期，预收款缴纳周期，账单生成日，当前时间，判断当前时间是否需要提醒该缴纳下一周期的预收款了
					isPay = isPayAdvance(compactStartDate,circle,produceDay,dateStr);
					//如果当前时间不需要提醒缴纳下一周期的预收款或（指定日期在合同开始日期之前并且需要计算的是下一周期的预收款，则不进行计算）
					if(!isPay||(dateFormat.parse(compactStartDate).after(dateFormat.parse(dateStr))&&appoint==1)){
						continue;
					}
				}
				if(!useCompactCircle){
					circle = myCircle;
				}
				
				//获取指定周期偏移量和预收款周期的房租
				double values = 0;
				try{values = getAppointDateRent(dateStr,appoint,circle,crc,true);}catch(Exception e){}
				countRent = countRent+values;
			}
		}
		return countRent;
	}

	/**
	 * 获得  指定收费标准的  房租
	 * @param dateStr 指定日期
	 * @param appoint 偏移量 如：appoint=1指代获取当前日期所在房租月份的下个周期房租
	 * @param circle 周期 如：circle=2指代需计算2个月的房租
	 * @param crc 房间客户对应收费标准
	 * @param flag 是否需要从最后创建账单日期开始计算 flag=true是；flag=false否
	 * 				flag=true表示得到的值是 周期缴纳费用 除去已缴纳的房租  剩余还需要缴纳的房租
	 * 				flag=false表示得到的值是 周期缴纳的总费用
	 * @return 返回具体  账单金额
	 */
	private double getAppointDateRent(String dateStr,int appoint,int circle,CompactRoomCoststandard crc,boolean flag) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//当前日期
		Date nowDate = null;
		Calendar nowC = Calendar.getInstance();
		try {
			nowDate = dateFormat.parse(dateStr);
		} catch (Exception e) {
		}
		
		//得到合同对象
		nowC.setTime(nowDate);
		Compact compact = crc.getCompact();
		//得到收费标准对象
		CCoststandard standard = crc.getCCoststandard();
		int accountTemplateId = crc.getCCoststandard().getAccountTemplateId();
		// 帐套对象 
		CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
		String produceDate = accounttemplate.getProduceDate();
		// 账单生成日
		int produceDay = Integer.parseInt(produceDate);
		//合同租赁开始日期
		String startDate = compact.getBeginDate();
		//合同迁出时间
		String goDate = GlobalMethod.NullToSpace(compact.getGoDate());
		//迁出时间的前一天（因为费用的计算就是到迁出的前一天）
		Calendar preGoCalendar = Calendar.getInstance();
		try {
			preGoCalendar.setTime(dateFormat.parse(goDate));
			preGoCalendar.set(Calendar.DAY_OF_MONTH, -1);
		} catch (Exception e2) {
			//如果迁出时间为空，（即上面抛异常），则 迁出时间的前一天 设定在  合同结束时间 
			preGoCalendar.setTime(dateFormat.parse(compact.getEndDate()));
		}
		String preGoDateStr = dateFormat.format(preGoCalendar.getTime());
		//最后创建账单日期
		String lastBuildDate = GlobalMethod.NullToSpace(crc.getLastBuildDate()).equals("")?crc.getBeginDate():crc.getLastBuildDate();
		Calendar lastBuildCalendar = Calendar.getInstance();
		lastBuildCalendar.setTime(dateFormat.parse(lastBuildDate));
		
		//指定日期所在周期  账单 开始日期(计费开始日期?)
		Calendar computeStartCalendar = Calendar.getInstance();
		computeStartCalendar = getStartDate4Circle(dateStr,startDate,produceDay,circle,appoint);
		//指定日期所在周期 账单 结束日期(计费结束日期?)
		Calendar computeEndCalendar = Calendar.getInstance();
		computeEndCalendar.setTime(computeStartCalendar.getTime());
		computeEndCalendar.add(Calendar.MONTH, circle); // 一个周期 
		computeEndCalendar.add(Calendar.DAY_OF_MONTH, -1);
		
		//收费标准 生效日期
		String beginDate = crc.getBeginDate();
		Calendar costStandardBeginCal = Calendar.getInstance();
		try {
			costStandardBeginCal.setTime(dateFormat.parse(beginDate));
		} catch (Exception e) {
		}
		//收费标准失效日期
		String endDate  = crc.getEndDate();
		if(!preGoDateStr.equals("")){//如果合同迁出日期不为空，则将收费标准失效日期  设为 合同迁出日期和收费标准失效日期中较小的那个值
			if(dateFormat.parse(endDate).after(dateFormat.parse(preGoDateStr))){
				endDate = preGoDateStr;
			}
		}
		Calendar costStandardEndCal = Calendar.getInstance();
		
		//如果抛出异常，则继续下一个循环
		try {
			costStandardEndCal.setTime(dateFormat.parse(endDate));
		} catch (Exception e) {
		}
		
		//指定月 账单日
		Calendar speciBillCal = Calendar.getInstance();
		speciBillCal.set(computeEndCalendar.get(Calendar.YEAR), computeEndCalendar.get(Calendar.MONTH), computeEndCalendar.get(Calendar.DAY_OF_MONTH));
		speciBillCal.add(Calendar.DAY_OF_MONTH, 1);
		String speciBillDate = dateFormat.format(speciBillCal.getTime());
		
		double values = 0;
		// 如果   账单开始日期  < 收费标准结束日期   / 收费标准生效日期 <= 账单结束日期 ? 
		//貌似有问题.. 并不能保障  账单 在 有效期内.
//		if(!computeStartCalendar.before(costStandardBeginCal)&&computeEndCalendar.before(costStandardEndCal)){
			if(computeStartCalendar.before(costStandardEndCal)&&!computeEndCalendar.before(costStandardBeginCal)){ //2018年1月22日11:06:18  此为原本代码
			// 创建账单对象
			CBill bill = makeBill(crc, accounttemplate, speciBillDate);
			// 获得参数map
			Map map = getParamsMap(crc,bill, speciBillDate);
			// 公式
			String formula = standard.getFormula();

			//账单开始时间的 Calendar 替代对象
			Calendar tempStartCal = Calendar.getInstance();
			tempStartCal.setTime((computeStartCalendar.after(costStandardBeginCal)?computeStartCalendar:costStandardBeginCal).getTime());//账单开始 时间  > 标准开始时间 ? 账单开始 : 标准开始
			if(flag){
				tempStartCal.setTime((tempStartCal.after(lastBuildCalendar)?tempStartCal:lastBuildCalendar).getTime());// 账单开始 > 最后创建 : 账单开始:最后创建
			}
			//账单结束时间的Calendar 替代对象
			Calendar tempEndCal = Calendar.getInstance();
			tempEndCal.setTime((computeEndCalendar.after(costStandardEndCal)?costStandardEndCal:computeEndCalendar).getTime());// 账单结束 > 标准结束 ? 标准结束 : 账单结束 
			
			//根据账单开始时间和结束时间  查找  所有的涉及到的  房租合同 房租关系记录
			List<CompactRent> rentList = costTransactDAO.getRent(map); 
			//计算租金
			double rent = 0;
			double previousValue = 0;
			while(!tempStartCal.after(tempEndCal)){  //指定日期所在周期  账单开始日  < 账单结束日
				double tempRent = 0;
				if(rentList!=null&&rentList.size()>0){
					for(CompactRent cr:rentList){
						// 合同 开始日 < 账单开始日    &&  账单开始日 <= 合同结束日
						if(!tempStartCal.before(DateUtil.strToCalendar(cr.getBeginDate())) && !tempStartCal.after(DateUtil.strToCalendar(cr.getEndDate()))){
							tempRent = cr.getRent();//获取 合同指定 租金 
							break;
						}
					}
					// 两者比较不相等
					if(new BigDecimal(rent).compareTo(new BigDecimal(tempRent))!=0){ 
						rent = tempRent;
						String tempSpecBillStartDateStr = dateFormat.format(tempStartCal.getTime());
						map.put(Contants.RENTDAY, tempSpecBillStartDateStr);//获取租金单价的日期条件
						String expression = "";
						// 得到算术公式
						expression = getExpression(map, formula);
						// 计算字符串的计算公式得到值
						previousValue = Cacu.cacu(expression);
					}
					values += previousValue;
				}
				tempStartCal.add(Calendar.DAY_OF_MONTH, 1);// 计算下一天的租金
			}
			
			// 根据进位方式和计算精度处理处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(), accounttemplate.getRounding(), values);
		}
		return values;
	}

	/**
	 * 获得  指定收费标准的 物业费季付 金
	 * @param currDateStr 指定日期
	 * @param appoint 偏移量 如：appoint=1指代获取当前日期所在房租月份的下个周期房租
	 * @param balCircle 周期 如：circle=2指代需计算2个月的房租
	 * @param computeCircle 计算周期 如：computeCircle=1指代  --> 以天为单位收费 ---< 没吊用啊!!!!!默认都是天 ,>
	 * @param crc 房间客户对应收费标准
	 * @param flag 是否需要从最后创建账单日期开始计算 flag=true是；flag=false否
	 * 				flag=true表示得到的值是 周期缴纳费用 除去已缴纳的房租  剩余还需要缴纳的房租
	 * 				flag=false表示得到的值是 周期缴纳的总费用
	 * @return 返回具体  账单金额
	 */
	private double getAppointDateWuYF(String currDateStr,int appoint,int balCircle,int computeCircle,CompactRoomCoststandard crc,boolean flag) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//得到合同对象
		Compact compact = crc.getCompact();
		//得到收费标准对象
		CCoststandard standard = crc.getCCoststandard();
		int accountTemplateId = crc.getCCoststandard().getAccountTemplateId();
		// 帐套对象
		CAccounttemplate accounttemplate = accountTemplateDAO.getById(accountTemplateId);
		String produceDate = accounttemplate.getProduceDate();
		// 账单生成日
		int produceDay = Integer.parseInt(produceDate);
		// 收费标准开始日期
		String standardBeginDate = crc.getBeginDate();
		
		// 迁出时间的前一天（因为费用的计算就是到迁出的前一天）
		String goDate = GlobalMethod.NullToSpace(compact.getGoDate());
		Calendar preGoCalendar = Calendar.getInstance();
		try {
			preGoCalendar.setTime(dateFormat.parse(goDate));
			preGoCalendar.set(Calendar.DAY_OF_MONTH, -1);
		} catch (Exception e2) {//如果迁出时间为空，（即上面抛异常），则 迁出时间的前一天 设定在  合同结束时间 
			preGoCalendar.setTime(dateFormat.parse(compact.getEndDate()));
		}
		String preGoDateStr = dateFormat.format(preGoCalendar.getTime());
		
		//最近一次创建账单的日期
		String lastBuildDate = ExtendUtil.checkNull(crc.getLastBuildDate())?crc.getBeginDate():crc.getLastBuildDate();
		Calendar lastBuildCalendar = Calendar.getInstance();
		lastBuildCalendar.setTime(dateFormat.parse(lastBuildDate));
		
		//指定日期所在周期  开始记账日期
		Calendar circleStartCal = Calendar.getInstance();
		circleStartCal = getStartDate4Circle(currDateStr,standardBeginDate,produceDay,balCircle,appoint);
		//指定日期所在周期 结束记账日期
		Calendar circleEndCal = Calendar.getInstance();
		circleEndCal.setTime(circleStartCal.getTime());
		circleEndCal.add(Calendar.MONTH, balCircle); // 一个周期 
		circleEndCal.add(Calendar.DAY_OF_MONTH, -1);
		
		//收费标准 生效日期
		Calendar costStandardBeginCal = Calendar.getInstance();
		try {
			costStandardBeginCal.setTime(dateFormat.parse(standardBeginDate));
		} catch (Exception e) {
		}
		//收费标准失效日期
		String standardEndDate  = crc.getEndDate();
		Calendar costStandardEndCal = Calendar.getInstance();
		if(!preGoDateStr.equals("")){//如果合同迁出日期不为空，则将收费标准失效日期  设为 合同迁出日期和收费标准失效日期中较小的那个值
			if(dateFormat.parse(standardEndDate).after(dateFormat.parse(preGoDateStr))){
				standardEndDate = preGoDateStr;
			}
		}
		try {
			costStandardEndCal.setTime(dateFormat.parse(standardEndDate));
		} catch (Exception e) {
		}
		
		//指定月 的 账单日
		Calendar speciBillCal = Calendar.getInstance();
		speciBillCal.set(circleEndCal.get(Calendar.YEAR), circleEndCal.get(Calendar.MONTH), circleEndCal.get(Calendar.DAY_OF_MONTH));
		speciBillCal.add(Calendar.DAY_OF_MONTH, 1);
		String speciBillDate = dateFormat.format(speciBillCal.getTime());
		
		/**开始计费*/
		double values = 0;
		// 如果   账单开始日期  < 收费标准结束日期   / 收费标准生效日期 < 账单结束日期 ? 
		//貌似有问题.. 并不能保障  账单 在 有效期内.
		/**如何确定 是在 本周期*/
		if(circleStartCal.before(costStandardEndCal)&&!circleEndCal.before(costStandardBeginCal)){
			// 创建账单对象
			CBill bill = makeBill(crc, accounttemplate, speciBillDate);
			// 获得参数map
			Map map = getParamsMap(crc,bill, speciBillDate);
			// 单价 计算 公式
			String formula = standard.getFormula();
			//计费开始时间的 Calendar 替代对象
			Calendar tempStartCal = Calendar.getInstance();
			tempStartCal.setTime((circleStartCal.after(costStandardBeginCal)?circleStartCal:costStandardBeginCal).getTime());//账单开始 时间  > 标准开始时间 ? 账单开始 : 标准开始
			if(flag){//如果是true ,是从上次计费结束开始.
				tempStartCal.setTime((tempStartCal.after(lastBuildCalendar)?tempStartCal:lastBuildCalendar).getTime());// 账单开始 > 最后创建 : 账单开始:最后创建
			}
			//计费结束时间的Calendar 替代对象
			Calendar tempEndCal = Calendar.getInstance();
			tempEndCal.setTime((circleEndCal.after(costStandardEndCal)?costStandardEndCal:circleEndCal).getTime());// 账单结束 > 标准结束 ? 标准结束 : 账单结束 
			
			double previousValue = 0;
			String tempSpecBillStartDateStr = dateFormat.format(tempStartCal.getTime());
			map.put(Contants.RENTDAY, tempSpecBillStartDateStr);//获取租金单价的日期条件
			String expression = "";
			// 得到算术公式
			expression = getExpression(map, formula);//formula :有效房屋费用标准 对象 的 计算公式
			// 计算字符串的计算公式得到值
			previousValue = Cacu.cacu(expression);
			//循环计算 
			while(!tempStartCal.after(tempEndCal)){  //指定日期所在周期  账单开始日  < 账单结束日 , 循环计算 物业费 
				values += previousValue;
				tempStartCal.add(Calendar.DAY_OF_MONTH, 1);// 计算下一天的租金
			}
			// 根据进位方式和计算精度处理处理账单的值
			values = dealValueByPrecision(accounttemplate.getPrecision(), accounttemplate.getRounding(), values);
		}
		return values;
	}
	
	/**
	 * 根据合同开始日期和预收款收费周期获得指定日期的所在的周期的下appiont个指定的周期的开始日期
	 * @param currDateStr 指定日期
	 * @param standardBeginDate 合同开始日期
	 * @param produceDay 生成账单日
	 * @param circle 周期
	 * @param appiont 偏移量
	 */
	public Calendar getStartDate4Circle(String currDateStr, String standardBeginDate,int produceDay,
			int circle,int appiont) {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//收费标准开始日期
		Calendar standardstartCalendar = DateUtil.strToCalendar(standardBeginDate);
		//指定日期
		Calendar speciCalendar = DateUtil.strToCalendar(currDateStr);
		
		//如果指定日期在收费标准开始日期之前，则将指定日期设为收费标准开始日期
		if(speciCalendar.before(standardstartCalendar)){
			speciCalendar.setTime(standardstartCalendar.getTime());
		}
		
		//计算 指定日所在周期 的 计费开始 日期
		Calendar circleStartDate = Calendar.getInstance();
		if(standardstartCalendar!=null&&speciCalendar!=null){//得到指定日期 距离 合同开始日期的月数
			int passMonth = (speciCalendar.get(Calendar.YEAR)*12+speciCalendar.get(Calendar.MONTH))-(standardstartCalendar.get(Calendar.YEAR)*12+standardstartCalendar.get(Calendar.MONTH));
			int a = passMonth/circle;
			int b = passMonth%circle;
			if(standardstartCalendar.get(Calendar.DAY_OF_MONTH) < produceDay){ //如果合同开始日  < 结算日(26),
				a = (passMonth+1)/circle;// 在相差周期上 加多一个月,(往前算一个月)
				b = (passMonth+1)%circle;
				/**判断是什么意思?*/
				if(speciCalendar.get(Calendar.DAY_OF_MONTH)<produceDay ){// ||b!=0 --暂时去掉此条件
					if(b==0){
						a = a-1;// 整除的情况下,a -1 代表 往前算一个周期(少算一个) ? 为什么 
					}
				}
				circleStartDate.setTime(standardstartCalendar.getTime());
				circleStartDate.add(Calendar.MONTH, (a+appiont)*circle - 1);// -1 表示提前一个月提醒 的意思
				circleStartDate.set(Calendar.DAY_OF_MONTH, produceDay);
			}else{
				a = (passMonth)/circle;
				b = (passMonth)%circle;
				if(speciCalendar.get(Calendar.DAY_OF_MONTH)<produceDay ){// ||b!=0 --暂时去掉此条件
					if(b==0){
						a = a-1;
					}
				}
				circleStartDate.setTime(standardstartCalendar.getTime());
				circleStartDate.add(Calendar.MONTH, (a+appiont)*circle);
				circleStartDate.set(Calendar.DAY_OF_MONTH, produceDay);
			}
		}
		return circleStartDate;
	}

	/**
	 * 判断当前时间  <<是否需要>> 提醒该缴纳下一周期的预收款(提前一个月提醒)
	 * 条件(租赁开始日期，预收款缴纳周期，生成账单日，当前时间 )
	 * @param startDate 合同开始时间
	 * @param circle 合同周期
	 * @param produceDate 生成账单日
	 * @param nowStr 指定日期
	 * @return 
	 * CostTransactServiceImpl.isPayAdvance
	 */
	private boolean isPayAdvance(String startDate,int circle,int produceDate, String nowStr) {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//合同开始日期
		Calendar csStartCalendar = DateUtil.strToCalendar(startDate);
		//指定日期
		Calendar speciCalendar = DateUtil.strToCalendar(nowStr);
		boolean isPay = false;
		//如果指定日期 < 合同开始日期 ，则延后 一 月提醒
		if(speciCalendar.before(csStartCalendar)){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.set(speciCalendar.get(Calendar.YEAR), speciCalendar.get(Calendar.MONTH), speciCalendar.get(Calendar.DAY_OF_MONTH));
			tempCalendar.add(Calendar.MONTH, 1);
			//如果  合同开始  <= 延后一个月的时间 
			if(!tempCalendar.before(csStartCalendar)){
				return true;
			}else{
				return false;
			}
		}
		if(csStartCalendar!=null&&speciCalendar!=null){
			//得到 指定日期距离合同开始日期的月数
			int passMonth = (speciCalendar.get(Calendar.YEAR)*12+speciCalendar.get(Calendar.MONTH))-(csStartCalendar.get(Calendar.YEAR)*12+csStartCalendar.get(Calendar.MONTH));
			int a = passMonth/circle;
			int b = passMonth%circle;
			//开始催缴日期
			Calendar startPress = Calendar.getInstance();
			startPress.setTime(csStartCalendar.getTime());
			//结束催缴日期
			Calendar endPress = Calendar.getInstance();
			endPress.setTime(csStartCalendar.getTime());
			
			boolean flag = false;
			if(csStartCalendar.get(Calendar.DAY_OF_MONTH)<produceDate){//如果 合同开始日期的日份 < 生成账单日
				if(b==circle-2||b==circle-1){// 少于周期一个月或两个月？ 
					startPress.add(Calendar.MONTH, (a+1)*circle-2);
					startPress.set(Calendar.DAY_OF_MONTH, produceDate);
System.out.println(startPress.getTime());
					endPress.add(Calendar.MONTH, (a+1)*circle-1);
					endPress.set(Calendar.DAY_OF_MONTH, produceDate);
					endPress.add(Calendar.DAY_OF_MONTH, -1);
System.out.println(endPress.getTime());
					flag = true;
				}else if(b==0){
					
				}
			}else{// 账单生成日  < 合同开始日          合同开始 -> 指定日  的月数  a=周期数 ,b=余数 
				if(b==0 || circle==b+1 ){// 
					startPress.add(Calendar.MONTH, (a+1)*circle-1);
					startPress.set(Calendar.DAY_OF_MONTH, produceDate);
System.out.println(startPress.getTime());
					endPress.add(Calendar.MONTH, (a+1)*circle);
					endPress.set(Calendar.DAY_OF_MONTH, produceDate);
					endPress.add(Calendar.DAY_OF_MONTH, -1);
System.out.println(endPress.getTime());
					flag = true;
				}
				/*else if( circle == b + 2){
					
				}*/
			}
			/**以上判断  代码 一处 都不进, 有问题!!! --无问题. 不进有不进的道理*/
			//如果指定日期　开始催款日期和结束催款日期之间
			if(!speciCalendar.before(startPress)
					&&!speciCalendar.after(endPress)
					&&flag){
				isPay = true;
			}
		}
		return isPay;
	}
	
	private boolean isAdvancePressDate(String csStartDate,int circle,int produceDate, String nowStr) {
		//合同开始日期
		Calendar csStartCalendar = DateUtil.strToCalendar(csStartDate);
		//指定日期
		Calendar speciCalendar = DateUtil.strToCalendar(nowStr);
//		boolean isPay = false;
		//如果指定日期 < 合同开始日期 ，则延后 一 月提醒
		if(speciCalendar.before(csStartCalendar)){
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.set(speciCalendar.get(Calendar.YEAR), speciCalendar.get(Calendar.MONTH), speciCalendar.get(Calendar.DAY_OF_MONTH));
			tempCalendar.add(Calendar.MONTH, 1);
			//如果  合同开始  <= 延后一个月的时间 
			if(!tempCalendar.before(csStartCalendar)){
				return true;
			}else{
				return false;
			}
		}else {
			return true ;
		}
		/*if(csStartCalendar!=null&&speciCalendar!=null){
			//得到 指定日期距离合同开始日期的月数
			int passMonth = (speciCalendar.get(Calendar.YEAR)*12+speciCalendar.get(Calendar.MONTH))-(csStartCalendar.get(Calendar.YEAR)*12+csStartCalendar.get(Calendar.MONTH));
			int a = passMonth/circle;
			int b = passMonth%circle;
			//开始催缴日期
			Calendar startPress = Calendar.getInstance();
			startPress.setTime(csStartCalendar.getTime());
			//结束催缴日期
			Calendar endPress = Calendar.getInstance();
			endPress.setTime(csStartCalendar.getTime());
			
			boolean flag = false;
			if(csStartCalendar.get(Calendar.DAY_OF_MONTH)<produceDate){//如果 合同开始日期的日份 < 生成账单日
				if(b==circle-2||b==circle-1){// 少于周期一个月或两个月？ 
					startPress.add(Calendar.MONTH, (a)*circle-2);
					startPress.set(Calendar.DAY_OF_MONTH, produceDate);
System.out.println(startPress.getTime());
					endPress.add(Calendar.MONTH, (a)*circle-1);
					endPress.set(Calendar.DAY_OF_MONTH, produceDate);
					endPress.add(Calendar.DAY_OF_MONTH, -1);
System.out.println(endPress.getTime());
					flag = true;
				}else if(b==0){
					
				}
			}else{// 账单生成日  < 合同开始日          合同开始 -> 指定日  的月数  a=周期数 ,b=余数 
				if(b==0 || circle==b+1 ){// 
					startPress.add(Calendar.MONTH, (a+1)*circle-1);
					startPress.set(Calendar.DAY_OF_MONTH, produceDate);
System.out.println(startPress.getTime());
					endPress.add(Calendar.MONTH, (a+1)*circle);
					endPress.set(Calendar.DAY_OF_MONTH, produceDate);
					endPress.add(Calendar.DAY_OF_MONTH, -1);
System.out.println(endPress.getTime());
					flag = true;
				}
				else if( circle == b + 2){
					
				}
			}
			*//**以上判断  代码 一处 都不进, 有问题!!! --无问题. 不进有不进的道理*//*
			//如果指定日期　开始催款日期和结束催款日期之间
			if(!speciCalendar.before(startPress)
					&&!speciCalendar.after(endPress)
					&&flag){
				isPay = true;
			}
		}
		return isPay;
		*/
	}
	

	/**
	 * 更新awoke_task表中的未交款客户数量
	 */
	public void updateAwokeTask4PressMoney() {
		int amount = 0;
		String result = "0";
		try {
			//获取需要缴款的客户的数量，将值赋给amount
			result = costTransactDAO.query("select count(distinct b.client_id) from c_bill b where b.status=0");
			amount = Integer.parseInt(result);
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressMoney的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.PRESSMONEY+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ClientBill> getPressMoneyClient(CostTransactForm form) throws Exception {
		List<ClientBill> list = null;
		list = costTransactDAO.getPressMoneyClient(form);
		return list;
	}

	/**
	 * 计算需要缴纳<<预收款??>> 客户的数量 
	 * @return
	 */
	public List getPressAdvanceClient(CostTransactForm form)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = dateFormat.format(new Date());
		if(form==null){
			form = new CostTransactForm();
		}
		form.setCountDate(nowStr);
		
		List<Object> list = null;
		//客户id，name,amount,amountWuYF 集合 -- 集合size 最多为1 ？
		list = costTransactDAO.getCurAvailClientWithAdvanceWuYF(form);
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				
				int clientId = Integer.parseInt(GlobalMethod.ObjToParam(obj[0], "0"));
				String clientName = GlobalMethod.ObjToStr(obj[1]);
				double amount = Double.parseDouble(GlobalMethod.ObjToParam(obj[2], "0"));//周期预收款?（租金）
				double amountWuYF = Double.parseDouble(GlobalMethod.ObjToParam(obj[3], "0"));//周期预收款?（物业费）
				String isNotice = GlobalMethod.ObjToStr(obj[4]);
				
				//得到下一周期需要缴纳的房租
				double nextRent = getAppointDateRentByClientId(clientId,1,nowStr,true,true,0,false);
		/** 2018年1月17日10:20:25 gd ：获取下一周期需缴纳的物业费*/
				double nextWuyf = 0 ;
				nextWuyf = getAppointDateWuYFByClientId(clientId,1,nowStr,true,true,0,false);
				
				//得到当前周期需要缴纳的房租
				double curCircleRent = 0.0;
				double curCircleWuYF = 0.0;
				int temp = 0;
				//该处修改于2011-12-27日，修改的作用主要是由于“已通知入住”的用户不能生成账单，但是客户可能是通知入住后很长时间都没有
				//正式入住，缴费周期可能跨越了好几个周期，这样的话，在需要缴纳的预收款中，只能统计到本周期和下周起，而不能统计到前面的周期
				//这样就会有问题，所有在这里设了个临时变量temp，指定如果状态是通知入住，则将做多前4个周期的预售房租纳入到本周期的应缴中。
				//该处其实还有问题，如果一个客户入住后，提醒他本周期需要缴纳的预收款，如果本周起已经过去了一个月，房租已经从预收款中扣除了一月，
				//这时在提醒本周起需缴纳的预收款时候，有可能会多加一月的费用，就是已经扣除的那个月的费用。
				if(isNotice.equals(Contants.HAVENOTICE)){
					temp = -4;
				}
				for(;temp<=0;temp++){
					
					boolean currPayFlag = false ;
					if(!isNotice.equals(Contants.HAVENOTICE)){
						currPayFlag = true ;
					}
					curCircleRent += getAppointDateRentByClientId(clientId,temp,nowStr,false,true,0,currPayFlag);
					curCircleWuYF += getAppointDateWuYFByClientId(clientId,temp,nowStr,false,true,0,currPayFlag);
				}
				//如果预收款剩余量不足于缴纳下一周期房租
				//加入了一个四舍五入一个double类型数相加会出现.00000001现在是保留两位小数去除此情况
				double money = GlobalMethod.round(nextRent+curCircleRent );
				if(amount < money || amountWuYF <  curCircleWuYF + nextWuyf){
					
					Map<String,Object> map2 = new HashMap<String,Object>();
					map2.put("clientId", clientId);//客户id
					map2.put("clientName", clientName);//客户名称
					map2.put("curAdvance", amount);//当前预收款金额
					map2.put("curCircleRent", curCircleRent);//当前周期还需要缴纳的房租
					map2.put("nextRent", nextRent);//下一周期房租
					double mustpay = GlobalMethod.round(nextRent+curCircleRent - amount);//
					map2.put("mustPay", mustpay < 0 ? 0 : mustpay);
					
					/** 2018年1月17日14:42:58 */
					map2.put("curAdvanceWuYF", amountWuYF);//当前预收物业费金额
					map2.put("curCircleWuYF", curCircleWuYF);//当前周期还需要缴纳的房租
					map2.put("nextWuyf", nextWuyf);//下一周期 预收物业费 
					double leastPay = GlobalMethod.round(curCircleWuYF+nextWuyf - amountWuYF);//
					map2.put("leastPay", leastPay < 0 ? 0 : leastPay);//数值 不为负数.
					list2.add(map2);
				}
			}
		}
		return list2;
	}
	
	/**
	 * 更新awoke_task表中的需缴纳预收款客户的数量
	 */
	public void updateAwokeTask4PressAdvance() {
		int amount = 0;
		String result = "0";
		try {
			//获取需要缴款的客户的数量，将值赋给amount
			List list = getPressAdvanceClient(null);
			amount = list.size();
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressAdvance的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.PRESSADVANCE+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得需要缴押金客户列表 (包括房租押金和装修押金)
	 */
	public List getPressDepositClient(CostTransactForm form) throws Exception {
		List list = null;
		if(form!=null&&!"".equals(GlobalMethod.NullToSpace(form.getDepositType()))){
			if(form.getDepositType().equals(Contants.RENT_DEPOSIT)){//如果是房租押金
				list = costTransactDAO.getPressDepositRentClient(form);
			}else{//如果是装修押金
				list = costTransactDAO.getPressDepositDecorationClient(form);
			}
		}else{
			if(form==null){
				form = new CostTransactForm();
			}
			form.setDepositType(Contants.RENT_DEPOSIT);//设为房租押金
			list = costTransactDAO.getPressDepositRentClient(form);
			form.setDepositType(Contants.DECORATION_DEPOSIT);//设为装修押金
			list.addAll(costTransactDAO.getPressDepositDecorationClient(form));//将房租押金和装修押金的信息放一块
		}
		
		return list;
	}

	//更新awoke_task表中的未交房租押金客户数量
	@Override
	public void updateAwokeTask4PressRentDeposit() {
		int amount = 0;
		String result = "0";
		try {
			CostTransactForm form = new CostTransactForm();
			form.setDepositType(Contants.RENT_DEPOSIT);
			//获取未缴房租押金客户的数量
			amount = getPressDepositClient(form).size();
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressRentAdvance的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.PRESSRENTDEPOSIT+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//更新awoke_task表中的未交装修押金客户数量
	public void updateAwokeTask4PressDecorationDeposit() {
		int amount = 0;
		String result = "0";
		try {
			CostTransactForm form = new CostTransactForm();
			form.setDepositType(Contants.DECORATION_DEPOSIT);
			//获取未缴装修押金客户的数量
			amount = getPressDepositClient(form).size();
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressDecorationDeposit的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.PRESSDECORATIONDEPOSIT+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取需要缴纳订金的客户
	 * CostTransactServiceImpl.getPressEarnestClient
	 */
	public List getPressEarnestClient(CostTransactForm form) throws Exception {
		List list = null;
		list = costTransactDAO.getPressEarnestClient(form);
		return list;
	}
	public void updateAwokeTask4PressEarnest() {
		int amount = 0;
		String result = "0";
		try {
			//获取未缴订金客户的数量
			amount = getPressEarnestClient(null).size();
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressAdvance的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.PRESSEARNEST+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取已缴定金尚未入住的客户数量
	 */
	public void updateAwokeTask4PressENotIn() {
		int amount = 0;
		try {
			//获取已缴定金尚未入住的客户数量
			amount = costTransactDAO.countCompact();
		} catch (Exception e) {
			amount = 0;
		}
		try {
			//更新awoke_task表中code=pressAdvance的amount字段的值
			costTransactDAO.update("update AwokeTask set amount="+amount+" where code='"+Contants.ENOTIN+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除账单
	 * CostTransactServiceImpl.clearBill
	 */
	@Override
	public void clearBill() {
		try {
			costTransactDAO.update("update CompactRoomCoststandard t set lastBuildDate=''");
			costTransactDAO.update("delete from CBill");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 清除账单日志，用于可以再次生成账单 
	 * CostTransactServiceImpl.clearBillLog
	 */
	@Override
	public void clearBillLog() {
		try {
			costTransactDAO.update("delete from CBuildBillsLog");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
