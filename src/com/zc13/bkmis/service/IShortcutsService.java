package com.zc13.bkmis.service;

import com.zc13.bkmis.form.ShortcutsForm;

public interface IShortcutsService extends IBasicService{

	/**
	 * 清除账单
	 * Date:May 3, 2011 
	 * Time:5:37:38 PM
	 */
	public void clearBill();

	/**
	 * 删除账单日志
	 * Date:May 3, 2011 
	 * Time:5:37:55 PM
	 */
	public void clearBillLog();

	/**
	 * 删除客户的押金、定金、暂存款、预收房租等信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:9:28:38 AM
	 */
	public void deleteFees(ShortcutsForm shortcutsForm);

	/**
	 * 删除客户
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:10:19:12 AM
	 */
	public void deleteClient(ShortcutsForm shortcutsForm);

	/**
	 * 删除统计信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:2:48:52 PM
	 */
	public void deleteAnalysis(ShortcutsForm shortcutsForm);

	/**
	 * 删除费用基础信息(不包括计费参数类型)
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:06:15 PM
	 */
	public void deleteCostBase(ShortcutsForm shortcutsForm);

	/**
	 * 删除库存管理信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:11:14 PM
	 */
	public void deleteDepot(ShortcutsForm shortcutsForm);

	/**
	 * 删除库存材料和类型信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:11:33 PM
	 */
	public void deleteDepotBase(ShortcutsForm shortcutsForm);

	/**
	 * 删除房产信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:11:57 PM
	 */
	public void deleteEstate(ShortcutsForm shortcutsForm);

	/**
	 * 删除人事信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:12:11 PM
	 */
	public void deleteHr(ShortcutsForm shortcutsForm);

	/**
	 * 删除客户服务信息
	 * @param shortcutsForm
	 * Date:May 4, 2011 
	 * Time:3:49:27 PM
	 */
	public void deleteSer(ShortcutsForm shortcutsForm);

}
