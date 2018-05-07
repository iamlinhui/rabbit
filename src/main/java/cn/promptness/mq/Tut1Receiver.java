package cn.promptness.mq;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author : Lynn
 * @Date : 2018-04-22 22:21
 */
@Component

public class Tut1Receiver {



    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "hello", durable = "true"), exchange = @Exchange(value = "spring-boot-exchange", durable = "true"), key = "hello"))
    public void receive(Channel channel, Message message) throws IOException {

        String msg = "[receive] MQData msg :" + new String(message.getBody());
//        System.out.println(msg);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        //重新放入队列
//       channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //抛弃此条消息
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

        //拒绝消息
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);

    }

}

