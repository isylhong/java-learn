package consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.util.Map;

public class SpringTopicConsumer {
    public static void main(String[] args) throws JMSException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-activemq.xml");
        JmsTemplate jmsTemplate = applicationContext.getBean("jmsTemplate", JmsTemplate.class);

//        while (true){
//            MapMessage mapMessage = (MapMessage) jmsTemplate.receive();
//            String message =  mapMessage.getString("message");
//            System.out.println(message);
//        }

        while (true) {
            Map<String, Object> map = (Map<String, Object>) jmsTemplate.receiveAndConvert();
            System.out.println("收到消息：" + map.get("message"));
        }
    }
}
