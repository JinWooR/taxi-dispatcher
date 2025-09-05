package com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Table(name = "DRIVER_WORK_GEOS")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DriverWorkGeoJpaEntity {
    @EmbeddedId
    private DriverWorkGeoJpaId workGeoId;

    @Column(nullable = false, updatable = false)
    private Double lat;
    @Column(nullable = false, updatable = false)
    private Double lng;
    @Column(nullable = false, updatable = false)
    private Instant deviceTs;

    public DriverWorkGeoJpaEntity(DriverWorkGeoJpaId workGeoId, Double lat, Double lng, Instant deviceTs) {
        this.workGeoId = workGeoId;
        this.lat = lat;
        this.lng = lng;
        this.deviceTs = deviceTs;
    }

    public DriverWorkGeoJpaEntity(DriverWorkGeoJpaId workGeoId) {
        this.workGeoId = workGeoId;
    }
}
