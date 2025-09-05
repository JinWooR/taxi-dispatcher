package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchAddressInfoId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchAddressInfoJpaEntity;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchJpaEntity;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.AddressGeoInfo;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DispatchMapper {

    public DispatchJpaEntity toJpa(Dispatch domain) {
        var dispatchId = domain.getId().id();
        var startAddr = domain.getStartAddr();
        var arrivalAddr = domain.getArrivalAddr();

        return new DispatchJpaEntity(
                dispatchId, domain.getStatus(), domain.getUserId(), domain.getDriverId(),
                toAddrJpa(dispatchId, startAddr, DispatchAddressInfoId.DispatchAddressTypeEnum.START),
                toAddrJpa(dispatchId, arrivalAddr, DispatchAddressInfoId.DispatchAddressTypeEnum.ARRIVAL),
                domain.getRequestDate(), domain.getCanceledDate(), domain.getFailedDate(),
                domain.getDispatchedDate(), domain.getStartedDate(), domain.getArrivedDate(),
                domain.getCompletedDate(), domain.getAround(), domain.getAroundSearchTimeOut()
            );
    }

    public Dispatch toDomain(DispatchJpaEntity entity) {
        var dispatchId = new DispatchId(entity.getId());
        var startAddr = toAddrDomain(entity.getStart());
        var arrivalAddr = toAddrDomain(entity.getArrival());

        return new Dispatch(
                dispatchId, entity.getStatus(), entity.getUserId(), entity.getDriverId(),
                startAddr, arrivalAddr,
                entity.getRequestDate(), entity.getCanceledDate(), entity.getFailedDate(),
                entity.getDispatchedDate(), entity.getStartedDate(), entity.getArrivedDate(),
                entity.getCompletedDate(), entity.getAround(), entity.getAroundSearchTimeOut()
            );
    }

    private DispatchAddressInfoJpaEntity toAddrJpa(UUID dispatchId, AddressGeoInfo addrDomain, DispatchAddressInfoId.DispatchAddressTypeEnum type) {
        var id = new DispatchAddressInfoId(type, dispatchId);
        return new DispatchAddressInfoJpaEntity(id, addrDomain.x(), addrDomain.y(), addrDomain.addressName());
    }

    private AddressGeoInfo toAddrDomain(DispatchAddressInfoJpaEntity entity) {
        return new AddressGeoInfo(entity.getAddressName(), entity.getLat(), entity.getLng());
    }
}
