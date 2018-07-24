package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.TypeForm;
import com.zc13.bkmis.mapping.DepotMaterialType;

/**
 * 
 * @author Administrator
 * Date：Dec 3, 2010
 * Time：9:05:38 AM
 */
public interface IDepotDAO extends BasicDAO{
	//获取树形参数
	public List getMaterialsList();
	//通过id获取子节点内容
	public List getChildList(int id);
	//修改类别信息
	public void editType(DepotMaterialType dsf);
	//删除材料类别
	public void delType(DepotMaterialType dsf);
	//添加材料类别
	public void addType(DepotMaterialType dsf);
	//检查要添加的类别名称
	public DepotMaterialType checkAddName(String name);
	//检查要添加的类别代码
	public DepotMaterialType checkAddCode(String code);
	//更新材料信息中材料类别
	public void updateMaterialCode(String oldCode,DepotMaterialType dsf);
	//更具id查询要删除的类别子类的类别代码
	public List selectTypeCode(int id);
	//删除相关的材料类别信息
	public void deleteMaterial(List list);
	//删除子类
	public void deleteChildTpye(List list);
	//判断是否可以添加子节点查询添加节点下是否有数据
	public List selectByCode(String code);
	//查询要删除类别的code以便删除材料
	public List selectCode(TypeForm typeForm);
}
