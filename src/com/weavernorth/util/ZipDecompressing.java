package com.weavernorth.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import weaver.general.BaseBean;
import weaver.general.Util;
/**
 * 解压缩文件方法封装
 * @author Dylan
 *
 */
public class ZipDecompressing {
	public String Decompre(String oldpath,String newpath,String strDocName){
		
		try {
			String name = ""; 
			ZipInputStream Zin=new ZipInputStream(new FileInputStream(oldpath));//输入源zip路径
			BufferedInputStream Bin=new BufferedInputStream(Zin);
			String Parent=newpath; //输出路径（文件夹目录）1370413753154.zip
			File Fout=null;   
			ZipEntry entry;
			try {
				while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
					String[] tep = Util.TokenizerString2(entry.getName(), "/");
					  
					if(tep.length>=2){   
					    name = tep[1];
					}
					else{
						name = entry.getName();
					}
					Fout=new File(Parent,strDocName);
					if(!Fout.exists()){
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out=new FileOutputStream(Fout);
					BufferedOutputStream Bout=new BufferedOutputStream(out);
					int b;
					while((b=Bin.read())!=-1){
						Bout.write(b);
					}
					Bout.close();
					out.close();
					//System.out.println(Fout+"解压成功");	
				}
				Bin.close();
				Zin.close();
				
				return name;
			} catch (IOException e) {
				e.printStackTrace();
				return "fail";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	public String Decompre2Ftp(String oldpath,String newpath,String name){
		String result = "";
		try {
			
			ZipInputStream Zin=new ZipInputStream(new FileInputStream(oldpath));//输入源zip路径
			BufferedInputStream Bin=new BufferedInputStream(Zin);
			String Parent=newpath; //输出路径（文件夹目录）1370413753154.zip
			File Fout=null;
			ZipEntry entry;
			try {
				while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
					Fout=new File(Parent,name);
					if(!Fout.exists()){
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out=new FileOutputStream(Fout);
					BufferedOutputStream Bout=new BufferedOutputStream(out);
					int b;
					while((b=Bin.read())!=-1){
						Bout.write(b);
					}
					Bout.close();
					out.close();
					System.out.println(Fout+"解压成功");	
					result = name;
				}
				Bin.close();
				Zin.close();
			} catch (IOException e) {
				new BaseBean().writeLog(e);
			
			}
		} catch (FileNotFoundException e) {
			new BaseBean().writeLog(e);  
			
		}
		return result;	
		
	}
	/*将文档转化为压缩包
	 * 
	 * */
	public String Decompre2Ftp(String oldpath,String newpath,String iszip,String name){
		System.out.println(iszip+"iszip"+"============");
		String result = "";
		try {
			//输入流
			ZipInputStream Zin=null;//解压缩输入流
			InputStream fileIo=null;//输入流
			BufferedInputStream Bin=null;//缓冲区输入流
			//输出流
			String Parent=newpath; //输出路径（文件夹目录）1370413753154.zip
			File Fout=null;
			ZipEntry entry;
			
			//当为压缩文件时需要用到解压缩输入流,文件输入流程，缓冲区输入流
			if("1".equals(iszip)){
			   Zin=new ZipInputStream(new FileInputStream(oldpath));//zip输入流+文件输入流
			   Bin=new BufferedInputStream(Zin);
				//当不为压缩文件时需要用到文件输入流程，缓冲区输入流 
			} else{
				 
				 fileIo=new FileInputStream(oldpath);//文件输入流程
			     Bin=new BufferedInputStream(fileIo);//缓冲区输入流
			 }
			
			
			try {
				 FileOutputStream out;
				 BufferedOutputStream Bout;
				 //进行压缩格式的解压，以及文件复制
			 if("1".equals(iszip)){
				   
					while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
						Fout=new File(Parent,name);
						if(!Fout.exists()){
							(new File(Fout.getParent())).mkdirs();
						}
					    out=new FileOutputStream(Fout);
						Bout=new BufferedOutputStream(out);
						int b;
						while((b=Bin.read())!=-1){
							Bout.write(b);
						}
						Bout.close();
						out.close();
						System.out.println(Fout+"解压成功");	
						result = name;
				 }
				Bin.close();
				Zin.close();
			 }else{
				  //进行非解压格式的复制
				
				 new BaseBean().writeLog("  filerealpath="+"文件输出开始;;;;;;;;;;");
					Fout=new File(Parent,name);
					if(!Fout.exists()){
						(new File(Fout.getParent())).mkdirs();
					}
				    out=new FileOutputStream(Fout);
					Bout=new BufferedOutputStream(out);
					int b;
					while((b=Bin.read())!=-1){
						Bout.write(b);
					}
					Bout.close();
					out.close();
				
					new BaseBean().writeLog("  filerealpath="+"文件输出成功");
					result = name;
					Bin.close();
					fileIo.close();
				
				 
			 }
			} catch (IOException e) {
				new BaseBean().writeLog(e);
				
			}
		} catch (FileNotFoundException e) {
			new BaseBean().writeLog(e);
			
		}
		return result;
	}
}
