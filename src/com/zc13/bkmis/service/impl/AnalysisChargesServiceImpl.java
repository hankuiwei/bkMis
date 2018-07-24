package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jfree.data.general.PieDataset;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAnalysisChargesDAO;
import com.zc13.bkmis.form.AnalysisChargesForm;
import com.zc13.bkmis.mapping.AnalysisCost;
import com.zc13.bkmis.service.IAnalysisChargesService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.Creategraphic;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：3:18:18 PM
 */
public class AnalysisChargesServiceImpl extends BasicServiceImpl implements IAnalysisChargesService {

	Logger logger = Logger.getLogger(this.getClass());
	/** 对iaccountDao 注入*/
	private IAnalysisChargesDAO ichargeDAO;
	
	public IAnalysisChargesDAO getIchargeDAO() {
		return ichargeDAO;
	}
	public void setIchargeDAO(IAnalysisChargesDAO ichargeDAO) {
		this.ichargeDAO = ichargeDAO;
	}
	//显示统计记录
	public void showChaAnalysis(AnalysisChargesForm achForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ichargeDAO.showChaAnalysis(achForm);
		}catch(DataAccessException e){
			logger.error("查询显示统计记录分析列表失败！AnalysisChargesServiceImpl.showChaAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示统计记录分析列表失败！AnalysisChargesServiceImpl.showChaAnalysis()");
		}
		achForm.setChaAnalysisList(list);
	}
	//统计记录列表的条数
	public int countQuery(AnalysisChargesForm achForm)
			throws BkmisServiceException {
		
		int count = 0;
		try{
			count = ichargeDAO.countQuery(achForm);
		}catch(DataAccessException e){
			logger.error("查询统计记录分析列表条数失败！AnalysisChargesServiceImpl.countQuery()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询统计记录分析列表条数失败！AnalysisChargesServiceImpl.countQuery()");
		}
		return count;
	}
	//按id删除统计记录
	public void delChaAnalysis(int id,String userName) throws BkmisServiceException {
		
		try{
			AnalysisCost ac = (AnalysisCost)ichargeDAO.getObject(AnalysisCost.class, id);

			//添加系统日志
			try {
				ichargeDAO.logDetail(userName, Contants.DELETE,"收费统计分析",Contants.L_SERVICE, "3", ac);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ichargeDAO.deleteObject(ac);
		}catch(DataAccessException e){
			logger.error("删除统计记录分析列表失败！AnalysisChargesServiceImpl.delChaAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除统计记录分析列表失败！AnalysisChargesServiceImpl.delChaAnalysis()");
		}
	}
	//添加收费统计记录
	@SuppressWarnings("unchecked")
	public void addChaAnalysis(AnalysisChargesForm achForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		float count = 0;
		try{
			list = ichargeDAO.addChaAnalysis(achForm);
			if(null != list && list.size()>0){
				if(null != list.get(0)){
					count = ((Double)list.get(0)).floatValue();
				}
			}
			AnalysisCost ac = new AnalysisCost();
			try {
				BeanUtils.copyProperties(ac, achForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			ac.setAmountMoney(count);
			ichargeDAO.saveObject(ac);
			//添加系统日志
			try {
				ichargeDAO.logDetail(achForm.getUserName(),Contants.ADD,"收费情况分析",Contants.L_ANNLYSIS,"2",ac);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(DataAccessException e){
			logger.error("添加统计记录分析列表失败！AnalysisChargesServiceImpl.addChaAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加统计记录分析列表失败！AnalysisChargesServiceImpl.addChaAnalysis()");
		}
	}
	//查询楼盘
	public List selectLp() throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ichargeDAO.selectLp();
		}catch(DataAccessException e){
			logger.error("查询楼盘信息失败！AnalysisChargesServiceImpl.selectLp()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询楼盘信息失败！AnalysisChargesServiceImpl.selectLp()");
		}
		return list;
	}
	//查询统计分析记录
	public Map selectAnalys(int id,String path,String type) throws BkmisServiceException {
		
		Map map = new HashMap();
		List list = new ArrayList();
		AnalysisChargesForm acForm = new AnalysisChargesForm();
		AnalysisCost ac = new AnalysisCost();
		//生成图的数据
		List graphicList = new ArrayList();
		try{
			//查询统计记录的信息
			list = ichargeDAO.selectAnalysis(id);
			if(null != list && list.size()>0){
				 ac = (AnalysisCost)list.get(0);
			}
			map.put("analysisList", list);
			try {
				BeanUtils.copyProperties(acForm, ac);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//查询生成图的判断
			if("".equals(type) || null == type){
				type = "item_type";
			}
			String imagePath = "";
			//以收费项来分析
			if(type.equals("item_type")){
				graphicList = ichargeDAO.createGraphicData(acForm, type);
				PieDataset chargeDateset = Creategraphic.createDateset2(graphicList);
				Creategraphic.createChart2("按收费类型对比分析图统", chargeDateset, path, "charge1.jpg");
				
				//路径
				imagePath = "/temporary/charge1.jpg";
			}
			//以客户来分析
			if(type.equals("customer")){
				graphicList = ichargeDAO.createGraphicData(acForm, type);
				PieDataset chargeDateset = Creategraphic.createDateset2(graphicList);
				Creategraphic.createChart2("按客户缴费对比分析图", chargeDateset, path, "charge2.jpg");
				
				//路径
				imagePath = "/temporary/charge2.jpg";
			}
			map.put("imagePath", imagePath);
		}catch(DataAccessException e){
			logger.error("查询统计记录信息失败！AnalysisChargesServiceImpl.selectAnalys()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询统计记录信息失败！AnalysisChargesServiceImpl.selectAnalys()");
		}
		return map;
	}
	//按id查询要删除的详细信息内容
	public List<AnalysisCost> selectDetailAnalys(Integer[] idArray) throws BkmisServiceException {
		
		List<AnalysisCost> list = new ArrayList<AnalysisCost>();
		try{
			list = ichargeDAO.selectDetailAnalys(idArray);
		}catch(DataAccessException e){
			logger.error("查询要删除记录信息的详细信息失败！AnalysisChargesServiceImpl.selectDetailAnalys()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除记录信息的详细信息失败！AnalysisChargesServiceImpl.selectDetailAnalys()");
		}
		return list;
	}
	
}
