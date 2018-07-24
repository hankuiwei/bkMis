package com.zc13.bkmis.dao;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.AttendanceForm;

public interface IRecordDAO extends BasicDAO{
	/**
	 * 获取指定考勤记录的最晚时间
	 * @param form
	 * @return
	 * @throws DataAccessException
	 */
	public String getLatestTime(AttendanceForm form) throws DataAccessException;
}
