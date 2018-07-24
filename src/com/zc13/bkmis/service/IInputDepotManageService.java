package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.InputDepotManageForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:06:45 PM
 */
public interface IInputDepotManageService extends IBasicService{
	//根据id获取选择的材料
	public List selectMaterialsById(String[] idArray) throws BkmisServiceException;
	//查询显示的入库单信息
	public void selectInputList(InputDepotManageForm idmf) throws BkmisServiceException;
	//查询记录总数
	public int queryCounttotal(InputDepotManageForm idmf) throws BkmisServiceException;
	//查询供应商
	public List selectSupplier() throws BkmisServiceException;
	//生成入库单编号
	public String GenerationNum() throws BkmisServiceException;
	//添加入库单信息
	public void doAddInput(InputDepotManageForm idmf) throws BkmisServiceException;
	//按id查询出要修改的信息
	public List selectEditInput(int id) throws BkmisServiceException;
	//按i出库单编号查询出库单详细信息
	public List selectDetailInput(String inputCode) throws BkmisServiceException;
	//按出库单编号删除详细信息
	public void deleteInOutput(String inputCode) throws BkmisServiceException;
	//更新入库单信息
	public void updateInput(InputDepotManageForm idmf) throws BkmisServiceException;
	//根据id查询出库单的出库编号
	public List selectIutputCode(Integer ids[]) throws BkmisServiceException;
	//删除入库单时删除入库单的详细信息
	public void deleteInOutList(List list) throws BkmisServiceException;
	// 按id删除入库单信息
	public void deleteInput(int id) throws BkmisServiceException;
	//执行入库统计记录的核算
	public void doAccountInput(int id) throws BkmisServiceException;
	//按入库单编号查询入库详细信息
	public List selectDetailList(List list);
	//查询所有的入库信息
	public List selectAllInput(InputDepotManageForm idmf) throws BkmisServiceException;
	/**入库表的详细信息的查询**/
	//查询显示入库表的详细信息
	public void selectInputDetail(InputDepotManageForm idmf) throws BkmisServiceException;
	//查询入库详细信息的记录条数
	public int queryCountInDetail(InputDepotManageForm idmf) throws BkmisServiceException;
	//查询入库的入库总数量和入库总金额
	public List summaryInDetail(InputDepotManageForm idmf) throws BkmisServiceException;
	//查询所有的入库明细信息
	public List selectAllDetail(InputDepotManageForm idmf) throws BkmisServiceException;
	
	public boolean ifDate() throws BkmisServiceException;
	
	/**
	 * 删除入库单
	 * @param idmf
	 * @return
	 * @throws BkmisServiceException
	 * Date:Oct 9, 2011 
	 * Time:3:42:55 PM
	 */
	public boolean deleteInput(InputDepotManageForm idmf) throws BkmisServiceException;
	/**
	 * 汇总入库总金额
	 * @param idmf
	 * @return
	 * @throws BkmisServiceException
	 * Date:Nov 23, 2011 
	 * Time:11:40:36 PM
	 */
	public List summary(InputDepotManageForm idmf) throws BkmisServiceException;
}
