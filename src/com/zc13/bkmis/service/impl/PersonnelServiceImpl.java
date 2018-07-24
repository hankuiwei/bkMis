package com.zc13.bkmis.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IPersonnelDAO;
import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.mapping.HrPact;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.bkmis.service.IPersonnelService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:36:14 AM
 */
public class PersonnelServiceImpl extends BasicServiceImpl implements IPersonnelService {	
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/** 对ipersonnelDao 注入*/
	private IPersonnelDAO ipersonnelDao;

	public IPersonnelDAO getIpersonnelDao() {
		
		return ipersonnelDao;
	}

	public void setIpersonnelDao(IPersonnelDAO ipersonnelDao) {
		
		this.ipersonnelDao = ipersonnelDao;
	}

	/**查找部门下拉列表的内容*/
	public void selectDepartment(PersonnelForm personnelForm) {
		
		List derList = null;
		try{
			derList = ipersonnelDao.selectDepartment(personnelForm);
		}catch(Exception e){
			logger.error("查询部门信息加载失败！PersonnelServiceImpl.selectDepartment()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询部门信息加载失败！PersonnelServiceImpl.selectDepartment()");
		}
		personnelForm.setDepartment(derList);
		
	}

	/**查询员工信息得出记录数*/
	public int queryCounttotal(PersonnelForm personnelForm) {
		int totalcount = 0;
		try{
			List list = ipersonnelDao.showPersonnel(personnelForm, false);
			if(list!=null){
				totalcount = list.size();
			}
		}catch(Exception e){
			logger.error("多条件查询员工信息的记录总数加载失败！PersonnelServiceImpl.queryCounttotal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("多条件查询员工信息的记录总数加载失败！PersonnelServiceImpl.queryCounttotal()");
		}
		return totalcount;
	}

	//显示员工信息
	public List showPersonnel(PersonnelForm personnelForm,boolean isPage) {
		
		List list = null;
		try{
			list=ipersonnelDao.showPersonnel(personnelForm,isPage);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("显示员工信息加载失败！PersonnelServiceImpl.showPersonnel()。详细信息："+e.getMessage());
			throw new BkmisServiceException("显示员工信息加载失败！PersonnelServiceImpl.showPersonnel()");
		}
		personnelForm.setPersonnelList(list);
		return list;
	}

	//删除员工信息
	public void delPersonnel(PersonnelForm personnelForm) throws BkmisServiceException {
		try{
			if(personnelForm!=null&&!GlobalMethod.NullToSpace(personnelForm.getIds()).equals("")){
				//记录日志
				String[] idStr = personnelForm.getIds().split(",");
				for(int i = 0;i<idStr.length;i++){
					ipersonnelDao.logDetail(personnelForm.getUserName(), Contants.DELETE, "人员信息",Contants.L_HR, "3", new HrPersonnel(Integer.parseInt(idStr[i])));
				}
				ipersonnelDao.delPersonnel(personnelForm.getIds());
			}
		}catch(Exception e){
			logger.error("删除员工信息加载失败！PersonnelServiceImpl.delPersonnel()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除员工信息加载失败！PersonnelServiceImpl.delPersonnel()");
		}
	}

	//实现员工信息的添加及合同信息
	public void addPersonnel(PersonnelForm personnelForm) {
		try{
			HrPersonnel personnel = personnelForm.getPersonnel();
			personnel.setLpId(personnelForm.getLpId());
			HrPact pact = personnelForm.getPact();
			pact.setLpId(personnelForm.getLpId());
			personnel.setDepartmentCode(pact.getDepartmentCode());
			ipersonnelDao.saveObject(personnel);
			pact.setPersonnelId(personnel.getPersonnelId());
			ipersonnelDao.saveObject(pact);
			ipersonnelDao.logDetail(personnelForm.getUserName(),Contants.ADD,"员工信息",Contants.L_HR,"2",personnel);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("添加员工信息加载失败！PersonnelServiceImpl.addPersonnel()。详细信息："+e.getMessage());
			throw new BkmisServiceException("员工更新信息加载失败！PersonnelServiceImpl.addPersonnel()");
		}
	}

	//按员工的id查询员工信息进行显示修改
	public void selectPersonnelById(PersonnelForm personnelForm) {
		try{
			int id = personnelForm.getPersonnelId();
			HrPersonnel personnel = (HrPersonnel)ipersonnelDao.getObject(HrPersonnel.class, id);
			HrPact pact = (HrPact)ipersonnelDao.getUniqueObject("from HrPact where personnelId="+id);
			personnelForm.setPersonnel(personnel);
			personnelForm.setPact(pact);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("员工查询信息加载失败！PersonnelServiceImpl.selectPersonnelById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("员工查询信息加载失败！PersonnelServiceImpl.selectPersonnelById()");
		}
	}

	//员工信息的修改
	public void updatePersonnel(PersonnelForm form) {
		try{
			HrPersonnel personnel = form.getPersonnel();
			HrPact pact = form.getPact();
			pact.setPersonnelId(personnel.getPersonnelId());
			personnel.setDepartmentCode(pact.getDepartmentCode());
			ipersonnelDao.logDetail(form.getUserName(),Contants.MODIFY,"员工信息",Contants.L_HR,"1",personnel);
			ipersonnelDao.updateObject(personnel);
			ipersonnelDao.updateObject(pact);
		}catch(Exception e){
			logger.error("员工更新信息加载失败！PersonnelServiceImpl.updatePersonnel()。详细信息："+e.getMessage());
			throw new BkmisServiceException("员工更新信息加载失败！PersonnelServiceImpl.updatePersonnel()");
		}
	}

	//按图片名查询是否已经存在该图片
	public List selectPicName(String picName,String path) {
		
		List list = new ArrayList();
		File uploadPath = new File(path+"\\uploadPerFile");//上传文件目录
	    if (!uploadPath.exists()) {
	       uploadPath.mkdirs();
	    }
	    StringBuffer realPath = new StringBuffer(path);
	    realPath.append("\\");
	    realPath.append("uploadPerFile");
	    realPath.append("\\");
	    realPath.append(picName);
	    String uriPath = realPath.toString();
	    
		try{
			list = ipersonnelDao.selectPicName(uriPath);
		}catch(Exception e){
			logger.error("查询图片名信息加载失败！PersonnelServiceImpl.selectPerNameById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询图片名信息加载失败！PersonnelServiceImpl.selectPerNameById()");
		}
		return list;
	}
	

	//查询上传的文件的名字是否存在
	public List selectFileName(String name,String path) {
		
		List list = new ArrayList();
		
		File uploadPath = new File(path+"\\uploadFile");//上传文件目录
	    if (!uploadPath.exists()) {
	       uploadPath.mkdirs();
	    }
	    
		try{
			list = ipersonnelDao.selectFileName(name);
		}catch(Exception e){
			logger.error("检查附件名信息加载失败！SetMaterialsManageServiceImpl.selectFileName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("检查附件名信息加载失败！SetMaterialsManageServiceImpl.selectFileName()");
		}
		return list;
	}

	//根据人员id字符串查询人员信息
	@Override
	public List<HrPersonnel> selectPersonalByIds(String ids) throws BkmisServiceException {
		List<HrPersonnel> list = null;
		try {
			if(!GlobalMethod.NullToSpace(ids).equals("")){
				String[] idsStr= ids.split(",");
				Integer[] idArray = new Integer[idsStr.length];
				for(int i = 0;i<idsStr.length;i++){
					idArray[i] = Integer.parseInt(idsStr[i]);
				}
				list = ipersonnelDao.selectPerNameById(idArray);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException("查询人员信息失败！");
		}
		return list;
	}

	@Override
	public List<HrPersonnel> getDispatchPersonnel(PersonnelForm personnelForm,boolean isPage) throws BkmisServiceException {
		List<HrPersonnel> list = null;
		try {
			list = ipersonnelDao.getPersonnelList(personnelForm,isPage);
			personnelForm.setPersonnelList(list);
			int total = 0;
			List totalList = ipersonnelDao.getPersonnelList(personnelForm, false);
			if(totalList!=null){
				total  =totalList.size();
			}
			personnelForm.setTotalcount(total);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException("查询可以派遣的员工失败！");
		}
		return list;
	}

	@Override
	public List getWorkingConditions4Personnel(PersonnelForm personnelForm, boolean isPage) throws BkmisServiceException {
		List list = null;
		try {
			list = ipersonnelDao.getWorkingConditions4Personnel(personnelForm,isPage);
			personnelForm.setPersonnelList(list);
			int total = 0;
			List totalList = ipersonnelDao.getWorkingConditions4Personnel(personnelForm, false);
			if(totalList!=null){
				total  =totalList.size();
			}
			personnelForm.setTotalcount(total);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException("查询可以派遣的员工失败！");
		}
		return list;
	}

	@Override
	public String getNamesByPersonnelIds(String ids) throws BkmisServiceException {
		String sendedManName = "";
		if(!GlobalMethod.NullToSpace(ids).equals("")){
			List tempList = ipersonnelDao.findObject("from HrPersonnel where personnelId in("+ids+")");
			if(tempList!=null&&tempList.size()>0){
				for(int j = 0;j<tempList.size();j++){
					sendedManName += ((HrPersonnel)tempList.get(j)).getPersonnelName()+"，";
				}
				sendedManName = sendedManName.substring(0, sendedManName.length()-1);
			}
		}
		return sendedManName;
	}

	/**
	 * 查询维修结果
	 */
	@Override
	public List<RepairResult> queryRepairResult() {
		
		return ipersonnelDao.getObjects(RepairResult.class);
	}
}
