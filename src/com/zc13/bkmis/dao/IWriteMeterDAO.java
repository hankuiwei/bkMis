package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.MeterInputForm;
import com.zc13.bkmis.mapping.AllMeterType;
import com.zc13.bkmis.mapping.EMeterExcerption;
import com.zc13.bkmis.mapping.TotalReadMeter;

/**
 * 表具抄录
 * @author 王正伟
 * Date：Dec 8, 2010
 * Time：3:02:57 PM
 */
public interface IWriteMeterDAO {
	/**
	 * 获取抄表页面的树状菜单显示的信息
	 * @return
	 * @throws Exception
	 */
	public List<AllMeterType> getInfoForTree(MeterInputForm meterInputForm) throws DataAccessException;

	/**
	 * 得到用户表具的抄表读数的统计
	 * @param meterInputForm
	 * @throws DataAccessException
	 */
	public List<TotalReadMeter> countUserReadMeterInfo(MeterInputForm meterInputForm) throws DataAccessException;

	/**
	 * 获得用户表具的详细信息
	 * @param meterInputForm
	 * @return
	 */
	public List getUserReadMeter(MeterInputForm meterInputForm) throws DataAccessException;

	/**
	 * 保存用户读表信息
	 * @param m
	 */
	public Integer saveMeterInfo(EMeterExcerption m) throws DataAccessException;

	/**
	 * 更新用户读表信息
	 * @param m
	 * @throws DataAccessException
	 */
	public void updateMeterInfo(EMeterExcerption m) throws DataAccessException;

	/**
	 * 获得公摊表具指定年度的抄表读数总计
	 * @param meterInputForm
	 * @return
	 */
	public List countPublicReadMeterInfo(MeterInputForm meterInputForm) throws DataAccessException;

	/**
	 * 获得指定年度和公摊表具的详细读数
	 * @param meterInputForm
	 * @return
	 * @throws DataAccessException
	 */
	public List<EMeterExcerption> getPublicReadMeterByYearAndId(MeterInputForm meterInputForm) throws DataAccessException;

	/**
	 * 获得公摊表具的详细信息
	 * @param meterInputForm
	 * @return
	 */
	public List getPublicReadMeter(MeterInputForm meterInputForm)throws DataAccessException;
	
}
