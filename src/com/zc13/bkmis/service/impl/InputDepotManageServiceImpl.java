package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IInputDepotManageDAO;
import com.zc13.bkmis.form.InputDepotManageForm;
import com.zc13.bkmis.mapping.DepotInOutputList;
import com.zc13.bkmis.mapping.DepotInputManager;
import com.zc13.bkmis.mapping.DepotMaterial;
import com.zc13.bkmis.service.IInputDepotManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
import com.zc13.util.LogParam;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:06:52 PM
 */
public class InputDepotManageServiceImpl extends BasicServiceImpl implements IInputDepotManageService {
	Logger logger = Logger.getLogger(this.getClass());
	
	/** 对ipersonnelDao 注入*/
	private IInputDepotManageDAO inputdepotDAO;

	public IInputDepotManageDAO getInputdepotDAO() {
		return inputdepotDAO;
	}

	public void setInputdepotDAO(IInputDepotManageDAO inputdepotDAO) {
		this.inputdepotDAO = inputdepotDAO;
	}
	//更据id显示选择添加的材料
	public List selectMaterialsById(String[] idArray) throws BkmisServiceException {

		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectMaterialsById(idArray);
		}catch(Exception e){
			logger.error("查询选择要添加的材料信息加载失败！InputDepotManageServiceImpl.selectMaterialsById()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询选择要添加的材料信息加载失败！InputDepotManageServiceImpl.selectMaterialsById()");
		}
		return list;
	}

	//查询显示的入库单信息
	public void selectInputList(InputDepotManageForm idmf)  throws BkmisServiceException{
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectInputList(idmf);
		}catch(Exception e){
			logger.error("查询显示材料入库信息加载失败！InputDepotManageServiceImpl.selectInputList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示材料入库信息加载失败！InputDepotManageServiceImpl.selectInputList()");
		}
		idmf.setInputList(list);
	}

	//入库单信息条数
	public int queryCounttotal(InputDepotManageForm idmf)  throws BkmisServiceException{
		
		int count = 0;
		try{
			count = inputdepotDAO.queryCounttotal(idmf);
		}catch(Exception e){
			logger.error("查询显示材料入库条数信息加载失败！InputDepotManageServiceImpl.queryCounttotal()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示材料入库条数信息加载失败！InputDepotManageServiceImpl.queryCounttotal()");
		}
		return count;
	}

	//查询供应商
	public List selectSupplier()  throws BkmisServiceException{
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectSupplier();
		}catch(Exception e){
			logger.error("查询供应商信息加载失败！InputDepotManageServiceImpl.selectSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询供应商信息加载失败！InputDepotManageServiceImpl.selectSupplier()");
		}
		return list;
	}

	//生成入库单编号
	public String GenerationNum()  throws BkmisServiceException{
		
		String num = "";
		String dateFormat = "";//存取时间
		String strsubNum = ""; //存取出库单后几位
		int subNum = 0;
		try{
			num = inputdepotDAO.GenerationNum();
			if("".equals(num) || null == num){
				dateFormat = GlobalMethod.getTime2();
				num = dateFormat + "001";
			}else{
				dateFormat = GlobalMethod.getTime2();
				strsubNum = num.substring(8,11);
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
			logger.error("查询入库单编号信息加载失败！InputDepotManageServiceImpl.GenerationNum()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询入库单编号信息加载失败！InputDepotManageServiceImpl.GenerationNum()");
		}
		return num;
	}

	//执行添加入库单信息及详细信息
	public void doAddInput(InputDepotManageForm idmf) throws BkmisServiceException {
		
		DepotInputManager dim = new DepotInputManager();
		idmf.setUpdateDate(GlobalMethod.getTime());
		idmf.setCode(this.GenerationNum());
		try {
			BeanUtils.copyProperties(dim, idmf);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		dim.setStatus(Contants.WAITACCOUNT);
		dim.setUpdateDate(GlobalMethod.dateToString(new Date()));
		inputdepotDAO.saveObject(dim);
		//处理详单
		dealInOutput(idmf);
	}

	//按id查询要修改的信息
	public List selectEditInput(int id)  throws BkmisServiceException{
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectEditInput(id);
		}catch(Exception e){
			logger.error("查询入库单信息加载失败！InputDepotManageServiceImpl.selectEditInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询入库单编号信息加载失败！InputDepotManageServiceImpl.selectEditInput()");
		}
		return list;
	}

	//查询修改的入库单的详细信息
	public List selectDetailInput(String inputCode) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectDetailInput(inputCode);
		}catch(Exception e){
			logger.error("查询入库单信息加载失败！InputDepotManageServiceImpl.selectDetailInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询入库单编号信息加载失败！InputDepotManageServiceImpl.selectDetailInput()");
		}
		return list;
	}

	//按入库单编号删除详细信息
	public void deleteInOutput(String inputCode)  throws BkmisServiceException{
		
		try{
			inputdepotDAO.deleteInOutput(inputCode);
		}catch(Exception e){
			logger.error("入库单详细信息加载失败！InputDepotManageServiceImpl.deleteInOutput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("入库单详细信息加载失败！InputDepotManageServiceImpl.deleteInOutput()");
		}
	}

	//更新入库单信息
	public void updateInput(InputDepotManageForm idmf) throws BkmisServiceException {
		
		int id = idmf.getId();
		DepotInputManager dim = (DepotInputManager)inputdepotDAO.getObject(DepotInputManager.class, id);
		DepotInputManager dim2 = new DepotInputManager();
		try {
			BeanUtils.copyProperties(dim2, idmf);
			dim2.setStatus(Contants.WAITACCOUNT);
			inputdepotDAO.logDetail(idmf.getUserName(), Contants.MODIFY, "入库管理",Contants.L_Depot, "1", dim2);//添加系统日志
			BeanUtils.copyProperties(dim, idmf);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		dim.setStatus(Contants.WAITACCOUNT);
		try{
			//处理详单
			dealInOutput(idmf);
			inputdepotDAO.saveOrUpdateObject(dim);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("更新入库单信息加载失败！InputDepotManageServiceImpl.updateInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("更新入库单信息加载失败！InputDepotManageServiceImpl.updateInput()");
		}
	}
	
	/**
	 * 对详单进行处理
	 * @param outputForm
	 */
	public void dealInOutput(InputDepotManageForm idmf){
		//获得单据原详情
		List<DepotInOutputList> inoutputList = inputdepotDAO.findObject("from DepotInOutputList where type2 = '1' and inOutputCode = '"+idmf.getCode()+"'");
		//根据原详单减少库存数量
		if(inoutputList!=null&&inoutputList.size()>0){
			for(int i = 0;i<inoutputList.size();i++){
				//获得库存对应材料
				DepotMaterial material = (DepotMaterial)inputdepotDAO.getObject(DepotMaterial.class, inoutputList.get(i).getMaterialId());
				//更新库存对应材料数量
				material.setNowStock(material.getNowStock()-inoutputList.get(i).getAmount());
				material.setDoStock(material.getDoStock()-inoutputList.get(i).getAmount());
				//material.setMoney((float)(Double.parseDouble(material.getUnitPrice())*(material.getNowStock())));
				double a = inoutputList.get(i).getUnitPrice()*inoutputList.get(i).getAmount();
				//设置库存金额
				material.setMoney((double)(material.getMoney()-a));
				//设置库存单价
				if(material.getNowStock()>0.001){
					material.setUnitPrice(material.getMoney()/material.getNowStock());
				}
				inputdepotDAO.updateObject(material);
			}
		}
		//删除单据原详细
		inputdepotDAO.deleteInOutput(idmf.getCode());
		
		//增加表单中的新详情
		String[] materialCode = idmf.getMaterialCode();
		double[] amount = idmf.getAmount();
		double[] unitPrice = idmf.getUnitPrice();
		double[] amountMoney = idmf.getAmountMoney();
		String[] inoutputCode = idmf.getInOutputCode();
		int[] ids = idmf.getIds();
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
			depotInOut[j].setType2(Contants.INPUTDEPOT);
			depotInOut[j].setDate(idmf.getDate());
			inputdepotDAO.saveObject(depotInOut[j]);
			//获得库存对应材料
			DepotMaterial material = (DepotMaterial)inputdepotDAO.getObject(DepotMaterial.class, ids[j]);
			//更新库存对应材料数量
			material.setNowStock(material.getNowStock()+amount[j]);
			material.setDoStock(material.getDoStock()+amount[j]);
			//设置库存金额
			material.setMoney((double)(material.getMoney()+unitPrice[j]*amount[j]));
			//设置库存单价
			if(material.getNowStock()>0.001){
				material.setUnitPrice(material.getMoney()/material.getNowStock());
			}
			//material.setLowerLimit(material.getLowerLimit());
			//material.setUpperLimit(material.getUpperLimit());
			inputdepotDAO.updateObject(material);
		}
	}

	//查询出要删除入库单编号
	public List selectIutputCode(Integer[] ids)  throws BkmisServiceException{
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectIutputCode(ids);
		}catch(Exception e){
			logger.error("查询入库单编号信息加载失败！InputDepotManageServiceImpl.selectIutputCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询入库单编号信息加载失败！InputDepotManageServiceImpl.selectIutputCode()");
		}
		return list;
	}

	//删除入库单时删除入库单的详细信息
	public void deleteInOutList(List list)  throws BkmisServiceException{
		
		try{
			inputdepotDAO.deleteInOutList(list);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除入库单时删除其详细信息加载失败！InputDepotManageServiceImpl.deleteInOutList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除入库单时删除其详细信息加载失败！InputDepotManageServiceImpl.deleteInOutList()");
		}
	}

	//删除入库单信息
	public void deleteInput(int id)  throws BkmisServiceException{
		
		DepotInputManager dim = (DepotInputManager)inputdepotDAO.getObject(DepotInputManager.class, id);
		try{
			inputdepotDAO.deleteObject(dim);
		}catch(Exception e){
			logger.error("删除入库单详细信息加载失败！InputDepotManageServiceImpl.deleteInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除入库单详细信息加载失败！InputDepotManageServiceImpl.deleteInput()");
		}
	}
	//执行入库统计记录的核算
	public void doAccountInput(int id) throws BkmisServiceException {
		
		DepotInputManager dim = (DepotInputManager)inputdepotDAO.getObject(DepotInputManager.class, id);
		dim.setStatus(Contants.ALREADYACCOUNT);
		try{
			inputdepotDAO.updateObject(dim);
		}catch(Exception e){
			logger.error("结算入库单信息记录加载失败！InputDepotManageServiceImpl.doAccountInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("结算入库单信息记录加载失败！InputDepotManageServiceImpl.doAccountInput()");
		}
	}
	/**入库表的详细信息**/
	//显示入库表的详细信息
	public void selectInputDetail(InputDepotManageForm idmf)  throws BkmisServiceException{
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectInputDetail(idmf);
		}catch(Exception e){
			logger.error("查询显示入库单详细信息加载失败！InputDepotManageServiceImpl.selectInputDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示入库单详细信息加载失败！InputDepotManageServiceImpl.selectInputDetail()");
		}
		idmf.setInputDatilList(list);
	}

	//查询入库详细记录的条数
	public int queryCountInDetail(InputDepotManageForm idmf) throws BkmisServiceException {

		int count = 0;
		try{
			count = inputdepotDAO.queryCountInDetail(idmf);
		}catch(Exception e){
			logger.error("查询显示入库单详细信息记录条数加载失败！InputDepotManageServiceImpl.queryCountInDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示入库单详细信息记录条数加载失败！InputDepotManageServiceImpl.queryCountInDetail()");
		}
		return count;
	}

	//查询入库的入库总数量和入库总金额
	public List summaryInDetail(InputDepotManageForm idmf) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.summaryInDetail(idmf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("汇总入库单详细信息加载失败！InputDepotManageServiceImpl.summaryInDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("汇总入库单详细信息加载失败！InputDepotManageServiceImpl.summaryInDetail()");
		}
		return list;
	}

	//按入库单编号查询详细信息,更新材料信息
	@SuppressWarnings("unchecked")
	public List selectDetailList(List list) throws BkmisServiceException {
		
		List detailList = new ArrayList();
		List materilList = new ArrayList();
		try{
			detailList = inputdepotDAO.selectDetailList(list);
			double amount = 0;
			double amountMoney = 0;
			double unitPrice = 0;
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
					if(null != obj[3]){
						unitPrice = (Double)obj[3];
					}
					if(null != obj[0]){
						materilId = obj[0].toString();
						//查询要更新的材料信息
						materilList = inputdepotDAO.selectMaterilList(materilId);
						Object[] dm = (Object[])materilList.get(0);
						updateNowStock = (Double)dm[0] + amount;
						updateDoStock = (Double)dm[1] + amount;
						updateMoney = (Double)dm[2] + amountMoney;
						if(!"".equals(materilId) && null != materilId){
							DepotMaterial updateDm = (DepotMaterial)inputdepotDAO.getObject(DepotMaterial.class, Integer.parseInt(materilId));
							updateDm.setNowStock(updateNowStock);
							updateDm.setDoStock(updateDoStock);
							updateDm.setMoney(updateMoney);
							updateDm.setUnitPrice(unitPrice);
							inputdepotDAO.updateObject(updateDm);
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("入库单详细信息加载失败！InputDepotManageServiceImpl.selectDetailList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("入库单详细信息加载失败！InputDepotManageServiceImpl.selectDetailList()");
		}
		return null;
	}

	//查询所有的入库明细信息
	public List selectAllDetail(InputDepotManageForm idmf) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectAllDetail(idmf);
		}catch (Exception e) {
			logger.error("查询所有入库单详细信息加载失败！InputDepotManageServiceImpl.selectAllDetail()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有入库单详细信息加载失败！InputDepotManageServiceImpl.selectAllDetail()");
		}
		return list;
	}

	//查询所有的入库信息
	public List selectAllInput(InputDepotManageForm idmf) throws BkmisServiceException {
		
		List list = new ArrayList();
		try{
			list = inputdepotDAO.selectAllInput(idmf);
		}catch (Exception e) {
			logger.error("查询所有入库单信息加载失败！InputDepotManageServiceImpl.selectAllInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有入库单信息加载失败！InputDepotManageServiceImpl.selectAllInput()");
		}
		return list;
	}

	@Override
	public boolean ifDate() throws BkmisServiceException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		boolean b = false;
		try{
			list = inputdepotDAO.findObject("from DepotAdjustAccounts where makeDate='"+GlobalMethod.getTime()+"'");
			if(list!=null && list.size()>0){
				b=true;
			}
		}catch (Exception e) {
			logger.error("查询所有入库单信息加载失败！InputDepotManageServiceImpl.selectAllInput()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询所有入库单信息加载失败！InputDepotManageServiceImpl.selectAllInput()");
		}
		return b;
	}

	//删除入库单
	public boolean deleteInput(InputDepotManageForm idmf)
			throws BkmisServiceException {
		boolean b = false;
		try{
		String delIds = idmf.getInOutputIds();//获取要删除的入库单的id字符串，已逗号隔开
		String idArray[] = delIds.split(",");
		Integer[] ids = new Integer[idArray.length];
		List codeList = new ArrayList();
		//查询要删除的入库单的编号
		for(int i = 0;i<idArray.length;i++){
			ids[i] = Integer.parseInt(idArray[i]);
		}
		
		codeList = inputdepotDAO.selectIutputCode(ids);
		//根据入库单编号处理入库单详细信息
		if(codeList != null && codeList.size()>0){
			for(int i = 0;i<codeList.size();i++){
				//处理详单
				idmf.setCode((String)codeList.get(i));
				dealInOutput(idmf);
			}
			//inputdepotDAO.deleteInOutList(codeList);
		}
		
		
		//删除入库单信息
		for(int i = 0;i<idArray.length;i++){
			DepotInputManager dim = (DepotInputManager)inputdepotDAO.getObject(DepotInputManager.class, Integer.parseInt(idArray[i]));
			//记录日志
			LogParam logParam = idmf.getLogParam();
			logParam.setOperateType(Contants.DELETE);
			logParam.setOperateObj("入库单");
			logParam.setOperateModule(Contants.L_Depot);
			logParam.setFlag("3");
			logParam.setObject(dim);
			inputdepotDAO.logDetail2(logParam);//该处需要修改，暂时不能记录日志
			
			inputdepotDAO.deleteObject(dim);
		}
		b = true;
		}catch(Exception e){
			throw new BkmisServiceException();
		}
		return b;
	}

	@Override
	public List summary(InputDepotManageForm idmf) throws BkmisServiceException {
		List list = new ArrayList();
		try{
			list = inputdepotDAO.summary(idmf);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询显示材料入库信息加载失败！InputDepotManageServiceImpl.selectInputList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示材料入库信息加载失败！InputDepotManageServiceImpl.selectInputList()");
		}
		return list;
	}

}
