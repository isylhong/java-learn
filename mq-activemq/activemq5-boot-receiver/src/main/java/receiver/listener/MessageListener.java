package receiver.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component // 一定不要忘记加@Component
public class MessageListener {
    /**
     * 使用@JmsListener注解来监听指定的某个队列内的消息,是否有新消息,有的话则取出队列内消息
     * 进行处理
     **/
    @JmsListener(destination = "boot-queue")
    public void receiveMessage(String message) {
        // 打印队列内的消息
        System.out.println("监听接收到的消息是:" + message);
    }
}
