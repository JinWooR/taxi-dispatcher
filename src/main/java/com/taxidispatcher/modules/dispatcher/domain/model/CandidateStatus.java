package com.taxidispatcher.modules.dispatcher.domain.model;

public enum CandidateStatus {
    REQUEST, // 요청중
    REJECT, // 거절
    APPROVAL, // 승인
    APPROVAL_CANCEL, // 승인 취소
    TIME_OUT // 시간 초과
    ;

    public boolean canTransition(CandidateStatus nextStatus) {
        return switch (this) {
            case REQUEST -> nextStatus.equals(APPROVAL) || nextStatus.equals(TIME_OUT);
            case APPROVAL -> nextStatus.equals(APPROVAL_CANCEL);
            case REJECT, APPROVAL_CANCEL, TIME_OUT -> false;
        };
    }

    public void ensureTransition(CandidateStatus nextStatus) {
        if (!canTransition(nextStatus)) {
            throw new IllegalStateException("상태 전이 불가능 : " + this + " -> " + nextStatus);
        }
    }
}
