package com.zc13.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	protected String encoding = "UTF-8";

    protected FilterConfig filterConfig = null;

    protected boolean ignore = true;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null){
            this.ignore = true;
        }    
        else if (value.equalsIgnoreCase("true")){
            this.ignore = true;
        }
        else if (value.equalsIgnoreCase("yes")){
            this.ignore = true;
        }
        else
            this.ignore = false;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // Conditionally select and set the character encoding to be used
    	// MessageSender.getInstance();
    	//System.out.println("******************客户端ip：*************"+request.getRemoteAddr());
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null) {
                request.setCharacterEncoding(encoding);
//                response.setCharacterEncoding(encoding);
                response.setContentType(encoding);
            }

        }
        // Pass control on to the next filter
        chain.doFilter(request, response);
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }
}