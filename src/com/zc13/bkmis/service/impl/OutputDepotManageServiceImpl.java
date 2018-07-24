package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IOutputDepotManageDAO;
import com.zc13.bkmis.form.OutputDepotManageForm;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotInOutputList;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.mapping.DepotOutputManager;
import com.zc13.bkmis.service.IOutputDepotManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
import com.zc13.util.LogParam;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:37:30 PM
 */
public class OutputDepotManageServiceImpl extends BasicServiceImpl implements IOutputDepotManageService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/** 对ipersonnelDao 注入*/
	private IOutputDepotManageDAO outputdepotDao;

	public IOutputDepotManageDAO getOutputdepotDao() {
		return outputdepotDao;
	}

	public void setOutputdepotDao(IOutputDepotManageDAO outputdepotDao) {
		this.outputdepotDao = outputdepotDao;
	}

	//查询部门
	public List selectDepartment() {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectDepartment();
		}catch(Exception e){
			logger.error("查询部门信息加载失败！OutputDepotManageServiceImpl.selectDepartment()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询部门信息加载失败！OutputDepotManageServiceImpl.selectDepartment()");
		}
		return list;
	}

	//按id查询选择要添加的材料信息
	@SuppressWarnings("unchecked")
	public List selectMaterialsById(String idArray[]) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectMaterialsById(idArray);
		}catch(Exception e){
			logger.error("查询选择要添加的材料信息加载失败！OutputDepotManageServiceImpl.selectMaterialsById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询选择要添加的材料信息加载失败！OutputDepotManageServiceImpl.selectMaterialsById()");
		}
		return list;
	}

	//显示出库单信息
	public void showOutputMaterial(SetMaterialsForm smf) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.showOutputMaterial(smf);
		}catch(Exception e){
			logger.error("显示出库单信息加载失败！OutputDepotManageServiceImpl.showOutputMaterial()。详细信息："+e.getMessage());
			throw new BkmisServiceException("显示出库单信息加载失败！OutputDepotManageServiceImpl.showOutputMaterial()");
		}
		smf.setOutputList(list);
	}

	//查询记录的总条数
	public int queryCounttotal(SetMaterialsForm smf) {
		int count = 0;
		try{
			count = outputdepotDao.queryCounttotal(smf);
		}catch(Exception e){
			logger.error("查询记录总条数信息加载失败！OutputDepotManageServiceImpl.queryCounttotal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询记录总条数信息加载失败！OutputDepotManageServiceImpl.queryCounttotal()");
		}
		return count;
	}

	//生成出库单编号
	public String GenerationNum() {
		String num = "";
		String dateFormat = "";//存储时间
		String strsubNum = "";//截取后几位单号
		int subNum = 0;
		try{
			num = outputdepotDao.GenerationNum();
			if("".equals(num) || null == num ){
				dateFormat =GlobalMethod.getTime2();
				num = dateFormat +"001";
			}else{
				dateFormat =GlobalMethod.getTime2();
				strsubNum= num.substring(8, 11);
				subNum = Integer.parseInt(strsubNum)+1;
				if(subNum <10 && subNum > 0){
					strsubNum = "00" + String.valueOf(subNum);
				}
				if(subNum >= 10 && subNum < 100){
					strsubNum = "0" + String.valueOf(subNum);
				}
				num = dateFormat + strsubNum;
			}
		}catch(Exception e){
			logger.error("查询出库单编号信息加载失败！OutputDepotManageServiceImpl.GenerationNum()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询出库单编号信息加载失败！OutputDepotManageServiceImpl.GenerationNum()");
		}
		return num;
	}

	//执行添加出库单信息
	public void doAddOutput(OutputDepotManageForm outputForm) {
		
		DepotOutputManager depotOutput = new DepotOutputManager();
		outputForm.setUpdateDate(GlobalMethod.getTime());
		outputForm.setCode(this.GenerationNum());
		try{
			BeanUtils.copyProperties(depotOutput, outputForm);
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		depotOutput.setStatus(Contants.WAITACCOUNT);
		depotOutput.setUpdateDate(GlobalMethod.dateToString(new Date()));
		outputdepotDao.doAddOutput(depotOutput);
		//处理详单
		dealInOutput(outputForm);
	}

	//按id查询出出库单信息
	public List selectOutput(int id) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectOutput(id);
		}catch(Exception e){
			logger.error("查询出库单信息加载失败！OutputDepotManageServiceImpl.editOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询出库单信息加载失败！OutputDepotManageServiceImpl.editOutput()");
		}
		return list;
	}

	//按编号查询出库的详细信息
	public List selectinoutput(String inouputCode) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectinoutput(inouputCode);
		}catch(Exception e){
			logger.error("查询出库单详细信息加载失败！OutputDepotManageServiceImpl.selectinoutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询出库单详细信息加载失败！OutputDepotManageServiceImpl.selectinoutput()");
		}
		return list;
	}

	//根据出库单号删除出库的详细信息
	public void deleteInOutput(String inoutputCode) {
			
		try{
			outputdepotDao.deleteInOutput(inoutputCode);
		}catch(Exception e){
			logger.error("删除出库单详细信息加载失败！OutputDepotManageServiceImpl.deleteInOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除出库单详细信息加载失败！OutputDepotManageServiceImpl.deleteInOutput()");
		}
	}

	//更新出库单信息
	public void updateOutput(OutputDepotManageForm outputForm) throws BkmisServiceException{
		
		int id = outputForm.getId();
		DepotOutputManager dom = (DepotOutputManager)outputdepotDao.getObject(DepotOutputManager.class, id);
		DepotOutputManager dom2 = new DepotOutputManager();
		try{
			BeanUtils.copyProperties(dom2, outputForm);
			try {
				outputdepotDao.logDetail(outputForm.getUserName(), Contants.MODIFY, "出库管理",Contants.L_Depot, "1", dom2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//添加系统日志
			BeanUtils.copyProperties(dom, outputForm);
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (BkmisServiceException e) {
			e.printStackTrace();
		}
		dom.setStatus(Contants.WAITACCOUNT);
		try{
			//处理详单
			
			outputdepotDao.logDetail(outputForm.getUserName(), Contants.MODIFY, "", "出库管理", "1", dom);//添加系统日志
			outputdepotDao.saveOrUpdateObject(dom);//更新
			dealInOutput(outputForm);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新出库单信息加载失败！OutputDepotManageServiceImpl.updateOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("更新出库单信息加载失败！OutputDepotManageServiceImpl.updateOutput()");
		}
	}
	
	/**
	 * 对详单进行处理
	 * @param outputForm
	 */
	public void dealInOutput(OutputDepotManageForm outputForm){
		//获得单据原详情
		List<DepotInOutputList> inoutputList = outputdepotDao.findObject("from DepotInOutputList where type2 = '0' and inOutputCode = '"+outputForm.getCode()+"'");
		//根据详单增加库存数量
		if(inoutputList!=null&&inoutputList.size()>0){
			for(int i = 0;i<inoutputList.size();i++){
				//获得库存对应材料
				DepotMaterial material = null;
				try {
					material = (DepotMaterial)outputdepotDao.getObject(DepotMaterial.class, inoutputList.get(i).getMaterialId());
				} catch(Exception e) {}
				if(null!=material) {
					//更新库存对应材料数量
					material.setNowStock(material.getNowStock()+inoutputList.get(i).getAmount());
					material.setDoStock(material.getDoStock()+inoutputList.get(i).getAmount());
					//更新金额
					double a = inoutputList.get(i).getAmount()*inoutputList.get(i).getUnitPrice();
					material.setMoney((double)(material.getMoney()+a));
					//更新单价
					if(material.getNowStock()>0.001){
						material.setUnitPrice(material.getMoney()/material.getNowStock());
					}
					//material.setUnitPrice(material.getMoney()/material.getNowStock());
					outputdepotDao.updateObject(material);
				}
			}
		}
		//删除单据原详细
		outputdepotDao.deleteInOutput(outputForm.getCode());
		
		//增加表单中的新详情
		String[] materialCode = outputForm.getMaterialCode();
		double[] amount = outputForm.getAmount();
		double[] unitPrice = outputForm.getUnitPrice();
		double[] amountMoney = outputForm.getAmountMoney();
		String[] inoutputCode = outputForm.getInOutputCode();
		int[] ids = outputForm.getIds();
		int i = 0;
		if(materialCode!=null){
			i=materialCode.length;
		}
		DepotInOutputList[] depotInOut = new DepotInOutputList[i];
		for(int j = 0;j<i;j++){
			depotInOut[j] = new DepotInOutputList();
			depotInOut[j].setMaterialId(ids[j]);
			depotInOut[j].setAmount(amount[j]);
			depotInOut[j].setUnitPrice(unitPrice[j]);
			depotInOut[j].setAmountMoney(amountMoney[j]);
			depotInOut[j].setInOutputCode(inoutputCode[j]);
			depotInOut[j].setType2(Contants.OUTPUTDEPOT);
			depotInOut[j].setDate(outputForm.getDate());
			outputdepotDao.doAddInOutput(depotInOut[j]);
			//获得库存对应材料
			DepotMaterial material = (DepotMaterial)outputdepotDao.getObject(DepotMaterial.class, ids[j]);
			//更新库存对应材料数量
			material.setNowStock(material.getNowStock()-amount[j]);
			material.setDoStock(material.getDoStock()-amount[j]);
			//更新总金额
			material.setMoney((double)(material.getMoney()-unitPrice[j]*amount[j]));
			//更新单价
			if(material.getNowStock()>0.001){
				material.setUnitPrice(material.getMoney()/material.getNowStock());
			}
			outputdepotDao.updateObject(material);
		}
	}

	//按id删除出库单信息
	public void delOutput(int id) {
		
		DepotOutputManager dom = (DepotOutputManager)outputdepotDao.getObject(DepotOutputManager.class, id);
		try{
			outputdepotDao.deleteObject(dom);
		}catch(Exception e){
			logger.error("删除出库单信息加载失败！OutputDepotManageServiceImpl.delOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除出库单信息加载失败！OutputDepotManageServiceImpl.delOutput()");
		}
	}

	//按id查询出要删除的出库单编号
	public List selectOutputCode(Integer[] ids) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectOutputCode(ids);
		}catch(Exception e){
			logger.error("查询要删除出库单编号加载失败！OutputDepotManageServiceImpl.selectOutputCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要删除出库单编号加载失败！OutputDepotManageServiceImpl.selectOutputCode()");
		}
		return list;
	}

	//按出库单编号可以批量删除出库单的详细信息
	public void deleteInOutList(List list) {
		
		try{
			outputdepotDao.deleteInOutList(list);
		}catch(Exception e){
			logger.error("删除多个出库单编号加载失败！OutputDepotManageServiceImpl.deleteInOutList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除多个出库单编号加载失败！OutputDepotManageServiceImpl.deleteInOutList()");
		}
	}

	/**出库明细表中出库详细信息查询**/
	public void selectOutputDetail(OutputDepotManageForm odmf) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectOutputDetail(odmf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("出库明细表查询信息加载失败！OutputDepotManageServiceImpl.selectOutputDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("出库明细表查询信息加载失败！OutputDepotManageServiceImpl.selectOutputDetail()");
		}
		odmf.setOutputDatilList(list);
	}

	//查询出库单详细信息的记录条数
	public int queryCountDetail(OutputDepotManageForm odmf) {
		
		int count = 0;
		try{
			count = outputdepotDao.queryCountDetail(odmf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("出库明细表查询记录条数信息加载失败！OutputDepotManageServiceImpl.queryCountDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("出库明细表查询记录条数信息加载失败！OutputDepotManageServiceImpl.queryCountDetail()");
		}
		return count;
	}

	//计算出库明细表中的总数量和总金额
	public List summaryDetail(OutputDepotManageForm odmf) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.summaryDetail(odmf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("统计出库明细表信息加载失败！OutputDepotManageServiceImpl.summaryDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("统计出库明细表信息加载失败！OutputDepotManageServiceImpl.summaryDetail()");
		}
		return list;
	}

	//对出库记录进行结算
	public void doAccountOutput(int id) {
		
		DepotOutputManager dom = (DepotOutputManager)outputdepotDao.getObject(DepotOutputManager.class, id);
		dom.setStatus(Contants.ALREADYACCOUNT);
		try{
			outputdepotDao.updateObject(dom);
		}catch(Exception e){
			logger.error("出库信息结算失败加载失败！OutputDepotManageServiceImpl.doAccountOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("出库信息结算失败加载失败！OutputDepotManageServiceImpl.doAccountOutput()");
		}
	}

	//查询详细信息然后更新材料库
	public void selectDetailInoutput(List list) {
		
		List detailList = new ArrayList();
		List materilList = new ArrayList();
		try{
			detailList = outputdepotDao.selectDetailInoutput(list);
			double amount = 0;
			double amountMoney = 0;
			String materilId = "";
			double updateNowStock = 0;
			double updateMoney = 0;
			double updateDoStock = 0;
			if(null != detailList && detailList.size()>0){
				for(int i = 0;i<detailList.size();i++){
					Object[] obj = (Object[])detailList.get(i);
					if(null != obj[1]){
						amount = (Double)obj[1];
					}
					if(null != obj[2]){
						amountMoney = (Double)obj[2];
					}
					if(null != obj[0]){
						materilId = obj[0].toString();
						//查询要更新的材料信息
						materilList = outputdepotDao.selectMaterilList(materilId);
						Object[] dm = (Object[])materilList.get(0);
						updateNowStock = (Double)dm[0] - amount;
						updateDoStock = (Double)dm[1] - amount;
						updateMoney = (Double)dm[2] - amountMoney;
						if(!"".equals(materilId) && null != materilId){
							DepotMaterial updateDm = (DepotMaterial)outputdepotDao.getObject(DepotMaterial.class, Integer.parseInt(materilId));
							updateDm.setNowStock(updateNowStock);
							updateDm.setDoStock(updateDoStock);
							updateDm.setMoney(updateMoney);
							outputdepotDao.updateObject(updateDm);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("删除出库信息更新材料失败加载失败！OutputDepotManageServiceImpl.selectDetailInoutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除出库信息更新材料失败加载失败！OutputDepotManageServiceImpl.selectDetailInoutput()");
		}
	}

	//查询所有的出库明细信息
	public List selectAllDetail(OutputDepotManageForm odmf) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectAllDetail(odmf);
		}catch (Exception e) {
			logger.error("查询所有出库详细信息失败加载失败！OutputDepotManageServiceImpl.selectAllDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有出库详细信息失败加载失败！OutputDepotManageServiceImpl.selectAllDetail()");
		}
		return list;
	}

	//查询所有的出库信息
	public List selectAllOutput(SetMaterialsForm smf) {
		
		List list = new ArrayList();
		try{
			list = outputdepotDao.selectAllOutput(smf);
		}catch (Exception e) {
			logger.error("查询所有出库信息失败加载失败！OutputDepotManageServiceImpl.selectAllOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有出库信息失败加载失败！OutputDepotManageServiceImpl.selectAllOutput()");
		}
		return list;
	}

	//删除出库单
	@Override
	public boolean deleteOutput(OutputDepotManageForm outputForm) {
		boolean b = false;
		try{
		String delIds = outputForm.getInOutputIds();//获取要删除的入库单的id字符串，已逗号隔开
		String idArray[] = delIds.split(",");
		Integer[] ids = new Integer[idArray.length];
		List codeList = new ArrayList();
		//查询要删除的入库单的编号
		for(int i = 0;i<idArray.length;i++){
			ids[i] = Integer.parseInt(idArray[i]);
		}
		
		codeList = outputdepotDao.selectOutputCode(ids);
		//根据入库单编号处理入库单详细信息
		if(codeList != null && codeList.size()>0){
			for(int i = 0;i<codeList.size();i++){
				//处理详单
				outputForm.setCode((String)codeList.get(i));
				dealInOutput(outputForm);
			}
			//inputdepotDAO.deleteInOutList(codeList);
		}
		
		
		//删除入库单信息
		for(int i = 0;i<idArray.length;i++){
			DepotOutputManager dim = (DepotOutputManager)outputdepotDao.getObject(DepotOutputManager.class, Integer.parseInt(idArray[i]));
			//记录日志
			LogParam logParam = outputForm.getLogParam();
			logParam.setOperateType(Contants.DELETE);
			logParam.setOperateObj("出库单");
			logParam.setOperateModule(Contants.L_Depot);
			logParam.setFlag("3");
			logParam.setObject(dim);
			outputdepotDao.logDetail2(logParam);//该处需要修改，暂时不能记录日志
			
			outputdepotDao.deleteObject(dim);
		}
		b = true;
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return b;
		
	}

	@Override
	public List summary(SetMaterialsForm smf) {
		List list = new ArrayList();
		try{
			list = outputdepotDao.summary(smf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("统计出库明细表信息加载失败！OutputDepotManageServiceImpl.summaryDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("统计出库明细表信息加载失败！OutputDepotManageServiceImpl.summaryDetail()");
		}
		return list;
	}
}
