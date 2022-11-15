package receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

// 开启jms
@EnableJms
@SpringBootApplication
public class ActiveMQ_Receive8002_App {
    public static void main(String[] args) {
        SpringApplication.run(ActiveMQ_Receive8002_App.class, args);
    }
}
