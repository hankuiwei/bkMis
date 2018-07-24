package com.zc13.bkmis.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IExteriorPersonnelDAO;
import com.zc13.bkmis.form.ExteriorPerForm;
import com.zc13.bkmis.mapping.HrExteriorPersonnel;
import com.zc13.bkmis.service.IExteriorPersonnelService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 1, 2010
 * Time：3:19:12 PM
 */
public class ExteriorPersonnelServiceImpl implements IExteriorPersonnelService {
	Logger logger = Logger.getLogger(this.getClass());
	/** 对ipersonnelDao 注入*/
	private IExteriorPersonnelDAO iexteriorpersonnelDao;
	
	public IExteriorPersonnelDAO getIexteriorpersonnelDao() {
		return iexteriorpersonnelDao;
	}
	public void setIexteriorpersonnelDao(IExteriorPersonnelDAO iexteriorpersonnelDao) {
		this.iexteriorpersonnelDao = iexteriorpersonnelDao;
	}
	//对工作单位下拉列表内容的获取
	@SuppressWarnings("unchecked")
	public void selectWorkPlace(ExteriorPerForm exteriorperForm) {
		
		List list = iexteriorpersonnelDao.selectWorkPlace();
		if(null != list && list.size()>0){
			exteriorperForm.setWorkList(list);
		}
		
	}
	//显示留学人员信息
	public void selectExteriorPer(ExteriorPerForm exteriorperForm) {
		
		List list = iexteriorpersonnelDao.selectExteriorPer(exteriorperForm);
		exteriorperForm.setExteriorperList(list);
	}
	//添加留学人员信息
	public void addExteriorPer(ExteriorPerForm exteriorperForm) {
		//实现员工信息的添加
		try{
			HrExteriorPersonnel ep = new HrExteriorPersonnel();
			try{
				BeanUtils.copyProperties(ep,exteriorperForm);
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			iexteriorpersonnelDao.addExteriorPer(ep);
			//添加系统日志
			iexteriorpersonnelDao.logDetail(exteriorperForm.getUserName(),Contants.ADD,"留学人员",Contants.L_HR,"2",ep);
		}catch(Exception e){
			logger.error("添加留学人员信息加载失败！ExteriorPersonnelServiceImpl.addExteriorPer()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加留学人员信息加载失败！ExteriorPersonnelServiceImpl.addExteriorPer()");
		}
	}
	//按id去查询员工信息
	public void selectExteriorById(ExteriorPerForm exteriorperForm) {
		int id = exteriorperForm.getId();
		List list = null;
		try{
			list = iexteriorpersonnelDao.selectExteriorById(id);
		}catch(Exception e){
			logger.error("查看留学人员信息加载失败！ExteriorPersonnelServiceImpl.selectExteriorById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查看留学人员信息加载失败！ExteriorPersonnelServiceImpl.selectExteriorById()");
		}
		exteriorperForm.setExteriorperList(list);
	}
	//修改留学人员信息
	public void updateExterior(ExteriorPerForm exteriorperForm) {
		try{
			
			HrExteriorPersonnel ep = new HrExteriorPersonnel();
			try{
				BeanUtils.copyProperties(ep,exteriorperForm);
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			//写入系统日志
			iexteriorpersonnelDao.logDetail(exteriorperForm.getUserName(), Contants.MODIFY,"留学人员",Contants.L_HR, "1", ep);
			//更新
			iexteriorpersonnelDao.updateObject(ep);
			
		}catch(Exception e){
			logger.error("修改留学人员信息加载失败！ExteriorPersonnelServiceImpl.updateExterior()。详细信息："+e.getMessage());
			throw new BkmisServiceException("修改留学人员信息加载失败！ExteriorPersonnelServiceImpl.updateExterior()");
		}
	}
	//按id删除留学信息
	public void delExterior(int id) {
		// TODO Auto-generated method stub
		try{
			iexteriorpersonnelDao.delExterior(id);
		}catch(Exception e){
			logger.error("删除留学人员信息加载失败！ExteriorPersonnelServiceImpl.delExterior()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除留学人员信息加载失败！ExteriorPersonnelServiceImpl.delExterior()");
		}
	}
	//查询留学人员的记录总数用于分页
	public int queryCounttotal(ExteriorPerForm exteriorperForm) {
		int totalcount = 0;
		try{
			totalcount = iexteriorpersonnelDao.queryCounttotal(exteriorperForm);
		}catch(Exception e){
			logger.error("多条件查询留学人员信息的记录总数加载失败！PersonnelServiceImpl.queryCounttotal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("多条件查询留学人员信息的记录总数加载失败！PersonnelServiceImpl.queryCounttotal()");
		}
		return totalcount;
	}
	//按id查询出留学人员姓名
	public List<HrExteriorPersonnel> selectNameById(Integer[] ids) {
		
		List list = new ArrayList();
		try{
			list = iexteriorpersonnelDao.selectNameById(ids);
		}catch (Exception e) {
			logger.error("查询留学人员姓名信息加载失败！PersonnelServiceImpl.selectNameById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询留学人员姓名信息加载失败！PersonnelServiceImpl.selectNameById()");
		}
		return list;
	}
	//查询所有留学人员信息
	public List selectAllPersonal(ExteriorPerForm exteriorperForm) {
		
		List list = new ArrayList();
		try{
			list = iexteriorpersonnelDao.selectAllPersonal(exteriorperForm);
		}catch (Exception e) {
			logger.error("查询所有留学人员信息加载失败！PersonnelServiceImpl.selectAllPersonal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有留学人员信息加载失败！PersonnelServiceImpl.selectAllPersonal()");
		}
		return list;
	}
	//检查要上传图片的名字
	public List checkPicName(String picName,String path) {
		
		List list = new ArrayList();
		File uploadPath = new File(path+"\\upExteriorPer");//上传文件目录
	    if (!uploadPath.exists()) {
	       uploadPath.mkdirs();
	    }
	    StringBuffer realPath = new StringBuffer(path);
	    realPath.append("\\");
	    realPath.append("upExteriorPer");
	    realPath.append("\\");
	    realPath.append(picName);
	    String uriPath = realPath.toString();
	    
		try{
			list = iexteriorpersonnelDao.checkPicName(uriPath);
		}catch (Exception e) {
			logger.error("查询图片名信息加载失败！PersonnelServiceImpl.checkPicName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询图片名信息加载失败！PersonnelServiceImpl.checkPicName()");
		}
		return list;
	}
}
