package com.taxidispatcher.modules.driver.adapter.web.dto.response;

import java.util.UUID;

public record InternalDriverResponse(
        UUID driverId,
        String name,
        String taxiNumber,
        String textSize,
        String color
) {
}
