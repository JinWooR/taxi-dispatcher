package com.taxidispatcher.modules.dispatcher.domain.aggregate;

import com.taxidispatcher.modules.dispatcher.domain.model.CandidateDriverId;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import lombok.Getter;

@Getter // 배차 후보
public class DispatchCandidateDriver {
    private final CandidateDriverId id;

    private CandidateStatus status;

    private DispatchCandidateDriver(CandidateDriverId id, CandidateStatus status) {
        this.id = id;
        this.status = status;
    }

    public static DispatchCandidateDriver createNew(CandidateDriverId id) {
        return new DispatchCandidateDriver(id, CandidateStatus.REQUEST);
    }

    public static DispatchCandidateDriver reconstitute(CandidateDriverId id, CandidateStatus status) {
        return new DispatchCandidateDriver(id, status);
    }

    public void updateStatus(CandidateStatus status) {
        this.status.ensureTransition(status);

        this.status = status;
    }
}
