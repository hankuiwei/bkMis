package com.zc13.msmis.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.msmis.service.ProcessService;

/**
 * 系统业务流程图展示
 * @author daokui
 * @Date 2011-5-24
 * @Time 上午10:41:26
 */
public class ProcessAction extends BasicAction{
	
	public ActionForward getTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			ProcessService processService = (ProcessService)getBean("processService");
			Map map = (Map)request.getSession().getAttribute("userInfo");
			List<AwokeTask> list = processService.getTask((Integer)map.get("userrole"));
			request.setAttribute("taskList", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return mapping.findForward("showProcessPicture");
	}
}
