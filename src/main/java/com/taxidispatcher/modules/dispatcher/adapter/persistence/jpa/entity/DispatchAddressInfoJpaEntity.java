package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "DISPATCH_ADDRESS_INFO")
@Entity
@Getter
@NoArgsConstructor
public class DispatchAddressInfoJpaEntity {
    @EmbeddedId
    private DispatchAddressInfoId id;

    @Column(nullable = false, updatable = false)
    private Double lat;
    @Column(nullable = false, updatable = false)
    private Double lng;
    @Column
    private String addressName;

    public DispatchAddressInfoJpaEntity(DispatchAddressInfoId id, Double lat, Double lng, String addressName) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.addressName = addressName;
    }
}
