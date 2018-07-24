package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.AttendanceForm;
import com.zc13.bkmis.service.IAttendanceService;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

import common.Logger;

/**
 * 考勤action
 * @author wangzw
 * @Date Jun 20, 2011
 * @Time 2:40:35 PM
 */
public class AttendanceAction extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private IAttendanceService attendanceService;
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		attendanceService = (IAttendanceService) super.getBean("attendanceService");
	}
	
	/**
	 * 查询考勤信息
	 */
	public ActionForward showAttendanceList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AttendanceForm form1 = (AttendanceForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		//根据考勤机的信息更新数据库
		form1.setLpId(lpId);
		attendanceService.updatePersonnelAndRecord(form1);
		
		attendanceService.getAttendanceList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getAttendanceList() || form1.getAttendanceList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/attendance.do?method=showAttendanceList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/attendance.do?method=showAttendanceList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		return mapping.findForward("showAttendance");
	}
	
	/**
	 * 查询当前员工的考勤
	 */
	public ActionForward showCurrentAttendance(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AttendanceForm form1 = (AttendanceForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		//根据考勤机的信息更新数据库
		form1.setLpId(lpId);
		attendanceService.updatePersonnelAndRecord(form1);
		
		attendanceService.getCurrentAttendance(form1,false);
		return mapping.findForward("showCurrentAttendance");
	}
	
	/**
	 * 设置考勤记录时间
	 */
	public ActionForward setAttendanceTime(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AttendanceForm form1 = (AttendanceForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		form1.setUserName(userName);
		form1.setLpId(lpId);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			attendanceService.setAttendanceTime(form1);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		out.close();
		return null;
	}
	
	//导出报表
	public ActionForward exportExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			try{
				AttendanceForm form1 = (AttendanceForm)form;
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
				form1.setLpId(lpId);
				form1.setUserName(userName);
				List list = attendanceService.getAttendanceList(form1,false);
				//表头
				String[] cellHeader = Constant.EXCEL_RECORD_DETAIL;
				String[] cellValue = Constant.EXCEL_RECORD_VALUE;
				//定义文件名
				String sheetName = "员工考勤信息列表";
				HSSFWorkbook workbook = ExplortExcel.creatWorkbookMap(list, sheetName, cellHeader, cellValue);
				
				response.setBufferSize(100*1024);
				
				workbook.write(response.getOutputStream());
				//弹出另存为窗口
				super.setResponseHeader(response, "员工考勤信息列表"+GlobalMethod.getTime2()+".xls");
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e){
				log.error("导出员工excel出错，详细信息："+e.getMessage());
				e.printStackTrace();
			}
			return null;
	}
}
