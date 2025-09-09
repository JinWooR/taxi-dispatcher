package com.taxidispatcher.modules.driver.adapter.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UpdateDriverGeoRequest(
        @NotNull Double lat,
        @NotNull Double lng,
        Instant deviceTs,
        Long seq
) {
}
