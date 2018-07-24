package com.zc13.bkmis.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;

import com.zc13.bkmis.form.LpForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ILpManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * @author 侯哓娟
 * Date：Nov 17, 2010
 * Time：4:08:41 PM
 */
public class LpManageAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());

	/** 从spring容器中得到注入的Service */
	private ILpManageService ilpManageService = null;
	public void setServlet(ActionServlet actionServlet){
		
		super.setServlet(actionServlet);
		ilpManageService = (ILpManageService)getBean("ilpManageService");
	}
	
	/**
	 * 导出楼盘列表excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returna
	 */
	public ActionForward exportLpExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			//list中存放的就是当前页面上显示的所有数据
//			List list = (List)request.getSession().getAttribute("lpList");
			List list = ilpManageService.getLp();
			//表头
			String[] cellHeader = Constant.EXCEL_LP_HEADER;
			String[] cellValue = Constant.EXCEL_LP_VALUE;
			//定义文件名
			String sheetName = "楼盘信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new ELp());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "楼盘信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出楼盘excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/** 得到楼盘信息列表 */
	public ActionForward getLpList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<ELp> lpList = null;
		LpForm lpForm = (LpForm)form;
		
		try{
			lpList = ilpManageService.getLp(lpForm);
			//取总条数
			int totalcount = ilpManageService.queryCounttotal(lpForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == lpList || lpList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/lp.do?method=getLpList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/lp.do?method=getLpList", Integer.parseInt(GlobalMethod.NullToParam(lpForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(lpForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("楼盘信息查询失败!LpManageAction.getLpList()。详细信息：" + e.getMessage());
		}
		request.getSession().setAttribute("lpList", lpList);
		request.setAttribute("size", lpList.size()); // list的长度，以便导出excel的时候判断有无数据
		return mapping.findForward("lpList");
	}
	
	/** 根据楼盘名称查询楼盘信息,因为查询条件就只有一个楼盘名称…… */
	public ActionForward queryLpByName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		List<ELp> lpList = null;
		try {
			String lpName = lpForm.getLpName();
			lpList = ilpManageService.queryLpByName(lpName);
		} catch (Exception e) {
			logger.error("楼盘信息查询失败!LpManageAction.queryLpByName()。详细信息：" + e.getMessage());
		}
		request.setAttribute("lpList", lpList);
		return mapping.findForward("lpinfo");
	}
	
	/** 跳至添加楼盘页面 */
	public ActionForward goAddLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("add");
	}
	
	/** 添加楼盘 */
	public ActionForward addLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LpForm lpForm = (LpForm)form;
		try {
			
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			lpForm.setUserName(userName);
			//writeLog(userName, "添加楼盘", "所添加的楼盘ID为" + lpForm.getLpId() + "信息", Contants.BUILD);
			/* 添加楼盘 */
			ilpManageService.addLp(lpForm);
		
		} catch (Exception e) {
			logger.error("楼盘信息添加失败!LpManageAction.addLp()。详细信息：" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("flag", true);
		return mapping.findForward("add");
	}
	
	/** 检查数据库里是否已经存在用户想要添加的楼盘 */
	public ActionForward checkLpName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		ELp elp = new ELp();
		try {
			BeanUtils.copyProperties(elp,lpForm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String forward = request.getParameter("forward");
		PrintWriter out = null;
		try {
			boolean arg = ilpManageService.checkLpName(elp.getLpName());
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addLp".equals(forward)){
					out.print(false);
				}
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.checkLpName()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息检查失败!LpManageAction.checkLpName()!",e);
		}
		return null;
	}
	
	/** 获得修改楼盘信息所需的信息 */
	public ActionForward getModifyInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Integer lpId = 0;
		try {
			String temp = GlobalMethod.NullToSpace(request.getParameter("lpId"));
			if(!"".equals(temp)){
				lpId = Integer.parseInt(temp);
			}
			ELp elp = ilpManageService.getELpById(lpId);
			/* 根据楼盘id得到相应的表具信息 */
			List meterlist = ilpManageService.getEMeterByLpId(lpId);
			request.setAttribute("meterlist", meterlist);
			request.setAttribute("elp", elp);
		} catch (Exception e) {
			logger.error("根据id获取楼盘信息失败!LpManageAction.getModifyInfo()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息查询失败!LpManageAction.getModifyInfo()!",e);
		}
		return mapping.findForward("modify");
	}
	
	/** 修改楼盘信息 */
	public ActionForward modifyLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		try{
			
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			lpForm.setUserName(userName);
			ilpManageService.modifyLp(lpForm);
			
		}catch(Exception e){
			logger.error("楼盘信息修改失败!LpManageAction.modifyLp()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息查询失败!LpManageAction.modifyLp()!",e);
		}
		request.setAttribute("flag", true);
		return mapping.findForward("modify");
	}
	
	/**删除楼盘  */
	public ActionForward deleteLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String[] lpId = request.getParameterValues("lpId");
		List<String> idList = Arrays.asList(lpId);
		try {
			if(!idList.isEmpty()){
				
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				
				ilpManageService.deleteLp(idList,userName);
				/* 删除对应楼盘下的表具信息 */
				ilpManageService.deleteMeterBylpId(idList);
				response.sendRedirect("lp.do?method=getLpList");
			}
		}catch (Exception e) {
			logger.error("楼盘信息删除失败!LpManageAction.deleteLp()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息查询失败!LpManageAction.deleteLp()!",e);
		}
		return null;
	}
	
	/** 文件上传 */
	public ActionForward upload(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm uploadForm = (LpForm)form;
		//得到上传文件
		FormFile file = uploadForm.getFile();
		//得到当前系统环境的根路径
		String filePath = this.getServlet().getServletContext().getRealPath("/");
		/** 判断上传文件是否为空 */
		if(file == null || file.getFileSize() == 0 ){
			return null;
		}else{
			try{
				InputStream stream = file.getInputStream();
				//得到上传文件的名称
				String filename = file.getFileName();
				//截取文件名的后缀
				String extention = filename.substring(filename.lastIndexOf('.'));
				//创建一个文件类型的对像,用于创建上传文件的存放路径
				File f = new File(filePath + "/upload");
				//判断file指定的文件路径是否存在，不存在就根据指定的抽象路径名创建目录
				if(!f.exists()){
					f.mkdir();
				}
				//new一个文件类型的用于存放上传文件的对象
				File realFile = new File(filePath + "/upload" +"/"+ filename);
				//把要上传的文件用输出字节流写出去
				OutputStream out = new FileOutputStream(realFile);
				out.write(file.getFileData());
				out.flush();
				out.close();
				//把文件类型的对象转换称字符串的，用于在页面显示
				String path = realFile.toString();
				String mapUrl = null;
				response.getWriter().println(path);
			}catch(Exception e){
					logger.error("楼盘效果图片上传失败!LpManageAction.upload()。详细信息：" + e.getMessage());
					throw new BkmisWebException("楼盘效果图片上传失败!LpManageAction.upload()!",e);
				}
			}
		return null;
		
	}
	
	/** 图片的删除 */
	public ActionForward delPic(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

		String picpath = null;
		picpath = request.getParameter("path");
		String str1 = picpath.substring(0, picpath.lastIndexOf("/") + 1);
		/* 截取报存上传文件夹的名字 */
		String folder = str1.substring(1, str1.lastIndexOf("/"));
		/* 截取上传文件的名字 */
		String uploadname = picpath.substring(str1.length());
		/* 得到当前系统的根路径 */
		String filePath = this.getServlet().getServletContext()
				.getRealPath("/");
		String realpath = filePath + folder + "\\" + uploadname;
		// String realpath = filePath +picpath;
		File file = new File(realpath);
		boolean test = file.exists();
		if (!file.exists()) {
			response.getWriter().println("1");
		} else {
			/* 删除图片 */
			file.delete();
			response.getWriter().println("0");
		}

		return null;
	}
	
	/** 调至添加表具页面 */
	public ActionForward goaddMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		/* 得到表具类型 */
		getMeterType(mapping,form,request,response);
		return mapping.findForward("bj");
	}
	
	/** 从数据库里获得表具类型 */
	public ActionForward getMeterType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<SysCode> metertypelist = null;
		try{
			metertypelist = ilpManageService.getMeterType();
			request.setAttribute("metertypelist", metertypelist);
		} catch (Exception e) {
			logger.error("表具类型查询失败!LpManageAction.getMeterType()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具类型查询失败!LpManageAction.getMeterType()!",e);
		}
		return null;
	}
	
	/** 检查数据库里是否已经存在用户想要添加的表具,根据表具编号 */
	public ActionForward checkMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		String forward = request.getParameter("forward");
		PrintWriter out = null;
		try {
			boolean arg = ilpManageService.checkMeter(lpForm);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addMeter".equals(forward)){
					out.print(false);
				}
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息检查失败!LpManageAction.checkMeter()!",e);
		}
		return null;
	}
	
	/** 添加表具 */
	public ActionForward addMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		try {
			
			/*//加入日志的管理中
			String meterCode = lpForm.getCode();
			String meterName = lpForm.getType();
			Integer meterId = 0;
			List<EMeter> list = ilpManageService.getMeterByCodeAndName(meterCode, meterName);
			for(EMeter m:list){
				meterId = m.getId();
			}*/
			
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			lpForm.setUserName(userName);
			ilpManageService.addMeter(lpForm);
			//writeLog(userName, "添加表具", "所添加的表具ID为:" + meterId + "信息", Contants.BUILD);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("楼盘信息添加失败!LpManageAction.addMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息添加失败!LpManageAction.addMeter()!",e);
		}
		return null;
	}
	
	/** 从系统参数里获取表的名称，用于页面显示 */
	public ActionForward getMeterName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		PrintWriter out = null;
		try {
			String meterName = ilpManageService.getMeterName(lpForm);
			out = response.getWriter();
			if(meterName != null){
				out.print(meterName);
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息检查失败!LpManageAction.checkMeter()!",e);
		}
		return null;
	}
	
   /** 删除表具信息 */
	public ActionForward deleteMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String code = GlobalMethod.NullToSpace(request.getParameter("code"));
	    String type = GlobalMethod.NullToSpace(request.getParameter("type"));
	    String[] rCode = null;
	    String[] rType = null;
		String meterCode = GlobalMethod.NullToSpace(request.getParameter("meterCode"));
	    String meterType = GlobalMethod.NullToSpace(request.getParameter("meterType"));
		try {
//			if(!"".equals("meterCode")&& !"".equals(meterType)){
				/* 用来得到一个EMeter对象的id，做添加日志之用*/
//				Integer meterId = 0;
//				List<EMeter> list = ilpManageService.getMeterByCodeAndName(meterCode, meterType);
//				for(EMeter m:list){
//					meterId = m.getId();
//				}
				/* 删除表具信息 */
				if(!"".equals(code) && !"".equals(type)){
					rCode = code.split(";");
					rType = type.split(";");
					for(int i=0,j=0;i<rCode.length && j<rType.length;i++,j++){
						ilpManageService.deleteMeter(rCode[i],rType[j]);
					}
				}else{
					if(!"".equals(meterCode) && !"".equals(meterType)){
						ilpManageService.deleteMeter(meterCode,meterType);
					}
//				}
				
				//加入日志的管理中
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
//				writeLog(userName, "删除表具", "删除了表具ID为" + meterId + "信息", Contants.BUILD);
			}
		}catch(Exception e){
			logger.error("楼盘信息删除失败!LpManageAction.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息查询失败!LpManageAction.deleteMeter()!",e);
		}
		return null;
	}
	
	/** 根据表具的名称得到系统参数表里对应的codeValue值 */
	public ActionForward getMeterTypeByCodeName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String meterName = GlobalMethod.NullToSpace(request.getParameter("meterName"));
		PrintWriter out = null;
		try {
			String meterType = ilpManageService.getMeterTypeByCodeName(meterName);
			out = response.getWriter();
			if(meterType != null){
				out.print(meterType);
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.getMeterTypeByCodeName()。详细信息：" + e.getMessage());
		}
		return null;
	}
	
	//*******************************往后的都是董道奎在实现多楼盘功能的时候添加的方法***************************//
	//*******************************************************************************************************//
	/**
	 * 根据用户信息所属的楼盘id得到楼盘信息，然后跳转到单个楼盘的查询页面。
	 */
	public ActionForward getLpById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String forward = "looklp";
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		/** 到此为止*/
		ELp elp = ilpManageService.getELpById(lpId);
		List meterlist = null;
		meterlist = ilpManageService.getEMeterByLpId(lpId);
		String keyString = GlobalMethod.NullToParam(request.getParameter("key"), "0");
		if("1".equals(keyString)){//判断是否是要跳转到修改页面
			forward="editlp";
		}
		request.setAttribute("elp", elp);
		request.setAttribute("meterlist", meterlist);
		return mapping.findForward(forward);
		
	}

	/** 修改楼盘信息 */
	public ActionForward modifyLp2(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LpForm lpForm = (LpForm)form;
		try{
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			lpForm.setUserName(userName);
			ilpManageService.modifyLp(lpForm);

			
		}catch(Exception e){
			logger.error("楼盘信息修改失败!LpManageAction.modifyLp()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息查询失败!LpManageAction.modifyLp()!",e);
		}
		return getLpById( mapping, form,
				 request,  response);
	}
}











