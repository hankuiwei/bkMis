package com.zc13.bkmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zc13.bkmis.form.MeterInputForm;
import com.zc13.bkmis.mapping.AllMeterType;
import com.zc13.bkmis.mapping.EMeterExcerption;
import com.zc13.bkmis.mapping.TotalReadMeter;

/**
 * 表具抄录
 * @author 王正伟
 * Date：Dec 8, 2010
 * Time：2:57:53 PM
 */
public interface IWriteMeterService {
	/**
	 * 获取抄表页面的树状菜单显示的信息
	 * @return
	 * @throws Exception
	 */
	public List<AllMeterType> getInfoForTree(MeterInputForm meterInputForm) throws Exception;

	/**
	 * 得到用户表具的抄表读数的统计
	 * @param meterInputForm
	 * @throws Exception
	 */
	public List<TotalReadMeter> getTotalWriteMeterInfo(MeterInputForm meterInputForm) throws Exception;

	/**
	 * 获得用户表具的详细信息
	 * @param meterInputForm
	 * @return
	 * @throws Exception
	 */
	public List getUserReadMeter(MeterInputForm meterInputForm) throws Exception;

	/**
	 * 保存用户读表信息
	 * @return 更新的id
	 * @param request
	 */
	public List saveUserReadMeter(HttpServletRequest request) throws Exception;

	/**
	 * 获得公摊表具指定年度的抄表读数总计
	 * @param meterInputForm
	 * @return
	 */
	public List getTotalPublicWriteMeterInfo(MeterInputForm meterInputForm) throws Exception;

	/**
	 * 获得指定年度和公摊表具的详细读数
	 * @param meterInputForm
	 * @return
	 * @throws Exception
	 */
	public List<EMeterExcerption> getPublicReadMeterByYearAndId(MeterInputForm meterInputForm) throws Exception;

	/**
	 * 获得指定月份公摊表具的详细信息
	 * @param meterInputForm
	 * @return
	 * @throws Exception
	 */
	public List getPublicReadMeter(MeterInputForm meterInputForm) throws Exception;
}
