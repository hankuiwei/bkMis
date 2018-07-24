package com.zc13.msmis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.service.impl.BasicServiceImpl;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.IFunctionListDao;
import com.zc13.msmis.mapping.MFunction;
import com.zc13.msmis.service.IFunctionListService;

public class FunctionListServiceImpl extends BasicServiceImpl implements IFunctionListService {

	Logger logger = Logger.getLogger(this.getClass());	
	
	private IFunctionListDao functionListDaoImpl;
	
	
	public IFunctionListDao getFunctionListDaoImpl() {
		return functionListDaoImpl;
	}

	public void setFunctionListDaoImpl(IFunctionListDao functionListDaoImpl) {
		this.functionListDaoImpl = functionListDaoImpl;
	}

	@Override
	public List<MFunction> getFunctionList() throws Exception {
		List functionList = new ArrayList();
		try{
			functionList = functionListDaoImpl.getFunctions();
		}catch (Exception e) {
			logger.error("功能列表信息加载失败！FunctionListServiceImpl.getFunctionList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能列表信息加载失败！FunctionListServiceImpl.getFunctionList()");
		}
		
		return functionList;
	}
	@Override
	public List<MFunction> getFunctionListAll() throws Exception {
		List functionList = new ArrayList();
		try{
			functionList = functionListDaoImpl.findObject("from MFunction as f order by f.sequence");
		}catch (Exception e) {
			logger.error("功能列表信息加载失败！FunctionListServiceImpl.getFunctionList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能列表信息加载失败！FunctionListServiceImpl.getFunctionList()");
		}
		
		return functionList;
	}
	@Override
	public MFunction getFunction(int id) throws Exception {
		MFunction function = null;
		
		try{
			function = functionListDaoImpl.getFunction(id);
		}catch (Exception e) {
			logger.error("功能信息加载失败！FunctionListServiceImpl.getFunction()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能信息加载失败！FunctionListServiceImpl.getFunction()");
		}
		
		return function;
	}

	@Override
	public boolean checkFunction(String functionname,int parentid) throws Exception {
		boolean isExist = false;
		try{
			List< MFunction> list = functionListDaoImpl.checkFunction(functionname, parentid);
			if(list.size()!=0){
				isExist = true;
			}
		}catch (Exception e) {
			logger.error("功能信息加载失败！FunctionListServiceImpl.checkFunction()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能信息加载失败！FunctionListServiceImpl.checkFunction()");
		}
		return isExist;
	}

	@Override
	public void addFunction(String functionname,int functionid,String direction, int parentid,
			int sequence, String urlname) throws Exception {
	
		List<MFunction> functions = new ArrayList<MFunction>();
		Date updatetime = new Date();
		try{
			//向前添加兄弟节点
			if(direction.equals("1")){
				functions = functionListDaoImpl.getSonFunctionList(parentid);
				for(MFunction f:functions){
					if(sequence <= f.getSequence()){
						f.setSequence(f.getSequence() + 1);
						f.setUpdatetime(updatetime);
						functionListDaoImpl.updateObject(f);
					}
				}
				functionListDaoImpl.saveFunction(functionname,urlname, sequence, parentid);
			}else
				//向后添加兄弟节点
				if(direction.equals("2")){
					functions = functionListDaoImpl.getSonFunctionList(parentid);
					for(MFunction f:functions){
						if(sequence < f.getSequence()){
							f.setSequence(f.getSequence() + 1);
							f.setUpdatetime(updatetime);
							functionListDaoImpl.updateObject(f);						}
					}
					functionListDaoImpl.saveFunction(functionname,urlname, sequence+1, parentid);
				}
				//添加子节点
				else{
					sequence = (Object)functionListDaoImpl.getSequence(functionid) == null ? 0 : functionListDaoImpl.getSequence(functionid) + 1;
					System.out.println("urlname:"+urlname);
					System.out.println("sequence:"+sequence);
					functionListDaoImpl.saveFunction(functionname,urlname, sequence, functionid);
					System.out.println("functionid:"+functionid);
			}
			
		}catch (Exception e) {
			logger.error("功能信息添加失败！FunctionListServiceImpl.addFunction()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能信息添加失败！FunctionListServiceImpl.addFunction()");
		}		
	}

	@Override
	public void updateFunction(String functionname,int functionid, String urlname,int parentid,int sequence)
			throws Exception {
		try{
			functionListDaoImpl.updateFunction(functionname,functionid, urlname,parentid, sequence);
		}catch (Exception e) {
			logger.error("功能信息更新失败！FunctionListServiceImpl.updateFunction()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能信息更新失败！FunctionListServiceImpl.updateFunction()");
		}		
	}

	@Override
	public void delFunction(int functionid) throws Exception {
		try{
			functionListDaoImpl.delFunction(functionid);
		}catch (Exception e) {
			logger.error("功能信息删除失败！FunctionListServiceImpl.delFunction()。详细信息："+e.getMessage());
			throw new BkmisServiceException("功能信息删除失败！FunctionListServiceImpl.delFunction()");
		}		
	}
	
	
}
