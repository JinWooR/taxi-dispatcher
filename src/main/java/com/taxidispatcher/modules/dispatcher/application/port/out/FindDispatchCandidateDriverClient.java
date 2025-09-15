package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.CandidateDriverRequest;

import java.util.List;

public interface FindDispatchCandidateDriverClient {
    List<String> callDrivers(CandidateDriverRequest request);
}
