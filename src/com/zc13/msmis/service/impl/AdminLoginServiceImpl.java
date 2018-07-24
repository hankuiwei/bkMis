package com.zc13.msmis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IRoomManageDAO;
import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.IAdminLoginDAO;
import com.zc13.msmis.form.AdminLoginForm;
import com.zc13.msmis.mapping.LimitCar;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;
import com.zc13.msmis.service.IAdminLoginService;
import com.zc13.util.GlobalMethod;
public class AdminLoginServiceImpl implements IAdminLoginService {

	Logger logger = Logger.getLogger(this.getClass());	
	
	IAdminLoginDAO adminLoginDAOImpl;
	/* houxj
	 * 注入iroomManageDao
	 * */
	private IRoomManageDAO iroomManageDao;
	public IRoomManageDAO getIroomManageDao() {
		return iroomManageDao;
	}
	public void setIroomManageDao(IRoomManageDAO iroomManageDao) {
		this.iroomManageDao = iroomManageDao;
	}
	public IAdminLoginDAO getAdminLoginDAOImpl() {
		return adminLoginDAOImpl;
	}
	public void setAdminLoginDAOImpl(IAdminLoginDAO adminLoginDAOImpl) {
		this.adminLoginDAOImpl = adminLoginDAOImpl;
	}
	public void login(AdminLoginForm adminLoginForm) throws Exception {
		// TODO Auto-generated method stub
		
		String username = adminLoginForm.getUsername();
		String password = adminLoginForm.getPassword();
		//对密码进行加密处理
		String userpass = GlobalMethod.encryptPassword(username, password);
		
		try{
			//根据用户名和密码得到当前用户的相关信息
			MUser user = adminLoginDAOImpl.getUserInfo(username,userpass);
			
			if(user != null){//用户存在，接下来取出他的所属角色
				//userinfo这个Map负责存放用户的基本信息，包括名字、id、角色等
				Map userinfo = new HashMap();
				MUserRole userRole = adminLoginDAOImpl.getRoleByUserID(user.getUserid());
				if(userRole != null){//成功得到角色
					//获取角色下所有的功能菜单列表
					List functionList = adminLoginDAOImpl.getFundesc(userRole.getMRole().getRoleid());
					adminLoginForm.setFunctionList(functionList);
					/* houxj S
					 * 获取代办任务信息 */
					List<AwokeTask> tipsList = adminLoginDAOImpl.getTask(userRole.getMRole().getRoleid());
					adminLoginForm.setTipsList(tipsList);
					/*houxj
					 * 获取房间信息 */
					int roomNum1 =  iroomManageDao.getObjects(ERooms.class).size();//获取总房间数
					String status0 = "已预租";
					String status1 = "已出租";
					String status2 = "未出租";
					String status3 = "装修中";
					int bookNum = iroomManageDao.getRoomByStatus(status0).size();
					int rentalNum = iroomManageDao.getRoomByStatus(status1).size();
					int unrentalNum = iroomManageDao.getRoomByStatus(status2).size();
					int decorationNum= iroomManageDao.getRoomByStatus(status3).size();
					Map roomInfoMap = new HashMap<String, Integer>();
					roomInfoMap.put("totalNum", roomNum1);
					roomInfoMap.put("rentalNum", rentalNum);
					roomInfoMap.put("unrentalNum", unrentalNum);
					roomInfoMap.put("decorationNum", decorationNum);
					roomInfoMap.put("bookNum", bookNum);
					adminLoginForm.setRoomInfoMap(roomInfoMap);
					
					userinfo.put("userrole",userRole.getMRole().getRoleid());//用户角色
					userinfo.put("userRoleRange",userRole.getMRole().getRange()); //用户所属角色等级
				}
				userinfo.put("userroot", user.getRootUser());//根用户
				userinfo.put("userlp", user.getLpId());//用户所属楼盘id
				userinfo.put("username",username);			//用户名
				userinfo.put("password", password);
				userinfo.put("name",user.getRealName());	//真实姓名
				userinfo.put("userid", user.getUserid());	//用户id
				adminLoginForm.setUsername(username);
				adminLoginForm.setUserinfoMap(userinfo);
				
				//下面是机动车辆相关的代码
				List<LimitCar> carList = adminLoginDAOImpl.getCarList();
				String week = "67";
				String week1 = "67";
				int tomorrow = GlobalMethod.getDay(new Date());
				int today = tomorrow-1;
				if(today==0){
					today = 7;
				}
				if(carList.size()>0){
					LimitCar car = (LimitCar)carList.get(0);
					switch (today) {
					case 1: week = car.getMon();
							week1 = car.getTues();
						break;
					case 2: week = car.getTues();
							week1 = car.getWed();
						break;
					case 3: week = car.getWed();
							week1 = car.getThrus();
						break;
					case 4: week = car.getThrus();
							week1 = car.getFriday();
						break;
					case 5: week = car.getFriday();
						break;
					case 6: 
						break;
					case 7: week1 = car.getMon();
							
						break;
					default:
						break;
					}
				}
				adminLoginForm.setWeek(week);
				adminLoginForm.setWeek1(week1);
				adminLoginForm.setToday(today);
				adminLoginForm.setTomorrow(tomorrow);
				
				adminLoginForm.setForward("success");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("用户登录信息加载失败！AdminLoginServiceImpl.login()。详细信息："+e.getMessage());
			throw new BkmisServiceException("用户登录信息加载失败！AdminLoginServiceImpl.login()");
		}
	}
	
	
}
	

