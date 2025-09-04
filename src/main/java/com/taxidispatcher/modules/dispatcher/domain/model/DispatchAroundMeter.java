package com.taxidispatcher.modules.dispatcher.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DispatchAroundMeter {
    PREPARING(0), // 준비중
    M_100(100),
    M_300(300),
    M_1000(1000),
    END(-1) // 종료
    ;

    private final int meters;

    // 다음 범위 확인
    public DispatchAroundMeter nextAround() {
        return switch (this) {
            case PREPARING -> M_100;
            case M_100 -> M_300;
            case M_300 -> M_1000;
            case M_1000 -> END;
            case END -> throw new IllegalStateException("종료 상태.");
        };
    }

    public boolean canTransition(DispatchAroundMeter next) {
        return switch (this) {
            case PREPARING -> next.equals(M_100);
            case M_100 -> next.equals(M_300);
            case M_300 -> next.equals(M_1000);
            case M_1000 -> next.equals(END);
            case END -> false;
        };
    }
}
