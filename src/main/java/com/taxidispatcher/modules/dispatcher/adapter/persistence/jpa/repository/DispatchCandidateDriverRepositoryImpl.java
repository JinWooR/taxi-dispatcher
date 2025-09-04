package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DispatchCandidateDriverRepositoryImpl implements DispatchCandidateDriverRepository {
    @Override
    public List<DispatchCandidateDriver> findByDrivers(DispatchId dispatchId) {
        return Collections.emptyList();
    }

    @Override
    public DispatchCandidateDriver save(DispatchCandidateDriver candidateDriver) {
        return null;
    }

    @Override
    public void saveAll(List<DispatchCandidateDriver> candidateDrivers) {
        if (Optional.ofNullable(candidateDrivers).isEmpty()) {
            return;
        }
    }
}
