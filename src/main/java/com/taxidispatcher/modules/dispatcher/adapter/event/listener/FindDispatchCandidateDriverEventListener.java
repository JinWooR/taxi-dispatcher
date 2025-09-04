package com.taxidispatcher.modules.dispatcher.adapter.event.listener;

import com.taxidispatcher.modules.dispatcher.application.port.in.FindDispatchCandidateDriverAdapter;
import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FindDispatchCandidateDriverEventListener {
    private final FindDispatchCandidateDriverAdapter candidateDriverAdapter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(FindDispatchCandidateDriverEvent event) {
        candidateDriverAdapter.handle(event.dispatchId());
    }
}
