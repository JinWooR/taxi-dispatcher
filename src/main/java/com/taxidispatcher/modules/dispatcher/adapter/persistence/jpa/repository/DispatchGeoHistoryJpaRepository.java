package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatchGeoHistoryJpaRepository extends JpaRepository<DispatchGeoHistoryJpaEntity, DispatchGeoHistoryId> {
}
