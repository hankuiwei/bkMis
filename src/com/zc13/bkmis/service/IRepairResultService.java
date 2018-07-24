package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.RepairResultForm;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.exception.BkmisServiceException;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IRepairResultService extends IBasicService {
	
	/** 获得楼盘信息列表 */
	public List<RepairResult> getList(RepairResultForm repairResultForm,boolean isPage)throws BkmisServiceException;
	
	 /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(RepairResultForm repairResultForm);
    
    public void addClient(RepairResultForm form) throws BkmisServiceException;

    public void delRepair(String id) throws BkmisServiceException;

    public RepairResult getRepair(String id) throws BkmisServiceException;
    
    public void editRepair(RepairResultForm form) throws BkmisServiceException;
}
