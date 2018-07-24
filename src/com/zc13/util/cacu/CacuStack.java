package com.zc13.util.cacu;
/**
 * 
 * @author 王正伟
 * Date：Dec 21, 2010
 * Time：3:07:45 PM
 */
public class CacuStack {  
	  
    CacuNode top;//保存栈中最顶层的数据  
      
    public CacuStack() {  
        top = null;  
    }  
      
    public boolean isEmpty() {  
          
        return top==null;  
    }  
    
    //入栈
    public boolean push(double data) {  
        CacuNode node = new CacuNode(data);  
        if(isEmpty()) {//如果栈中为空 ，及顶层节点为空
            top = node;  //将入栈的节点设为顶层节点
            return true;  
        }else {  //如果栈不为空
            node.next=top;  //将顶层节点设为新入栈节点的下一个节点
            top = node;  //将新入栈的节点设为顶层节点
            return true;  
        }  
          
    }  
    
    //出栈
    public double pop() {  
    	double data = top.data;//得到栈中最顶层的数据  
        top = top.next;  //将栈中下一个节点设为顶层节点
        return data;  
    }  
      
    public static void main(String[] args) {  
  
        CacuStack stack=new CacuStack ();  
        stack.push(1);  
        stack.push(2);  
        stack.push(3);  
        stack.push(4);  
        stack.pop();  
        stack.pop();  
        while(!stack.isEmpty())  
        {  
           // System.out.println(stack.pop());  
        }  
    }  
} 
