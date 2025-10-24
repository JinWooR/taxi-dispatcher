package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper.DispatchGeoHistoryMapper;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchGeoHistoryRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchDriverGeoHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DispatchGeoHistoryRepositoryImpl implements DispatchGeoHistoryRepository {
    private final DispatchGeoHistoryJpaRepository dispatchGeoHistoryJpaRepository;
    private final DispatchGeoHistoryMapper dispatchGeoHistoryMapper;

    @Override
    public void save(DispatchDriverGeoHistory dispatchDriverGeoHistory) {
        dispatchGeoHistoryJpaRepository.save(dispatchGeoHistoryMapper.toJpa(dispatchDriverGeoHistory));
    }
}
