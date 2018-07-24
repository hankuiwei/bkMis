package com.zc13.bkmis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IPersonnelDAO;
import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:38:16 AM
 */
public class PersonnelDAOImpl extends BasicDAOImpl implements IPersonnelDAO {

	//对部门下拉列表的内容的查询
	public List<SysCode> selectDepartment(PersonnelForm personnelForm) {

		String hql = "select s from SysCode as s where codeType=?";
		
//		Integer lpId = personnelForm.getLpId();
//		if(lpId!=null && lpId!=0){
//			hql+=" and s.lpId = "+lpId;
//		}
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0,"department");
		return query.list();
	}

	//显示所有员工信息
	public List showPersonnel(PersonnelForm personnelForm,boolean isPage) {
		StringBuffer hql = new StringBuffer("select new Map(per.personnelId as personnelId,per.personnelName as personnelName,per.personnelCode as personnelCode,per.personnelNum as personnelNum, per.sex as sex,per.academicCertificate as academicCertificate,per.speciality as speciality,per.groups as groups,per.nation as nation,per.departmentCode as departmentCode,per.status as status,per.isDispatch as isDispatch,pa.engageChannel as engageChannel,pa.startDate as startDate,pa.duty as duty) from HrPersonnel as per,HrPact as pa where per.personnelId=pa.personnelId ");
		if(personnelForm!=null){
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelName()).equals("")){
				hql.append(" and per.personnelName like '%").append(personnelForm.getCx_personnelName()).append("%' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_starttime()).equals("")){
				hql.append(" and pa.startDate>='").append(personnelForm.getCx_starttime()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_endtime()).equals("")){
				hql.append(" and pa.startDate<='").append(personnelForm.getCx_endtime()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_departmentcode()).equals("")){
				hql.append(" and per.departmentCode='").append(personnelForm.getCx_departmentcode()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCxStatus()).equals("")){
				hql.append(" and per.status='").append(personnelForm.getCxStatus()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isDispatch()).equals("")){
				hql.append(" and per.isDispatch='").append(personnelForm.getCx_isDispatch()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isInPost()).equals("")){
				hql.append(" and per.isInPost='").append(personnelForm.getCx_isInPost()).append("' ");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isOut()).equals("")){
				hql.append(" and per.isOut='").append(personnelForm.getCx_isOut()).append("' ");
			}
			
			if(personnelForm.getLpId()!=null&&personnelForm.getLpId()!=0){
				hql.append(" and per.lpId=").append(personnelForm.getLpId());
			}
			
		}
		hql.append(" order by per.personnelId asc");
		Query query=this.getSession().createQuery(hql.toString());
		List personnelList = null;
		if(isPage){
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")) - 1);
			personnelList = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}else{
			personnelList = query.list();
		}
		return personnelList;
	}

	//删除员工信息
	public void delPersonnel(String ids) {
		//删除员工信息
		super.update("delete HrPersonnel where personnelId in("+ids+")");
		//删除员工附件信息
		super.update("delete HrFile where pactId in(select pactId from HrPact where personnelId in("+ids+"))");
		//删除合同信息
		super.update("delete HrPact where personnelId in("+ids+")");
	}

	//按id查询员工姓名
	public List<HrPersonnel> selectPerNameById(Integer[] idArray) {
		
		String hql = "from HrPersonnel where 1=1";
		if(idArray != null && idArray.length > 0){
			hql += " and (";
			for(int i=0; i<idArray.length; i++){
				hql += " personnelId=? or ";
			}
			hql = hql.substring(0,hql.length() - 3);
			hql += ")";
		}
		List perNameList = this.getHibernateTemplate().find(hql, idArray);
		return perNameList;
	}

	//按图片名查询是否已经存在该图片
	public List selectPicName(String name) {
		
		String hql = "select imageUrl from HrPersonnel where imageUrl = :name";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("name", name);
		List uriList = query.list();
		return uriList;
	}


	//查询上传的文件的名字是否存在
	public List selectFileName(String name) {
		
		String hql = "select pactFileName,identityCopyName,academicCertificateCopyName,physicalExaminationReportName,qualificationCertificateCopyName,bankCardCopyName" +
				" from HrPact where pactFileName = :pactFileName or identityCopyName = :identityCopyName or academicCertificateCopyName = :academicCertificateCopyName" +
				" or physicalExaminationReportName = :physicalExaminationReportName or qualificationCertificateCopyName = :qualificationCertificateCopyName or bankCardCopyName = :bankCardCopyName";
		
		Query query = this.getSession().createQuery(hql);
		query.setParameter("pactFileName", name);
		query.setParameter("identityCopyName", name);
		query.setParameter("academicCertificateCopyName", name);
		query.setParameter("physicalExaminationReportName", name);
		query.setParameter("qualificationCertificateCopyName", name);
		query.setParameter("bankCardCopyName", name);
		
		List existUriList = query.list();
		return existUriList;
	}

	@Override
	public List<HrPersonnel> getPersonnelList(PersonnelForm personnelForm,boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("from HrPersonnel where 1=1 ");
		if(personnelForm!=null){
			if(personnelForm.getLpId()!=null&&personnelForm.getLpId()!=0){
				hql.append(" and lpId=").append(personnelForm.getLpId());
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelName()).equals("")){
				hql.append(" and personnelName like '%").append(personnelForm.getCx_personnelName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelCode()).equals("")){
				hql.append(" and personnelCode like '%").append(personnelForm.getCx_personnelCode()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isDispatch()).equals("")){
				hql.append(" and isDispatch = '").append(personnelForm.getCx_isDispatch()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isInPost()).equals("")){
				hql.append(" and isInPost = '").append(personnelForm.getCx_isInPost()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isOut()).equals("")){
				hql.append(" and isOut = '").append(personnelForm.getCx_isOut()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelNum()).equals("")){
				hql.append(" and personnelNum = '").append(personnelForm.getCx_personnelNum()).append("'");
			}
		}
		hql.append(" order by personnelId");
		List<HrPersonnel> list = null;
		if(!isPage){
			list = this.getSession().createQuery(hql.toString()).list();
		}else{
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")) - 1);
			Query query=this.getSession().createQuery(hql.toString());
			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
		}
		return list;
	}

	@Override
	public void updatePersonnelByNum(HrPersonnel personnel) throws DataAccessException {
		StringBuffer hql = new StringBuffer("update HrPersonnel set isInPost='");
		hql.append(personnel.getIsInPost()).append("'");
		hql.append(" where personnelNum='").append(personnel.getPersonnelNum()).append("'");
		hql.append(" and lpId=").append(personnel.getLpId());
		this.update(hql.toString());
	}

	@Override
	public List getWorkingConditions4Personnel(PersonnelForm personnelForm, boolean isPage) throws DataAccessException {
		StringBuffer hql = new StringBuffer("");
		StringBuffer condition = new StringBuffer(" where s.status='").append(Contants.REPAIR_COMPLETED).append("'");//条件
		if(personnelForm!=null){
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_starttime()).equals("")){
				condition.append(" and SUBSTRING((case s.appearance_time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else s.appearance_time end),1,10) >= '").append(personnelForm.getCx_starttime()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_endtime()).equals("")){
				condition.append(" and SUBSTRING((case s.appearance_time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else s.appearance_time end),1,10) <= '").append(personnelForm.getCx_endtime()).append("'");
			}
		}
		hql.append("select * from (select p.personnel_id,p.personnel_name,p.personnel_code,p.personnel_num,p.phone,p.status,is_inPost=(case p.is_inPost when '1' then '已上班' else '已下班' end),p.is_out,p.sex, ");
		hql.append("	   ISNULL((select sum(CONVERT(decimal(15,3),s.man_hour)) from ser_client_maintain s ").append(condition).append(" and PATINDEX('%,'+CONVERT(nvarchar,p.personnel_id)+',%',','+s.sended_man+',')>0 ),0) as total_hour, ");
		hql.append("	   (select count(id) from ser_client_maintain s ").append(condition).append(" and PATINDEX('%,'+CONVERT(nvarchar,p.personnel_id)+',%',','+s.sended_man+',')>0 and s.client_notion='非常满意')  as very_satisfied, ");
		hql.append("	   (select count(id) from ser_client_maintain s ").append(condition).append(" and PATINDEX('%,'+CONVERT(nvarchar,p.personnel_id)+',%',','+s.sended_man+',')>0 and s.client_notion='满意')  as satisfied, ");
		hql.append("	   (select count(id) from ser_client_maintain s ").append(condition).append(" and PATINDEX('%,'+CONVERT(nvarchar,p.personnel_id)+',%',','+s.sended_man+',')>0 and s.client_notion='不满意')  as not_satisfied, ");
		hql.append("	   (select count(id) from ser_client_maintain s ").append(condition).append(" and PATINDEX('%,'+CONVERT(nvarchar,p.personnel_id)+',%',','+s.sended_man+',')>0)  as number, ");
		hql.append("ROW_NUMBER()OVER(ORDER BY p.personnel_id)AS _page_row_num_hb ");
		hql.append(" from hr_personnel p where 1=1 ");
		if(personnelForm!=null){
			if(personnelForm.getLpId()!=null&&personnelForm.getLpId()!=0){
				hql.append(" and p.lp_id=").append(personnelForm.getLpId());
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelName()).equals("")){
				hql.append(" and p.personnel_name like '%").append(personnelForm.getCx_personnelName()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelCode()).equals("")){
				hql.append(" and p.personnel_code like '%").append(personnelForm.getCx_personnelCode()).append("%'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isDispatch()).equals("")){
				hql.append(" and p.is_dispatch = '").append(personnelForm.getCx_isDispatch()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isInPost()).equals("")){
				hql.append(" and p.is_inPost = '").append(personnelForm.getCx_isInPost()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_isOut()).equals("")){
				hql.append(" and p.is_out = '").append(personnelForm.getCx_isOut()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCx_personnelNum()).equals("")){
				hql.append(" and p.personnel_num = '").append(personnelForm.getCx_personnelNum()).append("'");
			}
			if(!GlobalMethod.NullToSpace(personnelForm.getCxStatus()).equals("")){
				hql.append(" and p.status = '").append(personnelForm.getCxStatus()).append("'");
			}
		}
		List list = null;
		if(!isPage){
			hql.append(" )temp ");
			//list = this.getSession().createCriteria(hql.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			list = this.getSession().createSQLQuery(hql.toString()).list();
		}else{
			int pagesize = Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize*(Integer.parseInt(GlobalMethod.NullToParam(personnelForm.getCurrentpage(),"1")) - 1);
			hql.append(" )temp where _page_row_num_hb BETWEEN ").append(currentindex+1).append(" AND ").append(currentindex+pagesize);
//			Query query=this.getSession().createSQLQuery(hql.toString());
//			Query query = this.getSession().createSQLQuery(hql.toString());
//			list = query.setFirstResult(currentindex).setMaxResults(pagesize).list();
			list = this.getSession().createSQLQuery(hql.toString()).list();
		}
		//将object[]转换成map
		List list2 = null;
		if(list!=null&&list.size()>0){
			list2 = new ArrayList();
			for(int i = 0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				Map map = new HashMap();
				map.put("personnelId", objs[0]);
				map.put("personnelName", objs[1]);
				map.put("personnelCode", objs[2]);
				map.put("personnelNum", objs[3]);
				map.put("phone", objs[4]);
				map.put("status", objs[5]);
				map.put("isInPost", objs[6]);
				map.put("isOut", objs[7]);
				map.put("sex", objs[8]);
				map.put("totalHour", objs[9]);
				map.put("verySatisfied", objs[10]);
				map.put("satisfied", objs[11]);
				map.put("notSatisfied", objs[12]);
				map.put("number", objs[13]);
				list2.add(map);
			}
		}
		return list2;
	}

}
