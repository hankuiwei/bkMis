package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.AnalysisCustomerForm;
import com.zc13.bkmis.mapping.AnalysisCustomer;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 28, 2010
 * Time：3:14:45 PM
 */
public interface IAnalysisCustomerService extends IBasicService{
	
	//显示查询出的客户信息分析列表
	public void showAnalysis(AnalysisCustomerForm acForm) throws BkmisServiceException;
	//查询客户信息分析记录的条数
	public int queryCountAnalysisCus(AnalysisCustomerForm acForm) throws BkmisServiceException;
	//执行添加操作
	public void addAnalysisCus(AnalysisCustomerForm acForm) throws BkmisServiceException;
	//查询楼盘名称
	public List selectLp() throws BkmisServiceException;
	//按id查询分析统计信息
	public Map selectAnalysisById(int id,String path) throws BkmisServiceException;
	//按条件查询统计分析信息
	public Map selectAnalysisByParam(String path,AnalysisCustomerForm acForm) throws BkmisServiceException;
	//按id查询分析统计信息参数不同
	public List selectAnaById(int id) throws BkmisServiceException;
	//按id删除分析统计信息记录
	public void delAnalysis(int id,String userName) throws BkmisServiceException;
	//查看客户的详细信息
	public Map selecetDetail(AnalysisCustomerForm acForm) throws BkmisServiceException;
	//按id查询要删除统计记录的详细信息
	public List<AnalysisCustomer> selectDetailAnalysis(Integer[] idArray) throws BkmisServiceException;
}
