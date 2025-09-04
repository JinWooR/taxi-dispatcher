package com.taxidispatcher.modules.dispatcher.domain.event;

import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

public record FindDispatchCandidateDriverEvent(
        DispatchId dispatchId
) {
}
