package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

import java.util.UUID;

public record DispatchDrivingArrivalCommand(
        DispatchId dispatchId,
        UUID driverId
) {
}
