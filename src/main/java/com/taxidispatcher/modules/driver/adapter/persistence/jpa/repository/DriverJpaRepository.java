package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverJpaEntity;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverJpaRepository extends JpaRepository<DriverJpaEntity, UUID> {
    Optional<DriverJpaEntity> findByAccountId(UUID accountId);

    @Query("""
        select d.driverId
        from DriverJpaEntity d
        where d.driverId not in (:driverIds)
            and d.status = 'ACTIVE'
            and d.activeStatus = 'WAITING'
            and (d.lat >= :maxLat and d.lat <= :minLat)
            and (d.lng >= :maxLng and d.lng <= :minLng)
    """)
    List<UUID> findByNearbyGeoDriversNotIn(List<DriverId> driverIds, double maxLat, double minLat, double maxLng, double minLng);
}
