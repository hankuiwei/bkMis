package com.zc13.util;

public class Constant {

	//导出楼盘报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_LP_HEADER = {"楼盘名称","物业名称","联系电话","传真","占地面积","绿化面积","使用面积","开工日期","竣工日期","进驻日期","所属区域"};
	public static final String[] EXCEL_LP_VALUE = {"getLpName","getRegional","getPhone","getFax","getConstructionArea","getVirescenceArea","getUseArea","getBeginDate","getEndDate","getUseDate","getRegional"};
	
	//导出客户信息报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_CUSTOMERMANAGE_HEADER = {"客户代码","客户名称","证件号","联系人","联系电话","客户类别","是否高新技术企业","注册时间","注册资金","单位全称"};
	public static final String[] EXCEL_CUSTOMERMANAGE_VALUE = {"getCode","getName","getIdentityCard","getLinkMan","getPhone","getClientType","getIsHighTech","getLoginDate","getLoginFund","getUnitName"};
	
	//导出预入驻信息报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_DESTINE_HEADER = {"客户名称","合同号","联系电话","房间号","面积","客户类型","合同类型","合同开始日期","合同到期日期","是否已交定金","状态"};
	public static final String[] EXCEL_DESTINE_VALUE = {"getName","getCode","getPhone","getRoomCodes","getTotalArea","getClientType","getType","getBeginDate","getEndDate","getIsEarnest","getStatus"};
	
	//导出意向书信息报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_INTENTION_HEADER = {"客户名称","意向书编号","联系电话","房间号","面积","客户类型","合同开始日期","合同到期日期","是否已交定金","状态"};
	public static final String[] EXCEL_INTENTION_VALUE = {"getName","getIntentionCode","getPhone","getRoomCodes","getTotalArea","getClientType","getBeginDate","getEndDate","getIsEarnest","getStatus"};
	
	//导出入驻信息报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_CUSTOMERRMANAGER_HEADER = {"客户名称","合同号","联系电话","房间号","客户类型","合同类型","合同开始日期","合同到期日期","入驻时间","入驻状态"};
	public static final String[] EXCEL_CUSTOMERRMANAGER_VALUE = {"getName","getCode","getPhone","getRoomCodes","getClientType","getType","getBeginDate","getEndDate","getInDate","getIsNotice"};
	
	//导出楼栋报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_BUILD_HEADER = {"楼幢名称","所属楼盘","使用面积","楼层数","区块","用途","结构","开工日期","竣工日期"};
	public static final String[] EXCEL_BUILD_VALUE = {"getBuildName","getELp","getUseArea","getFloor","getBlock","getPurpose","getStructure","getBeginDate","getEndDate"};
	
	//导出房间报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_ROOM_HEADER = {"房间号","房型","所属楼栋","单元号","所在楼层","建筑面积","使用面积","朝向","使用状态","所属区域","备注"};
	public static final String[] EXCEL_ROOM_VALUE = {"getRoomCode","getRoomType","getEBuilds","getUnitId","getFloor","getConstructionArea","getUseArea","getToward","getStatus","getRegional","getBz"};
	//导出应收账款报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_BILL_HEADER = {"客户名称","费用类型","房号","账单月份","单据状态","开始日期","结束日期","最后缴款日期","合同金额","滞纳金额","收款日期","实收金额"};
	public static final String[] EXCEL_BILL_VALUE = {"getCompactClient","getStandardName","getERooms","getBillDate","getStatus","getStartDate","getEndDate","getCloseDate","getBillsExpenses","getDelayingExpenses","getCollectionDate","getActuallyPaid"};
	//导出收费信息报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_CHARGE_HEADER = {"单据类型","单据号","收款日期","收款员","总金额","合同金额","暂存款","预收款","押金"};
	public static final String[] EXCEL_CHARGE_VALUE = {"getBillType","getBillNum","getDate","getRealName","getAmount","getBillAmount","getTemporalAmount","getAdvanceAmount","getDepositAmount"};
	
