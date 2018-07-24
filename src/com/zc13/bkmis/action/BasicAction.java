package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.ExtendUtil;
import com.zc13.util.GlobalMethod;
import com.zc13.util.LogParam;

import net.sf.json.JSONObject;
/**
 * 所有action类的父类
 * @author daokui
 * Date:Oct 25, 2010
 * Time:9:48:35 PM
 */
public class BasicAction extends DispatchAction{

	private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
	
	//下面这些成员变量和setLogParam()方法都是为了写日志和存楼盘id的时候方便而写的
	protected String userName; 		//操作用户名
	protected Integer userId;			//操作人ID
	protected Integer lpId; 		    //用户所在楼盘
	protected LogParam logParam;
    /**
     * 填充日志参数对象，填充lpId的值，将当前用户信息、楼盘信息set进去
     * 执行完这个方法之后，就可以直接使用lpId,userName,userId三个变量了
     * @param request
     * Date:2011-8-25 
     * Time:上午10:24:35
     */
	public void setLogParam(HttpServletRequest request){
		
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		userId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userid").toString()),"0"));
		userName = GlobalMethod.NullToParam((map1.get("username").toString()),"0");
		logParam = new LogParam();
		logParam.setLpId(lpId);
		logParam.setUserId(userId);
		logParam.setUserName(userName);
	}

	/**
	 * 根据名称返回一个相应的bean
	 * @param id
	 * @return
	 */
	protected Object getBean(String id) {

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.servlet.getServletContext());

        return ctx.getBean(id);

	}
	/**
	 * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的具体内容
     * @param module 操作的系统模块
	 * @throws DataAccessException
	 */
	public void writeLog(String userName,String operateType,String operateContent,String module,String obj)throws DataAccessException { 
    	
		BasicDAO basicDAO =  (BasicDAOImpl)getBean("basicDAO");
		basicDAO.log(userName, operateType, operateContent, module,obj);

    }
	/**
	 * 
	 * 所有对数据库进行的增、删、改操作，都要调用此方法对日志存档
     * @param userName 操作用户
     * @param operateType 操作类型
     * @param operateContent 操作的物理对象（比如系统模块是房产模块的情况下，下面还有楼盘，房间等）
     * @param module 操作的系统模块
     * @param flag 类型：1,更新；2,新增；3,删除
     * @param afterModifyObj 修改之后的对象
     * @throws Exception
	 * Date:Apr 19, 2011 
	 * Time:10:01:31 AM
	 */
	public void logDetail(String userName, String operateType,
			String operateContent, String module,String flag,Object obj)throws BkmisWebException{
		
		BasicDAO basicDAO =  (BasicDAOImpl)getBean("basicDAO");
		
		try {
			basicDAO.logDetail(userName, operateType, operateContent, module, flag, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BkmisWebException("写系统日志出错！");
		}
		
	}
	/**
	 * 导出excel时弹出另存为以及设置文件名
	 * @param response 
	 * @param fileName 设置文件名
	 * @throws Exception
	 */
	public void setResponseHeader(HttpServletResponse response, String fileName)throws Exception {
		
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");//让浏览器知道要提示用户要下载的文件格式是excel
		response.setHeader("Content-Disposition", "attachment;filename=\""+ new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\"");// 解决excel文件乱码问题
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
	    response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 自定义token机制(需要在编辑页面定义一个名为my_Rtoken的隐藏域，其值通过request.getAttribute("my_Rtoken")得到)
	 * (wangzw)
	 * @param request
	 * @return
	 */
	public String my_saveToken(HttpServletRequest request){
		String token=String.valueOf(new Date().getTime());//IDUtil.getID();
		request.getSession().setAttribute("my_Stoken", token);
		request.setAttribute("my_Rtoken", token);
		return token;
	}
	
	/**
	 * 自定义token机制
	 * (wangzw)
	 */
	public boolean my_isTokenValid(HttpServletRequest request){
		boolean isTokenValid = false;
		String my_Stoken = ExtendUtil.null2str((String)request.getSession().getAttribute("my_Stoken"));
		String my_Rtoken = ExtendUtil.null2str(request.getParameter("my_Rtoken"));
		if(my_Stoken.equals(my_Rtoken)&&!my_Stoken.equals("")){
			isTokenValid = true;
		}
		request.getSession().removeAttribute("my_Stoken");
		return isTokenValid;
	}
	
	/**
	 * ajax render
	 * 
	 * @throws IOException
	 */
	public static void render(HttpServletResponse response,JSONObject json) throws IOException {
		response.setContentType(JSON_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}

