package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverWorkHistoryJpaRepository extends JpaRepository<DriverWorkHistoryJpaEntity, UUID> {
}
