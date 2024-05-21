package ru.openschool.aop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.openschool.aop.backend.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
