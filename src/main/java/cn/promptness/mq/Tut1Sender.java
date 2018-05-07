package cn.promptness.mq;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author : Lynn
 * @Date : 2018-04-22 22:19
 */
@Component
public class Tut1Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQProducer rabbitMQProducer;

//    @Autowired
//    private Queue queue;

    private static volatile int index = 0;

    @Scheduled(cron = "0/1 * * * * ?")
    public void send() {

        index++;

        String message = "Hello World!" + index;
        //rabbitTemplate.convertAndSend(queue.getName(), message);

//        CorrelationData correlationData = rabbitMQProducer.sendMsg(queue.getName(), message);
        CorrelationData correlationData = rabbitMQProducer.sendMsg("hello", message);

        System.out.println(correlationData);


//        template.convertSendAndReceive()

        System.out.println(" [x] Sent '" + message + "'");
    }
}