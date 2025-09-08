package com.taxidispatcher.modules.dispatcher.adapter.event.publisher;

import com.taxidispatcher.modules.dispatcher.application.port.out.StopFindDispatchCandidateDriverEventPublisher;
import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.shared.core.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StopFindDispatchCandidateDriverEventPublisherImpl implements StopFindDispatchCandidateDriverEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(DomainEvent event) {
        if (event instanceof StopFindDispatchCandidateDriverEvent) {
            eventPublisher.publishEvent(event);
        }
    }
}
