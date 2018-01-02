package com.weavernorth.junit;

import com.weavernorth.file.IOUtil;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * java反射机制
 */
public class ReflectionTest {

    @Test
    public void test1() {
        try {
            Class<?> ioUtil = Class.forName("com.weavernorth.doc.CreateDoc2ImageFileId");
            System.out.println(ioUtil.toString());
            Class<IOUtil> ioUtilClass = IOUtil.class;
            System.out.println(ioUtilClass);
            IOUtil io = new IOUtil();
            Class<? extends IOUtil> aClass = io.getClass();
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Class<IOUtil> ioUtilClass = IOUtil.class;
        Method[] declaredMethods = ioUtilClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            System.out.println(declaredMethods[i].toString());
        }

    }

}
