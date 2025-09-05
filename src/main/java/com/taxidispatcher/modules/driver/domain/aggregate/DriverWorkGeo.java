package com.taxidispatcher.modules.driver.domain.aggregate;

import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DriverWorkGeo {
    private final WorkGeoId id;
    private final Double lat; // 위도
    private final Double lng; // 경도
    private final Instant deviceTs; // 수집 당시 기기 시간

    private DriverWorkGeo(WorkGeoId id, Double lat, Double lng, Instant deviceTs) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public static DriverWorkGeo of(WorkGeoId id, Double lat, Double lng, Instant deviceTs) {
        return new DriverWorkGeo(id, lat, lng, deviceTs);
    }
}
