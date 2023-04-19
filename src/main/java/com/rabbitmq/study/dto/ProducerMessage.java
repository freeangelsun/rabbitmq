package com.rabbitmq.study.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProducerMessage {
    private String title;
    private String message;
}
