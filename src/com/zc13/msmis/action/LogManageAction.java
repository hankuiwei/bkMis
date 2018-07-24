package com.zc13.msmis.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysLog;
import com.zc13.msmis.service.ILogManagerService;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author 秦彦韬
 * Date：Nov 30, 2010
 * Time：5:15:50 PM
 */
public class LogManageAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ILogManagerService ILogManagerService = null;
	/** 从spring容器里得到iroomManageService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		ILogManagerService = (ILogManagerService)getBean("ILogManagerService");
	}
	/** 根据操作人，操作类型，操作内容等条件进行查询 */
	public ActionForward queryLog(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String sysLogName = request.getParameter("sysLogName");
		String sysLogType = request.getParameter("sysLogType");
		String sysLogContext = request.getParameter("sysLogContext");
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		String flag = GlobalMethod.NullToSpace(request.getParameter("flag"));
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		/** 到此为止 */

		int operateUserId = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("operateUserId"), "0"));//操作员
		//如果标志是只能查看自己的日志
		if(flag.equals("own")){
			Map map = (Map)request.getSession().getAttribute("userInfo");
			operateUserId = (Integer)map.get("userid");
		}
		SysLog sysLog = new SysLog();
		sysLog.setOperateUserName(sysLogName);
		sysLog.setOperateType(sysLogType);
		sysLog.setOperateModule(sysLogContext);
		sysLog.setOperateUserId(operateUserId);
		//sysLog.setLpId(lpId);
		
		List<SysLog> list = null;
		try{
			int totalcount = ILogManagerService.queryCount(sysLog);
			list = ILogManagerService.query(sysLog,currentpage,pagesize);
			//取总条数
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/logManager.do?method=queryLog", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/logManager.do?method=queryLog", Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10")),
						Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("sysLogName", sysLogName==null?"":sysLogName);
			request.setAttribute("sysLogType", sysLogType==null?"":sysLogType);
			request.setAttribute("sysLogContext", sysLogContext==null?"":sysLogContext);
			request.setAttribute("operateUserId", operateUserId);
		}catch(Exception e){
			logger.error("高级查询失败!LogManageAction.queryLog().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，LogManageAction.queryLog()!",e);
		}
		request.setAttribute("list",list);
		return mapping.findForward("success");
	}
	/**
	 * 查看某条日志详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:Apr 20, 2011 
	 * Time:5:22:04 AM
	 */
	public ActionForward queryLogById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		Integer id = Integer.parseInt(GlobalMethod.NullToSpace(request.getParameter("id")));
		try {
			SysLog sysLog = ILogManagerService.findbyID(id);
			request.setAttribute("sysLog", sysLog);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward("lookDetail");
	}
	
}

