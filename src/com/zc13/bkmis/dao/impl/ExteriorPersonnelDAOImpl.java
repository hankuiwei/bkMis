package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.zc13.bkmis.dao.IExteriorPersonnelDAO;
import com.zc13.bkmis.form.ExteriorPerForm;
import com.zc13.bkmis.mapping.HrExteriorPersonnel;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author Administrator
 * Date：Dec 1, 2010
 * Time：3:21:47 PM
 */
public class ExteriorPersonnelDAOImpl extends BasicDAOImpl implements IExteriorPersonnelDAO {

	//对工作单位下拉列表的内容的获取
	@SuppressWarnings("unchecked")
	public List<SysCode> selectWorkPlace() {
		
		String hql = "select s from SysCode as s where codeType=:codeType";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("codeType", "workplace");
		List workList = query.list();
		return workList;
	}

	//显示留学人员信息
	public List selectExteriorPer(ExteriorPerForm exteriorperForm) {
		String name = GlobalMethod.NullToSpace(exteriorperForm.getName());
		String currentCountry = GlobalMethod.NullToSpace(exteriorperForm.getCurrentCountry());
		String company = GlobalMethod.NullToSpace(exteriorperForm.getCompany());
		String foreignDegree = GlobalMethod.NullToSpace(exteriorperForm.getForeignDegree());
		String lpId = GlobalMethod.NullToSpace(String.valueOf(exteriorperForm.getLpId()));
		String hql = "from HrExteriorPersonnel where 1=1";
		if(!"".equals(name) && name != null){
			hql += " and name like '%"+name+"%'";
		}
		if(!"".equals(currentCountry) && currentCountry != null){
			hql += " and currentCountry like '%"+currentCountry+"%'";
		}
		if(!"".equals(company) && company != null){
			hql += " and company like '%"+company+"%'";
		}
		if(!"".equals(lpId) && lpId != null){
			hql += " and lpId = " + lpId;
		}
		if(!"".equals(foreignDegree) && foreignDegree != null){
			hql += " and foreignDegree like '%"+foreignDegree+"%'";
		}
		hql += " order by id desc";
		Query query = this.getSession().createQuery(hql);
		
		int pagesize = Integer.parseInt(GlobalMethod.NullToParam(exteriorperForm.getPagesize(),"10"));
		//当前是从第几条开始
		int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(exteriorperForm.getCurrentpage(),"1"))-1);
		List exteriorperList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		return exteriorperList;
	}

	//添加留学人员信息
	public void addExteriorPer(HrExteriorPersonnel ep) {
		
		super.saveObject(ep);
	}

	//按照id去查询留学信息
	public List selectExteriorById(int id) {

		String hql = "from HrExteriorPersonnel where id=:id";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("id",id);
		List exteriorList = query.list();
		return exteriorList;
	}

	//留学人员信息的修改
	public void updateExterior(HrExteriorPersonnel ep) {
		
		super.updateObject(ep);
	}

	//按id删除员工信息
	public void delExterior(int id) {
		HrExteriorPersonnel ep = new HrExteriorPersonnel();
		ep.setId(id);
		super.deleteObject(ep);
	}

	//查询留学人员的记录数用于分页
	public int queryCounttotal(ExteriorPerForm exteriorperForm) {
		int count = 0;
		String name = GlobalMethod.NullToSpace(exteriorperForm.getName());
		String currentCountry = GlobalMethod.NullToSpace(exteriorperForm.getCurrentCountry());
		String company = GlobalMethod.NullToSpace(exteriorperForm.getCompany());
		String foreignDegree = GlobalMethod.NullToSpace(exteriorperForm.getForeignDegree());
		String hql = "select count(ep) from HrExteriorPersonnel as ep where 1=1";
		if(!"".equals(name) && name != null){
			hql += " and name like '%"+name+"%'";
		}
		if(!"".equals(currentCountry) && currentCountry != null){
			hql += " and currentCountry like '%"+currentCountry+"%'";
		}
		if(!"".equals(company) && company != null){
			hql += " and company like '%"+company+"%'";
		}
		if(!"".equals(foreignDegree) && foreignDegree != null){
			hql += " and foreignDegree like '%"+foreignDegree+"%'";
		}
		Query query = this.getSession().createQuery(hql);
		
		try{
			List list = query.list();
			count = (Integer)list.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	//按id查询留学人员的姓名
	public List<HrExteriorPersonnel> selectNameById(Integer[] ids) {
		
		String hql = "from HrExteriorPersonnel where 1=1";
		if(ids != null && ids.length > 0){
			hql += " and (";
			for(int i=0; i<ids.length; i++){
				hql += " id=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List perNameList = this.getHibernateTemplate().find(hql, ids);
		return perNameList;
	}

	//查询所有留学人员信息
	public List selectAllPersonal(ExteriorPerForm exteriorperForm) {
		
		String name = GlobalMethod.NullToSpace(exteriorperForm.getName());
		String currentCountry = GlobalMethod.NullToSpace(exteriorperForm.getCurrentCountry());
		String company = GlobalMethod.NullToSpace(exteriorperForm.getCompany());
		String foreignDegree = GlobalMethod.NullToSpace(exteriorperForm.getForeignDegree());
		
		String hql = "from HrExteriorPersonnel where 1=1 ";
		
		if(!"".equals(name) && name != null){
			hql += " and name like '%"+name+"%'";
		}
		if(!"".equals(currentCountry) && currentCountry != null){
			hql += " and currentCountry like '%"+currentCountry+"%'";
		}
		if(!"".equals(company) && company != null){
			hql += " and company like '%"+company+"%'";
		}
		if(!"".equals(foreignDegree) && foreignDegree != null){
			hql += " and foreignDegree like '%"+foreignDegree+"%'";
		}
		
		hql += " order by id desc";
		Query query = this.getSession().createQuery(hql);
		
		List allPerList = query.list();
		return allPerList;
	}

	//检查要上传图片的名字
	public List checkPicName(String picName) {
		
		String hql = "select imageUrl from HrExteriorPersonnel where imageUrl = :picName";
		
		Query query = this.getSession().createQuery(hql);
		query.setParameter("picName", picName);
		
		List imgUriList = query.list();
		return imgUriList;
	}

}
