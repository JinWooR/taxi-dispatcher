package com.taxidispatcher.shared.core.domain.driver.event;

import com.taxidispatcher.shared.core.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class DriverGeoDomainEvent extends DomainEvent {
    private final UUID driverId;
    private final Double lat;
    private final Double lng;
    private final Instant deviceTs;
    private final Long seq;

    public DriverGeoDomainEvent(UUID driverId, Double lat, Double lng, Instant deviceTs, Long seq) {
        this.driverId = driverId;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
        this.seq = seq;
    }

    public UUID getDriverId() {
        return driverId;
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
