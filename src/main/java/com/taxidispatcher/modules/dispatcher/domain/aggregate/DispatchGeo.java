package com.taxidispatcher.modules.dispatcher.domain.aggregate;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchGeoId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DispatchGeo {
    private final DispatchGeoId id;
    private final String lat; // 위도
    private final String lng; // 경도
    private final Instant deviceTs; // 수집 당시 기기 시간

    private DispatchGeo(DispatchGeoId id, String lat, String lng, Instant deviceTs) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public static DispatchGeo of(DispatchGeoId id, String lat, String lng, Instant deviceTs) {
        return new DispatchGeo(id, lat, lng, deviceTs);
    }
}
