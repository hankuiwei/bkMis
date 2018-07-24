/**
 * ZHAOSG
 */
package com.zc13.bkmis.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CNoticeForm;
import com.zc13.bkmis.mapping.CNotice;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.util.PageBean;

/**
 * @author ZHAOSG
 * Date：Jan 3, 2011
 * Time：2:39:07 PM
 */
public interface ICNoticeService extends IBasicService {
	
	/**
	 * 保存缴款通知单
	 * @param noticeForm
	 * @param map 客户预收房租 房租押金 装修押金 定金的信息
	 * @return
	 * @throws Exception
	 * Date:Jul 21, 2011 
	 * Time:3:49:49 PM
	 */
	public int save(CNoticeForm noticeForm,Map<Integer,Map<String,List>> map)throws Exception;
	
	public PageBean getNoticeList(CNoticeForm noticeForm)throws Exception;
	
	public void delete(CNoticeForm noticeForm)throws Exception;
	
	public List getClientList(CNoticeForm noticeForm)throws Exception;
	
	public List getStandardList(CNoticeForm noticeForm)throws Exception;
	
	public List<CNotice> getNotice(CNoticeForm noticeForm)throws Exception;
	
	public List<ELp> getLp() throws Exception ;
	
	public List<EBuilds> getBuild() throws Exception;
	
	public List getAllNoticePrint(CNoticeForm noticeForm)throws Exception;
	
	public List getNoticePrint(CNoticeForm noticeForm)throws Exception;
	public String getRoomCodeByClientId(int id) throws DataAccessException;

	/**
	 * 获得收费项
	 * @param noticeForm
	 * @return
	 * Date:May 2, 2011 
	 * Time:4:06:46 PM
	 */
	public List getItemList(CNoticeForm noticeForm);
}
