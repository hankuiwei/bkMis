package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.ExteriorPerForm;
import com.zc13.bkmis.mapping.HrExteriorPersonnel;
import com.zc13.msmis.mapping.SysCode;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 1, 2010
 * Time：3:20:59 PM
 */
public interface IExteriorPersonnelDAO extends BasicDAO{
	//对工作单位下拉列表内容的获取
	public List<SysCode> selectWorkPlace();
	//显示留学人员信息
	public List selectExteriorPer(ExteriorPerForm exteriorperForm);
	//添加留学人员信息
	public void addExteriorPer(HrExteriorPersonnel ep);
	//按照员工id去查询留学人员信息信息
	public List selectExteriorById(int id);
	//留学人员信息的修改
	public void updateExterior(HrExteriorPersonnel ep);
	//按id删除留学信息
	public void delExterior(int id);
	//查询留学人员的记录数用于分页
	public int queryCounttotal(ExteriorPerForm exteriorperForm);
	//按id查询出留学人员姓名
	public List<HrExteriorPersonnel> selectNameById(Integer[] ids);
	//查询所有留学人员的信息
	public List selectAllPersonal(ExteriorPerForm exteriorperForm);
	//检查要上传图片的名字
	public List checkPicName(String picName);
}
