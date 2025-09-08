package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.shared.core.DomainEventPublisher;

public interface StopFindDispatchCandidateDriverEventPublisher extends DomainEventPublisher<StopFindDispatchCandidateDriverEvent> {
    void publish(StopFindDispatchCandidateDriverEvent event);
}
