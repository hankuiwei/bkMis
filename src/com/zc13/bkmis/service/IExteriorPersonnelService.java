package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.ExteriorPerForm;
import com.zc13.bkmis.mapping.HrExteriorPersonnel;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 1, 2010
 * Time：3:19:29 PM
 */
public interface IExteriorPersonnelService {
	//获取工作单位的下拉列表的内容
	public void selectWorkPlace(ExteriorPerForm exteriorperForm);
	//显示留学人员信息
	public void selectExteriorPer(ExteriorPerForm exteriorperForm);
	//添加留学人员信息
	public void addExteriorPer(ExteriorPerForm exteriorperForm);
	//按员工id去查询信息
	public void selectExteriorById(ExteriorPerForm exteriorperForm);
	//修改留学人员信息
	public void updateExterior(ExteriorPerForm exteriorperForm);
	//按id删除留学信息
	public void delExterior(int id);
	//查询留学人员的记录总数用于分页
	public int queryCounttotal(ExteriorPerForm exteriorperForm);
	//按id查询出留学人员姓名
	public List<HrExteriorPersonnel> selectNameById(Integer[] ids);
	//查询所有留学人员的信息
	public List selectAllPersonal(ExteriorPerForm exteriorperForm);
	//检查要上传图片的名字
	public List checkPicName(String picName,String path);
}
