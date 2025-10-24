package com.taxidispatcher.modules.dispatcher.adapter.event.listener;

import com.taxidispatcher.modules.dispatcher.application.port.in.FindDispatchCandidateDriverAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.StopFindDispatchCandidateDriverAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.TimeOutDispatchCandidateDriversAdapter;
import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;
import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.shared.core.DomainEvent;
import com.taxidispatcher.shared.core.DomainEventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class DispatcherEventListener implements DomainEventListener {
    private final Map<Class<? extends DomainEvent>, Consumer<DomainEvent>> handlers;

    public DispatcherEventListener(
            FindDispatchCandidateDriverAdapter findAdapter,
            StopFindDispatchCandidateDriverAdapter stopAdapter,
            TimeOutDispatchCandidateDriversAdapter timeOutAdapter
    ) {
        this.handlers = Map.of(
                StopFindDispatchCandidateDriverEvent.class, e -> stopAdapter.handle(((StopFindDispatchCandidateDriverEvent) e).dispatchId()),
                FindDispatchCandidateDriverEvent.class, e -> findAdapter.handle(((FindDispatchCandidateDriverEvent) e).dispatchId()),
                ApprovalDispatchEvent.class, e -> timeOutAdapter.handle(((ApprovalDispatchEvent) e).dispatchId())
        );
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DomainEvent event) {
        var handler = handlers.get(event.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("Event Error: " + event.getClass().getName());
        }

        handler.accept(event);
    }
}
