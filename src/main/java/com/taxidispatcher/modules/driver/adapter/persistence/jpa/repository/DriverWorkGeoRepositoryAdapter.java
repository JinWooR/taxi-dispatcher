package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkGeoJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper.DriverWorkGeoMapper;
import com.taxidispatcher.modules.driver.application.port.out.DriverWorkGeoRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkGeo;
import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DriverWorkGeoRepositoryAdapter implements DriverWorkGeoRepository {
    private final DriverWorkGeoJpaRepository driverWorkGeoJpaRepository;
    private final DriverWorkGeoMapper driverWorkGeoMapper;

    @Override
    public WorkGeoId save(DriverWorkGeo workGeo) {
        DriverWorkGeoJpaEntity workGeoJpaEntity = driverWorkGeoMapper.toJpa(workGeo);

        workGeoJpaEntity = driverWorkGeoJpaRepository.save(workGeoJpaEntity);

        return new WorkGeoId(new WorkHistoryId(workGeoJpaEntity.getWorkGeoId().getWorkHistoryId()), workGeoJpaEntity.getWorkGeoId().getSeq());
    }
}
