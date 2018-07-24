package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import com.zc13.bkmis.form.AnalysisEnergyForm;
import com.zc13.bkmis.mapping.AnalysisEnergy;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.form.AdminLoginForm;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 31, 2010
 * Time：5:13:53 PM
 */
public interface IAnalysisEnergyService extends IBasicService{

	//显示查询统计记录信息
	public void showEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException;
	//查询统计信息的记录条数
	public int queryCountEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException;
	//按id删除要统计记录
	public void doDelAnalysis(int id,String userName) throws BkmisServiceException;
	//添加统计信息记录
	public void addEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException;
	//查询楼盘信息
	public List selectLp() throws BkmisServiceException;
	//查看要生成图形的记录信息
	public Map graphicMessage(int id,String path) throws BkmisServiceException;
	//查询前3条统计记录用于在首页显示
	public void selectEngAnalysis(AdminLoginForm aeForm) throws BkmisServiceException;
	//按id查询要删除记录的详细信息
	public List<AnalysisEnergy> selectDetailAnalysis(Integer[] idArray) throws BkmisServiceException;
}
