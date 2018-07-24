package com.zc13.bkmis.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ILpManageDAO;
import com.zc13.bkmis.dao.IMeterManageDAO;
import com.zc13.bkmis.form.LpForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.service.ILpManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:09:54 PM
 */
public class LpManageServiceImpl implements ILpManageService {
    
	Logger logger = Logger.getLogger(this.getClass());
	//注入ILpManageDAO
	private ILpManageDAO ilpManageDao;
	/* 注入imeterManageDao */
	private IMeterManageDAO imeterManageDao;
	//注入iSysParamManagerDAO
    private ISysParamManagerDAO iSysParamManagerDAO;
	
	public ISysParamManagerDAO getISysParamManagerDAO() {
		return iSysParamManagerDAO;
	}

	public void setISysParamManagerDAO(ISysParamManagerDAO sysParamManagerDAOImpl){
		this.iSysParamManagerDAO = sysParamManagerDAOImpl;
	}

	public ILpManageDAO getIlpManageDao() {
		return ilpManageDao;
	}

	public void setIlpManageDao(ILpManageDAO ilpManageDao) {
		this.ilpManageDao = ilpManageDao;
	}
	public IMeterManageDAO getImeterManageDao() {
		return imeterManageDao;
	}
	public void setImeterManageDao(IMeterManageDAO imeterManageDao) {
		this.imeterManageDao = imeterManageDao;
	}

	public List<ELp> queryLpByName(String lpName) throws BkmisServiceException{
		List<ELp> list = null;
		try {
			list = ilpManageDao.queryLpByName(lpName);
			
		} catch (Exception e) {
			logger.error("楼盘信息查询失败!LpManageServiceImpl.queryLpByName(String lpName)。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘信息查询失败!LpManageServiceImpl.queryLpByName(String lpName)。");
		}
		return list;
	}

	public List<ELp> getLp(LpForm lpForm) throws BkmisServiceException {
		
		List<ELp> list = null;
		try{
			list = ilpManageDao.queryLp(lpForm);
		}catch(DataAccessException e){
			logger.error("楼盘信息查询失败!ilpManageDao.queryLp()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘信息查询失败!LpManageServiceImpl.getLp()。");
		}
		return list;
	}

	public void addLp(LpForm lpForm) throws BkmisServiceException {
		try{
			/* 添加楼盘 */
			ELp elp = new ELp();
		    BeanUtils.copyProperties(elp,lpForm);
		    ilpManageDao.saveLp(elp);
			//添加系统日志
		    ilpManageDao.logDetail(lpForm.getUserName(),Contants.ADD,"楼盘",Contants.L_BUILD,"2",elp);
		    
		    
		    /** 保存楼盘的表具信息 */
		   /* 从表单里面得到楼盘名称 */
		    String lpName = lpForm.getLpName();
		    List<ELp> lplist = ilpManageDao.queryLpByName(lpName);
		    Integer lpId = 0;
		    for(ELp lp:lplist){
		    	lpId = lp.getLpId();
		    }
		    
		    /* 添加表具 
		     * 从Form中得到表具的信息拼接的字符串 */
		   String str1 = lpForm.getCode();
		   String str2 = lpForm.getType();
		  if(!"".equals(str1) && !"".equals(str2)){
			  /* 把到的字符串从＂；＂分号处开始拆分　*/
			  String[] arr1 = str1.split(";");
			  String[] arr2 = str2.split(";");
			  String code = null;
			  String type = null;
			  Integer meterId = 0;
			  for(int i=0,j=0;i<arr1.length && j<arr2.length;i++,j++){
				  /* 循环出表具信息，得到表具编号 */
				  code = arr1[i];
				  type = arr2[j];
				  /* 根据表具编号得到一个表具list */
				  List<EMeter> list = ilpManageDao.queryMeterByCodeAndName(code, type);
				  for(EMeter e:list){
					  meterId = e.getId();
				  }
				  /* 再根据表具id去得到一个表具对象 */
				  EMeter emeter = (EMeter) ilpManageDao.getObject(EMeter.class, meterId);
				  /* 把楼盘号保存到表具对象里 */
				  emeter.setLpId(lpId);
				  /* 更新表具对象 */
				  ilpManageDao.updateObject(emeter);
			  }
		  }
		}catch(Exception e){
			logger.error("楼盘信息添加失败!LpManageServiceImpl.addLp()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘信息添加失败!LpManageServiceImpl.addLp()。");
		}
	}

