package com.taxidispatcher.modules.dispatcher.adapter.client.dto;

import java.util.UUID;

public record DriverInfoResponse(
        UUID driverId,
        String name,
        String taxiNumber,
        String textSize,
        String color
) {
}
