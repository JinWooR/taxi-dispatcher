package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;

public interface DriverInfoUseCase {
    Driver handle(DriverId driverId);
}
