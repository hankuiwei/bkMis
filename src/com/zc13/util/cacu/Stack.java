package com.zc13.util.cacu;
/**
 * 
 * @author 王正伟
 * Date：Dec 21, 2010
 * Time：3:07:45 PM
 */
public class Stack {  
	  
    private Node top;  
    public Stack() {  
        top=null;  
    }  
      
    public boolean push(char data) {  
        Node newNode = new Node(data);  
        if(isEmpty()) {  
            top= newNode;  
            return true;  
        }else {  
            newNode.next = top;  
            top = newNode;  
            return true;  
        }  
    }  
      
    public char pop(){  
        char data = top.data;  
        top =top.next;  
        return data;  
    }  
      
    public boolean isEmpty() {  
        return top==null;  
    }  
      
    public static void main(String[] args) {  
        Stack stack=new Stack();  
        stack.push('a');  
        stack.push('b');  
        stack.push('c');  
        stack.push('d');  
        stack.pop();  
        stack.pop();  
        while(!stack.isEmpty())  
        {  
           // System.out.println(stack.pop());  
        }  
    }  
}  
