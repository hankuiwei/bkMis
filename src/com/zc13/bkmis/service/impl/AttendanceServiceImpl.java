package com.zc13.bkmis.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zc13.HWFace.Face;
import com.zc13.HWFace.entity.Employee;
import com.zc13.HWFace.entity.Record;
import com.zc13.bkmis.dao.IAttendanceDAO;
import com.zc13.bkmis.dao.IPersonnelDAO;
import com.zc13.bkmis.dao.IRecordDAO;
import com.zc13.bkmis.form.AttendanceForm;
import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.mapping.HrPact;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.bkmis.mapping.HrRecord;
import com.zc13.bkmis.service.IAttendanceService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PropertiesReader;

import common.Logger;

public class AttendanceServiceImpl extends BasicServiceImpl implements IAttendanceService {
	Logger logger = Logger.getLogger(this.getClass());
	private IAttendanceDAO attendanceDAO;
	private IPersonnelDAO personnelDAO;
	private IRecordDAO recordDAO;
	public IAttendanceDAO getAttendanceDAO() {
		return attendanceDAO;
	}
	public void setAttendanceDAO(IAttendanceDAO attendanceDAO) {
		this.attendanceDAO = attendanceDAO;
	}
	
	public IPersonnelDAO getPersonnelDAO() {
		return personnelDAO;
	}
	public void setPersonnelDAO(IPersonnelDAO personnelDAO) {
		this.personnelDAO = personnelDAO;
	}
	
	public IRecordDAO getRecordDAO() {
		return recordDAO;
	}
	public void setRecordDAO(IRecordDAO recordDAO) {
		this.recordDAO = recordDAO;
	}
	@Override
	public void updatePersonnelAndRecord(AttendanceForm form) throws BkmisServiceException {
		PropertiesReader properties = null;
		try {
			//读取考勤机的配置文件
			properties = new PropertiesReader("/attendance.properties");
			//创建读取考勤机数据的对象
			Face face = new Face(properties.getValue("c220.dev_id"),properties.getValue("c220.dev_type"),properties.getValue("c220.comm_type"),properties.getValue("c220.ip_address"),properties.getValue("c220.password"),properties.getValue("c220.port_number"));
			//从考勤机中获取所有员工信息
			List<Employee> list = face.getAllEmployeeInfo();
			logger.info("从考勤机获取员工信息：" + (null==list?"null":list.size()));
			//将考勤机上的数据更新到数据库中的hr_personnel表中，更新的原则是：如果考勤机上的员工工号对应数据库存在，则不处理，如果不存在，则将该员工记录增加到数据库中
			if(list!=null&&list.size()>0){
				for(Employee e:list){
					PersonnelForm tempForm = new PersonnelForm();
					//根据员工工号查询员工
					tempForm.setCx_personnelNum(e.getId());
					List tempList = personnelDAO.getPersonnelList(tempForm, false);
					//如果没有查询到，则说明数据库没有该工号的员工，则需要将该信息插入数据库
					if(tempList==null||tempList.size()<=0){
						//创建hr_personnel对象
						HrPersonnel personnel = new HrPersonnel();
						personnel.setPersonnelNum(e.getId());
						personnel.setPersonnelName(e.getName());
						personnel.setIsDispatch(Contants.ISDISPATCH);
						personnel.setIsInPost(Contants.ISNOTINPOST);
						personnel.setIsOut(Contants.ISNOTOUT);
						personnel.setLpId(form.getLpId());
						personnel.setStatus("在职");
						//保存员工信息
						personnelDAO.saveObject(personnel);
						//保存合同信息
						HrPact pact = new HrPact();
						pact.setPersonnelId(personnel.getPersonnelId());
						pact.setLpId(form.getLpId());
						personnelDAO.saveObject(pact);
					}
				}
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//从数据库中获得考勤记录的最晚时间
			String latestTime = recordDAO.getLatestTime(form);
			String start_time = null;
			if(!GlobalMethod.NullToSpace(latestTime).equals("")){
				Date startTime = new Date();
				startTime.setTime(dateFormat.parse(latestTime).getTime()+1000);
				//开始时间的取值是latestTime的后一秒
				start_time = dateFormat.format(startTime);
			}
			//从考勤机中读取考勤记录
			List<Record> recordList = face.getDeviceRecordsByTime(start_time, null);
			logger.info("从考勤机获取考勤记录：" + (null==recordList?"null":recordList.size())+"dddd");
			//将考勤记录存入数据库
			if(recordList!=null&&recordList.size()>0){
				for(Record r:recordList){
					HrRecord tempRecord = new HrRecord();
					tempRecord.setPersonnelNum(r.getId());
					tempRecord.setPersonnelName(r.getName());
					tempRecord.setWorkcode(r.getWorkcode());
					tempRecord.setStatus(r.getStatus());
					tempRecord.setCardSrc(r.getCard_src());
					tempRecord.setType(r.getType());
					tempRecord.setTime(r.getTime());
					tempRecord.setLpId(form.getLpId());
					//保存考勤记录
					recordDAO.saveObject(tempRecord);
					//根据考勤记录更新员工信息
					HrPersonnel personnel = new HrPersonnel();
					personnel.setIsInPost(r.getStatus());
					personnel.setPersonnelNum(r.getId());
					personnel.setLpId(form.getLpId());
					personnelDAO.updatePersonnelByNum(personnel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BkmisServiceException();
		}
	}
	
	@Override
	public List getAttendanceList(AttendanceForm form1,boolean isPage) throws BkmisServiceException {
		List list = null;
		list = attendanceDAO.getAttendanceList(form1,isPage);
		form1.setAttendanceList(list);
		List list2 = attendanceDAO.getAttendanceList(form1, false);
		int totalcount = 0;
		if(list2!=null){
			totalcount = list2.size();
		}
		form1.setTotalcount(totalcount);
		return list;
	}
	
	public static void main(String[] args){
		AttendanceServiceImpl a = new AttendanceServiceImpl();
		a.updatePersonnelAndRecord(null);
	}
	@Override
	public List<HrPersonnel> getCurrentAttendance(AttendanceForm form1, boolean isPage) throws BkmisServiceException {
		List list = null;
		list = attendanceDAO.getCurrentAttendance(form1,isPage);
		form1.setPersonnelList(list);
		return list;
	}
	@Override
	public void setAttendanceTime(AttendanceForm form1) throws BkmisServiceException {
		HrPersonnel person = (HrPersonnel)attendanceDAO.getObject(HrPersonnel.class, form1.getPersonnelId());
		person.setIsInPost(form1.getStatus());
		attendanceDAO.updateObject(person);
		HrRecord record = new HrRecord();
		try {
			BeanUtils.copyProperties(record,form1);
			record.setPersonnelName(person.getPersonnelName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		attendanceDAO.saveObject(record);
	}
}
