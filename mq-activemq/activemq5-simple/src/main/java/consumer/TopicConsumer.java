package consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class TopicConsumer {
    private static final String BROKER_URL = "tcp://192.168.80.128:61616";

    public static void main(String[] args) throws JMSException {
//        useGeneralConnection();
        useTopicConnection();
    }

    public static void useGeneralConnection() throws JMSException {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("test-topic");
            consumer = session.createConsumer(topic);
            TextMessage message = session.createTextMessage("hello,test-topic");

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            System.out.println(textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            System.in.read();
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
            session.close();
            connection.close();
        }
    }


    public static void useTopicConnection() throws JMSException {
        TopicConnectionFactory connectionFactory = null;
        TopicConnection connection = null;
        TopicSession session = null;
        TopicSubscriber subscriber = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            connection = connectionFactory.createTopicConnection();
            connection.start();
            session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("test-topic");
            subscriber = session.createSubscriber(topic);
            // 设置持久化模式
            subscriber.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof MapMessage) {
                        MapMessage map = (MapMessage) message;
                        try {
                            System.out.println(map.getLong("time") + "接收:" + map.getString("text"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            // 休眠100ms再关闭
            Thread.sleep(1000 * 10);
            session.commit();
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            subscriber.close();
            session.close();
            connection.close();
        }
    }
}
