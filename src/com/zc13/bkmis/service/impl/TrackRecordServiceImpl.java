package com.zc13.bkmis.service.impl;

import java.util.List;

import com.zc13.bkmis.dao.ITrackRecordDAO;
import com.zc13.bkmis.form.TrackRecordForm;
import com.zc13.bkmis.mapping.TrackRecord;
import com.zc13.bkmis.service.ITrackRecordService;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;

/**
 * 客户跟踪记录service
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:27:35
 */
public class TrackRecordServiceImpl extends BasicServiceImpl implements ITrackRecordService {

	private ITrackRecordDAO trackRecordDao;

	public ITrackRecordDAO getTrackRecordDao() {
		return trackRecordDao;
	}

	public void setTrackRecordDao(ITrackRecordDAO trackRecordDao) {
		this.trackRecordDao = trackRecordDao;
	}

	@Override
	public List<TrackRecord> getList(TrackRecordForm trackRecordForm) {
		
		return trackRecordDao.getList(trackRecordForm);
	}

	@Override
	public int queryCounttotal(TrackRecordForm trackRecordForm) {
		
		return trackRecordDao.queryCounttotal(trackRecordForm);
	}

	@Override
	public List<MUser> getUserList() throws Exception {
		
		return trackRecordDao.getObjects(MUser.class);
	}

	@Override
	public TrackRecord queryTrackRecordById(Integer id) {
		
		return (TrackRecord)trackRecordDao.getObject(TrackRecord.class, id);
	}

	@Override
	public void SaveOrUpdate(TrackRecordForm trackRecordForm) {
		
		if(trackRecordForm.getTrackRecord().getId() == null || trackRecordForm.getTrackRecord().getId() == 0){
			trackRecordDao.saveObject(trackRecordForm.getTrackRecord());
		}else if(trackRecordForm.getTrackRecord().getId().intValue()>0){
			trackRecordDao.updateObject(trackRecordForm.getTrackRecord());
		}
	}

	@Override
	public List<SysCode> getSysCode(Integer lpId) {
		
		return trackRecordDao.getSysCode("enterpriseType",lpId);
	}

	@Override
	public void delById(String id) {
		
		TrackRecord track = (TrackRecord)trackRecordDao.getObject(TrackRecord.class, Integer.parseInt(id));
		trackRecordDao.deleteObject(track);
	}
}
