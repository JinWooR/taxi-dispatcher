package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchGeoHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DispatchGeoHistoryRepositoryImpl implements DispatchGeoHistoryRepository {
    private final DispatchGeoHistoryJpaRepository dispatchGeoHistoryJpaRepository;
}
