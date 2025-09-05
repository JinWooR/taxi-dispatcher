package com.taxidispatcher.modules.driver.adapter.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record InternalDriverNearbyGeoRequest(
        @NotNull List<@NotNull UUID> driverIds,
        @NotNull Double x,
        @NotNull Double y,
        @NotNull Integer distance
) {
}
