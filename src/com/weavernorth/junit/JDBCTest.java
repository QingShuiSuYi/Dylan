package com.weavernorth.junit;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.weavernorth.jdbc.ConnectionDB;

/**
 * JUnit测试类
 * 测试内容：JDBC
 * 备注：@
 * Test 表示这是一个用来测试待测试方法的方法。
 * Ignore 表示这个方法不执行，被忽略。
 * Before 表示在每个方法之前都会执行该测试方法一次。
 * After 表示在每个方法之后都会执行该测试方法一次。
 * @author Dylan
 *
 */
public class JDBCTest {
	
	@Test
	public void TestConn(){
		ConnectionDB cdb = new ConnectionDB();
		Connection connection = cdb.getConnection();
		System.out.println(connection);
	}
	@Test
	public void TestSQL(){
		ConnectionDB cdb = new ConnectionDB();
		List<Object> excuteQuery = cdb.excuteQuery("select id,lastname from hrmresource", null);
		System.out.println(excuteQuery.toString());
		for (Object object : excuteQuery) {
			System.out.println(object);
		}
	}
	
}
