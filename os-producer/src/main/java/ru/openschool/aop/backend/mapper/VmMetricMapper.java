package ru.openschool.aop.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.openschool.aop.backend.dto.VmMetricInDto;
import ru.openschool.aop.backend.dto.VmMetricViewDto;
import ru.openschool.aop.backend.dto.RefRecordDto;
import ru.openschool.aop.backend.dto.RefRecordInDto;
import ru.openschool.aop.backend.model.MetricType;
import ru.openschool.aop.backend.model.VmMetric;
import ru.openschool.aop.backend.model.VmMetricView;
import ru.openschool.aop.backend.repository.MetricTypeRepository;
import ru.openschool.aop.backend.util.CoreUtil;

/**
 * Маппинг:
 * -  между view и dto rest сервиса
 * -  между dto rest сервиса и entity сущности
 */
@Service
@RequiredArgsConstructor
public class VmMetricMapper {

    private final MetricTypeRepository metricTypeRepository;

    /**
     *
     * Маппинг из entity в DTO
     *
     * @param entity - строка из entity
     * @return Данные в структуре DTO
     */
    public RefRecordDto fromEntityToDto(MetricType entity) {
        RefRecordDto result = new RefRecordDto();
        CoreUtil.patch(entity, result);
        return result;
    }

    /**
     *
     * Маппинг из view в DTO
     *
     * @param view - строка из view
     * @return Данные в структуре DTO
     */
    public VmMetricViewDto fromViewToDto(VmMetricView view) {
        VmMetricViewDto result = new VmMetricViewDto();
        CoreUtil.patch(view, result);
        return result;
    }

    /**
     * Маппингиз DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public MetricType fromDtoToEntity(RefRecordInDto dto) {
        MetricType result = new MetricType();
        CoreUtil.patch(dto, result);

        return result;
    }

    /**
     * Маппингиз DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public VmMetric fromDtoToEntity(VmMetricInDto dto) {
        VmMetric result = new VmMetric();
        CoreUtil.patch(dto, result);
        MetricType metricType = metricTypeRepository.findById(dto.getMetricTypeId()).get();
        result.setMetricType(metricType);
        return result;
    }

}
