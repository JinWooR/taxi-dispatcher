package com.taxidispatcher.modules.driver.adapter.web.dto.response;

import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.DriverStatus;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;

import java.util.UUID;

public record DriverResponse(
        UUID driverId,
        DriverStatus status,
        String name,
        Double lat,
        Double lng,
        String taxiNumber,
        TaxiSize taxiSize,
        TaxiColor taxiColor,
        String taxiOtherColor,
        DriverActiveStatus activeStatus
) {
}
