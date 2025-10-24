package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchGeoHistoryJpaEntity;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchDriverGeoHistory;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchDriverGeoHistoryId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import org.springframework.stereotype.Component;

@Component
public class DispatchGeoHistoryMapper {

    public DispatchGeoHistoryJpaEntity toJpa(DispatchDriverGeoHistory domain) {
        var id = domain.getId();
        var entityId = new DispatchGeoHistoryId(id.dispatchId().id(), id.seq());

        return new DispatchGeoHistoryJpaEntity(entityId, domain.getLat(), domain.getLng(), domain.getDeviceTs());
    }

    public DispatchDriverGeoHistory toDomain(DispatchGeoHistoryJpaEntity entity) {
        var entityId = entity.getId();
        var id = new DispatchDriverGeoHistoryId(new DispatchId(entityId.getDispatchId()), entityId.getSeq());

        return DispatchDriverGeoHistory.of(id, entity.getLat(), entity.getLng(), entity.getDeviceTs());
    }
}
