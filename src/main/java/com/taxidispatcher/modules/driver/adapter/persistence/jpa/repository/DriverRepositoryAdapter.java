package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper.DriverMapper;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final DriverJpaRepository driverJpaRepository;
    private final DriverMapper driverMapper;

    @Override
    public Optional<Driver> findById(DriverId driverId) {
        var driver = driverJpaRepository.findById(driverId.id())
                .map(driverMapper::toDomain)
                .orElse(null);

        return Optional.ofNullable(driver);
    }

    @Override
    public Optional<Driver> findByAccountId(UUID accountId) {
        var driver = driverJpaRepository.findByAccountId(accountId)
                .map(driverMapper::toDomain)
                .orElse(null);

        return Optional.ofNullable(driver);
    }

    @Override
    public Driver save(Driver driver) {
        DriverJpaEntity driverJpaEntity = driverMapper.toJpa(driver);

        driverJpaEntity = driverJpaRepository.save(driverJpaEntity);

        return driverMapper.toDomain(driverJpaEntity);
    }

    @Override
    public void delete(DriverId driverId) {
    }
}
