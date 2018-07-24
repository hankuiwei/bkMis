package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.SerClientComplaintForm;
import com.zc13.bkmis.mapping.SerClientComplaint;
import com.zc13.exception.BkmisServiceException;
/***
 * @author qinyantao
 * Date：Dec 15, 2010
 * Time：11:35:50 AM
 */
public interface ISerClientComplaintService extends IBasicService {
	
	/**
	 * 查询客户投诉
	 * @param form
	 * @param isPage 是否分页
	 * @return
	 */
	public List<SerClientComplaint> getClientList(SerClientComplaintForm form,boolean isPage) throws BkmisServiceException;
	
	public void save(SerClientComplaintForm form)  throws BkmisServiceException;
	
	public void delComplaint(String id,String userName)  throws BkmisServiceException;
	
	public void editComplaint(SerClientComplaintForm form)  throws BkmisServiceException;
	
	public void dealComplaint(SerClientComplaintForm form)  throws BkmisServiceException;
	
	public SerClientComplaint getComplaint(String id)  throws BkmisServiceException;
	
}
