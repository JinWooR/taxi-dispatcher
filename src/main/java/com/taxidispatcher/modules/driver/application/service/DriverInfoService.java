package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.DriverInfoUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverInfoService implements DriverInfoUseCase {
    private final DriverRepository driverRepository;

    @Override
    public Driver handle(DriverId driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "존재하지 않는 택시 기사 정보입니다."));
    }
}
