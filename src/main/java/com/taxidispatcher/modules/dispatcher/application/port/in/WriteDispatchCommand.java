package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.model.AddressGeoInfo;

import java.util.UUID;

public record WriteDispatchCommand(
        UUID userId,
        AddressGeoInfo startAddr,
        AddressGeoInfo arrivalAddr
) {
}
