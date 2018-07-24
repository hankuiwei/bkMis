package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IDepotDAO;
import com.zc13.bkmis.form.TypeForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.service.IDepotService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
/**
 * 
 * @author Administrator
 * Date：Dec 3, 2010
 * Time：9:06:04 AM
 */
public class DepotServiceImpl extends BasicServiceImpl implements IDepotService {

	Logger logger = Logger.getLogger(this.getClass());
	
	/** 对ipersonnelDao 注入*/
	private IDepotDAO idepotdao;

	public IDepotDAO getIdepotdao() {
		return idepotdao;
	}

	public void setIdepotdao(IDepotDAO idepotdao) {
		this.idepotdao = idepotdao;
	}

	//获取材料树形参数
	public List getMaterialsList() {
		
		return idepotdao.getMaterialsList();
	}

	//通过id获取子节点内容
	public List getChildList(int id) {
		
		return idepotdao.getChildList(id);
	}

	//修改材料类别
	public void editType(DepotMaterialType dsf) {
		// TODO Auto-generated method stub
		try{
			idepotdao.editType(dsf);
		}catch(Exception e){
			logger.error("编辑类型失败！DepotServiceImpl.editType()。详细信息："+e.getMessage());
			throw new BkmisServiceException("编辑类型失败！DepotServiceImpl.editType()");
		}
	}

	//删除材料类别
	public void delType(TypeForm typeForm) {
		// TODO Auto-generated method stub
		int id = typeForm.getId();
		DepotMaterialType dsf = new DepotMaterialType();
		dsf.setId(id);
		try{
			idepotdao.delType(dsf);
		}catch(Exception e){
			logger.error("删除类型失败！DepotServiceImpl.delType()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除类型失败！DepotServiceImpl.delType()");
		}
	}

	//添加材料类别
	public void addType(DepotMaterialType dsf,String userName) {
		// TODO Auto-generated method stub
		
		try{
			idepotdao.addType(dsf);
			//添加系统日志
			idepotdao.logDetail(userName,Contants.ADD,"材料类别",Contants.L_Depot,"2",dsf);
		}catch(Exception e){
			logger.error("添加类型失败！DepotServiceImpl.addType()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加类型失败！DepotServiceImpl.addType()");
		}
	}

	//检验要添加的类别名称
	public boolean checkAddName(String name) {
		
		DepotMaterialType dsf = new DepotMaterialType();
		try{
			dsf = idepotdao.checkAddName(name);
		}catch(Exception e){
			logger.error("类型名称验证失败！DepotServiceImpl.checkAddName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("类型名称验证失败！DepotServiceImpl.checkAddName()");
		}
		boolean flag =true;
		if(dsf != null){
			flag = false;
		}
		return flag;
	}

	//检验要添加的类别代码
	public boolean checkAddCode(String code) {
		
		DepotMaterialType dsf = new DepotMaterialType();
		try{
			dsf = idepotdao.checkAddCode(code);
		}catch(Exception e){
			logger.error("类型代码验证失败！DepotServiceImpl.checkAddCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("类型代码验证失败！DepotServiceImpl.checkAddCode()");
		}
		boolean flag = true;
		if(dsf != null){
			flag = false;
		}
		return flag;
	}

	//更新材料信息中的材料类别
	public void updateMaterialCode(String oldCode,DepotMaterialType dsf) {
		
		try{
			idepotdao.updateMaterialCode(oldCode, dsf);
		}catch(Exception e){
			logger.error("更新材料信息中材料类别失败！DepotServiceImpl.updateMaterialCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("更新材料信息中材料类别失败！DepotServiceImpl.updateMaterialCode()");
		}
	}

	//查询该类别是不是有子类
	public List selectTypeCode(TypeForm typeForm) {
		
		int id  = typeForm.getId();
		List list = new ArrayList();
		try{
			list = idepotdao.selectTypeCode(id);
		}catch(Exception e){
			logger.error("查询要删除的子类材料类别类别代码失败！DepotServiceImpl.selectTypeCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除的子类材料类别类别代码失败！DepotServiceImpl.selectTypeCode()");
		}
		return list;
	}

	//删除相关联的材料类别信息
	public void deleteMaterial(List list) {
		
		try{
			idepotdao.deleteMaterial(list);
		}catch(Exception e){
			logger.error("删除相关的材料类别信息失败！DepotServiceImpl.deleteMaterial()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除相关的材料类别信息失败！DepotServiceImpl.deleteMaterial()");
		}
	}

	//删除子类
	public void deleteChildTpye(List list) {
		
		try{
			idepotdao.deleteChildTpye(list);
		}catch(Exception e){
			logger.error("删除子类材料类别信息失败！DepotServiceImpl.deleteChildTpye()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除子类材料类别信息失败！DepotServiceImpl.deleteChildTpye()");
		}
	}

	//判断是否可以添加子节点查询添加节点下是否有数据
	public List selectByCode(String code) {
		
		List list = new ArrayList();
		try{
			list = idepotdao.selectByCode(code);
		}catch(Exception e){
			logger.error("查询子类材料类别信息失败！DepotServiceImpl.selectByCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询子类材料类别信息失败！DepotServiceImpl.selectByCode()");
		}
		return list;
	}

	//查询要删除类别的code以便删除相关的材料
	public List selectCode(TypeForm typeForm) {
		
		List list = new ArrayList();
		try{
			list = idepotdao.selectCode(typeForm);
		}catch (Exception e) {
			logger.error("查询要删除类别代码信息失败！DepotServiceImpl.selectCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除类别代码信息失败！DepotServiceImpl.selectCode()");
		}
		return list;
	}

}
