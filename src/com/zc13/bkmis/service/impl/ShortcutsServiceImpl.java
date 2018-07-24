package com.zc13.bkmis.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zc13.bkmis.dao.ICostTransactDAO;
import com.zc13.bkmis.dao.IShortcutsDAO;
import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.bkmis.form.ShortcutsForm;
import com.zc13.bkmis.service.IShortcutsService;
import com.zc13.util.Contants;

public class ShortcutsServiceImpl implements IShortcutsService {
	private IShortcutsDAO shortcutsDAO;
	private ICostTransactDAO costTransactDAO;
	public IShortcutsDAO getShortcutsDAO() {
		return shortcutsDAO;
	}
	public void setShortcutsDAO(IShortcutsDAO shortcutsDAO) {
		this.shortcutsDAO = shortcutsDAO;
	}

	public ICostTransactDAO getCostTransactDAO() {
		return costTransactDAO;
	}

	public void setCostTransactDAO(ICostTransactDAO costTransactDAO) {
		this.costTransactDAO = costTransactDAO;
	}

	@Override
	public void deleteObject(Object obj) throws Exception {

	}

	@Override
	public List findObject(String queryString) throws Exception {
		return null;
	}

	@Override
	public BasicDAOImpl getBasicDAOImpl() {
		return null;
	}

	@Override
	public Object getObject(Class clazz, Serializable id) throws Exception {
		return null;
	}

	@Override
	public List getObjects(Class clazz) throws Exception {
		return null;
	}

	@Override
	public Object loadObject(Class clazz, Serializable id) throws Exception {
		return null;
	}

	@Override
	public void removeObject(Class clazz, Serializable id) throws Exception {

	}

	@Override
	public void saveObject(Object obj) throws Exception {

	}

	@Override
	public void saveOrUpdateObject(Object obj) throws Exception {

	}

	@Override
	public void setBasicDAOImpl(BasicDAOImpl basicDAOImpl) {

	}

	@Override
	public void updateObject(Object obj) throws Exception {

	}

