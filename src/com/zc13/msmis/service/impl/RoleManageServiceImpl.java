package com.zc13.msmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.bkmis.mapping.AwokeTaskRole;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.IRoleManageDAO;
import com.zc13.msmis.form.RoleForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MRolePerm;
import com.zc13.msmis.mapping.MRolreUserPrem;
import com.zc13.msmis.service.IRoleManageService;
import com.zc13.util.Contants;
import com.zc13.util.DateUtil;
/**
 * 
 * @author 侯哓娟
 * Date：Nov 8, 2010
 * Time：11:02:48 AM
 */
public class RoleManageServiceImpl implements IRoleManageService {
   
	Logger logger = Logger.getLogger(this.getClass());	
	//注入IRoleManageDAO
	private IRoleManageDAO iroleManageDao;
	
	public IRoleManageDAO getIroleManageDao() {
		return iroleManageDao;
	}

	public void setIroleManageDao(IRoleManageDAO iroleManageDao)throws BkmisServiceException {
		this.iroleManageDao = iroleManageDao;
	}

	public void addRole(RoleForm roleForm) throws BkmisServiceException{
		
		MRole mrole = new MRole();
		try {
			BeanUtils.copyProperties(mrole, roleForm);
			/** 得到当前系统时间作为角色更新时间 */
			Date updatetime = DateUtil.getSysDate();
			mrole.setUpdatetime(updatetime);
			iroleManageDao.saveRole(mrole);

			//添加系统日志
			iroleManageDao.logDetail(roleForm.getUserName(),Contants.ADD,"角色管理",Contants.L_SYSTEM,"2",mrole);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e){
			logger.error("添加角色失败!iroleManageDao.saveRole()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("添加角色失败!RoleManageServiceImpl.addRole()。");
		}
	}

	public boolean checkRoleName(String rolename) throws BkmisServiceException{
		return iroleManageDao.checkRoleName(rolename);
	}

	/**
	 * 删除角色
	 */
	public String deleteRole(RoleForm roleForm) throws BkmisServiceException {
		
		Integer roleid = roleForm.getRoleid();
		MRole mrole = (MRole)iroleManageDao.getObject(MRole.class, roleid);
		String roleName = mrole.getRolename();
		try {
			iroleManageDao.logDetail(roleForm.getUserName(), Contants.DELETE, "角色管理",Contants.L_SYSTEM, "3", mrole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iroleManageDao.deleteRole(mrole);
		return roleName;
	
	}

	public List<MRole> getRole(int range,int lpId)throws BkmisServiceException {
		return iroleManageDao.queryRole(range,lpId);
	}

	public MRole setModifyInfo(RoleForm roleForm) throws BkmisServiceException{
		
		Integer roleid = roleForm.getRoleid();
		MRole mrole = (MRole)iroleManageDao.getObject(MRole.class, roleid);
		return mrole;
	}
	
	public void updateRole(RoleForm roleForm)throws BkmisServiceException{
		
		try {
			Integer roleid = roleForm.getRoleid();
			MRole mrole = new MRole();
			BeanUtils.copyProperties(mrole, roleForm);
			/** 得到当前系统时间作为角色更新时间 */
			Date updatetime = DateUtil.getSysDate();
			mrole.setUpdatetime(updatetime);

			//写入系统日志
			try {
				iroleManageDao.logDetail(roleForm.getUserName(), Contants.MODIFY,"角色",Contants.L_SYSTEM, "1", mrole);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			iroleManageDao.updateObject(mrole);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public List<MRolePerm> getRolePerm(Integer roleid) throws BkmisServiceException {
		return iroleManageDao.getRolePerm(roleid);
	}

	public List<MRolreUserPrem> getRole_User_Perm() throws BkmisServiceException {
		List<MRolreUserPrem> list = iroleManageDao.getRole_User_Perm();
		return list;
	}

	public void updateRolePerm(List permList, Integer roleid) {
		iroleManageDao.updateRolePerm(permList, roleid);
	}

	@Override
	public List<AwokeTask> getTips() throws BkmisServiceException {
		
		List<AwokeTask> tipList = null;
		try{
			tipList = iroleManageDao.findObject("from AwokeTask order by type,sequence");
		}catch(DataAccessException e){
			logger.error("获取任务信息失败!iroleManageDao.getTips()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获取任务信息失败!RoleManageServiceImpl.getTips()。");
		}
		return tipList;
	}
	
	@Override
	public List getTipsByRoleId(Integer roleid) throws BkmisServiceException{
		
		List list = null;
		try{
			list = iroleManageDao.getTip(roleid);
		}catch(DataAccessException e){
			logger.error("获取某角色的任务信息失败!iroleManageDao.getTip(roleid)。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获取某角色的任务信息失败!RoleManageServiceImpl.getTipsByRoleId()。");
		}
		return list;
	}

	@Override
	public void addRoleTips(List<String> idList,Integer roleid,List rtipList) throws BkmisServiceException {

		Integer tipId = null;
		Integer roleId = null;
		try{
			Iterator<String> it = idList.iterator();
			iroleManageDao.deleteRoleTip(roleid);
			while(it.hasNext()){
				tipId = Integer.parseInt(it.next());
				AwokeTaskRole roleTip = new AwokeTaskRole();
				roleTip.setTaskId(tipId);
				roleTip.setRoleId(roleid);
				iroleManageDao.saveObject(roleTip);
			}
		}catch(DataAccessException e){
			logger.error("保存角色的任务提示信息失败!iroleManageDao.saveObject()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("保存角色的任务提示信息失败!RoleManageServiceImpl.addRoleTips()。");
		}
	}
}
