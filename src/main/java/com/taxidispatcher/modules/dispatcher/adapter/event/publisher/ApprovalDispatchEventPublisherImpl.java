package com.taxidispatcher.modules.dispatcher.adapter.event.publisher;

import com.taxidispatcher.modules.dispatcher.application.port.out.ApprovalDispatchEventPublisher;
import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalDispatchEventPublisherImpl implements ApprovalDispatchEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void handle(ApprovalDispatchEvent event) {
        eventPublisher.publishEvent(event);
    }
}
