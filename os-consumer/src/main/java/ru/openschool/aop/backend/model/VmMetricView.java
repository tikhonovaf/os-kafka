package ru.openschool.aop.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class VmMetricView {

    /**
     * Идентификатор
     */
    @Id
    Long id;

    /**
     * Наименование Виртуальной машины
     */
    String vmName;

    /**
     * Идентификатор типа метрики
     */
    Long metricTypeId;

    /**
     * Наименование типа метрики
     */
    String metricTypeName;

    /**
     * Значение метрики
     */
    Long value;

}
