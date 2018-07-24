package com.zc13.bkmis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AnalysisCustomerForm;
import com.zc13.bkmis.mapping.AnalysisCustomer;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 28, 2010
 * Time：3:15:09 PM
 */
public interface IAnalysisCustomerDAO extends BasicDAO{

	//显示查询客户信息分析列表
	public List showAnalysis(AnalysisCustomerForm acForm) throws DataAccessException;
	//查询记录的条数 
	public int queryCountAnalysisCus(AnalysisCustomerForm acForm) throws DataAccessException;
	//执行添加操作
	public List addAnalysis(AnalysisCustomerForm acForm) throws DataAccessException;
	//查询楼盘
	public List selectLp() throws DataAccessException;
	//按id查询统计分析数据
	public List selectAnalysisById(int id) throws DataAccessException;
	//查询详细数据
	public Map selectDetailAmount(AnalysisCustomerForm acForm) throws DataAccessException;
	//查看详细信息
	public List selectDetail(AnalysisCustomerForm acForm) throws DataAccessException;
	//按id查询要删除统计记录的详细信息
	public List<AnalysisCustomer> selectDetailAnalysis(Integer[] idArray) throws DataAccessException;
}
