package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.TrackRecordForm;
import com.zc13.bkmis.mapping.TrackRecord;
import com.zc13.msmis.mapping.SysCode;


/**
 * 客户跟踪记录
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:29:11
 */
public interface ITrackRecordDAO extends BasicDAO {

	public List<TrackRecord> getList(TrackRecordForm trackRecordForm);

	public int queryCounttotal(TrackRecordForm trackRecordForm);
	
	public List<SysCode> getSysCode(String codeType,Integer lpId);
}
