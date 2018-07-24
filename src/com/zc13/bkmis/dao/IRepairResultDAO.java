package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.RepairResultForm;
import com.zc13.bkmis.mapping.RepairResult;

public interface IRepairResultDAO  extends BasicDAO {
	
	public List<RepairResult> queryRepairResult(RepairResultForm repairResultForm,boolean isPage) throws DataAccessException;

	/**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(RepairResultForm repairResultForm);
    
}
