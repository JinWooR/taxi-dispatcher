package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DriverWorkHistoryJpaRepository extends JpaRepository<DriverWorkHistoryJpaEntity, UUID> {
    @Query("""
select dwh
from DriverWorkHistoryJpaEntity dwh
where dwh.driverId = :driverId and dwh.leaveWorkDt is null
""")
    Optional<DriverWorkHistoryJpaEntity> findByActiveOne(UUID driverId);
}
