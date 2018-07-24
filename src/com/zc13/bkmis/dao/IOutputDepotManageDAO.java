package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.OutputDepotManageForm;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotInOutputList;
import com.zc13.bkmis.mapping.DepotOutputManager;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:38:11 PM
 */
public interface IOutputDepotManageDAO extends BasicDAO{
	
	//查询部门
	public List selectDepartment();
	//按id查询选择要添加的材料信息
	public List selectMaterialsById(String idArray[]);
	
	/**
	 * //查询显示出库单信息
	 * @param smf SetMaterialsForm类型
	 */
	public List showOutputMaterial(SetMaterialsForm smf);
	//查询记录总数
	public int queryCounttotal(SetMaterialsForm smf);
	//生成编号
	public String GenerationNum();
	//添加出库单信息
	public void doAddOutput(DepotOutputManager depotOutput);
	//添加出库入库的详细信息
	public void doAddInOutput(DepotInOutputList depotInOutput);
	//按出库单id查询出库单信息
	public List selectOutput(int id);
	//按出库单编号查询详细出库信息
	public List selectinoutput(String inouputCode);
	//按出库单编号删除出库的详细信息
	public void deleteInOutput(String inouputCode);
	//按id查出要删除出库单编号
	public List selectOutputCode(Integer ids[]);
	//按出库单编号删除出库详细信息
	public void deleteInOutList(List list);
	//查询出库详细记录后执行更新数据库
	public List selectDetailInoutput(List list);
	//按材料编号查询材料的数量和金额
	public List selectMaterilList(String materId);
	//查询所有的出库信息
	public List selectAllOutput(SetMaterialsForm smf);
	/**出库明细表**/
	//查询显示出库明细表的详细信息
	public List selectOutputDetail(OutputDepotManageForm odmf);
	//查询出库记录的总条数
	public int queryCountDetail(OutputDepotManageForm odmf);
	//计算出库明细表中的总数量和总金额
	public List summaryDetail(OutputDepotManageForm odmf);
	//查询所有的出库明细信息
	public List selectAllDetail(OutputDepotManageForm odmf);
	
	/**
	 * 汇总出库金额和数量
	 * @param smf
	 * @return
	 * Date:Nov 23, 2011 
	 * Time:11:31:18 PM
	 */
	public List summary(SetMaterialsForm smf);
}
