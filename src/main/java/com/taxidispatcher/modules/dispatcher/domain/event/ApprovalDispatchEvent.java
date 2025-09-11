package com.taxidispatcher.modules.dispatcher.domain.event;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.core.DomainEvent;

public class ApprovalDispatchEvent extends DomainEvent {
    private final DispatchId dispatchId;

    public ApprovalDispatchEvent(DispatchId dispatchId) {
        this.dispatchId = dispatchId;
    }

    public DispatchId dispatchId() {
        return dispatchId;
    }
}
