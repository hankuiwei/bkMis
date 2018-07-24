package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.AnalysisChargesForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：3:18:04 PM
 */
public interface IAnalysisChargesService extends IBasicService{

	//显示统计记录列表
	public void showChaAnalysis(AnalysisChargesForm achForm) throws BkmisServiceException;
	//统计记录列表的条数
	public int countQuery(AnalysisChargesForm achForm) throws BkmisServiceException;
	//按id删除统计记录
	public void delChaAnalysis(int id,String userName) throws BkmisServiceException;
	//添加收费统计记录
	public void addChaAnalysis(AnalysisChargesForm achForm) throws BkmisServiceException;
	//查询楼盘信息
	public List selectLp() throws BkmisServiceException;
	//按id查询统计分析记录
	public Map selectAnalys(int id,String path,String type) throws BkmisServiceException;
	//按id查询要删除的统计记录信息
	public List selectDetailAnalys(Integer[] idArray) throws BkmisServiceException;
}
