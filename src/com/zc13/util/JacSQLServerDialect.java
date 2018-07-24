package com.zc13.util;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;
/**
 * hibernate对sqlServer 2005版本以上，dialect方法按如下定义
 * @author daokui
 * Date：Nov 29, 2010
 * Time：5:44:20 PM
 */
public class JacSQLServerDialect extends SQLServerDialect {
    static int getLastIndexOfOrderBy(String sql){
        return sql.toLowerCase().lastIndexOf("order by ");
    }
    
    public JacSQLServerDialect(){
    	
    	//为了适应配合2008版本，加上下面的数据类型转换
    	super();
    	registerHibernateType(Types.CHAR, Hibernate.STRING.getName());      
    	registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());      
    	registerHibernateType(Types.LONGNVARCHAR, Hibernate.STRING.getName());      
    	registerHibernateType(Types.DECIMAL, Hibernate.DOUBLE.getName());      

    	
    	//registerHibernateType(3, "double");
    }
   

    public String getLimitString(String querySelect, int offset, int limit ){
        int lastIndexOfOrderBy = getLastIndexOfOrderBy(querySelect);
        //　没有 order by 或第一页的情况下
        if(lastIndexOfOrderBy<0 || querySelect.endsWith(")") || offset==0)
            return super.getLimitString(querySelect, 0, limit);
        else {
            //取出 order by 语句
            String orderby = querySelect.substring(lastIndexOfOrderBy, querySelect.length());
            //取出 from 前的内容
            int indexOfFrom = querySelect.toLowerCase().indexOf("from");
            String selectFld = querySelect.substring(0,indexOfFrom);
            //取出 from 语句后的内容
            String selectFromTableAndWhere = querySelect.substring(indexOfFrom, lastIndexOfOrderBy);
            StringBuffer sql = new StringBuffer(querySelect.length()+100);
            sql.append("select * from (")
                .append(selectFld)
                .append(",ROW_NUMBER() OVER(").append(orderby).append(") as _page_row_num_hb ")
                .append(selectFromTableAndWhere).append(" ) temp ")
                .append(" where  _page_row_num_hb BETWEEN  ")
                .append(offset+1).append(" and ").append(limit);
            return sql.toString();
        }
    }
    //使offset 参数生效
    public boolean supportsLimitOffset(){
        return true;
    }
}
