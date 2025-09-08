package com.taxidispatcher.shared.core;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T event);
}