	//导出电费报表用。以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的get方法名称。
	public static final String[] EXCEL_ELEC_HEADER = {"客户名称","账单月份","金额","收款日期","实收金额"};
	public static final String[] EXCEL_ELEC_VALUE = {"getCompactClient","getBillDate","getBillsExpenses","getCollectionDate","getActuallyPaid"};
	
	//导出出库明细报表用。如果用的是map以HEADER结尾的是表头的显示,以value结尾的是对象下所有对性表头显示的map的键值
	public static final String[] EXCEL_OUTPUT_DETAIL = {"出库单编号","材料名称","材料编号","规格","出库数量","单位","出库时间","领用部门","领用人员"};
	public static final String[] EXCEL_OUTPUT_VALUE = {"inoutCode","name","code","spec","amount","unit","outDate","codeName","man"};
	//导出入库明细报表用。如果用的是map以HEADER结尾的是表头的显示，以value结尾的是对象下所有对应表头显示的map的键值
	public static final String[] EXCEL_INPUT_DETAIL = {"入库单编号","材料名称","材料编号","规格","入库数量","单位","单价","金额小计","入库人员","入库时间","供应商"};
	public static final String[] EXCEL_INPUT_VALUE = {"inoutCode","name","code","spec","amount","unit","unitPrice","amountMoney","man","date","suplyName"};
	//导出出库单报表
	public static final String[] EXCEL_OUT_DETAIL = {"出库单编号","出库时间","领用人员","领用部门","状态"};
	public static final String[] EXCEL_OUT_VALUE = {"outputCode","outputDate","man","codeName","status"};
	//导出入库单报表
	public static final String[] EXCEL_IN_DETAIL = {"入库编号","入库时间","供应商","入库人员","发票编号","金额","状态"};
	public static final String[] EXCEL_IN_VALUE = {"inputCode","inputDate","supplierName","man","money","invoiceCode","status"};
	//导出现有库存
	public static final String[] EXCEL_EXIST_DEPOT = {"材料名称","材料编号","当前库存","单位",};
	public static final String[] EXCEL_EXIST_VALUE = {"materName","materCode","nowStock","codeName"};
	//材料设置
	public static final String[] EXCEL_SET_MATERIAL = {"材料编号","材料名称","规格","单位","单价","库存上限","库存下限","材料类别"};
	public static final String[] EXCEL_SET_VALUE = {"materCode","materName","spec","codeName","unitPrice","upperLimit","lowerLimit","typeName"};
	//工人工作情况信息报表
	public static final String[] EXCEL_WCPERSONAL_DETAIL = {"姓名","编号","性别","电话","用工状态","是否在岗","是否已派出","工作时间总和","非常满意","满意","不满意","工作次数"}; 
	public static final String[] EXCEL_WCPERSONAL_VALUE = {"personnelName","personnelCode","sex","phone","status","isInPost","isOut","totalHour","verySatisfied","satisfied","notSatisfied","number"};
	//员工信息报表
	public static final String[] EXCEL_PERSONAL_DETAIL = {"应聘渠道","姓名","性别","到职日期","学历/学位","专业","职称/资格","政治面貌","民族","所属部门"}; 
	public static final String[] EXCEL_PERSONAL_VALUE = {"engageChannel","personnelName","sex","startDate","academicCertificate","speciality","duty","groups","nation","departmentCode"};
	//留学人员报表
	public static final String[] EXCEL_FORIGN_PERSON = {"姓名","性别","现住国家","国外学位","研究成果","工作单位","享受政策"};
	public static final String[] EXCEL_FORIGN_VALUE = {"getName","getSex","getCurrentCountry","getForeignDegree","getResearchResult","getCompany","getEnjoyPolicy"};
	//员工考勤信息报表
	public static final String[] EXCEL_RECORD_DETAIL = {"姓名","编号","工号","时间","考勤类型"}; 
	public static final String[] EXCEL_RECORD_VALUE = {"personnelName","personnelCode","personnelNum","time","status"};
	//合同信息报表
	public static final String[] EXCEL_COMPACT_PERSON = {"合同编号","合同类型","电话","租赁面积","签订日期","租赁开始日期","租赁结束日期","经办人","状态"};
	public static final String[] EXCEL_COMPACT_VALUE = {"getCode","getType","getPhone","getTotalArea","getSignDate","getBeginDate","getEndDate","getMan","getStatus"};
	//统计客户详细信息的报表
	public static final String[] EXCEL_CUSTOMER_DETAIL = {"客户名称","客户房间","客户类别","房间面积","入住时间","迁出时间"};
	public static final String[] EXCEL_CUSTOMER_VALUE = {"customerName","roomFullName","clientType","userArea","beginDate","endDate"};
	//用户表具读表的报表
	public static final String[] EXCEL_USERMETER_DETAIL = {"楼幢名称","客户名称","房间号","表具编号","开始日期","结束日期","上月读表","本月读表","本月用量"};
	public static final String[] EXCEL_USERMETER_VALUE = {"getBuildName","getUnitName","getRoomCode","getCode","getBeginDate","getEndDate","getLastRecord","getThisRecord","getUseRecord"};
	//公摊表具编辑读表的报表
	public static final String[] EXCEL_PUBLICMETER_DETAIL = {"表具编号","开始日期","结束日期","上月读表","本月读表","本月用量"};
	public static final String[] EXCEL_PUBLICMETER_VALUE = {"getCode","getBeginDate","getEndDate","getLastRecord","getThisRecord","getPublicRecord"};
	//公摊表具查看读表的报表
	public static final String[] EXCEL_PUBLICMETER_DETAIL2 = {"表具编号","年月","开始日期","结束日期","上月读表","本月读表","本月用量"};
	public static final String[] EXCEL_PUBLICMETER_VALUE2 = {"getCode","getYearMonth","getBeginDate","getEndDate","getLastRecord","getThisRecord","getPublicRecord2"};
	//导出客户报修项目的报表
	public static final String[] EXCEL_REPAIREPROJECT_DETAIL = {"项目编码","项目名称","类别","服务价格(单位元)","要求相应时间(小时)","说明"};
	public static final String[] EXCEL_REPAIREPROJECT_VALUE = {"getCode","getName","getType","getRate","getResponseTime","getBz"};
	//导出客户报修报表
	public static final String[] EXCEL_CLIENT_DETAIL = {"报修时间","报修人","报修项目名称","联系电话","接听(接待)人","派工人","报修内容","报修状态"};
	public static final String[] EXCEL_CLIENT_VALUE = {"getDate","getName","getProject","getPhone","getClerk","getSendedMan","getContentExplain","getStatus"};
	//导出客户投诉报表
	public static final String[] EXCEL_COMPLAIN_DETAIL = {"投诉时间","业户名称","联系电话","接待(接听)人","投诉内容","处理时间","处理状态"};
	public static final String[] EXCEL_COMPLAIN_VALUE = {"getComplaintDate","getName","getPhone","getClerk","getComplaintContent","getDisposalDate","getStatus"};
	//导出材料出处报表
	public static final String[] EXCEL_CLIENTMATERIAL_DETAIL = {"出料部门","材料编号","材料名称","个数","单价","总金额","出料时间","材料消耗详情"};
	public static final String[] EXCEL_CLIENTMATERIAL_VALUE = {"getDepartment","getMaterialCode","getMaterialName","getAmount","getUnitPrice","getAmountMoney","getOutDate","getRoomName"};
	//导出出入库核算表明细报表
	public static final String[] EXCEL_ACCOUNT_DETAIL = {"材料名称","材料编号","单位","规格型号","单价","期初数量","期初金额","本期入库数量","本期入库金额","本期出库数量","本期出库金额","期末数量","期末金额"};
	public static final String[] EXCEL_ACCOUNT_VALUE = {"name","code","unit","spec","unitPrice","qiAmount","qiMoney","benInAmount","benInMoney","benOutAmount","benOutMoney","residue","balance"};
	//导出电话费用信息列表
	public static final String[] EXCEL_PHONECOSTINFO_DETAIL = {"电话号码","所在房间号","住户名称","通话总时长(单位秒)","话费总金额(￥)"};
	public static final String[] EXCEL_PHONECOSTINFO_VALUE = {"phoneNumber","roomFullName","clientName","callTime","cost"};
	//导出电话费用信息详情列表
	public static final String[] EXCEL_PHONECOSTINFODETAIL_DETAIL = {"通话开始时间","通话结束时间","通话时长(单位秒)","通话类型","地区名称","对方号码","本次通话费用（￥）","缴费情况"};
	public static final String[] EXCEL_PHONECOSTINFODETAIL_VALUE = {"startTime","endTime","callTime","callType","areaName","phone","cost","isPaid"};
	//导出收据信息详情列表
	public static final String[] EXCEL_RECEIPT_DETAIL = {"客户名称","收款日期","收据号","收款员","总金额","项目明细","金额明细","发票号","未开发票金额"};
	public static final String[] EXCEL_RECEIPT_VALUE = {"clientName","reciveCostDate","billNum","reciveUserName","amount","openBill","invoiceNum","notOpenBill"};
	//导出发票信息详情列表
	public static final String[] EXCEL_INVOICE_DETAIL = {"客户名称","操作员","开票日期","发票号","发票总金额","发票内容","项目明细","发票金额"};
	public static final String[] EXCEL_INVOICE_VALUE = {"clientName","date","invoiceNum","standardname","realName","amount"};
	//导出电话费用信息详情列表
	public static final String[] EXCEL_PHONECOSTINFO_BILL_DETAIL = {"通话号码", "通话开始时间","通话结束时间","通话时长","地区名称","对方号码","本次通话费用（￥）","缴费情况"};
	public static final String[] EXCEL_PHONECOSTINFO_BILL_VALUE = {"callerPhone", "startTime","endTime","callTime","areaName","calledPhone","cost","isPaid"};
	//财务房租统计信息列表
	public static final String[] EXCEL_RENTINFO_DETALL = {"客户名称","房间号","房租","房租余额","房租押金","装修押金"};
	public static final String[] EXCEL_RENTINFO_VALUE = {"clientName","roomCodes","rent","advanceRent","rentDeposit","decorationDeposit"};

