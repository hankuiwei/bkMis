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

import com.zc13.bkmis.dao.IAnalysisEnergyDAO;
import com.zc13.bkmis.form.AnalysisEnergyForm;
import com.zc13.bkmis.mapping.AnalysisEnergy;
import com.zc13.bkmis.service.IAnalysisEnergyService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.form.AdminLoginForm;
import com.zc13.util.Contants;
import com.zc13.util.Creategraphic;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 31, 2010
 * Time：5:13:39 PM
 */
public class AnalysisEnergyServiceImpl extends BasicServiceImpl implements IAnalysisEnergyService {

	Logger logger = Logger.getLogger(this.getClass());
	/** 对ienergyDao 注入*/
	private IAnalysisEnergyDAO ienergyDAO;
	
	public IAnalysisEnergyDAO getIenergyDAO() {
		return ienergyDAO;
	}
	public void setIenergyDAO(IAnalysisEnergyDAO ienergyDAO) {
		this.ienergyDAO = ienergyDAO;
	}
	//显示查询统计记录
	public void showEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ienergyDAO.showEngAnalysis(aeForm);
		}catch(DataAccessException e){
			logger.error("查询显示能源统计信息失败！AnalysisEnergyServiceImpl.showEngAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示能源统计信息失败！AnalysisEnergyServiceImpl.showEngAnalysis()");
		}
		aeForm.setEnergyList(list);
	}
	//查询统计记录条数
	public int queryCountEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException {
		
		int count = 0;
		try{
			count = ienergyDAO.queryCountEngAnalysis(aeForm);
		}catch(DataAccessException e){
			logger.error("查询显示能源统记录条数计信息失败！AnalysisEnergyServiceImpl.queryCountEngAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示能源统记录条数计信息失败！AnalysisEnergyServiceImpl.queryCountEngAnalysis()");
		}
		return count;
	}
	//按id删除要统计的记录
	public void doDelAnalysis(int id,String userName) throws BkmisServiceException {
		
		try{
			AnalysisEnergy ae = new AnalysisEnergy();
			//获取要删除的对象
			ae = (AnalysisEnergy)ienergyDAO.getObject(AnalysisEnergy.class, id);

			//添加系统日志
			try {
				ienergyDAO.logDetail(userName,Contants.DELETE,"能源情况分析",Contants.L_ANNLYSIS,"3",ae);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//执行删除操作
			ienergyDAO.deleteObject(ae);
		}catch(DataAccessException e){
			logger.error("删除显示能源统记录计信息失败！AnalysisEnergyServiceImpl.doDelAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除显示能源统记录计信息失败！AnalysisEnergyServiceImpl.doDelAnalysis()");
		}
	}
	//添加统计记录信息
	public void addEngAnalysis(AnalysisEnergyForm aeForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			AnalysisEnergy ae = new AnalysisEnergy();
			try {
				BeanUtils.copyProperties(ae, aeForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			list = ienergyDAO.addEngAnalysis(aeForm);
			if(null != list && list.size()>0){
			  for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				if(obj[0].toString().equals("601")){//electric换为601
					ae.setTotalElectricity(((Double)obj[1]).floatValue());
				}
				if(obj[0].toString().equals("603")){//gas换为603
					ae.setTotalGas(((Double)obj[1]).floatValue());
				}
				if(obj[0].toString().equals("602")){//water换为602
					ae.setTotalWater(((Double)obj[1]).floatValue());
				}
			  }
			}
			ienergyDAO.saveObject(ae);
			//添加系统日志
			try {
				ienergyDAO.logDetail(aeForm.getUserName(),Contants.ADD,"能源情况分析",Contants.L_ANNLYSIS,"2",ae);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(DataAccessException e){
			e.printStackTrace();
			logger.error("添加能源统记录计信息失败！AnalysisEnergyServiceImpl.addEngAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加能源统记录计信息失败！AnalysisEnergyServiceImpl.addEngAnalysis()");
		}
	}
	//查询楼盘信息
	public List selectLp() throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ienergyDAO.selectLp();
		}catch(DataAccessException e){
			logger.error("查询楼盘信息失败！AnalysisEnergyServiceImpl.selectLp()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询楼盘信息失败！AnalysisEnergyServiceImpl.selectLp()");
		}
		return list;
	}
	//查询生成图信息
	public Map graphicMessage(int id,String path) throws BkmisServiceException {
		
		List list = new ArrayList();
		Map map = new HashMap();
		AnalysisEnergy ae = new AnalysisEnergy();
		Map graphicData = new HashMap();
		try{
			//查看要生成图的统计记录的信息
			list = ienergyDAO.graphicMessage(id);
			//生成图
			if(list != null && list.size()>0){
				ae = (AnalysisEnergy)list.get(0);
			}
			//查询生成图所需数据
			graphicData = ienergyDAO.graphicData(ae.getBeginDate(),ae.getEndDate(),ae.getLpId());
			
			List electricList = (List)graphicData.get("electricList");
			List waterList = (List)graphicData.get("waterList");
			List gasList = (List)graphicData.get("gasList");
			//调用生成图方法
			PieDataset elecDataset = Creategraphic.createEngDateset(electricList);
			Creategraphic.createEngChart("用电总量对比分析图", elecDataset, path, "electricEng.jpg");
			
			PieDataset waterDataset = Creategraphic.createEngDateset(waterList);
			Creategraphic.createEngChart("用水总量对比分析图", waterDataset, path, "waterEng.jpg");
			
			PieDataset gasDataset = Creategraphic.createEngDateset(gasList);
			Creategraphic.createEngChart("用气总量对比分析图", gasDataset, path, "gasEng.jpg");
			map.put("list", list);
			map.put("electricPath", "/temporary/electricEng.jpg");
			map.put("waterPath", "/temporary/waterEng.jpg");
			map.put("gasPath", "/temporary/gasEng.jpg");
			
		}catch(DataAccessException e){
			logger.error("查询生成图统计记录信息失败！AnalysisEnergyServiceImpl.graphicMessage()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询生成图统计记录信息失败！AnalysisEnergyServiceImpl.graphicMessage()");
		}
		return map;
	}
	//查询能源前3条记录用于首页的显示
	public void selectEngAnalysis(AdminLoginForm aeForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ienergyDAO.selectEngAnalysis();
		}catch(DataAccessException e){
			logger.error("查询前几条统计记录信息失败！AnalysisEnergyServiceImpl.selectEngAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询前几条统计记录信息失败！AnalysisEnergyServiceImpl.selectEngAnalysis()");
		}
		aeForm.setFrontEngList(list);
	}
	//按id查询要删除记录的详细信息
	public List<AnalysisEnergy> selectDetailAnalysis(Integer[] idArray) throws BkmisServiceException {
		
		List<AnalysisEnergy> list = new ArrayList<AnalysisEnergy>();
		try{
			list = ienergyDAO.selectDetailAnalysis(idArray);
		}catch (DataAccessException e) {
			logger.error("查询要删除记录的详细信息失败！AnalysisEnergyServiceImpl.selectDetailAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除记录的详细信息失败！AnalysisEnergyServiceImpl.selectDetailAnalysis()");
		}
		return list;
	}
	
}
