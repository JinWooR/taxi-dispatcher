package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkGeoJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkGeoJpaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverWorkGeoJpaRepository extends JpaRepository<DriverWorkGeoJpaEntity, DriverWorkGeoJpaId> {
}
