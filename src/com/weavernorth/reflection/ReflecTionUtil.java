package com.weavernorth.reflection;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;


public class ReflecTionUtil {
    /**
     * 反射工具类
     * 封装数据结果到集合
     * 传入result 实体和 实体类具体url
     */
    private String[] classMethods = new String[20];// set方法数组
    private Class[] classParams = new Class[20];// set方法参数类型
    private int classMethodsNum = 0;// 实体类属性个数
    private Class cs = null;// 会话管理器
    private List list = null;// 实体类属性字段名的集合

    public void getStandardManager(String url) throws ClassNotFoundException {
        cs = Class.forName(url);
    }

    public void getProtect(String url) throws ClassNotFoundException {
        // 实体类变量字段
        list = new ArrayList();
        this.getStandardManager(url);
        Field[] fields = cs.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            list.add(fields[i].getName());
        }
    }

    public void getConsructor(String url) throws ClassNotFoundException {
        // set方法和参数类型
        this.getStandardManager(url);
        Method[] methods = cs.getMethods();
        int count = 0;
        for (Method m : methods) {
            if (m.getName().substring(0, 3).equals("set")) {
                Class[] parms = m.getParameterTypes();
                classMethods[count] = m.getName();
                classParams[count] = parms[0];//
                count++;
            }
        }
        classMethodsNum = count;
    }

    public Object getObject(String url) throws SecurityException,
            NoSuchMethodException, ClassNotFoundException,
            IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        /**
         * 创建类对象
         */
        this.getStandardManager(url);
        Constructor constructor = cs.getConstructor();
        Object  object = constructor.newInstance();
        return object;
    }

    public Result checkResult(Result rs) {
        /**
         * 验证数据库中的数据
         */
        for (int i = 0; i < rs.getRowCount(); i++) {
            SortedMap map = rs.getRows()[i];
            for (int j = 0; j < list.size(); j++) {
                Object value = map.get(list.get(j));
                if(value==null){
                    System.out.println("数据验证失败，检查实体类与数据表规范！");
                    try {
                        throw new Exception("数据验证失败，检查实体类与数据表规范！");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    map.put(list.get(j), value);
                }
            }
        }
        return rs;
    }

    public List getValue(String url, Result rs) {
        /**
         * list列表  value
         */
        List resultlist = new ArrayList();
        try {

            this.getConsructor(url);
            this.getProtect(url);
            rs = checkResult(rs);
            for (int i = 0; i < rs.getRowCount(); i++) {
                Object object = this.getObject(url);
                for (int j = 0; j < classMethodsNum; j++) {
                    Method method = cs.getMethod(classMethods[j],
                            classParams[j]);

                    //System.out.println("当前调用set方法:"+method);

                    //System.out.println("表字段名:"+classMethods[j]
                    //	.substring(3).toLowerCase());//表字段名
                    String tstr = classMethods[j]
                            .substring(3).toLowerCase();

                    ///System.out.println("表字段值:"+rs.getRows()[i].get(tstr));
                    //表字段值
                    method.invoke(object, rs.getRows()[i].get(tstr));//动态设值
                    //System.out.println((Nr_users)object);
                }
                resultlist.add(object);
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultlist;
    }
}


