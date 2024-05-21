package ru.openschool.aop.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class VmMetric {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * Наименование Виртуальной машины
     */
    @NotNull
    String vmName;

    /**
     * Тип
     */
    @ManyToOne
    MetricType metricType;

    /**
     * Значение метрики
     */
    Long value;

}
