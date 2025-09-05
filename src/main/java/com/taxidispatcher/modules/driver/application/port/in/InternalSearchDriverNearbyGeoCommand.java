package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.DriverId;

import java.util.List;

public record InternalSearchDriverNearbyGeoCommand(
        List<DriverId> driverIds,
        Double x,
        Double y,
        Integer distance
) {
}
