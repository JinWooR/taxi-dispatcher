package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.Taxi;

public record UpdateDriverTaxiCommand(
        DriverId driverId,
        Taxi taxi
) {
}
