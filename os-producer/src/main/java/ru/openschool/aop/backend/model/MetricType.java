package ru.openschool.aop.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class MetricType {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * Наименование
     */
    @NotNull
    String name;
}
