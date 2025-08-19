package com.taxidispatcher.modules.user.domain.model;

public enum UserStatus {
    ACTIVE, // 활성화
    DELETED; // 삭제

    // 상태 전이 가능 여부
    public boolean canTransition(UserStatus nextStatus) {
        return switch (this) {
            case ACTIVE -> nextStatus.equals(DELETED);
            case DELETED -> false;
        };
    }

    // 상태 전이 확인
    public void ensureTransition(UserStatus nextStatus) {
        if (!canTransition(nextStatus)) {
            throw new IllegalStateException("상태 전이 불가능 : " + this + " -> " + nextStatus);
        }
    }
}
