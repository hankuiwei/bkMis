package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.ISetMaterialsManageDAO;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.DepotMaterialCopy;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：2:21:15 PM
 */
public class SetMaterialsManageServiceImpl extends BasicServiceImpl implements ISetMaterialManageService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/** 对ipersonnelDao 注入*/
	private ISetMaterialsManageDAO setmaterialsDao;
	
	public ISetMaterialsManageDAO getSetmaterialsDao() {
		return setmaterialsDao;
	}

	public void setSetmaterialsDao(ISetMaterialsManageDAO setmaterialsDao) {
		this.setmaterialsDao = setmaterialsDao;
	}


	//获取树形图
	public List getMaterialsList() {
		
		return setmaterialsDao.getMaterialsList();
	}

	//查询要显示的信息
	public void selectMaterials(SetMaterialsForm smf,boolean isPage) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		try{
			list = setmaterialsDao.selectMaterials(smf,isPage);
			if(isPage){//如果需要分页，则需要得到记录总数
				int count = 0;
				List tempList = setmaterialsDao.selectMaterials(smf,false);
				if(tempList!=null){
					count = tempList.size();
				}
				smf.setTotalcount(count);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询材料信息加载失败！SetMaterialsManageServiceImpl.selectMaterials()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询材料信息加载失败！SetMaterialsManageServiceImpl.selectMaterials()");
		}
		smf.setMaterialList(list);
	}

	//删除材料信息
	public void delMaterials(int id,String userName) {
		// TODO Auto-generated method stub
		try{
			DepotMaterial dm = (DepotMaterial)setmaterialsDao.getObject(DepotMaterial.class, id);
			//添加系统日志
			setmaterialsDao.logDetail(userName, Contants.DELETE, "材料设置",Contants.L_Depot, "3", dm);
			setmaterialsDao.deleteObject(dm);
		}catch(Exception e){
			logger.error("删除材料信息加载失败！SetMaterialsManageServiceImpl.delMaterials()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除材料信息加载失败！SetMaterialsManageServiceImpl.delMaterials()");
		}
	}

	//查询单位
	public void selectUnit(SetMaterialsForm smf) {
		// TODO Auto-generated method stub
		List list = null;
		try{
			list = setmaterialsDao.selectUnit();
		}catch(Exception e){
			logger.error("查询材料单位信息加载失败！SetMaterialsManageServiceImpl.selectUnit()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询材料单位信息加载失败！SetMaterialsManageServiceImpl.selectUnit()");
		}
		smf.setUnitList(list);
	}

	//添加材料信息
	public void addMaterials(DepotMaterial dm) {
		// TODO Auto-generated method stub
		try{
			setmaterialsDao.addMaterials(dm);
			//获取新插入的材料编号用于插入copy表中
			String code = dm.getCode();
			List copyList = setmaterialsDao.selectMaterialByCode(code);
			//将新插入的数据插入copy表
			if(copyList != null && copyList.size()>0){
				DepotMaterial dmc = (DepotMaterial)copyList.get(0);
				DepotMaterialCopy dmCopy = new DepotMaterialCopy();
				BeanUtils.copyProperties(dmCopy, dmc);
				System.out.println(dmCopy.getId()+"========");
				setmaterialsDao.saveObject(dmCopy);
			}
		}catch(Exception e){
			logger.error("添加材料信息加载失败！SetMaterialsManageServiceImpl.addMaterials()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加材料信息加载失败！SetMaterialsManageServiceImpl.addMaterials()");
		}
	}

	//验证添加的材料编号
	public boolean checkAddCode(String code) {

		DepotMaterial dm = new DepotMaterial();
		try{
			dm = setmaterialsDao.checkAddCode(code);
		}catch(Exception e){
			logger.error("验证材料编号加载失败！SetMaterialsManageServiceImpl.checkAddCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("验证材料编号加载失败！SetMaterialsManageServiceImpl.checkAddCode()");
		}
		boolean flag = true;
		if(dm != null){
			flag = false;
		}
		return flag;
	}

	//验证添加材料名称
	public boolean checkAddName(String name) {

		DepotMaterial dm = new DepotMaterial();
		try{
			dm = setmaterialsDao.checkAddName(name);
		}catch(Exception e){
			logger.error("验证材料名称加载失败！SetMaterialsManageServiceImpl.checkAddCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("验证材料名称加载失败！SetMaterialsManageServiceImpl.checkAddCode()");
		}
		boolean flag = true;
		if(dm != null){
			flag = false;
		}
		return flag;
	}

	//按id查询要修改的信息
	public void selectMaterialById(SetMaterialsForm smf) {
		// TODO Auto-generated method stub
		int id = smf.getId();
		List list = null;
		try{
			list = setmaterialsDao.selectMaterialById(id);
		}catch(Exception e){
			logger.error("查询要修改的材料信息加载失败！SetMaterialsManageServiceImpl.selectMaterialById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要修改的材料信息加载失败！SetMaterialsManageServiceImpl.selectMaterialById()");
		}
		smf.setMaterialList(list);
	}

	//执行修改
	public void editMaterials(SetMaterialsForm form) {
		// TODO Auto-generated method stub
		try{
			DepotMaterial dm2 = new DepotMaterial();//这个bean是提供写日志用的，除此之外没有其他作用
			DepotMaterial dm = (DepotMaterial)setmaterialsDao.getObject(DepotMaterial.class, form.getId());
			//下面往form中set数据的目的是这样的：页面上显示的数据并不全，导致页面填充form的时候并不是所有的字段全部都填充了，比如money这个字段在数据库中是有值的，
			//但是因为页面用不到它，也就没有弄一个name为money的东西。如果这样直接就使用copyProperties方法，那么修改后的bean对象的money字段就被清空了，这肯定是不允许的
			form.setType(dm.getType());
			form.setMoney(dm.getMoney());
			form.setNowStock(dm.getNowStock());
			form.setDoStock(dm.getDoStock());
			BeanUtils.copyProperties(dm2, form);
			//写入系统日志
			setmaterialsDao.logDetail(form.getUserName(), Contants.MODIFY,"材料设置",Contants.L_Depot, "1", dm2);
			
			BeanUtils.copyProperties(dm, form);
			setmaterialsDao.updateObject(dm);
			DepotMaterialCopy dmCopy = new DepotMaterialCopy();
			//更新备份表
			BeanUtils.copyProperties(dmCopy, dm);
			setmaterialsDao.updateObject(dmCopy);
		}catch(Exception e){
			logger.error("修改的材料信息失败！SetMaterialsManageServiceImpl.editMaterials()。详细信息："+e.getMessage());
			throw new BkmisServiceException("修改的材料信息失败！SetMaterialsManageServiceImpl.editMaterials()");
		}
	}

	//现有库存中金额的计算
	public double sumMoney(SetMaterialsForm smf) {
		double sum = 0;
		try{
			sum = setmaterialsDao.sumMoney(smf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("计算库存金额失败！SetMaterialsManageServiceImpl.sumMoney()。详细信息："+e.getMessage());
			throw new BkmisServiceException("计算库存金额失败！SetMaterialsManageServiceImpl.sumMoney()");
		}
		return sum;
	}

	//根据id判断节点
	public List judgeNode(SetMaterialsForm smf) {
		
		int id = smf.getDmtId();
		List list = new ArrayList();
		list = setmaterialsDao.judgeNode(id);
		return list;
	}

	//根据单击时id查找名
	public DepotMaterialType selectName(int id) {
		
	    //List list = new ArrayList();
	    //list = setmaterialsDao.selectName(id);
		//return list;
		DepotMaterialType dmType = (DepotMaterialType)setmaterialsDao.getObject(DepotMaterialType.class, id);
		return dmType;
	}

	//按id查找要删除的材料名称
	public List<DepotMaterial> selectMaterName(Integer[] idArray) {
		
		List list = new ArrayList();
		try{
			list = setmaterialsDao.selectMaterName(idArray);
		}catch (Exception e) {
			logger.error("查询材料名称信息加载失败！SetMaterialsManageServiceImpl.selectMaterName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询材料名称信息加载失败！SetMaterialsManageServiceImpl.selectMaterName()");
		}
		return list;
	}

	//按id查询材料的详细信息
	public List materialDetail(int id) {
		
		List list = new ArrayList();
		try{
			list = setmaterialsDao.materialDetail(id);
		}catch(Exception e){
			logger.error("查询材料详细信息加载失败！SetMaterialsManageServiceImpl.materialDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询材料详细信息加载失败！SetMaterialsManageServiceImpl.materialDetail()");
		}
		return list;
	}

	//查询所有的材料信息
	public List selectAllMaterial(SetMaterialsForm smf) {
		
		List list = new ArrayList();
		try{
			list = setmaterialsDao.selectAllMaterial(smf);
		}catch (Exception e) {
			logger.error("查询所有材料详细信息加载失败！SetMaterialsManageServiceImpl.selectAllMaterial()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有材料详细信息加载失败！SetMaterialsManageServiceImpl.selectAllMaterial()");
		}
		return list;
	}

}
