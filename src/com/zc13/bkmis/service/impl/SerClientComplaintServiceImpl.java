package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ISerClientComplaintDAO;
import com.zc13.bkmis.form.SerClientComplaintForm;
import com.zc13.bkmis.mapping.SerClientComplaint;
import com.zc13.bkmis.service.ISerClientComplaintService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.CloneObject;
import com.zc13.util.Contants;
/***
 * @author qinyantao
 * Date：Dec 15, 2010
 * Time：11:35:50 AM
 */
public class SerClientComplaintServiceImpl extends BasicServiceImpl implements
		ISerClientComplaintService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private ISerClientComplaintDAO iSerClientComplaintDAO;

	//得到客户投诉列表
	@Override
	public List<SerClientComplaint> getClientList(SerClientComplaintForm form,boolean isPage) throws BkmisServiceException {
		List<SerClientComplaint> list = null;
		int totalcount = 0;
		try {
			list = iSerClientComplaintDAO.getClientList(form,isPage);
			if(isPage){
				List list2 = iSerClientComplaintDAO.getClientList(form,false);
				if(list2!=null){
					totalcount = list2.size();
				}
				form.setTotalcount(totalcount);
			}
			form.setList(list);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
	}
	

	public ISerClientComplaintDAO getISerClientComplaintDAO() {
		return iSerClientComplaintDAO;
	}

	public void setISerClientComplaintDAO(
			ISerClientComplaintDAO serClientComplaintDAO) {
		iSerClientComplaintDAO = serClientComplaintDAO;
	}

	//保存客户投诉
	@Override
	public void save(SerClientComplaintForm form) throws BkmisServiceException {
		
		form.setStatus("未处理");
		SerClientComplaint obj = new SerClientComplaint();
		try {
			BeanUtils.copyProperties(obj, form);
			iSerClientComplaintDAO.saveObject(obj);
			iSerClientComplaintDAO.updateTask("complaint",1);
			//添加系统日志
			iSerClientComplaintDAO.logDetail(form.getUserName(),Contants.ADD,"客户投诉",Contants.L_SERVICE,"2",obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BkmisServiceException("保存客户投诉失败！");
		}
	}

	//删除客户投诉
	@Override
	public void delComplaint(String id,String userName) throws BkmisServiceException {
		
		SerClientComplaint obj = (SerClientComplaint)iSerClientComplaintDAO.getObject(SerClientComplaint.class, Integer.parseInt(id));

		try {
			//添加日志
			iSerClientComplaintDAO.logDetail(userName,Contants.DELETE,"客户投诉",Contants.L_SERVICE,"3",obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iSerClientComplaintDAO.deleteObject(obj);
	}

	@Override
	public SerClientComplaint getComplaint(String id) throws BkmisServiceException {
		
		return (SerClientComplaint)iSerClientComplaintDAO.getObject(SerClientComplaint.class, Integer.parseInt(id));
	}

	//编辑客户投诉
	@Override
	public void editComplaint(SerClientComplaintForm form)
			throws BkmisServiceException {
		
		SerClientComplaint obj = (SerClientComplaint)iSerClientComplaintDAO.getObject(SerClientComplaint.class,form.getId());
		SerClientComplaint obj2 = new 	SerClientComplaint();
		try {
			obj2 = (SerClientComplaint)CloneObject.copy(obj);
			obj2.setRoomCode(form.getRoomCode());
			obj2.setName(form.getName());
			obj2.setPhone(form.getPhone());
			obj2.setType(form.getType());
			obj2.setComplaintContent(form.getComplaintContent());
			obj2.setComplaintDate(form.getComplaintDate());
			obj2.setClerk(form.getClerk());

			//写入系统日志
			iSerClientComplaintDAO.logDetail(form.getUserName(), Contants.MODIFY,"客户投诉",Contants.L_SERVICE, "1", obj2);
			
			obj.setRoomCode(form.getRoomCode());
			obj.setName(form.getName());
			obj.setPhone(form.getPhone());
			obj.setType(form.getType());
			obj.setComplaintContent(form.getComplaintContent());
			obj.setComplaintDate(form.getComplaintDate());
			obj.setClerk(form.getClerk());

			iSerClientComplaintDAO.updateObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//根据投诉id处理客户投诉
	@Override
	public void dealComplaint(SerClientComplaintForm form)throws BkmisServiceException {
		SerClientComplaint obj = new SerClientComplaint();
		try {
			form.setType(obj.getType());
			BeanUtils.copyProperties(obj, form);
			iSerClientComplaintDAO.logDetail(form.getUserName(),Contants.DOCOMPLAIN,"客户投诉",Contants.L_SERVICE,"1",obj);
			iSerClientComplaintDAO.updateObject(obj);
			if("已处理".equals(form.getStatus())){
				iSerClientComplaintDAO.updateTask("complaint",-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
