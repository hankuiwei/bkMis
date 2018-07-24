package com.zc13.bkmis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IWriteMeterDAO;
import com.zc13.bkmis.form.MeterInputForm;
import com.zc13.bkmis.mapping.AllMeterType;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.EMeterExcerption;
import com.zc13.bkmis.mapping.TotalReadMeter;
import com.zc13.bkmis.service.IWriteMeterService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;
/**
 * 表具抄录
 * @author 王正伟
 * Date：Dec 8, 2010
 * Time：3:04:58 PM
 */
public class WriteMeterServiceImpl implements IWriteMeterService {
	private IWriteMeterDAO writeMeterDAO;
	private Logger logger = Logger.getLogger(this.getClass());

	public IWriteMeterDAO getWriteMeterDAO() {
		return writeMeterDAO;
	}

	public void setWriteMeterDAO(IWriteMeterDAO writeMeterDAO) {
		this.writeMeterDAO = writeMeterDAO;
	}

	@Override
	public List<AllMeterType> getInfoForTree(MeterInputForm meterInputForm) throws Exception {
		List<AllMeterType> list = null;
		try {
			list = writeMeterDAO.getInfoForTree(meterInputForm);
		} catch (Exception e) {
			logger.error("获取抄表页面的树状菜单显示的信息失败!WriteMeterServiceImpl.getInfoForTree()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("获取抄表页面的树状菜单显示的信息失败!WriteMeterServiceImpl.getInfoForTree()。");
		}
		return list;
	}

	@Override
	public List<TotalReadMeter> getTotalWriteMeterInfo(MeterInputForm meterInputForm)
			throws Exception {
		List<TotalReadMeter> list = null;
		list = writeMeterDAO.countUserReadMeterInfo(meterInputForm);
		return list;
	}

	@Override
	public List getUserReadMeter(MeterInputForm meterInputForm)
			throws Exception {
		List list = writeMeterDAO.getUserReadMeter(meterInputForm);
		return list;
	}

	@Override
	public List saveUserReadMeter(HttpServletRequest request) throws Exception {
		//保存所修改记录的id值
		ArrayList allIds = new ArrayList();
		
		//获得request对象中的参数的值
		String[] years = GlobalMethod.NullToSpace(request.getParameter("years")).split(",");
		String[] months = GlobalMethod.NullToSpace(request.getParameter("months")).split(",");
		String[] lookUpMan = GlobalMethod.NullToSpace(request.getParameter("lookUpMan")).split(",");
		String[] lookUpTime = GlobalMethod.NullToSpace(request.getParameter("lookUpTime")).split(",");
		String[] enterMan = GlobalMethod.NullToSpace(request.getParameter("enterMan")).split(",");
		
		String[] ids = GlobalMethod.NullToSpace(request.getParameter("ids")).split(",");
		
		String[] meterIds = GlobalMethod.NullToSpace(request.getParameter("meterIds")).split(",");
		String[] codes = GlobalMethod.NullToSpace(request.getParameter("codes")).split(",");
		String[] meterTypes = GlobalMethod.NullToSpace(request.getParameter("meterTypes")).split(",");
		String[] beginDates = GlobalMethod.NullToSpace(request.getParameter("beginDates")).split(",");
		String[] endDates = GlobalMethod.NullToSpace(request.getParameter("endDates")).split(",");
		String[] lastRecords = GlobalMethod.NullToSpace(request.getParameter("lastRecords")).split(",");
		String[] thisRecords = GlobalMethod.NullToSpace(request.getParameter("thisRecords")).split(",");
		
		//将获得的参数封装到EMeterExcerption对象中
		for(int i = 0;i<meterIds.length;i++){
			EMeterExcerption m = new EMeterExcerption();
			EMeter em = new EMeter();
			m.setYearMonth(years[0]+"-"+months[0]);
			m.setLookUpMan(lookUpMan[0]);
			m.setLookUpTime(lookUpTime[0]);
			m.setEnterMan(enterMan[0]);
			em.setId(Integer.parseInt(meterIds[i]));
			System.out.println("service:id["+i+"]="+ids[i]);
			m.setId(Integer.parseInt(ids[i]));
			m.setEMeter(em);
			m.setCode(codes[i]);
			m.setType(meterTypes[i]);
			m.setBeginDate(beginDates[i]);
			m.setEndDate(endDates[i]);
			m.setLastRecord(Double.parseDouble(lastRecords[i]));
			m.setThisRecord(Double.parseDouble(thisRecords[i]));
			m.setEnterTime(DateUtil.formatDateTimes(new Date()));
			try {
				if(ids[i].equals("0")){
					Integer temp_id = writeMeterDAO.saveMeterInfo(m);
					allIds.add(String.valueOf(temp_id));
				}else{
					writeMeterDAO.updateMeterInfo(m);
					allIds.add(m.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
		}
		return allIds;
	}

	@Override
	public List getTotalPublicWriteMeterInfo(MeterInputForm meterInputForm) {
		List list = null;
		try {
			list = writeMeterDAO.countPublicReadMeterInfo(meterInputForm);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<EMeterExcerption> getPublicReadMeterByYearAndId(MeterInputForm meterInputForm)
			throws Exception {
		List<EMeterExcerption> list = writeMeterDAO.getPublicReadMeterByYearAndId(meterInputForm);
		return list;
	}

	@Override
	public List getPublicReadMeter(MeterInputForm meterInputForm)
			throws Exception {
		List list = writeMeterDAO.getPublicReadMeter(meterInputForm);
		return list;
	}

}
