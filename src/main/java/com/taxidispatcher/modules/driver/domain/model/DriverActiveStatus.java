package com.taxidispatcher.modules.driver.domain.model;

public enum DriverActiveStatus {
    LEAVE_WORK, // 퇴근
    WAITING, // (배차) 대기중
    IN_OPERATION, // 배차로 인한 운행중
    PAUSE; // (퇴근X) 잠시 휴식 시간

    // 상태 전이 가능 여부
    public boolean canTransition(DriverActiveStatus nextStatus) {
        return switch (this) {
            case LEAVE_WORK, IN_OPERATION, PAUSE -> nextStatus == WAITING;
            case WAITING -> (nextStatus == LEAVE_WORK || nextStatus == IN_OPERATION || nextStatus == PAUSE);
        };
    }

    // 상태 전이 확인
    public void ensureTransition(DriverActiveStatus nextStatus) {
        if (!canTransition(nextStatus)) {
            throw new IllegalStateException("상태 전이 불가능 : " + this + " -> " + nextStatus);
        }
    }
}
