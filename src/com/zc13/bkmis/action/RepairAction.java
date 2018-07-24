package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.bkmis.service.IRepairService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author qinyantao Date：Dec 7, 2010 Time：13:35:50 PM
 */
public class RepairAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IRepairService iRepairService = null;

	/** 从spring容器里得到iCustomerRepairService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		iRepairService = (IRepairService) getBean("IRepairService");
	}

	/** 查询客户报修列表 */
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		RepairForm repairForm = (RepairForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		repairForm.setLpId(lpId);
		/** 到此为止 */
		String projectname = GlobalMethod.NullToSpace(request.getParameter("projectname"));
		String name = "";
		try {
			// 进行转码
			name = java.net.URLDecoder.decode(projectname, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		repairForm.setProjectname(name);
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		List<SerMaintainProject> list = null;
		try {
			list = iRepairService.getRepairList(repairForm, true);
			// 取总条数
			int totalcount = iRepairService.getCountList(repairForm);
			// 添加分页信息
			String htmlPagination = "&nbsp;";
			String params = "&projectname=" + name;
			if (null == list || list.size() <= 0) {// 如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/repair.do?method=getList" + params, 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/repair.do?method=getList" + params, Integer.parseInt(GlobalMethod.NullToParam(pagesize, "10")), Integer.parseInt(GlobalMethod.NullToParam(currentpage, "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("name", name);
			request.setAttribute("list", list);
		} catch (Exception e) {
			logger.error("高级查询失败!RoomManageAction.searchRoom().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，RoomManageAction.searchRoom()!", e);
		}

		return mapping.findForward("list");
	}

	// 增加客户报修项目
	public ActionForward addRepair(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		RepairForm form2 = (RepairForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		form2.setLpId(lpId);
		/** 到此为止 */
		try {
			Map map = (Map) request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form2.setUserName(userName);
			iRepairService.addRepair(form2);
			// writeLog(userName, "删除房间", "添加了报修项目NAME" + form2.getName() +
			// "信息", Contants.REPAIRS);
		} catch (Exception e) {
			logger.error("添加客户报修失败!iRepairService.addRepair(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加客户报修失败!iRepairService.addRepair(form2)!", e);
		}
		return null;
	}

	// 根据id得到客户报修项目
	public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		SerMaintainProject bean = null;
		try {
			bean = iRepairService.getRepair(id);
		} catch (Exception e) {
			logger.error("添加客户报修失败!iRepairService.addRepair(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加客户报修失败!iRepairService.addRepair(form2)!", e);
		}
		request.setAttribute("bean", bean);
		return mapping.findForward("edit");
	}

	// 根据id删除客户报修项目
	public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String ids = request.getParameter("ids");
		try {
			String[] strings = ids.split(",");
			for (int i = 0; i < strings.length; i++) {
				Map map = (Map) request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				iRepairService.delRepair(strings[i], userName);
			}
			response.sendRedirect("repair.do?method=getList");
		} catch (Exception e) {
			logger.error("删除客户报修失败!iRepairService.addRepair(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("删除客户报修失败!iRepairService.addRepair(form2)!", e);
		}
		return null;
	}

	// 编辑客户报修项目
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		RepairForm form2 = (RepairForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		form2.setLpId(lpId);
		/** 到此为止 */
		try {
			Map map = (Map) request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form2.setUserName(userName);
			iRepairService.editRepair(form2);

		} catch (Exception e) {
			logger.error("添加客户报修失败!iRepairService.addRepair(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加客户报修失败!iRepairService.addRepair(form2)!", e);
		}
		return null;
	}

	// 对Date的数据相减
	public ActionForward getManHour(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		PrintWriter out = null;
		String string = null;
		try {
			out = response.getWriter();
			string = GlobalMethod.minus(begin, end);
		} catch (Exception e) {
			logger.error("查询系统参数信息失败，SysParamManagerAction.getSysParam()。详细信息：" + e.getMessage());
			throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.getSysParam()!", e);
		}
		out.print(string);
		return null;
	}

	// 验证客户报修项目代码是否存在
	public ActionForward checkcode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		RepairForm repairForm = (RepairForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		repairForm.setLpId(lpId);
		/** 到此为止 */
		// String code = request.getParameter("code");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isExist = false;
		try {
			if(!GlobalMethod.NullToSpace(repairForm.getCode()).equals("")){
				List list = iRepairService.getRepairList(repairForm, false);
				if (list != null && list.size() > 0) {
					isExist = true;
				}
			}
		} catch (Exception e) {
			logger.error("查询系统参数信息失败，SysParamManagerAction.getSysParam()。详细信息：" + e.getMessage());
			throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.getSysParam()!", e);
		}
		out.print(isExist);
		return null;
	}

	// 验证客户报修项目名称是否存在
	public ActionForward checkname(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		RepairForm repairForm = (RepairForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		repairForm.setLpId(lpId);
		/** 到此为止 */
		String name = request.getParameter("name");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean isExist = false;

		try {
			if(!GlobalMethod.NullToSpace(repairForm.getName()).equals("")){
				List list = iRepairService.getRepairList(repairForm, false);
				if (list != null && list.size() > 0) {
					isExist = true;
				}
			}
		} catch (Exception e) {
			logger.error("查询系统参数信息失败，SysParamManagerAction.getSysParam()。详细信息：" + e.getMessage());
			throw new BkmisWebException("查询系统参数信息失败，SysParamManagerAction.getSysParam()!", e);
		}
		out.print(isExist);
		return null;
	}

	// 查询报修项目信息，导出报表
	public ActionForward explorReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		RepairForm repairForm = (RepairForm)form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		repairForm.setLpId(lpId);
		/** 到此为止 */
		String projectname = GlobalMethod.NullToSpace(request.getParameter("projectname"));
		String name = "";
		try {
			// 进行转码
			name = java.net.URLDecoder.decode(projectname, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		repairForm.setProjectname(name);
		try {
			List<SerMaintainProject> list = iRepairService.getRepairList(repairForm, false);
			// 表头
			String[] cellHeader = Constant.EXCEL_REPAIREPROJECT_DETAIL;
			String[] cellValue = Constant.EXCEL_REPAIREPROJECT_VALUE;
			// 定义文件名
			String sheetName = "报修项目维护";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new SerMaintainProject());
			response.setBufferSize(100 * 1024);
			workbook.write(response.getOutputStream());
			// 弹出另存为窗口
			super.setResponseHeader(response, "报修项目维护" + GlobalMethod.getTime2() + ".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error("导出报修项目excel出错，详细信息：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
