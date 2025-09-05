package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class DispatchCandidateDriverId implements Serializable {
    @Column(nullable = false, updatable = false)
    private UUID dispatchId;
    @Column(nullable = false, updatable = false)
    private UUID driverId;
}
