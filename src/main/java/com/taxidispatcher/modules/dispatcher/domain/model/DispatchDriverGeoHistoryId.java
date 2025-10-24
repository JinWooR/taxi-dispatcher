package com.taxidispatcher.modules.dispatcher.domain.model;

import java.util.UUID;

public record DispatchDriverGeoHistoryId(UUID driverId, Long seq) {
}
