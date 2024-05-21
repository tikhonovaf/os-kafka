package ru.openschool.aop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.openschool.aop.backend.model.VmMetric;

public interface VmMetricRepository extends JpaRepository<VmMetric, Long> {

}
