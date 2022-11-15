package sender.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQSendConfig {

    // 返回一个名为boot-queue的队列,并且注册为bean
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("boot-queue");
    }


}
