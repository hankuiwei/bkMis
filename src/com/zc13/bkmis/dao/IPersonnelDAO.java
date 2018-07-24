package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.msmis.mapping.SysCode;

/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:37:44 AM
 */
public interface IPersonnelDAO extends BasicDAO{
	/**查找部门下拉列表中的内容*/
	public List<SysCode> selectDepartment(PersonnelForm personnelForm);

	/**
	 * 显示员工和对于的合同信息
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @return
	 */
	public List showPersonnel(PersonnelForm personnelForm,boolean isPage);
	/**
	 * 删除员工信息
	 * @param ids 员工id字符串 如："23,14,45"
	 */
	public void delPersonnel(String ids);
	//按id查询员工的姓名
	public List<HrPersonnel> selectPerNameById(Integer[] idArray);
	//查询已经存在图片的名称
	public List selectPicName(String name);
	//查询上传的文件的名字是否存在
	public List selectFileName(String name);
	/**
	 * 查询人员列表
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @return
	 */
	public List<HrPersonnel> getPersonnelList(PersonnelForm personnelForm,boolean isPage) throws DataAccessException;
	/**
	 * 根据员工工号更新personnel
	 * @param personnel
	 */
	public void updatePersonnelByNum(HrPersonnel personnel) throws DataAccessException;

	/**
	 * 查询工人工作情况
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @return
	 */
	public List getWorkingConditions4Personnel(PersonnelForm personnelForm, boolean isPage) throws DataAccessException;
	
}
