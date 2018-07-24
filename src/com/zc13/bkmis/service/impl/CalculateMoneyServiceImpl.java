package com.zc13.bkmis.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zc13.bkmis.dao.ICalculateMoneyDAO;
import com.zc13.bkmis.form.CalculateMoneyForm;
import com.zc13.bkmis.mapping.CAccounttemplate;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CalculateMoney;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.service.ICalculateMoneyService;
import com.zc13.util.Contants;
import com.zc13.util.CostTransactUtil;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.cacu.Cacu;
import com.zc13.util.complier.template.BuildDynamicClass;
import com.zc13.util.complier.template.Function;


/**
 * 合同预算 impl
 * @author Administrator
 * @Date 2013-3-23
 * @Time 上午10:54:33
 */
public class CalculateMoneyServiceImpl extends BasicServiceImpl implements
		ICalculateMoneyService {

	public ICalculateMoneyDAO getCalculateDAO() {
		return calculateDAO;
	}

	public void setCalculateDAO(ICalculateMoneyDAO calculateDAO) {
		this.calculateDAO = calculateDAO;
	}

	private ICalculateMoneyDAO calculateDAO;

	/**
	 * 获取需要计算合同
	 */
	@Override
	public List<Compact> getNeedCompact(Integer compactId) {
		
		return calculateDAO.getNeedCalculateCompact(compactId);
	}

	/**
	 * 获取楼盘下的帐套
	 */
	@Override
	public CAccounttemplate getAccounttemlateByLpId(Integer lpId) {
		
		return calculateDAO.getAccounttemplateById(lpId);
	}

	/**
	 * 
	 * @return
	 * Date:2013-3-23 
	 * Time:下午3:22:01
	 */
	public List<CompactRoomCoststandard> getCompactRoomCoststandardById(Integer compactId){
		
		return calculateDAO.getCompactRoomCoststandardByCompactId(compactId);
	}
	/**
	 * 计算逻辑
	 * @throws Exception 
	 */
	@Override
	public void CalculateMoney(List<CompactRoomCoststandard> compactRoomCoststandard,CAccounttemplate accountTemplate,Integer compactId) throws Exception {
		
		//汇总前先进行删除
		if(null != compactId){
			calculateDAO.deleteCalculateMoney(compactId);
		}else{
			calculateDAO.deleteCalculateMoney(null);
		}
		List<CalculateMoney> list = new ArrayList<CalculateMoney>();
		//循环合同
		if(compactRoomCoststandard != null && compactRoomCoststandard.size()>0){
			for(CompactRoomCoststandard crc : compactRoomCoststandard){
				calculateParam(crc,accountTemplate,list);
			}
		}
		for(CalculateMoney calculateMoney : list){
			calculateDAO.saveOrUpdateObject(calculateMoney);
		}
	}
	
	/**
	 * 计算逻辑方法
	 * @param compact
	 * @param produceDay
	 * Date:2013-3-23 
	 * Time:下午2:14:55
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void calculateParam(CompactRoomCoststandard crc,CAccounttemplate accountTemplate,List<CalculateMoney> list) throws Exception{
		
		Compact compact = crc.getCompact();
		int produceDay = Integer.parseInt(accountTemplate.getProduceDate());
		String beginDate = GlobalMethod.NullToSpace(compact.getBeginDate());//合同开始时间
		String endDate = GlobalMethod.NullToSpace(compact.getEndDate());//合同结束时间
		String goDate = GlobalMethod.NullToSpace(compact.getGoDate());//迁出时间
		int lpId = compact.getLpId();
		
		if(!"".equals(goDate)){
			endDate = goDate;
		}
		
		Calendar calBeginDate = Calendar.getInstance();
		calBeginDate.setTime(DateUtil.parseDate(beginDate));
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(DateUtil.parseDate(endDate));
		
		Calendar circleBeginDate = Calendar.getInstance();
		circleBeginDate.setTime(DateUtil.parseDate(beginDate));
		Calendar circleEndDate = Calendar.getInstance();
		circleEndDate.setTime(circleBeginDate.getTime());
		
		/********用于计算周期时间变量***********/
		Calendar cirStart = Calendar.getInstance();
		cirStart.setTime(DateUtil.parseDate(beginDate));
		Calendar cirEnd = Calendar.getInstance();
		cirEnd.setTime(cirStart.getTime());
		Calendar tempStart = Calendar.getInstance();
		/********用于计算周期时间变量***********/
		//循环计算合同的每月的钱
		int flag = 0;
		int circle = Integer.parseInt(GlobalMethod.ObjToParam(compact.getCircle(), "0"));
		while(!circleBeginDate.after(calEndDate)){
			
			String startTime = DateUtil.formatDate(circleBeginDate.getTime());
			int startDay = circleBeginDate.get(Calendar.DAY_OF_MONTH);
			if(startDay<produceDay){
				circleEndDate.set(Calendar.DAY_OF_MONTH, produceDay);
			}else{
				circleBeginDate.add(Calendar.MONTH, 1);
				circleEndDate.setTime(circleBeginDate.getTime());
				if(circleEndDate.get(Calendar.DAY_OF_MONTH)>=produceDay){
					circleEndDate.set(Calendar.DAY_OF_MONTH, produceDay);
				}
			}
			
			circleBeginDate.setTime(circleEndDate.getTime());
			circleEndDate.add(Calendar.DAY_OF_MONTH, -1);
			if(!circleEndDate.before(calEndDate)){
				circleEndDate.setTime(calEndDate.getTime());
			}
			
			//以上用于计算计算每月的账单开始时间和结束时间
			String endTime = DateUtil.formatDate(circleEndDate.getTime());
			String billDate = DateUtil.formatDate(circleEndDate.getTime());
			billDate = billDate.substring(0,7);
			String year = String.valueOf(circleEndDate.get(circleEndDate.YEAR));
			String month = String.valueOf(circleEndDate.get(circleEndDate.MONTH)+1);
			
			//设置要查询需要的参数
			Map map = new HashMap();
			map.put(Contants.CLIENTID, compact.getClientId());//客户id
			map.put(Contants.COMPACTID, compact.getId());//合同id
			map.put(Contants.ROOMID, crc.getERooms().getRoomId());//房间id
			map.put(Contants.STANDARDID, crc.getCCoststandard().getId());//收费标准
			map.put(Contants.BILLSTARTDATE, startTime);//账单开始日期
			map.put(Contants.BILLENDDATE, endTime);//账单结束日期
			map.put(Contants.BILLDAY, billDate);//账单生成月
			map.put("circleBegin", startTime);
			map.put("circleEnd", endTime);
			
			map.put("cirStart", DateUtil.formatDate(cirStart.getTime()));
			if(flag == 0 && circle != 0){
				int start = cirStart.get(Calendar.DAY_OF_MONTH);
				if(start<produceDay){
					cirEnd.set(Calendar.DAY_OF_MONTH, produceDay);
					cirEnd.add(Calendar.MONTH, circle-1);
					System.out.println(DateUtil.formatDate(cirEnd.getTime()));
				}else{
					//cirStart.add(Calendar.MONTH, circle);
					//cirEnd.setTime(cirStart.getTime());
					tempStart.setTime(cirStart.getTime());
					tempStart.add(Calendar.MONTH, circle);
					cirEnd.setTime(tempStart.getTime());
					if(cirEnd.get(Calendar.DAY_OF_MONTH)>=produceDay){
						cirEnd.set(Calendar.DAY_OF_MONTH, produceDay);
					}
				}
				cirEnd.add(Calendar.DAY_OF_MONTH, -1);
			}
			if(!cirEnd.before(calEndDate)){
				cirEnd.setTime(calEndDate.getTime());
			}
			flag += 1;
			if(flag == circle){
				flag = 0;
				Calendar temp = Calendar.getInstance();
				temp.setTime(cirEnd.getTime());
				temp.add(Calendar.DAY_OF_MONTH, 1);
				cirStart.setTime(temp.getTime());
			}
			
			List<CompactRent> rent = calculateDAO.getRent(map);
			double value = calculate(crc,accountTemplate,rent,map);
			CalculateMoney money = new CalculateMoney();
			money.setCompactId(compact.getId());
			money.setClientId(compact.getClientId());
			money.setRoomId(crc.getERooms().getRoomId());
			money.setYear(year);
			money.setMonth(month);
			money.setBillDate(billDate);
			money.setStartTime(startTime);
			money.setEndTime(endTime);
			money.setMoney(value);
			money.setLpId(lpId);
			money.setCircleStart(GlobalMethod.ObjToStr(map.get("cirStart")));
			money.setCircleEnd(DateUtil.formatDate(cirEnd.getTime()));
			list.add(money);
		}
	}
	
	/**
	 * 计算逻辑
	 * @param crc
	 * @param accountTemplate
	 * @param rent
	 * Date:2013-3-23 
	 * Time:下午4:31:12
	 * @throws Exception 
	 */
	public double calculate(CompactRoomCoststandard crc,CAccounttemplate accountTemplate,List<CompactRent> rentList,Map map) throws Exception{
		
		String circleBegin = GlobalMethod.ObjToStr(map.get("circleBegin"));
		String circleEnd = GlobalMethod.ObjToStr(map.get("circleEnd"));
		
		//System.out.println("开始时间start---------"+circleBegin+"开始时间end");
		//System.out.println("开始时间start---------"+circleEnd+"开始时间end");
		Calendar cirBegin = Calendar.getInstance();
		cirBegin.setTime(DateUtil.parseDate(circleBegin));
		Calendar cirEnd = Calendar.getInstance();
		cirEnd.setTime(DateUtil.parseDate(circleEnd));
		
		double rent = 0;
		double previousValue = 0;
		double values = 0;
		// 公式
		String formula = crc.getCCoststandard().getFormula();
		//遍历时间：从开始时间遍历到结束时间
		while(!cirBegin.after(cirEnd)){
			double tempRent = 0;
			if(rentList!=null&&rentList.size()>0){
				for(CompactRent cr:rentList){
					if(!cirBegin.before(DateUtil.strToCalendar(cr.getBeginDate()))&&!cirBegin.after(DateUtil.strToCalendar(cr.getEndDate()))){
						tempRent = cr.getRent();
						break;
					}
				}
				if(new BigDecimal(rent).compareTo(new BigDecimal(tempRent))!=0){
					rent = tempRent;
					String tempDateStr = DateUtil.formatDate(cirBegin.getTime());
					map.put(Contants.RENTDAY, tempDateStr);//获取租金单价的日期条件
					// 得到算术公式
					//String expression = getExpression(map, formula);
					// 计算字符串的计算公式得到值
					//previousValue = Cacu.cacu(expression);
					previousValue = rent * crc.getERooms().getConstructionArea();
					values += previousValue;
					//System.out.println("================根据公式得到的值(每天的房租)=====================" + values);
				}else{
					values += previousValue;
				}
			}
			cirBegin.add(Calendar.DAY_OF_MONTH, 1);
		}
		// 根据进位方式和计算精度处理处理账单的值
		values = dealValueByPrecision(accountTemplate.getPrecision(),accountTemplate.getRounding(), values);
		return values;
	}

	/**
	 * 根据参数map和原始公式得到处理后的表达式
	 * 
	 * @param params{costId=11,costType=elec,......}
	 * @param expressions
	 *            公式 eg:{计费面积}*{单价}-12
	 * @return e.g:80.0*56.6-12
	 * @throws Exception
	 */
	public String getExpression(Map params, String expressions)throws Exception {
		
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
		
		Map typeName_values = getValueMapBySqls(params, paramNames);
		
		/* 根据得到的(类型名称-执行对应sql语句后的值)(key-value)键值对和公式得到该公式的计算结果 */
			String expressionsForValue = CostTransactUtil.assemblySql(typeName_values, expressions);
			return expressionsForValue;
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
		list = calculateDAO.getCostParaTypeByNames(paramNames);

		for (int i = 0; i < list.size(); i++) {
			// 获得代码
			String code = CostTransactUtil.assemblySql(params, list.get(i).getCode());
			// 获得代码类型
			String codeType = GlobalMethod.NullToSpace(list.get(i).getCodeType());
			// 执行代码后得到的值
			String reading = "";
			//System.out.println("计费参数代码为：" + code);
			if (codeType.equals("1")) {// 如果是sql语句
				reading = calculateDAO.query(code);
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
	 * 获取年度的钱统计
	 */
	@Override
	public List<CalculateMoney> getYearCompactMoney(CalculateMoneyForm form) {
		
		return calculateDAO.getYearCompactMoney(form);
	}
	
	/**
	 * 获取年度的钱统计数量
	 */
	@Override
	public int getYearCompactMoneyCount(CalculateMoneyForm form) {
		
		return calculateDAO.getYearCompactMoneyCount(form);
	}

	@Override
	public List<CalculateMoney> getYearDetail(CalculateMoneyForm form) {
		
		return calculateDAO.getYearDetail(form);
	}

	@Override
	public List getMonthDetail(CalculateMoneyForm form) {
		
		return calculateDAO.getMonthDetail(form);
	}

	@Override
	public int getMonthDetailCount(CalculateMoneyForm form) {
		
		return calculateDAO.getMonthDetailCount(form);
	}

	@Override
	public List getCompactBill(CalculateMoneyForm form) {
		
		return calculateDAO.getCompactBill(form);
	}

	/**
	 * 计算需要计算的合同id
	 */
	@Override
	public List<Integer> getNeedCalculateCompactIds() {
		
		return calculateDAO.getNeedCalculateCompactIds();
	}

	/**
	 * 根据楼盘id查询帐套
	 */
	@Override
	public CAccounttemplate getAccounttemlateByCompactId(Integer compactId) {
		
		return calculateDAO.getAccounttemlateByCompactId(compactId);
	}
}
