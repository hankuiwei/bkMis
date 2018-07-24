package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AnalysisChargesForm;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：3:17:24 PM
 */
public interface IAnalysisChargesDAO extends BasicDAO {

	//显示统计记录列表
	public List showChaAnalysis(AnalysisChargesForm acForm) throws DataAccessException;
	//统计记录列表条数
	public int countQuery(AnalysisChargesForm acForm) throws DataAccessException;
	//添加统计记录信息
	public List addChaAnalysis(AnalysisChargesForm acForm) throws DataAccessException;
	//查询楼盘信息
	public List selectLp() throws DataAccessException;
	//查询统计记录信息
	public List selectAnalysis(int id) throws DataAccessException;
	//按查询生成图条件查询所需数据
	public List createGraphicData(AnalysisChargesForm acForm,String type) throws DataAccessException;
	//按id查询要删除的统计记录信息
	public List selectDetailAnalys(Integer[] idArray) throws DataAccessException;
}
