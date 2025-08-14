package com.taxidispatcher.modules.account.domain.model;

public enum AccountStatus {
    ACTIVE, // 활성화
    LOCKED, // 잠김
    SUSPENDED, // 정지
    DELETED; // 삭제

    // 상태 전이 가능 여부
    public boolean canTransition(AccountStatus nextStatus) {
        return switch (this) {
            case ACTIVE -> (nextStatus.equals(LOCKED) || nextStatus.equals(SUSPENDED) || nextStatus.equals(DELETED));
            case LOCKED, SUSPENDED -> (nextStatus.equals(ACTIVE) || nextStatus.equals(DELETED));
            case DELETED -> false;
        };
    }

    // 상태 전이 확인
    public void ensureTransition(AccountStatus nextStatus) {
        if (!canTransition(nextStatus)) {
            throw new IllegalStateException("상태 전이 불가능 : " + this + " -> " + nextStatus);
        }
    }
}
