package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchJpaEntity;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DispatchJpaRepository extends JpaRepository<DispatchJpaEntity, UUID> {
    @Query("""
        select d
        from DispatchJpaEntity d
        where d.userId = :userId
            and d.status = :status
    """)
    Optional<DispatchJpaEntity> findOneUserIdAndStatus(UUID userId, DispatchStatus status);
}
