package com.taxidispatcher.modules.driver.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkHistoryJpaEntity;
import com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper.DriverWorkHistoryMapper;
import com.taxidispatcher.modules.driver.application.port.out.DriverWorkHistoryRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverWorkHistoryRepositoryAdapter implements DriverWorkHistoryRepository {
    private final DriverWorkHistoryJpaRepository driverWorkHistoryJpaRepository;
    private final DriverWorkHistoryMapper driverWorkHistoryMapper;

    @Override
    public Optional<DriverWorkHistory> findByActiveOne(DriverId driverId) {
        DriverWorkHistory workHistory = driverWorkHistoryJpaRepository.findByActiveOne(driverId.id())
                .map(driverWorkHistoryMapper::toDomain)
                .orElse(null);

        return Optional.ofNullable(workHistory);
    }

    @Override
    public WorkHistoryId save(DriverWorkHistory workHistory) {
        DriverWorkHistoryJpaEntity workHistoryJpaEntity = driverWorkHistoryMapper.toJpa(workHistory);

        workHistoryJpaEntity = driverWorkHistoryJpaRepository.save(workHistoryJpaEntity);

        return new WorkHistoryId(workHistoryJpaEntity.getWorkHistoryId());
    }
}
