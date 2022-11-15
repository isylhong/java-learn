package provider;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    private static final String BROKER_URL = "tcp://192.168.80.128:61616";

    public static void main(String[] args) throws JMSException {
//        useGeneralConnection();
        useTopicConnection();
    }

    public static void useGeneralConnection() throws JMSException {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("test-topic");
            producer = session.createProducer(topic);
            TextMessage textMessage = session.createTextMessage("hello,test-topic");
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }

    public static void useTopicConnection() throws JMSException {
        TopicConnectionFactory connectionFactory = null;
        TopicConnection connection = null;
        TopicSession session = null;
        TopicPublisher publisher = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            connection = connectionFactory.createTopicConnection();
            connection.start();
            session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("test-topic");
            publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, publisher, "useTopicConnection");
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            publisher.close();
            session.close();
            connection.close();
        }
    }

    public static void sendMessage(Session session, MessageProducer producer, String method) throws JMSException {
        for (int i = 0; i < 2; i++) {
            String message = method + "发送消息第" + (i + 1) + "条";

            MapMessage map = session.createMapMessage();
            map.setString("text", message);
            map.setLong("time", System.currentTimeMillis());
            System.out.println(map);
            producer.send(map);
        }
    }
}
