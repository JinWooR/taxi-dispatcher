package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DispatchGeoHistoryJpaRepository extends JpaRepository<DispatchGeoHistoryJpaEntity, DispatchGeoHistoryId> {
    @Query("""
select dgh
from DispatchGeoHistoryJpaEntity dgh
where
    dgh.id.dispatchId = :dispatchId
""")
    List<DispatchGeoHistoryJpaEntity> findByDispatchId(UUID dispatchId);
}
