package com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkGeoJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkGeoJpaId;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkGeo;
import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import org.springframework.stereotype.Component;

@Component
public class DriverWorkGeoMapper {
    public DriverWorkGeoJpaEntity toJpa(DriverWorkGeo driverWorkGeo) {
        DriverWorkGeoJpaId workGeoId = new DriverWorkGeoJpaId(driverWorkGeo.getId().seq(), driverWorkGeo.getId().workHistoryId().id());

        return new DriverWorkGeoJpaEntity(workGeoId, driverWorkGeo.getLat(), driverWorkGeo.getLng(), driverWorkGeo.getDeviceTs());
    }

    public DriverWorkGeo toDomain(DriverWorkGeoJpaEntity driverWorkGeoJpaEntity) {
        WorkHistoryId workHistoryId = new WorkHistoryId(driverWorkGeoJpaEntity.getWorkGeoId().getWorkHistoryId());
        WorkGeoId workGeoId = new WorkGeoId(workHistoryId, driverWorkGeoJpaEntity.getWorkGeoId().getSeq());

        return DriverWorkGeo.of(workGeoId, driverWorkGeoJpaEntity.getLat(), driverWorkGeoJpaEntity.getLng(), driverWorkGeoJpaEntity.getDeviceTs());
    }
}
