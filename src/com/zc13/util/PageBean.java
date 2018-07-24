/**
 * Administrator
 */
package com.zc13.util;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable {

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getEndPage() {
		return checkFirstPage(pagination) * rowsOnepage;
	}

	public int getStartPage() {
		return (checkFirstPage(pagination) - 1) * rowsOnepage ;
	}

	public int getTatol() {
		return tatol;
	}

	public void setTatol(int tatol) {
		this.tatol = tatol;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public int getTotalpage() {
		if (tatol > 0 && tatol <= rowsOnepage)
			return 1;
		if (tatol % rowsOnepage > 0)
			return tatol / rowsOnepage + 1;
		else
			return tatol / rowsOnepage;
	}

	public int getRowsOnepage() {
		return rowsOnepage;
	}

	public void setRowsOnepage(int rowsOnepage) {
		this.rowsOnepage = rowsOnepage;
	}

	public PageBean(int rowsOnepage) {
		this.rowsOnepage = 10;
		this.rowsOnepage = rowsOnepage;
	}

	public String toString(String queryFunction) {
		StringBuffer textHtml = new StringBuffer();
//		String FIRSTPAGINATIONVALUE = "1";
//		String FORM = "document.forms[0]";
//		String PAGINATION_VALUE = "document.forms[0].pagination.value";
		String FUNCTION_QUERY = null;
		if (queryFunction == null || "".equals(queryFunction))
			FUNCTION_QUERY = "document.forms[0].submit();";
		else
			FUNCTION_QUERY = queryFunction;
		int currentPagination = checkFirstPage(pagination);
		int TOTALPAGE = getTotalpage();
		currentPagination = checkGotoPageNum(currentPagination, TOTALPAGE);
		int PRIVIOUSPAGE = currentPagination - 1;
		int NEXTPAGE = currentPagination + 1;
		textHtml.append("<input type=\"hidden\" name=\"pagination\"> ");
		totalPageDisplay(textHtml, TOTALPAGE, currentPagination);
		if (PRIVIOUSPAGE <= 0) {
			textHtml.append(" <a  > 首页 </a> ");
			textHtml.append(" <a  >上一页 </a> ");
		} else {
			textHtml
					.append(" <a href=\"javascript:document.forms[0].pagination.value=1;"
							+ FUNCTION_QUERY + "\"  >首页</a> ");
			textHtml
					.append(" <a href=\"javascript:document.forms[0].pagination.value="
							+ PRIVIOUSPAGE
							+ ";"
							+ FUNCTION_QUERY
							+ "\" >上一页</a> ");
		}
		if (NEXTPAGE > TOTALPAGE) {
			textHtml.append("<a  > 下一页 </a>");
			textHtml.append("<a  > 尾页 </a>");
		} else {
			textHtml
					.append(" <a href=\"javascript:document.forms[0].pagination.value="
							+ NEXTPAGE + ";" + FUNCTION_QUERY + "\" >下一页</a> ");
			textHtml
					.append(" <a href=\"javascript:document.forms[0].pagination.value="
							+ TOTALPAGE + ";" + FUNCTION_QUERY + "\">尾页</a> ");
		}
		textHtml.append("&nbsp;&nbsp;共&nbsp;"+getTotalpage()+"&nbsp;页&nbsp;&nbsp;");
		textHtml.append("&nbsp;"+tatol+"&nbsp;条记录&nbsp;&nbsp;");
		
		selectGoto(textHtml, "document.forms[0].pagination.value",
					FUNCTION_QUERY, TOTALPAGE, currentPagination);
		
		textHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;每页显示<input type = \"text\" onKeyUp=\"this.value=this.value.replace(/\\D/g,'')\" " +
				"value = \""+rowsOnepage+"\" name=\"pagesize\" size = \"1\" />条  ");
		textHtml.append(" <input type =\"button\" value = \"确定\" " +
				"onclick = \"javascript:document.forms[0].pagination.value=1;"
							+ FUNCTION_QUERY + "\" >");
		
//		if (TOTALPAGE <= 1)
//			textHtml = new StringBuffer("");
		return textHtml.toString();
	}

	private void selectGoto(StringBuffer textHtml, String PAGINATION_VALUE,
			String FUNCTION_QUERY, int TOTALPAGE, int currentPagination) {
		textHtml
				.append("当前第<select id=\"page\" name=\"page\" onChange=\"javascript:"
						+ PAGINATION_VALUE
						+ "=document.all.page.value;"
						+ FUNCTION_QUERY + "\">");
		for (int i = 1; i <= TOTALPAGE; i++)
			if (i == currentPagination)
				textHtml.append("<option value=" + i + " selected>" + i
						+ "</option>");
			else
				textHtml.append("<option value=" + i + ">" + i + "</option>");

		textHtml.append("</select>页");
		//textHtml.append("/");
		//textHtml.append(TOTALPAGE + "页");
	}

//	private void textGoto(StringBuffer textHtml, String PAGINATION_VALUE,
//			String FUNCTION_QUERY, int TOTALPAGE, int currentPagination) {
//		textHtml.append("第 <input type=\"text\" name=\"page\"\tvalue="
//				+ currentPagination + " style=\"width:20px\" />页");
//		textHtml.append("<a href=\"#\" onclick=\"javascript:"
//				+ PAGINATION_VALUE + "=document.all.page.value;"
//				+ FUNCTION_QUERY + "\">GO</a>");
//	}

	private void totalPageDisplay(StringBuffer textHtml, int TOTALPAGE,
			int currentPagination) {
		textHtml.append(currentPagination + "/" + TOTALPAGE);
	}

	private int checkGotoPageNum(int num, int total) {
		if (num <= 0)
			return num = 0;
		if (num >= total)
			return total;
		else
			return num;
	}

	private int checkFirstPage(String page) {
		int currentPagination = 1;
		if (page != null && !"".equalsIgnoreCase(page.trim()))
			try {
				currentPagination = (new Integer(page.trim())).intValue();
			} catch (Exception exception) {
			}
		return currentPagination;
	}

	public static void main(String args[]) {
		PageBean p = new PageBean(10);
		p.setTatol(20);
		p.setPagination("3");
		//System.out.println(p.toString("go()", "text"));
		System.out.println(p.getStartPage() + "  " + p.getEndPage());
	}

	private int tatol;
	private String pagination;
	private int rowsOnepage;
	private int startPage;
	private int endPage;
	private List list;
}