package com.taxidispatcher.modules.driver.application.port.out;

import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;

public interface DriverRepository {
    DriverId save(Driver driver);
}
