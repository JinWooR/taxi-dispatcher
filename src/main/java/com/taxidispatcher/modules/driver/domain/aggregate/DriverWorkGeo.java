package com.taxidispatcher.modules.driver.domain.aggregate;

import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;

import java.time.Instant;

public class DriverWorkGeo {
    private final WorkGeoId id;
    private final String lat; // 위도
    private final String lng; // 경도
    private final Instant deviceTs; // 수집 당시 기기 시간

    private DriverWorkGeo(WorkGeoId id, String lat, String lng, Instant deviceTs) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public static DriverWorkGeo of(WorkGeoId id, String lat, String lng, Instant deviceTs) {
        return new DriverWorkGeo(id, lat, lng, deviceTs);
    }
}
