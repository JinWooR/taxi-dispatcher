package com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.DriverStatus;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Table(name = "DRIVERS")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DriverJpaEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID driverId;

    @Column(nullable = false, updatable = false)
    private UUID accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status;

    @Column(nullable = false)
    private String name;

    @Column
    private Double lat;

    @Column
    private Double lng;

    @Column(nullable = false)
    private String taxiNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxiSize size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxiColor color;

    @Column
    private String otherColor;

    @Enumerated(EnumType.STRING)
    @Column
    private DriverActiveStatus activeStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updatedAt;

    public DriverJpaEntity(UUID driverId, UUID accountId, DriverStatus status, String name, Double lat, Double lng, String taxiNumber, TaxiSize size, TaxiColor color, String otherColor, DriverActiveStatus activeStatus) {
        this.driverId = driverId;
        this.accountId = accountId;
        this.status = status;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.taxiNumber = taxiNumber;
        this.size = size;
        this.color = color;
        this.otherColor = otherColor;
        this.activeStatus = activeStatus;
    }

    public DriverJpaEntity(UUID driverId) {
        this.driverId = driverId;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
