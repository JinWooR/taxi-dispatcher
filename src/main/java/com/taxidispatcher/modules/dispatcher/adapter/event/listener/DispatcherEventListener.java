package com.taxidispatcher.modules.dispatcher.adapter.event.listener;

import com.taxidispatcher.modules.dispatcher.application.port.in.*;
import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;
import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.shared.core.DomainEvent;
import com.taxidispatcher.shared.core.DomainEventListener;
import com.taxidispatcher.shared.core.domain.driver.event.DriverGeoDomainEvent;
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
            TimeOutDispatchCandidateDriversAdapter timeOutAdapter,
            DispatchDriverGeoHistoryAdapter geoHistoryAdapter
    ) {
        this.handlers = Map.of(
                StopFindDispatchCandidateDriverEvent.class, e -> stopAdapter.handle(((StopFindDispatchCandidateDriverEvent) e).dispatchId()),
                FindDispatchCandidateDriverEvent.class, e -> findAdapter.handle(((FindDispatchCandidateDriverEvent) e).dispatchId()),
                ApprovalDispatchEvent.class, e -> timeOutAdapter.handle(((ApprovalDispatchEvent) e).dispatchId()),
                DriverGeoDomainEvent.class, e -> geoHistoryHandle(geoHistoryAdapter, (DriverGeoDomainEvent) e)
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

    private void geoHistoryHandle(DispatchDriverGeoHistoryAdapter geoHistoryAdapter, DriverGeoDomainEvent e) {
        var command = new DispatchDriverGeoHistoryCommand(
                e.getDriverId(),
                e.getLat(),
                e.getLng(),
                e.getDeviceTs(),
                e.getSeq()
        );

        geoHistoryAdapter.handle(command);
    }
}
