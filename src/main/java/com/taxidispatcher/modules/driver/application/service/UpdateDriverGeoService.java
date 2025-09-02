package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverGeoCommand;
import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverGeoUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.application.port.out.DriverWorkGeoRepository;
import com.taxidispatcher.modules.driver.application.port.out.DriverWorkHistoryRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkGeo;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateDriverGeoService implements UpdateDriverGeoUseCase {
    private final DriverRepository driverRepository;
    private final DriverWorkHistoryRepository driverWorkHistoryRepository;
    private final DriverWorkGeoRepository driverWorkGeoRepository;

    @Override
    public Driver handle(UpdateDriverGeoCommand command) {
        Driver driver = driverRepository.findById(command.driverId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "존재하지 않는 택시 기사 정보입니다."));

        DriverGeo curGeo = command.curGeo();

        driver.updateCurGeo(curGeo);
        driver = driverRepository.save(driver);

        if (driver.getActiveStatus() != DriverActiveStatus.LEAVE_WORK) {
            // 출근, 휴식, 운행중인 경우.
            // TODO. 이동 경로 로그화 저장 로직 필요
            
            // DriverWorkHistory 조회
            DriverWorkHistory workHistory = driverWorkHistoryRepository.findByActiveOne(command.driverId())
                    .orElseThrow(() -> new AppException(ErrorCode.CONFLICT, "현재 출근 중인 기사 정보가 아닙니다."));
            
            // DriverWorkGeo 저장
            WorkGeoId workGeoId = new WorkGeoId(workHistory.getId(), command.seq());
            DriverWorkGeo workGeo = DriverWorkGeo.of(workGeoId, curGeo.lat(), curGeo.lng(), command.deviceTs());
            driverWorkGeoRepository.save(workGeo);
        }

        return driver;
    }
}
