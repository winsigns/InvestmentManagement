package com.winsigns.investment.webGateway.webGateway.websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class KafkaController {
    ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	KafkaTemplate kafkaTemplate;

	@GetMapping("/send-kafka")
	public String sendKafka(){
		System.out.println("测试发送kafka");

        MessageKey key = new MessageKey("ECACashPoolMHT",
                13L,"ECACashMeasure",
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

}