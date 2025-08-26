package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.DriverId;

public record DeleteDriverCommand(
        DriverId driverId
) {
}
