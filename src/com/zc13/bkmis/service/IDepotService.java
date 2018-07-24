package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.TypeForm;
import com.zc13.bkmis.mapping.DepotMaterialType;

/**
 * 
 * @author Administrator
 * Date：Dec 3, 2010
 * Time：9:05:51 AM
 */
public interface IDepotService {
	
	//获取树形图
	public List getMaterialsList();
	//通过id获取子节点内容
	public List getChildList(int id);
	//修改材料类别
	public void editType(DepotMaterialType dsf);
	//删除材料类别
	public void delType(TypeForm typeForm);
	//添加材料类别
	public void addType(DepotMaterialType dsf,String userName);
	//检验要添加的类别名称
	public boolean checkAddName(String name);
	//检验要添加的类别代码
	public boolean checkAddCode(String code);
	//更新材料信息中的类别代码
	public void updateMaterialCode(String oldCode,DepotMaterialType dsf);
	//查询该类别是不是有子类
	public List selectTypeCode(TypeForm typeForm);
	//删除相关的材料类别信息
	public void deleteMaterial(List list);
	//删除子类
	public void deleteChildTpye(List list);
	//判断是否可以添加子节点查询添加节点下是否有数据
	public List selectByCode(String code);
	//查询要删除类别的code以便删除材料
	public List selectCode(TypeForm typeForm);
}
