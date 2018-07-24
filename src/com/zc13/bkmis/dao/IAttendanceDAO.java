package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AttendanceForm;

public interface IAttendanceDAO extends BasicDAO{

	/**
	 * 获得考勤记录信息列表
	 * @param form1
	 * @param isPage 是否分页
	 */
	List getAttendanceList(AttendanceForm form1, boolean isPage) throws DataAccessException;

	/**
	 * 查询当前员工的考勤
	 * @param form1
	 * @param isPage 是否分页
	 * @return
	 */
	List getCurrentAttendance(AttendanceForm form1, boolean isPage) throws DataAccessException;

}
