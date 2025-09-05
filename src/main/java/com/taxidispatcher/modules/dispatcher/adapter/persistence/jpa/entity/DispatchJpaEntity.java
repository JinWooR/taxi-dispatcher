package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchAroundMeter;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Table(name = "DISPATCHES")
@Entity
@Getter
@NoArgsConstructor
public class DispatchJpaEntity {
    @Id
    private UUID id;

    // 배차 상태
    @Column(nullable = false)
    private DispatchStatus status;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Column
    private UUID driverId;

    // 출발지
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private DispatchAddressInfoJpaEntity start;

    // 도착지
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private DispatchAddressInfoJpaEntity arrival;

    @Column(nullable = false, updatable = false)
    private Instant requestDate; // 배차 요청 시간

    @Column
    private Instant canceledDate; // 배차 실패 시간

    @Column
    private Instant failedDate; // 배차 실패 시간

    @Column
    private Instant dispatchedDate; // 배차 승인 시간

    @Column
    private Instant startedDate; // 출발 시간

    @Column
    private Instant arrivedDate; // 목적지 도착 시간

    @Column
    private Instant completedDate; // 완료 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchAroundMeter around; // 주변 n 미터

    @Column
    private Instant aroundSearchTimeOut; // 주변 n 미터 조회 타임 아웃

    public DispatchJpaEntity(UUID id, DispatchStatus status, UUID userId, UUID driverId, DispatchAddressInfoJpaEntity start, DispatchAddressInfoJpaEntity arrival, Instant requestDate, Instant canceledDate, Instant failedDate, Instant dispatchedDate, Instant startedDate, Instant arrivedDate, Instant completedDate, DispatchAroundMeter around, Instant aroundSearchTimeOut) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.driverId = driverId;
        this.start = start;
        this.arrival = arrival;
        this.requestDate = requestDate;
        this.canceledDate = canceledDate;
        this.failedDate = failedDate;
        this.dispatchedDate = dispatchedDate;
        this.startedDate = startedDate;
        this.arrivedDate = arrivedDate;
        this.completedDate = completedDate;
        this.around = around;
        this.aroundSearchTimeOut = aroundSearchTimeOut;
    }
}
