package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.DepotMaterialType;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：2:34:34 PM
 */
public interface ISetMaterialManageService extends IBasicService{
	//获取树形图
	public List getMaterialsList();
	/**
	 * 查询材料信息
	 * @param smf
	 * @param isPage 是否分页
	 */
	public void selectMaterials(SetMaterialsForm smf,boolean isPage);
	//删除材料信息
	public void delMaterials(int id,String userName);
	//查询单位
	public void selectUnit(SetMaterialsForm smf);
	//执行材料的添加
	public void addMaterials(DepotMaterial dm);
	//检验添加材料编号的验证
	public boolean checkAddCode(String code);
	//检验添加材料名称的验证
	public boolean checkAddName(String name);
	//按id查询出要修改的信息
	public void selectMaterialById(SetMaterialsForm smf);
	//执行修改
	public void editMaterials(SetMaterialsForm smf);
	//库存中的计算
	public double sumMoney(SetMaterialsForm smf);
	//按id判断该节点是不是根节点
	public List judgeNode(SetMaterialsForm smf);
	//根据单击时id查找姓名
	public DepotMaterialType selectName(int id);
	//按id查查删除材料的名称
	public List selectMaterName(Integer[] idArray);
	//按id查询材料的详细信息
	public List materialDetail(int id);
	//查询所有的材料信息
	public List selectAllMaterial(SetMaterialsForm smf);
}
