package com.weavernorth.junit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
/*import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;*/

public class test {

	public static void main(String[] args) {

/*		PushNotificationService service = new PushNotificationService();
		Map<String, String> para = new HashMap<String, String>();
		para.put("module", "-2");
		para.put("messagetypeid", "3");
		para.put("url", "http://www.baidu.com");
		service.push("ywy,ny,", "ecology消息推送提醒", 1, para);*/


/*            //创建连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
            try {
                //创建连接
                Connection connection = connectionFactory.createConnection();
                //开启连接
                connection.start();
                //创建一个回话
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                //创建一个Destination，queue或者Topic
                Topic topic = session.createTopic("mytopic");
                //创建一个生成者
                MessageProducer producer = session.createProducer(topic);
                //创建一个消息
                TextMessage textMessage = new ActiveMQTextMessage();
                textMessage.setText("hello my topic");
                //发送消息
                producer.send(textMessage);
                //关闭
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }*/
    }



}
