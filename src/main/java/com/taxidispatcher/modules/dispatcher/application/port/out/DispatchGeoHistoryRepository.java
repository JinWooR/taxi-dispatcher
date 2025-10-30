package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchDriverGeoHistory;

import java.util.List;
import java.util.UUID;

public interface DispatchGeoHistoryRepository {
    void save(DispatchDriverGeoHistory dispatchDriverGeoHistory);

    List<DispatchDriverGeoHistory> findByDispatchId(UUID dispatchId);
}
