package com.taxidispatcher.modules.dispatcher.adapter.event.publisher;

import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.application.port.out.FindDispatchCandidateDriverEventPublisher;
import com.taxidispatcher.shared.core.InMemoryDelayQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindDispatchCandidateDriverEventPublisherImpl implements FindDispatchCandidateDriverEventPublisher {
    private final InMemoryDelayQueue inMemoryDelayQueue;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(FindDispatchCandidateDriverEvent event) {
        if (event.delaySeconds() > 0) {
            inMemoryDelayQueue.schedule(() -> {
                eventPublisher.publishEvent(event);
            }, event.delaySeconds());
        } else {
            eventPublisher.publishEvent(event);
        }
    }
}
