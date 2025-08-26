package com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "DRIVER_WORK_HISTORIES")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DriverWorkHistoryJpaEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID workHistoryId;

    /**
     * @see DriverJpaEntity#getDriverId()
     */
    @Column(name = "driver_id", nullable = false, updatable = false)
    private UUID driverId;

    @Column(nullable = false, updatable = false)
    private Instant attendanceDt;

    @Column
    private Instant leaveWorkDt;

    public DriverWorkHistoryJpaEntity(UUID workHistoryId, UUID driverId, Instant attendanceDt, Instant leaveWorkDt) {
        this.workHistoryId = workHistoryId;
        this.driverId = driverId;
        this.attendanceDt = attendanceDt;
        this.leaveWorkDt = leaveWorkDt;
    }

    public DriverWorkHistoryJpaEntity(UUID workHistoryId) {
        this.workHistoryId = workHistoryId;
    }

    public void updateLeaveWorkDt(Instant leaveWorkDt) {
        this.leaveWorkDt = leaveWorkDt;
    }
}


