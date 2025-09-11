package com.taxidispatcher.modules.dispatcher.adapter.event.listener;

import com.taxidispatcher.modules.dispatcher.application.port.in.TimeOutDispatchCandidateDriversAdapter;
import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApprovalDispatchEventListener {
    private final TimeOutDispatchCandidateDriversAdapter timeOutDispatchCandidateDriversAdapter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ApprovalDispatchEvent event) {
        timeOutDispatchCandidateDriversAdapter.handle(event.dispatchId());
    }
}
