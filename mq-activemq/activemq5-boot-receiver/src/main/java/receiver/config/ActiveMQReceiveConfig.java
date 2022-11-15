package receiver.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQReceiveConfig {

    // 返回一个名为boot-queue的队列,并且注册为bean
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("boot-queue");
    }

    /**
     * 发送消息之后消费不了
     * 解决方法:
     * 将springboot里的消息加到jms工厂
     */
    // 将springboot里面的消息加到jms监听工厂
//    @Bean
//    public JmsListenerContainerFactory<?> factory(ConnectionFactory connectionFactory,
//                                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        // This provides all boot's default to this factory, including the message converter
//        configurer.configure(factory, connectionFactory);
//        // You could still override some of Boot's default if necessary.
//        return factory;
//    }

    /**
     * 转换消息格式, 没有这个会爆类型转换错误:
     */
//    @Bean
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }

}
