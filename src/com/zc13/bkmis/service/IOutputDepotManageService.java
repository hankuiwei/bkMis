package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.OutputDepotManageForm;
import com.zc13.bkmis.form.SetMaterialsForm;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:37:46 PM
 */
public interface IOutputDepotManageService {
	//查询所属部门
	public List selectDepartment();
	//根据id查询选择添加的材料信息
	public List selectMaterialsById(String idArray[]);
	//查询显示出库单信息
	public void showOutputMaterial(SetMaterialsForm smf);
	//查询记录总数
	public int queryCounttotal(SetMaterialsForm smf);
	//生成出库单编号
	public String GenerationNum();
	//执行添加的操作
	public void doAddOutput(OutputDepotManageForm outputForm);
	//按id查询出库单信息
	public List selectOutput(int id);
	//按出库单编号查询详细出库信息
	public List selectinoutput(String inouputCode);
	//删除出库单详细信息
	public void deleteInOutput(String inoutputCode);
	//更新出库单信息
	public void updateOutput(OutputDepotManageForm outputForm);
	//按id删除出库单信息
	public void delOutput(int id);
	//根据id查询出库单的出库编号
	public List selectOutputCode(Integer ids[]);
	//按出库单编号删除出库详细信息
	public void deleteInOutList(List list);
	//结算出库记录
	public void doAccountOutput(int id);
	//查询出库详细记录后执行更新数据库
	public void selectDetailInoutput(List list);
	//查询所有的出库信息
	public List selectAllOutput(SetMaterialsForm smf);
	/**出库明细表**/
	//查询显示出库的详细信息
	public void selectOutputDetail(OutputDepotManageForm odmf);
	//查询出库记录的总条数
	public int queryCountDetail(OutputDepotManageForm odmf);
	//计算出库明细表中的总数量和总金额
	public List summaryDetail(OutputDepotManageForm odmf);
	//查询所有的出库明细信息
	public List selectAllDetail(OutputDepotManageForm odmf);
	
	/**
	 * 删除出库单
	 * @param outputForm
	 * Date:Oct 9, 2011 
	 * Time:5:10:07 PM
	 */
	public boolean deleteOutput(OutputDepotManageForm outputForm);
	
	/**
	 * 汇总出库数量金额
	 * @param smf
	 * @return
	 * Date:Nov 23, 2011 
	 * Time:11:28:21 PM
	 */
	public List summary(SetMaterialsForm smf);
}
