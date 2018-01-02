package com.weavernorth.junit;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import com.weavernorth.poi.POIExport;

public class POIExcelTest {
	
	@Test
	public void test() throws IOException{
        String sql = "select id,lastname from hrmresource ";  
        System.out.println("sql:\n"+sql);  
        String columns = "id,lastname";  
        System.out.println("columns:\n"+columns);  
        String[] headers = new String[1];  
        headers[0] = "编号,名字";    
        System.out.println("表头：");  
        for(int i=0;i<headers.length;i++){  
            String[] header = headers[i].split(",");  
            for(int j=0;j<header.length;j++){  
                System.out.print(header[j]+"\t");  
            }  
            System.out.println();  
        }  
        System.out.println("生成文件开始。。。");  
        long t1 = System.currentTimeMillis();  
        Workbook wb = POIExport.export(sql,columns,headers,",");  
        long t11 = System.currentTimeMillis();  
        System.out.println("生成Workbook共花费："+(t11-t1)+"毫秒");  
        //写入文件  
        FileOutputStream out = new FileOutputStream("f:/exp.xlsx");  
        wb.write(out);  
        out.close();  
        long t2 = System.currentTimeMillis();  
        System.out.println("把Workbook写入文件共花费："+(t2-t11)+"毫秒");  
        System.out.println("生成文件结束，共花费："+(t2-t1)+"毫秒");  
	}

}
