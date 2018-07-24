package com.zc13.util.cacu;

import java.util.ArrayList;
import java.util.List;
/**
 * 解析字符串数学表达式，并返回计算结果
 * 只对简单的表达式有效，表达式包括的字符有：+*-/()和double类型的数字
 * @author 王正伟
 * Date：Dec 21, 2010
 * Time：3:07:45 PM
 */
public class Cacu {  
	
	/**
	 * 
	 * @param source
	 * 		字符串的数学表达式 e.g:80.0*56.6-12*(22.2-9.4)
	 * @return
	 * 		字符串表达式计算得到的结果
	 */
    public static double cacu(String source) { 
    	InToPost post=new InToPost();
    	List sourceList = new ArrayList();
    	sourceList = post.doTrans(source);
    	//System.out.println("处理过后的："+sourceList);
    	CacuStack cs = new CacuStack();  
        for(int i = 0;i<sourceList.size();i++){
        	String character = (String)sourceList.get(i);
        	if(!character.equals("+")&&!character.equals("-")&&!character.equals("*")&&!character.equals("/")&&!character.equals("(")&&!character.equals(")")){
        		cs.push(Double.parseDouble(character));
        	}else{
        		double num2 = cs.pop(); 
        		double num1 = cs.pop(); 
        		if(character.equals("+"))
        			cs.push(num1+num2);
        		if(character.equals("-"))
        			cs.push(num1-num2);
        		if(character.equals("*"))
        			cs.push(num1*num2);
        		if(character.equals("/"))
        			cs.push(num1/num2);
        	}
        }
        return cs.pop();  
    }  
      
    public static void main(String[] args) {  
       // System.out.println("结果："+Cacu.cacu("80.8"));  
          
    }  
}  

