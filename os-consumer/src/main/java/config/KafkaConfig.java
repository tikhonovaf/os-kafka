package config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic metrics() {
        return new NewTopic("metrics-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("metrics-topic.DLT", 1, (short) 1);
    }

    @Bean
    public RecordMessageConverter converter(){
        return new JsonMessageConverter();
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> kafkaOperations) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaOperations), new FixedBackOff(1000L, 2));
    }
}
