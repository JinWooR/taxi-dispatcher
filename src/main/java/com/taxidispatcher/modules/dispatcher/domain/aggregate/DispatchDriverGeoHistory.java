package com.taxidispatcher.modules.dispatcher.domain.aggregate;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchDriverGeoHistoryId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DispatchDriverGeoHistory {
    private final DispatchDriverGeoHistoryId id;

    private final Double lat;
    private final Double lng;
    private final Instant deviceTs;

    private DispatchDriverGeoHistory(DispatchDriverGeoHistoryId id, Double lat, Double lng, Instant deviceTs) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public static DispatchDriverGeoHistory of(DispatchDriverGeoHistoryId id, Double lat, Double lng, Instant deviceTs) {
        return new DispatchDriverGeoHistory(id, lat, lng, deviceTs);
    }
}
