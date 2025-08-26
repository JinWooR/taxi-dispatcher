package com.taxidispatcher.modules.driver.application.port.out;

import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;

import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
    Optional<Driver> findById(DriverId driverId);
    Optional<Driver> findByAccountId(UUID accountId);

    Driver save(Driver driver);
    void delete(DriverId driverId);
}
