package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

public interface FindDispatchCandidateDriverAdapter {
    void handle(DispatchId dispatchId);
}
