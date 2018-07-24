package com.zc13.util;
/**
 * 常量表
 * @author dongdk
 *
 */
public class Contants {
	
	static{
//		System.out.println("常量表！");
	}
	
	/*单据类型*/
	public static final String BILLTYPE_INVOICE = "1";//发票
	public static final String BILLTYPE_RECEIPT = "0";//收据
	/**
	 * 新增用户时的默认初始密码
	 */
	public static final String DEFAULTPASSWORD = "123456";
	
	
	/* 用户和角色表等，使用状态：1可用，0不可用 */
	public static final String ENABLE = "1";
	public static final String DISENABLE = "0";
	
	/* 角色等级：0为超级用户，1为操作员，2为普通用户 */
	public static final String RANGEADMIN = "0";
	public static final String RANGEOPERATE = "1";
	public static final String RANGEUSER = "2";
	
	/*系统模块划分 。写系统日志时用到*/
	/*** 房产信息管理*/
	public static final String L_BUILD = "房产信息管理";
	/*** 客户信息管理*/
	public static final String L_CLIENT = "客户信息管理";
	/*** 收费管理*/
	public static final String L_COST = "收费管理";
	/*** 客户服务平台*/
	public static final String L_SERVICE = "客户服务平台";
	/*** 综合信息分析*/
	public static final String L_ANNLYSIS = "综合信息分析";
	/*** 合同管理*/
	public static final String L_COMPACTMANAGE = "合同管理";
	/*** 仓库管理*/
	public static final String L_Depot = "仓库管理";
	/*** 人事管理*/
	public static final String L_HR = "人事管理";
	/*** 系统维护*/
	public static final String L_SYSTEM = "系统维护";
	
	//系统日志，操作类型
	/**添加*/
	public static final String ADD = "添加";
	/**修改*/
	public static final String MODIFY = "修改";
	/**删除*/
	public static final String DELETE = "删除";
	/**校验*/
	public static final String RELADAT = "校验";
	/**审批*/
	public static final String CHECK = "审批";
	/**审批*/
	public static final String LEAVE = "迁出";
	/**处理报修*/
	public static final String DOMAINTAIN = "处理报修";
	/**处理投诉*/
	public static final String DOCOMPLAIN = "处理投诉";
	
	/*房间使用状态 */
	/**未出租*/
	public static final String OUTUSE = "未出租";
	/**已预租*/
	public static final String DESTINE = "已预租";
	/**已出租*/
	public static final String BELEASED = "已出租";
	/**装修中*/
	
	
	public static final String INFITMENT = "装修中";
	//出入库明细表 0为出库，1为入库
	public static final String OUTPUTDEPOT = "0";
	public static final String INPUTDEPOT = "1";
	
	//意向书是否已转为合同
	/**意向书已转为合同*/
	public static final String CONVERT = "1";
	/**意向书未转为合同*/
	public static final String NOCONVERT = "0";
	
	//关于下面这些状态位的定义情况说明如下：
	//预入驻页面，能看到的所有合同列表都是合同状态为“预租”的那些，除此之外在没有
	//入驻页面，能看到的是那些审批状态为“通过审批”，并且合同状态不为“过期”的。除此之外没有
	//合同续租或者变更的时候，选择允许变更/续租的合同列表界面，能看到的是那些入驻状态为“已入驻”的，除此之外没有。
	
	//关于通过预租录入新增、续租新增、变更新增一个合同时，对各种状态的初始化说明：
	//新增时，默认分别为："预租"，"未通知"，"未提交审批"
	//变更时，默认全为空；当通过审批后，新的合同默认为： "常规"，"已入驻"，"通过审批"，老的则变为："过期"，"已变更"，"过期合同"
	//续租时，默认全为空；通过审批后，老合同为“常规”，“已续”，“通过审批”，新合同默认为空，空，“通过审批”。当老合同到期后，系统自动将新老合同状态变为：老：“过期”，“已续”，“过期合同”；新：“常规”，“已入驻”，“通过审批”
	
	//下面是几种状态间的转换说明：
	//合同刚签订，状态为“预租”，当合同正式入驻之后，合同状态由“预租”变为“常规”。当合同通过变更的审批后，老合同状态变为“过期”；当合同通过续租之后，合同状态不立即变化，直到合同自动到期那天，才会把合同状态变为“过期”
	//当合同正式入驻后，入驻状态变为“已入驻”，这时的合同才是真正有效的，即可以生成账单的。当合同变更或者续租，老合同的入驻状态相应的变为“已变更”和“已续”
	//当客户迁出，状态变为“过期”，入驻状态变为“已迁出”，审批状态变为“过期合同”
	
