package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DispatchCandidateDriverJpaRepository extends JpaRepository<DispatchCandidateDriverJpaEntity, DispatchCandidateDriverId> {

    @Query("""
        select dcd
        from DispatchCandidateDriverJpaEntity dcd
        where dcd.id.dispatchId = :dispatchId
    """)
    List<DispatchCandidateDriverJpaEntity> findByDispatchId(UUID dispatchId);
}
