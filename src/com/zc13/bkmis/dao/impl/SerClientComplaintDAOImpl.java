package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ISerClientComplaintDAO;
import com.zc13.bkmis.form.SerClientComplaintForm;
import com.zc13.bkmis.mapping.SerClientComplaint;
import com.zc13.util.GlobalMethod;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class SerClientComplaintDAOImpl extends BasicDAOImpl implements ISerClientComplaintDAO {

	//获取客户报修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<SerClientComplaint> getClientList(SerClientComplaintForm form,boolean isPage)
			throws DataAccessException {
		
		StringBuffer hql = new StringBuffer("from SerClientComplaint where 1 = 1");
		if(!GlobalMethod.NullToSpace(form.getBegindate()).equals("")){
			hql.append(" and complaintDate >= '"+form.getBegindate()+"'");	
		}
		if(!GlobalMethod.NullToSpace(form.getEnddate()).equals("")){
			hql.append(" and complaintDate <= '"+form.getEnddate()+"'");	
		}
		if(!GlobalMethod.NullToSpace(form.getCx_status()).equals("")){
			hql.append(" and status like '%"+form.getCx_status()+"%'");	
		}
		if(form.getLpId()!=null&&form.getLpId()!=0){
			hql.append(" and lpId ="+form.getLpId());	
		}
		hql.append(" order by id");
		List<SerClientComplaint> list = null;
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			list = (List<SerClientComplaint>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			list = this.getSession().createQuery(hql.toString()).list();
		}
		return list;
	}
}
