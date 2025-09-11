package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DispatchCandidateDriverRepository {
    List<DispatchCandidateDriver> findByDrivers(DispatchId dispatchId);
    List<DispatchCandidateDriver> findByDriversAndStatus(DispatchId dispatchId, CandidateStatus status);
    DispatchCandidateDriver save(DispatchCandidateDriver candidateDriver);
    void saveAll(List<DispatchCandidateDriver> candidateDrivers);

    Optional<DispatchCandidateDriver> findByDriver(DispatchId dispatchId, UUID driverId);
}
