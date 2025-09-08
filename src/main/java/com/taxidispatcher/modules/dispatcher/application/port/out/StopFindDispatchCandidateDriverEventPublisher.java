package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.shared.core.DomainEvent;
import com.taxidispatcher.shared.core.DomainEventPublisher;

public interface StopFindDispatchCandidateDriverEventPublisher extends DomainEventPublisher {
    void publish(DomainEvent event);
}
