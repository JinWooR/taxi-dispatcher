package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class DispatchAddressInfoId implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private DispatchAddressTypeEnum type;

    /** @see DispatchJpaEntity#getId()  */
    @Column(name = "dispatch_id", nullable = false, updatable = false)
    private UUID dispatchId;

    public enum DispatchAddressTypeEnum {
        START, // 출발지
        ARRIVAL; // 도착지
    }
}
