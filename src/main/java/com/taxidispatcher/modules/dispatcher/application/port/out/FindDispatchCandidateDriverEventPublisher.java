package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.event.FindDispatchCandidateDriverEvent;

public interface FindDispatchCandidateDriverEventPublisher {
    void publish(FindDispatchCandidateDriverEvent event);
}
