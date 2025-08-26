package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverJpaRepository extends JpaRepository<DriverJpaEntity, UUID> {
    Optional<DriverJpaEntity> findByAccountId(UUID accountId);
}
