package com.taxidispatcher.modules.driver.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record UpdateDriverGeoRequest(
        @NotBlank String lat,
        @NotBlank String lng,
        Instant deviceTs,
        Long seq
) {
}
