package io.joliveira.kafka.sandbox;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@EnableKafka
@Configuration
@Primary
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public static final String TEST_TOPIC =  "test-topic";

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(KafkaProperties properties) {
        Map<String, Object> configProps = properties.buildProducerProperties();

        ProducerFactory producerFactory =  new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean()
    public NewTopic createTestTopic(KafkaProperties properties) {
        LOGGER.info("[Kafka Admin] - Setting up contact DLQ topic...");

        return TopicBuilder.name(TEST_TOPIC)
                .configs(properties.getAdmin().getProperties())
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(KafkaProperties properties) {
        return new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());
    }

}
