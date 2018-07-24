package com.zc13.bkmis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AnalysisEnergyForm;
import com.zc13.bkmis.mapping.AnalysisEnergy;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 31, 2010
 * Time：5:13:03 PM
 */
public interface IAnalysisEnergyDAO extends BasicDAO{
	
	//显示查询统计记录
	public List showEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException;
	//查询记录的条数
	public int queryCountEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException;
	//执行添加统计记录
	public List addEngAnalysis(AnalysisEnergyForm aeForm) throws DataAccessException;
	//查询楼盘信息
	public List selectLp() throws DataAccessException;
	//查看生成图统计记录信息
	public List graphicMessage(int id) throws DataAccessException;
	//查询生成图所需要的数据
	public Map graphicData(String beginDate,String endDate,int lpid) throws DataAccessException;
	//查询前三条记录用于首页的显示
	public List selectEngAnalysis() throws DataAccessException;
	//按id查询要删除记录的详细信息
	public List<AnalysisEnergy> selectDetailAnalysis(Integer[] idArray) throws DataAccessException;
}
