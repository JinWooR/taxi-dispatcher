package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchCandidateDriverJpaEntity;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateDriverId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import org.springframework.stereotype.Component;

@Component
public class DispatchCandidateDriverMapper {

    public DispatchCandidateDriverJpaEntity toJpa(DispatchCandidateDriver domain) {
        var id = new DispatchCandidateDriverId(domain.getId().dispatchId().id(), domain.getId().driverId());

        return new DispatchCandidateDriverJpaEntity(id, domain.getStatus());
    }

    public DispatchCandidateDriver toDomain(DispatchCandidateDriverJpaEntity entity) {
        var dispatchId = new DispatchId(entity.getId().getDispatchId());
        var id = new CandidateDriverId(dispatchId, entity.getId().getDriverId());
        return DispatchCandidateDriver.reconstitute(id, entity.getStatus());
    }
}
