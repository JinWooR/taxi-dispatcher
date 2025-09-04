package com.taxidispatcher.modules.dispatcher.adapter.client.dto;

import java.util.List;
import java.util.UUID;

public record CandidateDriverRequest(
        List<UUID> driverIds,
        String x,
        String y,
        Integer distance
) {
}
