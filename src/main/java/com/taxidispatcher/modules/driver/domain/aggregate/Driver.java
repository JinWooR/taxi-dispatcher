package com.taxidispatcher.modules.driver.domain.aggregate;

import com.taxidispatcher.modules.driver.domain.model.*;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Driver {
    private final DriverId id;
    private final UUID accountId;
    private DriverStatus status;
    private String name; // 기사 이름
    private DriverGeo curGeo; // 현재 기사 위치 정보
    private Taxi taxi; // 차량 정보
    private DriverActiveStatus activeStatus; // 기사 활동 상태 (출근, 퇴근 등)

    private Driver(DriverId id, UUID accountId, DriverStatus status, String name, DriverGeo curGeo, Taxi taxi, DriverActiveStatus activeStatus) {
        this.id = id;
        this.accountId = accountId;
        this.status = status;
        this.name = name;
        this.curGeo = curGeo;
        this.taxi = taxi;
        this.activeStatus = activeStatus;
    }

    public static Driver of(DriverId driverId, UUID accountId, DriverStatus status, String name, DriverGeo coordinate, Taxi taxi, DriverActiveStatus activeStatus) {
        return new Driver(driverId, accountId, status, name, coordinate, taxi, activeStatus);
    }

    private void ensureUpdate() {
        if (status.equals(DriverStatus.DELETED)) {
            throw new IllegalStateException("삭제된 기사 정보입니다.");
        }
    }

    public void updateActiveStatus(DriverActiveStatus activeStatus) {
        ensureUpdate();

        this.activeStatus = activeStatus;
    }

    public void updateTaxi(Taxi taxi) {
        ensureUpdate();

        this.taxi = taxi;
    }

    public void updateCurGeo(DriverGeo driverGeo) {
        ensureUpdate();

        this.curGeo = driverGeo;
    }

    public void delete() {
        ensureUpdate();

        status.ensureTransition(DriverStatus.DELETED);

        if (this.activeStatus != DriverActiveStatus.LEAVE_WORK) {
            throw new IllegalStateException("퇴근 후 기사 삭제 요청을 진행해주세요.");
        }

        this.status = DriverStatus.DELETED;
    }
}
