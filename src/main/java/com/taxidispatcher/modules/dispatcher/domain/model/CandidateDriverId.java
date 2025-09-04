package com.taxidispatcher.modules.dispatcher.domain.model;

import java.util.UUID;

public record CandidateDriverId(
        DispatchId dispatchId,
        UUID driverId
) {
}
