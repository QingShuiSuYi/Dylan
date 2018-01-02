package com.weavernorth.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;



import weaver.conn.RecordSet;
import weaver.general.Util;
/**
 * 文件操作工具类
 * @author Dylan
 *
 */
public class FileUtil {
	

	
    /**
     * 将文件转换成byte[]
     * @param filePath	需要转换的文件的路径
     * @return
     */
    public static byte[] File2byte(String filePath)  
    {  
        byte[] buffer = null;  
        try  
        {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1)  
            {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }  
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
  /**
   * InputStream转byte[]
   * @param is
   * @return
   */
	public static byte[] inputStream2byte(InputStream is) {
		byte[] content = new byte[102400];
		try {
			int byteread;
			byte data[] = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((byteread = is.read(data)) != -1) {
				out.write(data, 0, byteread);
				out.flush();
			}
			content = out.toByteArray();
			is.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
    /**
     * byte[]转换成文件
     * @param buf	数据组
     * @param filePath	文件保存路径
     * @param fileName	文件保存文件名
     */
    public static void byte2File(byte[] buf, String filePath, String fileName)  
    {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try  
        {  
            File dir = new File(filePath);  
            if (!dir.exists() && dir.isDirectory())  
            {  
                dir.mkdirs();  
            }  
            file = new File(filePath + File.separator + fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(buf);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            if (bos != null)  
            {  
                try  
                {  
                    bos.close();  
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
            if (fos != null)  
            {  
                try  
                {  
                    fos.close();  
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
        }  
    } 
    
    /**
     * 根据系统文档id生成byte[]
     * @param docId
     * @return
     */
    public static byte[] docId2Byte(String docId){
    	String sql = "select top 1 t2.docid,t1.imagefilename,t1.filerealpath,t1.iszip,t1.isencrypt,t1.imagefiletype , t1.imagefileid, t1.imagefile,t2.imagefilename as realname from ImageFile t1 left join DocImageFile t2 on t1.imagefileid = t2.imagefileid where t2.docid in (" + docId + ") order by t1.imagefileid desc";
    	RecordSet rs = new RecordSet();
    	//文件真实路径
    	String strFilerealpath = "";
    	//返回值
    	byte[] filesByte = null;
    	if(!"".equals(docId)){
    		rs.execute(sql);
    		if(rs.next()){
    			strFilerealpath = Util.null2String(rs.getString("filerealpath"));
    			strFilerealpath = strFilerealpath.replaceAll("\\\\", "/");
    			int intlastIndex = strFilerealpath.lastIndexOf("/");
    			//解压文件名
				String strZipName = strFilerealpath.substring(intlastIndex + 1);
				//解压路径
				String strZipOldPath = strFilerealpath.substring(0,intlastIndex + 1);
				//重命名文件名
				String strFileName = strZipName.split("\\.")[0];
				//实例化解压Util
				ZipDecompressing zd = new ZipDecompressing();
				//解压附件并存储在本地
				
				String strDecompre = zd.Decompre(strFilerealpath, strZipOldPath, strFileName);
				if(!"fail".equals(strDecompre)){
					File file = new File(strZipOldPath + "/" + strFileName);
					File fileReal = getFile(file);
					filesByte = File2byte(fileReal.getPath());
					
				}
				//判断是否按文件夹解压
				if(null == filesByte){
					try {
						ZipDecompress.unZipFiles(new File(strFilerealpath),strZipOldPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
					File file2 = new File(strFilerealpath.substring(0,intlastIndex));
					String[] list = file2.list();
					for (int i = 0; i < list.length; i++) {
						File file1 = new File(file2.getPath()+"\\"+list[i] );
						if(file1.isDirectory()){      
							File file = getFile(file1);
							filesByte = File2byte(file.getPath());
						}  
					}
				}
				
    		}
    		
    		
    	}
    	return filesByte;
    }
    
    /**
     * 通过递归不限目录检索文件
     * @param file
     * @return
     */
	public static File getFile(File file){
        if(file.isDirectory()){           
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(file.getPath()+"\\"+files[i] );      
                if(file1.isDirectory()){      
                	getFile(file1);  
                }else{
                	return file1;
                }                 
            }      
        }else{      
        	return file;
        }
		return null;
	}
	
   /**文件重命名 
    * @param path 文件目录 
    * @param oldname  原来的文件名 
    * @param newname 新文件名 
    */ 
    public void renameFile(String path,String oldname,String newname){ 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(path+"/"+oldname); 
            File newfile=new File(path+"/"+newname); 
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
                System.out.println(newname+"已经存在！"); 
            else{ 
                oldfile.renameTo(newfile); 
            } 
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }
    
    
    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	    
	    
    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }
    
    
    /**
     * 判断文件夹是否存在,不存在就创建     
     * @param file
     */
    public static void judeDirExists(File file) {
 
        if (file.exists()) {
           if (file.isDirectory()) {
        	   LogUtil.doWriteLog("dir exists");
             } else {
            	 LogUtil.doWriteLog("the same name file exists, can not create dir");
             }
         } else {
        	 LogUtil.doWriteLog("dir not exists, create it ...");
             file.mkdir();
         }
 
     }
    
    /**
     * 复制文件
     * @param fromFile
     * @param toFile
     * @throws IOException 
     */
    public void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, b.length);
        }
        System.out.println(n);
        ins.close();
        out.close();
    }
	    
	    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
    	String strHTML = "";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	strHTML += tempString;
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return strHTML;
    }
    

    
    
	


}
