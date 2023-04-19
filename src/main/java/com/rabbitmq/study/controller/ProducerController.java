package com.rabbitmq.study.controller;

import com.rabbitmq.study.dto.ProducerMessage;
import com.rabbitmq.study.service.Producer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);
    private final Producer producer;

    @PostMapping("/send")
    public ResponseEntity send(@RequestBody ProducerMessage message){
        //producer.sendMessage(message);
        //return new ResponseEntity<>("success", HttpStatus.OK);
        return new ResponseEntity<>(producer.sendMessage(message), HttpStatus.OK);
    }
}
