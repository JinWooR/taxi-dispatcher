package com.taxidispatcher.modules.driver.adapter.event.publisher;

import com.taxidispatcher.modules.driver.application.port.out.DriverGeoPublisher;
import com.taxidispatcher.shared.core.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverGeoPublisherImpl implements DriverGeoPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
