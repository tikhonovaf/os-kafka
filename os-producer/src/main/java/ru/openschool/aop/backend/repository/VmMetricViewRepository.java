package ru.openschool.aop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.openschool.aop.backend.model.VmMetricView;

import java.util.List;

public interface VmMetricViewRepository extends JpaRepository<VmMetricView, Long> {

    List<VmMetricView> findAllByVmName(String vmName);



}
