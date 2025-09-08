package com.taxidispatcher.shared.core;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
