package com.taxidispatcher.shared.core.domain.driver.event;

import com.taxidispatcher.shared.core.DomainEvent;

import java.time.Instant;

public class DriverGeoDomainEvent extends DomainEvent {
    private final Double lat;
    private final Double lng;
    private final Instant deviceTs;
    private final Long seq;

    public DriverGeoDomainEvent(Double lat, Double lng, Instant deviceTs, Long seq) {
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
        this.seq = seq;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Instant getDeviceTs() {
        return deviceTs;
    }

    public Long getSeq() {
        return seq;
    }
}
