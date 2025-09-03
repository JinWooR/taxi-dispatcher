package com.taxidispatcher.modules.dispatcher.domain.model;

public enum DispatchStatus {
    REQUEST, // 배차 요청
    FAILED, // 배차 실패
    DISPATCHED, // 배차됨 (기사 도착 대기 상태)
    DRIVING, // 운행중
    ARRIVAL, // 목적지 도착
    COMPLETE // 완료
    ;

    // 상태 전이 가능 여부
    public boolean canTransition(DispatchStatus nextStatus) {
        return switch (this) {
            case REQUEST -> nextStatus.equals(DISPATCHED) || nextStatus.equals(FAILED);
            case DISPATCHED -> nextStatus.equals(DRIVING) || nextStatus.equals(REQUEST);
            case DRIVING -> nextStatus.equals(ARRIVAL);
            case ARRIVAL -> nextStatus.equals(COMPLETE);
            case FAILED, COMPLETE -> false;
        };
    }

    // 상태 전이 확인
    public void ensureTransition(DispatchStatus nextStatus) {
        if (!canTransition(nextStatus)) {
            throw new IllegalStateException("상태 전이 불가능 : " + this + " -> " + nextStatus);
        }
    }
}
