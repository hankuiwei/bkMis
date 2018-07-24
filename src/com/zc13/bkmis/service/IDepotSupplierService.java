package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.DepotSupplierForm;
import com.zc13.bkmis.mapping.DepotSupplier;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 21, 2010
 * Time：3:22:12 PM
 */
public interface IDepotSupplierService extends IBasicService{
	
	//查询显示供应商信息
	public void selectSupplierList(DepotSupplierForm dsf);
	//查询供应商记录总条数用于分页
	public int queryCount(DepotSupplierForm dsf);
	//执行添加供应商信息
	public void addSupplier(DepotSupplierForm dsf);
	//查询要修改的供应商的信息
	public void editSupplier(DepotSupplierForm dsf);
	//执行修改的操作
	public void doEditSupplier(DepotSupplierForm dsf);
	//按id删除信息
	public void delSupplier(int id);
	//按id查询要删除供应商的信息
	public List<DepotSupplier> selectSupplier(Integer[] idArray);
}
