package com.taxidispatcher.modules.dispatcher.domain.event;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.core.DomainEvent;

public class FindDispatchCandidateDriverEvent extends DomainEvent {
    private final DispatchId dispatchId;
    private final long delaySeconds;

    public FindDispatchCandidateDriverEvent(DispatchId dispatchId) {
        this.dispatchId = dispatchId;
        this.delaySeconds = 0;
    }

    public FindDispatchCandidateDriverEvent(DispatchId dispatchId, long delaySeconds) {
        this.dispatchId = dispatchId;
        this.delaySeconds = delaySeconds;
    }

    public DispatchId dispatchId() {
        return dispatchId;
    }

    public long delaySeconds() {
        return delaySeconds;
    }
}
