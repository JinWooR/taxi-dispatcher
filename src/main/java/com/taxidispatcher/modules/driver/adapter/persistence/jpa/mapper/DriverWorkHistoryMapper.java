package com.taxidispatcher.modules.driver.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity.DriverWorkHistoryJpaEntity;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import org.springframework.stereotype.Component;

@Component
public class DriverWorkHistoryMapper {

    public DriverWorkHistoryJpaEntity toJpa(DriverWorkHistory workHistory) {
        return new DriverWorkHistoryJpaEntity(workHistory.getId().id(), workHistory.getDriverId().id(), workHistory.getAttendanceDt(), workHistory.getLeaveWorkDt());
    }

    public DriverWorkHistory toDomain(DriverWorkHistoryJpaEntity workHistoryJpaEntity) {
        DriverId driverId = new DriverId(workHistoryJpaEntity.getDriverId());
        WorkHistoryId workHistoryId = new WorkHistoryId(workHistoryJpaEntity.getWorkHistoryId());

        return DriverWorkHistory.of(workHistoryId, driverId, workHistoryJpaEntity.getAttendanceDt(), workHistoryJpaEntity.getLeaveWorkDt());
    }
}
