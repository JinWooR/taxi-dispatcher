package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverActiveStatusCommand;
import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverActiveStatusUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.application.port.out.DriverWorkHistoryRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateDriverActiveStatusService implements UpdateDriverActiveStatusUseCase {
    private final DriverRepository driverRepository;
    private final DriverWorkHistoryRepository driverWorkHistoryRepository;

    @Override
    public Driver handle(UpdateDriverActiveStatusCommand command) {
        Driver driver = driverRepository.findById(command.driverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 택시 기사 정보입니다."));

        driver.updateActiveStatus(command.activeStatus());
        driver = driverRepository.save(driver);

        switch (command.activeStatus()) {
            case WAITING, LEAVE_WORK -> {
                DriverWorkHistory workHistory = driverWorkHistoryRepository.findByActiveOne(command.driverId())
                        .orElse(null);

                Instant now = Instant.now(Clock.systemUTC());

                if (workHistory == null) {
                    if (command.activeStatus() == DriverActiveStatus.WAITING) {
                        workHistory = DriverWorkHistory.of(
                                WorkHistoryId.newId(),
                                command.driverId(),
                                now
                        );
                    }
                } else {
                    if (command.activeStatus() == DriverActiveStatus.LEAVE_WORK) {
                        workHistory.leaveWork(now);
                    }
                }

                if (workHistory != null) {
                    driverWorkHistoryRepository.save(workHistory);
                }
            }
        };

        return driver;
    }
}
