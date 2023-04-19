package com.rabbitmq.study.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReceiverMessage {
    private String title;
    private String receiverMessage;
}
