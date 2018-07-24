package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.TrackRecordForm;
import com.zc13.bkmis.mapping.TrackRecord;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;

/**
 * 客户耿总记录service
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:26:01
 */
public interface ITrackRecordService extends IBasicService {

	public List<TrackRecord> getList(TrackRecordForm trackRecordForm);

	public int queryCounttotal(TrackRecordForm trackRecordForm);
	
	public List<MUser> getUserList() throws Exception;
	
	public TrackRecord queryTrackRecordById(Integer id);
	
	public void SaveOrUpdate(TrackRecordForm trackRecordForm);
	
	public List<SysCode> getSysCode(Integer lpId);
	
	public void delById(String id);
}
