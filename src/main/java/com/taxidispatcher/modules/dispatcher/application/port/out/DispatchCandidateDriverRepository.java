package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

import java.util.List;

public interface DispatchCandidateDriverRepository {
    List<DispatchCandidateDriver> findByDrivers(DispatchId dispatchId);
    DispatchCandidateDriver save(DispatchCandidateDriver candidateDriver);
    void saveAll(List<DispatchCandidateDriver> candidateDrivers);
}
