package sender.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Date;


@Component
public class BootMessageProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 3000)  // 每3s执行1次,将消息放入队列内
    public void send() {
        String message = "测试消息队列" + new Date();
        this.jmsMessagingTemplate.convertAndSend(this.queue, message);
    }
}
