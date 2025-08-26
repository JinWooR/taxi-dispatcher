package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.DriverId;

public record UpdateDriverGeoCommand(
        DriverId driverId,
        DriverGeo curGeo
) {
}
