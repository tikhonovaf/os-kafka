package ru.openschool.aop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.openschool.aop.backend.model.MetricType;

public interface MetricTypeRepository extends JpaRepository<MetricType, Long> {

}
