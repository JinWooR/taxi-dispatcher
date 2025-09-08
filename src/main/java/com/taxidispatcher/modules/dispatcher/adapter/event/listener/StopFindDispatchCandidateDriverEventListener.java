package com.taxidispatcher.modules.dispatcher.adapter.event.listener;

import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.modules.dispatcher.application.port.in.StopFindDispatchCandidateDriverAdapter;
import com.taxidispatcher.shared.core.DomainEvent;
import com.taxidispatcher.shared.core.DomainEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StopFindDispatchCandidateDriverEventListener implements DomainEventListener {

    /**
     * @param event
     *
     * {@link StopFindDispatchCandidateDriverAdapter#handle(DispatchId)} 주입 및 호출
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void handle(DomainEvent event) {
    }
}
