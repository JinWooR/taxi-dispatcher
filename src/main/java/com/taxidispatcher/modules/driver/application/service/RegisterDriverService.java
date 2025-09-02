package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.RegisterDriverCommand;
import com.taxidispatcher.modules.driver.application.port.in.RegisterDriverUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.DriverStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDriverService implements RegisterDriverUseCase {
    private final DriverRepository driverRepository;

    @Override
    public Driver handle(RegisterDriverCommand command) {
        if (driverRepository.findByAccountId(command.accountId()).isPresent()) {
            throw new AppException(ErrorCode.CONFLICT, "해당 어카운트는 이미 등록된 택시 기사가 존재합니다.");
        }

        DriverId driverId = DriverId.newId();
        Driver newDriver = Driver.of(driverId, command.accountId(), DriverStatus.ACTIVE, command.name(), new DriverGeo(null, null), command.taxi(), DriverActiveStatus.LEAVE_WORK);

        newDriver = driverRepository.save(newDriver);

        return newDriver;
    }
}
