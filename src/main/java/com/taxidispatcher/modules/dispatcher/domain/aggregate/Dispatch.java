package com.taxidispatcher.modules.dispatcher.domain.aggregate;

import com.taxidispatcher.modules.dispatcher.domain.model.AddressGeoInfo;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Dispatch {
    private final DispatchId id;

    // 배차 상태
    private DispatchStatus status;

    // 요청자
    private final UUID userId;

    // 배차 기사
    private UUID driverId;

    // 출발지 (지역명 + 위경도)
    private final AddressGeoInfo startAddr;

    // 도착지 (지역명 + 위경도)
    private final AddressGeoInfo arrivalAddr;

    private final Instant requestDate; // 배차 요청 시간
    private Instant failedDate; // 배차 실패 시간
    private Instant dispatchedDate; // 배차 승인 시간
    private Instant startDate; // 출발 시간
    private Instant arrivalDate; // 목적지 도착 시간
    private Instant completeDate; // 완료 시간

    public Dispatch(DispatchId id, DispatchStatus status, UUID userId, UUID driverId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate, Instant failedDate, Instant dispatchedDate, Instant startDate, Instant arrivalDate, Instant completeDate) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.driverId = driverId;
        this.startAddr = startAddr;
        this.arrivalAddr = arrivalAddr;
        this.requestDate = requestDate;
        this.failedDate = failedDate;
        this.dispatchedDate = dispatchedDate;
        this.startDate = startDate;
        this.arrivalDate = arrivalDate;
        this.completeDate = completeDate;
    }

    // 첫 생성시
    public static Dispatch createNew(DispatchId id, UUID userId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate) {
        return new Dispatch(id, DispatchStatus.REQUEST, userId, null, startAddr, arrivalAddr, requestDate, null, null, null, null, null);
    }

    // 기존 저장된 데이터를 반영시
    public static Dispatch reconstitute(DispatchId id, DispatchStatus status, UUID userId, UUID driverId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate, Instant failedDate, Instant dispatchedDate, Instant startDate, Instant arrivalDate, Instant completeDate) {
        return new Dispatch(id, status, userId, driverId, startAddr, arrivalAddr, requestDate, failedDate, dispatchedDate, startDate, arrivalDate, completeDate);
    }

    public void updateDriver(UUID driverId) {
        if (this.driverId != null && this.driverId.equals(driverId)) {
            throw new IllegalStateException("해당 배차 요청에 이미 다른 기사가 배차되어있습니다.");
        }

        this.driverId = driverId;
    }

    public void updateStatus(DispatchStatus dispatchStatus, Instant now) {
        this.status.ensureTransition(dispatchStatus);

        switch (dispatchStatus) {
            case REQUEST -> {
                if (this.status.equals(DispatchStatus.DISPATCHED)) {
                    this.dispatchedDate = null;
                }
            }
            case FAILED -> this.failedDate = now;
            case DISPATCHED -> {
                if (this.driverId == null) {
                    throw new IllegalStateException("배차된 기사 정보가 없습니다. 기사를 우선 배차 후 진행해주세요.");
                }
                this.dispatchedDate = now;
            }
            case DRIVING -> this.startDate = now;
            case ARRIVAL -> this.arrivalDate = now;
            case COMPLETE -> this.completeDate = now;
        }

        this.status = dispatchStatus;
    }
}
