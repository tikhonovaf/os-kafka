package ru.openschool.aop.backend.service;//package ru.openschool.aspect.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.openschool.aop.backend.api.MetricApi;
import ru.openschool.aop.backend.api.MetricApiDelegate;
import ru.openschool.aop.backend.dto.RefRecordDto;
import ru.openschool.aop.backend.dto.RefRecordInDto;
import ru.openschool.aop.backend.dto.VmMetricInDto;
import ru.openschool.aop.backend.dto.VmMetricViewDto;
import ru.openschool.aop.backend.exception.ValidateException;
import ru.openschool.aop.backend.mapper.VmMetricMapper;
import ru.openschool.aop.backend.model.VmMetric;
import ru.openschool.aop.backend.model.MetricType;
import ru.openschool.aop.backend.repository.MetricTypeRepository;
import ru.openschool.aop.backend.repository.VmMetricRepository;
import ru.openschool.aop.backend.repository.VmMetricViewRepository;
import ru.openschool.aop.backend.util.CoreUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для выполнения функций rest сервисов (GET, POST, PATCH, DELETE)
 *
 * @author Аркадий Тихонов
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VmMetricApiService implements MetricApiDelegate {

    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final VmMetricRepository vmMetricRepository;

    private final VmMetricViewRepository vmMetricViewRepository;

    private final MetricTypeRepository vmMetricTypeRepository;

    private final VmMetricMapper vmMetricMapper;

    /**
     * POST /vmMetric/vmMetrics : Добавление метрики
     *
     * @param vmMetricInDto Данные о метрике
     * @return Метрика (status code 200)
     */
    @Override
    public ResponseEntity<VmMetricViewDto> addVmMetric(VmMetricInDto vmMetricInDto) {
        VmMetric vmMetric = vmMetricMapper.fromDtoToEntity(vmMetricInDto);
        kafkaTemplate.send("metrics-topic", vmMetric);

//        vmMetricRepository.save(vmMetric);
//
//        VmMetricViewDto result = vmMetricMapper
//                .fromViewToDto(vmMetricViewRepository.findById(vmMetric.getId()).get());
        return ResponseEntity.noContent().build();    }

    /**
     * POST /vmMetric/vmMetricTypes : Добавление типа метрик
     *
     * @param refRecordInDto Данные о типе метрики
     * @return Тип метрики (status code 200)
     */
    @Override
    public ResponseEntity<RefRecordDto> addMetricType(RefRecordInDto refRecordInDto) {

        MetricType metricType = vmMetricMapper.fromDtoToEntity(refRecordInDto);
        vmMetricTypeRepository.save(metricType);

        RefRecordDto result = vmMetricMapper
                .fromEntityToDto(vmMetricTypeRepository.findById(metricType.getId()).get());
        return ResponseEntity.ok(result);

    }


    /**
     * GET /vmMetric/vmMetrics/{id} : Выборка метрики для ВМ
     *
     * @param vmName Наименование ВМ (required)
     * @return Тип метрики (status code 200)
     */
    @Override
    public ResponseEntity<List<VmMetricViewDto>> getVmMetricForVm(String vmName) {
        List<VmMetricViewDto> result =
                vmMetricViewRepository
                        .findAllByVmName(vmName)
                        .stream()
                        .map(vmMetricMapper::fromViewToDto)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * GET /vmMetric/vmMetricTypes/{id} : Выборка типа метрики
     *
     * @param id Идентификатор (required)
     * @return Тип метрики (status code 200)
     */
    @Override
    public ResponseEntity<RefRecordDto> getMetricType(Long id) {
        if (vmMetricTypeRepository.findById(id).isPresent()) {
            RefRecordDto result = vmMetricMapper
                    .fromEntityToDto(vmMetricTypeRepository.findById(id).get());
            return ResponseEntity.ok(result);
        } else {
            throw ValidateException.notFound("Тип метрики", id);
        }

    }

    /**
     * GET /vmMetric/vmMetricTypes : Выборка типов метрик
     *
     * @return Список типов метрик (status code 200)
     */
    @Override
//    @TrackAsyncTime
    public ResponseEntity<List<RefRecordDto>> getMetricTypes() {

        List<RefRecordDto> result =
                vmMetricTypeRepository
                        .findAll()
                        .stream()
                        .map(vmMetricMapper::fromEntityToDto)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**
     * GET /vmMetric/vmMetrics : Выборка метрик
     *
     * @return Список метрик (status code 200)
     */
    @Override
//    @TrackTime
    public ResponseEntity<List<VmMetricViewDto>> getVmMetrics() {
        List<VmMetricViewDto> result =
                vmMetricViewRepository
                        .findAll()
                        .stream()
                        .map(vmMetricMapper::fromViewToDto)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * PATCH /vmMetric/vmMetricTypes/{id} : Изменение типа метрик
     *
     * @param id Идентификатор (required)
     * @param refRecordInDto Данные о типе метрики
     * @return Тип метрики (status code 200)
     */
    @Override
    public ResponseEntity<RefRecordDto> modifyMetricType(Long id,
                                                         RefRecordInDto refRecordInDto) {
        if (vmMetricTypeRepository.findById(id).isPresent()) {
            MetricType entity = vmMetricTypeRepository.findById(id).get();
            MetricType entityNew = vmMetricMapper.fromDtoToEntity(refRecordInDto);
            CoreUtil.patch(entityNew, entity);
            vmMetricTypeRepository.save(entity);
            RefRecordDto result = vmMetricMapper
                    .fromEntityToDto(vmMetricTypeRepository.findById(entity.getId()).get());
            return ResponseEntity.ok(result);
        } else {
            throw ValidateException.notFound("Метрика", id);
        }
    }
}

