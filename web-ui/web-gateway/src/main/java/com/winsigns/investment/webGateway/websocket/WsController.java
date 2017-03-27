package com.winsigns.investment.webGateway.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Controller
public class WsController {
    ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	KafkaTemplate kafkaTemplate;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

	@MessageMapping("/welcome")//1
    @SendTo("/topic/getResponse")//2
    public WiselyResponse say(WiselyMessage message) throws Exception {
		System.out.println("welcome 地方地方");
		Thread.sleep(1000); // simulated delay
		System.out.println(message);
        return new WiselyResponse("欢迎, " + message.getName() + "!");
    }

	@GetMapping("/send-kafka")
	public String sendKafka(){
		System.out.println("测试发送kafka");

        MessageKey key = new MessageKey("ECACashPoolMHT",7L,"ECACashMeasure",
                false,"100");
        String tmp = new String();
        try {
            tmp = objectMapper.writeValueAsString(key);
        }catch(Exception e){
            e.printStackTrace();
        }
		//
		kafkaTemplate.send("measures", tmp, "123.565");

        return "test";
	}

    protected static class MessageKey {
        String measureHostType;
        Long measureHostId;
        String measureName;
        boolean isFlost;
        String version;

        public MessageKey(){
        }

        public MessageKey(String measureHostType, Long measureHostId,
                          String measureName, boolean isFlost, String version){
            this.measureHostType = measureHostType;
            this.measureHostId = measureHostId;
            this.measureName = measureName;
            this.isFlost = isFlost;
            this.version = version;
        }

        public String getMeasureHostType() {
            return measureHostType;
        }

        public Long getMeasureHostId() {
            return measureHostId;
        }

        public String getMeasureName() {
            return measureName;
        }

        public boolean isFlost() {
            return isFlost;
        }

        public String getVersion() {
            return version;
        }

        public void setMeasureHostType(String measureHostType) {
            this.measureHostType = measureHostType;
        }

        public void setMeasureHostId(Long measureHostId) {
            this.measureHostId = measureHostId;
        }

        public void setMeasureName(String measureName) {
            this.measureName = measureName;
        }

        public void setFlost(boolean flost) {
            isFlost = flost;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    @KafkaListener(topics = {"measures"})
	public void listen(ConsumerRecord<String, String> record) {
        System.out.println("test");
		System.out.println(record);

//        MessageKey messageKey = null;
//        try {
//            messageKey = objectMapper.readValue(record.key().getBytes(), MessageKey.class);
//            System.out.println(messageKey.getMeasureHostType() + "/" + messageKey.getMeasureHostId() + "/"
//                    + messageKey.getMeasureName());
//            messagingTemplate.send("/topic/"+messageKey.getMeasureHostType() + "/" + messageKey.getMeasureHostId() + "/"
//                    + messageKey.getMeasureName() ,new GenericMessage(record.value().getBytes()));
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
        messagingTemplate.send("/topic/"+record.key(),new GenericMessage(record.value().getBytes()));
	}

}