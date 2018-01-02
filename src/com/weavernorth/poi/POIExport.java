package com.weavernorth.poi;

import java.sql.ResultSet;  
import java.sql.SQLException;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Font;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.util.CellRangeAddress;  
import org.apache.poi.xssf.streaming.SXSSFWorkbook;  
import com.weavernorth.jdbc.ConnectionDB;
/**
 * 根据SQL导出Excel
 * @author Dylan
 *
 */
public class POIExport {  
    /** 
     * 根据所传入的参数生成一个Workbook 
     * @param sql 查询数据的sql 
     * @param columns sql中列名字符串，以英文逗号分隔，不区分大小写 
     * @param headers 表头字符串数组，如果是多表头，在需要合并的地方写"null"，如：test1,test2,null,null,test3。 
     * 这表示test2将占三列。如果test2下面没有null，则占一行三列，有n个null，则占n行3列 
     * @param splitStr 分割表头字符串的分割符 
     * @return 
     */  
    public static Workbook export(String sql,String columns,String[] headers, String splitStr){  
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);//创建excel文档,内存中保留 1000 条数据，以免内存溢出  
        Font font = wb.createFont();//字体  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//加粗  
        CellStyle cellStylehead = wb.createCellStyle();//表头样式  
        cellStylehead.setFont(font);//设置字体样式  
        cellStylehead.setAlignment(CellStyle.ALIGN_CENTER);//水平对齐  
        cellStylehead.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直对齐  
        cellStylehead.setWrapText(true);//自动换行  
        //设置边框  
        cellStylehead.setBorderTop(CellStyle.BORDER_THIN);  
        cellStylehead.setBorderRight(CellStyle.BORDER_THIN);  
        cellStylehead.setBorderBottom(CellStyle.BORDER_THIN);  
        cellStylehead.setBorderLeft(CellStyle.BORDER_THIN);  
        //表体样式  
        CellStyle cellStyleBody = wb.createCellStyle();//表体单元格样式  
        cellStyleBody.setAlignment(CellStyle.ALIGN_LEFT);//水平对齐  
        cellStyleBody.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直对齐  
        cellStyleBody.setWrapText(true);//自动换行  
        //设置边框  
        cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);  
        cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);  
        cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);  
        cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);  
        Sheet sheet = wb.createSheet("sheet1");//创建一个sheet  
        sheet.setDefaultColumnWidth(15);//设置默认列宽  
        //写表头  
        createHeader(wb,sheet,cellStylehead,headers,splitStr);  
        //写表体  
        int beginRowNumber = headers.length;//表体开始行  
        String[] cols = columns.split(",");//切分sql列名  
        int cellSize = cols.length;//列数  
        ResultSet rs = null; 
        ConnectionDB cdb = new ConnectionDB();
        rs = cdb.executeQueryRS(sql, null);
        try {  
            int count = 0;//记录行号  
            while(rs.next()){  
                Row row = sheet.createRow(count+beginRowNumber);  
                row.setHeightInPoints(14);//设置行高  
                for(int j=0;j<cellSize;j++){  
                    Cell cell = row.createCell(j);  
                    Object obj = rs.getObject(cols[j]);  
                    String cv = obj==null?"":obj.toString();//取得对应列中的值  
                    cell.setCellValue(cv);//设置单元格的值  
                    cell.setCellStyle(cellStyleBody);//设置样式  
                }  
                count++;  
            }  
            System.out.println("共写入数据："+count+"条");  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(rs!=null){  
                    rs.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
 
        }  
        return wb;  
    }  
  
    /** 
     * 创建excel表头 
     * @param wb excel的workbook 
     * @param sheet excel的sheet 
     * @param cellStylehead excel的样式 
     * @param headers   表头字符串数组 
     * @param splitStr  表头字符串切割符 
     */  
    private static void createHeader(SXSSFWorkbook wb, Sheet sheet,  
            CellStyle cellStylehead, String[] headers, String splitStr) {  
        //遍历创建单元格  
        for(int i=0;i<headers.length;i++){  
            Row row = sheet.createRow(i);  
            String[] header = headers[i].split(splitStr);  
            for(int r=0;r<header.length;r++){  
                Cell cell = row.createCell(r);  
                cell.setCellValue(header[r]);  
                cell.setCellStyle(cellStylehead);  
            }  
        }  
        //遍历合并单元格，如果是单表头则跳过  
        if(headers.length>1){  
            int[][][] mergeDatas = parseHeader(headers,splitStr);  
            for(int i=0;i<mergeDatas.length;i++){  
                int[][] mergeData = mergeDatas[i];  
                for(int j=0;j<mergeData.length;j++){  
                    int[] merges = mergeData[j];  
                    int mergesR = merges[1]-merges[0];  
                    int mergesC = merges[3]-merges[2];  
                    if(mergesR!=0||mergesC!=0){  
                        //合并单元格  
                        sheet.addMergedRegion(new CellRangeAddress(merges[0],merges[1],merges[2],merges[3]));  
                        //合并单元格后重新设置单元格的样式  
                        setMergedStyle(merges[0],merges[1],merges[2],merges[3],sheet,cellStylehead);  
                    }  
                }  
            }  
        }  
    }  
    /** 
     * 设置合并单元格的样式 
     * @param i first row(0-based) 
     * @param j last row(0-based) 
     * @param k first column(0-based) 
     * @param l last column(0-based) 
     * @param sheet excel的sheet 
     * @param cellStylehead excel的样式 
     */  
    private static void setMergedStyle(int i, int j, int k, int l, Sheet sheet, CellStyle cellStylehead) {  
        for(int mm=i;mm<=j;mm++){  
            Row row = sheet.getRow(mm);  
            if(row==null){  
                row = sheet.createRow(mm);  
            }  
            for(int nn=k;nn<=l;nn++){  
                Cell cell = row.getCell(nn);  
                if(cell==null){  
                    cell = row.createCell(nn);  
                }  
                cell.setCellStyle(cellStylehead);  
            }  
        }  
    }  
    /** 
     * 解析复杂表头，表头为多行且有合并的情况。表头要符合约定格式。 
     * 格式例子： 
     * String h21 = "test,test,test,null,null,null,null,null,test,null,null,null,null,null,test"; 
     * String h22 = "null,null,test,null,null,test,null,null,test,null,test,null,test,null,null"; 
     * String h23 = "null,null,test,test,test,test,test,test,test,test,test,test,test,test,null"; 
     * 这里表头有三行，且有合并情况。一行为一个字符串，字符串以英文逗号隔开，在需要合并的地方填写"null"字符串 
     * @param headers 要解析的表头字符串数组 
     * @param splitStr 切割表头字符串的分割符 
     * @return 返回一个三维数组，第三重数据中保存着合并数据 
     */  
    private static int[][][] parseHeader(String[] headers, String splitStr){  
        //依据表头建立一个二维数组  
        String[][] doubleAry = new String[headers.length][];  
        //依据表头建立一个三维数组，用来保存合并单元格所需要的数据，单元格所需要的数据有四个，依次是开始合并的行，结束合并的行，开始合并的列，结束合并的列  
        //sheet.addMergedRegion(new CellRangeAddress(merges[0],merges[1],merges[2],merges[3]));  
        int[][][] rcs = new int[headers.length][][];  
        //遍历赋值  
        for(int i=0;i<headers.length;i++){  
            String[] header = headers[i].split(splitStr);  
            doubleAry[i] = header;  
            rcs[i] = new int[header.length][4];  
        }  
        //遍历二维数组  
        for(int i=0;i<doubleAry.length;i++){  
            String[] sub = doubleAry[i];  
            for(int j=0;j<sub.length;j++){  
                int sum = 0;//计算行标识  
                //计算i,j需要合并的行数  
                if(!sub[j].equals("null")){//如果单元格不为null  
                    for(int m=i;m<doubleAry.length;m++){  
                        String rs = doubleAry[m][j];  
                        if(rs.equals("null")){//如果单元格为null  
                            doubleAry[m][j] = "null2";//把null重赋值为null2，防止在计算列时交错了  
                            sum++;//个数加1  
                        }  
                    }  
                }  
                rcs[i][j][0] = i;//赋合并的开始行，当前行  
                rcs[i][j][1] = i+sum;//赋合并的结束行，当前行加上它下面为null的行数  
                int sum2 = 0;//计算列标识  
                //计算i,j需要合并的列数  
                if(!sub[j].equals("null")){//如果单元格不为null  
                    for(int m=j+1;m<doubleAry[i].length;m++){  
                        String rs = doubleAry[i][m];  
                        if(rs.equals("null")){//如果单元格为null  
                            sum2++;  
                        }else{  
                            break;//一定要break，不然会算错  
                        }  
                    }  
                }  
                rcs[i][j][2] = j;//赋合并的开始列，当前列  
                rcs[i][j][3] = j+sum2;//赋合并的结束列，当前列加上它右边为null的列数  
            }  
        }  
        return rcs;  
    }
    //调用示例
    /*
    public static void main(String[] args) throws Exception {  
        StringBuilder sb = new StringBuilder();  
        sb.append("select to_char(sysdate,'yyyymmdd') c1,       \n");  
        sb.append("'类别'||round(dbms_random.value(0,4)) c2,    \n");  
        sb.append("round(dbms_random.value(0,10000),2) c3,      \n");  
        sb.append("round(dbms_random.value(0,100),2)||'%' c4,   \n");  
        sb.append("round(dbms_random.value(0,10000),2) c5,      \n");  
        sb.append("round(dbms_random.value(0,10000),2) c6,      \n");  
        sb.append("round(dbms_random.value(0,10000),2) c7,      \n");  
        sb.append("round(dbms_random.value(0,10000),2) c8       \n");  
        sb.append(" from dual connect by level < 100             \n");//注：改这里的数字，可得到不同行数的数据  
        String sql = sb.toString();  
        System.out.println("sql:\n"+sql);  
        String columns = "c1,c2,c3,c4,c5,c6,c7,c8";  
        System.out.println("columns:\n"+columns);  
        String[] headers = new String[2];  
        headers[0] = "日期,类别,3G,null,4G,null,null,null";  
        headers[1] = "null,null,月流量,同比,月流量,上行流量,下行流量,赠送流量";  
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
        Workbook wb = export(sql,columns,headers,",");  
        long t11 = System.currentTimeMillis();  
        System.out.println("生成Workbook共花费："+(t11-t1)+"毫秒");  
        //写入文件  
        FileOutputStream out = new FileOutputStream("f:/test/exp.xlsx");  
        wb.write(out);  
        out.close();  
        long t2 = System.currentTimeMillis();  
        System.out.println("把Workbook写入文件共花费："+(t2-t11)+"毫秒");  
        System.out.println("生成文件结束，共花费："+(t2-t1)+"毫秒");  
        //测试10万行*8列，所花时间13620毫秒  
        //测试100万行*8列，所花时间121443毫秒  
    } 
    */
   
}
