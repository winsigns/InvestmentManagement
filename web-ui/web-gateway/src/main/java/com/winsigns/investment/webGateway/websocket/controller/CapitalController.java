package com.winsigns.investment.webGateway.webGateway.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;

@Controller
public class CapitalController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = {"measures"})
	public void listen(ConsumerRecord<String, String> record) {
		System.out.println(record);
        messagingTemplate.send("/topic/"+record.key(),
                new GenericMessage(record.value().getBytes()));
	}

}