	public boolean checkLpName(String lpName) throws BkmisServiceException {
		boolean var = false;
		try{
			List<ELp> list = ilpManageDao.queryLpByName(lpName);
			if(list != null && list.size()>= 1){
				var = false;
			}else{
				var = true;
			}
		}catch(Exception e){
			logger.error("楼盘名称检测失败!LpManageServiceImpl.checkLpName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘名称检测失败!LpManageServiceImpl.checkLpName()。");
		}
		return var;
	}

	public ELp getELpById(Integer lpId) throws BkmisServiceException {
		
		ELp elp = new ELp();
		try {
			if (lpId == null || lpId ==0) {
				return null;
			} else {
				 elp = (ELp) ilpManageDao.getObject(ELp.class, lpId);
			}
		} catch (Exception e) {
			logger.error("获取楼盘信息失败!LpManageServiceImpl.setModifyInfo()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException("楼盘名称检测失败!LpManageServiceImpl.setModifyInfo()。");
		}
		return elp;
	}

	public void modifyLp(LpForm form)throws BkmisServiceException {
		try{
			/* 更新楼盘信息 */
			Integer lpId = form.getLpId();
			//ELp elp = (ELp) ilpManageDao.getObject(ELp.class, lpId);
			ELp elp = new ELp();
			BeanUtils.copyProperties(elp,form);

			//写入系统日志
			ilpManageDao.logDetail(form.getUserName(), Contants.MODIFY,"楼盘",Contants.L_BUILD, "1", elp);
			ilpManageDao.updateObject(elp);
		    
			/* 更新对应楼盘的表具信息 
		     * 从Form中得到新增的表具的信息拼接的字符串 */
		   String str1 = form.getCode();
		   String str2 = form.getType();
		   if (!"".equals(str1)  && !"".equals(str2)) {
				/* 把到的字符串从＂；＂分号处开始拆分 */
				String[] arr1 = str1.split(";");
				String[] arr2 = str2.split(";");
				String code = null;
				String type = null;
				Integer meterId = 0;
				for (int i = 0, j = 0; i < arr1.length && j < arr2.length; i++, j++) {
					/* 循环出表具信息，得到表具编号 */
					code = arr1[i];
					type = arr2[j];
					/* 根据表具编号得到一个表具list */
					List<EMeter> list = ilpManageDao.queryMeterByCodeAndName(code, type);
					for (EMeter e : list) {
						meterId = e.getId();
					}
					/* 再根据表具id去得到一个表具对象 */
					EMeter emeter = (EMeter) ilpManageDao.getObject(EMeter.class, meterId);
					/* 把楼盘号保存到表具对象里 */
					emeter.setLpId(lpId);
					/* 更新表具对象 */
					ilpManageDao.updateObject(emeter);
				}
		   }
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("楼盘信息修改失败!LpManageServiceImpl.modifyLp()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘信息修改失败!LpManageServiceImpl.modifyLp()。");
		}
	}

	@Override
	public void deleteLp(List<String> idList,String userName) throws BkmisServiceException {
		try{
			Iterator it = idList.iterator();
			while(it.hasNext()){
				Integer lpid = Integer.parseInt((String) it.next());
				ELp eLp = (ELp)ilpManageDao.getObject(ELp.class, lpid);

				//添加系统日志
				try {
					ilpManageDao.logDetail(userName,Contants.DELETE,"楼盘",Contants.L_BUILD,"3",eLp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ilpManageDao.deleteObject(eLp);
			}
		}catch(DataAccessException e){
			logger.error("楼盘信息删除失败!LpManageServiceImpl.deleteLp()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException("楼盘信息删除失败!LpManageServiceImpl.deleteLp()。");
		}
	}
	 /**
     * 取记录总条数
     * @param lpForm
     * @return
     */
    public int queryCounttotal(LpForm lpForm){
    	
    	return ilpManageDao.queryCounttotal(lpForm);
    }

	@Override
	public List<ELp> getLp() throws BkmisServiceException {
		
		List<ELp> list = null;
		try{
			list = ilpManageDao.queryLp();
		}catch(Exception e){
			logger.error("楼盘信息查询失败!LpManageServiceImpl.getLp()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼盘信息查询失败!LpManageServiceImpl.getLp()。");
		}
		return list;
	}
	
	@Override
	public List<SysCode> getMeterType() throws BkmisServiceException {
		List<SysCode> list = null;
		try{
			list = ilpManageDao.queryMeterType();
		}catch(Exception e){
			logger.error("表具类型获得失败!LpManageServiceImpl.getMeterType()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具类型获得失败!LpManageServiceImpl.getMeterType()。");
		}
		return list;
	}

	@Override
	public boolean checkMeter(LpForm lpForm) throws BkmisServiceException {
		boolean var = false;
		try{
			EMeter emeter = new EMeter();
			BeanUtils.copyProperties(emeter,lpForm);
			String meterName = emeter.getType();
			String meterCode = emeter.getCode();
			List<EMeter> list = (List<EMeter>) ilpManageDao.queryMeterByCodeAndName(meterCode,meterName);
			if(list != null && list.size()>= 1){
				var = false;
			}else{
				var = true;
			}
		}catch(Exception e){
			logger.error("表具名称检测失败!LpManageServiceImpl.checkBjName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具名称检测失败!LpManageServiceImpl.checkBjName()。");
		}
		return var;
	}


	@Override
	public void addMeter(LpForm lpForm) throws BkmisServiceException {
		
		try{
			EMeter emeter = new EMeter();
			BeanUtils.copyProperties(emeter,lpForm);
			
			ilpManageDao.saveObject(emeter);
			ilpManageDao.logDetail(lpForm.getUserName(),Contants.ADD,"楼盘总表具",Contants.L_BUILD,"2",emeter);
		}catch(Exception e){
			logger.error("表具添加失败!LpManageServiceImpl.addMeter()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具添加失败!LpManageServiceImpl.addMeter()。");
		}
		
	}

	@Override
	public String getMeterName(LpForm lpForm)throws BkmisServiceException{
		
		String meterName = null;
		try{
			String codeValue = lpForm.getType();
			SysCode syscode =  iSysParamManagerDAO.getCodeByValue(codeValue);
			meterName = syscode.getCodeName();
		}catch(DataAccessException e){
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterName;
	}
	
	@Override
	 public String getMeterTypeByCodeName(String meterName)throws BkmisServiceException{
		
		String meterType = null;
		try{
			SysCode syscode =  iSysParamManagerDAO.getCodeByName(meterName);
			meterType = syscode.getCodeValue();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterType;
	}
	
	@Override
	public void deleteMeter(String meterCode,String meterType) throws BkmisServiceException {
		
		try{
			imeterManageDao.deleteMeter(meterCode,meterType);
		}catch(Exception e){
			logger.error("表具信息删除失败!LpManageServiceImpl.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具信息删除失败!LpManageServiceImpl.deleteMeter()。");
		}
	}

	@Override
	public List<EMeter> getMeterByCodeAndName(String meterCode,String meterName) throws BkmisServiceException {
		List<EMeter> list = null;
		try{
			 list = ilpManageDao.queryMeterByCodeAndName(meterCode, meterName);
		}catch(Exception e){
			logger.error("根据表具编号查询表具失败!LpManageServiceImpl.getEMeterByCode()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据表具编号查询表具失败!LpManageServiceImpl.getEMeterByCode()。");
		}
		return list;
	}

	@Override
	public void deleteMeterBylpId(List<String> idList) throws BkmisServiceException{
		try{
			ilpManageDao.deleteMeterBylpId(idList);
		}catch(Exception e){
			logger.error("根据楼盘id删除表具信息失败!LpManageServiceImpl.deleteMeterBylpId()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException("根据楼盘id删除表具信息失败!LpManageServiceImpl.deleteMeterBylpId()。");
		}
	}

	@Override
	public List getEMeterByLpId(Integer lpId) throws BkmisServiceException {
		List list = null;
		try{
			 list = imeterManageDao.queryMeterByLpId(lpId);
		}catch(Exception e){
			logger.error("根据楼盘id查询表具信息失败!LpManageServiceImpl.getEMeterByLpId()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据楼盘id查询表具信息失败!LpManageServiceImpl.getEMeterByLpId()。");
		}
		return list;
	}
}
