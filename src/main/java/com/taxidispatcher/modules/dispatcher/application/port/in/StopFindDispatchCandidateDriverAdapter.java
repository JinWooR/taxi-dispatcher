package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

public interface StopFindDispatchCandidateDriverAdapter {
    void handle(DispatchId dispatchId);
}
