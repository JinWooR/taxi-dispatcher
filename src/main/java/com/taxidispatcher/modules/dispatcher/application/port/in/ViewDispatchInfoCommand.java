package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

import java.util.UUID;

public record ViewDispatchInfoCommand(
        DispatchId dispatchId,
        UUID userId,
        UUID driverId
) {
    public static ViewDispatchInfoCommand ofUser(DispatchId dispatchId, UUID userId) {
        return new ViewDispatchInfoCommand(dispatchId, userId, null);
    }

    public static ViewDispatchInfoCommand ofDriver(DispatchId dispatchId, UUID driverId) {
        return new ViewDispatchInfoCommand(dispatchId, null, driverId);
    }
}
