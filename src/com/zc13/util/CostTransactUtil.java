package com.zc13.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 费用处理的工具类
 * @author 王正伟
 * Date：Dec 22, 2010
 * Time：11:04:40 AM
 */
public class CostTransactUtil {
	/**
	 * 拆分公式
	 * 将公式拆分成参数数组
	 * eg:公式“{计费面积}*{单价}-12”可以拆分为"计费面积"和"单价"
	 * @param expressions 如：{计费面积}*{单价}-12
	 * @return 如：{计费面积,单价}
	 */
	public static List splitExpressions(String expressions){
		List<Integer> startIndexs = new ArrayList<Integer>();//保存'{'的位置数组
		List<Integer> endIndexs = new ArrayList<Integer>();//保存'}'的位置数组
		int appointIndex = 0;
		while(expressions.indexOf('{', appointIndex)>=0){
			int startIndex = expressions.indexOf('{', appointIndex);
			int endIndex = expressions.indexOf('}', startIndex);
			startIndexs.add(startIndex);
			endIndexs.add(endIndex);
			appointIndex = startIndex+1;
		}
		List paramNames = new ArrayList();//保存"计费面积"和"单价"等参数名称
		for(int i = 0;i<startIndexs.size();i++){
			paramNames.add(expressions.substring(startIndexs.get(i)+1, endIndexs.get(i)));
		}
//		System.out.println(paramNames);
		return paramNames;
	}
	
	/**
	 * 将sql中的参数替换成该参数对应的params中key对应的value的值
	 * @param params params的格式为:{costId=11,costType=elec,......} costId和costType分别对应于sql中的costId和costType
	 * @param sql(传入的sql语句的格式为：select name from test where id={costId} and type='{costType}')
	 * @return String 返回的是组装好的sql语句
	 */
	public static String assemblySql(Map params,String sql){
		String returnSql = GlobalMethod.NullToSpace(sql);
		if(params!=null&&!returnSql.equals("")){
			Set keySet = params.keySet();
			Iterator it = keySet.iterator();
			String key = "";
			String value = "";
			while(it.hasNext()){
				key = GlobalMethod.ObjToStr(it.next());
				value = GlobalMethod.ObjToStr(params.get(key));
				returnSql = replace(returnSql,"{"+key+"}",value);
			}
		}
		return returnSql;
	}
	
	/**
	 * 将line字符串中的子字符串oldString全部替换成newString
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static String replace(String line, String oldString, String newString) {
		line = GlobalMethod.NullToSpace(line);
		int i = 0;
		if (!line.equals("")&&(i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray(); // 字符串放入数组

			char[] newString2 = newString.toCharArray(); // 要替换的字符串
			int oLength = oldString.length(); // 被替换的字符串的长度
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			line = buf.toString();
		}
		return line;
	}
}
