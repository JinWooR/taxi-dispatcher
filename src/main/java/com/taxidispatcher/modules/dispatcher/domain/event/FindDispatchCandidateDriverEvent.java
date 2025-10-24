package com.taxidispatcher.modules.dispatcher.domain.event;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.core.DomainEvent;

public class FindDispatchCandidateDriverEvent extends DomainEvent {
    private final DispatchId dispatchId;

    public FindDispatchCandidateDriverEvent(DispatchId dispatchId) {
        this.dispatchId = dispatchId;
    }

    public DispatchId dispatchId() {
        return dispatchId;
    }
}
