package com.taxidispatcher.modules.dispatcher.domain.event;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.core.DomainEvent;

public class StopFindDispatchCandidateDriverEvent extends DomainEvent {
    private final DispatchId dispatchId;

    public StopFindDispatchCandidateDriverEvent(DispatchId dispatchId) {
        this.dispatchId = dispatchId;
    }

    public DispatchId dispatchId() {
        return dispatchId;
    }
}
