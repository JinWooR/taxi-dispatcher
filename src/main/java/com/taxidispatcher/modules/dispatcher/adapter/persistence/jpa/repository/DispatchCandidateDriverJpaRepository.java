package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatchCandidateDriverJpaRepository extends JpaRepository<DispatchCandidateDriverJpaEntity, DispatchCandidateDriverId> {
}
