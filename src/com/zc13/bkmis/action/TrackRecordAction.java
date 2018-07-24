package com.zc13.bkmis.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.TrackRecordForm;
import com.zc13.bkmis.mapping.TrackRecord;
import com.zc13.bkmis.service.ITrackRecordService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.DateUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 客户跟踪记录
 * @author Administrator
 * @Date 2013-4-9
 * @Time 下午10:24:41
 */
public class TrackRecordAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ITrackRecordService trackRecordService;
	public ITrackRecordService getTrackRecordService() {
		return trackRecordService;
	}

	public void setTrackRecordService(ITrackRecordService trackRecordService) {
		this.trackRecordService = trackRecordService;
	}

	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		trackRecordService = (ITrackRecordService)getBean("trackRecordService");
	}
	
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		TrackRecordForm trackRecordForm=(TrackRecordForm)form;
		
		List<TrackRecord> trackRecordList = null;
		List<MUser> userList = null;
		try{
			trackRecordList = trackRecordService.getList(trackRecordForm);
			
			userList = trackRecordService.getUserList();
			//取总条数
			int totalcount = trackRecordService.queryCounttotal(trackRecordForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == trackRecordList || trackRecordList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/trackRecord.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/trackRecord.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(trackRecordForm.getPagesize(), "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(trackRecordForm.getCurrentpage(), "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("客户跟踪查询失败!TrackRecordAction.getList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("trackRecordList", trackRecordList);
		request.setAttribute("userList", userList);
		request.setAttribute("trackRecordForm", trackRecordForm);
		return mapping.findForward("list");
	}
	
	@SuppressWarnings("rawtypes")
	public ActionForward goAddOrEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		TrackRecordForm trackRecordForm=(TrackRecordForm)form;
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer userid = (Integer) map1.get("userid");
		TrackRecord record = new TrackRecord();
		record.setUserId(userid);
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		List<SysCode> codeList = trackRecordService.getSysCode(lpId);
		String str = "";
		if(trackRecordForm.getTrackRecord().getId() != null){
			trackRecordForm.setTrackRecord(trackRecordService.queryTrackRecordById(trackRecordForm.getTrackRecord().getId()));
			str = "维护";
		}else{
			str = "添加";
			record.setCreateDate(DateUtil.formatDate(new Date()));
			trackRecordForm.setTrackRecord(record);
		}
		request.setAttribute("str", str);
		request.setAttribute("codeList", codeList);
		request.setAttribute("trackRecordForm", trackRecordForm);
		return mapping.findForward("goAddOrEdit");
	}
	
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		TrackRecordForm trackRecordForm=(TrackRecordForm)form;
		trackRecordService.SaveOrUpdate(trackRecordForm);
		request.setAttribute("trackRecordForm", trackRecordForm);
		return null;
	}
	
	public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		TrackRecordForm track = (TrackRecordForm)form;
		String ids = GlobalMethod.ObjToStr(track.getIds());
		try {
			String[] strings = ids.split(",");
			for (int i = 0; i < strings.length; i++) {
				trackRecordService.delById(strings[i]);
			}
			response.sendRedirect("trackRecord.do?method=getList");
		} catch (Exception e) {
			logger.error("删除实际财务失败!ifinancialActualService.delById(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("删除实际财务失败!ifinancialActualService.delById(form2)!", e);
		}
		return null;
	}
}
