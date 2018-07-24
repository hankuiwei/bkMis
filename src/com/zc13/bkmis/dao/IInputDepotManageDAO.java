package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.InputDepotManageForm;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:06:17 PM
 */
public interface IInputDepotManageDAO extends BasicDAO{
	//按id查询选择要添加的材料信息
	public List selectMaterialsById(String[] idArray);
	//查询显示的入库单信息
	public List selectInputList(InputDepotManageForm idmf);
	//查询记录总数
	public int queryCounttotal(InputDepotManageForm idmf);
	//查询供应商
	public List selectSupplier();
	//生成入库单编号
	public String GenerationNum();
	//按id查询出要修改的信息
	public List selectEditInput(int id);
	//按i出库单编号查询出库单详细信息
	public List selectDetailInput(String inputCode);
	//按出库单编号删除详细信息
	public void deleteInOutput(String inputCode);
	//根据id查询出库单的出库编号
	public List selectIutputCode(Integer ids[]);
	//删除入库单时删除入库单的详细信息
	public void deleteInOutList(List list);
	//按入库单编号查询入库详细信息
	public List selectDetailList(List list);
	//按材料编号查询材料的数量和金额
	public List selectMaterilList(String materId);
	//查询所有的入库信息
	public List selectAllInput(InputDepotManageForm idmf);
	/**查询显示入库表的详细信息**/
	//查询显示出库表的详细信息
	public List selectInputDetail(InputDepotManageForm idmf);
	//查询入库详细信息的记录条数
	public int queryCountInDetail(InputDepotManageForm idmf);
	//查询入库的入库总数量和入库总金额
	public List summaryInDetail(InputDepotManageForm idmf);
	//查询所有的入库明细信息
	public List selectAllDetail(InputDepotManageForm idmf);
	
	/**
	 * 汇总入库总金额
	 * @param idmf
	 * @return
	 * Date:Nov 23, 2011 
	 * Time:11:42:16 PM
	 */
	public List summary(InputDepotManageForm idmf);
}
