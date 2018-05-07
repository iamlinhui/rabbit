package cn.promptness.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author : Lynn
 * @Date : 2018-05-06 23:12
 */
@Component
public class RabbitMQProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    private HashMap<CorrelationData, String> msgMap = new HashMap();
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public CorrelationData sendMsg(String ROUTING_KEY, String content) throws AmqpException {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.msgMap.put(correlationId, content);
        this.rabbitTemplate.convertAndSend("spring-boot-exchange", ROUTING_KEY, content, correlationId);
        return correlationId;
    }

    public boolean confirmMsg(CorrelationData correlationData) {
        return !this.msgMap.containsKey(correlationData);
    }

    /**
     *  ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("回调id:" + correlationData + " cause:" + cause);
        if (ack) {
            this.msgMap.remove(correlationData);
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }

    }

    /**
     * ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey){

        System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);

    }
}

