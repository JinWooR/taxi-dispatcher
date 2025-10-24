package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Table(name = "DISPATCH_GEO_HISTORY")
@Entity
@Getter
@NoArgsConstructor
public class DispatchGeoHistoryJpaEntity {
    @EmbeddedId
    private DispatchGeoHistoryId id;
    @Column(nullable = false, updatable = false)
    private Double lat;
    @Column(nullable = false, updatable = false)
    private Double lng;
    @Column(nullable = false, updatable = false)
    private Instant deviceTs;

    public DispatchGeoHistoryJpaEntity(DispatchGeoHistoryId id, Double lat, Double lng, Instant deviceTs) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public DispatchGeoHistoryJpaEntity(DispatchGeoHistoryId id) {
        this.id = id;
    }
}
