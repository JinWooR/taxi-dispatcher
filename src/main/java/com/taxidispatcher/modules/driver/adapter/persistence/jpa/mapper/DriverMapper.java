package com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverJpaEntity;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public DriverJpaEntity toJpa(Driver driver) {
        DriverId driverId = driver.getId();
        DriverStatus status = driver.getStatus();
        DriverGeo curGeo = driver.getCurGeo();
        Taxi taxi = driver.getTaxi();
        DriverActiveStatus activeStatus = driver.getActiveStatus();

        return new DriverJpaEntity(driverId.id(), driver.getAccountId(), status, driver.getName(), curGeo.lat(), curGeo.lng(), taxi.taxiNumber(), taxi.size(), taxi.color(), taxi.otherColor(), activeStatus);
    }

    public Driver toDomain(DriverJpaEntity driverJpaEntity) {
        DriverId driverId = new DriverId(driverJpaEntity.getDriverId());
        DriverStatus status = driverJpaEntity.getStatus();
        DriverGeo curGeo = new DriverGeo(driverJpaEntity.getLat(), driverJpaEntity.getLng());
        Taxi taxi = new Taxi(driverJpaEntity.getTaxiNumber(), driverJpaEntity.getSize(), driverJpaEntity.getColor(), driverJpaEntity.getOtherColor());
        DriverActiveStatus activeStatus = driverJpaEntity.getActiveStatus();

        return Driver.of(driverId, driverJpaEntity.getAccountId(), status, driverJpaEntity.getName(), curGeo, taxi, activeStatus);
    }
}
