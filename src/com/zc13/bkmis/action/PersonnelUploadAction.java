package com.zc13.bkmis.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PersonnelUploadAction extends BasicAction{

	public ActionForward upLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

			String realpath = request.getParameter("path");
			String s = java.net.URLDecoder.decode(realpath, "UTF-8");
			System.out.println(s);
			String filename = s.substring(0, s.lastIndexOf("\\"));
			String realfilename = s.substring(filename.length()+1,s.length());
			System.out.print(realfilename);
			File file=new File(s);
			if(file.exists()){
				//写明要下载的文件的大小
				System.out.print(file);
				response.setContentLength((int)file.length());
				//设置附加文件名即文件保存对话框
				//response.setHeader("Content-Disposition","attachment;filename="+filename);
				response.setHeader("Content-Disposition","attachment;filename="+new String (realfilename.getBytes("gb2312"),"iso-8859-1"));
				//读出文件到i/o流
				FileInputStream fis=new FileInputStream(file);
				BufferedInputStream buff=new BufferedInputStream(fis);
				byte[] b=new byte[1024];//相当于我们的缓存
				long k=0;//该值用于计算当前实际下载了多少字节
		    	//从response对象中得到输出流,准备下载
				OutputStream myout=response.getOutputStream();
				//开始循环下载
				while(k<file.length()){
		        int j=buff.read(b,0,1024);
		        k+=j;
		        //将b中的数据写到客户端的内存
		        myout.write(b,0,j);
				}
				//将写入到客户端的内存的数据,刷新到磁盘
				myout.flush();
				return null;
			}else{
				System.out.println("不存在!");
				return mapping.findForward("error");
			}
		}
	//对存入数据库文件的下载
	public ActionForward loadExitsFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
			String spath = request.getParameter("path");
			String s = java.net.URLDecoder.decode(spath, "UTF-8");
//			String name = request.getParameter("name");
//			String realpath = new String(spath.getBytes("iso-8859-1"),"utf-8");
//		    String filename = new String(name.getBytes("iso-8859-1"),"utf-8");
			String filename = s.substring(0, s.lastIndexOf("\\"));
			String realfilename = s.substring(filename.length()+1,s.length());
			File file=new File(s);
			if(file.exists()){
			//写明要下载的文件的大小
				System.out.print(file);
				response.setContentLength((int)file.length());
				//设置附加文件名即文件保存对话框
				//response.setHeader("Content-Disposition","attachment;filename="+filename);
				response.setHeader("Content-Disposition","attachment;filename="+new String (realfilename.getBytes("gb2312"),"iso-8859-1"));
				//读出文件到i/o流
				FileInputStream fis=new FileInputStream(file);
				BufferedInputStream buff=new BufferedInputStream(fis);
				byte[] b=new byte[1024];//相当于我们的缓存
				long k=0;//该值用于计算当前实际下载了多少字节
		    	//从response对象中得到输出流,准备下载
				OutputStream myout=response.getOutputStream();
				//开始循环下载
				while(k<file.length()){
		        int j=buff.read(b,0,1024);
		        k+=j;
		        //将b中的数据写到客户端的内存
		        myout.write(b,0,j);
				}
				//将写入到客户端的内存的数据,刷新到磁盘
				myout.flush();
				return null;
			}else{
				System.out.println("不存在");
				return mapping.findForward("error");
			}
	}
}
