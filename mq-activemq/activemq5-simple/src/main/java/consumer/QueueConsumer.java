package consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class QueueConsumer {
    private final static String BROKER_URL = "tcp://192.168.80.128:61616";

    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            // 1、创建工厂连接对象，需要制定ip和端口号
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            //2、使用连接工厂创建一个连接对象
            connection = connectionFactory.createConnection();
            // 3、开启连接
            connection.start();
            // 4、使用连接对象创建会话（session）对象
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
            Queue queue = session.createQueue("test-queue");
            // 6、使用会话对象创建生产者对象
            consumer = session.createConsumer(queue);
            // 7、向consumer对象中设置一个messageListener对象，用来接收消息
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
            // 8、程序等待接收用户消
            System.in.read();
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        } finally {
            // 9、关闭资源
            consumer.close();
            session.close();
            connection.close();
        }
    }


}
