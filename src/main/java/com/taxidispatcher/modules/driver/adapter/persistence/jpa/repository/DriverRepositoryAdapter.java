package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper.DriverMapper;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final DriverJpaRepository driverJpaRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverId save(Driver driver) {
        DriverJpaEntity driverJpaEntity = driverMapper.toJpa(driver);

        driverJpaEntity = driverJpaRepository.save(driverJpaEntity);

        return new DriverId(driverJpaEntity.getDriverId());
    }
}