	/**
	 * 合同状态
	 */
	/**预租*/
	public static final String DESTINES = "预租";
	/**常规*/
	public static final String NORMARL = "常规";
	/**过期*/
	public static final String HASOVER = "过期";
	public static final String BEENDELETE = "被删除";
	/**
	 * 入驻状态
	 */
	public static final String NOTNOTICE = "未通知";
	public static final String HAVENOTICE = "已通知入驻";
	public static final String HAVEIN = "已入驻";
	public static final String HASAPPLYRELET = "已申请续租";
	public static final String HASRELET = "已续";
	public static final String HASAPPLYCHANGE = "已申请变更";
	public static final String HASCHANGE = "已变更";
	public static final String HASAPPLYGO = "已申请迁出";
	public static final String HASNOTICE = "已通知迁出";
	public static final String HASGO = "已迁出";
	//合同或意向书审批状态
	public static final String NOTSUBMIT = "未提交审批";           	//未提交审批
	public static final String ONAPPROVAL = "待审批";				//已提交，审批中
	public static final String THROUGHAPPROVAL = "通过审批";		//已通过审批
	public static final String NOTTHROUGH = "未通过审批";				//未通过审批
	//与合同有关的那些表的有效状态：1为有效，0为无效，这些表包括：客户房间表 e_room_client，合同房租关系表 compact_rent，房间对应收费标准表 compact_room_coststandard
	public static final String VALID = "1";
	public static final String INVALID = "0";
	
	//押金类型
	
	//系统收费项的value值
	public static final String DECORATION_DEPOSIT = "decoration_deposit";//装修押金
	public static final String RENT_DEPOSIT = "rent_deposit";//房租押金
	public static final String ITEM_RENT = "rent";//房租
	public static final String ITEM_DESTINERENT = "destineRent";//预收房租
	public static final String ITEM_EARNEST = "earnest";//定金
	public static final String ITEM_TEMPPORAL = "tempporal";//暂存款
	public static final String ITEM_REPAIR_SERVICES = "repair_services";//维修服务费
	
	//合同类型
	public static final String INTENTION = "意向书";
	public static final String COMPACT = "租赁合同";
	public static final String COMPACTRELET = "续租合同";
	public static final String COMPACTCHANGE = "变更合同";
	public static final String COMPACTDEAL = "合同退租";
	//此标志用来判断该租赁合同为正常还是预租，预租者只能在预租菜单点出的页面中看到。
	
	//各表单在表SysSequence中对应的英文代码
	/*** 任务单*/
	public static final String RWD = "rwd";
	/*** 合同编号*/
	public static final String HTH = "hth";
	
	//任务提醒名称对应代码
	/**即将过期合同处理*/
	public static final String COMPACTRIGHT = "compactRight";	
	/**报修处理*/
	public static final String MAINTAIN = "maintain";				
	/**投诉处理*/
	public static final String COMPLAINT = "complaint";		
	
	/**意向书校验*/
	public static final String COLLATEINTENTION = "collateIntention";
	/**新合同校验*/
	public static final String COLLATENEW = "collateNew";		
	/**变更合同校验*/
	public static final String COLLATECHAGE = "collateChage";	
	/**续租合同校验*/
	public static final String COLLATERELET = "collateRelet";		
	
	/**意向书审批*/
	public static final String APPROVEINTENTION = "approveIntention";
	/**新合同审批*/
	public static final String APPROVENEW = "approveNew";		
	/**变更合同审批*/
	public static final String APPROVECHANGE = "approveChange";	
	/**续租合同审批*/
	public static final String APPROVERELET = "approveRelet";
	
	/**催款*/
	public static final String PRESSMONEY = "pressMoney";
	/**催缴预收款*/
	public static final String PRESSADVANCE = "pressAdvance";
	/**催缴房租押金*/
	public static final String PRESSRENTDEPOSIT = "pressRentDeposit";
	/**催缴装修押金*/
	public static final String PRESSDECORATIONDEPOSIT = "pressDecorationDeposit";
	/**催缴定金*/
	public static final String PRESSEARNEST = "payEarnest";
	/**可以转为合同的意向书*/
	public static final String ENOTIN = "eNotIn";
	/**通知入住*/
	public static final String NOTICEIN ="noticeIn";
	/**正式入住*/
	public static final String IN ="in";
	
	//计费参数
	/*普遍收费标准参数*/
	/**客户id*/
	public static final String CLIENTID = "clientId";
	/**合同id*/
	public static final String COMPACTID = "compactId";
	/**房间id*/
	public static final String ROOMID = "roomId";
	/**收费标准id*/
	public static final String STANDARDID = "standardId";
	/**账单开始日期*/
	public static final String BILLSTARTDATE = "billStartDate";
	/**账单结束日期*/
	public static final String BILLENDDATE = "billEndDate";
	/**账单生成日*/
	public static final String BILLDAY = "billDay";
	/*滞纳金参数*/
	/**账单id*/
	public static final String BILLID = "billId";
	/**需要计算滞纳金的天数*/
	public static final String OVERDATE = "overDate";
	/**合同金额*/
	public static final String BILLSEXPENSES = "billsExpenses";
	/*房租特有参数*/
	/**获取租金单价的日期条件*/
	public static final String RENTDAY = "rentDay";
	/**单据年月*/
	public static final String YEARMONTH = "yearMonth";
	
