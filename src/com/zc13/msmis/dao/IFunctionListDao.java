package com.zc13.msmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.msmis.mapping.MFunction;

/**
 * 功能列维护相关
 * @author fengsen
 * Date：Nove,08, 2010
 * Time：15:38:15
 */

public interface IFunctionListDao extends BasicDAO {

	/**
	 * 查询所有的URL
	 * @return
	 */
	
	public List<MFunction> getFunctions() throws DataAccessException;
	
	/**
	 * 查询单个的url
	 * @param id
	 * @return 
	 */
	
	public MFunction getFunction(int id) throws DataAccessException; 
	
	/**
	 * 查询功能名称是否存在
	 * @param functionname
	 * @param parentid
	 * @return
	 */
	public List<MFunction> checkFunction(String functionname,int parentid) throws DataAccessException;
	
	/**
	 * 根据父节点查询功能名
	 * @param parentid
	 * @return
	 */
	public List<MFunction> getSonFunctionList(int parentid) throws DataAccessException;
	
	/**
	 * 更新功能信息
	 * @param functionid
	 * @param urlname
	 * @param sequence
	 * @param functionname
	 * @param parentid
	 * @return
	 */
	public void updateFunction(String functionname,int functionid,String urlname,int parentid,int sequence) throws DataAccessException;
	
	/**
	 * 添加功能信息
	 * @param urlname
	 * @param sequence
	 * @param parentid
	 */
	public void saveFunction(String functionname,String urlname,int sequence,int parentid) throws DataAccessException;
	
	/**
	 * 删除功能信息
	 * @param functionid
	 */
	public void delFunction(int functionid) throws DataAccessException;
	
	/**
	 * 取得最大的序列值
	 * @param parentid
	 * @param return
	 */
	public int getSequence(int parentid) throws DataAccessException;
}
