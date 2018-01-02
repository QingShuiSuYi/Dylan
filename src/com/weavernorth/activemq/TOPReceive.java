package com.weavernorth.activemq;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息订阅者一
 * @author MCL
 *
 */
public class TOPReceive {
    private static final String USERNAME = "admin";// 默认的连接的用户名
    private static final String PASSWORD = "admin";// 默认的连接密码
    private static final String BROKEURL = "tcp://localhost:61616";// 默认的连接地址

    public static void main(String[] args) {
        ConnectionFactory connectionFactory; // 连接工厂
        Connection connection = null; // 连接
        Session session; // 会话 接受或者发送消息的线程
        Destination destination; // 消息的目的地
        MessageConsumer messageConsumer; // 消息的消费者


        connectionFactory = new ActiveMQConnectionFactory(TOPReceive.USERNAME, TOPReceive.PASSWORD, TOPReceive.BROKEURL);
        try {
            // 通过连接工厂获取连接
            connection=connectionFactory.createConnection();  // 通过连接工厂获取连接
            connection.start(); // 启动连接
            session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
            destination=session.createTopic("topic-text");
            messageConsumer=session.createConsumer(destination); // 创建订阅者
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        //获取到接收的数据
                        String text = ((TextMessage)message).getText();
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }); // 注册消息监听

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}