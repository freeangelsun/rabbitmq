package com.rabbitmq.study.service;

import com.rabbitmq.study.dto.ProducerMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    private final RabbitTemplate rabbitTemplate;
    private static long index = 0;


    @RabbitListener(queues = "test.A.Queues")
    public void handleMyQueue(
            @Header(value = AmqpHeaders.REPLY_TO, required = false) String senderId,
            @Header(value = AmqpHeaders.CORRELATION_ID, required = false) String correlationId,
            @Payload ProducerMessage requestMessage
    ) {
        /**
         *  참고 구현
         *  https://dirask.com/posts/Spring-Boot-2-receive-response-message-for-the-request-message-using-RabbitMQ-RPC-DlkNW1
         *  rabbitTemplate.receiveAndReply 사용하는 다른 방법이 있을것 같은데 잘 안되네 ㅋ
         *  나중에 시간날때 더 테스트
         */
        log.info("Consume Message : {} {} {}", ++index, requestMessage.getTitle(), requestMessage.getMessage());
        log.info("Consume senderId : {} ", senderId);
        log.info("Consume correlationId : {} ", correlationId);

        String responseMessage = "{\"title\":\"testMessageTitle...\",\"receiverMessage\":\"testMessageBody...........\"";

        rabbitTemplate.convertAndSend(senderId, responseMessage, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setCorrelationId(correlationId);
            return message;
        });

       /*
        String res = new String(in.getBody(), StandardCharsets.UTF_8);

        log.info("Consume Message : {} {}", ++index, res);
        log.info("Consume Message getReceivedExchange : {} ", in.getMessageProperties().getReceivedExchange());
        log.info("Consume Message getReceivedRoutingKey : {} ", in.getMessageProperties().getReceivedRoutingKey());
        log.info("Consume Message getReplyTo : {} ", in.getMessageProperties().getReplyTo());
        log.info("Consume Message getCorrelationId : {} ", in.getMessageProperties().getCorrelationId());
        log.info("Consume Message getHeaders : {} ", in.getMessageProperties().getHeaders());
       */

    /*
        Message recMsg = MessageBuilder.withBody(genRcvMsg.getBytes(StandardCharsets.UTF_8))
                                .setReplyTo(in.getMessageProperties().getReplyTo())
                                //.setCorrelationId(in.getMessageProperties().getCorrelationId()
                                .setCorrelationId("id_20230419_1").build();

        rabbitTemplate.convertAndSend("testMQ.direct.reply.exchange", "test.A.reply.RoutingKey", recMsg);
  */
    }

/**
 * 이것은 단순히 큐 데이터를 소모만 시킨다.
    @RabbitListener(queues = "test.A.Queues")
    public void consume(ProducerMessage requestMessage) {
        log.info("Consume Message : {} {} {}", ++index, requestMessage.getTitle(), requestMessage.getMessage());
    }
*/
}


