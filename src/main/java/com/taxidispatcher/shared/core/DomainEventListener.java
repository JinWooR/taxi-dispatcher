package com.taxidispatcher.shared.core;

public interface DomainEventListener {
    void handle(DomainEvent event);
}
