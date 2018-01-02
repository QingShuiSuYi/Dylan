package com.weavernorth.reflection;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *            反射工具类 自动生成sql 语句 和参数赋值 实体类中含有id字样的只能唯一
 *            对外接口 对象 语句类型 查询参数Map<String,object>字段名 字段值
 *
 *            如果是查询操作，构造方法传入一个jvm初始化的对象实体，生成语句时调用createQuerySql(map ma)方法
 *            Map<String,object>字段名 字段值
 *
 *            其他操作,构造方法传入一个具体对象实体，生成语句时调用createUpdateSql(String type)方法
 *            type为update delete insert 的字符串
 */
public class SqlFactory {


    /** 需自动化的对象 **/
    private Object obj;

    /** 生成的sql语句 **/
    private String sql;

    /** 参数值 **/
    private List objParam = new ArrayList();

    /** 保存对象的属性名和属性值 **/
    private Map<String, Object> paramMap = new HashMap<String, Object>();


    public SqlFactory(Object obj){
        /**
         * 构造方法
         * 自动加载load
         */
        try {
            this.obj=obj;
            load(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("IllegalArgumentException***类反射失败");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("IllegalAccessException***类反射失败");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("InvocationTargetException***类反射失败");
        }
    }

    @SuppressWarnings("unchecked")
    private void load(Object obj) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        /**
         * 获得属性名称和值的集合
         *
         */
        Class c = obj.getClass();
        Method[] methods = c.getMethods();
        for (Method m : methods) {

            String mName = m.getName();
            if (mName.startsWith("get") && !mName.startsWith("getClass")) {
                String fieldName = mName.substring(3, mName.length());

                Object value = m.invoke(obj, null);
                if (value instanceof String) {
                    paramMap.put(fieldName, "\"" + value + "\"");
                } else {
                    paramMap.put(fieldName, value);
                }
            }
        }
    }

    public Object[] getSqlParams() {
        /**
         * 参数值
         */
        return objParam.toArray();
    }

    @SuppressWarnings("unchecked")
    public String createQuerySql(Map<String,Object> map){
        /**
         * 查询单表记录的sql
         * map 数据表的字段名 与值
         * 不支持分组与多表
         */
        Class c = obj.getClass();
        String tableName = c.getSimpleName();
        String sql="select * from "+tableName;
        if(map!=null){
            StringBuffer strb = new StringBuffer("select * from "+tableName+" where 1=1");
            Set<String> set = map.keySet();
            Object[] keys = set.toArray();
            int len = keys.length;
            for (int i = 0; i < len; i++) {
                strb.append(" and "+keys[i]+"=?");
                objParam.add(map.get(keys[i]));//将值加入到参数
            }
            sql = strb.toString();
        }
        return sql;
    }

    @SuppressWarnings("unchecked")
    public String createUpdateSql(String type) {
        /**
         * createUpdateSql 自动生成添删改的SQL语句
         * 表中 字段名只能有一个包含id的字段
         * @param obj 对象
         * @param type 传递过来的操作类型 delete update insert
         * @return String
         */
        Class c = obj.getClass();
        String tableName = c.getSimpleName();
        StringBuffer strb = new StringBuffer();
        Set<String> set = paramMap.keySet();
        Object[] keys = set.toArray();
        int len = keys.length;
        if ("insert".compareToIgnoreCase(type)==0) {
            strb.append("insert into " + tableName + "(");
            for (int i = 0; i < len; i++) {
                if (i < len - 1) {
                    strb.append(keys[i]);
                    objParam.add(paramMap.get(keys[i]));
                    strb.append(",");
                } else {
                    strb.append(keys[i]);
                    objParam.add(paramMap.get(keys[i]));
                    strb.append(") values(");
                }
            }
            for (int i = 0; i < len; i++) {
                if (i < len - 1) {
                    strb.append("?" + ",");
                } else {
                    strb.append("?" + ")");
                }
            }
        }
        if ("delete".compareToIgnoreCase(type)==0) {
            strb.append("delete from " + tableName);
            for (int i = 0; i < len; i++) {
                if (((String) keys[i]).contains("id")
                        || ((String) keys[i]).contains("Id")) {
                    strb.append(" where " + keys[i] + "=?");
                    objParam.add(paramMap.get(keys[i]));
                }
            }
        }
        if ("update".compareToIgnoreCase(type)==0) {
            strb.append("update " + tableName + " ");
            for (int i = 0; i < len; i++) {
                if (i < len - 1) {
                    strb.append("set" + keys[i] + "=?");
                    objParam.add(paramMap.get(keys[i]));
                    strb.append(",");
                } else {
                    strb.append("set" + keys[i] + "=?");
                    objParam.add(paramMap.get(keys[i]));
                }
            }
            for (int i = 0; i < len; i++) {
                if (((String) keys[i]).contains("id")
                        || ((String) keys[i]).contains("Id")) {
                    strb.append(" where " + keys[i] + "=?");
                    objParam.add(paramMap.get(keys[i]));
                }
            }
        }
        sql = strb.toString();
        return sql;
    }



    /**
     * Test
     *
     * @param args
     */
    public static void main(String[] args) {
        Users te = new Users();
 //       te.setName("张三");
        te.setPass("123456");
        //te.setId(123);
   //     te.setBir(new Time(new Date().getTime()));
        System.out.println("********添删改********");
        SqlFactory sf = new SqlFactory(te);
        String sql = sf.createUpdateSql("delete");
        Object[] oo = sf.getSqlParams();
        System.out.println(sql);
        System.out.println(Arrays.toString(oo));

        System.out.println("********查询********");
        SqlFactory sf2 = new SqlFactory(te);//1
        Map<String, Object> ma = new HashMap<String, Object>();
        ma.put("userName", "张三");
        ma.put("userPass", new Time(new Date().getTime()));
        String qsql = sf2.createQuerySql(ma);//2
        System.out.println(qsql);
        Object[] oo2 = sf2.getSqlParams();//3
        System.out.println(Arrays.toString(oo2));

        System.out.println("**********更新**********");
        SqlFactory sf3 = new SqlFactory(te);
        String updateSql = sf3.createUpdateSql("update");
        System.out.println(updateSql);
        Object[] sqlParams = sf3.getSqlParams();
        System.out.println(Arrays.toString(sqlParams));


    }
}

class Users {
    private String name;
    private String pass;
    private int id;
    private Time Bir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getBir() {
        return Bir;
    }

    public void setBir(Time bir) {
        Bir = bir;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users() {}
}

