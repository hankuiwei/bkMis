package com.zc13.util;
/**
 * 分页Servlet
 * @author daokui
 * Date：Nov 29, 2010
 * Time：11:01:45 AM
 */
public class PaginationHTMLUtil {
	public static String getPaginationHTML(String actionPath, int pagesize,
			int currentpage, int totalcount) {
		StringBuffer html = new StringBuffer();
		String tmppage = actionPath;
		/*if (totalcount <= 0) {
			//没有记录
			html
					.append("&nbsp;&nbsp;首页&nbsp;&nbsp;")
					.append("上一页&nbsp;&nbsp;")
					.append("下一页&nbsp;&nbsp;")
					.append("尾页&nbsp;&nbsp;")
					.append(
							"共&nbsp;<select name=\"totalpage\" style=\"width:40\"><option></option></select>&nbsp;页&nbsp;&nbsp;");
		*///} else {
			//计算总页数
			if (pagesize <= 0)
				pagesize = 10;
			int totalpage = 0;
			if(totalcount%pagesize==0){
				totalpage = totalcount / pagesize;
			}else{
				totalpage = totalcount / pagesize + 1;
			}
			tmppage = "&nbsp;&nbsp;<a href=\"javascript:onClick=goPage('" ;
			
			if (currentpage <= 1) {
				//当前是第一页，首页、上一页就没有了
				html.append("&nbsp;&nbsp;首页&nbsp;&nbsp;上一页&nbsp;&nbsp;");				
			} else {
				//不是第一页，就可以访问、首页上一页了
				html.append(tmppage +1+ "')\">首页</a>&nbsp;&nbsp;");				
				html.append(tmppage + (currentpage - 1)+ "')\">上一页</a>&nbsp;&nbsp;");
			}

			if (currentpage < totalpage) {
				//当前页小于最大页数，可以访问下一页
				html.append(tmppage + (currentpage + 1)+ "')\">下一页</a>&nbsp;&nbsp;");
			}
			else {
				//否则，只能访问最后一页了
				html.append("下一页&nbsp;&nbsp;");
			}
			
			if (currentpage < totalpage) {
				html.append(tmppage + totalpage + "')\">尾页</a>&nbsp;&nbsp;");
			}
			else {
				html.append("尾页&nbsp;&nbsp;");
			}
			html.append("共&nbsp;"+totalpage+"&nbsp;页&nbsp;&nbsp;");
			html.append("&nbsp;"+totalcount+"&nbsp;条记录&nbsp;&nbsp;");
			
			html.append("&nbsp;&nbsp;当前第&nbsp;<select name=\"totalpage\"  style=\"width:40\" onChange=\"goPage(this.value)\">");
			
			for(int i=0;i<totalpage;i++) {
				if (currentpage == (i+1)) {
					html.append("<option value=\""+(i+1)+"\" selected>"+(i+1)+"</option>");
				}
				else {
					html.append("<option value=\""+(i+1)+"\">"+(i+1)+"</option>");
				}
			}
			
			html.append("</select>&nbsp;页&nbsp;&nbsp;");


			html.append("&nbsp;&nbsp;&nbsp;&nbsp;每页显示<input type = \"text\"  onKeyUp=\"this.value=this.value.replace(/\\D/g,'')\"  value = \""+pagesize+"\" name=\"pagesize\" size = \"1\" />条  ");
			html.append(" <input type =\"button\" value = \"确定\" onclick = \"goPage(1)\">");
			html.append(" <input type =\"hidden\" name =\"currentpage\" value = \"1\" >");
			html.append("<script language=\"javascript\">");
			html.append("function goPage(currentpage) {");

			html.append("    actionUrl=encodeURI(encodeURI('"+actionPath+"'));");
			if (actionPath.indexOf("?") > 0) {
				html.append("	document.forms[0].action=actionUrl+")
					.append("\"&currentpage=\"+currentpage;");
				
			}
			else {
				html.append("	document.forms[0].action=actionUrl+")
					.append("\"?currentpage=\"+currentpage;");
			}
			html.append("	document.forms[0].submit();setTimeout('showLoadingDiv()',10);");
			
			html.append("}");
			html.append("</script>");

		//}

		return html.toString();
	}
}
