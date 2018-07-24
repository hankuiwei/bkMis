package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.DepotSupplierForm;
import com.zc13.bkmis.mapping.DepotSupplier;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 21, 2010
 * Time：3:22:57 PM
 */
public interface IDepotSupplierDAO extends  BasicDAO{
	
	//查询显示供应商信息
	public List selectSupplierList(DepotSupplierForm dsf);
	//查询供应商记录总条数用于分页
	public int queryCount(DepotSupplierForm dsf);
	//查询要修改的供应商的信息
	public List editSupplier(int id);
	//按id查询要删除供应商的信息
	public List<DepotSupplier> selectSupplier(Integer[] idArray);
}
