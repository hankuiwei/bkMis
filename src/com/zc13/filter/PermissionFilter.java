package com.zc13.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionFilter implements Filter {
	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		//System.out.println("JVM MAX MEMORY（JVM最大内存）: " + Runtime.getRuntime().maxMemory()/1024/1024+"M");
		//System.out.println("JVM IS USING MEMORY（JVM已用内存）:" + Runtime.getRuntime().totalMemory()/1024/1024+"M");
		//System.out.println("JVM IS FREE MEMORY（JVM剩余内存）:" + Runtime.getRuntime().freeMemory()/1024/1024+"M");
		/**
		 * doFilter方法的第一个参数为ServletRequest对象。此对象给过滤器提供了对进入的信息（包括
		 * 表单数据、cookie和HTTP请求头）的完全访问。第二个参数为ServletResponse，通常在简单的过
		 * 滤器中忽略此参数。最后一个参数为FilterChain，此参数用来调用servlet或JSP页。
		 */
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		/**
		 * 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中
		 * 无法得到的方法，就要把此request对象构造成HttpServletRequest
		 */
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径
		String targetURL = "";
		if(currentURL.startsWith(request.getContextPath()+"/adminLogin.do")){//如果当前是登录，那么放过
			
			filterChain.doFilter(request, response);
			return ;
		} else if (currentURL.contains("bkMis")) {//否则要进行验证
			targetURL = currentURL.substring(currentURL.indexOf("/", 1),
					currentURL.length());
		}
		if (null != currentURL) {
			String pathAry[] = currentURL.split("/");
			if(pathAry.length > 0){
				targetURL = "/" + pathAry[pathAry.length - 1];
			}
		}
		
		// 截取到当前文件名用于比较
		HttpSession session = request.getSession(false);
		//从session里取的用户名信息
	    HashMap map = session == null ? null : (HashMap) session.getAttribute("userInfo");
	    String username = (map == null) ? null : map.get("username") == null ? null : map.get("username").toString();
	    if (!"/login.jsp".equals(targetURL)&&!"/bkMis".equals(targetURL)) {
			// 判断当前页是否是重定向以后的登录页面，如果不是，做session的判断
			if (username == null || "".equals(username)) {
				// 用户登录以后需手动添加session
				response.getWriter().write("<script>parent.parent.parent.window.location.href='" + request.getContextPath() + "/login.jsp?flag=outTime" + "'</script>");
				//response.sendRedirect(request.getContextPath()+ "/adminLogin.jsp");
				// 如果session为空表示用户没有登录,就重定向到adminLogin.jsp页面
				return;
			}
		}
		// 加入filter链继续向下执行
		filterChain.doFilter(request, response);
		/**
		 * 调用FilterChain对象的doFilter方法。Filter接口的doFilter方法取一个FilterChain对象作 为它
		 * 的一个参数。在调用此对象的doFilter方法时，激活下一个相关的过滤器。如果没有另
		 * 一个过滤器与servlet或JSP页面关联，则servlet或JSP页面被激活。
		 */
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}