package com.taxidispatcher.modules.dispatcher.domain.aggregate;

import com.taxidispatcher.modules.dispatcher.domain.model.*;
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
    private Instant canceledDate; // 배차 실패 시간
    private Instant failedDate; // 배차 실패 시간
    private Instant dispatchedDate; // 배차 승인 시간
    private Instant startedDate; // 출발 시간
    private Instant arrivedDate; // 목적지 도착 시간
    private Instant completedDate; // 완료 시간

    private DispatchAroundMeter around; // 주변 n 미터
    private Instant aroundSearchTimeOut; // 주변 n 미터 조회 타임 아웃

    public Dispatch(DispatchId id, DispatchStatus status, UUID userId, UUID driverId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate, Instant canceledDate, Instant failedDate, Instant dispatchedDate, Instant startedDate, Instant arrivedDate, Instant completedDate, DispatchAroundMeter around, Instant aroundSearchTimeOut) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.driverId = driverId;
        this.startAddr = startAddr;
        this.arrivalAddr = arrivalAddr;
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

    // 첫 생성시
    public static Dispatch createNew(DispatchId id, UUID userId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate) {
        return new Dispatch(id, DispatchStatus.REQUEST, userId, null, startAddr, arrivalAddr, requestDate, null, null, null, null, null, null, DispatchAroundMeter.PREPARING, null);
    }

    // 기존 저장된 데이터를 반영시
    public static Dispatch reconstitute(DispatchId id, DispatchStatus status, UUID userId, UUID driverId, AddressGeoInfo startAddr, AddressGeoInfo arrivalAddr, Instant requestDate, Instant cancelDate, Instant failedDate, Instant dispatchedDate, Instant startDate, Instant arrivalDate, Instant completeDate, DispatchAroundMeter around, Instant aroundSearchTimeOut) {
        return new Dispatch(id, status, userId, driverId, startAddr, arrivalAddr, requestDate, cancelDate, failedDate, dispatchedDate, startDate, arrivalDate, completeDate, around, aroundSearchTimeOut);
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
            case CANCEL -> this.canceledDate = now;
            case FAILED -> this.failedDate = now;
            case DISPATCHED -> {
                if (this.driverId == null) {
                    throw new IllegalStateException("배차된 기사 정보가 없습니다. 기사를 우선 배차 후 진행해주세요.");
                }
                this.dispatchedDate = now;
            }
            case DRIVING -> this.startedDate = now;
            case ARRIVAL -> this.arrivedDate = now;
            case COMPLETE -> this.completedDate = now;
        }

        this.status = dispatchStatus;
    }

    public DispatchAroundMeter nextAround() {
        return this.around.nextAround();
    }

    public void nextAround(Instant timeOut) {
        if (!this.status.equals(DispatchStatus.REQUEST)) {
            throw new IllegalStateException("배차 요청(REQUEST) 상태에만 호출 가능 (현재 상태:" + this.status + ")");
        }

        this.around = this.around.nextAround();
        this.aroundSearchTimeOut = timeOut;
    }
}
