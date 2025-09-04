package com.taxidispatcher.modules.dispatcher.adapter.event.publisher;

import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.application.port.out.FindDispatchCandidateDriverEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindDispatchCandidateDriverEventPublisherImpl implements FindDispatchCandidateDriverEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(FindDispatchCandidateDriverEvent event) {
        eventPublisher.publishEvent(event);
    }
}
