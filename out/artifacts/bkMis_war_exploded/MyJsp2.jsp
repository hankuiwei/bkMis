 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>

<%@ page import="net.sf.jasperreports.engine.export.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@page import="java.net.URLEncoder"%>


<%
	try {
		File reportFile = null;
		//交费单
        reportFile  =   new   File(application.getRealPath("/beikong.jasper"));

	    //填充数据
	    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath()); 
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap() );
	    
		//PDF报表
	    byte bytes[] = JasperRunManager.runReportToPdf(reportFile.getPath(),new HashMap());
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition","attachment;filename=\""
                            + URLEncoder.encode("报修派遣单", "UTF-8") + ".pdf\"");
	    OutputStream output = response.getOutputStream();
	    output.write(bytes,0,bytes.length);
	    output.flush();
	    output.close();
	    
	    out.clear();
		out = pageContext.pushBody();
	    
	
	  } 
	  catch(Exception ex){ 
		  ex.printStackTrace();
	}
%> 