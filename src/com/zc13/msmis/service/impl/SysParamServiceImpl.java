package com.zc13.msmis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.form.SysParamForm;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.mapping.SysCodeType;
import com.zc13.msmis.service.ISysParamManagerService;
import com.zc13.util.CloneObject;
import com.zc13.util.Contants;

public class SysParamServiceImpl implements ISysParamManagerService {
	
	Logger logger = Logger.getLogger(this.getClass());	
	
	private ISysParamManagerDAO ISysParamManagerDAOImpl;
	
	public ISysParamManagerDAO getISysParamManagerDAOImpl() {
		return ISysParamManagerDAOImpl;
	}

	public void setISysParamManagerDAOImpl(ISysParamManagerDAO sysParamManagerDAOImpl){
		this.ISysParamManagerDAOImpl = sysParamManagerDAOImpl;
	}

	@Override
	public List getSysParamList() throws BkmisServiceException {
		
		return ISysParamManagerDAOImpl.getTest();
	}
	
	@Override
	public List getCodeList() throws BkmisServiceException {
		SysCode code = new SysCode();
		code.setCodeType("department");
		List list = null;
		try {
			list = ISysParamManagerDAOImpl.getSysCode(code);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
	}

	@Override
	public boolean checkCodeName(String codeName) throws BkmisServiceException {
		
		SysCodeType code = new SysCodeType();
		try{
		code = ISysParamManagerDAOImpl.getSysCodeType(codeName);
		}catch (Exception e) {
			logger.error("系统参数验证失败！SysParamServiceImpl.checkCodeName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("系统参数验证失败！SysParamServiceImpl.checkCodeName()");
		}	
		Boolean isExist = true;
		if(code != null){
			isExist = false;
		}
		return isExist;
	}
	
	@Override
	public SysCode saveCodeById(int codeId, String codeName) throws BkmisServiceException{
		
		SysCode code = new SysCode();
		try {
			try {
				code = (SysCode)ISysParamManagerDAOImpl.getObject(SysCode.class, codeId);
				code.setCodeName(codeName);
				ISysParamManagerDAOImpl.saveOrUpdateObject(code);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (DataAccessException e) {
			logger.error("系统参数保存失败！ISysParamManagerDAO.getObject()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("系统参数保存失败！ISysParamManagerDAO.getObject()");
		}
		return null;
	}

	@Override
	public Map chooseType(String typeValue) throws BkmisServiceException {
		Map map = new HashMap();
		List<SysParamForm> list = null;
		List<SysCodeType> list2 = null;
		try{
		if(typeValue == null||typeValue.equals("")){
			list =  ISysParamManagerDAOImpl.getSysParam();
		}else{
			list  = ISysParamManagerDAOImpl.getChoose(typeValue);		
		}
			list2 = ISysParamManagerDAOImpl.getObjects(SysCodeType.class);
		}catch (Exception e) {
			logger.error("系统参数选择显示失败！SysParamServiceImpl.chooseType()。详细信息："+e.getMessage());
			throw new BkmisServiceException("系统参数选择显示失败！SysParamServiceImpl.chooseType()");
		}
		map.put("list", list);
		map.put("list2", list2);
		return map;
	}

	@Override
	public void delCode(String codeId,String userName) throws BkmisServiceException {
		try{	
				SysCode code = new SysCode();
				code.setCodeId(Integer.parseInt(codeId));
				try {
					ISysParamManagerDAOImpl.logDetail(userName,Contants.DELETE, "系统参数",Contants.L_SYSTEM, "3", code);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("系统参数删除失败！SysParamServiceImpl.delCode()。详细信息："+e.getMessage());
					throw new BkmisServiceException("系统参数删除失败！SysParamServiceImpl.delCode()");
				}
				ISysParamManagerDAOImpl.deleteObject(code);
				
		}catch (Exception e) {
//			logger.error("系统参数删除失败！SysParamServiceImpl.delCode()。详细信息："+e.getMessage());
//			throw new BkmisServiceException("系统参数删除失败！SysParamServiceImpl.delCode()");
			e.printStackTrace();
		}
	}

	@Override
	public Map goAdd(int id,String table) throws BkmisServiceException {
		
		Map map = new HashMap();
	    try {
			if (table.trim().equals("sys_code")) {
				SysCode code;
				code = (SysCode)ISysParamManagerDAOImpl.getObject(SysCode.class, id);
				map.put("code", code);
				map.put("forWard", "code");
			} else {
				SysCodeType codeType = (SysCodeType)ISysParamManagerDAOImpl.getObject(SysCodeType.class, id);

				map.put("codeType", codeType);
				map.put("forWard", "codeType");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}

	@Override
	public SysCode insertCode(String codeType, String codeName,String userName,String codeValue) throws BkmisServiceException {
		
		SysCode code = new SysCode();
		code.setCodeName(codeName);
		code.setCodeType(codeType);
		code.setUpdateTime(new Date());
		code.setCodeValue(codeValue);
		
		String operateContent = "在SysCode表内为代码类型:" + codeType + "加入一个代码名称:" + codeName;
		String operateType = "系统参数模块";
		
		try{
			ISysParamManagerDAOImpl.saveObject(code);
			try {
				ISysParamManagerDAOImpl.logDetail(userName, Contants.ADD, "系统参数", Contants.L_SYSTEM, "2", code);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			logger.error("系统参数保存失败！SysParamServiceImpl.insertCode()。详细信息："+e.getMessage());
			throw new BkmisServiceException("系统参数保存失败！SysParamServiceImpl.insertCode()");
		}
		return code;
	}

	public boolean checkCodeNameTwo(String codeName)
			throws BkmisServiceException {
		
		SysCode code = null;
		try{
			code = ISysParamManagerDAOImpl.getCodeByName(codeName);
		}catch (Exception e) {
			logger.error("系统参数验证失败！SysParamServiceImpl.checkCodeNameTwo()。详细信息："+e.getMessage());
			throw new BkmisServiceException("系统参数验证失败！SysParamServiceImpl.checkCodeNameTwo()");
		}	
		Boolean isExist = false;
		if(code != null){
			isExist = true;
		}
		return isExist;
	}
	
	@Override
	public List getTest(){
		
		return ISysParamManagerDAOImpl.getTest();
	}

	public boolean checkValue(String value) throws BkmisServiceException {
		SysCode code = null;
		SysCodeType codeType = null;
		
		try{
			code = ISysParamManagerDAOImpl.getCodeByValue(value);
		}catch (Exception e) {
			logger.error("系统参数验证失败！SysParamServiceImpl.checkCodeNameTwo()。详细信息："+e.getMessage());
			throw new BkmisServiceException("系统参数验证失败！SysParamServiceImpl.checkCodeNameTwo()");
		}	
		
		Boolean isExist = false;
		
		if(code != null || codeType != null){
			
			isExist = true;
		}
		return isExist;
	}

	@Override
	public void updateCode(int codeId, String codeName, String codeValue,
			String userName) throws BkmisServiceException {
		
		SysCode code = null;
		try {
			code = (SysCode)ISysParamManagerDAOImpl.getObject(SysCode.class, codeId);
			SysCode tempCode = null;
			tempCode = (SysCode)CloneObject.copy(code);
			tempCode.setCodeName(codeName);
			tempCode.setCodeValue(codeValue);
			try {
				ISysParamManagerDAOImpl.logDetail(userName, Contants.MODIFY, "系统参数", Contants.L_SYSTEM, "1", tempCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			code.setCodeName(codeName);
			code.setCodeValue(codeValue);
			ISysParamManagerDAOImpl.saveOrUpdateObject(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean getCodeByName(String code) throws BkmisServiceException {
		if(ISysParamManagerDAOImpl.getCodeByName(code)!=null){
			return true;
		}
		return false;
	}

	@Override
	public void updateCodeType(int codeId, String codeName, String codeValue,
			int userId,String userName) throws BkmisServiceException {
		SysCodeType code = null;
		try {
			code = (SysCodeType)ISysParamManagerDAOImpl.getObject(SysCodeType.class, codeId);
			SysCodeType tempCode = null;
			tempCode = (SysCodeType)CloneObject.copy(code);
			tempCode.setCodeTypeName(codeName);
			tempCode.setCodeTypeValue(codeValue);
			ISysParamManagerDAOImpl.logDetail(userName, Contants.MODIFY, "系统参数类型", Contants.L_SYSTEM, "1", tempCode);
			
			code.setCodeTypeName(codeName);
			code.setCodeTypeValue(codeValue);
			ISysParamManagerDAOImpl.saveOrUpdateObject(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean checkCodeTypeValue(String codeValue) throws BkmisServiceException {
		SysCodeType codeType= ISysParamManagerDAOImpl.getSysCodeType1(codeValue);
		if(codeType!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	/** 
	 *  @author houxj
	 * 获得系统参数表里id最小的 */
	public Integer getSysCodeTypeByMinId() throws BkmisServiceException {
		
		List list = null;
		Integer codeTypeId = null;
		try{
			list = ISysParamManagerDAOImpl.getSysCodeTypeByMinId();
			codeTypeId = (Integer)list.get(0);
		}catch(DataAccessException e){
			logger.error("系统参数表里的最小id失败!ISysParamManagerDAOImpl.getSysCodeTypeByMinId()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("系统参数表里的最小id失败!ISysParamManagerDAOImpl.getSysCodeTypeByMinId()。");
		}
		return codeTypeId;
	}

	@Override
	public Integer getParentIdByViewId(Integer parentId) {
		return ISysParamManagerDAOImpl.getParentIdByViewId(parentId);
	}

	@Override
	public List<SysCode> getSysCode(SysCode sysCode) throws BkmisServiceException {
		List<SysCode> list = null;
		try {
			list = ISysParamManagerDAOImpl.getSysCode(sysCode);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException("获取系统参数代码失败！");
		}
		return list;
	}

}
