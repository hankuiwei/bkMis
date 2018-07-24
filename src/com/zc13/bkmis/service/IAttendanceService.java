package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.AttendanceForm;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.exception.BkmisServiceException;

/**
 * 考勤service
 * @author wangzw
 * @Date Jun 20, 2011
 * @Time 2:43:51 PM
 */
public interface IAttendanceService extends IBasicService{
	
	/**
	 * 读取考勤机得信息并根据考勤机的信息更新人员信息表中的员工及考勤信息和考勤信息表中的信息
	 * (在系统中每次获取在岗人员的时候需要调用此方法)
	 * @throws BkmisServiceException
	 */
	public void updatePersonnelAndRecord(AttendanceForm form) throws BkmisServiceException;

	/**
	 * 获得考勤记录信息列表
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 */
	public List getAttendanceList(AttendanceForm form1,boolean isPage) throws BkmisServiceException;

	/**
	 * 查询当前员工的考勤
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 */
	public List<HrPersonnel> getCurrentAttendance(AttendanceForm form1, boolean isPage) throws BkmisServiceException;

	/**
	 * 设置考勤记录时间
	 * @param form1
	 */
	public void setAttendanceTime(AttendanceForm form1) throws BkmisServiceException;
}
