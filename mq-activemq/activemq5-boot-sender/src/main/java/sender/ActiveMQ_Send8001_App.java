package sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJms  // 开启jms
@EnableScheduling  // 开启定时器
@SpringBootApplication
public class ActiveMQ_Send8001_App {
    public static void main(String[] args) {
        SpringApplication.run(ActiveMQ_Send8001_App.class, args);
    }
}
