package com.zc13.bkmis.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ISerClientMaintainDAO;
import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.mapping.SerMaterialConsume;
import com.zc13.util.GlobalMethod;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class SerClientMaintainDAOImpl extends BasicDAOImpl implements ISerClientMaintainDAO {

	//获取客户报修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<SerClientMaintain> getClientList(SerClientMaintainForm form,boolean isPage)
			throws DataAccessException {
		StringBuffer hql = new StringBuffer("from SerClientMaintain where 1 = 1");
		String pagesize = "10";
		String currentpage = "1";
		if(form!=null){
			if(!GlobalMethod.NullToSpace(form.getSelstatus()).equals("")){
				String cxStatus = "'"+form.getSelstatus().replace(",", "','")+"'";
				hql.append(" and status  in("+cxStatus+")");
			}
			if(form.getLpId()!=null&&!"0".equals(String.valueOf(form.getLpId()))){
				hql.append(" and lpId  = '"+form.getLpId()+"'");
			}
			if(form.getCx_buildId()!=null&&!"0".equals(String.valueOf(form.getCx_buildId()))){
				hql.append(" and buildId  = '"+form.getCx_buildId()+"'");
			}
			if(form.getCx_sendedMan()!=null&&!(form.getCx_sendedMan()).isEmpty()){
				//hql.append(" and sendedMan  like '%"+form.getCx_sendedMan()+"%'");
				hql.append(" and LOCATE(','+'"+form.getCx_sendedMan()+"'+',',','+sendedMan+',')<>0");
			}
			if(form.getBegindate()!=null&&!"".equals(form.getBegindate())){
				hql.append(" and SUBSTRING((case date when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else date end),1,10) >= '"+form.getBegindate()+"'");	
			}
			if(form.getEnddate()!=null&&!"".equals(form.getEnddate())){
				hql.append(" and SUBSTRING((case date when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else date end),1,10) <= '"+form.getEnddate()+"'");	
			}
			if(form.getWorkBegindate()!=null&&!"".equals(form.getWorkBegindate())){
				hql.append(" and SUBSTRING((case appearance_time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else appearance_time end),1,10) >= '"+form.getWorkBegindate()+"'");	
			}
			if(form.getWorkEnddate()!=null&&!"".equals(form.getWorkEnddate())){
				hql.append(" and SUBSTRING((case appearance_time when null then '0000-00-00 00:00:00' when '' then '0000-00-00 00:00:00' else appearance_time end),1,10) <= '"+form.getWorkEnddate()+"'");	
			}
			if(!GlobalMethod.NullToSpace(form.getCx_isEnabled()).equals("")){
				hql.append(" and isEnabled='").append(form.getCx_isEnabled()).append("' ");
			}
			pagesize = form.getPagesize();
			currentpage = form.getCurrentpage();
		}
		hql.append(" order by date desc");
		List<SerClientMaintain> list = null;
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")) - 1);
			list = (List<SerClientMaintain>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			Query query = this.getSession().createQuery(hql.toString());
			list = (List<SerClientMaintain>)query.list();
		}
		return list;
	}

	//根据编码得到客户报修
	@SuppressWarnings("unchecked")
	@Override
	public List<SerClientMaintain> getClientByCode(String code)
			throws DataAccessException {
		String hql = "from SerClientMaintain where code = '"+code+"'";
		Query query = this.getSession().createQuery(hql);
		List<SerClientMaintain> list = (List<SerClientMaintain>)query.list();
		return list;
	}

	//根据名称得到客户报修
	@SuppressWarnings("unchecked")
	@Override
	public List<SerClientMaintain> getClientByName(String name)
			throws DataAccessException {
		String hql = "from SerClientMaintain where code = '"+name+"'";
		Query query = this.getSession().createQuery(hql);
		List<SerClientMaintain> list = (List<SerClientMaintain>)query.list();
		return list;
	}
	
	//根据客户报修项目id查询材料消耗列表
	public List<SerMaterialConsume> getConsume(int id)
	throws DataAccessException {
		
		String hql = "from SerMaterialConsume where maintain_id = "+id;
		Query query = this.getSession().createQuery(hql);
		List<SerMaterialConsume> list = (List<SerMaterialConsume>)query.list();
		return list;
	}

	//查询材料出处列表
	@SuppressWarnings("unchecked")
	@Override
	public List<SerMaterialConsume> queryConsume(SerClientMaintainForm form,boolean isPage) throws DataAccessException {

		StringBuffer hql = new StringBuffer("from SerMaterialConsume where 1 = 1");
		if(form.getCx_department()!=null&&!"".equals(form.getCx_department())){
			hql.append(" and department like '%"+form.getCx_department()+"%'");
		}
		if(form.getCx_materialName()!=null&&!"".equals(form.getCx_materialName())){
			hql.append(" and materialName like '%"+form.getCx_materialName()+"%'");
		}
//		if(form.getLpId()!=null&&!"".equals(form.getLpId())){
//			hql.append("and lpId="+form.getLpId() );
//		}
		if(form.getBegindate()!=null&&!"".equals(form.getBegindate())){
			hql.append(" and outDate >= '"+form.getBegindate()+"'");	
		}
		if(form.getEnddate()!=null&&!"".equals(form.getEnddate())){
			hql.append(" and outDate <= '"+form.getEnddate()+" 23:59:59'");	
		}
		if(!GlobalMethod.NullToSpace(form.getCx_consumeDetails()).equals("")){
			hql.append(" and (roomName like '%").append(form.getCx_consumeDetails()).append("%' or areaName like '%").append(form.getCx_consumeDetails()).append("%') ");
		}
		if(!GlobalMethod.NullToSpace(form.getCx_sendcardCode()).equals("")){
			hql.append(" and serClientMaintain.sendcardCode = '"+form.getCx_sendcardCode()+"' ");
		}
		hql.append(" order by id");
		List<SerMaterialConsume> list = null;
		if(isPage){
			//每页显示的条数，空的情况下默认是10
			int pagesize1 = Integer.parseInt(GlobalMethod.NullToParam(form.getPagesize(),"10"));
			//当前是从第几条开始
			int currentindex = pagesize1*(Integer.parseInt(GlobalMethod.NullToParam(form.getCurrentpage(),"1")) - 1);
			list = (List<SerMaterialConsume>)this.getSession().createQuery(hql.toString()).setFirstResult(currentindex).setMaxResults(pagesize1).list();
		}else{
			list = this.getSession().createQuery(hql.toString()).list();
		}
		return list;
	}

	//查询材料出处,用于导出报表
	public List<SerMaterialConsume> explorMaterialList(String department,
			String materialName, String begin, String end)
			throws DataAccessException {

		StringBuffer hql = new StringBuffer("from SerMaterialConsume where 1 = 1");
		if(department!=null&&!"".equals(department)){
			hql.append(" and department like '%"+department+"%'");
		}
		if(materialName!=null&&!"".equals(materialName)){
			hql.append(" and materialName like '%"+materialName+"%'");
		}
		if(begin!=null&&!"".equals(begin)){
			hql.append(" and outDate >= '"+begin+"'");	
		}
		if(end!=null&&!"".equals(end)){
			hql.append(" and outDate <= '"+end+" 23:59:59'");	
		}
		hql.append(" order by id");
		
		Query query = this.getSession().createQuery(hql.toString());
		List list = query.list();
		return list;
	}
	//李荣刚修改
	@Override
	public void updateDepotMaterial(String bh, String sl) {
		String hql="update DepotMaterial set nowStock=nowStock-"+sl+",doStock=doStock-"+sl+",money=unitPrice*(nowStock-"+sl+") where code='"+bh+"'";
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	@Override
	public String getDostack(String code){
		String hql = "select doStock from DepotMaterial where code='"+code+"'";
		Query query = this.getSession().createQuery(hql);
		return String.valueOf(query.uniqueResult());
	}
}