	//收费项
	/**房租*/
	public static final String RENT = "房租";
	public static final String WuYFJF = "预收物业费";
	public static final String WuYFBNF = "预收物业费半年付";
	public static final String WuYFNF = "预收物业费年付";
	//结算出入库记录的状态
	public static final String ALREADYACCOUNT = "已结算";
	public static final String WAITACCOUNT = "未结算";
	
	
	//平面图类型
	/**平面图类型 楼盘平面图*/
	public static final String LP_MAP = "lpMap";
	/**平面图类型 楼栋平面图*/
	public static final String BUILD_MAP = "buildMap";
	/**平面图类型 楼层平面图*/
	public static final String FLOOR_MAP = "floorMap";
	
	//房产信息树的表名
	/**楼盘表名*/
	public static final String E_LP = "e_lp";
	/**楼栋表名*/
	public static final String E_BUILD = "e_builds";
	
	/*客户服务start*/
	//处理状态
	/**派工等待*/
	public static final String DISPATCHING_WAIT = "派工等待";
	/**维修中*/
	public static final String BEING_REPAIRED = "维修中";
	/**维修等待*/
	public static final String REPAIR_WAIT = "维修等待";
	/**维修结束*/
	public static final String REPAIR_COMPLETED = "维修结束";
	
	//工人派工状态
	/**已派遣*/
	public static final String IS_DISPATCH = "已派遣";
	/**已到场*/
	public static final String PERSONNEL_IS_REACH = "已到场";
	/**已离场*/
	public static final String PERSONNEL_IS_LEAVE = "已离场";
	
	//客户投诉
	public static final String UNPROCESSED = "未处理";
	public static final String PROCESSED = "已处理";
	/*
	public static final String DISPATCH = "已派工";
	public static final String DISPATCH_REACH = "已到场";
	
	public static final String DISPATCH_LEAVE = "已离场";
	*/
	
	//报修处理方式
	/**处理方式：物业维修*/
	public static final String DOMETHOD_MAINTENANCE = "物业维修";
	/**处理方式：维保期*/
	public static final String DOMETHOD_SERVICE = "维保期";
	
	//报修项目服务计费方式
	/**报修项目服务计费方式：按小时*/
	public static final String CHARGE_TYPE_HOUR = "按小时";
	/**报修项目服务计费方式：按次*/
	public static final String CHARGE_TYPE_TIMES = "按次";
	
	//报修类型
	/**报修类型：业主报修*/
	public static final String MAINTAIN_TYPE_OWNER = "业主报修";
	/**报修类型：公共区域*/
	public static final String MAINTAIN_TYPE_PUBLIC = "公共区域";
	
	//项目类型
	/**项目类型：有偿*/
	public static final String PROJECT_TYPE_PAID = "有偿";
	/**项目类型：无偿*/
	public static final String PROJECT_TYPE_FREE = "无偿";
	
	//人员派遣状态
	/**已派出*/
	public static final String ISOUT = "已派出";
	/**未派出*/
	public static final String ISNOTOUT = "未派出";
	
	//是否在岗
	/**已上班*/
	public static final String ISINPOST = "1";
	/**已下班*/
	public static final String ISNOTINPOST = "2";
	
	//是否为派工工人
	/**是派工工人*/
	public static final String ISDISPATCH = "1";
	/**不是派工工人*/
	public static final String ISNOTDISPATCH = "0";
	
	//加收方式
	/**加收方式：按比例*/
	public static final String PROPORTIONAL = "按比例";
	/**加收方式：固定值*/
	public static final String FIXED_VALUE = "固定值";
	
	/*客户服务end*/
	
	//费用缴纳情况
	/**已缴*/
	public static final String ISPAID = "已缴";
	/**未缴*/
	public static final String ISNOTPAID = "未缴";
	
	//车辆限行号码

	public static final String L_ONE = "16";
	public static final String L_TWO = "27";
	public static final String L_THREE = "38";
	public static final String L_FOUR = "49";
	public static final String L_FIVE = "50";
	
	//手机前缀
	public static final String[] PHONE_HEAD = {"13", "15", "18"};
	
	//发票内容
	public static final String INVOICE_CONTENT = "invoiceContent";
	
	public static final String PLAN_BLOCK = "planBlock";
	
	public static final String GL_DEPT = "管理部门";
	public static final String GC_DEPT = "工程部门";
	public static final String ZS_DEPT = "招商部门";
	public static final String BY = "本月";
	public static final String YSJE = "预算金额";
	public static final String YS = "预算";
	public static final String SJ = "实际";
}