	/******************start*********************/
	public static final String[] EXCEL_YEAR_CALCULATE_DETAIL = {"年度","金额"};
	public static final String[] EXCEL_YEAR_CALCULATE_VALUE = {"year","money"};
	
	public static final String[] EXCEL_YEAR_DETAIL_CALCULATE_DETAIL = {"月份","金额"};
	public static final String[] EXCEL_YEAR_DETAIL_CALCULATE_VALUE = {"month","money"};
	
	public static final String[] EXCEL_MONTH_DETAIL_CALCULATE_DETAIL = {"合同编号","客户名称","金额"};
	public static final String[] EXCEL_MONTH_DETAIL_CALCULATE_VALUE = {"code","name","money"};
	
	public static final String[] EXCEL_COMPACT_CALCULATE_DETAIL = {"周期开始时间","周期结束时间","账单开始时间","账单结束时间","账单月份","金额"};
	public static final String[] EXCEL_COMPACT_CALCULATE_VALUE = {"circleStart","circleEnd","startTime","endTime","billDate","money"};
	
	public static final String[] EXCEL_FINANCIAL_REPORT_PROJECT = {"应付工资","应付福利费(14%)","工会经费(2%)","教育经费(2.5%)","补充医疗(4%)","企业年金(4%)","基本养老保险","基本医疗保险","住房公积金 ","失业保险","工伤保险","生育保险 ","交通费 ","电话费 ","折旧费","业务招待费","差旅费 ","办公费","审计费、律师费 ","董事会费","印花税","会议费","其他 ","管理费用合计(不含房产折旧、土地税、房产税) ","房产、土地摊销 ","土地税 ","房产税 ","费用合计 "};
	public static final String[] EXCEL_FINANCIAL_REPORT_PROMONEY = {"proName","glys","gcys","zsys","yshj","glsj","gcsj","zssj","sjhj","nlj","zqn"};
	public static final String[] EXCEL_FINANCIAL_REPORT_OPERATEMONEY = {"costName","byCost","nljCost","ysjeCost","zqnCost","operateBlank","incomeName","byIncome","nljIncome","ysjeIncome","zqnIncome"};
	/******************end*********************/
}