	/**
	 * 清除账单
	 */
	@Override
	public void clearBill() {
		try {
			costTransactDAO.update("update CompactRoomCoststandard t set lastBuildDate=''");
			costTransactDAO.update("delete from CBill");//删除账单信息
			costTransactDAO.update("delete from CCharge");//删除每次收款的详细信息表
			costTransactDAO.update("delete from AnalysisCost");//删除收费情况分析表
			costTransactDAO.update("delete from CNotice");//删除催缴款通知单表
			costTransactDAO.update("delete from CRefund");//删除退款信息表
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除账单日志，用于可以再次生成账单 
	 */
	@Override
	public void clearBillLog() {
		try {
			costTransactDAO.update("delete from CBuildBillsLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除客户的押金、定金、暂存款、预收房租等信息
	 */
	@Override
	public void deleteFees(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from CDeposit");//删除押金
			costTransactDAO.update("delete from CEarnest");//删除定金
			costTransactDAO.update("delete from CTemporal");//删除暂存款
			costTransactDAO.update("delete from CAdvance");//删除预收房租
			costTransactDAO.update("delete from CAdvanceDetail");//删除预收房租详细信息
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除客户及其相关的信息
	 */
	@Override
	public void deleteClient(ShortcutsForm shortcutsForm) {
		try {
			/*费用有关start*/
			costTransactDAO.update("delete from CBill");//删除账单信息
			costTransactDAO.update("delete from CCharge");//删除每次收款的详细信息表
			costTransactDAO.update("delete from AnalysisCost");//删除收费情况分析表
			costTransactDAO.update("delete from CNotice");//删除催缴款通知单表
			costTransactDAO.update("delete from CRefund");//删除退款信息表
			
			costTransactDAO.update("delete from CDeposit");//删除押金
			costTransactDAO.update("delete from CEarnest");//删除定金
			costTransactDAO.update("delete from CTemporal");//删除暂存款
			costTransactDAO.update("delete from CAdvance");//删除预收房租
			costTransactDAO.update("delete from CAdvanceDetail");//删除预收房租详细信息
			/*费用有关end*/
			
			/*意向书有关start*/
			costTransactDAO.update("delete from ERoomIntention");//删除房间意向书关系表
			costTransactDAO.update("delete from IntentionRoomCoststandard");//删除意向书收费标准关系表
			costTransactDAO.update("delete from IntentionRent");//删除意向书房租关系表
			costTransactDAO.update("delete from CompactIntention");//删除意向书表
			/*意向书有关end*/
			
			/*合同有关start*/
			costTransactDAO.update("delete from CompactChange");//删除合同变更表
			costTransactDAO.update("delete from CompactQuit");//删除合同退租信息
			costTransactDAO.update("delete from CompactQuitBill");//删除合同迁出时生成的账单表
			costTransactDAO.update("delete from CompactRelet");//删除合同续租表
			costTransactDAO.update("delete from CompactRent");//删除合同房租关系表
			costTransactDAO.update("delete from CompactRoomCoststandard");//删除客户房间对应收费标准
			costTransactDAO.update("delete from Compact");//删除合同
			/*合同有关end*/
			
			costTransactDAO.update("delete from AnalysisClientComeGo");//删除客户入住迁出日期表
			costTransactDAO.update("delete from AnalysisCustomer");//删除客户情况分析表
			costTransactDAO.update("delete from CompactTask");//删除客户的工作任务单
			costTransactDAO.update("delete from ERoomClient");//删除客户房间关系表
			costTransactDAO.update("delete from CompactClient");//删除客户信息
			costTransactDAO.update("update ERooms t set t.status='"+Contants.OUTUSE+"' where t.status in('"+Contants.BELEASED+"','"+Contants.DESTINE+"')");//更新room表中的房间状态为未出租
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除统计信息
	 */
	@Override
	public void deleteAnalysis(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from AnalysisClientComeGo");//删除客户迁入迁出信息
			costTransactDAO.update("delete from AnalysisCost");//删除费用统计信息
			costTransactDAO.update("delete from AnalysisCustomer");//删除客户客户统计信息
			costTransactDAO.update("delete from AnalysisEnergy");//删除能源统计信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除费用基础信息(不包括计费参数类型) 
	 */
	@Override
	public void deleteCostBase(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from CAccounttemplate");//删除帐套信息
			costTransactDAO.update("delete from CCosttype");//删除费用类型信息
			costTransactDAO.update("delete from CCoststandard");//删除收费标准信息
			costTransactDAO.update("delete from CItems");//删除收费项信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除库存管理信息
	 */
	@Override
	public void deleteDepot(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from DepotAdjustAccounts");//删除库存核算信息
			costTransactDAO.update("delete from DepotInOutputList");//删除仓库出入库明细信息
			costTransactDAO.update("delete from DepotInputManager");//删除仓库入库管理信息
			costTransactDAO.update("delete from DepotOutputManager");//删除出库管理信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除库存材料和类型信息
	 */
	@Override
	public void deleteDepotBase(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from DepotMaterial");//删除仓库库存材料信息
			costTransactDAO.update("delete from DepotMaterialCopy");//删除库存材料表复制信息
			costTransactDAO.update("delete from DepotMaterialType");//删除材料类别信息
			costTransactDAO.update("delete from DepotSupplier");//删除供应商信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除房产信息 
	 */
	@Override
	public void deleteEstate(ShortcutsForm shortcutsForm) {
	
	}
	
	/**
	 * 删除人事信息
	 */
	@Override
	public void deleteHr(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from HrFile");//删除员工附件信息
			costTransactDAO.update("delete from HrPact");//删除员工合同信息
			costTransactDAO.update("delete from HrPersonnel");//删除员工信息
			costTransactDAO.update("delete from HrExteriorPersonnel");//删除留学人员信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除客户服务信息
	 */
	@Override
	public void deleteSer(ShortcutsForm shortcutsForm) {
		try {
			costTransactDAO.update("delete from SerClientComplaint");//删除客户投诉信息
			costTransactDAO.update("delete from SerClientMaintain");//删除客户保修信息
			costTransactDAO.update("delete from SerMaintainProject");//删除报修项目信息
			costTransactDAO.update("delete from SerMaterialConsume");//删除材料消耗信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
