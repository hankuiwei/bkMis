package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CNoticeForm;
import com.zc13.bkmis.mapping.CNotice;
import com.zc13.util.PageBean;

public interface ICNoticeDAO extends BasicDAO {
	
	public void deleteNotice(CNoticeForm noticeForm) throws DataAccessException;
	
	public int save(CNoticeForm noticeForm) throws DataAccessException;
	public String getRoomCodeByClientId(int id) throws DataAccessException;
	
	public PageBean getNotiveList(CNoticeForm noticeForm)throws DataAccessException;
	
	public List getClientList(CNoticeForm noticeForm)throws DataAccessException;
	
	public List getStandardList(CNoticeForm noticeForm)throws DataAccessException;
	
	public List<CNotice> getNotice(CNoticeForm noticeForm)throws DataAccessException;
	
	public void delete(CNotice notice) throws DataAccessException;
	
	public List<CNotice> getAllNoticePrint(CNoticeForm noticeForm)throws DataAccessException;
	
	public List<CNotice> getNoticePrint(CNoticeForm noticeForm)throws DataAccessException;
}
