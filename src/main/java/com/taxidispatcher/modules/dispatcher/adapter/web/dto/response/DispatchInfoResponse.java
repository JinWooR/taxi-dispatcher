package com.taxidispatcher.modules.dispatcher.adapter.web.dto.response;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;

import java.time.Instant;
import java.util.UUID;

public record DispatchInfoResponse(
        UUID dispatchId, // 배차 아이디
        Driver driver, // 택시 운전자
        DispatchStatus status, // 배차 상태
        AddressGeo startAddress, // 출발지 위치 정보
        AddressGeo arrivalAddress, // 목적지 위치 정보

        Instant requestDate, // 배차 요청 시간
        Instant canceledDate, // 배차 실패 시간
        Instant failedDate, // 배차 실패 시간
        Instant dispatchedDate, // 배차 승인 시간
        Instant startedDate, // 출발 시간
        Instant arrivedDate, // 목적지 도착 시간
        Instant completedDate // 완료 시간
) {
    public record AddressGeo(String address, Double x, Double y) {
    }

    public record Driver(
            UUID driverId,
            String name,
            String taxiNumber,
            String textSize,
            String color
    ) {
    }
}
