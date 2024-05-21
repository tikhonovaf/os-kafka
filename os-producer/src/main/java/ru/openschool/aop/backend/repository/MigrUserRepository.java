package ru.openschool.aop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.openschool.aop.backend.model.MigrUser;

import java.util.List;
import java.util.Optional;

public interface MigrUserRepository extends JpaRepository<MigrUser, Long> {
    Optional<MigrUser> findByLogin(String login);

}
