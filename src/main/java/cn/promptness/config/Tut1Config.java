package cn.promptness.config;

import cn.promptness.mq.Tut1Receiver;
import cn.promptness.mq.Tut1Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author : Lynn
 * @Date : 2018-04-22 22:17
 */
@Configuration
public class Tut1Config {

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Bean
//    public Queue hello() {
//        return new Queue("hello");
//    }

}
