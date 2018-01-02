package com.weavernorth.junit;

import java.io.File;
import com.weavernorth.file.IOUtil;
import org.junit.Test;
import com.weavernorth.ftp.FtpCollectionUtil;

public class FtpCollectionUtilTest {
	
	@Test
	public void dowTest() throws Exception{
		FtpCollectionUtil t = new FtpCollectionUtil();    
        if(t.connect("", "192.168.1.104", 21, "xly", "xialu")){
        	System.out.println("连接成功");
        	t.upload(new File("F:\\WeaverProject\\Y-伊利\\msg"));
        	//t.downFileOrDir("/work.txt", "f:/xxx.test");
        	t.disConnection();
        }else{
        	System.out.println("连接失败");
        }
	}
	
	@Test
	public void test(){
		File file = new File("F:/exp.xlsx");
		System.out.println(file.getName());
	}

	@Test
    public void test1(){

        IOUtil.judeDirExistsAll(new File("F:/a/b/c/d/p/o/"));

    }
}
