package com.taxidispatcher.shared.core;

public interface DomainEventListener<T extends DomainEvent> {
    void handle(T event);
}
