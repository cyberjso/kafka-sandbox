package io.joliveira.kafka.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = Config.TEST_TOPIC, containerFactory = "kafkaListenerContainerFactory", groupId = "consumer-1")
    public void listen(Message record) {
        LOGGER.info("**** Receiving message ****");
        LOGGER.info(record.getMessage());
    }

}
