package com.zc13.util.cacu;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 王正伟
 * Date：Dec 21, 2010
 * Time：3:07:45 PM
 */
public class InToPost {  
	  
    List wordsList = new ArrayList();
    Stack stack;  
  
    public List doTrans(String source) {  
  
        stack = new Stack();
        String outputStr = "";
        for (int i = 0; i < source.length(); i++) {  
            char ch = source.charAt(i);  
            switch (ch) {  
	            case '+':  
	            case '-':  
	                doOper(ch,1); 
	                break;  
	            case '*':  
	            case '/':  
	                doOper(ch,2);
	                break;  
	            case '(':  
	                stack.push(ch);  
	                break;  
	            case ')':  
	                getParent();
	                break;  
	            default: 
        			outputStr = outputStr + ch;
        			if(i<source.length()-1){
        				char tempCh = source.charAt(i+1);
        				if(tempCh!='+'&&tempCh!='-'&&tempCh!='*'&&tempCh!='/'&&tempCh!='('&&tempCh!=')'){
        				}else{
        					wordsList.add(outputStr);
        					outputStr = "";
        				}
        			}else{
        				wordsList.add(outputStr);
        				outputStr = "";
        			}
	                break;  
            }  
        }  
//       将栈中全部取出  
        while (!stack.isEmpty()) {  
        	char ch = stack.pop();
            wordsList.add(ch+"");
            
        }  
        return this.wordsList;  
    }  
  
    private void getParent() {  
        while(!stack.isEmpty()) {  
            char ch = stack.pop();  
            if(ch=='('){  
                break;  
            }else{  
                wordsList.add(ch+"");
            }  
        }  
          
    }  
  
    public void doOper(char ch1, int lev) {  
  
        while (!stack.isEmpty()) {  
            char popch = stack.pop();  
            if (popch == '(') {//如果顶层节点是'('  则结束该循环
                stack.push(popch);  
                break;  
            } else {  
                int lev2;  
                if (popch == '+' || popch == '-') {  
                    lev2 = 1;  
                } else {  
                    lev2 = 2;  
                }  
  
                // 将栈中取出的与传入的比较优先级  
                if (lev > lev2) {  
                    stack.push(popch); // 将低级别的放回去  
                    break;  
                } else {  
                    wordsList.add(popch+"");
                }  
            }  
  
        }  
        stack.push(ch1);    //将高级别放在外层  
    }  
      
    public static void main(String[] args) {  
        InToPost post=new InToPost();  
        String temp="(a+b*c)*(e+f+m+n*j)";  
        String temp2 = "(12.5+32*5)*(2.1+1.1+3.1+2*33)";
        //abc*+ef+m+nj*+*
       // System.out.println(post.doTrans(temp2));
        //System.out.println(post.wordsList);
          
    }  
  
} 

