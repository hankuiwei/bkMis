package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterial;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：2:21:50 PM
 */
public interface ISetMaterialsManageDAO extends BasicDAO{
	//获取树形图
	public List getMaterialsList();
	/**
	 * 查询材料信息
	 * @param smf
	 * @param isPage 是否分页
	 * @return
	 */
	public List selectMaterials(SetMaterialsForm smf,boolean isPage);
	//查询单位
	public List selectUnit();
	//执行材料的添加
	public void addMaterials(DepotMaterial dm);
	//验证添加编号
	public DepotMaterial checkAddCode(String code);
	//检验添加材料的名称
	public DepotMaterial checkAddName(String name);
	//按id查询要修改的信息
	public List selectMaterialById(int id);
	//现有库存中库存金额的计算
	public double sumMoney(SetMaterialsForm smf);
	//按id判断该节点是不是根节点
	public List judgeNode(int id);
	//根据材料编号查询新插入的材料信息
	public List selectMaterialByCode(String code);
	//按id查查删除材料的名称
	public List<DepotMaterial> selectMaterName(Integer[] idArray);
	//按id查询材料的详细信息
	public List materialDetail(int id);
	//查询所有的材料信息
	public List selectAllMaterial(SetMaterialsForm smf);
}
