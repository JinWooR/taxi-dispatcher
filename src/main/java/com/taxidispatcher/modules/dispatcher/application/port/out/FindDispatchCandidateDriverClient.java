package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.CandidateDriverRequest;

import java.util.List;
import java.util.UUID;

public interface FindDispatchCandidateDriverClient {
    List<UUID> callDrivers(CandidateDriverRequest request);
}
