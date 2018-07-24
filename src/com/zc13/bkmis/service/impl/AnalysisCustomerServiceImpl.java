package com.zc13.bkmis.service.impl;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IAnalysisCustomerDAO;
import com.zc13.bkmis.form.AnalysisCustomerForm;
import com.zc13.bkmis.mapping.AnalysisCustomer;
import com.zc13.bkmis.service.IAnalysisCustomerService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.Creategraphic;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 28, 2010
 * Time：3:14:26 PM
 */
public class AnalysisCustomerServiceImpl extends BasicServiceImpl implements IAnalysisCustomerService {
	
	Logger logger = Logger.getLogger(this.getClass());
	/** 对iaccountDao 注入*/
	private IAnalysisCustomerDAO ianalysiscusDAO;
	
	public IAnalysisCustomerDAO getIanalysiscusDAO() {
		return ianalysiscusDAO;
	}
	
	public void setIanalysiscusDAO(IAnalysisCustomerDAO ianalysiscusDAO) {
		this.ianalysiscusDAO = ianalysiscusDAO;
	}

	//显示查询客户信息分析列表
	public void showAnalysis(AnalysisCustomerForm acForm) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ianalysiscusDAO.showAnalysis(acForm);
		}catch(DataAccessException e){
			logger.error("查询客户分析信息失败！AnalysisCustomerServiceImpl.showAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询客户分析信息失败！AnalysisCustomerServiceImpl.showAnalysis()");
		}
		acForm.setAnalysisCusList(list);
	}

	//查询记录的条数
	public int queryCountAnalysisCus(AnalysisCustomerForm acForm)
			throws BkmisServiceException {
		
			int count = 0;
			try{
				count = ianalysiscusDAO.queryCountAnalysisCus(acForm);
			}catch(DataAccessException e){
				logger.error("查询客户分析信息记录条数失败！AnalysisCustomerServiceImpl.queryCountAnalysisCus()。详细信息："+e.getMessage());
				throw new BkmisServiceException("查询客户分析信息记录条数失败！AnalysisCustomerServiceImpl.queryCountAnalysisCus()");
			}
			return count;
	}

	//执行添加操作
	public void addAnalysisCus(AnalysisCustomerForm acForm) {
			
			AnalysisCustomer ac = new AnalysisCustomer();
			List countList = new ArrayList();
			try {
				BeanUtils.copyProperties(ac, acForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			try{
				countList = ianalysiscusDAO.addAnalysis(acForm);
				if(countList != null && countList.size()>0){
					ac.setInamount((Integer)countList.get(0));
					ac.setOutamount((Integer)countList.get(1));
				}
				ianalysiscusDAO.saveObject(ac);
				//添加系统日志
				try {
					ianalysiscusDAO.logDetail(acForm.getUserName(),Contants.ADD,"客户情况分析",Contants.L_ANNLYSIS,"2",ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch(DataAccessException e){
				logger.error("查询入住迁出数量信息失败！AnalysisCustomerServiceImpl.addAnalysisCus()。详细信息："+e.getMessage());
				throw new BkmisServiceException("查询入住迁出数量信息失败！AnalysisCustomerServiceImpl.addAnalysisCus()");
			}
	}

	//查询楼盘
	public List selectLp() throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ianalysiscusDAO.selectLp();
		}catch(DataAccessException e){
			logger.error("查询楼盘信息失败！AnalysisCustomerServiceImpl.selectLp()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询楼盘信息失败！AnalysisCustomerServiceImpl.selectLp()");
		}
		return list;
	}

	//按id查询统计分析
	public Map selectAnalysisById(int id,String p) throws BkmisServiceException {
		
		List list = new ArrayList();
		AnalysisCustomer ac = new AnalysisCustomer();
		Map map = new HashMap();
		try{
			list = ianalysiscusDAO.selectAnalysisById(id);
			//生成图片
			if(null != list && list.size()>0){
				ac = (AnalysisCustomer)list.get(0);
			}
			//TextTitle title = new TextTitle(ac.getBeginDate()--ac.getEndDate());
			DefaultPieDataset dpd = new DefaultPieDataset ();
			dpd.setValue("入住总数量", ac.getInamount());
			dpd.setValue("迁出总数量", ac.getOutamount());
			JFreeChart chart = ChartFactory.createPieChart("入住与迁出对比分析图", dpd, true, true,false);
			chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
			chart.getLegend().setItemFont(new Font("", Font.BOLD, 12));
			PiePlot plot2 = (PiePlot)chart.getPlot();
		    plot2.setLabelFont(new Font("黑体", Font.BOLD, 12));
		    plot2.setLabelGenerator(new StandardPieSectionLabelGenerator( 
		    		("{0}是：{1} 占百分比：{2}"), NumberFormat.getNumberInstance(), 
		    		new DecimalFormat("0.00%"))); 

			String fileName = null;
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
//			try {
//				fileName = ServletUtilities.saveChartAsJPEG(chart, 650, 450, info,
//						session);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			File temporaryPath = new File(p);
		    if (!temporaryPath.exists()) {
		    	temporaryPath.mkdirs();
		    }
			FileOutputStream out2 = null;
			try {
				out2 = new FileOutputStream(temporaryPath + "\\jfree.jpg");
				ChartUtilities.writeChartAsJPEG(out2, chart, 1000, 300);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					out2.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			map.put("imagePath", "/temporary/jfree.jpg");
			map.put("list", list);
		}catch(DataAccessException e){
			logger.error("按id查询统计分析数据！AnalysisCustomerServiceImpl.selectLp()。详细信息："+e.getMessage());
			throw new BkmisServiceException("按id查询统计分析数据！AnalysisCustomerServiceImpl.selectLp()");
		}
		return map;
	}

	//查询详细信息
	public Map selectAnalysisByParam(String path, AnalysisCustomerForm acForm)
			throws BkmisServiceException {
		
			Map mapCount = new HashMap();
			List inList = new ArrayList();
			List outList = new ArrayList();
			Map map = new HashMap();
			String type = acForm.getSelectValue();
			try{
				//查询入住与迁出的数量
				mapCount = ianalysiscusDAO.selectDetailAmount(acForm);
				//生成入住数量图
				inList = (List)mapCount.get("inList");
				PieDataset inDataset = Creategraphic.createDateset(inList);
				Creategraphic.createChart(type+"的入住对比分析图", inDataset, path, "inFree.jpg");
				//生成迁出数量图
				outList = (List)mapCount.get("outList");
				PieDataset outDataset = Creategraphic.createDateset(outList);
				Creategraphic.createChart(type+"的迁出对比分析图", outDataset, path, "outFree.jpg");
				
				map.put("inRealPath", "/temporary/inFree.jpg");
				map.put("outRealPath", "/temporary/outFree.jpg");
			}catch(DataAccessException e){
				logger.error("按条件查询统计分析数据！AnalysisCustomerServiceImpl.selectAnalysisByParam()。详细信息："+e.getMessage());
				throw new BkmisServiceException("按条件查询统计分析数据！AnalysisCustomerServiceImpl.selectAnalysisByParam()");
			}
			return map;
	}

	//按id查询统计信息参数返回值不同
	public List selectAnaById(int id) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = ianalysiscusDAO.selectAnalysisById(id);
		}catch(DataAccessException e){
			logger.error("按id查询统计分析数据！AnalysisCustomerServiceImpl.selectAnaById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("按id查询统计分析数据！AnalysisCustomerServiceImpl.selectAnaById()");
		}
		return list;
	}

	//删除统计信息
	public void delAnalysis(int id,String userName) throws BkmisServiceException {
		
		try{
			AnalysisCustomer acus = (AnalysisCustomer)ianalysiscusDAO.getObject(AnalysisCustomer.class, id);
			//添加系统日志
			try {
				ianalysiscusDAO.logDetail(userName,Contants.DELETE,"客户情况分析",Contants.L_ANNLYSIS,"3",acus);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ianalysiscusDAO.deleteObject(acus);
		}catch(DataAccessException e){
			logger.error("删除客户统计分析记录！AnalysisCustomerServiceImpl.delAnalysis()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除客户统计分析记录！AnalysisCustomerServiceImpl.delAnalysis()");
		}
	}

	//查看客户详细信息
	@SuppressWarnings("unchecked")
	public Map selecetDetail(AnalysisCustomerForm acForm) throws BkmisServiceException {
		
		List accountList = new ArrayList();
		List detailList = new ArrayList();
		Map map = new HashMap();
		try{
			//获取统计记录信息
			accountList = ianalysiscusDAO.selectAnalysisById(acForm.getId());
			if(null != accountList && accountList.size()>0){
				AnalysisCustomer acus = (AnalysisCustomer)accountList.get(0);
				acForm.setLpId(acus.getLpId());
				acForm.setBeginDate(acus.getBeginDate());
				acForm.setEndDate(acus.getEndDate());
			}
			//查询详细信息
			detailList = ianalysiscusDAO.selectDetail(acForm);
			
			List<Map> list = new ArrayList<Map>();
			for(int i = 0;i<detailList.size();i++){
				Map detailMap = (Map)detailList.get(i);
				if(null != detailMap.get("customerName")){
					String customerName = GlobalMethod.ObjToStr(detailMap.get("customerName"));
					String roomName = GlobalMethod.ObjToStr(detailMap.get("roomFullName"));
					float userArea = 0;
					if(null != detailMap.get("userArea")){
						userArea = (Float)detailMap.get("userArea");
					}
					String clientType = GlobalMethod.ObjToStr(detailMap.get("clientType"));
					String beginDate = GlobalMethod.ObjToStr(detailMap.get("beginDate"));
					String endDate = GlobalMethod.ObjToStr(detailMap.get("endDate"));
					boolean flag = true;
					for(int j = 0;j<list.size();j++){
						Map map2 = list.get(j);
						String cName = GlobalMethod.ObjToStr(map2.get("customerName"));
						String rName = GlobalMethod.ObjToStr(map2.get("roomFullName"));
						float uArea = (Float)map2.get("userArea");
						String bDate = GlobalMethod.ObjToStr(map2.get("beginDate"));
						String eDate = GlobalMethod.ObjToStr(map2.get("endDate"));
						if(customerName.equals(cName)){
							map2.put("roomFullName", roomName+","+rName);
							map2.put("userArea", userArea + uArea);
							if(GlobalMethod.StringToDate(beginDate).before(GlobalMethod.StringToDate(bDate))){
								map2.put("beginDate", beginDate);
							}
							if(GlobalMethod.StringToDate(endDate).after(GlobalMethod.StringToDate(eDate))){
								map2.put("endDate", endDate);
							}
							flag = false;
							break;
						}
					}
					if(flag){
						Map map2 = new HashMap();
						map2.put("customerName", customerName);
						map2.put("roomFullName", roomName);
						map2.put("userArea", userArea);
						map2.put("clientType", clientType);
						map2.put("beginDate", beginDate);
						map2.put("endDate", endDate);
						list.add(map2);
					}
				}
			}	
			map.put("accountList", accountList);
			map.put("detailList", list);
		}catch(DataAccessException e){
			logger.error("查看客户详细信息！AnalysisCustomerServiceImpl.selecetDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查看客户详细信息！AnalysisCustomerServiceImpl.selecetDetail()");
		}
		return map;
	}

	//按id查询要删除统计记录的详细信息
	public List<AnalysisCustomer> selectDetailAnalysis(Integer[] idArray) throws BkmisServiceException {
		
		List<AnalysisCustomer> list = new ArrayList<AnalysisCustomer>();
		try{
			list = ianalysiscusDAO.selectDetailAnalysis(idArray);
		}catch(DataAccessException e){
			logger.error("查询要删除记录的详细信息！AnalysisCustomerServiceImpl.selecetDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除记录的详细信息！AnalysisCustomerServiceImpl.selecetDetail()");
		}
		return list;
	}
	
}
