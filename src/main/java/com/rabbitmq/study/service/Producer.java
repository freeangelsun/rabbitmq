package com.rabbitmq.study.service;

import com.rabbitmq.study.dto.ProducerMessage;
import com.rabbitmq.study.dto.ReceiverMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private final RabbitTemplate rabbitTemplate;
    private static long producerId = 0;

    public Object sendMessage(ProducerMessage producerMessage){

        ++producerId;

        MessagePostProcessor messagePostProcessor = messagePostProcessorMessage ->{
            messagePostProcessorMessage.getMessageProperties().setReceivedExchange("testMQ.direct.reply.exchange");
            messagePostProcessorMessage.getMessageProperties().setReceivedRoutingKey("test.A.reply.RoutingKey");
            messagePostProcessorMessage.getMessageProperties().setReplyTo("test.A.Reply.Queues");
            //messagePostProcessorMessage.getMessageProperties().setReceivedExchange("testMQ.direct.exchange");
            //messagePostProcessorMessage.getMessageProperties().setReceivedRoutingKey("test.A.Queues.RoutingKey");
            //messagePostProcessorMessage.getMessageProperties().setReplyTo("test.A.Queues");
            messagePostProcessorMessage.getMessageProperties().setCorrelationId("id_20230419_"+producerId);
            messagePostProcessorMessage.getMessageProperties().setRedelivered(false);
            return messagePostProcessorMessage;
        };

        //송신
        //rabbitTemplate.convertAndSend("testMQ.direct.exchange", "test.A.Queues.RoutingKey", producerMessage);

        // 송수신
        log.info("Producer Message : {} {} {}",producerId, producerMessage.getTitle(), producerMessage.getMessage());
        //ReceiverMessage receiverMessage = new ReceiverMessage("1","2");
        //rabbitTemplate.convertSendAndReceive("testMQ.direct.exchange", "test.A.Queues.RoutingKey", producerMessage, messagePostProcessor);
        Object receiverMessage =  rabbitTemplate.convertSendAndReceive("testMQ.direct.exchange", "test.A.Queues.RoutingKey", producerMessage, messagePostProcessor);

        log.info("Producer receiverMessage : {}", receiverMessage);
      //  log.info("Producer receiverMessage : {} {} {}",producerId, receiverMessage.getTitle(), receiverMessage.getReceiverMessage());

        return receiverMessage;
    }
}
