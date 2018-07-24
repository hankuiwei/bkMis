package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author 赵玉龙
 * Date：Nov 18, 2010
 * Time：11:36:38 AM
 */
public interface IPersonnelService extends IBasicService{
	/** 查找所属部门下拉列表的内容*/
	public void selectDepartment(PersonnelForm personnelForm);
	//查询员工信息获取记录个数
	public int queryCounttotal(PersonnelForm personnelForm);
	/**
	 * 显示员工信息
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @return
	 * Date:Jul 18, 2011 
	 * Time:10:12:10 AM
	 */
	public List showPersonnel(PersonnelForm personnelForm,boolean isPage);
	/**
	 * 删除员工信息
	 * @param personnelForm 其中的ids字段是员工id字符串 如："23,14,45"
	 */
	public void delPersonnel(PersonnelForm personnelForm) throws BkmisServiceException;
	//实现员工信息的添加及合同信息
	public void addPersonnel(PersonnelForm personnelForm);
	//按员工的id查询员工信息进行修改
	public void selectPersonnelById(PersonnelForm personnelForm);
	//员工信息的修改
	public void updatePersonnel(PersonnelForm form) throws BkmisServiceException;
	//查询已经存在图片的名称
	public List selectPicName(String picName,String path);
	//查询上传的文件的名字是否存在
	public List selectFileName(String name,String path);

	/**
	 * 根据人员id字符串查询人员信息
	 * @param ids 字符串格式为：1,4,8
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<HrPersonnel> selectPersonalByIds(String ids) throws BkmisServiceException;
	
	/**
	 * 查询可以被派遣的员工
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @return
	 * @throws BkmisServiceException
	 */
	public List<HrPersonnel> getDispatchPersonnel(PersonnelForm personnelForm,boolean isPage) throws BkmisServiceException;
	/**
	 * 查询工人工作情况
	 * @param personnelForm
	 * @param isPage 是否分页
	 * @throws BkmisServiceException
	 */
	public List getWorkingConditions4Personnel(PersonnelForm personnelForm,boolean isPage)throws BkmisServiceException;
	
	/**
	 * 更加id字符串，查询对应的姓名字符串
	 * @param ids 如："12,32,31"
	 * @return 姓名字符串 如："张三，李四，王五"
	 * @throws BkmisServiceException
	 */
	public String getNamesByPersonnelIds(String ids) throws BkmisServiceException;
	
	/**
	 * 查询维修结果
	 * @return
	 * Date:2013-4-9 
	 * Time:下午11:13:54
	 */
	public List<RepairResult> queryRepairResult();
}
