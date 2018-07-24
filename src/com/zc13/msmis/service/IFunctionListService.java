package com.zc13.msmis.service;

import java.util.List;

import com.zc13.bkmis.service.IBasicService;
import com.zc13.msmis.mapping.MFunction;

/**
 * 功能列维护相关
 * @author fengsen
 * Date：Nove,08, 2010
 * Time：15:38:15
 */

public interface IFunctionListService extends IBasicService{

	/**
	 * 功能列展示,这个方法给用户角色设置权限的地方使用，并不齐全。
	 * @return
	 */
	
	public List<MFunction> getFunctionList()throws Exception;
	/**
	 * 功能列展示
	 * @return
	 */
	
	public List<MFunction> getFunctionListAll()throws Exception;
	
	/**
	 * 单个功能信息
	 * @param String
	 * @return
	 */
	public MFunction getFunction(int id)throws Exception;
	
	/**
	 * 检查功能名是否可用
	 * @param functionname
	 * @param parentid
	 * @return
	 */
	public boolean checkFunction(String functionname,int parentid) throws Exception;
	
	/**
	 * 添加功能信息并修改相应信息
	 * @param direction
	 * @param functionid
	 * @param parentid
	 * @param functionname
	 * @param urlname
	 */
	public void addFunction(String functionname,int functionid,String direction,int parentid,int sequence,String urlname) throws Exception;
	
	/**
	 * 更新功能信息 
	 * @param functionid
	 * @param urlname 
	 * @param sequence
	 * @param parentid
	 * @param functionname
	 */
	public void updateFunction(String functionname,int functionid,String urlname,int parentid,int sequence) throws Exception;
	
	/**
	 * 删除功能信息
	 * @param functionid
	 */
	public void delFunction(int functionid) throws Exception;
}
