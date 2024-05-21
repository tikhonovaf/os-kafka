package ru.openschool.aop.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.openschool.aop.backend.model.VmMetric;
import ru.openschool.aop.backend.repository.VmMetricRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class VmMetricsListener {

    private final KafkaTemplate<Object, Object> template;

    private final VmMetricRepository vmMetricRepository;

    @KafkaListener(id = "metricId", topics = "metrics-topic")
    public void listenMetrics(VmMetric vmMetric) {
        vmMetricRepository.save(vmMetric);
        log.info("======  Запись в БД для ВМ: {}  метрики: {}, значение: {}", vmMetric.getVmName(), vmMetric.getMetricType().getName(), vmMetric.getValue() );
    }

    @KafkaListener(id = "dltGroup", topics = "metrics-topic.DLT")
    public void dltListen(byte[] in) {
        log.info("Received from DLT: {}", new String(in));
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown: {}", object);
    }

    @DltHandler
    public void listenDlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Received from DLT: {}, topic: {}, offset: {}", in, topic, offset);
    }
}
