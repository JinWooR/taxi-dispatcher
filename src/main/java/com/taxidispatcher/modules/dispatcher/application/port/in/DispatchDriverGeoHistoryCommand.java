package com.taxidispatcher.modules.dispatcher.application.port.in;

import java.time.Instant;
import java.util.UUID;

public record DispatchDriverGeoHistoryCommand(
        UUID driverId,
        Double lat,
        Double lng,
        Instant deviceTs,
        Long seq
) {
}
