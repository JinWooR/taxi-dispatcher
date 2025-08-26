package com.taxidispatcher.modules.driver.domain.aggregate;

import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DriverWorkHistory {
    private final WorkHistoryId id;
    private final DriverId driverId;
    private final Instant attendanceDt; // 출근 시간
    private Instant leaveWorkDt; // 퇴근 시간

    private DriverWorkHistory(WorkHistoryId id, DriverId driverId, Instant attendanceDt, Instant leaveWorkDt) {
        this.id = id;
        this.driverId = driverId;
        this.attendanceDt = attendanceDt;
        this.leaveWorkDt = leaveWorkDt;
    }

    public static DriverWorkHistory of(WorkHistoryId id, DriverId driverId, Instant attendanceDt, Instant leaveWorkDt) {
        return new DriverWorkHistory(id, driverId, attendanceDt, leaveWorkDt);
    }

    public static DriverWorkHistory of(WorkHistoryId id, DriverId driverId, Instant attendanceDt) {
        return new DriverWorkHistory(id, driverId, attendanceDt, null);
    }

    // 퇴근
    public void leaveWork(Instant now) {
        if (leaveWorkDt == null) {
            leaveWorkDt = now;
        }
    }
}
