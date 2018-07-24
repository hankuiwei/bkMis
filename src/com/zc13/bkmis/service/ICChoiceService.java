/**
 * Administrator
 */
package com.zc13.bkmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zc13.bkmis.form.CChoiceForm;
import com.zc13.bkmis.mapping.CCoststandard;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.util.DTree;

/**
 * @author zhaosg
 * Date：Dec 7, 2010
 * Time：2:44:57 PM
 */
public interface ICChoiceService extends IBasicService {
	
	public List<DTree> getTreeList(CChoiceForm choiceForm) throws Exception;
	
	public List getList(CChoiceForm form) throws Exception;
	
	public CCoststandard getCStandard(CChoiceForm form) throws Exception;
	
	public List<EBuilds> getEBuilds(CChoiceForm form) throws Exception;
	
	public void save(CChoiceForm form) throws Exception;
	
	/**根据id获取特定的 收费标准
	 * @author gd
	 * @time 2018年3月3日13:07:58
	*/
	CCoststandard getCCoststandardById(Integer id ) throws Exception ;
	
	public void saveBill(CChoiceForm choiceForm,HttpServletRequest request) throws Exception;
	
	public List<DTree> getClientList(CChoiceForm formbean) throws Exception;
}
