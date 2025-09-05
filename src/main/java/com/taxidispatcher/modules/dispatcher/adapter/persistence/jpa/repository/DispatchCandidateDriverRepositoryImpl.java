package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper.DispatchCandidateDriverMapper;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DispatchCandidateDriverRepositoryImpl implements DispatchCandidateDriverRepository {
    private final DispatchCandidateDriverJpaRepository dispatchCandidateDriverJpaRepository;
    private final DispatchCandidateDriverMapper dispatchCandidateDriverMapper;

    @Override
    public List<DispatchCandidateDriver> findByDrivers(DispatchId dispatchId) {
        return dispatchCandidateDriverJpaRepository.findByDispatchId(dispatchId);
    }

    @Override
    public DispatchCandidateDriver save(DispatchCandidateDriver candidateDriver) {
        var entity = dispatchCandidateDriverMapper.toJpa(candidateDriver);

        entity = dispatchCandidateDriverJpaRepository.save(entity);

        return dispatchCandidateDriverMapper.toDomain(entity);
    }

    @Override
    public void saveAll(List<DispatchCandidateDriver> candidateDrivers) {
        if (Optional.ofNullable(candidateDrivers).isEmpty()) {
            return;
        }

        var entities = candidateDrivers.stream()
                .map(dispatchCandidateDriverMapper::toJpa)
                .toList();

        dispatchCandidateDriverJpaRepository.saveAll(entities);
    }
}
