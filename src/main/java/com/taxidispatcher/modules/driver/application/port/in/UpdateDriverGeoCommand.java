package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.DriverId;

import java.time.Instant;

public record UpdateDriverGeoCommand(
        DriverId driverId,
        DriverGeo curGeo,
        Instant deviceTs,
        Long seq
) {
}
